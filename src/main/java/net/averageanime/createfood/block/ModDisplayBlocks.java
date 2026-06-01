package net.averageanime.createfood.block;

import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.display.*;
import net.averageanime.createfood.block.plate.EmptyPlateBlock;
import net.averageanime.createfood.block.plate.GenericDisplayPlateBlock;
import net.averageanime.createfood.block.plate.PlateBlock;
import net.averageanime.createfood.block.plate.SmallPlateBlock;
import net.averageanime.createfood.registry.DisplayBlockRegistry;
import net.averageanime.createfood.registry.DisplayBlockRegistry.DisplayBlockConfig;
import net.averageanime.createfood.registry.DisplayBlockRegistry.DisplayType;
import net.minecraft.core.particles.ParticleOptions;
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

        RegistryObject<Block> block = BLOCKS.register(blockName, () -> createBlock(itemSupplier, config));

        ITEMS.register(blockName, () -> new BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()));
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

        // Register the plate block items as "empty plate" items so they can't be placed on plates
        FoodBlock.Registry.registerEmptyPlateItem(() -> PLATE_BLOCK.get().asItem());
        FoodBlock.Registry.registerEmptyPlateItem(() -> SMALL_PLATE_BLOCK.get().asItem());
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Display Blocks");
        autoRegisterDisplayBlocks();

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
                event.enqueueWork(ModDisplayBlocks::registerBlockItems));
    }
}
