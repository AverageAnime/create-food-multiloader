package dev.averageanime.registry.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Platform-neutral definition of a single registered fluid.
 * <p>
 * Populated at class-load time via static fields in {@link dev.averageanime.registry.FluidRegistry}.
 * Platforms iterate {@link #ALL} to register their platform-specific fluid objects.
 * <p>
 * {@code slope} and {@code level} mirror the {@code flow(slope, level)} call on the platform
 * FluidEntry builder. A value of {@code -1} means "no custom flow" (use platform defaults).
 */
public final class Fluid {

    private static final List<Fluid> REGISTRY = new ArrayList<>();
    public  static final List<Fluid> ALL      = Collections.unmodifiableList(REGISTRY);

    public final String id;
    public final int    slope;
    public final int    level;

    private Fluid(String id, int slope, int level) {
        this.id    = id;
        this.slope = slope;
        this.level = level;
    }

    private static Fluid register(Fluid def) {
        REGISTRY.add(def);
        return def;
    }

    /** Fluid with default flow physics. */
    public static Fluid fluid(String id) {
        return register(new Fluid(id, -1, -1));
    }

    /** Fluid with explicit {@code flow(slope, level)} physics. */
    public static Fluid fluid(String id, int slope, int level) {
        return register(new Fluid(id, slope, level));
    }

    /** Triggers class initialization; call before iterating {@link #ALL}. */
    public static void init() {}
}
