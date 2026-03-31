package dev.averageanime.neoforge.item;

import dev.averageanime.CommonClass;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.ArrayList;
import java.util.List;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ModTooltips.addTooltip;

@SuppressWarnings({"NullableProblems", "unused"})
public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CommonClass.ID);

    /** Tooltip specification: optional compat key + short ingredient keys. */
    record Tip(String compat, String[] keys) {}

    /** Effect specification: a MobEffect with duration and amplifier. */
    record Fx(Holder<MobEffect> effect, int duration, int amplifier) {}

    /** Build a {@link Tip}. Pass {@code null} for compat if no mod dependency. */
    private static Tip tips(String compat, String... shortKeys) { return new Tip(compat, shortKeys); }

    /** Build an {@link Fx} with amplifier 0. */
    private static Fx fx(Holder<MobEffect> effect, int duration) { return new Fx(effect, duration, 0); }

    /** Build an {@link Fx} with an explicit amplifier. */
    private static Fx fx(Holder<MobEffect> effect, int duration, int amplifier) { return new Fx(effect, duration, amplifier); }

    private static final String TIP_PREFIX = "tooltip.createfood.";

    private static void splitArgs(Object[] args, Tip[] outTip, List<Fx> outFx) {
        for (Object a : args) {
            if (a instanceof Tip t) outTip[0] = t;
            else if (a instanceof Fx f) outFx.add(f);
        }
    }

    private static Item.Properties foodProps(int nut, float sat, boolean fast, Item converts, List<Fx> fxList) {
        var b = new FoodProperties.Builder().nutrition(nut).saturationModifier(sat);
        if (fast)     b.fast();
        if (converts != null) b.usingConvertsTo(converts);
        for (Fx f : fxList) b.effect(() -> new MobEffectInstance(f.effect(), f.duration(), f.amplifier()), 1.0f);
        return new Item.Properties().food(b.build());
    }

    /** Apply tooltip to hover text. Short ingredient keys are prefixed automatically. */
    private static void doTip(List<Component> l, Tip tip) {
        if (tip == null) return;
        String[] fullKeys = new String[tip.keys().length];
        for (int i = 0; i < tip.keys().length; i++) {
            String k = tip.keys()[i];
            fullKeys[i] = k.startsWith("tooltip.") ? k : TIP_PREFIX + k;
        }
        addTooltip(l, tip.compat(), fullKeys);
    }

    private static DeferredItem<Item> plain(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties()));
    }

    private static DeferredItem<Item> plain(String id, String compat, String... keys) {
        final Tip tip = new Tip(compat, keys);
        return ITEMS.register(id, () -> new Item(new Item.Properties()) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip);
            }
        });
    }

    private static DeferredItem<Item> plainCr(String id, java.util.function.Supplier<Item> remainder) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().craftRemainder(remainder.get())));
    }

    private static DeferredItem<Item> plainCr(String id, java.util.function.Supplier<Item> remainder, String compat, String... keys) {
        final Tip tip = new Tip(compat, keys);
        return ITEMS.register(id, () -> new Item(new Item.Properties().craftRemainder(remainder.get())) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip);
            }
        });
    }

    private static DeferredItem<Item> ingredientBottle(String id) {
        return ITEMS.register(id, () -> new DrinkableItem(
                new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    }

    private static DeferredItem<Item> ingredientBottleItem(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));
    }

    private static DeferredItem<Item> ingredientBowlItem(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().stacksTo(16).craftRemainder(Items.BOWL)));
    }

    private static DeferredItem<Item> pipingBag(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties().stacksTo(2).craftRemainder(PIPING_BAG.get())));
    }

    private static DeferredItem<Item> pipingBag(String id, String compat, String... fullKeys) {
        final Tip tip = new Tip(compat, fullKeys);
        return ITEMS.register(id, () -> new Item(new Item.Properties().stacksTo(2).craftRemainder(PIPING_BAG.get())) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip);
            }
        });
    }

    private static DeferredItem<Item> food(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, false, null, fx); final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new Item(fp));
        return ITEMS.register(id, () -> new Item(fp) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static DeferredItem<Item> fastFood(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, true, null, fx); final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new Item(fp));
        return ITEMS.register(id, () -> new Item(fp) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static DeferredItem<Item> consumable(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, false, null, fx); final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new ConsumableItem(fp, true));
        return ITEMS.register(id, () -> new ConsumableItem(fp, true) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static DeferredItem<Item> consumableFast(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, true, null, fx); final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new ConsumableItem(fp, true));
        return ITEMS.register(id, () -> new ConsumableItem(fp, true) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static DeferredItem<Item> bottle(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, false, Items.GLASS_BOTTLE, fx)
                .stacksTo(16).craftRemainder(Items.GLASS_BOTTLE);
        final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new DrinkableItem(fp));
        return ITEMS.register(id, () -> new DrinkableItem(fp) {
            @Override public void appendHoverText(ItemStack s, Item.TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static DeferredItem<Item> bowlFood(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, false, Items.BOWL, fx).stacksTo(16); final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new Item(fp));
        return ITEMS.register(id, () -> new Item(fp) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static DeferredItem<Item> bowlConsumable(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, false, Items.BOWL, fx).stacksTo(16); final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new ConsumableItem(fp, true));
        return ITEMS.register(id, () -> new ConsumableItem(fp, true) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static DeferredItem<Item> stickFood(String id, int nut, float sat, boolean crStick, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        var fp = foodProps(nut, sat, true, Items.STICK, fx);
        if (crStick) fp.craftRemainder(Items.STICK);
        final var finalFp = fp; final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new Item(finalFp));
        return ITEMS.register(id, () -> new Item(finalFp) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static DeferredItem<Item> stickConsumable(String id, int nut, float sat, boolean crStick, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        var fp = foodProps(nut, sat, true, Items.STICK, fx);
        if (crStick) fp.craftRemainder(Items.STICK);
        final var finalFp = fp; final Tip t = tip[0];
        if (t == null) return ITEMS.register(id, () -> new ConsumableItem(finalFp, true));
        return ITEMS.register(id, () -> new ConsumableItem(finalFp, true) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    public static final DeferredItem<Item> PIPING_BAG = plain("piping_bag");
    public static final DeferredItem<Item> CLOTH_FILTER = plain("cloth_filter");
    public static final DeferredItem<Item> CLOTH_FILTER_PRESSED_COCOA = plainCr("cloth_filter_pressed_cocoa", ModItems.CLOTH_FILTER, null, "tooltip.createfood.pressed_cocoa_ingredient");
    public static final DeferredItem<Item> CLOTH_FILTER_EGG_YOLK = plainCr("cloth_filter_egg_yolk", ModItems.CLOTH_FILTER, null, "tooltip.createfood.egg_yolk_ingredient");
    public static final DeferredItem<Item> CLOTH_FILTER_EGG = plainCr("cloth_filter_egg", ModItems.CLOTH_FILTER_EGG_YOLK, null, "tooltip.createfood.egg_ingredient");
    public static final DeferredItem<Item> CLOTH_FILTER_CACAO_MASS = plainCr("cloth_filter_cacao_mass", ModItems.CLOTH_FILTER_PRESSED_COCOA, null, "tooltip.createfood.cacao_mass_ingredient");
    public static final DeferredItem<Item> EGGSHELL = plain("eggshell");

    public static final DeferredItem<Item> APPLE_CHEESECAKE_SLICE = consumable("apple_cheesecake_slice", 3, 0.1f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> APPLE_CREAM_CAKE_SLICE = food("apple_cream_cake_slice", 2, 0.3f, tips(null, "apple_cream_frosting_ingredient"));
    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE = consumableFast("apple_cream_chocolate", 6, 0.5f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE_CUPCAKE = consumableFast("apple_cream_chocolate_cupcake", 4, 1.1f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE_DONUT = consumable("apple_cream_chocolate_donut", 4, 0.8f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE_PASTRY = consumable("apple_cream_chocolate_pastry", 3, 1.0f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE_SWEET_ROLL = consumable("apple_cream_chocolate_sweet_roll", 5, 0.6f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> APPLE_CREAM_CUPCAKE = consumableFast("apple_cream_cupcake", 3, 1.0f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> APPLE_CREAM_DARK_CHOCOLATE = consumableFast("apple_cream_dark_chocolate", 5, 0.6f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> APPLE_CREAM_DONUT = consumable("apple_cream_donut", 3, 0.8f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> APPLE_CREAM_FROSTING_BOTTLE = bottle("apple_cream_frosting_bottle", 3, 0.7f);
    public static final DeferredItem<Item> APPLE_CREAM_FROSTING_PIPING_BAG = pipingBag("apple_cream_frosting_piping_bag", null, "tooltip.createfood.apple_cream_frosting_ingredient");
    public static final DeferredItem<Item> APPLE_CREAM_MINI_WAFFLE = consumableFast("apple_cream_mini_waffle", 3, 1.6f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> APPLE_CREAM_PASTRY = consumable("apple_cream_pastry", 2, 1.0f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> APPLE_CREAM_SWEET_ROLL = consumable("apple_cream_sweet_roll", 4, 0.7f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> APPLE_CREAM_WHITE_CHOCOLATE = consumable("apple_cream_white_chocolate", 7, 0.4f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> APPLE_CUSTARD_BOTTLE = bottle("apple_custard_bottle", 6, 0.9f);
    public static final DeferredItem<Item> APPLE_ICE_CREAM_BOWL = bowlFood("apple_ice_cream_bowl", 2, 1.1f);
    public static final DeferredItem<Item> APPLE_ICE_CREAM_CONE = consumableFast("apple_ice_cream_cone", 2, 0.6f, fx(ModEffects.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> APPLE_ICE_CREAM_SANDWICH = food("apple_ice_cream_sandwich", 6, 0.6f, tips(null, "apple_ice_cream_ingredient"));
    public static final DeferredItem<Item> APPLE_ICE_CREAM_STICK = stickFood("apple_ice_cream_stick", 1, 0.6f, true);
    public static final DeferredItem<Item> APPLE_JAM_BOTTLE = bottle("apple_jam_bottle", 3, 1.6f);
    public static final DeferredItem<Item> APPLE_JAM_SANDWICH = consumableFast("apple_jam_sandwich", 7, 0.7f, tips(null, "apple_jam_ingredient"), fx(ModEffects.COMFORT, 600), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> APPLE_JUICE_BOTTLE = bottle("apple_juice_bottle", 2, 1.5f);
    public static final DeferredItem<Item> APPLE_MILKSHAKE_BOTTLE = bottle("apple_milkshake_bottle", 5, 0.9f, fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> APPLE_POPSICLE = stickFood("apple_popsicle", 3, 0.7f, false);
    public static final DeferredItem<Item> APPLE_SLICE = fastFood("apple_slice", 2, 0.4f);
    public static final DeferredItem<Item> BACON_BITS = food("bacon_bits", 1, 0.4f);
    public static final DeferredItem<Item> BACON_CALZONE = consumableFast("bacon_calzone", 6, 0.4f, tips(null, "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> BACON_PIZZA_SLICE = food("bacon_pizza_slice", 5, 0.6f, tips(null, "bacon_ingredient"));
    public static final DeferredItem<Item> BACON_SANDWICH = consumable("bacon_sandwich", 7, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> BACON_SANDWICH_LETTUCE = consumable("bacon_sandwich_lettuce", 9, 0.5f, tips(null, "bacon_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> BAKED_POTATO_BUTTER = food("baked_potato_butter", 5, 0.8f, tips(null, "butter_ingredient"));
    public static final DeferredItem<Item> BAKED_POTATO_BUTTER_CHEESE = food("baked_potato_butter_cheese", 5, 0.9f, tips(null, "butter_ingredient", "cheese_ingredient"));
    public static final DeferredItem<Item> BAKED_POTATO_BUTTER_FISH = food("baked_potato_butter_fish", 5, 0.9f, tips(null, "butter_ingredient", "fish_ingredient"));
    public static final DeferredItem<Item> BAKED_POTATO_BUTTER_FRIED_EGG = food("baked_potato_butter_fried_egg", 5, 0.7f, tips(null, "butter_ingredient", "fried_egg_ingredient"));
    public static final DeferredItem<Item> BAKED_POTATO_BUTTER_MUSHROOM = food("baked_potato_butter_mushroom", 5, 1.1f, tips(null, "butter_ingredient", "mushroom_ingredient"));
    public static final DeferredItem<Item> BAR_OF_DARK_CHOCOLATE = food("bar_of_dark_chocolate", 7, 0.2f);
    public static final DeferredItem<Item> BAR_OF_WHITE_CHOCOLATE = food("bar_of_white_chocolate", 5, 0.4f);
    public static final DeferredItem<Item> BEEF_BUN = food("beef_bun", 6, 0.6f);
    public static final DeferredItem<Item> BEEF_BUN_BACON = food("beef_bun_bacon", 8, 0.6f, tips(null, "bacon_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_BACON_LETTUCE = food("beef_bun_bacon_lettuce", 9, 0.7f, tips(null, "bacon_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_BACON_LETTUCE_TOMATO = food("beef_bun_bacon_lettuce_tomato", 9, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE = food("beef_bun_cheese", 8, 0.7f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_BACON = food("beef_bun_cheese_bacon", 9, 0.7f, tips(null, "cheese_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_BACON_LETTUCE = food("beef_bun_cheese_bacon_lettuce", 10, 0.8f, tips(null, "cheese_ingredient", "bacon_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_BACON_LETTUCE_TOMATO = food("beef_bun_cheese_bacon_lettuce_tomato", 10, 0.9f, tips(null, "cheese_ingredient", "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_LETTUCE = food("beef_bun_cheese_lettuce", 9, 0.8f, tips(null, "cheese_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_LETTUCE_TOMATO = food("beef_bun_cheese_lettuce_tomato", 9, 0.9f, tips(null, "cheese_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION = food("beef_bun_cheese_onion", 9, 0.9f, tips(null, "cheese_ingredient", "onion_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION_BACON = food("beef_bun_cheese_onion_bacon", 10, 0.9f, tips(null, "cheese_ingredient", "onion_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION_BACON_LETTUCE = food("beef_bun_cheese_onion_bacon_lettuce", 12, 1.0f, tips(null, "cheese_ingredient", "onion_ingredient", "bacon_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION_LETTUCE = food("beef_bun_cheese_onion_lettuce", 10, 1.0f, tips(null, "cheese_ingredient", "onion_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION_LETTUCE_TOMATO = food("beef_bun_cheese_onion_lettuce_tomato", 10, 1.1f, tips(null, "cheese_ingredient", "onion_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CHEESE_TOMATO = food("beef_bun_cheese_tomato", 9, 0.8f, tips(null, "cheese_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_CRIMSON_FUNGUS = food("beef_bun_crimson_fungus", 8, 0.5f, tips(null, "crimson_fungus_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_LETTUCE = food("beef_bun_lettuce", 7, 0.7f, tips(null, "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_LETTUCE_TOMATO = food("beef_bun_lettuce_tomato", 7, 0.8f, tips(null, "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_ONION = food("beef_bun_onion", 6, 0.8f, tips(null, "onion_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_ONION_BACON = food("beef_bun_onion_bacon", 9, 0.8f, tips(null, "onion_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_ONION_BACON_LETTUCE = food("beef_bun_onion_bacon_lettuce", 10, 0.9f, tips(null, "onion_ingredient", "bacon_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_ONION_LETTUCE = food("beef_bun_onion_lettuce", 8, 0.9f, tips(null, "onion_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_ONION_LETTUCE_TOMATO = food("beef_bun_onion_lettuce_tomato", 8, 1.0f, tips(null, "onion_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_PEANUT_BUTTER = food("beef_bun_peanut_butter", 6, 0.9f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_PEANUT_BUTTER_BACON = food("beef_bun_peanut_butter_bacon", 8, 0.9f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_TOMATO = food("beef_bun_tomato", 7, 0.7f, tips(null, "tomato_ingredient"));
    public static final DeferredItem<Item> BEEF_BUN_WARPED_FUNGUS = food("beef_bun_warped_fungus", 8, 0.6f, tips(null, "warped_fungus_ingredient"));
    public static final DeferredItem<Item> BEEF_BURRITO_RICE = food("beef_burrito_rice", 14, 0.8f, tips(null, "beef_ingredient", "rice_ingredient"));
    public static final DeferredItem<Item> BEEF_MEATBALL = food("beef_meatball", 4, 0.6f);
    public static final DeferredItem<Item> BEEF_MEATBALL_SANDWICH = consumable("beef_meatball_sandwich", 10, 0.4f, tips(null, "beef_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> BEEF_MEATBALL_STICK_1 = stickConsumable("beef_meatball_stick_1", 5, 0.6f, false, fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> BEEF_MEATBALL_STICK_2 = stickConsumable("beef_meatball_stick_2", 6, 0.6f, true, fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> BEEF_MEATBALL_STICK_3 = stickConsumable("beef_meatball_stick_3", 7, 0.7f, false, fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> BEEF_TACO = food("beef_taco", 11, 0.6f, tips(null, "beef_ingredient"));
    public static final DeferredItem<Item> BEEF_TACO_LETTUCE = food("beef_taco_lettuce", 12, 0.7f, tips(null, "beef_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BEEF_TACO_LETTUCE_TACO_SAUCE = consumable("beef_taco_lettuce_taco_sauce", 13, 0.8f, tips(null, "beef_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> BEEF_WRAP_LETTUCE_BEETROOT = food("beef_wrap_lettuce_beetroot", 12, 0.7f, tips(null, "beef_ingredient", "lettuce_ingredient", "beetroot_ingredient"));
    public static final DeferredItem<Item> BERRY_CREAM_CAKE_SLICE = food("berry_cream_cake_slice", 2, 0.3f, tips(null, "berry_cream_frosting_ingredient"));
    public static final DeferredItem<Item> BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT = food("berry_cream_cake_slice_chorus_fruit", 2, 0.3f, tips(null, "berry_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final DeferredItem<Item> BERRY_CREAM_CAKE_SLICE_GLOW_BERRY = food("berry_cream_cake_slice_glow_berry", 2, 0.2f, tips(null, "berry_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final DeferredItem<Item> BERRY_CREAM_CAKE_SLICE_SWEET_BERRY = food("berry_cream_cake_slice_sweet_berry", 2, 0.2f, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"));
    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE = consumableFast("berry_cream_chocolate", 7, 0.4f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_CUPCAKE = consumableFast("berry_cream_chocolate_cupcake", 4, 1.1f, tips(null, "berry_cream_frosting_ingredient"), fx(MobEffects.DIG_SPEED, 1200));
    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_DONUT = consumable("berry_cream_chocolate_donut", 4, 0.7f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_PASTRY = consumable("berry_cream_chocolate_pastry", 3, 1.0f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_SWEET_ROLL = consumable("berry_cream_chocolate_sweet_roll", 6, 0.7f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY = consumable("berry_cream_chocolate_sweet_roll_sweet_berry", 6, 0.7f, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"), fx(ModEffects.COMFORT, 1200), fx(MobEffects.DIG_SPEED, 1200));
    public static final DeferredItem<Item> BERRY_CREAM_CUPCAKE = consumableFast("berry_cream_cupcake", 3, 1.0f, tips(null, "berry_cream_frosting_ingredient"), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> BERRY_CREAM_DARK_CHOCOLATE = consumableFast("berry_cream_dark_chocolate", 8, 0.3f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> BERRY_CREAM_DONUT = consumable("berry_cream_donut", 3, 0.7f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BERRY_CREAM_FROSTING_BOTTLE = bottle("berry_cream_frosting_bottle", 4, 0.4f);
    public static final DeferredItem<Item> BERRY_CREAM_FROSTING_PIPING_BAG = pipingBag("berry_cream_frosting_piping_bag", null, "tooltip.createfood.berry_cream_frosting_ingredient");
    public static final DeferredItem<Item> BERRY_CREAM_MINI_WAFFLE = consumableFast("berry_cream_mini_waffle", 3, 1.9f, tips(null, "berry_cream_frosting_ingredient"), fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> BERRY_CREAM_MINI_WAFFLE_SWEET_BERRY = consumableFast("berry_cream_mini_waffle_sweet_berry", 3, 1.9f, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"), fx(MobEffects.DIG_SPEED, 1200), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> BERRY_CREAM_PASTRY = consumable("berry_cream_pastry", 2, 1.0f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BERRY_CREAM_SWEET_ROLL = consumable("berry_cream_sweet_roll", 5, 0.6f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> BERRY_CREAM_SWEET_ROLL_SWEET_BERRY = consumable("berry_cream_sweet_roll_sweet_berry", 5, 0.6f, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> BERRY_CREAM_WHITE_CHOCOLATE = consumableFast("berry_cream_white_chocolate", 6, 0.5f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> BERRY_CUSTARD_BOTTLE = bottle("berry_custard_bottle", 8, 0.9f);
    public static final DeferredItem<Item> BERRY_ICE_CREAM_BOWL = bowlFood("berry_ice_cream_bowl", 3, 1.0f);
    public static final DeferredItem<Item> BERRY_ICE_CREAM_CONE = consumableFast("berry_ice_cream_cone", 2, 0.9f, fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> BERRY_ICE_CREAM_SANDWICH = food("berry_ice_cream_sandwich", 6, 0.6f, tips(null, "berry_ice_cream_ingredient"));
    public static final DeferredItem<Item> BERRY_ICE_CREAM_STICK = stickFood("berry_ice_cream_stick", 1, 0.9f, true);
    public static final DeferredItem<Item> BERRY_JAM_BOTTLE = bottle("berry_jam_bottle", 4, 1.2f);
    public static final DeferredItem<Item> BERRY_JAM_SANDWICH = consumable("berry_jam_sandwich", 9, 0.4f, tips(null, "berry_jam_ingredient"), fx(ModEffects.COMFORT, 600), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> BERRY_JUICE_BOTTLE = bottle("berry_juice_bottle", 2, 1.6f);
    public static final DeferredItem<Item> BERRY_MILKSHAKE_BOTTLE = bottle("berry_milkshake_bottle", 5, 1.0f, fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> BERRY_PIE_SLICE = consumable("berry_pie_slice", 3, 0.1f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BERRY_POPSICLE = stickFood("berry_popsicle", 4, 0.5f, false);
    public static final DeferredItem<Item> BISCUIT = fastFood("biscuit", 9, 0.7f);
    public static final DeferredItem<Item> BLACKSTRAP_MOLASSES_BOTTLE = bottle("blackstrap_molasses_bottle", 1, 3.0f);
    public static final DeferredItem<Item> BLACK_GELATIN_DESSERT_SLICE = consumable("black_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BLUE_GELATIN_DESSERT_SLICE = consumable("blue_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BOILED_EGG = plainCr("boiled_egg", ModItems.EGGSHELL);
    public static final DeferredItem<Item> BOILED_EGG_PEELED = fastFood("boiled_egg_peeled", 6, 0.1f);
    public static final DeferredItem<Item> BOILED_EGG_PEELED_SALT = consumableFast("boiled_egg_peeled_salt", 6, 0.2f, tips(null, "salt_ingredient"), fx(MobEffects.LUCK, 300));
    public static final DeferredItem<Item> BREAD_CARROT = food("bread_carrot", 7, 0.4f, tips(null, "carrot_ingredient"));
    public static final DeferredItem<Item> BREAD_CRUMBS = plain("bread_crumbs");
    public static final DeferredItem<Item> BREAD_FRIED_EGG = food("bread_fried_egg", 6, 0.6f, tips(null, "fried_egg_ingredient"));
    public static final DeferredItem<Item> BREAD_LETTUCE = food("bread_lettuce", 7, 0.3f, tips(null, "lettuce_ingredient"));
    public static final DeferredItem<Item> BREAD_LETTUCE_CARROT = food("bread_lettuce_carrot", 8, 0.5f, tips(null, "lettuce_ingredient", "carrot_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE = fastFood("bread_slice", 2, 0.2f);
    public static final DeferredItem<Item> BREAD_SLICE_APPLE_JAM = food("bread_slice_apple_jam", 5, 0.6f, tips(null, "apple_jam_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_BACON = food("bread_slice_bacon", 5, 0.6f, tips(null, "bacon_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_BACON_LETTUCE = food("bread_slice_bacon_lettuce", 6, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_BACON_LETTUCE_TOMATO = food("bread_slice_bacon_lettuce_tomato", 9, 0.5f, tips(null, "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_BEETROOT = food("bread_slice_beetroot", 7, 0.3f, tips(null, "beetroot_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_BEETROOT_LETTUCE = food("bread_slice_beetroot_lettuce", 7, 0.5f, tips(null, "beetroot_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_BERRY_JAM = food("bread_slice_berry_jam", 7, 0.4f, tips(null, "berry_jam_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_CHEESE = food("bread_slice_cheese", 5, 0.3f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_CHORUS_FRUIT_JAM = food("bread_slice_chorus_fruit_jam", 8, 0.5f, tips(null, "chorus_fruit_jam_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_GLOW_BERRY_JAM = food("bread_slice_glow_berry_jam", 7, 0.4f, tips(null, "glow_berry_jam_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_HONEY = food("bread_slice_honey", 7, 0.4f, tips(null, "honey_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_LETTUCE = food("bread_slice_lettuce", 7, 0.3f, tips(null, "lettuce_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_LETTUCE_TOMATO = food("bread_slice_lettuce_tomato", 7, 0.4f, tips(null, "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_MELON_JAM = food("bread_slice_melon_jam", 8, 0.5f, tips(null, "melon_jam_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_MUTTON = food("bread_slice_mutton", 5, 0.6f, tips(null, "mutton_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_MUTTON_BEETROOT = food("bread_slice_mutton_beetroot", 5, 0.8f, tips(null, "mutton_ingredient", "beetroot_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_PEANUT_BUTTER = food("bread_slice_peanut_butter", 7, 0.4f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_SCRAMBLED_EGG = food("bread_slice_scrambled_egg", 7, 0.8f, tips(null, "scrambled_egg_ingredient"));
    public static final DeferredItem<Item> BREAD_SLICE_TOMATO = food("bread_slice_tomato", 7, 0.3f, tips(null, "tomato_ingredient"));
    public static final DeferredItem<Item> BREAKFAST_BAR = consumable("breakfast_bar", 2, 4.1f, fx(MobEffects.DIG_SPEED, 600), fx(ModEffects.NOURISHMENT, 3600), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BREAKFAST_PLATE = bowlConsumable("breakfast_plate", 8, 1.6f, tips(null, "fried_egg_ingredient", "hash_browns_ingredient", "toast_ingredient"), fx(ModEffects.NOURISHMENT, 6000), fx(ModEffects.COMFORT, 3600), fx(MobEffects.DIG_SPEED, 6000));
    public static final DeferredItem<Item> BROWN_GELATIN_DESSERT_SLICE = consumable("brown_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BROWN_SUGAR = plain("brown_sugar");
    public static final DeferredItem<Item> BUN = fastFood("bun", 2, 0.5f);
    public static final DeferredItem<Item> BUTTER = plain("butter");
    public static final DeferredItem<Item> BUTTERED_TOAST = food("buttered_toast", 2, 1.2f, tips(null, "butter_ingredient"));
    public static final DeferredItem<Item> BUTTERSCOTCH = fastFood("butterscotch", 1, 1.0f);
    public static final DeferredItem<Item> BUTTERSCOTCH_APPLE = consumable("butterscotch_apple", 8, 0.8f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> BUTTERSCOTCH_BERRIES = consumable("butterscotch_berries", 6, 0.9f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIPS = plain("butterscotch_chips");
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE = food("butterscotch_chip_chocolate_cookie", 2, 1.3f, tips(null, "butterscotch_chips_ingredient"));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("butterscotch_chip_chocolate_milkshake_bottle", 6, 1.5f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_COOKIE = food("butterscotch_chip_cookie", 1, 1.2f, tips(null, "butterscotch_chips_ingredient"));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_ICE_CREAM_CONE = consumableFast("butterscotch_chip_ice_cream_cone", 2, 1.2f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_MILKSHAKE_BOTTLE = bottle("butterscotch_chip_milkshake_bottle", 5, 1.2f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_MINI_WAFFLE = consumableFast("butterscotch_chip_mini_waffle", 3, 1.7f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_MUFFIN = consumableFast("butterscotch_chip_muffin", 3, 0.8f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHOCOLATE = consumable("butterscotch_chocolate", 7, 0.8f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHOCOLATE_PASTRY = consumable("butterscotch_chocolate_pastry", 3, 1.1f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHOCOLATE_SWEET_ROLL = consumable("butterscotch_chocolate_sweet_roll", 7, 0.8f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> BUTTERSCOTCH_DARK_CHOCOLATE = consumable("butterscotch_dark_chocolate", 8, 0.7f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> BUTTERSCOTCH_FUDGE = consumable("butterscotch_fudge", 3, 0.8f, fx(ModEffects.COMFORT, 3600), fx(MobEffects.DIG_SPEED, 3600));
    public static final DeferredItem<Item> BUTTERSCOTCH_PASTRY = consumable("butterscotch_pastry", 2, 1.1f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> BUTTERSCOTCH_SWEET_ROLL = consumable("butterscotch_sweet_roll", 6, 0.7f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> BUTTERSCOTCH_TOAST = consumable("butterscotch_toast", 3, 0.9f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> BUTTERSCOTCH_WHITE_CHOCOLATE = consumable("butterscotch_white_chocolate", 6, 0.9f, tips(null, "butterscotch_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> BUTTER_DOUGH = plain("butter_dough");
    public static final DeferredItem<Item> BUTTER_DOUGH_SMALL = plain("butter_dough_small");
    public static final DeferredItem<Item> CACAO_BUTTER = plain("cacao_butter");
    public static final DeferredItem<Item> CACAO_NIBS = plain("cacao_nibs");
    public static final DeferredItem<Item> CARAMEL = fastFood("caramel", 1, 0.8f);
    public static final DeferredItem<Item> CARAMEL_APPLE = consumable("caramel_apple", 6, 1.0f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CARAMEL_APPLE_SLICE = consumableFast("caramel_apple_slice", 3, 0.6f, fx(ModEffects.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final DeferredItem<Item> CARAMEL_BERRIES = consumable("caramel_berries", 6, 0.9f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CARAMEL_CHIPS = plain("caramel_chips");
    public static final DeferredItem<Item> CARAMEL_CHIP_CHOCOLATE_COOKIE = food("caramel_chip_chocolate_cookie", 2, 1.1f, tips(null, "caramel_chips_ingredient"));
    public static final DeferredItem<Item> CARAMEL_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("caramel_chip_chocolate_milkshake_bottle", 6, 1.5f, tips(null, "caramel_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final DeferredItem<Item> CARAMEL_CHIP_COOKIE = food("caramel_chip_cookie", 2, 0.8f, tips(null, "caramel_chips_ingredient"));
    public static final DeferredItem<Item> CARAMEL_CHIP_ICE_CREAM_CONE = consumableFast("caramel_chip_ice_cream_cone", 2, 1.2f, tips(null, "caramel_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CARAMEL_CHIP_MILKSHAKE_BOTTLE = bottle("caramel_chip_milkshake_bottle", 5, 1.2f, tips(null, "caramel_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final DeferredItem<Item> CARAMEL_CHIP_MINI_WAFFLE = consumableFast("caramel_chip_mini_waffle", 3, 1.7f, tips(null, "caramel_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.DAMAGE_BOOST, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CARAMEL_CHIP_MUFFIN = consumableFast("caramel_chip_muffin", 3, 0.9f, tips(null, "caramel_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final DeferredItem<Item> CARAMEL_CHOCOLATE = consumable("caramel_chocolate", 7, 0.7f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CARAMEL_CHOCOLATE_PASTRY = consumable("caramel_chocolate_pastry", 3, 1.0f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CARAMEL_CHOCOLATE_SWEET_ROLL = consumable("caramel_chocolate_sweet_roll", 7, 0.7f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CARAMEL_DARK_CHOCOLATE = consumable("caramel_dark_chocolate", 8, 0.6f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CARAMEL_FUDGE = consumable("caramel_fudge", 5, 0.4f, fx(ModEffects.COMFORT, 3600), fx(MobEffects.DAMAGE_BOOST, 3600));
    public static final DeferredItem<Item> CARAMEL_PASTRY = consumable("caramel_pastry", 2, 1.0f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final DeferredItem<Item> CARAMEL_POPCORN = consumable("caramel_popcorn", 3, 0.8f, tips("tooltip.compat.corn", "caramel_ingredient"), fx(MobEffects.LUCK, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final DeferredItem<Item> CARAMEL_SWEET_ROLL = consumable("caramel_sweet_roll", 6, 0.6f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CARAMEL_TOAST = consumable("caramel_toast", 3, 0.8f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CARAMEL_WHITE_CHOCOLATE = consumable("caramel_white_chocolate", 6, 0.8f, tips(null, "caramel_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CHEESEBURGER = consumable("cheeseburger", 10, 0.7f, fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> CHEESEBURGER_BACON = consumable("cheeseburger_bacon", 12, 0.7f, tips(null, "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> CHEESEBURGER_BACON_LETTUCE = consumable("cheeseburger_bacon_lettuce", 12, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> CHEESEBURGER_BACON_LETTUCE_TOMATO = consumable("cheeseburger_bacon_lettuce_tomato", 13, 0.9f, tips(null, "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 6000));
    public static final DeferredItem<Item> CHEESEBURGER_LETTUCE = consumable("cheeseburger_lettuce", 11, 0.8f, tips(null, "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> CHEESEBURGER_LETTUCE_TOMATO = consumable("cheeseburger_lettuce_tomato", 11, 0.9f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> CHEESEBURGER_ONION = consumable("cheeseburger_onion", 11, 0.8f, tips(null, "onion_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> CHEESEBURGER_ONION_BACON = consumable("cheeseburger_onion_bacon", 13, 0.8f, tips(null, "onion_ingredient", "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> CHEESEBURGER_ONION_BACON_LETTUCE = consumable("cheeseburger_onion_bacon_lettuce", 13, 0.9f, tips(null, "onion_ingredient", "bacon_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 6000));
    public static final DeferredItem<Item> CHEESEBURGER_ONION_LETTUCE = consumable("cheeseburger_onion_lettuce", 11, 0.9f, tips(null, "onion_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> CHEESEBURGER_ONION_LETTUCE_TOMATO = consumable("cheeseburger_onion_lettuce_tomato", 12, 1.0f, tips(null, "onion_ingredient", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 6000));
    public static final DeferredItem<Item> CHEESEBURGER_TOMATO = consumable("cheeseburger_tomato", 11, 0.8f, tips(null, "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> CHEESECAKE_SLICE = consumable("cheesecake_slice", 2, 0.3f, fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHEESE_BISCUIT = food("cheese_biscuit", 7, 0.5f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> CHEESE_BISCUIT_SANDWICH = food("cheese_biscuit_sandwich", 12, 0.5f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> CHEESE_CALZONE = fastFood("cheese_calzone", 5, 0.4f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> CHEESE_PIZZA_SLICE = food("cheese_pizza_slice", 5, 0.4f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> CHEESE_SANDWICH = food("cheese_sandwich", 8, 0.4f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> CHEESE_SLICE = fastFood("cheese_slice", 2, 0.1f);
    public static final DeferredItem<Item> CHICKEN_BUN = food("chicken_bun", 9, 0.6f);
    public static final DeferredItem<Item> CHICKEN_BUN_BACON = food("chicken_bun_bacon", 11, 0.6f, tips(null, "bacon_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_BACON_LETTUCE = food("chicken_bun_bacon_lettuce", 12, 0.7f, tips(null, "bacon_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE = food("chicken_bun_cheese", 11, 0.7f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_BACON = food("chicken_bun_cheese_bacon", 12, 0.7f, tips(null, "cheese_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_BACON_LETTUCE = food("chicken_bun_cheese_bacon_lettuce", 13, 0.8f, tips(null, "cheese_ingredient", "bacon_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_LETTUCE = food("chicken_bun_cheese_lettuce", 11, 0.8f, tips(null, "cheese_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_LETTUCE_TOMATO = food("chicken_bun_cheese_lettuce_tomato", 11, 0.9f, tips(null, "cheese_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_TOMATO = food("chicken_bun_cheese_tomato", 11, 0.8f, tips(null, "cheese_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_LETTUCE = food("chicken_bun_lettuce", 10, 0.7f, tips(null, "lettuce_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_LETTUCE_TOMATO = food("chicken_bun_lettuce_tomato", 10, 0.8f, tips(null, "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BUN_TOMATO = food("chicken_bun_tomato", 10, 0.7f, tips(null, "tomato_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BURGER = food("chicken_burger", 9, 0.7f);
    public static final DeferredItem<Item> CHICKEN_BURGER_BACON = consumable("chicken_burger_bacon", 13, 0.5f, tips(null, "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> CHICKEN_BURGER_BACON_LETTUCE = food("chicken_burger_bacon_lettuce", 14, 0.7f, tips(null, "bacon_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> CHICKEN_BURGER_LETTUCE = consumable("chicken_burger_lettuce", 12, 0.6f, tips(null, "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> CHICKEN_BURGER_LETTUCE_TOMATO = consumable("chicken_burger_lettuce_tomato", 12, 0.7f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> CHICKEN_BURGER_TOMATO = consumable("chicken_burger_tomato", 12, 0.6f, tips(null, "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> CHICKEN_BURRITO_RICE = food("chicken_burrito_rice", 11, 0.7f, tips(null, "chicken_ingredient", "rice_ingredient"));
    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER = consumable("chicken_cheeseburger", 9, 0.8f, fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_BACON = consumable("chicken_cheeseburger_bacon", 13, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_BACON_LETTUCE = consumable("chicken_cheeseburger_bacon_lettuce", 15, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 6000));
    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_LETTUCE = consumable("chicken_cheeseburger_lettuce", 12, 0.7f, tips(null, "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_LETTUCE_TOMATO = consumable("chicken_cheeseburger_lettuce_tomato", 12, 0.8f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 6000));
    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_TOMATO = consumable("chicken_cheeseburger_tomato", 12, 0.7f, tips(null, "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> CHICKEN_NUGGETS = consumable("chicken_nuggets", 4, 0.9f, fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> CHICKEN_PATTY = food("chicken_patty", 6, 0.8f);
    public static final DeferredItem<Item> CHICKEN_TACO = food("chicken_taco", 9, 0.6f, tips(null, "chicken_ingredient"));
    public static final DeferredItem<Item> CHICKEN_TACO_LETTUCE = food("chicken_taco_lettuce", 10, 0.6f, tips(null, "chicken_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> CHICKEN_TACO_LETTUCE_TACO_SAUCE = consumable("chicken_taco_lettuce_taco_sauce", 11, 0.8f, tips(null, "chicken_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> CHICKEN_WRAP_ONION_TOMATO = food("chicken_wrap_onion_tomato", 10, 0.7f, tips(null, "chicken_ingredient", "onion_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_APPLE = consumable("chocolate_apple", 8, 0.7f, tips(null, "chocolate_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CHOCOLATE_BOTTLE = bottle("chocolate_bottle", 7, 0.4f);
    public static final DeferredItem<Item> CHOCOLATE_CHIPS = plain("chocolate_chips");
    public static final DeferredItem<Item> CHOCOLATE_CHIP_CHOCOLATE_COOKIE = food("chocolate_chip_chocolate_cookie", 3, 0.5f, tips(null, "chocolate_chips_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("chocolate_chip_chocolate_milkshake_bottle", 6, 1.3f, tips(null, "chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_CHIP_ICE_CREAM_CONE = consumableFast("chocolate_chip_ice_cream_cone", 2, 1.2f, tips(null, "chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = bottle("chocolate_chip_milkshake_bottle", 5, 1.2f, tips(null, "chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_CHIP_MINI_WAFFLE = consumableFast("chocolate_chip_mini_waffle", 3, 1.7f, tips(null, "chocolate_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHOCOLATE_CHIP_MUFFIN = consumableFast("chocolate_chip_muffin", 3, 0.8f, tips(null, "chocolate_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHOCOLATE_CHOCOLATE_PASTRY = consumable("chocolate_chocolate_pastry", 3, 1.0f, tips(null, "chocolate_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE = food("chocolate_cream_cake_slice", 2, 0.3f, tips(null, "chocolate_cream_frosting_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_BUTTERSCOTCH = consumable("chocolate_cream_cake_slice_butterscotch", 4, 0.4f, tips(null, "chocolate_cream_frosting_ingredient", "butterscotch_chips_ingredient"), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL = consumable("chocolate_cream_cake_slice_caramel", 2, 0.4f, tips(null, "chocolate_cream_frosting_ingredient", "caramel_ingredient"), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_CHOCOLATE = consumable("chocolate_cream_cake_slice_chocolate", 4, 0.3f, tips(null, "chocolate_cream_frosting_ingredient", "chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_DARK_CHOCOLATE = consumable("chocolate_cream_cake_slice_dark_chocolate", 5, 0.2f, tips(null, "chocolate_cream_frosting_ingredient", "dark_chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_TOFFEE = consumable("chocolate_cream_cake_slice_toffee", 3, 0.4f, tips(null, "chocolate_cream_frosting_ingredient", "toffee_chips_ingredient"), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_WHITE_CHOCOLATE = consumable("chocolate_cream_cake_slice_white_chocolate", 3, 0.5f, tips(null, "chocolate_cream_frosting_ingredient", "white_chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CHOCOLATE_CUPCAKE = consumableFast("chocolate_cream_chocolate_cupcake", 4, 1.3f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 2400));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CHOCOLATE_DONUT = consumable("chocolate_cream_chocolate_donut", 4, 0.7f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CHOCOLATE_SWEET_ROLL = consumable("chocolate_cream_chocolate_sweet_roll", 6, 0.6f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CUPCAKE = consumableFast("chocolate_cream_cupcake", 3, 1.2f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_DONUT = consumable("chocolate_cream_donut", 3, 0.7f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_FROSTING_BOTTLE = bottle("chocolate_cream_frosting_bottle", 4, 0.3f);
    public static final DeferredItem<Item> CHOCOLATE_CREAM_FROSTING_PIPING_BAG = pipingBag("chocolate_cream_frosting_piping_bag", null, "tooltip.createfood.chocolate_cream_frosting_ingredient");
    public static final DeferredItem<Item> CHOCOLATE_CREAM_MINI_WAFFLE = consumableFast("chocolate_cream_mini_waffle", 3, 1.7f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffects.NOURISHMENT, 1200), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_SWEET_ROLL = consumable("chocolate_cream_sweet_roll", 5, 0.5f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHOCOLATE_CUPCAKE_BASE = consumable("chocolate_cupcake_base", 3, 0.9f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_CUSTARD_BOTTLE = bottle("chocolate_custard_bottle", 7, 1.0f);
    public static final DeferredItem<Item> CHOCOLATE_DONUT_BASE = food("chocolate_donut_base", 2, 1.2f);
    public static final DeferredItem<Item> CHOCOLATE_DONUT_HOLE = food("chocolate_donut_hole", 1, 0.8f);
    public static final DeferredItem<Item> CHOCOLATE_DONUT_HOLE_SUGAR = consumable("chocolate_donut_hole_sugar", 1, 0.8f, tips(null, "sugar_ingredient"), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHOCOLATE_DONUT_SUGAR = consumable("chocolate_donut_sugar", 4, 0.6f, tips(null, "sugar_ingredient"), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHOCOLATE_FUDGE = consumable("chocolate_fudge", 3, 0.6f, fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER = food("chocolate_graham_cracker", 3, 0.8f);
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_APPLE_ICE_CREAM = food("chocolate_graham_cracker_apple_ice_cream", 5, 0.8f, tips(null, "apple_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_BERRY_ICE_CREAM = food("chocolate_graham_cracker_berry_ice_cream", 5, 0.8f, tips(null, "berry_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_CHOCOLATE_ICE_CREAM = food("chocolate_graham_cracker_chocolate_ice_cream", 5, 1.0f, tips(null, "chocolate_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_CHORUS_FRUIT_ICE_CREAM = food("chocolate_graham_cracker_chorus_fruit_ice_cream", 6, 1.0f, tips(null, "chorus_fruit_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_CRUMBS = plain("chocolate_graham_cracker_crumbs");
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_GLOW_BERRY_ICE_CREAM = food("chocolate_graham_cracker_glow_berry_ice_cream", 5, 0.8f, tips(null, "berry_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_ICE_CREAM = food("chocolate_graham_cracker_ice_cream", 5, 0.9f, tips(null, "ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_MELON_ICE_CREAM = food("chocolate_graham_cracker_melon_ice_cream", 5, 0.9f, tips(null, "melon_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_1 = food("chocolate_graham_cracker_neapolitan_scoop_1", 4, 0.7f, tips(null, "chocolate_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_2 = food("chocolate_graham_cracker_neapolitan_scoop_2", 5, 0.6f, tips(null, "chocolate_ice_cream_ingredient", "ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_3 = food("chocolate_graham_cracker_neapolitan_scoop_3", 6, 0.7f, tips(null, "chocolate_ice_cream_ingredient", "ice_cream_ingredient", "berry_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = food("chocolate_graham_cracker_pie_crust", 7, 0.6f);
    public static final DeferredItem<Item> CHOCOLATE_ICE_CREAM_BOWL = bowlFood("chocolate_ice_cream_bowl", 3, 1.2f);
    public static final DeferredItem<Item> CHOCOLATE_ICE_CREAM_CONE = consumableFast("chocolate_ice_cream_cone", 2, 1.2f, fx(ModEffects.COMFORT, 1200), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHOCOLATE_ICE_CREAM_SANDWICH = food("chocolate_ice_cream_sandwich", 6, 0.8f, tips(null, "chocolate_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHOCOLATE_ICE_CREAM_STICK = stickFood("chocolate_ice_cream_stick", 1, 1.2f, false);
    public static final DeferredItem<Item> CHOCOLATE_MARSHMALLOW_STICK = stickConsumable("chocolate_marshmallow_stick", 4, 0.3f, false, tips(null, "chocolate_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHOCOLATE_MILKSHAKE_BOTTLE = bottle("chocolate_milkshake_bottle", 5, 1.2f, fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHOCOLATE_MILK_BOTTLE = bottle("chocolate_milk_bottle", 2, 0.3f, fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHOCOLATE_PASTRY = consumable("chocolate_pastry", 2, 1.0f, tips(null, "chocolate_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_PASTRY_BASE = consumable("chocolate_pastry_base", 4, 0.8f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_PIE_GRAHAM_CRACKER_SLICE = consumable("chocolate_pie_graham_cracker_slice", 4, 0.3f, tips(null, "graham_cracker_pie_crust_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHOCOLATE_SUGAR_DOUGH = plain("chocolate_sugar_dough");
    public static final DeferredItem<Item> CHOCOLATE_SUGAR_DOUGH_SMALL = plain("chocolate_sugar_dough_small");
    public static final DeferredItem<Item> CHOCOLATE_SWEET_ROLL_BASE = consumable("chocolate_sweet_roll_base", 5, 0.7f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHOCOLATE_TOAST = consumable("chocolate_toast", 4, 0.6f, tips(null, "chocolate_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CHEESECAKE_SLICE = consumable("chorus_fruit_cheesecake_slice", 4, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHORUS_FRUIT_COOKIE = food("chorus_fruit_cookie", 3, 0.8f, tips(null, "chorus_fruit_ingredient"));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_SLICE = food("chorus_fruit_cream_cake_slice", 2, 0.3f, tips(null, "chorus_fruit_cream_frosting_ingredient"));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT = food("chorus_fruit_cream_cake_slice_chorus_fruit", 4, 0.5f, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_SLICE_GLOW_BERRY = food("chorus_fruit_cream_cake_slice_glow_berry", 4, 0.4f, tips(null, "chorus_fruit_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_SLICE_SWEET_BERRY = food("chorus_fruit_cream_cake_slice_sweet_berry", 4, 0.4f, tips(null, "chorus_fruit_cream_frosting_ingredient", "berry_ingredient"));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE = consumableFast("chorus_fruit_cream_chocolate", 9, 0.5f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_CUPCAKE = consumableFast("chorus_fruit_cream_chocolate_cupcake", 5, 1.2f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(MobEffects.JUMP, 1200), fx(MobEffects.SLOW_FALLING, 1200));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_DONUT = consumable("chorus_fruit_cream_chocolate_donut", 4, 0.7f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_PASTRY = consumable("chorus_fruit_cream_chocolate_pastry", 4, 1.0f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL = consumable("chorus_fruit_cream_chocolate_sweet_roll", 8, 0.8f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT = consumable("chorus_fruit_cream_chocolate_sweet_roll_chorus_fruit", 8, 0.8f, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(ModEffects.COMFORT, 3600), fx(MobEffects.JUMP, 1200), fx(MobEffects.SLOW_FALLING, 1200));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CUPCAKE = consumableFast("chorus_fruit_cream_cupcake", 4, 1.1f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_DARK_CHOCOLATE = consumableFast("chorus_fruit_cream_dark_chocolate", 10, 0.4f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_DONUT = consumable("chorus_fruit_cream_donut", 3, 0.7f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_FROSTING_BOTTLE = bottle("chorus_fruit_cream_frosting_bottle", 6, 0.5f);
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_FROSTING_PIPING_BAG = pipingBag("chorus_fruit_cream_frosting_piping_bag", null, "tooltip.createfood.chorus_fruit_cream_frosting_ingredient");
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_MINI_WAFFLE = consumableFast("chorus_fruit_cream_mini_waffle", 4, 1.9f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_MINI_WAFFLE_CHORUS_FRUIT = consumableFast("chorus_fruit_cream_mini_waffle_chorus_fruit", 4, 1.9f, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(MobEffects.JUMP, 1200), fx(MobEffects.SLOW_FALLING, 1200), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_PASTRY = consumable("chorus_fruit_cream_pastry", 3, 1.0f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_SWEET_ROLL = consumable("chorus_fruit_cream_sweet_roll", 7, 0.7f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_SWEET_ROLL_CHORUS_FRUIT = consumable("chorus_fruit_cream_sweet_roll_chorus_fruit", 7, 0.7f, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_WHITE_CHOCOLATE = consumableFast("chorus_fruit_cream_white_chocolate", 8, 0.6f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_CUSTARD_BOTTLE = bottle("chorus_fruit_custard_bottle", 9, 0.9f);
    public static final DeferredItem<Item> CHORUS_FRUIT_ICE_CREAM_BOWL = bowlFood("chorus_fruit_ice_cream_bowl", 4, 1.2f);
    public static final DeferredItem<Item> CHORUS_FRUIT_ICE_CREAM_CONE = consumableFast("chorus_fruit_ice_cream_cone", 3, 0.9f, fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_ICE_CREAM_SANDWICH = food("chorus_fruit_ice_cream_sandwich", 7, 0.8f, tips(null, "chorus_fruit_ice_cream_ingredient"));
    public static final DeferredItem<Item> CHORUS_FRUIT_ICE_CREAM_STICK = stickFood("chorus_fruit_ice_cream_stick", 2, 0.9f, false);
    public static final DeferredItem<Item> CHORUS_FRUIT_JAM_BOTTLE = bottle("chorus_fruit_jam_bottle", 5, 1.1f);
    public static final DeferredItem<Item> CHORUS_FRUIT_JAM_SANDWICH = consumable("chorus_fruit_jam_sandwich", 10, 0.5f, tips(null, "chorus_fruit_jam_ingredient"), fx(ModEffects.COMFORT, 600), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_JUICE_BOTTLE = bottle("chorus_fruit_juice_bottle", 3, 1.6f);
    public static final DeferredItem<Item> CHORUS_FRUIT_MILKSHAKE_BOTTLE = bottle("chorus_fruit_milkshake_bottle", 6, 1.1f, fx(ModEffects.COMFORT, 600), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600));
    public static final DeferredItem<Item> CHORUS_FRUIT_PIE_SLICE = consumable("chorus_fruit_pie_slice", 4, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CHORUS_FRUIT_POPSICLE = stickFood("chorus_fruit_popsicle", 4, 0.6f, false);
    public static final DeferredItem<Item> CHORUS_FRUIT_SLICE = fastFood("chorus_fruit_slice", 4, 0.9f);
    public static final DeferredItem<Item> CINNAMON_SWEET_ROLL_BASE = food("cinnamon_sweet_roll_base", 4, 0.6f, tips("tooltip.compat.cinnamon"));
    public static final DeferredItem<Item> COCOA_POWDER = plain("cocoa_powder");
    public static final DeferredItem<Item> COFFEE_TOFFEE = consumable("coffee_toffee", 1, 2.0f, tips("tooltip.compat.coffee"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> COFFEE_TOFFEE_FUDGE = consumable("coffee_toffee_fudge", 4, 0.7f, tips("tooltip.compat.coffee"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> CONDENSED_MILK_BOTTLE = ingredientBottleItem("condensed_milk_bottle");
    public static final DeferredItem<Item> COOKED_RABBIT_CUTS = food("cooked_rabbit_cuts", 3, 0.6f);
    public static final DeferredItem<Item> COOKIE_CREAM_PIE_SLICE = consumable("cookie_cream_pie_slice", 5, 0.7f, fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> COOKIE_CRUMBS = plain("cookie_crumbs");
    public static final DeferredItem<Item> CORN_FLOUR = plain("corn_flour", "tooltip.compat.corn");
    public static final DeferredItem<Item> CORN_STICK = stickFood("corn_stick", 4, 0.7f, false, tips("tooltip.compat.corn"));
    public static final DeferredItem<Item> COTTON_CANDY_STICK = stickFood("cotton_candy_stick", 1, 5.0f, false);
    public static final DeferredItem<Item> CREAM_CAKE_SLICE = food("cream_cake_slice", 2, 0.3f, tips(null, "cream_frosting_ingredient"));
    public static final DeferredItem<Item> CREAM_CAKE_SLICE_CHORUS_FRUIT = food("cream_cake_slice_chorus_fruit", 4, 0.4f, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final DeferredItem<Item> CREAM_CAKE_SLICE_GLOW_BERRY = food("cream_cake_slice_glow_berry", 3, 0.3f, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final DeferredItem<Item> CREAM_CHEESE = plain("cream_cheese");
    public static final DeferredItem<Item> CREAM_CHOCOLATE = consumableFast("cream_chocolate", 6, 0.4f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CREAM_CHOCOLATE_CUPCAKE = consumableFast("cream_chocolate_cupcake", 4, 1.2f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CREAM_CHOCOLATE_DONUT = consumable("cream_chocolate_donut", 4, 0.6f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> CREAM_CHOCOLATE_PASTRY = consumable("cream_chocolate_pastry", 3, 0.9f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CREAM_CHOCOLATE_SWEET_ROLL = consumable("cream_chocolate_sweet_roll", 6, 0.8f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT = consumable("cream_chocolate_sweet_roll_chorus_fruit", 9, 0.7f, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY = consumable("cream_chocolate_sweet_roll_glow_berry", 7, 0.6f, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600));
    public static final DeferredItem<Item> CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY = consumable("cream_chocolate_sweet_roll_sweet_berry", 6, 0.5f, tips(null, "cream_frosting_ingredient", "berry_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> CREAM_CUPCAKE = consumableFast("cream_cupcake", 3, 1.1f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CREAM_DARK_CHOCOLATE = consumableFast("cream_dark_chocolate", 7, 0.3f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CREAM_DONUT = consumable("cream_donut", 3, 0.7f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CREAM_FROSTING_BOTTLE = bottle("cream_frosting_bottle", 3, 0.2f);
    public static final DeferredItem<Item> CREAM_FROSTING_PIPING_BAG = pipingBag("cream_frosting_piping_bag", null, "tooltip.createfood.cream_frosting_ingredient");
    public static final DeferredItem<Item> CREAM_MINI_WAFFLE = consumableFast("cream_mini_waffle", 3, 1.8f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CREAM_MINI_WAFFLE_CHORUS_FRUIT = consumableFast("cream_mini_waffle_chorus_fruit", 3, 1.9f, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CREAM_MINI_WAFFLE_GLOW_BERRY = consumableFast("cream_mini_waffle_glow_berry", 3, 1.8f, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CREAM_MINI_WAFFLE_SWEET_BERRY = consumableFast("cream_mini_waffle_sweet_berry", 3, 1.8f, tips(null, "cream_frosting_ingredient", "berry_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> CREAM_PASTRY = consumable("cream_pastry", 2, 0.9f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_SLICE = consumable("cream_pie_chocolate_graham_cracker_slice", 4, 0.5f, tips(null, "chocolate_graham_cracker_pie_crust_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CREAM_PIE_GRAHAM_CRACKER_SLICE = consumable("cream_pie_graham_cracker_slice", 3, 0.6f, tips(null, "graham_cracker_pie_crust_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CREAM_SWEET_ROLL_CHORUS_FRUIT = consumable("cream_sweet_roll_chorus_fruit", 8, 0.8f, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(MobEffects.JUMP, 300), fx(MobEffects.SLOW_FALLING, 300), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CREAM_SWEET_ROLL_GLOW_BERRY = consumable("cream_sweet_roll_glow_berry", 6, 0.7f, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"), fx(ModEffects.COMFORT, 300), fx(MobEffects.GLOWING, 300), fx(MobEffects.NIGHT_VISION, 300));
    public static final DeferredItem<Item> CREAM_SWEET_ROLL_SWEET_BERRY = consumable("cream_sweet_roll_sweet_berry", 5, 0.6f, tips(null, "cream_frosting_ingredient", "berry_ingredient"), fx(ModEffects.COMFORT, 300), fx(MobEffects.DIG_SPEED, 300));
    public static final DeferredItem<Item> CREAM_WHITE_CHOCOLATE = consumableFast("cream_white_chocolate", 5, 0.5f, tips(null, "cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> CREME_BRULEE_BOTTLE = bottle("creme_brulee_bottle", 10, 1.0f);
    public static final DeferredItem<Item> CUPCAKE_BASE = fastFood("cupcake_base", 2, 0.9f);
    public static final DeferredItem<Item> CUSTARD_BOTTLE = bottle("custard_bottle", 5, 0.8f);
    public static final DeferredItem<Item> CUSTARD_SUGAR_BOTTLE = bottle("custard_sugar_bottle", 6, 0.6f, tips(null, "sugar_ingredient"));
    public static final DeferredItem<Item> CYAN_GELATIN_DESSERT_SLICE = consumableFast("cyan_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> DARK_CHOCOLATE_APPLE = consumable("dark_chocolate_apple", 9, 0.8f, tips(null, "dark_chocolate_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> DARK_CHOCOLATE_BERRIES = consumable("dark_chocolate_berries", 8, 0.7f, tips(null, "dark_chocolate_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> DARK_CHOCOLATE_BOTTLE = bottle("dark_chocolate_bottle", 8, 0.3f);
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIPS = plain("dark_chocolate_chips");
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = food("dark_chocolate_chip_chocolate_cookie", 4, 0.4f, tips(null, "dark_chocolate_chips_ingredient"));
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("dark_chocolate_chip_chocolate_milkshake_bottle", 7, 1.2f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1800));
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_COOKIE = food("dark_chocolate_chip_cookie", 2, 0.5f, tips(null, "dark_chocolate_chips_ingredient"));
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_ICE_CREAM_CONE = consumableFast("dark_chocolate_chip_ice_cream_cone", 2, 1.2f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1200), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = bottle("dark_chocolate_chip_milkshake_bottle", 6, 1.1f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1800));
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_MINI_WAFFLE = consumableFast("dark_chocolate_chip_mini_waffle", 3, 1.7f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffects.NOURISHMENT, 1200), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_MUFFIN = consumableFast("dark_chocolate_chip_muffin", 4, 0.9f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHOCOLATE_PASTRY = consumable("dark_chocolate_chocolate_pastry", 4, 0.9f, tips(null, "dark_chocolate_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> DARK_CHOCOLATE_FUDGE = consumable("dark_chocolate_fudge", 4, 0.5f, fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> DARK_CHOCOLATE_MARSHMALLOW_STICK = stickConsumable("dark_chocolate_marshmallow_stick", 5, 0.3f, false, tips(null, "dark_chocolate_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> DARK_CHOCOLATE_PASTRY = consumable("dark_chocolate_pastry", 3, 0.9f, tips(null, "dark_chocolate_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> DARK_CHOCOLATE_TOAST = consumable("dark_chocolate_toast", 5, 0.5f, tips(null, "dark_chocolate_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> DICED_ONION = plain("diced_onion");
    public static final DeferredItem<Item> DICED_TOMATO = plain("diced_tomato");
    public static final DeferredItem<Item> DONUT_BASE = food("donut_base", 1, 1.1f);
    public static final DeferredItem<Item> DONUT_HOLE = food("donut_hole", 1, 0.4f);
    public static final DeferredItem<Item> DONUT_HOLE_SUGAR = consumable("donut_hole_sugar", 1, 0.4f, tips(null, "sugar_ingredient"), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> DONUT_SUGAR = consumable("donut_sugar", 3, 0.6f, tips(null, "sugar_ingredient"), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> DRAGON_BUN = food("dragon_bun", 9, 0.4f, tips("tooltip.compat.dragon_meat"));
    public static final DeferredItem<Item> DRAGON_BUN_CRIMSON_FUNGUS = food("dragon_bun_crimson_fungus", 9, 0.5f, tips("tooltip.compat.dragon_meat", "crimson_fungus_ingredient"));
    public static final DeferredItem<Item> DRAGON_BUN_WARPED_FUNGUS = food("dragon_bun_warped_fungus", 9, 0.6f, tips("tooltip.compat.dragon_meat", "warped_fungus_ingredient"));
    public static final DeferredItem<Item> DRAGON_BURGER = consumable("dragon_burger", 11, 0.4f, tips("tooltip.compat.dragon_meat"), fx(MobEffects.ABSORPTION, 1200), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> DRAGON_BURGER_CRIMSON_FUNGUS = consumable("dragon_burger_crimson_fungus", 11, 0.5f, tips("tooltip.compat.dragon_meat", "crimson_fungus_ingredient"), fx(MobEffects.ABSORPTION, 6000), fx(ModEffects.NOURISHMENT, 3600), fx(MobEffects.FIRE_RESISTANCE, 1200));
    public static final DeferredItem<Item> DRAGON_BURGER_WARPED_FUNGUS = consumable("dragon_burger_warped_fungus", 11, 0.6f, tips("tooltip.compat.dragon_meat", "warped_fungus_ingredient"), fx(MobEffects.ABSORPTION, 6000), fx(ModEffects.NOURISHMENT, 3600), fx(MobEffects.JUMP, 1200), fx(MobEffects.SLOW_FALLING, 1200));
    public static final DeferredItem<Item> DRAGON_PATTY = food("dragon_patty", 7, 0.6f, tips("tooltip.compat.dragon_meat"));
    public static final DeferredItem<Item> DRIED_COFFEE_BEANS = plain("dried_coffee_beans", "tooltip.compat.coffee");
    public static final DeferredItem<Item> DUMPLING_WRAPPERS = plain("dumpling_wrappers");
    public static final DeferredItem<Item> EGGPLANT_BUN = food("eggplant_bun", 5, 0.9f, tips("tooltip.compat.eggplant"));
    public static final DeferredItem<Item> EGGPLANT_BUN_CHEESE = food("eggplant_bun_cheese", 8, 0.9f, tips("tooltip.compat.eggplant", "cheese_ingredient"));
    public static final DeferredItem<Item> EGGPLANT_BUN_CHEESE_LETTUCE = food("eggplant_bun_cheese_lettuce", 10, 0.9f, tips("tooltip.compat.eggplant", "cheese_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> EGGPLANT_BUN_CHEESE_LETTUCE_TOMATO = food("eggplant_bun_cheese_lettuce_tomato", 10, 1.0f, tips("tooltip.compat.eggplant", "cheese_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> EGGPLANT_BUN_CHEESE_TOMATO = food("eggplant_bun_cheese_tomato", 10, 0.9f, tips("tooltip.compat.eggplant", "cheese_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> EGGPLANT_BUN_LETTUCE = food("eggplant_bun_lettuce", 7, 1.0f, tips("tooltip.compat.eggplant", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> EGGPLANT_BUN_LETTUCE_TOMATO = food("eggplant_bun_lettuce_tomato", 7, 1.1f, tips("tooltip.compat.eggplant", "lettuce_ingredient"));
    public static final DeferredItem<Item> EGGPLANT_BUN_TOMATO = food("eggplant_bun_tomato", 7, 1.0f, tips("tooltip.compat.eggplant", "tomato_ingredient"));
    public static final DeferredItem<Item> EGGPLANT_BURGER = consumable("eggplant_burger", 7, 0.9f, tips("tooltip.compat.eggplant"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> EGGPLANT_BURGER_LETTUCE = consumable("eggplant_burger_lettuce", 9, 1.0f, tips("tooltip.compat.eggplant", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> EGGPLANT_BURGER_TOMATO = consumable("eggplant_burger_tomato", 9, 1.0f, tips("tooltip.compat.eggplant", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> EGGPLANT_CHEESEBURGER = consumable("eggplant_cheeseburger", 10, 0.9f, tips("tooltip.compat.eggplant"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> EGGPLANT_CHEESEBURGER_LETTUCE = consumable("eggplant_cheeseburger_lettuce", 12, 0.9f, tips("tooltip.compat.eggplant", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 6000));
    public static final DeferredItem<Item> EGGPLANT_CHEESEBURGER_LETTUCE_TOMATO = consumable("eggplant_cheeseburger_lettuce_tomato", 12, 1.0f, tips("tooltip.compat.eggplant", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 6000));
    public static final DeferredItem<Item> EGGPLANT_CHEESEBURGER_TOMATO = consumable("eggplant_cheeseburger_tomato", 12, 0.9f, tips("tooltip.compat.eggplant", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 6000));
    public static final DeferredItem<Item> EGG_BURRITO = food("egg_burrito", 10, 0.6f, tips(null, "egg_ingredient"));
    public static final DeferredItem<Item> EGG_BURRITO_BACON = food("egg_burrito_bacon", 12, 0.7f, tips(null, "egg_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> EGG_BURRITO_CHEESE = food("egg_burrito_cheese", 11, 0.7f, tips(null, "egg_ingredient", "cheese_ingredient"));
    public static final DeferredItem<Item> EGG_BURRITO_CHEESE_BACON = food("egg_burrito_cheese_bacon", 13, 0.8f, tips(null, "egg_ingredient", "cheese_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> EGG_BURRITO_CHEESE_SAUSAGE = food("egg_burrito_cheese_sausage", 13, 0.8f, tips(null, "egg_ingredient", "cheese_ingredient", "sausage_ingredient"));
    public static final DeferredItem<Item> EGG_BURRITO_CHEESE_SAUSAGE_BACON = food("egg_burrito_cheese_sausage_bacon", 14, 0.8f, tips(null, "egg_ingredient", "cheese_ingredient", "sausage_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> EGG_BURRITO_SAUSAGE = food("egg_burrito_sausage", 12, 0.7f, tips(null, "egg_ingredient", "sausage_ingredient"));
    public static final DeferredItem<Item> EGG_POWDER = plain("egg_powder");
    public static final DeferredItem<Item> EGG_WHITES_BOTTLE = ingredientBottle("egg_whites_bottle");
    public static final DeferredItem<Item> EGG_YOLK = plain("egg_yolk");
    public static final DeferredItem<Item> ENDERMITE_MEATBALL = food("endermite_meatball", 4, 0.6f, tips("tooltip.compat.endermite_meat"));
    public static final DeferredItem<Item> ENDERMITE_MEATBALL_SANDWICH = consumable("endermite_meatball_sandwich", 10, 0.4f, tips("tooltip.compat.endermite_meat", "endermite_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200), fx(MobEffects.REGENERATION, 1200));
    public static final DeferredItem<Item> ENDERMITE_MEATBALL_STICK_1 = stickConsumable("endermite_meatball_stick_1", 5, 0.6f, false, tips("tooltip.compat.endermite_meat"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.REGENERATION, 1200));
    public static final DeferredItem<Item> ENDERMITE_MEATBALL_STICK_2 = stickConsumable("endermite_meatball_stick_2", 6, 0.6f, false, tips("tooltip.compat.endermite_meat"), fx(MobEffects.REGENERATION, 3600), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> ENDERMITE_MEATBALL_STICK_3 = stickConsumable("endermite_meatball_stick_3", 8, 0.7f, false, tips("tooltip.compat.endermite_meat"), fx(MobEffects.REGENERATION, 6000), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> ESPRESSO_POWDER = plain("espresso_powder", "tooltip.compat.coffee");
    public static final DeferredItem<Item> FISHCAKE = consumableFast("fishcake", 4, 0.9f, fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.WATER_BREATHING, 3600));
    public static final DeferredItem<Item> FISH_BACON_PIZZA_SLICE = food("fish_bacon_pizza_slice", 6, 0.9f, tips(null, "fish_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> FISH_BURRITO_RICE = food("fish_burrito_rice", 11, 0.6f, tips(null, "fish_ingredient", "rice_ingredient"));
    public static final DeferredItem<Item> FISH_CALZONE = consumableFast("fish_calzone", 7, 0.4f, tips(null, "fish_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> FISH_ONION_PIZZA_SLICE = food("fish_onion_pizza_slice", 8, 0.7f, tips(null, "fish_ingredient", "onion_ingredient"));
    public static final DeferredItem<Item> FISH_PIZZA_SLICE = food("fish_pizza_slice", 6, 0.6f, tips(null, "fish_ingredient"));
    public static final DeferredItem<Item> FISH_STICKS = food("fish_sticks", 6, 0.7f);
    public static final DeferredItem<Item> FISH_TACO = food("fish_taco", 8, 0.5f, tips(null, "fish_ingredient"));
    public static final DeferredItem<Item> FISH_TACO_KELP = consumable("fish_taco_kelp", 9, 0.5f, tips(null, "fish_ingredient", "kelp_ingredient"), fx(MobEffects.WATER_BREATHING, 600));
    public static final DeferredItem<Item> FISH_TACO_KELP_TACO_SAUCE = consumable("fish_taco_kelp_taco_sauce", 10, 0.7f, tips(null, "fish_ingredient", "kelp_ingredient", "taco_sauce_ingredient"), fx(ModEffects.NOURISHMENT, 1200), fx(MobEffects.WATER_BREATHING, 1200));
    public static final DeferredItem<Item> FISH_WRAP_KELP_ONION = consumable("fish_wrap_kelp_onion", 10, 0.6f, tips(null, "fish_ingredient", "kelp_ingredient", "onion_ingredient"), fx(MobEffects.WATER_BREATHING, 600));
    public static final DeferredItem<Item> FRIED_EGG_HASH_BROWN_SANDWICH = consumable("fried_egg_hash_brown_sandwich", 9, 1.2f, tips(null, "fried_egg_ingredient", "hash_browns_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> FRIED_EGG_PLATE = bowlFood("fried_egg_plate", 4, 0.4f);
    public static final DeferredItem<Item> FRUIT_SMOOTHIE_BOTTLE = bottle("fruit_smoothie_bottle", 4, 1.8f, fx(ModEffects.COMFORT, 6000), fx(MobEffects.DIG_SPEED, 1200), fx(MobEffects.LUCK, 1200), fx(ModEffects.NOURISHMENT, 1200), fx(MobEffects.DAMAGE_BOOST, 1200));
    public static final DeferredItem<Item> GELATIN = plain("gelatin");
    public static final DeferredItem<Item> GELATIN_DESSERT_SLICE = consumableFast("gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> GLOW_BERRY_CHEESECAKE_SLICE = consumable("glow_berry_cheesecake_slice", 3, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> GLOW_BERRY_COOKIE = food("glow_berry_cookie", 2, 0.5f, tips(null, "glow_berry_ingredient"));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CAKE_SLICE = food("glow_berry_cream_cake_slice", 2, 0.3f, tips(null, "glow_berry_cream_frosting_ingredient"));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT = food("glow_berry_cream_cake_slice_chorus_fruit", 3, 0.4f, tips(null, "glow_berry_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CAKE_SLICE_GLOW_BERRY = food("glow_berry_cream_cake_slice_glow_berry", 3, 0.3f, tips(null, "glow_berry_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CAKE_SLICE_SWEET_BERRY = food("glow_berry_cream_cake_slice_sweet_berry", 3, 0.3f, tips(null, "glow_berry_cream_frosting_ingredient", "berry_ingredient"));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE = consumableFast("glow_berry_cream_chocolate", 7, 0.4f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_CUPCAKE = consumableFast("glow_berry_cream_chocolate_cupcake", 4, 1.1f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(MobEffects.GLOWING, 1200), fx(MobEffects.NIGHT_VISION, 1200));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_DONUT = consumable("glow_berry_cream_chocolate_donut", 4, 0.7f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_PASTRY = consumable("glow_berry_cream_chocolate_pastry", 3, 1.0f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL = consumable("glow_berry_cream_chocolate_sweet_roll", 6, 0.7f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY = consumable("glow_berry_cream_chocolate_sweet_roll_glow_berry", 6, 0.7f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200), fx(MobEffects.GLOWING, 1200), fx(MobEffects.NIGHT_VISION, 1200));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CUPCAKE = consumableFast("glow_berry_cream_cupcake", 3, 1.0f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_DARK_CHOCOLATE = consumableFast("glow_berry_cream_dark_chocolate", 8, 0.3f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_DONUT = consumable("glow_berry_cream_donut", 3, 0.7f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_FROSTING_BOTTLE = bottle("glow_berry_cream_frosting_bottle", 4, 0.4f);
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_FROSTING_PIPING_BAG = pipingBag("glow_berry_cream_frosting_piping_bag", null, "tooltip.createfood.glow_berry_cream_frosting_ingredient");
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_MINI_WAFFLE = consumableFast("glow_berry_cream_mini_waffle", 3, 1.9f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_MINI_WAFFLE_GLOW_BERRY = consumableFast("glow_berry_cream_mini_waffle_glow_berry", 3, 1.9f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(MobEffects.GLOWING, 1200), fx(MobEffects.NIGHT_VISION, 1200), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_PASTRY = consumable("glow_berry_cream_pastry", 2, 1.0f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200), fx(MobEffects.GLOWING, 1200), fx(MobEffects.NIGHT_VISION, 1200));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_SWEET_ROLL = consumable("glow_berry_cream_sweet_roll", 5, 0.6f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_SWEET_ROLL_GLOW_BERRY = consumable("glow_berry_cream_sweet_roll_glow_berry", 5, 0.6f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_WHITE_CHOCOLATE = consumableFast("glow_berry_cream_white_chocolate", 6, 0.5f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> GLOW_BERRY_ICE_CREAM_BOWL = bowlFood("glow_berry_ice_cream_bowl", 3, 1.0f);
    public static final DeferredItem<Item> GLOW_BERRY_ICE_CREAM_CONE = consumableFast("glow_berry_ice_cream_cone", 2, 0.9f, fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> GLOW_BERRY_ICE_CREAM_SANDWICH = food("glow_berry_ice_cream_sandwich", 6, 0.6f, tips(null, "glow_berry_ice_cream_ingredient"));
    public static final DeferredItem<Item> GLOW_BERRY_ICE_CREAM_STICK = stickFood("glow_berry_ice_cream_stick", 1, 0.9f, true);
    public static final DeferredItem<Item> GLOW_BERRY_JAM_BOTTLE = bottle("glow_berry_jam_bottle", 4, 1.2f);
    public static final DeferredItem<Item> GLOW_BERRY_JAM_SANDWICH = consumable("glow_berry_jam_sandwich", 9, 0.4f, tips(null, "glow_berry_jam_ingredient"), fx(ModEffects.COMFORT, 600), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> GLOW_BERRY_JUICE_BOTTLE = bottle("glow_berry_juice_bottle", 2, 1.6f);
    public static final DeferredItem<Item> GLOW_BERRY_MILKSHAKE_BOTTLE = bottle("glow_berry_milkshake_bottle", 5, 1.0f, fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> GLOW_BERRY_PIE_SLICE = consumable("glow_berry_pie_slice", 3, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> GLOW_BERRY_POPSICLE = stickFood("glow_berry_popsicle", 3, 0.5f, false);
    public static final DeferredItem<Item> GRAHAM_CRACKER = food("graham_cracker", 2, 0.8f);
    public static final DeferredItem<Item> GRAHAM_CRACKER_CHOCOLATE = food("graham_cracker_chocolate", 4, 0.8f, tips(null, "chocolate_ingredient"));
    public static final DeferredItem<Item> GRAHAM_CRACKER_CHOCOLATE_MARSHMALLOW = food("graham_cracker_chocolate_marshmallow", 6, 0.7f, tips(null, "chocolate_ingredient", "marshmallow_ingredient"));
    public static final DeferredItem<Item> GRAHAM_CRACKER_CRUMBS = plain("graham_cracker_crumbs");
    public static final DeferredItem<Item> GRAHAM_CRACKER_MARSHMALLOW = food("graham_cracker_marshmallow", 3, 0.7f, tips(null, "marshmallow_ingredient"));
    public static final DeferredItem<Item> GRAHAM_CRACKER_PIE_CRUST = food("graham_cracker_pie_crust", 6, 0.7f);
    public static final DeferredItem<Item> GRAY_GELATIN_DESSERT_SLICE = consumableFast("gray_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> GREEN_GELATIN_DESSERT_SLICE = consumableFast("green_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> GRILLED_CHEESE_SANDWICH = food("grilled_cheese_sandwich", 9, 0.5f);
    public static final DeferredItem<Item> GROUND_BEEF = plain("ground_beef");
    public static final DeferredItem<Item> GROUND_CHICKEN = plain("ground_chicken");
    public static final DeferredItem<Item> GROUND_ENDERMITE = plain("ground_endermite", "tooltip.compat.endermite_meat");
    public static final DeferredItem<Item> GROUND_MUTTON = plain("ground_mutton");
    public static final DeferredItem<Item> GROUND_PORK = plain("ground_pork");
    public static final DeferredItem<Item> GROUND_RABBIT = plain("ground_rabbit");
    public static final DeferredItem<Item> GROUND_SAUSAGE = plain("ground_sausage");
    public static final DeferredItem<Item> GYRO_MEAT_SLICE = food("gyro_meat_slice", 4, 1.1f);
    public static final DeferredItem<Item> HAMBURGER = consumable("hamburger", 8, 0.6f, fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_BACON = consumable("hamburger_bacon", 10, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_BACON_LETTUCE = consumable("hamburger_bacon_lettuce", 11, 0.7f, tips(null, "bacon_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_BACON_LETTUCE_TOMATO = consumable("hamburger_bacon_lettuce_tomato", 11, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_CRIMSON_FUNGUS = consumable("hamburger_crimson_fungus", 11, 0.5f, tips(null, "crimson_fungus_ingredient"), fx(ModEffects.NOURISHMENT, 3600), fx(MobEffects.FIRE_RESISTANCE, 1200));
    public static final DeferredItem<Item> HAMBURGER_LETTUCE = consumable("hamburger_lettuce", 9, 0.7f, tips(null, "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_LETTUCE_TOMATO = consumable("hamburger_lettuce_tomato", 9, 0.8f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_ONION = consumable("hamburger_onion", 8, 0.8f, tips(null, "onion_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_ONION_BACON = consumable("hamburger_onion_bacon", 11, 0.7f, tips(null, "onion_ingredient", "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_ONION_BACON_LETTUCE = consumable("hamburger_onion_bacon_lettuce", 12, 0.8f, tips(null, "onion_ingredient", "bacon_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_ONION_LETTUCE = consumable("hamburger_onion_lettuce", 10, 0.7f, tips(null, "onion_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_PEANUT_BUTTER = consumable("hamburger_peanut_butter", 8, 0.8f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_PEANUT_BUTTER_BACON = consumable("hamburger_peanut_butter_bacon", 10, 0.8f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient", "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_TOMATO = consumable("hamburger_tomato", 9, 0.7f, tips(null, "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HAMBURGER_WARPED_FUNGUS = consumable("hamburger_warped_fungus", 11, 0.6f, tips(null, "warped_fungus_ingredient"), fx(ModEffects.NOURISHMENT, 3600), fx(MobEffects.SLOW_FALLING, 1200));
    public static final DeferredItem<Item> HASH_BROWNS = food("hash_browns", 4, 0.6f);
    public static final DeferredItem<Item> HASH_BROWN_FRIED_EGG_PLATE = bowlFood("hash_brown_fried_egg_plate", 8, 0.7f);
    public static final DeferredItem<Item> HASH_BROWN_PLATE = bowlFood("hash_brown_plate", 4, 0.6f);
    public static final DeferredItem<Item> HASH_BROWN_TOAST_PLATE = bowlFood("hash_brown_toast_plate", 8, 0.7f);
    public static final DeferredItem<Item> HOLLOW_CHOCOLATE = fastFood("hollow_chocolate", 2, 0.2f);
    public static final DeferredItem<Item> HOLLOW_DARK_CHOCOLATE = fastFood("hollow_dark_chocolate", 5, 0.2f);
    public static final DeferredItem<Item> HOLLOW_WHITE_CHOCOLATE = fastFood("hollow_white_chocolate", 3, 0.4f);
    public static final DeferredItem<Item> HONEYED_BERRIES = consumable("honeyed_berries", 7, 0.9f, tips(null, "honey_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> HONEYED_BISCUIT = consumable("honeyed_biscuit", 4, 0.7f, tips(null, "honey_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> HONEYED_CHOCOLATE_CUPCAKE = consumableFast("honeyed_chocolate_cupcake", 5, 1.1f, tips(null, "honey_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> HONEYED_CHOCOLATE_DONUT = consumable("honeyed_chocolate_donut", 4, 0.7f, tips(null, "honey_ingredient"), fx(MobEffects.MOVEMENT_SPEED, 3600));
    public static final DeferredItem<Item> HONEYED_CHOCOLATE_SWEET_ROLL = consumable("honeyed_chocolate_sweet_roll", 6, 0.8f, tips(null, "honey_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> HONEYED_CUPCAKE = consumableFast("honeyed_cupcake", 4, 1.0f, tips(null, "honey_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> HONEYED_DONUT = consumable("honeyed_donut", 3, 0.7f, tips(null, "honey_ingredient"), fx(MobEffects.MOVEMENT_SPEED, 1200));
    public static final DeferredItem<Item> HONEYED_MINI_WAFFLE = consumableFast("honeyed_mini_waffle", 3, 1.8f, tips(null, "honey_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(ModEffects.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> HONEYED_MUFFIN = consumableFast("honeyed_muffin", 4, 0.7f, tips(null, "honey_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> HONEYED_SWEET_ROLL = consumable("honeyed_sweet_roll", 5, 0.7f, tips(null, "honey_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> HONEYED_TOAST = consumable("honeyed_toast", 4, 0.8f, tips(null, "honey_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> HONEY_SANDWICH = consumableFast("honey_sandwich", 7, 0.7f, fx(MobEffects.MOVEMENT_SPEED, 600), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> HOT_CHOCOLATE_BOTTLE = bottle("hot_chocolate_bottle", 9, 0.9f, fx(ModEffects.COMFORT, 6000));
    public static final DeferredItem<Item> HOT_DARK_CHOCOLATE_BOTTLE = bottle("hot_dark_chocolate_bottle", 10, 0.8f, fx(ModEffects.COMFORT, 6000));
    public static final DeferredItem<Item> HOT_WHITE_CHOCOLATE_BOTTLE = bottle("hot_white_chocolate_bottle", 8, 1.0f, fx(ModEffects.COMFORT, 6000));
    public static final DeferredItem<Item> ICE_CREAM_BOWL = bowlFood("ice_cream_bowl", 2, 1.1f);
    public static final DeferredItem<Item> ICE_CREAM_CONE = consumableFast("ice_cream_cone", 2, 0.8f, fx(ModEffects.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> ICE_CREAM_SANDWICH = food("ice_cream_sandwich", 6, 0.7f);
    public static final DeferredItem<Item> ICE_CREAM_SANDWICH_NEAPOLITAN = food("ice_cream_sandwich_neapolitan", 7, 0.8f, tips(null, "chocolate_ice_cream_ingredient", "ice_cream_ingredient", "berry_ice_cream_ingredient"));
    public static final DeferredItem<Item> ICE_CREAM_STICK = stickFood("ice_cream_stick", 1, 0.8f, true);
    public static final DeferredItem<Item> KELP_ROLL_BEETROOT = consumable("kelp_roll_beetroot", 13, 0.6f, tips(null, "beetroot_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_BROWN_MUSHROOM = consumable("kelp_roll_brown_mushroom", 12, 0.6f, tips(null, "brown_mushroom_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_CRIMSON_FUNGUS = consumable("kelp_roll_crimson_fungus", 13, 0.6f, tips(null, "crimson_fungus_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_LETTUCE = consumable("kelp_roll_lettuce", 10, 0.4f, tips(null, "lettuce_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_ONION = consumable("kelp_roll_onion", 11, 0.6f, tips(null, "onion_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_RED_MUSHROOM = consumable("kelp_roll_red_mushroom", 12, 0.6f, tips(null, "red_mushroom_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_RICE = consumable("kelp_roll_rice", 12, 0.7f, tips(null, "rice_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_BEETROOT = consumable("kelp_roll_slice_beetroot", 6, 0.6f, tips(null, "beetroot_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_BROWN_MUSHROOM = consumable("kelp_roll_slice_brown_mushroom", 6, 0.6f, tips(null, "brown_mushroom_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_CRIMSON_FUNGUS = consumable("kelp_roll_slice_crimson_fungus", 7, 0.6f, tips(null, "crimson_fungus_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_LETTUCE = consumable("kelp_roll_slice_lettuce", 5, 0.4f, tips(null, "lettuce_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_ONION = consumable("kelp_roll_slice_onion", 5, 0.6f, tips(null, "onion_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_RED_MUSHROOM = consumable("kelp_roll_slice_red_mushroom", 6, 0.6f, tips(null, "red_mushroom_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_RICE = consumable("kelp_roll_slice_rice", 6, 0.7f, tips(null, "rice_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_TOMATO = consumable("kelp_roll_slice_tomato", 6, 0.5f, tips(null, "tomato_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_SLICE_WARPED_FUNGUS = consumable("kelp_roll_slice_warped_fungus", 7, 0.6f, tips(null, "warped_fungus_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_TOMATO = consumable("kelp_roll_tomato", 12, 0.5f, tips(null, "tomato_ingredient"));
    public static final DeferredItem<Item> KELP_ROLL_WARPED_FUNGUS = consumable("kelp_roll_warped_fungus", 13, 0.6f, tips(null, "warped_fungus_ingredient"));
    public static final DeferredItem<Item> LEATHER_SOUP_BOWL = bowlConsumable("leather_soup_bowl", 3, 1.5f, fx(ModEffects.COMFORT, 300));
    public static final DeferredItem<Item> LIGHT_BLUE_GELATIN_DESSERT_SLICE = consumableFast("light_blue_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> LIGHT_GRAY_GELATIN_DESSERT_SLICE = consumableFast("light_gray_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> LIME_GELATIN_DESSERT_SLICE = consumableFast("lime_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> MACARONI = food("macaroni", 2, 0.5f);
    public static final DeferredItem<Item> MACARONI_BOWL = bowlFood("macaroni_bowl", 3, 0.5f);
    public static final DeferredItem<Item> MACARONI_BOWL_BACON = bowlConsumable("macaroni_bowl_bacon", 4, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 1800));
    public static final DeferredItem<Item> MACARONI_BOWL_CHEESE = bowlFood("macaroni_bowl_cheese", 3, 0.6f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> MACARONI_BOWL_CHEESE_BACON = bowlConsumable("macaroni_bowl_cheese_bacon", 4, 0.6f, tips(null, "cheese_ingredient", "bacon_ingredient"), fx(ModEffects.COMFORT, 3600), fx(ModEffects.NOURISHMENT, 2400));
    public static final DeferredItem<Item> MACARONI_BOWL_CHEESE_SAUSAGE = bowlConsumable("macaroni_bowl_cheese_sausage", 5, 0.5f, tips(null, "cheese_ingredient", "sausage_ingredient"), fx(ModEffects.COMFORT, 2400), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> MACARONI_BOWL_SAUSAGE = bowlConsumable("macaroni_bowl_sausage", 5, 0.5f, tips(null, "sausage_ingredient"), fx(ModEffects.NOURISHMENT, 3000));
    public static final DeferredItem<Item> MAGENTA_GELATIN_DESSERT_SLICE = consumableFast("magenta_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> MAGMA_CREAM_MARSHMALLOW = consumableFast("magma_cream_marshmallow", 2, 0.2f, fx(MobEffects.FIRE_RESISTANCE, 600));
    public static final DeferredItem<Item> MAGMA_CREAM_MARSHMALLOW_STICK = stickConsumable("magma_cream_marshmallow_stick", 3, 0.2f, false, fx(ModEffects.COMFORT, 600), fx(MobEffects.FIRE_RESISTANCE, 1200));
    public static final DeferredItem<Item> MARSHMALLOW = fastFood("marshmallow", 2, 0.2f);
    public static final DeferredItem<Item> MARSHMALLOW_BUTTERSCOTCH_FUDGE = consumable("marshmallow_butterscotch_fudge", 3, 0.9f, tips(null, "marshmallow_ingredient"), fx(MobEffects.DIG_SPEED, 6000));
    public static final DeferredItem<Item> MARSHMALLOW_CARAMEL_FUDGE = consumable("marshmallow_caramel_fudge", 5, 0.5f, tips(null, "marshmallow_ingredient"), fx(MobEffects.DAMAGE_BOOST, 6000), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> MARSHMALLOW_CHOCOLATE = consumableFast("marshmallow_chocolate", 6, 0.5f, tips(null, "marshmallow_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> MARSHMALLOW_CHOCOLATE_FUDGE = consumable("marshmallow_chocolate_fudge", 3, 0.7f, tips(null, "marshmallow_ingredient"), fx(ModEffects.COMFORT, 6000));
    public static final DeferredItem<Item> MARSHMALLOW_COFFEE_TOFFEE_FUDGE = consumable("marshmallow_coffee_toffee_fudge", 4, 0.8f, tips("tooltip.compat.coffee", "marshmallow_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> MARSHMALLOW_DARK_CHOCOLATE = consumableFast("marshmallow_dark_chocolate", 7, 0.4f, tips(null, "marshmallow_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> MARSHMALLOW_DARK_CHOCOLATE_FUDGE = consumable("marshmallow_dark_chocolate_fudge", 4, 0.6f, tips(null, "marshmallow_ingredient"), fx(ModEffects.COMFORT, 6000));
    public static final DeferredItem<Item> MARSHMALLOW_STICK = stickConsumable("marshmallow_stick", 3, 0.2f, false, fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> MARSHMALLOW_TOFFEE_FUDGE = consumable("marshmallow_toffee_fudge", 4, 0.6f, tips(null, "marshmallow_ingredient"), fx(MobEffects.MOVEMENT_SPEED, 6000), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> MARSHMALLOW_WHITE_CHOCOLATE = consumableFast("marshmallow_white_chocolate", 5, 0.6f, tips(null, "marshmallow_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> MARSHMALLOW_WHITE_CHOCOLATE_FUDGE = consumable("marshmallow_white_chocolate_fudge", 2, 0.8f, tips(null, "marshmallow_ingredient"), fx(ModEffects.COMFORT, 6000));
    public static final DeferredItem<Item> MASHED_POTATOES_BOWL = bowlFood("mashed_potatoes_bowl", 7, 0.8f);
    public static final DeferredItem<Item> MASHED_POTATOES_BOWL_BACON = bowlConsumable("mashed_potatoes_bowl_bacon", 9, 1.0f, tips(null, "bacon_ingredient"));
    public static final DeferredItem<Item> MASHED_POTATOES_BOWL_MUSHROOM = bowlConsumable("mashed_potatoes_bowl_mushroom", 8, 0.8f, tips(null, "mushroom_ingredient"));
    public static final DeferredItem<Item> MEAT_PIE_FILLING = plain("meat_pie_filling");
    public static final DeferredItem<Item> MEAT_PIE_SLICE = consumable("meat_pie_slice", 5, 0.8f, fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> MELON_CREAM_CAKE_SLICE = food("melon_cream_cake_slice", 2, 0.3f, tips(null, "melon_cream_frosting_ingredient"));
    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE = consumableFast("melon_cream_chocolate", 7, 0.6f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE_CUPCAKE = consumableFast("melon_cream_chocolate_cupcake", 4, 1.2f, tips(null, "melon_cream_frosting_ingredient"), fx(MobEffects.REGENERATION, 1200));
    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE_DONUT = consumable("melon_cream_chocolate_donut", 5, 0.9f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 3600), fx(MobEffects.REGENERATION, 3600));
    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE_PASTRY = consumable("melon_cream_chocolate_pastry", 4, 1.1f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE_SWEET_ROLL = consumable("melon_cream_chocolate_sweet_roll", 6, 0.9f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> MELON_CREAM_CUPCAKE = consumableFast("melon_cream_cupcake", 3, 1.1f, tips(null, "melon_cream_frosting_ingredient"), fx(MobEffects.REGENERATION, 600));
    public static final DeferredItem<Item> MELON_CREAM_DARK_CHOCOLATE = consumableFast("melon_cream_dark_chocolate", 8, 0.5f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> MELON_CREAM_DONUT = consumable("melon_cream_donut", 4, 0.9f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200), fx(MobEffects.REGENERATION, 1200));
    public static final DeferredItem<Item> MELON_CREAM_FROSTING_BOTTLE = bottle("melon_cream_frosting_bottle", 4, 0.8f);
    public static final DeferredItem<Item> MELON_CREAM_FROSTING_PIPING_BAG = pipingBag("melon_cream_frosting_piping_bag", null, "tooltip.createfood.melon_cream_frosting_ingredient");
    public static final DeferredItem<Item> MELON_CREAM_MINI_WAFFLE = consumableFast("melon_cream_mini_waffle", 3, 1.6f, tips(null, "melon_cream_frosting_ingredient"), fx(MobEffects.REGENERATION, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> MELON_CREAM_PASTRY = consumable("melon_cream_pastry", 3, 1.1f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 1200), fx(MobEffects.REGENERATION, 1200));
    public static final DeferredItem<Item> MELON_CREAM_SWEET_ROLL = consumable("melon_cream_sweet_roll", 5, 0.8f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> MELON_CREAM_WHITE_CHOCOLATE = consumable("melon_cream_white_chocolate", 6, 0.7f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> MELON_CUSTARD_BOTTLE = bottle("melon_custard_bottle", 9, 0.8f);
    public static final DeferredItem<Item> MELON_ICE_CREAM_BOWL = bowlFood("melon_ice_cream_bowl", 3, 1.2f);
    public static final DeferredItem<Item> MELON_ICE_CREAM_CONE = consumableFast("melon_ice_cream_cone", 2, 1.1f, fx(MobEffects.REGENERATION, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> MELON_ICE_CREAM_SANDWICH = food("melon_ice_cream_sandwich", 6, 0.7f, tips(null, "melon_ice_cream_ingredient"));
    public static final DeferredItem<Item> MELON_ICE_CREAM_STICK = stickFood("melon_ice_cream_stick", 1, 1.1f, true);
    public static final DeferredItem<Item> MELON_JAM_BOTTLE = bottle("melon_jam_bottle", 4, 1.7f);
    public static final DeferredItem<Item> MELON_JAM_SANDWICH = consumableFast("melon_jam_sandwich", 8, 0.8f, tips(null, "melon_jam_ingredient"), fx(ModEffects.COMFORT, 600), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> MELON_MILKSHAKE_BOTTLE = bottle("melon_milkshake_bottle", 6, 1.0f, fx(ModEffects.COMFORT, 600), fx(MobEffects.REGENERATION, 600));
    public static final DeferredItem<Item> MERINGUE_BOWL = ingredientBowlItem("meringue_bowl");
    public static final DeferredItem<Item> MERINGUE_COOKIE = food("meringue_cookie", 2, 0.4f);
    public static final DeferredItem<Item> MILKSHAKE_BOTTLE = bottle("milkshake_bottle", 4, 0.8f);
    public static final DeferredItem<Item> MILK_POWDER = plain("milk_powder");
    public static final DeferredItem<Item> MINCED_DRAGON = plain("minced_dragon", "tooltip.compat.dragon_meat");
    public static final DeferredItem<Item> MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = food("mini_chocolate_graham_cracker_pie_crust", 4, 0.9f);
    public static final DeferredItem<Item> MINI_CHOCOLATE_PIE_GRAHAM_CRACKER = food("mini_chocolate_pie_graham_cracker", 7, 0.8f, tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final DeferredItem<Item> MINI_COOKIE_CREAM_PIE = food("mini_cookie_cream_pie", 8, 0.8f);
    public static final DeferredItem<Item> MINI_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = food("mini_cream_pie_chocolate_graham_cracker", 6, 1.0f, tips(null, "chocolate_graham_cracker_pie_crust_ingredient"));
    public static final DeferredItem<Item> MINI_CREAM_PIE_GRAHAM_CRACKER = food("mini_cream_pie_graham_cracker", 6, 0.9f, tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final DeferredItem<Item> MINI_GRAHAM_CRACKER_PIE_CRUST = food("mini_graham_cracker_pie_crust", 3, 0.9f);
    public static final DeferredItem<Item> MINI_SMORES_PIE = consumable("mini_smores_pie", 9, 0.7f, fx(ModEffects.COMFORT, 6000));
    public static final DeferredItem<Item> MINI_WAFFLE = fastFood("mini_waffle", 2, 0.6f);
    public static final DeferredItem<Item> MIXED_SALAD_BEETROOT_CARROT = consumable("mixed_salad_beetroot_carrot", 6, 0.7f, tips(null, "beetroot_ingredient", "carrot_ingredient"), fx(MobEffects.REGENERATION, 300));
    public static final DeferredItem<Item> MOLASSES_BOTTLE = ingredientBottleItem("molasses_bottle");
    public static final DeferredItem<Item> MOZZARELLA_STICKS = food("mozzarella_sticks", 5, 0.7f);
    public static final DeferredItem<Item> MUFFIN_BASE = fastFood("muffin_base", 3, 0.7f);
    public static final DeferredItem<Item> MUSHROOM_BACON_PIZZA_SLICE = food("mushroom_bacon_pizza_slice", 5, 0.9f, tips(null, "mushroom_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> MUSHROOM_CALZONE = consumableFast("mushroom_calzone", 6, 0.6f, tips(null, "mushroom_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> MUSHROOM_FISH_PIZZA_SLICE = food("mushroom_fish_pizza_slice", 6, 0.9f, tips(null, "mushroom_ingredient", "fish_ingredient"));
    public static final DeferredItem<Item> MUSHROOM_ONION_PIZZA_SLICE = food("mushroom_onion_pizza_slice", 7, 0.7f, tips(null, "mushroom_ingredient", "onion_ingredient"));
    public static final DeferredItem<Item> MUSHROOM_PIZZA_SLICE = food("mushroom_pizza_slice", 5, 0.6f, tips(null, "mushroom_ingredient"));
    public static final DeferredItem<Item> MUTTON_BURRITO_RICE = food("mutton_burrito_rice", 12, 0.7f, tips(null, "mutton_ingredient", "rice_ingredient"));
    public static final DeferredItem<Item> MUTTON_SANDWICH = food("mutton_sandwich", 7, 0.6f, tips(null, "mutton_ingredient"));
    public static final DeferredItem<Item> MUTTON_SANDWICH_BEETROOT = food("mutton_sandwich_beetroot", 7, 0.8f, tips(null, "mutton_ingredient", "beetroot_ingredient"));
    public static final DeferredItem<Item> MUTTON_STEW_BOWL = bowlConsumable("mutton_stew_bowl", 12, 0.9f, fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> MUTTON_TACO = food("mutton_taco", 9, 0.6f, tips(null, "mutton_ingredient"));
    public static final DeferredItem<Item> MUTTON_TACO_LETTUCE = food("mutton_taco_lettuce", 11, 0.6f, tips(null, "mutton_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> MUTTON_TACO_LETTUCE_TACO_SAUCE = consumable("mutton_taco_lettuce_taco_sauce", 11, 0.8f, tips(null, "mutton_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> MUTTON_WRAP_LETTUCE_TOMATO = food("mutton_wrap_lettuce_tomato", 10, 0.7f, tips(null, "mutton_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final DeferredItem<Item> NACHO_BOWL = bowlConsumable("nacho_bowl", 6, 0.9f, fx(MobEffects.LUCK, 1200));
    public static final DeferredItem<Item> ONION_BACON_PIZZA_SLICE = food("onion_bacon_pizza_slice", 7, 0.7f, tips(null, "onion_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> ONION_CALZONE = consumableFast("onion_calzone", 6, 0.5f, tips(null, "onion_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> ONION_PIZZA_SLICE = food("onion_pizza_slice", 6, 0.5f, tips(null, "onion_ingredient"));
    public static final DeferredItem<Item> ONION_RINGS = food("onion_rings", 5, 0.6f);
    public static final DeferredItem<Item> ORANGE_GELATIN_DESSERT_SLICE = consumableFast("orange_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> PAPRIKA = plain("paprika");
    public static final DeferredItem<Item> PASTA = food("pasta", 2, 0.8f);
    public static final DeferredItem<Item> PASTA_PLATE = bowlConsumable("pasta_plate", 5, 0.7f, fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_BEEF_MEATBALLS = bowlConsumable("pasta_plate_beef_meatballs", 8, 0.6f, tips(null, "beef_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_BUTTER = bowlConsumable("pasta_plate_butter", 6, 0.8f, tips(null, "butter_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> PASTA_PLATE_CHEESE = bowlFood("pasta_plate_cheese", 5, 0.9f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> PASTA_PLATE_CHEESE_TOMATO_SAUCE = bowlConsumable("pasta_plate_cheese_tomato_sauce", 9, 1.1f, tips(null, "tomato_sauce_ingredient", "cheese_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_CHICKEN_CUT = bowlConsumable("pasta_plate_chicken_cut", 7, 0.7f, tips(null, "chicken_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_CHICKEN_CUT_TOMATO_SAUCE = bowlConsumable("pasta_plate_chicken_cut_tomato_sauce", 11, 0.9f, tips(null, "tomato_sauce_ingredient", "chicken_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> PASTA_PLATE_EGGPLANT = bowlConsumable("pasta_plate_eggplant", 7, 0.8f, tips("tooltip.compat.eggplant", "eggplant_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_ENDERMITE_MEATBALLS = bowlConsumable("pasta_plate_endermite_meatballs", 8, 0.6f, tips("tooltip.compat.endermite_meat", "endermite_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_ENDERMITE_MEATBALLS_TOMATO_SAUCE = bowlConsumable("pasta_plate_endermite_meatballs_tomato_sauce", 12, 0.8f, tips("tooltip.compat.endermite_meat", "tomato_sauce_ingredient", "endermite_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> PASTA_PLATE_FISH = bowlConsumable("pasta_plate_fish", 7, 0.8f, tips(null, "fish_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_FISH_TOMATO_SAUCE = bowlConsumable("pasta_plate_fish_tomato_sauce", 11, 0.8f, tips(null, "tomato_sauce_ingredient", "fish_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> PASTA_PLATE_MUTTON_CHOP = bowlConsumable("pasta_plate_mutton_chop", 8, 0.6f, tips(null, "mutton_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_PORK_MEATBALLS = bowlConsumable("pasta_plate_pork_meatballs", 7, 0.8f, tips(null, "pork_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_PORK_MEATBALLS_TOMATO_SAUCE = bowlConsumable("pasta_plate_pork_meatballs_tomato_sauce", 11, 1.0f, tips(null, "tomato_sauce_ingredient", "pork_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> PASTA_PLATE_RABBIT_MEATBALLS = bowlConsumable("pasta_plate_rabbit_meatballs", 6, 0.9f, tips(null, "rabbit_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_RABBIT_MEATBALLS_TOMATO_SAUCE = bowlConsumable("pasta_plate_rabbit_meatballs_tomato_sauce", 10, 1.1f, tips(null, "tomato_sauce_ingredient", "rabbit_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> PASTA_PLATE_SLIME = bowlConsumable("pasta_plate_slime", 6, 0.8f, tips(null, "slime_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_SLIMEBALLS = bowlConsumable("pasta_plate_slimeballs", 8, 0.6f, tips(null, "slimeballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_SQUID_INK = bowlConsumable("pasta_plate_squid_ink", 6, 0.8f, tips(null, "squid_ink_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_STRIDER_MEATBALLS = bowlConsumable("pasta_plate_strider_meatballs", 8, 0.6f, tips("tooltip.compat.strider_meat", "strider_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTA_PLATE_STRIDER_MEATBALLS_TOMATO_SAUCE = bowlConsumable("pasta_plate_strider_meatballs_tomato_sauce", 12, 0.8f, tips("tooltip.compat.strider_meat", "tomato_sauce_ingredient", "strider_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> PASTA_PLATE_TOMATO_SAUCE = bowlConsumable("pasta_plate_tomato_sauce", 7, 0.9f, tips(null, "tomato_sauce_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PASTRY_BASE = food("pastry_base", 2, 0.8f);
    public static final DeferredItem<Item> PEANUT_BUTTER_APPLE_JAM_SANDWICH = consumable("peanut_butter_apple_jam_sandwich", 8, 0.8f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient", "apple_jam_ingredient"), fx(ModEffects.COMFORT, 600), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PEANUT_BUTTER_CHORUS_FRUIT_JAM_SANDWICH = consumable("peanut_butter_chorus_fruit_jam_sandwich", 11, 0.6f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient", "chorus_fruit_jam_ingredient"), fx(ModEffects.COMFORT, 600), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PEANUT_BUTTER_MELON_JAM_SANDWICH = consumable("peanut_butter_melon_jam_sandwich", 9, 0.9f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient", "melon_jam_ingredient"), fx(ModEffects.COMFORT, 600), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PINK_GELATIN_DESSERT_SLICE = consumable("pink_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> PITA_BREAD = food("pita_bread", 3, 1.8f);
    public static final DeferredItem<Item> PITA_DOUGH = plain("pita_dough");
    public static final DeferredItem<Item> PORK_BURRITO_RICE = food("pork_burrito_rice", 13, 0.6f, tips(null, "pork_ingredient", "rice_ingredient"));
    public static final DeferredItem<Item> PORK_MEATBALL = food("pork_meatball", 3, 0.8f);
    public static final DeferredItem<Item> PORK_MEATBALL_SANDWICH = consumable("pork_meatball_sandwich", 9, 0.6f, tips(null, "pork_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PORK_MEATBALL_STICK_1 = stickConsumable("pork_meatball_stick_1", 4, 0.8f, true, fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> PORK_MEATBALL_STICK_2 = stickConsumable("pork_meatball_stick_2", 5, 0.8f, false, fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PORK_MEATBALL_STICK_3 = stickConsumable("pork_meatball_stick_3", 6, 0.9f, false, fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> PORK_STEW_BOWL = bowlConsumable("pork_stew_bowl", 12, 0.9f, fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> PORK_TACO = food("pork_taco", 10, 0.5f, tips(null, "pork_ingredient"));
    public static final DeferredItem<Item> PORK_TACO_LETTUCE = food("pork_taco_lettuce", 11, 0.6f, tips(null, "pork_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> PORK_TACO_LETTUCE_TACO_SAUCE = consumable("pork_taco_lettuce_taco_sauce", 12, 0.7f, tips(null, "pork_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> PORK_WRAP_ONION_LETTUCE = food("pork_wrap_onion_lettuce", 11, 0.6f, tips(null, "pork_ingredient", "onion_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> POTATO_CHIPS = consumableFast("potato_chips", 2, 0.2f, fx(ModEffects.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> POTATO_CHIP_BOWL = bowlConsumable("potato_chip_bowl", 4, 0.5f, fx(ModEffects.COMFORT, 1200), fx(MobEffects.LUCK, 1200));
    public static final DeferredItem<Item> POWDERED_SUGAR = plain("powdered_sugar");
    public static final DeferredItem<Item> PRESSED_COCOA = plain("pressed_cocoa");
    public static final DeferredItem<Item> PUMPERNICKEL_BREAD = food("pumpernickel_bread", 5, 1.1f);
    public static final DeferredItem<Item> PUMPERNICKEL_BREAD_SLICE = fastFood("pumpernickel_bread_slice", 3, 0.5f);
    public static final DeferredItem<Item> PUMPERNICKEL_DOUGH = plain("pumpernickel_dough");
    public static final DeferredItem<Item> PUMPERNICKEL_TOAST_SLICE = fastFood("pumpernickel_toast_slice", 6, 1.3f);
    public static final DeferredItem<Item> PUMPKIN_CUSTARD_BOTTLE = bottle("pumpkin_custard_bottle", 10, 0.6f);
    public static final DeferredItem<Item> PUMPKIN_PIE_SLICE = food("pumpkin_pie_slice", 3, 1.0f);
    public static final DeferredItem<Item> PUMPKIN_PUREE_BOTTLE = ingredientBottleItem("pumpkin_puree_bottle");
    public static final DeferredItem<Item> PURPLE_GELATIN_DESSERT_SLICE = consumableFast("purple_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> RABBIT_BURRITO_RICE = food("rabbit_burrito_rice", 10, 0.6f, tips(null, "rabbit_ingredient", "rice_ingredient"));
    public static final DeferredItem<Item> RABBIT_CUTS = plain("rabbit_cuts");
    public static final DeferredItem<Item> RABBIT_JERKY = consumable("rabbit_jerky", 2, 1.6f, fx(MobEffects.MOVEMENT_SPEED, 1200), fx(MobEffects.DIG_SPEED, 1200));
    public static final DeferredItem<Item> RABBIT_MEATBALL = food("rabbit_meatball", 3, 0.6f);
    public static final DeferredItem<Item> RABBIT_MEATBALL_SANDWICH = consumable("rabbit_meatball_sandwich", 9, 0.4f, tips(null, "rabbit_meatballs_ingredient"), fx(ModEffects.NOURISHMENT, 1200), fx(MobEffects.MOVEMENT_SPEED, 1200), fx(MobEffects.DIG_SPEED, 1200));
    public static final DeferredItem<Item> RABBIT_MEATBALL_STICK_1 = stickConsumable("rabbit_meatball_stick_1", 4, 0.6f, false, fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> RABBIT_MEATBALL_STICK_2 = stickConsumable("rabbit_meatball_stick_2", 5, 0.6f, false, fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> RABBIT_MEATBALL_STICK_3 = stickConsumable("rabbit_meatball_stick_3", 6, 0.7f, false, fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> RABBIT_TACO = food("rabbit_taco", 8, 0.5f, tips(null, "rabbit_ingredient"));
    public static final DeferredItem<Item> RABBIT_TACO_LETTUCE = food("rabbit_taco_lettuce", 9, 0.5f, tips(null, "rabbit_ingredient", "lettuce_ingredient"));
    public static final DeferredItem<Item> RABBIT_TACO_LETTUCE_TACO_SAUCE = consumable("rabbit_taco_lettuce_taco_sauce", 10, 0.7f, tips(null, "rabbit_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> RABBIT_WRAP_ONION_POTATO = food("rabbit_wrap_onion_potato", 10, 0.6f, tips(null, "rabbit_ingredient", "onion_ingredient", "potato_ingredient"));
    public static final DeferredItem<Item> RAW_BACON_CALZONE = plain("raw_bacon_calzone", null, "tooltip.createfood.bacon_ingredient");
    public static final DeferredItem<Item> RAW_BEEF_MEATBALL = plain("raw_beef_meatball");
    public static final DeferredItem<Item> RAW_BERRY_COOKIE = plain("raw_berry_cookie", null, "tooltip.createfood.berry_ingredient");
    public static final DeferredItem<Item> RAW_BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE = plain("raw_butterscotch_chip_chocolate_cookie", null, "tooltip.createfood.butterscotch_chips_ingredient");
    public static final DeferredItem<Item> RAW_BUTTERSCOTCH_CHIP_COOKIE = plain("raw_butterscotch_chip_cookie", null, "tooltip.createfood.butterscotch_chips_ingredient");
    public static final DeferredItem<Item> RAW_BUTTERSCOTCH_CHIP_MUFFIN = plain("raw_butterscotch_chip_muffin", null, "tooltip.createfood.butterscotch_chips_ingredient");
    public static final DeferredItem<Item> RAW_CAKE_BASE = plain("raw_cake_base");
    public static final DeferredItem<Item> RAW_CALZONE = plain("raw_calzone");
    public static final DeferredItem<Item> RAW_CARAMEL_CHIP_CHOCOLATE_COOKIE = plain("raw_caramel_chip_chocolate_cookie", null, "tooltip.createfood.caramel_chips_ingredient");
    public static final DeferredItem<Item> RAW_CARAMEL_CHIP_COOKIE = plain("raw_caramel_chip_cookie", null, "tooltip.createfood.caramel_chips_ingredient");
    public static final DeferredItem<Item> RAW_CARAMEL_CHIP_MUFFIN = plain("raw_caramel_chip_muffin", null, "tooltip.createfood.caramel_chips_ingredient");
    public static final DeferredItem<Item> RAW_CHEESE_CALZONE = plain("raw_cheese_calzone");
    public static final DeferredItem<Item> RAW_CHICKEN_PATTY = plain("raw_chicken_patty");
    public static final DeferredItem<Item> RAW_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = plain("raw_chocolate_chip_chocolate_cookie", null, "tooltip.createfood.chocolate_chips_ingredient");
    public static final DeferredItem<Item> RAW_CHOCOLATE_CHIP_COOKIE = plain("raw_chocolate_chip_cookie", null, "tooltip.createfood.chocolate_chips_ingredient");
    public static final DeferredItem<Item> RAW_CHOCOLATE_CHIP_MUFFIN = plain("raw_chocolate_chip_muffin", null, "tooltip.createfood.chocolate_chips_ingredient");
    public static final DeferredItem<Item> RAW_CHOCOLATE_CUPCAKE_BASE = plain("raw_chocolate_cupcake_base");
    public static final DeferredItem<Item> RAW_CHOCOLATE_PASTRY_BASE = plain("raw_chocolate_pastry_base");
    public static final DeferredItem<Item> RAW_CHOCOLATE_SWEET_ROLL_BASE = plain("raw_chocolate_sweet_roll_base");
    public static final DeferredItem<Item> RAW_CHORUS_FRUIT_COOKIE = plain("raw_chorus_fruit_cookie", null, "tooltip.createfood.chorus_fruit_ingredient");
    public static final DeferredItem<Item> RAW_CINNAMON_SWEET_ROLL_BASE = plain("raw_cinnamon_sweet_roll_base", "tooltip.compat.cinnamon");
    public static final DeferredItem<Item> RAW_CUPCAKE_BASE = plain("raw_cupcake_base");
    public static final DeferredItem<Item> RAW_DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = plain("raw_dark_chocolate_chip_chocolate_cookie", null, "tooltip.createfood.dark_chocolate_chips_ingredient");
    public static final DeferredItem<Item> RAW_DARK_CHOCOLATE_CHIP_COOKIE = plain("raw_dark_chocolate_chip_cookie", null, "tooltip.createfood.dark_chocolate_chips_ingredient");
    public static final DeferredItem<Item> RAW_DARK_CHOCOLATE_CHIP_MUFFIN = plain("raw_dark_chocolate_chip_muffin", null, "tooltip.createfood.dark_chocolate_chips_ingredient");
    public static final DeferredItem<Item> RAW_ENDERMITE_MEATBALL = plain("raw_endermite_meatball", "tooltip.compat.endermite_meat");
    public static final DeferredItem<Item> RAW_FISHCAKE = plain("raw_fishcake");
    public static final DeferredItem<Item> RAW_FISH_CALZONE = plain("raw_fish_calzone", null, "tooltip.createfood.fish_ingredient");
    public static final DeferredItem<Item> RAW_FISH_STICKS = plain("raw_fish_sticks");
    public static final DeferredItem<Item> RAW_FLESH_COOKIE = plain("raw_flesh_cookie", "tooltip.compat.raw_flesh_cookie", "tooltip.createfood.flesh_ingredient");
    public static final DeferredItem<Item> RAW_GINGER_COOKIE = plain("raw_ginger_cookie", "tooltip.compat.raw_ginger_cookie", "tooltip.createfood.ginger_ingredient");
    public static final DeferredItem<Item> RAW_GLOW_BERRY_COOKIE = plain("raw_glow_berry_cookie", null, "tooltip.createfood.glow_berry_ingredient");
    public static final DeferredItem<Item> RAW_GREEN_TEA_COOKIE = plain("raw_green_tea_cookie", "tooltip.compat.raw_green_tea_cookie");
    public static final DeferredItem<Item> RAW_GYRO_MEAT_BLOCK = plain("raw_gyro_meat_block");
    public static final DeferredItem<Item> RAW_HONEY_COOKIE = plain("raw_honey_cookie");
    public static final DeferredItem<Item> RAW_MACARONI = plain("raw_macaroni");
    public static final DeferredItem<Item> RAW_MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = plain("raw_mini_chocolate_graham_cracker_pie_crust");
    public static final DeferredItem<Item> RAW_MINI_CHOCOLATE_PIE_GRAHAM_CRACKER = plain("raw_mini_chocolate_pie_graham_cracker", null, "tooltip.createfood.graham_cracker_pie_crust_ingredient");
    public static final DeferredItem<Item> RAW_MINI_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = plain("raw_mini_cream_pie_chocolate_graham_cracker", null, "tooltip.createfood.chocolate_graham_cracker_pie_crust_ingredient");
    public static final DeferredItem<Item> RAW_MINI_CREAM_PIE_GRAHAM_CRACKER = plain("raw_mini_cream_pie_graham_cracker", null, "tooltip.createfood.graham_cracker_pie_crust_ingredient");
    public static final DeferredItem<Item> RAW_MINI_GRAHAM_CRACKER_PIE_CRUST = plain("raw_mini_graham_cracker_pie_crust");
    public static final DeferredItem<Item> RAW_MOZZARELLA_STICKS = plain("raw_mozzarella_sticks");
    public static final DeferredItem<Item> RAW_MUFFIN_BASE = plain("raw_muffin_base");
    public static final DeferredItem<Item> RAW_MUSHROOM_CALZONE = plain("raw_mushroom_calzone", null, "tooltip.createfood.mushroom_ingredient");
    public static final DeferredItem<Item> RAW_ONION_CALZONE = plain("raw_onion_calzone", null, "tooltip.createfood.onion_ingredient");
    public static final DeferredItem<Item> RAW_ONION_RINGS = plain("raw_onion_rings");
    public static final DeferredItem<Item> RAW_PASTRY_BASE = plain("raw_pastry_base");
    public static final DeferredItem<Item> RAW_PORK_MEATBALL = plain("raw_pork_meatball");
    public static final DeferredItem<Item> RAW_RABBIT_MEATBALL = plain("raw_rabbit_meatball");
    public static final DeferredItem<Item> RAW_SAUSAGES = plain("raw_sausages");
    public static final DeferredItem<Item> RAW_SAUSAGE_CALZONE = plain("raw_sausage_calzone", null, "tooltip.createfood.sausage_ingredient");
    public static final DeferredItem<Item> RAW_SAUSAGE_PATTY = plain("raw_sausage_patty");
    public static final DeferredItem<Item> RAW_SAUSAGE_ROLL = plain("raw_sausage_roll");
    public static final DeferredItem<Item> RAW_SAUSAGE_ROLL_CHEESE = plain("raw_sausage_roll_cheese", null, "tooltip.createfood.cheese_ingredient");
    public static final DeferredItem<Item> RAW_SNICKERDOODLE = plain("raw_snickerdoodle", "tooltip.compat.raw_snickerdoodle");
    public static final DeferredItem<Item> RAW_SOUL_BERRY_COOKIE = plain("raw_soul_berry_cookie", "tooltip.compat.raw_flesh_cookie", "tooltip.createfood.soul_berry_ingredient");
    public static final DeferredItem<Item> RAW_SPICY_SAUSAGES = plain("raw_spicy_sausages");
    public static final DeferredItem<Item> RAW_SPICY_SAUSAGE_ROLL = plain("raw_spicy_sausage_roll");
    public static final DeferredItem<Item> RAW_SPICY_SAUSAGE_ROLL_CHEESE = plain("raw_spicy_sausage_roll_cheese", null, "tooltip.createfood.cheese_ingredient");
    public static final DeferredItem<Item> RAW_SPIDER_EYE_COOKIE = plain("raw_spider_eye_cookie", "tooltip.compat.raw_flesh_cookie", "tooltip.createfood.spider_eye_ingredient");
    public static final DeferredItem<Item> RAW_STRIDER_MEATBALL = plain("raw_strider_meatball", "tooltip.compat.strider_meat");
    public static final DeferredItem<Item> RAW_SUGAR_COOKIE = plain("raw_sugar_cookie", "tooltip.compat.raw_sugar_cookie");
    public static final DeferredItem<Item> RAW_SWEET_ROLL_BASE = plain("raw_sweet_roll_base");
    public static final DeferredItem<Item> RAW_TATER_TOTS = plain("raw_tater_tots");
    public static final DeferredItem<Item> RAW_TOFFEE_CHIP_CHOCOLATE_COOKIE = plain("raw_toffee_chip_chocolate_cookie", null, "tooltip.createfood.toffee_chips_ingredient");
    public static final DeferredItem<Item> RAW_TOFFEE_CHIP_COOKIE = plain("raw_toffee_chip_cookie", null, "tooltip.createfood.toffee_chips_ingredient");
    public static final DeferredItem<Item> RAW_TOFFEE_CHIP_MUFFIN = plain("raw_toffee_chip_muffin", null, "tooltip.createfood.toffee_chips_ingredient");
    public static final DeferredItem<Item> RAW_UBE_CAKE_BASE = plain("raw_ube_cake_base", "tooltip.compat.ube");
    public static final DeferredItem<Item> RAW_UBE_COOKIE = plain("raw_ube_cookie", "tooltip.compat.ube");
    public static final DeferredItem<Item> RAW_WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = plain("raw_white_chocolate_chip_chocolate_cookie", null, "tooltip.createfood.white_chocolate_chips_ingredient");
    public static final DeferredItem<Item> RAW_WHITE_CHOCOLATE_CHIP_COOKIE = plain("raw_white_chocolate_chip_cookie", null, "tooltip.createfood.white_chocolate_chips_ingredient");
    public static final DeferredItem<Item> RAW_WHITE_CHOCOLATE_CHIP_MUFFIN = plain("raw_white_chocolate_chip_muffin", null, "tooltip.createfood.white_chocolate_chips_ingredient");
    public static final DeferredItem<Item> RED_GELATIN_DESSERT_SLICE = consumableFast("red_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> SALT = plain("salt");
    public static final DeferredItem<Item> SALT_DOUGH = plain("salt_dough");
    public static final DeferredItem<Item> SALT_DOUGH_SMALL = plain("salt_dough_small");
    public static final DeferredItem<Item> SAUSAGES = consumable("sausages", 6, 0.4f, fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> SAUSAGE_BACON_PIZZA_SLICE = food("sausage_bacon_pizza_slice", 6, 0.8f, tips(null, "sausage_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT = food("sausage_biscuit", 12, 0.4f);
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_BACON = food("sausage_biscuit_bacon", 13, 0.6f, tips(null, "bacon_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_CHEESE = food("sausage_biscuit_cheese", 14, 0.4f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_CHEESE_BACON = food("sausage_biscuit_cheese_bacon", 15, 0.4f, tips(null, "cheese_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_CHEESE_FRIED_EGG = food("sausage_biscuit_cheese_fried_egg", 14, 0.6f, tips(null, "cheese_ingredient", "fried_egg_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_CHEESE_FRIED_EGG_BACON = food("sausage_biscuit_cheese_fried_egg_bacon", 16, 0.6f, tips(null, "cheese_ingredient", "fried_egg_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_FRIED_EGG = food("sausage_biscuit_fried_egg", 12, 0.8f, tips(null, "fried_egg_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_FRIED_EGG_BACON = food("sausage_biscuit_fried_egg_bacon", 14, 0.8f, tips(null, "fried_egg_ingredient", "bacon_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH = consumable("sausage_biscuit_sandwich", 17, 0.5f, fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_BACON = consumable("sausage_biscuit_sandwich_bacon", 18, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_CHEESE = consumable("sausage_biscuit_sandwich_cheese", 19, 0.6f, tips(null, "cheese_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_CHEESE_BACON = consumable("sausage_biscuit_sandwich_cheese_bacon", 20, 0.7f, tips(null, "cheese_ingredient", "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG = consumable("sausage_biscuit_sandwich_cheese_fried_egg", 19, 0.7f, tips(null, "cheese_ingredient", "fried_egg_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG_BACON = consumable("sausage_biscuit_sandwich_cheese_fried_egg_bacon", 21, 0.7f, tips(null, "cheese_ingredient", "fried_egg_ingredient", "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG = consumable("sausage_biscuit_sandwich_fried_egg", 17, 0.6f, tips(null, "fried_egg_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG_BACON = consumable("sausage_biscuit_sandwich_fried_egg_bacon", 19, 0.6f, tips(null, "fried_egg_ingredient", "bacon_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_BITS = food("sausage_bits", 2, 0.2f);
    public static final DeferredItem<Item> SAUSAGE_CALZONE = consumableFast("sausage_calzone", 7, 0.5f, tips(null, "sausage_ingredient"), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SAUSAGE_FISH_PIZZA_SLICE = food("sausage_fish_pizza_slice", 7, 0.8f, tips(null, "sausage_ingredient", "fish_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_MUSHROOM_PIZZA_SLICE = food("sausage_mushroom_pizza_slice", 6, 0.8f, tips(null, "sausage_ingredient", "mushroom_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_ONION_PIZZA_SLICE = food("sausage_onion_pizza_slice", 8, 0.6f, tips(null, "sausage_ingredient", "onion_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_PATTY = food("sausage_patty", 6, 0.3f);
    public static final DeferredItem<Item> SAUSAGE_PIZZA_SLICE = food("sausage_pizza_slice", 6, 0.5f, tips(null, "sausage_ingredient"));
    public static final DeferredItem<Item> SAUSAGE_ROLL = fastFood("sausage_roll", 4, 0.5f);
    public static final DeferredItem<Item> SAUSAGE_ROLL_CHEESE = fastFood("sausage_roll_cheese", 4, 0.6f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> SCRAMBLED_EGGS_PLATE = bowlConsumable("scrambled_eggs_plate", 6, 0.7f);
    public static final DeferredItem<Item> SCRAMBLED_EGGS_PLATE_BACON = bowlConsumable("scrambled_eggs_plate_bacon", 8, 0.8f, tips(null, "bacon_ingredient"));
    public static final DeferredItem<Item> SCRAMBLED_EGGS_PLATE_CHEESE = bowlConsumable("scrambled_eggs_plate_cheese", 7, 0.8f, tips(null, "cheese_ingredient"));
    public static final DeferredItem<Item> SCRAMBLED_EGGS_PLATE_MUSHROOM = bowlConsumable("scrambled_eggs_plate_mushroom", 7, 0.7f, tips(null, "mushroom_ingredient"));
    public static final DeferredItem<Item> SCRAMBLED_EGGS_PLATE_TOMATO = bowlConsumable("scrambled_eggs_plate_tomato", 7, 0.7f, tips(null, "tomato_ingredient"));
    public static final DeferredItem<Item> SCRAMBLED_EGG_SANDWICH = food("scrambled_egg_sandwich", 9, 0.9f, tips(null, "scrambled_egg_ingredient"));
    public static final DeferredItem<Item> SHREDDED_BEETROOT = plain("shredded_beetroot");
    public static final DeferredItem<Item> SHREDDED_CARROT = plain("shredded_carrot");
    public static final DeferredItem<Item> SHREDDED_POTATO = plain("shredded_potato");
    public static final DeferredItem<Item> SLICED_BEETROOT = fastFood("sliced_beetroot", 2, 0.3f);
    public static final DeferredItem<Item> SLICED_BROWN_MUSHROOM = fastFood("sliced_brown_mushroom", 1, 1.1f);
    public static final DeferredItem<Item> SLICED_BROWN_MUSHROOM_SALT = consumableFast("sliced_brown_mushroom_salt", 1, 1.1f, tips(null, "salt_ingredient"), fx(MobEffects.NIGHT_VISION, 100));
    public static final DeferredItem<Item> SLICED_CARROT = fastFood("sliced_carrot", 1, 0.4f);
    public static final DeferredItem<Item> SLICED_CRIMSON_FUNGUS = fastFood("sliced_crimson_fungus", 2, 0.9f);
    public static final DeferredItem<Item> SLICED_CRIMSON_FUNGUS_SALT = consumableFast("sliced_crimson_fungus_salt", 2, 0.9f, tips(null, "salt_ingredient"), fx(MobEffects.NIGHT_VISION, 600));
    public static final DeferredItem<Item> SLICED_ONION = food("sliced_onion", 2, 0.4f);
    public static final DeferredItem<Item> SLICED_POTATO = fastFood("sliced_potato", 1, 0.6f);
    public static final DeferredItem<Item> SLICED_RED_MUSHROOM = fastFood("sliced_red_mushroom", 1, 1.1f);
    public static final DeferredItem<Item> SLICED_RED_MUSHROOM_SALT = consumableFast("sliced_red_mushroom_salt", 1, 1.1f, tips(null, "salt_ingredient"), fx(MobEffects.NIGHT_VISION, 100));
    public static final DeferredItem<Item> SLICED_TOMATO = fastFood("sliced_tomato", 1, 0.6f);
    public static final DeferredItem<Item> SLICED_WARPED_FUNGUS = fastFood("sliced_warped_fungus", 2, 0.9f);
    public static final DeferredItem<Item> SLICED_WARPED_FUNGUS_SALT = consumableFast("sliced_warped_fungus_salt", 2, 0.9f, tips(null, "salt_ingredient"), fx(MobEffects.NIGHT_VISION, 600));
    public static final DeferredItem<Item> SMALL_BEEF_MEATBALLS = consumable("small_beef_meatballs", 3, 0.7f, fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SMALL_ENDERMITE_MEATBALLS = consumable("small_endermite_meatballs", 3, 0.7f, tips("tooltip.compat.endermite_meat"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.REGENERATION, 600));
    public static final DeferredItem<Item> SMALL_PORK_MEATBALLS = consumable("small_pork_meatballs", 2, 0.9f, fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SMALL_RABBIT_MEATBALLS = consumable("small_rabbit_meatballs", 2, 0.7f, fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.MOVEMENT_SPEED, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final DeferredItem<Item> SMALL_SLIMEBALLS = food("small_slimeballs", 3, 0.7f);
    public static final DeferredItem<Item> SMALL_STRIDER_MEATBALLS = consumable("small_strider_meatballs", 3, 0.7f, tips("tooltip.compat.strider_meat"), fx(MobEffects.FIRE_RESISTANCE, 600), fx(ModEffects.NOURISHMENT, 600));
    public static final DeferredItem<Item> SMORE = consumable("smore", 7, 0.5f, fx(ModEffects.COMFORT, 6000));
    public static final DeferredItem<Item> SMORES_PIE_SLICE = consumable("smores_pie_slice", 4, 0.8f, fx(ModEffects.COMFORT, 1800));
    public static final DeferredItem<Item> SOUR_CREAM_BOTTLE = ingredientBottle("sour_cream_bottle");
    public static final DeferredItem<Item> SPICY_CHICKEN_NUGGETS = consumable("spicy_chicken_nuggets", 4, 1.1f, fx(MobEffects.FIRE_RESISTANCE, 600), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> SPICY_SAUSAGES = consumable("spicy_sausages", 6, 0.8f, fx(MobEffects.FIRE_RESISTANCE, 600), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> SPICY_SAUSAGE_ROLL = consumableFast("spicy_sausage_roll", 5, 0.5f, fx(MobEffects.FIRE_RESISTANCE, 1200));
    public static final DeferredItem<Item> SPICY_SAUSAGE_ROLL_CHEESE = consumableFast("spicy_sausage_roll_cheese", 5, 0.6f, tips(null, "cheese_ingredient"), fx(MobEffects.FIRE_RESISTANCE, 1200));
    public static final DeferredItem<Item> STRIDER_MEATBALL = food("strider_meatball", 4, 0.6f, tips("tooltip.compat.strider_meat"));
    public static final DeferredItem<Item> STRIDER_MEATBALL_SANDWICH = consumable("strider_meatball_sandwich", 10, 0.4f, tips("tooltip.compat.strider_meat", "strider_meatballs_ingredient"), fx(MobEffects.FIRE_RESISTANCE, 1200), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> STRIDER_MEATBALL_STICK_1 = stickConsumable("strider_meatball_stick_1", 5, 0.6f, false, tips("tooltip.compat.strider_meat"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.FIRE_RESISTANCE, 1200));
    public static final DeferredItem<Item> STRIDER_MEATBALL_STICK_2 = stickConsumable("strider_meatball_stick_2", 6, 0.6f, false, tips("tooltip.compat.strider_meat"), fx(MobEffects.FIRE_RESISTANCE, 3600), fx(ModEffects.NOURISHMENT, 1200));
    public static final DeferredItem<Item> STRIDER_MEATBALL_STICK_3 = stickConsumable("strider_meatball_stick_3", 8, 0.7f, false, tips("tooltip.compat.strider_meat"), fx(MobEffects.FIRE_RESISTANCE, 6000), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> SUGAR_DOUGH = plain("sugar_dough");
    public static final DeferredItem<Item> SUGAR_DOUGH_SMALL = plain("sugar_dough_small");
    public static final DeferredItem<Item> SWEET_ROLL_BASE = food("sweet_roll_base", 4, 0.6f);
    public static final DeferredItem<Item> TACO_SAUCE_BOTTLE = ingredientBottleItem("taco_sauce_bottle");
    public static final DeferredItem<Item> TACO_SHELL = plain("taco_shell");
    public static final DeferredItem<Item> TATER_TOTS = food("tater_tots", 4, 0.6f);
    public static final DeferredItem<Item> TOAST_FRIED_EGG_PLATE = bowlFood("toast_fried_egg_plate", 8, 0.7f);
    public static final DeferredItem<Item> TOAST_PLATE = bowlFood("toast_plate", 3, 0.3f);
    public static final DeferredItem<Item> TOAST_SLICE = fastFood("toast_slice", 3, 0.3f);
    public static final DeferredItem<Item> TOFFEE = fastFood("toffee", 1, 1.8f);
    public static final DeferredItem<Item> TOFFEE_APPLE = consumable("toffee_apple", 8, 0.8f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> TOFFEE_BERRIES = consumable("toffee_berries", 6, 0.9f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> TOFFEE_CHIPS = plain("toffee_chips");
    public static final DeferredItem<Item> TOFFEE_CHIP_CHOCOLATE_COOKIE = food("toffee_chip_chocolate_cookie", 3, 0.6f, tips(null, "toffee_chips_ingredient"));
    public static final DeferredItem<Item> TOFFEE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("toffee_chip_chocolate_milkshake_bottle", 6, 1.3f, tips(null, "toffee_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final DeferredItem<Item> TOFFEE_CHIP_COOKIE = food("toffee_chip_cookie", 2, 0.7f, tips(null, "toffee_chips_ingredient"));
    public static final DeferredItem<Item> TOFFEE_CHIP_ICE_CREAM_CONE = consumableFast("toffee_chip_ice_cream_cone", 2, 1.2f, tips(null, "toffee_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> TOFFEE_CHIP_MILKSHAKE_BOTTLE = bottle("toffee_chip_milkshake_bottle", 5, 1.2f, tips(null, "toffee_chips_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final DeferredItem<Item> TOFFEE_CHIP_MINI_WAFFLE = consumableFast("toffee_chip_mini_waffle", 3, 1.7f, tips(null, "toffee_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.MOVEMENT_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> TOFFEE_CHIP_MUFFIN = consumableFast("toffee_chip_muffin", 3, 0.9f, tips(null, "toffee_chips_ingredient"), fx(ModEffects.NOURISHMENT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final DeferredItem<Item> TOFFEE_CHOCOLATE = consumable("toffee_chocolate", 7, 0.8f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> TOFFEE_CHOCOLATE_PASTRY = consumable("toffee_chocolate_pastry", 3, 1.1f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> TOFFEE_CHOCOLATE_SWEET_ROLL = consumable("toffee_chocolate_sweet_roll", 7, 0.8f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> TOFFEE_DARK_CHOCOLATE = consumable("toffee_dark_chocolate", 8, 0.7f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> TOFFEE_FUDGE = consumable("toffee_fudge", 4, 0.5f, fx(ModEffects.COMFORT, 3600), fx(MobEffects.MOVEMENT_SPEED, 3600));
    public static final DeferredItem<Item> TOFFEE_PASTRY = consumable("toffee_pastry", 2, 1.1f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final DeferredItem<Item> TOFFEE_SWEET_ROLL = consumable("toffee_sweet_roll", 6, 0.7f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> TOFFEE_TOAST = consumable("toffee_toast", 3, 0.9f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> TOFFEE_WHITE_CHOCOLATE = consumable("toffee_white_chocolate", 6, 0.9f, tips(null, "toffee_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> TORTILLA_CHIP_BOWL = bowlFood("tortilla_chip_bowl", 4, 0.7f, tips("tooltip.compat.corn"));
    public static final DeferredItem<Item> UBE_CREAM_FROSTING_BOTTLE = bottle("ube_cream_frosting_bottle", 5, 0.7f, tips("tooltip.compat.ube"));
    public static final DeferredItem<Item> UBE_CREAM_FROSTING_PIPING_BAG = pipingBag("ube_cream_frosting_piping_bag", "tooltip.compat.ube", "tooltip.createfood.ube_cream_frosting_ingredient");
    public static final DeferredItem<Item> UBE_CREAM_UBE_CAKE_SLICE = food("ube_cream_ube_cake_slice", 3, 0.3f, tips("tooltip.compat.ube", "ube_cream_frosting_ingredient"));
    public static final DeferredItem<Item> UBE_SUGAR_DOUGH = plain("ube_sugar_dough", "tooltip.compat.ube");
    public static final DeferredItem<Item> UNBREADED_CHICKEN_PATTY = plain("unbreaded_chicken_patty");
    public static final DeferredItem<Item> VEGETABLE_SANDWICH_BEETROOT_LETTUCE = consumable("vegetable_sandwich_beetroot_lettuce", 9, 0.5f, tips(null, "beetroot_ingredient", "lettuce_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> VEGETABLE_SANDWICH_LETTUCE_TOMATO = consumable("vegetable_sandwich_lettuce_tomato", 9, 0.7f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffects.NOURISHMENT, 3600));
    public static final DeferredItem<Item> VINEGAR_BOTTLE = ingredientBottle("vinegar_bottle");
    public static final DeferredItem<Item> WAFFLE_CONE = fastFood("waffle_cone", 2, 1.0f);
    public static final DeferredItem<Item> WHEAT_DOUGH_SMALL = plain("wheat_dough_small");
    public static final DeferredItem<Item> WHITE_CHOCOLATE_APPLE = consumable("white_chocolate_apple", 7, 0.9f, tips(null, "white_chocolate_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_BERRIES = consumable("white_chocolate_berries", 6, 0.8f, tips(null, "white_chocolate_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_BOTTLE = bottle("white_chocolate_bottle", 6, 0.5f);
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIPS = plain("white_chocolate_chips");
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = food("white_chocolate_chip_chocolate_cookie", 3, 0.5f, tips(null, "white_chocolate_chips_ingredient"));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("white_chocolate_chip_chocolate_milkshake_bottle", 6, 1.2f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1800));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_COOKIE = food("white_chocolate_chip_cookie", 2, 0.6f, tips(null, "white_chocolate_chips_ingredient"));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_ICE_CREAM_CONE = consumableFast("white_chocolate_chip_ice_cream_cone", 2, 1.2f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffects.NOURISHMENT, 1200), fx(ModEffects.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = bottle("white_chocolate_chip_milkshake_bottle", 5, 1.4f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffects.COMFORT, 1800));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_MINI_WAFFLE = consumableFast("white_chocolate_chip_mini_waffle", 3, 1.7f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffects.NOURISHMENT, 1200), fx(MobEffects.LUCK, 600));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_MUFFIN = consumableFast("white_chocolate_chip_muffin", 4, 0.9f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffects.NOURISHMENT, 1200), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHOCOLATE_PASTRY = consumable("white_chocolate_chocolate_pastry", 2, 1.2f, tips(null, "white_chocolate_ingredient"), fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_FUDGE = consumable("white_chocolate_fudge", 2, 0.7f, fx(ModEffects.COMFORT, 3600));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_MARSHMALLOW_STICK = stickConsumable("white_chocolate_marshmallow_stick", 3, 0.4f, false, tips(null, "white_chocolate_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_PASTRY = consumable("white_chocolate_pastry", 1, 1.2f, tips(null, "white_chocolate_ingredient"), fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_TOAST = consumable("white_chocolate_toast", 3, 0.7f, tips(null, "white_chocolate_ingredient"), fx(ModEffects.COMFORT, 600));
    public static final DeferredItem<Item> YELLOW_GELATIN_DESSERT_SLICE = consumableFast("yellow_gelatin_dessert_slice", 5, 0.3f, fx(ModEffects.COMFORT, 1200));
    public static final DeferredItem<Item> YOGURT_BOTTLE = bottle("yogurt_bottle", 2, 0.9f);

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Items");
        ITEMS.register(eventBus);
    }
}