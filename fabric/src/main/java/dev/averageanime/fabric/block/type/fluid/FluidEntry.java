package dev.averageanime.fabric.block.type.fluid;

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
import static dev.averageanime.fabric.CreateFood.MOD_ID;

public class FluidEntry {

    private static final Map<Fluid, Vector3f> FOG_COLOR_MAP = new HashMap<>();

    private final String name;
    private int slopeFindDistance = 4;
    private int levelDecreasePerBlock = 3;

    public FlowingFluid SOURCE;
    public FlowingFluid FLOWING;
    public LiquidBlock BLOCK;
    public Item BUCKET;

    @Environment(EnvType.CLIENT)
    public static Vector3f getFogColor(Fluid fluid) {
        return FOG_COLOR_MAP.get(fluid);
    }

    public FluidEntry(String name) {
        this.name = name;
    }

    public FluidEntry flow(int slopeFindDistance, int levelDecreasePerBlock) {
        this.slopeFindDistance = slopeFindDistance;
        this.levelDecreasePerBlock = levelDecreasePerBlock;
        return this;
    }

    public FluidEntry build() {
        FluidType fluidType = new FluidType(FluidType.Properties.create()
                .density(1400)
                .viscosity(1500));
        Registry.register(PortingLibFluids.FLUID_TYPES,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name), fluidType);

        FluidEntry self = this;
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
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BaseFlowingFluid.Source(props));

        FLOWING = Registry.register(BuiltInRegistries.FLUID,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "flowing_" + name),
                new BaseFlowingFluid.Flowing(props));

        BLOCK = Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name + "_block"),
                new LiquidBlock(SOURCE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

        BUCKET = Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name + "_bucket"),
                new BucketItem(SOURCE, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

        return this;
    }

    @Environment(EnvType.CLIENT)
    public void registerClientRendering() {
        ResourceLocation stillTexture = ResourceLocation.fromNamespaceAndPath(MOD_ID, "fluid/" + name + "_still");
        ResourceLocation flowingTexture = ResourceLocation.fromNamespaceAndPath(MOD_ID, "fluid/" + name + "_flow");
        FluidRenderHandlerRegistry.INSTANCE.register(SOURCE, FLOWING,
                new SimpleFluidRenderHandler(stillTexture, flowingTexture));

        Vector3f color = extractColorFromTexture(name);
        FOG_COLOR_MAP.put(SOURCE, color);
        FOG_COLOR_MAP.put(FLOWING, color);
    }

    @Environment(EnvType.CLIENT)
    private static Vector3f extractColorFromTexture(String fluidName) {
        try {
            String texturePath = "/assets/" + MOD_ID + "/textures/fluid/" + fluidName + "_still.png";
            java.io.InputStream stream = FluidEntry.class.getResourceAsStream(texturePath);
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
