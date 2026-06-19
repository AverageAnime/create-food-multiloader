package net.averageanime.createfood.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.averageanime.createfood.CreateFood;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Builder that registers a complete Forge fluid (type, source, flowing, block, bucket)
 * without depending on Create's CreateRegistrate or the Registrate library.
 */
public class FluidEntry {

    public static final DeferredRegister<FluidType> FLUID_TYPE_REGISTER =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CreateFood.ID);
    public static final DeferredRegister<Fluid> FLUID_REGISTER =
            DeferredRegister.create(ForgeRegistries.FLUIDS, CreateFood.ID);
    public static final DeferredRegister<net.minecraft.world.level.block.Block> FLUID_BLOCK_REGISTER =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateFood.ID);
    public static final DeferredRegister<Item> FLUID_ITEM_REGISTER =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateFood.ID);

    public static void registerAll(IEventBus eventBus) {
        FLUID_TYPE_REGISTER.register(eventBus);
        FLUID_REGISTER.register(eventBus);
        FLUID_BLOCK_REGISTER.register(eventBus);
        FLUID_ITEM_REGISTER.register(eventBus);
    }

    // ── builder fields ────────────────────────────────────────────────────────

    private final String name;
    private final Vector3f fogColor;
    private int density = 1400;
    private int viscosity = 1500;
    private int slopeFindDistance = 4;
    private int levelDecreasePerBlock = 3;

    public FluidEntry(String name, int rgbColor) {
        this.name = name;
        this.fogColor = new Vector3f(
                ((rgbColor >> 16) & 0xFF) / 255.0f,
                ((rgbColor >> 8) & 0xFF) / 255.0f,
                (rgbColor & 0xFF) / 255.0f);
    }

    public FluidEntry physics(int density, int viscosity) {
        this.density = density;
        this.viscosity = viscosity;
        return this;
    }

    public FluidEntry flow(int slopeFindDistance, int levelDecreasePerBlock) {
        this.slopeFindDistance = slopeFindDistance;
        this.levelDecreasePerBlock = levelDecreasePerBlock;
        return this;
    }

    // ── build ─────────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public Type build() {
        final String n = name;
        final Vector3f color = fogColor;
        final int d = density, v = viscosity, s = slopeFindDistance, l = levelDecreasePerBlock;

        RegistryObject<FluidType> typeObj = FLUID_TYPE_REGISTER.register(n,
                () -> buildFluidType(n, color, d, v));

        // Arrays allow mutable capture in lambdas while remaining effectively-final
        RegistryObject<ForgeFlowingFluid>[] src = new RegistryObject[1];
        RegistryObject<ForgeFlowingFluid>[] flo = new RegistryObject[1];
        RegistryObject<LiquidBlock>[] blk = new RegistryObject[1];
        RegistryObject<Item>[] bkt = new RegistryObject[1];

        src[0] = FLUID_REGISTER.register(n,
                (Supplier<ForgeFlowingFluid>) () -> new ForgeFlowingFluid.Source(makeProps(typeObj, src[0], flo[0], blk[0], bkt[0], s, l)));

        flo[0] = FLUID_REGISTER.register("flowing_" + n,
                (Supplier<ForgeFlowingFluid>) () -> new ForgeFlowingFluid.Flowing(makeProps(typeObj, src[0], flo[0], blk[0], bkt[0], s, l)));

        blk[0] = FLUID_BLOCK_REGISTER.register(n + "_block",
                (Supplier<LiquidBlock>) () -> new LiquidBlock(src[0],
                        BlockBehaviour.Properties.copy(Blocks.WATER)
                                .noCollission().strength(100.0F).noLootTable()));

        bkt[0] = FLUID_ITEM_REGISTER.register(n + "_bucket",
                (Supplier<Item>) () -> new BucketItem(src[0],
                        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

        return new Type(typeObj, src[0], flo[0], blk[0], bkt[0]);
    }

    private static ForgeFlowingFluid.Properties makeProps(
            RegistryObject<FluidType> type,
            RegistryObject<ForgeFlowingFluid> source,
            RegistryObject<ForgeFlowingFluid> flowing,
            RegistryObject<LiquidBlock> block,
            RegistryObject<Item> bucket,
            int slope, int level) {
        return new ForgeFlowingFluid.Properties(type, source, flowing)
                .slopeFindDistance(slope)
                .levelDecreasePerBlock(level)
                .block(block)
                .bucket(bucket)
                .tickRate(25)
                .explosionResistance(100f);
    }

    private static FluidType buildFluidType(String name, Vector3f fogColor, int density, int viscosity) {
        ResourceLocation still = new ResourceLocation(CreateFood.ID, "fluid/" + name + "_still");
        ResourceLocation flowing = new ResourceLocation(CreateFood.ID, "fluid/" + name + "_flow");

        return new FluidType(FluidType.Properties.create().density(density).viscosity(viscosity)) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override
                    public ResourceLocation getStillTexture() { return still; }

                    @Override
                    public ResourceLocation getFlowingTexture() { return flowing; }

                    @Override
                    public int getTintColor(FluidStack stack) { return 0xffffffff; }

                    @Override
                    public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                        return 0xffffffff;
                    }

                    @Override
                    public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick,
                            ClientLevel level, int renderDistance,
                            float darkenWorldAmount, Vector3f fluidFogColor) {
                        return fogColor;
                    }

                    @Override
                    public void modifyFogRender(Camera camera, FogRenderer.FogMode mode,
                            float renderDistance, float partialTick,
                            float nearDistance, float farDistance, FogShape shape) {
                        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
                        RenderSystem.setShaderFogStart(-8);
                        RenderSystem.setShaderFogEnd(96.0f * (1f / 32f));
                    }
                });
            }
        };
    }

    // ── result record ─────────────────────────────────────────────────────────

    public record Type(
            RegistryObject<FluidType> FLUID_TYPE,
            RegistryObject<ForgeFlowingFluid> SOURCE,
            RegistryObject<ForgeFlowingFluid> FLOWING,
            RegistryObject<LiquidBlock> BLOCK,
            RegistryObject<Item> BUCKET
    ) {}
}
