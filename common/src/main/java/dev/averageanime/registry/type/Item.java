package dev.averageanime.registry.type;

import dev.averageanime.registry.ItemRegistry;
import dev.averageanime.util.Tooltips;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

/**
 * Platform-neutral definition of a single registered item.
 * <p>
 * Acts as both the definition (populated at class-load time via static fields in
 * {@link ItemRegistry}) and the live accessor (via
 * {@link #bind}/{@link #get}) once a platform has registered the item.
 * <p>
 * {@link #ALL} is auto-populated in declaration order as {@code ModItemDefs} loads —
 * no manual list maintenance is needed.
 */
public final class Item {

    private static final List<Item> REGISTRY = new ArrayList<>();
    public  static final List<Item> ALL       = Collections.unmodifiableList(REGISTRY);
    private static final Map<String, Item> BY_ID = new LinkedHashMap<>();

    public final String       id;
    public final ItemCategory category;
    public final int          nutrition;
    public final float        saturation;
    public final @Nullable Tooltips.Tip tip;
    public final List<Effect>       effects;
    /**
     * For {@link ItemCategory#PLAIN_CR}: the registry id of the item to use as
     * {@code craftRemainder}. Resolved at registration time via {@link #getById}.
     */
    public final @Nullable String remainderId;

    private Supplier<net.minecraft.world.item.Item> registered;

    private Item(String id, ItemCategory category, int nutrition, float saturation,
                 @Nullable Tooltips.Tip tip, @Nullable String remainderId, List<Effect> effects) {
        this.id          = id;
        this.category    = category;
        this.nutrition   = nutrition;
        this.saturation  = saturation;
        this.tip         = tip;
        this.remainderId = remainderId;
        this.effects     = List.copyOf(effects);
    }

    /** Called by each platform immediately after registering this item. */
    public void bind(Supplier<net.minecraft.world.item.Item> supplier) {
        if (this.registered != null) throw new IllegalStateException("Already bound: " + id);
        this.registered = supplier;
    }

    /** Returns the registered {@link net.minecraft.world.item.Item}. Throws if called before registration. */
    public net.minecraft.world.item.Item get() {
        if (registered == null) throw new IllegalStateException("Not yet registered: " + id);
        return registered.get();
    }

    /** Looks up a def by its registry id. Used to resolve {@link #remainderId} references. */
    public static Item getById(String id) {
        Item def = BY_ID.get(id);
        if (def == null) throw new IllegalArgumentException("No ItemDef with id: " + id);
        return def;
    }

    private static Item register(Item def) {
        REGISTRY.add(def);
        BY_ID.put(def.id, def);
        return def;
    }

    public static Item food(String id, int nut, float sat, Effect... effects) {
        return register(new Item(id, ItemCategory.FOOD, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static Item food(String id, int nut, float sat, Tooltips.Tip tip, Effect... effects) {
        return register(new Item(id, ItemCategory.FOOD, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static Item fastFood(String id, int nut, float sat, Effect... effects) {
        return register(new Item(id, ItemCategory.FAST_FOOD, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static Item fastFood(String id, int nut, float sat, Tooltips.Tip tip, Effect... effects) {
        return register(new Item(id, ItemCategory.FAST_FOOD, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static Item bowlFood(String id, int nut, float sat, Effect... effects) {
        return register(new Item(id, ItemCategory.BOWL_FOOD, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static Item bowlFood(String id, int nut, float sat, Tooltips.Tip tip, Effect... effects) {
        return register(new Item(id, ItemCategory.BOWL_FOOD, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static Item bowlFoodCr(String id, int nut, float sat, Effect... effects) {
        return register(new Item(id, ItemCategory.BOWL_FOOD_CR, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static Item bowlFoodCr(String id, int nut, float sat, Tooltips.Tip tip, Effect... effects) {
        return register(new Item(id, ItemCategory.BOWL_FOOD_CR, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static Item stickFood(String id, int nut, float sat, Effect... effects) {
        return register(new Item(id, ItemCategory.STICK_FOOD, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static Item stickFood(String id, int nut, float sat, Tooltips.Tip tip, Effect... effects) {
        return register(new Item(id, ItemCategory.STICK_FOOD, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static Item stickFoodCr(String id, int nut, float sat, Effect... effects) {
        return register(new Item(id, ItemCategory.STICK_FOOD_CR, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static Item stickFoodCr(String id, int nut, float sat, Tooltips.Tip tip, Effect... effects) {
        return register(new Item(id, ItemCategory.STICK_FOOD_CR, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static Item bottle(String id, int nut, float sat, Effect... effects) {
        return register(new Item(id, ItemCategory.BOTTLE, nut, sat, null, null, Arrays.asList(effects)));
    }
    public static Item bottle(String id, int nut, float sat, Tooltips.Tip tip, Effect... effects) {
        return register(new Item(id, ItemCategory.BOTTLE, nut, sat, tip, null, Arrays.asList(effects)));
    }

    public static Item plain(String id) {
        return register(new Item(id, ItemCategory.PLAIN, 0, 0, null, null, List.of()));
    }
    public static Item plain(String id, Tooltips.Tip tip) {
        return register(new Item(id, ItemCategory.PLAIN, 0, 0, tip, null, List.of()));
    }

    /** @param remainderId registry id of the item to use as {@code craftRemainder} */
    public static Item plainCr(String id, String remainderId) {
        return register(new Item(id, ItemCategory.PLAIN_CR, 0, 0, null, remainderId, List.of()));
    }
    public static Item plainCr(String id, String remainderId, Tooltips.Tip tip) {
        return register(new Item(id, ItemCategory.PLAIN_CR, 0, 0, tip, remainderId, List.of()));
    }

    public static Item ingredientBottle(String id) {
        return register(new Item(id, ItemCategory.INGREDIENT_BOTTLE, 0, 0, null, null, List.of()));
    }

    public static Item ingredientBowl(String id) {
        return register(new Item(id, ItemCategory.INGREDIENT_BOWL, 0, 0, null, null, List.of()));
    }

    public static Item pipingBag(String id) {
        return register(new Item(id, ItemCategory.PIPING_BAG, 0, 0, null, null, List.of()));
    }
    public static Item pipingBag(String id, Tooltips.Tip tip) {
        return register(new Item(id, ItemCategory.PIPING_BAG, 0, 0, tip, null, List.of()));
    }

    /** Describes how an item is constructed by platform registration code. */
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
        /** Plain item with craft remainder — no food, craftRemainder resolved from {@code ItemDef.remainderId} */
        PLAIN_CR,
        /** Ingredient bottle — EffectDrink, stacksTo(16), craftRemainder(GLASS_BOTTLE), no food */
        INGREDIENT_BOTTLE,
        /** Ingredient bowl — plain Item, stacksTo(16), craftRemainder(BOWL) */
        INGREDIENT_BOWL,
        /** Piping bag — plain Item, stacksTo(2), craftRemainder(piping_bag) */
        PIPING_BAG,
    }
}