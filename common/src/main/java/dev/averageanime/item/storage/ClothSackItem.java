package dev.averageanime.item.storage;

import dev.averageanime.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public abstract class ClothSackItem extends StorageItem {

    protected ClothSackItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override protected boolean isInventoryEnabled()   { return Services.PLATFORM.isClothSackInventoryEnabled(); }
    @Override protected boolean isEatFromItemEnabled() { return Services.PLATFORM.isClothSackEatFromItem(); }

    @Override
    protected InteractionResultHolder<ItemStack> onInventoryDisabled(ItemStack stack, boolean isClientSide) {
        return InteractionResultHolder.sidedSuccess(stack, isClientSide);
    }

    @Override
    protected void playOpenSound(Level level, Player player) {
        level.playSound(null, player.blockPosition(), SoundEvents.BUNDLE_INSERT, SoundSource.PLAYERS, 0.8f, 1.0f);
    }

    @Override
    protected Component menuTitle() {
        return Component.translatable("container.createfood.cloth_sack");
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return Services.PLATFORM.getClothSackBlockEntityType();
    }
}