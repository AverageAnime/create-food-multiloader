package dev.averageanime.neoforge.menu.type;

import dev.averageanime.neoforge.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.neoforge.item.storage.StorageInventory;
import dev.averageanime.neoforge.menu.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ClothSackMenu extends dev.averageanime.menu.type.ClothSackMenu {

    public ClothSackMenu(int containerId, Inventory playerInventory, ClothSackBlockEntity blockEntity) {
        super(ModMenus.CLOTH_SACK.get(), containerId, playerInventory, blockEntity);
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
