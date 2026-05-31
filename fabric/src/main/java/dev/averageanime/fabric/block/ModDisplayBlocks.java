package dev.averageanime.fabric.block;

import dev.averageanime.fabric.CreateFood;
import dev.averageanime.block.type.display.*;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.fabric.block.handler.FoodPlacementHandler;
import dev.averageanime.fabric.item.ModItems;
import dev.averageanime.fabric.item.ModTooltips;
import dev.averageanime.registry.DisplayBlockRegistry;
import dev.averageanime.registry.DisplayBlockRegistry.DisplayBlockConfig;
import dev.averageanime.registry.DisplayBlockRegistry.DisplayType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.util.*;
import java.util.function.Supplier;

import static dev.averageanime.fabric.CreateFood.MOD_ID;

@SuppressWarnings("unused")
public class ModDisplayBlocks {

    private static final List<Block> REGISTERED_DISPLAY_BLOCKS = new ArrayList<>();

    public static List<Block> getRegisteredDisplayBlocks() {
        return REGISTERED_DISPLAY_BLOCKS;
    }

    public static Block SMALL_PLATE_BLOCK;
    public static Block PLATE_BLOCK;
    public static Block GENERIC_DISPLAY_PLATE_BLOCK;

    public static void init() {
        CreateFood.LOGGER.info("Create: Food - Registering Display Blocks");

        SMALL_PLATE_BLOCK = regBlock("small_plate_block",
                new SmallPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> PLATE_BLOCK));
        PLATE_BLOCK = regBlock("plate_block",
                new EmptyPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),
                        () -> SMALL_PLATE_BLOCK));

        GENERIC_DISPLAY_PLATE_BLOCK = regBlock("generic_display_plate_block",
                new GenericDisplayPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> PLATE_BLOCK));

        autoRegisterDisplayBlocks();
        registerConfigDisplayBlocks();
        registerBlockItems();
        ModPlateBlocks.registerCompatiblePlates();
        FoodPlacementHandler.register();
    }

    private static void autoRegisterDisplayBlocks() {
        try {
            List<String> itemNames = getItemNames();
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
            CreateFood.LOGGER.error("Failed to auto-register display blocks", e);
        }
    }

    private static List<String> getItemNames() {
        return DisplayBlockRegistry.getItemNames();
    }

    private static void registerDisplayBlock(String blockName, String itemName,
                                             DisplayBlockConfig config) {
        Supplier<Item> itemSupplier = resolveModItemSupplier(itemName);
        registerDisplayBlockFromSupplier(blockName, itemSupplier, config);
    }

    private static Supplier<Item> resolveModItemSupplier(String itemName) {
        return () -> {
            for (dev.averageanime.registry.type.Item def : dev.averageanime.registry.type.Item.ALL) {
                if (def.id.equals(itemName)) return def.get();
            }
            dev.averageanime.registry.type.Block blockDef =
                    dev.averageanime.registry.type.Block.getById(itemName);
            if (blockDef != null) return blockDef.get().asItem();
            return Items.BARRIER;
        };
    }

    private static void registerDisplayBlockFromSupplier(String blockName,
                                                         Supplier<Item> itemSupplier,
                                                         DisplayBlockConfig config) {
        String suffixKey = switch (config.type()) {
            case PLATE, SMALL_PLATE, PLATE_FOOD -> "display.createfood.suffix.plate";
            case BOWL, SALAD_BOWL               -> "display.createfood.suffix.bowl";
            case BOTTLE                         -> "display.createfood.suffix.bottle";
        };

        Block block = createBlock(itemSupplier, config);
        Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, blockName), block);
        REGISTERED_DISPLAY_BLOCKS.add(block);

        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, blockName),
                new BlockItem(block, new Item.Properties()) {
                    @Override
                    public @NotNull Component getName(@NotNull ItemStack stack) {
                        Item original = itemSupplier.get();
                        if (original != null && original != Items.BARRIER) {
                            return Component.translatable("display.createfood.format",
                                    original.getDescription(),
                                    Component.translatable(suffixKey));
                        }
                        return super.getName(stack);
                    }

                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        Item original = itemSupplier.get();
                        if (original != null && original != Items.BARRIER) {
                            original.appendHoverText(new ItemStack(original), context, components, flag);
                        }
                        super.appendHoverText(stack, context, components, flag);
                    }
                });
    }

    private static Block createBlock(Supplier<Item> itemSupplier, DisplayBlockConfig config) {
        return switch (config.type()) {
            case PLATE       -> new PlateBlock(itemSupplier, config.maxStack(), () -> PLATE_BLOCK);
            case SMALL_PLATE -> new SmallPlateFoodBlock(itemSupplier, () -> SMALL_PLATE_BLOCK);
            case BOTTLE      -> new BottleFoodBlock(itemSupplier, config.height(),
                    config.hasParticles(), config.particleType());
            case BOWL        -> new BowlFoodBlock(itemSupplier, config.height(),
                    config.hasParticles(), config.particleType());
            case SALAD_BOWL  -> new SaladBowlFoodBlock(itemSupplier);
            case PLATE_FOOD  -> new PlateFoodBlock(itemSupplier);
        };
    }

    public static Block registerPlateBlock(String name, Supplier<Item> foodItem, int maxStack) {
        Block block = new PlateBlock(foodItem, maxStack, () -> PLATE_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties()));
        REGISTERED_DISPLAY_BLOCKS.add(block);
        return block;
    }

    public static Block registerPlateBlock(String name, Supplier<Item> foodItem, int maxStack,
                                           String compatTooltip, String... ingredientTooltips) {
        Block block = new PlateBlock(foodItem, maxStack, () -> PLATE_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        ModTooltips.addTooltip(components, compatTooltip, ingredientTooltips);
                        super.appendHoverText(stack, context, components, flag);
                    }
                });
        REGISTERED_DISPLAY_BLOCKS.add(block);
        return block;
    }

    public static Block registerPlateBlockFromBlock(String name, Supplier<Block> foodBlock, int maxStack) {
        Supplier<Item> itemSupplier = () -> foodBlock.get().asItem();
        Block block = new PlateBlock(itemSupplier, maxStack, () -> PLATE_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        Item original = itemSupplier.get();
                        if (original != null && original != Items.BARRIER)
                            original.appendHoverText(new ItemStack(original), context, components, flag);
                        super.appendHoverText(stack, context, components, flag);
                    }
                });
        REGISTERED_DISPLAY_BLOCKS.add(block);
        return block;
    }

    public static Block registerPlateBlockFromBlock(String name, Supplier<Block> foodBlock, int maxStack,
                                                    String compatTooltip, String... ingredientTooltips) {
        Supplier<Item> itemSupplier = () -> foodBlock.get().asItem();
        Block block = new PlateBlock(itemSupplier, maxStack, () -> PLATE_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        Item original = itemSupplier.get();
                        if (original != null && original != Items.BARRIER)
                            original.appendHoverText(new ItemStack(original), context, components, flag);
                        ModTooltips.addTooltip(components, compatTooltip, ingredientTooltips);
                        super.appendHoverText(stack, context, components, flag);
                    }
                });
        REGISTERED_DISPLAY_BLOCKS.add(block);
        return block;
    }

    public static Block registerBottleBlock(String name, Supplier<Item> foodItem,
                                            double heightInPixels, boolean hasParticles,
                                            Supplier<ParticleOptions> particleType) {
        Block block = new BottleFoodBlock(foodItem, heightInPixels, hasParticles, particleType);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties()));
        REGISTERED_DISPLAY_BLOCKS.add(block);
        return block;
    }

    private static void registerBlockItems() {
        for (Block block : REGISTERED_DISPLAY_BLOCKS) {
            if (block instanceof FoodBlock displayableBlock) {
                Item displayItem = displayableBlock.displayItem.get();
                FoodBlock.Registry.register(() -> displayItem, () -> block);
            }
        }
    }

    private static void registerConfigDisplayBlocks() {
        var configFile = FabricLoader.getInstance().getConfigDir().resolve("createfood-client.toml");
        if (!Files.exists(configFile)) return;

        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("display.display_block", List.of());
            for (String entry : entries) {
                String[] p = entry.split("\\|");
                if (p.length < 3 || p.length > 5) {
                    CreateFood.LOGGER.warn("Create: Food - Skipping invalid custom_display_block entry: {}", entry);
                    continue;
                }
                String modItemId = p[0];
                String typeStr   = p[1].toLowerCase();
                int    maxStack;
                try {
                    maxStack = Integer.parseInt(p[2]);
                } catch (NumberFormatException e) {
                    CreateFood.LOGGER.warn("Create: Food - Invalid max_stack in custom_display_block entry: {}", entry);
                    continue;
                }
                double height = 12;
                if (p.length >= 4) {
                    try {
                        height = Integer.parseInt(p[3]);
                    } catch (NumberFormatException e) {
                        CreateFood.LOGGER.warn("Create: Food - Invalid height in custom_display_block entry: {}", entry);
                        continue;
                    }
                }
                boolean hasParticles = p.length == 5 && Boolean.parseBoolean(p[4]);
                DisplayType displayType = switch (typeStr) {
                    case "plate"       -> DisplayType.PLATE;
                    case "small_plate" -> DisplayType.SMALL_PLATE;
                    case "bottle"      -> DisplayType.BOTTLE;
                    case "bowl"        -> DisplayType.BOWL;
                    case "salad_bowl"  -> DisplayType.SALAD_BOWL;
                    default            -> null;
                };
                if (displayType == null) {
                    CreateFood.LOGGER.warn("Create: Food - Unknown display_type '{}' in entry: {}", typeStr, entry);
                    continue;
                }
                ResourceLocation itemRL = ResourceLocation.tryParse(modItemId);
                if (itemRL == null) {
                    CreateFood.LOGGER.warn("Create: Food - Invalid item ID '{}' in entry: {}", modItemId, entry);
                    continue;
                }
                String blockName = DisplayBlockRegistry.getBlockName(itemRL.getPath(), displayType);

                Supplier<Item> itemSupplier = () -> {
                    try {
                        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(modItemId));
                        return item != Items.AIR ? item : Items.BARRIER;
                    } catch (Exception e) { return Items.BARRIER; }
                };
                registerDisplayBlockFromSupplier(blockName, itemSupplier,
                        new DisplayBlockConfig(displayType, maxStack, height, hasParticles,
                                hasParticles ? () -> ParticleTypes.WHITE_SMOKE : null));
            }
        } catch (Exception e) {
            CreateFood.LOGGER.warn("Create: Food - Failed to read custom_display_block from config", e);
        }
    }

    private static Block regBlock(String id, Block block) {
        Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, id), block);
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, id),
                new BlockItem(block, new Item.Properties()));
        return block;
    }

}
