package dev.averageanime.neoforge.block;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.type.fluid.FluidEntry;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

import static dev.averageanime.neoforge.CreateFood.LOGGER;

@SuppressWarnings("unused")
public class ModFluids {

    public static final DeferredRegister<net.minecraft.world.level.material.Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, CommonClass.MOD_ID);
    public static final DeferredRegister<net.neoforged.neoforge.fluids.FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, CommonClass.MOD_ID);

    public static final FluidEntry.FluidType APPLE_CREAM_FROSTING_FLUID = new FluidEntry("apple_cream_frosting").build();
    public static final FluidEntry.FluidType APPLE_CUSTARD_FLUID = new FluidEntry("apple_custard").flow(1, 4).build();
    public static final FluidEntry.FluidType APPLE_ICE_CREAM_FLUID = new FluidEntry("apple_ice_cream").build();
    public static final FluidEntry.FluidType APPLE_JAM_FLUID = new FluidEntry("apple_jam").flow(2, 4).build();
    public static final FluidEntry.FluidType APPLE_JUICE_FLUID = new FluidEntry("apple_juice").flow(3, 2).build();
    public static final FluidEntry.FluidType APPLE_MILKSHAKE_FLUID = new FluidEntry("apple_milkshake").flow(2, 4).build();
    public static final FluidEntry.FluidType APPLE_PIE_FILLING_FLUID = new FluidEntry("apple_pie_filling").build();
    public static final FluidEntry.FluidType BERRY_CREAM_FROSTING_FLUID = new FluidEntry("berry_cream_frosting").build();
    public static final FluidEntry.FluidType BERRY_CUSTARD_FLUID = new FluidEntry("berry_custard").flow(1, 4).build();
    public static final FluidEntry.FluidType BERRY_ICE_CREAM_FLUID = new FluidEntry("berry_ice_cream").build();
    public static final FluidEntry.FluidType BERRY_JAM_FLUID = new FluidEntry("berry_jam").flow(2, 4).build();
    public static final FluidEntry.FluidType BERRY_JUICE_FLUID = new FluidEntry("berry_juice").flow(3, 2).build();
    public static final FluidEntry.FluidType BERRY_MILKSHAKE_FLUID = new FluidEntry("berry_milkshake").flow(2, 4).build();
    public static final FluidEntry.FluidType BERRY_PIE_FILLING_FLUID = new FluidEntry("berry_pie_filling").build();
    public static final FluidEntry.FluidType BLACKSTRAP_MOLASSES_FLUID = new FluidEntry("blackstrap_molasses").build();
    public static final FluidEntry.FluidType BLACK_GELATIN_MIX_FLUID = new FluidEntry("black_gelatin_mix").build();
    public static final FluidEntry.FluidType BLUE_GELATIN_MIX_FLUID = new FluidEntry("blue_gelatin_mix").build();
    public static final FluidEntry.FluidType BREAD_PUDDING_FLUID = new FluidEntry("bread_pudding").build();
    public static final FluidEntry.FluidType BROWN_GELATIN_MIX_FLUID = new FluidEntry("brown_gelatin_mix").build();
    public static final FluidEntry.FluidType BUTTERSCOTCH_FLUID = new FluidEntry("butterscotch").build();
    public static final FluidEntry.FluidType BUTTERSCOTCH_FUDGE_FLUID = new FluidEntry("butterscotch_fudge").build();
    public static final FluidEntry.FluidType CACAO_BUTTER_FLUID = new FluidEntry("cacao_butter").build();
    public static final FluidEntry.FluidType CACAO_MASS_FLUID = new FluidEntry("cacao_mass").build();
    public static final FluidEntry.FluidType CAKE_BATTER_FLUID = new FluidEntry("cake_batter").flow(2, 4).build();
    public static final FluidEntry.FluidType CANE_SYRUP_FLUID = new FluidEntry("cane_syrup").build();
    public static final FluidEntry.FluidType CARAMEL_FLUID = new FluidEntry("caramel").build();
    public static final FluidEntry.FluidType CARAMEL_FUDGE_FLUID = new FluidEntry("caramel_fudge").build();
    public static final FluidEntry.FluidType CHEESECAKE_FILLING_FLUID = new FluidEntry("cheesecake_filling").build();
    public static final FluidEntry.FluidType CHOCOLATE_CAKE_BATTER_FLUID = new FluidEntry("chocolate_cake_batter").flow(2, 4).build();
    public static final FluidEntry.FluidType CHOCOLATE_CREAM_FROSTING_FLUID = new FluidEntry("chocolate_cream_frosting").build();
    public static final FluidEntry.FluidType CHOCOLATE_CUSTARD_FLUID = new FluidEntry("chocolate_custard").flow(1, 4).build();
    public static final FluidEntry.FluidType CHOCOLATE_FUDGE_FLUID = new FluidEntry("chocolate_fudge").build();
    public static final FluidEntry.FluidType CHOCOLATE_ICE_CREAM_FLUID = new FluidEntry("chocolate_ice_cream").build();
    public static final FluidEntry.FluidType CHOCOLATE_MILKSHAKE_FLUID = new FluidEntry("chocolate_milkshake").flow(2, 4).build();
    public static final FluidEntry.FluidType CHOCOLATE_MILK_FLUID = new FluidEntry("chocolate_milk").build();
    public static final FluidEntry.FluidType CHORUS_FRUIT_CREAM_FROSTING_FLUID = new FluidEntry("chorus_fruit_cream_frosting").build();
    public static final FluidEntry.FluidType CHORUS_FRUIT_CUSTARD_FLUID = new FluidEntry("chorus_fruit_custard").flow(1, 4).build();
    public static final FluidEntry.FluidType CHORUS_FRUIT_ICE_CREAM_FLUID = new FluidEntry("chorus_fruit_ice_cream").build();
    public static final FluidEntry.FluidType CHORUS_FRUIT_JAM_FLUID = new FluidEntry("chorus_fruit_jam").flow(2, 4).build();
    public static final FluidEntry.FluidType CHORUS_FRUIT_JUICE_FLUID = new FluidEntry("chorus_fruit_juice").flow(3, 2).build();
    public static final FluidEntry.FluidType CHORUS_FRUIT_MILKSHAKE_FLUID = new FluidEntry("chorus_fruit_milkshake").flow(2, 4).build();
    public static final FluidEntry.FluidType CHORUS_FRUIT_PIE_FILLING_FLUID = new FluidEntry("chorus_fruit_pie_filling").build();
    public static final FluidEntry.FluidType COFFEE_TOFFEE_FLUID = new FluidEntry("coffee_toffee").build();
    public static final FluidEntry.FluidType COFFEE_TOFFEE_FUDGE_FLUID = new FluidEntry("coffee_toffee_fudge").build();
    public static final FluidEntry.FluidType CONDENSED_MILK_FLUID = new FluidEntry("condensed_milk").build();
    public static final FluidEntry.FluidType CREAM_CHEESE_FLUID = new FluidEntry("cream_cheese").build();
    public static final FluidEntry.FluidType CREAM_FROSTING_FLUID = new FluidEntry("cream_frosting").build();
    public static final FluidEntry.FluidType CREAM_PIE_FILLING_FLUID = new FluidEntry("cream_pie_filling").build();
    public static final FluidEntry.FluidType CUSTARD_FLUID = new FluidEntry("custard").flow(1, 4).build();
    public static final FluidEntry.FluidType CYAN_GELATIN_MIX_FLUID = new FluidEntry("cyan_gelatin_mix").build();
    public static final FluidEntry.FluidType DARK_CHOCOLATE_FLUID = new FluidEntry("dark_chocolate").build();
    public static final FluidEntry.FluidType DARK_CHOCOLATE_FUDGE_FLUID = new FluidEntry("dark_chocolate_fudge").build();
    public static final FluidEntry.FluidType EGG_FLUID = new FluidEntry("egg").build();
    public static final FluidEntry.FluidType EGG_WHITES_FLUID = new FluidEntry("egg_whites").build();
    public static final FluidEntry.FluidType FISH_CHOWDER_FLUID = new FluidEntry("fish_chowder").build();
    public static final FluidEntry.FluidType FRUIT_SMOOTHIE_FLUID = new FluidEntry("fruit_smoothie").build();
    public static final FluidEntry.FluidType GELATIN_MIX_FLUID = new FluidEntry("gelatin_mix").build();
    public static final FluidEntry.FluidType GLOW_BERRY_CREAM_FROSTING_FLUID = new FluidEntry("glow_berry_cream_frosting").build();
    public static final FluidEntry.FluidType GLOW_BERRY_ICE_CREAM_FLUID = new FluidEntry("glow_berry_ice_cream").build();
    public static final FluidEntry.FluidType GLOW_BERRY_JAM_FLUID = new FluidEntry("glow_berry_jam").flow(2, 4).build();
    public static final FluidEntry.FluidType GLOW_BERRY_JUICE_FLUID = new FluidEntry("glow_berry_juice").flow(3, 2).build();
    public static final FluidEntry.FluidType GLOW_BERRY_MILKSHAKE_FLUID = new FluidEntry("glow_berry_milkshake").flow(2, 4).build();
    public static final FluidEntry.FluidType GLOW_BERRY_PIE_FILLING_FLUID = new FluidEntry("glow_berry_pie_filling").build();
    public static final FluidEntry.FluidType GRAY_GELATIN_MIX_FLUID = new FluidEntry("gray_gelatin_mix").build();
    public static final FluidEntry.FluidType GREEN_GELATIN_MIX_FLUID = new FluidEntry("green_gelatin_mix").build();
    public static final FluidEntry.FluidType HEAVY_CREAM_FLUID = new FluidEntry("heavy_cream").build();
    public static final FluidEntry.FluidType HOT_CHOCOLATE_FLUID = new FluidEntry("hot_chocolate").build();
    public static final FluidEntry.FluidType HOT_DARK_CHOCOLATE_FLUID = new FluidEntry("hot_dark_chocolate").build();
    public static final FluidEntry.FluidType HOT_WHITE_CHOCOLATE_FLUID = new FluidEntry("hot_white_chocolate").build();
    public static final FluidEntry.FluidType ICE_CREAM_FLUID = new FluidEntry("ice_cream").build();
    public static final FluidEntry.FluidType KELP_SOUP_FLUID = new FluidEntry("kelp_soup").build();
    public static final FluidEntry.FluidType LEATHER_SOUP_FLUID = new FluidEntry("leather_soup").build();
    public static final FluidEntry.FluidType LIGHT_BLUE_GELATIN_MIX_FLUID = new FluidEntry("light_blue_gelatin_mix").build();
    public static final FluidEntry.FluidType LIGHT_GRAY_GELATIN_MIX_FLUID = new FluidEntry("light_gray_gelatin_mix").build();
    public static final FluidEntry.FluidType LIME_GELATIN_MIX_FLUID = new FluidEntry("lime_gelatin_mix").build();
    public static final FluidEntry.FluidType LIQUID_CHEESE_FLUID = new FluidEntry("liquid_cheese").build();
    public static final FluidEntry.FluidType MAGENTA_GELATIN_MIX_FLUID = new FluidEntry("magenta_gelatin_mix").build();
    public static final FluidEntry.FluidType MELON_CREAM_FROSTING_FLUID = new FluidEntry("melon_cream_frosting").build();
    public static final FluidEntry.FluidType MELON_CUSTARD_FLUID = new FluidEntry("melon_custard").flow(1, 4).build();
    public static final FluidEntry.FluidType MELON_ICE_CREAM_FLUID = new FluidEntry("melon_ice_cream").build();
    public static final FluidEntry.FluidType MELON_JAM_FLUID = new FluidEntry("melon_jam").flow(2, 4).build();
    public static final FluidEntry.FluidType MELON_MILKSHAKE_FLUID = new FluidEntry("melon_milkshake").flow(2, 4).build();
    public static final FluidEntry.FluidType MERINGUE_FLUID = new FluidEntry("meringue").flow(1, 4).build();
    public static final FluidEntry.FluidType MILKSHAKE_FLUID = new FluidEntry("milkshake").flow(2, 4).build();
    public static final FluidEntry.FluidType MOLASSES_FLUID = new FluidEntry("molasses").build();
    public static final FluidEntry.FluidType MUFFIN_BATTER_FLUID = new FluidEntry("muffin_batter").flow(2, 4).build();
    public static final FluidEntry.FluidType MUSHROOM_CREAM_SOUP_FLUID = new FluidEntry("mushroom_cream_soup").build();
    public static final FluidEntry.FluidType MUTTON_STEW_FLUID = new FluidEntry("mutton_stew").build();
    public static final FluidEntry.FluidType ORANGE_GELATIN_MIX_FLUID = new FluidEntry("orange_gelatin_mix").build();
    public static final FluidEntry.FluidType PINK_GELATIN_MIX_FLUID = new FluidEntry("pink_gelatin_mix").build();
    public static final FluidEntry.FluidType PORK_STEW_FLUID = new FluidEntry("pork_stew").build();
    public static final FluidEntry.FluidType POTATO_CREAM_SOUP_FLUID = new FluidEntry("potato_cream_soup").build();
    public static final FluidEntry.FluidType PUMPKIN_CUSTARD_FLUID = new FluidEntry("pumpkin_custard").flow(1, 4).build();
    public static final FluidEntry.FluidType PUMPKIN_PIE_FILLING_FLUID = new FluidEntry("pumpkin_pie_filling").build();
    public static final FluidEntry.FluidType PUMPKIN_PUREE_FLUID = new FluidEntry("pumpkin_puree").build();
    public static final FluidEntry.FluidType PURPLE_GELATIN_MIX_FLUID = new FluidEntry("purple_gelatin_mix").build();
    public static final FluidEntry.FluidType RED_GELATIN_MIX_FLUID = new FluidEntry("red_gelatin_mix").build();
    public static final FluidEntry.FluidType RICE_PUDDING_FLUID = new FluidEntry("rice_pudding").build();
    public static final FluidEntry.FluidType SLIME_FLUID = new FluidEntry("slime").build();
    public static final FluidEntry.FluidType SOUR_CREAM_FLUID = new FluidEntry("sour_cream").build();
    public static final FluidEntry.FluidType SQUID_INK_FLUID = new FluidEntry("squid_ink").build();
    public static final FluidEntry.FluidType SUGAR_CANE_JUICE_FLUID = new FluidEntry("sugar_cane_juice").flow(3, 2).build();
    public static final FluidEntry.FluidType TACO_SAUCE_FLUID = new FluidEntry("taco_sauce").flow(2, 4).build();
    public static final FluidEntry.FluidType TOFFEE_FLUID = new FluidEntry("toffee").build();
    public static final FluidEntry.FluidType TOFFEE_FUDGE_FLUID = new FluidEntry("toffee_fudge").build();
    public static final FluidEntry.FluidType TOMATO_CREAM_SOUP_FLUID = new FluidEntry("tomato_cream_soup").build();
    public static final FluidEntry.FluidType UBE_CAKE_BATTER_FLUID = new FluidEntry("ube_cake_batter").flow(2, 4).build();
    public static final FluidEntry.FluidType UBE_CREAM_FROSTING_FLUID = new FluidEntry("ube_cream_frosting").build();
    public static final FluidEntry.FluidType VEGETABLE_OIL_FLUID = new FluidEntry("vegetable_oil").build();
    public static final FluidEntry.FluidType VINEGAR_FLUID = new FluidEntry("vinegar").build();
    public static final FluidEntry.FluidType WAFFLE_BATTER_FLUID = new FluidEntry("waffle_batter").build();
    public static final FluidEntry.FluidType WHITE_CHOCOLATE_FLUID = new FluidEntry("white_chocolate").build();
    public static final FluidEntry.FluidType WHITE_CHOCOLATE_FUDGE_FLUID = new FluidEntry("white_chocolate_fudge").build();
    public static final FluidEntry.FluidType YELLOW_GELATIN_MIX_FLUID = new FluidEntry("yellow_gelatin_mix").build();
    public static final FluidEntry.FluidType YOGURT_FLUID = new FluidEntry("yogurt").build();

    private static void registerConfigFluids() {
        var configFile = net.neoforged.fml.loading.FMLPaths.CONFIGDIR.get().resolve("createfood-client.toml");
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
                new FluidEntry(name).flow(slope, level).build();
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_fluid from config", e);
        }
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Fluids");
        registerConfigFluids();
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }
}