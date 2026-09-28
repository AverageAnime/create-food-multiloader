package dev.averageanime.block.type.misc;

import dev.averageanime.createfood.lib.util.ItemSpawns;
import dev.averageanime.item.effect.EffectContext;
import dev.averageanime.item.type.EffectFood;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import java.util.function.Supplier;
import dev.averageanime.createfood.lib.block.BiteBlock;

/** Adds nutrition and effects on top of {@link BiteBlock}. */
public abstract class ConsumableBlock extends BiteBlock {

    public ConsumableBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult hitResult) {
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

        ItemStack sliceStack = getSliceItem();
        FoodProperties food = sliceStack.get(DataComponents.FOOD);
        if (food != null) {
            player.getFoodData().eat(food);
            EffectContext.begin(player, sliceStack);
            try {
                for (FoodProperties.PossibleEffect effect : EffectFood.enabledEffects(food,
                        BuiltInRegistries.ITEM.getKey(sliceStack.getItem()).getPath())) {
                    if (!level.isClientSide && effect != null
                            && level.random.nextFloat() < effect.probability()) {
                        player.addEffect(effect.effect());
                    }
                }
                // Compat-category effects are deferred rather than baked into FOOD.
                if (sliceStack.getItem() instanceof EffectFood effectFood) {
                    effectFood.applyNonBakedEffects(level, player);
                }
            } finally {
                EffectContext.end(player);
            }
        }

        advanceBites(level, pos, state);
        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }
}
