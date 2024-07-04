package net.averageanime.createfood.item;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.averageanime.createfood.CreateFood;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class ModItems {
    private static final CreateRegistrate REGISTRATE = CreateFood.registrate();

    public static final ItemEntry<Item> APPLE_CREAM_CHOCOLATE_DONUT = REGISTRATE.item("apple_cream_chocolate_donut", Item::new)
            .properties(prop -> prop.food(new FoodProperties.Builder()
                    .nutrition(4).saturationMod(0.8F).fast().build()))
            .register();
    public static final ItemEntry<Item> APPLE_CREAM_DONUT = REGISTRATE.item("apple_cream_donut", Item::new)
            .properties(prop -> prop.food(new FoodProperties.Builder()
                    .nutrition(3).saturationMod(0.8F).fast().build()))
            .register();
    public static final ItemEntry<Item> APPLE_CREAM_CHOCOLATE = REGISTRATE.item("apple_cream_chocolate", Item::new)
            .properties(prop -> prop.food(new FoodProperties.Builder()
                    .nutrition(6).saturationMod(0.5F).fast().build()))
            .register();
    public static final ItemEntry<Item> APPLE_CREAM_DARK_CHOCOLATE = REGISTRATE.item("apple_cream_dark_chocolate", Item::new)
            .properties(prop -> prop.food(new FoodProperties.Builder()
                    .nutrition(5).saturationMod(0.6F).fast().build()))
            .register();
    public static final ItemEntry<Item> APPLE_CREAM_WHITE_CHOCOLATE = REGISTRATE.item("apple_cream_white_chocolate", Item::new)
            .properties(prop -> prop.food(new FoodProperties.Builder()
                    .nutrition(7).saturationMod(0.4F).fast().build()))
            .register();
    public static final ItemEntry<Item> APPLE_CREAM_CHOCOLATE_PASTRY_BAR = REGISTRATE.item("apple_cream_chocolate_pastry_bar", Item::new)
            .properties(prop -> prop.food(new FoodProperties.Builder()
                    .nutrition(3).saturationMod(1.0F).fast().build()))
            .register();
    public static final ItemEntry<Item> APPLE_CREAM_PASTRY_BAR = REGISTRATE.item("apple_cream_pastry_bar", Item::new)
            .properties(prop -> prop.food(new FoodProperties.Builder()
                    .nutrition(2).saturationMod(1.0F).fast().build()))
            .register();
    public static final ItemEntry<BottleItem> APPLE_CREAM_FROSTING = REGISTRATE.item("apple_cream_frosting", BottleItem::new)
            .properties(prop -> prop.food(new FoodProperties.Builder()
                    .nutrition(3).saturationMod(0.7F).fast().build()))
            .register();

    public static void register() {}
}
