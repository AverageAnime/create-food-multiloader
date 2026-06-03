package net.averageanime.createfood.item.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuConstructor;
import net.averageanime.createfood.client.tooltip.StorageContentsTooltip;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

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
                MenuProvider provider = new SimpleMenuProvider(menuConstructor(slotIndex), menuTitle());
                NetworkHooks.openScreen(
                        (net.minecraft.server.level.ServerPlayer) player,
                        provider,
                        buf -> buf.writeInt(slotIndex));
            }
            return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide);
        }

        int slot = findFirstFoodSlot(heldStack);
        if (slot >= 0 && player.canEat(false)) {
            IStorageItemHandler handler = loadInventory(heldStack);
            boolean isDrink = handler.getInventoryItem(slot).getUseAnimation() == UseAnim.DRINK;
            ItemStack modified = heldStack.copy();
            // Store is_drink in BlockEntityTag
            CompoundTag beTag = modified.getOrCreateTagElement("BlockEntityTag");
            beTag.putBoolean("is_drink", isDrink);
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(modified);
        }
        return InteractionResultHolder.pass(heldStack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack,
            @NotNull Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            int slot = findFirstFoodSlot(stack);
            if (slot >= 0) {
                IStorageItemHandler handler = loadInventory(stack);
                ItemStack food = handler.getInventoryItem(slot);
                ItemStack foodCopy = food.copy();
                foodCopy.setCount(1);

                // Get the container item returned from eating (e.g. bowl from stew)
                ItemStack afterEating = food.getItem().finishUsingItem(foodCopy.copy(), level, entity);

                player.eat(level, foodCopy);
                handler.removeItem(slot, 1);

                // afterEating is the remainder (e.g. empty bowl)
                if (!afterEating.isEmpty() && afterEating.getItem() != foodCopy.getItem()) {
                    for (int s = 0; s < handler.getInventorySize(); s++) {
                        afterEating = handler.insertItem(s, afterEating);
                        if (afterEating.isEmpty()) break;
                    }
                    if (!afterEating.isEmpty() && !player.getInventory().add(afterEating)) {
                        player.drop(afterEating, false);
                    }
                }

                saveInventory(stack, handler);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        Tag beTagRaw = stack.getTagElement("BlockEntityTag");
        if (beTagRaw instanceof CompoundTag beTag && beTag.getBoolean("is_drink")) return UseAnim.DRINK;
        return UseAnim.EAT;
    }

    public IStorageItemHandler loadInventory(ItemStack stack) {
        IStorageItemHandler handler = createHandler();
        Tag beTagRaw = stack.getTagElement("BlockEntityTag");
        if (beTagRaw instanceof CompoundTag beTag && beTag.contains("inventory")) {
            handler.deserializeInventory(beTag.getCompound("inventory"));
        }
        return handler;
    }

    public void saveInventory(ItemStack stack, IStorageItemHandler handler) {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", handler.serializeInventory());
        BlockItem.setBlockEntityData(stack, getBlockEntityType(), tag);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        if (CreateFoodConfig.CLIENT == null || !CreateFoodConfig.CLIENT.showStorageTooltipIcons.get())
            return Optional.empty();
        IStorageItemHandler handler = loadInventory(stack);
        java.util.List<ItemStack> contents = new java.util.ArrayList<>();
        for (int i = 0; i < handler.getInventorySize(); i++) {
            ItemStack stored = handler.getInventoryItem(i);
            if (!stored.isEmpty()) contents.add(stored);
        }
        return contents.isEmpty() ? Optional.empty()
                : Optional.of(new StorageContentsTooltip(contents));
    }

    private int findFirstFoodSlot(ItemStack stack) {
        IStorageItemHandler handler = loadInventory(stack);
        for (int i = 0; i < handler.getInventorySize(); i++) {
            ItemStack stored = handler.getInventoryItem(i);
            FoodProperties food = stored.getItem().getFoodProperties(stored, null);
            if (!stored.isEmpty() && food != null) return i;
        }
        return -1;
    }
}
