package dev.averageanime.neoforge.fluid.builder;

import dev.averageanime.CommonClass;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector3f;

import static dev.averageanime.neoforge.CreateFood.LOGGER;

/**
 * Builder class for creating fluid entries.
 * Provides defaults for all properties.
 */
@SuppressWarnings("unused")
public class CustomFluid {
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
    public CustomFluid(String name) {
        this.name = name;
    }

    /**
     * Sets the fog color when the player is submerged in this fluid.
     * @param r Red component (0.0 - 1.0)
     * @param g Green component (0.0 - 1.0)
     * @param b Blue component (0.0 - 1.0)
     * @return This builder for chaining
     */
    public CustomFluid color(float r, float g, float b) {
        this.fogColor = new Vector3f(r, g, b);
        return this;
    }

    /**
     * Sets the fog color using a Vector3f.
     * @param color The fog color vector
     * @return This builder for chaining
     */
    public CustomFluid color(Vector3f color) {
        this.fogColor = color;
        return this;
    }

    /**
     * Sets the density and viscosity of the fluid.
     * @param density How dense the fluid is (affects movement)
     * @param viscosity How thick/sticky the fluid is (affects flow speed)
     * @return This builder for chaining
     */
    public CustomFluid physics(int density, int viscosity) {
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
    public CustomFluid flow(int slopeFindDistance, int levelDecreasePerBlock) {
        this.slopeFindDistance = slopeFindDistance;
        this.levelDecreasePerBlock = levelDecreasePerBlock;
        return this;
    }

    /**
     * Sets the light level emitted by this fluid.
     * @param lightLevel Light level (0-15)
     * @return This builder for chaining
     */
    public CustomFluid lightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
        return this;
    }

    /**
     * Sets the sound played when drinking this fluid.
     * @param sound The sound event
     * @return This builder for chaining
     */
    public CustomFluid drinkSound(SoundEvent sound) {
        this.drinkSound = sound;
        return this;
    }

    /**
     * Builds and registers the FluidEntry with all configured properties.
     * If no color was set, attempts to auto-detect from texture.
     * @return The created FluidEntry
     */
    public CustomFluidType build() {
        // Auto-detect color from texture if not manually set
        Vector3f finalColor = fogColor;
        if (finalColor == null) {
            finalColor = extractColorFromTexture(name);
        }

        FluidType.Properties properties = FluidType.Properties.create()
                .lightLevel(lightLevel)
                .sound(SoundAction.get("drink"), drinkSound)
                .density(density)
                .viscosity(viscosity);

        return new CustomFluidType(name, finalColor, properties, slopeFindDistance, levelDecreasePerBlock);
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
            java.io.InputStream stream = CustomFluid.class.getResourceAsStream(texturePath);

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
}