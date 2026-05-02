package dev.averageanime.block.type.pie;

import dev.averageanime.block.type.ConsumableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PieBlock extends ConsumableBlock {

    protected static final VoxelShape[] SHAPE_BY_BITE = PieVoxelShapes.byBite(4);

    public PieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape base = SHAPE_BY_BITE[state.getValue(BITES)];
        Direction facing = state.getValue(FACING);
        return facing == Direction.NORTH ? base : rotateShape(base, facing);
    }
}
