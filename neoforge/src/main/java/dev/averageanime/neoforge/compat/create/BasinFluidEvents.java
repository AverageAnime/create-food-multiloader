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
        // UseItemOnBlockEvent fires once per phase (ITEM_BEFORE_BLOCK, BLOCK, ITEM_AFTER_BLOCK) for a
        // single click; filtered to BLOCK, the phase matching the block's own use-item-on behavior,
        // so this doesn't run the basin fill/drain check three times per interaction.
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
