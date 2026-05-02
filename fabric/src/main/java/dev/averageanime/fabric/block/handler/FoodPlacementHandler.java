package dev.averageanime.fabric.block.handler;

import dev.averageanime.block.type.display.FoodBlock;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class FoodPlacementHandler {
    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            InteractionResult result = FoodBlock.Registry.tryPlace(
                    player, level, hand,
                    hitResult.getBlockPos(),
                    level.getBlockState(hitResult.getBlockPos()),
                    hitResult.getDirection()
            );
            return result != null ? result : InteractionResult.PASS;
        });
    }
}
