package dev.averageanime.fabric.item.storage;

import dev.averageanime.fabric.block.type.blockentity.ModBlockEntities;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.menu.RationBoxItemMenu;
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

public class RationBoxItem extends FoodStorageItem {

    public RationBoxItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean isInventoryEnabled() {
        return ModConfig.isRationBoxInventoryEnabled();
    }

    @Override
    protected boolean isEatFromItemEnabled() {
        return ModConfig.isRationBoxEatFromItemEnabled();
    }

    @Override
    protected InteractionResultHolder<ItemStack> onInventoryDisabled(ItemStack stack, boolean isClientSide) {
        return InteractionResultHolder.pass(stack);
    }

    @Override
    protected void playOpenSound(Level level, Player player) {
        level.playSound(null, player.blockPosition(),
                SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0f, 1.0f);
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
        return ModBlockEntities.RATION_BOX;
    }

    @Override
    protected FoodInventory createHandler() {
        return new FoodInventory(5,
                () -> ModConfig.isRationBoxStacking() ? 64 : 1,
                (slot, stack) -> ModConfig.isRationBoxItemAllowed(stack));
    }
}
