package dev.averageanime.neoforge.block.type.blockentity;

import dev.averageanime.neoforge.block.BlockEntityRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SmallBowlBlockEntity
        extends dev.averageanime.block.type.blockentity.SmallBowlBlockEntity {

    public SmallBowlBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistration.SMALL_BOWL.get(), pos, state);
    }
}
