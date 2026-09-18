package dev.averageanime.config;

import dev.averageanime.item.type.EffectFood;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.ArrayList;
import java.util.List;

final class FoodPatcher {

    private FoodPatcher() {}

    static FoodProperties patch(FoodProperties base, String itemKey) {
        ItemNutritionOverride nutrition = ConfigValues.getItemNutritionOverride(itemKey);
        List<ItemEffectOverride> entries = ConfigValues.getItemOverrideEntries(itemKey);
        if (nutrition == null && entries.isEmpty()) return base;

        FoodProperties patched = EffectFood.applyNutritionOverride(base, itemKey);
        if (entries.isEmpty()) return patched;

        List<ItemEffectOverride> additions = new ArrayList<>(entries);
        var fx = new FoodProperties.Builder();
        for (Pair<MobEffectInstance, Float> possible : patched.getEffects()) {
            MobEffectInstance instance = possible.getFirst();
            ItemEffectOverride override = EffectFood.findOverrideForEffect(entries, instance);
            if (override == null) {
                fx.effect(instance, possible.getSecond());
                continue;
            }
            additions.remove(override);
            if (!override.remove()) {
                fx.effect(new MobEffectInstance(instance.getEffect(),
                        override.duration(), override.amplifier()), possible.getSecond());
            }
        }
        for (ItemEffectOverride addition : additions) {
            if (addition.remove()) continue;
            EffectFood.resolveEffect(addition.categoryOrEffectId()).ifPresent(holder ->
                    fx.effect(new MobEffectInstance(holder.value(), addition.duration(), addition.amplifier()),
                            addition.chance()));
        }
        return EffectFood.withEffects(patched, fx);
    }
}
