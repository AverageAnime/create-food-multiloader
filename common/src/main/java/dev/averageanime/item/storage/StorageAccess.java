package dev.averageanime.item.storage;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface StorageAccess {

    ItemStack getInventoryItem(int slot);

    void setInventoryItem(int slot, ItemStack stack);

    int getInventorySize();

    int getSlotLimit(int slot);

    boolean canPlaceItem(int slot, ItemStack stack);

    ItemStack removeItem(int slot, int amount);

    ItemStack insertItem(int slot, ItemStack stack);

    CompoundTag serializeInventory(HolderLookup.Provider registries);

    void deserializeInventory(HolderLookup.Provider registries, CompoundTag tag);

    void setOnChanged(Runnable onChanged);
}