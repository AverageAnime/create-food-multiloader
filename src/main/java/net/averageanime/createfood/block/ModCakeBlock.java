package net.averageanime.createfood.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

public class ModCakeBlock extends CakeBlock {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public final Item cakeSlice;
    protected static final VoxelShape[] BITES_TO_SHAPE;

    public ModCakeBlock(Item pieSlice) {
        super(FabricBlockSettings.copyOf(Blocks.CAKE));
        this.cakeSlice = pieSlice;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(BITES, 0));
    }

    public ItemStack getCakeSliceItem() {
        return new ItemStack(this.cakeSlice);
    }

    public int getMaxBites() {
        return 7;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getHorizontalPlayerFacing());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        VoxelShape originalShape = BITES_TO_SHAPE[state.get(BITES)];
        if (facing == Direction.NORTH) {
            return originalShape;
        } else {
            return rotateShape(Direction.NORTH, facing, originalShape);
        }
    }

    private static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};

        int times = (to.getHorizontal() - from.getHorizontal() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(3, 0.3F);
            int i = (Integer)state.get(BITES);
            world.emitGameEvent(player, GameEvent.EAT, pos);
            if (i < 6) {
                world.setBlockState(pos, (BlockState)state.with(BITES, i + 1), 3);
            } else {
                world.removeBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return ActionResult.SUCCESS;
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getStackInHand(hand);
        if (level.isClient) {
            if (heldStack.isIn(ModTags.KNIVES)) {
                return cutSlice(level, pos, state, player);
            }

            if (this.tryEat(level, pos, state, player) == ActionResult.SUCCESS) {
                return ActionResult.SUCCESS;
            }

            if (heldStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }

        if (heldStack.isIn(ModTags.KNIVES)) {
            return cutSlice(level, pos, state, player);
        }

        return this.tryEat(level, pos, state, player);
    }

    protected ActionResult cutSlice(World level, BlockPos pos, BlockState state, PlayerEntity player) {
        int bites = state.get(BITES);
        if (bites < getMaxBites() - 1) {
            level.setBlockState(pos, state.with(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        Direction direction = player.getHorizontalFacing().getOpposite();
        ItemUtils.spawnItemEntity(level, this.getCakeSliceItem(), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                direction.getOffsetX() * 0.15, 0.05, direction.getOffsetZ() * 0.15);
        level.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    static {
        BITES_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(1, 0, 1, 15, 8, 15),
                Block.createCuboidShape(3, 0, 1, 15, 8, 15),
                Block.createCuboidShape(5, 0, 1, 15, 8, 15),
                Block.createCuboidShape(7, 0, 1, 15, 8, 15),
                Block.createCuboidShape(9, 0, 1, 15, 8, 15),
                Block.createCuboidShape(11, 0, 1, 15, 8, 15),
                Block.createCuboidShape(13, 0, 1, 15, 8, 15)
        };
    }
}