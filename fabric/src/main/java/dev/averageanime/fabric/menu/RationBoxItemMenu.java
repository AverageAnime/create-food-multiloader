package dev.averageanime.fabric.menu;

import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.item.storage.FoodInventory;
import dev.averageanime.fabric.item.storage.FoodStorageItem;
import dev.averageanime.fabric.item.storage.RationBoxItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;

public class RationBoxItemMenu extends AbstractContainerMenu {

    private final Player player;
    private final int slotIndex;
    private final FoodInventory handler;

    public RationBoxItemMenu(int containerId, Inventory playerInventory, int slotIndex) {
        super(ModMenus.RATION_BOX_ITEM, containerId);
        this.player = playerInventory.player;
        this.slotIndex = slotIndex;
        this.handler = new FoodInventory(5,
                () -> ModConfig.isRationBoxStacking() ? 64 : 1,
                (slot, stack) -> ModConfig.isRationBoxItemAllowed(stack));

        ItemStack boxItem = playerInventory.getItem(slotIndex);
        CustomData beData = boxItem.get(DataComponents.BLOCK_ENTITY_DATA);
        if (beData != null) {
            CompoundTag tag = beData.copyTag();
            if (tag.contains("inventory")) {
                handler.deserializeNBT(playerInventory.player.level().registryAccess(), tag.getCompound("inventory"));
            }
        }

        for (int i = 0; i < 5; i++) {
            final int slot = i;
            addSlot(new Slot(handler, i, 44 + i * 18, 35) {
                @Override public boolean mayPlace(@NotNull ItemStack stack) { return handler.canPlaceItem(slot, stack); }
                @Override public int getMaxStackSize() { return handler.getSlotLimit(slot); }
                @Override public int getMaxStackSize(@NotNull ItemStack stack) { return Math.min(getMaxStackSize(), stack.getMaxStackSize()); }
            });
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            ItemStack boxItem = player.getInventory().getItem(slotIndex);
            if (!boxItem.isEmpty() && boxItem.getItem() instanceof FoodStorageItem storageItem) {
                storageItem.saveInventory(boxItem, handler, player.level().registryAccess());
            }
            player.level().playSound(null, player.blockPosition(),
                    SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (slotIndex < 5) {
                if (!this.moveItemStackTo(stack, 5, this.slots.size(), true)) return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(stack, 0, 5, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        ItemStack item = player.getInventory().getItem(slotIndex);
        return !item.isEmpty() && item.getItem() instanceof RationBoxItem;
    }
}
