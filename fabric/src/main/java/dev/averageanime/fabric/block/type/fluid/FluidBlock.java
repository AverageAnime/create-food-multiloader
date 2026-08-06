package dev.averageanime.fabric.block.type.fluid;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.registry.type.FluidEntry;
import io.github.fabricators_of_create.porting_lib.fluids.BaseFlowingFluid;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static dev.averageanime.fabric.CreateFood.LOGGER;

public class FluidBlock {

    /** Per-fluid submersion fog parameters (color + shader fog distances). */
    public record FogParams(Vector3f color, float start, float end) {}

    private static final Map<Fluid, FogParams> FOG_MAP = new HashMap<>();

    private final String name;
    private int slopeFindDistance = 4;
    private int levelDecreasePerBlock = 3;
    private float fogStart = FluidEntry.DEFAULT_FOG_START;
    private float fogEnd = FluidEntry.DEFAULT_FOG_END;

    public FlowingFluid SOURCE;
    public FlowingFluid FLOWING;
    public LiquidBlock BLOCK;
    public Item BUCKET;

    /** Null for fluids that are not this mod's — the "is ours" test used by the client mixins. */
    @Environment(EnvType.CLIENT)
    public static Vector3f getFogColor(Fluid fluid) {
        FogParams params = FOG_MAP.get(fluid);
        return params == null ? null : params.color();
    }

    @Environment(EnvType.CLIENT)
    public static FogParams getFogParams(Fluid fluid) {
        return FOG_MAP.get(fluid);
    }

    public FluidBlock(String name) {
        this.name = name;
    }

    public FluidBlock flow(int slopeFindDistance, int levelDecreasePerBlock) {
        this.slopeFindDistance = slopeFindDistance;
        this.levelDecreasePerBlock = levelDecreasePerBlock;
        return this;
    }

    public FluidBlock fog(float fogStart, float fogEnd) {
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
        return this;
    }

    public FluidBlock build() {
        FluidType fluidType = new FluidType(FluidType.Properties.create()
                .density(1400)
                .viscosity(1500));
        Registry.register(PortingLibFluids.FLUID_TYPES,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, name), fluidType);

        FluidBlock self = this;
        BaseFlowingFluid.Properties props = new BaseFlowingFluid.Properties(
                () -> fluidType,
                () -> self.SOURCE,
                () -> self.FLOWING)
                .slopeFindDistance(slopeFindDistance)
                .levelDecreasePerBlock(levelDecreasePerBlock)
                .block(() -> self.BLOCK)
                .bucket(() -> self.BUCKET)
                .tickRate(25);

        SOURCE = Registry.register(BuiltInRegistries.FLUID,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, name),
                new BaseFlowingFluid.Source(props));

        FLOWING = Registry.register(BuiltInRegistries.FLUID,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "flowing_" + name),
                new BaseFlowingFluid.Flowing(props));

        BLOCK = Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, name + "_block"),
                new LiquidBlock(SOURCE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

        BUCKET = Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, name + "_bucket"),
                new BucketItem(SOURCE, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

        return this;
    }

    @Environment(EnvType.CLIENT)
    public void registerClientRendering() {
        ResourceLocation stillTexture = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "fluid/" + name + "_still");
        ResourceLocation flowingTexture = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "fluid/" + name + "_flow");
        FluidRenderHandlerRegistry.INSTANCE.register(SOURCE, FLOWING,
                new SimpleFluidRenderHandler(stillTexture, flowingTexture));

        FogParams params = new FogParams(extractColorFromTexture(name), fogStart, fogEnd);
        FOG_MAP.put(SOURCE, params);
        FOG_MAP.put(FLOWING, params);
    }

    @Environment(EnvType.CLIENT)
    private static Vector3f extractColorFromTexture(String fluidName) {
        try {
            String texturePath = "/assets/" + CreateFoodCommon.MOD_ID + "/textures/fluid/" + fluidName + "_still.png";
            java.io.InputStream stream = FluidBlock.class.getResourceAsStream(texturePath);
            if (stream == null) {
                LOGGER.warn("Create: Food - Could not find fluid texture: {}", texturePath);
                return new Vector3f(1.0f, 1.0f, 1.0f);
            }
            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(stream);
            stream.close();
            if (image == null) return new Vector3f(1.0f, 1.0f, 1.0f);

            long totalR = 0, totalG = 0, totalB = 0, totalAlpha = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);
                    int alpha = (pixel >> 24) & 0xff;
                    totalR += ((pixel >> 16) & 0xff) * alpha;
                    totalG += ((pixel >> 8) & 0xff) * alpha;
                    totalB += (pixel & 0xff) * alpha;
                    totalAlpha += alpha;
                }
            }
            if (totalAlpha == 0) return new Vector3f(1.0f, 1.0f, 1.0f);
            return new Vector3f(
                    (float)(totalR / totalAlpha) / 255.0f,
                    (float)(totalG / totalAlpha) / 255.0f,
                    (float)(totalB / totalAlpha) / 255.0f);
        } catch (Exception e) {
            LOGGER.error("Create: Food - Error extracting color for fluid: {}", fluidName, e);
            return new Vector3f(1.0f, 1.0f, 1.0f);
        }
    }
}
