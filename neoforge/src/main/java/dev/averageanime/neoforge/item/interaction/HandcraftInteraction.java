package dev.averageanime.neoforge.item.interaction;

import dev.averageanime.CommonClass;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public class HandcraftInteraction {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        boolean crafted = dev.averageanime.item.interaction.HandcraftInteraction
                .tryHandcraft(player, event.getLevel());
        if (crafted) {
            event.setCanceled(true);
        }
    }
}
