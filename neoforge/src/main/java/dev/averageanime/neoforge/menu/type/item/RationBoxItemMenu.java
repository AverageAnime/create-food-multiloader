package dev.averageanime.neoforge.menu.type.item;

import dev.averageanime.neoforge.block.BlockEntityRegistration;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.neoforge.item.storage.StorageInventory;
import dev.averageanime.neoforge.item.storage.RationBoxItem;
import dev.averageanime.neoforge.menu.MenuRegistration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.items.SlotItemHandler;

public class RationBoxItemMenu extends dev.averageanime.menu.type.item.RationBoxItemMenu {

    public RationBoxItemMenu(int containerId, Inventory playerInventory, int slotIndex) {
        super(MenuRegistration.RATION_BOX_ITEM.get(), containerId, playerInventory, slotIndex,
                new StorageInventory(5,
                        slot -> ConfigValues.isRationBoxStacking() ? 64 : 1,
                        (slot, stack) -> ConfigValues.isRationBoxItemAllowed(stack)));
    }

    @Override
    protected void addInventorySlots() {
        StorageInventory inv = (StorageInventory) handler;
        for (int i = 0; i < 5; i++) {
            addSlot(new SlotItemHandler(inv, i, 44 + i * 18, 35));
        }
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return BlockEntityRegistration.RATION_BOX.get();
    }

    @Override
    protected boolean isValidStorageItem(ItemStack stack) {
        return stack.getItem() instanceof RationBoxItem;
    }
}
