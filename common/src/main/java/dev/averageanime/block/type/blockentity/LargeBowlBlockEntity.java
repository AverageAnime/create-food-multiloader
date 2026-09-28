package dev.averageanime.block.type.blockentity;

import dev.averageanime.config.ConfigValues;
import dev.averageanime.createfood.lib.block.FluidTankBlockEntity;
import dev.averageanime.registry.FluidAmounts;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** The large bowl's fluid tank; capacity is a config value, so it is read fresh rather than captured. */
public class LargeBowlBlockEntity extends FluidTankBlockEntity {

    public static final int DEFAULT_CAPACITY_MB = FluidAmounts.BUCKET * 4;

    public LargeBowlBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, ConfigValues::getLargeBowlCapacityMb);
    }
}
