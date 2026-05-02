package dev.averageanime.fabric.block;

import dev.averageanime.fabric.block.type.fluid.FluidEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

import static dev.averageanime.fabric.CreateFood.LOGGER;

@SuppressWarnings("unused")
public class ModFluids {

    private static final List<FluidEntry> CONFIG_FLUIDS = new ArrayList<>();

    public static final FluidEntry APPLE_CREAM_FROSTING_FLUID = new FluidEntry("apple_cream_frosting").build();
    public static final FluidEntry APPLE_CUSTARD_FLUID = new FluidEntry("apple_custard").flow(1, 4).build();
    public static final FluidEntry APPLE_ICE_CREAM_FLUID = new FluidEntry("apple_ice_cream").build();
    public static final FluidEntry APPLE_JAM_FLUID = new FluidEntry("apple_jam").flow(2, 4).build();
    public static final FluidEntry APPLE_JUICE_FLUID = new FluidEntry("apple_juice").flow(3, 2).build();
    public static final FluidEntry APPLE_MILKSHAKE_FLUID = new FluidEntry("apple_milkshake").flow(2, 4).build();
    public static final FluidEntry APPLE_PIE_FILLING_FLUID = new FluidEntry("apple_pie_filling").build();
    public static final FluidEntry BERRY_CREAM_FROSTING_FLUID = new FluidEntry("berry_cream_frosting").build();
    public static final FluidEntry BERRY_CUSTARD_FLUID = new FluidEntry("berry_custard").flow(1, 4).build();
    public static final FluidEntry BERRY_ICE_CREAM_FLUID = new FluidEntry("berry_ice_cream").build();
    public static final FluidEntry BERRY_JAM_FLUID = new FluidEntry("berry_jam").flow(2, 4).build();
    public static final FluidEntry BERRY_JUICE_FLUID = new FluidEntry("berry_juice").flow(3, 2).build();
    public static final FluidEntry BERRY_MILKSHAKE_FLUID = new FluidEntry("berry_milkshake").flow(2, 4).build();
    public static final FluidEntry BERRY_PIE_FILLING_FLUID = new FluidEntry("berry_pie_filling").build();
    public static final FluidEntry BLACKSTRAP_MOLASSES_FLUID = new FluidEntry("blackstrap_molasses").build();
    public static final FluidEntry BLACK_GELATIN_MIX_FLUID = new FluidEntry("black_gelatin_mix").build();
    public static final FluidEntry BLUE_GELATIN_MIX_FLUID = new FluidEntry("blue_gelatin_mix").build();
    public static final FluidEntry BREAD_PUDDING_FLUID = new FluidEntry("bread_pudding").build();
    public static final FluidEntry BROWN_GELATIN_MIX_FLUID = new FluidEntry("brown_gelatin_mix").build();
    public static final FluidEntry BUTTERSCOTCH_FLUID = new FluidEntry("butterscotch").build();
    public static final FluidEntry BUTTERSCOTCH_FUDGE_FLUID = new FluidEntry("butterscotch_fudge").build();
    public static final FluidEntry CACAO_BUTTER_FLUID = new FluidEntry("cacao_butter").build();
    public static final FluidEntry CACAO_MASS_FLUID = new FluidEntry("cacao_mass").build();
    public static final FluidEntry CAKE_BATTER_FLUID = new FluidEntry("cake_batter").flow(2, 4).build();
    public static final FluidEntry CANE_SYRUP_FLUID = new FluidEntry("cane_syrup").build();
    public static final FluidEntry CARAMEL_FLUID = new FluidEntry("caramel").build();
    public static final FluidEntry CARAMEL_FUDGE_FLUID = new FluidEntry("caramel_fudge").build();
    public static final FluidEntry CHEESECAKE_FILLING_FLUID = new FluidEntry("cheesecake_filling").build();
    public static final FluidEntry CHOCOLATE_CAKE_BATTER_FLUID = new FluidEntry("chocolate_cake_batter").flow(2, 4).build();
    public static final FluidEntry CHOCOLATE_CREAM_FROSTING_FLUID = new FluidEntry("chocolate_cream_frosting").build();
    public static final FluidEntry CHOCOLATE_CUSTARD_FLUID = new FluidEntry("chocolate_custard").flow(1, 4).build();
    public static final FluidEntry CHOCOLATE_FUDGE_FLUID = new FluidEntry("chocolate_fudge").build();
    public static final FluidEntry CHOCOLATE_ICE_CREAM_FLUID = new FluidEntry("chocolate_ice_cream").build();
    public static final FluidEntry CHOCOLATE_MILKSHAKE_FLUID = new FluidEntry("chocolate_milkshake").flow(2, 4).build();
    public static final FluidEntry CHOCOLATE_MILK_FLUID = new FluidEntry("chocolate_milk").build();
    public static final FluidEntry CHORUS_FRUIT_CREAM_FROSTING_FLUID = new FluidEntry("chorus_fruit_cream_frosting").build();
    public static final FluidEntry CHORUS_FRUIT_CUSTARD_FLUID = new FluidEntry("chorus_fruit_custard").flow(1, 4).build();
    public static final FluidEntry CHORUS_FRUIT_ICE_CREAM_FLUID = new FluidEntry("chorus_fruit_ice_cream").build();
    public static final FluidEntry CHORUS_FRUIT_JAM_FLUID = new FluidEntry("chorus_fruit_jam").flow(2, 4).build();
    public static final FluidEntry CHORUS_FRUIT_JUICE_FLUID = new FluidEntry("chorus_fruit_juice").flow(3, 2).build();
    public static final FluidEntry CHORUS_FRUIT_MILKSHAKE_FLUID = new FluidEntry("chorus_fruit_milkshake").flow(2, 4).build();
    public static final FluidEntry CHORUS_FRUIT_PIE_FILLING_FLUID = new FluidEntry("chorus_fruit_pie_filling").build();
    public static final FluidEntry COFFEE_TOFFEE_FLUID = new FluidEntry("coffee_toffee").build();
    public static final FluidEntry COFFEE_TOFFEE_FUDGE_FLUID = new FluidEntry("coffee_toffee_fudge").build();
    public static final FluidEntry CONDENSED_MILK_FLUID = new FluidEntry("condensed_milk").build();
    public static final FluidEntry CREAM_CHEESE_FLUID = new FluidEntry("cream_cheese").build();
    public static final FluidEntry CREAM_FROSTING_FLUID = new FluidEntry("cream_frosting").build();
    public static final FluidEntry CREAM_PIE_FILLING_FLUID = new FluidEntry("cream_pie_filling").build();
    public static final FluidEntry CUSTARD_FLUID = new FluidEntry("custard").flow(1, 4).build();
    public static final FluidEntry CYAN_GELATIN_MIX_FLUID = new FluidEntry("cyan_gelatin_mix").build();
    public static final FluidEntry DARK_CHOCOLATE_FLUID = new FluidEntry("dark_chocolate").build();
    public static final FluidEntry DARK_CHOCOLATE_FUDGE_FLUID = new FluidEntry("dark_chocolate_fudge").build();
    public static final FluidEntry EGG_FLUID = new FluidEntry("egg").build();
    public static final FluidEntry EGG_WHITES_FLUID = new FluidEntry("egg_whites").build();
    public static final FluidEntry FISH_CHOWDER_FLUID = new FluidEntry("fish_chowder").build();
    public static final FluidEntry FRUIT_SMOOTHIE_FLUID = new FluidEntry("fruit_smoothie").build();
    public static final FluidEntry GELATIN_MIX_FLUID = new FluidEntry("gelatin_mix").build();
    public static final FluidEntry GLOW_BERRY_CREAM_FROSTING_FLUID = new FluidEntry("glow_berry_cream_frosting").build();
    public static final FluidEntry GLOW_BERRY_ICE_CREAM_FLUID = new FluidEntry("glow_berry_ice_cream").build();
    public static final FluidEntry GLOW_BERRY_JAM_FLUID = new FluidEntry("glow_berry_jam").flow(2, 4).build();
    public static final FluidEntry GLOW_BERRY_JUICE_FLUID = new FluidEntry("glow_berry_juice").flow(3, 2).build();
    public static final FluidEntry GLOW_BERRY_MILKSHAKE_FLUID = new FluidEntry("glow_berry_milkshake").flow(2, 4).build();
    public static final FluidEntry GLOW_BERRY_PIE_FILLING_FLUID = new FluidEntry("glow_berry_pie_filling").build();
    public static final FluidEntry GRAY_GELATIN_MIX_FLUID = new FluidEntry("gray_gelatin_mix").build();
    public static final FluidEntry GREEN_GELATIN_MIX_FLUID = new FluidEntry("green_gelatin_mix").build();
    public static final FluidEntry HEAVY_CREAM_FLUID = new FluidEntry("heavy_cream").build();
    public static final FluidEntry HOT_CHOCOLATE_FLUID = new FluidEntry("hot_chocolate").build();
    public static final FluidEntry HOT_DARK_CHOCOLATE_FLUID = new FluidEntry("hot_dark_chocolate").build();
    public static final FluidEntry HOT_WHITE_CHOCOLATE_FLUID = new FluidEntry("hot_white_chocolate").build();
    public static final FluidEntry ICE_CREAM_FLUID = new FluidEntry("ice_cream").build();
    public static final FluidEntry KELP_SOUP_FLUID = new FluidEntry("kelp_soup").build();
    public static final FluidEntry LEATHER_SOUP_FLUID = new FluidEntry("leather_soup").build();
    public static final FluidEntry LIGHT_BLUE_GELATIN_MIX_FLUID = new FluidEntry("light_blue_gelatin_mix").build();
    public static final FluidEntry LIGHT_GRAY_GELATIN_MIX_FLUID = new FluidEntry("light_gray_gelatin_mix").build();
    public static final FluidEntry LIME_GELATIN_MIX_FLUID = new FluidEntry("lime_gelatin_mix").build();
    public static final FluidEntry LIQUID_CHEESE_FLUID = new FluidEntry("liquid_cheese").build();
    public static final FluidEntry MAGENTA_GELATIN_MIX_FLUID = new FluidEntry("magenta_gelatin_mix").build();
    public static final FluidEntry MELON_CREAM_FROSTING_FLUID = new FluidEntry("melon_cream_frosting").build();
    public static final FluidEntry MELON_CUSTARD_FLUID = new FluidEntry("melon_custard").flow(1, 4).build();
    public static final FluidEntry MELON_ICE_CREAM_FLUID = new FluidEntry("melon_ice_cream").build();
    public static final FluidEntry MELON_JAM_FLUID = new FluidEntry("melon_jam").flow(2, 4).build();
    public static final FluidEntry MELON_MILKSHAKE_FLUID = new FluidEntry("melon_milkshake").flow(2, 4).build();
    public static final FluidEntry MERINGUE_FLUID = new FluidEntry("meringue").flow(1, 4).build();
    public static final FluidEntry MILKSHAKE_FLUID = new FluidEntry("milkshake").flow(2, 4).build();
    public static final FluidEntry MOLASSES_FLUID = new FluidEntry("molasses").build();
    public static final FluidEntry MUFFIN_BATTER_FLUID = new FluidEntry("muffin_batter").flow(2, 4).build();
    public static final FluidEntry MUSHROOM_CREAM_SOUP_FLUID = new FluidEntry("mushroom_cream_soup").build();
    public static final FluidEntry MUTTON_STEW_FLUID = new FluidEntry("mutton_stew").build();
    public static final FluidEntry ORANGE_GELATIN_MIX_FLUID = new FluidEntry("orange_gelatin_mix").build();
    public static final FluidEntry PINK_GELATIN_MIX_FLUID = new FluidEntry("pink_gelatin_mix").build();
    public static final FluidEntry PORK_STEW_FLUID = new FluidEntry("pork_stew").build();
    public static final FluidEntry POTATO_CREAM_SOUP_FLUID = new FluidEntry("potato_cream_soup").build();
    public static final FluidEntry PUMPKIN_CUSTARD_FLUID = new FluidEntry("pumpkin_custard").flow(1, 4).build();
    public static final FluidEntry PUMPKIN_PIE_FILLING_FLUID = new FluidEntry("pumpkin_pie_filling").build();
    public static final FluidEntry PUMPKIN_PUREE_FLUID = new FluidEntry("pumpkin_puree").build();
    public static final FluidEntry PURPLE_GELATIN_MIX_FLUID = new FluidEntry("purple_gelatin_mix").build();
    public static final FluidEntry RED_GELATIN_MIX_FLUID = new FluidEntry("red_gelatin_mix").build();
    public static final FluidEntry RICE_PUDDING_FLUID = new FluidEntry("rice_pudding").build();
    public static final FluidEntry SLIME_FLUID = new FluidEntry("slime").build();
    public static final FluidEntry SOUR_CREAM_FLUID = new FluidEntry("sour_cream").build();
    public static final FluidEntry SQUID_INK_FLUID = new FluidEntry("squid_ink").build();
    public static final FluidEntry SUGAR_CANE_JUICE_FLUID = new FluidEntry("sugar_cane_juice").flow(3, 2).build();
    public static final FluidEntry TACO_SAUCE_FLUID = new FluidEntry("taco_sauce").flow(2, 4).build();
    public static final FluidEntry TOFFEE_FLUID = new FluidEntry("toffee").build();
    public static final FluidEntry TOFFEE_FUDGE_FLUID = new FluidEntry("toffee_fudge").build();
    public static final FluidEntry TOMATO_CREAM_SOUP_FLUID = new FluidEntry("tomato_cream_soup").build();
    public static final FluidEntry UBE_CAKE_BATTER_FLUID = new FluidEntry("ube_cake_batter").flow(2, 4).build();
    public static final FluidEntry UBE_CREAM_FROSTING_FLUID = new FluidEntry("ube_cream_frosting").build();
    public static final FluidEntry VEGETABLE_OIL_FLUID = new FluidEntry("vegetable_oil").build();
    public static final FluidEntry VINEGAR_FLUID = new FluidEntry("vinegar").build();
    public static final FluidEntry WAFFLE_BATTER_FLUID = new FluidEntry("waffle_batter").build();
    public static final FluidEntry WHITE_CHOCOLATE_FLUID = new FluidEntry("white_chocolate").build();
    public static final FluidEntry WHITE_CHOCOLATE_FUDGE_FLUID = new FluidEntry("white_chocolate_fudge").build();
    public static final FluidEntry YELLOW_GELATIN_MIX_FLUID = new FluidEntry("yellow_gelatin_mix").build();
    public static final FluidEntry YOGURT_FLUID = new FluidEntry("yogurt").build();

    public static void registerConfigFluids() {
        var configFile = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve("createfood-client.toml");
        if (!java.nio.file.Files.exists(configFile)) return;
        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("fluids.fluid", List.of());
            for (String entry : entries) {
                String[] p = entry.split("\\|");
                if (p.length != 3) {
                    LOGGER.warn("Create: Food - Skipping invalid custom_fluid entry (expected name|slope|level): {}", entry);
                    continue;
                }
                String name = p[0];
                int slope, level;
                try {
                    slope = Integer.parseInt(p[1]);
                    level = Integer.parseInt(p[2]);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Create: Food - Skipping custom_fluid entry with non-integer flow values: {}", entry);
                    continue;
                }
                FluidEntry fluid = new FluidEntry(name).flow(slope, level).build();
                CONFIG_FLUIDS.add(fluid);
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_fluid from config", e);
        }
    }

    public static void init() {
        registerConfigFluids();
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientRendering() {
        APPLE_CREAM_FROSTING_FLUID.registerClientRendering();
        APPLE_CUSTARD_FLUID.registerClientRendering();
        APPLE_ICE_CREAM_FLUID.registerClientRendering();
        APPLE_JAM_FLUID.registerClientRendering();
        APPLE_JUICE_FLUID.registerClientRendering();
        APPLE_MILKSHAKE_FLUID.registerClientRendering();
        APPLE_PIE_FILLING_FLUID.registerClientRendering();
        BERRY_CREAM_FROSTING_FLUID.registerClientRendering();
        BERRY_CUSTARD_FLUID.registerClientRendering();
        BERRY_ICE_CREAM_FLUID.registerClientRendering();
        BERRY_JAM_FLUID.registerClientRendering();
        BERRY_JUICE_FLUID.registerClientRendering();
        BERRY_MILKSHAKE_FLUID.registerClientRendering();
        BERRY_PIE_FILLING_FLUID.registerClientRendering();
        BLACKSTRAP_MOLASSES_FLUID.registerClientRendering();
        BLACK_GELATIN_MIX_FLUID.registerClientRendering();
        BLUE_GELATIN_MIX_FLUID.registerClientRendering();
        BREAD_PUDDING_FLUID.registerClientRendering();
        BROWN_GELATIN_MIX_FLUID.registerClientRendering();
        BUTTERSCOTCH_FLUID.registerClientRendering();
        BUTTERSCOTCH_FUDGE_FLUID.registerClientRendering();
        CACAO_BUTTER_FLUID.registerClientRendering();
        CACAO_MASS_FLUID.registerClientRendering();
        CAKE_BATTER_FLUID.registerClientRendering();
        CANE_SYRUP_FLUID.registerClientRendering();
        CARAMEL_FLUID.registerClientRendering();
        CARAMEL_FUDGE_FLUID.registerClientRendering();
        CHEESECAKE_FILLING_FLUID.registerClientRendering();
        CHOCOLATE_CAKE_BATTER_FLUID.registerClientRendering();
        CHOCOLATE_CREAM_FROSTING_FLUID.registerClientRendering();
        CHOCOLATE_CUSTARD_FLUID.registerClientRendering();
        CHOCOLATE_FUDGE_FLUID.registerClientRendering();
        CHOCOLATE_ICE_CREAM_FLUID.registerClientRendering();
        CHOCOLATE_MILKSHAKE_FLUID.registerClientRendering();
        CHOCOLATE_MILK_FLUID.registerClientRendering();
        CHORUS_FRUIT_CREAM_FROSTING_FLUID.registerClientRendering();
        CHORUS_FRUIT_CUSTARD_FLUID.registerClientRendering();
        CHORUS_FRUIT_ICE_CREAM_FLUID.registerClientRendering();
        CHORUS_FRUIT_JAM_FLUID.registerClientRendering();
        CHORUS_FRUIT_JUICE_FLUID.registerClientRendering();
        CHORUS_FRUIT_MILKSHAKE_FLUID.registerClientRendering();
        CHORUS_FRUIT_PIE_FILLING_FLUID.registerClientRendering();
        COFFEE_TOFFEE_FLUID.registerClientRendering();
        COFFEE_TOFFEE_FUDGE_FLUID.registerClientRendering();
        CONDENSED_MILK_FLUID.registerClientRendering();
        CREAM_CHEESE_FLUID.registerClientRendering();
        CREAM_FROSTING_FLUID.registerClientRendering();
        CREAM_PIE_FILLING_FLUID.registerClientRendering();
        CUSTARD_FLUID.registerClientRendering();
        CYAN_GELATIN_MIX_FLUID.registerClientRendering();
        DARK_CHOCOLATE_FLUID.registerClientRendering();
        DARK_CHOCOLATE_FUDGE_FLUID.registerClientRendering();
        EGG_FLUID.registerClientRendering();
        EGG_WHITES_FLUID.registerClientRendering();
        FISH_CHOWDER_FLUID.registerClientRendering();
        FRUIT_SMOOTHIE_FLUID.registerClientRendering();
        GELATIN_MIX_FLUID.registerClientRendering();
        GLOW_BERRY_CREAM_FROSTING_FLUID.registerClientRendering();
        GLOW_BERRY_ICE_CREAM_FLUID.registerClientRendering();
        GLOW_BERRY_JAM_FLUID.registerClientRendering();
        GLOW_BERRY_JUICE_FLUID.registerClientRendering();
        GLOW_BERRY_MILKSHAKE_FLUID.registerClientRendering();
        GLOW_BERRY_PIE_FILLING_FLUID.registerClientRendering();
        GRAY_GELATIN_MIX_FLUID.registerClientRendering();
        GREEN_GELATIN_MIX_FLUID.registerClientRendering();
        HEAVY_CREAM_FLUID.registerClientRendering();
        HOT_CHOCOLATE_FLUID.registerClientRendering();
        HOT_DARK_CHOCOLATE_FLUID.registerClientRendering();
        HOT_WHITE_CHOCOLATE_FLUID.registerClientRendering();
        ICE_CREAM_FLUID.registerClientRendering();
        KELP_SOUP_FLUID.registerClientRendering();
        LEATHER_SOUP_FLUID.registerClientRendering();
        LIGHT_BLUE_GELATIN_MIX_FLUID.registerClientRendering();
        LIGHT_GRAY_GELATIN_MIX_FLUID.registerClientRendering();
        LIME_GELATIN_MIX_FLUID.registerClientRendering();
        LIQUID_CHEESE_FLUID.registerClientRendering();
        MAGENTA_GELATIN_MIX_FLUID.registerClientRendering();
        MELON_CREAM_FROSTING_FLUID.registerClientRendering();
        MELON_CUSTARD_FLUID.registerClientRendering();
        MELON_ICE_CREAM_FLUID.registerClientRendering();
        MELON_JAM_FLUID.registerClientRendering();
        MELON_MILKSHAKE_FLUID.registerClientRendering();
        MERINGUE_FLUID.registerClientRendering();
        MILKSHAKE_FLUID.registerClientRendering();
        MOLASSES_FLUID.registerClientRendering();
        MUFFIN_BATTER_FLUID.registerClientRendering();
        MUSHROOM_CREAM_SOUP_FLUID.registerClientRendering();
        MUTTON_STEW_FLUID.registerClientRendering();
        ORANGE_GELATIN_MIX_FLUID.registerClientRendering();
        PINK_GELATIN_MIX_FLUID.registerClientRendering();
        PORK_STEW_FLUID.registerClientRendering();
        POTATO_CREAM_SOUP_FLUID.registerClientRendering();
        PUMPKIN_CUSTARD_FLUID.registerClientRendering();
        PUMPKIN_PIE_FILLING_FLUID.registerClientRendering();
        PUMPKIN_PUREE_FLUID.registerClientRendering();
        PURPLE_GELATIN_MIX_FLUID.registerClientRendering();
        RED_GELATIN_MIX_FLUID.registerClientRendering();
        RICE_PUDDING_FLUID.registerClientRendering();
        SLIME_FLUID.registerClientRendering();
        SOUR_CREAM_FLUID.registerClientRendering();
        SQUID_INK_FLUID.registerClientRendering();
        SUGAR_CANE_JUICE_FLUID.registerClientRendering();
        TACO_SAUCE_FLUID.registerClientRendering();
        TOFFEE_FLUID.registerClientRendering();
        TOFFEE_FUDGE_FLUID.registerClientRendering();
        TOMATO_CREAM_SOUP_FLUID.registerClientRendering();
        UBE_CAKE_BATTER_FLUID.registerClientRendering();
        UBE_CREAM_FROSTING_FLUID.registerClientRendering();
        VEGETABLE_OIL_FLUID.registerClientRendering();
        VINEGAR_FLUID.registerClientRendering();
        WAFFLE_BATTER_FLUID.registerClientRendering();
        WHITE_CHOCOLATE_FLUID.registerClientRendering();
        WHITE_CHOCOLATE_FUDGE_FLUID.registerClientRendering();
        YELLOW_GELATIN_MIX_FLUID.registerClientRendering();
        YOGURT_FLUID.registerClientRendering();
        for (FluidEntry configFluid : CONFIG_FLUIDS) {
            configFluid.registerClientRendering();
        }
    }
}
