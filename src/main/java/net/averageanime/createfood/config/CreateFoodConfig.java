package net.averageanime.createfood.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

public class CreateFoodConfig {

    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }

    public static class Server {

        public final ForgeConfigSpec.BooleanValue enablePumpkinPiePlacement;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> handcraftExclude;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> nutritionSaturation;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> categoryOverrides;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> itemOverrides;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> customItem;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> customBlock;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> customFluid;

        Server(ForgeConfigSpec.Builder builder) {
            builder.push("general");

            enablePumpkinPiePlacement = builder
                    .comment("Allow the pumpkin pie to be placed as a block")
                    .define("enable_pumpkin_pie_placement", true);

            handcraftExclude = builder
                    .comment("Item IDs to exclude from handcrafting interactions (e.g. createfood:apple_jam)")
                    .defineList("handcraft_exclude", Collections.emptyList(), e -> e instanceof String);

            customItem = builder
                    .comment("Additional item IDs to register (format: 'modid:item_id')")
                    .defineList("custom_item", Collections.emptyList(), e -> e instanceof String);

            customBlock = builder
                    .comment("Additional block IDs to register (format: 'modid:block_id')")
                    .defineList("custom_block", Collections.emptyList(), e -> e instanceof String);

            customFluid = builder
                    .comment("Additional fluid IDs to register (format: 'modid:fluid_id')")
                    .defineList("custom_fluid", Collections.emptyList(), e -> e instanceof String);

            builder.pop();

            builder.push("nutrition");

            nutritionSaturation = builder
                    .comment("Override nutrition and saturation per item. Format: 'item_id|nutrition|saturation' (use '-' to keep original)")
                    .defineList("nutrition_saturation", Collections.emptyList(), e -> e instanceof String);

            builder.pop();

            builder.push("effects");

            categoryOverrides = builder
                    .comment("Override the effect applied to a food category. Format: 'category_name|effect_id'")
                    .defineList("category_overrides", Collections.emptyList(), e -> e instanceof String);

            itemOverrides = builder
                    .comment("Override effects per item. Format: 'item_id|effect_id|duration|amplifier' or 'item_id|effect_id|remove'")
                    .defineList("item_overrides", Collections.emptyList(), e -> e instanceof String);

            builder.pop();
        }
    }
}
