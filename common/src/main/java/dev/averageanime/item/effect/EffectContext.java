package dev.averageanime.item.effect;

import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class EffectContext {

    private static final Map<LivingEntity, List<ItemEffectOverride>> ACTIVE = new WeakHashMap<>();

    private EffectContext() {}

    public static void begin(LivingEntity entity, ItemStack stack) {
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        List<ItemEffectOverride> overrides = Services.PLATFORM.getItemOverrideEntries(itemId);
        if (!overrides.isEmpty()) ACTIVE.put(entity, overrides);
    }

    public static void end(LivingEntity entity) {
        ACTIVE.remove(entity);
    }

    public static List<ItemEffectOverride> active(LivingEntity entity) {
        return ACTIVE.getOrDefault(entity, List.of());
    }
}
