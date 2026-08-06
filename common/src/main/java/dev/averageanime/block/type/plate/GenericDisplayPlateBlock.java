package dev.averageanime.block.type.plate;

import com.mojang.serialization.MapCodec;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.platform.Services;
import dev.averageanime.util.ItemSpawns;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GenericDisplayPlateBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

    private static final MapCodec<GenericDisplayPlateBlock> CODEC =
            simpleCodec(props -> new GenericDisplayPlateBlock(props, () -> null));

    private final Supplier<Block> emptyPlateBlock;

    public GenericDisplayPlateBlock(Properties properties, Supplier<Block> emptyPlateBlock) {
        super(properties);
        this.emptyPlateBlock = emptyPlateBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
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
        return Services.PLATFORM.createGenericDisplayPlateBlockEntity(pos, state);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack heldStack,
                                                       @NotNull BlockState state,
                                                       @NotNull Level level,
                                                       @NotNull BlockPos pos,
                                                       @NotNull Player player,
                                                       @NotNull InteractionHand hand,
                                                       @NotNull BlockHitResult hit) {
        if (heldStack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (!Services.PLATFORM.isGenericDisplayEnabled()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (!Services.PLATFORM.isGenericDisplayAllowed(heldStack)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof GenericDisplayBlockEntity be && !be.isEmpty()
                    && PlateSliceInteraction.couldSlice(player, level, InteractionHand.OFF_HAND, pos, state)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            return ItemInteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof GenericDisplayBlockEntity be) {
            if (be.isEmpty()) {
                be.setDisplayedItem(heldStack.copyWithCount(1));
                if (!player.isCreative()) heldStack.shrink(1);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            } else {
                if (PlateSliceInteraction.couldSlice(player, level, InteractionHand.OFF_HAND, pos, state)) {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
                ItemStack stored = be.takeDisplayedItem();
                Direction direction = player.getDirection().getOpposite();
                ItemSpawns.spawnItemEntity(level, stored,
                        pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                        direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
                Block target = (emptyPlateBlock != null && emptyPlateBlock.get() != null)
                        ? emptyPlateBlock.get() : Services.PLATFORM.getPlateBlock();
                BlockState emptyState = target.defaultBlockState();
                if (emptyState.hasProperty(FACING)) {
                    emptyState = emptyState.setValue(FACING, state.getValue(FACING));
                }
                level.setBlock(pos, emptyState, 3);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
                return ItemInteractionResult.SUCCESS;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level,
                                                        @NotNull BlockPos pos, @NotNull Player player,
                                                        @NotNull BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof GenericDisplayBlockEntity be && !be.isEmpty()) {
            if (PlateSliceInteraction.couldSlice(player, level, InteractionHand.OFF_HAND, pos, state)) {
                return InteractionResult.PASS;
            }
            if (level.isClientSide) return InteractionResult.SUCCESS;
            ItemStack stored = be.takeDisplayedItem();
            Direction direction = player.getDirection().getOpposite();
            ItemSpawns.spawnItemEntity(level, stored,
                    pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                    direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);

            Block target = (emptyPlateBlock != null && emptyPlateBlock.get() != null)
                    ? emptyPlateBlock.get()
                    : Services.PLATFORM.getPlateBlock();
            BlockState emptyState = target.defaultBlockState();
            if (emptyState.hasProperty(FACING)) {
                emptyState = emptyState.setValue(FACING, state.getValue(FACING));
            }
            level.setBlock(pos, emptyState, 3);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public boolean tryEat(Player player, Level level, BlockPos pos) {
        if (!player.isShiftKeyDown()) return false;
        if (!(level.getBlockEntity(pos) instanceof GenericDisplayBlockEntity be)) return false;
        if (be.isEmpty()) return false;
        ItemStack displayed = be.getDisplayedItem();
        FoodProperties food = displayed.get(DataComponents.FOOD);
        if (food == null) return false;
        if (!player.canEat(food.canAlwaysEat()) && !player.getAbilities().instabuild) return false;
        if (!level.isClientSide) {
            ItemStack copy = displayed.copyWithCount(1);
            ItemStack container = copy.finishUsingItem(level, player);
            be.takeDisplayedItem();
            if (!container.isEmpty() && !ItemStack.isSameItem(container, copy)) {
                if (!player.getInventory().add(container)) {
                    player.drop(container, false);
                }
            }
            Block target = (emptyPlateBlock != null && emptyPlateBlock.get() != null)
                    ? emptyPlateBlock.get() : Services.PLATFORM.getPlateBlock();
            BlockState emptyState = target.defaultBlockState();
            BlockState currentState = level.getBlockState(pos);
            if (emptyState.hasProperty(FACING) && currentState.hasProperty(FACING)) {
                emptyState = emptyState.setValue(FACING, currentState.getValue(FACING));
            }
            level.setBlock(pos, emptyState, 3);
            level.playSound(null, pos, SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5f, 1.0f);
        }
        return true;
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                         @NotNull BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && !level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof GenericDisplayBlockEntity be) {
                ItemStack stored = be.getDisplayedItem();
                double cx = pos.getX() + 0.5, cy = pos.getY() + 0.2, cz = pos.getZ() + 0.5;
                if (!stored.isEmpty()) {
                    ItemSpawns.spawnItemEntity(level, stored, cx, cy, cz, 0.0, 0.05, 0.0);
                }
                boolean revertingToEmptyPlate = emptyPlateBlock != null
                        && emptyPlateBlock.get() != null
                        && newState.is(emptyPlateBlock.get());
                if (!revertingToEmptyPlate) {
                    ItemSpawns.spawnItemEntity(level, new ItemStack(Items.BOWL), cx, cy, cz, 0.0, 0.05, 0.0);
                }
            }
        }
        super.onRemove(state, level, pos, newState, moved);
    }
}