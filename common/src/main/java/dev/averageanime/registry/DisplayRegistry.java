package dev.averageanime.registry;

import dev.averageanime.registry.type.BlockEntry;
import dev.averageanime.registry.type.DisplayEntry;
import dev.averageanime.registry.type.ItemEntry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

import java.util.*;
import java.util.function.Supplier;

import static dev.averageanime.registry.DisplayRegistry.DisplayType.*;

public final class DisplayRegistry {
    private DisplayRegistry() {}

    public enum DisplayType {
        PLATE, SMALL_PLATE, BOTTLE, BOWL, BOWL_FOOD, LARGE_BOWL, PLATE_FOOD
    }

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

    public static final List<String> SKIP_PATTERNS = List.of(
            "raw_",
            "stick_1",
            "stick_2",
            "dough", "chips",
            "dried_coffee_beans",
            "chocolate_berries",
            "chocolate_apple",
            "bread_slice",
            "toast_slice",
            "apple_slice",
            "tropical_fish_slice",
            "pretzel_stick",
            "taco_shell",
            "donut_hole",
            "pie_crust",
            "sliced"
    );

    public static final Map<String, Object> DISPLAY_CONFIGS;

    @FunctionalInterface
    private interface Registrar {
        void add(String pattern, DisplayEntry... configs);
    }

    private static Supplier<ParticleOptions> smoke()     { return () -> ParticleTypes.WHITE_SMOKE; }
    private static Supplier<ParticleOptions> snowflake() { return () -> ParticleTypes.SNOWFLAKE; }

    static {
        Map<String, Object> m = new LinkedHashMap<>();

        Registrar register = (pattern, configs) ->
                m.put(pattern, configs.length == 1 ? configs[0] : List.of(configs));

        register.add("_truffle",                   DisplayEntry.of(PLATE).maxStack(6).build(), DisplayEntry.of(SMALL_PLATE).build());
        register.add("kelp_roll_slice",            DisplayEntry.of(PLATE).maxStack(6).build(), DisplayEntry.of(SMALL_PLATE).build());
        register.add("meringue_cookie",            DisplayEntry.of(PLATE).maxStack(9).build(), DisplayEntry.of(SMALL_PLATE).build());
        register.add("kelp_roll",                  DisplayEntry.of(PLATE).maxStack(3).build());
        register.add("gelatin_dessert",            DisplayEntry.of(PLATE).maxStack(6).build());
        register.add("scotch_egg_slice",           DisplayEntry.of(PLATE).maxStack(4).build(), DisplayEntry.of(SMALL_PLATE).build());
        register.add("slice",                      DisplayEntry.of(SMALL_PLATE).build());
        register.add("cream_mini_waffle",          DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("honeyed_mini_waffle",        DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("mini_waffle",                DisplayEntry.of(PLATE).maxStack(4).build());
        register.add("pizza",                      DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("cheese_block",               DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("gyro",                       DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("waffle",                     DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("cupcake",                    DisplayEntry.of(PLATE).maxStack(4).build());
        register.add("cake",                       DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("mini_cream_pie",             DisplayEntry.of(SMALL_PLATE).build());
        register.add("mini_smores_pie",            DisplayEntry.of(SMALL_PLATE).build());
        register.add("mini_cookie_cream_pie",      DisplayEntry.of(SMALL_PLATE).build());
        register.add("mini_chocolate_pie",         DisplayEntry.of(SMALL_PLATE).build());
        register.add("pie",                        DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("burger",                     DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("meatball_sandwich",          DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("hash_brown_sandwich",        DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("sandwich",                   DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("toast_plate",                DisplayEntry.of(PLATE_FOOD).build());
        register.add("toast_fried_egg_plate",      DisplayEntry.of(PLATE_FOOD).build());
        register.add("toast",                      DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("calzone",                    DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("smore",                      DisplayEntry.of(PLATE).maxStack(1).build());
        register.add("hot_chocolate_bottle",       DisplayEntry.of(BOTTLE).height(8).particles(smoke()).build());
        register.add("hot_dark_chocolate_bottle",  DisplayEntry.of(BOTTLE).height(8).particles(smoke()).build());
        register.add("hot_white_chocolate_bottle", DisplayEntry.of(BOTTLE).height(8).particles(smoke()).build());
        register.add("_jam_bottle",                DisplayEntry.of(BOTTLE).height(9).build());
        register.add("cane_syrup_bottle",          DisplayEntry.of(BOTTLE).height(9).build());
        register.add("_sauce_bottle",              DisplayEntry.of(BOTTLE).height(9).build());
        register.add("sugar_cane_juice_bottle",    DisplayEntry.of(BOTTLE).height(9).build());
        register.add("egg_whites_bottle",          DisplayEntry.of(BOTTLE).height(9).build());
        register.add("_juice_bottle",              DisplayEntry.of(BOTTLE).height(10).build());
        register.add("_trifle_bottle",             DisplayEntry.of(BOTTLE).height(8).build());
        register.add("chocolate_bottle",           DisplayEntry.of(BOTTLE).height(8).build());
        register.add("dark_chocolate_bottle",      DisplayEntry.of(BOTTLE).height(8).build());
        register.add("white_chocolate_bottle",     DisplayEntry.of(BOTTLE).height(8).build());
        register.add("chocolate_milk_bottle",      DisplayEntry.of(BOTTLE).height(8).build());
        register.add("fruit_smoothie_bottle",      DisplayEntry.of(BOTTLE).height(8).build());
        register.add("_bottle",                    DisplayEntry.of(BOTTLE).height(12).build());
        register.add("_powder",                    DisplayEntry.of(BOWL).maxStack(2).build());
        register.add("powdered_sugar",             DisplayEntry.of(BOWL).maxStack(2).build());
        register.add("ice_cream_bowl",             DisplayEntry.of(BOWL_FOOD).height(4.5).particles(snowflake()).build());
        register.add("soup_bowl",                  DisplayEntry.of(BOWL_FOOD).height(4).particles(smoke()).build());
        register.add("stew_bowl",                  DisplayEntry.of(BOWL_FOOD).height(4).particles(smoke()).build());
        register.add("_bowl",                      DisplayEntry.of(BOWL_FOOD).height(4).build());
        register.add("salad",                      DisplayEntry.of(LARGE_BOWL).build());
        register.add("pasta_plate",                DisplayEntry.of(PLATE_FOOD).build());
        register.add("breakfast_plate",            DisplayEntry.of(PLATE_FOOD).build());
        register.add("egg_plate",                  DisplayEntry.of(PLATE_FOOD).build());
        register.add("eggs_plate",                 DisplayEntry.of(PLATE_FOOD).build());
        register.add("hash_brown_plate",           DisplayEntry.of(PLATE_FOOD).build());
        register.add("cookie",                     DisplayEntry.of(PLATE).maxStack(4).build());
        register.add("wrap",                       DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("taco",                       DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("burrito",                    DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("ice_cream_stick",            DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("cotton_candy_stick",         DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("stick",                      DisplayEntry.of(PLATE).maxStack(3).build());
        register.add("scone",                      DisplayEntry.of(PLATE).maxStack(4).build(), DisplayEntry.of(SMALL_PLATE).build());
        register.add("cone",                       DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("muffin",                     DisplayEntry.of(PLATE).maxStack(4).build());
        register.add("pastry",                     DisplayEntry.of(PLATE).maxStack(4).build());
        register.add("sweet_roll",                 DisplayEntry.of(PLATE).maxStack(4).build());
        register.add("jam_donut",                  DisplayEntry.of(PLATE).maxStack(4).build());
        register.add("jam_chocolate_donut",        DisplayEntry.of(PLATE).maxStack(4).build());
        register.add("donut",                      DisplayEntry.of(PLATE).maxStack(5).build());
        register.add("fudge",                      DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("bar_of",                     DisplayEntry.of(PLATE).maxStack(6).build());
        register.add("popsicle",                   DisplayEntry.of(PLATE).maxStack(2).build());
        register.add("breakfast_bar",              DisplayEntry.of(PLATE).maxStack(6).build());
        register.add("baked_potato",               DisplayEntry.of(PLATE).maxStack(3).build(), DisplayEntry.of(SMALL_PLATE).build());
        register.add("_chocolate",                 DisplayEntry.of(PLATE).maxStack(6).build());

        DISPLAY_CONFIGS = Collections.unmodifiableMap(m);
    }

    @SuppressWarnings("unchecked")
    public static List<DisplayEntry> findMatchingConfigs(String itemName) {
        for (Map.Entry<String, Object> entry : DISPLAY_CONFIGS.entrySet()) {
            if (itemName.contains(entry.getKey())) {
                Object value = entry.getValue();
                if (value instanceof DisplayEntry single) return Collections.singletonList(single);
                else if (value instanceof List<?> configs) return (List<DisplayEntry>) configs;
            }
        }
        return Collections.emptyList();
    }

    public static List<String> getItemNames() {
        ItemRegistry.init();
        BlockRegistry.init();
        List<String> itemNames = new ArrayList<>();
        for (ItemEntry def : ItemEntry.ALL) {
            itemNames.add(def.id);
        }
        for (BlockEntry def : BlockEntry.ALL) {
            if (!def.id.contains("_dessert_block")) {
                itemNames.add(def.id);
            }
        }
        return itemNames;
    }

    public static String getBlockName(String itemName, DisplayType type) {
        return switch (type) {
            case PLATE       -> itemName + "_plate_block";
            case SMALL_PLATE -> itemName + "_small_plate_block";
            case LARGE_BOWL     -> itemName + "_large_bowl_block";
            case BOWL           -> itemName + "_bowl_block";
            case BOTTLE      -> itemName.contains("_bottle") ? itemName + "_block" : itemName + "_bottle_block";
            case BOWL_FOOD   -> itemName.contains("_bowl")   ? itemName + "_block" : itemName + "_bowl_block";
            default          -> itemName + "_block";
        };
    }
}