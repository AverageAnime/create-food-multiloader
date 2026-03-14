package dev.averageanime.neoforge.config;

import dev.averageanime.neoforge.config.condition.EnabledCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.mojang.serialization.MapCodec;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, "createfood");

    public static final Supplier<MapCodec<EnabledCondition>> CONFIG_CONDITION =
            CONDITIONS.register("enabled", () -> EnabledCondition.CODEC);

    public static void register(IEventBus modEventBus) {
        CONDITIONS.register(modEventBus);
    }
}