package dev.averageanime.neoforge.item.interaction;

import dev.averageanime.CommonClass;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public class ClothFilterInteraction {

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (event.getLevel().isClientSide()) return;

        boolean handled = dev.averageanime.item.interaction.ClothFilterInteraction
                .tryFilterInteraction(event.getEntity(), event.getLevel());
        if (handled) {
            event.setCanceled(true);
        }
    }
}
