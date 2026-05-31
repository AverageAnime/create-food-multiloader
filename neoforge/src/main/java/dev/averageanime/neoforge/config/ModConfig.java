package dev.averageanime.neoforge.config;

import dev.averageanime.config.ConfigDefaults;
import dev.averageanime.config.ConfigLogic;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.config.ItemNutritionOverride;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ALWAYS_DISPLAY_UPRIGHT;
    public static final ModConfigSpec.BooleanValue CLOTH_SACK_ALLOW_FOOD;
    public static final ModConfigSpec.BooleanValue CLOTH_SACK_EAT_FROM_ITEM;
    public static final ModConfigSpec.BooleanValue CLOTH_SACK_INVENTORY_ENABLED;
    public static final ModConfigSpec.BooleanValue CLOTH_SACK_STACK;
    public static final ModConfigSpec.BooleanValue ENABLE_CUTTING_BOARD;
    public static final ModConfigSpec.BooleanValue ENABLE_EGG_IMPACT_REMAINDER;
    public static final ModConfigSpec.BooleanValue ENABLE_FILTER_INTERACTIONS;
    public static final ModConfigSpec.BooleanValue ENABLE_GENERIC_PLATES;
    public static final ModConfigSpec.BooleanValue ENABLE_HANDCRAFTING;
    public static final ModConfigSpec.BooleanValue ENABLE_PUMPKIN_PIE_PLACEMENT;
    public static final ModConfigSpec.BooleanValue HANDCRAFTING_ALLOW_SINGLE;
    public static final ModConfigSpec.BooleanValue HANDCRAFTING_PARTICLES;
    public static final ModConfigSpec.BooleanValue RATION_BOX_ALLOW_FOOD;
    public static final ModConfigSpec.BooleanValue RATION_BOX_EAT_FROM_ITEM;
    public static final ModConfigSpec.BooleanValue RATION_BOX_INVENTORY_ENABLED;
    public static final ModConfigSpec.BooleanValue RATION_BOX_STACK;
    public static final ModConfigSpec.BooleanValue REQUIRE_SHIFT_FOR_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue SHOW_COMPATIBILITY;
    public static final ModConfigSpec.BooleanValue SHOW_INGREDIENTS;
    public static final ModConfigSpec.BooleanValue SHOW_SACK_BLOCK_ICONS;
    public static final ModConfigSpec.BooleanValue SHOW_STORAGE_TOOLTIP_ICONS;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CATEGORY_EFFECT_OVERRIDES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CLOTH_SACK_EXCLUDE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CLOTH_SACK_FILTER;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CRAFTING_REMAINDERS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_BLOCK;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_DISPLAY_BLOCK;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_FLUID;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_ITEM;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_TOOLTIPS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FILTER_INTERACTIONS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> GENERIC_DISPLAY_EXCLUDE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HANDCRAFTING_EXCLUDE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HANDCRAFTING_FILTER;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HIDE_ITEMS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_EFFECT_OVERRIDES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_NUTRITION_OVERRIDES;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> RATION_BOX_EXCLUDE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> RATION_BOX_FILTER;

    static {
        BUILDER.push("display");

        ALWAYS_DISPLAY_UPRIGHT = BUILDER.define("always_display_upright", false);

        CUSTOM_DISPLAY_BLOCK = BUILDER
                .gameRestart()
                .defineListAllowEmpty("display_block",
                        ConfigDefaults.CUSTOM_DISPLAY_BLOCK_DEFAULT,
                        () -> "mod:item_id|display_type|max_stack[|height[|particles]]",
                        ConfigDefaults.CUSTOM_DISPLAY_BLOCK_VALIDATOR
                );

        BUILDER.pop();

        BUILDER.push("items");

        CUSTOM_ITEM = BUILDER
                .gameRestart()
                .defineListAllowEmpty("item",
                        List.of(),
                        () -> "name|type|nutrition|saturation  OR  name|type",
                        ConfigDefaults.CUSTOM_ITEM_VALIDATOR
                );

        HIDE_ITEMS = BUILDER
                .defineListAllowEmpty("hide_items",
                        ConfigDefaults.HIDE_ITEMS_DEFAULT,
                        () -> "item_id",
                        obj -> obj instanceof String
                );

        BUILDER.push("tooltips");

        REQUIRE_SHIFT_FOR_TOOLTIPS = BUILDER
                .define("require_shift", false);

        SHOW_COMPATIBILITY = BUILDER
                .define("show_compatibility", true);

        SHOW_INGREDIENTS = BUILDER
                .define("show_ingredients", true);

        CUSTOM_TOOLTIPS = BUILDER
                .gameRestart()
                .defineListAllowEmpty("custom_tooltips",
                        ConfigDefaults.CUSTOM_TOOLTIPS_DEFAULT,
                        () -> "item_key|ingredients",
                        obj -> obj instanceof String
                );

        BUILDER.pop(); // items.tooltips
        BUILDER.pop(); // items

        BUILDER.push("blocks");

        CUSTOM_BLOCK = BUILDER
                .gameRestart()
                .defineListAllowEmpty("block",
                        List.of(),
                        () -> "name|type|slice_item_id  OR  name|type",
                        ConfigDefaults.CUSTOM_BLOCK_VALIDATOR
                );

        BUILDER.push("storage");

        SHOW_SACK_BLOCK_ICONS = BUILDER
                .define("show_sack_block_icons", true);

        SHOW_STORAGE_TOOLTIP_ICONS = BUILDER
                .define("show_tooltip_icons", true);

        BUILDER.pop(); // blocks.storage
        BUILDER.pop(); // blocks

        BUILDER.push("fluids");

        CUSTOM_FLUID = BUILDER
                .gameRestart()
                .defineListAllowEmpty("fluid",
                        List.of(),
                        () -> "name|slopeFindDistance|levelDecreasePerBlock",
                        ConfigDefaults.CUSTOM_FLUID_VALIDATOR
                );

        BUILDER.pop(); // fluids

        SERVER_BUILDER.push("items");

        ITEM_NUTRITION_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("nutrition_saturation",
                        List.of(),
                        () -> "item_id|nutrition|saturation",
                        ConfigDefaults.ITEM_NUTRITION_OVERRIDE_VALIDATOR
                );

        SERVER_BUILDER.push("effects");

        CATEGORY_EFFECT_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("category_overrides",
                        List.of(),
                        () -> "category_name|mod_id:effect_id",
                        ConfigDefaults.CATEGORY_EFFECT_OVERRIDE_VALIDATOR
                );

        ITEM_EFFECT_OVERRIDES = SERVER_BUILDER
                .defineListAllowEmpty("item_overrides",
                        List.of(),
                        () -> "item_id|category_or_effect_id|duration|amplifier  OR  item_id|category_or_effect_id|remove",
                        ConfigDefaults.ITEM_EFFECT_OVERRIDE_VALIDATOR
                );

        SERVER_BUILDER.pop(); // items.effects

        SERVER_BUILDER.push("remainders");

        ENABLE_EGG_IMPACT_REMAINDER = SERVER_BUILDER
                .define("enable_egg_impact_remainder", true);

        CRAFTING_REMAINDERS = SERVER_BUILDER
                .defineListAllowEmpty("crafting_remainders",
                        ConfigDefaults.CRAFTING_REMAINDERS_DEFAULT,
                        () -> "input_item|remainder_item",
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop(); // items.remainders
        SERVER_BUILDER.pop(); // items

        SERVER_BUILDER.push("interactions");

        ENABLE_FILTER_INTERACTIONS = SERVER_BUILDER
                .define("enable_filter_interactions", true);

        ENABLE_PUMPKIN_PIE_PLACEMENT = SERVER_BUILDER
                .define("enable_pumpkin_pie_placement", false);

        FILTER_INTERACTIONS = SERVER_BUILDER
                .defineListAllowEmpty("filter_interactions",
                        ConfigDefaults.FILTER_INTERACTIONS_DEFAULT,
                        () -> "filter_item|offhand_item|filter_result|container_result",
                        ConfigDefaults.FILTER_INTERACTIONS_VALIDATOR
                );

        SERVER_BUILDER.push("handcraft");

        HANDCRAFTING_ALLOW_SINGLE = SERVER_BUILDER
                .define("allow_single_item", false);

        ENABLE_HANDCRAFTING = SERVER_BUILDER
                .define("enable_handcrafting", true);

        HANDCRAFTING_PARTICLES = SERVER_BUILDER
                .define("enable_particles", true);

        HANDCRAFTING_EXCLUDE = SERVER_BUILDER
                .defineListAllowEmpty("exclude",
                        ConfigDefaults.HANDCRAFTING_EXCLUDE_DEFAULT,
                        () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name",
                        obj -> obj instanceof String
                );

        HANDCRAFTING_FILTER = SERVER_BUILDER
                .defineListAllowEmpty("filter",
                        List.of(),
                        () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name",
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop(); // interactions.handcraft
        SERVER_BUILDER.pop(); // interactions

        SERVER_BUILDER.push("display");

        ENABLE_CUTTING_BOARD = SERVER_BUILDER
                .define("enable_cutting_board", true);

        ENABLE_GENERIC_PLATES = SERVER_BUILDER
                .define("enable_generic_plates", true);

        GENERIC_DISPLAY_EXCLUDE = SERVER_BUILDER
                .defineListAllowEmpty("exclude",
                        ConfigDefaults.GENERIC_DISPLAY_EXCLUDE_DEFAULT,
                        () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name",
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop(); // display

        SERVER_BUILDER.push("storage");

        SERVER_BUILDER.push("cloth_sack");

        CLOTH_SACK_ALLOW_FOOD = SERVER_BUILDER
                .define("cloth_sack_allow_food", false);

        CLOTH_SACK_EAT_FROM_ITEM = SERVER_BUILDER
                .define("cloth_sack_eat_from_item", false);

        CLOTH_SACK_INVENTORY_ENABLED = SERVER_BUILDER
                .define("cloth_sack_inventory", true);

        CLOTH_SACK_STACK = SERVER_BUILDER
                .define("cloth_sack_stack", true);

        CLOTH_SACK_EXCLUDE = SERVER_BUILDER
                .defineListAllowEmpty("cloth_sack_exclude",
                        List.of(
                                "item:createfood:cloth_sack"
                        ),
                        () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name",
                        obj -> obj instanceof String
                );

        CLOTH_SACK_FILTER = SERVER_BUILDER
                .defineListAllowEmpty("cloth_sack_filter",
                        List.of(),
                        () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name",
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("ration_box");

        RATION_BOX_ALLOW_FOOD = SERVER_BUILDER
                .define("ration_box_allow_food", true);

        RATION_BOX_EAT_FROM_ITEM = SERVER_BUILDER
                .define("ration_box_eat_from_item", true);

        RATION_BOX_INVENTORY_ENABLED = SERVER_BUILDER
                .define("ration_box_inventory", true);

        RATION_BOX_STACK = SERVER_BUILDER
                .define("ration_box_stack", false);

        RATION_BOX_EXCLUDE = SERVER_BUILDER
                .defineListAllowEmpty("ration_box_exclude",
                        List.of(),
                        () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name",
                        obj -> obj instanceof String
                );

        RATION_BOX_FILTER = SERVER_BUILDER
                .defineListAllowEmpty("ration_box_filter",
                        List.of(),
                        () -> "mod:mod_id,  item:mod_id:item_id,  tag:mod_id:tag_name",
                        obj -> obj instanceof String
                );

        SERVER_BUILDER.pop();

        SERVER_BUILDER.pop();
    }

    @Nullable
    public static ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId) {
        try { return ConfigLogic.getItemEffectOverride(itemId, categoryOrEffectId, ITEM_EFFECT_OVERRIDES.get()); }
        catch (IllegalStateException ignored) { return null; }
    }

    public static List<ItemEffectOverride> getItemOverrideEntries(String itemId) {
        try { return ConfigLogic.getItemOverrideEntries(itemId, ITEM_EFFECT_OVERRIDES.get()); }
        catch (IllegalStateException ignored) { return List.of(); }
    }

    @Nullable
    public static ItemNutritionOverride getItemNutritionOverride(String itemId) {
        try { return ConfigLogic.getItemNutritionOverride(itemId, ITEM_NUTRITION_OVERRIDES.get()); }
        catch (IllegalStateException ignored) { return null; }
    }

    @Nullable
    public static String getCategoryEffectOverride(String categoryName) {
        try { return ConfigLogic.getCategoryEffectOverride(categoryName, CATEGORY_EFFECT_OVERRIDES.get()); }
        catch (IllegalStateException ignored) { return null; }
    }

    public static boolean isItemEnabled(String itemId) {
        try { return ConfigLogic.isItemEnabled(itemId, HIDE_ITEMS.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isDisplayBlockEnabled(String blockId) {
        try { return ConfigLogic.isDisplayBlockEnabled(blockId, HIDE_ITEMS.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean matchesFilterList(ItemStack stack, List<? extends String> list) {
        return ConfigLogic.matchesFilterList(stack, list);
    }

    public static boolean isClothSackItemAllowed(ItemStack stack) {
        try { return ConfigLogic.isClothSackItemAllowed(stack, CLOTH_SACK_EXCLUDE.get(), CLOTH_SACK_FILTER.get(), CLOTH_SACK_ALLOW_FOOD.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isRationBoxItemAllowed(ItemStack stack) {
        try { return ConfigLogic.isRationBoxItemAllowed(stack, RATION_BOX_EXCLUDE.get(), RATION_BOX_FILTER.get(), RATION_BOX_ALLOW_FOOD.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isAlwaysDisplayUpright()         { try { return ALWAYS_DISPLAY_UPRIGHT.get();        } catch (IllegalStateException e) { return false; } }
    public static boolean isEnableCuttingBoard()           { try { return ENABLE_CUTTING_BOARD.get();           } catch (IllegalStateException e) { return true;  } }
    public static boolean isEnableGenericPlates()          { try { return ENABLE_GENERIC_PLATES.get();          } catch (IllegalStateException e) { return true;  } }

    public static boolean isGenericDisplayAllowed(ItemStack stack) {
        try { return !ConfigLogic.matchesFilterList(stack, GENERIC_DISPLAY_EXCLUDE.get()); }
        catch (IllegalStateException e) { return true; }
    }
    public static boolean isClothSackInventoryEnabled()   { try { return CLOTH_SACK_INVENTORY_ENABLED.get();  } catch (IllegalStateException e) { return true;  } }
    public static boolean isClothSackEatFromItem()        { try { return CLOTH_SACK_EAT_FROM_ITEM.get();      } catch (IllegalStateException e) { return false; } }
    public static boolean isClothSackStacking()           { try { return CLOTH_SACK_STACK.get();              } catch (IllegalStateException e) { return true;  } }
    public static boolean isRationBoxInventoryEnabled()   { try { return RATION_BOX_INVENTORY_ENABLED.get();  } catch (IllegalStateException e) { return true;  } }
    public static boolean isRationBoxEatFromItemEnabled() { try { return RATION_BOX_EAT_FROM_ITEM.get();      } catch (IllegalStateException e) { return true;  } }
    public static boolean isRationBoxStacking()           { try { return RATION_BOX_STACK.get();              } catch (IllegalStateException e) { return false; } }
    public static boolean isStorageTooltipIconsEnabled()  { try { return SHOW_STORAGE_TOOLTIP_ICONS.get();    } catch (IllegalStateException e) { return true;  } }
    public static boolean isSackBlockIconsEnabled()       { try { return SHOW_SACK_BLOCK_ICONS.get();         } catch (IllegalStateException e) { return true;  } }

    public static class ConfigScreen implements IConfigScreenFactory {
        @Override
        public @NotNull Screen createScreen(@NotNull ModContainer modContainer, @NotNull Screen parent) {
            return new ConfigurationScreen(modContainer, parent);
        }
    }
}