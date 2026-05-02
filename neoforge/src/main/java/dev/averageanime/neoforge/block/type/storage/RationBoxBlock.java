package dev.averageanime.neoforge.block.type.storage;

import dev.averageanime.neoforge.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.neoforge.config.ModConfig;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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

public class RationBoxBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_NS = Block.box(3, 0, 4, 13, 4, 12); // NORTH / SOUTH
    private static final VoxelShape SHAPE_EW = Block.box(4, 0, 3, 12, 4, 13); // EAST / WEST

    public RationBoxBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(RationBoxBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
            @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return (facing == Direction.EAST || facing == Direction.WEST) ? SHAPE_EW : SHAPE_NS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new RationBoxBlockEntity(pos, state);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state,
            @NotNull Level level, @NotNull BlockPos pos,
            @NotNull Player player, @NotNull BlockHitResult hit) {
        if (!level.isClientSide && ModConfig.isRationBoxInventoryEnabled()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof RationBoxBlockEntity rationBox) {
                level.playSound(null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                player.openMenu(rationBox, buf -> buf.writeBlockPos(pos));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level,
            @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && !level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof RationBoxBlockEntity) {
                ItemStack stack = new ItemStack(this);
                be.saveToItem(stack, level.registryAccess());
                popResource(level, pos, stack);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level,
            @NotNull BlockPos pos, @NotNull BlockState state) {
        ItemStack stack = super.getCloneItemStack(level, pos, state);
        if (level instanceof Level worldLevel) {
            BlockEntity be = worldLevel.getBlockEntity(pos);
            if (be != null) {
                be.saveToItem(stack, worldLevel.registryAccess());
            }
        }
        return stack;
    }
}
