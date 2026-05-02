package dev.averageanime.fabric.block.type.pie;

import dev.averageanime.fabric.block.ModBlocks;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PumpkinPieBlock {

    public static void register() {
        UseBlockCallback.EVENT.register(PumpkinPieBlock::onUseBlock);
    }

    private static InteractionResult onUseBlock(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (!player.getItemInHand(hand).is(Items.PUMPKIN_PIE)) {
            return InteractionResult.PASS;
        }

        if (!ModConfig.ENABLE_PUMPKIN_PIE_PLACEMENT.get()) {
            return InteractionResult.PASS;
        }

        BlockPos clickedPos = hitResult.getBlockPos();
        BlockState clickedState = level.getBlockState(clickedPos);

        if (clickedState.getBlock() instanceof EmptyPlateBlock) {
            return InteractionResult.PASS;
        }

        if (clickedState.is(ModBlocks.PUMPKIN_PIE_BLOCK)) {
            return InteractionResult.PASS;
        }

        BlockPos placePos;
        BlockState placeState;

        if (clickedState.canBeReplaced()) {
            placePos = clickedPos;
            placeState = clickedState;
        } else {
            placePos = clickedPos.relative(hitResult.getDirection());
            placeState = level.getBlockState(placePos);
        }

        if (!placeState.canBeReplaced()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            level.setBlock(placePos, ModBlocks.PUMPKIN_PIE_BLOCK.defaultBlockState(), 3);
            level.playSound(null, placePos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
        }

        return level.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }
}
