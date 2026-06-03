package net.averageanime.createfood.block.plate;

import net.averageanime.createfood.block.ModDisplayBlocks;
import net.averageanime.createfood.block.blockentity.GenericDisplayPlateBlockEntity;
import net.averageanime.createfood.block.display.FoodBlock;
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

        if (!heldStack.isEmpty()) {
            if (player.isShiftKeyDown()) return InteractionResult.PASS;

            if (level.isClientSide) {
                if (FoodBlock.Registry.canPlaceOnPlate(heldStack.getItem(), false)
                        || isGenericDisplayEligible(heldStack.getItem())) {
                    return InteractionResult.SUCCESS;
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
                Block genericPlate = ModDisplayBlocks.GENERIC_DISPLAY_PLATE_BLOCK.get();
                BlockState newState = genericPlate.defaultBlockState()
                        .setValue(FACING, state.getValue(FACING));
                level.setBlock(pos, newState, 3);
                if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be) {
                    be.setDisplayedItem(heldStack.copyWithCount(1));
                }
                if (!player.isCreative()) heldStack.shrink(1);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        }

        // Empty hand + shift → shrink to small plate
        if (player.isShiftKeyDown() && smallPlateBlock != null) {
            if (level.isClientSide) return InteractionResult.SUCCESS;
            BlockState smallPlate = smallPlateBlock.get().defaultBlockState();
            if (smallPlate.hasProperty(FACING)) smallPlate = smallPlate.setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, smallPlate, 3);
            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 1.2F);
            return InteractionResult.SUCCESS;
        }

        // Empty hand — check if offhand has a food/plate item before picking up
        ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!offhand.isEmpty()
                && (FoodBlock.Registry.canPlaceOnPlate(offhand.getItem(), false)
                    || isGenericDisplayEligible(offhand.getItem()))) {
            return InteractionResult.PASS;
        }

        // Drop bowl and remove block
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

    public static boolean isGenericDisplayEligible(Item item) {
        return item != Items.AIR
                && !FoodBlock.Registry.isEmptyPlateItem(item)
                && !FoodBlock.Registry.isRegistered(item);
    }
}
