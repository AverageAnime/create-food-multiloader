package dev.averageanime.config;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.createfood.lib.config.AddonSpecLoader;
import dev.averageanime.platform.Services;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Decides which addons contribute config defaults. Reads {@code [addons] addons_enabled} straight out of
 * the common toml, because registration needs the answer during mod construction, before the loader has
 * loaded any config.
 *
 * <p>The list holds the enabled addons and nothing else: an id on it is on, an id missing from it is off.
 * Disabling an addon is deleting its line.
 */
public final class AddonGate {

    public static final String KEY = "addons.addons_enabled";

    private static volatile Set<String> enabled;

    private AddonGate() {}

    public static List<String> defaults() {
        List<String> ids = new ArrayList<>();
        for (AddonSpecLoader.Addon addon : AddonRegistry.addons()) {
            if (addon.defaultEnabled()) ids.add(addon.addonId());
        }
        return List.copyOf(ids);
    }

    public static boolean isEnabled(String addonId) {
        Set<String> local = enabled;
        if (local == null) local = resolve();
        return local.contains(addonId);
    }

    private static synchronized Set<String> resolve() {
        if (enabled != null) return enabled;

        Set<String> installed = new LinkedHashSet<>();
        for (AddonSpecLoader.Addon addon : AddonRegistry.addons()) installed.add(addon.addonId());

        Set<String> resolved = new LinkedHashSet<>();
        switch (source()) {
            // Generated resources must not depend on one machine's config, so datagen sees every addon.
            case DATAGEN -> resolved.addAll(installed);
            // A bare-JVM tool has no config to read; what it should see is a stock install.
            case NO_PLATFORM -> resolved.addAll(defaults());
            // An absent key means the option has never been written, so the addons' own defaults stand in.
            case CONFIG -> {
                for (String id : ConfigBootstrap.readRaw(KEY, defaults())) {
                    String addonId = id.trim();
                    if (addonId.isEmpty()) continue;
                    if (!installed.contains(addonId)) {
                        CreateFoodCommon.LOGGER.info(
                                "Create: Food - [addons] names {}, which is not installed; ignoring", addonId);
                        continue;
                    }
                    resolved.add(addonId);
                }
            }
        }

        // Logged on both sides: the option is common, but it gates server lists too, so a client and a
        // server disagreeing is worth being able to see in two logs rather than inferring. The disabled
        // half doubles as the list of ids a player can add to turn something on.
        List<String> off = new ArrayList<>(installed);
        off.removeAll(resolved);
        CreateFoodCommon.LOGGER.info("Addons - enabled: {}; disabled: {}",
                resolved.isEmpty() ? "(none)" : resolved, off.isEmpty() ? "(none)" : off);

        enabled = Set.copyOf(resolved);
        return enabled;
    }

    private enum Source { CONFIG, DATAGEN, NO_PLATFORM }

    private static Source source() {
        try {
            return Services.PLATFORM.isRunningDataGen() ? Source.DATAGEN : Source.CONFIG;
        } catch (Throwable t) {
            CreateFoodCommon.LOGGER.debug("No platform service; using each addon's declared default");
            return Source.NO_PLATFORM;
        }
    }
}
