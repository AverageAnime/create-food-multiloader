package dev.averageanime.fabric.block;

import dev.averageanime.config.ConfigBootstrap;
import dev.averageanime.config.ConfigDefaults;
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
            FluidBlock entry = new FluidBlock(f.id).tex(f.texture).fog(f.fogStart, f.fogEnd);
            if (f.slope >= 0) entry = entry.flow(f.slope, f.level);
            FluidBlock built = entry.build();
            ALL_ENTRIES.add(built);
            BY_ID.put(f.id, built);
        }
        registerConfigFluids();
    }

    private static void registerConfigFluids() {
        for (String entry : ConfigBootstrap.read(ConfigBootstrap.FLUIDS, ConfigDefaults.CUSTOM_FLUID_DEFAULT)) {
            String[] p = entry.split("\\|");
            if (p.length != 1 && p.length != 3) {
                LOGGER.warn("Create: Food - Skipping invalid custom_fluid entry (expected name or name|slope|level): {}", entry);
                continue;
            }
            String name = p[0];
            FluidBlock builder = new FluidBlock(name);
            if (p.length == 3) {
                try {
                    builder = builder.flow(Integer.parseInt(p[1]), Integer.parseInt(p[2]));
                } catch (NumberFormatException e) {
                    LOGGER.warn("Create: Food - Skipping custom_fluid entry with non-integer flow values: {}", entry);
                    continue;
                }
            }
            FluidBlock fluidBlock = builder.build();
            CONFIG_FLUID_ENTRIES.add(fluidBlock);
            BY_ID.put(name, fluidBlock);
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
