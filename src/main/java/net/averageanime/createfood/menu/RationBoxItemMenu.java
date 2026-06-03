package net.averageanime.createfood.menu;

import net.averageanime.createfood.block.ModBlockEntities;
import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.averageanime.createfood.item.storage.IStorageItemHandler;
import net.averageanime.createfood.item.storage.RationBoxItem;
import net.averageanime.createfood.item.storage.StorageInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class RationBoxItemMenu extends AbstractContainerMenu {

    private final Player player;
    private final int slotIndex;
    private final IStorageItemHandler handler;

    public RationBoxItemMenu(int containerId, Inventory playerInventory, int slotIndex) {
        super(ModMenus.RATION_BOX_ITEM.get(), containerId);
        this.player    = playerInventory.player;
        this.slotIndex = slotIndex;
        this.handler   = new StorageInventory(5,
                slot -> CreateFoodConfig.SERVER.rationBoxStack.get() ? 64 : 1,
                (slot, stack) -> ConfigLogic.isRationBoxItemAllowed(stack));

        loadFromNBT(playerInventory.getItem(slotIndex));

        for (int i = 0; i < 5; i++) {
            addSlot(new SlotItemHandler((StorageInventory) handler, i, 44 + i * 18, 35));
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

    private void loadFromNBT(ItemStack boxItem) {
        net.minecraft.nbt.Tag raw = boxItem.getTagElement("BlockEntityTag");
        if (raw instanceof CompoundTag tag && tag.contains("inventory")) {
            handler.deserializeInventory(tag.getCompound("inventory"));
        }
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            ItemStack boxItem = player.getInventory().getItem(slotIndex);
            if (!boxItem.isEmpty()) {
                CompoundTag tag = new CompoundTag();
                tag.put("inventory", handler.serializeInventory());
                if (boxItem.getCount() > 1) {
                    ItemStack single = boxItem.copy(); single.setCount(1);
                    BlockItem.setBlockEntityData(single, ModBlockEntities.RATION_BOX.get(), tag);
                    boxItem.shrink(1);
                    if (!player.getInventory().add(single)) {
                        player.drop(single, false);
                    }
                } else {
                    BlockItem.setBlockEntityData(boxItem, ModBlockEntities.RATION_BOX.get(), tag);
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
        return !item.isEmpty() && item.getItem() instanceof RationBoxItem;
    }
}
