package dev.averageanime.forge.menu.type.block;

import dev.averageanime.forge.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.forge.item.storage.StorageInventory;
import dev.averageanime.forge.menu.MenuRegistration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

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
