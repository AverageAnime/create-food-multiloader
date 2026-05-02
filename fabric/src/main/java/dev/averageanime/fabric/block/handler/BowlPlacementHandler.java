package dev.averageanime.fabric.block.handler;

import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.display.SmallPlateFoodBlock;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.fabric.block.ModDisplayBlocks;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;
import java.util.function.Supplier;

public class BowlPlacementHandler {

    public static void register() {
        UseBlockCallback.EVENT.register(BowlPlacementHandler::onUseBlock);
    }

    private static InteractionResult onUseBlock(Player player, Level level,
                                                InteractionHand hand,
                                                BlockHitResult hitResult) {
        if (!player.isShiftKeyDown()) return InteractionResult.PASS;

        ItemStack heldStack = player.getItemInHand(hand);
        Item heldItem = heldStack.getItem();

        boolean isBowl = heldItem == Items.BOWL;
        boolean isRegisteredPlate = FoodBlock.Registry.isEmptyPlateItem(heldItem);
        boolean isFoodItem = FoodBlock.Registry.isRegistered(heldItem);
        boolean isCompatFoodItem = !isFoodItem && (FoodBlock.Registry.getDisplayDelightPlateBlock(heldItem) != null
                || FoodBlock.Registry.getDisplayDelightSmallPlateBlock(heldItem) != null);

        if (!isBowl && !isRegisteredPlate && !isFoodItem && !isCompatFoodItem) return InteractionResult.PASS;

        BlockPos clickedPos = hitResult.getBlockPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Block clickedBlock = clickedState.getBlock();

        // Shift + food on existing FoodBlock → fill to max
        if ((isFoodItem || isCompatFoodItem) && clickedBlock instanceof FoodBlock foodBlock) {
            if (!heldStack.is(foodBlock.displayItem.get())) return InteractionResult.PASS;
            if (!level.isClientSide()) {
                int currentStack = clickedState.getValue(FoodBlock.STACK_SIZE);
                int maxStack = foodBlock.maxStackSize;
                int available = maxStack - currentStack;
                if (available <= 0) return InteractionResult.PASS;
                int toAdd = player.isCreative() ? available : Math.min(available, heldStack.getCount());
                if (toAdd <= 0) return InteractionResult.PASS;
                level.setBlock(clickedPos, clickedState.setValue(FoodBlock.STACK_SIZE, currentStack + toAdd), 3);
                if (!player.isCreative()) heldStack.shrink(toAdd);
                level.playSound(null, clickedPos, foodBlock.getAddSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }

        // Shift + food on EmptyPlateBlock or SmallPlateBlock → place food display block with max stack
        boolean isEmptyPlate = clickedBlock instanceof EmptyPlateBlock;
        boolean isSmallPlate = clickedBlock instanceof SmallPlateBlock;
        if ((isFoodItem || isCompatFoodItem) && (isEmptyPlate || isSmallPlate)) {
            Block targetFoodBlock = null;
            if (isFoodItem) {
                List<Supplier<Block>> stackBlocks = FoodBlock.Registry.getAllBlocks(heldItem);
                if (stackBlocks != null) {
                    for (Supplier<Block> supplier : stackBlocks) {
                        Block block = supplier.get();
                        if (isEmptyPlate && block instanceof PlateBlock) { targetFoodBlock = block; break; }
                        if (isSmallPlate && block instanceof SmallPlateFoodBlock) { targetFoodBlock = block; break; }
                    }
                }
            }
            if (targetFoodBlock == null && isCompatFoodItem) {
                targetFoodBlock = isEmptyPlate
                        ? FoodBlock.Registry.getDisplayDelightPlateBlock(heldItem)
                        : FoodBlock.Registry.getDisplayDelightSmallPlateBlock(heldItem);
            }
            if (targetFoodBlock == null) return InteractionResult.PASS;

            if (!level.isClientSide()) {
                BlockState newState = targetFoodBlock.defaultBlockState();
                if (clickedState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                    newState = newState.setValue(BlockStateProperties.HORIZONTAL_FACING,
                            clickedState.getValue(BlockStateProperties.HORIZONTAL_FACING));
                }
                int stackSize = 1;
                if (targetFoodBlock instanceof FoodBlock foodBlock && newState.hasProperty(FoodBlock.STACK_SIZE)) {
                    int maxStack = foodBlock.maxStackSize;
                    stackSize = player.isCreative() ? maxStack : Math.min(maxStack, heldStack.getCount());
                    newState = newState.setValue(FoodBlock.STACK_SIZE, stackSize);
                } else {
                    for (Property<?> property : newState.getProperties()) {
                        if (property.getName().equals("stacks") && property instanceof IntegerProperty intProp) {
                            int maxStack = intProp.getPossibleValues().stream().max(Integer::compareTo).orElse(1);
                            stackSize = player.isCreative() ? maxStack : Math.min(maxStack, heldStack.getCount());
                            newState = newState.setValue(intProp, stackSize);
                            break;
                        }
                    }
                }
                level.setBlock(clickedPos, newState, 3);
                level.playSound(null, clickedPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) heldStack.shrink(stackSize);
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }

        // Shift + bowl/plate item → place empty plate on ground
        if (!isBowl && !isRegisteredPlate) return InteractionResult.PASS;

        if (clickedState.is(ModDisplayBlocks.PLATE_BLOCK) || clickedState.is(ModDisplayBlocks.SMALL_PLATE_BLOCK)) {
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

        if (!placeState.canBeReplaced()) return InteractionResult.PASS;

        if (!level.isClientSide()) {
            Block blockToPlace;
            if (isBowl) {
                blockToPlace = ModDisplayBlocks.PLATE_BLOCK;
            } else {
                List<Supplier<Block>> registered = FoodBlock.Registry.getAllBlocks(heldItem);
                blockToPlace = (registered != null && !registered.isEmpty())
                        ? registered.getFirst().get()
                        : ModDisplayBlocks.PLATE_BLOCK;
            }

            BlockState newState = blockToPlace.defaultBlockState();
            if (newState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                newState = newState.setValue(BlockStateProperties.HORIZONTAL_FACING, player.getDirection());
            }
            level.setBlock(placePos, newState, 3);
            level.playSound(null, placePos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!player.isCreative()) {
                heldStack.shrink(1);
            }
        }

        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }
}
