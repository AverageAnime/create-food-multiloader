package dev.averageanime.fabric.block.type.display;

import dev.averageanime.block.type.display.BlockFoodConversion;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class FoodConversionEvents {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            boolean consumed = BlockFoodConversion.tryTransform(
                    player, level, hand, hitResult.getBlockPos());
            if (consumed) {
                return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        });
    }
}
