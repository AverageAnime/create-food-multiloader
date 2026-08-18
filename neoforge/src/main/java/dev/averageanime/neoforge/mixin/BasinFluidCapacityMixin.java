package dev.averageanime.neoforge.mixin;

import dev.averageanime.config.ConfigValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Raises Create's basin from 2 to 4 fluid <em>input</em> slots so recipes can call for more than two
 * distinct fluids. The output tank is left at Create's stock 2.
 *
 * <p>{@code BasinBlockEntity.addBehaviours} builds both tanks inline; the input tank is the first
 * {@code SmartFluidTankBehaviour} constructed, so {@code ordinal = 0} isolates it. Arg 2 of that
 * constructor is the tank <em>segment</em> count (segment capacity, arg 3, stays at 1000 mB), and
 * {@code enforceVariety} keeps one distinct fluid per segment.
 *
 * <p>Everything downstream is already arity-agnostic: the basin's {@code CombinedTankWrapper} is
 * built from the field after this returns, {@code BasinRecipe.apply} walks {@code getTanks()}, and
 * both the renderer and the mixer iterate the segment array.
 */
@Mixin(targets = "com.simibubi.create.content.processing.basin.BasinBlockEntity", remap = false)
public class BasinFluidCapacityMixin {

    private static final int CREATEFOOD_INPUT_TANKS = 4;

    @ModifyArg(
            method = "addBehaviours",
            at = @At(
                    value = "INVOKE",
                    ordinal = 0,
                    target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/fluid/SmartFluidTankBehaviour;"
                            + "<init>(Lcom/simibubi/create/foundation/blockEntity/behaviour/BehaviourType;"
                            + "Lcom/simibubi/create/foundation/blockEntity/SmartBlockEntity;IIZ)V"),
            index = 2)
    private int createfood$moreInputTanks(int tanks) {
        return ConfigValues.isExpandedBasinFluidsEnabled() ? Math.max(tanks, CREATEFOOD_INPUT_TANKS) : tanks;
    }
}
