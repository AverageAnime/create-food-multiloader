package dev.averageanime.block.type.blockentity;

import dev.averageanime.item.storage.IStorageItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RationBoxBlockEntity extends BlockEntity implements MenuProvider {

    public final IStorageItemHandler inventory;

    protected RationBoxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,
                                    IStorageItemHandler inventory) {
        super(type, pos, state);
        this.inventory = inventory;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeInventory(registries));
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("inventory")) {
            inventory.deserializeInventory(registries, tag.getCompound("inventory"));
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.createfood.ration_box");
    }

    @Nullable
    @Override
    public abstract AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player);
}
