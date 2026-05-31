package dev.averageanime.neoforge.block.type.blockentity;

import dev.averageanime.neoforge.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GenericDisplayPlateBlockEntity
        extends dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity {

    public GenericDisplayPlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENERIC_DISPLAY_PLATE.get(), pos, state);
    }
}
