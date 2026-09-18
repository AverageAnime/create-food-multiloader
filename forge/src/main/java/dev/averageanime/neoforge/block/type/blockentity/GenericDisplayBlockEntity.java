package dev.averageanime.forge.block.type.blockentity;

import dev.averageanime.forge.block.BlockEntityRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GenericDisplayBlockEntity
        extends dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity {

    public GenericDisplayBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistration.GENERIC_DISPLAY_PLATE.get(), pos, state);
    }
}
