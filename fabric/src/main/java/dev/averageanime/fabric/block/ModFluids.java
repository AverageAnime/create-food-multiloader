package dev.averageanime.fabric.block;

import dev.averageanime.fabric.block.type.fluid.FluidEntry;
import dev.averageanime.registry.FluidRegistry;
import dev.averageanime.registry.type.Fluid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static dev.averageanime.fabric.CreateFood.LOGGER;

public class ModFluids {

    private static final List<FluidEntry> ALL_ENTRIES   = new ArrayList<>();
    private static final List<FluidEntry> CONFIG_FLUIDS = new ArrayList<>();
    public  static final Map<String, FluidEntry> BY_ID  = new LinkedHashMap<>();

    public static void init() {
        FluidRegistry.init();
        for (Fluid f : Fluid.ALL) {
            FluidEntry entry = new FluidEntry(f.id);
            if (f.slope >= 0) entry = entry.flow(f.slope, f.level);
            FluidEntry built = entry.build();
            ALL_ENTRIES.add(built);
            BY_ID.put(f.id, built);
        }
        registerConfigFluids();
    }

    private static void registerConfigFluids() {
        var configFile = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve("createfood-client.toml");
        if (!java.nio.file.Files.exists(configFile)) return;
        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("fluids.fluid", List.of());
            for (String entry : entries) {
                String[] p = entry.split("\\|");
                if (p.length != 3) {
                    LOGGER.warn("Create: Food - Skipping invalid custom_fluid entry (expected name|slope|level): {}", entry);
                    continue;
                }
                String name = p[0];
                int slope, level;
                try {
                    slope = Integer.parseInt(p[1]);
                    level = Integer.parseInt(p[2]);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Create: Food - Skipping custom_fluid entry with non-integer flow values: {}", entry);
                    continue;
                }
                FluidEntry fluid = new FluidEntry(name).flow(slope, level).build();
                CONFIG_FLUIDS.add(fluid);
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_fluid from config", e);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientRendering() {
        for (FluidEntry entry : ALL_ENTRIES) {
            entry.registerClientRendering();
        }
        for (FluidEntry configFluid : CONFIG_FLUIDS) {
            configFluid.registerClientRendering();
        }
    }
}
