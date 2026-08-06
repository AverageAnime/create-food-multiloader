package dev.averageanime.fabric.block.type.blockentity;

import dev.averageanime.fabric.block.BlockEntityRegistration;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.fabric.item.storage.StorageInventory;
import dev.averageanime.fabric.menu.type.block.RationBoxBlockMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class RationBoxBlockEntity
        extends dev.averageanime.block.type.blockentity.RationBoxBlockEntity
        implements ExtendedScreenHandlerFactory<BlockPos> {

    public RationBoxBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistration.RATION_BOX, pos, state,
                new StorageInventory(5,
                        () -> ConfigValues.isRationBoxStacking() ? 64 : 1,
                        (slot, stack) -> ConfigValues.isRationBoxItemAllowed(stack)));
        inventory.setOnChanged(() -> {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        });
    }

    /** Typed accessor for platform menus that need a {@link StorageInventory}. */
    public StorageInventory storageInventory() {
        return (StorageInventory) inventory;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player) {
        return worldPosition;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new RationBoxBlockMenu(id, inv, this);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        loadWithComponents(tag, registries);
    }
}
