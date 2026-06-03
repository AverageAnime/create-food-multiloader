package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class BerryCreamCakeSweetBerryBlock extends ModCakeBlock {

    public BerryCreamCakeSweetBerryBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.BERRY_CREAM_CAKE_SLICE_SWEET_BERRY.get());
    }
}
