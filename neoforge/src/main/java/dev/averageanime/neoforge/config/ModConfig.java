package dev.averageanime.neoforge.config;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_EGG_IMPACT_REMAINDER;
    public static final ModConfigSpec.BooleanValue ENABLE_FILTER_INTERACTIONS;
    public static final ModConfigSpec.BooleanValue ENABLE_HANDCRAFTING;
    public static final ModConfigSpec.BooleanValue REQUIRE_SHIFT_FOR_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue SHOW_COMPATIBILITY;
    public static final ModConfigSpec.BooleanValue SHOW_INGREDIENTS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CATEGORY_EFFECT_OVERRIDES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CRAFTING_REMAINDERS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_TOOLTIPS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLE_ITEMS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FILTER_INTERACTIONS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HANDCRAFTING_FILTER;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_EFFECT_OVERRIDES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_NUTRITION_OVERRIDES;

    static {
        DISABLE_ITEMS = BUILDER
                .defineListAllowEmpty("disable_items",
                        List.of(
                                "beef_bun_peanut_butter",
                                "beef_bun_peanut_butter_bacon",
                                "bread_slice_peanut_butter",
                                "caramel_popcorn",
                                "cinnamon_sweet_roll_base",
                                "coffee_toffee",
                                "coffee_toffee_fudge",
                                "corn_flour",
                                "corn_stick",
                                "dragon_bun",
                                "dragon_bun_crimson_fungus",
                                "dragon_bun_warped_fungus",
                                "dragon_burger",
                                "dragon_burger_crimson_fungus",
                                "dragon_burger_warped_fungus",
                                "dragon_patty",
                                "dried_coffee_beans",
                                "eggplant_bun",
                                "eggplant_bun_cheese",
                                "eggplant_bun_cheese_lettuce",
                                "eggplant_bun_cheese_lettuce_tomato",
                                "eggplant_bun_cheese_tomato",
                                "eggplant_bun_lettuce",
                                "eggplant_bun_lettuce_tomato",
                                "eggplant_bun_tomato",
                                "eggplant_burger",
                                "eggplant_burger_lettuce",
                                "eggplant_burger_tomato",
                                "eggplant_cheeseburger",
                                "eggplant_cheeseburger_lettuce",
                                "eggplant_cheeseburger_lettuce_tomato",
                                "eggplant_cheeseburger_tomato",
                                "endermite_meatball",
                                "endermite_meatball_sandwich",
                                "endermite_meatball_stick_1",
                                "endermite_meatball_stick_2",
                                "endermite_meatball_stick_3",
                                "espresso_powder",
                                "ground_endermite",
                                "hamburger_peanut_butter",
                                "hamburger_peanut_butter_bacon",
                                "marshmallow_coffee_toffee_fudge",
                                "minced_dragon",
                                "pasta_plate_eggplant",
                                "pasta_plate_endermite_meatballs",
                                "pasta_plate_endermite_meatballs_tomato_sauce",
                                "pasta_plate_strider_meatballs",
                                "pasta_plate_strider_meatballs_tomato_sauce",
                                "peanut_butter_apple_jam_sandwich",
                                "peanut_butter_chorus_fruit_jam_sandwich",
                                "peanut_butter_melon_jam_sandwich",
                                "raw_cinnamon_sweet_roll_base",
                                "raw_endermite_meatball",
                                "raw_flesh_cookie",
                                "raw_ginger_cookie",
                                "raw_green_tea_cookie",
                                "raw_soul_berry_cookie",
                                "raw_spider_eye_cookie",
                                "raw_strider_meatball",
                                "raw_sugar_cookie",
                                "raw_ube_cake_base",
                                "raw_ube_cookie",
                                "small_endermite_meatballs",
                                "small_strider_meatballs",
                                "strider_meatball",
                                "strider_meatball_sandwich",
                                "strider_meatball_stick_1",
                                "strider_meatball_stick_2",
                                "strider_meatball_stick_3",
                                "tortilla_chip_bowl",
                                "ube_cake_base",
                                "ube_cream_frosting_bottle",
                                "ube_cream_frosting_piping_bag",
                                "ube_cream_ube_cake",
                                "ube_cream_ube_cake_slice",
                                "ube_sugar_dough"
                        ),
                        () -> "item_id",
                        obj -> obj instanceof String
                );

        BUILDER.push("tooltips");

        SHOW_COMPATIBILITY = BUILDER
                .define("show_compatibility", true);

        SHOW_INGREDIENTS = BUILDER
                .define("show_ingredients", true);

        REQUIRE_SHIFT_FOR_TOOLTIPS = BUILDER
                .define("require_shift", false);

        CUSTOM_TOOLTIPS = BUILDER
                .gameRestart()
                .defineListAllowEmpty("custom_tooltips",
                        List.of(
                                "create:chocolate_glazed_berries|chocolate",
                                "create:honeyed_apple|honey",
                                "create:sweet_roll|cream_frosting",
                                "culturaldelights:avocado_toast|avocado",
                                "culturaldelights:eggplant_burger|lettuce,tomato",
                                "culturaldelights:mutton_sandwich|mutton,beetroot,fried_egg",
                                "culturaldelights:pork_wrap|pork,apple,lettuce",
                                "culturaldelights:beef_burrito|beef,rice,avocado",
                                "culturaldelights:fish_taco|fish,lettuce,tomato",
                                "culturaldelights:chicken_taco|chicken,cucumber,corn,tomato",
                                "delightfulcreators:incomplete_chicken_sandwich|chicken",
                                "delightfulcreators:incomplete_pasta_with_meatballs|beef_meatballs",
                                "delightfulcreators:incomplete_pasta_with_mutton_chop|mutton",
                                "delightfulcreators:incomplete_squid_ink_pasta|squid_ink",
                                "displaydelight:ctd_plated_avocado_toast|avocado",
                                "displaydelight:ctd_plated_eggplant_burger|lettuce,tomato",
                                "displaydelight:ctd_plated_mutton_sandwich|mutton,fried_egg,beetroot",
                                "displaydelight:ctd_plated_pork_wrap|pork,apple,lettuce",
                                "displaydelight:ed_plated_berry_sweet_roll|cream_frosting,berry",
                                "displaydelight:ed_plated_glow_berry_jelly_sandwich|peanut_butter,glow_berry",
                                "displaydelight:ed_plated_glow_berry_sweet_roll|cream_frosting,glow_berry",
                                "displaydelight:ed_plated_peanut_butter_honey_sandwich|peanut_butter",
                                "displaydelight:ed_plated_sweet_berry_jelly_sandwich|peanut_butter,berry",
                                "displaydelight:ed_plated_sweet_roll|cream_frosting",
                                "displaydelight:mixed_salad|beetroot,tomato",
                                "displaydelight:pasta_with_meatballs|tomato_sauce,beef_meatballs",
                                "displaydelight:pasta_with_mutton_chop|tomato_sauce,mutton",
                                "displaydelight:plated_bacon_sandwich|bacon,lettuce,tomato",
                                "displaydelight:plated_chicken_sandwich|chicken,lettuce,carrot",
                                "displaydelight:plated_egg_sandwich|fried_egg",
                                "displaydelight:plated_hamburger|onion,lettuce,tomato",
                                "displaydelight:plated_kelp_roll|carrot",
                                "displaydelight:plated_mutton_wrap|mutton,onion,lettuce",
                                "displaydelight:small_plated_cake_slice|cream_frosting,berry",
                                "displaydelight:small_plated_kelp_roll_slice|carrot",
                                "displaydelight:squid_ink_pasta|squid_ink,fish",
                                "expandeddelight:berry_sweet_roll|berry",
                                "expandeddelight:glow_berry_jelly_sandwich|peanut_butter,glow_berry_jam",
                                "expandeddelight:glow_berry_sweet_roll|glow_berry",
                                "expandeddelight:peanut_butter_honey_sandwich|peanut_butter",
                                "expandeddelight:sweet_berry_jelly_sandwich|peanut_butter,berry_jam",
                                "farmersdelight:bacon_sandwich|bacon,lettuce,tomato",
                                "farmersdelight:cake_slice|cream_frosting,berry",
                                "farmersdelight:chicken_sandwich|chicken,lettuce,carrot",
                                "farmersdelight:egg_sandwich|fried_egg",
                                "farmersdelight:hamburger|onion,lettuce,tomato",
                                "farmersdelight:kelp_roll_slice|carrot",
                                "farmersdelight:kelp_roll|carrot",
                                "farmersdelight:mixed_salad|beetroot,tomato",
                                "farmersdelight:mutton_wrap|mutton,onion,lettuce",
                                "farmersdelight:pasta_with_meatballs|tomato_sauce,beef_meatballs",
                                "farmersdelight:pasta_with_mutton_chop|tomato_sauce,mutton",
                                "farmersdelight:squid_ink_pasta|squid_ink,fish",
                                "farmersdelight:sweet_berry_cookie|berry",
                                "minecraft:cake|cream_frosting,berry",
                                "minecraft:cookie|chocolate_chips"
                        ),
                        () -> "item_key|ingredients",
                        obj -> obj instanceof String
                );

        BUILDER.pop();

        SERVER_BUILDER.push("effects");

        CATEGORY_EFFECT_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("category_overrides",
                        List.of(),
                        () -> "category_name|mod_id:effect_id",
                        obj -> obj instanceof String s && s.split("\\|").length == 2
                );

        ITEM_EFFECT_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("item_overrides",
                        List.of(),
                        () -> "item_id|category_or_effect_id|duration|amplifier  OR  item_id|category_or_effect_id|remove",
                        obj -> {
                            if (!(obj instanceof String s)) return false;
                            String[] p = s.split("\\|");
                            if (p.length == 3) return p[2].equals("remove");
                            if (p.length == 4) {
                                try {
                                    Integer.parseInt(p[2]);
                                    Integer.parseInt(p[3]);
                                    return true;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            }
                            return false;
                        }
                );

        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("handcraft");

        ENABLE_HANDCRAFTING = SERVER_BUILDER
                .define("enable_handcrafting", true);

        HANDCRAFTING_FILTER = SERVER_BUILDER
                .defineListAllowEmpty("handcraft_filter",
                        List.of(),
                        () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name",
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("interactions");

        ENABLE_FILTER_INTERACTIONS = SERVER_BUILDER
                .define("enable_filter_interactions", true);

        FILTER_INTERACTIONS = SERVER_BUILDER
                .defineListAllowEmpty("filter_interactions",
                        List.of(
                                "createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle",
                                "createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk",
                                "createfood:cloth_filter_cacao_mass|minecraft:bucket|createfood:cloth_filter_pressed_cocoa|createfood:cacao_butter_bucket",
                                "createfood:cloth_filter_pressed_cocoa|none|createfood:cloth_filter|createfood:pressed_cocoa"
                        ),
                        () -> "filter_item|offhand_item|filter_result|container_result",
                        obj -> obj instanceof String s && s.split("\\|").length == 4
                );

        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("food");

        ITEM_NUTRITION_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("nutrition_saturation",
                        List.of(),
                        () -> "item_id|nutrition|saturation",
                        obj -> {
                            if (!(obj instanceof String s)) return false;
                            String[] p = s.split("\\|");
                            if (p.length != 3) return false;
                            if (!p[1].equals("-")) {
                                try {
                                    if (Integer.parseInt(p[1]) < 0) return false;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            }
                            if (!p[2].equals("-")) {
                                try {
                                    if (Float.parseFloat(p[2]) < 0f) return false;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            }
                            return true;
                        }
                );

        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("remainders");

        ENABLE_EGG_IMPACT_REMAINDER = SERVER_BUILDER
                .define("enable_egg_impact_remainder", true);

        CRAFTING_REMAINDERS = SERVER_BUILDER
                .defineListAllowEmpty("crafting_remainders",
                        List.of(
                                "minecraft:egg|createfood:eggshell"
                        ),
                        () -> "input_item|remainder_item",
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop();
    }

    // -------------------------------------------------------------------------
    // Helper accessors
    // -------------------------------------------------------------------------
    private static boolean effectIdsMatch(String configId, String itemId) {
        if (configId.equals(itemId)) return true;
        // Allow unqualified vanilla effect names: "strength" ↔ "minecraft:strength"
        if (!configId.contains(":") && itemId.equals("minecraft:" + configId)) return true;
        if (!itemId.contains(":") && configId.equals("minecraft:" + itemId)) return true;
        return false;
    }
    /**
     * Parsed result of a per-item effect override entry.
     *
     * @param duration  replacement duration in ticks (ignored when {@code remove} is true)
     * @param amplifier replacement amplifier (0 = level I; ignored when {@code remove} is true)
     * @param remove    when true the effect is suppressed entirely for this item
     */
    public record ItemEffectOverride(String categoryOrEffectId, int duration, int amplifier, boolean remove) {}

    /**
     * Returns the override for the given {@code (itemId, categoryOrEffectId)} pair,
     * or {@code null} if no override is configured.
     *
     * <p>Matching is done via {@link #effectIdsMatch} so that unqualified names
     * (e.g. {@code "strength"}) correctly find entries whose item Fx stores the
     * fully-qualified ID ({@code "minecraft:strength"}), and vice-versa.
     *
     * <p>The first matching entry wins; entries are checked top-to-bottom.
     */
    @Nullable
    public static ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId) {
        try {
            for (String entry : ITEM_EFFECT_OVERRIDES.get()) {
                String[] p = entry.split("\\|");
                if (p.length < 3) continue;
                if (!p[0].equals(itemId)) continue;
                if (!effectIdsMatch(p[1], categoryOrEffectId)) continue;
                if (p.length == 3 && p[2].equals("remove")) {
                    return new ItemEffectOverride(categoryOrEffectId, 0, 0, true);
                }
                if (p.length == 4) {
                    try {
                        return new ItemEffectOverride(
                                categoryOrEffectId,
                                Integer.parseInt(p[2]),
                                Integer.parseInt(p[3]),
                                false
                        );
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IllegalStateException ignored) {
            // Config not yet loaded — return null and let the default apply.
        }
        return null;
    }

    /**
     * Returns every {@code item_overrides} entry for {@code itemId}, regardless
     * of which category or effect it targets.
     *
     * <p>{@link dev.averageanime.neoforge.item.type.EffectFood} uses this to find
     * config-driven additions — entries whose {@link ItemEffectOverride#categoryOrEffectId}
     * does not resolve to an effect already in the item's built-in definition.
     *
     * <p>The {@code categoryOrEffectId} stored in each returned override preserves
     * the original string from the config so that  can handle it.
     *
     * <p>Returns an empty list when the config is not yet loaded.
     */
    public static List<ItemEffectOverride> getItemOverrideEntries(String itemId) {
        List<ItemEffectOverride> result = new ArrayList<>();
        try {
            for (String entry : ITEM_EFFECT_OVERRIDES.get()) {
                String[] p = entry.split("\\|");
                if (p.length < 3 || !p[0].equals(itemId)) continue;
                if (p.length == 3 && p[2].equals("remove")) {
                    result.add(new ItemEffectOverride(p[1], 0, 0, true));
                } else if (p.length == 4) {
                    try {
                        result.add(new ItemEffectOverride(
                                p[1],
                                Integer.parseInt(p[2]),
                                Integer.parseInt(p[3]),
                                false
                        ));
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IllegalStateException ignored) {
            // Config not yet loaded — return empty list.
        }
        return result;
    }

    /**
     * Parsed result of a per-item nutrition/saturation override entry.
     *
     * <p>Either field may be  ({@code -1} / {@code -1f}) to indicate
     * that the item's built-in default should be used for that field.
     *
     * @param nutrition   replacement hunger points, or {@link #KEEP_INT} if unset
     * @param saturation  replacement saturation modifier, or {@link #KEEP_FLOAT} if unset
     */
    public record ItemNutritionOverride(int nutrition, float saturation) {
        /** Sentinel meaning "keep the item's built-in nutrition value". */
        public static final int   KEEP_INT   = -1;
        /** Sentinel meaning "keep the item's built-in saturation value". */
        public static final float KEEP_FLOAT = -1f;

        public boolean hasNutritionOverride()   { return nutrition   != KEEP_INT;   }
        public boolean hasSaturationOverride()  { return saturation  != KEEP_FLOAT; }
    }

    /**
     * Returns the nutrition/saturation override for {@code itemId}, or {@code null}
     * if no entry is configured. Either field of the returned record may be a
     * {@link ItemNutritionOverride#KEEP_INT} / {@link ItemNutritionOverride#KEEP_FLOAT}
     * sentinel indicating that the item's built-in default should be used.
     *
     * <p>The first matching entry wins; entries are checked top-to-bottom.
     */
    @Nullable
    public static ItemNutritionOverride getItemNutritionOverride(String itemId) {
        try {
            for (String entry : ITEM_NUTRITION_OVERRIDES.get()) {
                String[] p = entry.split("\\|");
                if (p.length != 3 || !p[0].equals(itemId)) continue;
                int nutrition   = p[1].equals("-") ? ItemNutritionOverride.KEEP_INT
                        : Integer.parseInt(p[1]);
                float saturation = p[2].equals("-") ? ItemNutritionOverride.KEEP_FLOAT
                        : Float.parseFloat(p[2]);
                return new ItemNutritionOverride(nutrition, saturation);
            }
        } catch (IllegalStateException ignored) {
            // Config not yet loaded — return null and use the item's built-in values.
        }
        return null;
    }

    /**
     * Returns the effect ID override for a named category, or {@code null} if none
     * is configured. Used by {@code FoodEffect.resolve()} as its first lookup step.
     */
    @Nullable
    public static String getCategoryEffectOverride(String categoryName) {
        try {
            for (String entry : CATEGORY_EFFECT_OVERRIDES.get()) {
                String[] p = entry.split("\\|");
                if (p.length == 2 && p[0].equals(categoryName)) return p[1];
            }
        } catch (IllegalStateException ignored) {}
        return null;
    }

    // -------------------------------------------------------------------------
    // Existing helpers (unchanged)
    // -------------------------------------------------------------------------

    public static boolean isItemEnabled(String itemId) {
        try {
            List<? extends String> disabledItems = DISABLE_ITEMS.get();
            return !disabledItems.contains(itemId);
        } catch (IllegalStateException e) {
            return true;
        }
    }

    public static boolean isDisplayBlockEnabled(String blockId) {
        if (!isItemEnabled(blockId)) {
            return false;
        }

        String baseItemId = extractBaseItemId(blockId);
        return isItemEnabled(baseItemId);
    }

    private static String extractBaseItemId(String blockId) {
        return blockId
                .replace("_small_plate_block", "")
                .replace("_plate_block", "")
                .replace("_block", "");
    }

    public static class ConfigScreen implements IConfigScreenFactory {
        @Override
        public @NotNull Screen createScreen(@NotNull ModContainer modContainer, @NotNull Screen parent) {
            return new ConfigurationScreen(modContainer, parent);
        }
    }
}