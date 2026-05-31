package dev.averageanime.fabric.block.handler;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;

public class PlateSliceHandler {
    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            boolean canCut = dev.averageanime.block.handler.PlateSliceHandler.couldSlice(player, level, hand, pos, state);
            boolean canCutOffhand = !canCut && hand == InteractionHand.MAIN_HAND
                    && dev.averageanime.block.handler.PlateSliceHandler.couldSlice(player, level, InteractionHand.OFF_HAND, pos, state);
            if (level.isClientSide) {
                return (canCut || canCutOffhand) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }
            if (dev.averageanime.block.handler.PlateSliceHandler.trySlice(player, level, hand, pos, state)) {
                return InteractionResult.CONSUME;
            }
            if (canCutOffhand && dev.averageanime.block.handler.PlateSliceHandler.trySlice(player, level, InteractionHand.OFF_HAND, pos, state)) {
                return InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        });
    }
}
