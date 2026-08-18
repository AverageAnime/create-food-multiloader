package dev.averageanime.block.type.cake;

import dev.averageanime.item.effect.EffectContext;
import dev.averageanime.item.type.EffectFood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CakeCandleBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final VoxelShape CAKE_SHAPE   = Block.box(1, 0, 1, 15, 8, 15);
    private static final VoxelShape CANDLE_SHAPE = Block.box(7, 8, 7,  9, 14,  9);
    private static final VoxelShape COMBINED_SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);

    private final Supplier<Block> parentCake;
    private final Block candleBlock;
    private final String parentCakeName;
    private final String candleSuffix;

    public CakeCandleBlock(Properties properties, Supplier<Block> parentCake, Block candleBlock,
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
    public Block getParentCake()      { return parentCake.get(); }

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
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state,
                                                     @NotNull Level level, @NotNull BlockPos pos,
                                                     @NotNull Player player, @NotNull InteractionHand hand,
                                                     @NotNull BlockHitResult hit) {
        if (!state.getValue(LIT)) {
            if (stack.is(Items.FLINT_AND_STEEL)) {
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS,
                        1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(pos, state.setValue(LIT, true), 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                if (!player.getAbilities().instabuild) {
                    stack.hurtAndBreak(1, player,
                            hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
            if (stack.is(Items.FIRE_CHARGE)) {
                level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS,
                        1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(pos, state.setValue(LIT, true), 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level,
                                                         @NotNull BlockPos pos, @NotNull Player player,
                                                         @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            if (player.canEat(false)) return InteractionResult.SUCCESS;
            return InteractionResult.PASS;
        }
        return eatSliceFromCandle(level, pos, state, player);
    }

    private InteractionResult eatSliceFromCandle(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) return InteractionResult.PASS;

        Block parent = parentCake.get();
        if (parent instanceof CakeFoodBlock modCake) {
            ItemStack sliceStack = modCake.getPieSliceItem();
            FoodProperties food = sliceStack.get(DataComponents.FOOD);
            if (food != null) {
                player.getFoodData().eat(food);
                EffectContext.begin(player, sliceStack);
                try {
                    for (FoodProperties.PossibleEffect effect : food.effects()) {
                        if (effect != null && level.random.nextFloat() < effect.probability()) {
                            player.addEffect(effect.effect());
                        }
                    }
                    // Compat-category effects are deferred rather than baked into FOOD,
                    // so eating the block form would otherwise skip them entirely.
                    if (sliceStack.getItem() instanceof EffectFood effectFood) {
                        effectFood.applyNonBakedEffects(level, player);
                    }
                } finally {
                    EffectContext.end(player);
                }
            }
        }

        popResource(level, pos, new ItemStack(candleBlock.asItem()));
        BlockState cakeState = parent.defaultBlockState()
                .setValue(CakeFoodBlock.FACING, state.getValue(FACING))
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