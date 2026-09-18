package dev.averageanime.forge.block.type.misc;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.bowl.GenericDisplayBowlBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.level.block.state.BlockState;
import dev.averageanime.util.FoodAccess;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public class FoodPlacementHandler {

    @SubscribeEvent
    public static void onUseItemOnBlock(PlayerInteractEvent.RightClickBlock event) {
        boolean handled = FoodBlock.Registry.tryPlace(
                event.getEntity(),
                event.getLevel(),
                event.getHand(),
                event.getPos(),
                event.getLevel().getBlockState(event.getPos()),
                event.getFace()
        ) != null;
        if (handled) {
            event.setCancellationResult(net.minecraft.world.InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (!event.getEntity().isShiftKeyDown()) return;
        BlockState state = event.getLevel().getBlockState(event.getPos());

        if (state.getBlock() instanceof GenericDisplayPlateBlock gdpb) {
            if (event.getLevel().isClientSide()) {
                if (event.getLevel().getBlockEntity(event.getPos()) instanceof GenericDisplayBlockEntity be
                        && !be.isEmpty() && FoodAccess.isFood(be.getDisplayedItem())) {
                    event.setCanceled(true);
                }
                return;
            }
            if (gdpb.tryEat(event.getEntity(), event.getLevel(), event.getPos())) {
                event.setCanceled(true);
            }
            return;
        }

        if (state.getBlock() instanceof GenericDisplayBowlBlock gdbb) {
            if (event.getLevel().isClientSide()) {
                if (event.getLevel().getBlockEntity(event.getPos()) instanceof GenericDisplayBlockEntity be
                        && !be.isEmpty() && FoodAccess.isFood(be.getDisplayedItem())) {
                    event.setCanceled(true);
                }
                return;
            }
            if (gdbb.tryEat(event.getEntity(), event.getLevel(), event.getPos())) {
                event.setCanceled(true);
            }
            return;
        }

        if (state.getBlock() instanceof FoodBlock fb) {
            if (event.getLevel().isClientSide()) {
                var food = new net.minecraft.world.item.ItemStack(fb.displayItem.get());
                if (FoodAccess.isFood(food)) event.setCanceled(true);
                return;
            }
            if (fb.tryEat(event.getEntity(), event.getLevel(), event.getPos(), state)) {
                event.setCanceled(true);
            }
        }
    }
}