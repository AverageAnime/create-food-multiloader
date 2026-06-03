package net.averageanime.createfood.block;

import net.averageanime.createfood.block.display.FoodBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static net.averageanime.createfood.CreateFood.LOGGER;

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
            if (registerPlateEntry(entry, ModDisplayBlocks.PLATE_BLOCK.get())) normalPlatesFound++;
        }

        for (PlateEntry entry : COMPATIBLE_SMALL_PLATES) {
            if (registerPlateEntry(entry, ModDisplayBlocks.SMALL_PLATE_BLOCK.get())) smallPlatesFound++;
        }

        if (normalPlatesFound == 0 && smallPlatesFound == 0) {
            LOGGER.debug("No compatible plate items from other mods found");
        }
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
            ResourceLocation itemRL = new ResourceLocation(modId, itemName);
            Item item = ForgeRegistries.ITEMS.getValue(itemRL);
            return item != null && item != Items.AIR ? item : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static Block getBlockIfExists(String modId, String blockName) {
        try {
            ResourceLocation blockRL = new ResourceLocation(modId, blockName);
            Block block = ForgeRegistries.BLOCKS.getValue(blockRL);
            return block != null && block != Blocks.AIR ? block : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean registerPlate(String modId, String itemName, String blockName, boolean isSmallPlate) {
        Block targetBlock = isSmallPlate ?
                ModDisplayBlocks.SMALL_PLATE_BLOCK.get() :
                ModDisplayBlocks.PLATE_BLOCK.get();
        return registerPlateEntry(new PlateEntry(modId, itemName, blockName), targetBlock);
    }

    private record PlateEntry(String modId, String itemName, String blockName) {}
}
