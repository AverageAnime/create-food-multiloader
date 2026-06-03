package net.averageanime.createfood.block.display;

import net.averageanime.createfood.block.plate.EmptyPlateBlock;
import net.averageanime.createfood.block.plate.PlateBlock;
import net.averageanime.createfood.block.plate.SmallPlateBlock;
import net.averageanime.createfood.block.plate.SmallPlateFoodBlock;
import net.averageanime.createfood.util.ItemSpawn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import net.averageanime.createfood.block.handler.PlateSliceHandler;

import java.util.*;
import java.util.function.Supplier;

public abstract class FoodBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty STACK_SIZE = IntegerProperty.create("stack_size", 1, 9);

    public final int maxStackSize;
    public final Supplier<Item> displayItem;
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

    public FoodBlock(Properties properties, Supplier<Item> displayItem, int maxStackSize) {
        super(properties);
        this.displayItem = displayItem;
        this.maxStackSize = Mth.clamp(maxStackSize, 1, 9);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(STACK_SIZE, this.maxStackSize));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level,
                                          @NotNull BlockPos pos, @NotNull Player player,
                                          @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);

        // Check off-hand cutting before main use logic
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!player.isShiftKeyDown() && !offHand.isEmpty()
                && PlateSliceHandler.couldSlice(player, level, InteractionHand.OFF_HAND, pos, state)) {
            if (!level.isClientSide) PlateSliceHandler.trySlice(player, level, InteractionHand.OFF_HAND, pos, state);
            return level.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }

        // useItemOn logic: held item is the same as this display item
        if (!heldStack.isEmpty() && heldStack.is(this.displayItem.get())) {
            InteractionResult result = addItem(state, level, pos, player, heldStack);
            if (result != InteractionResult.PASS) return result;
        }

        // useWithoutItem logic
        if (!heldStack.isEmpty()) return InteractionResult.PASS;

        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                removeAllItems(state, level, pos, player);
            } else {
                removeItem(state, level, pos, player);
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected InteractionResult addItem(BlockState state, Level level, BlockPos pos,
                                        Player player, ItemStack heldStack) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        int currentStack = state.getValue(STACK_SIZE);
        if (currentStack >= maxStackSize) return InteractionResult.FAIL;

        level.setBlock(pos, state.setValue(STACK_SIZE, currentStack + 1), 3);
        if (!player.isCreative()) heldStack.shrink(1);
        level.playSound(null, pos, getAddSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }

    protected void removeItem(BlockState state, Level level, BlockPos pos, Player player) {
        int currentStack = state.getValue(STACK_SIZE);
        Direction direction = player.getDirection().getOpposite();
        ItemStack dropStack = new ItemStack(this.displayItem.get());
        ItemSpawn.spawnItemEntity(level, dropStack,
                pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
        if (currentStack > 1) {
            level.setBlock(pos, state.setValue(STACK_SIZE, currentStack - 1), 3);
        } else {
            handleLastItemRemoved(state, level, pos);
        }
        level.playSound(null, pos, getRemoveSound(), SoundSource.BLOCKS, 0.8F, 0.8F);
    }

    protected void removeAllItems(BlockState state, Level level, BlockPos pos, Player player) {
        int currentStack = state.getValue(STACK_SIZE);
        Direction direction = player.getDirection().getOpposite();
        ItemStack dropStack = new ItemStack(this.displayItem.get(), currentStack);
        ItemSpawn.spawnItemEntity(level, dropStack,
                pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
        handleLastItemRemoved(state, level, pos);
        level.playSound(null, pos, getRemoveSound(), SoundSource.BLOCKS, 1.0F, 0.8F);
    }

    /**
     * Shift+LMB: instantly eat one item from this food plate.
     * Returns true if the eat was handled.
     */
    public boolean tryEat(Player player, Level level, BlockPos pos, BlockState state) {
        if (!player.isShiftKeyDown()) return false;
        Item food = this.displayItem.get();
        if (food == null) return false;
        ItemStack foodStack = new ItemStack(food);
        FoodProperties props = foodStack.getItem().getFoodProperties();
        if (props == null) return false;
        if (!player.canEat(props.canAlwaysEat()) && !player.getAbilities().instabuild) return false;
        if (!level.isClientSide) {
            ItemStack copy = foodStack.copyWithCount(1);
            applyEatEffects(copy, props, player, level, pos);
            dropContainerOnEat(player, level, pos);
            int currentStack = state.getValue(STACK_SIZE);
            if (currentStack > 1) {
                level.setBlock(pos, state.setValue(STACK_SIZE, currentStack - 1), 3);
            } else {
                handleLastItemEaten(state, level, pos);
            }
            level.playSound(null, pos, getEatSound(), SoundSource.PLAYERS, 0.5f, 1.0f);
        }
        return true;
    }

    protected void applyEatEffects(ItemStack copy, FoodProperties props, Player player,
                                   Level level, BlockPos pos) {
        player.getFoodData().eat(props.getNutrition(), props.getSaturationModifier());
        for (var entry : props.getEffects()) {
            if (level.random.nextFloat() < entry.getSecond()) {
                player.addEffect(new MobEffectInstance(entry.getFirst())); // copy constructor
            }
        }
        if (!handlesOwnContainer()) {
            ItemStack container = copy.getItem().getCraftingRemainingItem() != null
                    ? new ItemStack(copy.getItem().getCraftingRemainingItem()) : ItemStack.EMPTY;
            if (!container.isEmpty()) {
                if (!player.getInventory().add(container)) player.drop(container, false);
            }
        }
    }

    protected boolean handlesOwnContainer() { return false; }

    protected void dropContainerOnEat(Player player, Level level, BlockPos pos) {}

    protected SoundEvent getEatSound() { return SoundEvents.PLAYER_BURP; }

    protected void handleLastItemEaten(BlockState state, Level level, BlockPos pos) {
        handleLastItemRemoved(state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, STACK_SIZE);
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) { return true; }

    @Override
    public int getAnalogOutputSignal(BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return state.getValue(STACK_SIZE);
    }

    protected abstract void handleLastItemRemoved(BlockState state, Level level, BlockPos pos);

    public SoundEvent getAddSound() { return SoundEvents.ITEM_FRAME_ADD_ITEM; }
    protected SoundEvent getRemoveSound() { return SoundEvents.ITEM_FRAME_REMOVE_ITEM; }

    // ──────────────────────── Registry ────────────────────────────────────────

    public static class Registry {

        private static final Map<Item, List<Supplier<Block>>> ITEM_TO_BLOCKS = new HashMap<>();
        private static final Map<Block, Supplier<Block>> COMPAT_BLOCK_MAPPING = new HashMap<>();
        private static final Set<Item> EMPTY_PLATE_ITEMS = new HashSet<>();

        public static void register(Supplier<Item> item, Supplier<Block> block) {
            ITEM_TO_BLOCKS.computeIfAbsent(item.get(), k -> new ArrayList<>()).add(block);
        }

        public static Supplier<Block> getBlock(Item item) {
            List<Supplier<Block>> blocks = ITEM_TO_BLOCKS.get(item);
            return blocks != null && !blocks.isEmpty() ? blocks.get(0) : null;
        }

        public static List<Supplier<Block>> getAllBlocks(Item item) {
            return ITEM_TO_BLOCKS.getOrDefault(item, new ArrayList<>());
        }

        public static boolean isRegistered(Item item) { return ITEM_TO_BLOCKS.containsKey(item); }

        public static void registerEmptyPlateItem(Supplier<Item> plateItem) {
            EMPTY_PLATE_ITEMS.add(plateItem.get());
        }

        public static boolean isEmptyPlateItem(Item item) { return EMPTY_PLATE_ITEMS.contains(item); }

        public static void registerCompatBlock(Block compatBlock, Supplier<Block> targetBlock) {
            COMPAT_BLOCK_MAPPING.put(compatBlock, targetBlock);
        }

        public static boolean isCompatBlock(Block block) { return COMPAT_BLOCK_MAPPING.containsKey(block); }

        public static Block getTargetBlock(Block compatBlock) {
            Supplier<Block> supplier = COMPAT_BLOCK_MAPPING.get(compatBlock);
            return supplier != null ? supplier.get() : null;
        }

        public static void clear() {
            ITEM_TO_BLOCKS.clear();
            COMPAT_BLOCK_MAPPING.clear();
        }

        public static Block getDisplayDelightPlateBlock(Item item) {
            try {
                ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
                String namespace = itemId.getNamespace();
                String path = itemId.getPath();
                Map<String, String> modAbbreviations = buildModAbbreviations();
                String modPrefix = modAbbreviations.getOrDefault(namespace, "");
                String[] patterns = {
                        modPrefix.isEmpty() ? null : modPrefix + "_plated_" + path,
                        "plated_" + path,
                        namespace + "_plated_" + path,
                        "plated_" + path.replaceFirst("^[a-z]+_", ""),
                        path.replaceFirst("^([a-z]+)_", "$1_plated_")
                };
                for (String pattern : patterns) {
                    if (pattern == null) continue;
                    ResourceLocation plateBlockId = new ResourceLocation("displaydelight", pattern);
                    Block plateBlock = BuiltInRegistries.BLOCK.get(plateBlockId);
                    if (plateBlock != Blocks.AIR) return plateBlock;
                }
            } catch (Exception ignored) {}
            return null;
        }

        public static Block getDisplayDelightSmallPlateBlock(Item item) {
            try {
                ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
                String namespace = itemId.getNamespace();
                String path = itemId.getPath();
                Map<String, String> modAbbreviations = buildModAbbreviations();
                String modPrefix = modAbbreviations.getOrDefault(namespace, "");
                String[] patterns = {
                        modPrefix.isEmpty() ? null : modPrefix + "_small_plated_" + path,
                        "small_plated_" + path,
                        namespace + "_small_plated_" + path,
                        "small_plated_" + path.replaceFirst("^[a-z]+_", ""),
                        path.replaceFirst("^([a-z]+)_", "$1_small_plated_")
                };
                for (String pattern : patterns) {
                    if (pattern == null) continue;
                    ResourceLocation plateBlockId = new ResourceLocation("displaydelight", pattern);
                    Block plateBlock = BuiltInRegistries.BLOCK.get(plateBlockId);
                    if (plateBlock != Blocks.AIR) return plateBlock;
                }
            } catch (Exception ignored) {}
            return null;
        }

        private static Map<String, String> buildModAbbreviations() {
            Map<String, String> m = new HashMap<>();
            m.put("culturaldelights", "ctd"); m.put("endersdelight", "erd");
            m.put("mynethersdelight", "mnd"); m.put("oceansdelight", "od");
            m.put("nethersdelight", "nd");    m.put("corn_delight", "cd");
            m.put("expanded_delight", "ed");  m.put("delightful", "df");
            m.put("pineapple_delight", "pd"); m.put("alexsdelight", "ad");
            m.put("largemeals", "lm");        m.put("festivedelight", "fd");
            m.put("brewinandchewin", "bnc");  m.put("ends_delight", "edd");
            m.put("crabbersdelight", "crd");  m.put("aquaculturedelight", "acd");
            return m;
        }

        public static boolean canPlaceOnPlate(Item item, boolean isSmallPlate) {
            if (isRegistered(item)) return true;
            return isSmallPlate ? getDisplayDelightSmallPlateBlock(item) != null
                                : getDisplayDelightPlateBlock(item) != null;
        }

        public static InteractionResult tryPlace(Player player, Level level, InteractionHand hand,
                                                  BlockPos clickedPos, BlockState clickedState,
                                                  Direction clickedFace) {
            ItemStack heldStack = player.getItemInHand(hand);
            List<Supplier<Block>> displayBlockSuppliers = getAllBlocks(heldStack.getItem());
            if (displayBlockSuppliers == null || displayBlockSuppliers.isEmpty()) return null;

            Block clickedBlock = clickedState.getBlock();
            boolean isCompatPlate = isCompatBlock(clickedBlock);
            Block compatTargetBlock = isCompatPlate ? getTargetBlock(clickedBlock) : null;

            Block targetBlock = null;
            for (Supplier<Block> supplier : displayBlockSuppliers) {
                Block block = supplier.get();
                if (block instanceof PlateBlock
                        && (clickedBlock instanceof EmptyPlateBlock
                            || (isCompatPlate && compatTargetBlock instanceof EmptyPlateBlock))) {
                    targetBlock = block;
                    break;
                }
                if (block instanceof SmallPlateFoodBlock
                        && (clickedBlock instanceof SmallPlateBlock
                            || (isCompatPlate && compatTargetBlock instanceof SmallPlateBlock))) {
                    targetBlock = block;
                    break;
                }
                if (block instanceof BottleFoodBlock || block instanceof BowlFoodBlock
                        || block instanceof SaladBowlFoodBlock || block instanceof PlateFoodBlock) {
                    targetBlock = block;
                    break;
                }
            }

            if (targetBlock == null) return null;
            if (targetBlock instanceof PlateBlock && !isCompatPlate) return null;
            if (targetBlock instanceof SmallPlateFoodBlock && !isCompatPlate) return null;
            if ((targetBlock instanceof BottleFoodBlock || targetBlock instanceof BowlFoodBlock
                    || targetBlock instanceof SaladBowlFoodBlock || targetBlock instanceof PlateFoodBlock)
                    && !player.isShiftKeyDown()) return null;
            if (clickedBlock == targetBlock) return null;

            BlockPos placePos;
            if (isCompatPlate) {
                placePos = clickedPos;
            } else if (clickedState.canBeReplaced()) {
                placePos = clickedPos;
            } else {
                placePos = clickedPos.relative(clickedFace);
            }

            if (!isCompatPlate && !level.getBlockState(placePos).canBeReplaced()) return null;

            if (!level.isClientSide()) {
                BlockState newState = targetBlock.defaultBlockState();
                Direction facing = player.getDirection();
                if (clickedState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                    facing = clickedState.getValue(BlockStateProperties.HORIZONTAL_FACING);
                }
                if (newState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                    newState = newState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
                }
                if (newState.hasProperty(STACK_SIZE)) {
                    newState = newState.setValue(STACK_SIZE, 1);
                }
                level.setBlock(placePos, newState, 3);
                if (targetBlock instanceof FoodBlock stack) {
                    level.playSound(null, placePos, stack.getAddSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playSound(null, placePos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                if (!player.isCreative()) heldStack.shrink(1);
            }

            return level.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }
    }
}
