package dev.averageanime.neoforge.block.type.cake;

import dev.averageanime.neoforge.block.type.ConsumableBlock;
import dev.averageanime.neoforge.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack((ItemLike) ModItems.GYRO_MEAT_SLICE);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ConsumableBlock.rotateShape(SHAPE_BY_BITE[state.getValue(BITES)], state.getValue(FACING));
    }
}
