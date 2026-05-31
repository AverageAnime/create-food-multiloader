package dev.averageanime.fabric.block.type.pie;

import dev.averageanime.block.handler.PumpkinPieHandler;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class PumpkinPieBlock {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            boolean handled = PumpkinPieHandler.tryPlace(
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
