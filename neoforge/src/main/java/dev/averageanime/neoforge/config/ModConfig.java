package dev.averageanime.neoforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import java.util.List;

public class ModConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLE_ITEMS;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue REQUIRE_SHIFT_FOR_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue SHOW_COMPATIBILITY;
    public static final ModConfigSpec.BooleanValue SHOW_INGREDIENTS;

    static {
        DISABLE_ITEMS = BUILDER
                .defineListAllowEmpty("disable_items",
                        List.of(),
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
                                "minecraft:cake|cream_frosting,berry",
                                "minecraft:cookie|chocolate_chips",
                                "create:chocolate_glazed_berries|chocolate",
                                "create:honeyed_apple|honey",
                                "create:sweet_roll|cream_frosting",
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
                                "delightfulcreators:incomplete_chicken_sandwich|chicken",
                                "delightfulcreators:incomplete_squid_ink_pasta|squid_ink",
                                "delightfulcreators:incomplete_pasta_with_meatballs|beef_meatballs",
                                "delightfulcreators:incomplete_pasta_with_mutton_chop|mutton",
                                "expandeddelight:berry_sweet_roll|berry",
                                "expandeddelight:glow_berry_sweet_roll|glow_berry",
                                "expandeddelight:peanut_butter_honey_sandwich|peanut_butter",
                                "expandeddelight:sweet_berry_jelly_sandwich|peanut_butter,berry_jam",
                                "expandeddelight:glow_berry_jelly_sandwich|peanut_butter,glow_berry_jam",
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
                                "displaydelight:ed_plated_berry_sweet_roll|cream_frosting,berry",
                                "displaydelight:ed_plated_glow_berry_sweet_roll|cream_frosting,glow_berry",
                                "displaydelight:ed_plated_peanut_butter_honey_sandwich|peanut_butter",
                                "displaydelight:ed_plated_glow_berry_jelly_sandwich|peanut_butter,glow_berry",
                                "displaydelight:ed_plated_sweet_berry_jelly_sandwich|peanut_butter,berry",
                                "displaydelight:ed_plated_sweet_roll|cream_frosting",
                                "culturaldelights:eggplant_burger|lettuce,tomato",
                                "culturaldelights:avocado_toast|avocado",
                                "culturaldelights:mutton_sandwich|mutton,beetroot,fried_egg",
                                "displaydelight:ctd_plated_avocado_toast|avocado",
                                "displaydelight:ctd_plated_eggplant_burger|lettuce,tomato",
                                "displaydelight:ctd_plated_mutton_sandwich|mutton,fried_egg,beetroot"
                        ),
                        () -> "item_key|ingredients",
                        obj -> obj instanceof String
                );
        BUILDER.pop();
    }

    public static boolean isItemEnabled(String itemId) {
        List<? extends String> disabledItems = DISABLE_ITEMS.get();
        return !disabledItems.contains(itemId);
    }
}