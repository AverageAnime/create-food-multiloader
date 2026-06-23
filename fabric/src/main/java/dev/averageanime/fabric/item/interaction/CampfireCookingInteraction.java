package dev.averageanime.fabric.item.interaction;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;

public class CampfireCookingInteraction {

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(level -> {
            for (ServerPlayer player : level.players()) {
                dev.averageanime.item.interaction.CampfireCookingInteraction.tickPlayer(player, level);
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
                dev.averageanime.item.interaction.CampfireCookingInteraction.clearProgress(handler.player));
    }
}
