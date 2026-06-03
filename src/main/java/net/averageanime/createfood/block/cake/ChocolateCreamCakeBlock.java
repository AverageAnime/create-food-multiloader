package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class ChocolateCreamCakeBlock extends ModCakeBlock {

    public ChocolateCreamCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.CHOCOLATE_CREAM_CAKE_SLICE.get());
    }
}
