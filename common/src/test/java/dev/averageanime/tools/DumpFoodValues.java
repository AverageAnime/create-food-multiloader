package dev.averageanime.tools;

import dev.averageanime.registry.ItemRegistry;
import dev.averageanime.registry.type.EffectEntry;
import dev.averageanime.registry.type.ItemEntry;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Dumps every registered food value as the compiler sees it, for the rebalance tooling to diff against its text parse. */
public final class DumpFoodValues {

    private DumpFoodValues() {}

    public static void main(String[] args) throws Exception {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        // Touch the registry so its static initialisers run.
        ItemRegistry.init();

        Path out = Path.of(args.length > 0 ? args[0] : "build/food-values-runtime.csv");
        Files.createDirectories(out.getParent());

        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8))) {
            w.println("item_id,category,nutrition,saturation,effects");
            for (ItemEntry e : ItemEntry.ALL) {
                List<String> fx = new ArrayList<>();
                for (EffectEntry spec : e.effects) {
                    fx.add(spec.categoryOrEffectId() + ":" + spec.duration
                            + ":" + spec.amplifier + ":" + spec.chance);
                }
                w.printf("%s,%s,%d,%s,%s%n",
                        e.id, e.category, e.nutrition,
                        Float.toString(e.saturation), String.join(" ", fx));
            }
        }
        System.out.println("wrote " + out.toAbsolutePath() + " (" + ItemEntry.ALL.size() + " entries)");
    }
}
