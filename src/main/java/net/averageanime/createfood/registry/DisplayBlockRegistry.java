package net.averageanime.createfood.registry;

import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

import java.util.*;
import java.util.function.Supplier;

/**
 * Platform-neutral display-block configuration registry.
 * Holds the shared pattern→config map, exclusion sets, and lookup helpers.
 */
public final class DisplayBlockRegistry {
    private DisplayBlockRegistry() {}

    // ── Enum / records ────────────────────────────────────────────────────────

    public enum DisplayType {
        PLATE, SMALL_PLATE, BOTTLE, BOWL, SALAD_BOWL, PLATE_FOOD
    }

    public record DisplayBlockConfig(DisplayType type, int maxStack, double height,
                                     boolean hasParticles, Supplier<ParticleOptions> particleType) {
        public DisplayBlockConfig(DisplayType type) {
            this(type, 1, 12, false, null);
        }
        public DisplayBlockConfig(DisplayType type, int maxStack) {
            this(type, maxStack, 12, false, null);
        }
        public DisplayBlockConfig(DisplayType type, double height) {
            this(type, 1, height, false, null);
        }
        public DisplayBlockConfig(DisplayType type, double height,
                                  boolean hasParticles, Supplier<ParticleOptions> particleType) {
            this(type, 1, height, hasParticles, particleType);
        }
    }

    public record MultiDisplayConfig(List<DisplayBlockConfig> configs) {
        public MultiDisplayConfig(DisplayBlockConfig... configs) {
            this(Arrays.asList(configs));
        }
    }

    // ── Exclusion lists ───────────────────────────────────────────────────────

    public static final Set<String> EXCLUDED_ITEMS = Set.of(
            "apple_slice",
            "fish_sticks",
            "mozzarella_sticks",
            "cookie_crumbs",
            "chorus_fruit_slice",
            "waffle_cone",
            "meat_pie_filling",
            "dumpling_wrappers",
            "pumpkin_pie_block",
            "pumpkin_pie_slice",
            "graham_cracker_chocolate_marshmallow",
            "graham_cracker_chocolate",
            "chocolate_graham_cracker_chocolate_ice_cream"
    );

    /** Substrings that, if present in an item name, mean no display block should be created. */
    public static final List<String> SKIP_PATTERNS = List.of(
            "raw_", "stick_1", "stick_2", "dough", "chips",
            "chocolate_berries", "chocolate_apple", "bread_slice", "toast_slice",
            "apple_slice", "tropical_fish_slice", "pretzel_stick",
            "taco_shell", "donut_hole", "pie_crust", "sliced", "_candle"
    );

    // ── Pattern → config map ──────────────────────────────────────────────────

    public static final Map<String, Object> DISPLAY_CONFIGS;

    static {
        Map<String, Object> m = new LinkedHashMap<>();

        putMulti(m, "kelp_roll_slice",
                new DisplayBlockConfig(DisplayType.PLATE, 6),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE));
        putMulti(m, "meringue_cookie",
                new DisplayBlockConfig(DisplayType.PLATE, 9),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE));
        put(m, "kelp_roll",                     DisplayType.PLATE,      3);
        put(m, "gelatin_dessert",               DisplayType.PLATE,      6);
        put(m, "slice",                         DisplayType.SMALL_PLATE);
        put(m, "cream_mini_waffle",             DisplayType.PLATE,      1);
        put(m, "honeyed_mini_waffle",           DisplayType.PLATE,      1);
        put(m, "mini_waffle",                   DisplayType.PLATE,      4);
        put(m, "pizza",                         DisplayType.PLATE,      1);
        put(m, "cheese_block",                  DisplayType.PLATE,      1);
        put(m, "gyro",                          DisplayType.PLATE,      1);
        put(m, "waffle",                        DisplayType.PLATE,      1);
        put(m, "cupcake",                       DisplayType.PLATE,      4);
        put(m, "cake",                          DisplayType.PLATE,      1);
        put(m, "mini_cream_pie",                DisplayType.SMALL_PLATE);
        put(m, "mini_smores_pie",               DisplayType.SMALL_PLATE);
        put(m, "mini_cookie_cream_pie",         DisplayType.SMALL_PLATE);
        put(m, "mini_chocolate_pie",            DisplayType.SMALL_PLATE);
        put(m, "pie",                           DisplayType.PLATE,      1);
        put(m, "burger",                        DisplayType.PLATE,      1);
        put(m, "meatball_sandwich",             DisplayType.PLATE,      2);
        put(m, "hash_brown_sandwich",           DisplayType.PLATE,      2);
        put(m, "sandwich",                      DisplayType.PLATE,      1);
        put(m, "toast_plate",                   DisplayType.PLATE_FOOD);
        put(m, "toast_fried_egg_plate",         DisplayType.PLATE_FOOD);
        put(m, "toast",                         DisplayType.PLATE,      1);
        put(m, "calzone",                       DisplayType.PLATE,      2);
        put(m, "smore",                         DisplayType.PLATE,      1);
        put(m, "hot_chocolate_bottle",          DisplayType.BOTTLE, 8, true,  () -> ParticleTypes.WHITE_ASH);
        put(m, "hot_dark_chocolate_bottle",     DisplayType.BOTTLE, 8, true,  () -> ParticleTypes.WHITE_ASH);
        put(m, "hot_white_chocolate_bottle",    DisplayType.BOTTLE, 8, true,  () -> ParticleTypes.WHITE_ASH);
        put(m, "_jam_bottle",                   DisplayType.BOTTLE, 9, false, null);
        put(m, "taco_sauce_bottle",             DisplayType.BOTTLE, 9, false, null);
        put(m, "sugar_cane_juice_bottle",       DisplayType.BOTTLE, 9, false, null);
        put(m, "egg_whites_bottle",             DisplayType.BOTTLE, 9, false, null);
        put(m, "_juice_bottle",                 DisplayType.BOTTLE, 10, false, null);
        put(m, "chocolate_bottle",              DisplayType.BOTTLE, 8, false, null);
        put(m, "dark_chocolate_bottle",         DisplayType.BOTTLE, 8, false, null);
        put(m, "white_chocolate_bottle",        DisplayType.BOTTLE, 8, false, null);
        put(m, "chocolate_milk_bottle",         DisplayType.BOTTLE, 8, false, null);
        put(m, "fruit_smoothie_bottle",         DisplayType.BOTTLE, 8, false, null);
        put(m, "_bottle",                       DisplayType.BOTTLE, 12, false, null);
        put(m, "ice_cream_bowl",                DisplayType.BOWL,  4.5, true,  () -> ParticleTypes.SNOWFLAKE);
        put(m, "soup_bowl",                     DisplayType.BOWL,  4,   true,  () -> ParticleTypes.WHITE_ASH);
        put(m, "stew_bowl",                     DisplayType.BOWL,  4,   true,  () -> ParticleTypes.WHITE_ASH);
        put(m, "_bowl",                         DisplayType.BOWL,  4,   false, null);
        put(m, "salad",                         DisplayType.SALAD_BOWL);
        put(m, "pasta_plate",                   DisplayType.PLATE_FOOD);
        put(m, "breakfast_plate",               DisplayType.PLATE_FOOD);
        put(m, "egg_plate",                     DisplayType.PLATE_FOOD);
        put(m, "eggs_plate",                    DisplayType.PLATE_FOOD);
        put(m, "hash_brown_plate",              DisplayType.PLATE_FOOD);
        put(m, "cookie",                        DisplayType.PLATE,  4);
        put(m, "wrap",                          DisplayType.PLATE,  2);
        put(m, "taco",                          DisplayType.PLATE,  2);
        put(m, "burrito",                       DisplayType.PLATE,  2);
        put(m, "ice_cream_stick",               DisplayType.PLATE,  2);
        put(m, "corn_stick",                    DisplayType.PLATE,  2);
        put(m, "cotton_candy_stick",            DisplayType.PLATE,  2);
        put(m, "stick",                         DisplayType.PLATE,  3);
        putMulti(m, "scone",
                new DisplayBlockConfig(DisplayType.PLATE, 4),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE));
        put(m, "cone",                          DisplayType.PLATE,  2);
        put(m, "muffin",                        DisplayType.PLATE,  4);
        put(m, "pastry",                        DisplayType.PLATE,  4);
        put(m, "sweet_roll",                    DisplayType.PLATE,  4);
        put(m, "jam_donut",                     DisplayType.PLATE,  4);
        put(m, "jam_chocolate_donut",           DisplayType.PLATE,  4);
        put(m, "donut",                         DisplayType.PLATE,  5);
        put(m, "fudge",                         DisplayType.PLATE,  2);
        put(m, "bar_of",                        DisplayType.PLATE,  6);
        put(m, "popsicle",                      DisplayType.PLATE,  2);
        put(m, "breakfast_bar",                 DisplayType.PLATE,  6);
        putMulti(m, "baked_potato",
                new DisplayBlockConfig(DisplayType.PLATE, 3),
                new DisplayBlockConfig(DisplayType.SMALL_PLATE));
        put(m, "_chocolate",                    DisplayType.PLATE,  6);

        DISPLAY_CONFIGS = Collections.unmodifiableMap(m);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static void put(Map<String, Object> m, String pattern, DisplayType type) {
        m.put(pattern, new DisplayBlockConfig(type));
    }

    private static void put(Map<String, Object> m, String pattern, DisplayType type, int maxStack) {
        m.put(pattern, new DisplayBlockConfig(type, maxStack));
    }

    private static void put(Map<String, Object> m, String pattern, DisplayType type,
                            double height, boolean hasParticles, Supplier<ParticleOptions> particles) {
        m.put(pattern, new DisplayBlockConfig(type, height, hasParticles, particles));
    }

    private static void putMulti(Map<String, Object> m, String pattern, DisplayBlockConfig... configs) {
        m.put(pattern, new MultiDisplayConfig(configs));
    }

    // ── Lookup helpers ────────────────────────────────────────────────────────

    public static List<DisplayBlockConfig> findMatchingConfigs(String itemName) {
        for (Map.Entry<String, Object> entry : DISPLAY_CONFIGS.entrySet()) {
            if (itemName.contains(entry.getKey())) {
                Object value = entry.getValue();
                if (value instanceof MultiDisplayConfig mdc) return mdc.configs();
                else if (value instanceof DisplayBlockConfig single) return Collections.singletonList(single);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Returns all item IDs from ModItems that should be candidates for display block
     * auto-registration. Block items (cakes, pies, pizzas, etc.) are already excluded
     * by EXCLUDED_ITEMS / SKIP_PATTERNS, or handled via block-based registration.
     */
    public static List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (var entry : ModItems.ITEMS.getEntries()) {
            names.add(entry.getId().getPath());
        }
        // Include REGISTRATE-registered block items (cheese_block, gyro_meat_block, etc.)
        for (var entry : net.averageanime.createfood.CreateFood.REGISTRATE.getAll(
                net.minecraft.core.registries.Registries.BLOCK)) {
            String id = entry.getId().getPath();
            if (!id.contains("_dessert_block")) {
                names.add(id);
            }
        }
        return names;
    }

    public static String getBlockName(String itemName, DisplayType type) {
        return switch (type) {
            case PLATE       -> itemName + "_plate_block";
            case SMALL_PLATE -> itemName + "_small_plate_block";
            case SALAD_BOWL  -> itemName + "_bowl_block";
            case BOTTLE      -> itemName.contains("_bottle") ? itemName + "_block" : itemName + "_bottle_block";
            case BOWL        -> itemName.contains("_bowl")   ? itemName + "_block" : itemName + "_bowl_block";
            default          -> itemName + "_block";
        };
    }
}
