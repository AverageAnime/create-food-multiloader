package dev.averageanime.registry.type;

import dev.averageanime.util.Tooltips;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public final class ItemEntry {

    private static final List<ItemEntry> REGISTRY = new ArrayList<>();
    public  static final List<ItemEntry> ALL       = Collections.unmodifiableList(REGISTRY);
    private static final Map<String, ItemEntry> BY_ID = new LinkedHashMap<>();

    public final String       id;
    public final ItemCategory category;
    public final int          nutrition;
    public final float        saturation;
    public final @Nullable Tooltips.TooltipSpec tip;
    public final List<EffectEntry>       effects;
    public final @Nullable String remainderId;

    private Supplier<net.minecraft.world.item.Item> registered;

    private ItemEntry(String id, ItemCategory category, int nutrition, float saturation,
                      @Nullable Tooltips.TooltipSpec tip, @Nullable String remainderId, List<EffectEntry> effects) {
        this.id          = id;
        this.category    = category;
        this.nutrition   = nutrition;
        this.saturation  = saturation;
        this.tip         = tip;
        this.remainderId = remainderId;
        this.effects     = List.copyOf(effects);
    }

    public void bind(Supplier<net.minecraft.world.item.Item> supplier) {
        if (this.registered != null) throw new IllegalStateException("Already bound: " + id);
        this.registered = supplier;
    }

    public net.minecraft.world.item.Item get() {
        if (registered == null) throw new IllegalStateException("Not yet registered: " + id);
        return registered.get();
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
        /** Normal food — EffectFood, fast=false */
        FOOD,
        /** Fast food — EffectFood, fast=true */
        FAST_FOOD,
        /** Bowl food — EffectFood, converts to BOWL, stacksTo(16) */
        BOWL_FOOD,
        /** Bowl food with craft remainder — EffectFood, converts to BOWL, stacksTo(16), craftRemainder(BOWL) */
        BOWL_FOOD_CR,
        /** Stick food — EffectFood, fast=true, converts to STICK */
        STICK_FOOD,
        /** Stick food with craft remainder — EffectFood, fast=true, converts to STICK, craftRemainder(STICK) */
        STICK_FOOD_CR,
        /** Bottle drink — EffectDrink, converts to GLASS_BOTTLE, stacksTo(16), craftRemainder(GLASS_BOTTLE) */
        BOTTLE,
        /** Plain item — no food properties */
        PLAIN,
        /** Plain item with craft remainder — no food, craftRemainder resolved from {@link ItemEntry#remainderId} */
        PLAIN_CR,
        /** Ingredient bottle — EffectDrink, stacksTo(16), craftRemainder(GLASS_BOTTLE), no food */
        INGREDIENT_BOTTLE,
        /** Ingredient bowl — plain Item, stacksTo(16), craftRemainder(BOWL) */
        INGREDIENT_BOWL,
        /** Piping bag — plain Item, stacksTo(2), craftRemainder(piping_bag) */
        PIPING_BAG,
    }
}