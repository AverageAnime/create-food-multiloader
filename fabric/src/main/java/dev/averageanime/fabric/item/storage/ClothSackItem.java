package dev.averageanime.fabric.item.storage;

import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.menu.type.ClothSackItemMenu;
import dev.averageanime.item.storage.IStorageItemHandler;
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
                () -> ModConfig.isClothSackStacking() ? 64 : 1,
                (slot, stack) -> ModConfig.isClothSackItemAllowed(stack));
    }
}
