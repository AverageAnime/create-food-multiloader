package dev.averageanime.fabric.config;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.config.ConfigLifecycle;
import io.github.fabricators_of_create.porting_lib.config.ModConfig;
import io.github.fabricators_of_create.porting_lib.config.ModConfigEvent;

/** The EVENT fields are global, so each callback filters for this mod. */
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
