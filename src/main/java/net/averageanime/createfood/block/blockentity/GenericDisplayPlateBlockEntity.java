package net.averageanime.createfood.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.averageanime.createfood.block.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GenericDisplayPlateBlockEntity extends BlockEntity {

    private ItemStack displayedItem = ItemStack.EMPTY;

    public GenericDisplayPlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENERIC_DISPLAY_PLATE.get(), pos, state);
    }

    public ItemStack getDisplayedItem() {
        return displayedItem.copy();
    }

    public boolean isEmpty() {
        return displayedItem.isEmpty();
    }

    public ItemStack takeDisplayedItem() {
        ItemStack taken = displayedItem;
        displayedItem = ItemStack.EMPTY;
        return taken;
    }

    public void setDisplayedItem(ItemStack stack) {
        this.displayedItem = stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (!displayedItem.isEmpty()) {
            tag.put("item", displayedItem.save(new CompoundTag()));
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("item")) {
            displayedItem = ItemStack.of(tag.getCompound("item"));
        } else {
            displayedItem = ItemStack.EMPTY;
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (!displayedItem.isEmpty()) {
            tag.put("item", displayedItem.save(new CompoundTag()));
        }
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
