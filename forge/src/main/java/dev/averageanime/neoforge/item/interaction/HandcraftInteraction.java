package dev.averageanime.forge.item.interaction;

import dev.averageanime.CreateFoodCommon;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class HandcraftInteraction {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (event.getHand() == InteractionHand.OFF_HAND) {
            if (dev.averageanime.item.interaction.HandcraftInteraction
                    .consumeRecentTwoHandCraft(player, event.getLevel())) {
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
            return;
        }

        boolean crafted = dev.averageanime.item.interaction.HandcraftInteraction
                .tryHandcraft(player, event.getLevel());
        if (crafted) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.OFF_HAND) return;
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (dev.averageanime.item.interaction.HandcraftInteraction
                .consumeRecentTwoHandCraft(player, event.getLevel())) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
