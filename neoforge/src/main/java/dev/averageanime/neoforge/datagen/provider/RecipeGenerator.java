package dev.averageanime.neoforge.datagen.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.averageanime.CreateFoodCommon;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

public record RecipeGenerator(PackOutput output) implements DataProvider {

    /** Extra resource roots to scan for shapeless recipes, separated by {@link File#pathSeparator}. */
    public static final String RECIPE_SOURCE_ROOTS_PROPERTY = "createfood.recipeSourceRoots";

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        Path recipeRoot = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(CreateFoodCommon.MOD_ID)
                .resolve("recipe");
        Path shapedRoot = recipeRoot.resolve("crafting").resolve("shaped");

        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (Path sourceRoot : locateSourceRecipes()) {
            if (!Files.isDirectory(sourceRoot)) continue;
            try (var stream = Files.walk(sourceRoot)) {
                futures.addAll(stream
                        .filter(p -> p.toString().endsWith(".json"))
                        .filter(p -> p.getFileName().toString().contains("_from_crafting"))
                        .map(sourcePath -> processRecipe(sourcePath, sourceRoot, shapedRoot, cache))
                        .filter(Objects::nonNull)
                        .toList());
            } catch (IOException e) {
                return CompletableFuture.completedFuture(null);
            }
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private CompletableFuture<?> processRecipe(Path sourcePath, Path sourceRoot,
                                               Path shapedRoot, CachedOutput cache) {
        try (InputStream in = Files.newInputStream(sourcePath)) {
            JsonObject json = JsonParser.parseReader(new InputStreamReader(in)).getAsJsonObject();

            String type = json.has("type") ? json.get("type").getAsString() : "";
            if (!type.equals("minecraft:crafting_shapeless")) return null;

            JsonArray ingredients = json.getAsJsonArray("ingredients");
            if (ingredients == null || ingredients.size() != 2) return null;

            JsonObject ing0 = ingredients.get(0).getAsJsonObject();
            JsonObject ing1 = ingredients.get(1).getAsJsonObject();

            if (!json.has("result")) return null;
            JsonObject sourceResult = json.getAsJsonObject("result");

            JsonObject shaped = new JsonObject();
            shaped.addProperty("type", "minecraft:crafting_shaped");

            JsonArray pattern = new JsonArray();
            pattern.add("1");
            pattern.add("2");
            shaped.add("pattern", pattern);

            JsonObject key = new JsonObject();
            key.add("1", ing1.deepCopy()); // topping on top
            key.add("2", ing0.deepCopy()); // base on bottom
            shaped.add("key", key);

            shaped.add("result", sourceResult.deepCopy());

            if (json.has("neoforge:conditions")) {
                shaped.add("neoforge:conditions", json.get("neoforge:conditions").deepCopy());
            }
            if (json.has("fabric:load_conditions")) {
                shaped.add("fabric:load_conditions", json.get("fabric:load_conditions").deepCopy());
            }

            Path relative = sourceRoot.relativize(sourcePath.getParent());
            String outFileName = sourcePath.getFileName().toString()
                    .replace("_from_crafting", "_from_shaped");
            Path outPath = shapedRoot.resolve(relative).resolve(outFileName);

            return DataProvider.saveStable(cache, shaped, outPath);

        } catch (IOException e) {
            return null;
        }
    }

    /** Roots holding the shapeless recipes this mirrors into shaped ones; {@code -Dcreatefood.recipeSourceRoots} adds more. */
    private List<Path> locateSourceRecipes() {
        String suffix = "data/" + CreateFoodCommon.MOD_ID + "/recipe/minecraft/crafting";
        List<Path> roots = new ArrayList<>();
        roots.add(Path.of("../../common/src/main/resources/" + suffix));

        String extra = System.getProperty(RECIPE_SOURCE_ROOTS_PROPERTY);
        if (extra != null && !extra.isBlank()) {
            for (String root : extra.split(File.pathSeparator)) {
                if (!root.isBlank()) roots.add(Path.of(root.trim()).resolve(suffix));
            }
        }
        return roots;
    }

    @Override
    public @NotNull String getName() {
        return "Shaped Recipes: " + CreateFoodCommon.MOD_ID;
    }
}