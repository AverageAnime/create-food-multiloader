package dev.averageanime.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.platform.Services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConfigBootstrap {

    public static final String ITEMS         = "items.item";
    public static final String BLOCKS        = "blocks.block";
    public static final String DISPLAY_BLOCK = "blocks.display_block";
    public static final String FLUIDS        = "blocks.fluid";

    private static final Map<String, List<String>> CACHE = new ConcurrentHashMap<>();
    private static volatile boolean loaded;

    private ConfigBootstrap() {}

    public static List<String> read(String key, List<String> defaults) {
        if (Services.PLATFORM.isRunningDataGen()) return defaults;
        load();
        List<String> configured = CACHE.get(key);
        return configured != null ? configured : defaults;
    }

    private static void load() {
        if (loaded) return;
        synchronized (ConfigBootstrap.class) {
            if (loaded) return;
            loaded = true;
            Path file = Services.PLATFORM.getConfigDir().resolve("createfood-common.toml");
            if (!Files.exists(file)) {
                CreateFoodCommon.LOGGER.info(
                        "Create: Food - createfood-common.toml not written yet; using built-in defaults for this launch");
                return;
            }
            try (FileConfig raw = FileConfig.of(file.toFile())) {
                raw.load();
                for (String key : List.of(ITEMS, BLOCKS, DISPLAY_BLOCK, FLUIDS)) {
                    Object value = raw.get(key);
                    if (value instanceof List<?> list) {
                        CACHE.put(key, list.stream().map(String::valueOf).toList());
                    }
                }
            } catch (Exception e) {
                CreateFoodCommon.LOGGER.warn("Create: Food - Failed to read createfood-common.toml; using built-in defaults", e);
            }
        }
    }
}
