package dev.averageanime.forge.config;

import dev.averageanime.config.ConfigSchema;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConfigRegistration {
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        ForgeConfigSpec.Builder client = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder common = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder server = new ForgeConfigSpec.Builder();
        ConfigSchema.build(adapt(client), adapt(common), adapt(server));
        CLIENT_SPEC = client.build();
        COMMON_SPEC = common.build();
        SERVER_SPEC = server.build();
    }

    private static ConfigSchema.SpecBuilder adapt(ForgeConfigSpec.Builder b) {
        return new ConfigSchema.SpecBuilder() {
            @Override public void push(String path) { b.push(path); }
            @Override public void pop() { b.pop(); }

            @Override
            public Supplier<Boolean> defineBool(String key, boolean defaultValue) {
                return b.define(key, defaultValue)::get;
            }

            @Override
            public Supplier<Integer> defineInt(String key, int defaultValue, int min, int max, boolean gameRestart) {
                return b.defineInRange(key, defaultValue, min, max)::get;
            }

            @Override
            public Supplier<List<? extends String>> defineList(String key, List<String> defaultValue,
                    Supplier<String> elementHint, Predicate<Object> elementValidator, boolean gameRestart) {
                return b.defineListAllowEmpty(key, defaultValue, elementValidator)::get;
            }
        };
    }
}
