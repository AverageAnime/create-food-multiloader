package dev.averageanime.neoforge.block;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.neoforge.block.type.fluid.FluidBlock;
import dev.averageanime.registry.FluidRegistry;
import dev.averageanime.registry.type.FluidEntry;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static dev.averageanime.neoforge.CreateFood.LOGGER;

public class FluidRegistration {

    public static final DeferredRegister<net.minecraft.world.level.material.Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, CreateFoodCommon.MOD_ID);
    public static final DeferredRegister<net.neoforged.neoforge.fluids.FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, CreateFoodCommon.MOD_ID);

    public static final Map<String, FluidBlock.FluidType> BY_ID = new LinkedHashMap<>();

    static {
        FluidRegistry.init();
        for (FluidEntry f : FluidEntry.ALL) {
            FluidBlock entry = new FluidBlock(f.id).fog(f.fogStart, f.fogEnd);
            if (f.slope >= 0) entry = entry.flow(f.slope, f.level);
            BY_ID.put(f.id, entry.build());
        }
    }

    private static void registerConfigFluids() {
        var configFile = net.neoforged.fml.loading.FMLPaths.CONFIGDIR.get().resolve("createfood-common.toml");
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
                new FluidBlock(name).flow(slope, level).build();
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_fluid from config", e);
        }
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Fluids");
        registerConfigFluids();
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }
}
