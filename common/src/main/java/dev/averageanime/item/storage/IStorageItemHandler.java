package dev.averageanime.item.storage;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * Platform-agnostic view of the food storage inventory used by Fabric and NeoForge.
 * <p>Fabric's {@code StorageInventory} satisfies most methods via its {@code SimpleContainer}
 * supertype; NeoForge's handler satisfies them via {@code ItemStackHandler} / {@code IItemHandler}.
 */
public interface IStorageItemHandler {

    /** Returns the item currently in {@code slot}, or {@link ItemStack#EMPTY}. */
    ItemStack getInventoryItem(int slot);

    /** Directly sets the item in {@code slot} (no validation check). */
    void setInventoryItem(int slot, ItemStack stack);

    /** Total number of slots in this inventory. */
    int getInventorySize();

    /** Maximum stack size allowed in {@code slot}. */
    int getSlotLimit(int slot);

    /** Returns {@code true} if {@code stack} may be placed in {@code slot}. */
    boolean canPlaceItem(int slot, ItemStack stack);

    /**
     * Removes up to {@code amount} items from {@code slot}.
     *
     * @return the items that were actually removed
     */
    ItemStack removeItem(int slot, int amount);

    /**
     * Attempts to insert {@code stack} into {@code slot}.
     *
     * @return the items that could not be inserted (may be {@link ItemStack#EMPTY})
     */
    ItemStack insertItem(int slot, ItemStack stack);

    /** Serialises the inventory contents to NBT. */
    CompoundTag serializeInventory(HolderLookup.Provider registries);

    /** Deserialises previously serialised inventory contents from NBT. */
    void deserializeInventory(HolderLookup.Provider registries, CompoundTag tag);

    /**
     * Registers a callback that fires whenever the contents of any slot change.
     * Block entities use this to trigger {@code setChanged()} and sync packets.
     */
    void setOnChanged(Runnable onChanged);
}