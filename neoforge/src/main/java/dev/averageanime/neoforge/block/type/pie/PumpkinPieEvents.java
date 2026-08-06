package dev.averageanime.neoforge.block.type.pie;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.pie.PumpkinPieInteraction;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class PumpkinPieEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext ctx = event.getUseOnContext();
        if (ctx.getPlayer() == null) return;
        boolean handled = PumpkinPieInteraction.tryPlace(
                ctx.getPlayer(), ctx.getLevel(), ctx.getHand(),
                ctx.getClickedPos(), ctx.getClickedFace(),
                ctx.getItemInHand());
        if (handled) {
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(ctx.getLevel().isClientSide()));
        }
    }
}
