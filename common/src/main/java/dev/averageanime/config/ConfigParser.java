package dev.averageanime.config;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ConfigParser {
    private ConfigParser() {}

    static boolean effectIdsMatch(String configId, String itemId) {
        if (configId.equals(itemId)) return true;
        if (!configId.contains(":") && itemId.equals("minecraft:" + configId)) return true;
        return !itemId.contains(":") && configId.equals("minecraft:" + itemId);
    }

    private static final ListParseCache<Map<String, List<ItemEffectOverride>>> EFFECT_OVERRIDES =
            new ListParseCache<>(ConfigParser::parseEffectOverrides);

    private static Map<String, List<ItemEffectOverride>> parseEffectOverrides(List<? extends String> entries) {
        Map<String, List<ItemEffectOverride>> byItem = new HashMap<>();
        for (String entry : entries) {
            String[] p = entry.split("\\|");
            if (p.length < 3) continue;
            ItemEffectOverride parsed;
            if (p.length == 3 && p[2].equals("remove")) {
                parsed = new ItemEffectOverride(p[1], 0, 0, true, 1.0f);
            } else if (p.length >= 4) {
                try {
                    float chance = p.length >= 5 ? Float.parseFloat(p[4]) : 1.0f;
                    parsed = new ItemEffectOverride(p[1],
                            Integer.parseInt(p[2]), Integer.parseInt(p[3]), false, chance);
                } catch (NumberFormatException ignored) {
                    continue;
                }
            } else {
                continue;
            }
            byItem.computeIfAbsent(p[0], k -> new ArrayList<>()).add(parsed);
        }
        byItem.replaceAll((id, list) -> List.copyOf(list));
        return byItem;
    }

    @Nullable
    public static ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId,
                                                           List<? extends String> itemEffectOverrides) {
        for (ItemEffectOverride entry : getItemOverrideEntries(itemId, itemEffectOverrides)) {
            if (!effectIdsMatch(entry.categoryOrEffectId(), categoryOrEffectId)) continue;
            // The result carries the queried id, not the config's spelling.
            return new ItemEffectOverride(categoryOrEffectId, entry.duration(),
                    entry.amplifier(), entry.remove(), entry.chance());
        }
        return null;
    }

    public static List<ItemEffectOverride> getItemOverrideEntries(String itemId,
                                                                   List<? extends String> itemEffectOverrides) {
        return EFFECT_OVERRIDES.get(itemEffectOverrides).getOrDefault(itemId, List.of());
    }

    private static final ListParseCache<ConcurrentHashMap<String, Optional<ItemNutritionOverride>>>
            NUTRITION_OVERRIDES = new ListParseCache<>(entries -> new ConcurrentHashMap<>());

    @Nullable
    public static ItemNutritionOverride getItemNutritionOverride(String itemId,
                                                                 List<? extends String> nutritionOverrides) {
        return NUTRITION_OVERRIDES.get(nutritionOverrides)
                .computeIfAbsent(itemId, id -> Optional.ofNullable(scanNutritionOverride(id, nutritionOverrides)))
                .orElse(null);
    }

    @Nullable
    private static ItemNutritionOverride scanNutritionOverride(String itemId,
                                                               List<? extends String> nutritionOverrides) {
        for (String entry : nutritionOverrides) {
            String[] p = entry.split("\\|");
            if (p.length != 3 || !p[0].equals(itemId)) continue;
            int   nutrition  = p[1].equals("-") ? ItemNutritionOverride.KEEP_INT   : Integer.parseInt(p[1]);
            float saturation = p[2].equals("-") ? ItemNutritionOverride.KEEP_FLOAT : Float.parseFloat(p[2]);
            return new ItemNutritionOverride(nutrition, saturation);
        }
        return null;
    }

    private static final ListParseCache<Map<String, String>> CATEGORY_OVERRIDES =
            new ListParseCache<>(ConfigParser::parseCategoryOverrides);

    private static Map<String, String> parseCategoryOverrides(List<? extends String> entries) {
        Map<String, String> byCategory = new HashMap<>();
        for (String entry : entries) {
            String[] p = entry.split("\\|");
            if (p.length == 2) byCategory.putIfAbsent(p[0], p[1]);
        }
        return byCategory;
    }

    @Nullable
    public static String getCategoryEffectOverride(String categoryName,
                                                    List<? extends String> categoryOverrides) {
        return CATEGORY_OVERRIDES.get(categoryOverrides).get(categoryName);
    }

    public static boolean isItemEnabled(String itemId, List<? extends String> hideItems) {
        return !hideItems.contains(itemId);
    }

    public static boolean isDisplayBlockEnabled(String blockId, List<? extends String> hideItems) {
        if (!isItemEnabled(blockId, hideItems)) return false;
        String base = blockId
                .replace("_small_plate_block", "")
                .replace("_plate_block", "")
                .replace("_block", "");
        return isItemEnabled(base, hideItems);
    }

    public static boolean isFluidBucketEnabled(String bucketId, List<? extends String> hideItems) {
        if (!isItemEnabled(bucketId, hideItems)) return false;
        String base = bucketId.replace("_bucket", "");
        return isItemEnabled(base, hideItems);
    }

    private record CompiledFilter(Set<String> mods, Set<String> items, List<TagKey<Item>> tags) {}

    private static final ListParseCache<CompiledFilter> FILTERS =
            new ListParseCache<>(ConfigParser::compileFilter);

    private static CompiledFilter compileFilter(List<? extends String> list) {
        Set<String> mods = new HashSet<>();
        Set<String> items = new HashSet<>();
        List<TagKey<Item>> tags = new ArrayList<>();
        for (String entry : list) {
            if (entry.startsWith("mod:")) {
                mods.add(entry.substring(4));
            } else if (entry.startsWith("item:")) {
                items.add(entry.substring(5));
            } else if (entry.startsWith("tag:")) {
                ResourceLocation tagLoc = ResourceLocation.tryParse(entry.substring(4));
                if (tagLoc != null) tags.add(TagKey.create(Registries.ITEM, tagLoc));
            }
        }
        return new CompiledFilter(Set.copyOf(mods), Set.copyOf(items), List.copyOf(tags));
    }

    public static boolean matchesFilterList(ItemStack stack, List<? extends String> list) {
        if (list.isEmpty()) return false;
        CompiledFilter filter = FILTERS.get(list);
        ResourceLocation key = stack.getItem().builtInRegistryHolder().key().location();
        if (filter.mods().contains(key.getNamespace()) || filter.items().contains(key.toString())) return true;
        for (TagKey<Item> tag : filter.tags()) {
            if (stack.is(tag)) return true;
        }
        return false;
    }

    public static boolean isClothSackItemAllowed(ItemStack stack, List<? extends String> exclude,
                                                  List<? extends String> filter, boolean allowFood) {
        if (matchesFilterList(stack, exclude)) return false;
        boolean isFood = stack.has(DataComponents.FOOD);
        if (allowFood && isFood) return true;
        if (!filter.isEmpty()) return matchesFilterList(stack, filter);
        return !allowFood;
    }

    public static boolean isRationBoxItemAllowed(ItemStack stack, List<? extends String> exclude,
                                                  List<? extends String> filter, boolean allowFood) {
        if (matchesFilterList(stack, exclude)) return false;
        boolean isFood = stack.has(DataComponents.FOOD);
        if (allowFood && isFood) return true;
        if (!filter.isEmpty()) return matchesFilterList(stack, filter);
        return !allowFood;
    }
}