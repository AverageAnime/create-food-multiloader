package dev.averageanime.fabric.block.cake;

import dev.averageanime.fabric.block.ModCakeBlock;
import dev.averageanime.fabric.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class ChocolateCreamCakeCaramel extends ModCakeBlock {

    public ChocolateCreamCakeCaramel(Properties properties) {
        super(properties, (Supplier<Item>) ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL);
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack((ItemLike) ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL);
    }

}