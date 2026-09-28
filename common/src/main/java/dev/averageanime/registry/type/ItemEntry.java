package dev.averageanime.registry.type;

import dev.averageanime.createfood.lib.registry.LazyEntry;
import dev.averageanime.util.Tooltips;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public final class ItemEntry extends LazyEntry<net.minecraft.world.item.Item> {

    private static final List<ItemEntry> REGISTRY = new ArrayList<>();
    public  static final List<ItemEntry> ALL       = Collections.unmodifiableList(REGISTRY);
    private static final Map<String, ItemEntry> BY_ID = new LinkedHashMap<>();

    public final ItemCategory category;
    public final int          nutrition;
    public final float        saturation;
    public final @Nullable Tooltips.TooltipSpec tip;
    public final List<EffectEntry>       effects;
    public final @Nullable String remainderId;

    private ItemEntry(String id, ItemCategory category, int nutrition, float saturation,
                      @Nullable Tooltips.TooltipSpec tip, @Nullable String remainderId, List<EffectEntry> effects) {
        super(id);
        this.category    = category;
        this.nutrition   = nutrition;
        this.saturation  = saturation;
        this.tip         = tip;
        this.remainderId = remainderId;
        this.effects     = List.copyOf(effects);
    }

    /** {@link #getById} throws for an unknown id. */
    public static boolean exists(String id) {
        return BY_ID.containsKey(id);
    }

    public static ItemEntry getById(String id) {
        ItemEntry def = BY_ID.get(id);
        if (def == null) throw new IllegalArgumentException("No ItemDef with id: " + id);
        return def;
    }

    private static ItemEntry register(ItemEntry def) {
        REGISTRY.add(def);
        BY_ID.put(def.id, def);
        return def;
    }

    public static ItemEntry food(String id, int nut, float sat, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.FOOD, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static ItemEntry food(String id, int nut, float sat, Tooltips.TooltipSpec tip, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.FOOD, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static ItemEntry fastFood(String id, int nut, float sat, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.FAST_FOOD, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static ItemEntry fastFood(String id, int nut, float sat, Tooltips.TooltipSpec tip, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.FAST_FOOD, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static ItemEntry bowlFood(String id, int nut, float sat, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.BOWL_FOOD, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static ItemEntry bowlFood(String id, int nut, float sat, Tooltips.TooltipSpec tip, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.BOWL_FOOD, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static ItemEntry bowlFoodCr(String id, int nut, float sat, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.BOWL_FOOD_CR, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static ItemEntry bowlFoodCr(String id, int nut, float sat, Tooltips.TooltipSpec tip, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.BOWL_FOOD_CR, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static ItemEntry stickFood(String id, int nut, float sat, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.STICK_FOOD, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static ItemEntry stickFood(String id, int nut, float sat, Tooltips.TooltipSpec tip, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.STICK_FOOD, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static ItemEntry stickFoodCr(String id, int nut, float sat, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.STICK_FOOD_CR, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static ItemEntry stickFoodCr(String id, int nut, float sat, Tooltips.TooltipSpec tip, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.STICK_FOOD_CR, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static ItemEntry bottle(String id, int nut, float sat, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.BOTTLE, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static ItemEntry bottle(String id, int nut, float sat, Tooltips.TooltipSpec tip, EffectEntry... effects) {
        return register(new ItemEntry(id, ItemCategory.BOTTLE, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static ItemEntry plain(String id) {
        return register(new ItemEntry(id, ItemCategory.PLAIN, 0, 0, null, null, List.of()));
    }
    public static ItemEntry plain(String id, Tooltips.TooltipSpec tip) {
        return register(new ItemEntry(id, ItemCategory.PLAIN, 0, 0, tip, null, List.of()));
    }

    public static ItemEntry plainCr(String id, String remainderId) {
        return register(new ItemEntry(id, ItemCategory.PLAIN_CR, 0, 0, null, remainderId, List.of()));
    }
    public static ItemEntry plainCr(String id, String remainderId, Tooltips.TooltipSpec tip) {
        return register(new ItemEntry(id, ItemCategory.PLAIN_CR, 0, 0, tip, remainderId, List.of()));
    }

    public static ItemEntry ingredientBottle(String id) {
        return register(new ItemEntry(id, ItemCategory.INGREDIENT_BOTTLE, 0, 0, null, null, List.of()));
    }

    public static ItemEntry ingredientBowl(String id) {
        return register(new ItemEntry(id, ItemCategory.INGREDIENT_BOWL, 0, 0, null, null, List.of()));
    }

    public static ItemEntry pipingBag(String id) {
        return register(new ItemEntry(id, ItemCategory.PIPING_BAG, 0, 0, null, null, List.of()));
    }
    public static ItemEntry pipingBag(String id, Tooltips.TooltipSpec tip) {
        return register(new ItemEntry(id, ItemCategory.PIPING_BAG, 0, 0, tip, null, List.of()));
    }

    public enum ItemCategory {
        FOOD,
        FAST_FOOD,
        BOWL_FOOD,
        BOWL_FOOD_CR,
        STICK_FOOD,
        STICK_FOOD_CR,
        BOTTLE,
        PLAIN,
        PLAIN_CR,
        INGREDIENT_BOTTLE,
        INGREDIENT_BOWL,
        PIPING_BAG,
    }
}