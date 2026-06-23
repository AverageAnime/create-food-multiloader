package dev.averageanime;

import dev.averageanime.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class CommonClass {
    public static final String MOD_ID = "createfood";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Set<String> TRANSLUCENT_FLUIDS = Set.of(
            "apple_juice", "berry_juice", "black_gelatin_mix", "blue_gelatin_mix",
            "brown_gelatin_mix", "chorus_fruit_juice", "cyan_gelatin_mix", "gelatin_mix",
            "glow_berry_juice", "gray_gelatin_mix", "green_gelatin_mix",
            "light_blue_gelatin_mix", "light_gray_gelatin_mix", "lime_gelatin_mix",
            "magenta_gelatin_mix", "orange_gelatin_mix", "pink_gelatin_mix",
            "purple_gelatin_mix", "red_gelatin_mix", "squid_ink",
            "vegetable_oil", "vinegar", "yellow_gelatin_mix"
    );

    public static void run() {
        LOGGER.info("Platform - {}", Services.PLATFORM.getClass().getSimpleName());
    }
}