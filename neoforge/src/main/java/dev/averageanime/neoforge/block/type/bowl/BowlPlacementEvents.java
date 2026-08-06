package dev.averageanime.neoforge.block.type.bowl;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.bowl.BowlPlacementInteraction;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class BowlPlacementEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext ctx = event.getUseOnContext();
        if (ctx.getPlayer() == null) return;

        boolean consumed = BowlPlacementInteraction.tryBowlPlacement(
                ctx.getPlayer(), ctx.getLevel(),
                ctx.getClickedPos(), ctx.getClickedFace(),
                ctx.getItemInHand());
        if (consumed) {
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(ctx.getLevel().isClientSide()));
        }
    }
}
