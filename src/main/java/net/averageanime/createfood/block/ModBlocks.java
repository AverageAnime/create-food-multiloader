package net.averageanime.createfood.block;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.cake.*;
import net.averageanime.createfood.block.gyro.GyroMeatBlock;
import net.averageanime.createfood.block.pie.*;
import net.averageanime.createfood.block.pizza.*;
import net.averageanime.createfood.block.waffle.*;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static net.minecraft.world.item.Items.registerBlock;


public class ModBlocks {
    private static final CreateRegistrate REGISTRATE = CreateFood.registrate();

    public static final BlockEntry<GyroMeatBlock> GYRO_MEAT_BLOCK = REGISTRATE.block("gyro_meat_block", GyroMeatBlock::new).item().build().register();

    public static final BlockEntry<RawPizzaBlock> PIZZA_DOUGH = REGISTRATE.block("pizza_dough", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> PIZZA_DOUGH_TOMATO_SAUCE = REGISTRATE.block("pizza_dough_tomato_sauce", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_BACON_PIZZA = REGISTRATE.block("raw_bacon_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_CHEESE_PIZZA = REGISTRATE.block("raw_cheese_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_FISH_BACON_PIZZA = REGISTRATE.block("raw_fish_bacon_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_FISH_ONION_PIZZA = REGISTRATE.block("raw_fish_onion_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_FISH_PIZZA = REGISTRATE.block("raw_fish_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_MUSHROOM_BACON_PIZZA = REGISTRATE.block("raw_mushroom_bacon_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_MUSHROOM_FISH_PIZZA = REGISTRATE.block("raw_mushroom_fish_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_MUSHROOM_ONION_PIZZA = REGISTRATE.block("raw_mushroom_onion_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_MUSHROOM_PIZZA = REGISTRATE.block("raw_mushroom_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_ONION_BACON_PIZZA = REGISTRATE.block("raw_onion_bacon_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_ONION_PIZZA = REGISTRATE.block("raw_onion_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_SAUSAGE_BACON_PIZZA = REGISTRATE.block("raw_sausage_bacon_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_SAUSAGE_FISH_PIZZA = REGISTRATE.block("raw_sausage_fish_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_SAUSAGE_MUSHROOM_PIZZA = REGISTRATE.block("raw_sausage_mushroom_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_SAUSAGE_ONION_PIZZA = REGISTRATE.block("raw_sausage_onion_pizza", RawPizzaBlock::new).item().build().register();
    public static final BlockEntry<RawPizzaBlock> RAW_SAUSAGE_PIZZA = REGISTRATE.block("raw_sausage_pizza", RawPizzaBlock::new).item().build().register();

    public static final BlockEntry<BaconPizzaBlock> BACON_PIZZA = REGISTRATE.block("bacon_pizza", BaconPizzaBlock::new).item().build().register();
    public static final BlockEntry<CheesePizzaBlock> CHEESE_PIZZA = REGISTRATE.block("cheese_pizza", CheesePizzaBlock::new).item().build().register();
    public static final BlockEntry<FishBaconPizzaBlock> FISH_BACON_PIZZA = REGISTRATE.block("fish_bacon_pizza", FishBaconPizzaBlock::new).item().build().register();
    public static final BlockEntry<FishOnionPizzaBlock> FISH_ONION_PIZZA = REGISTRATE.block("fish_onion_pizza", FishOnionPizzaBlock::new).item().build().register();
    public static final BlockEntry<FishPizzaBlock> FISH_PIZZA = REGISTRATE.block("fish_pizza", FishPizzaBlock::new).item().build().register();
    public static final BlockEntry<MushroomBaconPizzaBlock> MUSHROOM_BACON_PIZZA = REGISTRATE.block("mushroom_bacon_pizza", MushroomBaconPizzaBlock::new).item().build().register();
    public static final BlockEntry<MushroomFishPizzaBlock> MUSHROOM_FISH_PIZZA = REGISTRATE.block("mushroom_fish_pizza", MushroomFishPizzaBlock::new).item().build().register();
    public static final BlockEntry<MushroomOnionPizzaBlock> MUSHROOM_ONION_PIZZA = REGISTRATE.block("mushroom_onion_pizza", MushroomOnionPizzaBlock::new).item().build().register();
    public static final BlockEntry<MushroomPizzaBlock> MUSHROOM_PIZZA = REGISTRATE.block("mushroom_pizza", MushroomPizzaBlock::new).item().build().register();
    public static final BlockEntry<OnionBaconPizzaBlock> ONION_BACON_PIZZA = REGISTRATE.block("onion_bacon_pizza", OnionBaconPizzaBlock::new).item().build().register();
    public static final BlockEntry<OnionPizzaBlock> ONION_PIZZA = REGISTRATE.block("onion_pizza", OnionPizzaBlock::new).item().build().register();
    public static final BlockEntry<SausageBaconPizzaBlock> SAUSAGE_BACON_PIZZA = REGISTRATE.block("sausage_bacon_pizza", SausageBaconPizzaBlock::new).item().build().register();
    public static final BlockEntry<SausageFishPizzaBlock> SAUSAGE_FISH_PIZZA = REGISTRATE.block("sausage_fish_pizza", SausageFishPizzaBlock::new).item().build().register();
    public static final BlockEntry<SausageMushroomPizzaBlock> SAUSAGE_MUSHROOM_PIZZA = REGISTRATE.block("sausage_mushroom_pizza", SausageMushroomPizzaBlock::new).item().build().register();
    public static final BlockEntry<SausageOnionPizzaBlock> SAUSAGE_ONION_PIZZA = REGISTRATE.block("sausage_onion_pizza", SausageOnionPizzaBlock::new).item().build().register();
    public static final BlockEntry<SausagePizzaBlock> SAUSAGE_PIZZA = REGISTRATE.block("sausage_pizza", SausagePizzaBlock::new).item().build().register();

    public static final BlockEntry<RawPieBlock> RAW_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = REGISTRATE.block("raw_chocolate_graham_cracker_pie_crust", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_GRAHAM_CRACKER_PIE_CRUST = REGISTRATE.block("raw_graham_cracker_pie_crust", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_PIE_CRUST = REGISTRATE.block("raw_pie_crust", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_APPLE_CHEESECAKE = REGISTRATE.block("raw_apple_cheesecake", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_APPLE_PIE = REGISTRATE.block("raw_apple_pie", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_BERRY_CHEESECAKE = REGISTRATE.block("raw_berry_cheesecake", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_BERRY_PIE = REGISTRATE.block("raw_berry_pie", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_CHEESECAKE = REGISTRATE.block("raw_cheesecake", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_CHOCOLATE_PIE = REGISTRATE.block("raw_chocolate_pie", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_CHOCOLATE_PIE_GRAHAM_CRACKER = REGISTRATE.block("raw_chocolate_pie_graham_cracker", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_CHORUS_FRUIT_CHEESECAKE = REGISTRATE.block("raw_chorus_fruit_cheesecake", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_CHORUS_FRUIT_PIE = REGISTRATE.block("raw_chorus_fruit_pie", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = REGISTRATE.block("raw_cream_pie_chocolate_graham_cracker", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_CREAM_PIE_GRAHAM_CRACKER = REGISTRATE.block("raw_cream_pie_graham_cracker", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_GLOW_BERRY_CHEESECAKE = REGISTRATE.block("raw_glow_berry_cheesecake", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_GLOW_BERRY_PIE = REGISTRATE.block("raw_glow_berry_pie", RawPieBlock::new).item().build().register();
    public static final BlockEntry<RawPieBlock> RAW_PUMPKIN_PIE = REGISTRATE.block("raw_pumpkin_pie", RawPieBlock::new).item().build().register();


    public static final BlockEntry<AppleCheesecakeBlock> APPLE_CHEESECAKE = REGISTRATE.block("apple_cheesecake", AppleCheesecakeBlock::new).item().build().register();
    public static final BlockEntry<BerryPieBlock> BERRY_PIE = REGISTRATE.block("berry_pie", BerryPieBlock::new).item().build().register();
    public static final BlockEntry<CheesecakeBlock> CHEESECAKE = REGISTRATE.block("cheesecake", CheesecakeBlock::new).item().build().register();
    public static final BlockEntry<ChocolatePieGrahamCrackerBlock> CHOCOLATE_PIE_GRAHAM_CRACKER = REGISTRATE.block("chocolate_pie_graham_cracker", ChocolatePieGrahamCrackerBlock::new).item().build().register();
    public static final BlockEntry<ChorusFruitCheesecakeBlock> CHORUS_FRUIT_CHEESECAKE = REGISTRATE.block("chorus_fruit_cheesecake", ChorusFruitCheesecakeBlock::new).item().build().register();
    public static final BlockEntry<ChorusFruitPieBlock> CHORUS_FRUIT_PIE = REGISTRATE.block("chorus_fruit_pie", ChorusFruitPieBlock::new).item().build().register();
    public static final BlockEntry<CookieCreamPieBlock> COOKIE_CREAM_PIE = REGISTRATE.block("cookie_cream_pie", CookieCreamPieBlock::new).item().build().register();
    public static final BlockEntry<CreamPieChocolateGrahamCrackerBlock> CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = REGISTRATE.block("cream_pie_chocolate_graham_cracker", CreamPieChocolateGrahamCrackerBlock::new).item().build().register();
    public static final BlockEntry<CreamPieGrahamCrackerBlock> CREAM_PIE_GRAHAM_CRACKER = REGISTRATE.block("cream_pie_graham_cracker", CreamPieGrahamCrackerBlock::new).item().build().register();
    public static final BlockEntry<GlowBerryCheesecakeBlock> GLOW_BERRY_CHEESECAKE = REGISTRATE.block("glow_berry_cheesecake", GlowBerryCheesecakeBlock::new).item().build().register();
    public static final BlockEntry<GlowBerryPieBlock> GLOW_BERRY_PIE = REGISTRATE.block("glow_berry_pie", GlowBerryPieBlock::new).item().build().register();
    public static final BlockEntry<SmoresPieBlock> SMORES_PIE = REGISTRATE.block("smores_pie", SmoresPieBlock::new).item().build().register();

    public static final BlockEntry<WaffleBlock> WAFFLE = REGISTRATE.block("waffle", WaffleBlock::new).item().build().register();
    public static final BlockEntry<ButterscotchChipWaffleBlock> BUTTERSCOTCH_CHIP_WAFFLE = REGISTRATE.block("butterscotch_chip_waffle", ButterscotchChipWaffleBlock::new).item().build().register();
    public static final BlockEntry<CaramelChipWaffleBlock> CARAMEL_CHIP_WAFFLE = REGISTRATE.block("caramel_chip_waffle", CaramelChipWaffleBlock::new).item().build().register();
    public static final BlockEntry<ChocolateChipWaffleBlock> CHOCOLATE_CHIP_WAFFLE = REGISTRATE.block("chocolate_chip_waffle", ChocolateChipWaffleBlock::new).item().build().register();
    public static final BlockEntry<DarkChocolateChipWaffleBlock> DARK_CHOCOLATE_CHIP_WAFFLE = REGISTRATE.block("dark_chocolate_chip_waffle", DarkChocolateChipWaffleBlock::new).item().build().register();
    public static final BlockEntry<WhiteChocolateChipWaffleBlock> WHITE_CHOCOLATE_CHIP_WAFFLE = REGISTRATE.block("white_chocolate_chip_waffle", WhiteChocolateChipWaffleBlock::new).item().build().register();
    public static final BlockEntry<ToffeeChipWaffleBlock> TOFFEE_CHIP_WAFFLE = REGISTRATE.block("toffee_chip_waffle", ToffeeChipWaffleBlock::new).item().build().register();


    public static final BlockEntry<CakeBaseBlock> CAKE_BASE = REGISTRATE.block("cake_base", CakeBaseBlock::new).item().build().register();
    public static final BlockEntry<CakeBaseBlock> UBE_CAKE_BASE = REGISTRATE.block("ube_cake_base", CakeBaseBlock::new).item().build().register();

    public static final BlockEntry<CakeBaseBlock> BERRY_CREAM_FROSTED_CAKE = REGISTRATE.block("berry_cream_frosted_cake", CakeBaseBlock::new).item().build().register();
    public static final BlockEntry<CakeBaseBlock> CHOCOLATE_CREAM_FROSTED_CAKE = REGISTRATE.block("chocolate_cream_frosted_cake", CakeBaseBlock::new).item().build().register();
    public static final BlockEntry<CakeBaseBlock> CHORUS_FRUIT_CREAM_FROSTED_CAKE = REGISTRATE.block("chorus_fruit_cream_frosted_cake", CakeBaseBlock::new).item().build().register();
    public static final BlockEntry<CakeBaseBlock> FROSTED_CAKE = REGISTRATE.block("frosted_cake", CakeBaseBlock::new).item().build().register();
    public static final BlockEntry<CakeBaseBlock> GLOW_BERRY_CREAM_CREAM_FROSTED_CAKE = REGISTRATE.block("glow_berry_cream_frosted_cake", CakeBaseBlock::new).item().build().register();
    public static final BlockEntry<CakeBaseBlock> UBE_CREAM_FROSTED_UBE_CAKE = REGISTRATE.block("ube_cream_frosted_ube_cake", CakeBaseBlock::new).item().build().register();

    public static final BlockEntry<CakeChorusFruitBlock> CAKE_CHORUS_FRUIT = REGISTRATE.block("cake_chorus_fruit", CakeChorusFruitBlock::new).item().build().register();
    public static final BlockEntry<CakeGlowBerryBlock> CAKE_GLOW_BERRY = REGISTRATE.block("cake_glow_berry", CakeGlowBerryBlock::new).item().build().register();
    public static final BlockEntry<BerryCreamCakeSweetBerryBlock> BERRY_CREAM_CAKE_SWEET_BERRY = REGISTRATE.block("berry_cream_cake_sweet_berry", BerryCreamCakeSweetBerryBlock::new).item().build().register();
    public static final BlockEntry<BerryCreamCakeChorusFruitBlock> BERRY_CREAM_CAKE_CHORUS_FRUIT = REGISTRATE.block("berry_cream_cake_chorus_fruit", BerryCreamCakeChorusFruitBlock::new).item().build().register();
    public static final BlockEntry<BerryCreamCakeGlowBerryBlock> BERRY_CREAM_CAKE_GLOW_BERRY = REGISTRATE.block("berry_cream_cake_glow_berry", BerryCreamCakeGlowBerryBlock::new).item().build().register();
    public static final BlockEntry<ChocolateCreamCakeButterscotchBlock> CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH = REGISTRATE.block("chocolate_cream_cake_butterscotch", ChocolateCreamCakeButterscotchBlock::new).item().build().register();
    public static final BlockEntry<ChocolateCreamCakeCaramelBlock> CHOCOLATE_CREAM_CAKE_CARAMEL = REGISTRATE.block("chocolate_cream_cake_caramel", ChocolateCreamCakeCaramelBlock::new).item().build().register();
    public static final BlockEntry<ChocolateCreamCakeChocolateBlock> CHOCOLATE_CREAM_CAKE_CHOCOLATE = REGISTRATE.block("chocolate_cream_cake_chocolate", ChocolateCreamCakeChocolateBlock::new).item().build().register();
    public static final BlockEntry<ChocolateCreamCakeDarkChocolateBlock> CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE = REGISTRATE.block("chocolate_cream_cake_dark_chocolate", ChocolateCreamCakeDarkChocolateBlock::new).item().build().register();
    public static final BlockEntry<ChocolateCreamCakeToffeeBlock> CHOCOLATE_CREAM_CAKE_TOFFEE = REGISTRATE.block("chocolate_cream_cake_toffee", ChocolateCreamCakeToffeeBlock::new).item().build().register();
    public static final BlockEntry<ChocolateCreamCakeWhiteChocolateBlock> CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE = REGISTRATE.block("chocolate_cream_cake_white_chocolate", ChocolateCreamCakeWhiteChocolateBlock::new).item().build().register();
    public static final BlockEntry<ChorusFruitCreamCakeSweetBerryBlock> CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY = REGISTRATE.block("chorus_fruit_cream_cake_sweet_berry", ChorusFruitCreamCakeSweetBerryBlock::new).item().build().register();
    public static final BlockEntry<ChorusFruitCreamCakeChorusFruitBlock> CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT = REGISTRATE.block("chorus_fruit_cream_cake_chorus_fruit", ChorusFruitCreamCakeChorusFruitBlock::new).item().build().register();
    public static final BlockEntry<ChorusFruitCreamCakeGlowBerryBlock> CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY = REGISTRATE.block("chorus_fruit_cream_cake_glow_berry", ChorusFruitCreamCakeGlowBerryBlock::new).item().build().register();
    public static final BlockEntry<GlowBerryCreamCakeSweetBerryBlock> GLOW_BERRY_CREAM_CREAM_CAKE_SWEET_BERRY = REGISTRATE.block("glow_berry_cream_cake_sweet_berry", GlowBerryCreamCakeSweetBerryBlock::new).item().build().register();
    public static final BlockEntry<GlowBerryCreamCakeChorusFruitBlock> GLOW_BERRY_CREAM_CREAM_CAKE_CHORUS_FRUIT = REGISTRATE.block("glow_berry_cream_cake_chorus_fruit", GlowBerryCreamCakeChorusFruitBlock::new).item().build().register();
    public static final BlockEntry<GlowBerryCreamCakeGlowBerryBlock> GLOW_BERRY_CREAM_CREAM_CAKE_GLOW_BERRY = REGISTRATE.block("glow_berry_cream_cake_glow_berry", GlowBerryCreamCakeGlowBerryBlock::new).item().build().register();

    public static final BlockEntry<SlimeBlock> BLACK_GELATIN_DESSERT_BLOCK = REGISTRATE.block("black_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> BLUE_GELATIN_DESSERT_BLOCK = REGISTRATE.block("blue_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> BROWN_GELATIN_DESSERT_BLOCK = REGISTRATE.block("brown_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> CYAN_GELATIN_DESSERT_BLOCK = REGISTRATE.block("cyan_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> GELATIN_DESSERT_BLOCK = REGISTRATE.block("gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> GRAY_GELATIN_DESSERT_BLOCK = REGISTRATE.block("gray_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> GREEN_GELATIN_DESSERT_BLOCK = REGISTRATE.block("green_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> LIGHT_BLUE_GELATIN_DESSERT_BLOCK = REGISTRATE.block("light_blue_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> LIGHT_GRAY_GELATIN_DESSERT_BLOCK = REGISTRATE.block("light_gray_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> LIME_GELATIN_DESSERT_BLOCK = REGISTRATE.block("lime_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> MAGENTA_GELATIN_DESSERT_BLOCK = REGISTRATE.block("magenta_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> ORANGE_GELATIN_DESSERT_BLOCK = REGISTRATE.block("orange_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> PINK_GELATIN_DESSERT_BLOCK = REGISTRATE.block("pink_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> PURPLE_GELATIN_DESSERT_BLOCK = REGISTRATE.block("purple_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> RED_GELATIN_DESSERT_BLOCK = REGISTRATE.block("red_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();
    public static final BlockEntry<SlimeBlock> YELLOW_GELATIN_DESSERT_BLOCK = REGISTRATE.block("yellow_gelatin_dessert_block", SlimeBlock::new).properties(BlockBehaviour.Properties::noOcclusion).item().build().register();


    public static void register() {}
}
