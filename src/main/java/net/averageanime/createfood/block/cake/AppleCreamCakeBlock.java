package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class AppleCreamCakeBlock extends ModCakeBlock {

    public AppleCreamCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.APPLE_CREAM_CAKE_SLICE.get());
    }
}
