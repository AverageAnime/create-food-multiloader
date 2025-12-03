package net.averageanime.createfood.block;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

public class ModPieBlock extends Block {
    public static final int MAX_PIE_BITES = 4;
    // Create our own BITES property with the correct range (0-3 instead of 0-6)
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, MAX_PIE_BITES - 1);
    public static final int FULL_PIE_SIGNAL;
    protected static final float AABB_OFFSET = 1.0F;
    protected static final float AABB_SIZE_PER_BITE = 2.0F;
    protected static VoxelShape[] SHAPE_BY_BITE;
    public static final DirectionProperty FACING;

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(BITES, 0);
    }

    public ModPieBlock(Properties pProperties) {
        super(pProperties);
        // Set the default state with 0 bites
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int bites = pState.getValue(BITES);

        // Defensive check: Clamp bites to valid range
        bites = Math.max(0, Math.min(bites, SHAPE_BY_BITE.length - 1));

        VoxelShape baseShape = SHAPE_BY_BITE[bites];
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
        if (bites < MAX_PIE_BITES - 1) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        Direction direction = player.getDirection().getOpposite();
        ItemUtils.spawnItemEntity(level, this.getPieSliceItem(), (double) pos.getX() + 0.5, (double) pos.getY() + 0.3, (double) pos.getZ() + 0.5, (double) direction.getStepX() * 0.15, 0.05, (double) direction.getStepZ() * 0.15);
        level.playSound((Player) null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.APPLE_CHEESECAKE_SLICE.get());
    }

    protected InteractionResult consumeBite(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            pPlayer.addEffect(new MobEffectInstance(ModEffects.COMFORT.get(), 1200, 0));
            pPlayer.getFoodData().eat(3, 0.1F);
            int currentBites = pState.getValue(BITES);
            pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
            if (currentBites < MAX_PIE_BITES - 1) {
                pLevel.setBlock(pPos, pState.setValue(BITES, currentBites + 1), 3);
            } else {
                pLevel.removeBlock(pPos, false);
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : pState;
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isSolid();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BITES);
    }

    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return getOutputSignal(pBlockState.getValue(BITES));
    }

    public static int getOutputSignal(int pEaten) {
        return (MAX_PIE_BITES - pEaten) * 2;
    }

    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        FULL_PIE_SIGNAL = getOutputSignal(0);
        SHAPE_BY_BITE = new VoxelShape[]{
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),  // 0 bites (full pie)
                Shapes.or(  // 1 bite
                        Block.box(2.0D, 0.0D, 8.0D, 8.0D, 4.0D, 14.0D),
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 8.0D)
                ),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 8.0D),    // 2 bites
                Block.box(8.0D, 0.0D, 2.0D, 14.0D, 4.0D, 8.0D)     // 3 bites
        };
    }
}