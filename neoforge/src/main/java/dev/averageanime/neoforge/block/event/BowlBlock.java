package dev.averageanime.neoforge.block.event;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = CommonClass.ID, bus = EventBusSubscriber.Bus.GAME)
public class BowlBlock {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext context = event.getUseOnContext();

        if (!context.getItemInHand().is(Items.BOWL)) {
            return;
        }

        if (context.getPlayer() == null || !context.getPlayer().isShiftKeyDown()) {
            return;
        }

        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);

        if (clickedState.is(ModBlocks.PLATE_BLOCK.get())) {
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
            level.setBlock(placePos, ModBlocks.PLATE_BLOCK.get().defaultBlockState()
                    .setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING,
                            context.getHorizontalDirection()), 3);
            level.playSound(null, placePos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }
        }

        event.cancelWithResult(net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide()));
    }
}