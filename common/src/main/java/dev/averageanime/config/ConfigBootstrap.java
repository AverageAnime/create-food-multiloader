package dev.averageanime.config;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.platform.Services;
import dev.averageanime.createfood.lib.config.RegistryCondition;
import dev.averageanime.createfood.lib.config.TomlListReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** The registration lists, read from the toml during mod construction. Keep keys in sync with {@link ConfigSchema#build}. */
public final class ConfigBootstrap {

    public static final String ITEMS         = "items.item";
    public static final String BLOCKS        = "blocks.block";
    public static final String DISPLAY_BLOCK = "blocks.display_block";
    public static final String FLUIDS        = "blocks.fluid";

    // AddonGate.KEY is read from the same file, and the reader caches it on first load, so it has to be
    // named here or it reads as absent for the rest of the launch.
    private static final List<String> KEYS = List.of(ITEMS, BLOCKS, DISPLAY_BLOCK, FLUIDS, AddonGate.KEY);

    private static volatile TomlListReader reader;
    private static volatile boolean reported;

    private ConfigBootstrap() {}

    /** Ignored during datagen. */
    public static List<String> read(String key, List<String> defaults) {
        if (Services.PLATFORM.isRunningDataGen()) return resolve(defaults);
        List<String> configured = RegistryCondition.inherit(
                reader().read(key, defaults), defaults, ConfigBootstrap::name, CreateFoodCommon.LOGGER);
        reportDriftOnce();
        return resolve(configured);
    }

    /** A stored list with no condition handling and no drift report; {@code @} clauses are meaningless here. */
    public static List<String> readRaw(String key, List<String> defaults) {
        return reader().read(key, defaults);
    }

    /** Drops entries a {@code @} clause rules out and strips the clause from the rest. */
    private static List<String> resolve(List<String> entries) {
        List<String> kept = new ArrayList<>(entries.size());
        for (String entry : entries) {
            RegistryCondition.Split split = RegistryCondition.split(entry, CreateFoodCommon.LOGGER);
            if (split == null) continue; // Malformed clause, already logged.
            if (!split.condition().isSatisfied(Services.PLATFORM)) {
                CreateFoodCommon.LOGGER.info("Create: Food - not registering {}: {}",
                        name(split.payload()), split.condition().reason(Services.PLATFORM));
                continue;
            }
            kept.add(split.payload());
        }
        return List.copyOf(kept);
    }

    private static TomlListReader reader() {
        TomlListReader local = reader;
        if (local != null) return local;
        synchronized (ConfigBootstrap.class) {
            if (reader == null) {
                reader = new TomlListReader(
                        Services.PLATFORM.getConfigDir().resolve("createfood-common.toml"),
                        KEYS, CreateFoodCommon.LOGGER);
            }
            return reader;
        }
    }

    private static void reportDriftOnce() {
        if (reported) return;
        synchronized (ConfigBootstrap.class) {
            if (reported) return;
            reported = true;
            reportDrift();
        }
    }

    private static void reportDrift() {
        report(ITEMS, ConfigDefaults.CUSTOM_ITEM_DEFAULT, AddonRegistry.items(), "items.item");
        report(BLOCKS, ConfigDefaults.CUSTOM_BLOCK_DEFAULT, AddonRegistry.blocks(), "blocks.block");
        report(FLUIDS, ConfigDefaults.CUSTOM_FLUID_DEFAULT, AddonRegistry.fluids(), "blocks.fluid");
        report(DISPLAY_BLOCK, ConfigDefaults.CUSTOM_DISPLAY_BLOCK_DEFAULT, AddonRegistry.displayBlocks(),
                "blocks.display_block");
    }

    private static void report(String key, List<String> builtIn, List<String> addon, String label) {
        List<String> stored = reader().stored().get(key);
        if (stored == null) return;

        Set<String> storedNames = names(stored);
        List<String> missing = addon.stream().map(ConfigBootstrap::name)
                .filter(n -> !storedNames.contains(n)).toList();
        if (!missing.isEmpty()) {
            CreateFoodCommon.LOGGER.info(
                    "Create: Food - {} addon entries are not in your [{}] yet: {}. "
                            + "Add them, or reset that list to its default to pick them up.",
                    missing.size(), label, missing);
        }

        Set<String> known = names(builtIn);
        known.addAll(names(addon));
        List<String> orphaned = stored.stream().map(ConfigBootstrap::name)
                .filter(n -> !known.contains(n)).toList();
        if (!orphaned.isEmpty()) {
            CreateFoodCommon.LOGGER.info(
                    "Create: Food - {} entries in your [{}] match no built-in default and no installed "
                            + "addon: {}. Harmless if you added them yourself; otherwise their addon is gone.",
                    orphaned.size(), label, orphaned);
        }
    }

    private static Set<String> names(List<String> entries) {
        return entries.stream().map(ConfigBootstrap::name).collect(Collectors.toCollection(HashSet::new));
    }

    private static String name(String entry) {
        int at = entry.indexOf('@');
        if (at >= 0) entry = entry.substring(0, at);
        int bar = entry.indexOf('|');
        return bar < 0 ? entry : entry.substring(0, bar);
    }
}
