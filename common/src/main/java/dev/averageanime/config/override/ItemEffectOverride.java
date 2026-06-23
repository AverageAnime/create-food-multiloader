package dev.averageanime.config.override;

public record ItemEffectOverride(String categoryOrEffectId, int duration, int amplifier, boolean remove, float chance) {}