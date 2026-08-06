package dev.averageanime.fabric.menu.type.block;

import dev.averageanime.fabric.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.fabric.item.storage.StorageInventory;
import dev.averageanime.fabric.menu.MenuRegistration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RationBoxBlockMenu extends dev.averageanime.menu.type.block.RationBoxBlockMenu {

    public RationBoxBlockMenu(int containerId, Inventory playerInventory, RationBoxBlockEntity blockEntity) {
        super(MenuRegistration.RATION_BOX, containerId, playerInventory, blockEntity);
    }

    @Override
    protected void addInventorySlots() {
        StorageInventory inv = ((RationBoxBlockEntity) blockEntity).storageInventory();
        for (int i = 0; i < 5; i++) {
            final int slot = i;
            addSlot(new Slot(inv, i, 44 + i * 18, 35) {
                @Override public boolean mayPlace(@NotNull ItemStack stack) { return inv.canPlaceItem(slot, stack); }
                @Override public int getMaxStackSize() { return inv.getSlotLimit(slot); }
                @Override public int getMaxStackSize(@NotNull ItemStack stack) { return Math.min(getMaxStackSize(), stack.getMaxStackSize()); }
            });
        }
    }
}
