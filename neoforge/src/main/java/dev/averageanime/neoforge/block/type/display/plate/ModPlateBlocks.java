package dev.averageanime.neoforge.block.type.display.plate;

import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.type.display.FoodBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

import static dev.averageanime.neoforge.CreateFood.LOGGER;

public class ModPlateBlocks {

    private static final List<PlateEntry> COMPATIBLE_PLATES = new ArrayList<>();
    private static final List<PlateEntry> COMPATIBLE_SMALL_PLATES = new ArrayList<>();

    static {
        COMPATIBLE_PLATES.add(new PlateEntry("displaydelight", "food_plate", "food_plate"));
        COMPATIBLE_SMALL_PLATES.add(new PlateEntry("displaydelight", "small_food_plate", "small_food_plate"));
    }

    public static void registerCompatiblePlates() {
        int normalPlatesFound = 0;
        int smallPlatesFound = 0;

        for (PlateEntry entry : COMPATIBLE_PLATES) {
            if (registerPlateEntry(entry, ModDisplayBlocks.PLATE_BLOCK.get(), "normal")) {
                normalPlatesFound++;
            }
        }

        for (PlateEntry entry : COMPATIBLE_SMALL_PLATES) {
            if (registerPlateEntry(entry, ModDisplayBlocks.SMALL_PLATE_BLOCK.get(), "small")) {
                smallPlatesFound++;
            }
        }

        if (normalPlatesFound > 0 || smallPlatesFound > 0) {
        } else {
            LOGGER.debug("No compatible plate items from other mods found");
        }
    }

    private static boolean registerPlateEntry(PlateEntry entry, Block targetBlock, String plateType) {
        boolean registered = false;
        Item plateItem = getItemIfExists(entry.modId, entry.itemName);
        if (plateItem != null && plateItem != Items.AIR) {
            FoodBlock.Registry.registerEmptyPlateItem(() -> plateItem);  // NEW LINE
            FoodBlock.Registry.register(() -> plateItem, () -> targetBlock);
            registered = true;
        }

        Block plateBlock = getBlockIfExists(entry.modId, entry.blockName);
        if (plateBlock != null && plateBlock != Blocks.AIR) {
            FoodBlock.Registry.registerCompatBlock(plateBlock, () -> targetBlock);
            registered = true;
        }

        return registered;
    }

    private static Item getItemIfExists(String modId, String itemName) {
        try {
            ResourceLocation itemRL = ResourceLocation.fromNamespaceAndPath(modId, itemName);
            Item item = BuiltInRegistries.ITEM.get(itemRL);
            return (item != null && item != Items.AIR) ? item : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static Block getBlockIfExists(String modId, String blockName) {
        try {
            ResourceLocation blockRL = ResourceLocation.fromNamespaceAndPath(modId, blockName);
            Block block = BuiltInRegistries.BLOCK.get(blockRL);
            return (block != null && block != Blocks.AIR) ? block : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Helper method to manually register a plate item and block from another mod.
     */
    public static boolean registerPlate(String modId, String itemName, String blockName, boolean isSmallPlate) {
        Block targetBlock = isSmallPlate ?
                ModDisplayBlocks.SMALL_PLATE_BLOCK.get() :
                ModDisplayBlocks.PLATE_BLOCK.get();

        PlateEntry entry = new PlateEntry(modId, itemName, blockName);
        return registerPlateEntry(entry, targetBlock, isSmallPlate ? "small" : "normal");
    }

    private static class PlateEntry {
        final String modId;
        final String itemName;
        final String blockName;

        PlateEntry(String modId, String itemName, String blockName) {
            this.modId = modId;
            this.itemName = itemName;
            this.blockName = blockName;
        }
    }
}