package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class MelonCreamCakeBlock extends ModCakeBlock {

    public MelonCreamCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.MELON_CREAM_CAKE_SLICE.get());
    }
}
