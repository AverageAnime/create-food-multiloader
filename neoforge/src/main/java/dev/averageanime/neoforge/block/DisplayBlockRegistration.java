package dev.averageanime.neoforge.block;

import dev.averageanime.config.ConfigBootstrap;
import dev.averageanime.config.ConfigDefaults;
import com.electronwill.nightconfig.core.file.FileConfig;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.display.DisplayBlocks;
import dev.averageanime.block.type.plate.CompatPlateBlocks;
import dev.averageanime.block.type.display.*;
import dev.averageanime.block.type.bowl.EmptyBowlBlock;
import dev.averageanime.block.type.bowl.EmptySmallBowlBlock;
import dev.averageanime.block.type.bowl.GenericDisplayBowlBlock;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.plate.EmptySmallPlateBlock;
import dev.averageanime.registry.DisplayRegistry;
import dev.averageanime.registry.DisplayRegistry.DisplayType;
import dev.averageanime.registry.type.DisplayEntry;
import dev.averageanime.registry.type.BlockEntry;
import dev.averageanime.registry.type.ItemEntry;
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
import static dev.averageanime.neoforge.item.ItemRegistration.ITEMS;
import static dev.averageanime.client.tooltip.ItemTooltips.addTooltip;

@SuppressWarnings("unused")
public class DisplayBlockRegistration {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateFoodCommon.MOD_ID);

    public static final DeferredBlock<net.minecraft.world.level.block.Block> SMALL_PLATE_BLOCK = BLOCKS.register("small_plate_block",
            () -> new EmptySmallPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.BOWL_BLOCK));

    public static final DeferredBlock<net.minecraft.world.level.block.Block> PLATE_BLOCK = BLOCKS.register("plate_block",
            () -> new EmptyPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.SMALL_PLATE_BLOCK));

    public static final DeferredBlock<net.minecraft.world.level.block.Block> BOWL_BLOCK = BLOCKS.register("bowl_block",
            () -> new EmptyBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.SMALL_BOWL_BLOCK));

    public static final DeferredBlock<net.minecraft.world.level.block.Block> SMALL_BOWL_BLOCK = BLOCKS.register("small_bowl_block",
            () -> new EmptySmallBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.PLATE_BLOCK));

    public static final DeferredBlock<net.minecraft.world.level.block.Block> GENERIC_DISPLAY_PLATE_BLOCK = BLOCKS.register("generic_display_plate_block",
            () -> new GenericDisplayPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.PLATE_BLOCK));

    public static final DeferredBlock<net.minecraft.world.level.block.Block> GENERIC_DISPLAY_BOWL_BLOCK = BLOCKS.register("generic_display_bowl_block",
            () -> new GenericDisplayBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.BOWL_BLOCK));

    public static void autoRegisterDisplayBlocks() {
        try {
            List<String> itemNames = getItemNames();

            for (String itemName : itemNames) {
                if (DisplayRegistry.EXCLUDED_ITEMS.contains(itemName)) continue;

                boolean skip = false;
                for (String pattern : DisplayRegistry.SKIP_PATTERNS) {
                    if (itemName.contains(pattern)) { skip = true; break; }
                }
                if (skip) continue;

                List<DisplayEntry> configs = DisplayRegistry.findMatchingConfigs(itemName);
                for (DisplayEntry config : configs) {
                    String blockName = DisplayRegistry.getBlockName(itemName, config.type());
                    registerDisplayBlock(blockName, itemName, config);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to auto-register display blocks", e);
        }
    }

    private static @NotNull List<String> getItemNames() {
        return DisplayRegistry.getItemNames();
    }

    private static void registerDisplayBlock(String blockName, String itemName,
                                             DisplayEntry config) {
        Supplier<net.minecraft.world.item.Item> itemSupplier = () -> {
            ItemEntry itemEntryDef = ItemEntry.ALL.stream()
                    .filter(d -> d.id.equals(itemName)).findFirst().orElse(null);
            if (itemEntryDef != null) return itemEntryDef.get();
            BlockEntry blockEntryDef = BlockEntry.getById(itemName);
            if (blockEntryDef != null) return blockEntryDef.get().asItem();
            return Items.BARRIER;
        };

        String suffixKey = DisplayBlocks.suffixKey(config.type());

        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(blockName, () ->
                DisplayBlocks.createBlock(itemSupplier, config)
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
                            dev.averageanime.item.effect.TooltipContext.runWith(originalStack, () ->
                                    originalItem.appendHoverText(originalStack, context, components, flag));
                        }
                        super.appendHoverText(stack, context, components, flag);
                    }
                }
        );
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
                            dev.averageanime.item.effect.TooltipContext.runWith(originalStack, () ->
                                    originalItem.appendHoverText(originalStack, context, components, flag));
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
                            dev.averageanime.item.effect.TooltipContext.runWith(originalStack, () ->
                                    originalItem.appendHoverText(originalStack, context, components, flag));
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
        {
            List<String> entries = ConfigBootstrap.read(
                    ConfigBootstrap.DISPLAY_BLOCK, ConfigDefaults.CUSTOM_DISPLAY_BLOCK_DEFAULT);
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
                        height = Double.parseDouble(p[3]);
                    } catch (NumberFormatException e) {
                        LOGGER.warn("Create: Food - Invalid height in custom_display_block entry: {}", entry);
                        continue;
                    }
                }
                // Fifth field: "true"/"false", or a particle id ("minecraft:snowflake").
                // "true" stays an alias for white smoke so existing configs keep working.
                boolean hasParticles = false;
                ParticleOptions particle = ParticleTypes.WHITE_SMOKE;
                if (p.length == 5 && !p[4].isBlank() && !p[4].equalsIgnoreCase("false")) {
                    hasParticles = true;
                    if (!p[4].equalsIgnoreCase("true")) {
                        ResourceLocation particleRL = ResourceLocation.tryParse(p[4]);
                        Object resolved = particleRL == null ? null : BuiltInRegistries.PARTICLE_TYPE.get(particleRL);
                        if (resolved instanceof ParticleOptions options) {
                            particle = options;
                        } else {
                            LOGGER.warn("Create: Food - Unknown or non-simple particle '{}' in display_block entry: {} (using white smoke)", p[4], entry);
                        }
                    }
                }
                final ParticleOptions particleType = particle;
                DisplayType displayType = switch (typeStr) {
                    case "plate"       -> DisplayType.PLATE;
                    case "small_plate" -> DisplayType.SMALL_PLATE;
                    case "bottle"      -> DisplayType.BOTTLE;
                    case "bowl"        -> DisplayType.BOWL_FOOD;
                    case "display_bowl" -> DisplayType.BOWL;
                    case "salad_bowl"  -> DisplayType.SMALL_BOWL;
                    case "small_bowl"  -> DisplayType.SMALL_BOWL;
                    case "plate_food"  -> DisplayType.PLATE_FOOD;
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
                String blockName = DisplayRegistry.getBlockName(itemRL.getPath(), displayType);
                Supplier<net.minecraft.world.item.Item> itemSupplier = () -> {
                    try {
                        net.minecraft.world.item.Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(modItemId));
                        return item != Items.AIR ? item : Items.BARRIER;
                    } catch (Exception e) { return Items.BARRIER; }
                };
                DisplayEntry config = new DisplayEntry(displayType, maxStack, height, hasParticles,
                        hasParticles ? () -> particleType : null);
                registerDisplayBlockFromSupplier(blockName, itemSupplier, config);
            }
        }
    }

    private static void registerDisplayBlockFromSupplier(String blockName, Supplier<net.minecraft.world.item.Item> itemSupplier,
                                                         DisplayEntry config) {
        String suffixKey = DisplayBlocks.suffixKey(config.type());

        DeferredBlock<net.minecraft.world.level.block.Block> block = BLOCKS.register(blockName, () -> DisplayBlocks.createBlock(itemSupplier, config));

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
                            ItemStack originalStack = new ItemStack(original);
                            dev.averageanime.item.effect.TooltipContext.runWith(originalStack, () ->
                                    original.appendHoverText(originalStack, context, components, flag));
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
        ITEMS.register("generic_display_bowl_block", () ->
                new BlockItem(GENERIC_DISPLAY_BOWL_BLOCK.get(), new net.minecraft.world.item.Item.Properties()));

        BLOCKS.register(eventBus);

        eventBus.addListener((net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) -> event.enqueueWork(() -> {
            registerBlockItems();
            CompatPlateBlocks.registerCompatiblePlates();
        }));
    }
}
