package dev.averageanime.neoforge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.averageanime.item.remainder.CraftingRemainder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

// Deliberate exception to this mod's usual no-mixins-into-other-mods policy: Create's encased-fan
// Bulk Smoking/Blasting produces its output purely in code with no event or API to hook.
// String-targeting plus remap = false keeps the project from ever compiling against Create, and
// CreateCompatMixinPlugin drops this mixin entirely when Create isn't loaded.
@Mixin(targets = {
        "com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes$SmokingType",
        "com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes$BlastingType"
}, remap = false)
public class FanProcessingRemainderMixin {

    // require = 0, unlike this project's usual default of 1: silently no-ops instead of crashing if
    // a future Create build renames or changes process. @Local(argsOnly = true) below pulls
    // process's own parameters, not fields on this mixin class.
    @ModifyReturnValue(method = "process", at = @At("RETURN"), require = 0)
    private List<ItemStack> createfood$addRemainder(List<ItemStack> original,
                                                    @Local(argsOnly = true) ItemStack stack,
                                                    @Local(argsOnly = true) Level level) {
        if (original == null || level.isClientSide()) return original;
        Item remainder = CraftingRemainder.getRemainderFor(stack.getItem());
        if (remainder == null) return original;
        List<ItemStack> result = new ArrayList<>(original);
        result.add(new ItemStack(remainder, stack.getCount()));
        return result;
    }
}
