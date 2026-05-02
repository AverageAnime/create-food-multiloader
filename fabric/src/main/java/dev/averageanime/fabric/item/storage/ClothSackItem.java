package dev.averageanime.fabric.item.storage;

import dev.averageanime.fabric.block.type.blockentity.ModBlockEntities;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.menu.ClothSackItemMenu;
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
        return ModBlockEntities.CLOTH_SACK;
    }

    @Override
    protected FoodInventory createHandler() {
        return new FoodInventory(4,
                () -> ModConfig.isClothSackStacking() ? 64 : 1,
                (slot, stack) -> ModConfig.isClothSackItemAllowed(stack));
    }
}
