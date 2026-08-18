package dev.averageanime.block.type.bowl;

import dev.averageanime.block.type.display.DisplayFoodBlock;
import dev.averageanime.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LargeBowlFoodBlock extends DisplayFoodBlock {
    protected static final VoxelShape SHAPE = Block.box(4, 0.0, 4, 12, 2, 12);

    public LargeBowlFoodBlock(Supplier<Item> displayItem) {
        super(Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, 1);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean handlesOwnContainer() { return true; }

    @Override
    protected void handleLastItemEaten(BlockState state, Level level, BlockPos pos) {
        Block emptyLargeBowl = Services.PLATFORM.getLargeBowlBlock();
        BlockState emptyState = emptyLargeBowl.defaultBlockState();
        if (emptyState.hasProperty(FACING) && state.hasProperty(FACING)) {
            emptyState = emptyState.setValue(FACING, state.getValue(FACING));
        }
        level.setBlock(pos, emptyState, 3);
    }
}
