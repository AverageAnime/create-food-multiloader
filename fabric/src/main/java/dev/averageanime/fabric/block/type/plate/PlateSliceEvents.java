package dev.averageanime.fabric.block.type.plate;

import dev.averageanime.block.type.plate.PlateSliceInteraction;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;

public class PlateSliceEvents {
    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            boolean canCut = PlateSliceInteraction.couldSlice(player, level, hand, pos, state);
            boolean canCutOffhand = !canCut && hand == InteractionHand.MAIN_HAND
                    && PlateSliceInteraction.couldSlice(player, level, InteractionHand.OFF_HAND, pos, state);
            if (level.isClientSide) {
                return (canCut || canCutOffhand) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }
            if (PlateSliceInteraction.trySlice(player, level, hand, pos, state)) {
                return InteractionResult.CONSUME;
            }
            if (canCutOffhand && PlateSliceInteraction.trySlice(player, level, InteractionHand.OFF_HAND, pos, state)) {
                return InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        });
    }
}
