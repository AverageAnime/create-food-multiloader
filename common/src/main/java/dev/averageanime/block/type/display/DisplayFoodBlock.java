package dev.averageanime.block.type.display;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public abstract class DisplayFoodBlock extends FoodBlock {

    protected DisplayFoodBlock(Properties properties, Supplier<Item> displayItem, int maxStackSize) {
        super(properties, displayItem, maxStackSize);
    }

    @Override
    protected void handleLastItemRemoved(BlockState state, Level level, net.minecraft.core.BlockPos pos) {
        level.removeBlock(pos, false);
    }

    @Override
    public SoundEvent getAddSound() {
        return SoundEvents.WOOD_PLACE;
    }

    @Override
    protected SoundEvent getRemoveSound() {
        return SoundEvents.WOOD_BREAK;
    }
}
