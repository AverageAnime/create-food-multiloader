package net.averageanime.createfood.item.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class FoodEffect {

    private final String categoryName;
    private final Candidate[] candidates;
    private MobEffect resolved = null;
    private boolean resolveAttempted = false;

    private FoodEffect(String categoryName, Candidate[] candidates) {
        this.categoryName = categoryName;
        this.candidates = candidates;
    }

    public void resolve() {
        for (Candidate c : candidates) {
            if (!ModList.get().isLoaded(c.modId())) continue;
            ResourceLocation loc = ResourceLocation.tryParse(c.effectId());
            if (loc == null) continue;
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(loc);
            if (effect != null) {
                this.resolved = effect;
                this.resolveAttempted = true;
                return;
            }
        }
    }

    public Optional<MobEffect> get() {
        if (!resolveAttempted) {
            resolve();
        }
        return Optional.ofNullable(resolved);
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
        private final List<Candidate> candidates = new ArrayList<>();

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
