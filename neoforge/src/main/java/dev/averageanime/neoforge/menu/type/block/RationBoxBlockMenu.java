package dev.averageanime.neoforge.menu.type.block;

import dev.averageanime.neoforge.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.neoforge.item.storage.StorageInventory;
import dev.averageanime.neoforge.menu.MenuRegistration;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;

public class RationBoxBlockMenu extends dev.averageanime.menu.type.block.RationBoxBlockMenu {

    public RationBoxBlockMenu(int containerId, Inventory playerInventory, RationBoxBlockEntity blockEntity) {
        super(MenuRegistration.RATION_BOX.get(), containerId, playerInventory, blockEntity);
    }

    @Override
    protected void addInventorySlots() {
        StorageInventory inv = ((RationBoxBlockEntity) blockEntity).storageInventory();
        for (int i = 0; i < 5; i++) {
            addSlot(new SlotItemHandler(inv, i, 44 + i * 18, 35));
        }
    }
}
