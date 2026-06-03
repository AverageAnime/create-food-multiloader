package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class ChorusFruitCreamCakeBlock extends ModCakeBlock {

    public ChorusFruitCreamCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE.get());
    }
}
