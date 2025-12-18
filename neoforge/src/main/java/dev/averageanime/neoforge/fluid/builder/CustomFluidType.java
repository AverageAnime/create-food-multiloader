package dev.averageanime.neoforge.fluid.builder;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.averageanime.CommonClass;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.joml.Vector3f;

import static dev.averageanime.neoforge.block.ModBlocks.BLOCKS;
import static dev.averageanime.neoforge.fluid.ModFluids.FLUIDS;
import static dev.averageanime.neoforge.fluid.ModFluids.FLUID_TYPES;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;

/**
 * Represents a complete fluid registration including the fluid type, source, flowing variants,
 * block, and bucket item. This class handles all the NeoForge registration and client rendering.
 */
@SuppressWarnings("NullableProblems")
public class CustomFluidType {
    public final DeferredHolder<FluidType, FluidType> FLUID_TYPE;
    public final DeferredHolder<Fluid, FlowingFluid> SOURCE;
    public final DeferredHolder<Fluid, FlowingFluid> FLOWING;
    public final DeferredBlock<LiquidBlock> BLOCK;
    public final DeferredItem<Item> BUCKET;
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final int slopeFindDistance;
    private final int levelDecreasePerBlock;

    /**
     * Creates a new FluidType and registers all associated objects.
     * This should typically only be called from FluidBuilder.build().
     *
     * @param name The name of the fluid
     * @param fogColor The fog color to use when submerged
     * @param fluidTypeProperties The fluid type properties
     * @param slopeFindDistance How far the fluid searches for slopes
     * @param levelDecreasePerBlock How much the fluid level decreases per block
     */
    public CustomFluidType(String name, Vector3f fogColor, FluidType.Properties fluidTypeProperties,
                           int slopeFindDistance, int levelDecreasePerBlock) {
        this.stillTexture = ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "fluid/" + name + "_still");
        this.flowingTexture = ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "fluid/" + name + "_flow");
        this.slopeFindDistance = slopeFindDistance;
        this.levelDecreasePerBlock = levelDecreasePerBlock;

        FLUID_TYPE = FLUID_TYPES.register(name, () -> new FluidType(fluidTypeProperties) {
            @SuppressWarnings("removal")
            @Override
            public void initializeClient(java.util.function.Consumer<IClientFluidTypeExtensions> consumer) {
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
                    public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                   int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                        return fogColor;
                    }

                    @Override
                    public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance,
                                                float partialTick, float nearDistance, float farDistance, FogShape shape) {
                        RenderSystem.setShaderFogColor(fogColor.x(), fogColor.y(), fogColor.z());
                        RenderSystem.setShaderFogStart(0.5f);
                        RenderSystem.setShaderFogEnd(1.5f);
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

    /**
     * Creates the fluid properties for this fluid entry.
     * @return The configured BaseFlowingFluid.Properties
     */
    private BaseFlowingFluid.Properties createFluidPropertiesInternal() {
        return new BaseFlowingFluid.Properties(
                FLUID_TYPE,
                SOURCE,
                FLOWING)
                .slopeFindDistance(this.slopeFindDistance)
                .levelDecreasePerBlock(this.levelDecreasePerBlock)
                .block(BLOCK)
                .bucket(BUCKET)
                .tickRate(25);
    }
}