package dev.averageanime.config.type;

public record ItemEffectOverride(String categoryOrEffectId, int duration, int amplifier, boolean remove, float chance) {}