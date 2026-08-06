package dev.averageanime.block.type.plate;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class CompatPlateBlocks {

    private record PlateEntry(String modId, String itemName, String blockName) {}

    private static final List<PlateEntry> COMPATIBLE_PLATES =
            List.of(new PlateEntry("displaydelight", "food_plate", "food_plate"));
    private static final List<PlateEntry> COMPATIBLE_SMALL_PLATES =
            List.of(new PlateEntry("displaydelight", "small_food_plate", "small_food_plate"));

    private CompatPlateBlocks() {}

    public static void registerCompatiblePlates() {
        int found = 0;
        for (PlateEntry entry : COMPATIBLE_PLATES) {
            if (registerPlateEntry(entry, Services.PLATFORM.getPlateBlock())) found++;
        }
        for (PlateEntry entry : COMPATIBLE_SMALL_PLATES) {
            if (registerPlateEntry(entry, Services.PLATFORM.getSmallPlateBlock())) found++;
        }
        if (found == 0) {
            CreateFoodCommon.LOGGER.debug("Create: Food - No compatible plate items from other mods found");
        }
    }

    public static boolean registerPlate(String modId, String itemName, String blockName, boolean isSmallPlate) {
        Block targetBlock = isSmallPlate ? Services.PLATFORM.getSmallPlateBlock() : Services.PLATFORM.getPlateBlock();
        return registerPlateEntry(new PlateEntry(modId, itemName, blockName), targetBlock);
    }

    private static boolean registerPlateEntry(PlateEntry entry, Block targetBlock) {
        boolean registered = false;

        Item plateItem = getItemIfExists(entry.modId, entry.itemName);
        if (plateItem != null && plateItem != Items.AIR) {
            FoodBlock.Registry.registerEmptyPlateItem(() -> plateItem);
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
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(modId, itemName));
            return item != Items.AIR ? item : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static Block getBlockIfExists(String modId, String blockName) {
        try {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(modId, blockName));
            return block != Blocks.AIR ? block : null;
        } catch (Exception e) {
            return null;
        }
    }
}
