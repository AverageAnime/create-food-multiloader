package net.averageanime.createfood.block.plate;

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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import net.averageanime.createfood.block.ModBlocks;
import net.averageanime.createfood.block.blockentity.GenericDisplayPlateBlockEntity;

public class EmptyPlateBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

    public EmptyPlateBlock(BlockBehaviour.Properties properties) {
        super(properties);
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
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        ItemStack heldStack = player.getItemInHand(hand);

        if (heldStack.isEmpty()) {
            // Pick up the empty plate (returns a bowl)
            if (!level.isClientSide) {
                Direction direction = player.getDirection().getOpposite();
                double cx = pos.getX() + 0.5, cy = pos.getY() + 0.3, cz = pos.getZ() + 0.5;
                ItemEntity entity = new ItemEntity(level, cx, cy, cz, new ItemStack(Items.BOWL));
                entity.setDeltaMovement(direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
                level.addFreshEntity(entity);
                level.removeBlock(pos, false);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // Place any item onto the plate
        Block genericPlate = ModBlocks.GENERIC_DISPLAY_PLATE.get();
        BlockState newState = genericPlate.defaultBlockState().setValue(FACING, state.getValue(FACING));
        if (!level.isClientSide) {
            level.setBlock(pos, newState, 3);
            if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be) {
                be.setDisplayedItem(heldStack.copyWithCount(1));
            }
            if (!player.isCreative()) heldStack.shrink(1);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
