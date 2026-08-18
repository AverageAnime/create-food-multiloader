package dev.averageanime.mixin;

import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.item.effect.EffectContext;
import dev.averageanime.item.type.EffectFood;
import dev.averageanime.platform.Services;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityAddEffectMixin {

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"), cancellable = true)
    private void createfood$cancelRemovedEffect(MobEffectInstance effectInstance, @Nullable Entity source,
                                                CallbackInfoReturnable<Boolean> cir) {
        ItemEffectOverride override = findOverride(effectInstance);
        if (override != null && override.remove()) {
            cir.setReturnValue(false);
        }
    }

    @ModifyVariable(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"), argsOnly = true)
    private MobEffectInstance createfood$replaceEffect(MobEffectInstance effectInstance) {
        ItemEffectOverride override = findOverride(effectInstance);
        if (override != null && !override.remove()) {
            effectInstance = new MobEffectInstance(effectInstance.getEffect(), override.duration(), override.amplifier(),
                    effectInstance.isAmbient(), effectInstance.isVisible(), effectInstance.showIcon());
        }
        return createfood$stackDuration(effectInstance);
    }

    /**
     * Vanilla's {@code MobEffectInstance#update} keeps whichever instance is longer and discards the
     * other, so eating a second food throws away the remaining time of the first. While a Create: Food
     * item is being consumed, sum the two durations instead (same amplifier only) and hand vanilla the
     * longer instance, which it then accepts through its normal path.
     */
    private MobEffectInstance createfood$stackDuration(MobEffectInstance incoming) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!EffectContext.isConsuming(self)) return incoming;
        if (!Services.PLATFORM.isEffectDurationStacking()) return incoming;

        MobEffectInstance existing = self.getEffect(incoming.getEffect());
        if (existing == null) return incoming;
        if (existing.getAmplifier() != incoming.getAmplifier()) return incoming;
        if (existing.isInfiniteDuration() || incoming.isInfiniteDuration()) return incoming;

        int cap = Services.PLATFORM.getMaxStackedEffectDuration();
        long summed = (long) existing.getDuration() + incoming.getDuration();
        int merged = (int) Math.min(summed, cap > 0 ? cap : Integer.MAX_VALUE);
        if (merged <= existing.getDuration()) return incoming;

        return new MobEffectInstance(incoming.getEffect(), merged, incoming.getAmplifier(),
                incoming.isAmbient(), incoming.isVisible(), incoming.showIcon());
    }

    @Nullable
    private ItemEffectOverride findOverride(MobEffectInstance effectInstance) {
        LivingEntity self = (LivingEntity) (Object) this;
        return EffectFood.findOverrideForEffect(EffectContext.active(self), effectInstance);
    }
}
