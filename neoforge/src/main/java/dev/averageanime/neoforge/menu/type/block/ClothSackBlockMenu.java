package dev.averageanime.neoforge.menu.type.block;

import dev.averageanime.neoforge.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.neoforge.item.storage.StorageInventory;
import dev.averageanime.neoforge.menu.MenuRegistration;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ClothSackBlockMenu extends dev.averageanime.menu.type.block.ClothSackBlockMenu {

    public ClothSackBlockMenu(int containerId, Inventory playerInventory, ClothSackBlockEntity blockEntity) {
        super(MenuRegistration.CLOTH_SACK.get(), containerId, playerInventory, blockEntity);
    }

    @Override
    protected void addInventorySlots() {
        StorageInventory inv = ((ClothSackBlockEntity) blockEntity).storageInventory();
        int[] xs = {71, 89, 71, 89};
        int[] ys = {25, 25, 43, 43};
        for (int i = 0; i < 4; i++) {
            addSlot(new SlotItemHandler(inv, i, xs[i], ys[i]));
        }
    }
}
