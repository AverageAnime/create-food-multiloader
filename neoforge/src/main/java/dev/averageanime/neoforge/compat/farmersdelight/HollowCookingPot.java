package dev.averageanime.neoforge.compat.farmersdelight;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

/**
 * Whether the hollow cooking pot from the {@code farmersdelight_overrides} pack is in use. The pot's
 * block model cannot follow its contents (FD gives it no blockstate property for that), so it is always
 * hollow and {@link CookingPotRenderer} either draws the contents inside it or, with the display off,
 * closes it with FD's own top face.
 * With the pack switched off the block is FD's solid pot again, and drawing that lid would z-fight it.
 *
 * <p>Detected by a file only the pack ships, rather than by the pack's selection id, which NeoForge
 * derives internally and could change.
 */
public final class HollowCookingPot {

    private static final ResourceLocation MARKER =
            ResourceLocation.fromNamespaceAndPath("farmersdelight", "models/block/cooking_pot_solid.json");

    private static volatile boolean active;

    public static final ResourceManagerReloadListener RELOAD_LISTENER =
            resources -> active = resources.getResource(MARKER).isPresent();

    private HollowCookingPot() {}

    public static boolean isActive() {
        return active;
    }
}
