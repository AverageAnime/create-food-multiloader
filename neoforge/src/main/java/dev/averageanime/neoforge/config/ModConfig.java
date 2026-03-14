package dev.averageanime.neoforge.config;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLE_ITEMS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_TOOLTIPS;

    public static final ModConfigSpec.BooleanValue REQUIRE_SHIFT_FOR_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue SHOW_COMPATIBILITY;
    public static final ModConfigSpec.BooleanValue SHOW_INGREDIENTS;

    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_HAND_CRAFTING;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HAND_CRAFTING_FILTER;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CRAFTING_REMAINDERS;
    public static final ModConfigSpec.BooleanValue ENABLE_EGG_IMPACT_REMAINDER;
    public static final ModConfigSpec.BooleanValue ENABLE_FILTER_INTERACTIONS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FILTER_INTERACTIONS;

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
                                "marshmallow_dark_chocolate",
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
                                "displaydelight:plated_mutton_wrap|mutton,onion,lettuce",
                                "displaydelight:small_plated_cake_slice|cream_frosting,berry",
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

        SERVER_BUILDER.push("hand_craft");

        ENABLE_HAND_CRAFTING = SERVER_BUILDER
                .define("enable_hand_crafting", true);

        HAND_CRAFTING_FILTER = SERVER_BUILDER
                .defineListAllowEmpty("hand_craft_filter",
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