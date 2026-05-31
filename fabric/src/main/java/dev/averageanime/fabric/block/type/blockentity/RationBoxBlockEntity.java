package dev.averageanime.fabric.block.type.blockentity;

import dev.averageanime.fabric.block.ModBlockEntities;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.item.storage.StorageInventory;
import dev.averageanime.fabric.menu.type.RationBoxMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class RationBoxBlockEntity
        extends dev.averageanime.block.type.blockentity.RationBoxBlockEntity
        implements ExtendedScreenHandlerFactory<BlockPos> {

    public RationBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RATION_BOX, pos, state,
                new StorageInventory(5,
                        () -> ModConfig.isRationBoxStacking() ? 64 : 1,
                        (slot, stack) -> ModConfig.isRationBoxItemAllowed(stack)));
        inventory.setOnChanged(this::setChanged);
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
        return new RationBoxMenu(id, inv, this);
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
