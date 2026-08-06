package dev.averageanime.block.type.plate;

import dev.averageanime.block.type.display.ContainerFoodBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class PlateBlock extends ContainerFoodBlock {

    public PlateBlock(Supplier<Item> displayItem, int maxStackSize, Supplier<? extends Block> baseBlock) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, maxStackSize, baseBlock);
    }
}
