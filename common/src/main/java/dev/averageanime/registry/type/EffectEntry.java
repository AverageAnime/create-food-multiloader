package dev.averageanime.registry.type;

import dev.averageanime.item.effect.FoodEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.Nullable;

public final class EffectEntry {

    private final @Nullable FoodEffect foodEffect;
    private final @Nullable Holder<MobEffect> rawEffect;
    public final int duration;
    public final int amplifier;
    public final float chance;

    private EffectEntry(@Nullable FoodEffect foodEffect, @Nullable Holder<MobEffect> rawEffect,
                        int duration, int amplifier, float chance) {
        this.foodEffect = foodEffect;
        this.rawEffect  = rawEffect;
        this.duration   = duration;
        this.amplifier  = amplifier;
        this.chance     = chance;
    }

    public boolean isFoodEffect() { return foodEffect != null; }

    public @Nullable FoodEffect foodEffect() { return foodEffect; }

    public @Nullable Holder<MobEffect> rawEffect() { return rawEffect; }

    public String categoryOrEffectId() {
        if (foodEffect != null) return foodEffect.getCategoryName();
        if (rawEffect  != null) return rawEffect.unwrapKey()
                .map(k -> k.location().toString()).orElse("unknown");
        throw new IllegalStateException("EffectSpec has neither FoodEffect nor rawEffect");
    }

    public static EffectEntry of(FoodEffect e, int dur) {
        return new EffectEntry(e, null, dur, 0, 1.0f);
    }

    public static EffectEntry of(FoodEffect e, int dur, int amp) {
        return new EffectEntry(e, null, dur, amp, 1.0f);
    }

    public static EffectEntry of(FoodEffect e, int dur, int amp, float chance) {
        return new EffectEntry(e, null, dur, amp, chance);
    }

    public static EffectEntry ofRaw(Holder<MobEffect> e, int dur) {
        return new EffectEntry(null, e, dur, 0, 1.0f);
    }

    public static EffectEntry ofRaw(Holder<MobEffect> e, int dur, int amp) {
        return new EffectEntry(null, e, dur, amp, 1.0f);
    }

    public static EffectEntry ofRaw(Holder<MobEffect> e, int dur, int amp, float chance) {
        return new EffectEntry(null, e, dur, amp, chance);
    }
}