package dev.averageanime.fabric.item;

import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.item.type.EffectDrink;
import dev.averageanime.fabric.item.type.EffectFood;
import dev.averageanime.item.ModEffectCategories;
import dev.averageanime.item.effect.FoodEffect;
import dev.averageanime.util.Tip;
import dev.averageanime.util.Tooltips;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static dev.averageanime.fabric.CreateFood.MOD_ID;
import static dev.averageanime.fabric.item.ModTooltips.addTooltip;

@SuppressWarnings({"unused"})
public class ModItems {

    record Fx(FoodEffect foodEffect, Supplier<Optional<Holder<MobEffect>>> effect, int duration, int amplifier, String categoryOrEffectId) {}

    private static Fx fx(FoodEffect effect, int duration) {
        return new Fx(effect, effect::get, duration, 0, effect.getCategoryName());
    }

    private static Fx fx(FoodEffect effect, int duration, int amplifier) {
        return new Fx(effect, effect::get, duration, amplifier, effect.getCategoryName());
    }

    private static Fx fx(Holder<MobEffect> effect, int duration) {
        String id = effect.unwrapKey().map(k -> k.location().toString()).orElse("unknown");
        return new Fx(null, () -> Optional.of(effect), duration, 0, id);
    }

    private static Fx fx(Holder<MobEffect> effect, int duration, int amplifier) {
        String id = effect.unwrapKey().map(k -> k.location().toString()).orElse("unknown");
        return new Fx(null, () -> Optional.of(effect), duration, amplifier, id);
    }

    private static Tip tips(String compat, String... shortKeys) {
        return Tooltips.tips(compat, shortKeys);
    }

    private static void splitArgs(Object[] args, Tip[] outTip, List<Fx> outFx) {
        for (Object a : args) {
            if (a instanceof Tip t) outTip[0] = t;
            else if (a instanceof Fx f) outFx.add(f);
        }
    }

    private static Set<String> buildExistingIds(List<Fx> fx) {
        return fx.stream()
                .map(Fx::categoryOrEffectId)
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }

    private static List<EffectFood.DeferredFx> buildDeferredEffects(List<Fx> fxList) {
        return fxList.stream()
                .filter(f -> f != null && f.foodEffect() != null)
                .map(f -> new EffectFood.DeferredFx(f.categoryOrEffectId(), f.effect(), f.duration(), f.amplifier()))
                .toList();
    }

    private static void doTip(List<Component> l, Tip tip) {
        if (tip == null) return;
        String[] fullKeys = new String[tip.keys().length];
        for (int i = 0; i < tip.keys().length; i++) {
            String k = tip.keys()[i];
            fullKeys[i] = k.startsWith("tooltip.") ? k : Tooltips.TIP_PREFIX + k;
        }
        addTooltip(l, tip.compat(), fullKeys);
    }

    private static Item.Properties foodProps(int nut, float sat, boolean fast, Item converts, List<Fx> fxList) {
        var b = new FoodProperties.Builder().nutrition(nut).saturationModifier(sat);
        if (fast) b.fast();
        if (converts != null) b.usingConvertsTo(converts);
        for (Fx f : fxList) {
            if (f == null || f.foodEffect() != null) continue; // mod-dependent effects are deferred
            Optional<Holder<MobEffect>> eff = f.effect().get();
            if (eff.isEmpty()) continue;
            b.effect(new MobEffectInstance(eff.get(), f.duration(), f.amplifier()), 1.0f);
        }
        return new Item.Properties().food(b.build());
    }

    private static Item reg(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, id), item);
    }

    private static Item plain(String id) {
        return reg(id, new Item(new Item.Properties()));
    }

    private static Item plain(String id, String compat, String... keys) {
        final Tip tip = new Tip(compat != null ? Tooltips.COMPAT_PREFIX + compat : null, keys);
        return reg(id, new Item(new Item.Properties()) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c, @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip);
            }
        });
    }

    private static Item plainCr(String id, Supplier<Item> remainder) {
        return reg(id, new Item(new Item.Properties().craftRemainder(remainder.get())));
    }

    private static Item plainCr(String id, Supplier<Item> remainder, String compat, String... keys) {
        final Tip tip = new Tip(compat != null ? Tooltips.COMPAT_PREFIX + compat : null, keys);
        return reg(id, new Item(new Item.Properties().craftRemainder(remainder.get())) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c, @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip);
            }
        });
    }

    private static Item ingredientBottle(String id) {
        return reg(id, new EffectDrink(
                new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    }

    private static Item ingredientBowl(String id) {
        return reg(id, new Item(new Item.Properties().stacksTo(16).craftRemainder(Items.BOWL)));
    }

    private static Item pipingBag(String id) {
        return reg(id, new Item(new Item.Properties().stacksTo(2).craftRemainder(PIPING_BAG)));
    }

    private static Item pipingBag(String id, String compat, String... keys) {
        final Tip tip = new Tip(compat != null ? Tooltips.COMPAT_PREFIX + compat : null, keys);
        return reg(id, new Item(new Item.Properties().stacksTo(2).craftRemainder(PIPING_BAG)) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c, @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip);
            }
        });
    }

    private static Item food(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, false, null, fx);
        final Tip t = tip[0];
        final var existingIds = buildExistingIds(fx);
        final var deferred = buildDeferredEffects(fx);
        if (t == null) return reg(id, new EffectFood(fp, existingIds, deferred));
        return reg(id, new EffectFood(fp, existingIds, deferred) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static Item fastFood(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, true, null, fx);
        final Tip t = tip[0];
        final var existingIds = buildExistingIds(fx);
        final var deferred = buildDeferredEffects(fx);
        if (t == null) return reg(id, new EffectFood(fp, existingIds, deferred));
        return reg(id, new EffectFood(fp, existingIds, deferred) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static Item bowlFood(String id, int nut, float sat, Object... args) {
        return bowlFoodImpl(id, nut, sat, false, args);
    }

    private static Item bowlFoodCr(String id, int nut, float sat, Object... args) {
        return bowlFoodImpl(id, nut, sat, true, args);
    }

    private static Item bowlFoodImpl(String id, int nut, float sat, boolean crBowl, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        var fp = foodProps(nut, sat, false, Items.BOWL, fx).stacksTo(16);
        if (crBowl) fp = fp.craftRemainder(Items.BOWL);
        final Item.Properties finalFp = fp;
        final Tip t = tip[0];
        final var existingIds = buildExistingIds(fx);
        final var deferred = buildDeferredEffects(fx);
        if (t == null) return reg(id, new EffectFood(finalFp, existingIds, deferred));
        return reg(id, new EffectFood(finalFp, existingIds, deferred) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static Item stickFood(String id, int nut, float sat, boolean crStick, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        Item.Properties fp = foodProps(nut, sat, true, Items.STICK, fx);
        if (crStick) fp = fp.craftRemainder(Items.STICK);
        final Item.Properties finalFp = fp;
        final Tip t = tip[0];
        final var existingIds = buildExistingIds(fx);
        final var deferred = buildDeferredEffects(fx);
        if (t == null) return reg(id, new EffectFood(finalFp, existingIds, deferred));
        return reg(id, new EffectFood(finalFp, existingIds, deferred) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    private static Item bottle(String id, int nut, float sat, Object... args) {
        Tip[] tip = {null}; var fx = new ArrayList<Fx>(); splitArgs(args, tip, fx);
        final var fp = foodProps(nut, sat, false, Items.GLASS_BOTTLE, fx)
                .stacksTo(16).craftRemainder(Items.GLASS_BOTTLE);
        final Tip t = tip[0];
        final var existingIds = buildExistingIds(fx);
        final var deferred = buildDeferredEffects(fx);
        if (t == null) return reg(id, new EffectDrink(fp, existingIds, deferred));
        return reg(id, new EffectDrink(fp, existingIds, deferred) {
            @Override public void appendHoverText(ItemStack s, TooltipContext c, List<Component> l, TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, t);
            }
        });
    }

    public static final Item PIPING_BAG = plain("piping_bag");
    public static final Item CLOTH_FILTER = plain("cloth_filter");
    public static final Item CLOTH_FILTER_PRESSED_COCOA = plainCr("cloth_filter_pressed_cocoa", () -> CLOTH_FILTER, null, "pressed_cocoa_ingredient");
    public static final Item CLOTH_FILTER_EGG_YOLK = plainCr("cloth_filter_egg_yolk", () -> CLOTH_FILTER, null, "egg_yolk_ingredient");
    public static final Item CLOTH_FILTER_EGG = plainCr("cloth_filter_egg", () -> CLOTH_FILTER_EGG_YOLK, null, "egg_ingredient");
    public static final Item CLOTH_FILTER_CACAO_MASS = plainCr("cloth_filter_cacao_mass", () -> CLOTH_FILTER_PRESSED_COCOA, null, "cacao_mass_ingredient");
    public static final Item EGGSHELL = plain("eggshell");

    public static final Item APPLE_CHEESECAKE_SLICE = food("apple_cheesecake_slice", 3, 0.1f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item APPLE_CREAM_CAKE_SLICE = food("apple_cream_cake_slice", 2, 0.3f, tips(null, "apple_cream_frosting_ingredient"));
    public static final Item APPLE_CREAM_CHOCOLATE = fastFood("apple_cream_chocolate", 6, 0.5f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item APPLE_CREAM_CHOCOLATE_CUPCAKE = fastFood("apple_cream_chocolate_cupcake", 4, 1.1f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item APPLE_CREAM_CHOCOLATE_DONUT = food("apple_cream_chocolate_donut", 4, 0.8f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item APPLE_CREAM_CHOCOLATE_PASTRY = food("apple_cream_chocolate_pastry", 3, 1.0f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item APPLE_CREAM_CHOCOLATE_SWEET_ROLL = food("apple_cream_chocolate_sweet_roll", 5, 0.6f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item APPLE_CREAM_CUPCAKE = fastFood("apple_cream_cupcake", 3, 1.0f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item APPLE_CREAM_DARK_CHOCOLATE = fastFood("apple_cream_dark_chocolate", 5, 0.6f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item APPLE_CREAM_DONUT = food("apple_cream_donut", 3, 0.8f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item APPLE_CREAM_FROSTING_BOTTLE = bottle("apple_cream_frosting_bottle", 3, 0.7f);
    public static final Item APPLE_CREAM_FROSTING_PIPING_BAG = pipingBag("apple_cream_frosting_piping_bag", null, "apple_cream_frosting_ingredient");
    public static final Item APPLE_CREAM_MINI_WAFFLE = fastFood("apple_cream_mini_waffle", 3, 1.6f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final Item APPLE_CREAM_PASTRY = food("apple_cream_pastry", 2, 1.0f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item APPLE_CREAM_SWEET_ROLL = food("apple_cream_sweet_roll", 4, 0.7f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item APPLE_CREAM_WHITE_CHOCOLATE = food("apple_cream_white_chocolate", 7, 0.4f, tips(null, "apple_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item APPLE_CUSTARD_BOTTLE = bottle("apple_custard_bottle", 6, 0.9f);
    public static final Item APPLE_ICE_CREAM_BOWL = bowlFood("apple_ice_cream_bowl", 2, 1.1f);
    public static final Item APPLE_ICE_CREAM_CONE = fastFood("apple_ice_cream_cone", 2, 0.6f, fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item APPLE_ICE_CREAM_SANDWICH = food("apple_ice_cream_sandwich", 6, 0.6f, tips(null, "apple_ice_cream_ingredient"));
    public static final Item APPLE_ICE_CREAM_STICK = stickFood("apple_ice_cream_stick", 1, 0.6f, true);
    public static final Item APPLE_JAM_BOTTLE = bottle("apple_jam_bottle", 3, 1.6f);
    public static final Item APPLE_JAM_SANDWICH = fastFood("apple_jam_sandwich", 7, 0.7f, tips(null, "apple_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item APPLE_JUICE_BOTTLE = bottle("apple_juice_bottle", 2, 1.5f);
    public static final Item APPLE_MILKSHAKE_BOTTLE = bottle("apple_milkshake_bottle", 5, 0.9f, fx(ModEffectCategories.COMFORT, 600));
    public static final Item APPLE_POPSICLE = stickFood("apple_popsicle", 3, 0.7f, false);
    public static final Item APPLE_SLICE = fastFood("apple_slice", 2, 0.4f, fx(ModEffectCategories.ANIMAL_CHARM, 300));
    public static final Item BACON_BITS = food("bacon_bits", 1, 0.4f);
    public static final Item BACON_CALZONE = fastFood("bacon_calzone", 6, 0.4f, tips(null, "cheese_ingredient", "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item BACON_PIZZA_SLICE = food("bacon_pizza_slice", 5, 0.6f, tips(null, "bacon_ingredient"));
    public static final Item BACON_SANDWICH = food("bacon_sandwich", 7, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item BACON_SANDWICH_LETTUCE = food("bacon_sandwich_lettuce", 9, 0.5f, tips(null, "bacon_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item BAKED_POTATO_BUTTER = food("baked_potato_butter", 5, 0.8f, tips(null, "butter_ingredient"), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item BAKED_POTATO_BUTTER_BACON = food("baked_potato_butter_bacon", 5, 0.9f, tips(null, "butter_ingredient", "bacon_ingredient"), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item BAKED_POTATO_BUTTER_CHEESE = food("baked_potato_butter_cheese", 5, 0.9f, tips(null, "butter_ingredient", "cheese_ingredient"), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item BAKED_POTATO_BUTTER_FISH = food("baked_potato_butter_fish", 5, 0.9f, tips(null, "butter_ingredient", "fish_ingredient"), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item BAKED_POTATO_BUTTER_FRIED_EGG = food("baked_potato_butter_fried_egg", 5, 0.7f, tips(null, "butter_ingredient", "fried_egg_ingredient"), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item BAKED_POTATO_BUTTER_MUSHROOM = food("baked_potato_butter_mushroom", 5, 1.1f, tips(null, "butter_ingredient", "mushroom_ingredient"), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item BAKED_POTATO_BUTTER_SOUR_CREAM = food("baked_potato_butter_sour_cream", 5, 0.8f, tips(null, "butter_ingredient", "sour_cream_ingredient"), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item BAR_OF_DARK_CHOCOLATE = food("bar_of_dark_chocolate", 7, 0.2f);
    public static final Item BAR_OF_WHITE_CHOCOLATE = food("bar_of_white_chocolate", 5, 0.4f);
    public static final Item BEEF_BUN = food("beef_bun", 6, 0.6f);
    public static final Item BEEF_BUN_BACON = food("beef_bun_bacon", 8, 0.6f, tips(null, "bacon_ingredient"));
    public static final Item BEEF_BUN_BACON_LETTUCE = food("beef_bun_bacon_lettuce", 9, 0.7f, tips(null, "bacon_ingredient", "lettuce_ingredient"));
    public static final Item BEEF_BUN_BACON_LETTUCE_TOMATO = food("beef_bun_bacon_lettuce_tomato", 9, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item BEEF_BUN_CHEESE = food("beef_bun_cheese", 8, 0.7f, tips(null, "cheese_ingredient"));
    public static final Item BEEF_BUN_CHEESE_BACON = food("beef_bun_cheese_bacon", 9, 0.7f, tips(null, "cheese_ingredient", "bacon_ingredient"));
    public static final Item BEEF_BUN_CHEESE_BACON_LETTUCE = food("beef_bun_cheese_bacon_lettuce", 10, 0.8f, tips(null, "cheese_ingredient", "bacon_ingredient", "lettuce_ingredient"));
    public static final Item BEEF_BUN_CHEESE_BACON_LETTUCE_TOMATO = food("beef_bun_cheese_bacon_lettuce_tomato", 10, 0.9f, tips(null, "cheese_ingredient", "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item BEEF_BUN_CHEESE_LETTUCE = food("beef_bun_cheese_lettuce", 9, 0.8f, tips(null, "cheese_ingredient", "lettuce_ingredient"));
    public static final Item BEEF_BUN_CHEESE_LETTUCE_TOMATO = food("beef_bun_cheese_lettuce_tomato", 9, 0.9f, tips(null, "cheese_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item BEEF_BUN_CHEESE_ONION = food("beef_bun_cheese_onion", 9, 0.9f, tips(null, "cheese_ingredient", "onion_ingredient"));
    public static final Item BEEF_BUN_CHEESE_ONION_BACON = food("beef_bun_cheese_onion_bacon", 10, 0.9f, tips(null, "cheese_ingredient", "onion_ingredient", "bacon_ingredient"));
    public static final Item BEEF_BUN_CHEESE_ONION_BACON_LETTUCE = food("beef_bun_cheese_onion_bacon_lettuce", 12, 1.0f, tips(null, "cheese_ingredient", "onion_ingredient", "bacon_ingredient", "lettuce_ingredient"));
    public static final Item BEEF_BUN_CHEESE_ONION_LETTUCE = food("beef_bun_cheese_onion_lettuce", 10, 1.0f, tips(null, "cheese_ingredient", "onion_ingredient", "lettuce_ingredient"));
    public static final Item BEEF_BUN_CHEESE_ONION_LETTUCE_TOMATO = food("beef_bun_cheese_onion_lettuce_tomato", 10, 1.1f, tips(null, "cheese_ingredient", "onion_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item BEEF_BUN_CHEESE_TOMATO = food("beef_bun_cheese_tomato", 9, 0.8f, tips(null, "cheese_ingredient", "tomato_ingredient"));
    public static final Item BEEF_BUN_CRIMSON_FUNGUS = food("beef_bun_crimson_fungus", 8, 0.5f, tips(null, "crimson_fungus_ingredient"));
    public static final Item BEEF_BUN_LETTUCE = food("beef_bun_lettuce", 7, 0.7f, tips(null, "lettuce_ingredient"));
    public static final Item BEEF_BUN_LETTUCE_TOMATO = food("beef_bun_lettuce_tomato", 7, 0.8f, tips(null, "lettuce_ingredient", "tomato_ingredient"));
    public static final Item BEEF_BUN_ONION = food("beef_bun_onion", 6, 0.8f, tips(null, "onion_ingredient"));
    public static final Item BEEF_BUN_ONION_BACON = food("beef_bun_onion_bacon", 9, 0.8f, tips(null, "onion_ingredient", "bacon_ingredient"));
    public static final Item BEEF_BUN_ONION_BACON_LETTUCE = food("beef_bun_onion_bacon_lettuce", 10, 0.9f, tips(null, "onion_ingredient", "bacon_ingredient", "lettuce_ingredient"));
    public static final Item BEEF_BUN_ONION_LETTUCE = food("beef_bun_onion_lettuce", 8, 0.9f, tips(null, "onion_ingredient", "lettuce_ingredient"));
    public static final Item BEEF_BUN_ONION_LETTUCE_TOMATO = food("beef_bun_onion_lettuce_tomato", 8, 1.0f, tips(null, "onion_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item BEEF_BUN_PEANUT_BUTTER = food("beef_bun_peanut_butter", 6, 0.9f, tips("peanut_butter", "peanut_butter_ingredient"));
    public static final Item BEEF_BUN_PEANUT_BUTTER_BACON = food("beef_bun_peanut_butter_bacon", 8, 0.9f, tips("peanut_butter", "peanut_butter_ingredient", "bacon_ingredient"));
    public static final Item BEEF_BUN_TOMATO = food("beef_bun_tomato", 7, 0.7f, tips(null, "tomato_ingredient"));
    public static final Item BEEF_BUN_WARPED_FUNGUS = food("beef_bun_warped_fungus", 8, 0.6f, tips(null, "warped_fungus_ingredient"));
    public static final Item BEEF_BURRITO_RICE = food("beef_burrito_rice", 14, 0.8f, tips(null, "beef_ingredient", "rice_ingredient"), fx(ModEffectCategories.SUSTENANCE, 3600));
    public static final Item BEEF_CALZONE = fastFood("beef_calzone", 6, 0.9f, tips(null, "cheese_ingredient", "beef_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item BEEF_MEATBALL = food("beef_meatball", 4, 0.6f);
    public static final Item BEEF_MEATBALL_SANDWICH = food("beef_meatball_sandwich", 10, 0.4f, tips(null, "beef_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item BEEF_MEATBALL_STICK_1 = stickFood("beef_meatball_stick_1", 5, 0.6f, false, fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item BEEF_MEATBALL_STICK_2 = stickFood("beef_meatball_stick_2", 6, 0.6f, false, fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item BEEF_MEATBALL_STICK_3 = stickFood("beef_meatball_stick_3", 7, 0.7f, false, fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item BEEF_TACO = food("beef_taco", 11, 0.6f, tips(null, "beef_ingredient"));
    public static final Item BEEF_TACO_LETTUCE = food("beef_taco_lettuce", 12, 0.7f, tips(null, "beef_ingredient", "lettuce_ingredient"));
    public static final Item BEEF_TACO_LETTUCE_TACO_SAUCE = food("beef_taco_lettuce_taco_sauce", 13, 0.8f, tips(null, "beef_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item BEEF_WRAP_LETTUCE_BEETROOT = food("beef_wrap_lettuce_beetroot", 12, 0.7f, tips(null, "beef_ingredient", "lettuce_ingredient", "beetroot_ingredient"));
    public static final Item BERRY_CREAM_CAKE_SLICE = food("berry_cream_cake_slice", 2, 0.3f, tips(null, "berry_cream_frosting_ingredient"));
    public static final Item BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT = food("berry_cream_cake_slice_chorus_fruit", 2, 0.3f, tips(null, "berry_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final Item BERRY_CREAM_CAKE_SLICE_GLOW_BERRY = food("berry_cream_cake_slice_glow_berry", 2, 0.2f, tips(null, "berry_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final Item BERRY_CREAM_CAKE_SLICE_SWEET_BERRY = food("berry_cream_cake_slice_sweet_berry", 2, 0.2f, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"));
    public static final Item BERRY_CREAM_CHOCOLATE = fastFood("berry_cream_chocolate", 7, 0.4f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item BERRY_CREAM_CHOCOLATE_CUPCAKE = fastFood("berry_cream_chocolate_cupcake", 4, 1.1f, tips(null, "berry_cream_frosting_ingredient"), fx(MobEffects.DIG_SPEED, 1200));
    public static final Item BERRY_CREAM_CHOCOLATE_DONUT = food("berry_cream_chocolate_donut", 4, 0.7f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item BERRY_CREAM_CHOCOLATE_PASTRY = food("berry_cream_chocolate_pastry", 3, 1.0f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item BERRY_CREAM_CHOCOLATE_SWEET_ROLL = food("berry_cream_chocolate_sweet_roll", 6, 0.7f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BERRY_CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY = food("berry_cream_chocolate_sweet_roll_sweet_berry", 6, 0.7f, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.DIG_SPEED, 1200));
    public static final Item BERRY_CREAM_CUPCAKE = fastFood("berry_cream_cupcake", 3, 1.0f, tips(null, "berry_cream_frosting_ingredient"), fx(MobEffects.DIG_SPEED, 600));
    public static final Item BERRY_CREAM_DARK_CHOCOLATE = fastFood("berry_cream_dark_chocolate", 8, 0.3f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item BERRY_CREAM_DONUT = food("berry_cream_donut", 3, 0.7f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BERRY_CREAM_FROSTING_BOTTLE = bottle("berry_cream_frosting_bottle", 4, 0.4f);
    public static final Item BERRY_CREAM_FROSTING_PIPING_BAG = pipingBag("berry_cream_frosting_piping_bag", null, "berry_cream_frosting_ingredient");
    public static final Item BERRY_CREAM_MINI_WAFFLE = fastFood("berry_cream_mini_waffle", 3, 1.9f, tips(null, "berry_cream_frosting_ingredient"), fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final Item BERRY_CREAM_MINI_WAFFLE_SWEET_BERRY = fastFood("berry_cream_mini_waffle_sweet_berry", 3, 1.9f, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"), fx(MobEffects.DIG_SPEED, 1200), fx(MobEffects.LUCK, 600));
    public static final Item BERRY_CREAM_PASTRY = food("berry_cream_pastry", 2, 1.0f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BERRY_CREAM_SWEET_ROLL = food("berry_cream_sweet_roll", 5, 0.6f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item BERRY_CREAM_SWEET_ROLL_SWEET_BERRY = food("berry_cream_sweet_roll_sweet_berry", 5, 0.6f, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item BERRY_CREAM_WHITE_CHOCOLATE = fastFood("berry_cream_white_chocolate", 6, 0.5f, tips(null, "berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item BERRY_CUSTARD_BOTTLE = bottle("berry_custard_bottle", 8, 0.9f);
    public static final Item BERRY_ICE_CREAM_BOWL = bowlFood("berry_ice_cream_bowl", 3, 1.0f);
    public static final Item BERRY_ICE_CREAM_CONE = fastFood("berry_ice_cream_cone", 2, 0.9f, fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item BERRY_ICE_CREAM_SANDWICH = food("berry_ice_cream_sandwich", 6, 0.6f, tips(null, "berry_ice_cream_ingredient"));
    public static final Item BERRY_ICE_CREAM_STICK = stickFood("berry_ice_cream_stick", 1, 0.9f, true);
    public static final Item BERRY_JAM_BOTTLE = bottle("berry_jam_bottle", 4, 1.2f);
    public static final Item BERRY_JAM_SANDWICH = food("berry_jam_sandwich", 9, 0.4f, tips(null, "berry_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item BERRY_JUICE_BOTTLE = bottle("berry_juice_bottle", 2, 1.6f);
    public static final Item BERRY_MILKSHAKE_BOTTLE = bottle("berry_milkshake_bottle", 5, 1.0f, fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item BERRY_PIE_SLICE = food("berry_pie_slice", 3, 0.1f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BERRY_POPSICLE = stickFood("berry_popsicle", 4, 0.5f, false);
    public static final Item BISCUIT = fastFood("biscuit", 9, 0.7f);
    public static final Item BLACKSTRAP_MOLASSES_BOTTLE = bottle("blackstrap_molasses_bottle", 1, 3.0f);
    public static final Item BLACK_GELATIN_DESSERT_SLICE = food("black_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BLUE_GELATIN_DESSERT_SLICE = food("blue_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BOILED_EGG = plainCr("boiled_egg", () -> EGGSHELL);
    public static final Item BOILED_EGG_PEELED = fastFood("boiled_egg_peeled", 6, 0.1f);
    public static final Item BOILED_EGG_PEELED_SALT = fastFood("boiled_egg_peeled_salt", 6, 0.2f, tips(null, "salt_ingredient"), fx(MobEffects.LUCK, 300));
    public static final Item BREAD_CARROT = food("bread_carrot", 7, 0.4f, tips(null, "carrot_ingredient"));
    public static final Item BREAD_CRUMBS = plain("bread_crumbs");
    public static final Item BREAD_FRIED_EGG = food("bread_fried_egg", 6, 0.6f, tips(null, "fried_egg_ingredient"));
    public static final Item BREAD_LETTUCE = food("bread_lettuce", 7, 0.3f, tips(null, "lettuce_ingredient"));
    public static final Item BREAD_LETTUCE_CARROT = food("bread_lettuce_carrot", 8, 0.5f, tips(null, "lettuce_ingredient", "carrot_ingredient"));
    public static final Item BREAD_PUDDING_BOWL = bowlFoodCr("bread_pudding_bowl", 7, 0.9f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BREAD_SLICE = fastFood("bread_slice", 2, 0.2f);
    public static final Item BREAD_SLICE_APPLE_JAM = food("bread_slice_apple_jam", 5, 0.6f, tips(null, "apple_jam_ingredient"));
    public static final Item BREAD_SLICE_BACON = food("bread_slice_bacon", 5, 0.6f, tips(null, "bacon_ingredient"));
    public static final Item BREAD_SLICE_BACON_LETTUCE = food("bread_slice_bacon_lettuce", 6, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient"));
    public static final Item BREAD_SLICE_BACON_LETTUCE_TOMATO = food("bread_slice_bacon_lettuce_tomato", 9, 0.5f, tips(null, "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item BREAD_SLICE_BEETROOT = food("bread_slice_beetroot", 7, 0.3f, tips(null, "beetroot_ingredient"));
    public static final Item BREAD_SLICE_BEETROOT_LETTUCE = food("bread_slice_beetroot_lettuce", 7, 0.5f, tips(null, "beetroot_ingredient", "lettuce_ingredient"));
    public static final Item BREAD_SLICE_BERRY_JAM = food("bread_slice_berry_jam", 7, 0.4f, tips(null, "berry_jam_ingredient"));
    public static final Item BREAD_SLICE_CHEESE = food("bread_slice_cheese", 5, 0.3f, tips(null, "cheese_ingredient"));
    public static final Item BREAD_SLICE_CHORUS_FRUIT_JAM = food("bread_slice_chorus_fruit_jam", 8, 0.5f, tips(null, "chorus_fruit_jam_ingredient"));
    public static final Item BREAD_SLICE_GLOW_BERRY_JAM = food("bread_slice_glow_berry_jam", 7, 0.4f, tips(null, "glow_berry_jam_ingredient"));
    public static final Item BREAD_SLICE_HONEY = food("bread_slice_honey", 7, 0.4f, tips(null, "honey_ingredient"));
    public static final Item BREAD_SLICE_LETTUCE = food("bread_slice_lettuce", 7, 0.3f, tips(null, "lettuce_ingredient"));
    public static final Item BREAD_SLICE_LETTUCE_TOMATO = food("bread_slice_lettuce_tomato", 7, 0.4f, tips(null, "lettuce_ingredient", "tomato_ingredient"));
    public static final Item BREAD_SLICE_MELON_JAM = food("bread_slice_melon_jam", 8, 0.5f, tips(null, "melon_jam_ingredient"));
    public static final Item BREAD_SLICE_MUTTON = food("bread_slice_mutton", 5, 0.6f, tips(null, "mutton_ingredient"));
    public static final Item BREAD_SLICE_MUTTON_BEETROOT = food("bread_slice_mutton_beetroot", 5, 0.8f, tips(null, "mutton_ingredient", "beetroot_ingredient"));
    public static final Item BREAD_SLICE_PEANUT_BUTTER = food("bread_slice_peanut_butter", 7, 0.4f, tips("peanut_butter", "peanut_butter_ingredient"));
    public static final Item BREAD_SLICE_SCRAMBLED_EGG = food("bread_slice_scrambled_egg", 7, 0.8f, tips(null, "scrambled_egg_ingredient"));
    public static final Item BREAD_SLICE_TOMATO = food("bread_slice_tomato", 7, 0.3f, tips(null, "tomato_ingredient"));
    public static final Item BREAKFAST_BAR = food("breakfast_bar", 2, 4.1f, fx(MobEffects.DIG_SPEED, 600), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BREAKFAST_PLATE = bowlFood("breakfast_plate", 8, 1.6f, tips(null, "fried_egg_ingredient", "hash_browns_ingredient", "toast_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000), fx(ModEffectCategories.COMFORT, 3600), fx(MobEffects.DIG_SPEED, 6000), fx(ModEffectCategories.FEAST, 3600));
    public static final Item BROWN_GELATIN_DESSERT_SLICE = food("brown_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BROWN_SUGAR = plain("brown_sugar");
    public static final Item BUN = fastFood("bun", 2, 0.5f);
    public static final Item BUTTER = plain("butter");
    public static final Item BUTTERED_TOAST = food("buttered_toast", 2, 1.2f, tips(null, "butter_ingredient"));
    public static final Item BUTTERSCOTCH = fastFood("butterscotch", 1, 1.0f);
    public static final Item BUTTERSCOTCH_APPLE = food("butterscotch_apple", 8, 0.8f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item BUTTERSCOTCH_APPLE_SLICE = fastFood("butterscotch_apple_slice", 4, 0.5f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.DAMAGE_BOOST, 600), fx(ModEffectCategories.ANIMAL_CHARM, 1200));
    public static final Item BUTTERSCOTCH_BERRIES = food("butterscotch_berries", 6, 0.9f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BUTTERSCOTCH_CHIPS = plain("butterscotch_chips");
    public static final Item BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE = food("butterscotch_chip_chocolate_cookie", 2, 1.3f, tips(null, "butterscotch_chips_ingredient"));
    public static final Item BUTTERSCOTCH_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("butterscotch_chip_chocolate_milkshake_bottle", 6, 1.5f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item BUTTERSCOTCH_CHIP_COOKIE = food("butterscotch_chip_cookie", 1, 1.2f, tips(null, "butterscotch_chips_ingredient"));
    public static final Item BUTTERSCOTCH_CHIP_ICE_CREAM_CONE = fastFood("butterscotch_chip_ice_cream_cone", 2, 1.2f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item BUTTERSCOTCH_CHIP_MILKSHAKE_BOTTLE = bottle("butterscotch_chip_milkshake_bottle", 5, 1.2f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item BUTTERSCOTCH_CHIP_MINI_WAFFLE = fastFood("butterscotch_chip_mini_waffle", 3, 1.7f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final Item BUTTERSCOTCH_CHIP_MUFFIN = fastFood("butterscotch_chip_muffin", 3, 0.8f, tips(null, "butterscotch_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item BUTTERSCOTCH_CHOCOLATE = food("butterscotch_chocolate", 7, 0.8f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item BUTTERSCOTCH_CHOCOLATE_PASTRY = food("butterscotch_chocolate_pastry", 3, 1.1f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item BUTTERSCOTCH_CHOCOLATE_SWEET_ROLL = food("butterscotch_chocolate_sweet_roll", 7, 0.8f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item BUTTERSCOTCH_DARK_CHOCOLATE = food("butterscotch_dark_chocolate", 8, 0.7f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item BUTTERSCOTCH_FUDGE = food("butterscotch_fudge", 3, 0.8f, fx(ModEffectCategories.COMFORT, 3600), fx(MobEffects.DIG_SPEED, 3600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item BUTTERSCOTCH_MARSHMALLOW_STICK = stickFood("butterscotch_marshmallow_stick", 3, 0.7f, false, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600), fx(ModEffectCategories.WARMTH, 1200), fx(ModEffectCategories.SUGAR_RUSH, 300));
    public static final Item BUTTERSCOTCH_PASTRY = food("butterscotch_pastry", 2, 1.1f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item BUTTERSCOTCH_PRETZEL_STICK = fastFood("butterscotch_pretzel_stick", 3, 0.9f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final Item BUTTERSCOTCH_SWEET_ROLL = food("butterscotch_sweet_roll", 6, 0.7f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item BUTTERSCOTCH_TOAST = food("butterscotch_toast", 3, 0.9f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item BUTTERSCOTCH_WHITE_CHOCOLATE = food("butterscotch_white_chocolate", 6, 0.9f, tips(null, "butterscotch_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item BUTTER_DOUGH = plain("butter_dough");
    public static final Item BUTTER_DOUGH_SMALL = plain("butter_dough_small");
    public static final Item CACAO_BUTTER = plain("cacao_butter");
    public static final Item CACAO_NIBS = plain("cacao_nibs");
    public static final Item CANE_SYRUP_BOTTLE = bowlFood("cane_syrup_bottle", 1, 1.0f, fx(ModEffectCategories.SUGAR_RUSH, 2400));
    public static final Item CARAMEL = fastFood("caramel", 1, 0.8f);
    public static final Item CARAMEL_APPLE = food("caramel_apple", 6, 1.0f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CARAMEL_APPLE_SLICE = fastFood("caramel_apple_slice", 3, 0.6f, fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.DAMAGE_BOOST, 600), fx(ModEffectCategories.ANIMAL_CHARM, 1200));
    public static final Item CARAMEL_BERRIES = food("caramel_berries", 6, 0.9f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CARAMEL_CHIPS = plain("caramel_chips");
    public static final Item CARAMEL_CHIP_CHOCOLATE_COOKIE = food("caramel_chip_chocolate_cookie", 2, 1.1f, tips(null, "caramel_chips_ingredient"));
    public static final Item CARAMEL_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("caramel_chip_chocolate_milkshake_bottle", 6, 1.5f, tips(null, "caramel_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final Item CARAMEL_CHIP_COOKIE = food("caramel_chip_cookie", 2, 0.8f, tips(null, "caramel_chips_ingredient"));
    public static final Item CARAMEL_CHIP_ICE_CREAM_CONE = fastFood("caramel_chip_ice_cream_cone", 2, 1.2f, tips(null, "caramel_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item CARAMEL_CHIP_MILKSHAKE_BOTTLE = bottle("caramel_chip_milkshake_bottle", 5, 1.2f, tips(null, "caramel_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final Item CARAMEL_CHIP_MINI_WAFFLE = fastFood("caramel_chip_mini_waffle", 3, 1.7f, tips(null, "caramel_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.DAMAGE_BOOST, 600), fx(MobEffects.LUCK, 600));
    public static final Item CARAMEL_CHIP_MUFFIN = fastFood("caramel_chip_muffin", 3, 0.9f, tips(null, "caramel_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final Item CARAMEL_CHOCOLATE = food("caramel_chocolate", 7, 0.7f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CARAMEL_CHOCOLATE_PASTRY = food("caramel_chocolate_pastry", 3, 1.0f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CARAMEL_CHOCOLATE_SWEET_ROLL = food("caramel_chocolate_sweet_roll", 7, 0.7f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CARAMEL_DARK_CHOCOLATE = food("caramel_dark_chocolate", 8, 0.6f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CARAMEL_FUDGE = food("caramel_fudge", 5, 0.4f, fx(ModEffectCategories.COMFORT, 3600), fx(MobEffects.DAMAGE_BOOST, 3600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item CARAMEL_MARSHMALLOW_STICK = stickFood("caramel_marshmallow_stick", 3, 0.8f, false, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600), fx(ModEffectCategories.WARMTH, 1200), fx(ModEffectCategories.SUGAR_RUSH, 300));
    public static final Item CARAMEL_PASTRY = food("caramel_pastry", 2, 1.0f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final Item CARAMEL_POPCORN = food("caramel_popcorn", 3, 0.8f, tips("corn", "caramel_ingredient"), fx(MobEffects.LUCK, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final Item CARAMEL_PRETZEL_STICK = fastFood("caramel_pretzel_stick", 3, 0.8f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final Item CARAMEL_SWEET_ROLL = food("caramel_sweet_roll", 6, 0.6f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CARAMEL_TOAST = food("caramel_toast", 3, 0.8f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CARAMEL_WHITE_CHOCOLATE = food("caramel_white_chocolate", 6, 0.8f, tips(null, "caramel_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CHEESEBURGER = food("cheeseburger", 10, 0.7f, fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item CHEESEBURGER_BACON = food("cheeseburger_bacon", 12, 0.7f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item CHEESEBURGER_BACON_LETTUCE = food("cheeseburger_bacon_lettuce", 12, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item CHEESEBURGER_BACON_LETTUCE_TOMATO = food("cheeseburger_bacon_lettuce_tomato", 13, 0.9f, tips(null, "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000));
    public static final Item CHEESEBURGER_LETTUCE = food("cheeseburger_lettuce", 11, 0.8f, tips(null, "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item CHEESEBURGER_LETTUCE_TOMATO = food("cheeseburger_lettuce_tomato", 11, 0.9f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item CHEESEBURGER_ONION = food("cheeseburger_onion", 11, 0.8f, tips(null, "onion_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item CHEESEBURGER_ONION_BACON = food("cheeseburger_onion_bacon", 13, 0.8f, tips(null, "onion_ingredient", "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item CHEESEBURGER_ONION_BACON_LETTUCE = food("cheeseburger_onion_bacon_lettuce", 13, 0.9f, tips(null, "onion_ingredient", "bacon_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000));
    public static final Item CHEESEBURGER_ONION_LETTUCE = food("cheeseburger_onion_lettuce", 11, 0.9f, tips(null, "onion_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item CHEESEBURGER_ONION_LETTUCE_TOMATO = food("cheeseburger_onion_lettuce_tomato", 12, 1.0f, tips(null, "onion_ingredient", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000));
    public static final Item CHEESEBURGER_TOMATO = food("cheeseburger_tomato", 11, 0.8f, tips(null, "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item CHEESECAKE_SLICE = food("cheesecake_slice", 2, 0.3f, fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHEESE_BISCUIT = food("cheese_biscuit", 7, 0.5f, tips(null, "cheese_ingredient"));
    public static final Item CHEESE_BISCUIT_SANDWICH = food("cheese_biscuit_sandwich", 12, 0.5f, tips(null, "cheese_ingredient"));
    public static final Item CHEESE_CALZONE = fastFood("cheese_calzone", 5, 0.4f, tips(null, "cheese_ingredient"));
    public static final Item CHEESE_PIZZA_SLICE = food("cheese_pizza_slice", 5, 0.4f, tips(null, "cheese_ingredient"));
    public static final Item CHEESE_POTATO_DUMPLINGS = food("cheese_potato_dumplings", 7, 0.7f, tips(null, "potato_ingredient", "cheese_ingredient", "onion_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHEESE_PRETZEL_STICK = fastFood("cheese_pretzel_stick", 4, 0.5f, tips(null, "cheese_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item CHEESE_SANDWICH = food("cheese_sandwich", 8, 0.4f, tips(null, "cheese_ingredient"));
    public static final Item CHEESE_SLICE = fastFood("cheese_slice", 2, 0.1f);
    public static final Item CHICKEN_BUN = food("chicken_bun", 9, 0.6f);
    public static final Item CHICKEN_BUN_BACON = food("chicken_bun_bacon", 11, 0.6f, tips(null, "bacon_ingredient"));
    public static final Item CHICKEN_BUN_BACON_LETTUCE = food("chicken_bun_bacon_lettuce", 12, 0.7f, tips(null, "bacon_ingredient", "lettuce_ingredient"));
    public static final Item CHICKEN_BUN_CHEESE = food("chicken_bun_cheese", 11, 0.7f, tips(null, "cheese_ingredient"));
    public static final Item CHICKEN_BUN_CHEESE_BACON = food("chicken_bun_cheese_bacon", 12, 0.7f, tips(null, "cheese_ingredient", "bacon_ingredient"));
    public static final Item CHICKEN_BUN_CHEESE_BACON_LETTUCE = food("chicken_bun_cheese_bacon_lettuce", 13, 0.8f, tips(null, "cheese_ingredient", "bacon_ingredient", "lettuce_ingredient"));
    public static final Item CHICKEN_BUN_CHEESE_LETTUCE = food("chicken_bun_cheese_lettuce", 11, 0.8f, tips(null, "cheese_ingredient", "lettuce_ingredient"));
    public static final Item CHICKEN_BUN_CHEESE_LETTUCE_TOMATO = food("chicken_bun_cheese_lettuce_tomato", 11, 0.9f, tips(null, "cheese_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item CHICKEN_BUN_CHEESE_TOMATO = food("chicken_bun_cheese_tomato", 11, 0.8f, tips(null, "cheese_ingredient", "tomato_ingredient"));
    public static final Item CHICKEN_BUN_LETTUCE = food("chicken_bun_lettuce", 10, 0.7f, tips(null, "lettuce_ingredient"));
    public static final Item CHICKEN_BUN_LETTUCE_TOMATO = food("chicken_bun_lettuce_tomato", 10, 0.8f, tips(null, "lettuce_ingredient", "tomato_ingredient"));
    public static final Item CHICKEN_BUN_TOMATO = food("chicken_bun_tomato", 10, 0.7f, tips(null, "tomato_ingredient"));
    public static final Item CHICKEN_BURGER = food("chicken_burger", 9, 0.7f);
    public static final Item CHICKEN_BURGER_BACON = food("chicken_burger_bacon", 13, 0.5f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHICKEN_BURGER_BACON_LETTUCE = food("chicken_burger_bacon_lettuce", 14, 0.7f, tips(null, "bacon_ingredient", "lettuce_ingredient"));
    public static final Item CHICKEN_BURGER_LETTUCE = food("chicken_burger_lettuce", 12, 0.6f, tips(null, "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHICKEN_BURGER_LETTUCE_TOMATO = food("chicken_burger_lettuce_tomato", 12, 0.7f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHICKEN_BURGER_TOMATO = food("chicken_burger_tomato", 12, 0.6f, tips(null, "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHICKEN_BURRITO_RICE = food("chicken_burrito_rice", 11, 0.7f, tips(null, "chicken_ingredient", "rice_ingredient"), fx(ModEffectCategories.SUSTENANCE, 3600));
    public static final Item CHICKEN_CALZONE = fastFood("chicken_calzone", 6, 0.8f, tips(null, "cheese_ingredient", "chicken_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHICKEN_CHEESEBURGER = food("chicken_cheeseburger", 9, 0.8f, fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item CHICKEN_CHEESEBURGER_BACON = food("chicken_cheeseburger_bacon", 13, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item CHICKEN_CHEESEBURGER_BACON_LETTUCE = food("chicken_cheeseburger_bacon_lettuce", 15, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000));
    public static final Item CHICKEN_CHEESEBURGER_LETTUCE = food("chicken_cheeseburger_lettuce", 12, 0.7f, tips(null, "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item CHICKEN_CHEESEBURGER_LETTUCE_TOMATO = food("chicken_cheeseburger_lettuce_tomato", 12, 0.8f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000));
    public static final Item CHICKEN_CHEESEBURGER_TOMATO = food("chicken_cheeseburger_tomato", 12, 0.7f, tips(null, "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item CHICKEN_NUGGETS = food("chicken_nuggets", 4, 0.9f, fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHICKEN_PATTY = food("chicken_patty", 6, 0.8f);
    public static final Item CHICKEN_TACO = food("chicken_taco", 9, 0.6f, tips(null, "chicken_ingredient"));
    public static final Item CHICKEN_TACO_LETTUCE = food("chicken_taco_lettuce", 10, 0.6f, tips(null, "chicken_ingredient", "lettuce_ingredient"));
    public static final Item CHICKEN_TACO_LETTUCE_TACO_SAUCE = food("chicken_taco_lettuce_taco_sauce", 11, 0.8f, tips(null, "chicken_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item CHICKEN_WRAP_ONION_TOMATO = food("chicken_wrap_onion_tomato", 10, 0.7f, tips(null, "chicken_ingredient", "onion_ingredient", "tomato_ingredient"));
    public static final Item CHOCOLATE_APPLE = food("chocolate_apple", 8, 0.7f, tips(null, "chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CHOCOLATE_APPLE_SLICE = fastFood("chocolate_apple_slice", 4, 0.4f, tips(null, "chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1800), fx(ModEffectCategories.ANIMAL_CHARM, 1200));
    public static final Item CHOCOLATE_BOTTLE = bottle("chocolate_bottle", 7, 0.4f);
    public static final Item CHOCOLATE_CHIPS = plain("chocolate_chips");
    public static final Item CHOCOLATE_CHIP_CHOCOLATE_COOKIE = food("chocolate_chip_chocolate_cookie", 3, 0.5f, tips(null, "chocolate_chips_ingredient"));
    public static final Item CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("chocolate_chip_chocolate_milkshake_bottle", 6, 1.3f, tips(null, "chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_CHIP_ICE_CREAM_CONE = fastFood("chocolate_chip_ice_cream_cone", 2, 1.2f, tips(null, "chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = bottle("chocolate_chip_milkshake_bottle", 5, 1.2f, tips(null, "chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_CHIP_MINI_WAFFLE = fastFood("chocolate_chip_mini_waffle", 3, 1.7f, tips(null, "chocolate_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.LUCK, 600));
    public static final Item CHOCOLATE_CHIP_MUFFIN = fastFood("chocolate_chip_muffin", 3, 0.8f, tips(null, "chocolate_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHOCOLATE_CHOCOLATE_PASTRY = food("chocolate_chocolate_pastry", 3, 1.0f, tips(null, "chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CHOCOLATE_CREAM_CAKE_SLICE = food("chocolate_cream_cake_slice", 2, 0.3f, tips(null, "chocolate_cream_frosting_ingredient"));
    public static final Item CHOCOLATE_CREAM_CAKE_SLICE_BUTTERSCOTCH = food("chocolate_cream_cake_slice_butterscotch", 4, 0.4f, tips(null, "chocolate_cream_frosting_ingredient", "butterscotch_chips_ingredient"), fx(MobEffects.DIG_SPEED, 600));
    public static final Item CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL = food("chocolate_cream_cake_slice_caramel", 2, 0.4f, tips(null, "chocolate_cream_frosting_ingredient", "caramel_ingredient"), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final Item CHOCOLATE_CREAM_CAKE_SLICE_CHOCOLATE = food("chocolate_cream_cake_slice_chocolate", 4, 0.3f, tips(null, "chocolate_cream_frosting_ingredient", "chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHOCOLATE_CREAM_CAKE_SLICE_DARK_CHOCOLATE = food("chocolate_cream_cake_slice_dark_chocolate", 5, 0.2f, tips(null, "chocolate_cream_frosting_ingredient", "dark_chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_CREAM_CAKE_SLICE_TOFFEE = food("chocolate_cream_cake_slice_toffee", 3, 0.4f, tips(null, "chocolate_cream_frosting_ingredient", "toffee_chips_ingredient"), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final Item CHOCOLATE_CREAM_CAKE_SLICE_WHITE_CHOCOLATE = food("chocolate_cream_cake_slice_white_chocolate", 3, 0.5f, tips(null, "chocolate_cream_frosting_ingredient", "white_chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_CREAM_CHOCOLATE_CAKE_SLICE = food("chocolate_cream_chocolate_cake_slice", 2, 0.3f, tips(null, "chocolate_cream_frosting_ingredient"));
    public static final Item CHOCOLATE_CREAM_CHOCOLATE_CUPCAKE = fastFood("chocolate_cream_chocolate_cupcake", 4, 1.3f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 2400));
    public static final Item CHOCOLATE_CREAM_CHOCOLATE_DONUT = food("chocolate_cream_chocolate_donut", 4, 0.7f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CHOCOLATE_CREAM_CHOCOLATE_SWEET_ROLL = food("chocolate_cream_chocolate_sweet_roll", 6, 0.6f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_CREAM_CUPCAKE = fastFood("chocolate_cream_cupcake", 3, 1.2f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_CREAM_DONUT = food("chocolate_cream_donut", 3, 0.7f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_CREAM_FROSTING_BOTTLE = bottle("chocolate_cream_frosting_bottle", 4, 0.3f);
    public static final Item CHOCOLATE_CREAM_FROSTING_PIPING_BAG = pipingBag("chocolate_cream_frosting_piping_bag", null, "chocolate_cream_frosting_ingredient");
    public static final Item CHOCOLATE_CREAM_MINI_WAFFLE = fastFood("chocolate_cream_mini_waffle", 3, 1.7f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(MobEffects.LUCK, 600));
    public static final Item CHOCOLATE_CREAM_SWEET_ROLL = food("chocolate_cream_sweet_roll", 5, 0.5f, tips(null, "chocolate_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHOCOLATE_CUPCAKE_BASE = food("chocolate_cupcake_base", 3, 0.9f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_CUSTARD_BOTTLE = bottle("chocolate_custard_bottle", 7, 1.0f);
    public static final Item CHOCOLATE_DONUT_BASE = food("chocolate_donut_base", 2, 1.2f);
    public static final Item CHOCOLATE_DONUT_HOLE = food("chocolate_donut_hole", 1, 0.8f);
    public static final Item CHOCOLATE_DONUT_HOLE_SUGAR = food("chocolate_donut_hole_sugar", 1, 0.8f, tips(null, "sugar_ingredient"), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.SUGAR_RUSH, 600));
    public static final Item CHOCOLATE_DONUT_SUGAR = food("chocolate_donut_sugar", 4, 0.6f, tips(null, "sugar_ingredient"), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item CHOCOLATE_FUDGE = food("chocolate_fudge", 3, 0.6f, fx(ModEffectCategories.COMFORT, 3600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item CHOCOLATE_GRAHAM_CRACKER = food("chocolate_graham_cracker", 3, 0.8f);
    public static final Item CHOCOLATE_GRAHAM_CRACKER_APPLE_ICE_CREAM = food("chocolate_graham_cracker_apple_ice_cream", 5, 0.8f, tips(null, "apple_ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_BERRY_ICE_CREAM = food("chocolate_graham_cracker_berry_ice_cream", 5, 0.8f, tips(null, "berry_ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_CHOCOLATE_ICE_CREAM = food("chocolate_graham_cracker_chocolate_ice_cream", 5, 1.0f, tips(null, "chocolate_ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_CHORUS_FRUIT_ICE_CREAM = food("chocolate_graham_cracker_chorus_fruit_ice_cream", 6, 1.0f, tips(null, "chorus_fruit_ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_CRUMBS = plain("chocolate_graham_cracker_crumbs");
    public static final Item CHOCOLATE_GRAHAM_CRACKER_GLOW_BERRY_ICE_CREAM = food("chocolate_graham_cracker_glow_berry_ice_cream", 5, 0.8f, tips(null, "berry_ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_ICE_CREAM = food("chocolate_graham_cracker_ice_cream", 5, 0.9f, tips(null, "ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_MELON_ICE_CREAM = food("chocolate_graham_cracker_melon_ice_cream", 5, 0.9f, tips(null, "melon_ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_1 = food("chocolate_graham_cracker_neapolitan_scoop_1", 4, 0.7f, tips(null, "chocolate_ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_2 = food("chocolate_graham_cracker_neapolitan_scoop_2", 5, 0.6f, tips(null, "chocolate_ice_cream_ingredient", "ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_3 = food("chocolate_graham_cracker_neapolitan_scoop_3", 6, 0.7f, tips(null, "chocolate_ice_cream_ingredient", "ice_cream_ingredient", "berry_ice_cream_ingredient"));
    public static final Item CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = food("chocolate_graham_cracker_pie_crust", 7, 0.6f);
    public static final Item CHOCOLATE_ICE_CREAM_BOWL = bowlFood("chocolate_ice_cream_bowl", 3, 1.2f);
    public static final Item CHOCOLATE_ICE_CREAM_CONE = fastFood("chocolate_ice_cream_cone", 2, 1.2f, fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item CHOCOLATE_ICE_CREAM_SANDWICH = food("chocolate_ice_cream_sandwich", 6, 0.8f, tips(null, "chocolate_ice_cream_ingredient"));
    public static final Item CHOCOLATE_ICE_CREAM_STICK = stickFood("chocolate_ice_cream_stick", 1, 1.2f, false);
    public static final Item CHOCOLATE_MARSHMALLOW_STICK = stickFood("chocolate_marshmallow_stick", 4, 0.3f, false, tips(null, "chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.WARMTH, 1200), fx(ModEffectCategories.SUGAR_RUSH, 300));
    public static final Item CHOCOLATE_MILKSHAKE_BOTTLE = bottle("chocolate_milkshake_bottle", 5, 1.2f, fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHOCOLATE_MILK_BOTTLE = bottle("chocolate_milk_bottle", 2, 0.3f, fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHOCOLATE_PASTRY = food("chocolate_pastry", 2, 1.0f, tips(null, "chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_PASTRY_BASE = food("chocolate_pastry_base", 4, 0.8f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_PIE_GRAHAM_CRACKER_SLICE = food("chocolate_pie_graham_cracker_slice", 4, 0.3f, tips(null, "graham_cracker_pie_crust_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHOCOLATE_PRETZEL_STICK = fastFood("chocolate_pretzel_stick", 4, 0.6f, tips(null, "chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHOCOLATE_SUGAR_DOUGH = plain("chocolate_sugar_dough");
    public static final Item CHOCOLATE_SUGAR_DOUGH_SMALL = plain("chocolate_sugar_dough_small");
    public static final Item CHOCOLATE_SWEET_ROLL_BASE = food("chocolate_sweet_roll_base", 5, 0.7f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHOCOLATE_TOAST = food("chocolate_toast", 4, 0.6f, tips(null, "chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHORUS_FRUIT_CHEESECAKE_SLICE = food("chorus_fruit_cheesecake_slice", 4, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHORUS_FRUIT_COOKIE = food("chorus_fruit_cookie", 3, 0.8f, tips(null, "chorus_fruit_ingredient"));
    public static final Item CHORUS_FRUIT_CREAM_CAKE_SLICE = food("chorus_fruit_cream_cake_slice", 2, 0.3f, tips(null, "chorus_fruit_cream_frosting_ingredient"));
    public static final Item CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT = food("chorus_fruit_cream_cake_slice_chorus_fruit", 4, 0.5f, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final Item CHORUS_FRUIT_CREAM_CAKE_SLICE_GLOW_BERRY = food("chorus_fruit_cream_cake_slice_glow_berry", 4, 0.4f, tips(null, "chorus_fruit_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final Item CHORUS_FRUIT_CREAM_CAKE_SLICE_SWEET_BERRY = food("chorus_fruit_cream_cake_slice_sweet_berry", 4, 0.4f, tips(null, "chorus_fruit_cream_frosting_ingredient", "berry_ingredient"));
    public static final Item CHORUS_FRUIT_CREAM_CHOCOLATE = fastFood("chorus_fruit_cream_chocolate", 9, 0.5f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHORUS_FRUIT_CREAM_CHOCOLATE_CUPCAKE = fastFood("chorus_fruit_cream_chocolate_cupcake", 5, 1.2f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(MobEffects.JUMP, 1200), fx(MobEffects.SLOW_FALLING, 1200));
    public static final Item CHORUS_FRUIT_CREAM_CHOCOLATE_DONUT = food("chorus_fruit_cream_chocolate_donut", 4, 0.7f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CHORUS_FRUIT_CREAM_CHOCOLATE_PASTRY = food("chorus_fruit_cream_chocolate_pastry", 4, 1.0f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL = food("chorus_fruit_cream_chocolate_sweet_roll", 8, 0.8f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT = food("chorus_fruit_cream_chocolate_sweet_roll_chorus_fruit", 8, 0.8f, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(ModEffectCategories.COMFORT, 3600), fx(MobEffects.JUMP, 1200), fx(MobEffects.SLOW_FALLING, 1200));
    public static final Item CHORUS_FRUIT_CREAM_CUPCAKE = fastFood("chorus_fruit_cream_cupcake", 4, 1.1f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600));
    public static final Item CHORUS_FRUIT_CREAM_DARK_CHOCOLATE = fastFood("chorus_fruit_cream_dark_chocolate", 10, 0.4f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHORUS_FRUIT_CREAM_DONUT = food("chorus_fruit_cream_donut", 3, 0.7f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHORUS_FRUIT_CREAM_FROSTING_BOTTLE = bottle("chorus_fruit_cream_frosting_bottle", 6, 0.5f);
    public static final Item CHORUS_FRUIT_CREAM_FROSTING_PIPING_BAG = pipingBag("chorus_fruit_cream_frosting_piping_bag", null, "chorus_fruit_cream_frosting_ingredient");
    public static final Item CHORUS_FRUIT_CREAM_MINI_WAFFLE = fastFood("chorus_fruit_cream_mini_waffle", 4, 1.9f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(MobEffects.LUCK, 600));
    public static final Item CHORUS_FRUIT_CREAM_MINI_WAFFLE_CHORUS_FRUIT = fastFood("chorus_fruit_cream_mini_waffle_chorus_fruit", 4, 1.9f, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(MobEffects.JUMP, 1200), fx(MobEffects.SLOW_FALLING, 1200), fx(MobEffects.LUCK, 600));
    public static final Item CHORUS_FRUIT_CREAM_PASTRY = food("chorus_fruit_cream_pastry", 3, 1.0f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHORUS_FRUIT_CREAM_SWEET_ROLL = food("chorus_fruit_cream_sweet_roll", 7, 0.7f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHORUS_FRUIT_CREAM_SWEET_ROLL_CHORUS_FRUIT = food("chorus_fruit_cream_sweet_roll_chorus_fruit", 7, 0.7f, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHORUS_FRUIT_CREAM_WHITE_CHOCOLATE = fastFood("chorus_fruit_cream_white_chocolate", 8, 0.6f, tips(null, "chorus_fruit_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CHORUS_FRUIT_CUSTARD_BOTTLE = bottle("chorus_fruit_custard_bottle", 9, 0.9f);
    public static final Item CHORUS_FRUIT_ICE_CREAM_BOWL = bowlFood("chorus_fruit_ice_cream_bowl", 4, 1.2f);
    public static final Item CHORUS_FRUIT_ICE_CREAM_CONE = fastFood("chorus_fruit_ice_cream_cone", 3, 0.9f, fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item CHORUS_FRUIT_ICE_CREAM_SANDWICH = food("chorus_fruit_ice_cream_sandwich", 7, 0.8f, tips(null, "chorus_fruit_ice_cream_ingredient"));
    public static final Item CHORUS_FRUIT_ICE_CREAM_STICK = stickFood("chorus_fruit_ice_cream_stick", 2, 0.9f, false);
    public static final Item CHORUS_FRUIT_JAM_BOTTLE = bottle("chorus_fruit_jam_bottle", 5, 1.1f);
    public static final Item CHORUS_FRUIT_JAM_SANDWICH = food("chorus_fruit_jam_sandwich", 10, 0.5f, tips(null, "chorus_fruit_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item CHORUS_FRUIT_JUICE_BOTTLE = bottle("chorus_fruit_juice_bottle", 3, 1.6f);
    public static final Item CHORUS_FRUIT_MILKSHAKE_BOTTLE = bottle("chorus_fruit_milkshake_bottle", 6, 1.1f, fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600));
    public static final Item CHORUS_FRUIT_PIE_SLICE = food("chorus_fruit_pie_slice", 4, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CHORUS_FRUIT_POPSICLE = stickFood("chorus_fruit_popsicle", 4, 0.6f, false);
    public static final Item CHORUS_FRUIT_SLICE = fastFood("chorus_fruit_slice", 4, 0.9f);
    public static final Item CINNAMON_SWEET_ROLL_BASE = food("cinnamon_sweet_roll_base", 4, 0.6f, tips("cinnamon"));
    public static final Item COCOA_POWDER = plain("cocoa_powder");
    public static final Item COFFEE_TOFFEE = food("coffee_toffee", 1, 2.0f, tips("coffee"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600), fx(ModEffectCategories.LIGHTNING, 600));
    public static final Item COFFEE_TOFFEE_FUDGE = food("coffee_toffee_fudge", 4, 0.7f, tips("coffee"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600), fx(ModEffectCategories.SUGAR_RUSH, 1200), fx(ModEffectCategories.LIGHTNING, 1200));
    public static final Item CONDENSED_MILK_BOTTLE = ingredientBottle("condensed_milk_bottle");
    public static final Item COOKED_RABBIT_CUTS = food("cooked_rabbit_cuts", 3, 0.6f);
    public static final Item COOKED_TROPICAL_FISH = food("cooked_tropical_fish", 5, 0.8f);
    public static final Item COOKED_TROPICAL_FISH_SLICE = fastFood("cooked_tropical_fish_slice", 5, 0.8f);
    public static final Item COOKIE_CREAM_PIE_SLICE = food("cookie_cream_pie_slice", 5, 0.7f, fx(ModEffectCategories.COMFORT, 3600));
    public static final Item COOKIE_CRUMBS = plain("cookie_crumbs");
    public static final Item CORN_FLOUR = plain("corn_flour", "corn");
    public static final Item CORN_STICK = stickFood("corn_stick", 4, 0.7f, false, tips("corn"));
    public static final Item COTTON_CANDY_STICK = fastFood("cotton_candy_stick", 1, 5.0f, false, fx(ModEffectCategories.COMFORT, 300), fx(MobEffects.LUCK, 1200), fx(ModEffectCategories.SUGAR_RUSH, 2400));
    public static final Item CREAM_CAKE_SLICE = food("cream_cake_slice", 2, 0.3f, tips(null, "cream_frosting_ingredient"));
    public static final Item CREAM_CAKE_SLICE_CHORUS_FRUIT = food("cream_cake_slice_chorus_fruit", 4, 0.4f, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final Item CREAM_CAKE_SLICE_GLOW_BERRY = food("cream_cake_slice_glow_berry", 3, 0.3f, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final Item CREAM_CHEESE = plain("cream_cheese");
    public static final Item CREAM_CHOCOLATE = fastFood("cream_chocolate", 6, 0.4f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CREAM_CHOCOLATE_CAKE_SLICE = food("cream_chocolate_cake_slice", 2, 0.3f, tips(null, "cream_frosting_ingredient"));
    public static final Item CREAM_CHOCOLATE_CUPCAKE = fastFood("cream_chocolate_cupcake", 4, 1.2f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CREAM_CHOCOLATE_DONUT = food("cream_chocolate_donut", 4, 0.6f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item CREAM_CHOCOLATE_PASTRY = food("cream_chocolate_pastry", 3, 0.9f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CREAM_CHOCOLATE_SWEET_ROLL = food("cream_chocolate_sweet_roll", 6, 0.8f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT = food("cream_chocolate_sweet_roll_chorus_fruit", 9, 0.7f, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY = food("cream_chocolate_sweet_roll_glow_berry", 7, 0.6f, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600));
    public static final Item CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY = food("cream_chocolate_sweet_roll_sweet_berry", 6, 0.5f, tips(null, "cream_frosting_ingredient", "berry_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item CREAM_CUPCAKE = fastFood("cream_cupcake", 3, 1.1f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CREAM_DARK_CHOCOLATE = fastFood("cream_dark_chocolate", 7, 0.3f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CREAM_DONUT = food("cream_donut", 3, 0.7f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CREAM_FROSTING_BOTTLE = bottle("cream_frosting_bottle", 3, 0.2f);
    public static final Item CREAM_FROSTING_PIPING_BAG = pipingBag("cream_frosting_piping_bag", null, "cream_frosting_ingredient");
    public static final Item CREAM_MINI_WAFFLE = fastFood("cream_mini_waffle", 3, 1.8f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.LUCK, 600));
    public static final Item CREAM_MINI_WAFFLE_CHORUS_FRUIT = fastFood("cream_mini_waffle_chorus_fruit", 3, 1.9f, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.JUMP, 600), fx(MobEffects.SLOW_FALLING, 600), fx(MobEffects.LUCK, 600));
    public static final Item CREAM_MINI_WAFFLE_GLOW_BERRY = fastFood("cream_mini_waffle_glow_berry", 3, 1.8f, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600), fx(MobEffects.LUCK, 600));
    public static final Item CREAM_MINI_WAFFLE_SWEET_BERRY = fastFood("cream_mini_waffle_sweet_berry", 3, 1.8f, tips(null, "cream_frosting_ingredient", "berry_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.DIG_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final Item CREAM_PASTRY = food("cream_pastry", 2, 0.9f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_SLICE = food("cream_pie_chocolate_graham_cracker_slice", 4, 0.5f, tips(null, "chocolate_graham_cracker_pie_crust_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CREAM_PIE_GRAHAM_CRACKER_SLICE = food("cream_pie_graham_cracker_slice", 3, 0.6f, tips(null, "graham_cracker_pie_crust_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CREAM_SWEET_ROLL_CHORUS_FRUIT = food("cream_sweet_roll_chorus_fruit", 8, 0.8f, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"), fx(MobEffects.JUMP, 300), fx(MobEffects.SLOW_FALLING, 300), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CREAM_SWEET_ROLL_GLOW_BERRY = food("cream_sweet_roll_glow_berry", 6, 0.7f, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"), fx(ModEffectCategories.COMFORT, 300), fx(MobEffects.GLOWING, 300), fx(MobEffects.NIGHT_VISION, 300));
    public static final Item CREAM_SWEET_ROLL_SWEET_BERRY = food("cream_sweet_roll_sweet_berry", 5, 0.6f, tips(null, "cream_frosting_ingredient", "berry_ingredient"), fx(ModEffectCategories.COMFORT, 300), fx(MobEffects.DIG_SPEED, 300));
    public static final Item CREAM_WHITE_CHOCOLATE = fastFood("cream_white_chocolate", 5, 0.5f, tips(null, "cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item CREME_BRULEE_BOTTLE = bottle("creme_brulee_bottle", 10, 1.0f, fx(ModEffectCategories.SUGAR_RUSH, 3600), fx(ModEffectCategories.CHARISMA, 6000));
    public static final Item CUPCAKE_BASE = fastFood("cupcake_base", 2, 0.9f);
    public static final Item CUSTARD_BOTTLE = bottle("custard_bottle", 5, 0.8f);
    public static final Item CUSTARD_SUGAR_BOTTLE = bottle("custard_sugar_bottle", 6, 0.6f, tips(null, "sugar_ingredient"), fx(ModEffectCategories.SUGAR_RUSH, 600));
    public static final Item CYAN_GELATIN_DESSERT_SLICE = fastFood("cyan_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item DARK_CHOCOLATE_APPLE = food("dark_chocolate_apple", 9, 0.8f, tips(null, "dark_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item DARK_CHOCOLATE_APPLE_SLICE = fastFood("dark_chocolate_apple_slice", 5, 0.5f, tips(null, "dark_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1800), fx(ModEffectCategories.ANIMAL_CHARM, 1200));
    public static final Item DARK_CHOCOLATE_BERRIES = food("dark_chocolate_berries", 8, 0.7f, tips(null, "dark_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item DARK_CHOCOLATE_BOTTLE = bottle("dark_chocolate_bottle", 8, 0.3f);
    public static final Item DARK_CHOCOLATE_CHIPS = plain("dark_chocolate_chips");
    public static final Item DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = food("dark_chocolate_chip_chocolate_cookie", 4, 0.4f, tips(null, "dark_chocolate_chips_ingredient"));
    public static final Item DARK_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("dark_chocolate_chip_chocolate_milkshake_bottle", 7, 1.2f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1800));
    public static final Item DARK_CHOCOLATE_CHIP_COOKIE = food("dark_chocolate_chip_cookie", 2, 0.5f, tips(null, "dark_chocolate_chips_ingredient"));
    public static final Item DARK_CHOCOLATE_CHIP_ICE_CREAM_CONE = fastFood("dark_chocolate_chip_ice_cream_cone", 2, 1.2f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item DARK_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = bottle("dark_chocolate_chip_milkshake_bottle", 6, 1.1f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1800));
    public static final Item DARK_CHOCOLATE_CHIP_MINI_WAFFLE = fastFood("dark_chocolate_chip_mini_waffle", 3, 1.7f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(MobEffects.LUCK, 600));
    public static final Item DARK_CHOCOLATE_CHIP_MUFFIN = fastFood("dark_chocolate_chip_muffin", 4, 0.9f, tips(null, "dark_chocolate_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item DARK_CHOCOLATE_CHOCOLATE_PASTRY = food("dark_chocolate_chocolate_pastry", 4, 0.9f, tips(null, "dark_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item DARK_CHOCOLATE_FUDGE = food("dark_chocolate_fudge", 4, 0.5f, fx(ModEffectCategories.COMFORT, 3600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item DARK_CHOCOLATE_MARSHMALLOW_STICK = stickFood("dark_chocolate_marshmallow_stick", 5, 0.3f, false, tips(null, "dark_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.WARMTH, 1200), fx(ModEffectCategories.SUGAR_RUSH, 300));
    public static final Item DARK_CHOCOLATE_PASTRY = food("dark_chocolate_pastry", 3, 0.9f, tips(null, "dark_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item DARK_CHOCOLATE_PRETZEL_STICK = fastFood("dark_chocolate_pretzel_stick", 4, 0.5f, tips(null, "dark_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(ModEffectCategories.NOURISHMENT, 300));
    public static final Item DARK_CHOCOLATE_TOAST = food("dark_chocolate_toast", 5, 0.5f, tips(null, "dark_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item DICED_ONION = plain("diced_onion");
    public static final Item DICED_TOMATO = plain("diced_tomato");
    public static final Item DONUT_BASE = food("donut_base", 1, 1.1f);
    public static final Item DONUT_HOLE = food("donut_hole", 1, 0.4f);
    public static final Item DONUT_HOLE_SUGAR = food("donut_hole_sugar", 1, 0.4f, tips(null, "sugar_ingredient"), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.SUGAR_RUSH, 600));
    public static final Item DONUT_SUGAR = food("donut_sugar", 3, 0.6f, tips(null, "sugar_ingredient"), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item DRAGON_BUN = food("dragon_bun", 9, 0.4f, tips("dragon_meat"));
    public static final Item DRAGON_BUN_CRIMSON_FUNGUS = food("dragon_bun_crimson_fungus", 9, 0.5f, tips("dragon_meat", "crimson_fungus_ingredient"));
    public static final Item DRAGON_BUN_WARPED_FUNGUS = food("dragon_bun_warped_fungus", 9, 0.6f, tips("dragon_meat", "warped_fungus_ingredient"));
    public static final Item DRAGON_BURGER = food("dragon_burger", 11, 0.4f, tips("dragon_meat"), fx(MobEffects.ABSORPTION, 1200), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.EXPLOSION, 1200));
    public static final Item DRAGON_BURGER_CRIMSON_FUNGUS = food("dragon_burger_crimson_fungus", 11, 0.5f, tips("dragon_meat", "crimson_fungus_ingredient"), fx(MobEffects.ABSORPTION, 6000), fx(ModEffectCategories.NOURISHMENT, 3600), fx(MobEffects.FIRE_RESISTANCE, 1200), fx(ModEffectCategories.SATIATED_SHIELD, 1200), fx(ModEffectCategories.EXPLOSION, 1200));
    public static final Item DRAGON_BURGER_WARPED_FUNGUS = food("dragon_burger_warped_fungus", 11, 0.6f, tips("dragon_meat", "warped_fungus_ingredient"), fx(MobEffects.ABSORPTION, 6000), fx(ModEffectCategories.NOURISHMENT, 3600), fx(MobEffects.JUMP, 1200), fx(MobEffects.SLOW_FALLING, 1200), fx(ModEffectCategories.SATIATED_SHIELD, 1200), fx(ModEffectCategories.EXPLOSION, 1200));
    public static final Item DRAGON_PATTY = food("dragon_patty", 7, 0.6f, tips("dragon_meat"));
    public static final Item DRIED_COFFEE_BEANS = plain("dried_coffee_beans", "coffee");
    public static final Item DUMPLING_WRAPPERS = plain("dumpling_wrappers");
    public static final Item EGGPLANT_BUN = food("eggplant_bun", 5, 0.9f, tips("eggplant"));
    public static final Item EGGPLANT_BUN_CHEESE = food("eggplant_bun_cheese", 8, 0.9f, tips("eggplant", "cheese_ingredient"));
    public static final Item EGGPLANT_BUN_CHEESE_LETTUCE = food("eggplant_bun_cheese_lettuce", 10, 0.9f, tips("eggplant", "cheese_ingredient", "lettuce_ingredient"));
    public static final Item EGGPLANT_BUN_CHEESE_LETTUCE_TOMATO = food("eggplant_bun_cheese_lettuce_tomato", 10, 1.0f, tips("eggplant", "cheese_ingredient", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item EGGPLANT_BUN_CHEESE_TOMATO = food("eggplant_bun_cheese_tomato", 10, 0.9f, tips("eggplant", "cheese_ingredient", "tomato_ingredient"));
    public static final Item EGGPLANT_BUN_LETTUCE = food("eggplant_bun_lettuce", 7, 1.0f, tips("eggplant", "lettuce_ingredient", "tomato_ingredient"));
    public static final Item EGGPLANT_BUN_LETTUCE_TOMATO = food("eggplant_bun_lettuce_tomato", 7, 1.1f, tips("eggplant", "lettuce_ingredient"));
    public static final Item EGGPLANT_BUN_TOMATO = food("eggplant_bun_tomato", 7, 1.0f, tips("eggplant", "tomato_ingredient"));
    public static final Item EGGPLANT_BURGER = food("eggplant_burger", 7, 0.9f, tips("eggplant"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item EGGPLANT_BURGER_LETTUCE = food("eggplant_burger_lettuce", 9, 1.0f, tips("eggplant", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item EGGPLANT_BURGER_TOMATO = food("eggplant_burger_tomato", 9, 1.0f, tips("eggplant", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item EGGPLANT_CHEESEBURGER = food("eggplant_cheeseburger", 10, 0.9f, tips("eggplant"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item EGGPLANT_CHEESEBURGER_LETTUCE = food("eggplant_cheeseburger_lettuce", 12, 0.9f, tips("eggplant", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000));
    public static final Item EGGPLANT_CHEESEBURGER_LETTUCE_TOMATO = food("eggplant_cheeseburger_lettuce_tomato", 12, 1.0f, tips("eggplant", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000));
    public static final Item EGGPLANT_CHEESEBURGER_TOMATO = food("eggplant_cheeseburger_tomato", 12, 0.9f, tips("eggplant", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 6000));
    public static final Item EGG_BURRITO = food("egg_burrito", 10, 0.6f, tips(null, "egg_ingredient"), fx(ModEffectCategories.SUSTENANCE, 1200));
    public static final Item EGG_BURRITO_BACON = food("egg_burrito_bacon", 12, 0.7f, tips(null, "egg_ingredient", "bacon_ingredient"), fx(ModEffectCategories.SUSTENANCE, 2400));
    public static final Item EGG_BURRITO_CHEESE = food("egg_burrito_cheese", 11, 0.7f, tips(null, "egg_ingredient", "cheese_ingredient"), fx(ModEffectCategories.SUSTENANCE, 2400));
    public static final Item EGG_BURRITO_CHEESE_BACON = food("egg_burrito_cheese_bacon", 13, 0.8f, tips(null, "egg_ingredient", "cheese_ingredient", "bacon_ingredient"), fx(ModEffectCategories.SUSTENANCE, 2400));
    public static final Item EGG_BURRITO_CHEESE_SAUSAGE = food("egg_burrito_cheese_sausage", 13, 0.8f, tips(null, "egg_ingredient", "cheese_ingredient", "sausage_ingredient"), fx(ModEffectCategories.SUSTENANCE, 2400));
    public static final Item EGG_BURRITO_CHEESE_SAUSAGE_BACON = food("egg_burrito_cheese_sausage_bacon", 14, 0.8f, tips(null, "egg_ingredient", "cheese_ingredient", "sausage_ingredient", "bacon_ingredient"), fx(ModEffectCategories.SUSTENANCE, 2400));
    public static final Item EGG_BURRITO_SAUSAGE = food("egg_burrito_sausage", 12, 0.7f, tips(null, "egg_ingredient", "sausage_ingredient"), fx(ModEffectCategories.SUSTENANCE, 2400));
    public static final Item EGG_DUMPLINGS = food("egg_dumplings", 5, 0.6f, tips(null, "egg_ingredient", "onion_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item EGG_POWDER = plain("egg_powder");
    public static final Item EGG_WHITES_BOTTLE = ingredientBottle("egg_whites_bottle");
    public static final Item EGG_YOLK = plain("egg_yolk");
    public static final Item ENDERMITE_MEATBALL = food("endermite_meatball", 4, 0.6f, tips("endermite_meat"));
    public static final Item ENDERMITE_MEATBALL_SANDWICH = food("endermite_meatball_sandwich", 10, 0.4f, tips("endermite_meat", "endermite_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(MobEffects.REGENERATION, 1200), fx(ModEffectCategories.SULFUR, 1200));
    public static final Item ENDERMITE_MEATBALL_STICK_1 = stickFood("endermite_meatball_stick_1", 5, 0.6f, false, tips("endermite_meat"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.REGENERATION, 1200), fx(ModEffectCategories.SULFUR, 1200));
    public static final Item ENDERMITE_MEATBALL_STICK_2 = stickFood("endermite_meatball_stick_2", 6, 0.6f, false, tips("endermite_meat"), fx(MobEffects.REGENERATION, 3600), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SULFUR, 1200));
    public static final Item ENDERMITE_MEATBALL_STICK_3 = stickFood("endermite_meatball_stick_3", 8, 0.7f, false, tips("endermite_meat"), fx(MobEffects.REGENERATION, 6000), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.SULFUR, 4800));
    public static final Item ESPRESSO_POWDER = plain("espresso_powder", "coffee");
    public static final Item FISHCAKE = fastFood("fishcake", 4, 0.9f, fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.WATER_BREATHING, 3600));
    public static final Item FISH_BACON_PIZZA_SLICE = food("fish_bacon_pizza_slice", 6, 0.9f, tips(null, "fish_ingredient", "bacon_ingredient"));
    public static final Item FISH_BURRITO_RICE = food("fish_burrito_rice", 11, 0.6f, tips(null, "fish_ingredient", "rice_ingredient"), fx(ModEffectCategories.SUSTENANCE, 3600));
    public static final Item FISH_CALZONE = fastFood("fish_calzone", 7, 0.4f, tips(null, "cheese_ingredient", "fish_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item FISH_CHOWDER_BOWL = bowlFood("fish_chowder_bowl", 8, 0.8f, true, tips(null, "fish_ingredient"), fx(MobEffects.WATER_BREATHING, 1200), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.RESTED, 2400));
    public static final Item FISH_ONION_PIZZA_SLICE = food("fish_onion_pizza_slice", 8, 0.7f, tips(null, "fish_ingredient", "onion_ingredient"), fx(ModEffectCategories.REPULSION, 300));
    public static final Item FISH_PIZZA_SLICE = food("fish_pizza_slice", 6, 0.6f, tips(null, "fish_ingredient"));
    public static final Item FISH_RICE_BOWL = bowlFood("fish_rice_bowl", 8, 0.8f, true, tips(null, "kelp_ingredient"), fx(MobEffects.WATER_BREATHING, 1200), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item FISH_STICKS = food("fish_sticks", 6, 0.7f);
    public static final Item FISH_TACO = food("fish_taco", 8, 0.5f, tips(null, "fish_ingredient"));
    public static final Item FISH_TACO_KELP = food("fish_taco_kelp", 9, 0.5f, tips(null, "fish_ingredient", "kelp_ingredient"), fx(MobEffects.WATER_BREATHING, 600));
    public static final Item FISH_TACO_KELP_TACO_SAUCE = food("fish_taco_kelp_taco_sauce", 10, 0.7f, tips(null, "fish_ingredient", "kelp_ingredient", "taco_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(MobEffects.WATER_BREATHING, 1200));
    public static final Item FISH_WRAP_KELP_ONION = food("fish_wrap_kelp_onion", 10, 0.6f, tips(null, "fish_ingredient", "kelp_ingredient", "onion_ingredient"), fx(MobEffects.WATER_BREATHING, 600), fx(ModEffectCategories.REPULSION, 300));
    public static final Item FRIED_EGG_HASH_BROWN_SANDWICH = food("fried_egg_hash_brown_sandwich", 9, 1.2f, tips(null, "fried_egg_ingredient", "hash_browns_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item FRIED_EGG_PLATE = bowlFood("fried_egg_plate", 4, 0.4f);
    public static final Item FRIED_PITA_BREAD = food("fried_pita_bread", 2, 0.3f);
    public static final Item FRUIT_SMOOTHIE_BOTTLE = bottle("fruit_smoothie_bottle", 4, 1.8f, fx(ModEffectCategories.COMFORT, 6000), fx(MobEffects.DIG_SPEED, 1200), fx(MobEffects.LUCK, 1200), fx(ModEffectCategories.NOURISHMENT, 1200), fx(MobEffects.DAMAGE_BOOST, 1200));
    public static final Item GELATIN = plain("gelatin");
    public static final Item GELATIN_DESSERT_SLICE = fastFood("gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item GLOW_BERRY_CHEESECAKE_SLICE = food("glow_berry_cheesecake_slice", 3, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item GLOW_BERRY_COOKIE = food("glow_berry_cookie", 2, 0.5f, tips(null, "glow_berry_ingredient"));
    public static final Item GLOW_BERRY_CREAM_CAKE_SLICE = food("glow_berry_cream_cake_slice", 2, 0.3f, tips(null, "glow_berry_cream_frosting_ingredient"));
    public static final Item GLOW_BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT = food("glow_berry_cream_cake_slice_chorus_fruit", 3, 0.4f, tips(null, "glow_berry_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final Item GLOW_BERRY_CREAM_CAKE_SLICE_GLOW_BERRY = food("glow_berry_cream_cake_slice_glow_berry", 3, 0.3f, tips(null, "glow_berry_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final Item GLOW_BERRY_CREAM_CAKE_SLICE_SWEET_BERRY = food("glow_berry_cream_cake_slice_sweet_berry", 3, 0.3f, tips(null, "glow_berry_cream_frosting_ingredient", "berry_ingredient"));
    public static final Item GLOW_BERRY_CREAM_CHOCOLATE = fastFood("glow_berry_cream_chocolate", 7, 0.4f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item GLOW_BERRY_CREAM_CHOCOLATE_CUPCAKE = fastFood("glow_berry_cream_chocolate_cupcake", 4, 1.1f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(MobEffects.GLOWING, 1200), fx(MobEffects.NIGHT_VISION, 1200));
    public static final Item GLOW_BERRY_CREAM_CHOCOLATE_DONUT = food("glow_berry_cream_chocolate_donut", 4, 0.7f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item GLOW_BERRY_CREAM_CHOCOLATE_PASTRY = food("glow_berry_cream_chocolate_pastry", 3, 1.0f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL = food("glow_berry_cream_chocolate_sweet_roll", 6, 0.7f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY = food("glow_berry_cream_chocolate_sweet_roll_glow_berry", 6, 0.7f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.GLOWING, 1200), fx(MobEffects.NIGHT_VISION, 1200));
    public static final Item GLOW_BERRY_CREAM_CUPCAKE = fastFood("glow_berry_cream_cupcake", 3, 1.0f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600));
    public static final Item GLOW_BERRY_CREAM_DARK_CHOCOLATE = fastFood("glow_berry_cream_dark_chocolate", 8, 0.3f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item GLOW_BERRY_CREAM_DONUT = food("glow_berry_cream_donut", 3, 0.7f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item GLOW_BERRY_CREAM_FROSTING_BOTTLE = bottle("glow_berry_cream_frosting_bottle", 4, 0.4f);
    public static final Item GLOW_BERRY_CREAM_FROSTING_PIPING_BAG = pipingBag("glow_berry_cream_frosting_piping_bag", null, "glow_berry_cream_frosting_ingredient");
    public static final Item GLOW_BERRY_CREAM_MINI_WAFFLE = fastFood("glow_berry_cream_mini_waffle", 3, 1.9f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600), fx(MobEffects.LUCK, 600));
    public static final Item GLOW_BERRY_CREAM_MINI_WAFFLE_GLOW_BERRY = fastFood("glow_berry_cream_mini_waffle_glow_berry", 3, 1.9f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(MobEffects.GLOWING, 1200), fx(MobEffects.NIGHT_VISION, 1200), fx(MobEffects.LUCK, 600));
    public static final Item GLOW_BERRY_CREAM_PASTRY = food("glow_berry_cream_pastry", 2, 1.0f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.GLOWING, 1200), fx(MobEffects.NIGHT_VISION, 1200));
    public static final Item GLOW_BERRY_CREAM_SWEET_ROLL = food("glow_berry_cream_sweet_roll", 5, 0.6f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item GLOW_BERRY_CREAM_SWEET_ROLL_GLOW_BERRY = food("glow_berry_cream_sweet_roll_glow_berry", 5, 0.6f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600));
    public static final Item GLOW_BERRY_CREAM_WHITE_CHOCOLATE = fastFood("glow_berry_cream_white_chocolate", 6, 0.5f, tips(null, "glow_berry_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item GLOW_BERRY_ICE_CREAM_BOWL = bowlFood("glow_berry_ice_cream_bowl", 3, 1.0f);
    public static final Item GLOW_BERRY_ICE_CREAM_CONE = fastFood("glow_berry_ice_cream_cone", 2, 0.9f, fx(MobEffects.GLOWING, 600), fx(MobEffects.NIGHT_VISION, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item GLOW_BERRY_ICE_CREAM_SANDWICH = food("glow_berry_ice_cream_sandwich", 6, 0.6f, tips(null, "glow_berry_ice_cream_ingredient"));
    public static final Item GLOW_BERRY_ICE_CREAM_STICK = stickFood("glow_berry_ice_cream_stick", 1, 0.9f, true);
    public static final Item GLOW_BERRY_JAM_BOTTLE = bottle("glow_berry_jam_bottle", 4, 1.2f);
    public static final Item GLOW_BERRY_JAM_SANDWICH = food("glow_berry_jam_sandwich", 9, 0.4f, tips(null, "glow_berry_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item GLOW_BERRY_JUICE_BOTTLE = bottle("glow_berry_juice_bottle", 2, 1.6f);
    public static final Item GLOW_BERRY_MILKSHAKE_BOTTLE = bottle("glow_berry_milkshake_bottle", 5, 1.0f, fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item GLOW_BERRY_PIE_SLICE = food("glow_berry_pie_slice", 3, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item GLOW_BERRY_POPSICLE = stickFood("glow_berry_popsicle", 3, 0.5f, false);
    public static final Item GRAHAM_CRACKER = food("graham_cracker", 2, 0.8f);
    public static final Item GRAHAM_CRACKER_CHOCOLATE = food("graham_cracker_chocolate", 4, 0.8f, tips(null, "chocolate_ingredient"));
    public static final Item GRAHAM_CRACKER_CHOCOLATE_MARSHMALLOW = food("graham_cracker_chocolate_marshmallow", 6, 0.7f, tips(null, "chocolate_ingredient", "marshmallow_ingredient"));
    public static final Item GRAHAM_CRACKER_CRUMBS = plain("graham_cracker_crumbs");
    public static final Item GRAHAM_CRACKER_MARSHMALLOW = food("graham_cracker_marshmallow", 3, 0.7f, tips(null, "marshmallow_ingredient"));
    public static final Item GRAHAM_CRACKER_PIE_CRUST = food("graham_cracker_pie_crust", 6, 0.7f);
    public static final Item GRAY_GELATIN_DESSERT_SLICE = fastFood("gray_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item GREEN_GELATIN_DESSERT_SLICE = fastFood("green_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item GRILLED_CHEESE_SANDWICH = food("grilled_cheese_sandwich", 9, 0.5f);
    public static final Item GROUND_BEEF = plain("ground_beef");
    public static final Item GROUND_CHICKEN = plain("ground_chicken");
    public static final Item GROUND_ENDERMITE = plain("ground_endermite", "endermite_meat");
    public static final Item GROUND_MUTTON = plain("ground_mutton");
    public static final Item GROUND_PORK = plain("ground_pork");
    public static final Item GROUND_RABBIT = plain("ground_rabbit");
    public static final Item GROUND_SAUSAGE = plain("ground_sausage");
    public static final Item GYRO_MEAT_SLICE = food("gyro_meat_slice", 4, 1.1f, fx(ModEffectCategories.TOUGH, 300));
    public static final Item HAMBURGER = food("hamburger", 8, 0.6f, fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_BACON = food("hamburger_bacon", 10, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_BACON_LETTUCE = food("hamburger_bacon_lettuce", 11, 0.7f, tips(null, "bacon_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_BACON_LETTUCE_TOMATO = food("hamburger_bacon_lettuce_tomato", 11, 0.8f, tips(null, "bacon_ingredient", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_CRIMSON_FUNGUS = food("hamburger_crimson_fungus", 11, 0.5f, tips(null, "crimson_fungus_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(MobEffects.FIRE_RESISTANCE, 1200), fx(ModEffectCategories.SATIATED_SHIELD, 600));
    public static final Item HAMBURGER_LETTUCE = food("hamburger_lettuce", 9, 0.7f, tips(null, "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_LETTUCE_TOMATO = food("hamburger_lettuce_tomato", 9, 0.8f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_ONION = food("hamburger_onion", 8, 0.8f, tips(null, "onion_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.REPULSION, 300));
    public static final Item HAMBURGER_ONION_BACON = food("hamburger_onion_bacon", 11, 0.7f, tips(null, "onion_ingredient", "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.REPULSION, 300));
    public static final Item HAMBURGER_ONION_BACON_LETTUCE = food("hamburger_onion_bacon_lettuce", 12, 0.8f, tips(null, "onion_ingredient", "bacon_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.REPULSION, 300));
    public static final Item HAMBURGER_ONION_LETTUCE = food("hamburger_onion_lettuce", 10, 0.7f, tips(null, "onion_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.REPULSION, 300));
    public static final Item HAMBURGER_PEANUT_BUTTER = food("hamburger_peanut_butter", 8, 0.8f, tips("peanut_butter", "peanut_butter_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_PEANUT_BUTTER_BACON = food("hamburger_peanut_butter_bacon", 10, 0.8f, tips("peanut_butter", "peanut_butter_ingredient", "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_TOMATO = food("hamburger_tomato", 9, 0.7f, tips(null, "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HAMBURGER_WARPED_FUNGUS = food("hamburger_warped_fungus", 11, 0.6f, tips(null, "warped_fungus_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(MobEffects.SLOW_FALLING, 1200), fx(ModEffectCategories.SATIATED_SHIELD, 600));
    public static final Item HASH_BROWNS = food("hash_browns", 4, 0.6f);
    public static final Item HASH_BROWN_FRIED_EGG_PLATE = bowlFood("hash_brown_fried_egg_plate", 8, 0.7f);
    public static final Item HASH_BROWN_PLATE = bowlFood("hash_brown_plate", 4, 0.6f);
    public static final Item HASH_BROWN_TOAST_PLATE = bowlFood("hash_brown_toast_plate", 8, 0.7f);
    public static final Item HEAVY_CREAM_BOTTLE = ingredientBottle("heavy_cream_bottle");
    public static final Item HOLLOW_CHOCOLATE = fastFood("hollow_chocolate", 2, 0.2f);
    public static final Item HOLLOW_DARK_CHOCOLATE = fastFood("hollow_dark_chocolate", 5, 0.2f);
    public static final Item HOLLOW_WHITE_CHOCOLATE = fastFood("hollow_white_chocolate", 3, 0.4f);
    public static final Item HONEYED_APPLE_SLICE = fastFood("honeyed_apple_slice", 4, 0.9f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.ANIMAL_CHARM, 1200));
    public static final Item HONEYED_BERRIES = food("honeyed_berries", 7, 0.9f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item HONEYED_BISCUIT = food("honeyed_biscuit", 4, 0.7f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item HONEYED_CHOCOLATE_CUPCAKE = fastFood("honeyed_chocolate_cupcake", 5, 1.1f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item HONEYED_CHOCOLATE_DONUT = food("honeyed_chocolate_donut", 4, 0.7f, tips(null, "honey_ingredient"), fx(MobEffects.MOVEMENT_SPEED, 3600));
    public static final Item HONEYED_CHOCOLATE_SWEET_ROLL = food("honeyed_chocolate_sweet_roll", 6, 0.8f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item HONEYED_CUPCAKE = fastFood("honeyed_cupcake", 4, 1.0f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item HONEYED_DONUT = food("honeyed_donut", 3, 0.7f, tips(null, "honey_ingredient"), fx(MobEffects.MOVEMENT_SPEED, 1200));
    public static final Item HONEYED_MINI_WAFFLE = fastFood("honeyed_mini_waffle", 3, 1.8f, tips(null, "honey_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final Item HONEYED_MUFFIN = fastFood("honeyed_muffin", 4, 0.7f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item HONEYED_PRETZEL_STICK = fastFood("honeyed_pretzel_stick", 3, 0.8f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.REGENERATION, 600));
    public static final Item HONEYED_SWEET_ROLL = food("honeyed_sweet_roll", 5, 0.7f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item HONEYED_TOAST = food("honeyed_toast", 4, 0.8f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item HONEY_SANDWICH = fastFood("honey_sandwich", 7, 0.7f, fx(MobEffects.MOVEMENT_SPEED, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item HOT_CHOCOLATE_BOTTLE = bottle("hot_chocolate_bottle", 9, 0.9f, fx(ModEffectCategories.COMFORT, 6000), fx(ModEffectCategories.RESTED, 6000), fx(ModEffectCategories.WARMTH, 6000));
    public static final Item HOT_DARK_CHOCOLATE_BOTTLE = bottle("hot_dark_chocolate_bottle", 10, 0.8f, fx(ModEffectCategories.COMFORT, 6000), fx(ModEffectCategories.RESTED, 6000), fx(ModEffectCategories.WARMTH, 6000));
    public static final Item HOT_WHITE_CHOCOLATE_BOTTLE = bottle("hot_white_chocolate_bottle", 8, 1.0f, fx(ModEffectCategories.COMFORT, 6000), fx(ModEffectCategories.RESTED, 6000), fx(ModEffectCategories.WARMTH, 6000));
    public static final Item ICE_CREAM_BOWL = bowlFood("ice_cream_bowl", 2, 1.1f);
    public static final Item ICE_CREAM_CONE = fastFood("ice_cream_cone", 2, 0.8f, fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item ICE_CREAM_SANDWICH = food("ice_cream_sandwich", 6, 0.7f);
    public static final Item ICE_CREAM_SANDWICH_NEAPOLITAN = food("ice_cream_sandwich_neapolitan", 7, 0.8f, tips(null, "chocolate_ice_cream_ingredient", "ice_cream_ingredient", "berry_ice_cream_ingredient"));
    public static final Item ICE_CREAM_STICK = stickFood("ice_cream_stick", 1, 0.8f, true);
    public static final Item KELP_ROLL_BEETROOT = food("kelp_roll_beetroot", 13, 0.6f, tips(null, "beetroot_ingredient"));
    public static final Item KELP_ROLL_BROWN_MUSHROOM = food("kelp_roll_brown_mushroom", 12, 0.6f, tips(null, "brown_mushroom_ingredient"));
    public static final Item KELP_ROLL_CRIMSON_FUNGUS = food("kelp_roll_crimson_fungus", 13, 0.6f, tips(null, "crimson_fungus_ingredient"));
    public static final Item KELP_ROLL_FISH = food("kelp_roll_fish", 11, 0.6f, tips(null, "fish_ingredient"));
    public static final Item KELP_ROLL_LETTUCE = food("kelp_roll_lettuce", 10, 0.4f, tips(null, "lettuce_ingredient"));
    public static final Item KELP_ROLL_ONION = food("kelp_roll_onion", 11, 0.6f, tips(null, "onion_ingredient"), fx(ModEffectCategories.REPULSION, 300));
    public static final Item KELP_ROLL_RED_MUSHROOM = food("kelp_roll_red_mushroom", 12, 0.6f, tips(null, "red_mushroom_ingredient"));
    public static final Item KELP_ROLL_RICE = food("kelp_roll_rice", 12, 0.7f, tips(null, "rice_ingredient"));
    public static final Item KELP_ROLL_SLICE_BEETROOT = food("kelp_roll_slice_beetroot", 6, 0.6f, tips(null, "beetroot_ingredient"));
    public static final Item KELP_ROLL_SLICE_BROWN_MUSHROOM = food("kelp_roll_slice_brown_mushroom", 6, 0.6f, tips(null, "brown_mushroom_ingredient"));
    public static final Item KELP_ROLL_SLICE_CRIMSON_FUNGUS = food("kelp_roll_slice_crimson_fungus", 7, 0.6f, tips(null, "crimson_fungus_ingredient"));
    public static final Item KELP_ROLL_SLICE_FISH = food("kelp_roll_slice_fish", 5, 0.6f, tips(null, "fish_ingredient"));
    public static final Item KELP_ROLL_SLICE_LETTUCE = food("kelp_roll_slice_lettuce", 5, 0.4f, tips(null, "lettuce_ingredient"));
    public static final Item KELP_ROLL_SLICE_ONION = food("kelp_roll_slice_onion", 5, 0.6f, tips(null, "onion_ingredient"), fx(ModEffectCategories.REPULSION, 150));
    public static final Item KELP_ROLL_SLICE_RED_MUSHROOM = food("kelp_roll_slice_red_mushroom", 6, 0.6f, tips(null, "red_mushroom_ingredient"));
    public static final Item KELP_ROLL_SLICE_RICE = food("kelp_roll_slice_rice", 6, 0.7f, tips(null, "rice_ingredient"));
    public static final Item KELP_ROLL_SLICE_TOMATO = food("kelp_roll_slice_tomato", 6, 0.5f, tips(null, "tomato_ingredient"));
    public static final Item KELP_ROLL_SLICE_WARPED_FUNGUS = food("kelp_roll_slice_warped_fungus", 7, 0.6f, tips(null, "warped_fungus_ingredient"));
    public static final Item KELP_ROLL_TOMATO = food("kelp_roll_tomato", 12, 0.5f, tips(null, "tomato_ingredient"));
    public static final Item KELP_ROLL_WARPED_FUNGUS = food("kelp_roll_warped_fungus", 13, 0.6f, tips(null, "warped_fungus_ingredient"));
    public static final Item KELP_SOUP_BOWL = bowlFood("kelp_soup_bowl", 11, 0.7f, true, fx(MobEffects.WATER_BREATHING, 3600), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item LEATHER_SOUP_BOWL = bowlFood("leather_soup_bowl", 3, 1.5f, true, fx(ModEffectCategories.COMFORT, 300));
    public static final Item LIGHT_BLUE_GELATIN_DESSERT_SLICE = fastFood("light_blue_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item LIGHT_GRAY_GELATIN_DESSERT_SLICE = fastFood("light_gray_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item LIME_GELATIN_DESSERT_SLICE = fastFood("lime_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item MACARONI = food("macaroni", 2, 0.5f);
    public static final Item MACARONI_BOWL = bowlFood("macaroni_bowl", 3, 0.5f, fx(ModEffectCategories.SATIATION, 1800));
    public static final Item MACARONI_BOWL_BACON = bowlFood("macaroni_bowl_bacon", 4, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1800), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item MACARONI_BOWL_CHEESE = bowlFood("macaroni_bowl_cheese", 3, 0.6f, tips(null, "cheese_ingredient"), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item MACARONI_BOWL_CHEESE_BACON = bowlFood("macaroni_bowl_cheese_bacon", 4, 0.6f, tips(null, "cheese_ingredient", "bacon_ingredient"), fx(ModEffectCategories.COMFORT, 3600), fx(ModEffectCategories.NOURISHMENT, 2400), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item MACARONI_BOWL_CHEESE_SAUSAGE = bowlFood("macaroni_bowl_cheese_sausage", 5, 0.5f, tips(null, "cheese_ingredient", "sausage_ingredient"), fx(ModEffectCategories.COMFORT, 2400), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item MACARONI_BOWL_SAUSAGE = bowlFood("macaroni_bowl_sausage", 5, 0.5f, tips(null, "sausage_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3000), fx(ModEffectCategories.SATIATION, 1800));
    public static final Item MAGENTA_GELATIN_DESSERT_SLICE = fastFood("magenta_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item MAGMA_CREAM_MARSHMALLOW = fastFood("magma_cream_marshmallow", 2, 0.2f, fx(MobEffects.FIRE_RESISTANCE, 600));
    public static final Item MAGMA_CREAM_MARSHMALLOW_STICK = stickFood("magma_cream_marshmallow_stick", 3, 0.2f, false, fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.FIRE_RESISTANCE, 1200), fx(ModEffectCategories.WARMTH, 3600), fx(ModEffectCategories.SUGAR_RUSH, 600));
    public static final Item MARSHMALLOW = fastFood("marshmallow", 2, 0.2f);
    public static final Item MARSHMALLOW_BUTTERSCOTCH_FUDGE = food("marshmallow_butterscotch_fudge", 3, 0.9f, tips(null, "marshmallow_ingredient"), fx(MobEffects.DIG_SPEED, 6000), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item MARSHMALLOW_CARAMEL_FUDGE = food("marshmallow_caramel_fudge", 5, 0.5f, tips(null, "marshmallow_ingredient"), fx(MobEffects.DAMAGE_BOOST, 6000), fx(ModEffectCategories.COMFORT, 3600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item MARSHMALLOW_CHOCOLATE = fastFood("marshmallow_chocolate", 6, 0.5f, tips(null, "marshmallow_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item MARSHMALLOW_CHOCOLATE_FUDGE = food("marshmallow_chocolate_fudge", 3, 0.7f, tips(null, "marshmallow_ingredient"), fx(ModEffectCategories.COMFORT, 6000), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item MARSHMALLOW_COFFEE_TOFFEE_FUDGE = food("marshmallow_coffee_toffee_fudge", 4, 0.8f, tips("coffee", "marshmallow_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DIG_SPEED, 600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item MARSHMALLOW_DARK_CHOCOLATE = fastFood("marshmallow_dark_chocolate", 7, 0.4f, tips(null, "marshmallow_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item MARSHMALLOW_DARK_CHOCOLATE_FUDGE = food("marshmallow_dark_chocolate_fudge", 4, 0.6f, tips(null, "marshmallow_ingredient"), fx(ModEffectCategories.COMFORT, 6000), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item MARSHMALLOW_STICK = stickFood("marshmallow_stick", 3, 0.2f, false, fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.WARMTH, 600), fx(ModEffectCategories.SUGAR_RUSH, 150));
    public static final Item MARSHMALLOW_TOFFEE_FUDGE = food("marshmallow_toffee_fudge", 4, 0.6f, tips(null, "marshmallow_ingredient"), fx(MobEffects.MOVEMENT_SPEED, 6000), fx(ModEffectCategories.COMFORT, 3600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item MARSHMALLOW_WHITE_CHOCOLATE = fastFood("marshmallow_white_chocolate", 5, 0.6f, tips(null, "marshmallow_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item MARSHMALLOW_WHITE_CHOCOLATE_FUDGE = food("marshmallow_white_chocolate_fudge", 2, 0.8f, tips(null, "marshmallow_ingredient"), fx(ModEffectCategories.COMFORT, 6000), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item MASHED_POTATOES_BOWL = bowlFood("mashed_potatoes_bowl", 7, 0.8f, fx(ModEffectCategories.SATIATION, 3600));
    public static final Item MASHED_POTATOES_BOWL_BACON = bowlFood("mashed_potatoes_bowl_bacon", 9, 1.0f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.SATIATION, 3600));
    public static final Item MASHED_POTATOES_BOWL_CHEESE = bowlFood("mashed_potatoes_bowl_cheese", 9, 1f, tips(null, "cheese_ingredient"), fx(ModEffectCategories.SATIATION, 3600));
    public static final Item MASHED_POTATOES_BOWL_MUSHROOM = bowlFood("mashed_potatoes_bowl_mushroom", 8, 0.8f, tips(null, "mushroom_ingredient"), fx(ModEffectCategories.SATIATION, 3600));
    public static final Item MASHED_POTATOES_BOWL_SOUR_CREAM = bowlFood("mashed_potatoes_bowl_sour_cream", 8, 0.9f, tips(null, "sour_cream_ingredient"), fx(ModEffectCategories.SATIATION, 3600));
    public static final Item MEAT_PIE_FILLING = plain("meat_pie_filling");
    public static final Item MEAT_PIE_SLICE = food("meat_pie_slice", 5, 0.8f, fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item MELON_CREAM_CAKE_SLICE = food("melon_cream_cake_slice", 2, 0.3f, tips(null, "melon_cream_frosting_ingredient"));
    public static final Item MELON_CREAM_CHOCOLATE = fastFood("melon_cream_chocolate", 7, 0.6f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item MELON_CREAM_CHOCOLATE_CUPCAKE = fastFood("melon_cream_chocolate_cupcake", 4, 1.2f, tips(null, "melon_cream_frosting_ingredient"), fx(MobEffects.REGENERATION, 1200));
    public static final Item MELON_CREAM_CHOCOLATE_DONUT = food("melon_cream_chocolate_donut", 5, 0.9f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 3600), fx(MobEffects.REGENERATION, 3600));
    public static final Item MELON_CREAM_CHOCOLATE_PASTRY = food("melon_cream_chocolate_pastry", 4, 1.1f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item MELON_CREAM_CHOCOLATE_SWEET_ROLL = food("melon_cream_chocolate_sweet_roll", 6, 0.9f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item MELON_CREAM_CUPCAKE = fastFood("melon_cream_cupcake", 3, 1.1f, tips(null, "melon_cream_frosting_ingredient"), fx(MobEffects.REGENERATION, 600));
    public static final Item MELON_CREAM_DARK_CHOCOLATE = fastFood("melon_cream_dark_chocolate", 8, 0.5f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item MELON_CREAM_DONUT = food("melon_cream_donut", 4, 0.9f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.REGENERATION, 1200));
    public static final Item MELON_CREAM_FROSTING_BOTTLE = bottle("melon_cream_frosting_bottle", 4, 0.8f);
    public static final Item MELON_CREAM_FROSTING_PIPING_BAG = pipingBag("melon_cream_frosting_piping_bag", null, "melon_cream_frosting_ingredient");
    public static final Item MELON_CREAM_MINI_WAFFLE = fastFood("melon_cream_mini_waffle", 3, 1.6f, tips(null, "melon_cream_frosting_ingredient"), fx(MobEffects.REGENERATION, 600), fx(MobEffects.LUCK, 600));
    public static final Item MELON_CREAM_PASTRY = food("melon_cream_pastry", 3, 1.1f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.REGENERATION, 1200));
    public static final Item MELON_CREAM_SWEET_ROLL = food("melon_cream_sweet_roll", 5, 0.8f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item MELON_CREAM_WHITE_CHOCOLATE = food("melon_cream_white_chocolate", 6, 0.7f, tips(null, "melon_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item MELON_CUSTARD_BOTTLE = bottle("melon_custard_bottle", 9, 0.8f);
    public static final Item MELON_ICE_CREAM_BOWL = bowlFood("melon_ice_cream_bowl", 3, 1.2f);
    public static final Item MELON_ICE_CREAM_CONE = fastFood("melon_ice_cream_cone", 2, 1.1f, fx(MobEffects.REGENERATION, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item MELON_ICE_CREAM_SANDWICH = food("melon_ice_cream_sandwich", 6, 0.7f, tips(null, "melon_ice_cream_ingredient"));
    public static final Item MELON_ICE_CREAM_STICK = stickFood("melon_ice_cream_stick", 1, 1.1f, true);
    public static final Item MELON_JAM_BOTTLE = bottle("melon_jam_bottle", 4, 1.7f);
    public static final Item MELON_JAM_SANDWICH = fastFood("melon_jam_sandwich", 8, 0.8f, tips(null, "melon_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item MELON_MILKSHAKE_BOTTLE = bottle("melon_milkshake_bottle", 6, 1.0f, fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.REGENERATION, 600));
    public static final Item MERINGUE_BOWL = ingredientBowl("meringue_bowl");
    public static final Item MERINGUE_COOKIE = food("meringue_cookie", 2, 0.4f);
    public static final Item MILKSHAKE_BOTTLE = bottle("milkshake_bottle", 4, 0.8f);
    public static final Item MILK_POWDER = plain("milk_powder");
    public static final Item MINCED_DRAGON = plain("minced_dragon", "dragon_meat");
    public static final Item MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = food("mini_chocolate_graham_cracker_pie_crust", 4, 0.9f);
    public static final Item MINI_CHOCOLATE_PIE_GRAHAM_CRACKER = food("mini_chocolate_pie_graham_cracker", 7, 0.8f, tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final Item MINI_COOKIE_CREAM_PIE = food("mini_cookie_cream_pie", 8, 0.8f);
    public static final Item MINI_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = food("mini_cream_pie_chocolate_graham_cracker", 6, 1.0f, tips(null, "chocolate_graham_cracker_pie_crust_ingredient"));
    public static final Item MINI_CREAM_PIE_GRAHAM_CRACKER = food("mini_cream_pie_graham_cracker", 6, 0.9f, tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final Item MINI_GRAHAM_CRACKER_PIE_CRUST = food("mini_graham_cracker_pie_crust", 3, 0.9f);
    public static final Item MINI_SMORES_PIE = food("mini_smores_pie", 9, 0.7f, fx(ModEffectCategories.COMFORT, 6000), fx(ModEffectCategories.WARMTH, 3600));
    public static final Item MINI_WAFFLE = fastFood("mini_waffle", 2, 0.6f);
    public static final Item MIXED_SALAD_BEETROOT_CARROT = food("mixed_salad_beetroot_carrot", 6, 0.7f, tips(null, "beetroot_ingredient", "carrot_ingredient"), fx(MobEffects.REGENERATION, 300));
    public static final Item MOLASSES_BOTTLE = ingredientBottle("molasses_bottle");
    public static final Item MOZZARELLA_STICKS = food("mozzarella_sticks", 5, 0.7f);
    public static final Item MUFFIN_BASE = fastFood("muffin_base", 3, 0.7f);
    public static final Item MUSHROOM_BACON_PIZZA_SLICE = food("mushroom_bacon_pizza_slice", 5, 0.9f, tips(null, "mushroom_ingredient", "bacon_ingredient"));
    public static final Item MUSHROOM_BURRITO_RICE = food("mushroom_burrito_rice", 8, 0.6f, tips(null, "mushroom_ingredient", "rice_ingredient"), fx(ModEffectCategories.SUSTENANCE, 3600), fx(ModEffectCategories.FARMERS_BLESSING, 1200));
    public static final Item MUSHROOM_CALZONE = fastFood("mushroom_calzone", 6, 0.6f, tips(null, "cheese_ingredient", "mushroom_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item MUSHROOM_CREAM_SOUP_BOWL = bowlFood("mushroom_cream_soup_bowl", 7, 0.9f, true, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item MUSHROOM_FISH_PIZZA_SLICE = food("mushroom_fish_pizza_slice", 6, 0.9f, tips(null, "mushroom_ingredient", "fish_ingredient"));
    public static final Item MUSHROOM_ONION_PIZZA_SLICE = food("mushroom_onion_pizza_slice", 7, 0.7f, tips(null, "mushroom_ingredient", "onion_ingredient"), fx(ModEffectCategories.REPULSION, 300));
    public static final Item MUSHROOM_PIZZA_SLICE = food("mushroom_pizza_slice", 5, 0.6f, tips(null, "mushroom_ingredient"));
    public static final Item MUSHROOM_TACO = food("mushroom_taco", 7, 0.5f, tips(null, "mushroom_ingredient"));
    public static final Item MUSHROOM_TACO_LETTUCE = food("mushroom_taco_lettuce", 8, 0.6f, tips(null, "mushroom_ingredient", "lettuce_ingredient"));
    public static final Item MUSHROOM_TACO_LETTUCE_TACO_SAUCE = food("mushroom_taco_lettuce_taco_sauce", 9, 0.7f, tips(null, "mushroom_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item MUSHROOM_WRAP_LETTUCE_POTATO = food("mushroom_wrap_lettuce_potato", 9, 0.7f, tips(null, "mushroom_ingredient", "lettuce_ingredient", "potato_ingredient"), fx(ModEffectCategories.FARMERS_BLESSING, 3600));
    public static final Item MUTTON_BURRITO_RICE = food("mutton_burrito_rice", 12, 0.7f, tips(null, "mutton_ingredient", "rice_ingredient"), fx(ModEffectCategories.SUSTENANCE, 3600), fx(ModEffectCategories.TOUGH, 300));
    public static final Item MUTTON_CALZONE = fastFood("mutton_calzone", 7, 0.5f, tips(null, "cheese_ingredient", "mutton_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.TOUGH, 300));
    public static final Item MUTTON_SANDWICH = food("mutton_sandwich", 7, 0.6f, tips(null, "mutton_ingredient"), fx(ModEffectCategories.TOUGH, 300));
    public static final Item MUTTON_SANDWICH_BEETROOT = food("mutton_sandwich_beetroot", 7, 0.8f, tips(null, "mutton_ingredient", "beetroot_ingredient"), fx(ModEffectCategories.TOUGH, 300));
    public static final Item MUTTON_STEW_BOWL = bowlFood("mutton_stew_bowl", 12, 0.9f, true, fx(ModEffectCategories.COMFORT, 3600), fx(ModEffectCategories.TOUGH, 300));
    public static final Item MUTTON_TACO = food("mutton_taco", 9, 0.6f, tips(null, "mutton_ingredient"), fx(ModEffectCategories.TOUGH, 300));
    public static final Item MUTTON_TACO_LETTUCE = food("mutton_taco_lettuce", 11, 0.6f, tips(null, "mutton_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.TOUGH, 300));
    public static final Item MUTTON_TACO_LETTUCE_TACO_SAUCE = food("mutton_taco_lettuce_taco_sauce", 11, 0.8f, tips(null, "mutton_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.TOUGH, 300));
    public static final Item MUTTON_WRAP_LETTUCE_TOMATO = food("mutton_wrap_lettuce_tomato", 10, 0.7f, tips(null, "mutton_ingredient", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.TOUGH, 300));
    public static final Item NACHO_BOWL = bowlFood("nacho_bowl", 6, 0.9f, fx(MobEffects.LUCK, 1200));
    public static final Item NACHO_BOWL_BEEF = bowlFood("nacho_bowl_beef", 9, 0.8f, tips(null, "beef_ingredient"), fx(MobEffects.LUCK, 1200));
    public static final Item NACHO_BOWL_BEEF_TACO_SAUCE = bowlFood("nacho_bowl_beef_taco_sauce", 11, 0.8f, tips(null, "beef_ingredient", "taco_sauce_ingredient"), fx(MobEffects.LUCK, 1200));
    public static final Item NACHO_BOWL_SOUR_CREAM = bowlFood("nacho_bowl_sour_cream", 7, 0.9f, tips(null, "sour_cream_ingredient"), fx(MobEffects.LUCK, 1200));
    public static final Item NACHO_BOWL_TACO_SAUCE = bowlFood("nacho_bowl_taco_sauce", 8, 0.7f, tips(null, "taco_sauce_ingredient"), fx(MobEffects.LUCK, 1200));
    public static final Item ONION_BACON_PIZZA_SLICE = food("onion_bacon_pizza_slice", 7, 0.7f, tips(null, "onion_ingredient", "bacon_ingredient"), fx(ModEffectCategories.REPULSION, 300));
    public static final Item ONION_CALZONE = fastFood("onion_calzone", 6, 0.5f, tips(null, "cheese_ingredient", "onion_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.REPULSION, 300));
    public static final Item ONION_PIZZA_SLICE = food("onion_pizza_slice", 6, 0.5f, tips(null, "onion_ingredient"), fx(ModEffectCategories.REPULSION, 300));
    public static final Item ONION_RINGS = food("onion_rings", 5, 0.6f, fx(ModEffectCategories.REPULSION, 300));
    public static final Item ORANGE_GELATIN_DESSERT_SLICE = fastFood("orange_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item PAPRIKA = plain("paprika");
    public static final Item PASTA = food("pasta", 2, 0.8f);
    public static final Item PASTA_PLATE = bowlFood("pasta_plate", 5, 0.7f, fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_BEEF_MEATBALLS = bowlFood("pasta_plate_beef_meatballs", 8, 0.6f, tips(null, "beef_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_BUTTER = bowlFood("pasta_plate_butter", 6, 0.8f, tips(null, "butter_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_CHEESE = bowlFood("pasta_plate_cheese", 5, 0.9f, tips(null, "cheese_ingredient"), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_CHEESE_TOMATO_SAUCE = bowlFood("pasta_plate_cheese_tomato_sauce", 9, 1.1f, tips(null, "tomato_sauce_ingredient", "cheese_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_CHICKEN_CUT = bowlFood("pasta_plate_chicken_cut", 7, 0.7f, tips(null, "chicken_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_CHICKEN_CUT_TOMATO_SAUCE = bowlFood("pasta_plate_chicken_cut_tomato_sauce", 11, 0.9f, tips(null, "tomato_sauce_ingredient", "chicken_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_EGGPLANT = bowlFood("pasta_plate_eggplant", 7, 0.8f, tips("eggplant", "eggplant_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_ENDERMITE_MEATBALLS = bowlFood("pasta_plate_endermite_meatballs", 8, 0.6f, tips("endermite_meat", "endermite_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200), fx(ModEffectCategories.SULFUR, 4800));
    public static final Item PASTA_PLATE_ENDERMITE_MEATBALLS_TOMATO_SAUCE = bowlFood("pasta_plate_endermite_meatballs_tomato_sauce", 12, 0.8f, tips("endermite_meat", "tomato_sauce_ingredient", "endermite_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.SATIATION, 1200), fx(ModEffectCategories.SULFUR, 6000));
    public static final Item PASTA_PLATE_FISH = bowlFood("pasta_plate_fish", 7, 0.8f, tips(null, "fish_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_FISH_TOMATO_SAUCE = bowlFood("pasta_plate_fish_tomato_sauce", 11, 0.8f, tips(null, "tomato_sauce_ingredient", "fish_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_MUSHROOM = bowlFood("pasta_plate_mushroom", 7, 0.7f, tips(null, "mushroom_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item PASTA_PLATE_MUSHROOM_TOMATO_SAUCE = bowlFood("pasta_plate_mushroom_tomato_sauce", 9, 0.9f, tips(null, "mushroom_ingredient", "tomato_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item PASTA_PLATE_MUTTON_CHOP = bowlFood("pasta_plate_mutton_chop", 8, 0.6f, tips(null, "mutton_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_PORK_MEATBALLS = bowlFood("pasta_plate_pork_meatballs", 7, 0.8f, tips(null, "pork_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_PORK_MEATBALLS_TOMATO_SAUCE = bowlFood("pasta_plate_pork_meatballs_tomato_sauce", 11, 1.0f, tips(null, "tomato_sauce_ingredient", "pork_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_RABBIT_MEATBALLS = bowlFood("pasta_plate_rabbit_meatballs", 6, 0.9f, tips(null, "rabbit_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_RABBIT_MEATBALLS_TOMATO_SAUCE = bowlFood("pasta_plate_rabbit_meatballs_tomato_sauce", 10, 1.1f, tips(null, "tomato_sauce_ingredient", "rabbit_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_SLIME = bowlFood("pasta_plate_slime", 6, 0.8f, tips(null, "slime_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_SLIMEBALLS = bowlFood("pasta_plate_slimeballs", 8, 0.6f, tips(null, "slimeballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_SQUID_INK = bowlFood("pasta_plate_squid_ink", 6, 0.8f, tips(null, "squid_ink_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_STRIDER_MEATBALLS = bowlFood("pasta_plate_strider_meatballs", 8, 0.6f, tips("strider_meat", "strider_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_STRIDER_MEATBALLS_TOMATO_SAUCE = bowlFood("pasta_plate_strider_meatballs_tomato_sauce", 12, 0.8f, tips("strider_meat", "tomato_sauce_ingredient", "strider_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTA_PLATE_TOMATO_SAUCE = bowlFood("pasta_plate_tomato_sauce", 7, 0.9f, tips(null, "tomato_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.SATIATION, 1200));
    public static final Item PASTRY_BASE = food("pastry_base", 2, 0.8f);
    public static final Item PEANUT_BUTTER_APPLE_JAM_SANDWICH = food("peanut_butter_apple_jam_sandwich", 8, 0.8f, tips("peanut_butter", "peanut_butter_ingredient", "apple_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item PEANUT_BUTTER_CHORUS_FRUIT_JAM_SANDWICH = food("peanut_butter_chorus_fruit_jam_sandwich", 11, 0.6f, tips("peanut_butter", "peanut_butter_ingredient", "chorus_fruit_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item PEANUT_BUTTER_MELON_JAM_SANDWICH = food("peanut_butter_melon_jam_sandwich", 9, 0.9f, tips("peanut_butter", "peanut_butter_ingredient", "melon_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item PINK_GELATIN_DESSERT_SLICE = food("pink_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item PITA_BREAD = food("pita_bread", 3, 1.8f);
    public static final Item PITA_CHIPS = fastFood("pita_chips", 2, 0.3f);
    public static final Item PITA_CHIP_BOWL = bowlFood("pita_chip_bowl", 4, 0.4f);
    public static final Item PITA_DOUGH = plain("pita_dough");
    public static final Item PORK_BURRITO_RICE = food("pork_burrito_rice", 13, 0.6f, tips(null, "pork_ingredient", "rice_ingredient"), fx(ModEffectCategories.SUSTENANCE, 3600));
    public static final Item PORK_MEATBALL = food("pork_meatball", 3, 0.8f);
    public static final Item PORK_MEATBALL_SANDWICH = food("pork_meatball_sandwich", 9, 0.6f, tips(null, "pork_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item PORK_MEATBALL_STICK_1 = stickFood("pork_meatball_stick_1", 4, 0.8f, false, fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item PORK_MEATBALL_STICK_2 = stickFood("pork_meatball_stick_2", 5, 0.8f, false, fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item PORK_MEATBALL_STICK_3 = stickFood("pork_meatball_stick_3", 6, 0.9f, false, fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item PORK_STEW_BOWL = bowlFood("pork_stew_bowl", 12, 0.9f, true, fx(ModEffectCategories.COMFORT, 3600));
    public static final Item PORK_TACO = food("pork_taco", 10, 0.5f, tips(null, "pork_ingredient"));
    public static final Item PORK_TACO_LETTUCE = food("pork_taco_lettuce", 11, 0.6f, tips(null, "pork_ingredient", "lettuce_ingredient"));
    public static final Item PORK_TACO_LETTUCE_TACO_SAUCE = food("pork_taco_lettuce_taco_sauce", 12, 0.7f, tips(null, "pork_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item PORK_WRAP_ONION_LETTUCE = food("pork_wrap_onion_lettuce", 11, 0.6f, tips(null, "pork_ingredient", "onion_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.REPULSION, 300));
    public static final Item POTATO_CHIPS = fastFood("potato_chips", 2, 0.2f, fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.LUCK, 600));
    public static final Item POTATO_CHIP_BOWL = bowlFood("potato_chip_bowl", 4, 0.5f, fx(ModEffectCategories.COMFORT, 1200), fx(MobEffects.LUCK, 1200));
    public static final Item POTATO_CREAM_SOUP_BOWL = bowlFood("potato_cream_soup_bowl", 8, 0.9f, true, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item POTATO_CREAM_SOUP_BOWL_CHEESE = bowlFood("potato_cream_soup_bowl_cheese", 9, 0.9f, tips(null, "cheese_ingredient"), fx(ModEffectCategories.COMFORT, 1800));
    public static final Item POWDERED_SUGAR = plain("powdered_sugar");
    public static final Item PRESSED_COCOA = plain("pressed_cocoa");
    public static final Item PRETZEL_STICK = fastFood("pretzel_stick", 2, 0.5f, fx(ModEffectCategories.COMFORT, 300));
    public static final Item PUMPERNICKEL_BREAD = food("pumpernickel_bread", 5, 1.1f, fx(ModEffectCategories.VITALITY, 6000));
    public static final Item PUMPERNICKEL_BREAD_SLICE = fastFood("pumpernickel_bread_slice", 3, 0.5f, fx(ModEffectCategories.VITALITY, 3000));
    public static final Item PUMPERNICKEL_DOUGH = plain("pumpernickel_dough");
    public static final Item PUMPERNICKEL_TOAST_SLICE = fastFood("pumpernickel_toast_slice", 6, 1.3f, fx(ModEffectCategories.VITALITY, 4800));
    public static final Item PUMPKIN_CUSTARD_BOTTLE = bottle("pumpkin_custard_bottle", 10, 0.6f);
    public static final Item PUMPKIN_PIE_SLICE = food("pumpkin_pie_slice", 3, 1.0f);
    public static final Item PUMPKIN_PUREE_BOTTLE = ingredientBottle("pumpkin_puree_bottle");
    public static final Item PURPLE_GELATIN_DESSERT_SLICE = fastFood("purple_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item RABBIT_BURRITO_RICE = food("rabbit_burrito_rice", 10, 0.6f, tips(null, "rabbit_ingredient", "rice_ingredient"), fx(ModEffectCategories.SUSTENANCE, 3600));
    public static final Item RABBIT_CALZONE = fastFood("rabbit_calzone", 6, 0.6f, tips(null, "cheese_ingredient", "rabbit_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item RABBIT_CUTS = plain("rabbit_cuts");
    public static final Item RABBIT_JERKY = food("rabbit_jerky", 2, 1.6f, fx(MobEffects.MOVEMENT_SPEED, 1200), fx(MobEffects.DIG_SPEED, 1200), fx(ModEffectCategories.VIGOR, 6000));
    public static final Item RABBIT_MEATBALL = food("rabbit_meatball", 3, 0.6f);
    public static final Item RABBIT_MEATBALL_SANDWICH = food("rabbit_meatball_sandwich", 9, 0.4f, tips(null, "rabbit_meatballs_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(MobEffects.MOVEMENT_SPEED, 1200), fx(MobEffects.DIG_SPEED, 1200), fx(ModEffectCategories.VIGOR, 3600));
    public static final Item RABBIT_MEATBALL_STICK_1 = stickFood("rabbit_meatball_stick_1", 4, 0.6f, false, fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item RABBIT_MEATBALL_STICK_2 = stickFood("rabbit_meatball_stick_2", 5, 0.6f, false, fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item RABBIT_MEATBALL_STICK_3 = stickFood("rabbit_meatball_stick_3", 6, 0.7f, false, fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item RABBIT_TACO = food("rabbit_taco", 8, 0.5f, tips(null, "rabbit_ingredient"));
    public static final Item RABBIT_TACO_LETTUCE = food("rabbit_taco_lettuce", 9, 0.5f, tips(null, "rabbit_ingredient", "lettuce_ingredient"));
    public static final Item RABBIT_TACO_LETTUCE_TACO_SAUCE = food("rabbit_taco_lettuce_taco_sauce", 10, 0.7f, tips(null, "rabbit_ingredient", "lettuce_ingredient", "taco_sauce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item RABBIT_WRAP_ONION_POTATO = food("rabbit_wrap_onion_potato", 10, 0.6f, tips(null, "rabbit_ingredient", "onion_ingredient", "potato_ingredient"), fx(ModEffectCategories.REPULSION, 300));
    public static final Item RAW_BACON_CALZONE = plain("raw_bacon_calzone", null, "cheese_ingredient", "bacon_ingredient");
    public static final Item RAW_BEEF_CALZONE = plain("raw_beef_calzone", null, "cheese_ingredient", "beef_ingredient");
    public static final Item RAW_BEEF_MEATBALL = plain("raw_beef_meatball");
    public static final Item RAW_BERRY_COOKIE = plain("raw_berry_cookie", null, "berry_ingredient");
    public static final Item RAW_BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE = plain("raw_butterscotch_chip_chocolate_cookie", null, "butterscotch_chips_ingredient");
    public static final Item RAW_BUTTERSCOTCH_CHIP_COOKIE = plain("raw_butterscotch_chip_cookie", null, "butterscotch_chips_ingredient");
    public static final Item RAW_CALZONE = plain("raw_calzone");
    public static final Item RAW_CARAMEL_CHIP_CHOCOLATE_COOKIE = plain("raw_caramel_chip_chocolate_cookie", null, "caramel_chips_ingredient");
    public static final Item RAW_CARAMEL_CHIP_COOKIE = plain("raw_caramel_chip_cookie", null, "caramel_chips_ingredient");
    public static final Item RAW_CHEESE_CALZONE = plain("raw_cheese_calzone", null, "cheese_ingredient");
    public static final Item RAW_CHICKEN_CALZONE = plain("raw_chicken_calzone", null, "cheese_ingredient", "chicken_ingredient");
    public static final Item RAW_CHICKEN_PATTY = plain("raw_chicken_patty");
    public static final Item RAW_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = plain("raw_chocolate_chip_chocolate_cookie", null, "chocolate_chips_ingredient");
    public static final Item RAW_CHOCOLATE_CHIP_COOKIE = plain("raw_chocolate_chip_cookie", null, "chocolate_chips_ingredient");
    public static final Item RAW_CHOCOLATE_PASTRY_BASE = plain("raw_chocolate_pastry_base");
    public static final Item RAW_CHOCOLATE_SWEET_ROLL_BASE = plain("raw_chocolate_sweet_roll_base");
    public static final Item RAW_CHORUS_FRUIT_COOKIE = plain("raw_chorus_fruit_cookie", null, "chorus_fruit_ingredient");
    public static final Item RAW_CINNAMON_SWEET_ROLL_BASE = plain("raw_cinnamon_sweet_roll_base", "cinnamon");
    public static final Item RAW_DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = plain("raw_dark_chocolate_chip_chocolate_cookie", null, "dark_chocolate_chips_ingredient");
    public static final Item RAW_DARK_CHOCOLATE_CHIP_COOKIE = plain("raw_dark_chocolate_chip_cookie", null, "dark_chocolate_chips_ingredient");
    public static final Item RAW_ENDERMITE_MEATBALL = plain("raw_endermite_meatball", "endermite_meat");
    public static final Item RAW_FISHCAKE = plain("raw_fishcake");
    public static final Item RAW_FISH_CALZONE = plain("raw_fish_calzone", null, "cheese_ingredient", "fish_ingredient");
    public static final Item RAW_FISH_STICKS = plain("raw_fish_sticks");
    public static final Item RAW_FLESH_COOKIE = plain("raw_flesh_cookie", "raw_flesh_cookie", "flesh_ingredient");
    public static final Item RAW_GINGER_COOKIE = plain("raw_ginger_cookie", "raw_ginger_cookie", "ginger_ingredient");
    public static final Item RAW_GLOW_BERRY_COOKIE = plain("raw_glow_berry_cookie", null, "glow_berry_ingredient");
    public static final Item RAW_GREEN_TEA_COOKIE = plain("raw_green_tea_cookie", "raw_green_tea_cookie");
    public static final Item RAW_GYRO_MEAT_BLOCK = plain("raw_gyro_meat_block");
    public static final Item RAW_HONEY_COOKIE = plain("raw_honey_cookie");
    public static final Item RAW_MACARONI = plain("raw_macaroni");
    public static final Item RAW_MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = plain("raw_mini_chocolate_graham_cracker_pie_crust");
    public static final Item RAW_MINI_CHOCOLATE_PIE_GRAHAM_CRACKER = plain("raw_mini_chocolate_pie_graham_cracker", null, "graham_cracker_pie_crust_ingredient");
    public static final Item RAW_MINI_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = plain("raw_mini_cream_pie_chocolate_graham_cracker", null, "chocolate_graham_cracker_pie_crust_ingredient");
    public static final Item RAW_MINI_CREAM_PIE_GRAHAM_CRACKER = plain("raw_mini_cream_pie_graham_cracker", null, "graham_cracker_pie_crust_ingredient");
    public static final Item RAW_MINI_GRAHAM_CRACKER_PIE_CRUST = plain("raw_mini_graham_cracker_pie_crust");
    public static final Item RAW_MOZZARELLA_STICKS = plain("raw_mozzarella_sticks");
    public static final Item RAW_MUSHROOM_CALZONE = plain("raw_mushroom_calzone", null, "cheese_ingredient", "mushroom_ingredient");
    public static final Item RAW_MUTTON_CALZONE = plain("raw_mutton_calzone", null, "cheese_ingredient", "mutton_ingredient");
    public static final Item RAW_ONION_CALZONE = plain("raw_onion_calzone", null, "cheese_ingredient", "onion_ingredient");
    public static final Item RAW_ONION_RINGS = plain("raw_onion_rings");
    public static final Item RAW_PASTRY_BASE = plain("raw_pastry_base");
    public static final Item RAW_PORK_MEATBALL = plain("raw_pork_meatball");
    public static final Item RAW_PRETZEL_STICK = plain("raw_pretzel_stick");
    public static final Item RAW_RABBIT_CALZONE = plain("raw_rabbit_calzone", null, "cheese_ingredient", "rabbit_ingredient");
    public static final Item RAW_RABBIT_MEATBALL = plain("raw_rabbit_meatball");
    public static final Item RAW_SAUSAGES = plain("raw_sausages");
    public static final Item RAW_SAUSAGE_CALZONE = plain("raw_sausage_calzone", null, "cheese_ingredient", "sausage_ingredient");
    public static final Item RAW_SAUSAGE_PATTY = plain("raw_sausage_patty");
    public static final Item RAW_SAUSAGE_ROLL = plain("raw_sausage_roll");
    public static final Item RAW_SAUSAGE_ROLL_CHEESE = plain("raw_sausage_roll_cheese", null, "cheese_ingredient");
    public static final Item RAW_SCONE = plain("raw_scone");
    public static final Item RAW_SNICKERDOODLE = plain("raw_snickerdoodle", "raw_snickerdoodle");
    public static final Item RAW_SOUL_BERRY_COOKIE = plain("raw_soul_berry_cookie", "raw_flesh_cookie", "soul_berry_ingredient");
    public static final Item RAW_SPICY_SAUSAGES = plain("raw_spicy_sausages");
    public static final Item RAW_SPICY_SAUSAGE_ROLL = plain("raw_spicy_sausage_roll");
    public static final Item RAW_SPICY_SAUSAGE_ROLL_CHEESE = plain("raw_spicy_sausage_roll_cheese", null, "cheese_ingredient");
    public static final Item RAW_SPIDER_EYE_COOKIE = plain("raw_spider_eye_cookie", "raw_flesh_cookie", "spider_eye_ingredient");
    public static final Item RAW_STRIDER_MEATBALL = plain("raw_strider_meatball", "strider_meat");
    public static final Item RAW_SUGAR_COOKIE = plain("raw_sugar_cookie", "raw_sugar_cookie");
    public static final Item RAW_SWEET_ROLL_BASE = plain("raw_sweet_roll_base");
    public static final Item RAW_TATER_TOTS = plain("raw_tater_tots");
    public static final Item RAW_TOFFEE_CHIP_CHOCOLATE_COOKIE = plain("raw_toffee_chip_chocolate_cookie", null, "toffee_chips_ingredient");
    public static final Item RAW_TOFFEE_CHIP_COOKIE = plain("raw_toffee_chip_cookie", null, "toffee_chips_ingredient");
    public static final Item RAW_UBE_COOKIE = plain("raw_ube_cookie", "ube");
    public static final Item RAW_WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = plain("raw_white_chocolate_chip_chocolate_cookie", null, "white_chocolate_chips_ingredient");
    public static final Item RAW_WHITE_CHOCOLATE_CHIP_COOKIE = plain("raw_white_chocolate_chip_cookie", null, "white_chocolate_chips_ingredient");
    public static final Item RED_GELATIN_DESSERT_SLICE = fastFood("red_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item RICE_PUDDING_BOWL = bowlFood("rice_pudding_bowl", 6, 0.8f, true, fx(ModEffectCategories.COMFORT, 1200), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.RESTED, 3600), fx(ModEffectCategories.BALANCED, 1200));
    public static final Item SALT = plain("salt");
    public static final Item SALT_DOUGH = plain("salt_dough");
    public static final Item SALT_DOUGH_SMALL = plain("salt_dough_small");
    public static final Item SAUSAGES = food("sausages", 6, 0.4f, fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item SAUSAGE_BACON_PIZZA_SLICE = food("sausage_bacon_pizza_slice", 6, 0.8f, tips(null, "sausage_ingredient", "bacon_ingredient"));
    public static final Item SAUSAGE_BISCUIT = food("sausage_biscuit", 12, 0.4f);
    public static final Item SAUSAGE_BISCUIT_BACON = food("sausage_biscuit_bacon", 13, 0.6f, tips(null, "bacon_ingredient"));
    public static final Item SAUSAGE_BISCUIT_CHEESE = food("sausage_biscuit_cheese", 14, 0.4f, tips(null, "cheese_ingredient"));
    public static final Item SAUSAGE_BISCUIT_CHEESE_BACON = food("sausage_biscuit_cheese_bacon", 15, 0.4f, tips(null, "cheese_ingredient", "bacon_ingredient"));
    public static final Item SAUSAGE_BISCUIT_CHEESE_FRIED_EGG = food("sausage_biscuit_cheese_fried_egg", 14, 0.6f, tips(null, "cheese_ingredient", "fried_egg_ingredient"));
    public static final Item SAUSAGE_BISCUIT_CHEESE_FRIED_EGG_BACON = food("sausage_biscuit_cheese_fried_egg_bacon", 16, 0.6f, tips(null, "cheese_ingredient", "fried_egg_ingredient", "bacon_ingredient"));
    public static final Item SAUSAGE_BISCUIT_FRIED_EGG = food("sausage_biscuit_fried_egg", 12, 0.8f, tips(null, "fried_egg_ingredient"));
    public static final Item SAUSAGE_BISCUIT_FRIED_EGG_BACON = food("sausage_biscuit_fried_egg_bacon", 14, 0.8f, tips(null, "fried_egg_ingredient", "bacon_ingredient"));
    public static final Item SAUSAGE_BISCUIT_SANDWICH = food("sausage_biscuit_sandwich", 17, 0.5f, fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATED_SHIELD, 3600));
    public static final Item SAUSAGE_BISCUIT_SANDWICH_BACON = food("sausage_biscuit_sandwich_bacon", 18, 0.6f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATED_SHIELD, 3600));
    public static final Item SAUSAGE_BISCUIT_SANDWICH_CHEESE = food("sausage_biscuit_sandwich_cheese", 19, 0.6f, tips(null, "cheese_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATED_SHIELD, 3600));
    public static final Item SAUSAGE_BISCUIT_SANDWICH_CHEESE_BACON = food("sausage_biscuit_sandwich_cheese_bacon", 20, 0.7f, tips(null, "cheese_ingredient", "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATED_SHIELD, 3600));
    public static final Item SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG = food("sausage_biscuit_sandwich_cheese_fried_egg", 19, 0.7f, tips(null, "cheese_ingredient", "fried_egg_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATED_SHIELD, 3600));
    public static final Item SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG_BACON = food("sausage_biscuit_sandwich_cheese_fried_egg_bacon", 21, 0.7f, tips(null, "cheese_ingredient", "fried_egg_ingredient", "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATED_SHIELD, 3600));
    public static final Item SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG = food("sausage_biscuit_sandwich_fried_egg", 17, 0.6f, tips(null, "fried_egg_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATED_SHIELD, 3600));
    public static final Item SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG_BACON = food("sausage_biscuit_sandwich_fried_egg_bacon", 19, 0.6f, tips(null, "fried_egg_ingredient", "bacon_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(ModEffectCategories.SATIATED_SHIELD, 3600));
    public static final Item SAUSAGE_BITS = food("sausage_bits", 2, 0.2f);
    public static final Item SAUSAGE_CALZONE = fastFood("sausage_calzone", 7, 0.5f, tips(null, "cheese_ingredient", "sausage_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item SAUSAGE_FISH_PIZZA_SLICE = food("sausage_fish_pizza_slice", 7, 0.8f, tips(null, "sausage_ingredient", "fish_ingredient"));
    public static final Item SAUSAGE_MUSHROOM_PIZZA_SLICE = food("sausage_mushroom_pizza_slice", 6, 0.8f, tips(null, "sausage_ingredient", "mushroom_ingredient"));
    public static final Item SAUSAGE_ONION_PIZZA_SLICE = food("sausage_onion_pizza_slice", 8, 0.6f, tips(null, "sausage_ingredient", "onion_ingredient"), fx(ModEffectCategories.REPULSION, 300));
    public static final Item SAUSAGE_PATTY = food("sausage_patty", 6, 0.3f);
    public static final Item SAUSAGE_PIZZA_SLICE = food("sausage_pizza_slice", 6, 0.5f, tips(null, "sausage_ingredient"));
    public static final Item SAUSAGE_ROLL = fastFood("sausage_roll", 4, 0.5f);
    public static final Item SAUSAGE_ROLL_CHEESE = fastFood("sausage_roll_cheese", 4, 0.6f, tips(null, "cheese_ingredient"));
    public static final Item SCONE = food("scone", 3, 0.5f);
    public static final Item SCONE_APPLE_JAM = food("scone_apple_jam", 5, 0.8f, tips(null, "apple_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item SCONE_BERRY_JAM = food("scone_berry_jam", 5, 0.8f, tips(null, "berry_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item SCONE_CHORUS_FRUIT_JAM = food("scone_chorus_fruit_jam", 5, 0.8f, tips(null, "chorus_fruit_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item SCONE_GLOW_BERRY_JAM = food("scone_glow_berry_jam", 5, 0.8f, tips(null, "glow_berry_jam_ingredient"), fx(MobEffects.NIGHT_VISION, 300), fx(ModEffectCategories.COMFORT, 600));
    public static final Item SCONE_MELON_JAM = food("scone_melon_jam", 5, 0.8f, tips(null, "melon_jam_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item SCRAMBLED_EGGS_PLATE = bowlFood("scrambled_eggs_plate", 6, 0.7f, fx(ModEffectCategories.VIGOR, 1200));
    public static final Item SCRAMBLED_EGGS_PLATE_BACON = bowlFood("scrambled_eggs_plate_bacon", 8, 0.8f, tips(null, "bacon_ingredient"), fx(ModEffectCategories.VIGOR, 1200));
    public static final Item SCRAMBLED_EGGS_PLATE_CHEESE = bowlFood("scrambled_eggs_plate_cheese", 7, 0.8f, tips(null, "cheese_ingredient"), fx(ModEffectCategories.VIGOR, 1200));
    public static final Item SCRAMBLED_EGGS_PLATE_MUSHROOM = bowlFood("scrambled_eggs_plate_mushroom", 7, 0.7f, tips(null, "mushroom_ingredient"), fx(ModEffectCategories.VIGOR, 1200));
    public static final Item SCRAMBLED_EGGS_PLATE_ONION = bowlFood("scrambled_eggs_plate_onion", 7, 0.7f, tips(null, "onion_ingredient"), fx(ModEffectCategories.VIGOR, 1200));
    public static final Item SCRAMBLED_EGGS_PLATE_SAUSAGE = bowlFood("scrambled_eggs_plate_sausage", 8, 0.7f, tips(null, "sausage_ingredient"), fx(ModEffectCategories.VIGOR, 1200));
    public static final Item SCRAMBLED_EGGS_PLATE_TOMATO = bowlFood("scrambled_eggs_plate_tomato", 7, 0.7f, tips(null, "tomato_ingredient"), fx(ModEffectCategories.VIGOR, 1200));
    public static final Item SCRAMBLED_EGG_SANDWICH = food("scrambled_egg_sandwich", 9, 0.9f, tips(null, "scrambled_egg_ingredient"), fx(ModEffectCategories.VIGOR, 2400));
    public static final Item SHREDDED_BEETROOT = plain("shredded_beetroot");
    public static final Item SHREDDED_CARROT = plain("shredded_carrot");
    public static final Item SHREDDED_POTATO = plain("shredded_potato");
    public static final Item SLICED_BEETROOT = fastFood("sliced_beetroot", 2, 0.3f);
    public static final Item SLICED_BROWN_MUSHROOM = fastFood("sliced_brown_mushroom", 1, 1.1f);
    public static final Item SLICED_BROWN_MUSHROOM_SALT = fastFood("sliced_brown_mushroom_salt", 1, 1.1f, tips(null, "salt_ingredient"), fx(MobEffects.NIGHT_VISION, 100), fx(ModEffectCategories.MINING, 600));
    public static final Item SLICED_CARROT = fastFood("sliced_carrot", 1, 0.4f);
    public static final Item SLICED_CRIMSON_FUNGUS = fastFood("sliced_crimson_fungus", 2, 0.9f);
    public static final Item SLICED_CRIMSON_FUNGUS_SALT = fastFood("sliced_crimson_fungus_salt", 2, 0.9f, tips(null, "salt_ingredient"), fx(MobEffects.NIGHT_VISION, 600), fx(ModEffectCategories.MINING, 1200));
    public static final Item SLICED_ONION = food("sliced_onion", 2, 0.4f);
    public static final Item SLICED_POTATO = fastFood("sliced_potato", 1, 0.6f);
    public static final Item SLICED_RED_MUSHROOM = fastFood("sliced_red_mushroom", 1, 1.1f);
    public static final Item SLICED_RED_MUSHROOM_SALT = fastFood("sliced_red_mushroom_salt", 1, 1.1f, tips(null, "salt_ingredient"), fx(MobEffects.NIGHT_VISION, 100), fx(ModEffectCategories.MINING, 600));
    public static final Item SLICED_TOMATO = fastFood("sliced_tomato", 1, 0.6f);
    public static final Item SLICED_WARPED_FUNGUS = fastFood("sliced_warped_fungus", 2, 0.9f);
    public static final Item SLICED_WARPED_FUNGUS_SALT = fastFood("sliced_warped_fungus_salt", 2, 0.9f, tips(null, "salt_ingredient"), fx(MobEffects.NIGHT_VISION, 600), fx(ModEffectCategories.MINING, 1200));
    public static final Item SMALL_BEEF_MEATBALLS = food("small_beef_meatballs", 3, 0.7f, fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item SMALL_ENDERMITE_MEATBALLS = food("small_endermite_meatballs", 3, 0.7f, tips("endermite_meat"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.REGENERATION, 600), fx(ModEffectCategories.SULFUR, 600));
    public static final Item SMALL_PORK_MEATBALLS = food("small_pork_meatballs", 2, 0.9f, fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item SMALL_RABBIT_MEATBALLS = food("small_rabbit_meatballs", 2, 0.7f, fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.MOVEMENT_SPEED, 600), fx(MobEffects.DIG_SPEED, 600));
    public static final Item SMALL_SLIMEBALLS = food("small_slimeballs", 3, 0.7f);
    public static final Item SMALL_STRIDER_MEATBALLS = food("small_strider_meatballs", 3, 0.7f, tips("strider_meat"), fx(MobEffects.FIRE_RESISTANCE, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item SMORE = food("smore", 7, 0.5f, fx(ModEffectCategories.COMFORT, 6000), fx(ModEffectCategories.WARMTH, 1200));
    public static final Item SMORES_PIE_SLICE = food("smores_pie_slice", 4, 0.8f, fx(ModEffectCategories.COMFORT, 1800), fx(ModEffectCategories.WARMTH, 1200));
    public static final Item SOUR_CREAM_BOTTLE = ingredientBottle("sour_cream_bottle");
    public static final Item SPICY_CHICKEN_NUGGETS = food("spicy_chicken_nuggets", 4, 1.1f, fx(MobEffects.FIRE_RESISTANCE, 600), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.COMBUSTION, 1200));
    public static final Item SPICY_SAUSAGES = food("spicy_sausages", 6, 0.8f, fx(MobEffects.FIRE_RESISTANCE, 600), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.COMBUSTION, 1200));
    public static final Item SPICY_SAUSAGE_ROLL = fastFood("spicy_sausage_roll", 5, 0.5f, fx(MobEffects.FIRE_RESISTANCE, 1200), fx(ModEffectCategories.COMBUSTION, 1200));
    public static final Item SPICY_SAUSAGE_ROLL_CHEESE = fastFood("spicy_sausage_roll_cheese", 5, 0.6f, tips(null, "cheese_ingredient"), fx(MobEffects.FIRE_RESISTANCE, 1200), fx(ModEffectCategories.COMBUSTION, 1200));
    public static final Item STRIDER_MEATBALL = food("strider_meatball", 4, 0.6f, tips("strider_meat"));
    public static final Item STRIDER_MEATBALL_SANDWICH = food("strider_meatball_sandwich", 10, 0.4f, tips("strider_meat", "strider_meatballs_ingredient"), fx(MobEffects.FIRE_RESISTANCE, 1200), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item STRIDER_MEATBALL_STICK_1 = stickFood("strider_meatball_stick_1", 5, 0.6f, false, tips("strider_meat"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.FIRE_RESISTANCE, 1200));
    public static final Item STRIDER_MEATBALL_STICK_2 = stickFood("strider_meatball_stick_2", 6, 0.6f, false, tips("strider_meat"), fx(MobEffects.FIRE_RESISTANCE, 3600), fx(ModEffectCategories.NOURISHMENT, 1200));
    public static final Item STRIDER_MEATBALL_STICK_3 = stickFood("strider_meatball_stick_3", 8, 0.7f, false, tips("strider_meat"), fx(MobEffects.FIRE_RESISTANCE, 6000), fx(ModEffectCategories.NOURISHMENT, 3600));
    public static final Item SUGAR_CANE_JUICE_BOTTLE = bottle("sugar_cane_juice_bottle", 3, 0.4f, fx(ModEffectCategories.SUGAR_RUSH, 300));
    public static final Item SUGAR_DOUGH = plain("sugar_dough");
    public static final Item SUGAR_DOUGH_SMALL = plain("sugar_dough_small");
    public static final Item SWEET_ROLL_BASE = food("sweet_roll_base", 4, 0.6f);
    public static final Item TACO_SAUCE_BOTTLE = ingredientBottle("taco_sauce_bottle");
    public static final Item TACO_SHELL = plain("taco_shell");
    public static final Item TATER_TOTS = food("tater_tots", 4, 0.6f);
    public static final Item TOAST_FRIED_EGG_PLATE = bowlFood("toast_fried_egg_plate", 8, 0.7f);
    public static final Item TOAST_PLATE = bowlFood("toast_plate", 3, 0.3f);
    public static final Item TOAST_SLICE = fastFood("toast_slice", 3, 0.3f);
    public static final Item TOFFEE = fastFood("toffee", 1, 1.8f);
    public static final Item TOFFEE_APPLE = food("toffee_apple", 8, 0.8f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item TOFFEE_APPLE_SLICE = fastFood("toffee_apple_slice", 4, 0.5f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 1800), fx(MobEffects.MOVEMENT_SPEED, 600), fx(ModEffectCategories.ANIMAL_CHARM, 1200));
    public static final Item TOFFEE_BERRIES = food("toffee_berries", 6, 0.9f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item TOFFEE_CHIPS = plain("toffee_chips");
    public static final Item TOFFEE_CHIP_CHOCOLATE_COOKIE = food("toffee_chip_chocolate_cookie", 3, 0.6f, tips(null, "toffee_chips_ingredient"));
    public static final Item TOFFEE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("toffee_chip_chocolate_milkshake_bottle", 6, 1.3f, tips(null, "toffee_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final Item TOFFEE_CHIP_COOKIE = food("toffee_chip_cookie", 2, 0.7f, tips(null, "toffee_chips_ingredient"));
    public static final Item TOFFEE_CHIP_ICE_CREAM_CONE = fastFood("toffee_chip_ice_cream_cone", 2, 1.2f, tips(null, "toffee_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item TOFFEE_CHIP_MILKSHAKE_BOTTLE = bottle("toffee_chip_milkshake_bottle", 5, 1.2f, tips(null, "toffee_chips_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final Item TOFFEE_CHIP_MINI_WAFFLE = fastFood("toffee_chip_mini_waffle", 3, 1.7f, tips(null, "toffee_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.MOVEMENT_SPEED, 600), fx(MobEffects.LUCK, 600));
    public static final Item TOFFEE_CHIP_MUFFIN = fastFood("toffee_chip_muffin", 3, 0.9f, tips(null, "toffee_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final Item TOFFEE_CHOCOLATE = food("toffee_chocolate", 7, 0.8f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item TOFFEE_CHOCOLATE_PASTRY = food("toffee_chocolate_pastry", 3, 1.1f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item TOFFEE_CHOCOLATE_SWEET_ROLL = food("toffee_chocolate_sweet_roll", 7, 0.8f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item TOFFEE_DARK_CHOCOLATE = food("toffee_dark_chocolate", 8, 0.7f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item TOFFEE_FUDGE = food("toffee_fudge", 4, 0.5f, fx(ModEffectCategories.COMFORT, 3600), fx(MobEffects.MOVEMENT_SPEED, 3600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item TOFFEE_MARSHMALLOW_STICK = stickFood("toffee_marshmallow_stick", 3, 0.9f, false, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600), fx(ModEffectCategories.WARMTH, 1200), fx(ModEffectCategories.SUGAR_RUSH, 300));
    public static final Item TOFFEE_PASTRY = food("toffee_pastry", 2, 1.1f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.MOVEMENT_SPEED, 600));
    public static final Item TOFFEE_PRETZEL_STICK = fastFood("toffee_pretzel_stick", 3, 0.8f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.DAMAGE_BOOST, 600));
    public static final Item TOFFEE_SWEET_ROLL = food("toffee_sweet_roll", 6, 0.7f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item TOFFEE_TOAST = food("toffee_toast", 3, 0.9f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item TOFFEE_WHITE_CHOCOLATE = food("toffee_white_chocolate", 6, 0.9f, tips(null, "toffee_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item TOMATO_CREAM_SOUP_BOWL = bowlFood("tomato_cream_soup_bowl", 7, 0.8f, true, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item TORTILLA_CHIP_BOWL = bowlFood("tortilla_chip_bowl", 4, 0.7f, tips("corn"));
    public static final Item TROPICAL_FISH_SLICE = fastFood("tropical_fish_slice", 1, 1.0f);
    public static final Item UBE_CREAM_FROSTING_BOTTLE = bottle("ube_cream_frosting_bottle", 5, 0.7f, tips("ube"));
    public static final Item UBE_CREAM_FROSTING_PIPING_BAG = pipingBag("ube_cream_frosting_piping_bag", "ube", "ube_cream_frosting_ingredient");
    public static final Item UBE_CREAM_UBE_CAKE_SLICE = food("ube_cream_ube_cake_slice", 3, 0.3f, tips("ube", "ube_cream_frosting_ingredient"));
    public static final Item UBE_CREAM_UBE_CUPCAKE = fastFood("ube_cream_ube_cupcake", 3, 1.0f, tips("ube", "ube_cream_frosting_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item UBE_CUPCAKE_BASE = fastFood("ube_cupcake_base", 2, 0.9f, tips("ube"));
    public static final Item UBE_SUGAR_DOUGH = plain("ube_sugar_dough", "ube");
    public static final Item UNBREADED_CHICKEN_PATTY = plain("unbreaded_chicken_patty");
    public static final Item VEGETABLE_SANDWICH_BEETROOT_LETTUCE = food("vegetable_sandwich_beetroot_lettuce", 9, 0.5f, tips(null, "beetroot_ingredient", "lettuce_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.FARMERS_BLESSING, 3600));
    public static final Item VEGETABLE_SANDWICH_LETTUCE_TOMATO = food("vegetable_sandwich_lettuce_tomato", 9, 0.7f, tips(null, "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.NOURISHMENT, 3600), fx(ModEffectCategories.FARMERS_BLESSING, 3600));
    public static final Item VEGETABLE_WRAP_ONION_LETTUCE_TOMATO = food("vegetable_wrap_onion_lettuce_tomato", 8, 0.7f, tips(null, "onion_ingredient", "lettuce_ingredient", "tomato_ingredient"), fx(ModEffectCategories.FARMERS_BLESSING, 3600));
    public static final Item VINEGAR_BOTTLE = ingredientBottle("vinegar_bottle");
    public static final Item WAFFLE_CONE = fastFood("waffle_cone", 2, 1.0f);
    public static final Item WHEAT_DOUGH_SMALL = plain("wheat_dough_small");
    public static final Item WHITE_CHOCOLATE_APPLE = food("white_chocolate_apple", 7, 0.9f, tips(null, "white_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item WHITE_CHOCOLATE_APPLE_SLICE = fastFood("white_chocolate_apple_slice", 4, 0.6f, tips(null, "white_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1800), fx(ModEffectCategories.ANIMAL_CHARM, 1200));
    public static final Item WHITE_CHOCOLATE_BERRIES = food("white_chocolate_berries", 6, 0.8f, tips(null, "white_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item WHITE_CHOCOLATE_BOTTLE = bottle("white_chocolate_bottle", 6, 0.5f);
    public static final Item WHITE_CHOCOLATE_CHIPS = plain("white_chocolate_chips");
    public static final Item WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = food("white_chocolate_chip_chocolate_cookie", 3, 0.5f, tips(null, "white_chocolate_chips_ingredient"));
    public static final Item WHITE_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = bottle("white_chocolate_chip_chocolate_milkshake_bottle", 6, 1.2f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1800));
    public static final Item WHITE_CHOCOLATE_CHIP_COOKIE = food("white_chocolate_chip_cookie", 2, 0.6f, tips(null, "white_chocolate_chips_ingredient"));
    public static final Item WHITE_CHOCOLATE_CHIP_ICE_CREAM_CONE = fastFood("white_chocolate_chip_ice_cream_cone", 2, 1.2f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.TUNDRA_STRIDER, 1800));
    public static final Item WHITE_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = bottle("white_chocolate_chip_milkshake_bottle", 5, 1.4f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffectCategories.COMFORT, 1800));
    public static final Item WHITE_CHOCOLATE_CHIP_MINI_WAFFLE = fastFood("white_chocolate_chip_mini_waffle", 3, 1.7f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(MobEffects.LUCK, 600));
    public static final Item WHITE_CHOCOLATE_CHIP_MUFFIN = fastFood("white_chocolate_chip_muffin", 4, 0.9f, tips(null, "white_chocolate_chips_ingredient"), fx(ModEffectCategories.NOURISHMENT, 1200), fx(ModEffectCategories.COMFORT, 600));
    public static final Item WHITE_CHOCOLATE_CHOCOLATE_PASTRY = food("white_chocolate_chocolate_pastry", 2, 1.2f, tips(null, "white_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 3600));
    public static final Item WHITE_CHOCOLATE_FUDGE = food("white_chocolate_fudge", 2, 0.7f, fx(ModEffectCategories.COMFORT, 3600), fx(ModEffectCategories.SUGAR_RUSH, 1200));
    public static final Item WHITE_CHOCOLATE_MARSHMALLOW_STICK = stickFood("white_chocolate_marshmallow_stick", 3, 0.4f, false, tips(null, "white_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.WARMTH, 1200), fx(ModEffectCategories.SUGAR_RUSH, 300));
    public static final Item WHITE_CHOCOLATE_PASTRY = food("white_chocolate_pastry", 1, 1.2f, tips(null, "white_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 1200));
    public static final Item WHITE_CHOCOLATE_PRETZEL_STICK = fastFood("white_chocolate_pretzel_stick", 3, 0.7f, tips(null, "white_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(ModEffectCategories.NOURISHMENT, 600));
    public static final Item WHITE_CHOCOLATE_TOAST = food("white_chocolate_toast", 3, 0.7f, tips(null, "white_chocolate_ingredient"), fx(ModEffectCategories.COMFORT, 600));
    public static final Item YELLOW_GELATIN_DESSERT_SLICE = fastFood("yellow_gelatin_dessert_slice", 5, 0.3f, fx(ModEffectCategories.COMFORT, 1200));
    public static final Item YOGURT_BOTTLE = bottle("yogurt_bottle", 2, 0.9f, fx(ModEffectCategories.RESTED, 1200), fx(ModEffectCategories.PRESERVATION, 6000));
    public static final Item YOGURT_BOWL = bowlFood("yogurt_bowl", 4, 0.6f, fx(ModEffectCategories.RESTED, 3600), fx(ModEffectCategories.PRESERVATION, 1200));
    public static final Item YOGURT_BOWL_BERRY = bowlFood("yogurt_bowl_berry", 5, 0.8f, tips(null, "berry_ingredient"), fx(ModEffectCategories.COMFORT, 300), fx(ModEffectCategories.RESTED, 3600), fx(ModEffectCategories.PRESERVATION, 1200));
    public static final Item YOGURT_BOWL_CHORUS_FRUIT = bowlFood("yogurt_bowl_chorus_fruit", 6, 0.7f, tips(null, "chorus_fruit_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.SLOW_FALLING, 600), fx(ModEffectCategories.RESTED, 3600), fx(ModEffectCategories.PRESERVATION, 1200));
    public static final Item YOGURT_BOWL_GLOW_BERRY = bowlFood("yogurt_bowl_glow_berry", 5, 0.7f, tips(null, "glow_berry_ingredient"), fx(ModEffectCategories.COMFORT, 300), fx(ModEffectCategories.RESTED, 3600), fx(ModEffectCategories.PRESERVATION, 1200));
    public static final Item YOGURT_BOWL_HONEY = bowlFood("yogurt_bowl_honey", 4, 0.8f, tips(null, "honey_ingredient"), fx(ModEffectCategories.COMFORT, 600), fx(MobEffects.REGENERATION, 600), fx(MobEffects.LUCK, 600), fx(ModEffectCategories.RESTED, 3600), fx(ModEffectCategories.PRESERVATION, 1200));

    public static void registerConfigItems() {
        var configFile = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve("createfood-client.toml");
        if (!java.nio.file.Files.exists(configFile)) return;
        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("items.item", List.of());
            for (String entry : entries) {
                String[] p = entry.split("\\|");
                if (p.length < 2) {
                    CreateFood.LOGGER.warn("Create: Food - Skipping invalid custom_item entry: {}", entry);
                    continue;
                }
                String name = p[0];
                String type = p[1].toLowerCase();
                Item item = switch (type) {
                    case "plain"             -> new Item(new Item.Properties());
                    case "ingredient_bottle" -> new EffectDrink(new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE));
                    case "ingredient_bowl"   -> new Item(new Item.Properties().stacksTo(16).craftRemainder(Items.BOWL));
                    case "piping_bag"        -> new Item(new Item.Properties().stacksTo(2).craftRemainder(PIPING_BAG));
                    default -> {
                        if (p.length < 4) {
                            CreateFood.LOGGER.warn("Create: Food - Skipping invalid custom_item entry: {}", entry);
                            yield null;
                        }
                        int nut; float sat;
                        try {
                            nut = Integer.parseInt(p[2]);
                            sat = Float.parseFloat(p[3]);
                        } catch (NumberFormatException e) {
                            CreateFood.LOGGER.warn("Create: Food - Invalid nutrition/saturation in custom_item entry: {}", entry);
                            yield null;
                        }
                        var food = new FoodProperties.Builder().nutrition(nut).saturationModifier(sat);
                        yield switch (type) {
                            case "food"     -> new EffectFood(new Item.Properties().food(food.build()));
                            case "fast_food"-> { food.fast(); yield new EffectFood(new Item.Properties().food(food.build())); }
                            case "bowl"     -> { food.usingConvertsTo(Items.BOWL); yield new EffectFood(new Item.Properties().food(food.build()).stacksTo(16)); }
                            case "bowl_cr"  -> { food.usingConvertsTo(Items.BOWL); yield new EffectFood(new Item.Properties().food(food.build()).stacksTo(16).craftRemainder(Items.BOWL)); }
                            case "bottle"   -> { food.usingConvertsTo(Items.GLASS_BOTTLE); yield new EffectDrink(new Item.Properties().food(food.build()).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)); }
                            case "stick"    -> { food.fast(); food.usingConvertsTo(Items.STICK); yield new EffectFood(new Item.Properties().food(food.build())); }
                            case "stick_cr" -> { food.fast(); food.usingConvertsTo(Items.STICK); yield new EffectFood(new Item.Properties().food(food.build()).craftRemainder(Items.STICK)); }
                            default -> { CreateFood.LOGGER.warn("Create: Food - Unknown type '{}' in custom_item entry: {}", type, entry); yield null; }
                        };
                    }
                };
                if (item == null) continue;
                Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), item);
            }
        } catch (Exception e) {
            CreateFood.LOGGER.warn("Create: Food - Failed to read custom_item from config", e);
        }
    }

    public static void registerModItems() {
        registerConfigItems();
    }
}
