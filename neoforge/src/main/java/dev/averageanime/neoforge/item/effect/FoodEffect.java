package dev.averageanime.neoforge.item.effect;

import dev.averageanime.neoforge.config.ModConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.fml.ModList;

import java.util.Optional;

/**
 * Represents a logical effect category (e.g. "comfort", "nourishment") that
 * can be fulfilled by any number of mods in priority order. The first candidate
 * whose mod is loaded and whose effect exists in the registry wins.
 *
 * Resolution order:
 *   1. Per-category config override (createfood-server.toml → effects.category_overrides)
 *   2. Candidate list, top-to-bottom (first loaded mod wins)
 *
 * Add new candidates via builder. Order matters — highest priority first.
 */
public final class FoodEffect {

    private final String categoryName;
    private final Candidate[] candidates;
    private Holder<MobEffect> resolved = null;
    private boolean resolveAttempted = false;

    private FoodEffect(String categoryName, Candidate[] candidates) {
        this.categoryName = categoryName;
        this.candidates = candidates;
    }

    public void resolve() {
        resolveAttempted = true;

        String configOverride = ModConfig.getCategoryEffectOverride(categoryName);
        if (configOverride != null) {
            Optional<Holder.Reference<MobEffect>> holder =
                    BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(configOverride));
            if (holder.isPresent()) {
                this.resolved = holder.get();
                return;
            }
        }

        for (Candidate c : candidates) {
            if (!ModList.get().isLoaded(c.modId)) continue;
            Optional<Holder.Reference<MobEffect>> holder =
                    BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(c.effectId));
            if (holder.isPresent()) {
                this.resolved = holder.get();
                return;
            }
        }
    }

    public Optional<Holder<MobEffect>> get() {
        if (!resolveAttempted) {
            resolve();
        }
        return Optional.ofNullable(resolved);
    }

    /**
     * Clears the cached resolution so the next call to {@link #get()} re-evaluates
     * the config overrides and candidate list. Call this if config is reloaded at
     * runtime (not normally needed — effects are resolved lazily at item-use time).
     */
    public void invalidate() {
        resolved = null;
        resolveAttempted = false;
    }

    public boolean hasAnyLoadedCandidate() {
        for (Candidate c : candidates) {
            if (ModList.get().isLoaded(c.modId())) return true;
        }
        return false;
    }

    public String getCategoryName() {
        return categoryName;
    }

    private record Candidate(String modId, String effectId) {}

    public static Builder category(String name) {
        return new Builder(name);
    }

    public static final class Builder {
        private final String name;
        private final java.util.List<Candidate> candidates = new java.util.ArrayList<>();

        private Builder(String name) { this.name = name; }

        public Builder or(String modId, String effectId) {
            candidates.add(new Candidate(modId, effectId));
            return this;
        }

        public FoodEffect build() {
            return new FoodEffect(name, candidates.toArray(new Candidate[0]));
        }
    }
}