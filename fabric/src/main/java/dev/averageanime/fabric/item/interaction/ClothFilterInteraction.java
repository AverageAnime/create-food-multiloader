package dev.averageanime.fabric.item.interaction;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

public class ClothFilterInteraction {

    public static void register() {
        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (hand != InteractionHand.MAIN_HAND)
                return InteractionResultHolder.pass(player.getItemInHand(hand));
            if (level.isClientSide())
                return InteractionResultHolder.pass(player.getItemInHand(hand));

            boolean handled = dev.averageanime.item.interaction.ClothFilterInteraction
                    .tryFilterInteraction(player, level);
            if (handled) {
                return InteractionResultHolder.success(player.getMainHandItem());
            }
            return InteractionResultHolder.pass(player.getMainHandItem());
        });
    }
}
