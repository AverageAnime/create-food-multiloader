package dev.averageanime.config;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.createfood.lib.config.RegistryCondition;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public final class ConfigDefaults {
    private ConfigDefaults() {}

    public static final List<String> CRAFTING_REMAINDERS_DEFAULT = List.of(
            "minecraft:egg|createfood:eggshell"
    );

    public static final List<String> CUSTOM_BLOCK_DEFAULT = List.of();

    public static final List<String> CUSTOM_FLUID_DEFAULT = List.of();

        public static final List<String> CUSTOM_ITEM_DEFAULT = List.of();

        public static final List<String> CUSTOM_DISPLAY_BLOCK_DEFAULT = List.of(
            "create:bar_of_chocolate|plate|6",
            "create:builders_tea|bottle|1|10|true",
            "create:sweet_roll|plate|4",
            "farmersdelight:pumpkin_pie_slice|small_plate|1",
            "minecraft:cake|plate|1",
            "minecraft:pumpkin_pie|plate|1"
    );

        public static final List<String> CUSTOM_TOOLTIPS_DEFAULT = List.of(
            "brewinandchewin:cheesy_pasta|cheese",
            "brewinandchewin:ham_and_cheese_sandwich|ham,cheese",
            "create:chocolate_glazed_berries|chocolate",
            "create:honeyed_apple|honey",
            "create:sweet_roll|cream_frosting",
            "create_bic_bit:frikandel_sandwich|frikandel",
            "create_bic_bit:ketchup_topped_frikandel_sandwich|frikandel,ketchup",
            "create_bic_bit:ketchup_topped_kroket_sandwich|kroket,ketchup",
            "create_bic_bit:kroket_sandwich|kroket",
            "create_bic_bit:mayonnaise_ketchup_topped_frikandel_sandwich|frikandel,mayonnaise,ketchup",
            "create_bic_bit:mayonnaise_ketchup_topped_kroket_sandwich|kroket,mayonnaise,ketchup",
            "create_bic_bit:mayonnaise_topped_frikandel_sandwich|frikandel,mayonnaise",
            "create_bic_bit:mayonnaise_topped_kroket_sandwich|kroket,mayonnaise",
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
            "culturaldelights:calamari_roll|calamari,rice,kelp",
            "culturaldelights:chicken_roll_slice|chicken,beetroot",
            "culturaldelights:chicken_roll|chicken,beetroot",
            "culturaldelights:chicken_taco|chicken,cucumber,corn,tomato",
            "culturaldelights:egg_roll|egg,rice,kelp",
            "culturaldelights:eggplant_burger|lettuce,tomato",
            "culturaldelights:fish_taco|fish,lettuce,tomato",
            "culturaldelights:fried_eggplant_pasta|eggplant",
            "culturaldelights:midori_roll_slice|avocado,cucumber",
            "culturaldelights:midori_roll|avocado,cucumber",
            "culturaldelights:mutton_sandwich|mutton,beetroot,fried_egg",
            "culturaldelights:pork_wrap|pork,apple,lettuce",
            "culturaldelights:rice_ball|berry,rice,kelp",
            "culturaldelights:spicy_curry|chicken,rice,tomato_sauce,onion",
            "culturaldelights:tropical_roll|rice,kelp",
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
            "expandeddelight:asparagus_mushroom_pasta|asparagus,mushroom",
            "expandeddelight:berry_sweet_roll|berry",
            "expandeddelight:cheese_sandwich|cheese",
            "expandeddelight:chili_pepper_salmon|fish,rice,chili_pepper,onion",
            "expandeddelight:chocolate_cookie|chocolate_chips",
            "expandeddelight:cranberry_goat_cheese_toast|goat_cheese,cranberry",
            "expandeddelight:glow_berry_jelly_sandwich|peanut_butter,glow_berry_jam",
            "expandeddelight:glow_berry_sweet_roll|glow_berry",
            "expandeddelight:mac_and_cheese|cheese",
            "expandeddelight:peanut_butter_honey_sandwich|peanut_butter",
            "expandeddelight:peanut_butter_sandwich|peanut_butter",
            "expandeddelight:sugar_cookie|sugar",
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
            "farmersdelight:mushroom_rice|mushroom,rice",
            "farmersdelight:mutton_wrap|mutton,onion,lettuce",
            "farmersdelight:pasta_with_meatballs|tomato_sauce,beef_meatballs",
            "farmersdelight:pasta_with_mutton_chop|tomato_sauce,mutton",
            "farmersdelight:roasted_mutton_chops|mutton,rice,beetroot,tomato",
            "farmersdelight:squid_ink_pasta|squid_ink,fish",
            "farmersdelight:steak_and_potatoes|beef,rice,potato,onion",
            "farmersdelight:stuffed_potato|beef,potato",
            "farmersdelight:sweet_berry_cookie|berry",
            "farmersrespite:green_tea_cookie|green_tea",
            "farmersrespite:tea_curry|chicken,rice,tea,lettuce,onion",
            "frightsdelight:cookie_cobweb|cobweb",
            "frightsdelight:cookie_flesh|rotten_flesh",
            "frightsdelight:cookie_ghast_tear|ghast_tear",
            "frightsdelight:cookie_slimeapple|slime_apple",
            "frightsdelight:cookie_slime|slime",
            "frightsdelight:cookie_soul_berry|soul_berry",
            "frightsdelight:cookie_spidereye|spider_eye",
            "frightsdelight:cookie_wither_berry|wither_berry",
            "frightsdelight:pasta_with_slimeballs|slime",
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
            "kaleidoscope_cookery:braised_beef_rice_bowl|beef,rice",
            "kaleidoscope_cookery:braised_beef_with_potatoes|beef,potato",
            "kaleidoscope_cookery:braised_fish_rice_bowl|fish,rice",
            "kaleidoscope_cookery:fish_flavored_shredded_pork_rice_bowl|pork,mushroom,rice",
            "kaleidoscope_cookery:scramble_egg_with_tomatoes_rice_bowl|egg,tomato,rice",
            "kaleidoscope_cookery:spicy_chicken_rice_bowl|chicken,rice",
            "kaleidoscope_cookery:stir_fried_pork_with_peppers_rice_bowl|pork,rice",
            "minecraft:cake|cream_frosting,berry",
            "minecraft:cookie|chocolate_chips",
            "moredelight:creamy_pasta_with_chicken_cuts|chicken,alfredo_sauce",
            "moredelight:creamy_pasta_with_ham|pork,alfredo_sauce",
            "mynethersdelight:spicy_curry|beef,rice",
            "ratatouille_fried_delights:chocolate_donut|chocolate",
            "ratatouille_fried_delights:creamy_donut|cream_frosting",
            "rusticdelight:bell_pepper_pasta|bell_pepper",
            "rusticdelight:cherry_blossom_cookie|cherry_blossom",
            "rusticdelight:coffee_cookie|coffee",
            "rusticdelight:syrup_cookie|syrup",
            "rusticdelight:syrup_sandwich|syrup",
            "ubesdelight:cookie_ginger|ginger",
            "ubesdelight:cookie_ube|ube",
            "veggiesdelight:chicken_fajitas_wrap|chicken,onion,bell_pepper",
            "veggiesdelight:fish_and_chips|fish,potato",
            "veggiesdelight:garlic_rice_with_cauliflower|rice,garlic,cauliflower",
            "veggiesdelight:pasta_with_broccoli|broccoli",
            "veggiesdelight:steak_and_broccoli|beef,rice,broccoli",
            "veggiesdelight:sweet_potato_cupcake|sweet_potato",
            "veggiesdelight:turnip_cake|turnip,rice,corn",
            "veggiesdelight:vegetables_wrap|onion,vegetable,rice",
            "veggiesdelight:vegetarian_burger|cabbage,tomato",
            "veggiesdelight:zucchini_sandwich|zucchini,lettuce,tomato"
    );

    public static final List<String> DISPLAY_INTERACTIONS_EXCLUDE_DEFAULT = List.of(
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

        public static final List<String> HIDE_ITEMS_DEFAULT = List.of();

        public static final Predicate<Object> CATEGORY_EFFECT_OVERRIDE_VALIDATOR =
            obj -> obj instanceof String s && s.split("\\|").length == 2;

    /** The {@code |} fields of a registration line, with any trailing {@code @} clause removed. */
    private static String @Nullable [] fields(Object obj) {
        if (!(obj instanceof String s)) return null;
        RegistryCondition.Split split = RegistryCondition.split(s, CreateFoodCommon.LOGGER);
        if (split == null) return null; // Unreadable clause: reject rather than register unconditionally.
        return split.payload().split("\\|");
    }

    public static final Predicate<Object> CUSTOM_BLOCK_VALIDATOR = obj -> {
        String[] p = fields(obj);
        if (p == null) return false;
        if (p.length < 2) return false;
        Set<String> unsliced = Set.of("cake_base", "raw_pie", "raw_pizza", "gelatin");
        Set<String> sliced   = Set.of("cake", "pie", "pizza", "waffle", "cheese", "gyro_meat");
        String type = p[1].toLowerCase();
        if (unsliced.contains(type)) return p.length == 2;
        if (sliced.contains(type)) return p.length == 3 && !p[2].isBlank();
        return false;
    };

    public static final Predicate<Object> CUSTOM_DISPLAY_BLOCK_VALIDATOR = obj -> {
        String[] p = fields(obj);
        if (p == null) return false;
        if (p.length < 3 || p.length > 5) return false;
        Set<String> validTypes = Set.of("plate", "small_plate", "bottle", "bowl", "display_bowl", "salad_bowl", "large_bowl", "small_bowl", "plate_food");
        String type = p[1].toLowerCase();
        if (!validTypes.contains(type)) return false;
        try { Integer.parseInt(p[2]); } catch (NumberFormatException e) { return false; }
        if (p.length >= 4) {
            try { Double.parseDouble(p[3]); } catch (NumberFormatException e) { return false; }
        }
        if (p.length == 5) {
            // "true"/"false", or a particle id.
            if (!type.equals("bottle") && !type.equals("bowl")) return false;
            boolean bool = p[4].equalsIgnoreCase("true") || p[4].equalsIgnoreCase("false");
            if (!bool && ResourceLocation.tryParse(p[4]) == null) return false;
        }
        return true;
    };

    public static final Predicate<Object> CUSTOM_FLUID_VALIDATOR = obj -> {
        String[] p = fields(obj);
        if (p == null) return false;
        if (p.length == 1) return !p[0].isBlank();
        if (p.length != 3) return false;
        try { Integer.parseInt(p[1]); Integer.parseInt(p[2]); return true; }
        catch (NumberFormatException e) { return false; }
    };

    public static final Predicate<Object> CUSTOM_ITEM_VALIDATOR = obj -> {
        String[] p = fields(obj);
        if (p == null) return false;
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
