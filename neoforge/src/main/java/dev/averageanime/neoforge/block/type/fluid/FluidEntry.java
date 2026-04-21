package dev.averageanime.neoforge.block.type.fluid;

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
import dev.averageanime.CommonClass;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector3f;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.block.ModBlocks.BLOCKS;
import static dev.averageanime.neoforge.block.ModFluids.FLUIDS;
import static dev.averageanime.neoforge.block.ModFluids.FLUID_TYPES;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;

@SuppressWarnings("unused")
public class FluidEntry {
    private final String name;
    private Vector3f fogColor = null;
    private int density = 1400;
    private int viscosity = 1500;
    private int lightLevel = 0;
    private int slopeFindDistance = 4;
    private int levelDecreasePerBlock = 3;
    private SoundEvent drinkSound = SoundEvents.HONEY_DRINK;

    public FluidEntry(String name) {
        this.name = name;
    }

    public FluidEntry color(float r, float g, float b) {
        this.fogColor = new Vector3f(r, g, b);
        return this;
    }

    public FluidEntry color(Vector3f color) {
        this.fogColor = color;
        return this;
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

    public FluidEntry lightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
        return this;
    }

    public FluidEntry drinkSound(SoundEvent sound) {
        this.drinkSound = sound;
        return this;
    }

    public FluidType build() {
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

    private static Vector3f extractColorFromTexture(String fluidName) {
        try {
            String texturePath = "/assets/" + CommonClass.MOD_ID + "/textures/fluid/" + fluidName + "_still.png";
            java.io.InputStream stream = FluidEntry.class.getResourceAsStream(texturePath);

            if (stream == null) {
                LOGGER.warn("Could not find texture for fluid: {} at path: {}", fluidName, texturePath);
                return new Vector3f(1.0f, 1.0f, 1.0f); // Default to white
            }

            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(stream);
            stream.close();

            if (image == null) {
                LOGGER.warn("Could not read texture for fluid: {}", fluidName);
                return new Vector3f(1.0f, 1.0f, 1.0f);
            }

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

            float r = (float) (totalR / totalAlpha) / 255.0f;
            float g = (float) (totalG / totalAlpha) / 255.0f;
            float b = (float) (totalB / totalAlpha) / 255.0f;

            return new Vector3f(r, g, b);

        } catch (Exception e) {
            LOGGER.error("Error extracting color from texture for fluid: {}", fluidName, e);
            return new Vector3f(1.0f, 1.0f, 1.0f);
        }
    }

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

        public FluidType(String name, Vector3f fogColor, net.neoforged.neoforge.fluids.FluidType.Properties fluidTypeProperties,
                         int slopeFindDistance, int levelDecreasePerBlock) {
            this.stillTexture = ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, "fluid/" + name + "_still");
            this.flowingTexture = ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, "fluid/" + name + "_flow");
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