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
import dev.averageanime.neoforge.block.type.blockentity.ModBlockEntities;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.menu.RationBoxItemMenu;
import org.jetbrains.annotations.NotNull;

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
        return ModBlockEntities.RATION_BOX.get();
    }

    @Override
    protected ItemStackHandler createHandler() {
        return new ItemStackHandler(5) {
            @Override
            public int getSlotLimit(int slot) {
                return ModConfig.isRationBoxStacking() ? 64 : 1;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return ModConfig.isRationBoxItemAllowed(stack);
            }
        };
    }
}
