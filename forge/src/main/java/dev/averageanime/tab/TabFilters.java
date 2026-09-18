package dev.averageanime.tab;

import dev.averageanime.config.ConfigValues;

import java.util.Set;

public final class TabFilters {

    private TabFilters() {}

    public static boolean isMainTabItem(String path, Set<String> displayBlockPaths) {
        return !path.endsWith("_bucket")
                && !path.endsWith("pumpkin_pie_block")
                && !path.equals("small_plate_block")
                && !path.equals("plate_block")
                && !path.equals("bowl_block")
                && !path.equals("large_bowl_block")
                && !path.equals("bottle_block")
                && !displayBlockPaths.contains(path)
                && ConfigValues.isItemEnabled(path);
    }

    public static boolean isFluidTabItem(String path) {
        return path.endsWith("_bucket") && ConfigValues.isFluidBucketEnabled(path);
    }

    public static boolean isDisplayTabItem(String path) {
        return !path.equals("plate_block")
                && !path.equals("small_plate_block")
                && !path.equals("bowl_block")
                && !path.equals("large_bowl_block")
                && !path.equals("bottle_block")
                && !path.equals("generic_display_plate_block")
                && !path.equals("generic_display_bowl_block")
                && ConfigValues.isDisplayBlockEnabled(path);
    }
}
