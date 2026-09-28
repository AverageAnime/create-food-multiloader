package dev.averageanime.fabric.mixin;

import dev.averageanime.item.remainder.CraftingRemainder;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Fabric parity for NeoForge's ProjectileImpactEvent handler; HEAD placement runs before the egg discards itself. */
@Mixin(ThrownEgg.class)
public class ThrownEggMixin {

    @Inject(method = "onHit", at = @At("HEAD"))
    private void createfood$onEggImpact(HitResult result, CallbackInfo ci) {
        CraftingRemainder.handleEggImpact((ThrownEgg) (Object) this);
    }
}
