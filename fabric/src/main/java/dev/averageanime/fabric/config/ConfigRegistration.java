package dev.averageanime.fabric.config;

import dev.averageanime.config.ConfigSchema;
import dev.averageanime.createfood.lib.config.FabricConfigSpec;
import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;

public class ConfigRegistration {
    public static final ModConfigSpec CLIENT_SPEC;
    public static final ModConfigSpec COMMON_SPEC;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        ModConfigSpec.Builder client = new ModConfigSpec.Builder();
        ModConfigSpec.Builder common = new ModConfigSpec.Builder();
        ModConfigSpec.Builder server = new ModConfigSpec.Builder();
        ConfigSchema.build(FabricConfigSpec.adapt(client),
                FabricConfigSpec.adapt(common),
                FabricConfigSpec.adapt(server));
        CLIENT_SPEC = client.build();
        COMMON_SPEC = common.build();
        SERVER_SPEC = server.build();
    }
}
