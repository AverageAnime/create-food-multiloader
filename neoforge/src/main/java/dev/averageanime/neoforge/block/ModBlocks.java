package dev.averageanime.neoforge.block;

import dev.averageanime.CommonClass;
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
import dev.averageanime.neoforge.item.storage.ClothSackItem;
import dev.averageanime.neoforge.item.storage.RationBoxItem;
import dev.averageanime.registry.BlockRegistry;
import dev.averageanime.registry.ItemRegistry;
import dev.averageanime.registry.type.Block;
import dev.averageanime.registry.type.Item;

import dev.averageanime.util.Tooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;
import static dev.averageanime.neoforge.item.ModTooltips.addTooltip;

@SuppressWarnings("unused")
public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CommonClass.MOD_ID);

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

    private static final List<DeferredBlock<net.minecraft.world.level.block.Block>> CAKE_BLOCKS_FOR_CANDLES = new ArrayList<>();

    private record CandleWiring(Supplier<net.minecraft.world.level.block.Block> cake, net.minecraft.world.item.Item candleItem,
                                Supplier<net.minecraft.world.level.block.Block> candleCake) {}

    private static final List<CandleWiring> PENDING_CANDLE_WIRING = new ArrayList<>();

    public static void wireUpCandleMap() {
        for (CandleWiring w : PENDING_CANDLE_WIRING) {
            ModCakeBlock.registerCandleVariant(w.cake().get(), w.candleItem(), w.candleCake().get());
        }
    }

    // ── Registration loop ─────────────────────────────────────────────────────

    static {
        // Force class initialization so ALL lists are populated (JLS §12.4.1)
        ItemRegistry.init();
        BlockRegistry.init();

        for (Block def : Block.ALL) {
            final String sliceItemId = def.sliceItemId;
            Supplier<net.minecraft.world.item.Item> sliceSupplier = sliceItemId != null
                    ? () -> Item.getById(sliceItemId).get()
                    : null;
            DeferredBlock<net.minecraft.world.level.block.Block> deferred = switch (def.category) {
                case CAKE         -> registerCake(def.id, sliceSupplier, def.tip);
                case CAKE_BASE    -> registerCakeBase(def.id, def.tip);
                case RAW_PIE      -> registerRawPie(def.id, def.tip);
                case RAW_PIZZA    -> registerRawPizza(def.id, def.tip);
                case COOKED_PIE   -> registerCookedPie(def.id, sliceSupplier, def.tip);
                case COOKED_PIZZA -> registerCookedPizza(def.id, sliceSupplier, def.tip);
                case GELATIN      -> registerGelatinBlock(def.id);
                case CHEESE       -> register(def.id,
                        () -> new CheeseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceSupplier),
                        def.tip);
                case GYRO_MEAT    -> register(def.id,
                        () -> new GyroMeatBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceSupplier),
                        def.tip);
            };
            def.bind(deferred);
        }
    }

    // ── Cloth Sack / Ration Box (custom item factories) ───────────────────────

    public static final DeferredBlock<net.minecraft.world.level.block.Block> RATION_BOX = registerBlockWithCustomItem(
            "ration_box",
            () -> new RationBoxBlock(BlockBehaviour.Properties.of()
                    .strength(1.5f, 6.0f)
                    .sound(net.minecraft.world.level.block.SoundType.WOOD)
                    .noOcclusion()),
            block -> new RationBoxItem(block, new net.minecraft.world.item.Item.Properties()));

    public static final DeferredBlock<net.minecraft.world.level.block.Block> CLOTH_SACK = registerBlockWithCustomItem(
            "cloth_sack",
            () -> new ClothSackBlock(BlockBehaviour.Properties.of()
                    .strength(1.0f, 2.0f)
                    .sound(net.minecraft.world.level.block.SoundType.WOOL)
                    .noOcclusion()),
            block -> new ClothSackItem(block, new net.minecraft.world.item.Item.Properties()));

    // ── Helper methods ────────────────────────────────────────────────────────

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerBlock(String name, Supplier<net.minecraft.world.level.block.Block> blockSupplier) {
        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()));
        return block;
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerBlockWithTooltips(String name,
                                                                                                  Supplier<net.minecraft.world.level.block.Block> blockSupplier,
                                                                                                  Tooltips.Tip tip) {
        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()) {
            @Override
            public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                        @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                addTooltip(components, tip.compat(), tip.keys());
                super.appendHoverText(stack, context, components, flag);
            }
        });
        return block;
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> register(String name, Supplier<net.minecraft.world.level.block.Block> blockSupplier, Tooltips.Tip tip) {
        if (tip != null) return registerBlockWithTooltips(name, blockSupplier, tip);
        return registerBlock(name, blockSupplier);
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerRawPizza(String name, Tooltips.Tip tip) {
        return register(name, () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)), tip);
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerCookedPizza(String name, Supplier<net.minecraft.world.item.Item> sliceItem, Tooltips.Tip tip) {
        return register(name, () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem), tip);
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerRawPie(String name, Tooltips.Tip tip) {
        return register(name, () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)), tip);
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerCookedPie(String name, Supplier<net.minecraft.world.item.Item> sliceItem, Tooltips.Tip tip) {
        return register(name, () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem), tip);
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerCake(String name,
                                                                                     Supplier<net.minecraft.world.item.Item> sliceItem,
                                                                                     Tooltips.Tip tip) {
        Supplier<net.minecraft.world.level.block.Block> blockSupplier = () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem);
        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name, blockSupplier);
        CAKE_BLOCKS_FOR_CANDLES.add(block);
        ITEMS.register(name, () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties().stacksTo(1)) {
            @Override
            public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                        @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                if (tip != null) addTooltip(components, tip.compat(), tip.keys());
                super.appendHoverText(stack, context, components, flag);
            }
        });
        return block;
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerWaffle(String name,
                                                                                       Supplier<net.minecraft.world.item.Item> sliceItem,
                                                                                       Tooltips.Tip tip) {
        return registerCookedPizza(name, sliceItem, tip);
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerGelatinBlock(String name) {
        return registerBlock(name, () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerCakeBase(String name, Tooltips.Tip tip) {
        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name, () -> new CakeBaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
        ITEMS.register(name, () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties().stacksTo(1)) {
            @Override
            public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                        @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                if (tip != null) addTooltip(components, tip.compat(), tip.keys());
                super.appendHoverText(stack, context, components, flag);
            }
        });
        return block;
    }

    private static DeferredBlock<net.minecraft.world.level.block.Block> registerBlockWithCustomItem(
            String name, Supplier<net.minecraft.world.level.block.Block> blockSupplier,
            java.util.function.Function<net.minecraft.world.level.block.Block, net.minecraft.world.item.Item> itemFactory) {
        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> itemFactory.apply(block.get()));
        return block;
    }

    // ── Config-driven blocks (platform-specific) ──────────────────────────────

    private static void registerConfigBlocks() {
        var configFile = net.neoforged.fml.loading.FMLPaths.CONFIGDIR.get().resolve("createfood-client.toml");
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
                    registerRawPie(name, null);
                } else if (type.equals("raw_pizza")) {
                    registerRawPizza(name, null);
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
                    if (type.equals("cake"))             registerCake(name, sliceSupplier, null);
                    else if (type.equals("pie"))         registerCookedPie(name, sliceSupplier, null);
                    else if (type.equals("pizza"))       registerCookedPizza(name, sliceSupplier, null);
                    else if (type.equals("waffle"))      registerWaffle(name, sliceSupplier, null);
                } else {
                    LOGGER.warn("Create: Food - Skipping custom_block entry with unknown type '{}': {}", type, entry);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_block from config", e);
        }
    }

    private static void registerAllCandleCakes() {
        for (DeferredBlock<net.minecraft.world.level.block.Block> cakeRef : CAKE_BLOCKS_FOR_CANDLES) {
            String cakeName = cakeRef.getId().getPath();
            for (CandleType candle : CANDLE_TYPES) {
                String blockName = cakeName + "_" + candle.suffix();
                DeferredBlock<net.minecraft.world.level.block.Block> candleCakeRef = BLOCKS.register(blockName, () ->
                        new ModCandleCakeBlock(
                                BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                                        .lightLevel(s -> s.getValue(ModCandleCakeBlock.LIT) ? 3 : 0),
                                cakeRef::get, candle.block(), cakeName, candle.suffix()));
                PENDING_CANDLE_WIRING.add(new CandleWiring(cakeRef::get, candle.block().asItem(), candleCakeRef::get));
            }
        }
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Blocks");
        registerConfigBlocks();
        registerAllCandleCakes();
        BLOCKS.register(eventBus);
    }
}
