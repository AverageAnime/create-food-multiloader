package dev.averageanime.fabric.block.handler;

import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;

public class FoodPlacementHandler {
    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            InteractionResult result = FoodBlock.Registry.tryPlace(
                    player, level, hand,
                    hitResult.getBlockPos(),
                    level.getBlockState(hitResult.getBlockPos()),
                    hitResult.getDirection()
            );
            return result != null ? result : InteractionResult.PASS;
        });

        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            if (!player.isShiftKeyDown()) return InteractionResult.PASS;
            BlockState state = level.getBlockState(pos);

            if (state.getBlock() instanceof GenericDisplayPlateBlock gdpb) {
                if (level.isClientSide) {
                    if (level.getBlockEntity(pos) instanceof GenericDisplayPlateBlockEntity be
                            && !be.isEmpty() && be.getDisplayedItem().has(DataComponents.FOOD)) {
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                }
                return gdpb.tryEat(player, level, pos) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }

            if (state.getBlock() instanceof FoodBlock fb) {
                if (level.isClientSide) {
                    var food = new net.minecraft.world.item.ItemStack(fb.displayItem.get());
                    return food.has(DataComponents.FOOD) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                }
                return fb.tryEat(player, level, pos, state) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }

            return InteractionResult.PASS;
        });
    }
}
