package dev.averageanime.neoforge.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.neoforge.compat.farmersdelight.CookingPotContents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

// Another exception to the no-mixins-into-other-mods policy: a heated cooking pot's bubbles come from
// FD's static animationTick with no event to hook, and once the pot shows its contents, bubbles over an
// empty or solids-only pot look wrong. FarmersDelightCompatMixinPlugin drops this when FD is absent.
@Mixin(value = CookingPotBlockEntity.class, remap = false)
public class CookingPotBubblesMixin {

    // animationTick makes two addParticle calls, BUBBLE_POP and FD's steam; this wraps both and only
    // ever suppresses the bubbles, so a heated pot still steams. require = 0: this is cosmetic, so a
    // future FD that moves the particles should lose the gating rather than fail to launch.
    @WrapWithCondition(method = "animationTick", require = 0, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private static boolean createfood$bubbleOnlyWithFluid(Level level, ParticleOptions particle,
                                                          double x, double y, double z,
                                                          double dx, double dy, double dz,
                                                          @Local(argsOnly = true) CookingPotBlockEntity pot) {
        // With the display off there is nothing for the bubbles to agree with, so FD's are kept.
        if (particle != ParticleTypes.BUBBLE_POP || !ConfigValues.isCookingPotDisplayEnabled()) return true;
        return CookingPotContents.hasFluid(pot);
    }
}
