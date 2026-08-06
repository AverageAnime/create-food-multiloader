package dev.averageanime.fabric.item.storage;

import dev.averageanime.item.storage.StorageAccess;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class StorageInventory extends SimpleContainer implements StorageAccess {

    private final Supplier<Integer> slotLimitSupplier;
    private final BiPredicate<Integer, ItemStack> validator;
    private Runnable onChanged = () -> {};

    public StorageInventory(int size, Supplier<Integer> slotLimitSupplier, BiPredicate<Integer, ItemStack> validator) {
        super(size);
        this.slotLimitSupplier = slotLimitSupplier;
        this.validator = validator;
    }

    @Override
    public void setOnChanged(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        onChanged.run();
    }

    // ── IStorageAccess bridges ──────────────────────────────────────────

    @Override
    public ItemStack getInventoryItem(int slot) {
        return getItem(slot);
    }

    @Override
    public void setInventoryItem(int slot, ItemStack stack) {
        setItem(slot, stack);
    }

    @Override
    public int getInventorySize() {
        return getContainerSize();
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack) {
        if (stack.isEmpty() || !canPlaceItem(slot, stack)) return stack;
        ItemStack existing = getItem(slot);
        int limit = getSlotLimit(slot);
        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameComponents(existing, stack)) return stack;
            int space = limit - existing.getCount();
            if (space <= 0) return stack;
            int toAdd = Math.min(space, stack.getCount());
            setItem(slot, existing.copyWithCount(existing.getCount() + toAdd));
            return toAdd >= stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - toAdd);
        }
        int toPlace = Math.min(limit, stack.getCount());
        setItem(slot, stack.copyWithCount(toPlace));
        return toPlace >= stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - toPlace);
    }

    @Override
    public CompoundTag serializeInventory(HolderLookup.Provider registries) {
        return serializeNBT(registries);
    }

    @Override
    public void deserializeInventory(HolderLookup.Provider registries, CompoundTag tag) {
        deserializeNBT(registries, tag);
    }

    // ── SimpleContainer overrides ────────────────────────────────────────────

    @Override
    public int getSlotLimit(int slot) {
        return slotLimitSupplier.get();
    }

    @Override
    public int getMaxStackSize() {
        return slotLimitSupplier.get();
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return validator.test(slot, stack);
    }

    // ── NBT helpers (used by block entities that still call these directly) ──

    public CompoundTag serializeNBT(HolderLookup.Provider registries) {
        ListTag itemsTag = new ListTag();
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putByte("Slot", (byte) i);
                itemsTag.add(stack.save(registries, itemTag));
            }
        }
        CompoundTag tag = new CompoundTag();
        tag.put("Items", itemsTag);
        return tag;
    }

    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        ListTag itemsTag = tag.getList("Items", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < itemsTag.size(); i++) {
            CompoundTag itemTag = itemsTag.getCompound(i);
            int slot = itemTag.getByte("Slot") & 255;
            if (slot < getContainerSize()) {
                setItem(slot, ItemStack.parseOptional(registries, itemTag));
            }
        }
    }
}
