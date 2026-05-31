package dev.averageanime.neoforge.block;

import com.electronwill.nightconfig.core.file.FileConfig;
import dev.averageanime.CommonClass;
import dev.averageanime.block.type.display.*;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.registry.DisplayBlockRegistry;
import dev.averageanime.registry.DisplayBlockRegistry.DisplayBlockConfig;
import dev.averageanime.registry.DisplayBlockRegistry.DisplayType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.util.*;
import java.util.function.Supplier;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;
import static dev.averageanime.neoforge.item.ModTooltips.addTooltip;

@SuppressWarnings("unused")
public class ModDisplayBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CommonClass.MOD_ID);

    public static final DeferredBlock<net.minecraft.world.level.block.Block> SMALL_PLATE_BLOCK = BLOCKS.register("small_plate_block",
            () -> new SmallPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), ModDisplayBlocks.PLATE_BLOCK));

    public static final DeferredBlock<net.minecraft.world.level.block.Block> PLATE_BLOCK = BLOCKS.register("plate_block",
            () -> new EmptyPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), ModDisplayBlocks.SMALL_PLATE_BLOCK));

    public static final DeferredBlock<net.minecraft.world.level.block.Block> GENERIC_DISPLAY_PLATE_BLOCK = BLOCKS.register("generic_display_plate_block",
            () -> new GenericDisplayPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), ModDisplayBlocks.PLATE_BLOCK));

    public static void autoRegisterDisplayBlocks() {
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
            LOGGER.error("Failed to auto-register display blocks", e);
        }
    }

    private static @NotNull List<String> getItemNames() {
        return DisplayBlockRegistry.getItemNames();
    }

    private static void registerDisplayBlock(String blockName, String itemName,
                                             DisplayBlockConfig config) {
        Supplier<net.minecraft.world.item.Item> itemSupplier = () -> {
            dev.averageanime.registry.type.Item itemDef = dev.averageanime.registry.type.Item.ALL.stream()
                    .filter(d -> d.id.equals(itemName)).findFirst().orElse(null);
            if (itemDef != null) return itemDef.get();
            dev.averageanime.registry.type.Block blockDef = dev.averageanime.registry.type.Block.getById(itemName);
            if (blockDef != null) return blockDef.get().asItem();
            return Items.BARRIER;
        };

        String suffixKey = switch (config.type()) {
            case PLATE, SMALL_PLATE, PLATE_FOOD -> "display.createfood.suffix.plate";
            case BOWL, SALAD_BOWL               -> "display.createfood.suffix.bowl";
            case BOTTLE                         -> "display.createfood.suffix.bottle";
        };

        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(blockName, () ->
                createBlock(itemSupplier, config)
        );

        ITEMS.register(blockName, () ->
                new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()) {
                    @Override
                    public @NotNull Component getName(@NotNull ItemStack stack) {
                        net.minecraft.world.item.Item originalItem = itemSupplier.get();
                        if (originalItem != null && originalItem != Items.BARRIER) {
                            return Component.translatable(
                                    "display.createfood.format",
                                    originalItem.getDescription(),
                                    Component.translatable(suffixKey)
                            );
                        }
                        return super.getName(stack);
                    }

                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        net.minecraft.world.item.Item originalItem = itemSupplier.get();
                        if (originalItem != null && originalItem != Items.BARRIER) {
                            ItemStack originalStack = new ItemStack(originalItem);
                            originalItem.appendHoverText(originalStack, context, components, flag);
                        }
                        super.appendHoverText(stack, context, components, flag);
                    }
                }
        );
    }

    private static net.minecraft.world.level.block.Block createBlock(Supplier<net.minecraft.world.item.Item> itemSupplier,
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

    public static @NotNull DeferredBlock<net.minecraft.world.level.block.Block> registerPlateBlock(String name,
                                                                                                   Supplier<net.minecraft.world.item.Item> foodItem,
                                                                                                   int maxStack,
                                                                                                   String compatTooltip,
                                                                                                   String... ingredientTooltips) {
        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name,
                () -> new PlateBlock(foodItem, maxStack, PLATE_BLOCK));

        ITEMS.register(name,
                () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        addTooltip(components, compatTooltip, ingredientTooltips);
                        super.appendHoverText(stack, context, components, flag);
                    }
                });

        return block;
    }

    public static @NotNull DeferredBlock<net.minecraft.world.level.block.Block> registerPlateBlock(String name,
                                                                                                   Supplier<net.minecraft.world.item.Item> foodItem,
                                                                                                   int maxStack) {
        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name,
                () -> new PlateBlock(foodItem, maxStack, PLATE_BLOCK));

        ITEMS.register(name,
                () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()));

        return block;
    }

    public static @NotNull DeferredBlock<net.minecraft.world.level.block.Block> registerPlateBlockFromBlock(String name,
                                                                                                            Supplier<net.minecraft.world.level.block.Block> foodBlock,
                                                                                                            int maxStack) {
        Supplier<net.minecraft.world.item.Item> itemSupplier = () -> foodBlock.get().asItem();

        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name,
                () -> new PlateBlock(itemSupplier, maxStack, PLATE_BLOCK));

        ITEMS.register(name,
                () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        net.minecraft.world.item.Item originalItem = itemSupplier.get();
                        if (originalItem != null && originalItem != Items.BARRIER) {
                            ItemStack originalStack = new ItemStack(originalItem);
                            originalItem.appendHoverText(originalStack, context, components, flag);
                        }
                        super.appendHoverText(stack, context, components, flag);
                    }
                });

        return block;
    }

    public static @NotNull DeferredBlock<net.minecraft.world.level.block.Block> registerPlateBlockFromBlock(String name,
                                                                                                            Supplier<net.minecraft.world.level.block.Block> foodBlock,
                                                                                                            int maxStack,
                                                                                                            String compatTooltip,
                                                                                                            String... ingredientTooltips) {
        Supplier<net.minecraft.world.item.Item> itemSupplier = () -> foodBlock.get().asItem();

        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name,
                () -> new PlateBlock(itemSupplier, maxStack, PLATE_BLOCK));

        ITEMS.register(name,
                () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        net.minecraft.world.item.Item originalItem = itemSupplier.get();
                        if (originalItem != null && originalItem != Items.BARRIER) {
                            ItemStack originalStack = new ItemStack(originalItem);
                            originalItem.appendHoverText(originalStack, context, components, flag);
                        }
                        addTooltip(components, compatTooltip, ingredientTooltips);
                        super.appendHoverText(stack, context, components, flag);
                    }
                });

        return block;
    }

    public static @NotNull DeferredBlock<net.minecraft.world.level.block.Block> registerBottleBlock(String name,
                                                                                                    Supplier<net.minecraft.world.item.Item> foodItem,
                                                                                                    double heightInPixels,
                                                                                                    boolean hasParticles,
                                                                                                    Supplier<ParticleOptions> particleType,
                                                                                                    String compatTooltip,
                                                                                                    String... ingredientTooltips) {
        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(name,
                () -> new BottleFoodBlock(foodItem, heightInPixels, hasParticles, particleType));

        if ((compatTooltip != null && !compatTooltip.isEmpty()) ||
                (ingredientTooltips != null && ingredientTooltips.length > 0)) {
            ITEMS.register(name,
                    () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()) {
                        @Override
                        public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                    @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                            addTooltip(components, compatTooltip, ingredientTooltips);
                            super.appendHoverText(stack, context, components, flag);
                        }
                    });
        } else {
            ITEMS.register(name, () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()));
        }

        return block;
    }

    public static void registerBlockItems() {
        BLOCKS.getEntries().forEach(blockEntry -> {
            net.minecraft.world.level.block.Block block = blockEntry.get();
            if (block instanceof FoodBlock displayableBlock) {
                net.minecraft.world.item.Item displayItem = displayableBlock.displayItem.get();
                FoodBlock.Registry.register(() -> displayItem, (Supplier<net.minecraft.world.level.block.Block>) blockEntry);
            }
        });

    }

    private static void registerConfigDisplayBlocks() {
        var configFile = FMLPaths.CONFIGDIR.get().resolve("createfood-client.toml");
        if (!Files.exists(configFile)) return;

        try (FileConfig raw = FileConfig.of(configFile)) {
            raw.load();
            List<String> entries = raw.getOrElse("display.display_block", List.of());
            for (String entry : entries) {
                String[] p = entry.split("\\|");
                if (p.length < 3 || p.length > 5) {
                    LOGGER.warn("Create: Food - Skipping invalid custom_display_block entry: {}", entry);
                    continue;
                }
                String modItemId = p[0];
                String typeStr = p[1].toLowerCase();
                int maxStack;
                try {
                    maxStack = Integer.parseInt(p[2]);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Create: Food - Invalid max_stack in custom_display_block entry: {}", entry);
                    continue;
                }
                double height = 12;
                if (p.length >= 4) {
                    try {
                        height = Integer.parseInt(p[3]);
                    } catch (NumberFormatException e) {
                        LOGGER.warn("Create: Food - Invalid height in custom_display_block entry: {}", entry);
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
                    LOGGER.warn("Create: Food - Unknown display_type '{}' in custom_display_block entry: {}", typeStr, entry);
                    continue;
                }
                ResourceLocation itemRL = ResourceLocation.tryParse(modItemId);
                if (itemRL == null) {
                    LOGGER.warn("Create: Food - Invalid item ID '{}' in custom_display_block entry: {}", modItemId, entry);
                    continue;
                }
                String blockName = DisplayBlockRegistry.getBlockName(itemRL.getPath(), displayType);
                Supplier<net.minecraft.world.item.Item> itemSupplier = () -> {
                    try {
                        net.minecraft.world.item.Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(modItemId));
                        return item != Items.AIR ? item : Items.BARRIER;
                    } catch (Exception e) { return Items.BARRIER; }
                };
                DisplayBlockConfig config = new DisplayBlockConfig(displayType, maxStack, height, hasParticles,
                        hasParticles ? () -> ParticleTypes.WHITE_SMOKE : null);
                registerDisplayBlockFromSupplier(blockName, itemSupplier, config);
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_display_block from config", e);
        }
    }

    private static void registerDisplayBlockFromSupplier(String blockName, Supplier<net.minecraft.world.item.Item> itemSupplier,
                                                         DisplayBlockConfig config) {
        String suffixKey = switch (config.type()) {
            case PLATE, SMALL_PLATE, PLATE_FOOD -> "display.createfood.suffix.plate";
            case BOWL, SALAD_BOWL               -> "display.createfood.suffix.bowl";
            case BOTTLE                         -> "display.createfood.suffix.bottle";
        };

        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(blockName, () -> createBlock(itemSupplier, config));

        ITEMS.register(blockName, () ->
                new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()) {
                    @Override
                    public @NotNull Component getName(@NotNull ItemStack stack) {
                        net.minecraft.world.item.Item original = itemSupplier.get();
                        if (original != null && original != Items.BARRIER) {
                            return Component.translatable(
                                    "display.createfood.format",
                                    original.getDescription(),
                                    Component.translatable(suffixKey)
                            );
                        }
                        return super.getName(stack);
                    }

                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        net.minecraft.world.item.Item original = itemSupplier.get();
                        if (original != null && original != Items.BARRIER) {
                            original.appendHoverText(new ItemStack(original), context, components, flag);
                        }
                        super.appendHoverText(stack, context, components, flag);
                    }
                }
        );
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Display Blocks");
        autoRegisterDisplayBlocks();
        registerConfigDisplayBlocks();
        ITEMS.register("generic_display_plate_block", () ->
                new BlockItem(GENERIC_DISPLAY_PLATE_BLOCK.get(), new net.minecraft.world.item.Item.Properties()));

        BLOCKS.register(eventBus);

        eventBus.addListener((net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) -> event.enqueueWork(() -> {
            registerBlockItems();
            ModPlateBlocks.registerCompatiblePlates();
        }));
    }
}
