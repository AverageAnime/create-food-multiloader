package dev.averageanime.registry;

/**
 * Canonical fluid quantities, in millibuckets.
 *
 * <p>Every value here divides 1000 evenly, so any container converts to any other without
 * rounding. Recipe JSON is data and is parsed by Create directly, so these constants are not
 * read at recipe load time — they are the reference the recipe files, {@code Toolkit.jsx} and
 * the design docs are all kept consistent with.
 *
 * <p>mB is the unit on both loaders. Nothing in this mod uses Fabric's droplet representation,
 * so no conversion layer exists and recipe JSON is shared verbatim between NeoForge and Fabric.
 */
@SuppressWarnings("unused")
public final class FluidAmounts {
    private FluidAmounts() {}

    /** A bucket, a fluid block, or a full-batch mixing output. */
    public static final int BUCKET = 1000;

    /** A multi-serving mixing output. */
    public static final int BATCH = 750;

    /** Concentrated bottles — jams, milkshakes, molasses, egg whites, cakes, piping bags, fudge and toffee blocks. Four per bucket is {@link #SERVING}. */
    public static final int LARGE_SERVING = 500;

    /** One serving: a bowl, a standard bottle, a cupcake, a donut, a sauce portion. */
    public static final int SERVING = 250;

    /** A spread or coating: bread slices, toast, hollow chocolate, frying oil. */
    public static final int SLICE = 125;

    /** An ice cream stick or waffle cone. Same volume as {@link #SLICE}. */
    public static final int STICK = 125;
}
