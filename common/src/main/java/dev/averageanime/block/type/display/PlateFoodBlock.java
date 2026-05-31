package dev.averageanime.block.type.display;

import dev.averageanime.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class PlateFoodBlock extends DisplayFoodBlock {
    public PlateFoodBlock(Supplier<Item> displayItem) {
        super(Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, 1);
    }

    @Override
    protected void handleLastItemEaten(BlockState state, Level level, BlockPos pos) {
        Block emptyPlate = Services.PLATFORM.getPlateBlock();
        BlockState emptyState = emptyPlate.defaultBlockState();
        if (emptyState.hasProperty(FACING) && state.hasProperty(FACING)) {
            emptyState = emptyState.setValue(FACING, state.getValue(FACING));
        }
        level.setBlock(pos, emptyState, 3);
    }
}
