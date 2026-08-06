package dev.averageanime.config;

import dev.averageanime.item.type.EffectFood;
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
        for (var possible : patched.effects()) {
            ItemEffectOverride override = EffectFood.findOverrideForEffect(entries, possible.effect());
            if (override == null) {
                fx.effect(possible.effect(), possible.probability());
                continue;
            }
            additions.remove(override);
            if (!override.remove()) {
                fx.effect(new MobEffectInstance(possible.effect().getEffect(),
                        override.duration(), override.amplifier()), possible.probability());
            }
        }
        for (ItemEffectOverride addition : additions) {
            if (addition.remove()) continue;
            EffectFood.resolveEffect(addition.categoryOrEffectId()).ifPresent(holder ->
                    fx.effect(new MobEffectInstance(holder, addition.duration(), addition.amplifier()),
                            addition.chance()));
        }
        return EffectFood.withEffects(patched, fx);
    }
}
