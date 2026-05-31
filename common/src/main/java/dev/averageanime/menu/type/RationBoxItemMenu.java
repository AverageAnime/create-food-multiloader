package dev.averageanime.menu.type;

import dev.averageanime.item.storage.IStorageItemHandler;
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

public abstract class RationBoxItemMenu extends AbstractContainerMenu {

    protected final Player player;
    protected final int slotIndex;
    protected final IStorageItemHandler handler;

    /**
     * @param handler  Platform-specific inventory created by the subclass constructor and passed up.
     */
    protected RationBoxItemMenu(MenuType<?> type, int id,
                                Inventory playerInventory, int slotIndex,
                                IStorageItemHandler handler) {
        super(type, id);
        this.player    = playerInventory.player;
        this.slotIndex = slotIndex;
        this.handler   = handler;
        loadFromNBT(playerInventory.getItem(slotIndex), playerInventory.player);
        addInventorySlots();
        addPlayerSlots(playerInventory);
    }

    /** Platform subclass adds the 5 food inventory slots. */
    protected abstract void addInventorySlots();

    /**
     * Returns the block entity type used to persist the box's NBT on the item stack.
     * Fabric returns {@code ModBlockEntities.RATION_BOX};
     * NeoForge returns {@code ModBlockEntities.RATION_BOX.get()}.
     */
    protected abstract BlockEntityType<?> getBlockEntityType();

    /**
     * Returns true if the item in the player's hand-slot is still a valid ration box.
     * Platform subclass checks against its own {@code RationBoxItem} class.
     */
    protected abstract boolean isValidStorageItem(ItemStack stack);

    private void loadFromNBT(ItemStack boxItem, Player player) {
        CustomData beData = boxItem.get(DataComponents.BLOCK_ENTITY_DATA);
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
            ItemStack boxItem = player.getInventory().getItem(slotIndex);
            if (!boxItem.isEmpty()) {
                CompoundTag tag = new CompoundTag();
                tag.put("inventory", handler.serializeInventory(player.level().registryAccess()));
                if (boxItem.getCount() > 1) {
                    ItemStack single = boxItem.copyWithCount(1);
                    BlockItem.setBlockEntityData(single, getBlockEntityType(), tag);
                    boxItem.shrink(1);
                    if (!player.getInventory().add(single)) {
                        player.drop(single, false);
                    }
                } else {
                    BlockItem.setBlockEntityData(boxItem, getBlockEntityType(), tag);
                }
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
    public boolean stillValid(@NotNull Player player) {
        ItemStack item = player.getInventory().getItem(slotIndex);
        return !item.isEmpty() && isValidStorageItem(item);
    }
}
