package dev.averageanime.mixin;

import dev.averageanime.item.remainder.CraftingRemainder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Covers Furnace, Smoker, and Blast Furnace in one mixin: all three extend this class.
@Mixin(net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Unique private Item createfood$tickInput = Items.AIR;
    @Unique private int createfood$tickInputCount = 0;
    @Unique private ItemStack createfood$pendingRemainder = ItemStack.EMPTY;

    // No vanilla callback fires when a single item finishes smelting, and NeoForge patches burn's own
    // parameter list, so a more direct @WrapOperation/@Local mixin on burn works on Fabric but not
    // NeoForge. Diffing the slot-0 (input) count between serverTick's HEAD and TAIL works on both.
    @Inject(method = "serverTick", at = @At("HEAD"))
    private static void createfood$captureInput(Level level, BlockPos pos, BlockState state,
                                                net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        ItemStack input = blockEntity.getItem(0);
        AbstractFurnaceBlockEntityMixin self = (AbstractFurnaceBlockEntityMixin) (Object) blockEntity;
        self.createfood$tickInput = input.getItem();
        self.createfood$tickInputCount = input.getCount();
    }

    @Inject(method = "serverTick", at = @At("TAIL"))
    private static void createfood$afterTick(Level level, BlockPos pos, BlockState state,
                                             net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        AbstractFurnaceBlockEntityMixin self = (AbstractFurnaceBlockEntityMixin) (Object) blockEntity;
        if (self.createfood$tickInput != Items.AIR
                && self.createfood$tickInputCount - blockEntity.getItem(0).getCount() == 1) {
            Item remainder = CraftingRemainder.getRemainderFor(self.createfood$tickInput);
            if (remainder != null) self.createfood$bufferRemainder(blockEntity, remainder);
        }
        self.createfood$flushRemainder(blockEntity);
    }

    // Buffered until output slot 2 is free, merging with a same-type pending remainder; a different
    // pending type is dropped on the ground to make room rather than lost.
    @Unique
    private void createfood$bufferRemainder(net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity blockEntity, Item remainder) {
        if (createfood$pendingRemainder.isEmpty()) {
            createfood$pendingRemainder = new ItemStack(remainder);
        } else if (createfood$pendingRemainder.is(remainder)) {
            createfood$pendingRemainder.grow(1);
        } else {
            Level level = blockEntity.getLevel();
            if (level != null) {
                BlockPos pos = blockEntity.getBlockPos();
                Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        createfood$pendingRemainder);
            }
            createfood$pendingRemainder = new ItemStack(remainder);
        }
    }

    @Unique
    private void createfood$flushRemainder(net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity blockEntity) {
        if (createfood$pendingRemainder.isEmpty()) return;
        if (!blockEntity.getItem(2).isEmpty()) return;
        int move = Math.min(createfood$pendingRemainder.getCount(), createfood$pendingRemainder.getMaxStackSize());
        blockEntity.setItem(2, createfood$pendingRemainder.copyWithCount(move));
        createfood$pendingRemainder.shrink(move);
        blockEntity.setChanged();
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void createfood$saveRemainder(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (!createfood$pendingRemainder.isEmpty()) {
            tag.put("createfood$PendingRemainder", createfood$pendingRemainder.save(registries));
        }
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void createfood$loadRemainder(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (tag.contains("createfood$PendingRemainder")) {
            createfood$pendingRemainder = ItemStack.parse(registries, tag.getCompound("createfood$PendingRemainder"))
                    .orElse(ItemStack.EMPTY);
        } else {
            createfood$pendingRemainder = ItemStack.EMPTY;
        }
    }
}
