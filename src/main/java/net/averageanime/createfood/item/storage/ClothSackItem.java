package net.averageanime.createfood.item.storage;

import net.averageanime.createfood.block.ModBlockEntities;
import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.averageanime.createfood.menu.ClothSackItemMenu;
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

public class ClothSackItem extends StorageItem {

    public ClothSackItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean isInventoryEnabled() {
        return CreateFoodConfig.SERVER.clothSackInventoryEnabled.get();
    }

    @Override
    protected boolean isEatFromItemEnabled() {
        return CreateFoodConfig.SERVER.clothSackEatFromItem.get();
    }

    @Override
    protected InteractionResultHolder<ItemStack> onInventoryDisabled(ItemStack stack, boolean isClientSide) {
        return InteractionResultHolder.sidedSuccess(stack, isClientSide);
    }

    @Override
    protected void playOpenSound(Level level, Player player) {
        level.playSound(null, player.blockPosition(), SoundEvents.BUNDLE_INSERT, SoundSource.PLAYERS, 0.8f, 1.0f);
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
    protected IStorageItemHandler createHandler() {
        return new StorageInventory(4,
                slot -> CreateFoodConfig.SERVER.clothSackStack.get() ? 64 : 1,
                (slot, stack) -> !(stack.getItem() instanceof ClothSackItem)
                        && ConfigLogic.isClothSackItemAllowed(stack));
    }
}
