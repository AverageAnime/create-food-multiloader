package dev.averageanime.registry;

import dev.averageanime.registry.type.Block;

import static dev.averageanime.util.Tooltips.tips;

/**
 * All block definitions. {@link Block#ALL} is auto-populated as this class loads.
 * RATION_BOX and CLOTH_SACK are excluded — they stay platform-specific.
 */
@SuppressWarnings("unused")
public final class BlockRegistry {

    private BlockRegistry() {}

    /**
     * Calling this static method guarantees class initialization (JLS §12.4.1).
     * Use instead of {@code Class<?> t = ModBlockDefs.class} which only loads, not initializes.
     */
    public static void init() { /* triggers static field initialization */ }

    public static final Block APPLE_CHEESECAKE = Block.cookedPie("apple_cheesecake", "apple_cheesecake_slice");
    public static final Block APPLE_CREAM_CAKE = Block.cake("apple_cream_cake", "apple_cream_cake_slice", tips(null, "apple_cream_frosting_ingredient"));
    public static final Block BACON_PIZZA = Block.cookedPizza("bacon_pizza", "bacon_pizza_slice", tips(null, "bacon_ingredient"));
    public static final Block BERRY_CREAM_CAKE = Block.cake("berry_cream_cake", "berry_cream_cake_slice", tips(null, "berry_cream_frosting_ingredient"));
    public static final Block BERRY_CREAM_CAKE_CHORUS_FRUIT = Block.cake("berry_cream_cake_chorus_fruit", "berry_cream_cake_slice_chorus_fruit", tips(null, "berry_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final Block BERRY_CREAM_CAKE_GLOW_BERRY = Block.cake("berry_cream_cake_glow_berry", "berry_cream_cake_slice_glow_berry", tips(null, "berry_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final Block BERRY_CREAM_CAKE_SWEET_BERRY = Block.cake("berry_cream_cake_sweet_berry", "berry_cream_cake_slice_sweet_berry", tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"));
    public static final Block BERRY_PIE = Block.cookedPie("berry_pie", "berry_pie_slice");
    public static final Block BLACK_GELATIN_DESSERT_BLOCK = Block.gelatin("black_gelatin_dessert_block");
    public static final Block BLUE_GELATIN_DESSERT_BLOCK = Block.gelatin("blue_gelatin_dessert_block");
    public static final Block BROWN_GELATIN_DESSERT_BLOCK = Block.gelatin("brown_gelatin_dessert_block");
    public static final Block BUTTERSCOTCH_CHIP_WAFFLE = Block.waffle("butterscotch_chip_waffle", "butterscotch_chip_mini_waffle", tips(null, "butterscotch_chips_ingredient"));
    public static final Block CAKE_BASE = Block.cakeBase("cake_base");
    public static final Block CARAMEL_CHIP_WAFFLE = Block.waffle("caramel_chip_waffle", "caramel_chip_mini_waffle", tips(null, "caramel_chips_ingredient"));
    public static final Block CHEESECAKE = Block.cookedPie("cheesecake", "cheesecake_slice");
    public static final Block CHEESE_BLOCK = Block.cheese("cheese_block", "cheese_slice");
    public static final Block CHEESE_PIZZA = Block.cookedPizza("cheese_pizza", "cheese_pizza_slice", tips(null, "cheese_ingredient"));
    public static final Block CHOCOLATE_CAKE_BASE = Block.cakeBase("chocolate_cake_base");
    public static final Block CHOCOLATE_CHIP_WAFFLE = Block.waffle("chocolate_chip_waffle", "chocolate_chip_mini_waffle", tips(null, "chocolate_chips_ingredient"));
    public static final Block CHOCOLATE_CREAM_CAKE = Block.cake("chocolate_cream_cake", "chocolate_cream_cake_slice", tips(null, "chocolate_cream_frosting_ingredient"));
    public static final Block CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH = Block.cake("chocolate_cream_cake_butterscotch", "chocolate_cream_cake_slice_butterscotch", tips(null, "chocolate_cream_frosting_ingredient", "butterscotch_chips_ingredient"));
    public static final Block CHOCOLATE_CREAM_CAKE_CARAMEL = Block.cake("chocolate_cream_cake_caramel", "chocolate_cream_cake_slice_caramel", tips(null, "chocolate_cream_frosting_ingredient", "caramel_chips_ingredient"));
    public static final Block CHOCOLATE_CREAM_CAKE_CHOCOLATE = Block.cake("chocolate_cream_cake_chocolate", "chocolate_cream_cake_slice_chocolate", tips(null, "chocolate_cream_frosting_ingredient", "chocolate_chips_ingredient"));
    public static final Block CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE = Block.cake("chocolate_cream_cake_dark_chocolate", "chocolate_cream_cake_slice_dark_chocolate", tips(null, "chocolate_cream_frosting_ingredient", "dark_chocolate_chips_ingredient"));
    public static final Block CHOCOLATE_CREAM_CAKE_TOFFEE = Block.cake("chocolate_cream_cake_toffee", "chocolate_cream_cake_slice_toffee", tips(null, "chocolate_cream_frosting_ingredient", "toffee_chips_ingredient"));
    public static final Block CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE = Block.cake("chocolate_cream_cake_white_chocolate", "chocolate_cream_cake_slice_white_chocolate", tips(null, "chocolate_cream_frosting_ingredient", "white_chocolate_chips_ingredient"));
    public static final Block CHOCOLATE_CREAM_CHOCOLATE_CAKE = Block.cake("chocolate_cream_chocolate_cake", "chocolate_cream_chocolate_cake_slice", tips(null, "chocolate_cream_frosting_ingredient"));
    public static final Block CHOCOLATE_PIE_GRAHAM_CRACKER = Block.cookedPie("chocolate_pie_graham_cracker", "chocolate_pie_graham_cracker_slice", tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final Block CHORUS_FRUIT_CHEESECAKE = Block.cookedPie("chorus_fruit_cheesecake", "chorus_fruit_cheesecake_slice");
    public static final Block CHORUS_FRUIT_CREAM_CAKE = Block.cake("chorus_fruit_cream_cake", "chorus_fruit_cream_cake_slice", tips(null, "chorus_fruit_cream_frosting_ingredient"));
    public static final Block CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT = Block.cake("chorus_fruit_cream_cake_chorus_fruit", "chorus_fruit_cream_cake_slice_chorus_fruit", tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final Block CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY = Block.cake("chorus_fruit_cream_cake_glow_berry", "chorus_fruit_cream_cake_slice_glow_berry", tips(null, "chorus_fruit_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final Block CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY = Block.cake("chorus_fruit_cream_cake_sweet_berry", "chorus_fruit_cream_cake_slice_sweet_berry", tips(null, "chorus_fruit_cream_frosting_ingredient", "berry_ingredient"));
    public static final Block CHORUS_FRUIT_PIE = Block.cookedPie("chorus_fruit_pie", "chorus_fruit_pie_slice");
    public static final Block COOKIE_CREAM_PIE = Block.cookedPie("cookie_cream_pie", "cookie_cream_pie_slice");
    public static final Block CREAM_CAKE = Block.cake("cream_cake", "cream_cake_slice", tips(null, "cream_frosting_ingredient"));
    public static final Block CREAM_CAKE_CHORUS_FRUIT = Block.cake("cream_cake_chorus_fruit", "cream_cake_slice_chorus_fruit", tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final Block CREAM_CAKE_GLOW_BERRY = Block.cake("cream_cake_glow_berry", "cream_cake_slice_glow_berry", tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final Block CREAM_CHOCOLATE_CAKE = Block.cake("cream_chocolate_cake", "cream_chocolate_cake_slice", tips(null, "cream_frosting_ingredient"));
    public static final Block CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = Block.cookedPie("cream_pie_chocolate_graham_cracker", "cream_pie_chocolate_graham_cracker_slice", tips(null, "chocolate_graham_cracker_pie_crust_ingredient"));
    public static final Block CREAM_PIE_GRAHAM_CRACKER = Block.cookedPie("cream_pie_graham_cracker", "cream_pie_graham_cracker_slice", tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final Block CYAN_GELATIN_DESSERT_BLOCK = Block.gelatin("cyan_gelatin_dessert_block");
    public static final Block DARK_CHOCOLATE_CHIP_WAFFLE = Block.waffle("dark_chocolate_chip_waffle", "dark_chocolate_chip_mini_waffle", tips(null, "dark_chocolate_chips_ingredient"));
    public static final Block FISH_BACON_PIZZA = Block.cookedPizza("fish_bacon_pizza", "fish_bacon_pizza_slice", tips(null, "fish_ingredient", "bacon_ingredient"));
    public static final Block FISH_ONION_PIZZA = Block.cookedPizza("fish_onion_pizza", "fish_onion_pizza_slice", tips(null, "fish_ingredient", "onion_ingredient"));
    public static final Block FISH_PIZZA = Block.cookedPizza("fish_pizza", "fish_pizza_slice", tips(null, "fish_ingredient"));
    public static final Block GELATIN_DESSERT_BLOCK = Block.gelatin("gelatin_dessert_block");
    public static final Block GLOW_BERRY_CHEESECAKE = Block.cookedPie("glow_berry_cheesecake", "glow_berry_cheesecake_slice");
    public static final Block GLOW_BERRY_CREAM_CAKE = Block.cake("glow_berry_cream_cake", "glow_berry_cream_cake_slice", tips(null, "glow_berry_cream_frosting_ingredient"));
    public static final Block GLOW_BERRY_CREAM_CAKE_CHORUS_FRUIT = Block.cake("glow_berry_cream_cake_chorus_fruit", "glow_berry_cream_cake_slice_chorus_fruit", tips(null, "glow_berry_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final Block GLOW_BERRY_CREAM_CAKE_GLOW_BERRY = Block.cake("glow_berry_cream_cake_glow_berry", "glow_berry_cream_cake_slice_glow_berry", tips(null, "glow_berry_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final Block GLOW_BERRY_CREAM_CAKE_SWEET_BERRY = Block.cake("glow_berry_cream_cake_sweet_berry", "glow_berry_cream_cake_slice_sweet_berry", tips(null, "glow_berry_cream_frosting_ingredient", "berry_ingredient"));
    public static final Block GLOW_BERRY_PIE = Block.cookedPie("glow_berry_pie", "glow_berry_pie_slice");
    public static final Block GRAY_GELATIN_DESSERT_BLOCK = Block.gelatin("gray_gelatin_dessert_block");
    public static final Block GREEN_GELATIN_DESSERT_BLOCK = Block.gelatin("green_gelatin_dessert_block");
    public static final Block GYRO_MEAT_BLOCK = Block.gyroMeat("gyro_meat_block", "gyro_meat_slice");
    public static final Block LIGHT_BLUE_GELATIN_DESSERT_BLOCK = Block.gelatin("light_blue_gelatin_dessert_block");
    public static final Block LIGHT_GRAY_GELATIN_DESSERT_BLOCK = Block.gelatin("light_gray_gelatin_dessert_block");
    public static final Block LIME_GELATIN_DESSERT_BLOCK = Block.gelatin("lime_gelatin_dessert_block");
    public static final Block MAGENTA_GELATIN_DESSERT_BLOCK = Block.gelatin("magenta_gelatin_dessert_block");
    public static final Block MEAT_PIE = Block.cookedPie("meat_pie", "meat_pie_slice");
    public static final Block MELON_CREAM_CAKE = Block.cake("melon_cream_cake", "melon_cream_cake_slice", tips(null, "melon_cream_frosting_ingredient"));
    public static final Block MUSHROOM_BACON_PIZZA = Block.cookedPizza("mushroom_bacon_pizza", "mushroom_bacon_pizza_slice", tips(null, "mushroom_ingredient", "bacon_ingredient"));
    public static final Block MUSHROOM_FISH_PIZZA = Block.cookedPizza("mushroom_fish_pizza", "mushroom_fish_pizza_slice", tips(null, "mushroom_ingredient", "fish_ingredient"));
    public static final Block MUSHROOM_ONION_PIZZA = Block.cookedPizza("mushroom_onion_pizza", "mushroom_onion_pizza_slice", tips(null, "mushroom_ingredient", "onion_ingredient"));
    public static final Block MUSHROOM_PIZZA = Block.cookedPizza("mushroom_pizza", "mushroom_pizza_slice", tips(null, "mushroom_ingredient"));
    public static final Block ONION_BACON_PIZZA = Block.cookedPizza("onion_bacon_pizza", "onion_bacon_pizza_slice", tips(null, "onion_ingredient", "bacon_ingredient"));
    public static final Block ONION_PIZZA = Block.cookedPizza("onion_pizza", "onion_pizza_slice", tips(null, "onion_ingredient"));
    public static final Block ORANGE_GELATIN_DESSERT_BLOCK = Block.gelatin("orange_gelatin_dessert_block");
    public static final Block PINK_GELATIN_DESSERT_BLOCK = Block.gelatin("pink_gelatin_dessert_block");
    public static final Block PIZZA_DOUGH = Block.rawPizza("pizza_dough");
    public static final Block PIZZA_DOUGH_TOMATO_SAUCE = Block.rawPizza("pizza_dough_tomato_sauce", tips(null, "tomato_sauce_ingredient"));
    public static final Block PUMPKIN_PIE_BLOCK = Block.cookedPie("pumpkin_pie_block", "pumpkin_pie_slice");
    public static final Block PURPLE_GELATIN_DESSERT_BLOCK = Block.gelatin("purple_gelatin_dessert_block");
    public static final Block RAW_APPLE_CHEESECAKE = Block.rawPie("raw_apple_cheesecake");
    public static final Block RAW_APPLE_PIE = Block.rawPie("raw_apple_pie");
    public static final Block RAW_BACON_PIZZA = Block.rawPizza("raw_bacon_pizza", tips(null, "bacon_ingredient"));
    public static final Block RAW_BERRY_CHEESECAKE = Block.rawPie("raw_berry_cheesecake");
    public static final Block RAW_BERRY_PIE = Block.rawPie("raw_berry_pie");
    public static final Block RAW_CHEESECAKE = Block.rawPie("raw_cheesecake");
    public static final Block RAW_CHEESE_PIZZA = Block.rawPizza("raw_cheese_pizza", tips(null, "cheese_ingredient"));
    public static final Block RAW_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = Block.rawPie("raw_chocolate_graham_cracker_pie_crust");
    public static final Block RAW_CHOCOLATE_PIE = Block.rawPie("raw_chocolate_pie");
    public static final Block RAW_CHOCOLATE_PIE_GRAHAM_CRACKER = Block.rawPie("raw_chocolate_pie_graham_cracker", tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final Block RAW_CHORUS_FRUIT_CHEESECAKE = Block.rawPie("raw_chorus_fruit_cheesecake");
    public static final Block RAW_CHORUS_FRUIT_PIE = Block.rawPie("raw_chorus_fruit_pie");
    public static final Block RAW_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = Block.rawPie("raw_cream_pie_chocolate_graham_cracker", tips(null, "chocolate_graham_cracker_pie_crust_ingredient"));
    public static final Block RAW_CREAM_PIE_GRAHAM_CRACKER = Block.rawPie("raw_cream_pie_graham_cracker", tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final Block RAW_FISH_BACON_PIZZA = Block.rawPizza("raw_fish_bacon_pizza", tips(null, "fish_ingredient", "bacon_ingredient"));
    public static final Block RAW_FISH_ONION_PIZZA = Block.rawPizza("raw_fish_onion_pizza", tips(null, "fish_ingredient", "onion_ingredient"));
    public static final Block RAW_FISH_PIZZA = Block.rawPizza("raw_fish_pizza", tips(null, "fish_ingredient"));
    public static final Block RAW_GLOW_BERRY_CHEESECAKE = Block.rawPie("raw_glow_berry_cheesecake");
    public static final Block RAW_GLOW_BERRY_PIE = Block.rawPie("raw_glow_berry_pie");
    public static final Block RAW_GRAHAM_CRACKER_PIE_CRUST = Block.rawPie("raw_graham_cracker_pie_crust");
    public static final Block RAW_MEAT_PIE = Block.rawPie("raw_meat_pie");
    public static final Block RAW_MUSHROOM_BACON_PIZZA = Block.rawPizza("raw_mushroom_bacon_pizza", tips(null, "mushroom_ingredient", "bacon_ingredient"));
    public static final Block RAW_MUSHROOM_FISH_PIZZA = Block.rawPizza("raw_mushroom_fish_pizza", tips(null, "mushroom_ingredient", "fish_ingredient"));
    public static final Block RAW_MUSHROOM_ONION_PIZZA = Block.rawPizza("raw_mushroom_onion_pizza", tips(null, "mushroom_ingredient", "onion_ingredient"));
    public static final Block RAW_MUSHROOM_PIZZA = Block.rawPizza("raw_mushroom_pizza", tips(null, "mushroom_ingredient"));
    public static final Block RAW_ONION_BACON_PIZZA = Block.rawPizza("raw_onion_bacon_pizza", tips(null, "onion_ingredient", "bacon_ingredient"));
    public static final Block RAW_ONION_PIZZA = Block.rawPizza("raw_onion_pizza", tips(null, "onion_ingredient"));
    public static final Block RAW_PIE_CRUST = Block.rawPie("raw_pie_crust");
    public static final Block RAW_PUMPKIN_PIE = Block.rawPie("raw_pumpkin_pie");
    public static final Block RAW_SAUSAGE_BACON_PIZZA = Block.rawPizza("raw_sausage_bacon_pizza", tips(null, "sausage_ingredient", "bacon_ingredient"));
    public static final Block RAW_SAUSAGE_FISH_PIZZA = Block.rawPizza("raw_sausage_fish_pizza", tips(null, "sausage_ingredient", "fish_ingredient"));
    public static final Block RAW_SAUSAGE_MUSHROOM_PIZZA = Block.rawPizza("raw_sausage_mushroom_pizza", tips(null, "sausage_ingredient", "mushroom_ingredient"));
    public static final Block RAW_SAUSAGE_ONION_PIZZA = Block.rawPizza("raw_sausage_onion_pizza", tips(null, "sausage_ingredient", "onion_ingredient"));
    public static final Block RAW_SAUSAGE_PIZZA = Block.rawPizza("raw_sausage_pizza", tips(null, "sausage_ingredient"));
    public static final Block RED_GELATIN_DESSERT_BLOCK = Block.gelatin("red_gelatin_dessert_block");
    public static final Block SAUSAGE_BACON_PIZZA = Block.cookedPizza("sausage_bacon_pizza", "sausage_bacon_pizza_slice", tips(null, "sausage_ingredient", "bacon_ingredient"));
    public static final Block SAUSAGE_FISH_PIZZA = Block.cookedPizza("sausage_fish_pizza", "sausage_fish_pizza_slice", tips(null, "sausage_ingredient", "fish_ingredient"));
    public static final Block SAUSAGE_MUSHROOM_PIZZA = Block.cookedPizza("sausage_mushroom_pizza", "sausage_mushroom_pizza_slice", tips(null, "sausage_ingredient", "mushroom_ingredient"));
    public static final Block SAUSAGE_ONION_PIZZA = Block.cookedPizza("sausage_onion_pizza", "sausage_onion_pizza_slice", tips(null, "sausage_ingredient", "onion_ingredient"));
    public static final Block SAUSAGE_PIZZA = Block.cookedPizza("sausage_pizza", "sausage_pizza_slice", tips(null, "sausage_ingredient"));
    public static final Block SMORES_PIE = Block.cookedPie("smores_pie", "smores_pie_slice");
    public static final Block TOFFEE_CHIP_WAFFLE = Block.waffle("toffee_chip_waffle", "toffee_chip_mini_waffle", tips(null, "toffee_chips_ingredient"));
    public static final Block UBE_CAKE_BASE = Block.cakeBase("ube_cake_base", tips("ube"));
    public static final Block UBE_CREAM_UBE_CAKE = Block.cake("ube_cream_ube_cake", "ube_cream_ube_cake_slice", tips("ube", "ube_cream_frosting_ingredient"));
    public static final Block WAFFLE = Block.waffle("waffle", "mini_waffle");
    public static final Block WHITE_CHOCOLATE_CHIP_WAFFLE = Block.waffle("white_chocolate_chip_waffle", "white_chocolate_chip_mini_waffle", tips(null, "white_chocolate_chips_ingredient"));
    public static final Block YELLOW_GELATIN_DESSERT_BLOCK = Block.gelatin("yellow_gelatin_dessert_block");
}
