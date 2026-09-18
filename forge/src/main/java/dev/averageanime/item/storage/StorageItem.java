package dev.averageanime.item.storage;

import dev.averageanime.platform.Services;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import dev.averageanime.util.FoodAccess;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public abstract class StorageItem extends BlockItem {

    private static final int DEFAULT_USE_TICKS = 32;

    private static final Map<CompoundTag, StorageAccess> READ_CACHE =
            Collections.synchronizedMap(new WeakHashMap<>());

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
    protected abstract StorageAccess createHandler();

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

        StorageAccess handler = loadInventoryFresh(heldStack, level.registryAccess());
        int slot = findFirstFoodSlot(handler);
        if (slot >= 0) {
            ItemStack stored = handler.getInventoryItem(slot);
            FoodProperties props = FoodAccess.get(stored, player);
            if (props != null && (player.canEat(props.canAlwaysEat()) || player.getAbilities().instabuild)) {
                ItemStack modified = heldStack.copy();
                CompoundTag tag = modified.getTag() == null ? new CompoundTag() : modified.getTag().copy();
                tag.putBoolean("is_drink", stored.getUseAnimation() == UseAnim.DRINK);
                tag.putInt("use_ticks", stored.getUseDuration());
                modified.getOrCreateTag().putBoolean("is_drink", tag.getBoolean("is_drink"));
                modified.getOrCreateTag().putInt("use_ticks", tag.getInt("use_ticks"));
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(modified);
            }
        }
        return InteractionResultHolder.pass(heldStack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack,
            @NotNull Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            StorageAccess handler = loadInventoryFresh(stack, level.registryAccess());
            int slot = findFirstFoodSlot(handler);
            if (slot >= 0) {
                ItemStack food = handler.getInventoryItem(slot).copyWithCount(1);
                // finishUsingItem consumes the copy and hands back the container; it is also the only
                // path that runs effect overrides and deferred effects on EffectFood / EffectDrink
                ItemStack original = food.copy();
                ItemStack container = food.finishUsingItem(level, player);
                handler.removeItem(slot, 1);

                if (!container.isEmpty() && !ItemStack.isSameItem(container, original)) {
                    ItemStack leftover = container;
                    for (int s = 0; s < handler.getInventorySize(); s++) {
                        leftover = handler.insertItem(s, leftover);
                        if (leftover.isEmpty()) break;
                    }
                    if (!leftover.isEmpty() && !player.getInventory().add(leftover)) {
                        player.drop(leftover, false);
                    }
                }

                saveInventory(stack, handler, level.registryAccess());
            }
        }
        return stack;
    }

    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        CompoundTag beData = stack.getTag();
        if (beData != null) {
            int ticks = beData.getInt("use_ticks");
            if (ticks > 0) return ticks;
        }
        return DEFAULT_USE_TICKS;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        CompoundTag beData = stack.getTag();
        if (beData != null && beData.getBoolean("is_drink")) return UseAnim.DRINK;
        return UseAnim.EAT;
    }

    public StorageAccess loadInventory(ItemStack stack, HolderLookup.Provider registries) {
        CompoundTag beData = stack.getTag();
        if (beData == null) return createHandler();
        StorageAccess cached = READ_CACHE.get(beData);
        if (cached != null) return cached;
        StorageAccess handler = deserialize(beData, registries);
        READ_CACHE.put(beData, handler);
        return handler;
    }

    private StorageAccess loadInventoryFresh(ItemStack stack, HolderLookup.Provider registries) {
        CompoundTag beData = stack.getTag();
        if (beData == null) return createHandler();
        return deserialize(beData, registries);
    }

    private StorageAccess deserialize(CompoundTag beData, HolderLookup.Provider registries) {
        StorageAccess handler = createHandler();
        if (beData.contains("inventory")) {
            handler.deserializeInventory(beData.getCompound("inventory"));
        }
        return handler;
    }

    public void saveInventory(ItemStack stack, StorageAccess handler, HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", handler.serializeInventory());
        BlockItem.setBlockEntityData(stack, getBlockEntityType(), tag);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        if (!Services.PLATFORM.isStorageTooltipIconsEnabled()) return Optional.empty();
        var mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.level == null) return Optional.empty();
        StorageAccess handler = loadInventory(stack, mc.level.registryAccess());
        List<ItemStack> contents = new ArrayList<>();
        for (int i = 0; i < handler.getInventorySize(); i++) {
            ItemStack stored = handler.getInventoryItem(i);
            if (!stored.isEmpty()) contents.add(stored);
        }
        if (contents.isEmpty()) return Optional.empty();
        return Optional.of(new dev.averageanime.client.tooltip.StorageContentsTooltip(contents));
    }

    private static int findFirstFoodSlot(StorageAccess handler) {
        for (int i = 0; i < handler.getInventorySize(); i++) {
            ItemStack stored = handler.getInventoryItem(i);
            if (!stored.isEmpty() && FoodAccess.isFood(stored)) return i;
        }
        return -1;
    }
}