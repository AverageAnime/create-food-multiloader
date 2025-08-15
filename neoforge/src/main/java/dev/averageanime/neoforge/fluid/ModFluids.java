package dev.averageanime.neoforge.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.averageanime.CommonClass;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;
import static dev.averageanime.neoforge.block.ModBlocks.BLOCKS;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, CommonClass.ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, CommonClass.ID);

    public static final FluidEntry APPLE_CREAM_FROSTING_FLUID = new FluidEntry("apple_cream_frosting",
            new Vector3f(0.87f, 0.82f, 0.61f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry APPLE_ICE_CREAM_FLUID = new FluidEntry("apple_ice_cream",
            new Vector3f(0.96f, 0.95f, 0.89f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry APPLE_JAM_FLUID = new FluidEntry("apple_jam",
            new Vector3f(0.87f, 0.82f, 0.61f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry APPLE_JUICE_FLUID = new FluidEntry("apple_juice",
            new Vector3f(0.92f, 0.89f, 0.75f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 3, 2);

    public static final FluidEntry APPLE_MILKSHAKE_FLUID = new FluidEntry("apple_milkshake",
            new Vector3f(0.90f, 0.88f, 0.72f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry APPLE_PIE_FILLING_FLUID = new FluidEntry("apple_pie_filling",
            new Vector3f(0.84f, 0.60f, 0.62f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BERRY_CREAM_FROSTING_FLUID = new FluidEntry("berry_cream_frosting",
            new Vector3f(0.87f, 0.61f, 0.61f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BERRY_ICE_CREAM_FLUID = new FluidEntry("berry_ice_cream",
            new Vector3f(0.97f, 0.92f, 0.95f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BERRY_JAM_FLUID = new FluidEntry("berry_jam",
            new Vector3f(0.87f, 0.61f, 0.61f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry BERRY_JUICE_FLUID = new FluidEntry("berry_juice",
            new Vector3f(0.97f, 0.91f, 0.91f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 3, 2);

    public static final FluidEntry BERRY_MILKSHAKE_FLUID = new FluidEntry("berry_milkshake",
            new Vector3f(0.93f, 0.80f, 0.86f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry BERRY_PIE_FILLING_FLUID = new FluidEntry("berry_pie_filling",
            new Vector3f(0.80f, 0.65f, 0.64f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BLACK_GELATIN_MIX_FLUID = new FluidEntry("black_gelatin_mix",
            new Vector3f(0.22f, 0.22f, 0.25f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BLACKSTRAP_MOLASSES_FLUID = new FluidEntry("blackstrap_molasses",
            new Vector3f(0.18f, 0.12f, 0.09f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BLUE_GELATIN_MIX_FLUID = new FluidEntry("blue_gelatin_mix",
            new Vector3f(0.54f, 0.82f, 0.91f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BROWN_GELATIN_MIX_FLUID = new FluidEntry("brown_gelatin_mix",
            new Vector3f(0.71f, 0.47f, 0.29f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BUTTERSCOTCH_FLUID = new FluidEntry("butterscotch",
            new Vector3f(0.63f, 0.42f, 0.22f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry BUTTERSCOTCH_FUDGE_FLUID = new FluidEntry("butterscotch_fudge",
            new Vector3f(0.60f, 0.42f, 0.19f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CACAO_BUTTER_FLUID = new FluidEntry("cacao_butter",
            new Vector3f(0.65f, 0.50f, 0.29f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CACAO_MASS_FLUID = new FluidEntry("cacao_mass",
            new Vector3f(0.44f, 0.30f, 0.16f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CANE_SYRUP_FLUID = new FluidEntry("cane_syrup",
            new Vector3f(0.89f, 0.50f, 0.02f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CARAMEL_FLUID = new FluidEntry("caramel",
            new Vector3f(0.64f, 0.29f, 0.18f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CARAMEL_FUDGE_FLUID = new FluidEntry("caramel_fudge",
            new Vector3f(0.55f, 0.25f, 0.20f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CHEESECAKE_FILLING_FLUID = new FluidEntry("cheesecake_filling",
            new Vector3f(0.80f, 0.78f, 0.64f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CHOCOLATE_CREAM_FROSTING_FLUID = new FluidEntry("chocolate_cream_frosting",
            new Vector3f(0.49f, 0.21f, 0.00f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CHOCOLATE_FUDGE_FLUID = new FluidEntry("chocolate_fudge",
            new Vector3f(0.36f, 0.16f, 0.12f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CHOCOLATE_ICE_CREAM_FLUID = new FluidEntry("chocolate_ice_cream",
            new Vector3f(0.76f, 0.54f, 0.38f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CHOCOLATE_MILK_FLUID = new FluidEntry("chocolate_milk",
            new Vector3f(0.68f, 0.35f, 0.23f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CHOCOLATE_MILKSHAKE_FLUID = new FluidEntry("chocolate_milkshake",
            new Vector3f(0.73f, 0.47f, 0.29f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry CHORUS_FRUIT_CREAM_FROSTING_FLUID = new FluidEntry("chorus_fruit_cream_frosting",
            new Vector3f(0.55f, 0.21f, 0.78f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CHORUS_FRUIT_ICE_CREAM_FLUID = new FluidEntry("chorus_fruit_ice_cream",
            new Vector3f(0.77f, 0.64f, 0.90f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CHORUS_FRUIT_JAM_FLUID = new FluidEntry("chorus_fruit_jam",
            new Vector3f(0.55f, 0.21f, 0.78f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry CHORUS_FRUIT_JUICE_FLUID = new FluidEntry("chorus_fruit_juice",
            new Vector3f(0.71f, 0.49f, 0.87f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 3, 2);

    public static final FluidEntry CHORUS_FRUIT_MILKSHAKE_FLUID = new FluidEntry("chorus_fruit_milkshake",
            new Vector3f(0.69f, 0.51f, 0.86f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry CHORUS_FRUIT_PIE_FILLING_FLUID = new FluidEntry("chorus_fruit_pie_filling",
            new Vector3f(0.76f, 0.64f, 0.80f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry COFFEE_TOFFEE_FLUID = new FluidEntry("coffee_toffee",
            new Vector3f(0.72f, 0.22f, 0.00f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry COFFEE_TOFFEE_FUDGE_FLUID = new FluidEntry("coffee_toffee_fudge",
            new Vector3f(0.25f, 0.10f, 0.08f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CONDENSED_MILK_FLUID = new FluidEntry("condensed_milk",
            new Vector3f(1.00f, 0.87f, 0.57f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CREAM_CHEESE_FLUID = new FluidEntry("cream_cheese",
            new Vector3f(0.98f, 0.98f, 0.84f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CREAM_FROSTING_FLUID = new FluidEntry("cream_frosting",
            new Vector3f(0.80f, 0.80f, 0.80f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CREAM_PIE_FILLING_FLUID = new FluidEntry("cream_pie_filling",
            new Vector3f(0.69f, 0.67f, 0.58f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry CYAN_GELATIN_MIX_FLUID = new FluidEntry("cyan_gelatin_mix",
            new Vector3f(0.14f, 0.85f, 0.85f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry DARK_CHOCOLATE_FLUID = new FluidEntry("dark_chocolate",
            new Vector3f(0.31f, 0.14f, 0.11f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry DARK_CHOCOLATE_FUDGE_FLUID = new FluidEntry("dark_chocolate_fudge",
            new Vector3f(0.26f, 0.10f, 0.08f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry FRUIT_SMOOTHIE_FLUID = new FluidEntry("fruit_smoothie",
            new Vector3f(0.87f, 0.60f, 0.63f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry GELATIN_MIX_FLUID = new FluidEntry("gelatin_mix",
            new Vector3f(0.86f, 0.86f, 0.86f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry GLOW_BERRY_CREAM_FROSTING_FLUID = new FluidEntry("glow_berry_cream_frosting",
            new Vector3f(0.85f, 0.62f, 0.27f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry GLOW_BERRY_ICE_CREAM_FLUID = new FluidEntry("glow_berry_ice_cream",
            new Vector3f(0.92f, 0.87f, 0.77f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry GLOW_BERRY_JAM_FLUID = new FluidEntry("glow_berry_jam",
            new Vector3f(0.85f, 0.62f, 0.27f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry GLOW_BERRY_JUICE_FLUID = new FluidEntry("glow_berry_juice",
            new Vector3f(0.91f, 0.76f, 0.54f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 3, 2);

    public static final FluidEntry GLOW_BERRY_MILKSHAKE_FLUID = new FluidEntry("glow_berry_milkshake",
            new Vector3f(0.87f, 0.79f, 0.63f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry GLOW_BERRY_PIE_FILLING_FLUID = new FluidEntry("glow_berry_pie_filling",
            new Vector3f(0.80f, 0.74f, 0.64f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry GRAY_GELATIN_MIX_FLUID = new FluidEntry("gray_gelatin_mix",
            new Vector3f(0.48f, 0.53f, 0.55f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry GREEN_GELATIN_MIX_FLUID = new FluidEntry("green_gelatin_mix",
            new Vector3f(0.52f, 0.69f, 0.12f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry HEAVY_CREAM_FLUID = new FluidEntry("heavy_cream",
            new Vector3f(0.91f, 0.85f, 0.72f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry HOT_CHOCOLATE_FLUID = new FluidEntry("hot_chocolate",
            new Vector3f(0.63f, 0.29f, 0.23f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry HOT_DARK_CHOCOLATE_FLUID = new FluidEntry("hot_dark_chocolate",
            new Vector3f(0.33f, 0.13f, 0.10f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry HOT_WHITE_CHOCOLATE_FLUID = new FluidEntry("hot_white_chocolate",
            new Vector3f(0.91f, 0.70f, 0.42f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry ICE_CREAM_FLUID = new FluidEntry("ice_cream",
            new Vector3f(0.98f, 0.92f, 0.79f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry LIGHT_BLUE_GELATIN_MIX_FLUID = new FluidEntry("light_blue_gelatin_mix",
            new Vector3f(0.78f, 0.91f, 0.96f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry LIGHT_GRAY_GELATIN_MIX_FLUID = new FluidEntry("light_gray_gelatin_mix",
            new Vector3f(0.77f, 0.77f, 0.76f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry LIME_GELATIN_MIX_FLUID = new FluidEntry("lime_gelatin_mix",
            new Vector3f(0.68f, 0.90f, 0.38f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry MAGENTA_GELATIN_MIX_FLUID = new FluidEntry("magenta_gelatin_mix",
            new Vector3f(0.87f, 0.59f, 0.84f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry MELON_CREAM_FROSTING_FLUID = new FluidEntry("melon_cream_frosting",
            new Vector3f(1.00f, 0.48f, 0.50f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry MELON_ICE_CREAM_FLUID = new FluidEntry("melon_ice_cream",
            new Vector3f(1.00f, 0.90f, 0.95f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry MELON_JAM_FLUID = new FluidEntry("melon_jam",
            new Vector3f(1.00f, 0.48f, 0.50f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry MELON_MILKSHAKE_FLUID = new FluidEntry("melon_milkshake",
            new Vector3f(1.00f, 0.74f, 0.87f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry MILKSHAKE_FLUID = new FluidEntry("milkshake",
            new Vector3f(0.81f, 0.79f, 0.72f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 2, 4);

    public static final FluidEntry MOLASSES_FLUID = new FluidEntry("molasses",
            new Vector3f(0.44f, 0.23f, 0.09f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry ORANGE_GELATIN_MIX_FLUID = new FluidEntry("orange_gelatin_mix",
            new Vector3f(0.99f, 0.70f, 0.47f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry PINK_GELATIN_MIX_FLUID = new FluidEntry("pink_gelatin_mix",
            new Vector3f(0.97f, 0.73f, 0.80f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry PUMPKIN_PIE_FILLING_FLUID = new FluidEntry("pumpkin_pie_filling",
            new Vector3f(0.90f, 0.62f, 0.33f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry PUMPKIN_PUREE_FLUID = new FluidEntry("pumpkin_puree",
            new Vector3f(0.79f, 0.50f, 0.13f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry PURPLE_GELATIN_MIX_FLUID = new FluidEntry("purple_gelatin_mix",
            new Vector3f(0.71f, 0.45f, 0.85f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry RED_GELATIN_MIX_FLUID = new FluidEntry("red_gelatin_mix",
            new Vector3f(0.92f, 0.64f, 0.62f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry SLIME_FLUID = new FluidEntry("slime",
            new Vector3f(0.41f, 0.65f, 0.41f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry SOUR_CREAM_FLUID = new FluidEntry("sour_cream",
            new Vector3f(0.83f, 0.82f, 0.72f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry SQUID_INK_FLUID = new FluidEntry("squid_ink",
            new Vector3f(0.09f, 0.07f, 0.15f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry SUGAR_CANE_JUICE_FLUID = new FluidEntry("sugar_cane_juice",
            new Vector3f(1.00f, 0.99f, 0.81f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 3, 2);

    public static final FluidEntry TOFFEE_FLUID = new FluidEntry("toffee",
            new Vector3f(0.72f, 0.22f, 0.00f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry TOFFEE_FUDGE_FLUID = new FluidEntry("toffee_fudge",
            new Vector3f(0.25f, 0.10f, 0.08f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry UBE_CREAM_FROSTING_FLUID = new FluidEntry("ube_cream_frosting",
            new Vector3f(0.44f, 0.27f, 0.78f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry VEGETABLE_OIL_FLUID = new FluidEntry("vegetable_oil",
            new Vector3f(0.80f, 0.79f, 0.67f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry VINEGAR_FLUID = new FluidEntry("vinegar",
            new Vector3f(0.86f, 0.86f, 0.85f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry WHITE_CHOCOLATE_FLUID = new FluidEntry("white_chocolate",
            new Vector3f(0.89f, 0.71f, 0.50f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry WHITE_CHOCOLATE_FUDGE_FLUID = new FluidEntry("white_chocolate_fudge",
            new Vector3f(0.49f, 0.38f, 0.25f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry YELLOW_GELATIN_MIX_FLUID = new FluidEntry("yellow_gelatin_mix",
            new Vector3f(1.00f, 0.91f, 0.54f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry YOGURT_FLUID = new FluidEntry("yogurt",
            new Vector3f(0.83f, 0.80f, 0.72f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    public static final FluidEntry WAFFLE_BATTER_FLUID = new FluidEntry("waffle_batter",
            new Vector3f(0.82f, 0.76f, 0.61f),
            FluidType.Properties.create()
                    .canSwim(true).canDrown(true).canHydrate(true).lightLevel(1)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
                    .density(1400).viscosity(1500), 4, 3);

    /**
     * Registers all DeferredRegisters for fluids and fluid types to the NeoForge event bus,
     * and adds the listener for client extensions.
     * This method should be called during your mod's common setup phase.
     * @param eventBus The mod's event bus.
     */
    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Fluids");
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }

    public static class FluidEntry {
        public final DeferredHolder<FluidType, FluidType> FLUID_TYPE;
        public final DeferredHolder<Fluid, FlowingFluid> SOURCE;
        public final DeferredHolder<Fluid, FlowingFluid> FLOWING;
        public final DeferredBlock<LiquidBlock> BLOCK;
        public final DeferredItem<Item> BUCKET;
        private final ResourceLocation stillTexture;
        private final ResourceLocation flowingTexture;
        private final Vector3f fogColor;
        private final int slopeFindDistance;
        private final int levelDecreasePerBlock;

        public FluidEntry(String name, Vector3f fogColor, FluidType.Properties fluidTypeProperties,
                          int slopeFindDistance, int levelDecreasePerBlock) {
            this.stillTexture = ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "fluid/" + name + "_still");
            this.flowingTexture = ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "fluid/" + name + "_flow");
            this.fogColor = fogColor;
            this.slopeFindDistance = slopeFindDistance;
            this.levelDecreasePerBlock = levelDecreasePerBlock;

            FLUID_TYPE = FLUID_TYPES.register(name, () -> new FluidType(fluidTypeProperties) {
                @SuppressWarnings("removal")
                @Override
                public void initializeClient(java.util.function.Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {

                        @Override
                        public ResourceLocation getStillTexture() { return stillTexture; }

                        @Override
                        public ResourceLocation getFlowingTexture() { return flowingTexture; }

                        @Override
                        public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                            return fogColor;
                        }

                        @Override
                        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                            RenderSystem.setShaderFogColor(fogColor.x(), fogColor.y(), fogColor.z());
                            RenderSystem.setShaderFogStart(0.1f);
                            RenderSystem.setShaderFogEnd(1.0f);
                            RenderSystem.setShaderFogShape(FogShape.CYLINDER);


                        }
                    });
                }
            });

            SOURCE = FLUIDS.register(name, () -> new BaseFlowingFluid.Source(createFluidPropertiesInternal()));
            FLOWING = FLUIDS.register("flowing_" + name, () -> new BaseFlowingFluid.Flowing(createFluidPropertiesInternal()));

            BLOCK = BLOCKS.register(name + "_block",
                    () -> new LiquidBlock(SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
            BUCKET = ITEMS.register(name + "_bucket",
                    () -> new BucketItem(SOURCE.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
        }

        private BaseFlowingFluid.Properties createFluidPropertiesInternal() {
            return new BaseFlowingFluid.Properties(
                    FLUID_TYPE::get,
                    SOURCE::get,
                    FLOWING::get)
                    .slopeFindDistance(this.slopeFindDistance)
                    .levelDecreasePerBlock(this.levelDecreasePerBlock)
                    .block(BLOCK::get)
                    .bucket(BUCKET::get)
                    .tickRate(25);
        }

    }
}