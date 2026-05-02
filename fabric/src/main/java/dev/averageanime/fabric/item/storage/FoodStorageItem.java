package dev.averageanime.fabric.item.storage;

import dev.averageanime.fabric.config.ModConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class FoodStorageItem extends BlockItem {

    protected FoodStorageItem(Block block, Properties properties) {
        super(block, properties);
    }

    protected abstract boolean isInventoryEnabled();
    protected abstract boolean isEatFromItemEnabled();
    protected abstract InteractionResultHolder<ItemStack> onInventoryDisabled(ItemStack stack, boolean isClientSide);
    protected abstract void playOpenSound(Level level, Player player);
    protected abstract MenuConstructor menuConstructor(int slotIndex);
    protected abstract Component menuTitle();
    protected abstract BlockEntityType<?> getBlockEntityType();
    protected abstract FoodInventory createHandler();

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!isInventoryEnabled()) return onInventoryDisabled(heldStack, level.isClientSide);

        if (!isEatFromItemEnabled()) {
            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                int slotIndex = hand == InteractionHand.MAIN_HAND
                        ? player.getInventory().selected : 40;
                playOpenSound(level, player);
                final int finalSlot = slotIndex;
                final MenuConstructor constructor = menuConstructor(slotIndex);
                final Component title = menuTitle();
                serverPlayer.openMenu(new ExtendedScreenHandlerFactory<Integer>() {
                    @Override
                    public @NotNull Component getDisplayName() { return title; }

                    @Override
                    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player p) {
                        return constructor.createMenu(id, inv, p);
                    }

                    @Override
                    public Integer getScreenOpeningData(ServerPlayer p) { return finalSlot; }
                });
            }
            return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide);
        }

        int slot = findFirstFoodSlot(heldStack, level);
        if (slot >= 0 && player.canEat(false)) {
            FoodInventory handler = loadInventory(heldStack, level.registryAccess());
            boolean isDrink = handler.getItem(slot).getUseAnimation() == UseAnim.DRINK;
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
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            int slot = findFirstFoodSlot(stack, level);
            if (slot >= 0) {
                FoodInventory handler = loadInventory(stack, level.registryAccess());
                ItemStack food = handler.getItem(slot).copyWithCount(1);

                FoodProperties foodProps = food.get(DataComponents.FOOD);
                Optional<ItemStack> containerOpt = foodProps != null
                        ? foodProps.usingConvertsTo() : Optional.empty();

                player.eat(level, food);
                handler.removeItem(slot, 1);

                containerOpt.ifPresent(container -> {
                    ItemStack leftover = container.copy();
                    for (int s = 0; s < handler.getContainerSize(); s++) {
                        if (leftover.isEmpty()) break;
                        if (handler.getItem(s).isEmpty()) {
                            handler.setItem(s, leftover.copyWithCount(Math.min(leftover.getCount(), handler.getMaxStackSize())));
                            leftover.shrink(handler.getItem(s).getCount());
                        }
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
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        CustomData beData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (beData != null && beData.copyTag().getBoolean("is_drink")) return UseAnim.DRINK;
        return UseAnim.EAT;
    }

    public FoodInventory loadInventory(ItemStack stack, HolderLookup.Provider registries) {
        FoodInventory handler = createHandler();
        CustomData beData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (beData != null) {
            CompoundTag tag = beData.copyTag();
            if (tag.contains("inventory")) {
                handler.deserializeNBT(registries, tag.getCompound("inventory"));
            }
        }
        return handler;
    }

    public void saveInventory(ItemStack stack, FoodInventory handler, HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", handler.serializeNBT(registries));
        BlockItem.setBlockEntityData(stack, getBlockEntityType(), tag);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        if (!ModConfig.isStorageTooltipIconsEnabled()) return Optional.empty();
        var mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.level == null) return Optional.empty();
        FoodInventory handler = loadInventory(stack, mc.level.registryAccess());
        List<ItemStack> contents = new ArrayList<>();
        for (int i = 0; i < handler.getContainerSize(); i++) {
            ItemStack stored = handler.getItem(i);
            if (!stored.isEmpty()) contents.add(stored);
        }
        if (contents.isEmpty()) return Optional.empty();
        return Optional.of(new dev.averageanime.fabric.client.tooltip.StorageContentsTooltip(contents));
    }

    private int findFirstFoodSlot(ItemStack stack, Level level) {
        FoodInventory handler = loadInventory(stack, level.registryAccess());
        for (int i = 0; i < handler.getContainerSize(); i++) {
            ItemStack stored = handler.getItem(i);
            if (!stored.isEmpty() && stored.has(DataComponents.FOOD)) return i;
        }
        return -1;
    }
}
