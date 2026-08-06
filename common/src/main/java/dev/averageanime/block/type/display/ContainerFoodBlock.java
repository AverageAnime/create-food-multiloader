package dev.averageanime.block.type.display;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public abstract class ContainerFoodBlock extends FoodBlock {

    protected final Supplier<? extends Block> baseBlock;

    protected ContainerFoodBlock(Properties properties, Supplier<Item> displayItem, int maxStackSize,
                                  Supplier<? extends Block> baseBlock) {
        super(properties, displayItem, maxStackSize);
        this.baseBlock = baseBlock;
    }

    @Override
    protected void handleLastItemRemoved(BlockState state, Level level, BlockPos pos) {
        BlockState baseState = this.baseBlock.get().defaultBlockState();
        if (baseState.hasProperty(FACING)) {
            baseState = baseState.setValue(FACING, state.getValue(FACING));
        }
        level.setBlock(pos, baseState, 3);
    }
}
