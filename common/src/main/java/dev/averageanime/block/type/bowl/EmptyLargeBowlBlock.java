package dev.averageanime.block.type.bowl;

import com.mojang.serialization.MapCodec;
import dev.averageanime.block.type.blockentity.LargeBowlBlockEntity;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.platform.Services;
import dev.averageanime.util.ItemSpawns;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EmptyLargeBowlBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final int MAX_STACK = 8;
    protected static final VoxelShape[] SHAPE_BY_STACK = {
            Block.box(4, 0.0, 4, 12, 2, 12),
            Block.box(4, 0.0, 4, 12, 3, 12),
            Block.box(4, 0.0, 4, 12, 4, 12),
            Block.box(4, 0.0, 4, 12, 5, 12),
            Block.box(4, 0.0, 4, 12, 6, 12),
            Block.box(4, 0.0, 4, 12, 7, 12),
            Block.box(4, 0.0, 4, 12, 8, 12),
            Block.box(4, 0.0, 4, 12, 9, 12),
    };

    private static final MapCodec<EmptyLargeBowlBlock> CODEC =
            simpleCodec(properties -> new EmptyLargeBowlBlock(properties, () -> null));

    private final Supplier<Block> nextBlock;

    public EmptyLargeBowlBlock(Properties properties, Supplier<Block> nextBlock) {
        super(properties);
        this.nextBlock = nextBlock;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FoodBlock.STACK_SIZE, 1));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return Services.PLATFORM.createLargeBowlBlockEntity(pos, state);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int stack = Math.min(state.getValue(FoodBlock.STACK_SIZE), MAX_STACK);
        return SHAPE_BY_STACK[stack - 1];
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FoodBlock.STACK_SIZE);
    }

    @Nullable
    public static LargeBowlBlockEntity getTank(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.getValue(FoodBlock.STACK_SIZE) != 1) return null;
        return level.getBlockEntity(pos) instanceof LargeBowlBlockEntity bowl ? bowl : null;
    }

    public static boolean holdsFluid(BlockGetter level, BlockPos pos, BlockState state) {
        LargeBowlBlockEntity bowl = getTank(level, pos, state);
        return bowl != null && !bowl.isEmpty();
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack heldStack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                                       Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        boolean singleBowl = state.getValue(FoodBlock.STACK_SIZE) == 1;

        LargeBowlBlockEntity bowl = getTank(level, pos, state);
        if (bowl != null && !player.isShiftKeyDown()) {
            ItemInteractionResult dipped = BowlDippingInteraction.tryInteract(player, level, hand, pos, bowl);
            if (dipped != null) return dipped;
        }

        boolean filled = bowl != null && !bowl.isEmpty();
        if (player.isShiftKeyDown() && nextBlock != null && nextBlock.get() != null && singleBowl && !filled) {
            if (level.isClientSide) return ItemInteractionResult.SUCCESS;
            BlockState next = nextBlock.get().defaultBlockState();
            if (next.hasProperty(FACING)) next = next.setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, next, 3);
            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hit) {
        if (holdsFluid(level, pos, state)) return InteractionResult.PASS;

        if (!level.isClientSide) {
            int current = state.getValue(FoodBlock.STACK_SIZE);
            int toRemove = player.isShiftKeyDown() ? current : 1;
            Direction direction = player.getDirection().getOpposite();
            ItemSpawns.spawnItemEntity(level, new ItemStack(Items.BOWL, toRemove),
                    pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                    direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
            if (toRemove >= current) {
                level.removeBlock(pos, false);
            } else {
                level.setBlock(pos, state.setValue(FoodBlock.STACK_SIZE, current - toRemove), 3);
            }
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                            @NotNull BlockState newState, boolean isMoving) {
        Fluid spill = Fluids.EMPTY;
        if (!isMoving && !state.is(newState.getBlock()) && !level.isClientSide()) {
            LargeBowlBlockEntity bowl = getTank(level, pos, state);
            if (bowl != null && bowl.getAmount() == Services.PLATFORM.getLargeBowlCapacityMb()) {
                spill = bowl.getFluid();
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);

        if (spill != Fluids.EMPTY && level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, spill.defaultFluidState().createLegacyBlock(), Block.UPDATE_ALL);
        }
    }
}
