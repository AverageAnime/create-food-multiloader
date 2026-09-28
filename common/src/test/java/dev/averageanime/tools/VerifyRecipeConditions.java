package dev.averageanime.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.BlockFactory;
import dev.averageanime.config.AddonDefaults;
import dev.averageanime.config.ConfigDefaults;
import dev.averageanime.registry.BlockRegistry;
import dev.averageanime.registry.DisplayRegistry;
import dev.averageanime.registry.DisplayRegistry.DisplayType;
import dev.averageanime.registry.FluidRegistry;
import dev.averageanime.registry.ItemRegistry;
import dev.averageanime.registry.type.BlockEntry;
import dev.averageanime.createfood.lib.fluid.FluidEntry;
import dev.averageanime.registry.type.ItemEntry;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Checks that every {@code createfood:enabled} recipe condition names something this mod registers,
 * and that every {@code neoforge:} condition names a codec NeoForge actually has.
 */
public final class VerifyRecipeConditions {

    private static final String CONDITION = CreateFoodCommon.MOD_ID + ":enabled";

    /**
     * Everything NeoForgeMod registers into {@code neoforge:condition_codecs}. An id outside this set
     * does not resolve, and an unresolved id fails the whole recipe at load rather than dropping it
     * quietly -- which is how {@code neoforge:never}, a condition that never existed, went unnoticed.
     */
    private static final Set<String> NEOFORGE_CONDITIONS = Set.of(
            "neoforge:and", "neoforge:false", "neoforge:item_exists", "neoforge:mod_loaded",
            "neoforge:not", "neoforge:or", "neoforge:tag_empty", "neoforge:true");

    private VerifyRecipeConditions() {}

    public static void main(String[] args) throws Exception {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        Set<String> declared = declaredNames();
        System.out.println("declared " + CreateFoodCommon.MOD_ID + " names: " + declared.size());

        List<String> unresolved = new ArrayList<>();
        List<String> unknownTypes = new ArrayList<>();
        int files = 0;
        int conditions = 0;

        for (Path root : List.of(Path.of("common/src/main/resources"), Path.of("common/src/generated/resources"))) {
            if (!Files.isDirectory(root)) continue;
            try (Stream<Path> walk = Files.walk(root)) {
                for (Path file : walk.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".json"))
                        .filter(p -> p.toString().replace('\\', '/').contains("/recipe/"))
                        .toList()) {
                    JsonObject recipe = parse(file);
                    if (recipe == null) continue;
                    for (String type : unknownNeoforgeTypes(recipe)) {
                        unknownTypes.add(type + "   (" + root.relativize(file) + ")");
                    }
                    boolean counted = false;
                    for (String id : conditionIds(recipe)) {
                        conditions++;
                        if (!counted) { files++; counted = true; }
                        String qualified = id.contains(":") ? id : CreateFoodCommon.MOD_ID + ":" + id;
                        if (!qualified.startsWith(CreateFoodCommon.MOD_ID + ":")) continue;
                        if (!declared.contains(qualified.substring(CreateFoodCommon.MOD_ID.length() + 1))) {
                            unresolved.add(id + "   (" + root.relativize(file) + ")");
                        }
                    }
                }
            }
        }

        System.out.println("recipes carrying the condition: " + files + " (" + conditions + " ids)");

        boolean failed = false;
        if (unresolved.isEmpty()) {
            System.out.println("every " + CONDITION + " id resolves to a declared registration");
        } else {
            System.out.println("UNRESOLVED (" + unresolved.size() + "):");
            unresolved.stream().distinct().sorted().forEach(u -> System.out.println("    " + u));
            System.out.println("If one of these is a foreign item, write its namespace explicitly.");
            failed = true;
        }

        if (unknownTypes.isEmpty()) {
            System.out.println("every neoforge: condition type is one NeoForge registers");
        } else {
            System.out.println("UNKNOWN neoforge: CONDITION TYPES (" + unknownTypes.size() + "):");
            unknownTypes.stream().distinct().sorted().forEach(u -> System.out.println("    " + u));
            System.out.println("Valid ids: " + NEOFORGE_CONDITIONS.stream().sorted().toList());
            failed = true;
        }

        if (failed) System.exit(1);
    }

    /** Includes names derived from a declaration. */
    private static Set<String> declaredNames() {
        ItemRegistry.init();
        BlockRegistry.init();
        FluidRegistry.init();

        Set<String> names = new LinkedHashSet<>();
        for (ItemEntry entry : ItemEntry.ALL) names.add(entry.id);
        for (BlockEntry entry : BlockEntry.ALL) {
            names.add(entry.id);
            addCandleVariants(names, entry.id, entry.category.name());
        }
        for (FluidEntry entry : FluidEntry.ALL) addFluidNames(names, entry.id);

        for (String entry : AddonDefaults.customItems()) names.add(field(entry, 0));
        for (String entry : AddonDefaults.customBlocks()) {
            String id = field(entry, 0);
            names.add(id);
            addCandleVariants(names, id, field(entry, 1).toUpperCase());
        }
        for (String entry : AddonDefaults.customFluids()) addFluidNames(names, field(entry, 0));
        for (String entry : AddonDefaults.customDisplayBlocks()) {
            String item = field(entry, 0);
            String path = item.contains(":") ? item.substring(item.indexOf(':') + 1) : item;
            names.add(path);
            DisplayType type = displayType(field(entry, 1));
            if (type != null) names.add(DisplayRegistry.getBlockName(path, type));
        }

        // Registered by hand rather than from a table.
        names.add("cloth_sack");
        names.add("ration_box");

        names.removeIf(n -> n == null || n.isBlank());
        return names;
    }

    /** Mirrors the config string to {@code DisplayType} mapping in both loaders. */
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

    private static void addCandleVariants(Set<String> names, String id, String category) {
        if (!category.contains("CAKE") || category.contains("BASE")) return;
        for (String suffix : BlockFactory.CANDLE_SUFFIXES) names.add(id + "_" + suffix);
    }

    private static void addFluidNames(Set<String> names, String id) {
        names.add(id);
        names.add("flowing_" + id);
        names.add(id + "_block");
        names.add(id + "_bucket");
    }

    /** Every {@code neoforge:} condition type in the recipe that NeoForge does not register. */
    private static List<String> unknownNeoforgeTypes(JsonObject recipe) {
        List<String> bad = new ArrayList<>();
        JsonElement block = recipe.get("neoforge:conditions");
        if (block != null && block.isJsonArray()) {
            for (JsonElement raw : block.getAsJsonArray()) collectUnknown(raw, bad);
        }
        return bad;
    }

    /** Recurses, because and/or/not carry their operands in {@code values} and {@code value}. */
    private static void collectUnknown(JsonElement raw, List<String> bad) {
        if (raw == null || !raw.isJsonObject()) return;
        JsonObject condition = raw.getAsJsonObject();
        JsonElement type = condition.get("type");
        if (type != null && type.isJsonPrimitive()) {
            String id = type.getAsString();
            if (id.startsWith("neoforge:") && !NEOFORGE_CONDITIONS.contains(id)) bad.add(id);
        }
        collectUnknown(condition.get("value"), bad);
        JsonElement many = condition.get("values");
        if (many != null && many.isJsonArray()) {
            for (JsonElement nested : (JsonArray) many) collectUnknown(nested, bad);
        }
    }

    private static List<String> conditionIds(JsonObject recipe) {
        List<String> ids = new ArrayList<>();
        for (String key : List.of("neoforge:conditions", "fabric:load_conditions")) {
            JsonElement block = recipe.get(key);
            if (block == null || !block.isJsonArray()) continue;
            for (JsonElement raw : block.getAsJsonArray()) {
                if (!raw.isJsonObject()) continue;
                JsonObject condition = raw.getAsJsonObject();
                JsonElement type = condition.has("type") ? condition.get("type") : condition.get("condition");
                if (type == null || !CONDITION.equals(type.getAsString())) continue;
                if (condition.has("id")) ids.add(condition.get("id").getAsString());
                JsonElement many = condition.get("ids");
                if (many != null && many.isJsonArray()) {
                    for (JsonElement id : (JsonArray) many) ids.add(id.getAsString());
                }
            }
        }
        return ids;
    }

    private static JsonObject parse(Path file) {
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            JsonElement parsed = JsonParser.parseReader(reader);
            return parsed.isJsonObject() ? parsed.getAsJsonObject() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static String field(String entry, int index) {
        String[] parts = entry.split("\\|");
        return index < parts.length ? parts[index] : "";
    }
}
