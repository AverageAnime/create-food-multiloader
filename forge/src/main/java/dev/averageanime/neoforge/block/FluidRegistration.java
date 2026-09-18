package dev.averageanime.forge.block;

import dev.averageanime.config.ConfigBootstrap;
import dev.averageanime.config.ConfigDefaults;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.forge.block.type.fluid.FluidBlock;
import dev.averageanime.registry.FluidRegistry;
import dev.averageanime.registry.type.FluidEntry;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static dev.averageanime.forge.CreateFood.LOGGER;

public class FluidRegistration {

    public static final DeferredRegister<net.minecraft.world.level.material.Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, CreateFoodCommon.MOD_ID);
    public static final DeferredRegister<net.minecraftforge.fluids.FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CreateFoodCommon.MOD_ID);

    public static final Map<String, FluidBlock.FluidType> BY_ID = new LinkedHashMap<>();

    static {
        FluidRegistry.init();
        for (FluidEntry f : FluidEntry.ALL) {
            FluidBlock entry = new FluidBlock(f.id).tex(f.texture).fog(f.fogStart, f.fogEnd);
            if (f.slope >= 0) entry = entry.flow(f.slope, f.level);
            BY_ID.put(f.id, entry.build());
        }
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
            BY_ID.put(name, builder.build());
        }
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Fluids");
        registerConfigFluids();
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }
}
