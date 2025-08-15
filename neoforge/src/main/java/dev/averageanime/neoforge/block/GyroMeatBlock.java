package dev.averageanime.neoforge.block;

import dev.averageanime.neoforge.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GyroMeatBlock extends ModCakeBlock {

    protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 15.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 13.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 11.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 9.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 7.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 5.0),
            Block.box(3.0, 0.0, 1.0, 13.0, 8.0, 3.0)
    };

    public GyroMeatBlock(Properties properties) {
        super(properties, ModItems.GYRO_MEAT_SLICE);
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack((ItemLike) ModItems.GYRO_MEAT_SLICE);
    }

    public VoxelShape getShape(BlockState State, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape baseShape = SHAPE_BY_BITE[State.getValue(BITES)];
        Direction facing = State.getValue(FACING);

        if (facing == Direction.EAST) {
            return rotateShape(baseShape, Direction.EAST);
        } else if (facing == Direction.SOUTH) {
            return rotateShape(baseShape, Direction.SOUTH);
        } else if (facing == Direction.WEST) {
            return rotateShape(baseShape, Direction.WEST);
        } else {
            return baseShape;
        }
    }

    private VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (direction.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }
        return buffer[0];
    }

}