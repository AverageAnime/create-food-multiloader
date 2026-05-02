package dev.averageanime.block.type.display;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class PlateFoodBlock extends DisplayFoodBlock {
    public PlateFoodBlock(Supplier<Item> displayItem) {
        super(Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, 1);
    }
}
