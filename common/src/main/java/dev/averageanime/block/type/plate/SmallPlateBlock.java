package dev.averageanime.block.type.plate;

import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.display.SmallPlateFoodBlock;
import dev.averageanime.util.ItemSpawn;
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

public class SmallPlateBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 1, 12.0);

    private final Supplier<Block> normalPlateBlock;

    public SmallPlateBlock(Properties properties, Supplier<Block> normalPlateBlock) {
        super(properties);
        this.normalPlateBlock = normalPlateBlock;
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
        if (player.isShiftKeyDown()) {
            if (level.isClientSide) return ItemInteractionResult.SUCCESS;
            BlockState normalPlate = normalPlateBlock.get().defaultBlockState();
            if (normalPlate.hasProperty(FACING)) normalPlate = normalPlate.setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, normalPlate, 3);
            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 0.8F);
            return ItemInteractionResult.SUCCESS;
        }

        if (level.isClientSide) {
            if (FoodBlock.Registry.canPlaceOnPlate(heldStack.getItem(), true)) {
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        List<Supplier<Block>> stackBlockSuppliers = FoodBlock.Registry.getAllBlocks(heldStack.getItem());
        if (stackBlockSuppliers != null && !stackBlockSuppliers.isEmpty()) {
            Block stackBlock = null;
            for (Supplier<Block> supplier : stackBlockSuppliers) {
                Block block = supplier.get();
                if (block instanceof SmallPlateFoodBlock) { stackBlock = block; break; }
            }
            if (stackBlock != null) {
                BlockState newState = stackBlock.defaultBlockState();
                if (newState.hasProperty(FACING)) newState = newState.setValue(FACING, state.getValue(FACING));
                if (newState.hasProperty(FoodBlock.STACK_SIZE)) newState = newState.setValue(FoodBlock.STACK_SIZE, 1);
                level.setBlock(pos, newState, 3);
                if (!player.isCreative()) heldStack.shrink(1);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            }
        }

        Block displayDelightSmallPlate = FoodBlock.Registry.getDisplayDelightSmallPlateBlock(heldStack.getItem());
        if (displayDelightSmallPlate != null) {
            BlockState newState = displayDelightSmallPlate.defaultBlockState();
            if (newState.hasProperty(FACING)) newState = newState.setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, newState, 3);
            if (!player.isCreative()) heldStack.shrink(1);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
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
}
