package dev.averageanime.menu.type.item;

import dev.averageanime.item.storage.StorageAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public abstract class ClothSackItemMenu extends AbstractContainerMenu {

    protected final Player player;
    protected final int slotIndex;
    protected final StorageAccess handler;

    protected ClothSackItemMenu(MenuType<?> type, int id,
                                Inventory playerInventory, int slotIndex,
                                StorageAccess handler) {
        super(type, id);
        this.player    = playerInventory.player;
        this.slotIndex = slotIndex;
        this.handler   = handler;
        loadFromNBT(playerInventory.getItem(slotIndex), playerInventory.player);
        addInventorySlots();
        addPlayerSlots(playerInventory);
    }

    protected abstract void addInventorySlots();

    protected abstract BlockEntityType<?> getBlockEntityType();

    protected abstract boolean isValidStorageItem(ItemStack stack);

    private void loadFromNBT(ItemStack sackItem, Player player) {
        CustomData beData = sackItem.get(DataComponents.BLOCK_ENTITY_DATA);
        if (beData != null) {
            CompoundTag tag = beData.copyTag();
            if (tag.contains("inventory")) {
                handler.deserializeInventory(player.level().registryAccess(), tag.getCompound("inventory"));
            }
        }
    }

    protected void addPlayerSlots(Inventory playerInventory) {
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
            ItemStack sackItem = player.getInventory().getItem(slotIndex);
            if (!sackItem.isEmpty()) {
                CompoundTag tag = new CompoundTag();
                tag.put("inventory", handler.serializeInventory(player.level().registryAccess()));
                if (sackItem.getCount() > 1) {
                    ItemStack single = sackItem.copyWithCount(1);
                    BlockItem.setBlockEntityData(single, getBlockEntityType(), tag);
                    sackItem.shrink(1);
                    if (!player.getInventory().add(single)) {
                        player.drop(single, false);
                    }
                } else {
                    BlockItem.setBlockEntityData(sackItem, getBlockEntityType(), tag);
                }
            }
            player.level().playSound(null, player.blockPosition(),
                    SoundEvents.BUNDLE_REMOVE_ONE, SoundSource.PLAYERS, 0.8f, 1.0f);
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (slotIndex < 4) {
                if (!this.moveItemStackTo(stack, 4, this.slots.size(), true)) return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(stack, 0, 4, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return result;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        ItemStack item = player.getInventory().getItem(slotIndex);
        return !item.isEmpty() && isValidStorageItem(item);
    }
}