package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class ChorusFruitCreamCakeChorusFruitBlock extends ModCakeBlock {

    public ChorusFruitCreamCakeChorusFruitBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT.get());
    }
}
