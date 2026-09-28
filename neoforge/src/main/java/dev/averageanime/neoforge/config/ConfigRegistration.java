package dev.averageanime.neoforge.config;

import dev.averageanime.config.ConfigSchema;
import dev.averageanime.createfood.lib.config.NeoForgeConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigRegistration {
    public static final ModConfigSpec CLIENT_SPEC;
    public static final ModConfigSpec COMMON_SPEC;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        ModConfigSpec.Builder client = new ModConfigSpec.Builder();
        ModConfigSpec.Builder common = new ModConfigSpec.Builder();
        ModConfigSpec.Builder server = new ModConfigSpec.Builder();
        ConfigSchema.build(NeoForgeConfigSpec.adapt(client),
                NeoForgeConfigSpec.adapt(common),
                NeoForgeConfigSpec.adapt(server));
        CLIENT_SPEC = client.build();
        COMMON_SPEC = common.build();
        SERVER_SPEC = server.build();
    }

    /** Referenced from the mod's config-screen extension point. */
    public static class ConfigScreen extends NeoForgeConfigSpec.Screen {}
}
