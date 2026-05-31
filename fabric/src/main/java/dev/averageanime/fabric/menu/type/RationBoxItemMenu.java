package dev.averageanime.fabric.menu.type;

import dev.averageanime.fabric.block.ModBlockEntities;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.item.storage.StorageInventory;
import dev.averageanime.fabric.item.storage.RationBoxItem;
import dev.averageanime.fabric.menu.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class RationBoxItemMenu extends dev.averageanime.menu.type.RationBoxItemMenu {

    public RationBoxItemMenu(int containerId, Inventory playerInventory, int slotIndex) {
        super(ModMenus.RATION_BOX_ITEM, containerId, playerInventory, slotIndex,
                new StorageInventory(5,
                        () -> ModConfig.isRationBoxStacking() ? 64 : 1,
                        (slot, stack) -> ModConfig.isRationBoxItemAllowed(stack)));
    }

    @Override
    protected void addInventorySlots() {
        StorageInventory inv = (StorageInventory) handler;
        for (int i = 0; i < 5; i++) {
            final int slot = i;
            addSlot(new Slot(inv, i, 44 + i * 18, 35) {
                @Override public boolean mayPlace(@NotNull ItemStack stack) { return inv.canPlaceItem(slot, stack); }
                @Override public int getMaxStackSize() { return inv.getSlotLimit(slot); }
                @Override public int getMaxStackSize(@NotNull ItemStack stack) { return Math.min(getMaxStackSize(), stack.getMaxStackSize()); }
            });
        }
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntities.RATION_BOX;
    }

    @Override
    protected boolean isValidStorageItem(ItemStack stack) {
        return stack.getItem() instanceof RationBoxItem;
    }
}
