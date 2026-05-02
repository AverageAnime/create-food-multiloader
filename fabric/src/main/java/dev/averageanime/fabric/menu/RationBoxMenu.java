package dev.averageanime.fabric.menu;

import dev.averageanime.fabric.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.fabric.item.storage.FoodInventory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RationBoxMenu extends AbstractContainerMenu {

    private final RationBoxBlockEntity blockEntity;

    public RationBoxMenu(int containerId, Inventory playerInventory, RationBoxBlockEntity blockEntity) {
        super(ModMenus.RATION_BOX, containerId);
        this.blockEntity = blockEntity;

        if (blockEntity != null) {
            FoodInventory inv = blockEntity.inventory;
            for (int i = 0; i < 5; i++) {
                final int slot = i;
                addSlot(new Slot(inv, i, 44 + i * 18, 35) {
                    @Override public boolean mayPlace(@NotNull ItemStack stack) { return inv.canPlaceItem(slot, stack); }
                    @Override public int getMaxStackSize() { return inv.getSlotLimit(slot); }
                    @Override public int getMaxStackSize(@NotNull ItemStack stack) { return Math.min(getMaxStackSize(), stack.getMaxStackSize()); }
                });
            }
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
        if (blockEntity != null && !player.level().isClientSide) {
            var level = blockEntity.getLevel();
            var pos = blockEntity.getBlockPos();
            if (level != null) {
                level.playSound(null, pos, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
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
    public boolean stillValid(@NotNull Player player) {
        if (blockEntity == null) return false;
        return blockEntity.getLevel() != null && AbstractContainerMenu.stillValid(
                ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()),
                player, blockEntity.getBlockState().getBlock());
    }
}
