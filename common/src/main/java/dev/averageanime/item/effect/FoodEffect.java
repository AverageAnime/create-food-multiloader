package dev.averageanime.item.effect;

import dev.averageanime.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        for (Candidate c : candidates) {
            if (!Services.PLATFORM.isModLoaded(c.modId())) continue;
            Optional<Holder.Reference<MobEffect>> holder =
                    BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(c.effectId()));
            if (holder.isPresent()) {
                this.resolved = holder.get();
                break;
            }
        }
        // Marked whether or not a candidate matched. Setting this only on
        // success meant a category with no loaded provider re-walked every
        // candidate and hit the registry on every tooltip render and every
        // bite, forever.
        resolveAttempted = true;
    }

    public Optional<Holder<MobEffect>> get() {
        if (!resolveAttempted) {
            resolve();
        }
        return Optional.ofNullable(resolved);
    }

    public void invalidate() {
        resolved = null;
        resolveAttempted = false;
    }

    public boolean hasAnyLoadedCandidate() {
        for (Candidate c : candidates) {
            if (Services.PLATFORM.isModLoaded(c.modId())) return true;
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
        private final List<Candidate> candidates = new ArrayList<>();

        private Builder(String name) { this.name = name; }

        public Builder or(String modId, String effectId) {
            candidates.add(new Candidate(modId, effectId));
            return this;
        }

        /** Append another category's candidates, keeping their order. */
        public Builder orAll(FoodEffect other) {
            if (other != null) {
                candidates.addAll(Arrays.asList(other.candidates));
            }
            return this;
        }

        public FoodEffect build() {
            return new FoodEffect(name, candidates.toArray(new Candidate[0]));
        }
    }
}
