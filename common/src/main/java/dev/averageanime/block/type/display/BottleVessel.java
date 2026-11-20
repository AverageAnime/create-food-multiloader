package dev.averageanime.block.type.display;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * The glass shape an emptied {@link BottleFoodBlock} leaves behind, so the {@link EmptyBottleBlock} looks
 * like the drink it held. Each value has a {@code bottle_block_<name>} model (STANDARD uses
 * {@code bottle_block} itself) holding only the glass of the matching {@code bottle_base_*} model.
 */
public enum BottleVessel implements StringRepresentable {
    STANDARD("standard", 9.0),
    MUG("mug", 8.0),
    JAR("jar", 8.0),
    TALL("tall", 12.0),
    GOBLET("goblet", 12.0),
    JUICE("juice", 10.0);

    private final String name;
    private final VoxelShape shape;

    BottleVessel(String name, double heightInPixels) {
        this.name = name;
        this.shape = Block.box(5.5, 0.0, 5.5, 10.5, heightInPixels, 10.5);
    }

    private static final BottleVessel[] VALUES = values();

    public VoxelShape shape() { return shape; }

    public BottleVessel next() { return VALUES[(ordinal() + 1) % VALUES.length]; }

    @Override
    public @NotNull String getSerializedName() { return name; }

    /**
     * The drink models' {@code parent} is what actually decides the glass; this list mirrors those parents
     * and has to follow them when a drink is added or re-parented. First match wins, so milkshakes
     * ({@code chocolate_milkshake} contains {@code chocolate_milk}) must be caught before the mug rule.
     * The heights match the full bottle's in {@code DisplayRegistry}, which is keyed differently and
     * defaults to 12, so the two are kept in step by hand.
     */
    public static BottleVessel forBlockName(String path) {
        if (path.contains("_trifle_bottle")) return JAR;
        if (path.contains("milkshake") || path.contains("custard") || path.contains("creme_brulee")) return GOBLET;
        if (path.contains("cream_frosting") || path.contains("molasses") || path.contains("condensed_milk")
                || path.contains("sour_cream") || path.contains("vegetable_oil") || path.contains("vinegar")
                || path.contains("yogurt")) return TALL;
        if (path.contains("sugar_cane_juice")) return STANDARD;
        if (path.contains("_juice_bottle") || path.contains("builders_tea")) return JUICE;
        if (path.contains("chocolate_bottle") || path.contains("chocolate_milk")
                || path.contains("fruit_smoothie")) return MUG;
        return STANDARD;
    }
}
