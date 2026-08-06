package dev.averageanime.fabric.block.type.bowl;

import dev.averageanime.block.type.bowl.BowlPlacementInteraction;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class BowlPlacementEvents {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            boolean consumed = BowlPlacementInteraction.tryBowlPlacement(
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
