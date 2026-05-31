package dev.averageanime.neoforge.block.handler;

import dev.averageanime.CommonClass;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
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

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (!event.getEntity().isShiftKeyDown()) return;
        BlockState state = event.getLevel().getBlockState(event.getPos());

        if (state.getBlock() instanceof GenericDisplayPlateBlock gdpb) {
            if (event.getLevel().isClientSide()) {
                if (event.getLevel().getBlockEntity(event.getPos()) instanceof GenericDisplayPlateBlockEntity be
                        && !be.isEmpty() && be.getDisplayedItem().has(DataComponents.FOOD)) {
                    event.setCanceled(true);
                }
                return;
            }
            if (gdpb.tryEat(event.getEntity(), event.getLevel(), event.getPos())) {
                event.setCanceled(true);
            }
            return;
        }

        if (state.getBlock() instanceof FoodBlock fb) {
            if (event.getLevel().isClientSide()) {
                var food = new net.minecraft.world.item.ItemStack(fb.displayItem.get());
                if (food.has(DataComponents.FOOD)) event.setCanceled(true);
                return;
            }
            if (fb.tryEat(event.getEntity(), event.getLevel(), event.getPos(), state)) {
                event.setCanceled(true);
            }
        }
    }
}