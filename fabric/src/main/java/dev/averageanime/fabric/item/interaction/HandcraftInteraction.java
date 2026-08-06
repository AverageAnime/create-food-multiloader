package dev.averageanime.fabric.item.interaction;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;

public class HandcraftInteraction {

    public static void register() {
        // When the player right-clicks a block face (not open air), the off-hand's block
        // placement goes through this callback instead of UseItemCallback - the client tries
        // the off-hand's block interaction independently of the main-hand's own (locally-
        // unaware-of-our-recipe) result, so this is where the stray placement actually happens
        // after a two-slot craft.
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
                // The client decides locally (before any server response) whether to also try
                // the off-hand item, so this can fire even after we've already consumed it as
                // part of a two-slot craft on the main-hand invocation above. Swallow it then.
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
