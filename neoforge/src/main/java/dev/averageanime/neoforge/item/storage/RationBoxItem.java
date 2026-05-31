package dev.averageanime.neoforge.item.storage;

import dev.averageanime.item.storage.IStorageItemHandler;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.menu.type.RationBoxItemMenu;
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
                slot -> ModConfig.isRationBoxStacking() ? 64 : 1,
                (slot, stack) -> ModConfig.isRationBoxItemAllowed(stack));
    }
}
