package net.averageanime.createfood.block;

import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.display.*;
import net.averageanime.createfood.block.plate.*;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.averageanime.createfood.registry.DisplayBlockRegistry;
import net.averageanime.createfood.registry.DisplayBlockRegistry.DisplayBlockConfig;
import net.averageanime.createfood.registry.DisplayBlockRegistry.DisplayType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModDisplayBlocks {

    private static final Logger LOGGER = LogManager.getLogger(CreateFood.ID);

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateFood.ID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateFood.ID);

    // ── Core plate blocks ─────────────────────────────────────────────────────

    public static final RegistryObject<Block> SMALL_PLATE_BLOCK = BLOCKS.register("small_plate_block",
            () -> new SmallPlateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), ModDisplayBlocks.PLATE_BLOCK));

    public static final RegistryObject<Block> PLATE_BLOCK = BLOCKS.register("plate_block",
            () -> new EmptyPlateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), ModDisplayBlocks.SMALL_PLATE_BLOCK));

    public static final RegistryObject<Block> GENERIC_DISPLAY_PLATE_BLOCK = BLOCKS.register("generic_display_plate_block",
            () -> new GenericDisplayPlateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), ModDisplayBlocks.PLATE_BLOCK));

    // ── Auto-registration ─────────────────────────────────────────────────────

    public static void autoRegisterDisplayBlocks() {
        try {
            List<String> itemNames = DisplayBlockRegistry.getItemNames();

            for (String itemName : itemNames) {
                if (DisplayBlockRegistry.EXCLUDED_ITEMS.contains(itemName)) continue;

                boolean skip = false;
                for (String pattern : DisplayBlockRegistry.SKIP_PATTERNS) {
                    if (itemName.contains(pattern)) { skip = true; break; }
                }
                if (skip) continue;

                List<DisplayBlockConfig> configs = DisplayBlockRegistry.findMatchingConfigs(itemName);
                for (DisplayBlockConfig config : configs) {
                    String blockName = DisplayBlockRegistry.getBlockName(itemName, config.type());
                    registerDisplayBlock(blockName, itemName, config);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Create: Food - Failed to auto-register display blocks", e);
        }
    }

    private static void registerDisplayBlock(String blockName, String itemName, DisplayBlockConfig config) {
        Supplier<net.minecraft.world.item.Item> itemSupplier = () -> {
            net.minecraft.world.item.Item item = ForgeRegistries.ITEMS.getValue(
                    new net.minecraft.resources.ResourceLocation(CreateFood.ID, itemName));
            return (item != null && item != Items.AIR) ? item : Items.BARRIER;
        };

        String suffixKey = switch (config.type()) {
            case PLATE, SMALL_PLATE, PLATE_FOOD -> "display.createfood.suffix.plate";
            case BOWL, SALAD_BOWL               -> "display.createfood.suffix.bowl";
            case BOTTLE                         -> "display.createfood.suffix.bottle";
        };

        RegistryObject<Block> block = BLOCKS.register(blockName, () -> createBlock(itemSupplier, config));

        ITEMS.register(blockName, () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()) {
            @Override
            public net.minecraft.network.chat.@org.jetbrains.annotations.NotNull Component getName(
                    net.minecraft.world.item.@org.jetbrains.annotations.NotNull ItemStack stack) {
                net.minecraft.world.item.Item original = itemSupplier.get();
                if (original != null && original != Items.BARRIER) {
                    return net.minecraft.network.chat.Component.translatable(
                            "display.createfood.format",
                            original.getDescription(),
                            net.minecraft.network.chat.Component.translatable(suffixKey));
                }
                return super.getName(stack);
            }

            @Override
            public void appendHoverText(
                    net.minecraft.world.item.@org.jetbrains.annotations.NotNull ItemStack stack,
                    @org.jetbrains.annotations.Nullable net.minecraft.world.level.Level level,
                    java.util.@org.jetbrains.annotations.NotNull List<net.minecraft.network.chat.Component> components,
                    net.minecraft.world.item.@org.jetbrains.annotations.NotNull TooltipFlag flag) {
                net.minecraft.world.item.Item original = itemSupplier.get();
                if (original != null && original != Items.BARRIER) {
                    original.appendHoverText(new net.minecraft.world.item.ItemStack(original), level, components, flag);
                }
                super.appendHoverText(stack, level, components, flag);
            }
        });
    }

    private static Block createBlock(Supplier<net.minecraft.world.item.Item> itemSupplier,
                                     DisplayBlockConfig config) {
        return switch (config.type()) {
            case PLATE       -> new PlateBlock(itemSupplier, config.maxStack(), PLATE_BLOCK);
            case SMALL_PLATE -> new SmallPlateFoodBlock(itemSupplier, SMALL_PLATE_BLOCK);
            case BOTTLE      -> new BottleFoodBlock(itemSupplier, config.height(),
                    config.hasParticles(), config.particleType());
            case BOWL        -> new BowlFoodBlock(itemSupplier, config.height(),
                    config.hasParticles(), config.particleType());
            case SALAD_BOWL  -> new SaladBowlFoodBlock(itemSupplier);
            case PLATE_FOOD  -> new PlateFoodBlock(itemSupplier);
        };
    }

    public static void registerBlockItems() {
        BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof FoodBlock displayableBlock) {
                net.minecraft.world.item.Item displayItem = displayableBlock.displayItem.get();
                FoodBlock.Registry.register(() -> displayItem, (Supplier<Block>) blockEntry);
            }
        });

    }

    private static void registerCustomDisplayBlocks() {
        // Read config file directly (config values not loaded yet during registration phase)
        java.nio.file.Path configFile = net.minecraftforge.fml.loading.FMLPaths.CONFIGDIR.get()
                .resolve("createfood-client.toml");
        if (!java.nio.file.Files.exists(configFile)) return;
        try {
            com.electronwill.nightconfig.core.file.FileConfig raw =
                    com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile());
            raw.load();
            java.util.List<String> entries = raw.getOrElse("display.display_block",
                    java.util.List.of());
            raw.close();
            for (String entry : entries) {
                try {
                    String[] p = entry.split("\\|");
                    if (p.length < 3) continue;
                    String[] id = p[0].split(":", 2);
                    String ns = id.length > 1 ? id[0] : "minecraft";
                    String path = id.length > 1 ? id[1] : id[0];
                    DisplayType type;
                    try {
                        type = DisplayType.valueOf(p[1].toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException e) {
                        LOGGER.warn("Create: Food - Unknown display type '{}' in custom_display_block entry: {}", p[1], entry);
                        continue;
                    }
                    int maxStack = Integer.parseInt(p[2]);
                    double height = p.length > 3 ? Double.parseDouble(p[3]) : 12.0;
                    boolean particles = p.length > 4 && Boolean.parseBoolean(p[4]);
                    DisplayBlockConfig config = particles
                            ? new DisplayBlockConfig(type, maxStack, height, true, () -> ParticleTypes.WHITE_ASH)
                            : new DisplayBlockConfig(type, maxStack);
                    String blockName = DisplayBlockRegistry.getBlockName(path, type);
                    final String fns = ns, fpath = path;
                    Supplier<Item> itemSup = () -> {
                        Item i = ForgeRegistries.ITEMS.getValue(new ResourceLocation(fns, fpath));
                        return (i != null && i != Items.AIR) ? i : Items.BARRIER;
                    };
                    RegistryObject<Block> block = BLOCKS.register(blockName, () -> createBlock(itemSup, config));
                    ITEMS.register(blockName, () -> new BlockItem(block.get(), new Item.Properties()));
                } catch (Exception e) {
                    LOGGER.warn("Create: Food - Invalid custom_display_block entry: {}", entry);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_display_block from config file", e);
        }
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Display Blocks");
        autoRegisterDisplayBlocks();
        registerCustomDisplayBlocks();

        // Register core block items
        ITEMS.register("plate_block",
                () -> new BlockItem(PLATE_BLOCK.get(), new net.minecraft.world.item.Item.Properties()));
        ITEMS.register("small_plate_block",
                () -> new BlockItem(SMALL_PLATE_BLOCK.get(), new net.minecraft.world.item.Item.Properties()));
        ITEMS.register("generic_display_plate_block",
                () -> new BlockItem(GENERIC_DISPLAY_PLATE_BLOCK.get(), new net.minecraft.world.item.Item.Properties()));

        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);

        eventBus.addListener((net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) ->
                event.enqueueWork(() -> {
                    ModDisplayBlocks.registerBlockItems();
                    ModPlateBlocks.registerCompatiblePlates();
                }));
    }
}
