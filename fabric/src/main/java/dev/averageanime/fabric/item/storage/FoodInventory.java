package dev.averageanime.fabric.item.storage;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class FoodInventory extends SimpleContainer {

    private final Supplier<Integer> slotLimitSupplier;
    private final BiPredicate<Integer, ItemStack> validator;

    public FoodInventory(int size, Supplier<Integer> slotLimitSupplier, BiPredicate<Integer, ItemStack> validator) {
        super(size);
        this.slotLimitSupplier = slotLimitSupplier;
        this.validator = validator;
    }

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
