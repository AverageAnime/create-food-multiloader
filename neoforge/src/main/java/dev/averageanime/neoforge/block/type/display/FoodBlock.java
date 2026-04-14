package dev.averageanime.neoforge.block.type.display;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.type.plate.EmptyPlateBlock;
import dev.averageanime.neoforge.block.type.plate.PlateBlock;
import dev.averageanime.neoforge.block.type.plate.SmallPlateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import org.jetbrains.annotations.NotNull;
import dev.averageanime.neoforge.item.util.ItemSpawn;
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
        this.maxStackSize = Math.min(9, Math.max(1, maxStackSize));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(STACK_SIZE, this.maxStackSize));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                removeAllItems(state, level, pos, player);
            } else {
                removeItem(state, level, pos, player);
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected ItemInteractionResult addItem(BlockState state, Level level, BlockPos pos, Player player, ItemStack heldStack) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        int currentStack = state.getValue(STACK_SIZE);

        if (currentStack >= maxStackSize) {
            return ItemInteractionResult.FAIL;
        }

        level.setBlock(pos, state.setValue(STACK_SIZE, currentStack + 1), 3);

        if (!player.isCreative()) {
            heldStack.shrink(1);
        }

        level.playSound(null, pos, getAddSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack heldStack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                                       @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (heldStack.is(this.displayItem.get())) {
            return addItem(state, level, pos, player, heldStack);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, STACK_SIZE);
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return state.getValue(STACK_SIZE);
    }

    protected abstract void handleLastItemRemoved(BlockState state, Level level, BlockPos pos);

    public SoundEvent getAddSound() {
        return SoundEvents.ITEM_FRAME_ADD_ITEM;
    }

    protected SoundEvent getRemoveSound() {
        return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
    }

    public static class Registry {

        private static final Map<Item, List<Supplier<Block>>> ITEM_TO_BLOCKS = new HashMap<>();
        private static final Map<Block, Supplier<Block>> COMPAT_BLOCK_MAPPING = new HashMap<>();

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

        public static boolean isRegistered(Item item) {
            return ITEM_TO_BLOCKS.containsKey(item);
        }
        private static final Set<Item> EMPTY_PLATE_ITEMS = new HashSet<>();

        public static void registerEmptyPlateItem(Supplier<Item> plateItem) {
            EMPTY_PLATE_ITEMS.add(plateItem.get());
        }

        public static boolean isEmptyPlateItem(Item item) {
            return EMPTY_PLATE_ITEMS.contains(item);
        }

        public static void registerCompatBlock(Block compatBlock, Supplier<Block> targetBlock) {
            COMPAT_BLOCK_MAPPING.put(compatBlock, targetBlock);
        }

        public static boolean isCompatBlock(Block block) {
            return COMPAT_BLOCK_MAPPING.containsKey(block);
        }

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

                Map<String, String> modAbbreviations = new HashMap<>();
                modAbbreviations.put("culturaldelights", "ctd");
                modAbbreviations.put("endersdelight", "erd");
                modAbbreviations.put("mynethersdelight", "mnd");
                modAbbreviations.put("oceansdelight", "od");
                modAbbreviations.put("nethersdelight", "nd");
                modAbbreviations.put("corn_delight", "cd");
                modAbbreviations.put("expanded_delight", "ed");
                modAbbreviations.put("delightful", "df");
                modAbbreviations.put("pineapple_delight", "pd");
                modAbbreviations.put("alexsdelight", "ad");
                modAbbreviations.put("largemeals", "lm");
                modAbbreviations.put("festivedelight", "fd");
                modAbbreviations.put("brewinandchewin", "bnc");
                modAbbreviations.put("ends_delight", "edd");
                modAbbreviations.put("crabbersdelight", "crd");
                modAbbreviations.put("aquaculturedelight", "acd");

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

                    ResourceLocation plateBlockId = ResourceLocation.fromNamespaceAndPath("displaydelight", pattern);
                    Block plateBlock = BuiltInRegistries.BLOCK.get(plateBlockId);
                    if (plateBlock != null && plateBlock != Blocks.AIR) {
                        return plateBlock;
                    }
                }
            } catch (Exception ignored) {
            }
            return null;
        }

        public static Block getDisplayDelightSmallPlateBlock(Item item) {
            try {
                ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
                String namespace = itemId.getNamespace();
                String path = itemId.getPath();

                Map<String, String> modAbbreviations = new HashMap<>();
                modAbbreviations.put("culturaldelights", "ctd");
                modAbbreviations.put("endersdelight", "erd");
                modAbbreviations.put("mynethersdelight", "mnd");
                modAbbreviations.put("oceansdelight", "od");
                modAbbreviations.put("nethersdelight", "nd");
                modAbbreviations.put("corn_delight", "cd");
                modAbbreviations.put("expanded_delight", "ed");
                modAbbreviations.put("delightful", "df");
                modAbbreviations.put("pineapple_delight", "pd");
                modAbbreviations.put("alexsdelight", "ad");
                modAbbreviations.put("largemeals", "lm");
                modAbbreviations.put("festivedelight", "fd");
                modAbbreviations.put("brewinandchewin", "bnc");
                modAbbreviations.put("ends_delight", "edd");
                modAbbreviations.put("crabbersdelight", "crd");
                modAbbreviations.put("aquaculturedelight", "acd");

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

                    ResourceLocation plateBlockId = ResourceLocation.fromNamespaceAndPath("displaydelight", pattern);
                    Block plateBlock = BuiltInRegistries.BLOCK.get(plateBlockId);
                    if (plateBlock != null && plateBlock != Blocks.AIR) {
                        return plateBlock;
                    }
                }
            } catch (Exception ignored) {
            }
            return null;
        }

        public static boolean canPlaceOnPlate(Item item, boolean isSmallPlate) {
            if (isRegistered(item)) {
                return true;
            }

            if (isSmallPlate) {
                return getDisplayDelightSmallPlateBlock(item) != null;
            } else {
                return getDisplayDelightPlateBlock(item) != null;
            }
        }
    }

    @EventBusSubscriber(modid = CommonClass.ID)
    public static class PlacementHandler {

        @SubscribeEvent
        public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
            UseOnContext context = event.getUseOnContext();
            List<Supplier<Block>> displayBlockSuppliers = Registry.getAllBlocks(context.getItemInHand().getItem());

            if (displayBlockSuppliers == null || displayBlockSuppliers.isEmpty()) {
                return;
            }

            Level level = context.getLevel();
            BlockPos clickedPos = context.getClickedPos();
            BlockState clickedState = level.getBlockState(clickedPos);
            Block clickedBlock = clickedState.getBlock();

            boolean isCompatPlate = Registry.isCompatBlock(clickedBlock);
            Block compatTargetBlock = isCompatPlate ? Registry.getTargetBlock(clickedBlock) : null;

            Block targetBlock = null;
            for (Supplier<Block> supplier : displayBlockSuppliers) {
                Block block = supplier.get();

                if (block instanceof PlateBlock &&
                        (clickedBlock instanceof EmptyPlateBlock ||
                                (isCompatPlate && compatTargetBlock instanceof EmptyPlateBlock))) {
                    targetBlock = block;
                    break;
                }

                if (block instanceof SmallPlateFoodBlock &&
                        (clickedBlock instanceof SmallPlateBlock ||
                                (isCompatPlate && compatTargetBlock instanceof SmallPlateBlock))) {
                    targetBlock = block;
                    break;
                }

                if (block instanceof BottleFoodBlock || block instanceof BowlFoodBlock ||
                        block instanceof SaladBowlFoodBlock || block instanceof PlateFoodBlock) {
                    targetBlock = block;
                    break;
                }
            }

            if (targetBlock == null) {
                return;
            }

            if (targetBlock instanceof PlateBlock) {
                if (!(clickedBlock instanceof EmptyPlateBlock) &&
                        !(isCompatPlate && compatTargetBlock instanceof EmptyPlateBlock)) {
                    return;
                }
                if (isCompatPlate) {
                } else {
                    return;
                }
            }

            if (targetBlock instanceof SmallPlateFoodBlock) {
                if (!(clickedBlock instanceof SmallPlateBlock) &&
                        !(isCompatPlate && compatTargetBlock instanceof SmallPlateBlock)) {
                    event.cancelWithResult(ItemInteractionResult.FAIL);
                    return;
                }
                if (isCompatPlate) {
                } else {
                    return;
                }
            }

            if (targetBlock instanceof BottleFoodBlock || targetBlock instanceof BowlFoodBlock ||
                    targetBlock instanceof SaladBowlFoodBlock || targetBlock instanceof PlateFoodBlock) {
                Player player = context.getPlayer();
                if (player == null || !player.isShiftKeyDown()) {
                    return;
                }
            }

            if (clickedBlock == targetBlock) {
                return;
            }

            BlockPos placePos;
            if (isCompatPlate) {
                placePos = clickedPos;
            } else if (clickedState.canBeReplaced()) {
                placePos = clickedPos;
            } else {
                placePos = clickedPos.relative(context.getClickedFace());
            }

            if (!isCompatPlate && !level.getBlockState(placePos).canBeReplaced()) {
                return;
            }

            if (!level.isClientSide()) {
                BlockState newState = targetBlock.defaultBlockState();

                Direction facing = context.getHorizontalDirection();
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

                if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                    context.getItemInHand().shrink(1);
                }
            }

            event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide()));
        }
    }
}