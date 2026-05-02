package dev.averageanime.fabric.block;

import dev.averageanime.fabric.CreateFood;
import dev.averageanime.block.type.display.*;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.fabric.block.handler.FoodPlacementHandler;
import dev.averageanime.fabric.item.ModItems;
import dev.averageanime.fabric.item.ModTooltips;
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

import java.lang.reflect.Field;
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

    private static final Set<String> EXCLUDED_ITEMS = Set.of(
            "apple_slice", "fish_sticks", "mozzarella_sticks", "cookie_crumbs",
            "chorus_fruit_slice", "waffle_cone", "meat_pie_filling", "dumpling_wrappers",
            "pumpkin_pie_block", "graham_cracker_chocolate_marshmallow",
            "graham_cracker_chocolate", "chocolate_graham_cracker_chocolate_ice_cream"
    );

    private static final Map<String, Object> DISPLAY_CONFIGS = new LinkedHashMap<>();

    static {
        registerMultiConfig("kelp_roll_slice",
                new DisplayBlockConfig(DisplayType.PLATE, 6),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE));
        registerMultiConfig("meringue_cookie",
                new DisplayBlockConfig(DisplayType.PLATE, 9),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE));
        registerConfig("kelp_roll", DisplayType.PLATE, 3);
        registerConfig("gelatin_dessert", DisplayType.PLATE, 6);
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
                new DisplayBlockConfig(DisplayType.SMALL_PLATE));
        registerConfig("cone", DisplayType.PLATE, 2);
        registerConfig("muffin", DisplayType.PLATE, 4);
        registerConfig("pastry", DisplayType.PLATE, 4);
        registerConfig("sweet_roll", DisplayType.PLATE, 4);
        registerConfig("donut", DisplayType.PLATE, 5);
        registerConfig("fudge", DisplayType.PLATE, 2);
        registerConfig("bar_of", DisplayType.PLATE, 6);
        registerConfig("popsicle", DisplayType.PLATE, 2);
        registerConfig("breakfast_bar", DisplayType.PLATE, 6);
        registerMultiConfig("baked_potato",
                new DisplayBlockConfig(DisplayType.PLATE, 3),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE));
        registerConfig("_chocolate", DisplayType.PLATE, 6);
    }

    public static void init() {
        CreateFood.LOGGER.info("Create: Food - Registering Display Blocks");

        SMALL_PLATE_BLOCK = regBlock("small_plate_block",
                new SmallPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> PLATE_BLOCK));
        PLATE_BLOCK = regBlock("plate_block",
                new EmptyPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),
                        () -> SMALL_PLATE_BLOCK));

        CREAM_SWEET_ROLL_PLATE = registerPlateBlock("cream_sweet_roll_plate_block",
                createCompatItemSupplier("create:sweet_roll"), 4);
        BAR_OF_CHOCOLATE_PLATE = registerPlateBlock("bar_of_chocolate_plate_block",
                createCompatItemSupplier("create:bar_of_chocolate"), 6);
        PUMPKIN_PIE_PLATE = registerPlateBlock("pumpkin_pie_plate_block",
                createCompatItemSupplier("minecraft:pumpkin_pie"), 1);
        BUILDERS_TEA_BLOCK = registerBottleBlock("builders_tea_bottle_block",
                createCompatItemSupplier("create:builders_tea"), 10, true, () -> ParticleTypes.WHITE_SMOKE);

        autoRegisterDisplayBlocks();
        registerConfigDisplayBlocks();
        registerBlockItems();
        ModPlateBlocks.registerCompatiblePlates();
        FoodPlacementHandler.register();
    }

    public static Block CREAM_SWEET_ROLL_PLATE;
    public static Block BAR_OF_CHOCOLATE_PLATE;
    public static Block PUMPKIN_PIE_PLATE;
    public static Block BUILDERS_TEA_BLOCK;

    private static void autoRegisterDisplayBlocks() {
        try {
            List<String> itemNames = getItemNames();
            for (String itemName : itemNames) {
                if (EXCLUDED_ITEMS.contains(itemName)) continue;
                if (itemName.contains("raw_") || itemName.contains("stick_1") ||
                        itemName.contains("stick_2") || itemName.contains("dough") ||
                        itemName.contains("chips") || itemName.contains("chocolate_berries") ||
                        itemName.contains("chocolate_apple") || itemName.contains("bread_slice") ||
                        itemName.contains("toast_slice") || itemName.contains("apple_slice") ||
                        itemName.contains("tropical_fish_slice") || itemName.contains("pretzel_stick") ||
                        itemName.contains("taco_shell") || itemName.contains("donut_hole") ||
                        itemName.contains("pie_crust") || itemName.contains("sliced")) continue;

                List<DisplayBlockConfig> configs = findMatchingConfigs(itemName);
                for (DisplayBlockConfig config : configs) {
                    String blockName = getBlockName(itemName, config.type);
                    registerDisplayBlock(blockName, itemName, config);
                }
            }
        } catch (Exception e) {
            CreateFood.LOGGER.error("Failed to auto-register display blocks", e);
        }
    }

    private static List<String> getItemNames() {
        List<String> itemNames = new ArrayList<>();
        for (Field field : ModItems.class.getDeclaredFields()) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                    !java.lang.reflect.Modifier.isPublic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof Item item) {
                    ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
                    if (key.getNamespace().equals(MOD_ID))
                        itemNames.add(key.getPath());
                }
            } catch (Exception ignored) {}
        }
        for (Field field : ModBlocks.class.getDeclaredFields()) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                    !java.lang.reflect.Modifier.isPublic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof Block block) {
                    ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
                    if (key.getNamespace().equals(MOD_ID) && !key.getPath().contains("_dessert_block"))
                        itemNames.add(key.getPath());
                }
            } catch (Exception ignored) {}
        }
        return itemNames;
    }

    private static List<DisplayBlockConfig> findMatchingConfigs(String itemName) {
        for (Map.Entry<String, Object> entry : DISPLAY_CONFIGS.entrySet()) {
            if (itemName.contains(entry.getKey())) {
                Object value = entry.getValue();
                if (value instanceof MultiDisplayConfig(List<DisplayBlockConfig> configs)) return configs;
                else if (value instanceof DisplayBlockConfig single) return Collections.singletonList(single);
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
        Supplier<Item> itemSupplier = resolveModItemSupplier(itemName);
        registerDisplayBlockFromSupplier(blockName, itemSupplier, config);
    }

    private static Supplier<Item> resolveModItemSupplier(String itemName) {
        return () -> {
            try {
                for (Field field : ModItems.class.getDeclaredFields()) {
                    if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                            !java.lang.reflect.Modifier.isPublic(field.getModifiers())) continue;
                    Object value = field.get(null);
                    if (value instanceof Item item) {
                        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
                        if (key.getPath().equals(itemName)) return item;
                    }
                }
                for (Field field : ModBlocks.class.getDeclaredFields()) {
                    if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                            !java.lang.reflect.Modifier.isPublic(field.getModifiers())) continue;
                    Object value = field.get(null);
                    if (value instanceof Block block) {
                        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
                        if (key.getPath().equals(itemName)) return block.asItem();
                    }
                }
            } catch (Exception e) {
                CreateFood.LOGGER.error("Failed to get item for display block: {}", itemName, e);
            }
            return Items.BARRIER;
        };
    }

    private static void registerDisplayBlockFromSupplier(String blockName,
                                                         Supplier<Item> itemSupplier,
                                                         DisplayBlockConfig config) {
        String suffixKey = switch (config.type) {
            case PLATE, SMALL_PLATE, PLATE_FOOD -> "display.createfood.suffix.plate";
            case BOWL, SALAD_BOWL               -> "display.createfood.suffix.bowl";
            case BOTTLE                         -> "display.createfood.suffix.bottle";
        };

        Block block = createBlock(itemSupplier, config);
        Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, blockName), block);
        REGISTERED_DISPLAY_BLOCKS.add(block);

        final Supplier<Item> finalItemSupplier = itemSupplier;
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, blockName),
                new BlockItem(block, new Item.Properties()) {
                    @Override
                    public @NotNull Component getName(@NotNull ItemStack stack) {
                        Item original = finalItemSupplier.get();
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
                        Item original = finalItemSupplier.get();
                        if (original != null && original != Items.BARRIER) {
                            original.appendHoverText(new ItemStack(original), context, components, flag);
                        }
                        super.appendHoverText(stack, context, components, flag);
                    }
                });
    }

    private static Block createBlock(Supplier<Item> itemSupplier, DisplayBlockConfig config) {
        return switch (config.type) {
            case PLATE      -> new PlateBlock(itemSupplier, config.maxStack, () -> PLATE_BLOCK);
            case SMALL_PLATE -> new SmallPlateFoodBlock(itemSupplier, () -> SMALL_PLATE_BLOCK);
            case BOTTLE     -> new BottleFoodBlock(itemSupplier, config.height,
                    config.hasParticles, config.particleType);
            case BOWL       -> new BowlFoodBlock(itemSupplier, config.height,
                    config.hasParticles, config.particleType);
            case SALAD_BOWL -> new SaladBowlFoodBlock(itemSupplier);
            case PLATE_FOOD -> new PlateFoodBlock(itemSupplier);
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
        for (Block block : List.of(CREAM_SWEET_ROLL_PLATE, BAR_OF_CHOCOLATE_PLATE,
                PUMPKIN_PIE_PLATE, BUILDERS_TEA_BLOCK)) {
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
                if (p.length != 3) {
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
                String blockName = itemRL.getNamespace() + "_"
                        + getBlockName(itemRL.getPath(), displayType);

                Supplier<Item> itemSupplier = createCompatItemSupplier(modItemId);
                registerDisplayBlockFromSupplier(blockName, itemSupplier,
                        new DisplayBlockConfig(displayType, maxStack));
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

    private enum DisplayType { PLATE, SMALL_PLATE, BOTTLE, BOWL, SALAD_BOWL, PLATE_FOOD }

    private record MultiDisplayConfig(List<DisplayBlockConfig> configs) {
        private MultiDisplayConfig(DisplayBlockConfig... configs) { this(Arrays.asList(configs)); }
    }

    private record DisplayBlockConfig(DisplayType type, int maxStack, double height,
                                      boolean hasParticles, Supplier<ParticleOptions> particleType) {
        DisplayBlockConfig(DisplayType type) { this(type, 1, 12, false, null); }
        DisplayBlockConfig(DisplayType type, int maxStack) { this(type, maxStack, 12, false, null); }
        DisplayBlockConfig(DisplayType type, double height) { this(type, 1, height, false, null); }
        DisplayBlockConfig(DisplayType type, double height, boolean hasParticles,
                           Supplier<ParticleOptions> particleType) {
            this(type, 1, height, hasParticles, particleType);
        }
    }
}