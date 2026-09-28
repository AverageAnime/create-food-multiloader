package dev.averageanime.config;

import dev.averageanime.block.BlockFactory;
import dev.averageanime.registry.DisplayRegistry;
import dev.averageanime.registry.DisplayRegistry.DisplayType;

import java.util.Set;
import java.util.TreeSet;

/** Every registry name an addon owns rather than the base mod, including names derived from a declaration. */
public final class AddonManifest {

    private AddonManifest() {}

    /** Addon-owned registry names, without a namespace. */
    public static Set<String> names() {
        Set<String> names = new TreeSet<>();

        for (String entry : AddonRegistry.items()) {
            names.add(field(entry, 0));
        }
        for (String entry : AddonRegistry.blocks()) {
            String id = field(entry, 0);
            names.add(id);
            addCandleVariants(names, id, field(entry, 1));
        }
        for (String entry : AddonRegistry.fluids()) {
            addFluidNames(names, field(entry, 0));
        }
        for (String entry : AddonRegistry.displayBlocks()) {
            String item = field(entry, 0);
            String path = item.contains(":") ? item.substring(item.indexOf(':') + 1) : item;
            DisplayType type = displayType(field(entry, 1));
            if (type != null) names.add(DisplayRegistry.getBlockName(path, type));
        }

        names.removeIf(name -> name == null || name.isBlank());
        return names;
    }

    /** A cake registers a candle variant per candle colour. */
    private static void addCandleVariants(Set<String> names, String id, String type) {
        if (!type.equalsIgnoreCase("cake")) return;
        for (String suffix : BlockFactory.CANDLE_SUFFIXES) names.add(id + "_" + suffix);
    }

    private static void addFluidNames(Set<String> names, String id) {
        names.add(id);
        names.add("flowing_" + id);
        names.add(id + "_block");
        names.add(id + "_bucket");
    }

    /** Mirrors the config string to {@link DisplayType} mapping both loaders apply. */
    private static DisplayType displayType(String name) {
        return switch (name.toLowerCase()) {
            case "plate" -> DisplayType.PLATE;
            case "small_plate" -> DisplayType.SMALL_PLATE;
            case "bottle" -> DisplayType.BOTTLE;
            case "bowl" -> DisplayType.BOWL_FOOD;
            case "display_bowl" -> DisplayType.BOWL;
            case "salad_bowl", "large_bowl", "small_bowl" -> DisplayType.LARGE_BOWL;
            case "plate_food" -> DisplayType.PLATE_FOOD;
            default -> null;
        };
    }

    private static String field(String entry, int index) {
        String[] parts = entry.split("\\|");
        return index < parts.length ? parts[index] : "";
    }
}
