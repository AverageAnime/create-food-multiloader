package dev.averageanime.forge.compat.create;

import dev.averageanime.CreateFoodCommon;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class BasinFluidEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(PlayerInteractEvent.RightClickBlock event) {
        boolean handled = BasinFluidInteraction.tryInteract(
                event.getEntity(), event.getLevel(), event.getHand(), event.getPos());
        if (handled) {
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
            event.setCanceled(true);
        }
    }
}
