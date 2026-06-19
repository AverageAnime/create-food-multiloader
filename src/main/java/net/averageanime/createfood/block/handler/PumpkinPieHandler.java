package net.averageanime.createfood.block.handler;

import net.averageanime.createfood.block.ModBlocks;
import net.averageanime.createfood.block.plate.EmptyPlateBlock;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class PumpkinPieHandler {

    private PumpkinPieHandler() {}

    public static boolean tryPlace(Player player, Level level, InteractionHand hand,
                                   BlockPos clickedPos, Direction clickedFace, ItemStack heldItem) {
        if (!heldItem.is(Items.PUMPKIN_PIE)) return false;
        if (!CreateFoodConfig.SERVER.enablePumpkinPiePlacement.get()) return false;

        BlockState clickedState = level.getBlockState(clickedPos);
        if (clickedState.getBlock() instanceof EmptyPlateBlock) return false;
        if (clickedState.is(ModBlocks.PUMPKIN_PIE_BLOCK.get())) return false;

        BlockPos placePos;
        BlockState placeState;
        if (clickedState.canBeReplaced()) {
            placePos = clickedPos;
            placeState = clickedState;
        } else {
            placePos = clickedPos.relative(clickedFace);
            placeState = level.getBlockState(placePos);
        }

        if (!placeState.canBeReplaced()) return false;

        if (!level.isClientSide()) {
            level.setBlock(placePos, ModBlocks.PUMPKIN_PIE_BLOCK.get().defaultBlockState(), 3);
            level.playSound(null, placePos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) heldItem.shrink(1);
        }

        return true;
    }
}
