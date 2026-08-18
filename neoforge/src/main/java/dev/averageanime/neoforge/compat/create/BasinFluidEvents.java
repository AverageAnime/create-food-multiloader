package dev.averageanime.neoforge.compat.create;

import dev.averageanime.CreateFoodCommon;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class BasinFluidEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        if (event.getUsePhase() != UseItemOnBlockEvent.UsePhase.BLOCK) return;

        UseOnContext ctx = event.getUseOnContext();
        if (ctx.getPlayer() == null) return;

        boolean handled = BasinFluidInteraction.tryInteract(
                ctx.getPlayer(), ctx.getLevel(), ctx.getHand(), ctx.getClickedPos());
        if (handled) {
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(ctx.getLevel().isClientSide()));
        }
    }
}
