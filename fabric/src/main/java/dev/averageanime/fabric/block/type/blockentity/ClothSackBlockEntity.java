package dev.averageanime.fabric.block.type.blockentity;

import dev.averageanime.fabric.block.BlockEntityRegistration;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.fabric.item.storage.ClothSackItem;
import dev.averageanime.fabric.item.storage.StorageInventory;
import dev.averageanime.fabric.menu.type.block.ClothSackBlockMenu;
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

public class ClothSackBlockEntity
        extends dev.averageanime.block.type.blockentity.ClothSackBlockEntity
        implements ExtendedScreenHandlerFactory<BlockPos> {

    public ClothSackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistration.CLOTH_SACK, pos, state,
                new StorageInventory(4,
                        () -> ConfigValues.isClothSackStacking() ? 64 : 1,
                        (slot, stack) -> !(stack.getItem() instanceof ClothSackItem)
                                && ConfigValues.isClothSackItemAllowed(stack)));
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
        return new ClothSackBlockMenu(id, inv, this);
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
