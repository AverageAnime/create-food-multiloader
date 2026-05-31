package dev.averageanime.neoforge.menu.type;

import dev.averageanime.neoforge.block.ModBlockEntities;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.item.storage.ClothSackItem;
import dev.averageanime.neoforge.item.storage.StorageInventory;
import dev.averageanime.neoforge.menu.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ClothSackItemMenu extends dev.averageanime.menu.type.ClothSackItemMenu {

    public ClothSackItemMenu(int containerId, Inventory playerInventory, int slotIndex) {
        super(ModMenus.CLOTH_SACK_ITEM.get(), containerId, playerInventory, slotIndex,
                new StorageInventory(4,
                        slot -> ModConfig.isClothSackStacking() ? 64 : 1,
                        (slot, stack) -> ModConfig.isClothSackItemAllowed(stack)));
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
        return ModBlockEntities.CLOTH_SACK.get();
    }

    @Override
    protected boolean isValidStorageItem(ItemStack stack) {
        return stack.getItem() instanceof ClothSackItem;
    }
}
