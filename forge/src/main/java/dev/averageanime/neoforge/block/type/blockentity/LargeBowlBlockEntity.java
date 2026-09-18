package dev.averageanime.forge.block.type.blockentity;

import dev.averageanime.forge.block.BlockEntityRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class LargeBowlBlockEntity
        extends dev.averageanime.block.type.blockentity.LargeBowlBlockEntity {

    public LargeBowlBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistration.LARGE_BOWL.get(), pos, state);
    }
}
