package dev.averageanime.item.effect;

import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.platform.Services;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class EffectContext {

    private static final Map<LivingEntity, List<ItemEffectOverride>> ACTIVE = new WeakHashMap<>();
    private static final Set<LivingEntity> CONSUMING = Collections.newSetFromMap(new WeakHashMap<>());

    private EffectContext() {}

    public static void begin(LivingEntity entity, ItemStack stack) {
        // Only food counts as consumption for stacking purposes. This runs for every item finishing
        // its use animation, so without the check a potion would stack its duration too.
        if (stack.has(DataComponents.FOOD)) CONSUMING.add(entity);
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        List<ItemEffectOverride> overrides = Services.PLATFORM.getItemOverrideEntries(itemId);
        if (!overrides.isEmpty()) ACTIVE.put(entity, overrides);
    }

    public static void end(LivingEntity entity) {
        CONSUMING.remove(entity);
        ACTIVE.remove(entity);
    }

    public static List<ItemEffectOverride> active(LivingEntity entity) {
        return ACTIVE.getOrDefault(entity, List.of());
    }

    public static boolean isConsuming(LivingEntity entity) {
        return CONSUMING.contains(entity);
    }
}
