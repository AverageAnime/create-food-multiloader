package dev.averageanime.neoforge.block.type.display.plate;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.block.type.display.FoodBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = CommonClass.ID, bus = EventBusSubscriber.Bus.GAME)
public class BowlBlock {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext context = event.getUseOnContext();
        Item heldItem = context.getItemInHand().getItem();

        boolean isBowl = heldItem == Items.BOWL;
        boolean isRegisteredPlate = FoodBlock.Registry.isEmptyPlateItem(heldItem);  // CHANGED

        if (!isBowl && !isRegisteredPlate) {
            return;
        }

        if (context.getPlayer() == null || !context.getPlayer().isShiftKeyDown()) {
            return;
        }

        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);

        if (clickedState.is(ModBlocks.PLATE_BLOCK.get()) ||
                clickedState.is(ModBlocks.SMALL_PLATE_BLOCK.get())) {
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
            Block blockToPlace;

            if (isBowl) {
                // Minecraft bowl defaults to normal plate
                blockToPlace = ModBlocks.PLATE_BLOCK.get();
            } else {
                // For other mod's plates, check what they're registered as
                List<Supplier<Block>> registeredBlocks = FoodBlock.Registry.getAllBlocks(heldItem);
                if (registeredBlocks != null && !registeredBlocks.isEmpty()) {
                    blockToPlace = registeredBlocks.get(0).get();
                } else {
                    // Fallback to normal plate
                    blockToPlace = ModBlocks.PLATE_BLOCK.get();
                }
            }

            level.setBlock(placePos, blockToPlace.defaultBlockState()
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