package dev.averageanime.config;

public record ItemEffectOverride(String categoryOrEffectId, int duration, int amplifier, boolean remove, float chance) {}