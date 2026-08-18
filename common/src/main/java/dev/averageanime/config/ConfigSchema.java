package dev.averageanime.config;

import dev.averageanime.registry.FluidAmounts;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ConfigSchema {

    public interface SpecBuilder {
        void push(String path);
        void pop();
        Supplier<Boolean> defineBool(String key, boolean defaultValue);
        Supplier<Integer> defineInt(String key, int defaultValue, int min, int max, boolean gameRestart);
        Supplier<List<? extends String>> defineList(String key, List<String> defaultValue,
                Supplier<String> elementHint, Predicate<Object> elementValidator, boolean gameRestart);
    }

    private static final Predicate<Object> STRING = obj -> obj instanceof String;
    private static final Supplier<String> FILTER_HINT = () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name";

    private ConfigSchema() {}

    public static void build(SpecBuilder client, SpecBuilder common, SpecBuilder server) {
        buildClient(client);
        buildCommon(common);
        buildServer(server);
    }

    private static void buildClient(SpecBuilder b) {
        b.push("blocks");
        ConfigValues.ALWAYS_DISPLAY_UPRIGHT = b.defineBool("always_display_upright", false);
        ConfigValues.SHOW_SACK_BLOCK_ICONS = b.defineBool("show_sack_block_icons", true);
        ConfigValues.SHOW_STORAGE_TOOLTIP_ICONS = b.defineBool("show_tooltip_icons", true);
        b.pop();

        b.push("tooltips");
        ConfigValues.REQUIRE_SHIFT_FOR_TOOLTIPS = b.defineBool("require_shift", false);
        ConfigValues.SHOW_COMPATIBILITY = b.defineBool("show_compatibility", true);
        ConfigValues.SHOW_INGREDIENTS = b.defineBool("show_ingredients", true);
        ConfigValues.CUSTOM_TOOLTIPS = b.defineList("custom_tooltips", ConfigDefaults.CUSTOM_TOOLTIPS_DEFAULT,
                () -> "item_key|ingredients[|compat_key]", STRING, true);
        b.pop();
    }

    private static void buildCommon(SpecBuilder b) {
        b.push("blocks");
        b.defineList("block", ConfigDefaults.CUSTOM_BLOCK_DEFAULT,
                () -> "name|type|slice_item_id  OR  name|type",
                ConfigDefaults.CUSTOM_BLOCK_VALIDATOR, true);
        b.defineList("display_block", ConfigDefaults.CUSTOM_DISPLAY_BLOCK_DEFAULT,
                () -> "mod:item_id|display_type|max_stack[|height[|particles]]",
                ConfigDefaults.CUSTOM_DISPLAY_BLOCK_VALIDATOR, true);
        b.defineList("fluid", ConfigDefaults.CUSTOM_FLUID_DEFAULT,
                () -> "name  OR  name|slopeFindDistance|levelDecreasePerBlock",
                ConfigDefaults.CUSTOM_FLUID_VALIDATOR, true);
        b.pop();

        b.push("items");
        b.defineList("item", ConfigDefaults.CUSTOM_ITEM_DEFAULT,
                () -> "name|type|nutrition|saturation  OR  name|type",
                ConfigDefaults.CUSTOM_ITEM_VALIDATOR, true);
        ConfigValues.HIDE_ITEMS = b.defineList("hide_items", ConfigDefaults.HIDE_ITEMS_DEFAULT,
                () -> "item_id", STRING, false);
        b.pop();
    }

    private static void buildServer(SpecBuilder b) {
        b.push("blocks");

        b.push("display");
        ConfigValues.ENABLE_CUTTING_BOARD = b.defineBool("enable_cutting_board", true);
        ConfigValues.ENABLE_DISPLAY_INTERACTIONS = b.defineBool("enable_display_interactions", true);
        ConfigValues.ENABLE_GENERIC_DISPLAY = b.defineBool("enable_generic_display", true);
        ConfigValues.LARGE_BOWL_CAPACITY_MB = b.defineInt("large_bowl_capacity_mb",
                FluidAmounts.BUCKET * 4, FluidAmounts.BUCKET, FluidAmounts.BUCKET * 16, false);
        ConfigValues.DISPLAY_INTERACTIONS_EXCLUDE = b.defineList("display_interactions_exclude", ConfigDefaults.DISPLAY_INTERACTIONS_EXCLUDE_DEFAULT,
                FILTER_HINT, STRING, false);
        ConfigValues.GENERIC_DISPLAY_EXCLUDE = b.defineList("generic_display_exclude", ConfigDefaults.GENERIC_DISPLAY_EXCLUDE_DEFAULT,
                FILTER_HINT, STRING, false);
        b.pop();

        b.push("storage");
        b.push("cloth_sack");
        ConfigValues.CLOTH_SACK_ALLOW_FOOD = b.defineBool("cloth_sack_allow_food", false);
        ConfigValues.CLOTH_SACK_STACK = b.defineBool("cloth_sack_stack", true);
        ConfigValues.CLOTH_SACK_EAT_FROM_ITEM = b.defineBool("cloth_sack_eat_from_item", false);
        ConfigValues.CLOTH_SACK_INVENTORY_ENABLED = b.defineBool("cloth_sack_inventory", true);
        ConfigValues.CLOTH_SACK_EXCLUDE = b.defineList("cloth_sack_exclude", List.of("item:createfood:cloth_sack"),
                FILTER_HINT, STRING, false);
        ConfigValues.CLOTH_SACK_FILTER = b.defineList("cloth_sack_filter", List.of(), FILTER_HINT, STRING, false);
        b.pop();
        b.push("ration_box");
        ConfigValues.RATION_BOX_ALLOW_FOOD = b.defineBool("ration_box_allow_food", true);
        ConfigValues.RATION_BOX_STACK = b.defineBool("ration_box_stack", false);
        ConfigValues.RATION_BOX_EAT_FROM_ITEM = b.defineBool("ration_box_eat_from_item", true);
        ConfigValues.RATION_BOX_INVENTORY_ENABLED = b.defineBool("ration_box_inventory", true);
        ConfigValues.RATION_BOX_EXCLUDE = b.defineList("ration_box_exclude", List.of(), FILTER_HINT, STRING, false);
        ConfigValues.RATION_BOX_FILTER = b.defineList("ration_box_filter", List.of(), FILTER_HINT, STRING, false);
        b.pop();
        b.pop();

        b.pop();

        b.push("interactions");
        ConfigValues.ENABLE_FILTER_INTERACTIONS = b.defineBool("enable_filter_interactions", true);
        ConfigValues.ENABLE_PUMPKIN_PIE_PLACEMENT = b.defineBool("enable_pumpkin_pie_placement", false);

        b.push("campfire_cooking");
        ConfigValues.CAMPFIRE_COOKING_ENABLED = b.defineBool("enable_campfire_cooking", false);
        ConfigValues.CAMPFIRE_COOKING_REQUIRE_SHIFT = b.defineBool("require_shift", true);
        ConfigValues.CAMPFIRE_COOKING_STICKS_ONLY = b.defineBool("sticks_only", false);
        ConfigValues.CAMPFIRE_COOKING_HORIZONTAL_RANGE = b.defineInt("horizontal_range", 3, 1, 16, false);
        ConfigValues.CAMPFIRE_COOKING_VERTICAL_RANGE = b.defineInt("vertical_range", 1, 0, 8, false);
        ConfigValues.CAMPFIRE_COOKING_EXCLUDE = b.defineList("campfire_cooking_exclude", List.of(), FILTER_HINT, STRING, false);
        ConfigValues.CAMPFIRE_COOKING_FILTER = b.defineList("campfire_cooking_filter", List.of(), FILTER_HINT, STRING, false);
        b.pop();

        ConfigValues.FILTER_INTERACTIONS = b.defineList("filter_interactions", ConfigDefaults.FILTER_INTERACTIONS_DEFAULT,
                () -> "filter_item|offhand_item|filter_result|container_result",
                ConfigDefaults.FILTER_INTERACTIONS_VALIDATOR, false);

        b.push("handcraft");
        ConfigValues.HANDCRAFTING_ALLOW_SINGLE = b.defineBool("allow_single_item", false);
        ConfigValues.ENABLE_HANDCRAFTING = b.defineBool("enable_handcrafting", true);
        ConfigValues.HANDCRAFTING_PARTICLES = b.defineBool("enable_particles", true);
        ConfigValues.HANDCRAFTING_EXCLUDE = b.defineList("exclude", ConfigDefaults.HANDCRAFTING_EXCLUDE_DEFAULT,
                FILTER_HINT, STRING, false);
        ConfigValues.HANDCRAFTING_FILTER = b.defineList("filter", List.of(), FILTER_HINT, STRING, false);
        b.pop();

        b.pop();

        b.push("items");
        b.push("effects");
        ConfigValues.CATEGORY_EFFECT_OVERRIDES = b.defineList("category_overrides", List.of(),
                () -> "category_name|mod_id:effect_id",
                ConfigDefaults.CATEGORY_EFFECT_OVERRIDE_VALIDATOR, false);
        ConfigValues.ITEM_EFFECT_OVERRIDES = b.defineList("item_overrides", ConfigDefaults.ITEM_EFFECT_OVERRIDES_DEFAULT,
                () -> "item_id|category_or_effect_id|duration|amplifier[|chance]  OR  item_id|category_or_effect_id|remove",
                ConfigDefaults.ITEM_EFFECT_OVERRIDE_VALIDATOR, false);
        ConfigValues.STACK_EFFECT_DURATION = b.defineBool("stack_duration", true);
        ConfigValues.MAX_STACKED_DURATION = b.defineInt("max_stacked_duration", 36000, 0, 1728000, false);
        b.pop();
        ConfigValues.ITEM_NUTRITION_OVERRIDES = b.defineList("nutrition_saturation",
                ConfigDefaults.ITEM_NUTRITION_OVERRIDES_DEFAULT,
                () -> "item_id|nutrition|saturation",
                ConfigDefaults.ITEM_NUTRITION_OVERRIDE_VALIDATOR, false);
        b.push("remainders");
        ConfigValues.ENABLE_EGG_IMPACT_REMAINDER = b.defineBool("enable_egg_impact_remainder", true);
        ConfigValues.CRAFTING_REMAINDERS = b.defineList("crafting_remainders", ConfigDefaults.CRAFTING_REMAINDERS_DEFAULT,
                () -> "input_item|remainder_item", STRING, false);
        b.pop();
        b.pop();

        b.push("compat");
        b.push("create");
        ConfigValues.CREATE_EXPANDED_BASIN_FLUIDS = b.defineBool("expanded_basin_fluids", true);
        ConfigValues.CREATE_BASIN_FLUID_ITEMS = b.defineBool("basin_fluid_items", true);
        b.pop();
        b.pop();
    }
}
