package dev.averageanime.fabric.item.storage;

import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.menu.type.RationBoxItemMenu;
import dev.averageanime.item.storage.IStorageItemHandler;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.level.block.Block;

public class RationBoxItem extends dev.averageanime.item.storage.RationBoxItem {

    public RationBoxItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected MenuConstructor menuConstructor(int slotIndex) {
        return (id, inv, p) -> new RationBoxItemMenu(id, inv, slotIndex);
    }

    @Override
    protected IStorageItemHandler createHandler() {
        return new StorageInventory(5,
                () -> ModConfig.isRationBoxStacking() ? 64 : 1,
                (slot, stack) -> ModConfig.isRationBoxItemAllowed(stack));
    }
}
