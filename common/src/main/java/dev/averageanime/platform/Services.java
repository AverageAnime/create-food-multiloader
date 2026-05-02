package dev.averageanime.platform;

import dev.averageanime.CommonClass;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatform PLATFORM = load(IPlatform.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        CommonClass.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}