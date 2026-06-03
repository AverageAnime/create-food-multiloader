package net.averageanime.createfood.item.storage;

import net.averageanime.createfood.block.ModBlockEntities;
import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.averageanime.createfood.menu.RationBoxItemMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RationBoxItem extends StorageItem {

    public RationBoxItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean isInventoryEnabled() {
        return CreateFoodConfig.SERVER.rationBoxInventoryEnabled.get();
    }

    @Override
    protected boolean isEatFromItemEnabled() {
        return CreateFoodConfig.SERVER.rationBoxEatFromItem.get();
    }

    @Override
    protected InteractionResultHolder<ItemStack> onInventoryDisabled(ItemStack stack, boolean isClientSide) {
        return InteractionResultHolder.pass(stack);
    }

    @Override
    protected void playOpenSound(Level level, Player player) {
        level.playSound(null, player.blockPosition(), SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    protected MenuConstructor menuConstructor(int slotIndex) {
        return (id, inv, p) -> new RationBoxItemMenu(id, inv, slotIndex);
    }

    @Override
    protected Component menuTitle() {
        return Component.translatable("container.createfood.ration_box");
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntities.RATION_BOX.get();
    }

    @Override
    protected IStorageItemHandler createHandler() {
        return new StorageInventory(5,
                slot -> CreateFoodConfig.SERVER.rationBoxStack.get() ? 64 : 1,
                (slot, stack) -> ConfigLogic.isRationBoxItemAllowed(stack));
    }
}
