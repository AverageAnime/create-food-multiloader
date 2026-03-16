package dev.averageanime.neoforge.block.type.pie;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.block.type.display.plate.EmptyPlateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CommonClass.ID)
public class PumpkinPieBlock {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext context = event.getUseOnContext();

        if (!context.getItemInHand().is(Items.PUMPKIN_PIE)) {
            return;
        }

        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);

        if (clickedState.getBlock() instanceof EmptyPlateBlock) {
            return; // Let the plate placement handler take over
        }

        if (clickedState.is(ModBlocks.PUMPKIN_PIE_BLOCK.get())) {
            return;
        }

        BlockPos placePos;
        BlockState placeState;

        if (clickedState.canBeReplaced()) {
            placePos = clickedPos;
            placeState = clickedState;
        } else {
            placePos = clickedPos.relative(context.getClickedFace());
            placeState = level.getBlockState(placePos);
        }

        if (!placeState.canBeReplaced()) {
            return;
        }

        if (!level.isClientSide()) {
            level.setBlock(placePos, ModBlocks.PUMPKIN_PIE_BLOCK.get().defaultBlockState(), 3);
            level.playSound(null, placePos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }
        }

        event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide()));
    }
}