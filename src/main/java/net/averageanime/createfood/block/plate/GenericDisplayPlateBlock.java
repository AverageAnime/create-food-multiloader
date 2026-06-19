package net.averageanime.createfood.block.plate;

import net.averageanime.createfood.block.blockentity.GenericDisplayPlateBlockEntity;
import net.averageanime.createfood.block.display.FoodBlock;
import net.averageanime.createfood.block.handler.PlateSliceHandler;
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

    private final Supplier<Block> emptyPlateBlock;

    public GenericDisplayPlateBlock(Properties properties, Supplier<Block> emptyPlateBlock) {
        super(properties);
        this.emptyPlateBlock = emptyPlateBlock;
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
        if (!(level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be)) {
            return InteractionResult.PASS;
        }

        ItemStack heldStack = player.getItemInHand(hand);

        if (!heldStack.isEmpty()) {
            // Item held: if plate is empty, place item; if plate has item, remove it
            if (be.isEmpty()) {
                try {
                    if (!CreateFoodConfig.SERVER.enableGenericPlates.get()) return InteractionResult.PASS;
                    if (ConfigLogic.matchesFilterList(heldStack, CreateFoodConfig.SERVER.genericDisplayExclude.get()))
                        return InteractionResult.PASS;
                } catch (IllegalStateException ignored) {
                    // Config not yet loaded (e.g., dedicated server client); allow placement
                }
                if (FoodBlock.Registry.isRegistered(heldStack.getItem())) return InteractionResult.PASS;
                if (PlateSliceHandler.couldSlice(player, level, hand, pos, state)) return InteractionResult.PASS;
                if (level.isClientSide) return InteractionResult.SUCCESS;
                be.setDisplayedItem(heldStack.copyWithCount(1));
                if (!player.isCreative()) heldStack.shrink(1);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else {
                // Remove displayed item — only on MAIN_HAND to prevent the late OFF_HAND packet
                // (fired after MAIN_HAND placed food via fallthrough) from immediately un-placing it
                if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
                if (level.isClientSide) return InteractionResult.SUCCESS;
                ItemStack stored = be.takeDisplayedItem();
                Direction direction = player.getDirection().getOpposite();
                ItemSpawn.spawnItemEntity(level, stored,
                        pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                        direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
                revertToEmptyPlate(state, level, pos);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
                return InteractionResult.SUCCESS;
            }
        } else {
            // Empty hand: remove item from plate
            if (!be.isEmpty()) {
                if (level.isClientSide) return InteractionResult.SUCCESS;
                ItemStack stored = be.takeDisplayedItem();
                Direction direction = player.getDirection().getOpposite();
                ItemSpawn.spawnItemEntity(level, stored,
                        pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                        direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
                revertToEmptyPlate(state, level, pos);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private void revertToEmptyPlate(BlockState state, Level level, BlockPos pos) {
        Block target = (emptyPlateBlock != null && emptyPlateBlock.get() != null)
                ? emptyPlateBlock.get() : this; // fallback: stay as generic plate
        BlockState emptyState = target.defaultBlockState();
        if (emptyState.hasProperty(FACING) && state.hasProperty(FACING)) {
            emptyState = emptyState.setValue(FACING, state.getValue(FACING));
        }
        level.setBlock(pos, emptyState, 3);
    }

    /**
     * Shift+LMB: instantly eat the displayed food item.
     * Called by FoodPlacementHandler from the left-click event.
     * Returns true if the eat was handled.
     */
    public boolean tryEat(Player player, Level level, BlockPos pos) {
        if (!player.isShiftKeyDown()) return false;
        if (!(level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be)) return false;
        if (be.isEmpty()) return false;
        ItemStack displayed = be.getDisplayedItem();
        FoodProperties food = displayed.getItem().getFoodProperties();
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
            BlockState currentState = level.getBlockState(pos);
            revertToEmptyPlate(currentState, level, pos);
            level.playSound(null, pos, SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5f, 1.0f);
        }
        return true;
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                         @NotNull BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && !level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be) {
                ItemStack stored = be.getDisplayedItem();
                double cx = pos.getX() + 0.5, cy = pos.getY() + 0.2, cz = pos.getZ() + 0.5;
                if (!stored.isEmpty()) {
                    ItemSpawn.spawnItemEntity(level, stored, cx, cy, cz, 0.0, 0.05, 0.0);
                }
                boolean revertingToEmptyPlate = emptyPlateBlock != null
                        && emptyPlateBlock.get() != null
                        && newState.is(emptyPlateBlock.get());
                if (!revertingToEmptyPlate) {
                    ItemSpawn.spawnItemEntity(level, new ItemStack(Items.BOWL), cx, cy, cz, 0.0, 0.05, 0.0);
                }
            }
        }
        super.onRemove(state, level, pos, newState, moved);
    }
}
