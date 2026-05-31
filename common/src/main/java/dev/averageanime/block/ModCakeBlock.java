package dev.averageanime.block;

import dev.averageanime.block.type.ConsumableBlock;
import dev.averageanime.util.ItemSpawn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModCakeBlock extends CakeBlock {

    private static final Map<Block, Map<Item, Block>> CANDLE_CAKE_MAP = new HashMap<>();

    public static void registerCandleVariant(Block cakeBlock, Item candleItem, Block candleCakeBlock) {
        CANDLE_CAKE_MAP.computeIfAbsent(cakeBlock, k -> new HashMap<>()).put(candleItem, candleCakeBlock);
    }

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public final Supplier<Item> pieSlice;

    public ModCakeBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties);
        this.pieSlice = pieSlice;
    }

    public int getMaxBites() {
        return 7;
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack(this.pieSlice.get());
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            if (consumeBite(level, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }
        return consumeBite(level, pos, state, player);
    }

    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        }

        ItemStack sliceStack = getPieSliceItem();
        FoodProperties food = sliceStack.get(DataComponents.FOOD);
        if (food != null) {
            player.getFoodData().eat(food);
            for (FoodProperties.PossibleEffect effect : food.effects()) {
                if (!level.isClientSide && effect != null && level.random.nextFloat() < effect.probability()) {
                    player.addEffect(effect.effect());
                }
            }
        }

        advanceBites(level, pos, state);
        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    protected ItemInteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
        Direction direction = player.getDirection().getOpposite();
        advanceBites(level, pos, state);
        ItemSpawn.spawnItemEntity(level, getPieSliceItem(),
                pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
        level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return ItemInteractionResult.SUCCESS;
    }

    protected void advanceBites(Level level, BlockPos pos, BlockState state) {
        int bites = state.getValue(BITES);
        if (bites < getMaxBites() - 1) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return ConsumableBlock.rotateShape(SHAPE_BY_BITE[state.getValue(BITES)], state.getValue(FACING).getOpposite());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BITES);
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(ItemStack heldStack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                                    @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (heldStack.is(ConsumableBlock.KNIVES)) {
            return cutSlice(level, pos, state, player);
        }
        if (state.getValue(BITES) == 0) {
            Map<Item, Block> variants = CANDLE_CAKE_MAP.get(this);
            if (variants != null) {
                Block candleCake = variants.get(heldStack.getItem());
                if (candleCake != null) {
                    if (level.isClientSide) {
                        return ItemInteractionResult.SUCCESS;
                    }
                    BlockState candleState = candleCake.defaultBlockState()
                            .setValue(ModCandleCakeBlock.FACING, state.getValue(FACING))
                            .setValue(ModCandleCakeBlock.LIT, false);
                    level.setBlock(pos, candleState, 11);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    if (!player.getAbilities().instabuild) heldStack.shrink(1);
                    level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return ItemInteractionResult.CONSUME;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
