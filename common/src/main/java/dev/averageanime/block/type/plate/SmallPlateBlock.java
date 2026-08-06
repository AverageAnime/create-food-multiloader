package dev.averageanime.block.type.plate;

import dev.averageanime.block.type.display.ContainerFoodBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SmallPlateBlock extends ContainerFoodBlock {

    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 1, 12.0);

    public SmallPlateBlock(Supplier<Item> displayItem, Supplier<? extends Block> baseBlock) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, 1, baseBlock);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }
}
