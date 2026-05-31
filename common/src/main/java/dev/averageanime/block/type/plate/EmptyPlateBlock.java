package dev.averageanime.block.type.plate;

import dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.platform.Services;
import dev.averageanime.util.ItemSpawn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class EmptyPlateBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

    private final Supplier<Block> smallPlateBlock;

    public EmptyPlateBlock(Properties properties, Supplier<Block> smallPlateBlock) {
        super(properties);
        this.smallPlateBlock = smallPlateBlock;
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
        if (player.isShiftKeyDown() && smallPlateBlock != null) {
            if (level.isClientSide) return ItemInteractionResult.SUCCESS;
            BlockState smallPlate = smallPlateBlock.get().defaultBlockState();
            if (smallPlate.hasProperty(FACING)) smallPlate = smallPlate.setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, smallPlate, 3);
            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 1.2F);
            return ItemInteractionResult.SUCCESS;
        }

        if (level.isClientSide) {
            if (FoodBlock.Registry.canPlaceOnPlate(heldStack.getItem(), false)
                    || (isGenericDisplayEligible(heldStack.getItem())
                        && Services.PLATFORM.isGenericPlatesEnabled()
                        && Services.PLATFORM.isGenericDisplayAllowed(heldStack))) {
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        List<Supplier<Block>> stackBlockSuppliers = FoodBlock.Registry.getAllBlocks(heldStack.getItem());
        if (stackBlockSuppliers != null && !stackBlockSuppliers.isEmpty()) {
            Block stackBlock = null;
            for (Supplier<Block> supplier : stackBlockSuppliers) {
                Block block = supplier.get();
                if (block instanceof PlateBlock) { stackBlock = block; break; }
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

        Block displayDelightPlate = FoodBlock.Registry.getDisplayDelightPlateBlock(heldStack.getItem());
        if (displayDelightPlate != null) {
            BlockState newState = displayDelightPlate.defaultBlockState();
            if (newState.hasProperty(FACING)) newState = newState.setValue(FACING, state.getValue(FACING));
            for (Property<?> property : newState.getProperties()) {
                if (property.getName().equals("stacks") && property instanceof IntegerProperty ip) {
                    newState = newState.setValue(ip, 1);
                    break;
                }
            }
            level.setBlock(pos, newState, 3);
            if (!player.isCreative()) heldStack.shrink(1);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        if (isGenericDisplayEligible(heldStack.getItem())) {
            if (!Services.PLATFORM.isGenericPlatesEnabled()
                    || !Services.PLATFORM.isGenericDisplayAllowed(heldStack)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            Block genericPlate = Services.PLATFORM.getGenericDisplayPlateBlock();
            BlockState newState = genericPlate.defaultBlockState()
                    .setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, newState, 3);
            if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be) {
                be.setDisplayedItem(heldStack.copyWithCount(1));
            }
            if (!player.isCreative()) heldStack.shrink(1);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public static boolean isGenericDisplayEligible(Item item) {
        return item != Items.AIR
                && !FoodBlock.Registry.isEmptyPlateItem(item)
                && !FoodBlock.Registry.isRegistered(item);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hit) {
        if (player.isShiftKeyDown()) return InteractionResult.PASS;
        ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!offhand.isEmpty()
                && (FoodBlock.Registry.canPlaceOnPlate(offhand.getItem(), false)
                    || isGenericDisplayEligible(offhand.getItem()))) {
            return InteractionResult.PASS;
        }
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
