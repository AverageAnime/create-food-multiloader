package dev.averageanime.mixin;

import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.item.effect.EffectContext;
import dev.averageanime.item.type.EffectFood;
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
            return new MobEffectInstance(effectInstance.getEffect(), override.duration(), override.amplifier());
        }
        return effectInstance;
    }

    @Nullable
    private ItemEffectOverride findOverride(MobEffectInstance effectInstance) {
        LivingEntity self = (LivingEntity) (Object) this;
        return EffectFood.findOverrideForEffect(EffectContext.active(self), effectInstance);
    }
}
