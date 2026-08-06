package dev.averageanime.fabric.config;

import dev.averageanime.config.ConfigSchema;
import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConfigRegistration {
    public static final ModConfigSpec CLIENT_SPEC;
    public static final ModConfigSpec COMMON_SPEC;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        ModConfigSpec.Builder client = new ModConfigSpec.Builder();
        ModConfigSpec.Builder common = new ModConfigSpec.Builder();
        ModConfigSpec.Builder server = new ModConfigSpec.Builder();
        ConfigSchema.build(adapt(client), adapt(common), adapt(server));
        CLIENT_SPEC = client.build();
        COMMON_SPEC = common.build();
        SERVER_SPEC = server.build();
    }

    /** Porting Lib's builder has no gameRestart() or element-hint support; both are ignored. */
    private static ConfigSchema.SpecBuilder adapt(ModConfigSpec.Builder b) {
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
