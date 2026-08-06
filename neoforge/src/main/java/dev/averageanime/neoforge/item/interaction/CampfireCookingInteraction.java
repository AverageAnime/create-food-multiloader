package dev.averageanime.neoforge.item.interaction;

import dev.averageanime.CreateFoodCommon;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class CampfireCookingInteraction {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        dev.averageanime.item.interaction.CampfireCookingInteraction.tickPlayer(player, player.level());
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            dev.averageanime.item.interaction.CampfireCookingInteraction.clearProgress(player);
        }
    }
}
