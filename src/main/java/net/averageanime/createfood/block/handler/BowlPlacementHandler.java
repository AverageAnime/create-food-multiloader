package net.averageanime.createfood.block.handler;

import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.ModDisplayBlocks;
import net.averageanime.createfood.block.display.FoodBlock;
import net.averageanime.createfood.block.plate.EmptyPlateBlock;
import net.averageanime.createfood.block.plate.PlateBlock;
import net.averageanime.createfood.block.plate.SmallPlateBlock;
import net.averageanime.createfood.block.plate.SmallPlateFoodBlock;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CreateFood.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BowlPlacementHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        var player = event.getEntity();
        var level = event.getLevel();
        var pos = event.getPos();
        var face = event.getFace();
        var heldStack = player.getItemInHand(event.getHand());

        if (face == null) return;

        if (tryBowlPlacement(player, level, pos, face, heldStack)) {
            event.setCanceled(true);
            event.setCancellationResult(level.isClientSide
                    ? net.minecraft.world.InteractionResult.SUCCESS
                    : net.minecraft.world.InteractionResult.CONSUME);
        }
    }

    /**
     * Handles shift+right-click bowl/plate placement.
     * @return true if the interaction was consumed
     */
    public static boolean tryBowlPlacement(Player player, Level level,
                                           BlockPos clickedPos, Direction clickedFace,
                                           ItemStack heldStack) {
        if (!player.isShiftKeyDown()) return false;

        Item heldItem = heldStack.getItem();
        boolean isBowl            = heldItem == Items.BOWL;
        boolean isRegisteredPlate = FoodBlock.Registry.isEmptyPlateItem(heldItem);
        boolean isFoodItem        = FoodBlock.Registry.isRegistered(heldItem);
        boolean isCompatFoodItem  = !isFoodItem
                && (FoodBlock.Registry.getDisplayDelightPlateBlock(heldItem) != null
                    || FoodBlock.Registry.getDisplayDelightSmallPlateBlock(heldItem) != null);

        if (!isBowl && !isRegisteredPlate && !isFoodItem && !isCompatFoodItem) return false;

        BlockState clickedState = level.getBlockState(clickedPos);
        Block clickedBlock = clickedState.getBlock();

        // Case 1: food held + existing FoodBlock clicked → top-off the stack
        if ((isFoodItem || isCompatFoodItem) && clickedBlock instanceof FoodBlock foodBlock) {
            if (!heldStack.is(foodBlock.displayItem.get())) return false;
            if (!level.isClientSide()) {
                int current  = clickedState.getValue(FoodBlock.STACK_SIZE);
                int maxStack = foodBlock.maxStackSize;
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

        boolean isEmptyPlate = clickedBlock instanceof EmptyPlateBlock;
        boolean isSmallPlate = clickedBlock instanceof SmallPlateBlock;
        if (!isEmptyPlate && !isSmallPlate && FoodBlock.Registry.isCompatBlock(clickedBlock)) {
            Block targetBlock = FoodBlock.Registry.getTargetBlock(clickedBlock);
            if (targetBlock instanceof EmptyPlateBlock)      isEmptyPlate = true;
            else if (targetBlock instanceof SmallPlateBlock) isSmallPlate = true;
        }

        // Case 2: food held + empty plate clicked → place food display block
        if ((isFoodItem || isCompatFoodItem) && (isEmptyPlate || isSmallPlate)) {
            Block targetFoodBlock = null;
            if (isFoodItem) {
                List<Supplier<Block>> stackBlocks = FoodBlock.Registry.getAllBlocks(heldItem);
                if (stackBlocks != null) {
                    for (Supplier<Block> supplier : stackBlocks) {
                        Block b = supplier.get();
                        if (isEmptyPlate && b instanceof PlateBlock)         { targetFoodBlock = b; break; }
                        if (isSmallPlate && b instanceof SmallPlateFoodBlock) { targetFoodBlock = b; break; }
                    }
                }
            }
            if (targetFoodBlock == null && isCompatFoodItem) {
                targetFoodBlock = isEmptyPlate
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
                if (targetFoodBlock instanceof FoodBlock foodBlock
                        && newState.hasProperty(FoodBlock.STACK_SIZE)) {
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

        // Case 3: bowl or registered plate item → place empty plate on ground
        if (!isBowl && !isRegisteredPlate) return false;

        Block plateBlock      = ModDisplayBlocks.PLATE_BLOCK.get();
        Block smallPlateBlock = ModDisplayBlocks.SMALL_PLATE_BLOCK.get();
        if (clickedState.is(plateBlock) || clickedState.is(smallPlateBlock)) return false;

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
                        ? registered.get(0).get() : plateBlock;
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
