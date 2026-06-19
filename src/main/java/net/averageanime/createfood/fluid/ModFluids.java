package net.averageanime.createfood.fluid;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModFluids {

    /** All fluids keyed by registry id. Used by creative tab and datagen. */
    public static final Map<String, FluidEntry.Type> BY_ID = new LinkedHashMap<>();

    // ── default flow (slope=4, level=3) ──────────────────────────────────────

    public static final FluidEntry.Type APPLE_CREAM_FROSTING          = reg("apple_cream_frosting", 0xddd09a);
    public static final FluidEntry.Type APPLE_ICE_CREAM               = reg("apple_ice_cream", 0xf5f3e2);
    public static final FluidEntry.Type APPLE_JAM                     = flow("apple_jam", 0xddd09a, 2, 4);
    public static final FluidEntry.Type APPLE_JUICE                   = flow("apple_juice", 0xeae1c0, 3, 2);
    public static final FluidEntry.Type APPLE_MILKSHAKE               = flow("apple_milkshake", 0xe6e1b6, 2, 4);
    public static final FluidEntry.Type APPLE_PIE_FILLING             = reg("apple_pie_filling", 0xd7989f);
    public static final FluidEntry.Type BERRY_CREAM_FROSTING          = reg("berry_cream_frosting", 0xdd9a9a);
    public static final FluidEntry.Type BERRY_ICE_CREAM               = reg("berry_ice_cream", 0xf8ebf1);
    public static final FluidEntry.Type BERRY_JAM                     = flow("berry_jam", 0xdd9a9a, 2, 4);
    public static final FluidEntry.Type BERRY_JUICE                   = flow("berry_juice", 0xf7e6e6, 3, 2);
    public static final FluidEntry.Type BERRY_MILKSHAKE               = flow("berry_milkshake", 0xeecddb, 2, 4);
    public static final FluidEntry.Type BERRY_PIE_FILLING             = reg("berry_pie_filling", 0xcca5a3);
    public static final FluidEntry.Type BLACK_GELATIN_MIX             = reg("black_gelatin_mix", 0x36363e);
    public static final FluidEntry.Type BLACKSTRAP_MOLASSES           = reg("blackstrap_molasses", 0x2c1f16);
    public static final FluidEntry.Type BLUE_GELATIN_MIX             = reg("blue_gelatin_mix", 0x89d1e9);
    public static final FluidEntry.Type BROWN_GELATIN_MIX            = reg("brown_gelatin_mix", 0xb5774b);
    public static final FluidEntry.Type BUTTERSCOTCH                  = reg("butterscotch", 0x9f6b37);
    public static final FluidEntry.Type BUTTERSCOTCH_FUDGE           = reg("butterscotch_fudge", 0x9a6a2f);
    public static final FluidEntry.Type CACAO_BUTTER                  = reg("cacao_butter", 0xa6804b);
    public static final FluidEntry.Type CACAO_MASS                    = reg("cacao_mass", 0x704d29);
    public static final FluidEntry.Type CANE_SYRUP                    = reg("cane_syrup", 0xe28003);
    public static final FluidEntry.Type CARAMEL                       = reg("caramel", 0xa24a2c);
    public static final FluidEntry.Type CARAMEL_FUDGE                 = reg("caramel_fudge", 0x8d4034);
    public static final FluidEntry.Type CHEESECAKE_FILLING            = reg("cheesecake_filling", 0xccc6a3);
    public static final FluidEntry.Type CHOCOLATE_CREAM_FROSTING      = reg("chocolate_cream_frosting", 0x7c3500);
    public static final FluidEntry.Type CHOCOLATE_FUDGE               = reg("chocolate_fudge", 0x5c271f);
    public static final FluidEntry.Type CHOCOLATE_ICE_CREAM           = reg("chocolate_ice_cream", 0xc18960);
    public static final FluidEntry.Type CHOCOLATE_MILK                = reg("chocolate_milk", 0xae593a);
    public static final FluidEntry.Type CHOCOLATE_MILKSHAKE           = flow("chocolate_milkshake", 0xb9784a, 2, 4);
    public static final FluidEntry.Type CHORUS_FRUIT_CREAM_FROSTING   = reg("chorus_fruit_cream_frosting", 0x8b34c6);
    public static final FluidEntry.Type CHORUS_FRUIT_ICE_CREAM        = reg("chorus_fruit_ice_cream", 0xc4a2e4);
    public static final FluidEntry.Type CHORUS_FRUIT_JAM              = flow("chorus_fruit_jam", 0x8b34c6, 2, 4);
    public static final FluidEntry.Type CHORUS_FRUIT_JUICE            = flow("chorus_fruit_juice", 0xb57cdc, 3, 2);
    public static final FluidEntry.Type CHORUS_FRUIT_MILKSHAKE        = flow("chorus_fruit_milkshake", 0xb081db, 2, 4);
    public static final FluidEntry.Type CHORUS_FRUIT_PIE_FILLING      = reg("chorus_fruit_pie_filling", 0xc1a3cc);
    public static final FluidEntry.Type COFFEE_TOFFEE                 = reg("coffee_toffee", 0xb73800);
    public static final FluidEntry.Type COFFEE_TOFFEE_FUDGE          = reg("coffee_toffee_fudge", 0x401913);
    public static final FluidEntry.Type CONDENSED_MILK                = reg("condensed_milk", 0xfede91);
    public static final FluidEntry.Type CREAM_CHEESE                  = reg("cream_cheese", 0xfbfad5);
    public static final FluidEntry.Type CREAM_FROSTING                = reg("cream_frosting", 0xcbcbcb);
    public static final FluidEntry.Type CREAM_PIE_FILLING_FROSTING    = reg("cream_pie_filling", 0xaeaa93);
    public static final FluidEntry.Type CYAN_GELATIN_MIX             = reg("cyan_gelatin_mix", 0x24d7d7);
    public static final FluidEntry.Type DARK_CHOCOLATE                = reg("dark_chocolate", 0x50231c);
    public static final FluidEntry.Type DARK_CHOCOLATE_FUDGE         = reg("dark_chocolate_fudge", 0x421a14);
    public static final FluidEntry.Type FRUIT_SMOOTHIE                = reg("fruit_smoothie", 0xdd99a1);
    public static final FluidEntry.Type GELATIN_MIX                   = reg("gelatin_mix", 0xdadada);
    public static final FluidEntry.Type GLOW_BERRY_CREAM_FROSTING     = reg("glow_berry_cream_frosting", 0xd79c43);
    public static final FluidEntry.Type GLOW_BERRY_ICE_CREAM          = reg("glow_berry_ice_cream", 0xe9ddc3);
    public static final FluidEntry.Type GLOW_BERRY_JAM               = flow("glow_berry_jam", 0xd79c43, 2, 4);
    public static final FluidEntry.Type GLOW_BERRY_JUICE             = flow("glow_berry_juice", 0xe6c189, 3, 2);
    public static final FluidEntry.Type GLOW_BERRY_MILKSHAKE         = flow("glow_berry_milkshake", 0xddcaa1, 2, 4);
    public static final FluidEntry.Type GLOW_BERRY_PIE_FILLING       = reg("glow_berry_pie_filling", 0xccbda3);
    public static final FluidEntry.Type GRAY_GELATIN_MIX             = reg("gray_gelatin_mix", 0x7a878c);
    public static final FluidEntry.Type GREEN_GELATIN_MIX            = reg("green_gelatin_mix", 0x84af1f);
    public static final FluidEntry.Type HEAVY_CREAM                   = reg("heavy_cream", 0xe6d8b6);
    public static final FluidEntry.Type HOT_CHOCOLATE                 = reg("hot_chocolate", 0xa04a3a);
    public static final FluidEntry.Type HOT_DARK_CHOCOLATE           = reg("hot_dark_chocolate", 0x522119);
    public static final FluidEntry.Type HOT_WHITE_CHOCOLATE          = reg("hot_white_chocolate", 0xe7b26c);
    public static final FluidEntry.Type ICE_CREAM                     = reg("ice_cream", 0xf8ebc8);
    public static final FluidEntry.Type LIGHT_BLUE_GELATIN_MIX       = reg("light_blue_gelatin_mix", 0xc6e9f4);
    public static final FluidEntry.Type LIGHT_GRAY_GELATIN_MIX       = reg("light_gray_gelatin_mix", 0xc4c4c0);
    public static final FluidEntry.Type LIME_GELATIN_MIX             = reg("lime_gelatin_mix", 0xade661);
    public static final FluidEntry.Type MAGENTA_GELATIN_MIX          = reg("magenta_gelatin_mix", 0xdd95d7);
    public static final FluidEntry.Type MELON_CREAM_FROSTING          = reg("melon_cream_frosting", 0xff7980);
    public static final FluidEntry.Type MELON_ICE_CREAM               = reg("melon_ice_cream", 0xfee5f1);
    public static final FluidEntry.Type MELON_JAM                     = flow("melon_jam", 0xff7980, 2, 4);
    public static final FluidEntry.Type MELON_MILKSHAKE               = flow("melon_milkshake", 0xfebcdd, 2, 4);
    public static final FluidEntry.Type MILKSHAKE                     = flow("milkshake", 0xcfc8b6, 2, 4);
    public static final FluidEntry.Type MOLASSES                      = reg("molasses", 0x6f3917);
    public static final FluidEntry.Type ORANGE_GELATIN_MIX           = reg("orange_gelatin_mix", 0xfbb278);
    public static final FluidEntry.Type PINK_GELATIN_MIX             = reg("pink_gelatin_mix", 0xf7b9cb);
    public static final FluidEntry.Type PUMPKIN_PIE_FILLING           = reg("pumpkin_pie_filling", 0xd27d29);
    public static final FluidEntry.Type PUMPKIN_PUREE                 = reg("pumpkin_puree", 0xd27d29);
    public static final FluidEntry.Type PURPLE_GELATIN_MIX           = reg("purple_gelatin_mix", 0xb572d9);
    public static final FluidEntry.Type RED_GELATIN_MIX              = reg("red_gelatin_mix", 0xeaa39e);
    public static final FluidEntry.Type SLIME                         = reg("slime", 0x68a668);
    public static final FluidEntry.Type SOUR_CREAM                    = reg("sour_cream", 0xd4cfb7);
    public static final FluidEntry.Type SQUID_INK                     = reg("squid_ink", 0x161026);
    public static final FluidEntry.Type SUGAR_CANE_JUICE             = flow("sugar_cane_juice", 0xfefcce, 3, 2);
    public static final FluidEntry.Type TOFFEE                        = reg("toffee", 0xb73800);
    public static final FluidEntry.Type TOFFEE_FUDGE                 = reg("toffee_fudge", 0x401913);
    public static final FluidEntry.Type UBE_CREAM_FROSTING            = reg("ube_cream_frosting", 0x6f45c7);
    public static final FluidEntry.Type VEGETABLE_OIL                 = reg("vegetable_oil", 0xcbcaaa);
    public static final FluidEntry.Type VINEGAR                       = reg("vinegar", 0xdbdcd9);
    public static final FluidEntry.Type WHITE_CHOCOLATE               = reg("white_chocolate", 0xe1b47f);
    public static final FluidEntry.Type WHITE_CHOCOLATE_FUDGE        = reg("white_chocolate_fudge", 0x7d6040);
    public static final FluidEntry.Type YELLOW_GELATIN_MIX           = reg("yellow_gelatin_mix", 0xfee78a);
    public static final FluidEntry.Type YOGURT                        = reg("yogurt", 0xd4cab7);
    public static final FluidEntry.Type WAFFLE_BATTER                 = reg("waffle_batter", 0xcebc92);

    // ── custards / eggs / batter / soups ──────────────────────────────────────

    public static final FluidEntry.Type CUSTARD                = reg("custard", 0xf2c94c);
    public static final FluidEntry.Type APPLE_CUSTARD          = reg("apple_custard", 0xf2d06e);
    public static final FluidEntry.Type BERRY_CUSTARD          = reg("berry_custard", 0xd47ca8);
    public static final FluidEntry.Type CHOCOLATE_CUSTARD      = reg("chocolate_custard", 0x5c271f);
    public static final FluidEntry.Type CHORUS_FRUIT_CUSTARD   = reg("chorus_fruit_custard", 0x9060a0);
    public static final FluidEntry.Type MELON_CUSTARD          = reg("melon_custard", 0xa8c840);
    public static final FluidEntry.Type PUMPKIN_CUSTARD        = reg("pumpkin_custard", 0xe08030);
    public static final FluidEntry.Type MERINGUE               = reg("meringue", 0xf5f0e0);

    // Eggs: lower density (800), lower viscosity (1100) — lighter than water
    public static final FluidEntry.Type EGG                    = phys("egg", 0xf5c842, 800, 1100);
    public static final FluidEntry.Type EGG_WHITES             = phys("egg_whites", 0xf0f0e8, 800, 1100);

    // Batters: slightly denser (1300) and more viscous (1500)
    public static final FluidEntry.Type CAKE_BATTER            = phys("cake_batter", 0xe8d098, 1300, 1500);
    public static final FluidEntry.Type CHOCOLATE_CAKE_BATTER  = phys("chocolate_cake_batter", 0x5c2d1e, 1300, 1500);
    public static final FluidEntry.Type UBE_CAKE_BATTER        = phys("ube_cake_batter", 0x6f45c7, 1300, 1500);
    public static final FluidEntry.Type MUFFIN_BATTER          = phys("muffin_batter", 0xd4a060, 1300, 1500);

    public static final FluidEntry.Type BREAD_PUDDING          = reg("bread_pudding", 0xc89060);
    public static final FluidEntry.Type RICE_PUDDING           = reg("rice_pudding", 0xf0e8d4);
    public static final FluidEntry.Type LIQUID_CHEESE          = reg("liquid_cheese", 0xe07820);

    // Soups/stews: denser and more viscous than water
    public static final FluidEntry.Type FISH_CHOWDER           = phys("fish_chowder", 0xd4b882, 1200, 1200);
    public static final FluidEntry.Type KELP_SOUP              = phys("kelp_soup", 0x68a668, 1200, 1200);
    public static final FluidEntry.Type LEATHER_SOUP           = phys("leather_soup", 0x7d6040, 1200, 1200);
    public static final FluidEntry.Type MUSHROOM_CREAM_SOUP    = phys("mushroom_cream_soup", 0xc4a070, 1200, 1200);
    public static final FluidEntry.Type POTATO_CREAM_SOUP      = phys("potato_cream_soup", 0xe0d4a8, 1200, 1200);
    public static final FluidEntry.Type TOMATO_CREAM_SOUP      = phys("tomato_cream_soup", 0xd06040, 1200, 1200);
    public static final FluidEntry.Type MUTTON_STEW            = phys("mutton_stew", 0x9a5030, 1200, 1200);
    public static final FluidEntry.Type PORK_STEW              = phys("pork_stew", 0x9a3828, 1200, 1200);
    public static final FluidEntry.Type TACO_SAUCE             = phys("taco_sauce", 0xc04020, 1100, 1000);

    // ── helpers ───────────────────────────────────────────────────────────────

    /** Register with default flow and physics params. */
    private static FluidEntry.Type reg(String name, int rgb) {
        FluidEntry.Type t = new FluidEntry(name, rgb).build();
        BY_ID.put(name, t);
        return t;
    }

    /** Register with custom flow params (slopeFindDistance, levelDecreasePerBlock). */
    private static FluidEntry.Type flow(String name, int rgb, int slope, int level) {
        FluidEntry.Type t = new FluidEntry(name, rgb).flow(slope, level).build();
        BY_ID.put(name, t);
        return t;
    }

    /** Register with custom physics params (density, viscosity). */
    private static FluidEntry.Type phys(String name, int rgb, int density, int viscosity) {
        FluidEntry.Type t = new FluidEntry(name, rgb).physics(density, viscosity).build();
        BY_ID.put(name, t);
        return t;
    }

    public static void register() {}
}
