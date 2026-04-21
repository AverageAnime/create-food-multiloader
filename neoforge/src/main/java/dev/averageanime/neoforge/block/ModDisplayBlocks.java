package dev.averageanime.neoforge.block;

import com.electronwill.nightconfig.core.file.FileConfig;
import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.type.display.*;
import dev.averageanime.neoforge.block.type.plate.EmptyPlateBlock;
import dev.averageanime.neoforge.block.type.plate.ModPlateBlocks;
import dev.averageanime.neoforge.block.type.plate.PlateBlock;
import dev.averageanime.neoforge.block.type.plate.SmallPlateBlock;
import dev.averageanime.neoforge.item.ModItems;
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

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Supplier;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;
import static dev.averageanime.neoforge.item.ModTooltips.addTooltip;

@SuppressWarnings("unused")
public class ModDisplayBlocks {
    private static final Map<String, Object> DATAPACK_CONFIGS = new LinkedHashMap<>();

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CommonClass.MOD_ID);

    public static final DeferredBlock<Block> SMALL_PLATE_BLOCK = BLOCKS.register("small_plate_block",
            () -> new SmallPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> PLATE_BLOCK = BLOCKS.register("plate_block",
            () -> new EmptyPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), SMALL_PLATE_BLOCK));

    private static final Set<String> EXCLUDED_ITEMS = Set.of(
            "apple_slice",
            "fish_sticks",
            "mozzarella_sticks",
            "cookie_crumbs",
            "chorus_fruit_slice",
            "waffle_cone",
            "meat_pie_filling",
            "dumpling_wrappers",
            "pumpkin_pie_block",
            "graham_cracker_chocolate_marshmallow",
            "graham_cracker_chocolate",
            "chocolate_graham_cracker_chocolate_ice_cream"
            );

    private static final Map<String, Object> DISPLAY_CONFIGS = new LinkedHashMap<>();

    static {
        registerMultiConfig("kelp_roll_slice",
                new DisplayBlockConfig(DisplayType.PLATE, 6),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE)
        );
        registerMultiConfig("meringue_cookie",
                new DisplayBlockConfig(DisplayType.PLATE, 9),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE)
        );
        registerConfig("kelp_roll", DisplayType.PLATE, 3);
        registerConfig("gelatin_dessert", DisplayType.PLATE, 6);//
        registerConfig("slice", DisplayType.SMALL_PLATE);
        registerConfig("cream_mini_waffle", DisplayType.PLATE, 1);
        registerConfig("honeyed_mini_waffle", DisplayType.PLATE, 1);
        registerConfig("mini_waffle", DisplayType.PLATE, 4);

        registerConfig("pizza", DisplayType.PLATE, 1);
        registerConfig("cheese_block", DisplayType.PLATE, 1);
        registerConfig("gyro", DisplayType.PLATE, 1);
        registerConfig("waffle", DisplayType.PLATE, 1);
        registerConfig("cupcake", DisplayType.PLATE, 4);
        registerConfig("cake", DisplayType.PLATE, 1);
        registerConfig("mini_cream_pie", DisplayType.SMALL_PLATE);
        registerConfig("mini_smores_pie", DisplayType.SMALL_PLATE);
        registerConfig("mini_cookie_cream_pie", DisplayType.SMALL_PLATE);
        registerConfig("mini_chocolate_pie", DisplayType.SMALL_PLATE);
        registerConfig("pie", DisplayType.PLATE, 1);
        registerConfig("burger", DisplayType.PLATE, 1);
        registerConfig("meatball_sandwich", DisplayType.PLATE, 2);
        registerConfig("hash_brown_sandwich", DisplayType.PLATE, 2);
        registerConfig("sandwich", DisplayType.PLATE, 1);
        registerConfig("toast_plate", DisplayType.PLATE_FOOD);
        registerConfig("toast_fried_egg_plate", DisplayType.PLATE_FOOD);
        registerConfig("toast", DisplayType.PLATE, 1);
        registerConfig("calzone", DisplayType.PLATE, 2);
        registerConfig("smore", DisplayType.PLATE, 1);

        registerConfig("hot_chocolate_bottle", DisplayType.BOTTLE, 8, true, () -> ParticleTypes.WHITE_SMOKE);
        registerConfig("hot_dark_chocolate_bottle", DisplayType.BOTTLE, 8, true, () -> ParticleTypes.WHITE_SMOKE);
        registerConfig("hot_white_chocolate_bottle", DisplayType.BOTTLE, 8, true, () -> ParticleTypes.WHITE_SMOKE);

        registerConfig("_jam_bottle", DisplayType.BOTTLE, 9, false, null);
        registerConfig("taco_sauce_bottle", DisplayType.BOTTLE, 9, false, null);
        registerConfig("sugar_cane_juice_bottle", DisplayType.BOTTLE, 9, false, null);
        registerConfig("egg_whites_bottle", DisplayType.BOTTLE, 9, false, null);
        registerConfig("_juice_bottle", DisplayType.BOTTLE, 10, false, null);
        registerConfig("chocolate_bottle", DisplayType.BOTTLE, 8, false, null);
        registerConfig("dark_chocolate_bottle", DisplayType.BOTTLE, 8, false, null);
        registerConfig("white_chocolate_bottle", DisplayType.BOTTLE, 8, false, null);
        registerConfig("chocolate_milk_bottle", DisplayType.BOTTLE, 8, false, null);
        registerConfig("fruit_smoothie_bottle", DisplayType.BOTTLE, 8, false, null);
        registerConfig("_bottle", DisplayType.BOTTLE, 12, false, null);

        registerConfig("ice_cream_bowl", DisplayType.BOWL, 4.5, true, () -> ParticleTypes.SNOWFLAKE);
        registerConfig("soup_bowl", DisplayType.BOWL, 4, true, () -> ParticleTypes.WHITE_SMOKE);
        registerConfig("stew_bowl", DisplayType.BOWL, 4, true, () -> ParticleTypes.WHITE_SMOKE);
        registerConfig("_bowl", DisplayType.BOWL, 4, false, null);

        registerConfig("salad", DisplayType.SALAD_BOWL);

        registerConfig("pasta_plate", DisplayType.PLATE_FOOD);
        registerConfig("breakfast_plate", DisplayType.PLATE_FOOD);
        registerConfig("egg_plate", DisplayType.PLATE_FOOD);
        registerConfig("eggs_plate", DisplayType.PLATE_FOOD);
        registerConfig("hash_brown_plate", DisplayType.PLATE_FOOD);
        registerConfig("cookie", DisplayType.PLATE, 4);
        registerConfig("wrap", DisplayType.PLATE, 2);
        registerConfig("taco", DisplayType.PLATE, 2);
        registerConfig("burrito", DisplayType.PLATE, 2);
        registerConfig("ice_cream_stick", DisplayType.PLATE, 2);
        registerConfig("corn_stick", DisplayType.PLATE, 2);
        registerConfig("cotton_candy_stick", DisplayType.PLATE, 2);
        registerConfig("stick", DisplayType.PLATE, 3);
        registerMultiConfig("scone",
                new DisplayBlockConfig(DisplayType.PLATE, 4),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE)
        );
        registerConfig("cone", DisplayType.PLATE, 2);
        registerConfig("muffin", DisplayType.PLATE, 4);//
        registerConfig("pastry", DisplayType.PLATE, 4);//
        registerConfig("sweet_roll", DisplayType.PLATE, 4);//
        registerConfig("donut", DisplayType.PLATE, 5);//
        registerConfig("fudge", DisplayType.PLATE, 2);//
        registerConfig("bar_of", DisplayType.PLATE, 6);
        registerConfig("popsicle", DisplayType.PLATE, 2);
        registerConfig("breakfast_bar", DisplayType.PLATE, 6);
        registerMultiConfig("baked_potato",
                new DisplayBlockConfig(DisplayType.PLATE, 3),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE)
        );
        registerConfig("_chocolate", DisplayType.PLATE, 6);
    }

    private static void registerConfig(String pattern, DisplayType type) {
        DISPLAY_CONFIGS.put(pattern, new DisplayBlockConfig(type));
    }

    private static void registerConfig(String pattern, DisplayType type, int maxStack) {
        DISPLAY_CONFIGS.put(pattern, new DisplayBlockConfig(type, maxStack));
    }

    private static void registerConfig(String pattern, DisplayType type, double height) {
        DISPLAY_CONFIGS.put(pattern, new DisplayBlockConfig(type, height));
    }

    private static void registerConfig(String pattern, DisplayType type, double height,
                                       boolean hasParticles, Supplier<ParticleOptions> particles) {
        DISPLAY_CONFIGS.put(pattern, new DisplayBlockConfig(type, height, hasParticles, particles));
    }

    private static void registerMultiConfig(String pattern, DisplayBlockConfig... configs) {
        DISPLAY_CONFIGS.put(pattern, new MultiDisplayConfig(configs));
    }

    public static void autoRegisterDisplayBlocks() {
        try {
            List<String> itemNames = getItemNames();

            for (String itemName : itemNames) {
                if (EXCLUDED_ITEMS.contains(itemName)) {
                    continue;
                }

                if (itemName.contains("raw_") ||
                        itemName.contains("stick_1") ||
                        itemName.contains("stick_2") ||
                        itemName.contains("dough") ||
                        itemName.contains("chips") ||
                        itemName.contains("chocolate_berries") ||
                        itemName.contains("chocolate_apple") ||
                        itemName.contains("bread_slice") ||
                        itemName.contains("toast_slice") ||
                        itemName.contains("apple_slice") ||
                        itemName.contains("tropical_fish_slice") ||
                        itemName.contains("pretzel_stick") ||
                        itemName.contains("taco_shell") ||
                        itemName.contains("donut_hole") ||
                        itemName.contains("pie_crust") ||
                        itemName.contains("sliced")) {
                    continue;
                }

                List<DisplayBlockConfig> configs = findMatchingConfigs(itemName);

                for (DisplayBlockConfig config : configs) {
                    String blockName = getBlockName(itemName, config.type);
                    registerDisplayBlock(blockName, itemName, config);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to auto-register display blocks", e);
        }
    }

    private static @NotNull List<String> getItemNames() {
        List<String> itemNames = new ArrayList<>();

        for (Field field : ModItems.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                    java.lang.reflect.Modifier.isPublic(field.getModifiers())) {

                try {
                    Object value = field.get(null);
                    if (value instanceof net.neoforged.neoforge.registries.DeferredItem<?> deferredItem) {
                        String itemName = deferredItem.getId().getPath();
                        itemNames.add(itemName);
                    }
                } catch (Exception ignored) {
                }
            }
        }

        for (Field field : ModBlocks.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                    java.lang.reflect.Modifier.isPublic(field.getModifiers())) {

                try {
                    Object value = field.get(null);
                    if (value instanceof DeferredBlock<?> deferredBlock) {
                        String blockName = deferredBlock.getId().getPath();
                        if (!blockName.contains("_dessert_block")) {
                            itemNames.add(blockName);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return itemNames;
    }

    private static List<DisplayBlockConfig> findMatchingConfigs(String itemName) {
        for (Map.Entry<String, Object> entry : DISPLAY_CONFIGS.entrySet()) {
            if (itemName.contains(entry.getKey())) {
                Object value = entry.getValue();
                if (value instanceof MultiDisplayConfig(List<DisplayBlockConfig> configs)) {
                    return configs;
                } else if (value instanceof DisplayBlockConfig single) {
                    return Collections.singletonList(single);
                }
            }
        }
        return Collections.emptyList();
    }

    private static String getBlockName(String itemName, DisplayType type) {
        return switch (type) {
            case PLATE -> itemName + "_plate_block";
            case SMALL_PLATE -> itemName + "_small_plate_block";
            case SALAD_BOWL -> itemName + "_bowl_block";
            default -> itemName + "_block";
        };
    }

    private static void registerDisplayBlock(String blockName, String itemName,
                                             DisplayBlockConfig config) {
        Supplier<Item> itemSupplier = () -> {
            try {
                for (Field field : ModItems.class.getDeclaredFields()) {
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                            java.lang.reflect.Modifier.isPublic(field.getModifiers())) {

                        Object value = field.get(null);
                        if (value instanceof net.neoforged.neoforge.registries.DeferredItem<?> deferredItem) {
                            if (deferredItem.getId().getPath().equals(itemName)) {
                                return deferredItem.get();
                            }
                        }
                    }
                }

                for (Field field : ModBlocks.class.getDeclaredFields()) {
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                            java.lang.reflect.Modifier.isPublic(field.getModifiers())) {

                        Object value = field.get(null);
                        if (value instanceof net.neoforged.neoforge.registries.DeferredBlock<?> deferredBlock) {
                            if (deferredBlock.getId().getPath().equals(itemName)) {
                                return deferredBlock.get().asItem();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Failed to get item for display block: {}", itemName, e);
            }
            return Items.BARRIER;
        };

        String suffixKey = switch (config.type) {
            case PLATE, SMALL_PLATE, PLATE_FOOD -> "display.createfood.suffix.plate";
            case BOWL, SALAD_BOWL -> "display.createfood.suffix.bowl";
            case BOTTLE -> "display.createfood.suffix.bottle";
        };

        DeferredBlock<Block> block = BLOCKS.register(blockName, () ->
                createBlock(itemSupplier, config)
        );

        ITEMS.register(blockName, () ->
                new BlockItem(block.get(), new Item.Properties()) {
                    @Override
                    public @NotNull Component getName(@NotNull ItemStack stack) {
                        Item originalItem = itemSupplier.get();
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
                        Item originalItem = itemSupplier.get();
                        if (originalItem != null && originalItem != Items.BARRIER) {
                            ItemStack originalStack = new ItemStack(originalItem);
                            originalItem.appendHoverText(originalStack, context, components, flag);
                        }
                        super.appendHoverText(stack, context, components, flag);
                    }
                }
        );
    }

    private static Block createBlock(Supplier<Item> itemSupplier,
                                     DisplayBlockConfig config) {
        return switch (config.type) {
            case PLATE -> new PlateBlock(itemSupplier, config.maxStack, PLATE_BLOCK);
            case SMALL_PLATE -> new SmallPlateFoodBlock(itemSupplier, SMALL_PLATE_BLOCK);
            case BOTTLE -> new BottleFoodBlock(itemSupplier, config.height,
                    config.hasParticles, config.particleType);
            case BOWL -> new BowlFoodBlock(itemSupplier, config.height,
                    config.hasParticles, config.particleType);
            case SALAD_BOWL -> new SaladBowlFoodBlock(itemSupplier);
            case PLATE_FOOD -> new PlateFoodBlock(itemSupplier);
        };
    }

    public static @NotNull DeferredBlock<Block> registerPlateBlock(String name,
                                                                   Supplier<Item> foodItem,
                                                                   int maxStack,
                                                                   String compatTooltip,
                                                                   String... ingredientTooltips) {
        DeferredBlock<Block> block = BLOCKS.register(name,
                () -> new PlateBlock(foodItem, maxStack, PLATE_BLOCK));

        ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        addTooltip(components, compatTooltip, ingredientTooltips);
                        super.appendHoverText(stack, context, components, flag);
                    }
                });

        return block;
    }

    public static @NotNull DeferredBlock<Block> registerPlateBlock(String name,
                                                                   Supplier<Item> foodItem,
                                                                   int maxStack) {
        DeferredBlock<Block> block = BLOCKS.register(name,
                () -> new PlateBlock(foodItem, maxStack, PLATE_BLOCK));

        ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()) {});

        return block;
    }

    public static @NotNull DeferredBlock<Block> registerPlateBlockFromBlock(String name,
                                                                            Supplier<Block> foodBlock,
                                                                            int maxStack) {
        Supplier<Item> itemSupplier = () -> foodBlock.get().asItem();

        DeferredBlock<Block> block = BLOCKS.register(name,
                () -> new PlateBlock(itemSupplier, maxStack, PLATE_BLOCK));

        ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        Item originalItem = itemSupplier.get();
                        if (originalItem != null && originalItem != Items.BARRIER) {
                            ItemStack originalStack = new ItemStack(originalItem);
                            originalItem.appendHoverText(originalStack, context, components, flag);
                        }
                        super.appendHoverText(stack, context, components, flag);
                    }
                });

        return block;
    }

    public static @NotNull DeferredBlock<Block> registerPlateBlockFromBlock(String name,
                                                                            Supplier<Block> foodBlock,
                                                                            int maxStack,
                                                                            String compatTooltip,
                                                                            String... ingredientTooltips) {
        Supplier<Item> itemSupplier = () -> foodBlock.get().asItem();

        DeferredBlock<Block> block = BLOCKS.register(name,
                () -> new PlateBlock(itemSupplier, maxStack, PLATE_BLOCK));

        ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()) {
                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                        Item originalItem = itemSupplier.get();
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

    public static @NotNull DeferredBlock<Block> registerBottleBlock(String name,
                                                                    Supplier<Item> foodItem,
                                                                    double heightInPixels,
                                                                    boolean hasParticles,
                                                                    Supplier<ParticleOptions> particleType,
                                                                    String compatTooltip,
                                                                    String... ingredientTooltips) {
        DeferredBlock<Block> block = BLOCKS.register(name,
                () -> new BottleFoodBlock(foodItem, heightInPixels, hasParticles, particleType));

        if ((compatTooltip != null && !compatTooltip.isEmpty()) ||
                (ingredientTooltips != null && ingredientTooltips.length > 0)) {
            ITEMS.register(name,
                    () -> new BlockItem(block.get(), new Item.Properties()) {
                        @Override
                        public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                                    @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                            addTooltip(components, compatTooltip, ingredientTooltips);
                            super.appendHoverText(stack, context, components, flag);
                        }
                    });
        } else {
            ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        }

        return block;
    }

    public static final DeferredBlock<Block> CREAM_SWEET_ROLL_PLATE =
            registerPlateBlock("cream_sweet_roll_plate_block",
                    createCompatItemSupplier("create:sweet_roll"), 4, null, "tooltip.createfood.cream_frosting_ingredient");

    public static final DeferredBlock<Block> BAR_OF_CHOCOLATE_PLATE =
            registerPlateBlock("bar_of_chocolate_plate_block",
                    createCompatItemSupplier("create:bar_of_chocolate"), 6);

    public static final DeferredBlock<Block> PUMPKIN_PIE_PLATE =
            registerPlateBlock("pumpkin_pie_plate_block",
                    createCompatItemSupplier("minecraft:pumpkin_pie"), 1);

    public static final DeferredBlock<Block> BUILDERS_TEA_BLOCK =
            registerBottleBlock("builders_tea_bottle_block",
                    createCompatItemSupplier("create:builders_tea"), 10, true, () -> ParticleTypes.WHITE_SMOKE, null);

    private static Supplier<Item> createCompatItemSupplier(String modItemId) {
        return () -> {
            try {
                ResourceLocation itemRL = ResourceLocation.parse(modItemId);
                Item item = BuiltInRegistries.ITEM.get(itemRL);
                return item != Items.AIR ? item : Items.BARRIER;
            } catch (Exception e) {
                return Items.BARRIER;
            }
        };
    }

    public static void registerBlockItems() {
        BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof FoodBlock displayableBlock) {
                Item displayItem = displayableBlock.displayItem.get();
                FoodBlock.Registry.register(() -> displayItem, (Supplier<Block>) blockEntry);
            }
        });
    }

    private static void registerConfigDisplayBlocks() {
        var configFile = FMLPaths.CONFIGDIR.get().resolve("createfood-client.toml");
        if (!Files.exists(configFile)) return;

        try (FileConfig raw = FileConfig.of(configFile)) {
            raw.load();
            List<String> entries = raw.getOrElse("display.custom_display_block", List.of());
            for (String entry : entries) {
                String[] p = entry.split("\\|");
                if (p.length != 3) {
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
                String blockName = itemRL.getNamespace() + "_" + getBlockName(itemRL.getPath(), displayType);
                DisplayBlockConfig config = new DisplayBlockConfig(displayType, maxStack);
                registerDisplayBlockFromSupplier(blockName, createCompatItemSupplier(modItemId), config);
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_display_block from config", e);
        }
    }

    private static void registerDisplayBlockFromSupplier(String blockName, Supplier<Item> itemSupplier,
                                                         DisplayBlockConfig config) {
        String suffixKey = switch (config.type) {
            case PLATE, SMALL_PLATE, PLATE_FOOD -> "display.createfood.suffix.plate";
            case BOWL, SALAD_BOWL               -> "display.createfood.suffix.bowl";
            case BOTTLE                         -> "display.createfood.suffix.bottle";
        };

        DeferredBlock<Block> block = BLOCKS.register(blockName, () -> createBlock(itemSupplier, config));

        ITEMS.register(blockName, () ->
                new BlockItem(block.get(), new Item.Properties()) {
                    @Override
                    public @NotNull Component getName(@NotNull ItemStack stack) {
                        Item original = itemSupplier.get();
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
                        Item original = itemSupplier.get();
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
        BLOCKS.register(eventBus);

        eventBus.addListener((net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) -> {
            event.enqueueWork(() -> {
                registerBlockItems();
                ModPlateBlocks.registerCompatiblePlates();
            });
        });
    }

    private enum DisplayType {
        PLATE, SMALL_PLATE, BOTTLE, BOWL, SALAD_BOWL, PLATE_FOOD
    }

    private record MultiDisplayConfig(List<DisplayBlockConfig> configs) {
            private MultiDisplayConfig(DisplayBlockConfig... configs) {
                this(Arrays.asList(configs));
            }
        }

    private record DisplayBlockConfig(DisplayType type, int maxStack, double height, boolean hasParticles,
                                      Supplier<ParticleOptions> particleType) {
            DisplayBlockConfig(DisplayType type) {
                this(type, 1, 12, false, null);
            }

            DisplayBlockConfig(DisplayType type, int maxStack) {
                this(type, maxStack, 12, false, null);
            }

            DisplayBlockConfig(DisplayType type, double height) {
                this(type, 1, height, false, null);
            }

            DisplayBlockConfig(DisplayType type, double height, boolean hasParticles,
                               Supplier<ParticleOptions> particleType) {
                this(type, 1, height, hasParticles, particleType);
            }

    }
}