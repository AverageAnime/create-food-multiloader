package dev.averageanime.neoforge.item.type;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class EffectFood extends dev.averageanime.item.type.EffectFood {

    public EffectFood(Properties properties) {
        super(properties);
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds) {
        super(properties, existingEffectIds);
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds, List<DeferredFx> deferredEffects) {
        super(properties, existingEffectIds, deferredEffects);
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds, List<DeferredFx> deferredEffects,
                      dev.averageanime.util.Tooltips.TooltipSpec tip) {
        super(properties, existingEffectIds, deferredEffects, tip);
    }

    @Nullable
    @Override
    public FoodProperties getFoodProperties(@NotNull ItemStack stack, @Nullable LivingEntity entity) {
        FoodProperties base = super.getFoodProperties(stack, entity);
        if (base == null) return null;
        return applyNutritionOverride(base, BuiltInRegistries.ITEM.getKey(this).getPath());
    }
}
