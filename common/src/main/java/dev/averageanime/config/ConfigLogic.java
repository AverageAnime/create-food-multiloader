package dev.averageanime.config;

import dev.averageanime.config.override.ItemEffectOverride;
import dev.averageanime.config.override.ItemNutritionOverride;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class ConfigLogic {
    private ConfigLogic() {}

    static boolean effectIdsMatch(String configId, String itemId) {
        if (configId.equals(itemId)) return true;
        if (!configId.contains(":") && itemId.equals("minecraft:" + configId)) return true;
        return !itemId.contains(":") && configId.equals("minecraft:" + itemId);
    }

    @Nullable
    public static ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId,
                                                           List<? extends String> itemEffectOverrides) {
        for (String entry : itemEffectOverrides) {
            String[] p = entry.split("\\|");
            if (p.length < 3 || !p[0].equals(itemId)) continue;
            if (!effectIdsMatch(p[1], categoryOrEffectId)) continue;
            if (p.length == 3 && p[2].equals("remove"))
                return new ItemEffectOverride(categoryOrEffectId, 0, 0, true, 1.0f);
            if (p.length >= 4) {
                try {
                    float chance = p.length >= 5 ? Float.parseFloat(p[4]) : 1.0f;
                    return new ItemEffectOverride(categoryOrEffectId,
                            Integer.parseInt(p[2]), Integer.parseInt(p[3]), false, chance);
                } catch (NumberFormatException ignored) {}
            }
        }
        return null;
    }

    public static List<ItemEffectOverride> getItemOverrideEntries(String itemId,
                                                                   List<? extends String> itemEffectOverrides) {
        List<ItemEffectOverride> result = new ArrayList<>();
        for (String entry : itemEffectOverrides) {
            String[] p = entry.split("\\|");
            if (p.length < 3 || !p[0].equals(itemId)) continue;
            if (p.length == 3 && p[2].equals("remove")) {
                result.add(new ItemEffectOverride(p[1], 0, 0, true, 1.0f));
            } else if (p.length >= 4) {
                try {
                    float chance = p.length >= 5 ? Float.parseFloat(p[4]) : 1.0f;
                    result.add(new ItemEffectOverride(p[1],
                            Integer.parseInt(p[2]), Integer.parseInt(p[3]), false, chance));
                } catch (NumberFormatException ignored) {}
            }
        }
        return result;
    }

    @Nullable
    public static ItemNutritionOverride getItemNutritionOverride(String itemId,
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

    @Nullable
    public static String getCategoryEffectOverride(String categoryName,
                                                    List<? extends String> categoryOverrides) {
        for (String entry : categoryOverrides) {
            String[] p = entry.split("\\|");
            if (p.length == 2 && p[0].equals(categoryName)) return p[1];
        }
        return null;
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

    public static boolean matchesFilterList(ItemStack stack, List<? extends String> list) {
        if (list.isEmpty()) return false;
        String modId  = stack.getItem().builtInRegistryHolder().key().location().getNamespace();
        String itemId = stack.getItem().builtInRegistryHolder().key().location().toString();
        for (String entry : list) {
            if (entry.startsWith("mod:")  && entry.substring(4).equals(modId))  return true;
            if (entry.startsWith("item:") && entry.substring(5).equals(itemId)) return true;
            if (entry.startsWith("tag:")) {
                ResourceLocation tagLoc = ResourceLocation.tryParse(entry.substring(4));
                if (tagLoc != null && stack.is(TagKey.create(Registries.ITEM, tagLoc))) return true;
            }
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