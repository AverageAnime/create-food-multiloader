package dev.averageanime.item.storage;

import dev.averageanime.platform.Services;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class StorageItem extends BlockItem {

    protected StorageItem(Block block, Properties properties) {
        super(block, properties);
    }

    protected abstract boolean isInventoryEnabled();
    protected abstract boolean isEatFromItemEnabled();
    protected abstract InteractionResultHolder<ItemStack> onInventoryDisabled(ItemStack stack, boolean isClientSide);
    protected abstract void playOpenSound(Level level, Player player);
    protected abstract MenuConstructor menuConstructor(int slotIndex);
    protected abstract Component menuTitle();
    protected abstract BlockEntityType<?> getBlockEntityType();
    protected abstract IStorageItemHandler createHandler();

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level,
            @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!isInventoryEnabled()) return onInventoryDisabled(heldStack, level.isClientSide);

        if (!isEatFromItemEnabled()) {
            if (!level.isClientSide) {
                int slotIndex = hand == InteractionHand.MAIN_HAND
                        ? player.getInventory().selected : 40;
                playOpenSound(level, player);
                Services.PLATFORM.openStorageItemMenu(
                        player, menuConstructor(slotIndex), menuTitle(), slotIndex);
            }
            return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide);
        }

        int slot = findFirstFoodSlot(heldStack, level);
        if (slot >= 0 && player.canEat(false)) {
            IStorageItemHandler handler = loadInventory(heldStack, level.registryAccess());
            boolean isDrink = handler.getInventoryItem(slot).getUseAnimation() == UseAnim.DRINK;
            ItemStack modified = heldStack.copy();
            CustomData beData = modified.get(DataComponents.BLOCK_ENTITY_DATA);
            CompoundTag tag = beData != null ? beData.copyTag() : new CompoundTag();
            tag.putBoolean("is_drink", isDrink);
            modified.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(tag));
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(modified);
        }
        return InteractionResultHolder.pass(heldStack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack,
            @NotNull Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            int slot = findFirstFoodSlot(stack, level);
            if (slot >= 0) {
                IStorageItemHandler handler = loadInventory(stack, level.registryAccess());
                ItemStack food = handler.getInventoryItem(slot).copyWithCount(1);

                FoodProperties foodProps = food.get(DataComponents.FOOD);
                Optional<ItemStack> containerOpt = foodProps != null
                        ? foodProps.usingConvertsTo() : Optional.empty();

                player.eat(level, food);
                handler.removeItem(slot, 1);

                containerOpt.ifPresent(container -> {
                    ItemStack leftover = container.copy();
                    for (int s = 0; s < handler.getInventorySize(); s++) {
                        leftover = handler.insertItem(s, leftover);
                        if (leftover.isEmpty()) break;
                    }
                    if (!leftover.isEmpty() && !player.getInventory().add(leftover)) {
                        player.drop(leftover, false);
                    }
                });

                saveInventory(stack, handler, level.registryAccess());
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        CustomData beData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (beData != null && beData.copyTag().getBoolean("is_drink")) return UseAnim.DRINK;
        return UseAnim.EAT;
    }

    public IStorageItemHandler loadInventory(ItemStack stack, HolderLookup.Provider registries) {
        IStorageItemHandler handler = createHandler();
        CustomData beData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (beData != null) {
            CompoundTag tag = beData.copyTag();
            if (tag.contains("inventory")) {
                handler.deserializeInventory(registries, tag.getCompound("inventory"));
            }
        }
        return handler;
    }

    public void saveInventory(ItemStack stack, IStorageItemHandler handler, HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", handler.serializeInventory(registries));
        BlockItem.setBlockEntityData(stack, getBlockEntityType(), tag);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        if (!Services.PLATFORM.isStorageTooltipIconsEnabled()) return Optional.empty();
        var mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.level == null) return Optional.empty();
        IStorageItemHandler handler = loadInventory(stack, mc.level.registryAccess());
        List<ItemStack> contents = new ArrayList<>();
        for (int i = 0; i < handler.getInventorySize(); i++) {
            ItemStack stored = handler.getInventoryItem(i);
            if (!stored.isEmpty()) contents.add(stored);
        }
        if (contents.isEmpty()) return Optional.empty();
        return Optional.of(new dev.averageanime.client.tooltip.StorageContentsTooltip(contents));
    }

    private int findFirstFoodSlot(ItemStack stack, Level level) {
        IStorageItemHandler handler = loadInventory(stack, level.registryAccess());
        for (int i = 0; i < handler.getInventorySize(); i++) {
            ItemStack stored = handler.getInventoryItem(i);
            if (!stored.isEmpty() && stored.has(DataComponents.FOOD)) return i;
        }
        return -1;
    }
}