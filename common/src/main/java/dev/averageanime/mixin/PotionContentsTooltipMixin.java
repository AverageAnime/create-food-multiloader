package dev.averageanime.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.item.effect.TooltipContext;
import dev.averageanime.item.type.EffectFood;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(PotionContents.class)
public abstract class PotionContentsTooltipMixin {

    @WrapMethod(method = "addPotionTooltip(Ljava/lang/Iterable;Ljava/util/function/Consumer;FF)V")
    private static void createfood$filterOverriddenEffects(Iterable<MobEffectInstance> effects,
                                                           Consumer<Component> tooltipAdder,
                                                           float durationFactor, float tickRate,
                                                           Operation<Void> original) {
        List<ItemEffectOverride> overrides = TooltipContext.active();
        if (overrides.isEmpty()) {
            original.call(effects, tooltipAdder, durationFactor, tickRate);
            return;
        }
        List<MobEffectInstance> filtered = new ArrayList<>();
        boolean hadAny = false;
        for (MobEffectInstance instance : effects) {
            hadAny = true;
            ItemEffectOverride override = EffectFood.findOverrideForEffect(overrides, instance);
            if (override == null) {
                filtered.add(instance);
            } else if (!override.remove()) {
                filtered.add(new MobEffectInstance(instance.getEffect(),
                        override.duration(), override.amplifier()));
            }
        }
        if (hadAny && filtered.isEmpty()) return;
        original.call(filtered, tooltipAdder, durationFactor, tickRate);
    }
}
