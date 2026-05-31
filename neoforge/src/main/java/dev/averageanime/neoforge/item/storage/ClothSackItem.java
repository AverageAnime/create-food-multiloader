package dev.averageanime.neoforge.item.storage;

import dev.averageanime.item.storage.IStorageItemHandler;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.menu.type.ClothSackItemMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.level.block.Block;

public class ClothSackItem extends dev.averageanime.item.storage.ClothSackItem {

    public ClothSackItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected MenuConstructor menuConstructor(int slotIndex) {
        return (id, inv, p) -> new ClothSackItemMenu(id, inv, slotIndex);
    }

    @Override
    protected IStorageItemHandler createHandler() {
        return new StorageInventory(4,
                slot -> ModConfig.isClothSackStacking() ? 64 : 1,
                (slot, stack) -> ModConfig.isClothSackItemAllowed(stack));
    }
}
