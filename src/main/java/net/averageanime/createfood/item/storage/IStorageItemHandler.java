package net.averageanime.createfood.item.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IStorageItemHandler {

    ItemStack getInventoryItem(int slot);

    void setInventoryItem(int slot, ItemStack stack);

    int getInventorySize();

    int getSlotLimit(int slot);

    boolean canPlaceItem(int slot, ItemStack stack);

    ItemStack removeItem(int slot, int amount);

    ItemStack insertItem(int slot, ItemStack stack);

    CompoundTag serializeInventory();

    void deserializeInventory(CompoundTag tag);

    void setOnChanged(Runnable onChanged);
}
