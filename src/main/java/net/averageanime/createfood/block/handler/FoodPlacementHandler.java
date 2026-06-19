package net.averageanime.createfood.block.handler;

import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.display.FoodBlock;
import net.averageanime.createfood.block.plate.GenericDisplayPlateBlock;
import net.averageanime.createfood.block.blockentity.GenericDisplayPlateBlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateFood.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FoodPlacementHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        var player = event.getEntity();
        var level = event.getLevel();
        var pos = event.getPos();
        var hand = event.getHand();
        var face = event.getFace();

        if (player == null || face == null) return;

        // Pumpkin pie block placement — must run before FoodBlock.Registry.tryPlace
        ItemStack heldItem = player.getItemInHand(hand);
        if (PumpkinPieHandler.tryPlace(player, level, hand, pos, face, heldItem)) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
            return;
        }

        BlockState clickedState = level.getBlockState(pos);
        InteractionResult result = FoodBlock.Registry.tryPlace(player, level, hand, pos, clickedState, face);
        if (result != null && result != InteractionResult.PASS) {
            event.setCanceled(true);
            event.setCancellationResult(result);
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (!event.getEntity().isShiftKeyDown()) return;
        var level = event.getLevel();
        var pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof GenericDisplayPlateBlock gdpb) {
            if (level.isClientSide()) {
                if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be
                        && !be.isEmpty() && be.getDisplayedItem().isEdible()) {
                    event.setCanceled(true);
                }
                return;
            }
            if (gdpb.tryEat(event.getEntity(), level, pos)) {
                event.setCanceled(true);
            }
            return;
        }

        if (state.getBlock() instanceof FoodBlock fb) {
            if (level.isClientSide()) {
                var food = new net.minecraft.world.item.ItemStack(fb.displayItem.get());
                if (food.isEdible()) event.setCanceled(true);
                return;
            }
            if (fb.tryEat(event.getEntity(), level, pos, state)) {
                event.setCanceled(true);
            }
        }
    }
}
