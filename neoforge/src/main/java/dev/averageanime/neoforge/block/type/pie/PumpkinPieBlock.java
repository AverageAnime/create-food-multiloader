package dev.averageanime.neoforge.block.type.pie;

import dev.averageanime.CommonClass;
import dev.averageanime.block.handler.PumpkinPieHandler;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public class PumpkinPieBlock {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext ctx = event.getUseOnContext();
        if (ctx.getPlayer() == null) return;
        boolean handled = PumpkinPieHandler.tryPlace(
                ctx.getPlayer(), ctx.getLevel(), ctx.getHand(),
                ctx.getClickedPos(), ctx.getClickedFace(),
                ctx.getItemInHand());
        if (handled) {
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(ctx.getLevel().isClientSide()));
        }
    }
}
