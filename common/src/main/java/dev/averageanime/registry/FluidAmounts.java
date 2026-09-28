package dev.averageanime.registry;

@SuppressWarnings("unused")
public final class FluidAmounts {
    private FluidAmounts() {}

    /** Bucket and fluid block */
    public static final int BUCKET = 1000;

    /** A multi-serving mixing output. */
    public static final int BATCH = 750;

    /** One serving: jams, milkshakes, molasses, cakes, fudge. */
    public static final int LARGE_SERVING = 500;

    /** One serving: standard bottle/bowl, cupcakes, donuts, sauce portion. */
    public static final int SERVING = 250;

    /** One serving: bread slices, toast, hollow chocolate, ice cream stick. */
    public static final int SMALL_SERVING = 125;

}
