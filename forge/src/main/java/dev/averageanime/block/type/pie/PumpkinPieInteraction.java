package dev.averageanime.block.type.pie;

import dev.averageanime.platform.Services;
import dev.averageanime.registry.BlockRegistry;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
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

public final class PumpkinPieInteraction {

    private PumpkinPieInteraction() {}

    public static boolean tryPlace(Player player, Level level, InteractionHand hand,
                                   BlockPos clickedPos, Direction clickedFace, ItemStack heldItem) {
        if (!heldItem.is(Items.PUMPKIN_PIE)) return false;
        if (!Services.PLATFORM.isPumpkinPiePlacementEnabled()) return false;

        BlockState clickedState = level.getBlockState(clickedPos);
        if (clickedState.getBlock() instanceof EmptyPlateBlock) return false;
        if (clickedState.is(BlockRegistry.PUMPKIN_PIE_BLOCK_ENTRY.get())) return false;

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
            level.setBlock(placePos, BlockRegistry.PUMPKIN_PIE_BLOCK_ENTRY.get().defaultBlockState(), 3);
            level.playSound(null, placePos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) heldItem.shrink(1);
        }

        return true;
    }
}