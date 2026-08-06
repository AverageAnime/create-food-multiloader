package dev.averageanime.fabric.item.storage;

import dev.averageanime.fabric.menu.type.item.RationBoxItemMenu;
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
}
