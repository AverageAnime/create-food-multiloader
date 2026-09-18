package dev.averageanime.forge.item;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.client.tooltip.ItemTooltips;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class TooltipEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemTooltips.onItemTooltip(event.getItemStack(), event.getToolTip());
    }
}
