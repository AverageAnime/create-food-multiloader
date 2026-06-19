package net.averageanime.createfood.block.plate;

import net.averageanime.createfood.block.ModDisplayBlocks;
import net.averageanime.createfood.block.blockentity.GenericDisplayPlateBlockEntity;
import net.averageanime.createfood.block.display.FoodBlock;
import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.averageanime.createfood.util.ItemSpawn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class EmptyPlateBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

    private final Supplier<Block> smallPlateBlock;

    public EmptyPlateBlock(Properties properties, Supplier<Block> smallPlateBlock) {
        super(properties);
        this.smallPlateBlock = smallPlateBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level,
                                          @NotNull BlockPos pos, @NotNull Player player,
                                          @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);

        // Shift (any hand state) → convert to small plate
        if (player.isShiftKeyDown() && smallPlateBlock != null) {
            if (level.isClientSide) return InteractionResult.SUCCESS;
            BlockState smallPlate = smallPlateBlock.get().defaultBlockState();
            if (smallPlate.hasProperty(FACING))
                smallPlate = smallPlate.setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, smallPlate, 3);
            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 1.2F);
            return InteractionResult.SUCCESS;
        }

        if (!heldStack.isEmpty()) {
            if (level.isClientSide) {
                if (FoodBlock.Registry.canPlaceOnPlate(heldStack.getItem(), false))
                    return InteractionResult.SUCCESS;
                if (isGenericDisplayEligible(heldStack.getItem())) {
                    try {
                        if (CreateFoodConfig.SERVER.enableGenericPlates.get()
                                && !ConfigLogic.matchesFilterList(heldStack, CreateFoodConfig.SERVER.genericDisplayExclude.get()))
                            return InteractionResult.SUCCESS;
                    } catch (IllegalStateException ignored) {
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                }
                return InteractionResult.PASS;
            }

            // Try registered display block (PlateBlock variant)
            List<Supplier<Block>> stackBlockSuppliers = FoodBlock.Registry.getAllBlocks(heldStack.getItem());
            if (stackBlockSuppliers != null && !stackBlockSuppliers.isEmpty()) {
                Block stackBlock = null;
                for (Supplier<Block> supplier : stackBlockSuppliers) {
                    Block block = supplier.get();
                    if (block instanceof PlateBlock) { stackBlock = block; break; }
                }
                if (stackBlock != null) {
                    BlockState newState = stackBlock.defaultBlockState();
                    if (newState.hasProperty(FACING)) newState = newState.setValue(FACING, state.getValue(FACING));
                    if (newState.hasProperty(FoodBlock.STACK_SIZE)) newState = newState.setValue(FoodBlock.STACK_SIZE, 1);
                    level.setBlock(pos, newState, 3);
                    if (!player.isCreative()) heldStack.shrink(1);
                    level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }

            // Try DisplayDelight compat block
            Block displayDelightPlate = FoodBlock.Registry.getDisplayDelightPlateBlock(heldStack.getItem());
            if (displayDelightPlate != null) {
                BlockState newState = displayDelightPlate.defaultBlockState();
                if (newState.hasProperty(FACING)) newState = newState.setValue(FACING, state.getValue(FACING));
                for (Property<?> property : newState.getProperties()) {
                    if (property.getName().equals("stacks") && property instanceof IntegerProperty ip) {
                        newState = newState.setValue(ip, 1);
                        break;
                    }
                }
                level.setBlock(pos, newState, 3);
                if (!player.isCreative()) heldStack.shrink(1);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }

            // Generic display plate fallback
            if (isGenericDisplayEligible(heldStack.getItem())) {
                boolean allowed = false;
                try {
                    allowed = CreateFoodConfig.SERVER.enableGenericPlates.get()
                            && !ConfigLogic.matchesFilterList(heldStack, CreateFoodConfig.SERVER.genericDisplayExclude.get());
                } catch (IllegalStateException ignored) {}
                if (allowed) {
                    Block genericPlate = ModDisplayBlocks.GENERIC_DISPLAY_PLATE_BLOCK.get();
                    BlockState newState = genericPlate.defaultBlockState().setValue(FACING, state.getValue(FACING));
                    level.setBlock(pos, newState, 3);
                    if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be) {
                        be.setDisplayedItem(heldStack.copyWithCount(1));
                    }
                    if (!player.isCreative()) heldStack.shrink(1);
                    level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
                // excluded/disabled → fall through to off-hand section
            } else {
                return InteractionResult.PASS;
            }
        }

        // Empty hand — handle off-hand item placement
        ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!offhand.isEmpty()) {
            if (FoodBlock.Registry.canPlaceOnPlate(offhand.getItem(), false)) {
                // Registered food in off-hand: PASS so FoodPlacementHandler / Block.use(OFF_HAND) handles it
                return InteractionResult.PASS;
            }
            if (isGenericDisplayEligible(offhand.getItem())) {
                // Must be handled here — Block.use(OFF_HAND) is not reliably called in 1.20.1 Forge
                // for non-registered items after MAIN_HAND returns PASS
                if (level.isClientSide) return InteractionResult.SUCCESS;
                try {
                    if (CreateFoodConfig.SERVER.enableGenericPlates.get()
                            && !ConfigLogic.matchesFilterList(offhand,
                            CreateFoodConfig.SERVER.genericDisplayExclude.get())) {
                        Block genericPlate = ModDisplayBlocks.GENERIC_DISPLAY_PLATE_BLOCK.get();
                        BlockState newState = genericPlate.defaultBlockState().setValue(FACING, state.getValue(FACING));
                        level.setBlock(pos, newState, 3);
                        if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be) {
                            be.setDisplayedItem(offhand.copyWithCount(1));
                        }
                        if (!player.isCreative()) offhand.shrink(1);
                        level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                        return InteractionResult.SUCCESS;
                    }
                } catch (IllegalStateException ignored) {}
            }
        }

        // Drop bowl and remove block — only when main hand is empty AND this is the MAIN_HAND call
        // (OFF_HAND use() can fire with empty heldStack after a MAIN_HAND PASS; must not drop bowl then)
        if (heldStack.isEmpty() && hand == InteractionHand.MAIN_HAND) {
            if (!level.isClientSide) {
                Direction direction = player.getDirection().getOpposite();
                ItemSpawn.spawnItemEntity(level, new ItemStack(Items.BOWL),
                        pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                        direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
                level.removeBlock(pos, false);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static boolean isGenericDisplayEligible(Item item) {
        return item != Items.AIR
                && !FoodBlock.Registry.isEmptyPlateItem(item)
                && !FoodBlock.Registry.isRegistered(item);
    }
}
