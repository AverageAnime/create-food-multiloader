package dev.averageanime.tab;

import dev.averageanime.config.ConfigValues;
import dev.averageanime.createfood.lib.tab.TabFilter;

import java.util.Set;

public final class TabFilters {

    /** Placement mechanics: registered, but never their own entry in a tab. */
    private static final String[] CONTAINERS = {
            "plate_block", "small_plate_block", "bowl_block", "large_bowl_block", "bottle_block"
    };

    private static final TabFilter MAIN = TabFilter.builder()
            .exclude(CONTAINERS)
            .excludeSuffix("_bucket", "pumpkin_pie_block")
            .enabledWhen(ConfigValues::isItemEnabled)
            .build();

    private static final TabFilter FLUID = TabFilter.builder()
            .requireSuffix("_bucket")
            .enabledWhen(ConfigValues::isFluidBucketEnabled)
            .build();

    private static final TabFilter DISPLAY = TabFilter.builder()
            .exclude(CONTAINERS)
            .exclude("generic_display_plate_block", "generic_display_bowl_block")
            .enabledWhen(ConfigValues::isDisplayBlockEnabled)
            .build();

    private TabFilters() {}

    /** @param displayBlockPaths blocks that have their own tab, so they do not appear twice */
    public static boolean isMainTabItem(String path, Set<String> displayBlockPaths) {
        return MAIN.accepts(path, displayBlockPaths);
    }

    public static boolean isFluidTabItem(String path) {
        return FLUID.accepts(path);
    }

    public static boolean isDisplayTabItem(String path) {
        return DISPLAY.accepts(path);
    }
}
