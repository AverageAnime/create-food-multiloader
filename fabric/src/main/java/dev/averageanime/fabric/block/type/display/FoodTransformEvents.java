package dev.averageanime.fabric.block.type.display;

import dev.averageanime.block.type.display.BlockFoodTransformInteraction;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class FoodTransformEvents {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            boolean consumed = BlockFoodTransformInteraction.tryTransform(
                    player, level, hand, hitResult.getBlockPos());
            if (consumed) {
                return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        });
    }
}
