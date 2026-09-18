package dev.averageanime.forge.block.type.bowl;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.bowl.BowlPlacementInteraction;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class BowlPlacementEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity() == null) return;

        boolean consumed = BowlPlacementInteraction.tryBowlPlacement(
                event.getEntity(), event.getLevel(),
                event.getPos(), event.getFace(),
                event.getItemStack());
        if (consumed) {
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
            event.setCanceled(true);
        }
    }
}
