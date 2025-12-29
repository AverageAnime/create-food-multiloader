package dev.averageanime.neoforge.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.averageanime.CommonClass;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.joml.Vector3f;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.block.ModBlocks.BLOCKS;
import static dev.averageanime.neoforge.fluid.ModFluids.FLUIDS;
import static dev.averageanime.neoforge.fluid.ModFluids.FLUID_TYPES;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;

/**
 * Builder class for creating fluid entries.
 * Provides defaults for all properties.
 */
@SuppressWarnings("unused")
public class FluidEntry {
    private final String name;
    private Vector3f fogColor = null; // null means auto-detect from texture
    private int density = 1400;
    private int viscosity = 1500;
    private int lightLevel = 0;
    private int slopeFindDistance = 4;
    private int levelDecreasePerBlock = 3;
    private SoundEvent drinkSound = SoundEvents.HONEY_DRINK;

    /**
     * Creates a new FluidBuilder with the given name.
     * Auto-detects color from texture unless manually set.
     * @param name The name of the fluid (will be used for registration and texture paths)
     */
    public FluidEntry(String name) {
        this.name = name;
    }

    /**
     * Sets the fog color when the player is submerged in this fluid.
     * @param r Red component (0.0 - 1.0)
     * @param g Green component (0.0 - 1.0)
     * @param b Blue component (0.0 - 1.0)
     * @return This builder for chaining
     */
    public FluidEntry color(float r, float g, float b) {
        this.fogColor = new Vector3f(r, g, b);
        return this;
    }

    /**
     * Sets the fog color using a Vector3f.
     * @param color The fog color vector
     * @return This builder for chaining
     */
    public FluidEntry color(Vector3f color) {
        this.fogColor = color;
        return this;
    }

    /**
     * Sets the density and viscosity of the fluid.
     * @param density How dense the fluid is (affects movement)
     * @param viscosity How thick/sticky the fluid is (affects flow speed)
     * @return This builder for chaining
     */
    public FluidEntry physics(int density, int viscosity) {
        this.density = density;
        this.viscosity = viscosity;
        return this;
    }

    /**
     * Sets how the fluid flows and spreads.
     * @param slopeFindDistance How far the fluid searches for slopes to flow down
     * @param levelDecreasePerBlock How much the fluid level decreases per block
     * @return This builder for chaining
     */
    public FluidEntry flow(int slopeFindDistance, int levelDecreasePerBlock) {
        this.slopeFindDistance = slopeFindDistance;
        this.levelDecreasePerBlock = levelDecreasePerBlock;
        return this;
    }

    /**
     * Sets the light level emitted by this fluid.
     * @param lightLevel Light level (0-15)
     * @return This builder for chaining
     */
    public FluidEntry lightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
        return this;
    }

    /**
     * Sets the sound played when drinking this fluid.
     * @param sound The sound event
     * @return This builder for chaining
     */
    public FluidEntry drinkSound(SoundEvent sound) {
        this.drinkSound = sound;
        return this;
    }

    /**
     * Builds and registers the FluidEntry with all configured properties.
     * If no color was set, attempts to auto-detect from texture.
     * @return The created FluidEntry
     */
    public FluidType build() {
        // Auto-detect color from texture if not manually set
        Vector3f finalColor = fogColor;
        if (finalColor == null) {
            finalColor = extractColorFromTexture(name);
        }

        net.neoforged.neoforge.fluids.FluidType.Properties properties = net.neoforged.neoforge.fluids.FluidType.Properties.create()
                .lightLevel(lightLevel)
                .sound(SoundAction.get("drink"), drinkSound)
                .density(density)
                .viscosity(viscosity);

        return new FluidType(name, finalColor, properties, slopeFindDistance, levelDecreasePerBlock);
    }

    /**
     * Extracts the average color from a fluid texture.
     * Falls back to white if texture cannot be read.
     * @param fluidName The name of the fluid
     * @return The average color as a Vector3f (0.0-1.0 range)
     */
    private static Vector3f extractColorFromTexture(String fluidName) {
        try {
            // Try to load the texture from resources
            String texturePath = "/assets/" + CommonClass.ID + "/textures/fluid/" + fluidName + "_still.png";
            java.io.InputStream stream = FluidEntry.class.getResourceAsStream(texturePath);

            if (stream == null) {
                LOGGER.warn("Could not find texture for fluid: {} at path: {}", fluidName, texturePath);
                return new Vector3f(1.0f, 1.0f, 1.0f); // Default to white
            }

            // Read the image
            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(stream);
            stream.close();

            if (image == null) {
                LOGGER.warn("Could not read texture for fluid: {}", fluidName);
                return new Vector3f(1.0f, 1.0f, 1.0f);
            }

            // Calculate average color (weighted by alpha)
            long totalR = 0, totalG = 0, totalB = 0, totalAlpha = 0;
            int width = image.getWidth();
            int height = image.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    int alpha = (pixel >> 24) & 0xff;
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;

                    // Weight by alpha to ignore transparent pixels
                    totalR += red * alpha;
                    totalG += green * alpha;
                    totalB += blue * alpha;
                    totalAlpha += alpha;
                }
            }

            if (totalAlpha == 0) {
                LOGGER.warn("Texture for fluid {} is fully transparent", fluidName);
                return new Vector3f(1.0f, 1.0f, 1.0f);
            }

            // Calculate weighted average and convert to 0.0-1.0 range
            float r = (float) (totalR / totalAlpha) / 255.0f;
            float g = (float) (totalG / totalAlpha) / 255.0f;
            float b = (float) (totalB / totalAlpha) / 255.0f;

            return new Vector3f(r, g, b);

        } catch (Exception e) {
            LOGGER.error("Error extracting color from texture for fluid: {}", fluidName, e);
            return new Vector3f(1.0f, 1.0f, 1.0f); // Default to white on error
        }
    }

    /**
     * Represents a complete fluid registration including the fluid type, source, flowing variants,
     * block, and bucket item. This class handles all the NeoForge registration and client rendering.
     */
    @SuppressWarnings("NullableProblems")
    public static class FluidType {
        public final DeferredHolder<net.neoforged.neoforge.fluids.FluidType, net.neoforged.neoforge.fluids.FluidType> FLUID_TYPE;
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
        public FluidType(String name, Vector3f fogColor, net.neoforged.neoforge.fluids.FluidType.Properties fluidTypeProperties,
                         int slopeFindDistance, int levelDecreasePerBlock) {
            this.stillTexture = ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "fluid/" + name + "_still");
            this.flowingTexture = ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "fluid/" + name + "_flow");
            this.slopeFindDistance = slopeFindDistance;
            this.levelDecreasePerBlock = levelDecreasePerBlock;

            FLUID_TYPE = FLUID_TYPES.register(name, () -> new net.neoforged.neoforge.fluids.FluidType(fluidTypeProperties) {
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
}