package dev.averageanime.item.effect;

import dev.averageanime.config.ConfigValues;
import dev.averageanime.config.type.ItemEffectOverride;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * What is being consumed by which entity. A singleplayer client and server both run consumption, so
 * these are touched from two threads; the maps are weak so an entity that never reaches {@link #end}
 * is not kept alive.
 */
public final class EffectContext {

    private static final Map<LivingEntity, List<ItemEffectOverride>> ACTIVE = Collections.synchronizedMap(new WeakHashMap<>());
    private static final Set<LivingEntity> CONSUMING = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    private EffectContext() {}

    public static void begin(LivingEntity entity, ItemStack stack) {
        // Only food counts for stacking; this runs for every item finishing its use animation.
        if (stack.has(DataComponents.FOOD)) CONSUMING.add(entity);
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        List<ItemEffectOverride> overrides = ConfigValues.getItemOverrideEntries(itemId);
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
