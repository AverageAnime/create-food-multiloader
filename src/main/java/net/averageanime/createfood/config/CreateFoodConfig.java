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

    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();

        Pair<Client, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        CLIENT_SPEC = clientPair.getRight();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
    }

    public static class Server {

        // ── General ───────────────────────────────────────────────────────────
        public final ForgeConfigSpec.BooleanValue enablePumpkinPiePlacement;

        // ── Display ───────────────────────────────────────────────────────────
        public final ForgeConfigSpec.BooleanValue enableGenericPlates;
        public final ForgeConfigSpec.BooleanValue enableCuttingBoard;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> genericDisplayExclude;

        // ── Remainders ────────────────────────────────────────────────────────
        public final ForgeConfigSpec.BooleanValue enableEggImpactRemainder;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> craftingRemainders;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> nutritionSaturation;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> categoryOverrides;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> itemOverrides;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> customItem;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> customBlock;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> customFluid;

        // ── Cloth Sack ────────────────────────────────────────────────────────
        public final ForgeConfigSpec.BooleanValue clothSackInventoryEnabled;
        public final ForgeConfigSpec.BooleanValue clothSackEatFromItem;
        public final ForgeConfigSpec.BooleanValue clothSackStack;
        public final ForgeConfigSpec.BooleanValue clothSackAllowFood;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> clothSackExclude;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> clothSackFilter;

        // ── Ration Box ────────────────────────────────────────────────────────
        public final ForgeConfigSpec.BooleanValue rationBoxInventoryEnabled;
        public final ForgeConfigSpec.BooleanValue rationBoxEatFromItem;
        public final ForgeConfigSpec.BooleanValue rationBoxStack;
        public final ForgeConfigSpec.BooleanValue rationBoxAllowFood;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> rationBoxExclude;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> rationBoxFilter;

        // ── Handcrafting ──────────────────────────────────────────────────────
        public final ForgeConfigSpec.BooleanValue enableHandcrafting;
        public final ForgeConfigSpec.BooleanValue handcraftingAllowSingle;
        public final ForgeConfigSpec.BooleanValue handcraftingParticles;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> handcraftExclude;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> handcraftingFilter;

        // ── Filter Interactions ───────────────────────────────────────────────
        public final ForgeConfigSpec.BooleanValue enableFilterInteractions;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> filterInteractions;

        Server(ForgeConfigSpec.Builder builder) {
            builder.push("general");

            enablePumpkinPiePlacement = builder
                    .comment("Allow the pumpkin pie to be placed as a block")
                    .define("enable_pumpkin_pie_placement", true);

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

            builder.push("cloth_sack");

            clothSackInventoryEnabled = builder
                    .comment("Enable the Cloth Sack inventory (right-click to open GUI)")
                    .define("inventory_enabled", true);

            clothSackEatFromItem = builder
                    .comment("Allow eating food directly from a held Cloth Sack")
                    .define("eat_from_item", false);

            clothSackStack = builder
                    .comment("Allow items to stack inside the Cloth Sack (up to 64)")
                    .define("stacking", true);

            clothSackAllowFood = builder
                    .comment("Allow any food item to be placed in the Cloth Sack without a filter")
                    .define("allow_food", false);

            clothSackExclude = builder
                    .comment("Items excluded from the Cloth Sack. Format: 'item:modid:item_id', 'mod:modid', or 'tag:tag_id'")
                    .defineList("exclude", List.of("item:createfood:cloth_sack"), e -> e instanceof String);

            clothSackFilter = builder
                    .comment("Whitelist of items allowed in the Cloth Sack. Empty = allow all (respecting exclude). Format: 'item:modid:item_id', 'mod:modid', or 'tag:tag_id'")
                    .defineList("filter", Collections.emptyList(), e -> e instanceof String);

            builder.pop();

            builder.push("ration_box");

            rationBoxInventoryEnabled = builder
                    .comment("Enable the Ration Box inventory (right-click to open GUI)")
                    .define("inventory_enabled", true);

            rationBoxEatFromItem = builder
                    .comment("Allow eating food directly from a held Ration Box")
                    .define("eat_from_item", true);

            rationBoxStack = builder
                    .comment("Allow items to stack inside the Ration Box (up to 64)")
                    .define("stacking", false);

            rationBoxAllowFood = builder
                    .comment("Allow any food item to be placed in the Ration Box without a filter")
                    .define("allow_food", true);

            rationBoxExclude = builder
                    .comment("Items excluded from the Ration Box. Format: 'item:modid:item_id', 'mod:modid', or 'tag:tag_id'")
                    .defineList("exclude", Collections.emptyList(), e -> e instanceof String);

            rationBoxFilter = builder
                    .comment("Whitelist of items allowed in the Ration Box. Empty = allow all (respecting exclude). Format: 'item:modid:item_id', 'mod:modid', or 'tag:tag_id'")
                    .defineList("filter", Collections.emptyList(), e -> e instanceof String);

            builder.pop();

            builder.push("handcrafting");

            enableHandcrafting = builder
                    .comment("Enable right-click handcrafting using main-hand + off-hand items")
                    .define("enable_handcrafting", true);

            handcraftingAllowSingle = builder
                    .comment("Allow single-ingredient handcrafting (main hand only)")
                    .define("allow_single", false);

            handcraftingParticles = builder
                    .comment("Show particle effects when handcrafting succeeds")
                    .define("particles", true);

            handcraftExclude = builder
                    .comment("Items or tags excluded from being handcrafted. Format: 'item:modid:item_id', 'mod:modid', or 'tag:tag_id'")
                    .defineList("exclude", List.of("item:createfood:egg_yolk", "tag:forge:tools"), e -> e instanceof String);

            handcraftingFilter = builder
                    .comment("Whitelist of items that can be handcrafted. Empty = allow all. Format: 'item:modid:item_id', 'mod:modid', or 'tag:tag_id'")
                    .defineList("filter", Collections.emptyList(), e -> e instanceof String);

            builder.pop();

            builder.push("filter_interactions");

            enableFilterInteractions = builder
                    .comment("Enable cloth filter right-click interactions")
                    .define("enable_filter_interactions", true);

            filterInteractions = builder
                    .comment("Cloth filter interactions. Format: 'filterItem|offHandItem|filterResult|containerResult' (use 'none' for empty hand)")
                    .defineList("interactions", List.of(
                            "createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle",
                            "createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk",
                            "createfood:cloth_filter_cacao_mass|minecraft:bucket|createfood:cloth_filter_pressed_cocoa|createfood:cacao_butter_bucket",
                            "createfood:cloth_filter_pressed_cocoa|none|createfood:cloth_filter|createfood:pressed_cocoa"
                    ), e -> e instanceof String);

            builder.pop();

            builder.push("display");

            enableGenericPlates = builder
                    .comment("Allow plates to display any item generically")
                    .define("enable_generic_plates", true);

            enableCuttingBoard = builder
                    .comment("Allow plates to act as a FD cutting board")
                    .define("enable_cutting_board", true);

            genericDisplayExclude = builder
                    .comment("Items/tags excluded from generic plate display. Format: 'item:modid:item_id', 'mod:modid', 'tag:tag_id'")
                    .defineList("exclude", List.of("tag:forge:tools"), e -> e instanceof String);

            builder.pop();

            builder.push("remainders");

            enableEggImpactRemainder = builder
                    .comment("Drop eggshell when thrown egg hits a block")
                    .define("enable_egg_impact_remainder", true);

            craftingRemainders = builder
                    .comment("Remainder items when specific items are used in crafting. Format: 'input_item|remainder_item'")
                    .defineList("crafting_remainders", List.of("minecraft:egg|createfood:eggshell"), e -> e instanceof String);

            builder.pop();
        }
    }

    public static class Client {
        public final ForgeConfigSpec.BooleanValue alwaysDisplayUpright;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> customDisplayBlock;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> hideItems;
        public final ForgeConfigSpec.BooleanValue requireShiftForTooltips;
        public final ForgeConfigSpec.BooleanValue showCompatibility;
        public final ForgeConfigSpec.BooleanValue showIngredients;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> customTooltips;
        public final ForgeConfigSpec.BooleanValue showSackBlockIcons;
        public final ForgeConfigSpec.BooleanValue showStorageTooltipIcons;

        Client(ForgeConfigSpec.Builder builder) {
            builder.push("display");

            alwaysDisplayUpright = builder
                    .comment("Always render items upright on generic plates")
                    .define("always_display_upright", false);

            customDisplayBlock = builder
                    .comment("Add vanilla/mod items to the display block system. Format: 'modid:item_id|display_type|max_stack[|height[|particles]]'")
                    .defineList("display_block", List.of(
                            "create:bar_of_chocolate|plate|6",
                            "create:builders_tea|bottle|1|10|true",
                            "create:sweet_roll|plate|4",
                            "farmersdelight:pumpkin_pie_slice|small_plate|1",
                            "minecraft:cake|plate|1",
                            "minecraft:pumpkin_pie|plate|1"
                    ), e -> e instanceof String);

            builder.pop();

            builder.push("items");

            hideItems = builder
                    .comment("Item IDs to hide from the creative tab (path only, no namespace)")
                    .defineList("hide_items", List.of(
                            "beef_bun_peanut_butter",
                            "beef_bun_peanut_butter_bacon",
                            "bread_slice_peanut_butter",
                            "caramel_popcorn",
                            "cinnamon_sweet_roll_base",
                            "coffee_toffee",
                            "coffee_toffee_fudge",
                            "corn_flour",
                            "corn_stick",
                            "dragon_bun",
                            "dragon_bun_crimson_fungus",
                            "dragon_bun_warped_fungus",
                            "dragon_burger",
                            "dragon_burger_crimson_fungus",
                            "dragon_burger_warped_fungus",
                            "dragon_patty",
                            "dried_coffee_beans",
                            "eggplant_bun",
                            "eggplant_bun_cheese",
                            "eggplant_bun_cheese_lettuce",
                            "eggplant_bun_cheese_lettuce_tomato",
                            "eggplant_bun_cheese_tomato",
                            "eggplant_bun_lettuce",
                            "eggplant_bun_lettuce_tomato",
                            "eggplant_bun_tomato",
                            "eggplant_burger",
                            "eggplant_burger_lettuce",
                            "eggplant_burger_tomato",
                            "eggplant_cheeseburger",
                            "eggplant_cheeseburger_lettuce",
                            "eggplant_cheeseburger_lettuce_tomato",
                            "eggplant_cheeseburger_tomato",
                            "endermite_meatball",
                            "endermite_meatball_sandwich",
                            "endermite_meatball_stick_1",
                            "endermite_meatball_stick_2",
                            "endermite_meatball_stick_3",
                            "espresso_powder",
                            "ground_endermite",
                            "hamburger_peanut_butter",
                            "hamburger_peanut_butter_bacon",
                            "marshmallow_coffee_toffee_fudge",
                            "minced_dragon",
                            "pasta_plate_eggplant",
                            "pasta_plate_endermite_meatballs",
                            "pasta_plate_endermite_meatballs_tomato_sauce",
                            "pasta_plate_strider_meatballs",
                            "pasta_plate_strider_meatballs_tomato_sauce",
                            "peanut_butter_apple_jam_sandwich",
                            "peanut_butter_chorus_fruit_jam_sandwich",
                            "peanut_butter_melon_jam_sandwich",
                            "pumpkin_pie_slice",
                            "raw_cinnamon_sweet_roll_base",
                            "raw_endermite_meatball",
                            "raw_flesh_cookie",
                            "raw_ginger_cookie",
                            "raw_green_tea_cookie",
                            "raw_soul_berry_cookie",
                            "raw_spider_eye_cookie",
                            "raw_strider_meatball",
                            "raw_sugar_cookie",
                            "raw_ube_cake_base",
                            "raw_ube_cookie",
                            "small_endermite_meatballs",
                            "small_strider_meatballs",
                            "strider_meatball",
                            "strider_meatball_sandwich",
                            "strider_meatball_stick_1",
                            "strider_meatball_stick_2",
                            "strider_meatball_stick_3",
                            "tortilla_chip_bowl",
                            "ube_cake_base",
                            "ube_cream_frosting_bottle",
                            "ube_cream_frosting_piping_bag",
                            "ube_cream_ube_cake",
                            "ube_cream_ube_cake_slice",
                            "ube_cream_ube_cupcake",
                            "ube_cupcake_base",
                            "ube_sugar_dough"
                    ), e -> e instanceof String);

            builder.push("tooltips");

            requireShiftForTooltips = builder
                    .define("require_shift", false);

            showCompatibility = builder
                    .define("show_compatibility", true);

            showIngredients = builder
                    .define("show_ingredients", true);

            customTooltips = builder
                    .defineList("custom_tooltips", List.of(
                            "create:chocolate_glazed_berries|chocolate",
                            "create:honeyed_apple|honey",
                            "create:sweet_roll|cream_frosting",
                            "culturaldelights:avocado_toast|avocado",
                            "culturaldelights:beef_burrito|beef,rice,avocado",
                            "culturaldelights:chicken_taco|chicken,cucumber,corn,tomato",
                            "culturaldelights:eggplant_burger|lettuce,tomato",
                            "culturaldelights:fish_taco|fish,lettuce,tomato",
                            "culturaldelights:mutton_sandwich|mutton,beetroot,fried_egg",
                            "culturaldelights:pork_wrap|pork,apple,lettuce",
                            "delightfulcreators:incomplete_chicken_sandwich|chicken",
                            "delightfulcreators:incomplete_pasta_with_meatballs|beef_meatballs",
                            "delightfulcreators:incomplete_pasta_with_mutton_chop|mutton",
                            "delightfulcreators:incomplete_squid_ink_pasta|squid_ink",
                            "displaydelight:ctd_plated_avocado_toast|avocado",
                            "displaydelight:ctd_plated_eggplant_burger|lettuce,tomato",
                            "displaydelight:ctd_plated_mutton_sandwich|mutton,fried_egg,beetroot",
                            "displaydelight:ctd_plated_pork_wrap|pork,apple,lettuce",
                            "displaydelight:ed_plated_berry_sweet_roll|cream_frosting,berry",
                            "displaydelight:ed_plated_glow_berry_jelly_sandwich|peanut_butter,glow_berry",
                            "displaydelight:ed_plated_glow_berry_sweet_roll|cream_frosting,glow_berry",
                            "displaydelight:ed_plated_peanut_butter_honey_sandwich|peanut_butter",
                            "displaydelight:ed_plated_sweet_berry_jelly_sandwich|peanut_butter,berry",
                            "displaydelight:ed_plated_sweet_roll|cream_frosting",
                            "displaydelight:mixed_salad|beetroot,tomato",
                            "displaydelight:pasta_with_meatballs|tomato_sauce,beef_meatballs",
                            "displaydelight:pasta_with_mutton_chop|tomato_sauce,mutton",
                            "displaydelight:plated_bacon_sandwich|bacon,lettuce,tomato",
                            "displaydelight:plated_chicken_sandwich|chicken,lettuce,carrot",
                            "displaydelight:plated_egg_sandwich|fried_egg",
                            "displaydelight:plated_hamburger|onion,lettuce,tomato",
                            "displaydelight:plated_kelp_roll|carrot",
                            "displaydelight:plated_mutton_wrap|mutton,onion,lettuce",
                            "displaydelight:small_plated_cake_slice|cream_frosting,berry",
                            "displaydelight:small_plated_kelp_roll_slice|carrot",
                            "displaydelight:squid_ink_pasta|squid_ink,fish",
                            "expandeddelight:berry_sweet_roll|berry",
                            "expandeddelight:glow_berry_jelly_sandwich|peanut_butter,glow_berry_jam",
                            "expandeddelight:glow_berry_sweet_roll|glow_berry",
                            "expandeddelight:peanut_butter_honey_sandwich|peanut_butter",
                            "expandeddelight:sweet_berry_jelly_sandwich|peanut_butter,berry_jam",
                            "farmersdelight:bacon_sandwich|bacon,lettuce,tomato",
                            "farmersdelight:cake_slice|cream_frosting,berry",
                            "farmersdelight:chicken_sandwich|chicken,lettuce,carrot",
                            "farmersdelight:dumplings|protein,onion",
                            "farmersdelight:egg_sandwich|fried_egg",
                            "farmersdelight:hamburger|onion,lettuce,tomato",
                            "farmersdelight:kelp_roll|carrot",
                            "farmersdelight:kelp_roll_slice|carrot",
                            "farmersdelight:mixed_salad|beetroot,tomato",
                            "farmersdelight:mutton_wrap|mutton,onion,lettuce",
                            "farmersdelight:pasta_with_meatballs|tomato_sauce,beef_meatballs",
                            "farmersdelight:pasta_with_mutton_chop|tomato_sauce,mutton",
                            "farmersdelight:squid_ink_pasta|squid_ink,fish",
                            "farmersdelight:sweet_berry_cookie|berry",
                            "minecraft:cake|cream_frosting,berry",
                            "minecraft:cookie|chocolate_chips"
                    ), e -> e instanceof String);

            builder.pop(); // items.tooltips
            builder.pop(); // items

            builder.push("storage");

            showSackBlockIcons = builder
                    .define("show_sack_block_icons", true);

            showStorageTooltipIcons = builder
                    .define("show_tooltip_icons", true);

            builder.pop();
        }
    }
}
