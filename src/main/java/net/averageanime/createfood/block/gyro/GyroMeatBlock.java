package net.averageanime.createfood.block.gyro;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

public class GyroMeatBlock extends CakeBlock {

    public static final int MAX_BITES = 7;
    public static final IntegerProperty BITES = BlockStateProperties.BITES;
    public static final int FULL_CAKE_SIGNAL = getOutputSignal(0);
    protected static final float AABB_OFFSET = 1.0F;
    protected static final float AABB_SIZE_PER_BITE = 2.0F;
    protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 15.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 13.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 11.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 9.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 7.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 5.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 3.0)
    };
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public GyroMeatBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape baseShape = SHAPE_BY_BITE[pState.getValue(BITES)];
        Direction facing = pState.getValue(FACING);

        if (facing == Direction.EAST) {
            return rotateShape(baseShape, Direction.EAST);
        } else if (facing == Direction.SOUTH) {
            return rotateShape(baseShape, Direction.SOUTH);
        } else if (facing == Direction.WEST) {
            return rotateShape(baseShape, Direction.WEST);
        } else {
            return baseShape;
        }
    }

    private VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (direction.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (level.isClientSide) {
            if (heldStack.is(ModTags.KNIVES)) {
                return this.cutSlice(level, pos, state, player);
            }
            if (this.consumeBite(level, pos, state, player) == InteractionResult.SUCCESS) {
                return InteractionResult.SUCCESS;
            }
            if (heldStack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }
        return heldStack.is(ModTags.KNIVES) ? this.cutSlice(level, pos, state, player) : consumeBite(level, pos, state, player);
    }

    protected InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
        int bites = state.getValue(BITES);
        if (bites < MAX_BITES - 1) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }
        Direction direction = player.getDirection().getOpposite();
        ItemUtils.spawnItemEntity(level, this.getPieSliceItem(), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
        level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.GYRO_MEAT_SLICE.get());
    }

    protected static InteractionResult consumeBite(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            pPlayer.awardStat(Stats.EAT_CAKE_SLICE);
            pPlayer.getFoodData().eat(4, 1.1F);
            int bites = pState.getValue(BITES);
            pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
            if (bites < 6) {
                pLevel.setBlock(pPos, pState.setValue(BITES, bites + 1), 3);
            } else {
                pLevel.removeBlock(pPos, false);
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
            }
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, BITES);
    }

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return getOutputSignal(pBlockState.getValue(BITES));
    }

    public static int getOutputSignal(int pEaten) {
        return (7 - pEaten) * 2;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}