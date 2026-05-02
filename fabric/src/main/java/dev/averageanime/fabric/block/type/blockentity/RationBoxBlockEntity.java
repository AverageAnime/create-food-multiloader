package dev.averageanime.fabric.block.type.blockentity;

import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.item.storage.FoodInventory;
import dev.averageanime.fabric.menu.RationBoxMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class RationBoxBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos> {

    public final FoodInventory inventory = new FoodInventory(5,
            () -> ModConfig.isRationBoxStacking() ? 64 : 1,
            (slot, stack) -> ModConfig.isRationBoxItemAllowed(stack)) {
        @Override
        public void setChanged() {
            super.setChanged();
            RationBoxBlockEntity.this.setChanged();
        }
    };

    public RationBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RATION_BOX, pos, state);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        loadWithComponents(tag, registries);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player) {
        return worldPosition;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.createfood.ration_box");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new RationBoxMenu(syncId, playerInventory, this);
    }
}
