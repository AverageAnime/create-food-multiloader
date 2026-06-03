package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class UbeCreamUbeCakeBlock extends ModCakeBlock {

    public UbeCreamUbeCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.UBE_CREAM_UBE_CAKE_SLICE.get());
    }
}
