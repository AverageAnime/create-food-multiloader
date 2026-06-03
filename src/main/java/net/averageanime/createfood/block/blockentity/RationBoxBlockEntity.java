package net.averageanime.createfood.block.blockentity;

import net.averageanime.createfood.block.ModBlockEntities;
import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.averageanime.createfood.item.storage.IStorageItemHandler;
import net.averageanime.createfood.item.storage.StorageInventory;
import net.averageanime.createfood.menu.RationBoxMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RationBoxBlockEntity extends BlockEntity implements MenuProvider {

    public final IStorageItemHandler inventory;

    public RationBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RATION_BOX.get(), pos, state);
        this.inventory = new StorageInventory(5,
                slot -> CreateFoodConfig.SERVER.rationBoxStack.get() ? 64 : 1,
                (slot, stack) -> ConfigLogic.isRationBoxItemAllowed(stack));
        ((StorageInventory) inventory).setOnChanged(this::setChanged);
    }

    public RationBoxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,
                                IStorageItemHandler inventory) {
        super(type, pos, state);
        this.inventory = inventory;
    }

    public StorageInventory storageInventory() {
        return (StorageInventory) inventory;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", inventory.serializeInventory());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("inventory")) {
            inventory.deserializeInventory(tag.getCompound("inventory"));
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", inventory.serializeInventory());
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.createfood.ration_box");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new RationBoxMenu(id, inv, this);
    }
}
