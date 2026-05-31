package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class CreamChocolateCakeBlock extends ModCakeBlock {

    public CreamChocolateCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.CREAM_CHOCOLATE_CAKE_SLICE.get());
    }
}
