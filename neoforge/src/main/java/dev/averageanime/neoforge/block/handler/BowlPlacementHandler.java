package dev.averageanime.neoforge.block.handler;

import dev.averageanime.CommonClass;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.block.type.display.FoodBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public class BowlPlacementHandler {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        UseOnContext context = event.getUseOnContext();
        Item heldItem = context.getItemInHand().getItem();

        boolean isBowl = heldItem == Items.BOWL;
        boolean isRegisteredPlate = FoodBlock.Registry.isEmptyPlateItem(heldItem);
        boolean isFoodItem = FoodBlock.Registry.isRegistered(heldItem);

        boolean isCompatFoodItem = false;
        if (!isFoodItem) {
            isCompatFoodItem = FoodBlock.Registry.getDisplayDelightPlateBlock(heldItem) != null ||
                    FoodBlock.Registry.getDisplayDelightSmallPlateBlock(heldItem) != null;
        }

        if (!isBowl && !isRegisteredPlate && !isFoodItem && !isCompatFoodItem) {
            return;
        }

        if (context.getPlayer() == null || !context.getPlayer().isShiftKeyDown()) {
            return;
        }

        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Block clickedBlock = clickedState.getBlock();

        if (isBowl || isRegisteredPlate) {
            if (clickedState.is(ModDisplayBlocks.PLATE_BLOCK.get()) ||
                    clickedState.is(ModDisplayBlocks.SMALL_PLATE_BLOCK.get())) {
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
                    blockToPlace = ModDisplayBlocks.PLATE_BLOCK.get();
                } else {
                    List<Supplier<Block>> registeredBlocks = FoodBlock.Registry.getAllBlocks(heldItem);
                    if (registeredBlocks != null && !registeredBlocks.isEmpty()) {
                        blockToPlace = registeredBlocks.getFirst().get();
                    } else {
                        blockToPlace = ModDisplayBlocks.PLATE_BLOCK.get();
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
            return;
        }

        boolean isEmptyPlate = clickedBlock instanceof EmptyPlateBlock;
        boolean isSmallPlate = clickedBlock instanceof SmallPlateBlock;

        if (!isEmptyPlate && !isSmallPlate && FoodBlock.Registry.isCompatBlock(clickedBlock)) {
            Block targetBlock = FoodBlock.Registry.getTargetBlock(clickedBlock);
            if (targetBlock instanceof EmptyPlateBlock) {
                isEmptyPlate = true;
            } else if (targetBlock instanceof SmallPlateBlock) {
                isSmallPlate = true;
            }
        }

        if (isEmptyPlate || isSmallPlate) {
            Block targetFoodBlock = null;

            if (isFoodItem) {
                List<Supplier<Block>> stackBlockSuppliers = FoodBlock.Registry.getAllBlocks(heldItem);
                if (stackBlockSuppliers != null && !stackBlockSuppliers.isEmpty()) {
                    if (isEmptyPlate) {
                        for (Supplier<Block> supplier : stackBlockSuppliers) {
                            Block block = supplier.get();
                            if (block instanceof PlateBlock) {
                                targetFoodBlock = block;
                                break;
                            }
                        }
                    } else {
                        for (Supplier<Block> supplier : stackBlockSuppliers) {
                            Block block = supplier.get();
                            if (block instanceof dev.averageanime.block.type.display.SmallPlateFoodBlock) {
                                targetFoodBlock = block;
                                break;
                            }
                        }
                    }
                }
            }

            if (targetFoodBlock == null && isCompatFoodItem) {
                if (isEmptyPlate) {
                    targetFoodBlock = FoodBlock.Registry.getDisplayDelightPlateBlock(heldItem);
                } else {
                    targetFoodBlock = FoodBlock.Registry.getDisplayDelightSmallPlateBlock(heldItem);
                }
            }

            if (targetFoodBlock == null) {
                return;
            }

            if (!level.isClientSide()) {
                BlockState newState = targetFoodBlock.defaultBlockState();

                if (clickedState.hasProperty(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING)) {
                    newState = newState.setValue(
                            net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING,
                            clickedState.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING)
                    );
                }

                int stackSize = 1;

                if (targetFoodBlock instanceof FoodBlock foodBlock &&
                        newState.hasProperty(FoodBlock.STACK_SIZE)) {
                    int maxStack = foodBlock.maxStackSize;
                    if (context.getPlayer().isCreative()) {
                        stackSize = maxStack;
                    } else {
                        stackSize = Math.min(maxStack, context.getItemInHand().getCount());
                    }
                    newState = newState.setValue(FoodBlock.STACK_SIZE, stackSize);
                }
                else {
                    for (Property<?> property : newState.getProperties()) {
                        if (property.getName().equals("stacks") && property instanceof IntegerProperty intProp) {
                            int maxStack = intProp.getPossibleValues().stream().max(Integer::compareTo).orElse(1);
                            if (context.getPlayer().isCreative()) {
                                stackSize = maxStack;
                            } else {
                                stackSize = Math.min(maxStack, context.getItemInHand().getCount());
                            }
                            newState = newState.setValue(intProp, stackSize);
                            break;
                        }
                    }
                }

                level.setBlock(clickedPos, newState, 3);
                level.playSound(null, clickedPos, SoundEvents.ITEM_FRAME_ADD_ITEM,
                        SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!context.getPlayer().isCreative()) {
                    context.getItemInHand().shrink(stackSize);
                }
            }

            event.cancelWithResult(net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide()));
            return;
        }

        if (clickedBlock instanceof FoodBlock foodBlock) {
            if (!context.getItemInHand().is(foodBlock.displayItem.get())) {
                return;
            }

            if (!level.isClientSide()) {
                int currentStack = clickedState.getValue(FoodBlock.STACK_SIZE);
                int maxStack = foodBlock.maxStackSize;
                int availableSlots = maxStack - currentStack;

                if (availableSlots <= 0) {
                    return;
                }

                int itemsToAdd;
                if (context.getPlayer().isCreative()) {
                    itemsToAdd = availableSlots;
                } else {
                    itemsToAdd = Math.min(availableSlots, context.getItemInHand().getCount());
                }

                if (itemsToAdd <= 0) {
                    return;
                }

                level.setBlock(clickedPos, clickedState.setValue(FoodBlock.STACK_SIZE, currentStack + itemsToAdd), 3);

                if (!context.getPlayer().isCreative()) {
                    context.getItemInHand().shrink(itemsToAdd);
                }

                level.playSound(null, clickedPos, foodBlock.getAddSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            event.cancelWithResult(net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide()));
        }
    }
}