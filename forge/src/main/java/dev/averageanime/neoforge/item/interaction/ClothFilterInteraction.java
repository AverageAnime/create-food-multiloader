package dev.averageanime.forge.item.interaction;

import dev.averageanime.CreateFoodCommon;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class ClothFilterInteraction {

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (event.getLevel().isClientSide()) return;

        boolean handled = dev.averageanime.item.interaction.ClothFilterInteraction
                .tryFilterInteraction(event.getEntity(), event.getLevel());
        if (handled) {
            event.setCanceled(true);
        }
    }
}
