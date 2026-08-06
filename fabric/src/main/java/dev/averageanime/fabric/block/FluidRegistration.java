package dev.averageanime.fabric.block;

import dev.averageanime.fabric.block.type.fluid.FluidBlock;
import dev.averageanime.registry.FluidRegistry;
import dev.averageanime.registry.type.FluidEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static dev.averageanime.fabric.CreateFood.LOGGER;

public class FluidRegistration {

    private static final List<FluidBlock> ALL_ENTRIES   = new ArrayList<>();
    private static final List<FluidBlock> CONFIG_FLUID_ENTRIES = new ArrayList<>();
    public  static final Map<String, FluidBlock> BY_ID  = new LinkedHashMap<>();

    public static void init() {
        FluidRegistry.init();
        for (FluidEntry f : FluidEntry.ALL) {
            FluidBlock entry = new FluidBlock(f.id).fog(f.fogStart, f.fogEnd);
            if (f.slope >= 0) entry = entry.flow(f.slope, f.level);
            FluidBlock built = entry.build();
            ALL_ENTRIES.add(built);
            BY_ID.put(f.id, built);
        }
        registerConfigFluids();
    }

    private static void registerConfigFluids() {
        var configFile = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve("createfood-common.toml");
        if (!java.nio.file.Files.exists(configFile)) {
            LOGGER.warn("Create: Food - createfood-common.toml not found yet; skipping custom_fluid registration for this launch");
            return;
        }
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
                FluidBlock fluidBlock = new FluidBlock(name).flow(slope, level).build();
                CONFIG_FLUID_ENTRIES.add(fluidBlock);
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_fluid from config", e);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientRendering() {
        for (FluidBlock entry : ALL_ENTRIES) {
            entry.registerClientRendering();
        }
        for (FluidBlock configFluidBlock : CONFIG_FLUID_ENTRIES) {
            configFluidBlock.registerClientRendering();
        }
    }
}
