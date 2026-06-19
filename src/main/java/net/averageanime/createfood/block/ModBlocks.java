package net.averageanime.createfood.block;

import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.cake.*;
import net.averageanime.createfood.block.gyro.GyroMeatBlock;
import net.averageanime.createfood.block.pie.*;
import net.averageanime.createfood.block.pizza.*;
import net.averageanime.createfood.block.storage.ClothSackBlock;
import net.averageanime.createfood.block.storage.RationBoxBlock;
import net.averageanime.createfood.block.waffle.*;
import net.averageanime.createfood.item.storage.ClothSackItem;
import net.averageanime.createfood.item.storage.RationBoxItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateFood.ID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateFood.ID);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> supplier) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    // ── candle cake wiring ────────────────────────────────────────────────────

    private static final List<String> CANDLE_SUFFIXES = List.of(
            "candle", "white_candle", "orange_candle", "magenta_candle", "light_blue_candle",
            "yellow_candle", "lime_candle", "pink_candle", "gray_candle", "light_gray_candle",
            "cyan_candle", "purple_candle", "blue_candle", "brown_candle", "green_candle",
            "red_candle", "black_candle"
    );

    private static final List<Block> CANDLE_BLOCKS = List.of(
            Blocks.CANDLE, Blocks.WHITE_CANDLE, Blocks.ORANGE_CANDLE, Blocks.MAGENTA_CANDLE,
            Blocks.LIGHT_BLUE_CANDLE, Blocks.YELLOW_CANDLE, Blocks.LIME_CANDLE, Blocks.PINK_CANDLE,
            Blocks.GRAY_CANDLE, Blocks.LIGHT_GRAY_CANDLE, Blocks.CYAN_CANDLE, Blocks.PURPLE_CANDLE,
            Blocks.BLUE_CANDLE, Blocks.BROWN_CANDLE, Blocks.GREEN_CANDLE, Blocks.RED_CANDLE,
            Blocks.BLACK_CANDLE
    );

    private static final List<Runnable> CANDLE_WIRINGS = new ArrayList<>();

    public static void wireUpCandleMap() {
        CANDLE_WIRINGS.forEach(Runnable::run);
    }

    private static <T extends ModCakeBlock> void registerCandleCakesFor(String cakeName, RegistryObject<T> cakeEntry) {
        for (int i = 0; i < CANDLE_SUFFIXES.size(); i++) {
            String suffix = CANDLE_SUFFIXES.get(i);
            Block candleBlock = CANDLE_BLOCKS.get(i);
            String blockName = cakeName + "_" + suffix;
            RegistryObject<ModCandleCakeBlock> entry = registerBlock(blockName,
                    () -> new ModCandleCakeBlock(
                            BlockBehaviour.Properties.of()
                                    .lightLevel(s -> s.getValue(ModCandleCakeBlock.LIT) ? 3 : 0),
                            cakeEntry::get, candleBlock, cakeName, suffix));
            final Item finalCandleItem = candleBlock.asItem();
            CANDLE_WIRINGS.add(() -> ModCakeBlock.registerCandleVariant(
                    cakeEntry.get(), finalCandleItem, entry.get()));
        }
    }

    // ── storage blocks (custom item classes) ──────────────────────────────────

    public static final RegistryObject<ClothSackBlock> CLOTH_SACK_BLOCK =
            BLOCKS.register("cloth_sack", () -> new ClothSackBlock(
                    BlockBehaviour.Properties.of().strength(1.5f).noOcclusion()));

    public static final RegistryObject<RationBoxBlock> RATION_BOX_BLOCK =
            BLOCKS.register("ration_box", () -> new RationBoxBlock(
                    BlockBehaviour.Properties.of().strength(2.0f)));

    public static final RegistryObject<Item> CLOTH_SACK_ITEM =
            ITEMS.register("cloth_sack", () -> new ClothSackItem(CLOTH_SACK_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> RATION_BOX_ITEM =
            ITEMS.register("ration_box", () -> new RationBoxItem(RATION_BOX_BLOCK.get(), new Item.Properties()));

    // ── gyro ─────────────────────────────────────────────────────────────────

    public static final RegistryObject<GyroMeatBlock> GYRO_MEAT_BLOCK =
            registerBlock("gyro_meat_block", () -> new GyroMeatBlock(BlockBehaviour.Properties.of()));

    // ── pizza (raw) ───────────────────────────────────────────────────────────

    public static final RegistryObject<RawPizzaBlock> PIZZA_DOUGH                   = registerBlock("pizza_dough",                   () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> PIZZA_DOUGH_TOMATO_SAUCE      = registerBlock("pizza_dough_tomato_sauce",      () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_BACON_PIZZA               = registerBlock("raw_bacon_pizza",               () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_CHEESE_PIZZA              = registerBlock("raw_cheese_pizza",              () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_FISH_BACON_PIZZA          = registerBlock("raw_fish_bacon_pizza",          () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_FISH_ONION_PIZZA          = registerBlock("raw_fish_onion_pizza",          () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_FISH_PIZZA                = registerBlock("raw_fish_pizza",                () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_MUSHROOM_BACON_PIZZA      = registerBlock("raw_mushroom_bacon_pizza",      () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_MUSHROOM_FISH_PIZZA       = registerBlock("raw_mushroom_fish_pizza",       () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_MUSHROOM_ONION_PIZZA      = registerBlock("raw_mushroom_onion_pizza",      () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_MUSHROOM_PIZZA            = registerBlock("raw_mushroom_pizza",            () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_ONION_BACON_PIZZA         = registerBlock("raw_onion_bacon_pizza",         () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_ONION_PIZZA               = registerBlock("raw_onion_pizza",               () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_SAUSAGE_BACON_PIZZA       = registerBlock("raw_sausage_bacon_pizza",       () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_SAUSAGE_FISH_PIZZA        = registerBlock("raw_sausage_fish_pizza",        () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_SAUSAGE_MUSHROOM_PIZZA    = registerBlock("raw_sausage_mushroom_pizza",    () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_SAUSAGE_ONION_PIZZA       = registerBlock("raw_sausage_onion_pizza",       () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPizzaBlock> RAW_SAUSAGE_PIZZA             = registerBlock("raw_sausage_pizza",             () -> new RawPizzaBlock(BlockBehaviour.Properties.of()));

    // ── pizza (cooked) ────────────────────────────────────────────────────────

    public static final RegistryObject<BaconPizzaBlock>           BACON_PIZZA           = registerBlock("bacon_pizza",           () -> new BaconPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CheesePizzaBlock>          CHEESE_PIZZA          = registerBlock("cheese_pizza",          () -> new CheesePizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<FishBaconPizzaBlock>       FISH_BACON_PIZZA      = registerBlock("fish_bacon_pizza",      () -> new FishBaconPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<FishOnionPizzaBlock>       FISH_ONION_PIZZA      = registerBlock("fish_onion_pizza",      () -> new FishOnionPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<FishPizzaBlock>            FISH_PIZZA            = registerBlock("fish_pizza",            () -> new FishPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<MushroomBaconPizzaBlock>   MUSHROOM_BACON_PIZZA  = registerBlock("mushroom_bacon_pizza",  () -> new MushroomBaconPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<MushroomFishPizzaBlock>    MUSHROOM_FISH_PIZZA   = registerBlock("mushroom_fish_pizza",   () -> new MushroomFishPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<MushroomOnionPizzaBlock>   MUSHROOM_ONION_PIZZA  = registerBlock("mushroom_onion_pizza",  () -> new MushroomOnionPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<MushroomPizzaBlock>        MUSHROOM_PIZZA        = registerBlock("mushroom_pizza",        () -> new MushroomPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<OnionBaconPizzaBlock>      ONION_BACON_PIZZA     = registerBlock("onion_bacon_pizza",     () -> new OnionBaconPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<OnionPizzaBlock>           ONION_PIZZA           = registerBlock("onion_pizza",           () -> new OnionPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<SausageBaconPizzaBlock>    SAUSAGE_BACON_PIZZA   = registerBlock("sausage_bacon_pizza",   () -> new SausageBaconPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<SausageFishPizzaBlock>     SAUSAGE_FISH_PIZZA    = registerBlock("sausage_fish_pizza",    () -> new SausageFishPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<SausageMushroomPizzaBlock> SAUSAGE_MUSHROOM_PIZZA = registerBlock("sausage_mushroom_pizza", () -> new SausageMushroomPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<SausageOnionPizzaBlock>    SAUSAGE_ONION_PIZZA   = registerBlock("sausage_onion_pizza",   () -> new SausageOnionPizzaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<SausagePizzaBlock>         SAUSAGE_PIZZA         = registerBlock("sausage_pizza",         () -> new SausagePizzaBlock(BlockBehaviour.Properties.of()));

    // ── pie crusts / raw pies ────────────────────────────────────────────────

    public static final RegistryObject<RawPieBlock> RAW_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = registerBlock("raw_chocolate_graham_cracker_pie_crust", () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_GRAHAM_CRACKER_PIE_CRUST    = registerBlock("raw_graham_cracker_pie_crust",    () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_PIE_CRUST                   = registerBlock("raw_pie_crust",                   () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_APPLE_CHEESECAKE            = registerBlock("raw_apple_cheesecake",            () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_APPLE_PIE                   = registerBlock("raw_apple_pie",                   () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_BERRY_CHEESECAKE            = registerBlock("raw_berry_cheesecake",            () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_BERRY_PIE                   = registerBlock("raw_berry_pie",                   () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_CHEESECAKE                  = registerBlock("raw_cheesecake",                  () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_CHOCOLATE_PIE               = registerBlock("raw_chocolate_pie",               () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_CHOCOLATE_PIE_GRAHAM_CRACKER = registerBlock("raw_chocolate_pie_graham_cracker", () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_CHORUS_FRUIT_CHEESECAKE     = registerBlock("raw_chorus_fruit_cheesecake",     () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_CHORUS_FRUIT_PIE            = registerBlock("raw_chorus_fruit_pie",            () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = registerBlock("raw_cream_pie_chocolate_graham_cracker", () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_CREAM_PIE_GRAHAM_CRACKER    = registerBlock("raw_cream_pie_graham_cracker",    () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_GLOW_BERRY_CHEESECAKE       = registerBlock("raw_glow_berry_cheesecake",       () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_GLOW_BERRY_PIE              = registerBlock("raw_glow_berry_pie",              () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_PUMPKIN_PIE                 = registerBlock("raw_pumpkin_pie",                 () -> new RawPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<RawPieBlock> RAW_MEAT_PIE                    = registerBlock("raw_meat_pie",                    () -> new RawPieBlock(BlockBehaviour.Properties.of()));

    // ── pies / cheesecakes ────────────────────────────────────────────────────

    public static final RegistryObject<AppleCheesecakeBlock>            APPLE_CHEESECAKE              = registerBlock("apple_cheesecake",              () -> new AppleCheesecakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<BerryPieBlock>                   BERRY_PIE                     = registerBlock("berry_pie",                     () -> new BerryPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CheesecakeBlock>                 CHEESECAKE                    = registerBlock("cheesecake",                    () -> new CheesecakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChocolatePieGrahamCrackerBlock>  CHOCOLATE_PIE_GRAHAM_CRACKER  = registerBlock("chocolate_pie_graham_cracker",  () -> new ChocolatePieGrahamCrackerBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChorusFruitCheesecakeBlock>      CHORUS_FRUIT_CHEESECAKE       = registerBlock("chorus_fruit_cheesecake",       () -> new ChorusFruitCheesecakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChorusFruitPieBlock>             CHORUS_FRUIT_PIE              = registerBlock("chorus_fruit_pie",              () -> new ChorusFruitPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CookieCreamPieBlock>             COOKIE_CREAM_PIE              = registerBlock("cookie_cream_pie",              () -> new CookieCreamPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CreamPieChocolateGrahamCrackerBlock> CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = registerBlock("cream_pie_chocolate_graham_cracker", () -> new CreamPieChocolateGrahamCrackerBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CreamPieGrahamCrackerBlock>      CREAM_PIE_GRAHAM_CRACKER      = registerBlock("cream_pie_graham_cracker",      () -> new CreamPieGrahamCrackerBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<GlowBerryCheesecakeBlock>        GLOW_BERRY_CHEESECAKE         = registerBlock("glow_berry_cheesecake",         () -> new GlowBerryCheesecakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<GlowBerryPieBlock>               GLOW_BERRY_PIE                = registerBlock("glow_berry_pie",                () -> new GlowBerryPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<SmoresPieBlock>                   SMORES_PIE                    = registerBlock("smores_pie",                    () -> new SmoresPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<MeatPieBlock>                    MEAT_PIE                      = registerBlock("meat_pie",                      () -> new MeatPieBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PumpkinPieBlock>                 PUMPKIN_PIE_BLOCK             = registerBlock("pumpkin_pie_block",             () -> new PumpkinPieBlock(BlockBehaviour.Properties.of()));

    // ── waffles ───────────────────────────────────────────────────────────────

    public static final RegistryObject<WaffleBlock>                 WAFFLE                    = registerBlock("waffle",                    () -> new WaffleBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ButterscotchChipWaffleBlock> BUTTERSCOTCH_CHIP_WAFFLE  = registerBlock("butterscotch_chip_waffle",  () -> new ButterscotchChipWaffleBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CaramelChipWaffleBlock>      CARAMEL_CHIP_WAFFLE       = registerBlock("caramel_chip_waffle",       () -> new CaramelChipWaffleBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChocolateChipWaffleBlock>    CHOCOLATE_CHIP_WAFFLE     = registerBlock("chocolate_chip_waffle",     () -> new ChocolateChipWaffleBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<DarkChocolateChipWaffleBlock> DARK_CHOCOLATE_CHIP_WAFFLE = registerBlock("dark_chocolate_chip_waffle", () -> new DarkChocolateChipWaffleBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<WhiteChocolateChipWaffleBlock> WHITE_CHOCOLATE_CHIP_WAFFLE = registerBlock("white_chocolate_chip_waffle", () -> new WhiteChocolateChipWaffleBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ToffeeChipWaffleBlock>       TOFFEE_CHIP_WAFFLE        = registerBlock("toffee_chip_waffle",        () -> new ToffeeChipWaffleBlock(BlockBehaviour.Properties.of()));

    // ── cake bases ────────────────────────────────────────────────────────────

    public static final RegistryObject<CakeBaseBlock> CAKE_BASE           = registerBlock("cake_base",           () -> new CakeBaseBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CakeBaseBlock> UBE_CAKE_BASE       = registerBlock("ube_cake_base",       () -> new CakeBaseBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CakeBaseBlock> CHOCOLATE_CAKE_BASE = registerBlock("chocolate_cake_base", () -> new CakeBaseBlock(BlockBehaviour.Properties.of()));

    // ── cream cakes ───────────────────────────────────────────────────────────

    public static final RegistryObject<AppleCreamCakeBlock>          APPLE_CREAM_CAKE          = registerBlock("apple_cream_cake",          () -> new AppleCreamCakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<BerryCreamCakeBlock>          BERRY_CREAM_CAKE          = registerBlock("berry_cream_cake",          () -> new BerryCreamCakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChocolateCreamCakeBlock>      CHOCOLATE_CREAM_CAKE      = registerBlock("chocolate_cream_cake",      () -> new ChocolateCreamCakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChorusFruitCreamCakeBlock>    CHORUS_FRUIT_CREAM_CAKE   = registerBlock("chorus_fruit_cream_cake",   () -> new ChorusFruitCreamCakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CreamCakeBlock>               CREAM_CAKE                = registerBlock("cream_cake",                () -> new CreamCakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<GlowBerryCreamCakeBlock>      GLOW_BERRY_CREAM_CAKE     = registerBlock("glow_berry_cream_cake",     () -> new GlowBerryCreamCakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<MelonCreamCakeBlock>          MELON_CREAM_CAKE          = registerBlock("melon_cream_cake",          () -> new MelonCreamCakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<UbeCreamUbeCakeBlock>         UBE_CREAM_UBE_CAKE        = registerBlock("ube_cream_ube_cake",        () -> new UbeCreamUbeCakeBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<ChocolateCreamChocolateCakeBlock> CHOCOLATE_CREAM_CHOCOLATE_CAKE = registerBlock("chocolate_cream_chocolate_cake", () -> new ChocolateCreamChocolateCakeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CreamChocolateCakeBlock>      CREAM_CHOCOLATE_CAKE      = registerBlock("cream_chocolate_cake",      () -> new CreamChocolateCakeBlock(BlockBehaviour.Properties.of()));

    // ── cake topping variants ─────────────────────────────────────────────────

    public static final RegistryObject<CakeChorusFruitBlock>          CREAM_CAKE_CHORUS_FRUIT   = registerBlock("cream_cake_chorus_fruit",   () -> new CakeChorusFruitBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CakeGlowBerryBlock>            CREAM_CAKE_GLOW_BERRY     = registerBlock("cream_cake_glow_berry",     () -> new CakeGlowBerryBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<BerryCreamCakeSweetBerryBlock>       BERRY_CREAM_CAKE_SWEET_BERRY      = registerBlock("berry_cream_cake_sweet_berry",        () -> new BerryCreamCakeSweetBerryBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<BerryCreamCakeChorusFruitBlock>      BERRY_CREAM_CAKE_CHORUS_FRUIT     = registerBlock("berry_cream_cake_chorus_fruit",        () -> new BerryCreamCakeChorusFruitBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<BerryCreamCakeGlowBerryBlock>        BERRY_CREAM_CAKE_GLOW_BERRY       = registerBlock("berry_cream_cake_glow_berry",          () -> new BerryCreamCakeGlowBerryBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<ChocolateCreamCakeButterscotchBlock> CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH = registerBlock("chocolate_cream_cake_butterscotch",    () -> new ChocolateCreamCakeButterscotchBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChocolateCreamCakeCaramelBlock>      CHOCOLATE_CREAM_CAKE_CARAMEL      = registerBlock("chocolate_cream_cake_caramel",         () -> new ChocolateCreamCakeCaramelBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChocolateCreamCakeChocolateBlock>    CHOCOLATE_CREAM_CAKE_CHOCOLATE    = registerBlock("chocolate_cream_cake_chocolate",        () -> new ChocolateCreamCakeChocolateBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChocolateCreamCakeDarkChocolateBlock> CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE = registerBlock("chocolate_cream_cake_dark_chocolate", () -> new ChocolateCreamCakeDarkChocolateBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChocolateCreamCakeToffeeBlock>       CHOCOLATE_CREAM_CAKE_TOFFEE       = registerBlock("chocolate_cream_cake_toffee",          () -> new ChocolateCreamCakeToffeeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChocolateCreamCakeWhiteChocolateBlock> CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE = registerBlock("chocolate_cream_cake_white_chocolate", () -> new ChocolateCreamCakeWhiteChocolateBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<ChorusFruitCreamCakeSweetBerryBlock>  CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY   = registerBlock("chorus_fruit_cream_cake_sweet_berry",  () -> new ChorusFruitCreamCakeSweetBerryBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChorusFruitCreamCakeChorusFruitBlock> CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT  = registerBlock("chorus_fruit_cream_cake_chorus_fruit", () -> new ChorusFruitCreamCakeChorusFruitBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChorusFruitCreamCakeGlowBerryBlock>   CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY    = registerBlock("chorus_fruit_cream_cake_glow_berry",   () -> new ChorusFruitCreamCakeGlowBerryBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<GlowBerryCreamCakeSweetBerryBlock>    GLOW_BERRY_CREAM_CREAM_CAKE_SWEET_BERRY   = registerBlock("glow_berry_cream_cake_sweet_berry",  () -> new GlowBerryCreamCakeSweetBerryBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<GlowBerryCreamCakeChorusFruitBlock>   GLOW_BERRY_CREAM_CREAM_CAKE_CHORUS_FRUIT  = registerBlock("glow_berry_cream_cake_chorus_fruit", () -> new GlowBerryCreamCakeChorusFruitBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<GlowBerryCreamCakeGlowBerryBlock>     GLOW_BERRY_CREAM_CREAM_CAKE_GLOW_BERRY    = registerBlock("glow_berry_cream_cake_glow_berry",   () -> new GlowBerryCreamCakeGlowBerryBlock(BlockBehaviour.Properties.of()));

    // ── misc ──────────────────────────────────────────────────────────────────

    public static final RegistryObject<CheeseBlock> CHEESE_BLOCK = registerBlock("cheese_block", () -> new CheeseBlock(BlockBehaviour.Properties.of()));

    // ── gelatin dessert blocks (SlimeBlock subtype) ───────────────────────────

    public static final RegistryObject<SlimeBlock> BLACK_GELATIN_DESSERT_BLOCK      = registerBlock("black_gelatin_dessert_block",       () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> BLUE_GELATIN_DESSERT_BLOCK       = registerBlock("blue_gelatin_dessert_block",        () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> BROWN_GELATIN_DESSERT_BLOCK      = registerBlock("brown_gelatin_dessert_block",       () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> CYAN_GELATIN_DESSERT_BLOCK       = registerBlock("cyan_gelatin_dessert_block",        () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> GELATIN_DESSERT_BLOCK            = registerBlock("gelatin_dessert_block",             () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> GRAY_GELATIN_DESSERT_BLOCK       = registerBlock("gray_gelatin_dessert_block",        () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> GREEN_GELATIN_DESSERT_BLOCK      = registerBlock("green_gelatin_dessert_block",       () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> LIGHT_BLUE_GELATIN_DESSERT_BLOCK = registerBlock("light_blue_gelatin_dessert_block",  () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> LIGHT_GRAY_GELATIN_DESSERT_BLOCK = registerBlock("light_gray_gelatin_dessert_block",  () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> LIME_GELATIN_DESSERT_BLOCK       = registerBlock("lime_gelatin_dessert_block",        () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> MAGENTA_GELATIN_DESSERT_BLOCK    = registerBlock("magenta_gelatin_dessert_block",     () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> ORANGE_GELATIN_DESSERT_BLOCK     = registerBlock("orange_gelatin_dessert_block",      () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> PINK_GELATIN_DESSERT_BLOCK       = registerBlock("pink_gelatin_dessert_block",        () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> PURPLE_GELATIN_DESSERT_BLOCK     = registerBlock("purple_gelatin_dessert_block",      () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> RED_GELATIN_DESSERT_BLOCK        = registerBlock("red_gelatin_dessert_block",         () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<SlimeBlock> YELLOW_GELATIN_DESSERT_BLOCK     = registerBlock("yellow_gelatin_dessert_block",      () -> new SlimeBlock(BlockBehaviour.Properties.of().noOcclusion()));

    // ── candle cake registrations (must appear after all cake block fields) ───

    static {
        registerCandleCakesFor("cream_cake",                          CREAM_CAKE);
        registerCandleCakesFor("berry_cream_cake",                    BERRY_CREAM_CAKE);
        registerCandleCakesFor("chocolate_cream_cake",                CHOCOLATE_CREAM_CAKE);
        registerCandleCakesFor("chorus_fruit_cream_cake",             CHORUS_FRUIT_CREAM_CAKE);
        registerCandleCakesFor("glow_berry_cream_cake",               GLOW_BERRY_CREAM_CAKE);
        registerCandleCakesFor("ube_cream_ube_cake",                  UBE_CREAM_UBE_CAKE);
        registerCandleCakesFor("cream_cake_chorus_fruit",             CREAM_CAKE_CHORUS_FRUIT);
        registerCandleCakesFor("cream_cake_glow_berry",               CREAM_CAKE_GLOW_BERRY);
        registerCandleCakesFor("apple_cream_cake",                    APPLE_CREAM_CAKE);
        registerCandleCakesFor("berry_cream_cake_sweet_berry",        BERRY_CREAM_CAKE_SWEET_BERRY);
        registerCandleCakesFor("berry_cream_cake_chorus_fruit",       BERRY_CREAM_CAKE_CHORUS_FRUIT);
        registerCandleCakesFor("berry_cream_cake_glow_berry",         BERRY_CREAM_CAKE_GLOW_BERRY);
        registerCandleCakesFor("chocolate_cream_cake_butterscotch",   CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH);
        registerCandleCakesFor("chocolate_cream_cake_caramel",        CHOCOLATE_CREAM_CAKE_CARAMEL);
        registerCandleCakesFor("chocolate_cream_cake_chocolate",      CHOCOLATE_CREAM_CAKE_CHOCOLATE);
        registerCandleCakesFor("chocolate_cream_cake_dark_chocolate", CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE);
        registerCandleCakesFor("chocolate_cream_cake_toffee",         CHOCOLATE_CREAM_CAKE_TOFFEE);
        registerCandleCakesFor("chocolate_cream_cake_white_chocolate",CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE);
        registerCandleCakesFor("chocolate_cream_chocolate_cake",      CHOCOLATE_CREAM_CHOCOLATE_CAKE);
        registerCandleCakesFor("chorus_fruit_cream_cake_sweet_berry", CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY);
        registerCandleCakesFor("chorus_fruit_cream_cake_chorus_fruit",CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT);
        registerCandleCakesFor("chorus_fruit_cream_cake_glow_berry",  CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY);
        registerCandleCakesFor("cream_chocolate_cake",                CREAM_CHOCOLATE_CAKE);
        registerCandleCakesFor("glow_berry_cream_cake_sweet_berry",   GLOW_BERRY_CREAM_CREAM_CAKE_SWEET_BERRY);
        registerCandleCakesFor("glow_berry_cream_cake_chorus_fruit",  GLOW_BERRY_CREAM_CREAM_CAKE_CHORUS_FRUIT);
        registerCandleCakesFor("glow_berry_cream_cake_glow_berry",    GLOW_BERRY_CREAM_CREAM_CAKE_GLOW_BERRY);
        registerCandleCakesFor("melon_cream_cake",                    MELON_CREAM_CAKE);
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
