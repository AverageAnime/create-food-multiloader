package dev.averageanime.item.effect;

import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class TooltipContext {

    private static final ThreadLocal<List<ItemEffectOverride>> ACTIVE =
            ThreadLocal.withInitial(List::of);

    private TooltipContext() {}

    public static List<ItemEffectOverride> push(ItemStack stack) {
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        List<ItemEffectOverride> prev = ACTIVE.get();
        ACTIVE.set(Services.PLATFORM.getItemOverrideEntries(itemId));
        return prev;
    }

    public static void restore(List<ItemEffectOverride> prev) {
        if (prev.isEmpty()) ACTIVE.remove(); else ACTIVE.set(prev);
    }

    public static List<ItemEffectOverride> active() {
        return ACTIVE.get();
    }

    public static void runWith(ItemStack stack, Runnable action) {
        List<ItemEffectOverride> prev = push(stack);
        try {
            action.run();
        } finally {
            restore(prev);
        }
    }
}
