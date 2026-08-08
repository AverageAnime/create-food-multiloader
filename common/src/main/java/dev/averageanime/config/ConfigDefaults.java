package dev.averageanime.config;

import net.minecraft.resources.ResourceLocation;

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

    public static final List<String> CUSTOM_BLOCK_DEFAULT = List.of(
            "ube_cake_base|cake_base",
            "ube_cream_ube_cake|cake|createfood:ube_cream_ube_cake_slice"
    );

    public static final List<String> CUSTOM_FLUID_DEFAULT = List.of(
            "coffee_toffee",
            "coffee_toffee_fudge",
            "ube_cake_batter|2|4",
            "ube_cream_frosting"
    );

    public static final List<String> CUSTOM_ITEM_DEFAULT = List.of(
            "beef_bun_peanut_butter|food|6|0.9",
            "beef_bun_peanut_butter_bacon|food|8|0.9",
            "bread_slice_peanut_butter|food|7|0.4",
            "caramel_popcorn|food|3|0.8",
            "cinnamon_sweet_roll_base|food|4|0.6",
            "coffee_toffee|food|1|2.0",
            "coffee_toffee_fudge|food|4|0.7",
            "corn_flour|plain",
            "dark_chocolate_dried_coffee_beans|food|4|0.5",
            "dragon_bun|food|9|0.4",
            "dragon_bun_crimson_fungus|food|9|0.5",
            "dragon_bun_warped_fungus|food|9|0.6",
            "dragon_burger|food|11|0.4",
            "dragon_burger_crimson_fungus|food|11|0.5",
            "dragon_burger_warped_fungus|food|11|0.6",
            "dragon_patty|food|7|0.6",
            "dried_coffee_beans|plain",
            "chocolate_dried_coffee_beans|food|3|0.6",
            "eggplant_bun|food|5|0.9",
            "eggplant_bun_cheese|food|8|0.9",
            "eggplant_bun_cheese_lettuce|food|10|0.9",
            "eggplant_bun_cheese_lettuce_tomato|food|10|1.0",
            "eggplant_bun_cheese_tomato|food|10|0.9",
            "eggplant_bun_lettuce|food|7|1.0",
            "eggplant_bun_lettuce_tomato|food|7|1.1",
            "eggplant_bun_tomato|food|7|1.0",
            "eggplant_burger|food|7|0.9",
            "eggplant_burger_lettuce|food|9|1.0",
            "eggplant_burger_tomato|food|9|1.0",
            "eggplant_cheeseburger|food|10|0.9",
            "eggplant_cheeseburger_lettuce|food|12|0.9",
            "eggplant_cheeseburger_lettuce_tomato|food|12|1.0",
            "eggplant_cheeseburger_tomato|food|12|0.9",
            "endermite_meatball|food|4|0.6",
            "endermite_meatball_sandwich|food|10|0.4",
            "endermite_meatball_stick_1|stick|5|0.6",
            "endermite_meatball_stick_2|stick|6|0.6",
            "endermite_meatball_stick_3|stick|8|0.7",
            "espresso_powder|plain",
            "ground_endermite|plain",
            "hamburger_peanut_butter|food|8|0.8",
            "hamburger_peanut_butter_bacon|food|10|0.8",
            "marshmallow_coffee_toffee_fudge|food|4|0.8",
            "minced_dragon|plain",
            "pasta_plate_eggplant|bowl|7|0.8",
            "pasta_plate_endermite_meatballs|bowl|8|0.6",
            "pasta_plate_endermite_meatballs_tomato_sauce|bowl|12|0.8",
            "pasta_plate_strider_meatballs|bowl|8|0.6",
            "pasta_plate_strider_meatballs_tomato_sauce|bowl|12|0.8",
            "peanut_butter_apple_jam_sandwich|food|8|0.8",
            "peanut_butter_chorus_fruit_jam_sandwich|food|11|0.6",
            "peanut_butter_melon_jam_sandwich|food|9|0.9",
            "pumpkin_pie_slice|food|3|1.0",
            "raw_cinnamon_sweet_roll_base|plain",
            "raw_endermite_meatball|plain",
            "raw_flesh_cookie|plain",
            "raw_ginger_cookie|plain",
            "raw_green_tea_cookie|plain",
            "raw_soul_berry_cookie|plain",
            "raw_spider_eye_cookie|plain",
            "raw_strider_meatball|plain",
            "raw_sugar_cookie|plain",
            "raw_ube_cookie|plain",
            "small_endermite_meatballs|food|3|0.7",
            "small_strider_meatballs|food|3|0.7",
            "strider_meatball|food|4|0.6",
            "strider_meatball_sandwich|food|10|0.4",
            "strider_meatball_stick_1|stick|5|0.6",
            "strider_meatball_stick_2|stick|6|0.6",
            "strider_meatball_stick_3|stick|8|0.7",
            "tortilla_chip_bowl|bowl|4|0.7",
            "ube_cream_frosting_bottle|bottle|5|0.7",
            "ube_cream_frosting_piping_bag|piping_bag",
            "ube_cream_ube_cake_slice|food|3|0.3",
            "ube_cream_ube_cupcake|fast_food|3|1.0",
            "ube_cupcake_base|fast_food|2|0.9",
            "ube_sugar_dough|plain",
            "white_chocolate_dried_coffee_beans|food|2|0.7"
    );

    public static final List<String> CUSTOM_DISPLAY_BLOCK_DEFAULT = List.of(
            "create:bar_of_chocolate|plate|6",
            "create:builders_tea|bottle|1|10|true",
            "create:sweet_roll|plate|4",
            "farmersdelight:pumpkin_pie_slice|small_plate|1",
            "minecraft:cake|plate|1",
            "minecraft:pumpkin_pie|plate|1",
            "createfood:cinnamon_sweet_roll_base|plate|4",
            "createfood:coffee_toffee_fudge|plate|2",
            "createfood:dragon_burger_crimson_fungus|plate|1",
            "createfood:dragon_burger_warped_fungus|plate|1",
            "createfood:dragon_burger|plate|1",
            "createfood:eggplant_burger_lettuce|plate|1",
            "createfood:eggplant_burger_tomato|plate|1",
            "createfood:eggplant_burger|plate|1",
            "createfood:eggplant_cheeseburger_lettuce_tomato|plate|1",
            "createfood:eggplant_cheeseburger_lettuce|plate|1",
            "createfood:eggplant_cheeseburger_tomato|plate|1",
            "createfood:eggplant_cheeseburger|plate|1",
            "createfood:endermite_meatball_sandwich|plate|2",
            "createfood:endermite_meatball_stick_3|plate|3",
            "createfood:espresso_powder|display_bowl|2",
            "createfood:hamburger_peanut_butter_bacon|plate|1",
            "createfood:hamburger_peanut_butter|plate|1",
            "createfood:marshmallow_coffee_toffee_fudge|plate|2",
            "createfood:pasta_plate_eggplant|plate_food|1",
            "createfood:pasta_plate_endermite_meatballs_tomato_sauce|plate_food|1",
            "createfood:pasta_plate_endermite_meatballs|plate_food|1",
            "createfood:pasta_plate_strider_meatballs_tomato_sauce|plate_food|1",
            "createfood:pasta_plate_strider_meatballs|plate_food|1",
            "createfood:peanut_butter_apple_jam_sandwich|plate|1",
            "createfood:peanut_butter_chorus_fruit_jam_sandwich|plate|1",
            "createfood:peanut_butter_melon_jam_sandwich|plate|1",
            "createfood:strider_meatball_sandwich|plate|2",
            "createfood:strider_meatball_stick_3|plate|3",
            "createfood:tortilla_chip_bowl|bowl|1|4",
            "createfood:ube_cake_base|plate|1",
            "createfood:ube_cream_frosting_bottle|bottle|1|12",
            "createfood:ube_cream_ube_cake_slice|small_plate|1",
            "createfood:ube_cream_ube_cake|plate|1",
            "createfood:ube_cream_ube_cupcake|plate|4",
            "createfood:ube_cupcake_base|plate|4"
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
            "hearthandharvest:peanut_butter_and_jelly_sandwich|peanut_butter,jam",
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
            "veggiesdelight:zucchini_sandwich|zucchini,lettuce,tomato",
            "createfood:beef_bun_peanut_butter_bacon|peanut_butter,bacon|peanut_butter",
            "createfood:beef_bun_peanut_butter|peanut_butter|peanut_butter",
            "createfood:bread_slice_peanut_butter|peanut_butter|peanut_butter",
            "createfood:caramel_popcorn|caramel|corn",
            "createfood:chocolate_dried_coffee_beans|chocolate|coffee",
            "createfood:cinnamon_sweet_roll_base||cinnamon",
            "createfood:coffee_toffee_fudge||coffee",
            "createfood:coffee_toffee||coffee",
            "createfood:corn_flour||corn",
            "createfood:dark_chocolate_dried_coffee_beans|dark_chocolate|coffee",
            "createfood:dragon_bun_crimson_fungus|crimson_fungus|dragon_meat",
            "createfood:dragon_bun_warped_fungus|warped_fungus|dragon_meat",
            "createfood:dragon_bun||dragon_meat",
            "createfood:dragon_burger_crimson_fungus|crimson_fungus|dragon_meat",
            "createfood:dragon_burger_warped_fungus|warped_fungus|dragon_meat",
            "createfood:dragon_burger||dragon_meat",
            "createfood:dragon_patty||dragon_meat",
            "createfood:dried_coffee_beans||coffee",
            "createfood:eggplant_bun_cheese_lettuce_tomato|cheese,lettuce,tomato|eggplant",
            "createfood:eggplant_bun_cheese_lettuce|cheese,lettuce|eggplant",
            "createfood:eggplant_bun_cheese_tomato|cheese,tomato|eggplant",
            "createfood:eggplant_bun_cheese|cheese|eggplant",
            "createfood:eggplant_bun_lettuce_tomato|lettuce|eggplant",
            "createfood:eggplant_bun_lettuce|lettuce,tomato|eggplant",
            "createfood:eggplant_bun_tomato|tomato|eggplant",
            "createfood:eggplant_bun||eggplant",
            "createfood:eggplant_burger_lettuce|lettuce|eggplant",
            "createfood:eggplant_burger_tomato|tomato|eggplant",
            "createfood:eggplant_burger||eggplant",
            "createfood:eggplant_cheeseburger_lettuce_tomato|lettuce,tomato|eggplant",
            "createfood:eggplant_cheeseburger_lettuce|lettuce|eggplant",
            "createfood:eggplant_cheeseburger_tomato|tomato|eggplant",
            "createfood:eggplant_cheeseburger||eggplant",
            "createfood:endermite_meatball_sandwich|endermite_meatballs|endermite_meat",
            "createfood:endermite_meatball_stick_1||endermite_meat",
            "createfood:endermite_meatball_stick_2||endermite_meat",
            "createfood:endermite_meatball_stick_3||endermite_meat",
            "createfood:endermite_meatball||endermite_meat",
            "createfood:espresso_powder||coffee",
            "createfood:ground_endermite||endermite_meat",
            "createfood:hamburger_peanut_butter_bacon|peanut_butter,bacon|peanut_butter",
            "createfood:hamburger_peanut_butter|peanut_butter|peanut_butter",
            "createfood:marshmallow_coffee_toffee_fudge|marshmallow|coffee",
            "createfood:minced_dragon||dragon_meat",
            "createfood:pasta_plate_eggplant|eggplant|eggplant",
            "createfood:pasta_plate_endermite_meatballs_tomato_sauce|tomato_sauce,endermite_meatballs|endermite_meat",
            "createfood:pasta_plate_endermite_meatballs|endermite_meatballs|endermite_meat",
            "createfood:pasta_plate_strider_meatballs_tomato_sauce|tomato_sauce,strider_meatballs|strider_meat",
            "createfood:pasta_plate_strider_meatballs|strider_meatballs|strider_meat",
            "createfood:peanut_butter_apple_jam_sandwich|peanut_butter,apple_jam|peanut_butter",
            "createfood:peanut_butter_chorus_fruit_jam_sandwich|peanut_butter,chorus_fruit_jam|peanut_butter",
            "createfood:peanut_butter_melon_jam_sandwich|peanut_butter,melon_jam|peanut_butter",
            "createfood:raw_cinnamon_sweet_roll_base||cinnamon",
            "createfood:raw_endermite_meatball||endermite_meat",
            "createfood:raw_flesh_cookie|flesh|raw_flesh_cookie",
            "createfood:raw_ginger_cookie|ginger|raw_ginger_cookie",
            "createfood:raw_green_tea_cookie||raw_green_tea_cookie",
            "createfood:raw_soul_berry_cookie|soul_berry|raw_flesh_cookie",
            "createfood:raw_spider_eye_cookie|spider_eye|raw_flesh_cookie",
            "createfood:raw_strider_meatball||strider_meat",
            "createfood:raw_sugar_cookie||raw_sugar_cookie",
            "createfood:raw_ube_cookie||ube",
            "createfood:small_endermite_meatballs||endermite_meat",
            "createfood:small_strider_meatballs||strider_meat",
            "createfood:strider_meatball_sandwich|strider_meatballs|strider_meat",
            "createfood:strider_meatball_stick_1||strider_meat",
            "createfood:strider_meatball_stick_2||strider_meat",
            "createfood:strider_meatball_stick_3||strider_meat",
            "createfood:strider_meatball||strider_meat",
            "createfood:tortilla_chip_bowl||corn",
            "createfood:ube_cake_base||ube",
            "createfood:ube_cake_batter_bucket||ube",
            "createfood:ube_cream_frosting_bottle||ube",
            "createfood:ube_cream_frosting_bucket||ube",
            "createfood:ube_cream_frosting_piping_bag|ube_cream_frosting|ube",
            "createfood:ube_cream_ube_cake_slice|ube_cream_frosting|ube",
            "createfood:ube_cream_ube_cake|ube_cream_frosting|ube",
            "createfood:ube_cream_ube_cupcake|ube_cream_frosting|ube",
            "createfood:ube_cupcake_base||ube",
            "createfood:ube_sugar_dough||ube",
            "createfood:white_chocolate_dried_coffee_beans|white_chocolate|coffee"
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

    public static final List<String> ITEM_EFFECT_OVERRIDES_DEFAULT = List.of(
            "caramel_popcorn|minecraft:luck|600|0",
            "caramel_popcorn|minecraft:strength|600|0",
            "chocolate_dried_coffee_beans|comfort|1200|0",
            "chocolate_dried_coffee_beans|minecraft:haste|600|0",
            "coffee_toffee_fudge|comfort|600|0",
            "coffee_toffee_fudge|lightning|1200|0",
            "coffee_toffee_fudge|minecraft:haste|600|0",
            "coffee_toffee_fudge|sugar_rush|1200|0",
            "coffee_toffee|comfort|600|0",
            "coffee_toffee|lightning|600|0",
            "coffee_toffee|minecraft:haste|600|0",
            "dark_chocolate_dried_coffee_beans|comfort|1200|0",
            "dark_chocolate_dried_coffee_beans|minecraft:haste|600|0",
            "dragon_burger_crimson_fungus|explosion|1200|0",
            "dragon_burger_crimson_fungus|minecraft:absorption|6000|0",
            "dragon_burger_crimson_fungus|minecraft:fire_resistance|1200|0",
            "dragon_burger_crimson_fungus|nourishment|3600|0",
            "dragon_burger_crimson_fungus|satiated_shield|1200|0",
            "dragon_burger_warped_fungus|explosion|1200|0",
            "dragon_burger_warped_fungus|minecraft:absorption|6000|0",
            "dragon_burger_warped_fungus|minecraft:jump_boost|1200|0",
            "dragon_burger_warped_fungus|minecraft:slow_falling|1200|0",
            "dragon_burger_warped_fungus|nourishment|3600|0",
            "dragon_burger_warped_fungus|satiated_shield|1200|0",
            "dragon_burger|explosion|1200|0",
            "dragon_burger|minecraft:absorption|1200|0",
            "dragon_burger|nourishment|1200|0",
            "eggplant_burger_lettuce|nourishment|3600|0",
            "eggplant_burger_tomato|nourishment|3600|0",
            "eggplant_burger|nourishment|1200|0",
            "eggplant_cheeseburger_lettuce_tomato|nourishment|6000|0",
            "eggplant_cheeseburger_lettuce|nourishment|6000|0",
            "eggplant_cheeseburger_tomato|nourishment|6000|0",
            "eggplant_cheeseburger|nourishment|3600|0",
            "endermite_meatball_sandwich|minecraft:regeneration|1200|0",
            "endermite_meatball_sandwich|nourishment|1200|0",
            "endermite_meatball_sandwich|rest|1200|0",
            "endermite_meatball_stick_1|minecraft:regeneration|1200|0",
            "endermite_meatball_stick_1|nourishment|600|0",
            "endermite_meatball_stick_1|rest|1200|0",
            "endermite_meatball_stick_2|minecraft:regeneration|3600|0",
            "endermite_meatball_stick_2|nourishment|1200|0",
            "endermite_meatball_stick_2|rest|1200|0",
            "endermite_meatball_stick_3|minecraft:regeneration|6000|0",
            "endermite_meatball_stick_3|nourishment|3600|0",
            "endermite_meatball_stick_3|rest|4800|0",
            "hamburger_peanut_butter_bacon|nourishment|600|0",
            "hamburger_peanut_butter|nourishment|600|0",
            "marshmallow_coffee_toffee_fudge|comfort|600|0",
            "marshmallow_coffee_toffee_fudge|minecraft:haste|600|0",
            "marshmallow_coffee_toffee_fudge|sugar_rush|1200|0",
            "pasta_plate_eggplant|nourishment|1200|0",
            "pasta_plate_eggplant|satiation|1200|0",
            "pasta_plate_endermite_meatballs_tomato_sauce|nourishment|3600|0",
            "pasta_plate_endermite_meatballs_tomato_sauce|rest|6000|0",
            "pasta_plate_endermite_meatballs_tomato_sauce|satiation|1200|0",
            "pasta_plate_endermite_meatballs|nourishment|1200|0",
            "pasta_plate_endermite_meatballs|rest|4800|0",
            "pasta_plate_endermite_meatballs|satiation|1200|0",
            "pasta_plate_strider_meatballs_tomato_sauce|nourishment|3600|0",
            "pasta_plate_strider_meatballs_tomato_sauce|satiation|1200|0",
            "pasta_plate_strider_meatballs|nourishment|1200|0",
            "pasta_plate_strider_meatballs|satiation|1200|0",
            "peanut_butter_apple_jam_sandwich|comfort|600|0",
            "peanut_butter_apple_jam_sandwich|nourishment|1200|0",
            "peanut_butter_chorus_fruit_jam_sandwich|comfort|600|0",
            "peanut_butter_chorus_fruit_jam_sandwich|nourishment|1200|0",
            "peanut_butter_melon_jam_sandwich|comfort|600|0",
            "peanut_butter_melon_jam_sandwich|nourishment|1200|0",
            "small_endermite_meatballs|minecraft:regeneration|600|0",
            "small_endermite_meatballs|nourishment|600|0",
            "small_endermite_meatballs|rest|600|0",
            "small_strider_meatballs|minecraft:fire_resistance|600|0",
            "small_strider_meatballs|nourishment|600|0",
            "strider_meatball_sandwich|minecraft:fire_resistance|1200|0",
            "strider_meatball_sandwich|nourishment|1200|0",
            "strider_meatball_stick_1|minecraft:fire_resistance|1200|0",
            "strider_meatball_stick_1|nourishment|600|0",
            "strider_meatball_stick_2|minecraft:fire_resistance|3600|0",
            "strider_meatball_stick_2|nourishment|1200|0",
            "strider_meatball_stick_3|minecraft:fire_resistance|6000|0",
            "strider_meatball_stick_3|nourishment|3600|0",
            "ube_cream_ube_cupcake|comfort|600|0",
            "white_chocolate_dried_coffee_beans|comfort|1200|0",
            "white_chocolate_dried_coffee_beans|minecraft:haste|600|0"
    );

    public static final Predicate<Object> CATEGORY_EFFECT_OVERRIDE_VALIDATOR =
            obj -> obj instanceof String s && s.split("\\|").length == 2;

    public static final Predicate<Object> CUSTOM_BLOCK_VALIDATOR = obj -> {
        if (!(obj instanceof String s)) return false;
        String[] p = s.split("\\|");
        if (p.length < 2) return false;
        Set<String> unsliced = Set.of("cake_base", "raw_pie", "raw_pizza", "gelatin");
        Set<String> sliced   = Set.of("cake", "pie", "pizza", "waffle", "cheese", "gyro_meat");
        String type = p[1].toLowerCase();
        if (unsliced.contains(type)) return p.length == 2;
        if (sliced.contains(type)) return p.length == 3 && !p[2].isBlank();
        return false;
    };

    public static final Predicate<Object> CUSTOM_DISPLAY_BLOCK_VALIDATOR = obj -> {
        if (!(obj instanceof String s)) return false;
        String[] p = s.split("\\|");
        if (p.length < 3 || p.length > 5) return false;
        Set<String> validTypes = Set.of("plate", "small_plate", "bottle", "bowl", "display_bowl", "salad_bowl", "small_bowl", "plate_food");
        String type = p[1].toLowerCase();
        if (!validTypes.contains(type)) return false;
        try { Integer.parseInt(p[2]); } catch (NumberFormatException e) { return false; }
        if (p.length >= 4) {
            try { Double.parseDouble(p[3]); } catch (NumberFormatException e) { return false; }
        }
        if (p.length == 5) {
            // "true"/"false", or a particle id. Only the two types that carry a particle
            // supplier accept the field at all.
            if (!type.equals("bottle") && !type.equals("bowl")) return false;
            boolean bool = p[4].equalsIgnoreCase("true") || p[4].equalsIgnoreCase("false");
            if (!bool && ResourceLocation.tryParse(p[4]) == null) return false;
        }
        return true;
    };

    public static final Predicate<Object> CUSTOM_FLUID_VALIDATOR = obj -> {
        if (!(obj instanceof String s)) return false;
        String[] p = s.split("\\|");
        if (p.length == 1) return !p[0].isBlank();
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
        if (type.equals("plain_cr")) return p.length == 3 && !p[2].isBlank();
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
