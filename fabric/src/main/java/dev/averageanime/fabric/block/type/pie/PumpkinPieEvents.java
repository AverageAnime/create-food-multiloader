package dev.averageanime.fabric.block.type.pie;

import dev.averageanime.block.type.pie.PumpkinPieInteraction;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class PumpkinPieEvents {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            boolean handled = PumpkinPieInteraction.tryPlace(
                    player, level, hand,
                    hitResult.getBlockPos(), hitResult.getDirection(),
                    player.getItemInHand(hand));
            if (handled) {
                return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        });
    }
}
