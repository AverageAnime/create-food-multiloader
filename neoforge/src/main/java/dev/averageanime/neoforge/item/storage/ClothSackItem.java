package dev.averageanime.neoforge.item.storage;

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
import net.neoforged.neoforge.items.ItemStackHandler;
import dev.averageanime.neoforge.blockentity.ModBlockEntities;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.menu.ClothSackItemMenu;

public class ClothSackItem extends FoodStorageItem {

    public ClothSackItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean isInventoryEnabled() {
        return ModConfig.isClothSackInventoryEnabled();
    }

    @Override
    protected boolean isEatFromItemEnabled() {
        return ModConfig.isClothSackEatFromItem();
    }

    @Override
    protected InteractionResultHolder<ItemStack> onInventoryDisabled(ItemStack stack, boolean isClientSide) {
        return InteractionResultHolder.sidedSuccess(stack, isClientSide);
    }

    @Override
    protected void playOpenSound(Level level, Player player) {
        level.playSound(null, player.blockPosition(),
                SoundEvents.BUNDLE_INSERT, SoundSource.PLAYERS, 0.8f, 1.0f);
    }

    @Override
    protected MenuConstructor menuConstructor(int slotIndex) {
        return (id, inv, p) -> new ClothSackItemMenu(id, inv, slotIndex);
    }

    @Override
    protected Component menuTitle() {
        return Component.translatable("container.createfood.cloth_sack");
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntities.CLOTH_SACK.get();
    }

    @Override
    protected ItemStackHandler createHandler() {
        return new ItemStackHandler(4);
    }
}
