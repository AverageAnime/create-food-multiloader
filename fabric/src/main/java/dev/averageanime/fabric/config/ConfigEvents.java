package dev.averageanime.fabric.config;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.config.ConfigLifecycle;
import io.github.fabricators_of_create.porting_lib.config.ModConfig;
import io.github.fabricators_of_create.porting_lib.config.ModConfigEvent;

/**
 * Routes Porting Lib's config lifecycle events to {@link ConfigLifecycle}.
 * The EVENT fields are global across all mods, so each callback filters for
 * this mod's SERVER config. Loading fires at server start, Reloading on file
 * edits and on remote clients when the synced config arrives.
 */
public final class ConfigEvents {

    private ConfigEvents() {}

    public static void register() {
        ModConfigEvent.Loading.EVENT.register(event -> onLoadedOrReloaded(event.getConfig()));
        ModConfigEvent.Reloading.EVENT.register(event -> onLoadedOrReloaded(event.getConfig()));
        ModConfigEvent.Unloading.EVENT.register(event -> {
            if (isOurServerConfig(event.getConfig())) ConfigLifecycle.onServerConfigUnloaded();
        });
    }

    private static void onLoadedOrReloaded(ModConfig config) {
        if (isOurServerConfig(config)) ConfigLifecycle.onServerConfigLoaded();
    }

    private static boolean isOurServerConfig(ModConfig config) {
        return CreateFoodCommon.MOD_ID.equals(config.getModId()) && config.getType() == ModConfig.Type.SERVER;
    }
}
