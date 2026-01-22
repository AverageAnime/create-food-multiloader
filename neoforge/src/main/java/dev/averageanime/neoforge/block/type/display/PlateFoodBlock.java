package dev.averageanime.neoforge.block.type.display;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class PlateFoodBlock extends FoodBlock {
    public PlateFoodBlock(Supplier<Item> displayItem) {
        super(Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, 1);
    }

    @Override
    protected void handleLastItemRemoved(BlockState state, Level level, BlockPos pos) {
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