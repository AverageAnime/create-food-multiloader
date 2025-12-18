package dev.averageanime.neoforge.fluid;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.fluid.builder.CustomFluid;
import dev.averageanime.neoforge.fluid.builder.CustomFluidType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static dev.averageanime.neoforge.CreateFood.LOGGER;

@SuppressWarnings("unused")
public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, CommonClass.ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, CommonClass.ID);

    public static final CustomFluidType PORK_STEW_FLUID = new CustomFluid("pork_stew").build();

    public static final CustomFluidType MUTTON_STEW_FLUID = new CustomFluid("mutton_stew").build();

    public static final CustomFluidType LEATHER_SOUP_FLUID = new CustomFluid("leather_soup").build();

    public static final CustomFluidType APPLE_CREAM_FROSTING_FLUID = new CustomFluid("apple_cream_frosting").build();

    public static final CustomFluidType APPLE_ICE_CREAM_FLUID = new CustomFluid("apple_ice_cream").build();

    public static final CustomFluidType APPLE_JAM_FLUID = new CustomFluid("apple_jam").flow(2, 4).build();

    public static final CustomFluidType APPLE_JUICE_FLUID = new CustomFluid("apple_juice").flow(3, 2).build();

    public static final CustomFluidType APPLE_MILKSHAKE_FLUID = new CustomFluid("apple_milkshake").flow(2, 4).build();

    public static final CustomFluidType APPLE_PIE_FILLING_FLUID = new CustomFluid("apple_pie_filling").build();

    public static final CustomFluidType BERRY_CREAM_FROSTING_FLUID = new CustomFluid("berry_cream_frosting").build();

    public static final CustomFluidType BERRY_ICE_CREAM_FLUID = new CustomFluid("berry_ice_cream").build();

    public static final CustomFluidType BERRY_JAM_FLUID = new CustomFluid("berry_jam").flow(2, 4).build();

    public static final CustomFluidType BERRY_JUICE_FLUID = new CustomFluid("berry_juice").flow(3, 2).build();

    public static final CustomFluidType BERRY_MILKSHAKE_FLUID = new CustomFluid("berry_milkshake").flow(2, 4).build();

    public static final CustomFluidType BERRY_PIE_FILLING_FLUID = new CustomFluid("berry_pie_filling").build();

    public static final CustomFluidType BLACK_GELATIN_MIX_FLUID = new CustomFluid("black_gelatin_mix").build();

    public static final CustomFluidType BLACKSTRAP_MOLASSES_FLUID = new CustomFluid("blackstrap_molasses").build();

    public static final CustomFluidType BLUE_GELATIN_MIX_FLUID = new CustomFluid("blue_gelatin_mix").build();

    public static final CustomFluidType BROWN_GELATIN_MIX_FLUID = new CustomFluid("brown_gelatin_mix").build();

    public static final CustomFluidType BUTTERSCOTCH_FLUID = new CustomFluid("butterscotch").build();

    public static final CustomFluidType BUTTERSCOTCH_FUDGE_FLUID = new CustomFluid("butterscotch_fudge").build();

    public static final CustomFluidType CACAO_BUTTER_FLUID = new CustomFluid("cacao_butter").build();

    public static final CustomFluidType CACAO_MASS_FLUID = new CustomFluid("cacao_mass").build();

    public static final CustomFluidType CANE_SYRUP_FLUID = new CustomFluid("cane_syrup").build();

    public static final CustomFluidType CARAMEL_FLUID = new CustomFluid("caramel").build();

    public static final CustomFluidType CARAMEL_FUDGE_FLUID = new CustomFluid("caramel_fudge").build();

    public static final CustomFluidType CHEESECAKE_FILLING_FLUID = new CustomFluid("cheesecake_filling").build();

    public static final CustomFluidType CHOCOLATE_CREAM_FROSTING_FLUID = new CustomFluid("chocolate_cream_frosting").build();

    public static final CustomFluidType CHOCOLATE_FUDGE_FLUID = new CustomFluid("chocolate_fudge").build();

    public static final CustomFluidType CHOCOLATE_ICE_CREAM_FLUID = new CustomFluid("chocolate_ice_cream").build();

    public static final CustomFluidType CHOCOLATE_MILK_FLUID = new CustomFluid("chocolate_milk").build();

    public static final CustomFluidType CHOCOLATE_MILKSHAKE_FLUID = new CustomFluid("chocolate_milkshake").flow(2, 4).build();

    public static final CustomFluidType CHORUS_FRUIT_CREAM_FROSTING_FLUID = new CustomFluid("chorus_fruit_cream_frosting").build();

    public static final CustomFluidType CHORUS_FRUIT_ICE_CREAM_FLUID = new CustomFluid("chorus_fruit_ice_cream").build();

    public static final CustomFluidType CHORUS_FRUIT_JAM_FLUID = new CustomFluid("chorus_fruit_jam").flow(2, 4).build();

    public static final CustomFluidType CHORUS_FRUIT_JUICE_FLUID = new CustomFluid("chorus_fruit_juice").flow(3, 2).build();

    public static final CustomFluidType CHORUS_FRUIT_MILKSHAKE_FLUID = new CustomFluid("chorus_fruit_milkshake").flow(2, 4).build();

    public static final CustomFluidType CHORUS_FRUIT_PIE_FILLING_FLUID = new CustomFluid("chorus_fruit_pie_filling").build();

    public static final CustomFluidType COFFEE_TOFFEE_FLUID = new CustomFluid("coffee_toffee").build();

    public static final CustomFluidType COFFEE_TOFFEE_FUDGE_FLUID = new CustomFluid("coffee_toffee_fudge").build();

    public static final CustomFluidType CONDENSED_MILK_FLUID = new CustomFluid("condensed_milk").build();

    public static final CustomFluidType CREAM_CHEESE_FLUID = new CustomFluid("cream_cheese").build();

    public static final CustomFluidType CREAM_FROSTING_FLUID = new CustomFluid("cream_frosting").build();

    public static final CustomFluidType CREAM_PIE_FILLING_FLUID = new CustomFluid("cream_pie_filling").build();

    public static final CustomFluidType CYAN_GELATIN_MIX_FLUID = new CustomFluid("cyan_gelatin_mix").build();

    public static final CustomFluidType DARK_CHOCOLATE_FLUID = new CustomFluid("dark_chocolate").build();

    public static final CustomFluidType DARK_CHOCOLATE_FUDGE_FLUID = new CustomFluid("dark_chocolate_fudge").build();

    public static final CustomFluidType FRUIT_SMOOTHIE_FLUID = new CustomFluid("fruit_smoothie").build();

    public static final CustomFluidType GELATIN_MIX_FLUID = new CustomFluid("gelatin_mix").build();

    public static final CustomFluidType GLOW_BERRY_CREAM_FROSTING_FLUID = new CustomFluid("glow_berry_cream_frosting").build();

    public static final CustomFluidType GLOW_BERRY_ICE_CREAM_FLUID = new CustomFluid("glow_berry_ice_cream").build();

    public static final CustomFluidType GLOW_BERRY_JAM_FLUID = new CustomFluid("glow_berry_jam").flow(2, 4).build();

    public static final CustomFluidType GLOW_BERRY_JUICE_FLUID = new CustomFluid("glow_berry_juice").flow(3, 2).build();

    public static final CustomFluidType GLOW_BERRY_MILKSHAKE_FLUID = new CustomFluid("glow_berry_milkshake").flow(2, 4).build();

    public static final CustomFluidType GLOW_BERRY_PIE_FILLING_FLUID = new CustomFluid("glow_berry_pie_filling").build();

    public static final CustomFluidType GRAY_GELATIN_MIX_FLUID = new CustomFluid("gray_gelatin_mix").build();

    public static final CustomFluidType GREEN_GELATIN_MIX_FLUID = new CustomFluid("green_gelatin_mix").build();

    public static final CustomFluidType HEAVY_CREAM_FLUID = new CustomFluid("heavy_cream").build();

    public static final CustomFluidType HOT_CHOCOLATE_FLUID = new CustomFluid("hot_chocolate").build();

    public static final CustomFluidType HOT_DARK_CHOCOLATE_FLUID = new CustomFluid("hot_dark_chocolate").build();

    public static final CustomFluidType HOT_WHITE_CHOCOLATE_FLUID = new CustomFluid("hot_white_chocolate").build();

    public static final CustomFluidType ICE_CREAM_FLUID = new CustomFluid("ice_cream").build();

    public static final CustomFluidType LIGHT_BLUE_GELATIN_MIX_FLUID = new CustomFluid("light_blue_gelatin_mix").build();

    public static final CustomFluidType LIGHT_GRAY_GELATIN_MIX_FLUID = new CustomFluid("light_gray_gelatin_mix").build();

    public static final CustomFluidType LIME_GELATIN_MIX_FLUID = new CustomFluid("lime_gelatin_mix").build();

    public static final CustomFluidType MAGENTA_GELATIN_MIX_FLUID = new CustomFluid("magenta_gelatin_mix").build();

    public static final CustomFluidType MELON_CREAM_FROSTING_FLUID = new CustomFluid("melon_cream_frosting").build();

    public static final CustomFluidType MELON_ICE_CREAM_FLUID = new CustomFluid("melon_ice_cream").build();

    public static final CustomFluidType MELON_JAM_FLUID = new CustomFluid("melon_jam").flow(2, 4).build();

    public static final CustomFluidType MELON_MILKSHAKE_FLUID = new CustomFluid("melon_milkshake").flow(2, 4).build();

    public static final CustomFluidType MILKSHAKE_FLUID = new CustomFluid("milkshake").flow(2, 4).build();

    public static final CustomFluidType MOLASSES_FLUID = new CustomFluid("molasses").build();

    public static final CustomFluidType ORANGE_GELATIN_MIX_FLUID = new CustomFluid("orange_gelatin_mix").build();

    public static final CustomFluidType PINK_GELATIN_MIX_FLUID = new CustomFluid("pink_gelatin_mix").build();

    public static final CustomFluidType PUMPKIN_PIE_FILLING_FLUID = new CustomFluid("pumpkin_pie_filling").build();

    public static final CustomFluidType PUMPKIN_PUREE_FLUID = new CustomFluid("pumpkin_puree").build();

    public static final CustomFluidType PURPLE_GELATIN_MIX_FLUID = new CustomFluid("purple_gelatin_mix").build();

    public static final CustomFluidType RED_GELATIN_MIX_FLUID = new CustomFluid("red_gelatin_mix").build();

    public static final CustomFluidType SLIME_FLUID = new CustomFluid("slime").build();

    public static final CustomFluidType SOUR_CREAM_FLUID = new CustomFluid("sour_cream").build();

    public static final CustomFluidType SQUID_INK_FLUID = new CustomFluid("squid_ink").build();

    public static final CustomFluidType SUGAR_CANE_JUICE_FLUID = new CustomFluid("sugar_cane_juice").flow(3, 2).build();

    public static final CustomFluidType TOFFEE_FLUID = new CustomFluid("toffee").build();

    public static final CustomFluidType TOFFEE_FUDGE_FLUID = new CustomFluid("toffee_fudge").build();

    public static final CustomFluidType UBE_CREAM_FROSTING_FLUID = new CustomFluid("ube_cream_frosting").build();

    public static final CustomFluidType VEGETABLE_OIL_FLUID = new CustomFluid("vegetable_oil").build();

    public static final CustomFluidType VINEGAR_FLUID = new CustomFluid("vinegar").build();

    public static final CustomFluidType WHITE_CHOCOLATE_FLUID = new CustomFluid("white_chocolate").build();

    public static final CustomFluidType WHITE_CHOCOLATE_FUDGE_FLUID = new CustomFluid("white_chocolate_fudge").build();

    public static final CustomFluidType YELLOW_GELATIN_MIX_FLUID = new CustomFluid("yellow_gelatin_mix").build();

    public static final CustomFluidType YOGURT_FLUID = new CustomFluid("yogurt").build();

    public static final CustomFluidType WAFFLE_BATTER_FLUID = new CustomFluid("waffle_batter").build();

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Fluids");
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }
}