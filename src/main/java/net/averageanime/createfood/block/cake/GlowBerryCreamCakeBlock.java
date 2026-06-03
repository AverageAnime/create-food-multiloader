package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class GlowBerryCreamCakeBlock extends ModCakeBlock {

    public GlowBerryCreamCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.GLOW_BERRY_CREAM_CAKE_SLICE.get());
    }
}
