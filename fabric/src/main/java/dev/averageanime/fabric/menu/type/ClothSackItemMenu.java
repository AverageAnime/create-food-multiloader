package dev.averageanime.fabric.menu.type;

import dev.averageanime.fabric.block.ModBlockEntities;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.item.storage.ClothSackItem;
import dev.averageanime.fabric.item.storage.StorageInventory;
import dev.averageanime.fabric.menu.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class ClothSackItemMenu extends dev.averageanime.menu.type.ClothSackItemMenu {

    public ClothSackItemMenu(int containerId, Inventory playerInventory, int slotIndex) {
        super(ModMenus.CLOTH_SACK_ITEM, containerId, playerInventory, slotIndex,
                new StorageInventory(4,
                        () -> ModConfig.isClothSackStacking() ? 64 : 1,
                        (slot, stack) -> ModConfig.isClothSackItemAllowed(stack)));
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
        return ModBlockEntities.CLOTH_SACK;
    }

    @Override
    protected boolean isValidStorageItem(ItemStack stack) {
        return stack.getItem() instanceof ClothSackItem;
    }
}
