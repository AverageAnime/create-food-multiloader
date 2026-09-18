package dev.averageanime.forge.item.interaction;

import dev.averageanime.CreateFoodCommon;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class CampfireCookingInteraction {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        dev.averageanime.item.interaction.CampfireCookingInteraction.tickPlayer(player, player.level());
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            dev.averageanime.item.interaction.CampfireCookingInteraction.clearProgress(player);
        }
    }
}
