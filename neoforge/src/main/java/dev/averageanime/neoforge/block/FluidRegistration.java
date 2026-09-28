package dev.averageanime.neoforge.block;

import dev.averageanime.config.ConfigBootstrap;
import dev.averageanime.config.AddonDefaults;
import dev.averageanime.config.ConfigDefaults;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.neoforge.item.ItemRegistration;
import dev.averageanime.createfood.lib.fluid.FluidBlock;
import dev.averageanime.registry.FluidRegistry;
import dev.averageanime.createfood.lib.fluid.FluidEntry;
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
        // Must happen before the first declaration below.
        FluidBlock.configure(new FluidBlock.Registrars(
                CreateFoodCommon.MOD_ID, FLUIDS, FLUID_TYPES,
                BlockRegistration.BLOCKS, ItemRegistration.ITEMS));

        FluidRegistry.init();
        for (FluidEntry f : FluidEntry.ALL) {
            FluidBlock entry = new FluidBlock(f.id).tex(f.texture).fog(f.fogStart, f.fogEnd);
            if (f.slope >= 0) entry = entry.flow(f.slope, f.level);
            BY_ID.put(f.id, entry.build());
        }
    }

    private static void registerConfigFluids() {
        for (String entry : ConfigBootstrap.read(ConfigBootstrap.FLUIDS, AddonDefaults.customFluids())) {
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
