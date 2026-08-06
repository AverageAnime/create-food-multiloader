package dev.averageanime.registry.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FluidEntry {

    public static final float DEFAULT_FOG_START = 0.5f;
    public static final float DEFAULT_FOG_END   = 1.5f;

    private static final List<FluidEntry> REGISTRY = new ArrayList<>();
    public  static final List<FluidEntry> ALL      = Collections.unmodifiableList(REGISTRY);

    public final String id;
    public final int    slope;
    public final int    level;
    public final float  fogStart;
    public final float  fogEnd;

    private FluidEntry(String id, int slope, int level, float fogStart, float fogEnd) {
        this.id       = id;
        this.slope    = slope;
        this.level    = level;
        this.fogStart = fogStart;
        this.fogEnd   = fogEnd;
    }

    private static FluidEntry register(FluidEntry def) {
        REGISTRY.add(def);
        return def;
    }

    public static FluidEntry fluid(String id) {
        return register(new FluidEntry(id, -1, -1, DEFAULT_FOG_START, DEFAULT_FOG_END));
    }

    public static FluidEntry fluid(String id, int slope, int level) {
        return register(new FluidEntry(id, slope, level, DEFAULT_FOG_START, DEFAULT_FOG_END));
    }

    public static FluidEntry fluid(String id, float fogStart, float fogEnd) {
        return register(new FluidEntry(id, -1, -1, fogStart, fogEnd));
    }

    public static FluidEntry fluid(String id, int slope, int level, float fogStart, float fogEnd) {
        return register(new FluidEntry(id, slope, level, fogStart, fogEnd));
    }

    public static void init() {}
}