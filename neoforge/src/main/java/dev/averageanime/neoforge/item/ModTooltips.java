package dev.averageanime.neoforge.item;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

import static dev.averageanime.neoforge.item.ModItems.addModTooltip;

@EventBusSubscriber(modid = "createfood", bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModTooltips {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltips = event.getToolTip();

        if (stack.is(Items.CAKE)) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.cream_frosting_ingredient", "tooltip.createfood.berry_ingredient");
        }
        if (stack.is(Items.COOKIE)) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.chocolate_chips_ingredient");
        }
        // Example: Add tooltip to Create items using their registry name
        String itemId = stack.getItem().builtInRegistryHolder().key().location().toString();

        if (itemId.equals("create:sweet_roll")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.cream_frosting_ingredient");
        }
        if (itemId.equals("create:honeyed_apple")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.honey_ingredient");
        }
        if (itemId.equals("create:chocolate_glazed_berries")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.chocolate_ingredient");
        }
        if (itemId.equals("farmersdelight:cake_slice")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.cream_frosting_ingredient", "tooltip.createfood.berry_ingredient");
        }
        if (itemId.equals("farmersdelight:mutton_wrap")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.mutton_ingredient", "tooltip.createfood.onion_ingredient", "tooltip.createfood.lettuce_ingredient");
        }
        if (itemId.equals("farmersdelight:chicken_sandwich")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.lettuce_ingredient", "tooltip.createfood.carrot_ingredient");
        }
        if (itemId.equals("farmersdelight:hamburger")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.onion_ingredient", "tooltip.createfood.lettuce_ingredient", "tooltip.createfood.tomato_ingredient");
        }
        if (itemId.equals("farmersdelight:bacon_sandwich")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.bacon_ingredient", "tooltip.createfood.lettuce_ingredient", "tooltip.createfood.tomato_ingredient");
        }
        if (itemId.equals("farmersdelight:egg_sandwich")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.fried_egg_ingredient");
        }
        if (itemId.equals("farmersdelight:mixed_salad")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.beetroot_ingredient", "tooltip.createfood.tomato_ingredient");
        }
        if (itemId.equals("farmersdelight:squid_ink_pasta")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.squid_ink_ingredient", "tooltip.createfood.fish_ingredient");
        }
        if (itemId.equals("farmersdelight:pasta_with_mutton_chop")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.tomato_sauce_ingredient", "tooltip.createfood.mutton_ingredient");
        }
        if (itemId.equals("farmersdelight:pasta_with_meatballs")) {
            addModTooltip(tooltips, "tooltip.createfood.default", "tooltip.createfood.tomato_sauce_ingredient", "tooltip.createfood.beef_meatballs_ingredient");
        }
    }
}