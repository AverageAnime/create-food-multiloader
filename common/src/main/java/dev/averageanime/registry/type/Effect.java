package dev.averageanime.registry.type;

import dev.averageanime.item.effect.FoodEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.Nullable;

/**
 * Platform-neutral description of a single food effect.
 * Either a mod-category {@link FoodEffect} or a direct vanilla/registry {@link Holder}.
 */
public final class Effect {

    private final @Nullable FoodEffect foodEffect;
    private final @Nullable Holder<MobEffect> rawEffect;
    public final int duration;
    public final int amplifier;

    private Effect(@Nullable FoodEffect foodEffect, @Nullable Holder<MobEffect> rawEffect,
                   int duration, int amplifier) {
        this.foodEffect = foodEffect;
        this.rawEffect  = rawEffect;
        this.duration   = duration;
        this.amplifier  = amplifier;
    }

    /** True when this spec wraps a mod-category {@link FoodEffect}. */
    public boolean isFoodEffect() { return foodEffect != null; }

    /** The mod-category effect, or {@code null} for raw vanilla effects. */
    public @Nullable FoodEffect foodEffect() { return foodEffect; }

    /** The raw vanilla effect holder, or {@code null} for mod-category effects. */
    public @Nullable Holder<MobEffect> rawEffect() { return rawEffect; }

    /**
     * The string key used by {@code EffectFood} to identify this effect:
     * either the FoodEffect category name or the MobEffect registry key.
     */
    public String categoryOrEffectId() {
        if (foodEffect != null) return foodEffect.getCategoryName();
        if (rawEffect  != null) return rawEffect.unwrapKey()
                .map(k -> k.location().toString()).orElse("unknown");
        throw new IllegalStateException("EffectSpec has neither FoodEffect nor rawEffect");
    }

    // ── Factory methods ──────────────────────────────────────────────────────

    public static Effect of(FoodEffect e, int dur) {
        return new Effect(e, null, dur, 0);
    }

    public static Effect of(FoodEffect e, int dur, int amp) {
        return new Effect(e, null, dur, amp);
    }

    public static Effect ofRaw(Holder<MobEffect> e, int dur) {
        return new Effect(null, e, dur, 0);
    }

    public static Effect ofRaw(Holder<MobEffect> e, int dur, int amp) {
        return new Effect(null, e, dur, amp);
    }
}
