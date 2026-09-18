package dev.averageanime.forge.block.type.display;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.display.BlockFoodTransformation;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class FoodTransformEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(PlayerInteractEvent.RightClickBlock event) {

        boolean consumed = BlockFoodTransformation.tryTransform(
                event.getEntity(), event.getLevel(), event.getHand(), event.getPos());
        if (consumed) {
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
            event.setCanceled(true);
        }
    }
}
