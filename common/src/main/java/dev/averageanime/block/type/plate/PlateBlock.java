package dev.averageanime.block.type.plate;

import dev.averageanime.block.type.display.FoodBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class PlateBlock extends FoodBlock {

    protected final Supplier<? extends Block> baseBlock;

    public PlateBlock(Supplier<Item> displayItem, int maxStackSize, Supplier<? extends Block> baseBlock) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, maxStackSize);
        this.baseBlock = baseBlock;
    }

    @Override
    protected void handleLastItemRemoved(BlockState state, Level level, BlockPos pos) {
        BlockState plateState = this.baseBlock.get().defaultBlockState();
        if (plateState.hasProperty(FACING)) {
            plateState = plateState.setValue(FACING, state.getValue(FACING));
        }
        level.setBlock(pos, plateState, 3);
    }
}