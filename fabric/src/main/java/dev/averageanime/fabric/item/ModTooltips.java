package dev.averageanime.fabric.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ModTooltips {

    public static void addTooltip(List<Component> tooltips, String modName, String... ingredientLines) {
        dev.averageanime.item.ModTooltips.addTooltip(tooltips, modName, ingredientLines);
    }

    public static void onItemTooltip(ItemStack stack, List<Component> lines) {
        dev.averageanime.item.ModTooltips.onItemTooltip(stack, lines);
    }
}
