package dev.averageanime.item.effect;

import dev.averageanime.item.type.EffectFood;
import dev.averageanime.registry.type.EffectEntry;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class EffectSpecs {
    private EffectSpecs() {}

    public static Set<String> existingIds(List<EffectEntry> specs) {
        return specs.stream()
                .filter(s -> !s.isFoodEffect() || s.foodEffect().hasAnyLoadedCandidate())
                .map(EffectEntry::categoryOrEffectId)
                .collect(Collectors.toUnmodifiableSet());
    }

    public static List<EffectFood.DeferredFx> deferredFx(List<EffectEntry> specs) {
        return specs.stream()
                .filter(s -> s.isFoodEffect() && s.foodEffect().hasAnyLoadedCandidate())
                .map(s -> new EffectFood.DeferredFx(s.categoryOrEffectId(), s.foodEffect()::get, s.duration, s.amplifier, s.chance))
                .toList();
    }
}
