package dev.averageanime.neoforge.datagen.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.config.AddonManifest;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

/**
 * Adds a load condition to the loot table of every block an addon owns.
 *
 * <p>A loot table names its block in both a state-property condition and an item entry, and both are
 * resolved against the registries when the table is parsed. With the addon that registers the block turned
 * off, the block is absent, the codec fails, and the table is dropped with an error for every affected
 * block. The condition makes the table skip loading instead, which is what disabling an addon should look
 * like. Runs after the loot tables are written, and re-saves them through the same cache.
 */
public record AddonLootConditionProvider(PackOutput output) implements DataProvider {

    private static final String NEOFORGE_CONDITIONS = "neoforge:conditions";
    private static final String FABRIC_CONDITIONS = "fabric:load_conditions";

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        Path blocks = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(CreateFoodCommon.MOD_ID)
                .resolve("loot_table")
                .resolve("blocks");
        if (!Files.isDirectory(blocks)) return CompletableFuture.completedFuture(null);

        Set<String> addonOwned = AddonManifest.names();
        List<CompletableFuture<?>> futures = new ArrayList<>();
        try (var stream = Files.list(blocks)) {
            for (Path table : stream.filter(p -> p.toString().endsWith(".json")).toList()) {
                String name = table.getFileName().toString().replace(".json", "");
                if (!addonOwned.contains(name)) continue;
                CompletableFuture<?> written = condition(table, name, cache);
                if (written != null) futures.add(written);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to list loot tables in " + blocks, e);
        }
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private static CompletableFuture<?> condition(Path table, String name, CachedOutput cache) {
        JsonObject json;
        try (Reader reader = Files.newBufferedReader(table, StandardCharsets.UTF_8)) {
            json = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read " + table, e);
        }
        if (json.has(NEOFORGE_CONDITIONS) || json.has(FABRIC_CONDITIONS)) return null;

        JsonObject neoforge = new JsonObject();
        neoforge.addProperty("type", CreateFoodCommon.MOD_ID + ":enabled");
        neoforge.addProperty("id", name);
        JsonArray neoforgeArray = new JsonArray();
        neoforgeArray.add(neoforge);

        JsonObject fabric = new JsonObject();
        fabric.addProperty("condition", CreateFoodCommon.MOD_ID + ":enabled");
        fabric.addProperty("id", name);
        JsonArray fabricArray = new JsonArray();
        fabricArray.add(fabric);

        JsonObject conditioned = new JsonObject();
        conditioned.add(NEOFORGE_CONDITIONS, neoforgeArray);
        conditioned.add(FABRIC_CONDITIONS, fabricArray);
        json.entrySet().forEach(entry -> conditioned.add(entry.getKey(), entry.getValue()));

        return DataProvider.saveStable(cache, conditioned, table);
    }

    @Override
    public @NotNull String getName() {
        return CreateFoodCommon.MOD_ID + " addon loot conditions";
    }
}
