package dev.averageanime.fabric.block.type.blockentity;

import dev.averageanime.fabric.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GenericDisplayPlateBlockEntity
        extends dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity {

    public GenericDisplayPlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENERIC_DISPLAY_PLATE, pos, state);
    }
}
