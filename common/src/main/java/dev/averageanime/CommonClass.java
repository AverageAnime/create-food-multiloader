package dev.averageanime;

import dev.averageanime.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonClass {
    public static final String MOD_ID = "createfood";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void run() {
        LOGGER.info("Platform - {}", Services.PLATFORM.getClass().getSimpleName());
    }
}
