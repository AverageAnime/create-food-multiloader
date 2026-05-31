package dev.averageanime.neoforge.block.handler;

import dev.averageanime.CommonClass;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public class BowlPlacementHandler {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext ctx = event.getUseOnContext();
        if (ctx.getPlayer() == null) return;

        boolean consumed = dev.averageanime.block.handler.BowlPlacementHandler.tryBowlPlacement(
                ctx.getPlayer(), ctx.getLevel(),
                ctx.getClickedPos(), ctx.getClickedFace(),
                ctx.getItemInHand());
        if (consumed) {
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(ctx.getLevel().isClientSide()));
        }
    }
}
