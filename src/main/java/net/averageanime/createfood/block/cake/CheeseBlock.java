package net.averageanime.createfood.block.cake;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CheeseBlock extends ModCakeBlock {

    protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 15.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 13.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 11.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 9.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 7.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 5.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 3.0)
    };

    public CheeseBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.CHEESE_SLICE.get());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape base = SHAPE_BY_BITE[state.getValue(BITES)];
        Direction facing = state.getValue(FACING);
        if (facing == Direction.NORTH) return base;
        return rotateShapeForCheese(base, facing);
    }

    private static VoxelShape rotateShapeForCheese(VoxelShape shape, Direction direction) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};
        int times = (direction.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                    buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }
        return buffer[0];
    }
}
