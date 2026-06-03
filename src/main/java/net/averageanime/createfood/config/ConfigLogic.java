package net.averageanime.createfood.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class ConfigLogic {
    private ConfigLogic() {}

    // ── Effect override parsing ───────────────────────────────────────────────

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
                return new ItemEffectOverride(categoryOrEffectId, 0, 0, true);
            if (p.length == 4) {
                try {
                    return new ItemEffectOverride(categoryOrEffectId,
                            Integer.parseInt(p[2]), Integer.parseInt(p[3]), false);
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
                result.add(new ItemEffectOverride(p[1], 0, 0, true));
            } else if (p.length == 4) {
                try {
                    result.add(new ItemEffectOverride(p[1],
                            Integer.parseInt(p[2]), Integer.parseInt(p[3]), false));
                } catch (NumberFormatException ignored) {}
            }
        }
        return result;
    }

    // ── Nutrition override parsing ────────────────────────────────────────────

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

    // ── Category effect override parsing ─────────────────────────────────────

    @Nullable
    public static String getCategoryEffectOverride(String categoryName,
                                                    List<? extends String> categoryOverrides) {
        for (String entry : categoryOverrides) {
            String[] p = entry.split("\\|");
            if (p.length == 2 && p[0].equals(categoryName)) return p[1];
        }
        return null;
    }

    // ── Filter list matching ──────────────────────────────────────────────────

    public static boolean matchesFilterList(ItemStack stack, List<? extends String> list) {
        if (list.isEmpty()) return false;
        ResourceLocation itemLoc = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (itemLoc == null) return false;
        String modId  = itemLoc.getNamespace();
        String itemId = itemLoc.toString();
        for (String entry : list) {
            if (entry.startsWith("mod:")  && entry.substring(4).equals(modId))  return true;
            if (entry.startsWith("item:") && entry.substring(5).equals(itemId)) return true;
            if (entry.startsWith("tag:")) {
                ResourceLocation tagLoc = ResourceLocation.tryParse(entry.substring(4));
                if (tagLoc != null && stack.is(TagKey.create(net.minecraft.core.registries.Registries.ITEM, tagLoc)))
                    return true;
            }
        }
        return false;
    }

    // ── Storage item filtering ────────────────────────────────────────────────

    public static boolean isClothSackItemAllowed(ItemStack stack) {
        CreateFoodConfig.Server cfg = CreateFoodConfig.SERVER;
        if (matchesFilterList(stack, cfg.clothSackExclude.get())) return false;
        boolean isFood = stack.getItem().isEdible();
        if (cfg.clothSackAllowFood.get() && isFood) return true;
        List<? extends String> filter = cfg.clothSackFilter.get();
        if (!filter.isEmpty()) return matchesFilterList(stack, filter);
        return !cfg.clothSackAllowFood.get();
    }

    public static boolean isRationBoxItemAllowed(ItemStack stack) {
        CreateFoodConfig.Server cfg = CreateFoodConfig.SERVER;
        if (matchesFilterList(stack, cfg.rationBoxExclude.get())) return false;
        boolean isFood = stack.getItem().isEdible();
        if (cfg.rationBoxAllowFood.get() && isFood) return true;
        List<? extends String> filter = cfg.rationBoxFilter.get();
        if (!filter.isEmpty()) return matchesFilterList(stack, filter);
        return !cfg.rationBoxAllowFood.get();
    }

    public static boolean isHandcraftingAllowed(ItemStack result) {
        CreateFoodConfig.Server cfg = CreateFoodConfig.SERVER;
        if (matchesFilterList(result, cfg.handcraftExclude.get())) return false;
        List<? extends String> filter = cfg.handcraftingFilter.get();
        if (!filter.isEmpty()) return matchesFilterList(result, filter);
        return true;
    }

    // ── Creative tab item filtering ───────────────────────────────────────────

    public static boolean isItemEnabled(ItemStack stack) {
        if (CreateFoodConfig.CLIENT == null) return true;
        ResourceLocation loc = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (loc == null) return true;
        return !CreateFoodConfig.CLIENT.hideItems.get().contains(loc.getPath());
    }

    // ── Simple value records ──────────────────────────────────────────────────

    public record ItemEffectOverride(String effectId, int duration, int amplifier, boolean remove) {}

    public record ItemNutritionOverride(int nutrition, float saturation) {
        public static final int KEEP_INT = Integer.MIN_VALUE;
        public static final float KEEP_FLOAT = Float.MIN_VALUE;
    }
}
