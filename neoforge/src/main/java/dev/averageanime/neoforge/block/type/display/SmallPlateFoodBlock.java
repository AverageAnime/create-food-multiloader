package dev.averageanime.neoforge.block.type.display;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SmallPlateFoodBlock extends FoodBlock {

    protected final Supplier<? extends Block> baseBlock;
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 1, 12.0);

    public SmallPlateFoodBlock(Supplier<Item> displayItem, Supplier<? extends Block> baseBlock) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, 1);
        this.baseBlock = baseBlock;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
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