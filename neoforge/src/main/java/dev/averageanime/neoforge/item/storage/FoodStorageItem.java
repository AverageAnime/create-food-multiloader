package dev.averageanime.neoforge.item.storage;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemStackHandler;
import dev.averageanime.neoforge.client.tooltip.StorageContentsTooltip;
import dev.averageanime.neoforge.config.ModConfig;
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
    protected abstract ItemStackHandler createHandler();

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level,
            @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!isInventoryEnabled()) return onInventoryDisabled(heldStack, level.isClientSide);

        if (!isEatFromItemEnabled()) {
            if (!level.isClientSide) {
                int slotIndex = hand == InteractionHand.MAIN_HAND
                        ? player.getInventory().selected
                        : 40;
                playOpenSound(level, player);
                player.openMenu(
                        new SimpleMenuProvider(menuConstructor(slotIndex), menuTitle()),
                        buf -> buf.writeInt(slotIndex));
            }
            return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide);
        }

        int slot = findFirstFoodSlot(heldStack, level);
        if (slot >= 0 && player.canEat(false)) {
            ItemStack food = loadHandler(heldStack, level.registryAccess()).getStackInSlot(slot);
            boolean isDrink = food.getUseAnimation() == UseAnim.DRINK;
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
                ItemStackHandler handler = loadHandler(stack, level.registryAccess());
                ItemStack food = handler.getStackInSlot(slot).copyWithCount(1);

                FoodProperties foodProps = food.getFoodProperties(player);
                Optional<ItemStack> containerOpt = foodProps != null
                        ? foodProps.usingConvertsTo() : Optional.empty();

                player.eat(level, food);
                handler.extractItem(slot, 1, false);

                containerOpt.ifPresent(container -> {
                    ItemStack leftover = container.copy();
                    for (int s = 0; s < handler.getSlots(); s++) {
                        leftover = handler.insertItem(s, leftover, false);
                        if (leftover.isEmpty()) break;
                    }
                    if (!leftover.isEmpty() && !player.getInventory().add(leftover)) {
                        player.drop(leftover, false);
                    }
                });

                saveHandler(stack, handler, level.registryAccess());
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

    public ItemStackHandler loadHandler(ItemStack stack, HolderLookup.Provider registries) {
        ItemStackHandler handler = createHandler();
        CustomData beData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (beData != null) {
            CompoundTag tag = beData.copyTag();
            if (tag.contains("inventory")) {
                handler.deserializeNBT(registries, tag.getCompound("inventory"));
            }
        }
        return handler;
    }

    public void saveHandler(ItemStack stack, ItemStackHandler handler, HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", handler.serializeNBT(registries));
        BlockItem.setBlockEntityData(stack, getBlockEntityType(), tag);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        if (!ModConfig.isStorageTooltipIconsEnabled()) return Optional.empty();
        var mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.level == null) return Optional.empty();
        ItemStackHandler handler = loadHandler(stack, mc.level.registryAccess());
        List<ItemStack> contents = new ArrayList<>();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stored = handler.getStackInSlot(i);
            if (!stored.isEmpty()) contents.add(stored);
        }
        return contents.isEmpty() ? Optional.empty() : Optional.of(new StorageContentsTooltip(contents));
    }

    private int findFirstFoodSlot(ItemStack stack, Level level) {
        ItemStackHandler handler = loadHandler(stack, level.registryAccess());
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stored = handler.getStackInSlot(i);
            if (!stored.isEmpty() && stored.has(DataComponents.FOOD)) {
                return i;
            }
        }
        return -1;
    }
}
