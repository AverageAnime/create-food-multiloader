package dev.averageanime.registry.type;

import dev.averageanime.util.Tooltips;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

/** Platform-neutral definition and live accessor for a single registered block. */
public final class Block {

    private static final List<Block>        REGISTRY = new ArrayList<>();
    private static final Map<String, Block> BY_ID    = new HashMap<>();
    public  static final List<Block>        ALL      = Collections.unmodifiableList(REGISTRY);

    public final String        id;
    public final BlockCategory category;
    /**
     * Registry id of the slice item yielded when eating from this block
     * (used by {@link BlockCategory#CAKE}, {@link BlockCategory#COOKED_PIE},
     * {@link BlockCategory#COOKED_PIZZA}, {@link BlockCategory#CHEESE},
     * {@link BlockCategory#GYRO_MEAT}).  {@code null} for blocks with no slice item.
     */
    public final @Nullable String sliceItemId;
    /**
     * Block-item tooltip shown on hover.
     * Applied by NeoForge registration only; Fabric block-item registration does not support this field.
     */
    public final @Nullable Tooltips.Tip tip;

    private Supplier<net.minecraft.world.level.block.Block> registered;

    private Block(String id, BlockCategory category,
                  @Nullable String sliceItemId, @Nullable Tooltips.Tip tip) {
        this.id          = id;
        this.category    = category;
        this.sliceItemId = sliceItemId;
        this.tip         = tip;
    }

    public void bind(Supplier<net.minecraft.world.level.block.Block> supplier) {
        if (this.registered != null) throw new IllegalStateException("Already bound: " + id);
        this.registered = supplier;
    }

    public net.minecraft.world.level.block.Block get() {
        if (registered == null) throw new IllegalStateException("Not yet registered: " + id);
        return registered.get();
    }

    public static @Nullable Block getById(String id) {
        return BY_ID.get(id);
    }

    private static Block register(Block def) {
        REGISTRY.add(def);
        BY_ID.put(def.id, def);
        return def;
    }

    public static Block cake(String id, String sliceItemId) {
        return register(new Block(id, BlockCategory.CAKE, sliceItemId, null));
    }
    public static Block cake(String id, String sliceItemId, Tooltips.Tip tip) {
        return register(new Block(id, BlockCategory.CAKE, sliceItemId, tip));
    }

    public static Block cakeBase(String id) {
        return register(new Block(id, BlockCategory.CAKE_BASE, null, null));
    }
    public static Block cakeBase(String id, Tooltips.Tip tip) {
        return register(new Block(id, BlockCategory.CAKE_BASE, null, tip));
    }

    public static Block rawPie(String id) {
        return register(new Block(id, BlockCategory.RAW_PIE, null, null));
    }
    public static Block rawPie(String id, Tooltips.Tip tip) {
        return register(new Block(id, BlockCategory.RAW_PIE, null, tip));
    }

    public static Block rawPizza(String id) {
        return register(new Block(id, BlockCategory.RAW_PIZZA, null, null));
    }
    public static Block rawPizza(String id, Tooltips.Tip tip) {
        return register(new Block(id, BlockCategory.RAW_PIZZA, null, tip));
    }

    public static Block cookedPie(String id, String sliceItemId) {
        return register(new Block(id, BlockCategory.COOKED_PIE, sliceItemId, null));
    }
    public static Block cookedPie(String id, String sliceItemId, Tooltips.Tip tip) {
        return register(new Block(id, BlockCategory.COOKED_PIE, sliceItemId, tip));
    }

    public static Block cookedPizza(String id, String sliceItemId) {
        return register(new Block(id, BlockCategory.COOKED_PIZZA, sliceItemId, null));
    }
    public static Block cookedPizza(String id, String sliceItemId, Tooltips.Tip tip) {
        return register(new Block(id, BlockCategory.COOKED_PIZZA, sliceItemId, tip));
    }

    /** Waffles share the {@link BlockCategory#COOKED_PIZZA} category (use PizzaBlock). */
    public static Block waffle(String id, String sliceItemId) {
        return register(new Block(id, BlockCategory.COOKED_PIZZA, sliceItemId, null));
    }
    public static Block waffle(String id, String sliceItemId, Tooltips.Tip tip) {
        return register(new Block(id, BlockCategory.COOKED_PIZZA, sliceItemId, tip));
    }

    public static Block gelatin(String id) {
        return register(new Block(id, BlockCategory.GELATIN, null, null));
    }

    public static Block cheese(String id, String sliceItemId) {
        return register(new Block(id, BlockCategory.CHEESE, sliceItemId, null));
    }

    public static Block gyroMeat(String id, String sliceItemId) {
        return register(new Block(id, BlockCategory.GYRO_MEAT, sliceItemId, null));
    }

    public enum BlockCategory {
        CAKE,
        CAKE_BASE,
        RAW_PIE,
        RAW_PIZZA,
        COOKED_PIE,
        COOKED_PIZZA,
        GELATIN,
        CHEESE,
        GYRO_MEAT,
    }
}