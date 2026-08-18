package dev.averageanime.neoforge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.averageanime.config.ConfigValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Lifts the recipe-side fluid input cap to match the enlarged basin from {@link BasinFluidCapacityMixin}.
 *
 * <p>Required, not cosmetic: on 1.21.1 {@code ProcessingRecipe.validate()} returns a list of problems that is
 * wired into the recipe codec via {@code MapCodec.validate}, so a recipe declaring more fluid inputs than
 * {@code getMaxFluidInputCount()} fails codec validation rather than merely logging a warning.
 *
 * <p>{@code MixingRecipe} and {@code CompactingRecipe} inherit this method rather than overriding it, so both
 * are covered — harmless for compacting, which runs in the same basin.
 */
@Mixin(targets = "com.simibubi.create.content.processing.basin.BasinRecipe", remap = false)
public class BasinRecipeFluidLimitMixin {

    private static final int CREATEFOOD_FLUID_INPUTS = 4;

    @ModifyReturnValue(method = "getMaxFluidInputCount", at = @At("RETURN"))
    private int createfood$moreFluidInputs(int original) {
        return ConfigValues.isExpandedBasinFluidsEnabled() ? Math.max(original, CREATEFOOD_FLUID_INPUTS) : original;
    }
}
