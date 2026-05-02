package dev.averageanime.fabric.block;

import dev.averageanime.block.ModCakeBlock;
import dev.averageanime.block.type.cake.CakeBaseBlock;
import dev.averageanime.block.type.cake.CheeseBlock;
import dev.averageanime.block.type.cake.GyroMeatBlock;
import dev.averageanime.block.type.pie.PieBlock;
import dev.averageanime.block.type.pie.PizzaBlock;
import dev.averageanime.block.type.pie.RawPieBlock;
import dev.averageanime.block.type.pie.RawPizzaBlock;
import dev.averageanime.fabric.block.type.storage.ClothSackBlock;
import dev.averageanime.fabric.block.type.storage.RationBoxBlock;
import dev.averageanime.fabric.item.ModItems;
import dev.averageanime.fabric.item.storage.ClothSackItem;
import dev.averageanime.fabric.item.storage.RationBoxItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;
import java.util.function.Supplier;

import static dev.averageanime.fabric.CreateFood.LOGGER;
import static dev.averageanime.fabric.CreateFood.MOD_ID;

@SuppressWarnings("unused")
public class ModBlocks {

    private static Block registerBlock(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties()));
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
    }

    private static Block registerCake(String name, Supplier<Item> sliceItem) {
        Block block = new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties().stacksTo(1)));
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
    }

    private static Block registerCakeBase(String name) {
        Block block = new CakeBaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE));
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties().stacksTo(1)));
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
    }

    private static Block registerRawPie(String name) {
        return registerBlock(name, new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    }

    private static Block registerRawPizza(String name) {
        return registerBlock(name, new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    }

    private static Block registerCookedPie(String name, Supplier<Item> sliceItem) {
        return registerBlock(name, new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem));
    }

    private static Block registerCookedPizza(String name, Supplier<Item> sliceItem) {
        return registerBlock(name, new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem));
    }

    private static Block registerWaffle(String name, Supplier<Item> sliceItem) {
        return registerCookedPizza(name, sliceItem);
    }

    private static Block registerGelatinBlock(String name) {
        return registerBlock(name, new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    }

    public static final Block APPLE_CHEESECAKE = registerCookedPie("apple_cheesecake", () -> ModItems.APPLE_CHEESECAKE_SLICE);
    public static final Block APPLE_CREAM_CAKE = registerCake("apple_cream_cake", () -> ModItems.APPLE_CREAM_CAKE_SLICE);
    public static final Block BACON_PIZZA = registerCookedPizza("bacon_pizza", () -> ModItems.BACON_PIZZA_SLICE);
    public static final Block BERRY_CREAM_CAKE = registerCake("berry_cream_cake", () -> ModItems.BERRY_CREAM_CAKE_SLICE);
    public static final Block BERRY_CREAM_CAKE_CHORUS_FRUIT = registerCake("berry_cream_cake_chorus_fruit", () -> ModItems.BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT);
    public static final Block BERRY_CREAM_CAKE_GLOW_BERRY = registerCake("berry_cream_cake_glow_berry", () -> ModItems.BERRY_CREAM_CAKE_SLICE_GLOW_BERRY);
    public static final Block BERRY_CREAM_CAKE_SWEET_BERRY = registerCake("berry_cream_cake_sweet_berry", () -> ModItems.BERRY_CREAM_CAKE_SLICE_SWEET_BERRY);
    public static final Block BERRY_PIE = registerCookedPie("berry_pie", () -> ModItems.BERRY_PIE_SLICE);
    public static final Block BLACK_GELATIN_DESSERT_BLOCK = registerGelatinBlock("black_gelatin_dessert_block");
    public static final Block BLUE_GELATIN_DESSERT_BLOCK = registerGelatinBlock("blue_gelatin_dessert_block");
    public static final Block BROWN_GELATIN_DESSERT_BLOCK = registerGelatinBlock("brown_gelatin_dessert_block");
    public static final Block BUTTERSCOTCH_CHIP_WAFFLE = registerWaffle("butterscotch_chip_waffle", () -> ModItems.BUTTERSCOTCH_CHIP_MINI_WAFFLE);
    public static final Block CAKE_BASE = registerCakeBase("cake_base");
    public static final Block CARAMEL_CHIP_WAFFLE = registerWaffle("caramel_chip_waffle", () -> ModItems.CARAMEL_CHIP_MINI_WAFFLE);
    public static final Block CHEESECAKE = registerCookedPie("cheesecake", () -> ModItems.CHEESECAKE_SLICE);
    public static final Block CHEESE_BLOCK = registerBlock("cheese_block", new CheeseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), () -> ModItems.CHEESE_SLICE));
    public static final Block CHEESE_PIZZA = registerCookedPizza("cheese_pizza", () -> ModItems.CHEESE_PIZZA_SLICE);
    public static final Block CHOCOLATE_CAKE_BASE = registerCakeBase("chocolate_cake_base");
    public static final Block CHOCOLATE_CHIP_WAFFLE = registerWaffle("chocolate_chip_waffle", () -> ModItems.CHOCOLATE_CHIP_MINI_WAFFLE);
    public static final Block CHOCOLATE_CREAM_CAKE = registerCake("chocolate_cream_cake", () -> ModItems.CHOCOLATE_CREAM_CAKE_SLICE);
    public static final Block CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH = registerCake("chocolate_cream_cake_butterscotch", () -> ModItems.CHOCOLATE_CREAM_CAKE_SLICE_BUTTERSCOTCH);
    public static final Block CHOCOLATE_CREAM_CAKE_CARAMEL = registerCake("chocolate_cream_cake_caramel", () -> ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL);
    public static final Block CHOCOLATE_CREAM_CAKE_CHOCOLATE = registerCake("chocolate_cream_cake_chocolate", () -> ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CHOCOLATE);
    public static final Block CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE = registerCake("chocolate_cream_cake_dark_chocolate", () -> ModItems.CHOCOLATE_CREAM_CAKE_SLICE_DARK_CHOCOLATE);
    public static final Block CHOCOLATE_CREAM_CAKE_TOFFEE = registerCake("chocolate_cream_cake_toffee", () -> ModItems.CHOCOLATE_CREAM_CAKE_SLICE_TOFFEE);
    public static final Block CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE = registerCake("chocolate_cream_cake_white_chocolate", () -> ModItems.CHOCOLATE_CREAM_CAKE_SLICE_WHITE_CHOCOLATE);
    public static final Block CHOCOLATE_CREAM_CHOCOLATE_CAKE = registerCake("chocolate_cream_chocolate_cake", () -> ModItems.CHOCOLATE_CREAM_CHOCOLATE_CAKE_SLICE);
    public static final Block CHOCOLATE_PIE_GRAHAM_CRACKER = registerCookedPie("chocolate_pie_graham_cracker", () -> ModItems.CHOCOLATE_PIE_GRAHAM_CRACKER_SLICE);
    public static final Block CHORUS_FRUIT_CHEESECAKE = registerCookedPie("chorus_fruit_cheesecake", () -> ModItems.CHORUS_FRUIT_CHEESECAKE_SLICE);
    public static final Block CHORUS_FRUIT_CREAM_CAKE = registerCake("chorus_fruit_cream_cake", () -> ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE);
    public static final Block CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT = registerCake("chorus_fruit_cream_cake_chorus_fruit", () -> ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT);
    public static final Block CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY = registerCake("chorus_fruit_cream_cake_glow_berry", () -> ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_GLOW_BERRY);
    public static final Block CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY = registerCake("chorus_fruit_cream_cake_sweet_berry", () -> ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_SWEET_BERRY);
    public static final Block CHORUS_FRUIT_PIE = registerCookedPie("chorus_fruit_pie", () -> ModItems.CHORUS_FRUIT_PIE_SLICE);
    public static final Block COOKIE_CREAM_PIE = registerCookedPie("cookie_cream_pie", () -> ModItems.COOKIE_CREAM_PIE_SLICE);
    public static final Block CREAM_CAKE = registerCake("cream_cake", () -> ModItems.CREAM_CAKE_SLICE);
    public static final Block CREAM_CAKE_CHORUS_FRUIT = registerCake("cream_cake_chorus_fruit", () -> ModItems.CREAM_CAKE_SLICE_CHORUS_FRUIT);
    public static final Block CREAM_CAKE_GLOW_BERRY = registerCake("cream_cake_glow_berry", () -> ModItems.CREAM_CAKE_SLICE_GLOW_BERRY);
    public static final Block CREAM_CHOCOLATE_CAKE = registerCake("cream_chocolate_cake", () -> ModItems.CREAM_CHOCOLATE_CAKE_SLICE);
    public static final Block CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = registerCookedPie("cream_pie_chocolate_graham_cracker", () -> ModItems.CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_SLICE);
    public static final Block CREAM_PIE_GRAHAM_CRACKER = registerCookedPie("cream_pie_graham_cracker", () -> ModItems.CREAM_PIE_GRAHAM_CRACKER_SLICE);
    public static final Block CYAN_GELATIN_DESSERT_BLOCK = registerGelatinBlock("cyan_gelatin_dessert_block");
    public static final Block DARK_CHOCOLATE_CHIP_WAFFLE = registerWaffle("dark_chocolate_chip_waffle", () -> ModItems.DARK_CHOCOLATE_CHIP_MINI_WAFFLE);
    public static final Block FISH_BACON_PIZZA = registerCookedPizza("fish_bacon_pizza", () -> ModItems.FISH_BACON_PIZZA_SLICE);
    public static final Block FISH_ONION_PIZZA = registerCookedPizza("fish_onion_pizza", () -> ModItems.FISH_ONION_PIZZA_SLICE);
    public static final Block FISH_PIZZA = registerCookedPizza("fish_pizza", () -> ModItems.FISH_PIZZA_SLICE);
    public static final Block GELATIN_DESSERT_BLOCK = registerGelatinBlock("gelatin_dessert_block");
    public static final Block GLOW_BERRY_CHEESECAKE = registerCookedPie("glow_berry_cheesecake", () -> ModItems.GLOW_BERRY_CHEESECAKE_SLICE);
    public static final Block GLOW_BERRY_CREAM_CAKE = registerCake("glow_berry_cream_cake", () -> ModItems.GLOW_BERRY_CREAM_CAKE_SLICE);
    public static final Block GLOW_BERRY_CREAM_CAKE_CHORUS_FRUIT = registerCake("glow_berry_cream_cake_chorus_fruit", () -> ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT);
    public static final Block GLOW_BERRY_CREAM_CAKE_GLOW_BERRY = registerCake("glow_berry_cream_cake_glow_berry", () -> ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_GLOW_BERRY);
    public static final Block GLOW_BERRY_CREAM_CAKE_SWEET_BERRY = registerCake("glow_berry_cream_cake_sweet_berry", () -> ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_SWEET_BERRY);
    public static final Block GLOW_BERRY_PIE = registerCookedPie("glow_berry_pie", () -> ModItems.GLOW_BERRY_PIE_SLICE);
    public static final Block GRAY_GELATIN_DESSERT_BLOCK = registerGelatinBlock("gray_gelatin_dessert_block");
    public static final Block GREEN_GELATIN_DESSERT_BLOCK = registerGelatinBlock("green_gelatin_dessert_block");
    public static final Block GYRO_MEAT_BLOCK = registerBlock("gyro_meat_block", new GyroMeatBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), () -> ModItems.GYRO_MEAT_SLICE));
    public static final Block LIGHT_BLUE_GELATIN_DESSERT_BLOCK = registerGelatinBlock("light_blue_gelatin_dessert_block");
    public static final Block LIGHT_GRAY_GELATIN_DESSERT_BLOCK = registerGelatinBlock("light_gray_gelatin_dessert_block");
    public static final Block LIME_GELATIN_DESSERT_BLOCK = registerGelatinBlock("lime_gelatin_dessert_block");
    public static final Block MAGENTA_GELATIN_DESSERT_BLOCK = registerGelatinBlock("magenta_gelatin_dessert_block");
    public static final Block MEAT_PIE = registerCookedPie("meat_pie", () -> ModItems.MEAT_PIE_SLICE);
    public static final Block MELON_CREAM_CAKE = registerCake("melon_cream_cake", () -> ModItems.MELON_CREAM_CAKE_SLICE);
    public static final Block MUSHROOM_BACON_PIZZA = registerCookedPizza("mushroom_bacon_pizza", () -> ModItems.MUSHROOM_BACON_PIZZA_SLICE);
    public static final Block MUSHROOM_FISH_PIZZA = registerCookedPizza("mushroom_fish_pizza", () -> ModItems.MUSHROOM_FISH_PIZZA_SLICE);
    public static final Block MUSHROOM_ONION_PIZZA = registerCookedPizza("mushroom_onion_pizza", () -> ModItems.MUSHROOM_ONION_PIZZA_SLICE);
    public static final Block MUSHROOM_PIZZA = registerCookedPizza("mushroom_pizza", () -> ModItems.MUSHROOM_PIZZA_SLICE);
    public static final Block ONION_BACON_PIZZA = registerCookedPizza("onion_bacon_pizza", () -> ModItems.ONION_BACON_PIZZA_SLICE);
    public static final Block ONION_PIZZA = registerCookedPizza("onion_pizza", () -> ModItems.ONION_PIZZA_SLICE);
    public static final Block ORANGE_GELATIN_DESSERT_BLOCK = registerGelatinBlock("orange_gelatin_dessert_block");
    public static final Block PINK_GELATIN_DESSERT_BLOCK = registerGelatinBlock("pink_gelatin_dessert_block");
    public static final Block PIZZA_DOUGH = registerRawPizza("pizza_dough");
    public static final Block PIZZA_DOUGH_TOMATO_SAUCE = registerRawPizza("pizza_dough_tomato_sauce");
    public static final Block PUMPKIN_PIE_BLOCK = registerCookedPie("pumpkin_pie_block", () -> ModItems.PUMPKIN_PIE_SLICE);
    public static final Block PURPLE_GELATIN_DESSERT_BLOCK = registerGelatinBlock("purple_gelatin_dessert_block");
    public static final Block RAW_APPLE_CHEESECAKE = registerRawPie("raw_apple_cheesecake");
    public static final Block RAW_APPLE_PIE = registerRawPie("raw_apple_pie");
    public static final Block RAW_BACON_PIZZA = registerRawPizza("raw_bacon_pizza");
    public static final Block RAW_BERRY_CHEESECAKE = registerRawPie("raw_berry_cheesecake");
    public static final Block RAW_BERRY_PIE = registerRawPie("raw_berry_pie");
    public static final Block RAW_CHEESECAKE = registerRawPie("raw_cheesecake");
    public static final Block RAW_CHEESE_PIZZA = registerRawPizza("raw_cheese_pizza");
    public static final Block RAW_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = registerRawPie("raw_chocolate_graham_cracker_pie_crust");
    public static final Block RAW_CHOCOLATE_PIE = registerRawPie("raw_chocolate_pie");
    public static final Block RAW_CHOCOLATE_PIE_GRAHAM_CRACKER = registerRawPie("raw_chocolate_pie_graham_cracker");
    public static final Block RAW_CHORUS_FRUIT_CHEESECAKE = registerRawPie("raw_chorus_fruit_cheesecake");
    public static final Block RAW_CHORUS_FRUIT_PIE = registerRawPie("raw_chorus_fruit_pie");
    public static final Block RAW_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = registerRawPie("raw_cream_pie_chocolate_graham_cracker");
    public static final Block RAW_CREAM_PIE_GRAHAM_CRACKER = registerRawPie("raw_cream_pie_graham_cracker");
    public static final Block RAW_FISH_BACON_PIZZA = registerRawPizza("raw_fish_bacon_pizza");
    public static final Block RAW_FISH_ONION_PIZZA = registerRawPizza("raw_fish_onion_pizza");
    public static final Block RAW_FISH_PIZZA = registerRawPizza("raw_fish_pizza");
    public static final Block RAW_GLOW_BERRY_CHEESECAKE = registerRawPie("raw_glow_berry_cheesecake");
    public static final Block RAW_GLOW_BERRY_PIE = registerRawPie("raw_glow_berry_pie");
    public static final Block RAW_GRAHAM_CRACKER_PIE_CRUST = registerRawPie("raw_graham_cracker_pie_crust");
    public static final Block RAW_MEAT_PIE = registerRawPie("raw_meat_pie");
    public static final Block RAW_MUSHROOM_BACON_PIZZA = registerRawPizza("raw_mushroom_bacon_pizza");
    public static final Block RAW_MUSHROOM_FISH_PIZZA = registerRawPizza("raw_mushroom_fish_pizza");
    public static final Block RAW_MUSHROOM_ONION_PIZZA = registerRawPizza("raw_mushroom_onion_pizza");
    public static final Block RAW_MUSHROOM_PIZZA = registerRawPizza("raw_mushroom_pizza");
    public static final Block RAW_ONION_BACON_PIZZA = registerRawPizza("raw_onion_bacon_pizza");
    public static final Block RAW_ONION_PIZZA = registerRawPizza("raw_onion_pizza");
    public static final Block RAW_PIE_CRUST = registerRawPie("raw_pie_crust");
    public static final Block RAW_PUMPKIN_PIE = registerRawPie("raw_pumpkin_pie");
    public static final Block RAW_SAUSAGE_BACON_PIZZA = registerRawPizza("raw_sausage_bacon_pizza");
    public static final Block RAW_SAUSAGE_FISH_PIZZA = registerRawPizza("raw_sausage_fish_pizza");
    public static final Block RAW_SAUSAGE_MUSHROOM_PIZZA = registerRawPizza("raw_sausage_mushroom_pizza");
    public static final Block RAW_SAUSAGE_ONION_PIZZA = registerRawPizza("raw_sausage_onion_pizza");
    public static final Block RAW_SAUSAGE_PIZZA = registerRawPizza("raw_sausage_pizza");
    public static final Block RED_GELATIN_DESSERT_BLOCK = registerGelatinBlock("red_gelatin_dessert_block");
    public static final Block SAUSAGE_BACON_PIZZA = registerCookedPizza("sausage_bacon_pizza", () -> ModItems.SAUSAGE_BACON_PIZZA_SLICE);
    public static final Block SAUSAGE_FISH_PIZZA = registerCookedPizza("sausage_fish_pizza", () -> ModItems.SAUSAGE_FISH_PIZZA_SLICE);
    public static final Block SAUSAGE_MUSHROOM_PIZZA = registerCookedPizza("sausage_mushroom_pizza", () -> ModItems.SAUSAGE_MUSHROOM_PIZZA_SLICE);
    public static final Block SAUSAGE_ONION_PIZZA = registerCookedPizza("sausage_onion_pizza", () -> ModItems.SAUSAGE_ONION_PIZZA_SLICE);
    public static final Block SAUSAGE_PIZZA = registerCookedPizza("sausage_pizza", () -> ModItems.SAUSAGE_PIZZA_SLICE);
    public static final Block SMORES_PIE = registerCookedPie("smores_pie", () -> ModItems.SMORES_PIE_SLICE);
    public static final Block TOFFEE_CHIP_WAFFLE = registerWaffle("toffee_chip_waffle", () -> ModItems.TOFFEE_CHIP_MINI_WAFFLE);
    public static final Block UBE_CAKE_BASE = registerCakeBase("ube_cake_base");
    public static final Block UBE_CREAM_UBE_CAKE = registerCake("ube_cream_ube_cake", () -> ModItems.UBE_CREAM_UBE_CAKE_SLICE);
    public static final Block WAFFLE = registerWaffle("waffle", () -> ModItems.MINI_WAFFLE);
    public static final Block WHITE_CHOCOLATE_CHIP_WAFFLE = registerWaffle("white_chocolate_chip_waffle", () -> ModItems.WHITE_CHOCOLATE_CHIP_MINI_WAFFLE);
    public static final Block YELLOW_GELATIN_DESSERT_BLOCK = registerGelatinBlock("yellow_gelatin_dessert_block");

    private static Block registerBlockWithCustomItem(
            String name, Supplier<Block> blockSupplier,
            java.util.function.Function<Block, Item> itemFactory) {
        Block block = Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name), blockSupplier.get());
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                itemFactory.apply(block));
        return block;
    }

    public static final Block RATION_BOX = registerBlockWithCustomItem(
            "ration_box",
            () -> new RationBoxBlock(BlockBehaviour.Properties.of()
                    .strength(1.5f, 6.0f)
                    .sound(net.minecraft.world.level.block.SoundType.WOOD)
                    .noOcclusion()),
            block -> new RationBoxItem(block, new Item.Properties()));

    public static final Block CLOTH_SACK = registerBlockWithCustomItem(
            "cloth_sack",
            () -> new ClothSackBlock(BlockBehaviour.Properties.of()
                    .strength(1.0f, 2.0f)
                    .sound(net.minecraft.world.level.block.SoundType.WOOL)
                    .noOcclusion()),
            block -> new ClothSackItem(block, new Item.Properties()));

    public static void registerConfigBlocks() {
        var configFile = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve("createfood-client.toml");
        if (!java.nio.file.Files.exists(configFile)) return;
        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("blocks.block", List.of());
            for (String entry : entries) {
                String[] p = entry.split("\\|");
                if (p.length < 2) {
                    LOGGER.warn("Create: Food - Skipping invalid custom_block entry (too few fields): {}", entry);
                    continue;
                }
                String name = p[0];
                String type = p[1].toLowerCase();
                if (type.equals("raw_pie")) {
                    registerRawPie(name);
                } else if (type.equals("raw_pizza")) {
                    registerRawPizza(name);
                } else if (type.equals("gelatin")) {
                    registerGelatinBlock(name);
                } else if (type.equals("cake") || type.equals("pie") || type.equals("pizza") || type.equals("waffle")) {
                    if (p.length < 3 || p[2].isBlank()) {
                        LOGGER.warn("Create: Food - Skipping custom_block entry missing slice item: {}", entry);
                        continue;
                    }
                    String sliceId = p[2];
                    Supplier<Item> sliceSupplier = () -> {
                        ResourceLocation rl = ResourceLocation.tryParse(sliceId);
                        if (rl == null) return Items.BARRIER;
                        Item item = BuiltInRegistries.ITEM.get(rl);
                        return item != Items.AIR ? item : Items.BARRIER;
                    };
                    if (type.equals("cake"))             registerCake(name, sliceSupplier);
                    else if (type.equals("pie"))         registerCookedPie(name, sliceSupplier);
                    else if (type.equals("pizza"))       registerCookedPizza(name, sliceSupplier);
                    else if (type.equals("waffle"))      registerWaffle(name, sliceSupplier);
                } else {
                    LOGGER.warn("Create: Food - Skipping custom_block entry with unknown type '{}': {}", type, entry);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_block from config", e);
        }
    }

    public static void init() {
        registerConfigBlocks();
    }
}
