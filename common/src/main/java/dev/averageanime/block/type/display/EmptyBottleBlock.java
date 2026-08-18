package dev.averageanime.block.type.display;

import dev.averageanime.util.ItemSpawns;
import dev.averageanime.util.PlayerItems;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/**
 * The empty glass bottle left standing when a placed drink is taken, drunk, or decanted.
 *
 * <p>Unlike the other {@code Empty*} display blocks this has no {@code STACK_SIZE} - bottles are
 * single-serving, so empty bottles do not stack - and it stays out of the plate/bowl shift-cycle.
 */
public class EmptyBottleBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /** Matches {@link BottleFoodBlock}'s footprint, at the height of the empty bottle model. */
    private static final VoxelShape SHAPE = Block.box(5.5, 0.0, 5.5, 10.5, 9.0, 10.5);

    public EmptyBottleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack heldStack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                                       Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        // Sneaking is the existing placement path's gesture; leave it alone.
        if (player.isShiftKeyDown()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        Block bottleBlock = findBottleBlock(heldStack);
        if (bottleBlock == null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;

        BlockState newState = bottleBlock.defaultBlockState();
        if (newState.hasProperty(FoodBlock.FACING)) {
            newState = newState.setValue(FoodBlock.FACING, state.getValue(FACING));
        }
        if (newState.hasProperty(FoodBlock.STACK_SIZE)) {
            newState = newState.setValue(FoodBlock.STACK_SIZE, 1);
        }
        level.setBlock(pos, newState, 3);
        if (!player.isCreative()) {
            heldStack.shrink(1);
            // The held drink carries its own bottle, and a BottleFoodBlock vanishes when retrieved rather
            // than reverting here - so this bottle would simply be destroyed. Hand it back.
            PlayerItems.give(player, new ItemStack(Items.GLASS_BOTTLE));
        }
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    private static Block findBottleBlock(ItemStack heldStack) {
        List<Supplier<Block>> suppliers = FoodBlock.Registry.getAllBlocks(heldStack.getItem());
        if (suppliers == null) return null;
        for (Supplier<Block> supplier : suppliers) {
            Block block = supplier.get();
            if (block instanceof BottleFoodBlock) return block;
        }
        return null;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hit) {
        if (!level.isClientSide) {
            Direction direction = player.getDirection().getOpposite();
            ItemSpawns.spawnItemEntity(level, new ItemStack(Items.GLASS_BOTTLE),
                    pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                    direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
            level.removeBlock(pos, false);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 0.8F);
        }
        return InteractionResult.SUCCESS;
    }
}
