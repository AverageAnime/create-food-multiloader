package dev.averageanime.neoforge.item;

import dev.averageanime.CommonClass;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public class ModTooltips {

    public static void addTooltip(List<Component> tooltips, String modName, String... ingredientLines) {
        dev.averageanime.item.ModTooltips.addTooltip(tooltips, modName, ingredientLines);
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        dev.averageanime.item.ModTooltips.onItemTooltip(event.getItemStack(), event.getToolTip());
    }
}
