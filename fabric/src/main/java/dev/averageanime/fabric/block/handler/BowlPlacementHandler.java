package dev.averageanime.fabric.block.handler;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class BowlPlacementHandler {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            boolean consumed = dev.averageanime.block.handler.BowlPlacementHandler.tryBowlPlacement(
                    player, level,
                    hitResult.getBlockPos(),
                    hitResult.getDirection(),
                    player.getItemInHand(hand));
            if (consumed) {
                return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        });
    }
}
