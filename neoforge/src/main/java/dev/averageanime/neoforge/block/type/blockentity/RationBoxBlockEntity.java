package dev.averageanime.neoforge.block.type.blockentity;

import dev.averageanime.neoforge.block.BlockEntityRegistration;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.neoforge.item.storage.StorageInventory;
import dev.averageanime.neoforge.menu.type.block.RationBoxBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RationBoxBlockEntity
        extends dev.averageanime.block.type.blockentity.RationBoxBlockEntity {

    public RationBoxBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistration.RATION_BOX.get(), pos, state,
                new StorageInventory(5,
                        slot -> ConfigValues.isRationBoxStacking() ? 64 : 1,
                        (slot, stack) -> ConfigValues.isRationBoxItemAllowed(stack)));
        storageInventory().setOnChanged(() -> {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        });
    }

    public StorageInventory storageInventory() {
        return (StorageInventory) inventory;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new RationBoxBlockMenu(id, inv, this);
    }
}
