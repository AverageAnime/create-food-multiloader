package dev.averageanime.neoforge.block;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.type.cake.CakeBaseBlock;
import dev.averageanime.neoforge.block.type.cake.CheeseBlock;
import dev.averageanime.neoforge.block.type.cake.GyroMeatBlock;
import dev.averageanime.neoforge.block.type.cake.ModCakeBlock;
import dev.averageanime.neoforge.block.type.pie.PieBlock;
import dev.averageanime.neoforge.block.type.pie.PizzaBlock;
import dev.averageanime.neoforge.block.type.pie.RawPieBlock;
import dev.averageanime.neoforge.block.type.pie.RawPizzaBlock;
import dev.averageanime.neoforge.block.type.storage.ClothSackBlock;
import dev.averageanime.neoforge.block.type.storage.RationBoxBlock;
import dev.averageanime.neoforge.item.ModItems;
import dev.averageanime.neoforge.item.storage.ClothSackItem;
import dev.averageanime.neoforge.item.storage.RationBoxItem;
import dev.averageanime.util.Tip;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;
import static dev.averageanime.neoforge.item.ModTooltips.addTooltip;
import static dev.averageanime.util.Tooltips.tips;

@SuppressWarnings("unused")
public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CommonClass.MOD_ID);

    private static DeferredBlock<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        DeferredBlock<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static DeferredBlock<Block> registerBlockWithTooltips(String name,
                                                                  Supplier<Block> blockSupplier,
                                                                  Tip tip) {
        DeferredBlock<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {
            @Override
            public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                        @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                addTooltip(components, tip.compat(), tip.keys());
                super.appendHoverText(stack, context, components, flag);
            }
        });
        return block;
    }

    private static DeferredBlock<Block> register(String name, Supplier<Block> blockSupplier, Tip tip) {
        if (tip != null) return registerBlockWithTooltips(name, blockSupplier, tip);
        return registerBlock(name, blockSupplier);
    }

    private static DeferredBlock<Block> registerRawPizza(String name, Tip tip) {
        return register(name, () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)), tip);
    }

    private static DeferredBlock<Block> registerCookedPizza(String name, Supplier<Item> sliceItem, Tip tip) {
        return register(name, () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem), tip);
    }

    private static DeferredBlock<Block> registerRawPie(String name, Tip tip) {
        return register(name, () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)), tip);
    }

    private static DeferredBlock<Block> registerCookedPie(String name, Supplier<Item> sliceItem, Tip tip) {
        return register(name, () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem), tip);
    }

    private static DeferredBlock<Block> registerCake(String name,
                                                     Supplier<Item> sliceItem,
                                                     Tip tip) {
        Supplier<Block> blockSupplier = () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), sliceItem);
        DeferredBlock<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(1)) {
            @Override
            public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                        @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                if (tip != null) addTooltip(components, tip.compat(), tip.keys());
                super.appendHoverText(stack, context, components, flag);
            }
        });
        return block;
    }

    private static DeferredBlock<Block> registerWaffle(String name,
                                                       Supplier<Item> sliceItem,
                                                       Tip tip) {
        return registerCookedPizza(name, sliceItem, tip);
    }

    private static DeferredBlock<Block> registerGelatinBlock(String name) {
        return registerBlock(name, () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    }

    private static DeferredBlock<Block> registerCakeBase(String name, Tip tip) {
        DeferredBlock<Block> block = BLOCKS.register(name, () -> new CakeBaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(1)) {
            @Override
            public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                if (tip != null) addTooltip(components, tip.compat(), tip.keys());
                super.appendHoverText(stack, context, components, flag);
            }
        });
        return block;
    }

    public static final DeferredBlock<Block> APPLE_CHEESECAKE = registerCookedPie("apple_cheesecake", ModItems.APPLE_CHEESECAKE_SLICE, null);
    public static final DeferredBlock<Block> APPLE_CREAM_CAKE = registerCake("apple_cream_cake", ModItems.APPLE_CREAM_CAKE_SLICE, tips(null, "apple_cream_frosting_ingredient"));
    public static final DeferredBlock<Block> BACON_PIZZA = registerCookedPizza("bacon_pizza", ModItems.BACON_PIZZA_SLICE, tips(null, "bacon_ingredient"));
    public static final DeferredBlock<Block> BERRY_CREAM_CAKE = registerCake("berry_cream_cake", ModItems.BERRY_CREAM_CAKE_SLICE, tips(null, "berry_cream_frosting_ingredient"));
    public static final DeferredBlock<Block> BERRY_CREAM_CAKE_CHORUS_FRUIT = registerCake("berry_cream_cake_chorus_fruit", ModItems.BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT, tips(null, "berry_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final DeferredBlock<Block> BERRY_CREAM_CAKE_GLOW_BERRY = registerCake("berry_cream_cake_glow_berry", ModItems.BERRY_CREAM_CAKE_SLICE_GLOW_BERRY, tips(null, "berry_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final DeferredBlock<Block> BERRY_CREAM_CAKE_SWEET_BERRY = registerCake("berry_cream_cake_sweet_berry", ModItems.BERRY_CREAM_CAKE_SLICE_SWEET_BERRY, tips(null, "berry_cream_frosting_ingredient", "berry_ingredient"));
    public static final DeferredBlock<Block> BERRY_PIE = registerCookedPie("berry_pie", ModItems.BERRY_PIE_SLICE, null);
    public static final DeferredBlock<Block> BLACK_GELATIN_DESSERT_BLOCK = registerGelatinBlock("black_gelatin_dessert_block");
    public static final DeferredBlock<Block> BLUE_GELATIN_DESSERT_BLOCK = registerGelatinBlock("blue_gelatin_dessert_block");
    public static final DeferredBlock<Block> BROWN_GELATIN_DESSERT_BLOCK = registerGelatinBlock("brown_gelatin_dessert_block");
    public static final DeferredBlock<Block> BUTTERSCOTCH_CHIP_WAFFLE = registerWaffle("butterscotch_chip_waffle", ModItems.BUTTERSCOTCH_CHIP_MINI_WAFFLE, tips(null, "butterscotch_chips_ingredient"));
    public static final DeferredBlock<Block> CAKE_BASE = registerCakeBase("cake_base", null);
    public static final DeferredBlock<Block> CARAMEL_CHIP_WAFFLE = registerWaffle("caramel_chip_waffle", ModItems.CARAMEL_CHIP_MINI_WAFFLE, tips(null, "caramel_chips_ingredient"));
    public static final DeferredBlock<Block> CHEESECAKE = registerCookedPie("cheesecake", ModItems.CHEESECAKE_SLICE, null);
    public static final DeferredBlock<Block> CHEESE_BLOCK = registerBlock("cheese_block", () -> new CheeseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredBlock<Block> CHEESE_PIZZA = registerCookedPizza("cheese_pizza", ModItems.CHEESE_PIZZA_SLICE, tips(null, "cheese_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_CHIP_WAFFLE = registerWaffle("chocolate_chip_waffle", ModItems.CHOCOLATE_CHIP_MINI_WAFFLE, tips(null, "chocolate_chips_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE = registerCake("chocolate_cream_cake", ModItems.CHOCOLATE_CREAM_CAKE_SLICE, tips(null, "chocolate_cream_frosting_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH = registerCake("chocolate_cream_cake_butterscotch", ModItems.CHOCOLATE_CREAM_CAKE_SLICE_BUTTERSCOTCH, tips(null, "chocolate_cream_frosting_ingredient", "butterscotch_chips_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_CARAMEL = registerCake("chocolate_cream_cake_caramel", ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL, tips(null, "chocolate_cream_frosting_ingredient", "caramel_chips_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_CHOCOLATE = registerCake("chocolate_cream_cake_chocolate", ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CHOCOLATE, tips(null, "chocolate_cream_frosting_ingredient", "chocolate_chips_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE = registerCake("chocolate_cream_cake_dark_chocolate", ModItems.CHOCOLATE_CREAM_CAKE_SLICE_DARK_CHOCOLATE, tips(null, "chocolate_cream_frosting_ingredient", "dark_chocolate_chips_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_TOFFEE = registerCake("chocolate_cream_cake_toffee", ModItems.CHOCOLATE_CREAM_CAKE_SLICE_TOFFEE, tips(null, "chocolate_cream_frosting_ingredient", "toffee_chips_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE = registerCake("chocolate_cream_cake_white_chocolate", ModItems.CHOCOLATE_CREAM_CAKE_SLICE_WHITE_CHOCOLATE, tips(null, "chocolate_cream_frosting_ingredient", "white_chocolate_chips_ingredient"));
    public static final DeferredBlock<Block> CHOCOLATE_PIE_GRAHAM_CRACKER = registerCookedPie("chocolate_pie_graham_cracker", ModItems.CHOCOLATE_PIE_GRAHAM_CRACKER_SLICE, tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final DeferredBlock<Block> CHORUS_FRUIT_CHEESECAKE = registerCookedPie("chorus_fruit_cheesecake", ModItems.CHORUS_FRUIT_CHEESECAKE_SLICE, null);
    public static final DeferredBlock<Block> CHORUS_FRUIT_CREAM_CAKE = registerCake("chorus_fruit_cream_cake", ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE, tips(null, "chorus_fruit_cream_frosting_ingredient"));
    public static final DeferredBlock<Block> CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT = registerCake("chorus_fruit_cream_cake_chorus_fruit", ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT, tips(null, "chorus_fruit_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final DeferredBlock<Block> CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY = registerCake("chorus_fruit_cream_cake_glow_berry", ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_GLOW_BERRY, tips(null, "chorus_fruit_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final DeferredBlock<Block> CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY = registerCake("chorus_fruit_cream_cake_sweet_berry", ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_SWEET_BERRY, tips(null, "chorus_fruit_cream_frosting_ingredient", "berry_ingredient"));
    public static final DeferredBlock<Block> CHORUS_FRUIT_PIE = registerCookedPie("chorus_fruit_pie", ModItems.CHORUS_FRUIT_PIE_SLICE, null);
    public static final DeferredBlock<Block> COOKIE_CREAM_PIE = registerCookedPie("cookie_cream_pie", ModItems.COOKIE_CREAM_PIE_SLICE, null);
    public static final DeferredBlock<Block> CREAM_CAKE = registerCake("cream_cake", ModItems.CREAM_CAKE_SLICE, tips(null, "cream_frosting_ingredient"));
    public static final DeferredBlock<Block> CREAM_CAKE_CHORUS_FRUIT = registerCake("cream_cake_chorus_fruit", ModItems.CREAM_CAKE_SLICE_CHORUS_FRUIT, tips(null, "cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final DeferredBlock<Block> CREAM_CAKE_GLOW_BERRY = registerCake("cream_cake_glow_berry", ModItems.CREAM_CAKE_SLICE_GLOW_BERRY, tips(null, "cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final DeferredBlock<Block> CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = registerCookedPie("cream_pie_chocolate_graham_cracker", ModItems.CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_SLICE, tips(null, "chocolate_graham_cracker_pie_crust_ingredient"));
    public static final DeferredBlock<Block> CREAM_PIE_GRAHAM_CRACKER = registerCookedPie("cream_pie_graham_cracker", ModItems.CREAM_PIE_GRAHAM_CRACKER_SLICE, tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final DeferredBlock<Block> CYAN_GELATIN_DESSERT_BLOCK = registerGelatinBlock("cyan_gelatin_dessert_block");
    public static final DeferredBlock<Block> DARK_CHOCOLATE_CHIP_WAFFLE = registerWaffle("dark_chocolate_chip_waffle", ModItems.DARK_CHOCOLATE_CHIP_MINI_WAFFLE, tips(null, "dark_chocolate_chips_ingredient"));
    public static final DeferredBlock<Block> FISH_BACON_PIZZA = registerCookedPizza("fish_bacon_pizza", ModItems.FISH_BACON_PIZZA_SLICE, tips(null, "fish_ingredient", "bacon_ingredient"));
    public static final DeferredBlock<Block> FISH_ONION_PIZZA = registerCookedPizza("fish_onion_pizza", ModItems.FISH_ONION_PIZZA_SLICE, tips(null, "fish_ingredient", "onion_ingredient"));
    public static final DeferredBlock<Block> FISH_PIZZA = registerCookedPizza("fish_pizza", ModItems.FISH_PIZZA_SLICE, tips(null, "fish_ingredient"));
    public static final DeferredBlock<Block> GELATIN_DESSERT_BLOCK = registerGelatinBlock("gelatin_dessert_block");
    public static final DeferredBlock<Block> GLOW_BERRY_CHEESECAKE = registerCookedPie("glow_berry_cheesecake", ModItems.GLOW_BERRY_CHEESECAKE_SLICE, null);
    public static final DeferredBlock<Block> GLOW_BERRY_CREAM_CAKE = registerCake("glow_berry_cream_cake", ModItems.GLOW_BERRY_CREAM_CAKE_SLICE, tips(null, "glow_berry_cream_frosting_ingredient"));
    public static final DeferredBlock<Block> GLOW_BERRY_CREAM_CAKE_CHORUS_FRUIT = registerCake("glow_berry_cream_cake_chorus_fruit", ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT, tips(null, "glow_berry_cream_frosting_ingredient", "chorus_fruit_ingredient"));
    public static final DeferredBlock<Block> GLOW_BERRY_CREAM_CAKE_GLOW_BERRY = registerCake("glow_berry_cream_cake_glow_berry", ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_GLOW_BERRY, tips(null, "glow_berry_cream_frosting_ingredient", "glow_berry_ingredient"));
    public static final DeferredBlock<Block> GLOW_BERRY_CREAM_CAKE_SWEET_BERRY = registerCake("glow_berry_cream_cake_sweet_berry", ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_SWEET_BERRY, tips(null, "glow_berry_cream_frosting_ingredient", "berry_ingredient"));
    public static final DeferredBlock<Block> GLOW_BERRY_PIE = registerCookedPie("glow_berry_pie", ModItems.GLOW_BERRY_PIE_SLICE, null);
    public static final DeferredBlock<Block> GRAY_GELATIN_DESSERT_BLOCK = registerGelatinBlock("gray_gelatin_dessert_block");
    public static final DeferredBlock<Block> GREEN_GELATIN_DESSERT_BLOCK = registerGelatinBlock("green_gelatin_dessert_block");
    public static final DeferredBlock<Block> GYRO_MEAT_BLOCK = registerBlock("gyro_meat_block", () -> new GyroMeatBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredBlock<Block> LIGHT_BLUE_GELATIN_DESSERT_BLOCK = registerGelatinBlock("light_blue_gelatin_dessert_block");
    public static final DeferredBlock<Block> LIGHT_GRAY_GELATIN_DESSERT_BLOCK = registerGelatinBlock("light_gray_gelatin_dessert_block");
    public static final DeferredBlock<Block> LIME_GELATIN_DESSERT_BLOCK = registerGelatinBlock("lime_gelatin_dessert_block");
    public static final DeferredBlock<Block> MAGENTA_GELATIN_DESSERT_BLOCK = registerGelatinBlock("magenta_gelatin_dessert_block");
    public static final DeferredBlock<Block> MEAT_PIE = registerCookedPie("meat_pie", ModItems.MEAT_PIE_SLICE, null);
    public static final DeferredBlock<Block> MELON_CREAM_CAKE = registerCake("melon_cream_cake", ModItems.MELON_CREAM_CAKE_SLICE, tips(null, "melon_cream_frosting_ingredient"));
    public static final DeferredBlock<Block> MUSHROOM_BACON_PIZZA = registerCookedPizza("mushroom_bacon_pizza", ModItems.MUSHROOM_BACON_PIZZA_SLICE, tips(null, "mushroom_ingredient", "bacon_ingredient"));
    public static final DeferredBlock<Block> MUSHROOM_FISH_PIZZA = registerCookedPizza("mushroom_fish_pizza", ModItems.MUSHROOM_FISH_PIZZA_SLICE, tips(null, "mushroom_ingredient", "fish_ingredient"));
    public static final DeferredBlock<Block> MUSHROOM_ONION_PIZZA = registerCookedPizza("mushroom_onion_pizza", ModItems.MUSHROOM_ONION_PIZZA_SLICE, tips(null, "mushroom_ingredient", "onion_ingredient"));
    public static final DeferredBlock<Block> MUSHROOM_PIZZA = registerCookedPizza("mushroom_pizza", ModItems.MUSHROOM_PIZZA_SLICE, tips(null, "mushroom_ingredient"));
    public static final DeferredBlock<Block> ONION_BACON_PIZZA = registerCookedPizza("onion_bacon_pizza", ModItems.ONION_BACON_PIZZA_SLICE, tips(null, "onion_ingredient", "bacon_ingredient"));
    public static final DeferredBlock<Block> ONION_PIZZA = registerCookedPizza("onion_pizza", ModItems.ONION_PIZZA_SLICE, tips(null, "onion_ingredient"));
    public static final DeferredBlock<Block> ORANGE_GELATIN_DESSERT_BLOCK = registerGelatinBlock("orange_gelatin_dessert_block");
    public static final DeferredBlock<Block> PINK_GELATIN_DESSERT_BLOCK = registerGelatinBlock("pink_gelatin_dessert_block");
    public static final DeferredBlock<Block> PIZZA_DOUGH = registerRawPizza("pizza_dough", null);
    public static final DeferredBlock<Block> PIZZA_DOUGH_TOMATO_SAUCE = registerRawPizza("pizza_dough_tomato_sauce", tips(null, "tomato_sauce_ingredient"));
    public static final DeferredBlock<Block> PUMPKIN_PIE_BLOCK = registerCookedPie("pumpkin_pie_block", ModItems.PUMPKIN_PIE_SLICE, null);
    public static final DeferredBlock<Block> PURPLE_GELATIN_DESSERT_BLOCK = registerGelatinBlock("purple_gelatin_dessert_block");
    public static final DeferredBlock<Block> RAW_APPLE_CHEESECAKE = registerRawPie("raw_apple_cheesecake", null);
    public static final DeferredBlock<Block> RAW_APPLE_PIE = registerRawPie("raw_apple_pie", null);
    public static final DeferredBlock<Block> RAW_BACON_PIZZA = registerRawPizza("raw_bacon_pizza", tips(null, "bacon_ingredient"));
    public static final DeferredBlock<Block> RAW_BERRY_CHEESECAKE = registerRawPie("raw_berry_cheesecake", null);
    public static final DeferredBlock<Block> RAW_BERRY_PIE = registerRawPie("raw_berry_pie", null);
    public static final DeferredBlock<Block> RAW_CHEESECAKE = registerRawPie("raw_cheesecake", null);
    public static final DeferredBlock<Block> RAW_CHEESE_PIZZA = registerRawPizza("raw_cheese_pizza", tips(null, "cheese_ingredient"));
    public static final DeferredBlock<Block> RAW_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = registerRawPie("raw_chocolate_graham_cracker_pie_crust", null);
    public static final DeferredBlock<Block> RAW_CHOCOLATE_PIE = registerRawPie("raw_chocolate_pie", null);
    public static final DeferredBlock<Block> RAW_CHOCOLATE_PIE_GRAHAM_CRACKER = registerRawPie("raw_chocolate_pie_graham_cracker", tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final DeferredBlock<Block> RAW_CHORUS_FRUIT_CHEESECAKE = registerRawPie("raw_chorus_fruit_cheesecake", null);
    public static final DeferredBlock<Block> RAW_CHORUS_FRUIT_PIE = registerRawPie("raw_chorus_fruit_pie", null);
    public static final DeferredBlock<Block> RAW_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = registerRawPie("raw_cream_pie_chocolate_graham_cracker", tips(null, "chocolate_graham_cracker_pie_crust_ingredient"));
    public static final DeferredBlock<Block> RAW_CREAM_PIE_GRAHAM_CRACKER = registerRawPie("raw_cream_pie_graham_cracker", tips(null, "graham_cracker_pie_crust_ingredient"));
    public static final DeferredBlock<Block> RAW_FISH_BACON_PIZZA = registerRawPizza("raw_fish_bacon_pizza", tips(null, "fish_ingredient", "bacon_ingredient"));
    public static final DeferredBlock<Block> RAW_FISH_ONION_PIZZA = registerRawPizza("raw_fish_onion_pizza", tips(null, "fish_ingredient", "onion_ingredient"));
    public static final DeferredBlock<Block> RAW_FISH_PIZZA = registerRawPizza("raw_fish_pizza", tips(null, "fish_ingredient"));
    public static final DeferredBlock<Block> RAW_GLOW_BERRY_CHEESECAKE = registerRawPie("raw_glow_berry_cheesecake", null);
    public static final DeferredBlock<Block> RAW_GLOW_BERRY_PIE = registerRawPie("raw_glow_berry_pie", null);
    public static final DeferredBlock<Block> RAW_GRAHAM_CRACKER_PIE_CRUST = registerRawPie("raw_graham_cracker_pie_crust", null);
    public static final DeferredBlock<Block> RAW_MEAT_PIE = registerRawPie("raw_meat_pie", null);
    public static final DeferredBlock<Block> RAW_MUSHROOM_BACON_PIZZA = registerRawPizza("raw_mushroom_bacon_pizza", tips(null, "mushroom_ingredient", "bacon_ingredient"));
    public static final DeferredBlock<Block> RAW_MUSHROOM_FISH_PIZZA = registerRawPizza("raw_mushroom_fish_pizza", tips(null, "mushroom_ingredient", "fish_ingredient"));
    public static final DeferredBlock<Block> RAW_MUSHROOM_ONION_PIZZA = registerRawPizza("raw_mushroom_onion_pizza", tips(null, "mushroom_ingredient", "onion_ingredient"));
    public static final DeferredBlock<Block> RAW_MUSHROOM_PIZZA = registerRawPizza("raw_mushroom_pizza", tips(null, "mushroom_ingredient"));
    public static final DeferredBlock<Block> RAW_ONION_BACON_PIZZA = registerRawPizza("raw_onion_bacon_pizza", tips(null, "onion_ingredient", "bacon_ingredient"));
    public static final DeferredBlock<Block> RAW_ONION_PIZZA = registerRawPizza("raw_onion_pizza", tips(null, "onion_ingredient"));
    public static final DeferredBlock<Block> RAW_PIE_CRUST = registerRawPie("raw_pie_crust", null);
    public static final DeferredBlock<Block> RAW_PUMPKIN_PIE = registerRawPie("raw_pumpkin_pie", null);
    public static final DeferredBlock<Block> RAW_SAUSAGE_BACON_PIZZA = registerRawPizza("raw_sausage_bacon_pizza", tips(null, "sausage_ingredient", "bacon_ingredient"));
    public static final DeferredBlock<Block> RAW_SAUSAGE_FISH_PIZZA = registerRawPizza("raw_sausage_fish_pizza", tips(null, "sausage_ingredient", "fish_ingredient"));
    public static final DeferredBlock<Block> RAW_SAUSAGE_MUSHROOM_PIZZA = registerRawPizza("raw_sausage_mushroom_pizza", tips(null, "sausage_ingredient", "mushroom_ingredient"));
    public static final DeferredBlock<Block> RAW_SAUSAGE_ONION_PIZZA = registerRawPizza("raw_sausage_onion_pizza", tips(null, "sausage_ingredient", "onion_ingredient"));
    public static final DeferredBlock<Block> RAW_SAUSAGE_PIZZA = registerRawPizza("raw_sausage_pizza", tips(null, "sausage_ingredient"));
    public static final DeferredBlock<Block> RED_GELATIN_DESSERT_BLOCK = registerGelatinBlock("red_gelatin_dessert_block");
    public static final DeferredBlock<Block> SAUSAGE_BACON_PIZZA = registerCookedPizza("sausage_bacon_pizza", ModItems.SAUSAGE_BACON_PIZZA_SLICE, tips(null, "sausage_ingredient", "bacon_ingredient"));
    public static final DeferredBlock<Block> SAUSAGE_FISH_PIZZA = registerCookedPizza("sausage_fish_pizza", ModItems.SAUSAGE_FISH_PIZZA_SLICE, tips(null, "sausage_ingredient", "fish_ingredient"));
    public static final DeferredBlock<Block> SAUSAGE_MUSHROOM_PIZZA = registerCookedPizza("sausage_mushroom_pizza", ModItems.SAUSAGE_MUSHROOM_PIZZA_SLICE, tips(null, "sausage_ingredient", "mushroom_ingredient"));
    public static final DeferredBlock<Block> SAUSAGE_ONION_PIZZA = registerCookedPizza("sausage_onion_pizza", ModItems.SAUSAGE_ONION_PIZZA_SLICE, tips(null, "sausage_ingredient", "onion_ingredient"));
    public static final DeferredBlock<Block> SAUSAGE_PIZZA = registerCookedPizza("sausage_pizza", ModItems.SAUSAGE_PIZZA_SLICE, tips(null, "sausage_ingredient"));
    public static final DeferredBlock<Block> SMORES_PIE = registerCookedPie("smores_pie", ModItems.SMORES_PIE_SLICE, null);
    public static final DeferredBlock<Block> TOFFEE_CHIP_WAFFLE = registerWaffle("toffee_chip_waffle", ModItems.TOFFEE_CHIP_MINI_WAFFLE, tips(null, "toffee_chips_ingredient"));
    public static final DeferredBlock<Block> UBE_CAKE_BASE = registerCakeBase("ube_cake_base", tips("ube"));
    public static final DeferredBlock<Block> UBE_CREAM_UBE_CAKE = registerCake("ube_cream_ube_cake", ModItems.UBE_CREAM_UBE_CAKE_SLICE, tips("ube", "ube_cream_frosting_ingredient"));
    public static final DeferredBlock<Block> WAFFLE = registerWaffle("waffle", ModItems.MINI_WAFFLE, null);
    public static final DeferredBlock<Block> WHITE_CHOCOLATE_CHIP_WAFFLE = registerWaffle("white_chocolate_chip_waffle", ModItems.WHITE_CHOCOLATE_CHIP_MINI_WAFFLE, tips(null, "white_chocolate_chips_ingredient"));
    public static final DeferredBlock<Block> YELLOW_GELATIN_DESSERT_BLOCK = registerGelatinBlock("yellow_gelatin_dessert_block");

    private static DeferredBlock<Block> registerBlockWithCustomItem(
            String name, Supplier<Block> blockSupplier,
            java.util.function.Function<Block, Item> itemFactory) {
        DeferredBlock<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> itemFactory.apply(block.get()));
        return block;
    }

    public static final DeferredBlock<Block> RATION_BOX = registerBlockWithCustomItem(
            "ration_box",
            () -> new RationBoxBlock(BlockBehaviour.Properties.of()
                    .strength(1.5f, 6.0f)
                    .sound(net.minecraft.world.level.block.SoundType.WOOD)
                    .noOcclusion()),
            block -> new RationBoxItem(block, new Item.Properties()));

    public static final DeferredBlock<Block> CLOTH_SACK = registerBlockWithCustomItem(
            "cloth_sack",
            () -> new ClothSackBlock(BlockBehaviour.Properties.of()
                    .strength(1.0f, 2.0f)
                    .sound(net.minecraft.world.level.block.SoundType.WOOL)
                    .noOcclusion()),
            block -> new ClothSackItem(block, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Blocks");
        BLOCKS.register(eventBus);
    }
}