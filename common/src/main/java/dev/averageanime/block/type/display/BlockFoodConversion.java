package dev.averageanime.block.type.display;

import dev.averageanime.block.type.bowl.EmptyBowlBlock;
import dev.averageanime.block.type.bowl.EmptyLargeBowlBlock;
import dev.averageanime.block.type.bowl.GenericDisplayBowlBlock;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.EmptySmallPlateBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.config.ConfigValues;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public final class BlockFoodConversion {

    private BlockFoodConversion() {}

    public static boolean tryTransform(Player player, Level level, InteractionHand hand, BlockPos pos) {
        if (!ConfigValues.isDisplayInteractionsEnabled()) return false;

        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.isEmpty()) return false;

        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        // Display blocks run their own serving-aware version.
        if (block instanceof FoodBlock) return false;
        if (!isModBlock(block)) return false;
        // These surfaces have their own useItemOn, and this event runs first.
        if (block instanceof EmptyBottleBlock
                || block instanceof EmptyBowlBlock
                || block instanceof EmptyLargeBowlBlock
                || block instanceof EmptyPlateBlock
                || block instanceof EmptySmallPlateBlock
                || block instanceof GenericDisplayBowlBlock
                || block instanceof GenericDisplayPlateBlock) {
            return false;
        }

        // ConsumableBlock and vanilla CakeBlock each declare their own "bites" property, so match by name.
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals("bites") && property instanceof IntegerProperty bites
                    && state.getValue(bites) != 0) {
                return false;
            }
        }

        ItemStack placedStack = new ItemStack(block.asItem());
        if (placedStack.isEmpty() || placedStack.getItem() == Items.AIR) return false;
        if (ConfigValues.isDisplayInteractionExcluded(placedStack)) return false;

        FoodConversion.Outcome outcome = FoodConversion.resolve(level, placedStack, heldStack);
        if (outcome == null || !outcome.convertsBlock()) return false;

        Block target = Block.byItem(outcome.result().getItem());
        if (target == null || target == net.minecraft.world.level.block.Blocks.AIR) return false;

        if (level.isClientSide) return true;

        ItemStack heldBefore = heldStack.copy();
        if (!player.isCreative()) heldStack.shrink(1);
        if (!player.isCreative()) {
            for (ItemStack container : FoodConversion.containersFor(outcome, heldBefore)) {
                FoodConversion.giveCopies(player, hand, container, 1, true);
            }
        }

        level.setBlock(pos, copyShared(state, target.defaultBlockState()), 3);
        level.playSound(null, pos, FoodConversion.soundFor(level, heldBefore, outcome, SoundEvents.ITEM_FRAME_ADD_ITEM),
                SoundSource.BLOCKS, 0.9F, 1.0F);
        return true;
    }

    private static boolean isModBlock(Block block) {
        var key = BuiltInRegistries.BLOCK.getKey(block);
        return key != null && key.getNamespace().equals(dev.averageanime.CreateFoodCommon.MOD_ID);
    }

    private static BlockState copyShared(BlockState from, BlockState to) {
        if (from.hasProperty(BlockStateProperties.HORIZONTAL_FACING)
                && to.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            to = to.setValue(BlockStateProperties.HORIZONTAL_FACING,
                    from.getValue(BlockStateProperties.HORIZONTAL_FACING));
        }
        return to;
    }
}
