package dev.averageanime.neoforge.item;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.client.tooltip.ItemTooltips;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class TooltipEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemTooltips.onItemTooltip(event.getItemStack(), event.getToolTip());
    }
}
