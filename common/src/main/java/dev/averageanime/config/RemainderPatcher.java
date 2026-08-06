package dev.averageanime.config;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.item.remainder.CraftingRemainder;
import dev.averageanime.mixin.ItemAccessorMixin;
import dev.averageanime.platform.Services;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

final class RemainderPatcher {

    private static final Map<Item, Item> ORIGINALS = new HashMap<>();

    private RemainderPatcher() {}

    static void apply() {
        restore();
        for (String entry : Services.PLATFORM.getCraftingRemainders()) {
            String[] parts = entry.split("\\|");
            if (parts.length != 2) {
                CreateFoodCommon.LOGGER.warn("Create: Food - Ignoring malformed crafting_remainders entry '{}'", entry);
                continue;
            }
            Item input = CraftingRemainder.resolveItem(parts[0].trim());
            Item remainder = CraftingRemainder.resolveItem(parts[1].trim());
            if (input == Items.AIR || remainder == Items.AIR) {
                CreateFoodCommon.LOGGER.warn("Create: Food - Ignoring crafting_remainders entry '{}' (unknown item)", entry);
                continue;
            }
            if (!ORIGINALS.containsKey(input)) {
                ORIGINALS.put(input, input.getCraftingRemainingItem());
            }
            ((ItemAccessorMixin) input).createfood$setCraftingRemainingItem(remainder);
        }
    }

    static void restore() {
        ORIGINALS.forEach((item, original) -> ((ItemAccessorMixin) item).createfood$setCraftingRemainingItem(original));
        ORIGINALS.clear();
    }
}
