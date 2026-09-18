package dev.averageanime.forge.block.type.plate;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.plate.PlateSliceInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class PlateSliceEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockState state = event.getLevel().getBlockState(event.getPos());
        boolean canCut = PlateSliceInteraction.couldSlice(event.getEntity(), event.getLevel(), event.getHand(), event.getPos(), state);
        boolean canCutOffhand = !canCut && event.getHand() == InteractionHand.MAIN_HAND
                && PlateSliceInteraction.couldSlice(event.getEntity(), event.getLevel(), InteractionHand.OFF_HAND, event.getPos(), state);
        if (event.getLevel().isClientSide()) {
            if (canCut || canCutOffhand) {
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        } else {
            if (PlateSliceInteraction.trySlice(event.getEntity(), event.getLevel(), event.getHand(), event.getPos(), state)) {
                event.setCancellationResult(InteractionResult.CONSUME);
                event.setCanceled(true);
            } else if (canCutOffhand && PlateSliceInteraction.trySlice(event.getEntity(), event.getLevel(), InteractionHand.OFF_HAND, event.getPos(), state)) {
                event.setCancellationResult(InteractionResult.CONSUME);
                event.setCanceled(true);
            }
        }
    }
}
