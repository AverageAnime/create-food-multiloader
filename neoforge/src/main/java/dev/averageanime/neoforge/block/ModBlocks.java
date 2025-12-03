package dev.averageanime.neoforge.block;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ModItems.ITEMS;
import static dev.averageanime.neoforge.item.ModItems.addModTooltip;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CommonClass.ID);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static final DeferredBlock<Block> PUMPKIN_PIE_BLOCK = BLOCKS.register("pumpkin_pie",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.PUMPKIN_PIE_SLICE));
    public static final DeferredItem<Item> PUMPKIN_PIE_ITEM = ITEMS.register("pumpkin_pie",
            () -> new BlockItem(ModBlocks.PUMPKIN_PIE_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> GYRO_MEAT_BLOCK = BLOCKS.register("gyro_meat_block",
            () -> new GyroMeatBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> GYRO_MEAT_BLOCK_ITEM = ITEMS.register("gyro_meat_block",
            () -> new BlockItem(ModBlocks.GYRO_MEAT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> PIZZA_DOUGH = BLOCKS.register("pizza_dough",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> PIZZA_DOUGH_ITEM = ITEMS.register("pizza_dough",
            () -> new BlockItem(ModBlocks.PIZZA_DOUGH.get(), new Item.Properties()));

    public static final DeferredBlock<Block> PIZZA_DOUGH_TOMATO_SAUCE = BLOCKS.register("pizza_dough_tomato_sauce",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> PIZZA_DOUGH_TOMATO_SAUCE_ITEM = ITEMS.register("pizza_dough_tomato_sauce",
            () -> new BlockItem(ModBlocks.PIZZA_DOUGH_TOMATO_SAUCE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.tomato_sauce_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_BACON_PIZZA = BLOCKS.register("raw_bacon_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_BACON_PIZZA_ITEM = ITEMS.register("raw_bacon_pizza",
            () -> new BlockItem(ModBlocks.RAW_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_CHEESE_PIZZA = BLOCKS.register("raw_cheese_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CHEESE_PIZZA_ITEM = ITEMS.register("raw_cheese_pizza",
            () -> new BlockItem(ModBlocks.RAW_CHEESE_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.cheese_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_FISH_BACON_PIZZA = BLOCKS.register("raw_fish_bacon_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_FISH_BACON_PIZZA_ITEM = ITEMS.register("raw_fish_bacon_pizza",
            () -> new BlockItem(ModBlocks.RAW_FISH_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.fish_ingredient", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_FISH_ONION_PIZZA = BLOCKS.register("raw_fish_onion_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_FISH_ONION_PIZZA_ITEM = ITEMS.register("raw_fish_onion_pizza",
            () -> new BlockItem(ModBlocks.RAW_FISH_ONION_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.fish_ingredient", "tooltip.createfood.onion_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_FISH_PIZZA = BLOCKS.register("raw_fish_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_FISH_PIZZA_ITEM = ITEMS.register("raw_fish_pizza",
            () -> new BlockItem(ModBlocks.RAW_FISH_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.fish_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_MUSHROOM_BACON_PIZZA = BLOCKS.register("raw_mushroom_bacon_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_MUSHROOM_BACON_PIZZA_ITEM = ITEMS.register("raw_mushroom_bacon_pizza",
            () -> new BlockItem(ModBlocks.RAW_MUSHROOM_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.mushroom_ingredient", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_MUSHROOM_FISH_PIZZA = BLOCKS.register("raw_mushroom_fish_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_MUSHROOM_FISH_PIZZA_ITEM = ITEMS.register("raw_mushroom_fish_pizza",
            () -> new BlockItem(ModBlocks.RAW_MUSHROOM_FISH_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.mushroom_ingredient", "tooltip.createfood.fish_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_MUSHROOM_ONION_PIZZA = BLOCKS.register("raw_mushroom_onion_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_MUSHROOM_ONION_PIZZA_ITEM = ITEMS.register("raw_mushroom_onion_pizza",
            () -> new BlockItem(ModBlocks.RAW_MUSHROOM_ONION_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.mushroom_ingredient", "tooltip.createfood.onion_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_MUSHROOM_PIZZA = BLOCKS.register("raw_mushroom_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_MUSHROOM_PIZZA_ITEM = ITEMS.register("raw_mushroom_pizza",
            () -> new BlockItem(ModBlocks.RAW_MUSHROOM_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.mushroom_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_ONION_BACON_PIZZA = BLOCKS.register("raw_onion_bacon_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_ONION_BACON_PIZZA_ITEM = ITEMS.register("raw_onion_bacon_pizza",
            () -> new BlockItem(ModBlocks.RAW_ONION_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.onion_ingredient", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_ONION_PIZZA = BLOCKS.register("raw_onion_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_ONION_PIZZA_ITEM = ITEMS.register("raw_onion_pizza",
            () -> new BlockItem(ModBlocks.RAW_ONION_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.onion_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_SAUSAGE_BACON_PIZZA = BLOCKS.register("raw_sausage_bacon_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_SAUSAGE_BACON_PIZZA_ITEM = ITEMS.register("raw_sausage_bacon_pizza",
            () -> new BlockItem(ModBlocks.RAW_SAUSAGE_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_SAUSAGE_FISH_PIZZA = BLOCKS.register("raw_sausage_fish_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_SAUSAGE_FISH_PIZZA_ITEM = ITEMS.register("raw_sausage_fish_pizza",
            () -> new BlockItem(ModBlocks.RAW_SAUSAGE_FISH_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient", "tooltip.createfood.fish_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_SAUSAGE_MUSHROOM_PIZZA = BLOCKS.register("raw_sausage_mushroom_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_SAUSAGE_MUSHROOM_PIZZA_ITEM = ITEMS.register("raw_sausage_mushroom_pizza",
            () -> new BlockItem(ModBlocks.RAW_SAUSAGE_MUSHROOM_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient", "tooltip.createfood.mushroom_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_SAUSAGE_ONION_PIZZA = BLOCKS.register("raw_sausage_onion_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_SAUSAGE_ONION_PIZZA_ITEM = ITEMS.register("raw_sausage_onion_pizza",
            () -> new BlockItem(ModBlocks.RAW_SAUSAGE_ONION_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient", "tooltip.createfood.onion_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_SAUSAGE_PIZZA = BLOCKS.register("raw_sausage_pizza",
            () -> new RawPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_SAUSAGE_PIZZA_ITEM = ITEMS.register("raw_sausage_pizza",
            () -> new BlockItem(ModBlocks.RAW_SAUSAGE_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> BACON_PIZZA = BLOCKS.register("bacon_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.BACON_PIZZA_SLICE));
    public static final DeferredItem<Item> BACON_PIZZA_ITEM = ITEMS.register("bacon_pizza",
            () -> new BlockItem(ModBlocks.BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHEESE_PIZZA = BLOCKS.register("cheese_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHEESE_PIZZA_SLICE));
    public static final DeferredItem<Item> CHEESE_PIZZA_ITEM = ITEMS.register("cheese_pizza",
            () -> new BlockItem(ModBlocks.CHEESE_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.cheese_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> FISH_BACON_PIZZA = BLOCKS.register("fish_bacon_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.FISH_BACON_PIZZA_SLICE));
    public static final DeferredItem<Item> FISH_BACON_PIZZA_ITEM = ITEMS.register("fish_bacon_pizza",
            () -> new BlockItem(ModBlocks.FISH_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.fish_ingredient", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> FISH_ONION_PIZZA = BLOCKS.register("fish_onion_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.FISH_ONION_PIZZA_SLICE));
    public static final DeferredItem<Item> FISH_ONION_PIZZA_ITEM = ITEMS.register("fish_onion_pizza",
            () -> new BlockItem(ModBlocks.FISH_ONION_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.fish_ingredient", "tooltip.createfood.onion_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> FISH_PIZZA = BLOCKS.register("fish_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.FISH_PIZZA_SLICE));
    public static final DeferredItem<Item> FISH_PIZZA_ITEM = ITEMS.register("fish_pizza",
            () -> new BlockItem(ModBlocks.FISH_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.fish_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> MUSHROOM_BACON_PIZZA = BLOCKS.register("mushroom_bacon_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.MUSHROOM_BACON_PIZZA_SLICE));
    public static final DeferredItem<Item> MUSHROOM_BACON_PIZZA_ITEM = ITEMS.register("mushroom_bacon_pizza",
            () -> new BlockItem(ModBlocks.MUSHROOM_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.mushroom_ingredient", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> MUSHROOM_FISH_PIZZA = BLOCKS.register("mushroom_fish_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.MUSHROOM_FISH_PIZZA_SLICE));
    public static final DeferredItem<Item> MUSHROOM_FISH_PIZZA_ITEM = ITEMS.register("mushroom_fish_pizza",
            () -> new BlockItem(ModBlocks.MUSHROOM_FISH_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.mushroom_ingredient", "tooltip.createfood.fish_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> MUSHROOM_ONION_PIZZA = BLOCKS.register("mushroom_onion_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.MUSHROOM_ONION_PIZZA_SLICE));
    public static final DeferredItem<Item> MUSHROOM_ONION_PIZZA_ITEM = ITEMS.register("mushroom_onion_pizza",
            () -> new BlockItem(ModBlocks.MUSHROOM_ONION_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.mushroom_ingredient", "tooltip.createfood.onion_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> MUSHROOM_PIZZA = BLOCKS.register("mushroom_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.MUSHROOM_PIZZA_SLICE));
    public static final DeferredItem<Item> MUSHROOM_PIZZA_ITEM = ITEMS.register("mushroom_pizza",
            () -> new BlockItem(ModBlocks.MUSHROOM_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.mushroom_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> ONION_BACON_PIZZA = BLOCKS.register("onion_bacon_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.ONION_BACON_PIZZA_SLICE));
    public static final DeferredItem<Item> ONION_BACON_PIZZA_ITEM = ITEMS.register("onion_bacon_pizza",
            () -> new BlockItem(ModBlocks.ONION_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.onion_ingredient", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> ONION_PIZZA = BLOCKS.register("onion_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.ONION_PIZZA_SLICE));
    public static final DeferredItem<Item> ONION_PIZZA_ITEM = ITEMS.register("onion_pizza",
            () -> new BlockItem(ModBlocks.ONION_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.onion_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> SAUSAGE_BACON_PIZZA = BLOCKS.register("sausage_bacon_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.SAUSAGE_BACON_PIZZA_SLICE));
    public static final DeferredItem<Item> SAUSAGE_BACON_PIZZA_ITEM = ITEMS.register("sausage_bacon_pizza",
            () -> new BlockItem(ModBlocks.SAUSAGE_BACON_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient", "tooltip.createfood.bacon_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> SAUSAGE_FISH_PIZZA = BLOCKS.register("sausage_fish_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.SAUSAGE_FISH_PIZZA_SLICE));
    public static final DeferredItem<Item> SAUSAGE_FISH_PIZZA_ITEM = ITEMS.register("sausage_fish_pizza",
            () -> new BlockItem(ModBlocks.SAUSAGE_FISH_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient", "tooltip.createfood.fish_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> SAUSAGE_MUSHROOM_PIZZA = BLOCKS.register("sausage_mushroom_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.SAUSAGE_MUSHROOM_PIZZA_SLICE));
    public static final DeferredItem<Item> SAUSAGE_MUSHROOM_PIZZA_ITEM = ITEMS.register("sausage_mushroom_pizza",
            () -> new BlockItem(ModBlocks.SAUSAGE_MUSHROOM_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient", "tooltip.createfood.mushroom_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> SAUSAGE_ONION_PIZZA = BLOCKS.register("sausage_onion_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.SAUSAGE_ONION_PIZZA_SLICE));
    public static final DeferredItem<Item> SAUSAGE_ONION_PIZZA_ITEM = ITEMS.register("sausage_onion_pizza",
            () -> new BlockItem(ModBlocks.SAUSAGE_ONION_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient", "tooltip.createfood.onion_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> SAUSAGE_PIZZA = BLOCKS.register("sausage_pizza",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.SAUSAGE_PIZZA_SLICE));
    public static final DeferredItem<Item> SAUSAGE_PIZZA_ITEM = ITEMS.register("sausage_pizza",
            () -> new BlockItem(ModBlocks.SAUSAGE_PIZZA.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.sausage_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST = BLOCKS.register("raw_chocolate_graham_cracker_pie_crust",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST_ITEM = ITEMS.register("raw_chocolate_graham_cracker_pie_crust",
            () -> new BlockItem(ModBlocks.RAW_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_GRAHAM_CRACKER_PIE_CRUST = BLOCKS.register("raw_graham_cracker_pie_crust",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_GRAHAM_CRACKER_PIE_CRUST_ITEM = ITEMS.register("raw_graham_cracker_pie_crust",
            () -> new BlockItem(ModBlocks.RAW_GRAHAM_CRACKER_PIE_CRUST.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_PIE_CRUST = BLOCKS.register("raw_pie_crust",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_PIE_CRUST_ITEM = ITEMS.register("raw_pie_crust",
            () -> new BlockItem(ModBlocks.RAW_PIE_CRUST.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_APPLE_CHEESECAKE = BLOCKS.register("raw_apple_cheesecake",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_APPLE_CHEESECAKE_ITEM = ITEMS.register("raw_apple_cheesecake",
            () -> new BlockItem(ModBlocks.RAW_APPLE_CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_APPLE_PIE = BLOCKS.register("raw_apple_pie",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_APPLE_PIE_ITEM = ITEMS.register("raw_apple_pie",
            () -> new BlockItem(ModBlocks.RAW_APPLE_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_BERRY_CHEESECAKE = BLOCKS.register("raw_berry_cheesecake",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_BERRY_CHEESECAKE_ITEM = ITEMS.register("raw_berry_cheesecake",
            () -> new BlockItem(ModBlocks.RAW_BERRY_CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_BERRY_PIE = BLOCKS.register("raw_berry_pie",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_BERRY_PIE_ITEM = ITEMS.register("raw_berry_pie",
            () -> new BlockItem(ModBlocks.RAW_BERRY_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_CHEESECAKE = BLOCKS.register("raw_cheesecake",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CHEESECAKE_ITEM = ITEMS.register("raw_cheesecake",
            () -> new BlockItem(ModBlocks.RAW_CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_CHOCOLATE_PIE = BLOCKS.register("raw_chocolate_pie",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CHOCOLATE_PIE_ITEM = ITEMS.register("raw_chocolate_pie",
            () -> new BlockItem(ModBlocks.RAW_CHOCOLATE_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_CHOCOLATE_PIE_GRAHAM_CRACKER = BLOCKS.register("raw_chocolate_pie_graham_cracker",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CHOCOLATE_PIE_GRAHAM_CRACKER_ITEM = ITEMS.register("raw_chocolate_pie_graham_cracker",
            () -> new BlockItem(ModBlocks.RAW_CHOCOLATE_PIE_GRAHAM_CRACKER.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.graham_cracker_pie_crust_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_CHORUS_FRUIT_CHEESECAKE = BLOCKS.register("raw_chorus_fruit_cheesecake",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CHORUS_FRUIT_CHEESECAKE_ITEM = ITEMS.register("raw_chorus_fruit_cheesecake",
            () -> new BlockItem(ModBlocks.RAW_CHORUS_FRUIT_CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_CHORUS_FRUIT_PIE = BLOCKS.register("raw_chorus_fruit_pie",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CHORUS_FRUIT_PIE_ITEM = ITEMS.register("raw_chorus_fruit_pie",
            () -> new BlockItem(ModBlocks.RAW_CHORUS_FRUIT_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = BLOCKS.register("raw_cream_pie_chocolate_graham_cracker",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_ITEM = ITEMS.register("raw_cream_pie_chocolate_graham_cracker",
            () -> new BlockItem(ModBlocks.RAW_CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_graham_cracker_pie_crust_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_CREAM_PIE_GRAHAM_CRACKER = BLOCKS.register("raw_cream_pie_graham_cracker",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_CREAM_PIE_GRAHAM_CRACKER_ITEM = ITEMS.register("raw_cream_pie_graham_cracker",
            () -> new BlockItem(ModBlocks.RAW_CREAM_PIE_GRAHAM_CRACKER.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.graham_cracker_pie_crust_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> RAW_GLOW_BERRY_CHEESECAKE = BLOCKS.register("raw_glow_berry_cheesecake",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_GLOW_BERRY_CHEESECAKE_ITEM = ITEMS.register("raw_glow_berry_cheesecake",
            () -> new BlockItem(ModBlocks.RAW_GLOW_BERRY_CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_GLOW_BERRY_PIE = BLOCKS.register("raw_glow_berry_pie",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_GLOW_BERRY_PIE_ITEM = ITEMS.register("raw_glow_berry_pie",
            () -> new BlockItem(ModBlocks.RAW_GLOW_BERRY_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RAW_PUMPKIN_PIE = BLOCKS.register("raw_pumpkin_pie",
            () -> new RawPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> RAW_PUMPKIN_PIE_ITEM = ITEMS.register("raw_pumpkin_pie",
            () -> new BlockItem(ModBlocks.RAW_PUMPKIN_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> APPLE_CHEESECAKE = BLOCKS.register("apple_cheesecake",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.APPLE_CHEESECAKE_SLICE));
    public static final DeferredItem<Item> APPLE_CHEESECAKE_ITEM = ITEMS.register("apple_cheesecake",
            () -> new BlockItem(ModBlocks.APPLE_CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> BERRY_PIE = BLOCKS.register("berry_pie",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.BERRY_PIE_SLICE));
    public static final DeferredItem<Item> BERRY_PIE_ITEM = ITEMS.register("berry_pie",
            () -> new BlockItem(ModBlocks.BERRY_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> CHEESECAKE = BLOCKS.register("cheesecake",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHEESECAKE_SLICE));
    public static final DeferredItem<Item> CHEESECAKE_ITEM = ITEMS.register("cheesecake",
            () -> new BlockItem(ModBlocks.CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> CHOCOLATE_PIE_GRAHAM_CRACKER = BLOCKS.register("chocolate_pie_graham_cracker",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_PIE_GRAHAM_CRACKER_SLICE));
    public static final DeferredItem<Item> CHOCOLATE_PIE_GRAHAM_CRACKER_ITEM = ITEMS.register("chocolate_pie_graham_cracker",
            () -> new BlockItem(ModBlocks.CHOCOLATE_PIE_GRAHAM_CRACKER.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.graham_cracker_pie_crust_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHORUS_FRUIT_CHEESECAKE = BLOCKS.register("chorus_fruit_cheesecake",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHORUS_FRUIT_CHEESECAKE_SLICE));
    public static final DeferredItem<Item> CHORUS_FRUIT_CHEESECAKE_ITEM = ITEMS.register("chorus_fruit_cheesecake",
            () -> new BlockItem(ModBlocks.CHORUS_FRUIT_CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> CHORUS_FRUIT_PIE = BLOCKS.register("chorus_fruit_pie",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHORUS_FRUIT_PIE_SLICE));
    public static final DeferredItem<Item> CHORUS_FRUIT_PIE_ITEM = ITEMS.register("chorus_fruit_pie",
            () -> new BlockItem(ModBlocks.CHORUS_FRUIT_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> COOKIE_CREAM_PIE = BLOCKS.register("cookie_cream_pie",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.COOKIE_CREAM_PIE_SLICE));
    public static final DeferredItem<Item> COOKIE_CREAM_PIE_ITEM = ITEMS.register("cookie_cream_pie",
            () -> new BlockItem(ModBlocks.COOKIE_CREAM_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER = BLOCKS.register("cream_pie_chocolate_graham_cracker",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_SLICE));
    public static final DeferredItem<Item> CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER_ITEM = ITEMS.register("cream_pie_chocolate_graham_cracker",
            () -> new BlockItem(ModBlocks.CREAM_PIE_CHOCOLATE_GRAHAM_CRACKER.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_graham_cracker_pie_crust_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CREAM_PIE_GRAHAM_CRACKER = BLOCKS.register("cream_pie_graham_cracker",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CREAM_PIE_GRAHAM_CRACKER_SLICE));
    public static final DeferredItem<Item> CREAM_PIE_GRAHAM_CRACKER_ITEM = ITEMS.register("cream_pie_graham_cracker",
            () -> new BlockItem(ModBlocks.CREAM_PIE_GRAHAM_CRACKER.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.graham_cracker_pie_crust_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> GLOW_BERRY_CHEESECAKE = BLOCKS.register("glow_berry_cheesecake",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.GLOW_BERRY_CHEESECAKE_SLICE));
    public static final DeferredItem<Item> GLOW_BERRY_CHEESECAKE_ITEM = ITEMS.register("glow_berry_cheesecake",
            () -> new BlockItem(ModBlocks.GLOW_BERRY_CHEESECAKE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> GLOW_BERRY_PIE = BLOCKS.register("glow_berry_pie",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.GLOW_BERRY_PIE_SLICE));
    public static final DeferredItem<Item> GLOW_BERRY_PIE_ITEM = ITEMS.register("glow_berry_pie",
            () -> new BlockItem(ModBlocks.GLOW_BERRY_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> SMORES_PIE = BLOCKS.register("smores_pie",
            () -> new ModPieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.SMORES_PIE_SLICE));
    public static final DeferredItem<Item> SMORES_PIE_ITEM = ITEMS.register("smores_pie",
            () -> new BlockItem(ModBlocks.SMORES_PIE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> WAFFLE = BLOCKS.register("waffle",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.MINI_WAFFLE));
    public static final DeferredItem<Item> WAFFLE_ITEM = ITEMS.register("waffle",
            () -> new BlockItem(ModBlocks.WAFFLE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> BUTTERSCOTCH_CHIP_WAFFLE = BLOCKS.register("butterscotch_chip_waffle",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.BUTTERSCOTCH_CHIP_MINI_WAFFLE));
    public static final DeferredItem<Item> BUTTERSCOTCH_CHIP_WAFFLE_ITEM = ITEMS.register("butterscotch_chip_waffle",
            () -> new BlockItem(ModBlocks.BUTTERSCOTCH_CHIP_WAFFLE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.butterscotch_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CARAMEL_CHIP_WAFFLE = BLOCKS.register("caramel_chip_waffle",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CARAMEL_CHIP_MINI_WAFFLE));
    public static final DeferredItem<Item> CARAMEL_CHIP_WAFFLE_ITEM = ITEMS.register("caramel_chip_waffle",
            () -> new BlockItem(ModBlocks.CARAMEL_CHIP_WAFFLE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.caramel_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHOCOLATE_CHIP_WAFFLE = BLOCKS.register("chocolate_chip_waffle",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_CHIP_MINI_WAFFLE));
    public static final DeferredItem<Item> CHOCOLATE_CHIP_WAFFLE_ITEM = ITEMS.register("chocolate_chip_waffle",
            () -> new BlockItem(ModBlocks.CHOCOLATE_CHIP_WAFFLE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> DARK_CHOCOLATE_CHIP_WAFFLE = BLOCKS.register("dark_chocolate_chip_waffle",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.DARK_CHOCOLATE_CHIP_MINI_WAFFLE));
    public static final DeferredItem<Item> DARK_CHOCOLATE_CHIP_WAFFLE_ITEM = ITEMS.register("dark_chocolate_chip_waffle",
            () -> new BlockItem(ModBlocks.DARK_CHOCOLATE_CHIP_WAFFLE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.dark_chocolate_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });
    public static final DeferredBlock<Block> WHITE_CHOCOLATE_CHIP_WAFFLE = BLOCKS.register("white_chocolate_chip_waffle",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.WHITE_CHOCOLATE_CHIP_MINI_WAFFLE));
    public static final DeferredItem<Item> WHITE_CHOCOLATE_CHIP_WAFFLE_ITEM = ITEMS.register("white_chocolate_chip_waffle",
            () -> new BlockItem(ModBlocks.WHITE_CHOCOLATE_CHIP_WAFFLE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.white_chocolate_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> TOFFEE_CHIP_WAFFLE = BLOCKS.register("toffee_chip_waffle",
            () -> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.TOFFEE_CHIP_MINI_WAFFLE));
    public static final DeferredItem<Item> TOFFEE_CHIP_WAFFLE_ITEM = ITEMS.register("toffee_chip_waffle",
            () -> new BlockItem(ModBlocks.TOFFEE_CHIP_WAFFLE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.toffee_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CAKE_BASE = BLOCKS.register("cake_base",
            () -> new CakeBaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> CAKE_BASE_ITEM = ITEMS.register("cake_base",
            () -> new BlockItem(ModBlocks.CAKE_BASE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> UBE_CAKE_BASE = BLOCKS.register("ube_cake_base",
            () -> new CakeBaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final DeferredItem<Item> UBE_CAKE_BASE_ITEM = ITEMS.register("ube_cake_base",
            () -> new BlockItem(ModBlocks.UBE_CAKE_BASE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> BERRY_CREAM_CAKE = BLOCKS.register("berry_cream_cake",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.BERRY_CREAM_CAKE_SLICE));
    public static final DeferredItem<Item> BERRY_CREAM_CAKE_ITEM = ITEMS.register("berry_cream_cake",
            () -> new BlockItem(ModBlocks.BERRY_CREAM_CAKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.berry_cream_frosting_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> APPLE_CREAM_CAKE = BLOCKS.register("apple_cream_cake",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.APPLE_CREAM_CAKE_SLICE));
    public static final DeferredItem<Item> APPLE_CREAM_CAKE_ITEM = ITEMS.register("apple_cream_cake",
            () -> new BlockItem(ModBlocks.APPLE_CREAM_CAKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.apple_cream_frosting_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> MELON_CREAM_CAKE = BLOCKS.register("melon_cream_cake",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.MELON_CREAM_CAKE_SLICE));
    public static final DeferredItem<Item> MELON_CREAM_CAKE_ITEM = ITEMS.register("melon_cream_cake",
            () -> new BlockItem(ModBlocks.MELON_CREAM_CAKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.melon_cream_frosting_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE = BLOCKS.register("chocolate_cream_cake",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_CREAM_CAKE_SLICE));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_ITEM = ITEMS.register("chocolate_cream_cake",
            () -> new BlockItem(ModBlocks.CHOCOLATE_CREAM_CAKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_cream_frosting_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHORUS_FRUIT_CREAM_CAKE = BLOCKS.register("chorus_fruit_cream_cake",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_ITEM = ITEMS.register("chorus_fruit_cream_cake",
            () -> new BlockItem(ModBlocks.CHORUS_FRUIT_CREAM_CAKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chorus_fruit_cream_frosting_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CREAM_CAKE = BLOCKS.register("cream_cake",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CREAM_CAKE_SLICE));
    public static final DeferredItem<Item> CREAM_CAKE_ITEM = ITEMS.register("cream_cake",
            () -> new BlockItem(ModBlocks.CREAM_CAKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.cream_frosting_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> GLOW_BERRY_CREAM_CREAM_CAKE = BLOCKS.register("glow_berry_cream_cake",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.GLOW_BERRY_CREAM_CAKE_SLICE));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CREAM_CAKE_ITEM = ITEMS.register("glow_berry_cream_cake",
            () -> new BlockItem(ModBlocks.GLOW_BERRY_CREAM_CREAM_CAKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.glow_berry_cream_frosting_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> UBE_CREAM_UBE_CAKE = BLOCKS.register("ube_cream_ube_cake",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.UBE_CREAM_UBE_CAKE_SLICE));
    public static final DeferredItem<Item> UBE_CREAM_UBE_CAKE_ITEM = ITEMS.register("ube_cream_ube_cake",
            () -> new BlockItem(ModBlocks.UBE_CREAM_UBE_CAKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.ube_cream_frosting_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CREAM_CAKE_CHORUS_FRUIT = BLOCKS.register("cream_cake_chorus_fruit",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CREAM_CAKE_SLICE_CHORUS_FRUIT));
    public static final DeferredItem<Item> CREAM_CAKE_CHORUS_FRUIT_ITEM = ITEMS.register("cream_cake_chorus_fruit",
            () -> new BlockItem(ModBlocks.CREAM_CAKE_CHORUS_FRUIT.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.cream_frosting_ingredient", "tooltip.createfood.chorus_fruit_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CREAM_CAKE_GLOW_BERRY = BLOCKS.register("cream_cake_glow_berry",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CREAM_CAKE_SLICE_GLOW_BERRY));
    public static final DeferredItem<Item> CREAM_CAKE_GLOW_BERRY_ITEM = ITEMS.register("cream_cake_glow_berry",
            () -> new BlockItem(ModBlocks.CREAM_CAKE_GLOW_BERRY.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.cream_frosting_ingredient", "tooltip.createfood.glow_berry_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });
    public static final DeferredBlock<Block> BERRY_CREAM_CAKE_SWEET_BERRY = BLOCKS.register("berry_cream_cake_sweet_berry",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.BERRY_CREAM_CAKE_SLICE_SWEET_BERRY));
    public static final DeferredItem<Item> BERRY_CREAM_CAKE_SWEET_BERRY_ITEM = ITEMS.register("berry_cream_cake_sweet_berry",
            () -> new BlockItem(ModBlocks.BERRY_CREAM_CAKE_SWEET_BERRY.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.berry_cream_frosting_ingredient", "tooltip.createfood.berry_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> BERRY_CREAM_CAKE_CHORUS_FRUIT = BLOCKS.register("berry_cream_cake_chorus_fruit",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT));
    public static final DeferredItem<Item> BERRY_CREAM_CAKE_CHORUS_FRUIT_ITEM = ITEMS.register("berry_cream_cake_chorus_fruit",
            () -> new BlockItem(ModBlocks.BERRY_CREAM_CAKE_CHORUS_FRUIT.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.berry_cream_frosting_ingredient", "tooltip.createfood.chorus_fruit_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> BERRY_CREAM_CAKE_GLOW_BERRY = BLOCKS.register("berry_cream_cake_glow_berry",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.BERRY_CREAM_CAKE_SLICE_GLOW_BERRY));
    public static final DeferredItem<Item> BERRY_CREAM_CAKE_GLOW_BERRY_ITEM = ITEMS.register("berry_cream_cake_glow_berry",
            () -> new BlockItem(ModBlocks.BERRY_CREAM_CAKE_GLOW_BERRY.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.berry_cream_frosting_ingredient", "tooltip.createfood.glow_berry_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH = BLOCKS.register("chocolate_cream_cake_butterscotch",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_CREAM_CAKE_SLICE_BUTTERSCOTCH));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH_ITEM = ITEMS.register("chocolate_cream_cake_butterscotch",
            () -> new BlockItem(ModBlocks.CHOCOLATE_CREAM_CAKE_BUTTERSCOTCH.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_cream_frosting_ingredient", "tooltip.createfood.butterscotch_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_CARAMEL = BLOCKS.register("chocolate_cream_cake_caramel",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_CARAMEL_ITEM = ITEMS.register("chocolate_cream_cake_caramel",
            () -> new BlockItem(ModBlocks.CHOCOLATE_CREAM_CAKE_CARAMEL.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_cream_frosting_ingredient", "tooltip.createfood.caramel_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_CHOCOLATE = BLOCKS.register("chocolate_cream_cake_chocolate",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CHOCOLATE));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_CHOCOLATE_ITEM = ITEMS.register("chocolate_cream_cake_chocolate",
            () -> new BlockItem(ModBlocks.CHOCOLATE_CREAM_CAKE_CHOCOLATE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_cream_frosting_ingredient", "tooltip.createfood.chocolate_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE = BLOCKS.register("chocolate_cream_cake_dark_chocolate",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_CREAM_CAKE_SLICE_DARK_CHOCOLATE));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE_ITEM = ITEMS.register("chocolate_cream_cake_dark_chocolate",
            () -> new BlockItem(ModBlocks.CHOCOLATE_CREAM_CAKE_DARK_CHOCOLATE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_cream_frosting_ingredient", "tooltip.createfood.dark_chocolate_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });
    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_TOFFEE = BLOCKS.register("chocolate_cream_cake_toffee",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_CREAM_CAKE_SLICE_TOFFEE));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_TOFFEE_ITEM = ITEMS.register("chocolate_cream_cake_toffee",
            () -> new BlockItem(ModBlocks.CHOCOLATE_CREAM_CAKE_TOFFEE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_cream_frosting_ingredient", "tooltip.createfood.toffee_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE = BLOCKS.register("chocolate_cream_cake_white_chocolate",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHOCOLATE_CREAM_CAKE_SLICE_WHITE_CHOCOLATE));
    public static final DeferredItem<Item> CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE_ITEM = ITEMS.register("chocolate_cream_cake_white_chocolate",
            () -> new BlockItem(ModBlocks.CHOCOLATE_CREAM_CAKE_WHITE_CHOCOLATE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chocolate_cream_frosting_ingredient", "tooltip.createfood.white_chocolate_chips_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY = BLOCKS.register("chorus_fruit_cream_cake_sweet_berry",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_SWEET_BERRY));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY_ITEM = ITEMS.register("chorus_fruit_cream_cake_sweet_berry",
            () -> new BlockItem(ModBlocks.CHORUS_FRUIT_CREAM_CAKE_SWEET_BERRY.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chorus_fruit_cream_frosting_ingredient", "tooltip.createfood.berry_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT = BLOCKS.register("chorus_fruit_cream_cake_chorus_fruit",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_CHORUS_FRUIT));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT_ITEM = ITEMS.register("chorus_fruit_cream_cake_chorus_fruit",
            () -> new BlockItem(ModBlocks.CHORUS_FRUIT_CREAM_CAKE_CHORUS_FRUIT.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chorus_fruit_cream_frosting_ingredient", "tooltip.createfood.chorus_fruit_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY = BLOCKS.register("chorus_fruit_cream_cake_glow_berry",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE_GLOW_BERRY));
    public static final DeferredItem<Item> CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY_ITEM = ITEMS.register("chorus_fruit_cream_cake_glow_berry",
            () -> new BlockItem(ModBlocks.CHORUS_FRUIT_CREAM_CAKE_GLOW_BERRY.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.chorus_fruit_cream_frosting_ingredient", "tooltip.createfood.glow_berry_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> GLOW_BERRY_CREAM_CAKE_SWEET_BERRY = BLOCKS.register("glow_berry_cream_cake_sweet_berry",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_SWEET_BERRY));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CREAM_CAKE_SWEET_BERRY_ITEM = ITEMS.register("glow_berry_cream_cake_sweet_berry",
            () -> new BlockItem(ModBlocks.GLOW_BERRY_CREAM_CAKE_SWEET_BERRY.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.glow_berry_cream_frosting_ingredient", "tooltip.createfood.berry_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> GLOW_BERRY_CREAM_CAKE_CHORUS_FRUIT = BLOCKS.register("glow_berry_cream_cake_chorus_fruit",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_CHORUS_FRUIT));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CREAM_CAKE_CHORUS_FRUIT_ITEM = ITEMS.register("glow_berry_cream_cake_chorus_fruit",
            () -> new BlockItem(ModBlocks.GLOW_BERRY_CREAM_CAKE_CHORUS_FRUIT.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.glow_berry_cream_frosting_ingredient", "tooltip.createfood.chorus_fruit_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> GLOW_BERRY_CREAM_CAKE_GLOW_BERRY = BLOCKS.register("glow_berry_cream_cake_glow_berry",
            () -> new ModCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.GLOW_BERRY_CREAM_CAKE_SLICE_GLOW_BERRY));
    public static final DeferredItem<Item> GLOW_BERRY_CREAM_CREAM_CAKE_GLOW_BERRY_ITEM = ITEMS.register("glow_berry_cream_cake_glow_berry",
            () -> new BlockItem(ModBlocks.GLOW_BERRY_CREAM_CAKE_GLOW_BERRY.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
                    addModTooltip(components, "tooltip.createfood.default", "tooltip.createfood.glow_berry_cream_frosting_ingredient", "tooltip.createfood.glow_berry_ingredient");
                    super.appendHoverText(stack, context, components, flag);
                }
            });

    public static final DeferredBlock<Block> YELLOW_GELATIN_DESSERT_BLOCK = BLOCKS.register("yellow_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> YELLOW_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("yellow_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.YELLOW_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> GELATIN_DESSERT_BLOCK = BLOCKS.register("gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> ORANGE_GELATIN_DESSERT_BLOCK = BLOCKS.register("orange_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> ORANGE_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("orange_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.ORANGE_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> MAGENTA_GELATIN_DESSERT_BLOCK = BLOCKS.register("magenta_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> MAGENTA_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("magenta_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.MAGENTA_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> LIGHT_BLUE_GELATIN_DESSERT_BLOCK = BLOCKS.register("light_blue_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> LIGHT_BLUE_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("light_blue_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> LIME_GELATIN_DESSERT_BLOCK = BLOCKS.register("lime_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> LIME_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("lime_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.LIME_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> PINK_GELATIN_DESSERT_BLOCK = BLOCKS.register("pink_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> PINK_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("pink_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.PINK_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> GRAY_GELATIN_DESSERT_BLOCK = BLOCKS.register("gray_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> GRAY_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("gray_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.GRAY_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> LIGHT_GRAY_GELATIN_DESSERT_BLOCK = BLOCKS.register("light_gray_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> LIGHT_GRAY_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("light_gray_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> CYAN_GELATIN_DESSERT_BLOCK = BLOCKS.register("cyan_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> CYAN_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("cyan_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.CYAN_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> PURPLE_GELATIN_DESSERT_BLOCK = BLOCKS.register("purple_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> PURPLE_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("purple_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.PURPLE_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> BLUE_GELATIN_DESSERT_BLOCK = BLOCKS.register("blue_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> BLUE_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("blue_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.BLUE_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> BROWN_GELATIN_DESSERT_BLOCK = BLOCKS.register("brown_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> BROWN_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("brown_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.BROWN_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> GREEN_GELATIN_DESSERT_BLOCK = BLOCKS.register("green_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> GREEN_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("green_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.GREEN_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> RED_GELATIN_DESSERT_BLOCK = BLOCKS.register("red_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> RED_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("red_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.RED_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> BLACK_GELATIN_DESSERT_BLOCK = BLOCKS.register("black_gelatin_dessert_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final DeferredItem<Item> BLACK_GELATIN_DESSERT_BLOCK_ITEM = ITEMS.register("black_gelatin_dessert_block",
            () -> new BlockItem(ModBlocks.BLACK_GELATIN_DESSERT_BLOCK.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Blocks");
        BLOCKS.register(eventBus);
    }

}