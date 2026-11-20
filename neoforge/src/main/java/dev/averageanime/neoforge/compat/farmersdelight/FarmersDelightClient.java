package dev.averageanime.neoforge.compat.farmersdelight;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

/** Only call when Farmer's Delight is loaded: this is the class that first touches its registry. */
public final class FarmersDelightClient {

    private FarmersDelightClient() {}

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.COOKING_POT.get(), CookingPotRenderer::new);
    }
}
