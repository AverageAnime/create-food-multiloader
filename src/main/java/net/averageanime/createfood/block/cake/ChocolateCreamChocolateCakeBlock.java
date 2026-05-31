package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class ChocolateCreamChocolateCakeBlock extends ModCakeBlock {

    public ChocolateCreamChocolateCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.CHOCOLATE_CREAM_CHOCOLATE_CAKE_SLICE.get());
    }
}
