package net.averageanime.createfood.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.foundation.utility.Color;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.averageanime.createfood.CreateFood.REGISTRATE;
import static net.averageanime.createfood.CreateFood.registrate;

public class ModFluids {

    public static final FluidEntry<ForgeFlowingFluid.Flowing> APPLE_CREAM_FROSTING =
            REGISTRATE.standardFluid("apple_cream_frosting",
                            SolidRenderedPlaceableFluidType.create(0xddd09a,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> APPLE_ICE_CREAM_FROSTING =
            REGISTRATE.standardFluid("apple_ice_cream",
                            SolidRenderedPlaceableFluidType.create(0xf5f3e2,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> APPLE_JAM_FROSTING =
            REGISTRATE.standardFluid("apple_jam",
                            SolidRenderedPlaceableFluidType.create(0xddd09a,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> APPLE_JUICE_FROSTING =
            REGISTRATE.standardFluid("apple_juice",
                            SolidRenderedPlaceableFluidType.create(0xeae1c0,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> APPLE_MILKSHAKE_FROSTING =
            REGISTRATE.standardFluid("apple_milkshake",
                            SolidRenderedPlaceableFluidType.create(0xe6e1b6,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> APPLE_PIE_FILLING_FROSTING =
            REGISTRATE.standardFluid("apple_pie_filling",
                            SolidRenderedPlaceableFluidType.create(0xd7989f,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BERRY_CREAM_FROSTING =
            REGISTRATE.standardFluid("berry_cream_frosting",
                            SolidRenderedPlaceableFluidType.create(0xdd9a9a,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BERRY_ICE_CREAM_FROSTING =
            REGISTRATE.standardFluid("berry_ice_cream",
                            SolidRenderedPlaceableFluidType.create(0xf8ebf1,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BERRY_JAM_FROSTING =
            REGISTRATE.standardFluid("berry_jam",
                            SolidRenderedPlaceableFluidType.create(0xdd9a9a,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BERRY_JUICE_FROSTING =
            REGISTRATE.standardFluid("berry_juice",
                            SolidRenderedPlaceableFluidType.create(0xf7e6e6,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BERRY_MILKSHAKE_FROSTING =
            REGISTRATE.standardFluid("berry_milkshake",
                            SolidRenderedPlaceableFluidType.create(0xeecddb,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BERRY_PIE_FILLING_FROSTING =
            REGISTRATE.standardFluid("berry_pie_filling",
                            SolidRenderedPlaceableFluidType.create(0xcca5a3,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BLACK_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("black_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0x36363e,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BLACKSTRAP_MOLASSES_FROSTING =
            REGISTRATE.standardFluid("blackstrap_molasses",
                            SolidRenderedPlaceableFluidType.create(0x2c1f16,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BLUE_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("blue_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0x89d1e9,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BROWN_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("brown_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xb5774b,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BUTTERSCOTCH_FROSTING =
            REGISTRATE.standardFluid("butterscotch",
                            SolidRenderedPlaceableFluidType.create(0x9f6b37,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> BUTTERSCOTCH_FUDGE_FROSTING =
            REGISTRATE.standardFluid("butterscotch_fudge",
                            SolidRenderedPlaceableFluidType.create(0x9a6a2f,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CACAO_BUTTER_FROSTING =
            REGISTRATE.standardFluid("cacao_butter",
                            SolidRenderedPlaceableFluidType.create(0xa6804b,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CACAO_MASS_FROSTING =
            REGISTRATE.standardFluid("cacao_mass",
                            SolidRenderedPlaceableFluidType.create(0x704d29,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CANE_SYRUP_FROSTING =
            REGISTRATE.standardFluid("cane_syrup",
                            SolidRenderedPlaceableFluidType.create(0xe28003,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL_FROSTING =
            REGISTRATE.standardFluid("caramel",
                            SolidRenderedPlaceableFluidType.create(0xa24a2c,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL_FUDGE_FROSTING =
            REGISTRATE.standardFluid("caramel_fudge",
                            SolidRenderedPlaceableFluidType.create(0x8d4034,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHEESECAKE_FILLING_FROSTING =
            REGISTRATE.standardFluid("cheesecake_filling",
                            SolidRenderedPlaceableFluidType.create(0xccc6a3,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_CREAM_FROSTING =
            REGISTRATE.standardFluid("chocolate_cream_frosting",
                            SolidRenderedPlaceableFluidType.create(0x7c3500,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_FUDGE_FROSTING =
            REGISTRATE.standardFluid("chocolate_fudge",
                            SolidRenderedPlaceableFluidType.create(0x5c271f,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_ICE_CREAM_FROSTING =
            REGISTRATE.standardFluid("chocolate_ice_cream",
                            SolidRenderedPlaceableFluidType.create(0xc18960,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_MILK_FROSTING =
            REGISTRATE.standardFluid("chocolate_milk",
                            SolidRenderedPlaceableFluidType.create(0xae593a,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_MILKSHAKE_FROSTING =
            REGISTRATE.standardFluid("chocolate_milkshake",
                            SolidRenderedPlaceableFluidType.create(0xb9784a,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHORUS_FRUIT_CREAM_FROSTING =
            REGISTRATE.standardFluid("chorus_fruit_cream_frosting",
                            SolidRenderedPlaceableFluidType.create(0x8b34c6,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHORUS_FRUIT_ICE_CREAM_FROSTING =
            REGISTRATE.standardFluid("chorus_fruit_ice_cream",
                            SolidRenderedPlaceableFluidType.create(0xc4a2e4,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHORUS_FRUIT_JAM_FROSTING =
            REGISTRATE.standardFluid("chorus_fruit_jam",
                            SolidRenderedPlaceableFluidType.create(0x8b34c6,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHORUS_FRUIT_JUICE_FROSTING =
            REGISTRATE.standardFluid("chorus_fruit_juice",
                            SolidRenderedPlaceableFluidType.create(0xb57cdc,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHORUS_FRUIT_MILKSHAKE_FROSTING =
            REGISTRATE.standardFluid("chorus_fruit_milkshake",
                            SolidRenderedPlaceableFluidType.create(0xb081db,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHORUS_FRUIT_PIE_FILLING_FROSTING =
            REGISTRATE.standardFluid("chorus_fruit_pie_filling",
                            SolidRenderedPlaceableFluidType.create(0xc1a3cc,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> COFFEE_TOFFEE =
            REGISTRATE.standardFluid("coffee_toffee",
                            SolidRenderedPlaceableFluidType.create(0xb73800,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> COFFEE_TOFFEE_FUDGE_FROSTING =
            REGISTRATE.standardFluid("coffee_toffee_fudge",
                            SolidRenderedPlaceableFluidType.create(0x401913,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CONDENSED_MILK_FROSTING =
            REGISTRATE.standardFluid("condensed_milk",
                            SolidRenderedPlaceableFluidType.create(0xfede91,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREAM_CHEESE_FROSTING =
            REGISTRATE.standardFluid("cream_cheese",
                            SolidRenderedPlaceableFluidType.create(0xfbfad5,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREAM_FROSTING =
            REGISTRATE.standardFluid("cream_frosting",
                            SolidRenderedPlaceableFluidType.create(0xcbcbcb,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREAM_PIE_FILLING_FROSTING =
            REGISTRATE.standardFluid("cream_pie_filling",
                            SolidRenderedPlaceableFluidType.create(0xaeaa93,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CYAN_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("cyan_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0x24d7d7,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> DARK_CHOCOLATE_FROSTING =
            REGISTRATE.standardFluid("dark_chocolate",
                            SolidRenderedPlaceableFluidType.create(0x50231c,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> DARK_CHOCOLATE_FUDGE_FROSTING =
            REGISTRATE.standardFluid("dark_chocolate_fudge",
                            SolidRenderedPlaceableFluidType.create(0x421a14,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> FRUIT_SMOOTHIE_FROSTING =
            REGISTRATE.standardFluid("fruit_smoothie",
                            SolidRenderedPlaceableFluidType.create(0xdd99a1,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xdadada,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOW_BERRY_CREAM_FROSTING =
            REGISTRATE.standardFluid("glow_berry_cream_frosting",
                            SolidRenderedPlaceableFluidType.create(0xd79c43,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOW_BERRY_ICE_CREAM_FROSTING =
            REGISTRATE.standardFluid("glow_berry_ice_cream",
                            SolidRenderedPlaceableFluidType.create(0xe9ddc3,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOW_BERRY_JAM_FROSTING =
            REGISTRATE.standardFluid("glow_berry_jam",
                            SolidRenderedPlaceableFluidType.create(0xd79c43,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOW_BERRY_JUICE_FROSTING =
            REGISTRATE.standardFluid("glow_berry_juice",
                            SolidRenderedPlaceableFluidType.create(0xe6c189,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOW_BERRY_MILKSHAKE_FROSTING =
            REGISTRATE.standardFluid("glow_berry_milkshake",
                            SolidRenderedPlaceableFluidType.create(0xddcaa1,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOW_BERRY_PIE_FILLING_FROSTING =
            REGISTRATE.standardFluid("glow_berry_pie_filling",
                            SolidRenderedPlaceableFluidType.create(0xccbda3,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GRAY_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("gray_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0x7a878c,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GREEN_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("green_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0x84af1f,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HEAVY_CREAM_FROSTING =
            REGISTRATE.standardFluid("heavy_cream",
                            SolidRenderedPlaceableFluidType.create(0xe6d8b6,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HOT_CHOCOLATE_FROSTING =
            REGISTRATE.standardFluid("hot_chocolate",
                            SolidRenderedPlaceableFluidType.create(0xa04a3a,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HOT_DARK_CHOCOLATE_FROSTING =
            REGISTRATE.standardFluid("hot_dark_chocolate",
                            SolidRenderedPlaceableFluidType.create(0x522119,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HOT_WHITE_CHOCOLATE_FROSTING =
            REGISTRATE.standardFluid("hot_white_chocolate",
                            SolidRenderedPlaceableFluidType.create(0xe7b26c,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> ICE_CREAM_FROSTING =
            REGISTRATE.standardFluid("ice_cream",
                            SolidRenderedPlaceableFluidType.create(0xf8ebc8,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> LIGHT_BLUE_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("light_blue_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xc6e9f4,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> LIGHT_GRAY_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("light_gray_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xc4c4c0,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> LIME_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("lime_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xade661,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MAGENTA_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("magenta_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xdd95d7,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MELON_CREAM_FROSTING =
            REGISTRATE.standardFluid("melon_cream_frosting",
                            SolidRenderedPlaceableFluidType.create(0xff7980,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MELON_ICE_CREAM_FROSTING =
            REGISTRATE.standardFluid("melon_ice_cream",
                            SolidRenderedPlaceableFluidType.create(0xfee5f1,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MELON_JAM_FROSTING =
            REGISTRATE.standardFluid("melon_jam",
                            SolidRenderedPlaceableFluidType.create(0xff7980,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MELON_MILKSHAKE_FROSTING =
            REGISTRATE.standardFluid("melon_milkshake",
                            SolidRenderedPlaceableFluidType.create(0xfebcdd,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MILKSHAKE_FROSTING =
            REGISTRATE.standardFluid("milkshake",
                            SolidRenderedPlaceableFluidType.create(0xcfc8b6,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(4)
                            .tickRate(25)
                            .slopeFindDistance(2)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLASSES_FROSTING =
            REGISTRATE.standardFluid("molasses",
                            SolidRenderedPlaceableFluidType.create(0x6f3917,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> ORANGE_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("orange_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xfbb278,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> PINK_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("pink_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xf7b9cb,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> PUMPKIN_PIE_FILLING =
            REGISTRATE.standardFluid("pumpkin_pie_filling",
                            SolidRenderedPlaceableFluidType.create(0xd27d29,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> PUMPKIN_PUREE =
            REGISTRATE.standardFluid("pumpkin_puree",
                            SolidRenderedPlaceableFluidType.create(0xd27d29,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> PURPLE_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("purple_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xb572d9,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> RED_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("red_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xeaa39e,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SLIME_FROSTING =
            REGISTRATE.standardFluid("slime",
                            SolidRenderedPlaceableFluidType.create(0x68a668,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SOUR_CREAM_FROSTING =
            REGISTRATE.standardFluid("sour_cream",
                            SolidRenderedPlaceableFluidType.create(0xd4cfb7,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SQUID_INK_FROSTING =
            REGISTRATE.standardFluid("squid_ink",
                            SolidRenderedPlaceableFluidType.create(0x161026,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SUGAR_CANE_JUICE_FROSTING =
            REGISTRATE.standardFluid("sugar_cane_juice",
                            SolidRenderedPlaceableFluidType.create(0xfefcce,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> TOFFEE_FROSTING =
            REGISTRATE.standardFluid("toffee",
                            SolidRenderedPlaceableFluidType.create(0xb73800,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> TOFFEE_FUDGE_FROSTING =
            REGISTRATE.standardFluid("toffee_fudge",
                            SolidRenderedPlaceableFluidType.create(0x401913,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> UBE_CREAM_FROSTING =
            REGISTRATE.standardFluid("ube_cream_frosting",
                            SolidRenderedPlaceableFluidType.create(0x6f45c7,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VEGETABLE_OIL_FROSTING =
            REGISTRATE.standardFluid("vegetable_oil",
                            SolidRenderedPlaceableFluidType.create(0xcbcaaa,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VINEGAR_FROSTING =
            REGISTRATE.standardFluid("vinegar",
                            SolidRenderedPlaceableFluidType.create(0xdbdcd9,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> WHITE_CHOCOLATE_FROSTING =
            REGISTRATE.standardFluid("white_chocolate",
                            SolidRenderedPlaceableFluidType.create(0xe1b47f,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> WHITE_CHOCOLATE_FUDGE_FROSTING =
            REGISTRATE.standardFluid("white_chocolate_fudge",
                            SolidRenderedPlaceableFluidType.create(0x7d6040,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> YELLOW_GELATIN_MIX_FROSTING =
            REGISTRATE.standardFluid("yellow_gelatin_mix",
                            SolidRenderedPlaceableFluidType.create(0xfee78a,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> YOGURT_FROSTING =
            REGISTRATE.standardFluid("yogurt",
                            SolidRenderedPlaceableFluidType.create(0xd4cab7,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> WAFFLE_BATTER =
            REGISTRATE.standardFluid("waffle_batter",
                            SolidRenderedPlaceableFluidType.create(0xcebc92,
                                    () -> 1f / 32f * 1))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(3)
                            .tickRate(25)
                            .slopeFindDistance(4)
                            .explosionResistance(100f))
                    .register();

    public static void register() {}

    public static abstract class TintedFluidType extends FluidType {

        protected static final int NO_TINT = 0xffffffff;
        private ResourceLocation stillTexture;
        private ResourceLocation flowingTexture;

        public TintedFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            super(properties);
            this.stillTexture = stillTexture;
            this.flowingTexture = flowingTexture;
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {

                @Override
                public ResourceLocation getStillTexture() {
                    return stillTexture;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return flowingTexture;
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return TintedFluidType.this.getTintColor(stack);
                }

                @Override
                public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                    return TintedFluidType.this.getTintColor(state, getter, pos);
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                        int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    Vector3f customFogColor = TintedFluidType.this.getCustomFogColor();
                    return customFogColor == null ? fluidFogColor : customFogColor;
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick,
                                            float nearDistance, float farDistance, FogShape shape) {
                    float modifier = TintedFluidType.this.getFogDistanceModifier();
                    float baseWaterFog = 96.0f;
                    if (modifier != 1f) {
                        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
                        RenderSystem.setShaderFogStart(-8);
                        RenderSystem.setShaderFogEnd(baseWaterFog * modifier);
                    }
                }

            });
        }

        protected abstract int getTintColor(FluidStack stack);

        protected abstract int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos);

        protected Vector3f getCustomFogColor() {
            return null;
        }

        protected float getFogDistanceModifier() {
            return 1f;
        }

    }

    private static class SolidRenderedPlaceableFluidType extends TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }

        private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
                                                ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        /*
         * Removing alpha from tint prevents optifine from forcibly applying biome
         * colors to modded fluids (this workaround only works for fluids in the solid
         * render layer)
         */
        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}
