package dev.averageanime.neoforge.block.handler;

import dev.averageanime.CommonClass;
import dev.averageanime.block.type.display.FoodBlock;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public class FoodPlacementHandler {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext ctx = event.getUseOnContext();
        boolean handled = FoodBlock.Registry.tryPlace(
                ctx.getPlayer(),
                ctx.getLevel(),
                ctx.getHand(),
                ctx.getClickedPos(),
                ctx.getLevel().getBlockState(ctx.getClickedPos()),
                ctx.getClickedFace()
        ) != null;
        if (handled) {
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(ctx.getLevel().isClientSide()));
        }
    }
}