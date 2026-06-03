package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class CakeChorusFruitBlock extends ModCakeBlock {

    public CakeChorusFruitBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.CREAM_CAKE_SLICE_CHORUS_FRUIT.get());
    }
}
