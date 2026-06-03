package net.averageanime.createfood.menu;

import net.averageanime.createfood.block.blockentity.ClothSackBlockEntity;
import net.averageanime.createfood.block.storage.ClothSackBlock;
import net.averageanime.createfood.item.storage.StorageInventory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ClothSackMenu extends AbstractContainerMenu {

    private final ClothSackBlockEntity blockEntity;

    public ClothSackMenu(int containerId, Inventory playerInventory, ClothSackBlockEntity blockEntity) {
        super(ModMenus.CLOTH_SACK.get(), containerId);
        this.blockEntity = blockEntity;

        if (blockEntity != null) {
            StorageInventory inv = blockEntity.storageInventory();
            int[] xs = {71, 89, 71, 89};
            int[] ys = {25, 25, 43, 43};
            for (int i = 0; i < 4; i++) {
                addSlot(new SlotItemHandler(inv, i, xs[i], ys[i]));
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
            var pos   = blockEntity.getBlockPos();
            if (level != null) {
                BlockState state = level.getBlockState(pos);
                if (state.hasProperty(ClothSackBlock.OPEN) && state.getValue(ClothSackBlock.OPEN)) {
                    level.setBlock(pos, state.setValue(ClothSackBlock.OPEN, false), Block.UPDATE_ALL);
                }
                level.playSound(null, pos, SoundEvents.BUNDLE_REMOVE_ONE, SoundSource.BLOCKS, 1.0f, 1.0f);
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
        if (blockEntity == null) return false;
        return blockEntity.getLevel() != null && AbstractContainerMenu.stillValid(
                ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()),
                player, blockEntity.getBlockState().getBlock());
    }
}
