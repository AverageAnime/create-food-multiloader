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

public abstract class RationBoxItem extends StorageItem {

    protected RationBoxItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override protected boolean isInventoryEnabled()   { return Services.PLATFORM.isRationBoxInventoryEnabled(); }
    @Override protected boolean isEatFromItemEnabled() { return Services.PLATFORM.isRationBoxEatFromItemEnabled(); }

    @Override
    protected InteractionResultHolder<ItemStack> onInventoryDisabled(ItemStack stack, boolean isClientSide) {
        return InteractionResultHolder.pass(stack);
    }

    @Override
    protected void playOpenSound(Level level, Player player) {
        level.playSound(null, player.blockPosition(), SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    protected Component menuTitle() {
        return Component.translatable("container.createfood.ration_box");
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return Services.PLATFORM.getRationBoxBlockEntityType();
    }
}