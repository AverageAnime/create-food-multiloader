package dev.averageanime.neoforge.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class ModPieBlock extends PieBlock {

    public static final DirectionProperty FACING;
    protected static VoxelShape[] SHAPE_BY_BITE;

    public ModPieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
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

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, BITES});
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        SHAPE_BY_BITE = new VoxelShape[]{
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 8.0D, 8.0D, 4.0D, 14.0D),
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 8.0D)
                ),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 8.0D),
                Block.box(8.0D, 0.0D, 2.0D, 14.0D, 4.0D, 8.0D),
        };
    }

}