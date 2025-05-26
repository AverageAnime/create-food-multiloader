package net.averageanime.createfood.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WaffleBlock extends ModPieBlock {

    protected static VoxelShape[] BITES_TO_SHAPE;

    public WaffleBlock(Item pieSlice) {
        super(pieSlice);
        this.setDefaultState(this.stateManager.getDefaultState().with(BITES, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        VoxelShape originalShape = BITES_TO_SHAPE[state.get(BITES)];
        if (facing == Direction.NORTH) {
            return originalShape;
        } else {
            return rotateShape(Direction.NORTH, facing, originalShape);
        }
    }

    private static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};

        int times = (to.getHorizontal() - from.getHorizontal() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    static {
        BITES_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
                VoxelShapes.union(
                        Block.createCuboidShape(2.0D, 0.0D, 8.0D, 8.0D, 2.0D, 14.0D),
                        Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 8.0D)
                ),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 8.0D),
                Block.createCuboidShape(8.0D, 0.0D, 2.0D, 14.0D, 2.0D, 8.0D)
        };
    }

}
