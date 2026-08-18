package dev.averageanime.block.type.bowl;

import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.block.type.display.ContainerFoodBlock;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.platform.Services;
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

import java.util.List;
import java.util.function.Supplier;

public class EmptyBowlBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final int MAX_STACK = 4;
    protected static final VoxelShape[] SHAPE_BY_STACK = {
            Block.box(5, 0.0, 5, 11, 4, 11),
            Block.box(5, 0.0, 5, 11, 7, 11),
            Block.box(5, 0.0, 5, 11, 10, 11),
            Block.box(5, 0.0, 5, 11, 13, 11),
    };

    private final Supplier<Block> nextBlock;

    public EmptyBowlBlock(Properties properties, Supplier<Block> nextBlock) {
        super(properties);
        this.nextBlock = nextBlock;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FoodBlock.STACK_SIZE, 1));
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

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack heldStack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                                       Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        boolean singleBowl = state.getValue(FoodBlock.STACK_SIZE) == 1;

        if (player.isShiftKeyDown() && nextBlock != null && singleBowl) {
            if (level.isClientSide) return ItemInteractionResult.SUCCESS;
            BlockState plate = nextBlock.get().defaultBlockState();
            if (plate.hasProperty(FACING)) plate = plate.setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, plate, 3);
            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        if (!singleBowl) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        Block bowlBlock = findBowlBlock(heldStack);
        boolean genericEligible = bowlBlock == null
                && EmptyPlateBlock.isGenericDisplayEligible(heldStack.getItem())
                && Services.PLATFORM.isGenericDisplayEnabled()
                && Services.PLATFORM.isGenericDisplayAllowed(heldStack);

        if (level.isClientSide) {
            return (bowlBlock != null || genericEligible)
                    ? ItemInteractionResult.SUCCESS
                    : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (bowlBlock != null) {
            BlockState newState = bowlBlock.defaultBlockState();
            if (newState.hasProperty(FACING)) newState = newState.setValue(FACING, state.getValue(FACING));
            if (newState.hasProperty(FoodBlock.STACK_SIZE)) newState = newState.setValue(FoodBlock.STACK_SIZE, 1);
            level.setBlock(pos, newState, 3);
            if (!player.isCreative()) {
                heldStack.shrink(1);
                // A ContainerFoodBlock reverts to this empty bowl when emptied, so the bowl is retained.
                // Anything else vanishes instead, which would destroy it - hand it back.
                if (!(bowlBlock instanceof ContainerFoodBlock)) {
                    PlayerItems.give(player, new ItemStack(Items.BOWL));
                }
            }
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        if (genericEligible) {
            Block genericBowl = Services.PLATFORM.getGenericDisplayBowlBlock();
            BlockState newState = genericBowl.defaultBlockState().setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, newState, 3);
            if (level.getBlockEntity(pos) instanceof GenericDisplayBlockEntity be) {
                be.setDisplayedItem(heldStack.copyWithCount(1));
            }
            if (!player.isCreative()) heldStack.shrink(1);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static Block findBowlBlock(ItemStack heldStack) {
        List<Supplier<Block>> suppliers = FoodBlock.Registry.getAllBlocks(heldStack.getItem());
        if (suppliers == null) return null;
        // BowlBlock first: an item registered as both stacks servings, which is the richer behaviour.
        for (Supplier<Block> supplier : suppliers) {
            Block block = supplier.get();
            if (block instanceof BowlBlock) return block;
        }
        // Soups, stews and ice creams are BowlFoodBlock and belong on an empty bowl just the same.
        for (Supplier<Block> supplier : suppliers) {
            Block block = supplier.get();
            if (block instanceof BowlFoodBlock) return block;
        }
        return null;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hit) {
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
}
