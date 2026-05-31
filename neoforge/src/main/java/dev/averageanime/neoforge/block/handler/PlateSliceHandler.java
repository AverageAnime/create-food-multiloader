package dev.averageanime.neoforge.block.handler;

import dev.averageanime.CommonClass;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public class PlateSliceHandler {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext ctx = event.getUseOnContext();
        BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());
        boolean canCut = dev.averageanime.block.handler.PlateSliceHandler.couldSlice(ctx.getPlayer(), ctx.getLevel(), ctx.getHand(), ctx.getClickedPos(), state);
        boolean canCutOffhand = !canCut && ctx.getHand() == InteractionHand.MAIN_HAND
                && dev.averageanime.block.handler.PlateSliceHandler.couldSlice(ctx.getPlayer(), ctx.getLevel(), InteractionHand.OFF_HAND, ctx.getClickedPos(), state);
        if (ctx.getLevel().isClientSide()) {
            if (canCut || canCutOffhand) {
                event.cancelWithResult(ItemInteractionResult.SUCCESS);
            }
        } else {
            if (dev.averageanime.block.handler.PlateSliceHandler.trySlice(ctx.getPlayer(), ctx.getLevel(), ctx.getHand(), ctx.getClickedPos(), state)) {
                event.cancelWithResult(ItemInteractionResult.CONSUME);
            } else if (canCutOffhand && dev.averageanime.block.handler.PlateSliceHandler.trySlice(ctx.getPlayer(), ctx.getLevel(), InteractionHand.OFF_HAND, ctx.getClickedPos(), state)) {
                event.cancelWithResult(ItemInteractionResult.CONSUME);
            }
        }
    }
}
