package dev.averageanime.item.type;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import dev.averageanime.util.FoodAccess;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class EffectDrink extends EffectFood {

    public EffectDrink(Properties properties) {
        super(properties);
    }

    public EffectDrink(Properties properties, Set<String> existingEffectIds) {
        super(properties, existingEffectIds);
    }

    public EffectDrink(Properties properties, Set<String> existingEffectIds, List<DeferredFx> deferredEffects) {
        super(properties, existingEffectIds, deferredEffects);
    }

    public EffectDrink(Properties properties, Set<String> existingEffectIds, List<DeferredFx> deferredEffects,
                       dev.averageanime.util.Tooltips.TooltipSpec tip) {
        super(properties, existingEffectIds, deferredEffects, tip);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 32;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        FoodProperties food = FoodAccess.get(stack, consumer);
        if (food == null) {
            return super.finishUsingItem(stack, level, consumer);
        }

        FoodProperties patched = applyNutritionOverride(food);
        patched = applyEffectOverrides(patched);

        Item remItem = stack.getItem().getCraftingRemainingItem();
        ItemStack remainder = remItem != null ? new ItemStack(remItem) : ItemStack.EMPTY;

        if (!level.isClientSide) {
            if (consumer instanceof Player player) {
                EffectFood.eatAndApplyFoodProperties(patched, level, player);
                if (consumer instanceof ServerPlayer sp) {
                    CriteriaTriggers.CONSUME_ITEM.trigger(sp, stack);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }

        applyAdditions(level, consumer);
        applyDeferredEffects(level, consumer);

        if (stack.isEmpty()) return remainder;
        if (!remainder.isEmpty() && consumer instanceof Player player && !player.getAbilities().instabuild) {
            if (!player.getInventory().add(remainder)) {
                player.drop(remainder, false);
            }
        }
        return stack;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        FoodProperties food = FoodAccess.get(held, player);
        if (food != null) {
            if (player.canEat(food.canAlwaysEat())) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(held);
            }
            return InteractionResultHolder.fail(held);
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}