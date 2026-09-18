package dev.averageanime.forge.block.type.pie;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.pie.PumpkinPieInteraction;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class PumpkinPieEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(PlayerInteractEvent.RightClickBlock event) {
        boolean handled = PumpkinPieInteraction.tryPlace(
                event.getEntity(), event.getLevel(), event.getHand(),
                event.getPos(), event.getFace(),
                event.getItemStack());
        if (handled) {
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
            event.setCanceled(true);
        }
    }
}
