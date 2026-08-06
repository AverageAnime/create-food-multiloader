package dev.averageanime.neoforge.menu.type.item;

import dev.averageanime.neoforge.block.BlockEntityRegistration;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.neoforge.item.storage.ClothSackItem;
import dev.averageanime.neoforge.item.storage.StorageInventory;
import dev.averageanime.neoforge.menu.MenuRegistration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ClothSackItemMenu extends dev.averageanime.menu.type.item.ClothSackItemMenu {

    public ClothSackItemMenu(int containerId, Inventory playerInventory, int slotIndex) {
        super(MenuRegistration.CLOTH_SACK_ITEM.get(), containerId, playerInventory, slotIndex,
                new StorageInventory(4,
                        slot -> ConfigValues.isClothSackStacking() ? 64 : 1,
                        (slot, stack) -> ConfigValues.isClothSackItemAllowed(stack)));
    }

    @Override
    protected void addInventorySlots() {
        StorageInventory inv = (StorageInventory) handler;
        int[] xs = {71, 89, 71, 89};
        int[] ys = {25, 25, 43, 43};
        for (int i = 0; i < 4; i++) {
            addSlot(new SlotItemHandler(inv, i, xs[i], ys[i]));
        }
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return BlockEntityRegistration.CLOTH_SACK.get();
    }

    @Override
    protected boolean isValidStorageItem(ItemStack stack) {
        return stack.getItem() instanceof ClothSackItem;
    }
}
