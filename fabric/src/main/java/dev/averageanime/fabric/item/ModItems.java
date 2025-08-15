package dev.averageanime.fabric.item;

import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.block.ModPieBlock;
import dev.averageanime.fabric.block.cake.ChocolateCreamCakeCaramel;
import dev.averageanime.fabric.block.cake.GyroMeatBlock;
import dev.averageanime.item.BottleItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

import static dev.averageanime.fabric.CreateFood.MOD_ID;
import static dev.averageanime.fabric.block.ModBlocks.registerBlock;


public class ModItems {

    public static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                item);
    }

    public static void registerModItems() {
        CreateFood.LOGGER.info("Registering Items for " + MOD_ID);
    }

// Apple Cheesecake & Slice

    public static final Item APPLE_CHEESECAKE_SLICE = registerItem("apple_cheesecake_slice", new ConsumableItem(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.1f)
                    .effect(new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0F)
                    .build()), true));
    public static final Block APPLE_CHEESECAKE = registerBlock("apple_cheesecake", new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), () -> APPLE_CHEESECAKE_SLICE));

// Apple Cream Chocolate

    public static final Item APPLE_CREAM_CHOCOLATE = registerItem("apple_cream_chocolate", new ConsumableItem(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5f)
                    .effect(new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0F)
                    .fast()
                    .build()), true));

// Apple Cream Frosting Bucket

    public static final Item APPLE_CREAM_FROSTING_BUCKET = registerItem("apple_cream_frosting_bucket", new Item(new Item.Properties()
            .craftRemainder(Items.BUCKET)
            .stacksTo(1)));

// Chocolate Cream Cake Slice  (Caramel)

    public static final Item CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL = registerItem("chocolate_cream_cake_slice_caramel", new ConsumableItem(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4f)
                    .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0F)
                    .fast()
                    .build()), true));
    public static final Block CHOCOLATE_CREAM_CAKE_CARAMEL = registerBlock("chocolate_cream_cake_caramel", new ChocolateCreamCakeCaramel(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));

// Gyro Meat Slice

    public static final Item GYRO_MEAT_SLICE = registerItem("gyro_meat_slice", new ConsumableItem(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.1f)
                    .build())));
    public static final Block GYRO_MEAT_BLOCK = registerBlock("gyro_meat_block", new GyroMeatBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));

// Yogurt

    static Item.Properties settings = new Item.Properties()
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(16)
            .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.9f)
                    .usingConvertsTo(Items.GLASS_BOTTLE)
                    .build());
    public static final Item YOGURT_BOTTLE = registerItem("yogurt_bottle", new BottleItem(settings));

}
