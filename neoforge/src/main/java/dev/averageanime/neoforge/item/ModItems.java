package dev.averageanime.neoforge.item;

import dev.averageanime.CommonClass;
import dev.averageanime.item.BottleItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.registry.ModEffects;

import static dev.averageanime.neoforge.CreateFood.LOGGER;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CommonClass.ID);

// Food //

    public static final DeferredItem<Item> BOILED_EGG_PEELED = ITEMS.register("boiled_egg_peeled",
            () -> new Item(new Item.Properties().food(ModItems.BOILED_EGG_PEELED_FOOD).stacksTo(16)));
    public static final FoodProperties BOILED_EGG_PEELED_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.1f).fast()
            .build();

    public static final DeferredItem<Item> EGGSHELL = ITEMS.register("eggshell",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BROWN_SUGAR = ITEMS.register("brown_sugar",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BOILED_EGG = ITEMS.register("boiled_egg",
            () -> new Item(new Item.Properties().stacksTo(16).craftRemainder(ModItems.EGGSHELL.get())));

    public static final DeferredItem<Item> BUTTERSCOTCH_CHIPS = ITEMS.register("butterscotch_chips",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CARAMEL_CHIPS = ITEMS.register("caramel_chips",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHEESE_BLOCK = ITEMS.register("cheese_block",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHOCOLATE_CHIPS = ITEMS.register("chocolate_chips",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_CRUMBS = ITEMS.register("chocolate_graham_cracker_crumbs",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COOKIE_CRUMBS = ITEMS.register("cookie_crumbs",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CORN_FLOUR = ITEMS.register("corn_flour",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CREAM_CHEESE = ITEMS.register("cream_cheese",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DICED_ONION = ITEMS.register("diced_onion",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DICED_TOMATO = ITEMS.register("diced_tomato",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHOCOLATE_SUGAR_DOUGH = ITEMS.register("chocolate_sugar_dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHOCOLATE_SUGAR_DOUGH_SMALL = ITEMS.register("chocolate_sugar_dough_small",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIPS = ITEMS.register("dark_chocolate_chips",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CLOTH_FILTER = ITEMS.register("cloth_filter",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COCOA_POWDER = ITEMS.register("cocoa_powder",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CONDENSED_MILK_BOTTLE = ITEMS.register("condensed_milk_bottle",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DRIED_COFFEE_BEANS = ITEMS.register("dried_coffee_beans",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DUMPLING_WRAPPERS = ITEMS.register("dumpling_wrappers",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> EGG_POWDER = ITEMS.register("egg_powder",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ESPRESSO_POWDER = ITEMS.register("espresso_powder",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GELATIN = ITEMS.register("gelatin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GROUND_BEEF = ITEMS.register("ground_beef",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GROUND_MUTTON = ITEMS.register("ground_mutton",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GROUND_PORK = ITEMS.register("ground_pork",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GROUND_RABBIT = ITEMS.register("ground_rabbit",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GROUND_ENDERMITE = ITEMS.register("ground_endermite",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GRAHAM_CRACKER_CRUMBS = ITEMS.register("graham_cracker_crumbs",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_BACON_CALZONE = ITEMS.register("raw_bacon_calzone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE = ITEMS.register("raw_butterscotch_chip_chocolate_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_BUTTERSCOTCH_CHIP_COOKIE = ITEMS.register("raw_butterscotch_chip_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_BUTTERSCOTCH_CHIP_MUFFIN = ITEMS.register("raw_butterscotch_chip_muffin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CAKE_BASE = ITEMS.register("raw_cake_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CALZONE = ITEMS.register("raw_calzone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CARAMEL_CHIP_CHOCOLATE_COOKIE = ITEMS.register("raw_caramel_chip_chocolate_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CARAMEL_CHIP_COOKIE = ITEMS.register("raw_caramel_chip_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CARAMEL_CHIP_MUFFIN = ITEMS.register("raw_caramel_chip_muffin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CHEESE_CALZONE = ITEMS.register("raw_cheese_calzone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CHOCOLATE_CHIP_MUFFIN = ITEMS.register("raw_chocolate_chip_muffin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = ITEMS.register("raw_chocolate_chip_chocolate_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CHOCOLATE_CUPCAKE_BASE = ITEMS.register("raw_chocolate_cupcake_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CHOCOLATE_PASTRY_BASE = ITEMS.register("raw_chocolate_pastry_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CHOCOLATE_SWEET_ROLL_BASE = ITEMS.register("raw_chocolate_sweet_roll_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CHORUS_COOKIE = ITEMS.register("raw_chorus_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CINNAMON_SWEET_ROLL_BASE = ITEMS.register("raw_cinnamon_sweet_roll_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CHOCOLATE_CHIP_COOKIE = ITEMS.register("raw_chocolate_chip_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CUPCAKE_BASE = ITEMS.register("raw_cupcake_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = ITEMS.register("raw_dark_chocolate_chip_chocolate_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_DARK_CHOCOLATE_CHIP_COOKIE = ITEMS.register("raw_dark_chocolate_chip_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_DARK_CHOCOLATE_CHIP_MUFFIN = ITEMS.register("raw_dark_chocolate_chip_muffin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_ENDERMITE_MEATBALL = ITEMS.register("raw_endermite_meatball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_FISHCAKE = ITEMS.register("raw_fishcake",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_FISH_CALZONE = ITEMS.register("raw_fish_calzone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_FLESH_COOKIE = ITEMS.register("raw_flesh_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_GINGER_COOKIE = ITEMS.register("raw_ginger_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_GREEN_TEA_COOKIE = ITEMS.register("raw_green_tea_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_GYRO_MEAT_BLOCK = ITEMS.register("raw_gyro_meat_block",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_HONEY_COOKIE = ITEMS.register("raw_honey_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MACARONI = ITEMS.register("raw_macaroni",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_BEEF_MEATBALL = ITEMS.register("raw_beef_meatball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = ITEMS.register("raw_mini_chocolate_graham_cracker_pie_crust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MINI_CHOCOLATE_PIE_GRAHAM_CRACKER = ITEMS.register("raw_mini_chocolate_pie_graham_cracker",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MINI_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = ITEMS.register("raw_mini_cream_pie_chocolate_graham_cracker",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MINI_CREAM_PIE_GRAHAM_CRACKER = ITEMS.register("raw_mini_cream_pie_graham_cracker",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MINI_GRAHAM_CRACKER_PIE_CRUST = ITEMS.register("raw_mini_graham_cracker_pie_crust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MUFFIN_BASE = ITEMS.register("raw_muffin_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MUSHROOM_CALZONE = ITEMS.register("raw_mushroom_calzone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_ONION_CALZONE = ITEMS.register("raw_onion_calzone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_PASTRY_BASE = ITEMS.register("raw_pastry_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_PORK_MEATBALL = ITEMS.register("raw_pork_meatball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_RABBIT_MEATBALL = ITEMS.register("raw_rabbit_meatball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SAUSAGE_CALZONE = ITEMS.register("raw_sausage_calzone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SAUSAGE_PATTY = ITEMS.register("raw_sausage_patty",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SNICKERDOODLE = ITEMS.register("raw_snickerdoodle",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SOUL_BERRY_COOKIE = ITEMS.register("raw_soul_berry_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SPIDER_EYE_COOKIE = ITEMS.register("raw_spider_eye_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_STRIDER_MEATBALL = ITEMS.register("raw_strider_meatball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SUGAR_COOKIE = ITEMS.register("raw_sugar_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SWEET_BERRY_COOKIE = ITEMS.register("raw_sweet_berry_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SWEET_ROLL_BASE = ITEMS.register("raw_sweet_roll_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_TOFFEE_CHIP_CHOCOLATE_COOKIE = ITEMS.register("raw_toffee_chip_chocolate_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_TOFFEE_CHIP_COOKIE = ITEMS.register("raw_toffee_chip_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_TOFFEE_CHIP_MUFFIN = ITEMS.register("raw_toffee_chip_muffin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_UBE_CAKE_BASE = ITEMS.register("raw_ube_cake_base",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_UBE_COOKIE = ITEMS.register("raw_ube_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = ITEMS.register("raw_white_chocolate_chip_chocolate_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_WHITE_CHOCOLATE_CHIP_COOKIE = ITEMS.register("raw_white_chocolate_chip_cookie",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_WHITE_CHOCOLATE_CHIP_MUFFIN = ITEMS.register("raw_white_chocolate_chip_muffin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MILK_POWDER = ITEMS.register("milk_powder",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MINCED_DRAGON = ITEMS.register("minced_dragon",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MOLASSES_BOTTLE = ITEMS.register("molasses_bottle",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PAPRIKA = ITEMS.register("paprika",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PITA_DOUGH = ITEMS.register("pita_dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> POWDERED_SUGAR = ITEMS.register("powdered_sugar",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PRESSED_COCOA = ITEMS.register("pressed_cocoa",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PUMPERNICKEL_DOUGH = ITEMS.register("pumpernickel_dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RABBIT_CUTS = ITEMS.register("rabbit_cuts",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SALT = ITEMS.register("salt",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SALT_DOUGH = ITEMS.register("salt_dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SALT_DOUGH_SMALL = ITEMS.register("salt_dough_small",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GROUND_SAUSAGE = ITEMS.register("ground_sausage",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SHREDDED_BEETROOT = ITEMS.register("shredded_beetroot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SHREDDED_CARROT = ITEMS.register("shredded_carrot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GROUND_CHICKEN = ITEMS.register("ground_chicken",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SHREDDED_POTATO = ITEMS.register("shredded_potato",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SUGAR_DOUGH = ITEMS.register("sugar_dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SUGAR_DOUGH_SMALL = ITEMS.register("sugar_dough_small",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TOFFEE_CHIPS = ITEMS.register("toffee_chips",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UBE_SUGAR_DOUGH = ITEMS.register("ube_sugar_dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNBREADED_CHICKEN_PATTY = ITEMS.register("unbreaded_chicken_patty",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> WHEAT_DOUGH_SMALL = ITEMS.register("wheat_dough_small",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIPS = ITEMS.register("white_chocolate_chips",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SWEET_ROLL_BASE = ITEMS.register("sweet_roll_base",
            () -> new Item(new Item.Properties().food(ModItems.SWEET_ROLL_BASE_FOOD)));
    public static final FoodProperties SWEET_ROLL_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> TOAST_SLICE = ITEMS.register("toast_slice",
            () -> new Item(new Item.Properties().food(ModItems.TOAST_SLICE_FOOD)));
    public static final FoodProperties TOAST_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f).fast()
            .build();

    public static final DeferredItem<Item> TOFFEE = ITEMS.register("toffee",
            () -> new Item(new Item.Properties().food(ModItems.TOFFEE_FOOD)));
    public static final FoodProperties TOFFEE_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.8f).fast()
            .build();

    public static final DeferredItem<Item> TOFFEE_CHIP_CHOCOLATE_COOKIE = ITEMS.register("toffee_chip_chocolate_cookie",
            () -> new Item(new Item.Properties().food(ModItems.TOFFEE_CHIP_CHOCOLATE_COOKIE_FOOD)));
    public static final FoodProperties TOFFEE_CHIP_CHOCOLATE_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> TOFFEE_CHIP_COOKIE = ITEMS.register("toffee_chip_cookie",
            () -> new Item(new Item.Properties().food(ModItems.TOFFEE_CHIP_COOKIE_FOOD)));
    public static final FoodProperties TOFFEE_CHIP_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> TOFFEE_CHIP_ICE_CREAM_CONE = ITEMS.register("toffee_chip_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.TOFFEE_CHIP_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties TOFFEE_CHIP_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> TOFFEE_CHIP_MUFFIN = ITEMS.register("toffee_chip_muffin",
            () -> new Item(new Item.Properties().food(ModItems.TOFFEE_CHIP_MUFFIN_FOOD)));
    public static final FoodProperties TOFFEE_CHIP_MUFFIN_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> TOFFEE_CHIP_MINI_WAFFLE = ITEMS.register("toffee_chip_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.TOFFEE_CHIP_MINI_WAFFLE_FOOD)));
    public static final FoodProperties TOFFEE_CHIP_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> TOFFEE_FUDGE = ITEMS.register("toffee_fudge",
            () -> new Item(new Item.Properties().food(ModItems.TOFFEE_FUDGE_FOOD)));
    public static final FoodProperties TOFFEE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> VEGETABLE_SANDWICH_BEETROOT_LETTUCE = ITEMS.register("vegetable_sandwich_beetroot_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.VEGETABLE_SANDWICH_BEETROOT_LETTUCE_FOOD)));
    public static final FoodProperties VEGETABLE_SANDWICH_BEETROOT_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> VEGETABLE_SANDWICH_LETTUCE_TOMATO = ITEMS.register("vegetable_sandwich_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.VEGETABLE_SANDWICH_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties VEGETABLE_SANDWICH_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> WAFFLE_CONE = ITEMS.register("waffle_cone",
            () -> new Item(new Item.Properties().food(ModItems.WAFFLE_CONE_FOOD)));
    public static final FoodProperties WAFFLE_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.0f).fast()
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = ITEMS.register("white_chocolate_chip_chocolate_cookie",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_COOKIE = ITEMS.register("white_chocolate_chip_cookie",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_CHIP_COOKIE_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_CHIP_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_ICE_CREAM_CONE = ITEMS.register("white_chocolate_chip_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_CHIP_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_CHIP_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_MUFFIN = ITEMS.register("white_chocolate_chip_muffin",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_CHIP_MUFFIN_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_CHIP_MUFFIN_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_MINI_WAFFLE = ITEMS.register("white_chocolate_chip_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_CHIP_MINI_WAFFLE_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_CHIP_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHOCOLATE_PASTRY = ITEMS.register("white_chocolate_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_FUDGE = ITEMS.register("white_chocolate_fudge",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_FUDGE_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_APPLE = ITEMS.register("white_chocolate_apple",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_APPLE_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_APPLE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_BERRIES = ITEMS.register("white_chocolate_berries",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_BERRIES_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_BERRIES_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_PASTRY = ITEMS.register("white_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_TOAST = ITEMS.register("white_chocolate_toast",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_TOAST_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_TOAST_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> YELLOW_GELATIN_DESSERT_SLICE = ITEMS.register("yellow_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.YELLOW_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties YELLOW_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = ITEMS.register("mini_chocolate_graham_cracker_pie_crust",
            () -> new Item(new Item.Properties().food(ModItems.MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST_FOOD)));
    public static final FoodProperties MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> MINI_CHOCOLATE_PIE_GRAHAM_CRACKER = ITEMS.register("mini_chocolate_pie_graham_cracker",
            () -> new Item(new Item.Properties().food(ModItems.MINI_CHOCOLATE_PIE_GRAHAM_CRACKER_FOOD)));
    public static final FoodProperties MINI_CHOCOLATE_PIE_GRAHAM_CRACKER_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> MINI_COOKIE_CREAM_PIE = ITEMS.register("mini_cookie_cream_pie",
            () -> new Item(new Item.Properties().food(ModItems.MINI_COOKIE_CREAM_PIE_FOOD)));
    public static final FoodProperties MINI_COOKIE_CREAM_PIE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> MINI_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = ITEMS.register("mini_cream_pie_chocolate_graham_cracker",
            () -> new Item(new Item.Properties().food(ModItems.MINI_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_FOOD)));
    public static final FoodProperties MINI_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> MINI_CREAM_PIE_GRAHAM_CRACKER = ITEMS.register("mini_cream_pie_graham_cracker",
            () -> new Item(new Item.Properties().food(ModItems.MINI_CREAM_PIE_GRAHAM_CRACKER_FOOD)));
    public static final FoodProperties MINI_CREAM_PIE_GRAHAM_CRACKER_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> MINI_GRAHAM_CRACKER_PIE_CRUST = ITEMS.register("mini_graham_cracker_pie_crust",
            () -> new Item(new Item.Properties().food(ModItems.MINI_GRAHAM_CRACKER_PIE_CRUST_FOOD)));
    public static final FoodProperties MINI_GRAHAM_CRACKER_PIE_CRUST_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> MINI_SMORES_PIE = ITEMS.register("mini_smores_pie",
            () -> new Item(new Item.Properties().food(ModItems.MINI_SMORES_PIE_FOOD)));
    public static final FoodProperties MINI_SMORES_PIE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MINI_WAFFLE = ITEMS.register("mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.MINI_WAFFLE_FOOD)));
    public static final FoodProperties MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.6f).fast()
            .build();

    public static final DeferredItem<Item> MIXED_SALAD_BEETROOT_CARROT = ITEMS.register("mixed_salad_beetroot_carrot",
            () -> new Item(new Item.Properties().food(ModItems.MIXED_SALAD_BEETROOT_CARROT_FOOD)));
    public static final FoodProperties MIXED_SALAD_BEETROOT_CARROT_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MUFFIN_BASE = ITEMS.register("muffin_base",
            () -> new Item(new Item.Properties().food(ModItems.MUFFIN_BASE_FOOD)));
    public static final FoodProperties MUFFIN_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).fast()
            .build();

    public static final DeferredItem<Item> MUSHROOM_BACON_PIZZA_SLICE = ITEMS.register("mushroom_bacon_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.MUSHROOM_BACON_PIZZA_SLICE_FOOD)));
    public static final FoodProperties MUSHROOM_BACON_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> MUSHROOM_CALZONE = ITEMS.register("mushroom_calzone",
            () -> new Item(new Item.Properties().food(ModItems.MUSHROOM_CALZONE_FOOD)));
    public static final FoodProperties MUSHROOM_CALZONE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MUSHROOM_FISH_PIZZA_SLICE = ITEMS.register("mushroom_fish_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.MUSHROOM_FISH_PIZZA_SLICE_FOOD)));
    public static final FoodProperties MUSHROOM_FISH_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> MUSHROOM_ONION_PIZZA_SLICE = ITEMS.register("mushroom_onion_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.MUSHROOM_ONION_PIZZA_SLICE_FOOD)));
    public static final FoodProperties MUSHROOM_ONION_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> MUSHROOM_PIZZA_SLICE = ITEMS.register("mushroom_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.MUSHROOM_PIZZA_SLICE_FOOD)));
    public static final FoodProperties MUSHROOM_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> MUTTON_SANDWICH = ITEMS.register("mutton_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.MUTTON_SANDWICH_FOOD)));
    public static final FoodProperties MUTTON_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> MUTTON_SANDWICH_BEETROOT = ITEMS.register("mutton_sandwich_beetroot",
            () -> new Item(new Item.Properties().food(ModItems.MUTTON_SANDWICH_BEETROOT_FOOD)));
    public static final FoodProperties MUTTON_SANDWICH_BEETROOT_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> MUTTON_WRAP_LETTUCE_TOMATO = ITEMS.register("mutton_wrap_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.MUTTON_WRAP_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties MUTTON_WRAP_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> MUTTON_WRAP_ONION_LETTUCE_TOMATO = ITEMS.register("mutton_wrap_onion_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.MUTTON_WRAP_ONION_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties MUTTON_WRAP_ONION_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MUTTON_WRAP_ONION_TOMATO = ITEMS.register("mutton_wrap_onion_tomato",
            () -> new Item(new Item.Properties().food(ModItems.MUTTON_WRAP_ONION_TOMATO_FOOD)));
    public static final FoodProperties MUTTON_WRAP_ONION_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> ONION_BACON_PIZZA_SLICE = ITEMS.register("onion_bacon_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.ONION_BACON_PIZZA_SLICE_FOOD)));
    public static final FoodProperties ONION_BACON_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> ONION_CALZONE = ITEMS.register("onion_calzone",
            () -> new Item(new Item.Properties().food(ModItems.ONION_CALZONE_FOOD)));
    public static final FoodProperties ONION_CALZONE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> ONION_PIZZA_SLICE = ITEMS.register("onion_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.ONION_PIZZA_SLICE_FOOD)));
    public static final FoodProperties ONION_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> ORANGE_GELATIN_DESSERT_SLICE = ITEMS.register("orange_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.ORANGE_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties ORANGE_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> PASTA = ITEMS.register("pasta",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_FOOD)));
    public static final FoodProperties PASTA_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> PASTRY_BASE = ITEMS.register("pastry_base",
            () -> new Item(new Item.Properties().food(ModItems.PASTRY_BASE_FOOD)));
    public static final FoodProperties PASTRY_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> PEANUT_BUTTER_APPLE_JAM_SANDWICH = ITEMS.register("peanut_butter_apple_jam_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.PEANUT_BUTTER_APPLE_JAM_SANDWICH_FOOD)));
    public static final FoodProperties PEANUT_BUTTER_APPLE_JAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> PEANUT_BUTTER_CHORUS_FRUIT_JAM_SANDWICH = ITEMS.register("peanut_butter_chorus_fruit_jam_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.PEANUT_BUTTER_CHORUS_FRUIT_JAM_SANDWICH_FOOD)));
    public static final FoodProperties PEANUT_BUTTER_CHORUS_FRUIT_JAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> PEANUT_BUTTER_MELON_JAM_SANDWICH = ITEMS.register("peanut_butter_melon_jam_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.PEANUT_BUTTER_MELON_JAM_SANDWICH_FOOD)));
    public static final FoodProperties PEANUT_BUTTER_MELON_JAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> PINK_GELATIN_DESSERT_SLICE = ITEMS.register("pink_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.PINK_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties PINK_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> PITA_BREAD = ITEMS.register("pita_bread",
            () -> new Item(new Item.Properties().food(ModItems.PITA_BREAD_FOOD)));
    public static final FoodProperties PITA_BREAD_FOOD = new FoodProperties.Builder()
            .nutrition(3)
            .build();

    public static final DeferredItem<Item> PORK_MEATBALL = ITEMS.register("pork_meatball",
            () -> new Item(new Item.Properties().food(ModItems.PORK_MEATBALL_FOOD)));
    public static final FoodProperties PORK_MEATBALL_FOOD = new FoodProperties.Builder()
            .nutrition(3)
            .build();

    public static final DeferredItem<Item> PORK_MEATBALL_SANDWICH = ITEMS.register("pork_meatball_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.PORK_MEATBALL_SANDWICH_FOOD)));
    public static final FoodProperties PORK_MEATBALL_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(9).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> POTATO_CHIPS = ITEMS.register("potato_chips",
            () -> new Item(new Item.Properties().food(ModItems.POTATO_CHIPS_FOOD)));
    public static final FoodProperties POTATO_CHIPS_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> PUMPERNICKEL_BREAD = ITEMS.register("pumpernickel_bread",
            () -> new Item(new Item.Properties().food(ModItems.PUMPERNICKEL_BREAD_FOOD)));
    public static final FoodProperties PUMPERNICKEL_BREAD_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.1f)
            .build();

    public static final DeferredItem<Item> PUMPERNICKEL_BREAD_SLICE = ITEMS.register("pumpernickel_bread_slice",
            () -> new Item(new Item.Properties().food(ModItems.PUMPERNICKEL_BREAD_SLICE_FOOD)));
    public static final FoodProperties PUMPERNICKEL_BREAD_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.5f).fast()
            .build();

    public static final DeferredItem<Item> PUMPERNICKEL_TOAST_SLICE = ITEMS.register("pumpernickel_toast_slice",
            () -> new Item(new Item.Properties().food(ModItems.PUMPERNICKEL_TOAST_SLICE_FOOD)));
    public static final FoodProperties PUMPERNICKEL_TOAST_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.3f).fast()
            .build();

    public static final DeferredItem<Item> PURPLE_GELATIN_DESSERT_SLICE = ITEMS.register("purple_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.PURPLE_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties PURPLE_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> RABBIT_JERKY = ITEMS.register("rabbit_jerky",
            () -> new Item(new Item.Properties().food(ModItems.RABBIT_JERKY_FOOD)));
    public static final FoodProperties RABBIT_JERKY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.6f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> RABBIT_MEATBALL = ITEMS.register("rabbit_meatball",
            () -> new Item(new Item.Properties().food(ModItems.RABBIT_MEATBALL_FOOD)));
    public static final FoodProperties RABBIT_MEATBALL_FOOD = new FoodProperties.Builder()
            .nutrition(3)
            .build();

    public static final DeferredItem<Item> RABBIT_MEATBALL_SANDWICH = ITEMS.register("rabbit_meatball_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.RABBIT_MEATBALL_SANDWICH_FOOD)));
    public static final FoodProperties RABBIT_MEATBALL_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(9).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> RED_GELATIN_DESSERT_SLICE = ITEMS.register("red_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.RED_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties RED_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> SAUSAGE_BACON_PIZZA_SLICE = ITEMS.register("sausage_bacon_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BACON_PIZZA_SLICE_FOOD)));
    public static final FoodProperties SAUSAGE_BACON_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT = ITEMS.register("sausage_biscuit",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_FOOD = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_BACON = ITEMS.register("sausage_biscuit_bacon",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_BACON_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(15).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_CHEESE_BACON = ITEMS.register("sausage_biscuit_cheese_bacon",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_CHEESE_BACON_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_CHEESE_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(17).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_CHEESE_FRIED_EGG = ITEMS.register("sausage_biscuit_cheese_fried_egg",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_CHEESE_FRIED_EGG_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_CHEESE_FRIED_EGG_FOOD = new FoodProperties.Builder()
            .nutrition(16).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_CHEESE_FRIED_EGG_BACON = ITEMS.register("sausage_biscuit_cheese_fried_egg_bacon",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_CHEESE_FRIED_EGG_BACON_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_CHEESE_FRIED_EGG_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(18).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_FRIED_EGG = ITEMS.register("sausage_biscuit_fried_egg",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_FRIED_EGG_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_FRIED_EGG_FOOD = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_FRIED_EGG_BACON = ITEMS.register("sausage_biscuit_fried_egg_bacon",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_FRIED_EGG_BACON_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_FRIED_EGG_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(16).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH = ITEMS.register("sausage_biscuit_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_SANDWICH_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(19).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_BACON = ITEMS.register("sausage_biscuit_sandwich_bacon",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_SANDWICH_BACON_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_SANDWICH_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(20).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_CHEESE_BACON = ITEMS.register("sausage_biscuit_sandwich_cheese_bacon",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_SANDWICH_CHEESE_BACON_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_SANDWICH_CHEESE_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(22).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG = ITEMS.register("sausage_biscuit_sandwich_cheese_fried_egg",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG_FOOD = new FoodProperties.Builder()
            .nutrition(21).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG_BACON = ITEMS.register("sausage_biscuit_sandwich_cheese_fried_egg_bacon",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG_BACON_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_SANDWICH_CHEESE_FRIED_EGG_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(23).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG = ITEMS.register("sausage_biscuit_sandwich_fried_egg",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG_FOOD = new FoodProperties.Builder()
            .nutrition(19).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG_BACON = ITEMS.register("sausage_biscuit_sandwich_fried_egg_bacon",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG_BACON_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_SANDWICH_FRIED_EGG_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(21).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BITS = ITEMS.register("sausage_bits",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BITS_FOOD)));
    public static final FoodProperties SAUSAGE_BITS_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_CALZONE = ITEMS.register("sausage_calzone",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_CALZONE_FOOD)));
    public static final FoodProperties SAUSAGE_CALZONE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> SAUSAGE_FISH_PIZZA_SLICE = ITEMS.register("sausage_fish_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_FISH_PIZZA_SLICE_FOOD)));
    public static final FoodProperties SAUSAGE_FISH_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_MUSHROOM_PIZZA_SLICE = ITEMS.register("sausage_mushroom_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_MUSHROOM_PIZZA_SLICE_FOOD)));
    public static final FoodProperties SAUSAGE_MUSHROOM_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_ONION_PIZZA_SLICE = ITEMS.register("sausage_onion_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_ONION_PIZZA_SLICE_FOOD)));
    public static final FoodProperties SAUSAGE_ONION_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_PATTY = ITEMS.register("sausage_patty",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_PATTY_FOOD)));
    public static final FoodProperties SAUSAGE_PATTY_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_PIZZA_SLICE = ITEMS.register("sausage_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_PIZZA_SLICE_FOOD)));
    public static final FoodProperties SAUSAGE_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> SLICED_BEETROOT = ITEMS.register("sliced_beetroot",
            () -> new Item(new Item.Properties().food(ModItems.SLICED_BEETROOT_FOOD)));
    public static final FoodProperties SLICED_BEETROOT_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.3f).fast()
            .build();

    public static final DeferredItem<Item> SLICED_BROWN_MUSHROOM = ITEMS.register("sliced_brown_mushroom",
            () -> new Item(new Item.Properties().food(ModItems.SLICED_BROWN_MUSHROOM_FOOD)));
    public static final FoodProperties SLICED_BROWN_MUSHROOM_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.1f).fast()
            .build();

    public static final DeferredItem<Item> SLICED_CARROT = ITEMS.register("sliced_carrot",
            () -> new Item(new Item.Properties().food(ModItems.SLICED_CARROT_FOOD)));
    public static final FoodProperties SLICED_CARROT_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.4f).fast()
            .build();

    public static final DeferredItem<Item> SLICED_POTATO = ITEMS.register("sliced_potato",
            () -> new Item(new Item.Properties().food(ModItems.SLICED_POTATO_FOOD)));
    public static final FoodProperties SLICED_POTATO_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.6f).fast()
            .build();

    public static final DeferredItem<Item> SLICED_ONION = ITEMS.register("sliced_onion",
            () -> new Item(new Item.Properties().food(ModItems.SLICED_ONION_FOOD)));
    public static final FoodProperties SLICED_ONION_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> SLICED_RED_MUSHROOM = ITEMS.register("sliced_red_mushroom",
            () -> new Item(new Item.Properties().food(ModItems.SLICED_RED_MUSHROOM_FOOD)));
    public static final FoodProperties SLICED_RED_MUSHROOM_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.1f).fast()
            .build();

    public static final DeferredItem<Item> SLICED_TOMATO = ITEMS.register("sliced_tomato",
            () -> new Item(new Item.Properties().food(ModItems.SLICED_TOMATO_FOOD)));
    public static final FoodProperties SLICED_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.6f).fast()
            .build();

    public static final DeferredItem<Item> SMALL_ENDERMITE_MEATBALLS = ITEMS.register("small_endermite_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.SMALL_ENDERMITE_MEATBALLS_FOOD)));
    public static final FoodProperties SMALL_ENDERMITE_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SMALL_BEEF_MEATBALLS = ITEMS.register("small_beef_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.SMALL_BEEF_MEATBALLS_FOOD)));
    public static final FoodProperties SMALL_BEEF_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SMALL_RABBIT_MEATBALLS = ITEMS.register("small_rabbit_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.SMALL_RABBIT_MEATBALLS_FOOD)));
    public static final FoodProperties SMALL_RABBIT_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SMALL_PORK_MEATBALLS = ITEMS.register("small_pork_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.SMALL_PORK_MEATBALLS_FOOD)));
    public static final FoodProperties SMALL_PORK_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SMALL_SLIMEBALLS = ITEMS.register("small_slimeballs",
            () -> new Item(new Item.Properties().food(ModItems.SMALL_SLIMEBALLS_FOOD)));
    public static final FoodProperties SMALL_SLIMEBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> SMALL_STRIDER_MEATBALLS = ITEMS.register("small_strider_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.SMALL_STRIDER_MEATBALLS_FOOD)));
    public static final FoodProperties SMALL_STRIDER_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SMORE = ITEMS.register("smore",
            () -> new Item(new Item.Properties().food(ModItems.SMORE_FOOD)));
    public static final FoodProperties SMORE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SMORES_PIE_SLICE = ITEMS.register("smores_pie_slice",
            () -> new Item(new Item.Properties().food(ModItems.SMORES_PIE_SLICE_FOOD)));
    public static final FoodProperties SMORES_PIE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1800, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SPICY_SAUSAGES = ITEMS.register("spicy_sausages",
            () -> new Item(new Item.Properties().food(ModItems.SPICY_SAUSAGES_FOOD)));
    public static final FoodProperties SPICY_SAUSAGES_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SPICY_CHICKEN_NUGGETS = ITEMS.register("spicy_chicken_nuggets",
            () -> new Item(new Item.Properties().food(ModItems.SPICY_CHICKEN_NUGGETS_FOOD)));
    public static final FoodProperties SPICY_CHICKEN_NUGGETS_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.1f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> STRIDER_MEATBALL = ITEMS.register("strider_meatball",
            () -> new Item(new Item.Properties().food(ModItems.STRIDER_MEATBALL_FOOD)));
    public static final FoodProperties STRIDER_MEATBALL_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> STRIDER_MEATBALL_SANDWICH = ITEMS.register("strider_meatball_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.STRIDER_MEATBALL_SANDWICH_FOOD)));
    public static final FoodProperties STRIDER_MEATBALL_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.4f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GRAHAM_CRACKER_MARSHMALLOW = ITEMS.register("graham_cracker_marshmallow",
            () -> new Item(new Item.Properties().food(ModItems.GRAHAM_CRACKER_MARSHMALLOW_FOOD)));
    public static final FoodProperties GRAHAM_CRACKER_MARSHMALLOW_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> GRAHAM_CRACKER_PIE_CRUST = ITEMS.register("graham_cracker_pie_crust",
            () -> new Item(new Item.Properties().food(ModItems.GRAHAM_CRACKER_PIE_CRUST_FOOD)));
    public static final FoodProperties GRAHAM_CRACKER_PIE_CRUST_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> GRAY_GELATIN_DESSERT_SLICE = ITEMS.register("gray_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.GRAY_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties GRAY_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GREEN_GELATIN_DESSERT_SLICE = ITEMS.register("green_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.GREEN_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties GREEN_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GRILLED_CHEESE_SANDWICH = ITEMS.register("grilled_cheese_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.GRILLED_CHEESE_SANDWICH_FOOD)));
    public static final FoodProperties GRILLED_CHEESE_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> HAMBURGER = ITEMS.register("hamburger",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_FOOD)));
    public static final FoodProperties HAMBURGER_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_BACON = ITEMS.register("hamburger_bacon",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_BACON_FOOD)));
    public static final FoodProperties HAMBURGER_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_BACON_LETTUCE = ITEMS.register("hamburger_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_BACON_LETTUCE_FOOD)));
    public static final FoodProperties HAMBURGER_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_BACON_LETTUCE_TOMATO = ITEMS.register("hamburger_bacon_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_BACON_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties HAMBURGER_BACON_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_LETTUCE = ITEMS.register("hamburger_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_LETTUCE_FOOD)));
    public static final FoodProperties HAMBURGER_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_LETTUCE_TOMATO = ITEMS.register("hamburger_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties HAMBURGER_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_ONION = ITEMS.register("hamburger_onion",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_ONION_FOOD)));
    public static final FoodProperties HAMBURGER_ONION_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_ONION_BACON = ITEMS.register("hamburger_onion_bacon",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_ONION_BACON_FOOD)));
    public static final FoodProperties HAMBURGER_ONION_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_ONION_BACON_LETTUCE = ITEMS.register("hamburger_onion_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_ONION_BACON_LETTUCE_FOOD)));
    public static final FoodProperties HAMBURGER_ONION_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_ONION_LETTUCE = ITEMS.register("hamburger_onion_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_ONION_LETTUCE_FOOD)));
    public static final FoodProperties HAMBURGER_ONION_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_PEANUT_BUTTER = ITEMS.register("hamburger_peanut_butter",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_PEANUT_BUTTER_FOOD)));
    public static final FoodProperties HAMBURGER_PEANUT_BUTTER_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_PEANUT_BUTTER_BACON = ITEMS.register("hamburger_peanut_butter_bacon",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_PEANUT_BUTTER_BACON_FOOD)));
    public static final FoodProperties HAMBURGER_PEANUT_BUTTER_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HAMBURGER_TOMATO = ITEMS.register("hamburger_tomato",
            () -> new Item(new Item.Properties().food(ModItems.HAMBURGER_TOMATO_FOOD)));
    public static final FoodProperties HAMBURGER_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HASH_BROWNS = ITEMS.register("hash_browns",
            () -> new Item(new Item.Properties().food(ModItems.HASH_BROWNS_FOOD)));
    public static final FoodProperties HASH_BROWNS_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> HOLLOW_CHOCOLATE = ITEMS.register("hollow_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.HOLLOW_CHOCOLATE_FOOD)));
    public static final FoodProperties HOLLOW_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f).fast()
            .build();

    public static final DeferredItem<Item> HOLLOW_DARK_CHOCOLATE = ITEMS.register("hollow_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.HOLLOW_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties HOLLOW_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.2f).fast()
            .build();

    public static final DeferredItem<Item> HOLLOW_WHITE_CHOCOLATE = ITEMS.register("hollow_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.HOLLOW_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties HOLLOW_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.4f).fast()
            .build();

    public static final DeferredItem<Item> HONEYED_BERRIES = ITEMS.register("honeyed_berries",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_BERRIES_FOOD)));
    public static final FoodProperties HONEYED_BERRIES_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HONEYED_BISCUIT = ITEMS.register("honeyed_biscuit",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_BISCUIT_FOOD)));
    public static final FoodProperties HONEYED_BISCUIT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HONEYED_CHOCOLATE_DONUT = ITEMS.register("honeyed_chocolate_donut",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_CHOCOLATE_DONUT_FOOD)));
    public static final FoodProperties HONEYED_CHOCOLATE_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HONEYED_CHOCOLATE_SWEET_ROLL = ITEMS.register("honeyed_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties HONEYED_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HONEYED_CHOCOLATE_CUPCAKE = ITEMS.register("honeyed_chocolate_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_CHOCOLATE_CUPCAKE_FOOD)));
    public static final FoodProperties HONEYED_CHOCOLATE_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> HONEYED_CUPCAKE = ITEMS.register("honeyed_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_CUPCAKE_FOOD)));
    public static final FoodProperties HONEYED_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> HONEYED_MINI_WAFFLE = ITEMS.register("honeyed_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_MINI_WAFFLE_FOOD)));
    public static final FoodProperties HONEYED_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> HONEYED_DONUT = ITEMS.register("honeyed_donut",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_DONUT_FOOD)));
    public static final FoodProperties HONEYED_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HONEYED_MUFFIN = ITEMS.register("honeyed_muffin",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_MUFFIN_FOOD)));
    public static final FoodProperties HONEYED_MUFFIN_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> HONEYED_SWEET_ROLL = ITEMS.register("honeyed_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_SWEET_ROLL_FOOD)));
    public static final FoodProperties HONEYED_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> HONEYED_TOAST = ITEMS.register("honeyed_toast",
            () -> new Item(new Item.Properties().food(ModItems.HONEYED_TOAST_FOOD)));
    public static final FoodProperties HONEYED_TOAST_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> ICE_CREAM_CONE = ITEMS.register("ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> ICE_CREAM_SANDWICH = ITEMS.register("ice_cream_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.ICE_CREAM_SANDWICH_FOOD)));
    public static final FoodProperties ICE_CREAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).build();

    public static final DeferredItem<Item> ICE_CREAM_SANDWICH_NEAPOLITAN = ITEMS.register("ice_cream_sandwich_neapolitan",
            () -> new Item(new Item.Properties().food(ModItems.ICE_CREAM_SANDWICH_NEAPOLITAN_FOOD)));
    public static final FoodProperties ICE_CREAM_SANDWICH_NEAPOLITAN_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f).build();

    public static final DeferredItem<Item> LIGHT_BLUE_GELATIN_DESSERT_SLICE = ITEMS.register("light_blue_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.LIGHT_BLUE_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties LIGHT_BLUE_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> LIGHT_GRAY_GELATIN_DESSERT_SLICE = ITEMS.register("light_gray_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.LIGHT_GRAY_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties LIGHT_GRAY_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> LIME_GELATIN_DESSERT_SLICE = ITEMS.register("lime_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.LIME_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties LIME_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MACARONI = ITEMS.register("macaroni",
            () -> new Item(new Item.Properties().food(ModItems.MACARONI_FOOD)));
    public static final FoodProperties MACARONI_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> MAGENTA_GELATIN_DESSERT_SLICE = ITEMS.register("magenta_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.MAGENTA_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties MAGENTA_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MAGMA_CREAM_MARSHMALLOW = ITEMS.register("magma_cream_marshmallow",
            () -> new Item(new Item.Properties().food(ModItems.MAGMA_CREAM_MARSHMALLOW_FOOD)));
    public static final FoodProperties MAGMA_CREAM_MARSHMALLOW_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MARSHMALLOW = ITEMS.register("marshmallow",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_FOOD)));
    public static final FoodProperties MARSHMALLOW_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f).fast()
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_BUTTERSCOTCH_FUDGE = ITEMS.register("marshmallow_butterscotch_fudge",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_BUTTERSCOTCH_FUDGE_FOOD)));
    public static final FoodProperties MARSHMALLOW_BUTTERSCOTCH_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.9f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_CARAMEL_FUDGE = ITEMS.register("marshmallow_caramel_fudge",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_CARAMEL_FUDGE_FOOD)));
    public static final FoodProperties MARSHMALLOW_CARAMEL_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.5f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_CHOCOLATE = ITEMS.register("marshmallow_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_CHOCOLATE_FOOD)));
    public static final FoodProperties MARSHMALLOW_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(6).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_CHOCOLATE_FUDGE = ITEMS.register("marshmallow_chocolate_fudge",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_CHOCOLATE_FUDGE_FOOD)));
    public static final FoodProperties MARSHMALLOW_CHOCOLATE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_COFFEE_TOFFEE_FUDGE = ITEMS.register("marshmallow_coffee_toffee_fudge",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_COFFEE_TOFFEE_FUDGE_FOOD)));
    public static final FoodProperties MARSHMALLOW_COFFEE_TOFFEE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_DARK_CHOCOLATE = ITEMS.register("marshmallow_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties MARSHMALLOW_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_DARK_CHOCOLATE_FUDGE = ITEMS.register("marshmallow_dark_chocolate_fudge",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_DARK_CHOCOLATE_FUDGE_FOOD)));
    public static final FoodProperties MARSHMALLOW_DARK_CHOCOLATE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_TOFFEE_FUDGE = ITEMS.register("marshmallow_toffee_fudge",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_TOFFEE_FUDGE_FOOD)));
    public static final FoodProperties MARSHMALLOW_TOFFEE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_WHITE_CHOCOLATE = ITEMS.register("marshmallow_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties MARSHMALLOW_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_WHITE_CHOCOLATE_FUDGE = ITEMS.register("marshmallow_white_chocolate_fudge",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_WHITE_CHOCOLATE_FUDGE_FOOD)));
    public static final FoodProperties MARSHMALLOW_WHITE_CHOCOLATE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BEEF_MEATBALL = ITEMS.register("beef_meatball",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_MEATBALL_FOOD)));
    public static final FoodProperties BEEF_MEATBALL_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> BEEF_MEATBALL_SANDWICH = ITEMS.register("beef_meatball_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_MEATBALL_SANDWICH_FOOD)));
    public static final FoodProperties BEEF_MEATBALL_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE = ITEMS.register("melon_cream_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_CHOCOLATE_FOOD)));
    public static final FoodProperties MELON_CREAM_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE_PASTRY = ITEMS.register("melon_cream_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties MELON_CREAM_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MELON_CREAM_DARK_CHOCOLATE = ITEMS.register("melon_cream_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties MELON_CREAM_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE_SWEET_ROLL = ITEMS.register("melon_cream_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties MELON_CREAM_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE_CUPCAKE = ITEMS.register("melon_cream_chocolate_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_CHOCOLATE_CUPCAKE_FOOD)));
    public static final FoodProperties MELON_CREAM_CHOCOLATE_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.2f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MELON_CREAM_CUPCAKE = ITEMS.register("melon_cream_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_CUPCAKE_FOOD)));
    public static final FoodProperties MELON_CREAM_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.1f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MELON_CREAM_MINI_WAFFLE = ITEMS.register("melon_cream_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_MINI_WAFFLE_FOOD)));
    public static final FoodProperties MELON_CREAM_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.6f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MELON_CREAM_SWEET_ROLL = ITEMS.register("melon_cream_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_SWEET_ROLL_FOOD)));
    public static final FoodProperties MELON_CREAM_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MELON_CREAM_CHOCOLATE_DONUT = ITEMS.register("melon_cream_chocolate_donut",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_CHOCOLATE_DONUT_FOOD)));
    public static final FoodProperties MELON_CREAM_CHOCOLATE_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MELON_CREAM_DONUT = ITEMS.register("melon_cream_donut",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_DONUT_FOOD)));
    public static final FoodProperties MELON_CREAM_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MELON_CREAM_PASTRY = ITEMS.register("melon_cream_pastry",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_PASTRY_FOOD)));
    public static final FoodProperties MELON_CREAM_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MELON_CREAM_WHITE_CHOCOLATE = ITEMS.register("melon_cream_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.MELON_CREAM_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties MELON_CREAM_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> MELON_ICE_CREAM_CONE = ITEMS.register("melon_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.MELON_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties MELON_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.1f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> MELON_ICE_CREAM_SANDWICH = ITEMS.register("melon_ice_cream_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.MELON_ICE_CREAM_SANDWICH_FOOD)));
    public static final FoodProperties MELON_ICE_CREAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> MELON_JAM_SANDWICH = ITEMS.register("melon_jam_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.MELON_JAM_SANDWICH_FOOD)));
    public static final FoodProperties MELON_JAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> EGGPLANT_BUN = ITEMS.register("eggplant_bun",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BUN_FOOD)));
    public static final FoodProperties EGGPLANT_BUN_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BUN_CHEESE_TOMATO = ITEMS.register("eggplant_bun_cheese_tomato",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BUN_CHEESE_TOMATO_FOOD)));
    public static final FoodProperties EGGPLANT_BUN_CHEESE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BUN_LETTUCE = ITEMS.register("eggplant_bun_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BUN_LETTUCE_FOOD)));
    public static final FoodProperties EGGPLANT_BUN_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BUN_LETTUCE_TOMATO = ITEMS.register("eggplant_bun_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BUN_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties EGGPLANT_BUN_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(1.1f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BUN_TOMATO = ITEMS.register("eggplant_bun_tomato",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BUN_TOMATO_FOOD)));
    public static final FoodProperties EGGPLANT_BUN_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BURGER = ITEMS.register("eggplant_burger",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BURGER_FOOD)));
    public static final FoodProperties EGGPLANT_BURGER_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BURGER_LETTUCE = ITEMS.register("eggplant_burger_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BURGER_LETTUCE_FOOD)));
    public static final FoodProperties EGGPLANT_BURGER_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BURGER_TOMATO = ITEMS.register("eggplant_burger_tomato",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BURGER_TOMATO_FOOD)));
    public static final FoodProperties EGGPLANT_BURGER_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_CHEESEBURGER = ITEMS.register("eggplant_cheeseburger",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_CHEESEBURGER_FOOD)));
    public static final FoodProperties EGGPLANT_CHEESEBURGER_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_CHEESEBURGER_LETTUCE = ITEMS.register("eggplant_cheeseburger_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_CHEESEBURGER_LETTUCE_FOOD)));
    public static final FoodProperties EGGPLANT_CHEESEBURGER_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_CHEESEBURGER_LETTUCE_TOMATO = ITEMS.register("eggplant_cheeseburger_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_CHEESEBURGER_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties EGGPLANT_CHEESEBURGER_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_CHEESEBURGER_TOMATO = ITEMS.register("eggplant_cheeseburger_tomato",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_CHEESEBURGER_TOMATO_FOOD)));
    public static final FoodProperties EGGPLANT_CHEESEBURGER_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> ENDERMITE_MEATBALL = ITEMS.register("endermite_meatball",
            () -> new Item(new Item.Properties().food(ModItems.ENDERMITE_MEATBALL_FOOD)));
    public static final FoodProperties ENDERMITE_MEATBALL_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> ENDERMITE_MEATBALL_SANDWICH = ITEMS.register("endermite_meatball_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.ENDERMITE_MEATBALL_SANDWICH_FOOD)));
    public static final FoodProperties ENDERMITE_MEATBALL_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> FISH_BACON_PIZZA_SLICE = ITEMS.register("fish_bacon_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.FISH_BACON_PIZZA_SLICE_FOOD)));
    public static final FoodProperties FISH_BACON_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> FISHCAKE = ITEMS.register("fishcake",
            () -> new Item(new Item.Properties().food(ModItems.FISHCAKE_FOOD)));
    public static final FoodProperties FISHCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 3600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> FISH_CALZONE = ITEMS.register("fish_calzone",
            () -> new Item(new Item.Properties().food(ModItems.FISH_CALZONE_FOOD)));
    public static final FoodProperties FISH_CALZONE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> FISH_ONION_PIZZA_SLICE = ITEMS.register("fish_onion_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.FISH_ONION_PIZZA_SLICE_FOOD)));
    public static final FoodProperties FISH_ONION_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> FISH_PIZZA_SLICE = ITEMS.register("fish_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.FISH_PIZZA_SLICE_FOOD)));
    public static final FoodProperties FISH_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> FRIED_EGG_HASH_BROWN_SANDWICH = ITEMS.register("fried_egg_hash_brown_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.FRIED_EGG_HASH_BROWN_SANDWICH_FOOD)));
    public static final FoodProperties FRIED_EGG_HASH_BROWN_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_CHOCOLATE_CUPCAKE = ITEMS.register("cream_chocolate_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CHOCOLATE_CUPCAKE_FOOD)));
    public static final FoodProperties CREAM_CHOCOLATE_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY = ITEMS.register("cream_chocolate_sweet_roll_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY_FOOD)));
    public static final FoodProperties CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT = ITEMS.register("cream_chocolate_sweet_roll_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY = ITEMS.register("cream_chocolate_sweet_roll_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY_FOOD)));
    public static final FoodProperties CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_CUPCAKE = ITEMS.register("cream_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CUPCAKE_FOOD)));
    public static final FoodProperties CREAM_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CREAM_MINI_WAFFLE = ITEMS.register("cream_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_MINI_WAFFLE_FOOD)));
    public static final FoodProperties CREAM_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CREAM_MINI_WAFFLE_SWEET_BERRY = ITEMS.register("cream_mini_waffle_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_MINI_WAFFLE_SWEET_BERRY_FOOD)));
    public static final FoodProperties CREAM_MINI_WAFFLE_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CREAM_MINI_WAFFLE_CHORUS_FRUIT = ITEMS.register("cream_mini_waffle_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_MINI_WAFFLE_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties CREAM_MINI_WAFFLE_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CREAM_MINI_WAFFLE_GLOW_BERRY = ITEMS.register("cream_mini_waffle_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_MINI_WAFFLE_GLOW_BERRY_FOOD)));
    public static final FoodProperties CREAM_MINI_WAFFLE_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CREAM_SWEET_ROLL_SWEET_BERRY = ITEMS.register("cream_sweet_roll_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_SWEET_ROLL_SWEET_BERRY_FOOD)));
    public static final FoodProperties CREAM_SWEET_ROLL_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 300, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 300, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_SWEET_ROLL_CHORUS_FRUIT = ITEMS.register("cream_sweet_roll_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_SWEET_ROLL_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties CREAM_SWEET_ROLL_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 300, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 300, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_SWEET_ROLL_GLOW_BERRY = ITEMS.register("cream_sweet_roll_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_SWEET_ROLL_GLOW_BERRY_FOOD)));
    public static final FoodProperties CREAM_SWEET_ROLL_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 300, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 300, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GELATIN_DESSERT_SLICE = ITEMS.register("gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CHEESECAKE_SLICE = ITEMS.register("glow_berry_cheesecake_slice",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CHEESECAKE_SLICE_FOOD)));
    public static final FoodProperties GLOW_BERRY_CHEESECAKE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT = ITEMS.register("glow_berry_cream_cake_slice_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CAKE_SLICE_GLOW_BERRY = ITEMS.register("glow_berry_cream_cake_slice_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_GLOW_BERRY_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CAKE_SLICE_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CAKE_SLICE_SWEET_BERRY = ITEMS.register("glow_berry_cream_cake_slice_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_SWEET_BERRY_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CAKE_SLICE_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE = ITEMS.register("glow_berry_cream_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CHOCOLATE_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_PASTRY = ITEMS.register("glow_berry_cream_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_DARK_CHOCOLATE = ITEMS.register("glow_berry_cream_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL = ITEMS.register("glow_berry_cream_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY = ITEMS.register("glow_berry_cream_chocolate_sweet_roll_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CHOCOLATE_SWEET_ROLL_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_CUPCAKE = ITEMS.register("glow_berry_cream_chocolate_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CHOCOLATE_CUPCAKE_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CHOCOLATE_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.1f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CUPCAKE = ITEMS.register("glow_berry_cream_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CUPCAKE_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_MINI_WAFFLE = ITEMS.register("glow_berry_cream_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_MINI_WAFFLE_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.9f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_MINI_WAFFLE_GLOW_BERRY = ITEMS.register("glow_berry_cream_mini_waffle_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_MINI_WAFFLE_GLOW_BERRY_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_MINI_WAFFLE_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.9f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_SWEET_ROLL = ITEMS.register("glow_berry_cream_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_SWEET_ROLL_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_SWEET_ROLL_GLOW_BERRY = ITEMS.register("glow_berry_cream_sweet_roll_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_SWEET_ROLL_GLOW_BERRY_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_SWEET_ROLL_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CHOCOLATE_DONUT = ITEMS.register("glow_berry_cream_chocolate_donut",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_CHOCOLATE_DONUT_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_CHOCOLATE_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_DONUT = ITEMS.register("glow_berry_cream_donut",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_DONUT_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_PASTRY = ITEMS.register("glow_berry_cream_pastry",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_PASTRY_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_WHITE_CHOCOLATE = ITEMS.register("glow_berry_cream_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties GLOW_BERRY_CREAM_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_ICE_CREAM_CONE = ITEMS.register("glow_berry_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties GLOW_BERRY_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.9f).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_ICE_CREAM_SANDWICH = ITEMS.register("glow_berry_ice_cream_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_ICE_CREAM_SANDWICH_FOOD)));
    public static final FoodProperties GLOW_BERRY_ICE_CREAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_JAM_SANDWICH = ITEMS.register("glow_berry_jam_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_JAM_SANDWICH_FOOD)));
    public static final FoodProperties GLOW_BERRY_JAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_PIE_SLICE = ITEMS.register("glow_berry_pie_slice",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_PIE_SLICE_FOOD)));
    public static final FoodProperties GLOW_BERRY_PIE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> GRAHAM_CRACKER = ITEMS.register("graham_cracker",
            () -> new Item(new Item.Properties().food(ModItems.GRAHAM_CRACKER_FOOD)));
    public static final FoodProperties GRAHAM_CRACKER_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> GRAHAM_CRACKER_CHOCOLATE = ITEMS.register("graham_cracker_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.GRAHAM_CRACKER_CHOCOLATE_FOOD)));
    public static final FoodProperties GRAHAM_CRACKER_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> GRAHAM_CRACKER_CHOCOLATE_MARSHMALLOW = ITEMS.register("graham_cracker_chocolate_marshmallow",
            () -> new Item(new Item.Properties().food(ModItems.GRAHAM_CRACKER_CHOCOLATE_MARSHMALLOW_FOOD)));
    public static final FoodProperties GRAHAM_CRACKER_CHOCOLATE_MARSHMALLOW_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CLOTH_FILTER_CACAO_MASS = ITEMS.register("cloth_filter_cacao_mass",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COFFEE_TOFFEE = ITEMS.register("coffee_toffee",
            () -> new Item(new Item.Properties().food(ModItems.COFFEE_TOFFEE_FOOD)));
    public static final FoodProperties COFFEE_TOFFEE_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(2.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> COFFEE_TOFFEE_FUDGE = ITEMS.register("coffee_toffee_fudge",
            () -> new Item(new Item.Properties().food(ModItems.COFFEE_TOFFEE_FUDGE_FOOD)));
    public static final FoodProperties COFFEE_TOFFEE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> COOKED_RABBIT_CUTS = ITEMS.register("cooked_rabbit_cuts",
            () -> new Item(new Item.Properties().food(ModItems.COOKED_RABBIT_CUTS_FOOD)));
    public static final FoodProperties COOKED_RABBIT_CUTS_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> COOKIE_CREAM_PIE_SLICE = ITEMS.register("cookie_cream_pie_slice",
            () -> new Item(new Item.Properties().food(ModItems.COOKIE_CREAM_PIE_SLICE_FOOD)));
    public static final FoodProperties COOKIE_CREAM_PIE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_CHOCOLATE = ITEMS.register("cream_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CHOCOLATE_FOOD)));
    public static final FoodProperties CREAM_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CREAM_CHOCOLATE_PASTRY = ITEMS.register("cream_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties CREAM_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_DARK_CHOCOLATE = ITEMS.register("cream_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties CREAM_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CREAM_CHOCOLATE_DONUT = ITEMS.register("cream_chocolate_donut",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CHOCOLATE_DONUT_FOOD)));
    public static final FoodProperties CREAM_CHOCOLATE_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_DONUT = ITEMS.register("cream_donut",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_DONUT_FOOD)));
    public static final FoodProperties CREAM_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_PASTRY = ITEMS.register("cream_pastry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_PASTRY_FOOD)));
    public static final FoodProperties CREAM_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_SLICE = ITEMS.register("cream_pie_chocolate_graham_cracker_slice",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_SLICE_FOOD)));
    public static final FoodProperties CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_PIE_GRAHAM_CRACKER_SLICE = ITEMS.register("cream_pie_graham_cracker_slice",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_PIE_GRAHAM_CRACKER_SLICE_FOOD)));
    public static final FoodProperties CREAM_PIE_GRAHAM_CRACKER_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_WHITE_CHOCOLATE = ITEMS.register("cream_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties CREAM_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CUPCAKE_BASE = ITEMS.register("cupcake_base",
            () -> new Item(new Item.Properties().food(ModItems.CUPCAKE_BASE_FOOD)));
    public static final FoodProperties CUPCAKE_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.9f).fast()
            .build();

    public static final DeferredItem<Item> CYAN_GELATIN_DESSERT_SLICE = ITEMS.register("cyan_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.CYAN_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties CYAN_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE = ITEMS.register("dark_chocolate_chip_chocolate_cookie",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_COOKIE = ITEMS.register("dark_chocolate_chip_cookie",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_CHIP_COOKIE_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_CHIP_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_ICE_CREAM_CONE = ITEMS.register("dark_chocolate_chip_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_CHIP_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_CHIP_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_MUFFIN = ITEMS.register("dark_chocolate_chip_muffin",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_CHIP_MUFFIN_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_CHIP_MUFFIN_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_MINI_WAFFLE = ITEMS.register("dark_chocolate_chip_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_CHIP_MINI_WAFFLE_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_CHIP_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHOCOLATE_PASTRY = ITEMS.register("dark_chocolate_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_FUDGE = ITEMS.register("dark_chocolate_fudge",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_FUDGE_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_APPLE = ITEMS.register("dark_chocolate_apple",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_APPLE_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_APPLE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_BERRIES = ITEMS.register("dark_chocolate_berries",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_BERRIES_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_BERRIES_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_PASTRY = ITEMS.register("dark_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_TOAST = ITEMS.register("dark_chocolate_toast",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_TOAST_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_TOAST_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DONUT_BASE = ITEMS.register("donut_base",
            () -> new Item(new Item.Properties().food(ModItems.DONUT_BASE_FOOD)));
    public static final FoodProperties DONUT_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.1f)
            .build();

    public static final DeferredItem<Item> DONUT_HOLE = ITEMS.register("donut_hole",
            () -> new Item(new Item.Properties().food(ModItems.DONUT_HOLE_FOOD)));
    public static final FoodProperties DONUT_HOLE_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> DONUT_HOLE_SUGAR = ITEMS.register("donut_hole_sugar",
            () -> new Item(new Item.Properties().food(ModItems.DONUT_HOLE_SUGAR_FOOD)));
    public static final FoodProperties DONUT_HOLE_SUGAR_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.4f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DONUT_SUGAR = ITEMS.register("donut_sugar",
            () -> new Item(new Item.Properties().food(ModItems.DONUT_SUGAR_FOOD)));
    public static final FoodProperties DONUT_SUGAR_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.6f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DRAGON_BUN = ITEMS.register("dragon_bun",
            () -> new Item(new Item.Properties().food(ModItems.DRAGON_BUN_FOOD)));
    public static final FoodProperties DRAGON_BUN_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> DRAGON_BUN_CRIMSON_FUNGUS = ITEMS.register("dragon_bun_crimson_fungus",
            () -> new Item(new Item.Properties().food(ModItems.DRAGON_BUN_CRIMSON_FUNGUS_FOOD)));
    public static final FoodProperties DRAGON_BUN_CRIMSON_FUNGUS_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> DRAGON_BUN_WARPED_FUNGUS = ITEMS.register("dragon_bun_warped_fungus",
            () -> new Item(new Item.Properties().food(ModItems.DRAGON_BUN_WARPED_FUNGUS_FOOD)));
    public static final FoodProperties DRAGON_BUN_WARPED_FUNGUS_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> DRAGON_BURGER = ITEMS.register("dragon_burger",
            () -> new Item(new Item.Properties().food(ModItems.DRAGON_BURGER_FOOD)));
    public static final FoodProperties DRAGON_BURGER_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.4f).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DRAGON_BURGER_CRIMSON_FUNGUS = ITEMS.register("dragon_burger_crimson_fungus",
            () -> new Item(new Item.Properties().food(ModItems.DRAGON_BURGER_CRIMSON_FUNGUS_FOOD)));
    public static final FoodProperties DRAGON_BURGER_CRIMSON_FUNGUS_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.5f).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 6000, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DRAGON_BURGER_WARPED_FUNGUS = ITEMS.register("dragon_burger_warped_fungus",
            () -> new Item(new Item.Properties().food(ModItems.DRAGON_BURGER_WARPED_FUNGUS_FOOD)));
    public static final FoodProperties DRAGON_BURGER_WARPED_FUNGUS_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.6f).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 6000, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> DRAGON_PATTY = ITEMS.register("dragon_patty",
            () -> new Item(new Item.Properties().food(ModItems.DRAGON_PATTY_FOOD)));
    public static final FoodProperties DRAGON_PATTY_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> CREAM_CHOCOLATE_SWEET_ROLL = ITEMS.register("cream_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties CREAM_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_SWEET_ROLL_BASE = ITEMS.register("chocolate_sweet_roll_base",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_SWEET_ROLL_BASE_FOOD)));
    public static final FoodProperties CHOCOLATE_SWEET_ROLL_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_TOAST = ITEMS.register("chocolate_toast",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_TOAST_FOOD)));
    public static final FoodProperties CHOCOLATE_TOAST_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CHEESECAKE_SLICE = ITEMS.register("chorus_fruit_cheesecake_slice",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CHEESECAKE_SLICE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CHEESECAKE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT = ITEMS.register("chorus_fruit_cream_cake_slice_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_SLICE_GLOW_BERRY = ITEMS.register("chorus_fruit_cream_cake_slice_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_GLOW_BERRY_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CAKE_SLICE_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_SLICE_SWEET_BERRY = ITEMS.register("chorus_fruit_cream_cake_slice_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_SWEET_BERRY_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CAKE_SLICE_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE = ITEMS.register("chorus_fruit_cream_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CHOCOLATE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_PASTRY = ITEMS.register("chorus_fruit_cream_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_DARK_CHOCOLATE = ITEMS.register("chorus_fruit_cream_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL = ITEMS.register("chorus_fruit_cream_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT = ITEMS.register("chorus_fruit_cream_chocolate_sweet_roll_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CHOCOLATE_SWEET_ROLL_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_CUPCAKE = ITEMS.register("chorus_fruit_cream_chocolate_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CHOCOLATE_CUPCAKE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CHOCOLATE_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.2f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CUPCAKE = ITEMS.register("chorus_fruit_cream_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CUPCAKE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.1f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_MINI_WAFFLE = ITEMS.register("chorus_fruit_cream_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_MINI_WAFFLE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.9f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_MINI_WAFFLE_CHORUS_FRUIT = ITEMS.register("chorus_fruit_cream_mini_waffle_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_MINI_WAFFLE_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_MINI_WAFFLE_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.9f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_SWEET_ROLL = ITEMS.register("chorus_fruit_cream_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_SWEET_ROLL_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_SWEET_ROLL_CHORUS_FRUIT = ITEMS.register("chorus_fruit_cream_sweet_roll_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_SWEET_ROLL_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_SWEET_ROLL_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CHOCOLATE_DONUT = ITEMS.register("chorus_fruit_cream_chocolate_donut",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_CHOCOLATE_DONUT_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_CHOCOLATE_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_DONUT = ITEMS.register("chorus_fruit_cream_donut",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_DONUT_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_PASTRY = ITEMS.register("chorus_fruit_cream_pastry",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_PASTRY_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_WHITE_CHOCOLATE = ITEMS.register("chorus_fruit_cream_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_ICE_CREAM_CONE = ITEMS.register("chorus_fruit_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.9f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_ICE_CREAM_SANDWICH = ITEMS.register("chorus_fruit_ice_cream_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_ICE_CREAM_SANDWICH_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_ICE_CREAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_JAM_SANDWICH = ITEMS.register("chorus_fruit_jam_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_JAM_SANDWICH_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_JAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_PIE_SLICE = ITEMS.register("chorus_fruit_pie_slice",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_PIE_SLICE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_PIE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_SLICE = ITEMS.register("chorus_fruit_slice",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_SLICE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.9f).fast()
            .build();

    public static final DeferredItem<Item> CINNAMON_SWEET_ROLL_BASE = ITEMS.register("cinnamon_sweet_roll_base",
            () -> new Item(new Item.Properties().food(ModItems.CINNAMON_SWEET_ROLL_BASE_FOOD)));
    public static final FoodProperties CINNAMON_SWEET_ROLL_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CHIP_ICE_CREAM_CONE = ITEMS.register("chocolate_chip_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CHIP_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties CHOCOLATE_CHIP_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CHIP_MUFFIN = ITEMS.register("chocolate_chip_muffin",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CHIP_MUFFIN_FOOD)));
    public static final FoodProperties CHOCOLATE_CHIP_MUFFIN_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CHIP_MINI_WAFFLE = ITEMS.register("chocolate_chip_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CHIP_MINI_WAFFLE_FOOD)));
    public static final FoodProperties CHOCOLATE_CHIP_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CHOCOLATE_PASTRY = ITEMS.register("chocolate_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties CHOCOLATE_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CHIP_CHOCOLATE_COOKIE = ITEMS.register("chocolate_chip_chocolate_cookie",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CHIP_CHOCOLATE_COOKIE_FOOD)));
    public static final FoodProperties CHOCOLATE_CHIP_CHOCOLATE_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_BUTTERSCOTCH = ITEMS.register("chocolate_cream_cake_slice_butterscotch",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CAKE_SLICE_BUTTERSCOTCH_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CAKE_SLICE_BUTTERSCOTCH_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.4f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_CHOCOLATE = ITEMS.register("chocolate_cream_cake_slice_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CHOCOLATE_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CAKE_SLICE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_DARK_CHOCOLATE = ITEMS.register("chocolate_cream_cake_slice_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CAKE_SLICE_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CAKE_SLICE_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_TOFFEE = ITEMS.register("chocolate_cream_cake_slice_toffee",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CAKE_SLICE_TOFFEE_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CAKE_SLICE_TOFFEE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.4f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_WHITE_CHOCOLATE = ITEMS.register("chocolate_cream_cake_slice_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CAKE_SLICE_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CAKE_SLICE_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CHOCOLATE_CUPCAKE = ITEMS.register("chocolate_cream_chocolate_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CHOCOLATE_CUPCAKE_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CHOCOLATE_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 2400, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CUPCAKE = ITEMS.register("chocolate_cream_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CUPCAKE_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_MINI_WAFFLE = ITEMS.register("chocolate_cream_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_MINI_WAFFLE_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CUPCAKE_BASE = ITEMS.register("chocolate_cupcake_base",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CUPCAKE_BASE_FOOD)));
    public static final FoodProperties CHOCOLATE_CUPCAKE_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_DONUT_BASE = ITEMS.register("chocolate_donut_base",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_DONUT_BASE_FOOD)));
    public static final FoodProperties CHOCOLATE_DONUT_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_DONUT_HOLE = ITEMS.register("chocolate_donut_hole",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_DONUT_HOLE_FOOD)));
    public static final FoodProperties CHOCOLATE_DONUT_HOLE_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_DONUT_HOLE_SUGAR = ITEMS.register("chocolate_donut_hole_sugar",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_DONUT_HOLE_SUGAR_FOOD)));
    public static final FoodProperties CHOCOLATE_DONUT_HOLE_SUGAR_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.8f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_DONUT_SUGAR = ITEMS.register("chocolate_donut_sugar",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_DONUT_SUGAR_FOOD)));
    public static final FoodProperties CHOCOLATE_DONUT_SUGAR_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CHOCOLATE_SWEET_ROLL = ITEMS.register("chocolate_cream_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_SWEET_ROLL = ITEMS.register("chocolate_cream_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_SWEET_ROLL_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_FUDGE = ITEMS.register("chocolate_fudge",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_FUDGE_FOOD)));
    public static final FoodProperties CHOCOLATE_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_APPLE = ITEMS.register("chocolate_apple",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_APPLE_FOOD)));
    public static final FoodProperties CHOCOLATE_APPLE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CHOCOLATE_DONUT = ITEMS.register("chocolate_cream_chocolate_donut",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CHOCOLATE_DONUT_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CHOCOLATE_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_DONUT = ITEMS.register("chocolate_cream_donut",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_DONUT_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER = ITEMS.register("chocolate_graham_cracker",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_APPLE_ICE_CREAM = ITEMS.register("chocolate_graham_cracker_apple_ice_cream",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_APPLE_ICE_CREAM_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_APPLE_ICE_CREAM_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_BERRY_ICE_CREAM = ITEMS.register("chocolate_graham_cracker_berry_ice_cream",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_BERRY_ICE_CREAM_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_BERRY_ICE_CREAM_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_CHOCOLATE_ICE_CREAM = ITEMS.register("chocolate_graham_cracker_chocolate_ice_cream",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_CHOCOLATE_ICE_CREAM_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_CHOCOLATE_ICE_CREAM_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_CHORUS_FRUIT_ICE_CREAM = ITEMS.register("chocolate_graham_cracker_chorus_fruit_ice_cream",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_CHORUS_FRUIT_ICE_CREAM_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_CHORUS_FRUIT_ICE_CREAM_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_GLOW_BERRY_ICE_CREAM = ITEMS.register("chocolate_graham_cracker_glow_berry_ice_cream",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_GLOW_BERRY_ICE_CREAM_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_GLOW_BERRY_ICE_CREAM_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_ICE_CREAM = ITEMS.register("chocolate_graham_cracker_ice_cream",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_ICE_CREAM_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_ICE_CREAM_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_MELON_ICE_CREAM = ITEMS.register("chocolate_graham_cracker_melon_ice_cream",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_MELON_ICE_CREAM_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_MELON_ICE_CREAM_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_1 = ITEMS.register("chocolate_graham_cracker_neapolitan_scoop_1",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_1_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_1_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_2 = ITEMS.register("chocolate_graham_cracker_neapolitan_scoop_2",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_2_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_2_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_3 = ITEMS.register("chocolate_graham_cracker_neapolitan_scoop_3",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_3_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_NEAPOLITAN_SCOOP_3_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = ITEMS.register("chocolate_graham_cracker_pie_crust",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST_FOOD)));
    public static final FoodProperties CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_ICE_CREAM_CONE = ITEMS.register("chocolate_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties CHOCOLATE_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CHOCOLATE_ICE_CREAM_SANDWICH = ITEMS.register("chocolate_ice_cream_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_ICE_CREAM_SANDWICH_FOOD)));
    public static final FoodProperties CHOCOLATE_ICE_CREAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_PASTRY = ITEMS.register("chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_PASTRY_BASE = ITEMS.register("chocolate_pastry_base",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_PASTRY_BASE_FOOD)));
    public static final FoodProperties CHOCOLATE_PASTRY_BASE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_PIE_GRAHAM_CRACKER_SLICE = ITEMS.register("chocolate_pie_graham_cracker_slice",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_PIE_GRAHAM_CRACKER_SLICE_FOOD)));
    public static final FoodProperties CHOCOLATE_PIE_GRAHAM_CRACKER_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CREAM_CAKE_SLICE_CHORUS_FRUIT = ITEMS.register("cream_cake_slice_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CAKE_SLICE_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties CREAM_CAKE_SLICE_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> CREAM_CAKE_SLICE_GLOW_BERRY = ITEMS.register("cream_cake_slice_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.CREAM_CAKE_SLICE_GLOW_BERRY_FOOD)));
    public static final FoodProperties CREAM_CAKE_SLICE_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> CARAMEL = ITEMS.register("caramel",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_FOOD)));
    public static final FoodProperties CARAMEL_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.8f).fast()
            .build();

    public static final DeferredItem<Item> CARAMEL_APPLE_SLICE = ITEMS.register("caramel_apple_slice",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_APPLE_SLICE_FOOD)));
    public static final FoodProperties CARAMEL_APPLE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CARAMEL_CHIP_CHOCOLATE_COOKIE = ITEMS.register("caramel_chip_chocolate_cookie",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_CHIP_CHOCOLATE_COOKIE_FOOD)));
    public static final FoodProperties CARAMEL_CHIP_CHOCOLATE_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.1f)
            .build();

    public static final DeferredItem<Item> CARAMEL_CHIP_COOKIE = ITEMS.register("caramel_chip_cookie",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_CHIP_COOKIE_FOOD)));
    public static final FoodProperties CARAMEL_CHIP_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CARAMEL_CHIP_ICE_CREAM_CONE = ITEMS.register("caramel_chip_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_CHIP_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties CARAMEL_CHIP_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CARAMEL_CHIP_MUFFIN = ITEMS.register("caramel_chip_muffin",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_CHIP_MUFFIN_FOOD)));
    public static final FoodProperties CARAMEL_CHIP_MUFFIN_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CARAMEL_CHIP_MINI_WAFFLE = ITEMS.register("caramel_chip_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_CHIP_MINI_WAFFLE_FOOD)));
    public static final FoodProperties CARAMEL_CHIP_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> CARAMEL_CHOCOLATE = ITEMS.register("caramel_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_CHOCOLATE_FOOD)));
    public static final FoodProperties CARAMEL_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_CHOCOLATE_PASTRY = ITEMS.register("caramel_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties CARAMEL_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_DARK_CHOCOLATE = ITEMS.register("caramel_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties CARAMEL_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_FUDGE = ITEMS.register("caramel_fudge",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_FUDGE_FOOD)));
    public static final FoodProperties CARAMEL_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_APPLE = ITEMS.register("caramel_apple",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_APPLE_FOOD)));
    public static final FoodProperties CARAMEL_APPLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_BERRIES = ITEMS.register("caramel_berries",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_BERRIES_FOOD)));
    public static final FoodProperties CARAMEL_BERRIES_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_CHOCOLATE_SWEET_ROLL = ITEMS.register("caramel_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties CARAMEL_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_SWEET_ROLL = ITEMS.register("caramel_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_SWEET_ROLL_FOOD)));
    public static final FoodProperties CARAMEL_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_PASTRY = ITEMS.register("caramel_pastry",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_PASTRY_FOOD)));
    public static final FoodProperties CARAMEL_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_POPCORN = ITEMS.register("caramel_popcorn",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_POPCORN_FOOD)));
    public static final FoodProperties CARAMEL_POPCORN_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.8f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_TOAST = ITEMS.register("caramel_toast",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_TOAST_FOOD)));
    public static final FoodProperties CARAMEL_TOAST_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CARAMEL_WHITE_CHOCOLATE = ITEMS.register("caramel_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.CARAMEL_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties CARAMEL_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER = ITEMS.register("cheeseburger",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_FOOD)));
    public static final FoodProperties CHEESEBURGER_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_BACON = ITEMS.register("cheeseburger_bacon",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_BACON_FOOD)));
    public static final FoodProperties CHEESEBURGER_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_BACON_LETTUCE = ITEMS.register("cheeseburger_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_BACON_LETTUCE_FOOD)));
    public static final FoodProperties CHEESEBURGER_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_BACON_LETTUCE_TOMATO = ITEMS.register("cheeseburger_bacon_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_BACON_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties CHEESEBURGER_BACON_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(13).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_LETTUCE = ITEMS.register("cheeseburger_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_LETTUCE_FOOD)));
    public static final FoodProperties CHEESEBURGER_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_LETTUCE_TOMATO = ITEMS.register("cheeseburger_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties CHEESEBURGER_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_ONION = ITEMS.register("cheeseburger_onion",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_ONION_FOOD)));
    public static final FoodProperties CHEESEBURGER_ONION_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_ONION_BACON = ITEMS.register("cheeseburger_onion_bacon",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_ONION_BACON_FOOD)));
    public static final FoodProperties CHEESEBURGER_ONION_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(13).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_ONION_BACON_LETTUCE = ITEMS.register("cheeseburger_onion_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_ONION_BACON_LETTUCE_FOOD)));
    public static final FoodProperties CHEESEBURGER_ONION_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(13).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_ONION_LETTUCE = ITEMS.register("cheeseburger_onion_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_ONION_LETTUCE_FOOD)));
    public static final FoodProperties CHEESEBURGER_ONION_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_ONION_LETTUCE_TOMATO = ITEMS.register("cheeseburger_onion_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_ONION_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties CHEESEBURGER_ONION_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESEBURGER_TOMATO = ITEMS.register("cheeseburger_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHEESEBURGER_TOMATO_FOOD)));
    public static final FoodProperties CHEESEBURGER_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESECAKE_SLICE = ITEMS.register("cheesecake_slice",
            () -> new Item(new Item.Properties().food(ModItems.CHEESECAKE_SLICE_FOOD)));
    public static final FoodProperties CHEESECAKE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE = ITEMS.register("beef_bun_cheese",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_BACON = ITEMS.register("beef_bun_cheese_bacon",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_BACON_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_BACON_LETTUCE = ITEMS.register("beef_bun_cheese_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_BACON_LETTUCE_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_BACON_LETTUCE_TOMATO = ITEMS.register("beef_bun_cheese_bacon_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_BACON_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_BACON_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_LETTUCE = ITEMS.register("beef_bun_cheese_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_LETTUCE_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_LETTUCE_TOMATO = ITEMS.register("beef_bun_cheese_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION = ITEMS.register("beef_bun_cheese_onion",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_ONION_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_ONION_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION_BACON = ITEMS.register("beef_bun_cheese_onion_bacon",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_ONION_BACON_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_ONION_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION_BACON_LETTUCE = ITEMS.register("beef_bun_cheese_onion_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_ONION_BACON_LETTUCE_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_ONION_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION_LETTUCE = ITEMS.register("beef_bun_cheese_onion_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_ONION_LETTUCE_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_ONION_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_ONION_LETTUCE_TOMATO = ITEMS.register("beef_bun_cheese_onion_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_ONION_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_ONION_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(1.1f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE = ITEMS.register("chicken_bun_cheese",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_CHEESE_FOOD)));
    public static final FoodProperties CHICKEN_BUN_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_BACON_LETTUCE = ITEMS.register("chicken_bun_cheese_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_CHEESE_BACON_LETTUCE_FOOD)));
    public static final FoodProperties CHICKEN_BUN_CHEESE_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(13).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_LETTUCE = ITEMS.register("chicken_bun_cheese_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_CHEESE_LETTUCE_FOOD)));
    public static final FoodProperties CHICKEN_BUN_CHEESE_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_LETTUCE_TOMATO = ITEMS.register("chicken_bun_cheese_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_CHEESE_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties CHICKEN_BUN_CHEESE_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BUN_CHEESE = ITEMS.register("eggplant_bun_cheese",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BUN_CHEESE_FOOD)));
    public static final FoodProperties EGGPLANT_BUN_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BUN_CHEESE_LETTUCE = ITEMS.register("eggplant_bun_cheese_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BUN_CHEESE_LETTUCE_FOOD)));
    public static final FoodProperties EGGPLANT_BUN_CHEESE_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> EGGPLANT_BUN_CHEESE_LETTUCE_TOMATO = ITEMS.register("eggplant_bun_cheese_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.EGGPLANT_BUN_CHEESE_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties EGGPLANT_BUN_CHEESE_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_CHEESE = ITEMS.register("sausage_biscuit_cheese",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_CHEESE_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(16).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> SAUSAGE_BISCUIT_SANDWICH_CHEESE = ITEMS.register("sausage_biscuit_sandwich_cheese",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGE_BISCUIT_SANDWICH_CHEESE_FOOD)));
    public static final FoodProperties SAUSAGE_BISCUIT_SANDWICH_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(21).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHEESE_BISCUIT = ITEMS.register("cheese_biscuit",
            () -> new Item(new Item.Properties().food(ModItems.CHEESE_BISCUIT_FOOD)));
    public static final FoodProperties CHEESE_BISCUIT_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> CHEESE_BISCUIT_SANDWICH = ITEMS.register("cheese_biscuit_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.CHEESE_BISCUIT_SANDWICH_FOOD)));
    public static final FoodProperties CHEESE_BISCUIT_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> CHEESE_CALZONE = ITEMS.register("cheese_calzone",
            () -> new Item(new Item.Properties().food(ModItems.CHEESE_CALZONE_FOOD)));
    public static final FoodProperties CHEESE_CALZONE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.4f).fast()
            .build();

    public static final DeferredItem<Item> CHEESE_PIZZA_SLICE = ITEMS.register("cheese_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.CHEESE_PIZZA_SLICE_FOOD)));
    public static final FoodProperties CHEESE_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> CHEESE_SANDWICH = ITEMS.register("cheese_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.CHEESE_SANDWICH_FOOD)));
    public static final FoodProperties CHEESE_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> CHEESE_SLICE = ITEMS.register("cheese_slice",
            () -> new Item(new Item.Properties().food(ModItems.CHEESE_SLICE_FOOD)));
    public static final FoodProperties CHEESE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.1f).fast()
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN = ITEMS.register("chicken_bun",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_FOOD)));
    public static final FoodProperties CHICKEN_BUN_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_BACON = ITEMS.register("chicken_bun_bacon",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_BACON_FOOD)));
    public static final FoodProperties CHICKEN_BUN_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_BACON_LETTUCE = ITEMS.register("chicken_bun_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_BACON_LETTUCE_FOOD)));
    public static final FoodProperties CHICKEN_BUN_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_BACON = ITEMS.register("chicken_bun_cheese_bacon",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_CHEESE_BACON_FOOD)));
    public static final FoodProperties CHICKEN_BUN_CHEESE_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_CHEESE_TOMATO = ITEMS.register("chicken_bun_cheese_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_CHEESE_TOMATO_FOOD)));
    public static final FoodProperties CHICKEN_BUN_CHEESE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_LETTUCE = ITEMS.register("chicken_bun_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_LETTUCE_FOOD)));
    public static final FoodProperties CHICKEN_BUN_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_LETTUCE_TOMATO = ITEMS.register("chicken_bun_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties CHICKEN_BUN_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BUN_TOMATO = ITEMS.register("chicken_bun_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BUN_TOMATO_FOOD)));
    public static final FoodProperties CHICKEN_BUN_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BURGER = ITEMS.register("chicken_burger",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BURGER_FOOD)));
    public static final FoodProperties CHICKEN_BURGER_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BURGER_BACON = ITEMS.register("chicken_burger_bacon",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BURGER_BACON_FOOD)));
    public static final FoodProperties CHICKEN_BURGER_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(13).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BURGER_BACON_LETTUCE = ITEMS.register("chicken_burger_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BURGER_BACON_LETTUCE_FOOD)));
    public static final FoodProperties CHICKEN_BURGER_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BURGER_LETTUCE = ITEMS.register("chicken_burger_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BURGER_LETTUCE_FOOD)));
    public static final FoodProperties CHICKEN_BURGER_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BURGER_LETTUCE_TOMATO = ITEMS.register("chicken_burger_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BURGER_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties CHICKEN_BURGER_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_BURGER_TOMATO = ITEMS.register("chicken_burger_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_BURGER_TOMATO_FOOD)));
    public static final FoodProperties CHICKEN_BURGER_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER = ITEMS.register("chicken_cheeseburger",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_CHEESEBURGER_FOOD)));
    public static final FoodProperties CHICKEN_CHEESEBURGER_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_BACON = ITEMS.register("chicken_cheeseburger_bacon",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_CHEESEBURGER_BACON_FOOD)));
    public static final FoodProperties CHICKEN_CHEESEBURGER_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(13).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_BACON_LETTUCE = ITEMS.register("chicken_cheeseburger_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_CHEESEBURGER_BACON_LETTUCE_FOOD)));
    public static final FoodProperties CHICKEN_CHEESEBURGER_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(15).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_LETTUCE = ITEMS.register("chicken_cheeseburger_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_CHEESEBURGER_LETTUCE_FOOD)));
    public static final FoodProperties CHICKEN_CHEESEBURGER_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_LETTUCE_TOMATO = ITEMS.register("chicken_cheeseburger_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_CHEESEBURGER_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties CHICKEN_CHEESEBURGER_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_CHEESEBURGER_TOMATO = ITEMS.register("chicken_cheeseburger_tomato",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_CHEESEBURGER_TOMATO_FOOD)));
    public static final FoodProperties CHICKEN_CHEESEBURGER_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_NUGGETS = ITEMS.register("chicken_nuggets",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_NUGGETS_FOOD)));
    public static final FoodProperties CHICKEN_NUGGETS_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> CHICKEN_PATTY = ITEMS.register("chicken_patty",
            () -> new Item(new Item.Properties().food(ModItems.CHICKEN_PATTY_FOOD)));
    public static final FoodProperties CHICKEN_PATTY_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BUTTER_DOUGH_SMALL = ITEMS.register("butter_dough_small",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CACAO_BUTTER = ITEMS.register("cacao_butter",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CACAO_NIBS = ITEMS.register("cacao_nibs",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BUTTER = ITEMS.register("butter",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BUTTER_DOUGH = ITEMS.register("butter_dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_ICE_CREAM_CONE = ITEMS.register("butterscotch_chip_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.BUTTERSCOTCH_CHIP_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties BUTTERSCOTCH_CHIP_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_COOKIE = ITEMS.register("butterscotch_chip_cookie",
            () -> new Item(new Item.Properties().food(ModItems.BUTTERSCOTCH_CHIP_COOKIE_FOOD)));
    public static final FoodProperties BUTTERSCOTCH_CHIP_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.2f)
            .build();

    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE = ITEMS.register("butterscotch_chip_chocolate_cookie",
            () -> new Item(new Item.Properties().food(ModItems.BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE_FOOD)));
    public static final FoodProperties BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.3f)
            .build();

    public static final DeferredItem<Item> BUTTERED_TOAST = ITEMS.register("buttered_toast",
            () -> new Item(new Item.Properties().food(ModItems.BUTTERED_TOAST_FOOD)));
    public static final FoodProperties BUTTERED_TOAST_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f)
            .build();

    public static final DeferredItem<Item> BUTTERSCOTCH = ITEMS.register("butterscotch",
            () -> new Item(new Item.Properties().food(ModItems.BUTTERSCOTCH_FOOD)));
    public static final FoodProperties BUTTERSCOTCH_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.0f).fast()
            .build();

    public static final DeferredItem<Item> BUN = ITEMS.register("bun",
            () -> new Item(new Item.Properties().food(ModItems.BUN_FOOD)));
    public static final FoodProperties BUN_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.5f).fast()
            .build();

    public static final DeferredItem<Item> BREAKFAST_BAR = ITEMS.register("breakfast_bar",
            () -> new Item(new Item.Properties().food(ModItems.BREAKFAST_BAR_FOOD)));
    public static final FoodProperties BREAKFAST_BAR_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(4.1f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> SAUSAGES = ITEMS.register("sausages",
            () -> new Item(new Item.Properties().food(ModItems.SAUSAGES_FOOD)));
    public static final FoodProperties SAUSAGES_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BROWN_GELATIN_DESSERT_SLICE = ITEMS.register("brown_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.BROWN_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties BROWN_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_MUFFIN = ITEMS.register("butterscotch_chip_muffin",
            () -> new Item(new Item.Properties().food(ModItems.BUTTERSCOTCH_CHIP_MUFFIN_FOOD)));
    public static final FoodProperties BUTTERSCOTCH_CHIP_MUFFIN_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_MINI_WAFFLE = ITEMS.register("butterscotch_chip_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.BUTTERSCOTCH_CHIP_MINI_WAFFLE_FOOD)));
    public static final FoodProperties BUTTERSCOTCH_CHIP_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BUTTERSCOTCH_FUDGE = ITEMS.register("butterscotch_fudge",
            () -> new Item(new Item.Properties().food(ModItems.BUTTERSCOTCH_FUDGE_FOOD)));
    public static final FoodProperties BUTTERSCOTCH_FUDGE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 0), 1.0f)
            .build();



    public static final DeferredItem<Item> BREAD_CRUMBS = ITEMS.register("bread_crumbs",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_DONUT = ITEMS.register("berry_cream_chocolate_donut",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CHOCOLATE_DONUT_FOOD)));
    public static final FoodProperties BERRY_CREAM_CHOCOLATE_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_DONUT = ITEMS.register("berry_cream_donut",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_DONUT_FOOD)));
    public static final FoodProperties BERRY_CREAM_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_PASTRY = ITEMS.register("berry_cream_pastry",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_PASTRY_FOOD)));
    public static final FoodProperties BERRY_CREAM_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_WHITE_CHOCOLATE = ITEMS.register("berry_cream_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties BERRY_CREAM_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BERRY_ICE_CREAM_CONE = ITEMS.register("berry_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties BERRY_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.9f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BERRY_ICE_CREAM_SANDWICH = ITEMS.register("berry_ice_cream_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_ICE_CREAM_SANDWICH_FOOD)));
    public static final FoodProperties BERRY_ICE_CREAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> BERRY_JAM_SANDWICH = ITEMS.register("berry_jam_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_JAM_SANDWICH_FOOD)));
    public static final FoodProperties BERRY_JAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BERRY_PIE_SLICE = ITEMS.register("berry_pie_slice",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_PIE_SLICE_FOOD)));
    public static final FoodProperties BERRY_PIE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BISCUIT = ITEMS.register("biscuit",
            () -> new Item(new Item.Properties().food(ModItems.BISCUIT_FOOD)));
    public static final FoodProperties BISCUIT_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f).fast()
            .build();

    public static final DeferredItem<Item> BLACK_GELATIN_DESSERT_SLICE = ITEMS.register("black_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.BLACK_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties BLACK_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BLUE_GELATIN_DESSERT_SLICE = ITEMS.register("blue_gelatin_dessert_slice",
            () -> new Item(new Item.Properties().food(ModItems.BLUE_GELATIN_DESSERT_SLICE_FOOD)));
    public static final FoodProperties BLUE_GELATIN_DESSERT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BREAD_CARROT = ITEMS.register("bread_carrot",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_CARROT_FOOD)));
    public static final FoodProperties BREAD_CARROT_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> BREAD_FRIED_EGG = ITEMS.register("bread_fried_egg",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_FRIED_EGG_FOOD)));
    public static final FoodProperties BREAD_FRIED_EGG_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> BREAD_LETTUCE = ITEMS.register("bread_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_LETTUCE_FOOD)));
    public static final FoodProperties BREAD_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> BREAD_LETTUCE_CARROT = ITEMS.register("bread_lettuce_carrot",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_LETTUCE_CARROT_FOOD)));
    public static final FoodProperties BREAD_LETTUCE_CARROT_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE = ITEMS.register("bread_slice",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_FOOD)));
    public static final FoodProperties BREAD_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f).fast()
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_APPLE_JAM = ITEMS.register("bread_slice_apple_jam",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_APPLE_JAM_FOOD)));
    public static final FoodProperties BREAD_SLICE_APPLE_JAM_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_BACON = ITEMS.register("bread_slice_bacon",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_BACON_FOOD)));
    public static final FoodProperties BREAD_SLICE_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_BACON_LETTUCE = ITEMS.register("bread_slice_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_BACON_LETTUCE_FOOD)));
    public static final FoodProperties BREAD_SLICE_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_BACON_LETTUCE_TOMATO = ITEMS.register("bread_slice_bacon_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_BACON_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties BREAD_SLICE_BACON_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_BEETROOT = ITEMS.register("bread_slice_beetroot",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_BEETROOT_FOOD)));
    public static final FoodProperties BREAD_SLICE_BEETROOT_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_BEETROOT_LETTUCE = ITEMS.register("bread_slice_beetroot_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_BEETROOT_LETTUCE_FOOD)));
    public static final FoodProperties BREAD_SLICE_BEETROOT_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_BERRY_JAM = ITEMS.register("bread_slice_berry_jam",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_BERRY_JAM_FOOD)));
    public static final FoodProperties BREAD_SLICE_BERRY_JAM_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_CHEESE = ITEMS.register("bread_slice_cheese",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_CHEESE_FOOD)));
    public static final FoodProperties BREAD_SLICE_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_CHORUS_FRUIT_JAM = ITEMS.register("bread_slice_chorus_fruit_jam",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_CHORUS_FRUIT_JAM_FOOD)));
    public static final FoodProperties BREAD_SLICE_CHORUS_FRUIT_JAM_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_GLOW_BERRY_JAM = ITEMS.register("bread_slice_glow_berry_jam",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_GLOW_BERRY_JAM_FOOD)));
    public static final FoodProperties BREAD_SLICE_GLOW_BERRY_JAM_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_HONEY = ITEMS.register("bread_slice_honey",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_HONEY_FOOD)));
    public static final FoodProperties BREAD_SLICE_HONEY_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_LETTUCE = ITEMS.register("bread_slice_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_LETTUCE_FOOD)));
    public static final FoodProperties BREAD_SLICE_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_LETTUCE_TOMATO = ITEMS.register("bread_slice_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties BREAD_SLICE_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_MELON_JAM = ITEMS.register("bread_slice_melon_jam",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_MELON_JAM_FOOD)));
    public static final FoodProperties BREAD_SLICE_MELON_JAM_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.5f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_MUTTON = ITEMS.register("bread_slice_mutton",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_MUTTON_FOOD)));
    public static final FoodProperties BREAD_SLICE_MUTTON_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_MUTTON_BEETROOT = ITEMS.register("bread_slice_mutton_beetroot",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_MUTTON_BEETROOT_FOOD)));
    public static final FoodProperties BREAD_SLICE_MUTTON_BEETROOT_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_PEANUT_BUTTER = ITEMS.register("bread_slice_peanut_butter",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_PEANUT_BUTTER_FOOD)));
    public static final FoodProperties BREAD_SLICE_PEANUT_BUTTER_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> BREAD_SLICE_TOMATO = ITEMS.register("bread_slice_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BREAD_SLICE_TOMATO_FOOD)));
    public static final FoodProperties BREAD_SLICE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> BAR_OF_DARK_CHOCOLATE = ITEMS.register("bar_of_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.BAR_OF_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties BAR_OF_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.2f)
            .build();

    public static final DeferredItem<Item> GYRO_MEAT_SLICE = ITEMS.register("gyro_meat_slice",
            () -> new Item(new Item.Properties().food(ModItems.GYRO_MEAT_SLICE_FOOD)));
    public static final FoodProperties GYRO_MEAT_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.1f)
            .build();

    public static final DeferredItem<Item> APPLE_CHEESECAKE_SLICE = ITEMS.register("apple_cheesecake_slice",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CHEESECAKE_SLICE_FOOD)));
    public static final FoodProperties APPLE_CHEESECAKE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BACON_PIZZA_SLICE = ITEMS.register("bacon_pizza_slice",
            () -> new Item(new Item.Properties().food(ModItems.BACON_PIZZA_SLICE_FOOD)));
    public static final FoodProperties BACON_PIZZA_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL = ITEMS.register("chocolate_cream_cake_slice_caramel",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL_FOOD)));
    public static final FoodProperties CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.4f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BAR_OF_WHITE_CHOCOLATE = ITEMS.register("bar_of_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.BAR_OF_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties BAR_OF_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN = ITEMS.register("beef_bun",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_FOOD)));
    public static final FoodProperties BEEF_BUN_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_BACON = ITEMS.register("beef_bun_bacon",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_BACON_FOOD)));
    public static final FoodProperties BEEF_BUN_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_BACON_LETTUCE = ITEMS.register("beef_bun_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_BACON_LETTUCE_FOOD)));
    public static final FoodProperties BEEF_BUN_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_BACON_LETTUCE_TOMATO = ITEMS.register("beef_bun_bacon_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_BACON_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties BEEF_BUN_BACON_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_LETTUCE = ITEMS.register("beef_bun_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_LETTUCE_FOOD)));
    public static final FoodProperties BEEF_BUN_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_LETTUCE_TOMATO = ITEMS.register("beef_bun_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties BEEF_BUN_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_ONION = ITEMS.register("beef_bun_onion",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_ONION_FOOD)));
    public static final FoodProperties BEEF_BUN_ONION_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_ONION_BACON = ITEMS.register("beef_bun_onion_bacon",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_ONION_BACON_FOOD)));
    public static final FoodProperties BEEF_BUN_ONION_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_ONION_BACON_LETTUCE = ITEMS.register("beef_bun_onion_bacon_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_ONION_BACON_LETTUCE_FOOD)));
    public static final FoodProperties BEEF_BUN_ONION_BACON_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_ONION_LETTUCE = ITEMS.register("beef_bun_onion_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_ONION_LETTUCE_FOOD)));
    public static final FoodProperties BEEF_BUN_ONION_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_ONION_LETTUCE_TOMATO = ITEMS.register("beef_bun_onion_lettuce_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_ONION_LETTUCE_TOMATO_FOOD)));
    public static final FoodProperties BEEF_BUN_ONION_LETTUCE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(1.0f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_PEANUT_BUTTER = ITEMS.register("beef_bun_peanut_butter",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_PEANUT_BUTTER_FOOD)));
    public static final FoodProperties BEEF_BUN_PEANUT_BUTTER_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_PEANUT_BUTTER_BACON = ITEMS.register("beef_bun_peanut_butter_bacon",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_PEANUT_BUTTER_BACON_FOOD)));
    public static final FoodProperties BEEF_BUN_PEANUT_BUTTER_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_CHEESE_TOMATO = ITEMS.register("beef_bun_cheese_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_CHEESE_TOMATO_FOOD)));
    public static final FoodProperties BEEF_BUN_CHEESE_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT = ITEMS.register("berry_cream_cake_slice_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT_FOOD)));
    public static final FoodProperties BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.3f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CAKE_SLICE_GLOW_BERRY = ITEMS.register("berry_cream_cake_slice_glow_berry",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CAKE_SLICE_GLOW_BERRY_FOOD)));
    public static final FoodProperties BERRY_CREAM_CAKE_SLICE_GLOW_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CAKE_SLICE_SWEET_BERRY = ITEMS.register("berry_cream_cake_slice_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CAKE_SLICE_SWEET_BERRY_FOOD)));
    public static final FoodProperties BERRY_CREAM_CAKE_SLICE_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE = ITEMS.register("berry_cream_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CHOCOLATE_FOOD)));
    public static final FoodProperties BERRY_CREAM_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_PASTRY = ITEMS.register("berry_cream_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties BERRY_CREAM_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_DARK_CHOCOLATE = ITEMS.register("berry_cream_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties BERRY_CREAM_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_SWEET_ROLL = ITEMS.register("berry_cream_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties BERRY_CREAM_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY = ITEMS.register("berry_cream_chocolate_sweet_roll_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY_FOOD)));
    public static final FoodProperties BERRY_CREAM_CHOCOLATE_SWEET_ROLL_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CHOCOLATE_CUPCAKE = ITEMS.register("berry_cream_chocolate_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CHOCOLATE_CUPCAKE_FOOD)));
    public static final FoodProperties BERRY_CREAM_CHOCOLATE_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.1f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_CUPCAKE = ITEMS.register("berry_cream_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_CUPCAKE_FOOD)));
    public static final FoodProperties BERRY_CREAM_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_MINI_WAFFLE = ITEMS.register("berry_cream_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_MINI_WAFFLE_FOOD)));
    public static final FoodProperties BERRY_CREAM_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.9f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_MINI_WAFFLE_SWEET_BERRY = ITEMS.register("berry_cream_mini_waffle_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_MINI_WAFFLE_SWEET_BERRY_FOOD)));
    public static final FoodProperties BERRY_CREAM_MINI_WAFFLE_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.9f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_SWEET_ROLL = ITEMS.register("berry_cream_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_SWEET_ROLL_FOOD)));
    public static final FoodProperties BERRY_CREAM_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_SWEET_ROLL_SWEET_BERRY = ITEMS.register("berry_cream_sweet_roll_sweet_berry",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_CREAM_SWEET_ROLL_SWEET_BERRY_FOOD)));
    public static final FoodProperties BERRY_CREAM_SWEET_ROLL_SWEET_BERRY_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BEEF_BUN_TOMATO = ITEMS.register("beef_bun_tomato",
            () -> new Item(new Item.Properties().food(ModItems.BEEF_BUN_TOMATO_FOOD)));
    public static final FoodProperties BEEF_BUN_TOMATO_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> BACON_BITS = ITEMS.register("bacon_bits",
            () -> new Item(new Item.Properties().food(ModItems.BACON_BITS_FOOD)));
    public static final FoodProperties BACON_BITS_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.4f)
            .build();

    public static final DeferredItem<Item> BACON_CALZONE = ITEMS.register("bacon_calzone",
            () -> new Item(new Item.Properties().food(ModItems.BACON_CALZONE_FOOD)));
    public static final FoodProperties BACON_CALZONE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> BACON_SANDWICH = ITEMS.register("bacon_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.BACON_SANDWICH_FOOD)));
    public static final FoodProperties BACON_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BACON_SANDWICH_LETTUCE = ITEMS.register("bacon_sandwich_lettuce",
            () -> new Item(new Item.Properties().food(ModItems.BACON_SANDWICH_LETTUCE_FOOD)));
    public static final FoodProperties BACON_SANDWICH_LETTUCE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> BAKED_POTATO_BUTTER = ITEMS.register("baked_potato_butter",
            () -> new Item(new Item.Properties().food(ModItems.BAKED_POTATO_BUTTER_FOOD)));
    public static final FoodProperties BAKED_POTATO_BUTTER_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.8f)
            .build();

    public static final DeferredItem<Item> BAKED_POTATO_BUTTER_CHEESE = ITEMS.register("baked_potato_butter_cheese",
            () -> new Item(new Item.Properties().food(ModItems.BAKED_POTATO_BUTTER_CHEESE_FOOD)));
    public static final FoodProperties BAKED_POTATO_BUTTER_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BAKED_POTATO_BUTTER_FISH = ITEMS.register("baked_potato_butter_fish",
            () -> new Item(new Item.Properties().food(ModItems.BAKED_POTATO_BUTTER_FISH_FOOD)));
    public static final FoodProperties BAKED_POTATO_BUTTER_FISH_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f)
            .build();

    public static final DeferredItem<Item> BAKED_POTATO_BUTTER_FRIED_EGG = ITEMS.register("baked_potato_butter_fried_egg",
            () -> new Item(new Item.Properties().food(ModItems.BAKED_POTATO_BUTTER_FRIED_EGG_FOOD)));
    public static final FoodProperties BAKED_POTATO_BUTTER_FRIED_EGG_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.7f)
            .build();

    public static final DeferredItem<Item> BAKED_POTATO_BUTTER_MUSHROOM = ITEMS.register("baked_potato_butter_mushroom",
            () -> new Item(new Item.Properties().food(ModItems.BAKED_POTATO_BUTTER_MUSHROOM_FOOD)));
    public static final FoodProperties BAKED_POTATO_BUTTER_MUSHROOM_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.1f)
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE = ITEMS.register("apple_cream_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_CHOCOLATE_FOOD)));
    public static final FoodProperties APPLE_CREAM_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE_PASTRY = ITEMS.register("apple_cream_chocolate_pastry",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_CHOCOLATE_PASTRY_FOOD)));
    public static final FoodProperties APPLE_CREAM_CHOCOLATE_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_DARK_CHOCOLATE = ITEMS.register("apple_cream_dark_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_DARK_CHOCOLATE_FOOD)));
    public static final FoodProperties APPLE_CREAM_DARK_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE_SWEET_ROLL = ITEMS.register("apple_cream_chocolate_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_CHOCOLATE_SWEET_ROLL_FOOD)));
    public static final FoodProperties APPLE_CREAM_CHOCOLATE_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE_CUPCAKE = ITEMS.register("apple_cream_chocolate_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_CHOCOLATE_CUPCAKE_FOOD)));
    public static final FoodProperties APPLE_CREAM_CHOCOLATE_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_CUPCAKE = ITEMS.register("apple_cream_cupcake",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_CUPCAKE_FOOD)));
    public static final FoodProperties APPLE_CREAM_CUPCAKE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_MINI_WAFFLE = ITEMS.register("apple_cream_mini_waffle",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_MINI_WAFFLE_FOOD)));
    public static final FoodProperties APPLE_CREAM_MINI_WAFFLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_SWEET_ROLL = ITEMS.register("apple_cream_sweet_roll",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_SWEET_ROLL_FOOD)));
    public static final FoodProperties APPLE_CREAM_SWEET_ROLL_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_CHOCOLATE_DONUT = ITEMS.register("apple_cream_chocolate_donut",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_CHOCOLATE_DONUT_FOOD)));
    public static final FoodProperties APPLE_CREAM_CHOCOLATE_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_DONUT = ITEMS.register("apple_cream_donut",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_DONUT_FOOD)));
    public static final FoodProperties APPLE_CREAM_DONUT_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_PASTRY = ITEMS.register("apple_cream_pastry",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_PASTRY_FOOD)));
    public static final FoodProperties APPLE_CREAM_PASTRY_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> APPLE_CREAM_WHITE_CHOCOLATE = ITEMS.register("apple_cream_white_chocolate",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_CREAM_WHITE_CHOCOLATE_FOOD)));
    public static final FoodProperties APPLE_CREAM_WHITE_CHOCOLATE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f)
            .build();

    public static final DeferredItem<Item> APPLE_ICE_CREAM_CONE = ITEMS.register("apple_ice_cream_cone",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_ICE_CREAM_CONE_FOOD)));
    public static final FoodProperties APPLE_ICE_CREAM_CONE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> APPLE_ICE_CREAM_SANDWICH = ITEMS.register("apple_ice_cream_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_ICE_CREAM_SANDWICH_FOOD)));
    public static final FoodProperties APPLE_ICE_CREAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f)
            .build();

    public static final DeferredItem<Item> APPLE_JAM_SANDWICH = ITEMS.register("apple_jam_sandwich",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_JAM_SANDWICH_FOOD)));
    public static final FoodProperties APPLE_JAM_SANDWICH_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast()
            .build();

    public static final DeferredItem<Item> APPLE_SLICE = ITEMS.register("apple_slice",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_SLICE_FOOD)));
    public static final FoodProperties APPLE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.4f).fast()
            .build();


// Food - Bowl //

    public static final DeferredItem<Item> APPLE_ICE_CREAM_BOWL = ITEMS.register("apple_ice_cream_bowl",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_ICE_CREAM_BOWL_FOOD).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final FoodProperties APPLE_ICE_CREAM_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.1f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> BERRY_ICE_CREAM_BOWL = ITEMS.register("berry_ice_cream_bowl",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_ICE_CREAM_BOWL_FOOD).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final FoodProperties BERRY_ICE_CREAM_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> BREAKFAST_PLATE = ITEMS.register("breakfast_plate",
            () -> new Item(new Item.Properties().food(ModItems.BREAKFAST_PLATE_FOOD).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final FoodProperties BREAKFAST_PLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(1.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_ICE_CREAM_BOWL = ITEMS.register("chocolate_ice_cream_bowl",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_ICE_CREAM_BOWL_FOOD).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final FoodProperties CHOCOLATE_ICE_CREAM_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.2f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_ICE_CREAM_BOWL = ITEMS.register("chorus_fruit_ice_cream_bowl",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_ICE_CREAM_BOWL_FOOD).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final FoodProperties CHORUS_FRUIT_ICE_CREAM_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.2f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> FRIED_EGG_PLATE = ITEMS.register("fried_egg_plate",
            () -> new Item(new Item.Properties().food(ModItems.FRIED_EGG_PLATE_FOOD).stacksTo(16)));
    public static final FoodProperties FRIED_EGG_PLATE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.4f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_ICE_CREAM_BOWL = ITEMS.register("glow_berry_ice_cream_bowl",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_ICE_CREAM_BOWL_FOOD).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final FoodProperties GLOW_BERRY_ICE_CREAM_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> HASH_BROWN_FRIED_EGG_PLATE = ITEMS.register("hash_brown_fried_egg_plate",
            () -> new Item(new Item.Properties().food(ModItems.HASH_BROWN_FRIED_EGG_PLATE_FOOD).stacksTo(16)));
    public static final FoodProperties HASH_BROWN_FRIED_EGG_PLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> HASH_BROWN_PLATE = ITEMS.register("hash_brown_plate",
            () -> new Item(new Item.Properties().food(ModItems.HASH_BROWN_PLATE_FOOD).stacksTo(16)));
    public static final FoodProperties HASH_BROWN_PLATE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> HASH_BROWN_TOAST_PLATE = ITEMS.register("hash_brown_toast_plate",
            () -> new Item(new Item.Properties().food(ModItems.HASH_BROWN_TOAST_PLATE_FOOD).stacksTo(16)));
    public static final FoodProperties HASH_BROWN_TOAST_PLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> ICE_CREAM_BOWL = ITEMS.register("ice_cream_bowl",
            () -> new Item(new Item.Properties().food(ModItems.ICE_CREAM_BOWL_FOOD).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final FoodProperties ICE_CREAM_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.1f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> MACARONI_BOWL = ITEMS.register("macaroni_bowl",
            () -> new Item(new Item.Properties().food(ModItems.MACARONI_BOWL_FOOD).stacksTo(16)));
    public static final FoodProperties MACARONI_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.5f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> MACARONI_BOWL_BACON = ITEMS.register("macaroni_bowl_bacon",
            () -> new Item(new Item.Properties().food(ModItems.MACARONI_BOWL_BACON_FOOD).stacksTo(16)));
    public static final FoodProperties MACARONI_BOWL_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1800, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> MACARONI_BOWL_CHEESE = ITEMS.register("macaroni_bowl_cheese",
            () -> new Item(new Item.Properties().food(ModItems.MACARONI_BOWL_CHEESE_FOOD).stacksTo(16)));
    public static final FoodProperties MACARONI_BOWL_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.6f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> MACARONI_BOWL_CHEESE_BACON = ITEMS.register("macaroni_bowl_cheese_bacon",
            () -> new Item(new Item.Properties().food(ModItems.MACARONI_BOWL_CHEESE_BACON_FOOD).stacksTo(16)));
    public static final FoodProperties MACARONI_BOWL_CHEESE_BACON_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 2400, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> MACARONI_BOWL_CHEESE_SAUSAGE = ITEMS.register("macaroni_bowl_cheese_sausage",
            () -> new Item(new Item.Properties().food(ModItems.MACARONI_BOWL_CHEESE_SAUSAGE_FOOD).stacksTo(16)));
    public static final FoodProperties MACARONI_BOWL_CHEESE_SAUSAGE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 2400, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> MACARONI_BOWL_SAUSAGE = ITEMS.register("macaroni_bowl_sausage",
            () -> new Item(new Item.Properties().food(ModItems.MACARONI_BOWL_SAUSAGE_FOOD).stacksTo(16)));
    public static final FoodProperties MACARONI_BOWL_SAUSAGE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3000, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> MELON_ICE_CREAM_BOWL = ITEMS.register("melon_ice_cream_bowl",
            () -> new Item(new Item.Properties().food(ModItems.MELON_ICE_CREAM_BOWL_FOOD).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final FoodProperties MELON_ICE_CREAM_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.2f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> NACHO_BOWL = ITEMS.register("nacho_bowl",
            () -> new Item(new Item.Properties().food(ModItems.NACHO_BOWL_FOOD).stacksTo(16)));
    public static final FoodProperties NACHO_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE = ITEMS.register("pasta_plate",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_BUTTER = ITEMS.register("pasta_plate_butter",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_BUTTER_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_BUTTER_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_CHEESE = ITEMS.register("pasta_plate_cheese",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_CHEESE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_CHEESE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_CHEESE_TOMATO_SAUCE = ITEMS.register("pasta_plate_cheese_tomato_sauce",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_CHEESE_TOMATO_SAUCE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_CHEESE_TOMATO_SAUCE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_CHICKEN_CUT = ITEMS.register("pasta_plate_chicken_cut",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_CHICKEN_CUT_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_CHICKEN_CUT_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_CHICKEN_CUT_TOMATO_SAUCE = ITEMS.register("pasta_plate_chicken_cut_tomato_sauce",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_CHICKEN_CUT_TOMATO_SAUCE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_CHICKEN_CUT_TOMATO_SAUCE_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_EGGPLANT = ITEMS.register("pasta_plate_eggplant",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_EGGPLANT_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_EGGPLANT_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_ENDERMITE_MEATBALLS = ITEMS.register("pasta_plate_endermite_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_ENDERMITE_MEATBALLS_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_ENDERMITE_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_ENDERMITE_MEATBALLS_TOMATO_SAUCE = ITEMS.register("pasta_plate_endermite_meatballs_tomato_sauce",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_ENDERMITE_MEATBALLS_TOMATO_SAUCE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_ENDERMITE_MEATBALLS_TOMATO_SAUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_FISH = ITEMS.register("pasta_plate_fish",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_FISH_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_FISH_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_FISH_TOMATO_SAUCE = ITEMS.register("pasta_plate_fish_tomato_sauce",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_FISH_TOMATO_SAUCE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_FISH_TOMATO_SAUCE_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_BEEF_MEATBALLS = ITEMS.register("pasta_plate_beef_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_BEEF_MEATBALLS_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_BEEF_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_MUTTON_CHOP = ITEMS.register("pasta_plate_mutton_chop",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_MUTTON_CHOP_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_MUTTON_CHOP_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_PORK_MEATBALLS = ITEMS.register("pasta_plate_pork_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_PORK_MEATBALLS_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_PORK_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_PORK_MEATBALLS_TOMATO_SAUCE = ITEMS.register("pasta_plate_pork_meatballs_tomato_sauce",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_PORK_MEATBALLS_TOMATO_SAUCE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_PORK_MEATBALLS_TOMATO_SAUCE_FOOD = new FoodProperties.Builder()
            .nutrition(11).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_RABBIT_MEATBALLS = ITEMS.register("pasta_plate_rabbit_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_RABBIT_MEATBALLS_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_RABBIT_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_RABBIT_MEATBALLS_TOMATO_SAUCE = ITEMS.register("pasta_plate_rabbit_meatballs_tomato_sauce",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_RABBIT_MEATBALLS_TOMATO_SAUCE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_RABBIT_MEATBALLS_TOMATO_SAUCE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_SLIME = ITEMS.register("pasta_plate_slime",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_SLIME_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_SLIME_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_SLIMEBALLS = ITEMS.register("pasta_plate_slimeballs",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_SLIMEBALLS_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_SLIMEBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_SQUID_INK = ITEMS.register("pasta_plate_squid_ink",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_SQUID_INK_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_SQUID_INK_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_STRIDER_MEATBALLS = ITEMS.register("pasta_plate_strider_meatballs",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_STRIDER_MEATBALLS_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_STRIDER_MEATBALLS_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_STRIDER_MEATBALLS_TOMATO_SAUCE = ITEMS.register("pasta_plate_strider_meatballs_tomato_sauce",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_STRIDER_MEATBALLS_TOMATO_SAUCE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_STRIDER_MEATBALLS_TOMATO_SAUCE_FOOD = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> PASTA_PLATE_TOMATO_SAUCE = ITEMS.register("pasta_plate_tomato_sauce",
            () -> new Item(new Item.Properties().food(ModItems.PASTA_PLATE_TOMATO_SAUCE_FOOD).stacksTo(16)));
    public static final FoodProperties PASTA_PLATE_TOMATO_SAUCE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> POTATO_CHIP_BOWL = ITEMS.register("potato_chip_bowl",
            () -> new Item(new Item.Properties().food(ModItems.POTATO_CHIP_BOWL_FOOD).stacksTo(16)));
    public static final FoodProperties POTATO_CHIP_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 1200, 0), 1.0f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> TOAST_FRIED_EGG_PLATE = ITEMS.register("toast_fried_egg_plate",
            () -> new Item(new Item.Properties().food(ModItems.TOAST_FRIED_EGG_PLATE_FOOD).stacksTo(16)));
    public static final FoodProperties TOAST_FRIED_EGG_PLATE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> TOAST_PLATE = ITEMS.register("toast_plate",
            () -> new Item(new Item.Properties().food(ModItems.TOAST_PLATE_FOOD).stacksTo(16)));
    public static final FoodProperties TOAST_PLATE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f).usingConvertsTo(Items.BOWL)
            .build();

    public static final DeferredItem<Item> TORTILLA_CHIP_BOWL = ITEMS.register("tortilla_chip_bowl",
            () -> new Item(new Item.Properties().food(ModItems.TORTILLA_CHIP_BOWL_FOOD).stacksTo(16)));
    public static final FoodProperties TORTILLA_CHIP_BOWL_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).usingConvertsTo(Items.BOWL)
            .build();


// Food - Stick //

    public static final DeferredItem<Item> APPLE_ICE_CREAM_STICK = ITEMS.register("apple_ice_cream_stick",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_ICE_CREAM_STICK_FOOD).craftRemainder(Items.STICK)));
    public static final FoodProperties APPLE_ICE_CREAM_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.6f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> APPLE_POPSICLE = ITEMS.register("apple_popsicle",
            () -> new Item(new Item.Properties().food(ModItems.APPLE_POPSICLE_FOOD)));
    public static final FoodProperties APPLE_POPSICLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> BERRY_ICE_CREAM_STICK = ITEMS.register("berry_ice_cream_stick",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_ICE_CREAM_STICK_FOOD).craftRemainder(Items.STICK)));
    public static final FoodProperties BERRY_ICE_CREAM_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.9f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> BERRY_POPSICLE = ITEMS.register("berry_popsicle",
            () -> new Item(new Item.Properties().food(ModItems.BERRY_POPSICLE_FOOD)));
    public static final FoodProperties BERRY_POPSICLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_MARSHMALLOW_STICK = ITEMS.register("chocolate_marshmallow_stick",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_MARSHMALLOW_STICK_FOOD)));
    public static final FoodProperties CHOCOLATE_MARSHMALLOW_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_ICE_CREAM_STICK = ITEMS.register("chocolate_ice_cream_stick",
            () -> new Item(new Item.Properties().food(ModItems.CHOCOLATE_ICE_CREAM_STICK_FOOD)));
    public static final FoodProperties CHOCOLATE_ICE_CREAM_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.2f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_ICE_CREAM_STICK = ITEMS.register("chorus_fruit_ice_cream_stick",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_ICE_CREAM_STICK_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_ICE_CREAM_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.9f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_POPSICLE = ITEMS.register("chorus_fruit_popsicle",
            () -> new Item(new Item.Properties().food(ModItems.CHORUS_FRUIT_POPSICLE_FOOD)));
    public static final FoodProperties CHORUS_FRUIT_POPSICLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> COTTON_CANDY_STICK = ITEMS.register("cotton_candy_stick",
            () -> new Item(new Item.Properties().food(ModItems.COTTON_CANDY_STICK_FOOD)));
    public static final FoodProperties COTTON_CANDY_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(5.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_MARSHMALLOW_STICK = ITEMS.register("dark_chocolate_marshmallow_stick",
            () -> new Item(new Item.Properties().food(ModItems.DARK_CHOCOLATE_MARSHMALLOW_STICK_FOOD)));
    public static final FoodProperties DARK_CHOCOLATE_MARSHMALLOW_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> ENDERMITE_MEATBALL_STICK_1 = ITEMS.register("endermite_meatball_stick_1",
            () -> new Item(new Item.Properties().food(ModItems.ENDERMITE_MEATBALL_STICK_1_FOOD)));
    public static final FoodProperties ENDERMITE_MEATBALL_STICK_1_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> ENDERMITE_MEATBALL_STICK_2 = ITEMS.register("endermite_meatball_stick_2",
            () -> new Item(new Item.Properties().food(ModItems.ENDERMITE_MEATBALL_STICK_2_FOOD)));
    public static final FoodProperties ENDERMITE_MEATBALL_STICK_2_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> ENDERMITE_MEATBALL_STICK_3 = ITEMS.register("endermite_meatball_stick_3",
            () -> new Item(new Item.Properties().food(ModItems.ENDERMITE_MEATBALL_STICK_3_FOOD)));
    public static final FoodProperties ENDERMITE_MEATBALL_STICK_3_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 6000, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_ICE_CREAM_STICK = ITEMS.register("glow_berry_ice_cream_stick",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_ICE_CREAM_STICK_FOOD).craftRemainder(Items.STICK)));
    public static final FoodProperties GLOW_BERRY_ICE_CREAM_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.9f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_POPSICLE = ITEMS.register("glow_berry_popsicle",
            () -> new Item(new Item.Properties().food(ModItems.GLOW_BERRY_POPSICLE_FOOD)));
    public static final FoodProperties GLOW_BERRY_POPSICLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.5f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> ICE_CREAM_STICK = ITEMS.register("ice_cream_stick",
            () -> new Item(new Item.Properties().food(ModItems.ICE_CREAM_STICK_FOOD).craftRemainder(Items.STICK)));
    public static final FoodProperties ICE_CREAM_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.8f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> MAGMA_CREAM_MARSHMALLOW_STICK = ITEMS.register("magma_cream_marshmallow_stick",
            () -> new Item(new Item.Properties().food(ModItems.MAGMA_CREAM_MARSHMALLOW_STICK_FOOD)));
    public static final FoodProperties MAGMA_CREAM_MARSHMALLOW_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> MARSHMALLOW_STICK = ITEMS.register("marshmallow_stick",
            () -> new Item(new Item.Properties().food(ModItems.MARSHMALLOW_STICK_FOOD)));
    public static final FoodProperties MARSHMALLOW_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> BEEF_MEATBALL_STICK_1 = ITEMS.register("beef_meatball_stick_1",
            () -> new Item(new Item.Properties().food(ModItems.MEATBALL_STICK_1_FOOD)));
    public static final FoodProperties MEATBALL_STICK_1_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> BEEF_MEATBALL_STICK_2 = ITEMS.register("beef_meatball_stick_2",
            () -> new Item(new Item.Properties().food(ModItems.MEATBALL_STICK_2_FOOD).craftRemainder(Items.STICK)));
    public static final FoodProperties MEATBALL_STICK_2_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> BEEF_MEATBALL_STICK_3 = ITEMS.register("beef_meatball_stick_3",
            () -> new Item(new Item.Properties().food(ModItems.MEATBALL_STICK_3_FOOD)));
    public static final FoodProperties MEATBALL_STICK_3_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> MELON_ICE_CREAM_STICK = ITEMS.register("melon_ice_cream_stick",
            () -> new Item(new Item.Properties().food(ModItems.MELON_ICE_CREAM_STICK_FOOD).craftRemainder(Items.STICK)));
    public static final FoodProperties MELON_ICE_CREAM_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(1.1f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> PORK_MEATBALL_STICK_1 = ITEMS.register("pork_meatball_stick_1",
            () -> new Item(new Item.Properties().food(ModItems.PORK_MEATBALL_STICK_1_FOOD).craftRemainder(Items.STICK)));

    public static final FoodProperties PORK_MEATBALL_STICK_1_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f)
            .usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> PORK_MEATBALL_STICK_2 = ITEMS.register("pork_meatball_stick_2",
            () -> new Item(new Item.Properties().food(ModItems.PORK_MEATBALL_STICK_2_FOOD)));
    public static final FoodProperties PORK_MEATBALL_STICK_2_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> PORK_MEATBALL_STICK_3 = ITEMS.register("pork_meatball_stick_3",
            () -> new Item(new Item.Properties().food(ModItems.PORK_MEATBALL_STICK_3_FOOD)));
    public static final FoodProperties PORK_MEATBALL_STICK_3_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> RABBIT_MEATBALL_STICK_1 = ITEMS.register("rabbit_meatball_stick_1",
            () -> new Item(new Item.Properties().food(ModItems.RABBIT_MEATBALL_STICK_1_FOOD)));
    public static final FoodProperties RABBIT_MEATBALL_STICK_1_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> RABBIT_MEATBALL_STICK_2 = ITEMS.register("rabbit_meatball_stick_2",
            () -> new Item(new Item.Properties().food(ModItems.RABBIT_MEATBALL_STICK_2_FOOD)));
    public static final FoodProperties RABBIT_MEATBALL_STICK_2_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> RABBIT_MEATBALL_STICK_3 = ITEMS.register("rabbit_meatball_stick_3",
            () -> new Item(new Item.Properties().food(ModItems.RABBIT_MEATBALL_STICK_3_FOOD)));
    public static final FoodProperties RABBIT_MEATBALL_STICK_3_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> CORN_STICK = ITEMS.register("corn_stick",
            () -> new Item(new Item.Properties().food(ModItems.CORN_STICK_FOOD)));
    public static final FoodProperties CORN_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.7f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> STRIDER_MEATBALL_STICK_1 = ITEMS.register("strider_meatball_stick_1",
            () -> new Item(new Item.Properties().food(ModItems.STRIDER_MEATBALL_STICK_1_FOOD)));
    public static final FoodProperties STRIDER_MEATBALL_STICK_1_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> STRIDER_MEATBALL_STICK_2 = ITEMS.register("strider_meatball_stick_2",
            () -> new Item(new Item.Properties().food(ModItems.STRIDER_MEATBALL_STICK_2_FOOD)));
    public static final FoodProperties STRIDER_MEATBALL_STICK_2_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3600, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> STRIDER_MEATBALL_STICK_3 = ITEMS.register("strider_meatball_stick_3",
            () -> new Item(new Item.Properties().food(ModItems.STRIDER_MEATBALL_STICK_3_FOOD)));
    public static final FoodProperties STRIDER_MEATBALL_STICK_3_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_MARSHMALLOW_STICK = ITEMS.register("white_chocolate_marshmallow_stick",
            () -> new Item(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_MARSHMALLOW_STICK_FOOD)));
    public static final FoodProperties WHITE_CHOCOLATE_MARSHMALLOW_STICK_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).fast().usingConvertsTo(Items.STICK)
            .build();

// Drinks //

    public static final DeferredItem<Item> APPLE_CREAM_FROSTING_BOTTLE = ITEMS.register("apple_cream_frosting_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.APPLE_CREAM_FROSTING_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties APPLE_CREAM_FROSTING_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.7f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> APPLE_JAM_BOTTLE = ITEMS.register("apple_jam_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.APPLE_JAM_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties APPLE_JAM_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.6f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> APPLE_JUICE_BOTTLE = ITEMS.register("apple_juice_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.APPLE_JUICE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties APPLE_JUICE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.5f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> APPLE_MILKSHAKE_BOTTLE = ITEMS.register("apple_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.APPLE_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties APPLE_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> BERRY_CREAM_FROSTING_BOTTLE = ITEMS.register("berry_cream_frosting_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.BERRY_CREAM_FROSTING_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties BERRY_CREAM_FROSTING_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.4f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> BERRY_JAM_BOTTLE = ITEMS.register("berry_jam_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.BERRY_JAM_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties BERRY_JAM_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.2f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> BERRY_JUICE_BOTTLE = ITEMS.register("berry_juice_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.BERRY_JUICE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties BERRY_JUICE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.6f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> BERRY_MILKSHAKE_BOTTLE = ITEMS.register("berry_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.BERRY_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties BERRY_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> BLACKSTRAP_MOLASSES_BOTTLE = ITEMS.register("blackstrap_molasses_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.BLACKSTRAP_MOLASSES_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties BLACKSTRAP_MOLASSES_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(3.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = ITEMS.register("butterscotch_chip_chocolate_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.BUTTERSCOTCH_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties BUTTERSCOTCH_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_MILKSHAKE_BOTTLE = ITEMS.register("butterscotch_chip_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.BUTTERSCOTCH_CHIP_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties BUTTERSCOTCH_CHIP_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CARAMEL_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = ITEMS.register("caramel_chip_chocolate_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CARAMEL_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CARAMEL_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.5f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CARAMEL_CHIP_MILKSHAKE_BOTTLE = ITEMS.register("caramel_chip_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CARAMEL_CHIP_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CARAMEL_CHIP_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_BOTTLE = ITEMS.register("chocolate_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHOCOLATE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHOCOLATE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.4f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = ITEMS.register("chocolate_chip_chocolate_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = ITEMS.register("chocolate_chip_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHOCOLATE_CHIP_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHOCOLATE_CHIP_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_CREAM_FROSTING_BOTTLE = ITEMS.register("chocolate_cream_frosting_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHOCOLATE_CREAM_FROSTING_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHOCOLATE_CREAM_FROSTING_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_MILKSHAKE_BOTTLE = ITEMS.register("chocolate_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHOCOLATE_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHOCOLATE_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHOCOLATE_MILK_BOTTLE = ITEMS.register("chocolate_milk_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHOCOLATE_MILK_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHOCOLATE_MILK_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_FROSTING_BOTTLE = ITEMS.register("chorus_fruit_cream_frosting_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHORUS_FRUIT_CREAM_FROSTING_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHORUS_FRUIT_CREAM_FROSTING_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_JAM_BOTTLE = ITEMS.register("chorus_fruit_jam_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHORUS_FRUIT_JAM_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHORUS_FRUIT_JAM_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.1f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_JUICE_BOTTLE = ITEMS.register("chorus_fruit_juice_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHORUS_FRUIT_JUICE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHORUS_FRUIT_JUICE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.6f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CHORUS_FRUIT_MILKSHAKE_BOTTLE = ITEMS.register("chorus_fruit_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CHORUS_FRUIT_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CHORUS_FRUIT_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> CREAM_FROSTING_BOTTLE = ITEMS.register("cream_frosting_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.CREAM_FROSTING_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties CREAM_FROSTING_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_BOTTLE = ITEMS.register("dark_chocolate_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.DARK_CHOCOLATE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties DARK_CHOCOLATE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.3f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = ITEMS.register("dark_chocolate_chip_chocolate_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.DARK_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties DARK_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1800, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = ITEMS.register("dark_chocolate_chip_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.DARK_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties DARK_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.1f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1800, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> FRUIT_SMOOTHIE_BOTTLE = ITEMS.register("fruit_smoothie_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.FRUIT_SMOOTHIE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties FRUIT_SMOOTHIE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.LUCK, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_CREAM_FROSTING_BOTTLE = ITEMS.register("glow_berry_cream_frosting_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.GLOW_BERRY_CREAM_FROSTING_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties GLOW_BERRY_CREAM_FROSTING_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.4f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_JAM_BOTTLE = ITEMS.register("glow_berry_jam_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.GLOW_BERRY_JAM_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties GLOW_BERRY_JAM_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.2f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_JUICE_BOTTLE = ITEMS.register("glow_berry_juice_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.GLOW_BERRY_JUICE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties GLOW_BERRY_JUICE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.6f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> GLOW_BERRY_MILKSHAKE_BOTTLE = ITEMS.register("glow_berry_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.GLOW_BERRY_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties GLOW_BERRY_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> HOT_DARK_CHOCOLATE_BOTTLE = ITEMS.register("hot_dark_chocolate_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.HOT_DARK_CHOCOLATE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties HOT_DARK_CHOCOLATE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.8f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> HOT_WHITE_CHOCOLATE_BOTTLE = ITEMS.register("hot_white_chocolate_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.HOT_WHITE_CHOCOLATE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties HOT_WHITE_CHOCOLATE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> MELON_CREAM_FROSTING_BOTTLE = ITEMS.register("melon_cream_frosting_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.MELON_CREAM_FROSTING_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties MELON_CREAM_FROSTING_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> MELON_JAM_BOTTLE = ITEMS.register("melon_jam_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.MELON_JAM_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties MELON_JAM_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.7f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> MELON_MILKSHAKE_BOTTLE = ITEMS.register("melon_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.MELON_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties MELON_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.0f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> MILKSHAKE_BOTTLE = ITEMS.register("milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.8f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> SOUR_CREAM_BOTTLE = ITEMS.register("sour_cream_bottle",
            () -> new BottleItem(new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));

    public static final DeferredItem<Item> TOFFEE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = ITEMS.register("toffee_chip_chocolate_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.TOFFEE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties TOFFEE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.3f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> TOFFEE_CHIP_MILKSHAKE_BOTTLE = ITEMS.register("toffee_chip_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.TOFFEE_CHIP_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties TOFFEE_CHIP_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 600, 0), 1.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> UBE_CREAM_FROSTING_BOTTLE = ITEMS.register("ube_cream_frosting_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.UBE_CREAM_FROSTING_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties UBE_CREAM_FROSTING_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.7f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> VINEGAR_BOTTLE = ITEMS.register("vinegar_bottle",
            () -> new BottleItem(new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));

    public static final DeferredItem<Item> WHITE_CHOCOLATE_BOTTLE = ITEMS.register("white_chocolate_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_BOTTLE_FOOD).stacksTo(64).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties WHITE_CHOCOLATE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE = ITEMS.register("white_chocolate_chip_chocolate_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties WHITE_CHOCOLATE_CHIP_CHOCOLATE_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(1.2f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1800, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE = ITEMS.register("white_chocolate_chip_milkshake_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.WHITE_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties WHITE_CHOCOLATE_CHIP_MILKSHAKE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(1.4f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 1800, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> HOT_CHOCOLATE_BOTTLE = ITEMS.register("hot_chocolate_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.HOT_CHOCOLATE_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties HOT_CHOCOLATE_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.9f).effect(() -> new MobEffectInstance(ModEffects.COMFORT, 6000, 0), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final DeferredItem<Item> YOGURT_BOTTLE = ITEMS.register("yogurt_bottle",
            () -> new BottleItem(new Item.Properties().food(ModItems.YOGURT_BOTTLE_FOOD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final FoodProperties YOGURT_BOTTLE_FOOD = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.9f).usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Items");
        ITEMS.register(eventBus);
    }
}