package dev.averageanime.fabric.item.interaction;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;

public class HandcraftInteraction {

    public static void register() {
        // On a block face the off-hand's placement comes through this callback, not UseItemCallback.
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (hand != InteractionHand.OFF_HAND) return InteractionResult.PASS;
            if (level.isClientSide()) return InteractionResult.PASS;
            if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;

            if (dev.averageanime.item.interaction.HandcraftInteraction
                    .consumeRecentTwoHandCraft(serverPlayer, level)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (level.isClientSide())
                return InteractionResultHolder.pass(player.getItemInHand(hand));
            if (!(player instanceof ServerPlayer serverPlayer))
                return InteractionResultHolder.pass(player.getItemInHand(hand));

            if (hand == InteractionHand.OFF_HAND) {
                // The client may try the off-hand after a two-slot craft already consumed the item.
                if (dev.averageanime.item.interaction.HandcraftInteraction
                        .consumeRecentTwoHandCraft(serverPlayer, level)) {
                    return InteractionResultHolder.success(serverPlayer.getOffhandItem());
                }
                return InteractionResultHolder.pass(serverPlayer.getItemInHand(hand));
            }

            boolean crafted = dev.averageanime.item.interaction.HandcraftInteraction
                    .tryHandcraft(serverPlayer, level);
            if (crafted) {
                return InteractionResultHolder.success(serverPlayer.getMainHandItem());
            }
            return InteractionResultHolder.pass(serverPlayer.getMainHandItem());
        });
    }
}
