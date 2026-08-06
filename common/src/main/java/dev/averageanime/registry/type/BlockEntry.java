package dev.averageanime.registry.type;

import dev.averageanime.util.Tooltips;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public final class BlockEntry {

    private static final List<BlockEntry>        REGISTRY = new ArrayList<>();
    private static final Map<String, BlockEntry> BY_ID    = new HashMap<>();
    public  static final List<BlockEntry>        ALL      = Collections.unmodifiableList(REGISTRY);

    public final String        id;
    public final BlockCategory category;
    public final @Nullable String sliceItemId;
    public final @Nullable Tooltips.TooltipSpec tip;

    private Supplier<net.minecraft.world.level.block.Block> registered;

    private BlockEntry(String id, BlockCategory category,
                       @Nullable String sliceItemId, @Nullable Tooltips.TooltipSpec tip) {
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

    public static @Nullable BlockEntry getById(String id) {
        return BY_ID.get(id);
    }

    private static BlockEntry register(BlockEntry def) {
        REGISTRY.add(def);
        BY_ID.put(def.id, def);
        return def;
    }

    public static BlockEntry cake(String id, String sliceItemId) {
        return register(new BlockEntry(id, BlockCategory.CAKE, sliceItemId, null));
    }
    public static BlockEntry cake(String id, String sliceItemId, Tooltips.TooltipSpec tip) {
        return register(new BlockEntry(id, BlockCategory.CAKE, sliceItemId, tip));
    }

    public static BlockEntry cakeBase(String id) {
        return register(new BlockEntry(id, BlockCategory.CAKE_BASE, null, null));
    }
    public static BlockEntry cakeBase(String id, Tooltips.TooltipSpec tip) {
        return register(new BlockEntry(id, BlockCategory.CAKE_BASE, null, tip));
    }

    public static BlockEntry rawPie(String id) {
        return register(new BlockEntry(id, BlockCategory.RAW_PIE, null, null));
    }
    public static BlockEntry rawPie(String id, Tooltips.TooltipSpec tip) {
        return register(new BlockEntry(id, BlockCategory.RAW_PIE, null, tip));
    }

    public static BlockEntry rawPizza(String id) {
        return register(new BlockEntry(id, BlockCategory.RAW_PIZZA, null, null));
    }
    public static BlockEntry rawPizza(String id, Tooltips.TooltipSpec tip) {
        return register(new BlockEntry(id, BlockCategory.RAW_PIZZA, null, tip));
    }

    public static BlockEntry cookedPie(String id, String sliceItemId) {
        return register(new BlockEntry(id, BlockCategory.COOKED_PIE, sliceItemId, null));
    }
    public static BlockEntry cookedPie(String id, String sliceItemId, Tooltips.TooltipSpec tip) {
        return register(new BlockEntry(id, BlockCategory.COOKED_PIE, sliceItemId, tip));
    }

    public static BlockEntry cookedPizza(String id, String sliceItemId) {
        return register(new BlockEntry(id, BlockCategory.COOKED_PIZZA, sliceItemId, null));
    }
    public static BlockEntry cookedPizza(String id, String sliceItemId, Tooltips.TooltipSpec tip) {
        return register(new BlockEntry(id, BlockCategory.COOKED_PIZZA, sliceItemId, tip));
    }

    public static BlockEntry waffle(String id, String sliceItemId) {
        return register(new BlockEntry(id, BlockCategory.COOKED_PIZZA, sliceItemId, null));
    }
    public static BlockEntry waffle(String id, String sliceItemId, Tooltips.TooltipSpec tip) {
        return register(new BlockEntry(id, BlockCategory.COOKED_PIZZA, sliceItemId, tip));
    }

    public static BlockEntry gelatin(String id) {
        return register(new BlockEntry(id, BlockCategory.GELATIN, null, null));
    }

    public static BlockEntry cheese(String id, String sliceItemId) {
        return register(new BlockEntry(id, BlockCategory.CHEESE, sliceItemId, null));
    }

    public static BlockEntry gyroMeat(String id, String sliceItemId) {
        return register(new BlockEntry(id, BlockCategory.GYRO_MEAT, sliceItemId, null));
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