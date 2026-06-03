package net.averageanime.createfood.item.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;
import java.util.function.IntUnaryOperator;

public class StorageInventory extends ItemStackHandler implements IStorageItemHandler {

    private final IntUnaryOperator slotLimitFn;
    private final BiPredicate<Integer, ItemStack> validatorFn;

    private Runnable onChanged = () -> {};

    public StorageInventory(int size,
                            IntUnaryOperator slotLimitFn,
                            BiPredicate<Integer, ItemStack> validatorFn) {
        super(size);
        this.slotLimitFn = slotLimitFn;
        this.validatorFn = validatorFn;
    }

    @Override
    public void setOnChanged(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    @Override
    public ItemStack getInventoryItem(int slot) {
        return getStackInSlot(slot);
    }

    @Override
    public void setInventoryItem(int slot, ItemStack stack) {
        setStackInSlot(slot, stack);
    }

    @Override
    public int getInventorySize() {
        return getSlots();
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return extractItem(slot, amount, false);
    }

    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack) {
        return insertItem(slot, stack, false);
    }

    @Override
    public CompoundTag serializeInventory() {
        return serializeNBT();
    }

    @Override
    public void deserializeInventory(CompoundTag tag) {
        deserializeNBT(tag);
    }

    @Override
    public int getSlotLimit(int slot) {
        return slotLimitFn.applyAsInt(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return validatorFn.test(slot, stack);
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return isItemValid(slot, stack);
    }

    @Override
    protected void onContentsChanged(int slot) {
        onChanged.run();
    }
}
