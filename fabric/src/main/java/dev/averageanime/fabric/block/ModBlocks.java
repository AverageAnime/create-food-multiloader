package dev.averageanime.fabric.block;

import dev.averageanime.block.ModCakeBlock;
import dev.averageanime.block.ModCandleCakeBlock;
import dev.averageanime.block.type.cake.CakeBaseBlock;
import dev.averageanime.block.type.cake.CheeseBlock;
import dev.averageanime.block.type.cake.GyroMeatBlock;
import dev.averageanime.block.type.pie.PieBlock;
import dev.averageanime.block.type.pie.PizzaBlock;
import dev.averageanime.block.type.pie.RawPieBlock;
import dev.averageanime.block.type.pie.RawPizzaBlock;
import dev.averageanime.block.type.storage.ClothSackBlock;
import dev.averageanime.block.type.storage.RationBoxBlock;
import dev.averageanime.fabric.item.storage.ClothSackItem;
import dev.averageanime.fabric.item.storage.RationBoxItem;
import dev.averageanime.registry.BlockRegistry;
import dev.averageanime.registry.type.Block;
import dev.averageanime.registry.type.Item;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static dev.averageanime.fabric.CreateFood.LOGGER;
import static dev.averageanime.fabric.CreateFood.MOD_ID;

@SuppressWarnings("unused")
public class ModBlocks {

    public static net.minecraft.world.level.block.Block CLOTH_SACK;
    public static net.minecraft.world.level.block.Block RATION_BOX;

    // ── Candle registration ───────────────────────────────────────────────────

    private record CandleType(String suffix, net.minecraft.world.level.block.Block block) {}

    private static final List<CandleType> CANDLE_TYPES = List.of(
            new CandleType("candle",           Blocks.CANDLE),
            new CandleType("white_candle",      Blocks.WHITE_CANDLE),
            new CandleType("orange_candle",     Blocks.ORANGE_CANDLE),
            new CandleType("magenta_candle",    Blocks.MAGENTA_CANDLE),
            new CandleType("light_blue_candle", Blocks.LIGHT_BLUE_CANDLE),
            new CandleType("yellow_candle",     Blocks.YELLOW_CANDLE),
            new CandleType("lime_candle",       Blocks.LIME_CANDLE),
            new CandleType("pink_candle",       Blocks.PINK_CANDLE),
            new CandleType("gray_candle",       Blocks.GRAY_CANDLE),
            new CandleType("light_gray_candle", Blocks.LIGHT_GRAY_CANDLE),
            new CandleType("cyan_candle",       Blocks.CYAN_CANDLE),
            new CandleType("purple_candle",     Blocks.PURPLE_CANDLE),
            new CandleType("blue_candle",       Blocks.BLUE_CANDLE),
            new CandleType("brown_candle",      Blocks.BROWN_CANDLE),
            new CandleType("green_candle",      Blocks.GREEN_CANDLE),
            new CandleType("red_candle",        Blocks.RED_CANDLE),
            new CandleType("black_candle",      Blocks.BLACK_CANDLE)
    );

    private static final List<net.minecraft.world.level.block.Block> CAKE_BLOCKS_FOR_CANDLES = new ArrayList<>();

    // ── Low-level registration helpers ────────────────────────────────────────

    private static net.minecraft.world.level.block.Block registerBlock(String name, net.minecraft.world.level.block.Block block) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new net.minecraft.world.item.Item.Properties()));
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
    }

    private static net.minecraft.world.level.block.Block registerCake(String name, Supplier<net.minecraft.world.item.Item> sliceItem) {
        net.minecraft.world.level.block.Block block = new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new net.minecraft.world.item.Item.Properties().stacksTo(1)));
        net.minecraft.world.level.block.Block registered = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
        CAKE_BLOCKS_FOR_CANDLES.add(registered);
        return registered;
    }

    private static net.minecraft.world.level.block.Block registerCakeBase(String name) {
        net.minecraft.world.level.block.Block block = new CakeBaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE));
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new net.minecraft.world.item.Item.Properties().stacksTo(1)));
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
    }

    private static net.minecraft.world.level.block.Block registerRawPie(String name) {
        return registerBlock(name, new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    }

    private static net.minecraft.world.level.block.Block registerRawPizza(String name) {
        return registerBlock(name, new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    }

    private static net.minecraft.world.level.block.Block registerCookedPie(String name, Supplier<net.minecraft.world.item.Item> sliceItem) {
        return registerBlock(name, new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem));
    }

    private static net.minecraft.world.level.block.Block registerCookedPizza(String name, Supplier<net.minecraft.world.item.Item> sliceItem) {
        return registerBlock(name, new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem));
    }

    private static net.minecraft.world.level.block.Block registerGelatinBlock(String name) {
        return registerBlock(name, new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    }

    private static net.minecraft.world.level.block.Block registerBlockWithCustomItem(
            String name, Supplier<net.minecraft.world.level.block.Block> blockSupplier,
            java.util.function.Function<net.minecraft.world.level.block.Block, net.minecraft.world.item.Item> itemFactory) {
        net.minecraft.world.level.block.Block block = Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name), blockSupplier.get());
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                itemFactory.apply(block));
        return block;
    }

    // ── Main registration loop ────────────────────────────────────────────────

    private static void registerAllBlocks() {
        // Force ModBlockDefs class to initialize so BlockDef.ALL is populated (JLS §12.4.1)
        BlockRegistry.init();

        for (Block def : Block.ALL) {
            final String id          = def.id;
            final String sliceItemId = def.sliceItemId;
            Supplier<net.minecraft.world.item.Item> sliceSupplier = sliceItemId != null
                    ? () -> Item.getById(sliceItemId).get()
                    : null;

            net.minecraft.world.level.block.Block block = switch (def.category) {
                case CAKE       -> registerCake(id, sliceSupplier);
                case CAKE_BASE  -> registerCakeBase(id);
                case RAW_PIE    -> registerRawPie(id);
                case RAW_PIZZA  -> registerRawPizza(id);
                case COOKED_PIE -> registerCookedPie(id, sliceSupplier);
                case COOKED_PIZZA -> registerCookedPizza(id, sliceSupplier);
                case GELATIN    -> registerGelatinBlock(id);
                case CHEESE     -> registerBlock(id,
                        new CheeseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceSupplier));
                case GYRO_MEAT  -> registerBlock(id,
                        new GyroMeatBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceSupplier));
            };

            def.bind(() -> block);
        }
    }

    // ── Candle-cake wiring (platform-specific) ────────────────────────────────

    private static void registerAllCandleCakes() {
        for (net.minecraft.world.level.block.Block cake : CAKE_BLOCKS_FOR_CANDLES) {
            ResourceLocation cakeId = BuiltInRegistries.BLOCK.getKey(cake);
            if (cakeId == null) continue;
            String cakeName = cakeId.getPath();
            for (CandleType candle : CANDLE_TYPES) {
                String blockName = cakeName + "_" + candle.suffix();
                net.minecraft.world.level.block.Block candleCake = new ModCandleCakeBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                                .lightLevel(s -> s.getValue(ModCandleCakeBlock.LIT) ? 3 : 0),
                        () -> cake, candle.block(), cakeName, candle.suffix());
                Registry.register(BuiltInRegistries.BLOCK,
                        ResourceLocation.fromNamespaceAndPath(MOD_ID, blockName), candleCake);
                ModCakeBlock.registerCandleVariant(cake, candle.block().asItem(), candleCake);
            }
        }
    }

    // ── Config-driven blocks (platform-specific) ──────────────────────────────

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
                    Supplier<net.minecraft.world.item.Item> sliceSupplier = () -> {
                        ResourceLocation rl = ResourceLocation.tryParse(sliceId);
                        if (rl == null) return Items.BARRIER;
                        net.minecraft.world.item.Item item = BuiltInRegistries.ITEM.get(rl);
                        return item != Items.AIR ? item : Items.BARRIER;
                    };
                    if (type.equals("cake"))             registerCake(name, sliceSupplier);
                    else if (type.equals("pie"))         registerCookedPie(name, sliceSupplier);
                    else if (type.equals("pizza"))       registerCookedPizza(name, sliceSupplier);
                    else if (type.equals("waffle"))      registerCookedPizza(name, sliceSupplier);
                } else {
                    LOGGER.warn("Create: Food - Skipping custom_block entry with unknown type '{}': {}", type, entry);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_block from config", e);
        }
    }

    // ── Entry point ───────────────────────────────────────────────────────────

    public static void init() {
        registerAllBlocks();

        RATION_BOX = registerBlockWithCustomItem(
                "ration_box",
                () -> new RationBoxBlock(BlockBehaviour.Properties.of()
                        .strength(1.5f, 6.0f)
                        .sound(net.minecraft.world.level.block.SoundType.WOOD)
                        .noOcclusion()),
                block -> new RationBoxItem(block, new net.minecraft.world.item.Item.Properties()));

        CLOTH_SACK = registerBlockWithCustomItem(
                "cloth_sack",
                () -> new ClothSackBlock(BlockBehaviour.Properties.of()
                        .strength(1.0f, 2.0f)
                        .sound(net.minecraft.world.level.block.SoundType.WOOL)
                        .noOcclusion()),
                block -> new ClothSackItem(block, new net.minecraft.world.item.Item.Properties()));

        registerAllCandleCakes();
        registerConfigBlocks();
    }
}
