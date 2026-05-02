package dev.averageanime.block.type.pie;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class PieVoxelShapes {
    private PieVoxelShapes() {}

    public static VoxelShape[] byBite(double height) {
        return new VoxelShape[]{
                Block.box(2, 0, 2, 14, height, 14),
                Shapes.or(
                        Block.box(2, 0, 8, 8, height, 14),
                        Block.box(2, 0, 2, 14, height, 8)
                ),
                Block.box(2, 0, 2, 14, height, 8),
                Block.box(8, 0, 2, 14, height, 8),
        };
    }
}
