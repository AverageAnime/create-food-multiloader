package dev.averageanime.neoforge.item;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.client.tooltip.ItemTooltips;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

// Client only: ItemTooltips reaches net.minecraft.client.gui.screens.Screen, which does not exist on a
// dedicated server, and tooltips are never drawn there. Matches the Fabric side, where the equivalent
// ItemTooltipCallback is registered from CreateFoodClient rather than the main entrypoint.
@EventBusSubscriber(modid = CreateFoodCommon.MOD_ID, value = Dist.CLIENT)
public class TooltipEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemTooltips.onItemTooltip(event.getItemStack(), event.getToolTip());
    }
}
