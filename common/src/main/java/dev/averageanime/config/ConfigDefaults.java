package dev.averageanime.config;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public final class ConfigDefaults {
    private ConfigDefaults() {}

    // ── Defaults ──────────────────────────────────────────────────────────

    public static final List<String> CRAFTING_REMAINDERS_DEFAULT = List.of(
            "minecraft:egg|createfood:eggshell",
            "createfood:cake_batter_bucket|minecraft:bucket",
            "createfood:chocolate_cake_batter_bucket|minecraft:bucket",
            "createfood:ube_cake_batter_bucket|minecraft:bucket"
    );

    public static final List<String> CUSTOM_DISPLAY_BLOCK_DEFAULT = List.of(
            "create:bar_of_chocolate|plate|6",
            "create:builders_tea|bottle|1|10|true",
            "create:sweet_roll|plate|4",
            "farmersdelight:pumpkin_pie_slice|small_plate|1",
            "minecraft:cake|plate|1",
            "minecraft:pumpkin_pie|plate|1"
    );

    public static final List<String> CUSTOM_TOOLTIPS_DEFAULT = List.of(
            "create:chocolate_glazed_berries|chocolate",
            "create:honeyed_apple|honey",
            "create:sweet_roll|cream_frosting",
            "create_confectionery:black_chocolate_glazed_berries|dark_chocolate",
            "create_confectionery:black_chocolate_glazed_marshmallow|dark_chocolate",
            "create_confectionery:caramel_glazed_berries|caramel",
            "create_confectionery:chocolate_glazed_marshmallow|dark_chocolate",
            "create_confectionery:ruby_chocolate_glazed_berries|ruby_chocolate",
            "create_confectionery:ruby_chocolate_glazed_marshmallow|ruby_chocolate",
            "create_confectionery:soothing_hot_chocolate|marshmallow",
            "create_confectionery:white_chocolate_glazed_berries|white_chocolate",
            "create_confectionery:white_chocolate_glazed_marshmallow|white_chocolate",
            "culturaldelights:avocado_toast|avocado",
            "culturaldelights:beef_burrito|beef,rice,avocado",
            "culturaldelights:chicken_roll_slice|chicken,beetroot",
            "culturaldelights:chicken_roll|chicken,beetroot",
            "culturaldelights:chicken_taco|chicken,cucumber,corn,tomato",
            "culturaldelights:eggplant_burger|lettuce,tomato",
            "culturaldelights:fish_taco|fish,lettuce,tomato",
            "culturaldelights:midori_roll_slice|avocado,cucumber",
            "culturaldelights:midori_roll|avocado,cucumber",
            "culturaldelights:mutton_sandwich|mutton,beetroot,fried_egg",
            "culturaldelights:pork_wrap|pork,apple,lettuce",
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
            "expandeddelight:cheese_sandwich|cheese",
            "expandeddelight:cranberry_goat_cheese_toast|goat_cheese,cranberry",
            "expandeddelight:glow_berry_jelly_sandwich|peanut_butter,glow_berry_jam",
            "expandeddelight:glow_berry_sweet_roll|glow_berry",
            "expandeddelight:mac_and_cheese|cheese",
            "expandeddelight:peanut_butter_honey_sandwich|peanut_butter",
            "expandeddelight:peanut_butter_sandwich|peanut_butter",
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
            "fruitsdelight:bayberry_cookie|bayberry",
            "fruitsdelight:blueberry_muffin|blueberry",
            "fruitsdelight:cranberry_cookie|cranberry",
            "fruitsdelight:cranberry_muffin|cranberry",
            "fruitsdelight:lemon_cookie|lemon",
            "fruitsdelight:persimmon_cookie|persimmon",
            "hearthandharvest:blueberry_muffin|blueberry",
            "hearthandharvest:macaroni_and_cheese|cheese",
            "hearthandharvest:maple_cookie|syrup",
            "hearthandharvest:peanut_butter_and_jelly_sandwich|peanut_butter,jam",
            "hearthandharvest:peanut_butter_cookie|peanut_butter",
            "hearthandharvest:raspberry_scone|raspberry",
            "hearthandharvest:taco|beef,cheese,lettuce,tomato",
            "minecraft:cake|cream_frosting,berry",
            "minecraft:cookie|chocolate_chips",
            "ratatouille_fried_delights:chocolate_donut|chocolate",
            "ratatouille_fried_delights:creamy_donut|cream_frosting",
            "rusticdelight:syrup_sandwich|syrup",
            "veggiesdelight:chicken_fajitas_wrap|chicken,onion,bell_pepper",
            "veggiesdelight:vegetables_wrap|onion,vegetable,rice",
            "veggiesdelight:vegetarian_burger|cabbage,tomato",
            "veggiesdelight:zucchini_sandwich|zucchini,lettuce,tomato"
    );

    public static final List<String> DIPPING_EXCLUDE_DEFAULT = List.of(
    );

    public static final List<String> FILTER_INTERACTIONS_DEFAULT = List.of(
            "createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle",
            "createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk",
            "createfood:cloth_filter_cacao_mass|minecraft:bucket|createfood:cloth_filter_pressed_cocoa|createfood:cacao_butter_bucket",
            "createfood:cloth_filter_pressed_cocoa|none|createfood:cloth_filter|createfood:pressed_cocoa"
    );

    public static final List<String> GENERIC_DISPLAY_EXCLUDE_DEFAULT = List.of(
            "tag:c:tools"
    );

    public static final List<String> HANDCRAFTING_EXCLUDE_DEFAULT = List.of(
            "item:createfood:egg_yolk",
            "tag:minecraft:enchantable/durability",
            "tag:minecraft:enchantable/vanishing",
            "tag:c:tools"
    );

    public static final List<String> HIDE_ITEMS_DEFAULT = List.of(
            "beef_bun_peanut_butter",
            "beef_bun_peanut_butter_bacon",
            "bread_slice_peanut_butter",
            "caramel_popcorn",
            "cinnamon_sweet_roll_base",
            "coffee_toffee",
            "coffee_toffee_fudge",
            "corn_flour",
            "dark_chocolate_dried_coffee_beans",
            "dragon_bun",
            "dragon_bun_crimson_fungus",
            "dragon_bun_warped_fungus",
            "dragon_burger",
            "dragon_burger_crimson_fungus",
            "dragon_burger_warped_fungus",
            "dragon_patty",
            "dried_coffee_beans",
            "chocolate_dried_coffee_beans",
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
            "pumpkin_pie_slice",
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
            "ube_cake_batter",
            "ube_cream_frosting",
            "ube_cream_frosting_bottle",
            "ube_cream_frosting_piping_bag",
            "ube_cream_ube_cake",
            "ube_cream_ube_cake_slice",
            "ube_cream_ube_cupcake",
            "ube_cupcake_base",
            "ube_sugar_dough",
            "white_chocolate_dried_coffee_beans"
    );

    public static final Predicate<Object> CATEGORY_EFFECT_OVERRIDE_VALIDATOR =
            obj -> obj instanceof String s && s.split("\\|").length == 2;

    public static final Predicate<Object> CUSTOM_BLOCK_VALIDATOR = obj -> {
        if (!(obj instanceof String s)) return false;
        String[] p = s.split("\\|");
        if (p.length < 2) return false;
        Set<String> unsliced = Set.of("raw_pie", "raw_pizza", "gelatin");
        Set<String> sliced   = Set.of("cake", "pie", "pizza", "waffle");
        String type = p[1].toLowerCase();
        if (unsliced.contains(type)) return p.length == 2;
        if (sliced.contains(type)) return p.length == 3 && !p[2].isBlank();
        return false;
    };

    public static final Predicate<Object> CUSTOM_DISPLAY_BLOCK_VALIDATOR = obj -> {
        if (!(obj instanceof String s)) return false;
        String[] p = s.split("\\|");
        if (p.length < 3 || p.length > 5) return false;
        Set<String> validTypes = Set.of("plate", "small_plate", "bottle", "bowl", "display_bowl", "salad_bowl", "small_bowl");
        String type = p[1].toLowerCase();
        if (!validTypes.contains(type)) return false;
        try { Integer.parseInt(p[2]); } catch (NumberFormatException e) { return false; }
        if (p.length >= 4) {
            try { Integer.parseInt(p[3]); } catch (NumberFormatException e) { return false; }
        }
        if (p.length == 5) {
            if (!p[4].equalsIgnoreCase("true") && !p[4].equalsIgnoreCase("false")) return false;
            if (!type.equals("bottle")) return false;
        }
        return true;
    };

    public static final Predicate<Object> CUSTOM_FLUID_VALIDATOR = obj -> {
        if (!(obj instanceof String s)) return false;
        String[] p = s.split("\\|");
        if (p.length != 3) return false;
        try { Integer.parseInt(p[1]); Integer.parseInt(p[2]); return true; }
        catch (NumberFormatException e) { return false; }
    };

    public static final Predicate<Object> CUSTOM_ITEM_VALIDATOR = obj -> {
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
    };

    public static final Predicate<Object> FILTER_INTERACTIONS_VALIDATOR =
            obj -> obj instanceof String s && s.split("\\|").length == 4;

    public static final Predicate<Object> ITEM_EFFECT_OVERRIDE_VALIDATOR = obj -> {
        if (!(obj instanceof String s)) return false;
        String[] p = s.split("\\|");
        if (p.length == 3) return p[2].equals("remove");
        if (p.length == 4 || p.length == 5) {
            try {
                Integer.parseInt(p[2]);
                Integer.parseInt(p[3]);
                if (p.length == 5) {
                    float c = Float.parseFloat(p[4]);
                    if (c < 0.0f || c > 1.0f) return false;
                }
                return true;
            }
            catch (NumberFormatException e) { return false; }
        }
        return false;
    };

    public static final Predicate<Object> ITEM_NUTRITION_OVERRIDE_VALIDATOR = obj -> {
        if (!(obj instanceof String s)) return false;
        String[] p = s.split("\\|");
        if (p.length != 3) return false;
        if (!p[1].equals("-")) {
            try { if (Integer.parseInt(p[1]) < 0) return false; }
            catch (NumberFormatException e) { return false; }
        }
        if (!p[2].equals("-")) {
            try { if (Float.parseFloat(p[2]) < 0f) return false; }
            catch (NumberFormatException e) { return false; }
        }
        return true;
    };
}
