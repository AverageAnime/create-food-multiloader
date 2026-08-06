package dev.averageanime.neoforge.datagen.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.averageanime.CreateFoodCommon;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

public record RecipeGenerator(PackOutput output) implements DataProvider {

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        Path recipeRoot = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(CreateFoodCommon.MOD_ID)
                .resolve("recipe");
        Path shapedRoot = recipeRoot.resolve("crafting").resolve("shaped");

        Path sourceRoot = locateSourceRecipes();

        try (var stream = Files.walk(sourceRoot)) {
            var futures = stream
                    .filter(p -> p.toString().endsWith(".json"))
                    .filter(p -> p.getFileName().toString().contains("_from_crafting"))
                    .map(sourcePath -> processRecipe(sourcePath, sourceRoot, shapedRoot, cache))
                    .filter(Objects::nonNull)
                    .toList();
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        } catch (IOException e) {
            return CompletableFuture.completedFuture(null);
        }
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

    private Path locateSourceRecipes() {
        return Path.of("../../common/src/main/resources/data/" + CreateFoodCommon.MOD_ID + "/recipe/minecraft/crafting");
    }

    @Override
    public @NotNull String getName() {
        return "Shaped Recipes: " + CreateFoodCommon.MOD_ID;
    }
}