package net.averageanime.createfood.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Centralizes mob effect references used in food properties.
 * Effects are sourced from compatible mods when loaded.
 * Phase 15 will add fallback behaviour when FD is not present.
 */
public final class ModEffects {

    private ModEffects() {}

    public static final LazyEffect COMFORT    = fd("farmersdelight:comfort");
    public static final LazyEffect NOURISHMENT = fd("farmersdelight:nourishment");

    private static LazyEffect fd(String id) {
        return new LazyEffect("farmersdelight", id);
    }

    public static final class LazyEffect {
        private final String modId;
        private final ResourceLocation effectId;
        private MobEffect cached;

        LazyEffect(String modId, String effectId) {
            this.modId = modId;
            this.effectId = new ResourceLocation(effectId);
        }

        public MobEffect get() {
            if (cached == null && ModList.get().isLoaded(modId)) {
                cached = ForgeRegistries.MOB_EFFECTS.getValue(effectId);
            }
            return cached;
        }
    }
}
