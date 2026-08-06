package dev.averageanime.fabric.menu.type.block;

import dev.averageanime.fabric.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.fabric.item.storage.StorageInventory;
import dev.averageanime.fabric.menu.MenuRegistration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClothSackBlockMenu extends dev.averageanime.menu.type.block.ClothSackBlockMenu {

    public ClothSackBlockMenu(int containerId, Inventory playerInventory, ClothSackBlockEntity blockEntity) {
        super(MenuRegistration.CLOTH_SACK, containerId, playerInventory, blockEntity);
    }

    @Override
    protected void addInventorySlots() {
        StorageInventory inv = ((ClothSackBlockEntity) blockEntity).storageInventory();
        int[] xs = {71, 89, 71, 89};
        int[] ys = {25, 25, 43, 43};
        for (int i = 0; i < 4; i++) {
            final int slot = i;
            addSlot(new Slot(inv, i, xs[i], ys[i]) {
                @Override public boolean mayPlace(@NotNull ItemStack stack) { return inv.canPlaceItem(slot, stack); }
                @Override public int getMaxStackSize() { return inv.getSlotLimit(slot); }
                @Override public int getMaxStackSize(@NotNull ItemStack stack) { return Math.min(getMaxStackSize(), stack.getMaxStackSize()); }
            });
        }
    }
}
