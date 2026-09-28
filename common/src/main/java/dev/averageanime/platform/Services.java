package dev.averageanime.platform;

import dev.averageanime.CreateFoodCommon;

public class Services {

    public static final Platform PLATFORM = load(Platform.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = dev.averageanime.createfood.lib.platform.Services.load(clazz);
        CreateFoodCommon.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
