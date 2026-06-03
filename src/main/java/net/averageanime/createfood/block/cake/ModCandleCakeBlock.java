package net.averageanime.createfood.block.cake;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModCandleCakeBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final VoxelShape CAKE_SHAPE   = Block.box(1, 0, 1, 15, 8, 15);
    private static final VoxelShape CANDLE_SHAPE = Block.box(7, 8, 7,  9, 14,  9);
    private static final VoxelShape COMBINED_SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);

    private final Supplier<Block> parentCake;
    private final Block candleBlock;
    private final String parentCakeName;
    private final String candleSuffix;

    public ModCandleCakeBlock(Properties properties, Supplier<Block> parentCake, Block candleBlock,
                               String parentCakeName, String candleSuffix) {
        super(properties);
        this.parentCake = parentCake;
        this.candleBlock = candleBlock;
        this.parentCakeName = parentCakeName;
        this.candleSuffix = candleSuffix;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false));
    }

    public String getParentCakeName() { return parentCakeName; }
    public String getCandleSuffix()   { return candleSuffix; }

    @Override
    public @NotNull MutableComponent getName() {
        return parentCake.get().getName()
                .append(" with ")
                .append(candleBlock.getName());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                         @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return COMBINED_SHAPE;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level,
                                           @NotNull BlockPos pos, @NotNull Player player,
                                           @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);

        // Lighting with held item
        if (!state.getValue(LIT) && !held.isEmpty()) {
            if (held.is(Items.FLINT_AND_STEEL)) {
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS,
                        1.0F, level.random.nextFloat() * 0.4F + 0.8F);
                if (!level.isClientSide) level.setBlock(pos, state.setValue(LIT, true), 11);
                if (!player.getAbilities().instabuild)
                    held.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            if (held.is(Items.FIRE_CHARGE)) {
                level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS,
                        1.0F, level.random.nextFloat() * 0.4F + 0.8F);
                if (!level.isClientSide) {
                    level.setBlock(pos, state.setValue(LIT, true), 11);
                    held.shrink(1);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        // Eating with empty hand
        if (held.isEmpty()) {
            if (level.isClientSide) return player.canEat(false) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            return eatSliceFromCandle(level, pos, state, player);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult eatSliceFromCandle(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) return InteractionResult.PASS;

        Block parent = parentCake.get();
        if (parent instanceof ModCakeBlock modCake) {
            ItemStack sliceStack = modCake.getPieSliceItem();
            FoodProperties food = sliceStack.getItem().getFoodProperties();
            if (food != null) {
                player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
                for (var entry : food.getEffects()) {
                    if (entry != null && level.random.nextFloat() < entry.getSecond()) {
                        player.addEffect(entry.getFirst());
                    }
                }
            }
        }

        // Pop candle
        Block.popResource(level, pos, new ItemStack(candleBlock.asItem()));
        // Advance cake to bites=1
        BlockState cakeState = parent.defaultBlockState()
                .setValue(ModCakeBlock.FACING, state.getValue(FACING))
                .setValue(CakeBlock.BITES, 1);
        level.setBlock(pos, cakeState, 3);
        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level,
                             @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(LIT)) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5;
            if (random.nextInt(4) == 0) {
                level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
            }
            level.addParticle(ParticleTypes.SMALL_FLAME, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing,
                                            @NotNull BlockState facingState, @NotNull LevelAccessor level,
                                            @NotNull BlockPos pos, @NotNull BlockPos facingPos) {
        return facing == Direction.DOWN && !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, facing, facingState, level, pos, facingPos);
    }
}
