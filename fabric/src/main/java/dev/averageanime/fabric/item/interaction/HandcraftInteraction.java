package dev.averageanime.fabric.item.interaction;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

public class HandcraftInteraction {

    public static void register() {
        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (hand != InteractionHand.MAIN_HAND)
                return InteractionResultHolder.pass(player.getItemInHand(hand));
            if (level.isClientSide())
                return InteractionResultHolder.pass(player.getItemInHand(hand));
            if (!(player instanceof ServerPlayer serverPlayer))
                return InteractionResultHolder.pass(player.getItemInHand(hand));

            boolean crafted = dev.averageanime.item.interaction.HandcraftInteraction
                    .tryHandcraft(serverPlayer, level);
            if (crafted) {
                return InteractionResultHolder.success(serverPlayer.getMainHandItem());
            }
            return InteractionResultHolder.pass(serverPlayer.getMainHandItem());
        });
    }
}
