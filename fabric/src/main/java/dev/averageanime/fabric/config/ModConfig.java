package dev.averageanime.fabric.config;

import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModConfig {
    public static final ModConfigSpec.Builder BUILDER        = new ModConfigSpec.Builder();
    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue REQUIRE_SHIFT_FOR_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue SHOW_COMPATIBILITY;
    public static final ModConfigSpec.BooleanValue SHOW_INGREDIENTS;
    public static final ModConfigSpec.BooleanValue SHOW_SACK_BLOCK_ICONS;
    public static final ModConfigSpec.BooleanValue SHOW_STORAGE_TOOLTIP_ICONS;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_BLOCK;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_DISPLAY_BLOCK;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_FLUID;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_ITEM;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_TOOLTIPS;

    public static final ModConfigSpec.BooleanValue CLOTH_SACK_ALLOW_FOOD;
    public static final ModConfigSpec.BooleanValue CLOTH_SACK_EAT_FROM_ITEM;
    public static final ModConfigSpec.BooleanValue CLOTH_SACK_INVENTORY_ENABLED;
    public static final ModConfigSpec.BooleanValue CLOTH_SACK_STACK;
    public static final ModConfigSpec.BooleanValue ENABLE_EGG_IMPACT_REMAINDER;
    public static final ModConfigSpec.BooleanValue ENABLE_FILTER_INTERACTIONS;
    public static final ModConfigSpec.BooleanValue ENABLE_HANDCRAFTING;
    public static final ModConfigSpec.BooleanValue ENABLE_PUMPKIN_PIE_PLACEMENT;
    public static final ModConfigSpec.BooleanValue RATION_BOX_ALLOW_FOOD;
    public static final ModConfigSpec.BooleanValue RATION_BOX_EAT_FROM_ITEM;
    public static final ModConfigSpec.BooleanValue RATION_BOX_INVENTORY_ENABLED;
    public static final ModConfigSpec.BooleanValue RATION_BOX_STACK;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CATEGORY_EFFECT_OVERRIDES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CLOTH_SACK_EXCLUDE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CLOTH_SACK_FILTER;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CRAFTING_REMAINDERS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HIDE_ITEMS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FILTER_INTERACTIONS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HANDCRAFTING_EXCLUDE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HANDCRAFTING_FILTER;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_EFFECT_OVERRIDES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_NUTRITION_OVERRIDES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> RATION_BOX_EXCLUDE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> RATION_BOX_FILTER;

    public static final ModConfigSpec CLIENT_SPEC;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        BUILDER.push("display");

        CUSTOM_DISPLAY_BLOCK = BUILDER
                .defineListAllowEmpty("display_block",
                        List.of(),
                        obj -> {
                            if (!(obj instanceof String s)) return false;
                            String[] p = s.split("\\|");
                            if (p.length != 3) return false;
                            Set<String> validTypes = Set.of("plate", "small_plate", "bottle", "bowl", "salad_bowl");
                            if (!validTypes.contains(p[1].toLowerCase())) return false;
                            try { Integer.parseInt(p[2]); return true; }
                            catch (NumberFormatException e) { return false; }
                        }
                );

        BUILDER.pop();

        BUILDER.push("items");

        HIDE_ITEMS = BUILDER
                .defineListAllowEmpty("hide_items",
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
                                "ube_cream_ube_cupcake",
                                "ube_cupcake_base",
                                "ube_sugar_dough"
                        ),
                        obj -> obj instanceof String
                );

        CUSTOM_ITEM = BUILDER
                .defineListAllowEmpty("item",
                        List.of(),
                        obj -> {
                            if (!(obj instanceof String s)) return false;
                            String[] p = s.split("\\|");
                            if (p.length < 2) return false;
                            Set<String> ingredientTypes = Set.of("plain", "ingredient_bottle", "ingredient_bowl", "piping_bag");
                            Set<String> foodTypes = Set.of("food", "fast_food", "bowl", "bowl_cr", "bottle", "stick", "stick_cr");
                            String type = p[1].toLowerCase();
                            if (ingredientTypes.contains(type)) return p.length == 2;
                            if (!foodTypes.contains(type)) return false;
                            if (p.length < 4) return false;
                            try { Integer.parseInt(p[2]); Float.parseFloat(p[3]); return true; }
                            catch (NumberFormatException e) { return false; }
                        }
                );

        BUILDER.push("tooltips");


        SHOW_COMPATIBILITY = BUILDER
                .define("show_compatibility", true);

        SHOW_INGREDIENTS = BUILDER
                .define("show_ingredients", true);

        REQUIRE_SHIFT_FOR_TOOLTIPS = BUILDER
                .define("require_shift", false);

        CUSTOM_TOOLTIPS = BUILDER
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
                                "farmersdelight:dumplings|protein,onion",
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
                        obj -> obj instanceof String
                );

        BUILDER.pop(); // items.tooltips
        BUILDER.pop(); // items

        BUILDER.push("blocks");

        CUSTOM_BLOCK = BUILDER
                .defineListAllowEmpty("block",
                        List.of(),
                        obj -> {
                            if (!(obj instanceof String s)) return false;
                            String[] p = s.split("\\|");
                            if (p.length < 2) return false;
                            Set<String> unsliced = Set.of("raw_pie", "raw_pizza", "gelatin");
                            Set<String> sliced = Set.of("cake", "pie", "pizza", "waffle");
                            String type = p[1].toLowerCase();
                            if (unsliced.contains(type)) return p.length == 2;
                            if (sliced.contains(type)) return p.length == 3 && !p[2].isBlank();
                            return false;
                        }
                );

        BUILDER.push("storage");

        SHOW_STORAGE_TOOLTIP_ICONS = BUILDER
                .define("show_tooltip_icons", true);

        SHOW_SACK_BLOCK_ICONS = BUILDER
                .define("show_sack_block_icons", true);

        BUILDER.pop(); // blocks.storage
        BUILDER.pop(); // blocks

        BUILDER.push("fluids");

        CUSTOM_FLUID = BUILDER
                .defineListAllowEmpty("fluid",
                        List.of(),
                        obj -> {
                            if (!(obj instanceof String s)) return false;
                            String[] p = s.split("\\|");
                            if (p.length != 3) return false;
                            try { Integer.parseInt(p[1]); Integer.parseInt(p[2]); return true; }
                            catch (NumberFormatException e) { return false; }
                        }
                );

        BUILDER.pop(); // fluids

        CLIENT_SPEC = BUILDER.build();

        SERVER_BUILDER.push("items");

        ITEM_NUTRITION_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("nutrition_saturation",
                        List.of(),
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

        SERVER_BUILDER.push("effects");

        CATEGORY_EFFECT_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("category_overrides",
                        List.of(),
                        obj -> obj instanceof String s && s.split("\\|").length == 2
                );

        ITEM_EFFECT_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("item_overrides",
                        List.of(),
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

        SERVER_BUILDER.pop(); // items.effects

        SERVER_BUILDER.push("remainders");

        ENABLE_EGG_IMPACT_REMAINDER = SERVER_BUILDER
                .define("enable_egg_impact_remainder", true);

        CRAFTING_REMAINDERS = SERVER_BUILDER
                .defineListAllowEmpty("crafting_remainders",
                        List.of(
                                "minecraft:egg|createfood:eggshell"
                        ),
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop(); // items.remainders
        SERVER_BUILDER.pop(); // items

        SERVER_BUILDER.push("interactions");

        ENABLE_FILTER_INTERACTIONS = SERVER_BUILDER
                .define("enable_filter_interactions", true);

        ENABLE_PUMPKIN_PIE_PLACEMENT = SERVER_BUILDER
                .define("enable_pumpkin_pie_placement", false);

        FILTER_INTERACTIONS = SERVER_BUILDER
                .defineListAllowEmpty("filter_interactions",
                        List.of(
                                "createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle",
                                "createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk",
                                "createfood:cloth_filter_cacao_mass|minecraft:bucket|createfood:cloth_filter_pressed_cocoa|createfood:cacao_butter_bucket",
                                "createfood:cloth_filter_pressed_cocoa|none|createfood:cloth_filter|createfood:pressed_cocoa"
                        ),
                        obj -> obj instanceof String s && s.split("\\|").length == 4
                );

        SERVER_BUILDER.push("handcraft");

        ENABLE_HANDCRAFTING = SERVER_BUILDER
                .define("enable_handcrafting", true);

        HANDCRAFTING_FILTER = SERVER_BUILDER
                .defineListAllowEmpty("filter",
                        List.of(),
                        obj -> obj instanceof String
                );

        HANDCRAFTING_EXCLUDE = SERVER_BUILDER
                .defineListAllowEmpty("exclude",
                        List.of(),
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop(); // interactions.handcraft
        SERVER_BUILDER.pop(); // interactions

        SERVER_BUILDER.push("storage");
        SERVER_BUILDER.push("cloth_sack");

        CLOTH_SACK_INVENTORY_ENABLED = SERVER_BUILDER
                .define("cloth_sack_inventory", true);

        CLOTH_SACK_EAT_FROM_ITEM = SERVER_BUILDER
                .define("cloth_sack_eat_from_item", false);

        CLOTH_SACK_ALLOW_FOOD = SERVER_BUILDER
                .define("cloth_sack_allow_food", false);

        CLOTH_SACK_STACK = SERVER_BUILDER
                .define("cloth_sack_stack", true);

        CLOTH_SACK_FILTER = SERVER_BUILDER
                .defineListAllowEmpty("cloth_sack_filter",
                        List.of(),
                        obj -> obj instanceof String
                );

        CLOTH_SACK_EXCLUDE = SERVER_BUILDER
                .defineListAllowEmpty("cloth_sack_exclude",
                        List.of(
                                "item:createfood:cloth_sack"
                        ),
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop();
        SERVER_BUILDER.push("ration_box");

        RATION_BOX_INVENTORY_ENABLED = SERVER_BUILDER
                .define("ration_box_inventory", true);

        RATION_BOX_EAT_FROM_ITEM = SERVER_BUILDER
                .define("ration_box_eat_from_item", true);

        RATION_BOX_ALLOW_FOOD = SERVER_BUILDER
                .define("ration_box_allow_food", true);

        RATION_BOX_STACK = SERVER_BUILDER
                .define("ration_box_stack", false);

        RATION_BOX_FILTER = SERVER_BUILDER
                .defineListAllowEmpty("ration_box_filter",
                        List.of(),
                        obj -> obj instanceof String
                );

        RATION_BOX_EXCLUDE = SERVER_BUILDER
                .defineListAllowEmpty("ration_box_exclude",
                        List.of(),
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop();
        SERVER_BUILDER.pop(); // storage

        SERVER_SPEC = SERVER_BUILDER.build();
    }

    private static boolean effectIdsMatch(String configId, String itemId) {
        if (configId.equals(itemId)) return true;
        if (!configId.contains(":") && itemId.equals("minecraft:" + configId)) return true;
        return !itemId.contains(":") && configId.equals("minecraft:" + itemId);
    }

    public record ItemEffectOverride(String categoryOrEffectId, int duration, int amplifier, boolean remove) {}

    @Nullable
    public static ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId) {
        try {
            for (String entry : ITEM_EFFECT_OVERRIDES.get()) {
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
        } catch (IllegalStateException ignored) {}
        return null;
    }

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
                        result.add(new ItemEffectOverride(p[1],
                                Integer.parseInt(p[2]), Integer.parseInt(p[3]), false));
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IllegalStateException ignored) {}
        return result;
    }

    public record ItemNutritionOverride(int nutrition, float saturation) {
        public static final int   KEEP_INT   = -1;
        public static final float KEEP_FLOAT = -1f;

        public boolean hasNutritionOverride()  { return nutrition   != KEEP_INT;   }
        public boolean hasSaturationOverride() { return saturation  != KEEP_FLOAT; }
    }

    @Nullable
    public static ItemNutritionOverride getItemNutritionOverride(String itemId) {
        try {
            for (String entry : ITEM_NUTRITION_OVERRIDES.get()) {
                String[] p = entry.split("\\|");
                if (p.length != 3 || !p[0].equals(itemId)) continue;
                int   nutrition   = p[1].equals("-") ? ItemNutritionOverride.KEEP_INT   : Integer.parseInt(p[1]);
                float saturation  = p[2].equals("-") ? ItemNutritionOverride.KEEP_FLOAT : Float.parseFloat(p[2]);
                return new ItemNutritionOverride(nutrition, saturation);
            }
        } catch (IllegalStateException ignored) {}
        return null;
    }

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

    public static boolean isItemEnabled(String itemId) {
        try {
            return !HIDE_ITEMS.get().contains(itemId);
        } catch (IllegalStateException ignored) {
            return true;
        }
    }

    public static boolean isDisplayBlockEnabled(String blockId) {
        if (!isItemEnabled(blockId)) return false;
        String base = blockId
                .replace("_small_plate_block", "")
                .replace("_plate_block", "")
                .replace("_block", "");
        return isItemEnabled(base);
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

    public static boolean isClothSackItemAllowed(ItemStack stack) {
        try {
            if (matchesFilterList(stack, CLOTH_SACK_EXCLUDE.get())) return false;
            boolean isFood     = stack.has(DataComponents.FOOD);
            boolean allowFood  = CLOTH_SACK_ALLOW_FOOD.get();
            if (allowFood && isFood) return true;
            List<? extends String> filter = CLOTH_SACK_FILTER.get();
            if (!filter.isEmpty()) return matchesFilterList(stack, filter);
            return !allowFood;
        } catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isRationBoxItemAllowed(ItemStack stack) {
        try {
            if (matchesFilterList(stack, RATION_BOX_EXCLUDE.get())) return false;
            boolean isFood    = stack.has(DataComponents.FOOD);
            boolean allowFood = RATION_BOX_ALLOW_FOOD.get();
            if (allowFood && isFood) return true;
            List<? extends String> filter = RATION_BOX_FILTER.get();
            if (!filter.isEmpty()) return matchesFilterList(stack, filter);
            return !allowFood;
        } catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isClothSackInventoryEnabled()   { try { return CLOTH_SACK_INVENTORY_ENABLED.get();  } catch (IllegalStateException e) { return true;  } }
    public static boolean isClothSackEatFromItem()        { try { return CLOTH_SACK_EAT_FROM_ITEM.get();      } catch (IllegalStateException e) { return false; } }
    public static boolean isClothSackStacking()           { try { return CLOTH_SACK_STACK.get();              } catch (IllegalStateException e) { return true;  } }
    public static boolean isRationBoxInventoryEnabled()   { try { return RATION_BOX_INVENTORY_ENABLED.get();  } catch (IllegalStateException e) { return true;  } }
    public static boolean isRationBoxEatFromItemEnabled() { try { return RATION_BOX_EAT_FROM_ITEM.get();      } catch (IllegalStateException e) { return true;  } }
    public static boolean isRationBoxStacking()           { try { return RATION_BOX_STACK.get();              } catch (IllegalStateException e) { return false; } }
    public static boolean isStorageTooltipIconsEnabled()  { try { return SHOW_STORAGE_TOOLTIP_ICONS.get();    } catch (IllegalStateException e) { return true;  } }
    public static boolean isSackBlockIconsEnabled()       { try { return SHOW_SACK_BLOCK_ICONS.get();         } catch (IllegalStateException e) { return true;  } }
    public static boolean isCompatibilityEnabled()        { try { return SHOW_COMPATIBILITY.get();            } catch (IllegalStateException e) { return true;  } }
    public static boolean isIngredientsEnabled()          { try { return SHOW_INGREDIENTS.get();              } catch (IllegalStateException e) { return true;  } }
    public static boolean isShiftRequiredForTooltips()    { try { return REQUIRE_SHIFT_FOR_TOOLTIPS.get();    } catch (IllegalStateException e) { return false; } }
}