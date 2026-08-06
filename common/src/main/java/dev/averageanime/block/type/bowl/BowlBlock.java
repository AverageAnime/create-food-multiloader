package dev.averageanime.block.type.bowl;

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

public class BowlBlock extends ContainerFoodBlock {

    protected static final VoxelShape SHAPE = Block.box(5, 0.0, 5, 11, 4, 11);

    public BowlBlock(Supplier<Item> displayItem, int maxStackSize, Supplier<? extends Block> baseBlock) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, maxStackSize, baseBlock);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }
}
