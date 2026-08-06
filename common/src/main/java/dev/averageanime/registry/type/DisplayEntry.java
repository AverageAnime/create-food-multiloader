package dev.averageanime.registry.type;

import dev.averageanime.registry.DisplayRegistry.DisplayType;
import net.minecraft.core.particles.ParticleOptions;

import java.util.function.Supplier;

public record DisplayEntry(DisplayType type, int maxStack, double height,
                           boolean hasParticles, Supplier<ParticleOptions> particleType) {

    public static Builder of(DisplayType type) {
        return new Builder(type);
    }

    public static final class Builder {
        private final DisplayType type;
        private int maxStack = 1;
        private double height = 12;
        private Supplier<ParticleOptions> particleType = null;

        private Builder(DisplayType type) { this.type = type; }

        public Builder maxStack(int v) { this.maxStack = v; return this; }
        public Builder height(double v) { this.height = v; return this; }

        public Builder particles(Supplier<ParticleOptions> supplier) {
            this.particleType = supplier;
            return this;
        }

        public DisplayEntry build() {
            return new DisplayEntry(type, maxStack, height, particleType != null, particleType);
        }
    }
}