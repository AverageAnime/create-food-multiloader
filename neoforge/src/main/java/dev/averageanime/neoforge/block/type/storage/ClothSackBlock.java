package dev.averageanime.neoforge.block.type.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.averageanime.neoforge.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.neoforge.config.ModConfig;
import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClothSackBlock extends BaseEntityBlock {

    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(3, 0, 3, 13, 10, 13),
        Block.box(5, 10, 5, 11, 12, 11),
        Block.box(4, 12, 4, 12, 13, 12)
    );

    public ClothSackBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(OPEN, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN, FACING);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ClothSackBlock::new);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ClothSackBlockEntity(pos, state);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state,
            @NotNull Level level, @NotNull BlockPos pos,
            @NotNull Player player, @NotNull BlockHitResult hit) {
        if (!level.isClientSide && ModConfig.isClothSackInventoryEnabled()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ClothSackBlockEntity sack) {
                if (!state.getValue(OPEN)) {
                    level.setBlock(pos, state.setValue(OPEN, true), Block.UPDATE_ALL);
                    level.playSound(null, pos, SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
                player.openMenu(sack, buf -> buf.writeBlockPos(pos));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level,
            @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && !level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ClothSackBlockEntity) {
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
