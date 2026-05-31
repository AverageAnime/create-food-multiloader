package dev.averageanime.neoforge.block.type.blockentity;

import dev.averageanime.neoforge.block.ModBlockEntities;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.item.storage.StorageInventory;
import dev.averageanime.neoforge.menu.type.RationBoxMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RationBoxBlockEntity
        extends dev.averageanime.block.type.blockentity.RationBoxBlockEntity {

    public RationBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RATION_BOX.get(), pos, state,
                new StorageInventory(5,
                        slot -> ModConfig.isRationBoxStacking() ? 64 : 1,
                        (slot, stack) -> ModConfig.isRationBoxItemAllowed(stack)));
        storageInventory().setOnChanged(this::setChanged);
    }

    /** Typed accessor for platform menus that need a {@link StorageInventory}. */
    public StorageInventory storageInventory() {
        return (StorageInventory) inventory;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new RationBoxMenu(id, inv, this);
    }
}
