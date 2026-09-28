package dev.averageanime.block.type.pie;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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

    private static final ResourceLocation PUMPKIN_PIE_BLOCK =
            ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "pumpkin_pie_block");

    private PumpkinPieInteraction() {}

    /** The block is addon content, so it is looked up rather than referenced: absent means nothing to place. */
    private static Block block() {
        return BuiltInRegistries.BLOCK.getOptional(PUMPKIN_PIE_BLOCK).orElse(null);
    }

    public static boolean tryPlace(Player player, Level level, InteractionHand hand,
                                   BlockPos clickedPos, Direction clickedFace, ItemStack heldItem) {
        if (!heldItem.is(Items.PUMPKIN_PIE)) return false;
        if (!ConfigValues.isPumpkinPiePlacementEnabled()) return false;

        Block pumpkinPie = block();
        if (pumpkinPie == null) return false;

        BlockState clickedState = level.getBlockState(clickedPos);
        if (clickedState.getBlock() instanceof EmptyPlateBlock) return false;
        if (clickedState.is(pumpkinPie)) return false;

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
            level.setBlock(placePos, pumpkinPie.defaultBlockState(), 3);
            level.playSound(null, placePos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) heldItem.shrink(1);
        }

        return true;
    }
}