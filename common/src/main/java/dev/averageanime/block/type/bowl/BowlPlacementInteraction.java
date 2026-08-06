package dev.averageanime.block.type.bowl;

import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.plate.EmptySmallPlateBlock;
import dev.averageanime.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

import java.util.List;
import java.util.function.Supplier;

public class BowlPlacementInteraction {

    public static boolean tryBowlPlacement(Player player, Level level,
                                           BlockPos clickedPos, Direction clickedFace,
                                           ItemStack heldStack) {
        Item heldItem = heldStack.getItem();
        boolean isBowl             = heldItem == Items.BOWL;
        boolean isRegisteredPlate  = FoodBlock.Registry.isEmptyPlateItem(heldItem);
        boolean isFoodItem         = FoodBlock.Registry.isRegistered(heldItem);
        boolean isCompatFoodItem   = !isFoodItem
                && (FoodBlock.Registry.getDisplayDelightPlateBlock(heldItem) != null
                    || FoodBlock.Registry.getDisplayDelightSmallPlateBlock(heldItem) != null);

        if (!isBowl && !isRegisteredPlate && !isFoodItem && !isCompatFoodItem) return false;

        BlockState clickedState = level.getBlockState(clickedPos);
        Block clickedBlock = clickedState.getBlock();

        boolean isEmptyPlate = clickedBlock instanceof EmptyPlateBlock;
        boolean isSmallPlate = clickedBlock instanceof EmptySmallPlateBlock;
        boolean isEmptyBowl  = clickedBlock instanceof EmptyBowlBlock;
        boolean isSmallBowl  = clickedBlock instanceof EmptySmallBowlBlock;

        boolean isCompatEmpty = !isEmptyPlate && !isSmallPlate && !isEmptyBowl && !isSmallBowl
                && FoodBlock.Registry.isCompatBlock(clickedBlock);
        boolean isFilledSmallBowl = isSmallBowl
                && EmptySmallBowlBlock.holdsFluid(level, clickedPos, clickedState);
        if ((isBowl || isRegisteredPlate) && !isFilledSmallBowl
                && (isEmptyPlate || isSmallPlate || isEmptyBowl || isSmallBowl || isCompatEmpty)) {
            IntegerProperty stackProp = null;
            int maxStack = -1;
            if (isEmptyPlate)      { stackProp = FoodBlock.STACK_SIZE; maxStack = EmptyPlateBlock.MAX_STACK; }
            else if (isSmallPlate) { stackProp = FoodBlock.STACK_SIZE; maxStack = EmptySmallPlateBlock.MAX_STACK; }
            else if (isEmptyBowl)  { stackProp = FoodBlock.STACK_SIZE; maxStack = EmptyBowlBlock.MAX_STACK; }
            else if (isSmallBowl)  { stackProp = FoodBlock.STACK_SIZE; maxStack = EmptySmallBowlBlock.MAX_STACK; }
            else {
                for (Property<?> property : clickedState.getProperties()) {
                    if (property.getName().equals("stacks") && property instanceof IntegerProperty ip) {
                        stackProp = ip;
                        maxStack = ip.getPossibleValues().stream().max(Integer::compareTo).orElse(1);
                        break;
                    }
                }
            }

            if (stackProp != null) {
                if (!level.isClientSide()) {
                    int current = clickedState.getValue(stackProp);
                    int available = maxStack - current;
                    if (available > 0) {
                        int toAdd = player.isShiftKeyDown()
                                ? (player.isCreative() ? available : Math.min(available, heldStack.getCount()))
                                : Math.min(1, available);
                        level.setBlock(clickedPos, clickedState.setValue(stackProp, current + toAdd), 3);
                        if (!player.isCreative()) heldStack.shrink(toAdd);
                        level.playSound(null, clickedPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F);
                    }
                }
                return true;
            }
        }

        if (!player.isShiftKeyDown()) return false;

        if ((isFoodItem || isCompatFoodItem) && clickedBlock instanceof FoodBlock foodBlock) {
            if (!heldStack.is(foodBlock.displayItem.get())) return false;
            if (!level.isClientSide()) {
                int current   = clickedState.getValue(FoodBlock.STACK_SIZE);
                int maxStack  = foodBlock.maxStackSize;
                int available = maxStack - current;
                if (available <= 0) return false;
                int toAdd = player.isCreative() ? available : Math.min(available, heldStack.getCount());
                if (toAdd <= 0) return false;
                level.setBlock(clickedPos, clickedState.setValue(FoodBlock.STACK_SIZE, current + toAdd), 3);
                if (!player.isCreative()) heldStack.shrink(toAdd);
                level.playSound(null, clickedPos, foodBlock.getAddSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        }

        boolean isEmptyPlateOrCompat = isEmptyPlate;
        boolean isSmallPlateOrCompat = isSmallPlate;
        if (!isEmptyPlateOrCompat && !isSmallPlateOrCompat && FoodBlock.Registry.isCompatBlock(clickedBlock)) {
            Block targetBlock = FoodBlock.Registry.getTargetBlock(clickedBlock);
            if (targetBlock instanceof EmptyPlateBlock)      isEmptyPlateOrCompat = true;
            else if (targetBlock instanceof EmptySmallPlateBlock) isSmallPlateOrCompat = true;
        }

        if ((isFoodItem || isCompatFoodItem) && (isEmptyPlateOrCompat || isSmallPlateOrCompat)) {
            Block targetFoodBlock = null;
            if (isFoodItem) {
                List<Supplier<Block>> stackBlocks = FoodBlock.Registry.getAllBlocks(heldItem);
                if (stackBlocks != null) {
                    for (Supplier<Block> supplier : stackBlocks) {
                        Block b = supplier.get();
                        if (isEmptyPlateOrCompat && b instanceof PlateBlock)         { targetFoodBlock = b; break; }
                        if (isSmallPlateOrCompat && b instanceof SmallPlateBlock) { targetFoodBlock = b; break; }
                    }
                }
            }
            if (targetFoodBlock == null && isCompatFoodItem) {
                targetFoodBlock = isEmptyPlateOrCompat
                        ? FoodBlock.Registry.getDisplayDelightPlateBlock(heldItem)
                        : FoodBlock.Registry.getDisplayDelightSmallPlateBlock(heldItem);
            }
            if (targetFoodBlock == null) return false;

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
            return true;
        }

        if (!isBowl && !isRegisteredPlate) return false;

        Block plateBlock      = Services.PLATFORM.getPlateBlock();
        Block smallPlateBlock = Services.PLATFORM.getSmallPlateBlock();
        Block bowlBlock       = Services.PLATFORM.getBowlBlock();
        if (clickedState.is(plateBlock) || clickedState.is(smallPlateBlock) || clickedState.is(bowlBlock)) return false;

        BlockPos placePos;
        BlockState placeState;
        if (clickedState.canBeReplaced()) {
            placePos  = clickedPos;
            placeState = clickedState;
        } else {
            placePos  = clickedPos.relative(clickedFace);
            placeState = level.getBlockState(placePos);
        }
        if (!placeState.canBeReplaced()) return false;

        if (!level.isClientSide()) {
            Block blockToPlace;
            if (isBowl) {
                blockToPlace = plateBlock;
            } else {
                List<Supplier<Block>> registered = FoodBlock.Registry.getAllBlocks(heldItem);
                blockToPlace = (registered != null && !registered.isEmpty())
                        ? registered.getFirst().get()
                        : plateBlock;
            }
            BlockState newState = blockToPlace.defaultBlockState();
            if (newState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                newState = newState.setValue(BlockStateProperties.HORIZONTAL_FACING, player.getDirection());
            }
            level.setBlock(placePos, newState, 3);
            level.playSound(null, placePos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) heldStack.shrink(1);
        }
        return true;
    }
}