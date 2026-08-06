package dev.averageanime.fabric.menu.type.item;

import dev.averageanime.fabric.block.BlockEntityRegistration;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.fabric.item.storage.ClothSackItem;
import dev.averageanime.fabric.item.storage.StorageInventory;
import dev.averageanime.fabric.menu.MenuRegistration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class ClothSackItemMenu extends dev.averageanime.menu.type.item.ClothSackItemMenu {

    public ClothSackItemMenu(int containerId, Inventory playerInventory, int slotIndex) {
        super(MenuRegistration.CLOTH_SACK_ITEM, containerId, playerInventory, slotIndex,
                new StorageInventory(4,
                        () -> ConfigValues.isClothSackStacking() ? 64 : 1,
                        (slot, stack) -> ConfigValues.isClothSackItemAllowed(stack)));
    }

    @Override
    protected void addInventorySlots() {
        StorageInventory inv = (StorageInventory) handler;
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

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return BlockEntityRegistration.CLOTH_SACK;
    }

    @Override
    protected boolean isValidStorageItem(ItemStack stack) {
        return stack.getItem() instanceof ClothSackItem;
    }
}
