package dev.averageanime.fabric.item.storage;

import dev.averageanime.fabric.menu.type.item.ClothSackItemMenu;
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
}
