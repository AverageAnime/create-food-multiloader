package dev.averageanime.block.type.blockentity;

import dev.averageanime.platform.Services;
import dev.averageanime.registry.FluidAmounts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LargeBowlBlockEntity extends BlockEntity {

    /** Fallback used only before config is bound (e.g. datagen); live capacity comes from {@link Services#PLATFORM}. */
    public static final int DEFAULT_CAPACITY_MB = FluidAmounts.BUCKET * 4;

    private static final String TAG_FLUID = "fluid";
    private static final String TAG_AMOUNT = "amount";

    private Fluid fluid = Fluids.EMPTY;
    private int amount = 0;

    public LargeBowlBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public boolean isEmpty() {
        return amount <= 0 || fluid == Fluids.EMPTY;
    }

    public boolean isFull() {
        return amount >= Services.PLATFORM.getLargeBowlCapacityMb();
    }

    public Fluid getFluid() {
        return isEmpty() ? Fluids.EMPTY : fluid;
    }

    public int getAmount() {
        return isEmpty() ? 0 : amount;
    }

    public boolean canAccept(Fluid incoming, int mb) {
        if (incoming == null || incoming == Fluids.EMPTY || mb <= 0) return false;
        if (!isEmpty() && fluid != incoming) return false;
        return getAmount() + mb <= Services.PLATFORM.getLargeBowlCapacityMb();
    }

    public boolean fill(Fluid incoming, int mb) {
        if (!canAccept(incoming, mb)) return false;
        this.amount = getAmount() + mb;
        this.fluid = incoming;
        markUpdated();
        return true;
    }

    public boolean drain(int mb) {
        if (mb <= 0 || getAmount() < mb) return false;
        this.amount -= mb;
        if (this.amount <= 0) {
            this.amount = 0;
            this.fluid = Fluids.EMPTY;
        }
        markUpdated();
        return true;
    }

    public void clear() {
        if (isEmpty()) return;
        this.fluid = Fluids.EMPTY;
        this.amount = 0;
        markUpdated();
    }

    private void markUpdated() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    private void writeTank(CompoundTag tag) {
        tag.putInt(TAG_AMOUNT, isEmpty() ? 0 : amount);
        if (isEmpty()) return;
        ResourceLocation key = BuiltInRegistries.FLUID.getKey(fluid);
        if (key != null) tag.putString(TAG_FLUID, key.toString());
    }

    private void readTank(CompoundTag tag) {
        fluid = Fluids.EMPTY;
        amount = 0;
        if (!tag.contains(TAG_FLUID)) return;

        ResourceLocation key = ResourceLocation.tryParse(tag.getString(TAG_FLUID));
        if (key == null) return;
        Fluid stored = BuiltInRegistries.FLUID.get(key);
        if (stored == null || stored == Fluids.EMPTY) return;

        fluid = stored;
        amount = Math.max(0, Math.min(Services.PLATFORM.getLargeBowlCapacityMb(), tag.getInt(TAG_AMOUNT)));
        if (amount == 0) fluid = Fluids.EMPTY;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        writeTank(tag);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        readTank(tag);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = new CompoundTag();
        writeTank(tag);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag serializeNBT(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
        loadWithComponents(tag, registries);
    }
}
