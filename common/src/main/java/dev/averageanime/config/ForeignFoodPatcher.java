package dev.averageanime.config;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.item.remainder.CraftingRemainder;
import dev.averageanime.mixin.ItemAccessorMixin;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

final class ForeignFoodPatcher {

    private static final Map<Item, DataComponentMap> ORIGINALS = new HashMap<>();

    private ForeignFoodPatcher() {}

    static void apply() {
        restore();
        for (String itemKey : collectForeignKeys()) {
            Item item = CraftingRemainder.resolveItem(itemKey);
            if (item == Items.AIR) {
                CreateFoodCommon.LOGGER.warn("Create: Food - Unknown item '{}' in food overrides", itemKey);
                continue;
            }
            DataComponentMap components = item.components();
            FoodProperties food = components.get(DataComponents.FOOD);
            if (food == null) {
                CreateFoodCommon.LOGGER.warn("Create: Food - '{}' has no food component; override skipped", itemKey);
                continue;
            }
            FoodProperties patched = FoodPatcher.patch(food, itemKey);
            if (patched == food) continue;
            if (!ORIGINALS.containsKey(item)) {
                ORIGINALS.put(item, components);
            }
            ((ItemAccessorMixin) item).createfood$setComponents(
                    DataComponentMap.builder().addAll(components).set(DataComponents.FOOD, patched).build());
        }
    }

    static void restore() {
        ORIGINALS.forEach((item, original) -> ((ItemAccessorMixin) item).createfood$setComponents(original));
        ORIGINALS.clear();
    }

    private static Set<String> collectForeignKeys() {
        Set<String> keys = new LinkedHashSet<>();
        collectKeys(ConfigValues.getItemNutritionOverrideEntries(), keys);
        collectKeys(ConfigValues.getItemEffectOverrideEntries(), keys);
        return keys;
    }

    private static void collectKeys(Iterable<? extends String> entries, Set<String> out) {
        for (String entry : entries) {
            String key = entry.split("\\|")[0].trim();
            if (key.contains(":")) out.add(key);
        }
    }
}
