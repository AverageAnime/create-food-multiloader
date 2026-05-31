package net.averageanime.createfood.block.plate;

import net.averageanime.createfood.block.blockentity.GenericDisplayPlateBlockEntity;
import net.averageanime.createfood.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GenericDisplayPlateBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

    public GenericDisplayPlateBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GenericDisplayPlateBlockEntity(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level,
                                          @NotNull BlockPos pos, @NotNull Player player,
                                          @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        ItemStack heldStack = player.getItemInHand(hand);

        if (!(level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be)) {
            return InteractionResult.PASS;
        }

        if (!be.isEmpty()) {
            // Remove the displayed item
            if (level.isClientSide) return InteractionResult.SUCCESS;
            ItemStack stored = be.takeDisplayedItem();
            Direction direction = player.getDirection().getOpposite();
            double cx = pos.getX() + 0.5, cy = pos.getY() + 0.3, cz = pos.getZ() + 0.5;
            ItemEntity entity = new ItemEntity(level, cx, cy, cz, stored);
            entity.setDeltaMovement(direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
            level.addFreshEntity(entity);
            // Revert to empty plate
            BlockState emptyState = ModBlocks.EMPTY_PLATE.get().defaultBlockState()
                    .setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, emptyState, 3);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }

        if (!heldStack.isEmpty()) {
            // Place a new item on the (should-be-empty) plate
            if (level.isClientSide) return InteractionResult.SUCCESS;
            be.setDisplayedItem(heldStack.copyWithCount(1));
            if (!player.isCreative()) heldStack.shrink(1);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                         @NotNull BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && !level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be) {
                ItemStack stored = be.getDisplayedItem();
                double cx = pos.getX() + 0.5, cy = pos.getY() + 0.2, cz = pos.getZ() + 0.5;
                if (!stored.isEmpty()) {
                    ItemEntity entity = new ItemEntity(level, cx, cy, cz, stored);
                    entity.setDeltaMovement(0.0, 0.05, 0.0);
                    level.addFreshEntity(entity);
                }
                boolean revertingToEmptyPlate = newState.is(ModBlocks.EMPTY_PLATE.get());
                if (!revertingToEmptyPlate) {
                    ItemEntity bowl = new ItemEntity(level, cx, cy, cz, new ItemStack(Items.BOWL));
                    bowl.setDeltaMovement(0.0, 0.05, 0.0);
                    level.addFreshEntity(bowl);
                }
            }
        }
        super.onRemove(state, level, pos, newState, moved);
    }
}
