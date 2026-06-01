package net.averageanime.createfood.block.display;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SaladBowlFoodBlock extends DisplayFoodBlock {
    protected static final VoxelShape SHAPE = Block.box(4, 0.0, 4, 12, 2, 12);

    public SaladBowlFoodBlock(Supplier<Item> displayItem) {
        super(Properties.copy(Blocks.OAK_PLANKS), displayItem, 1);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean handlesOwnContainer() { return true; }

    @Override
    protected void dropContainerOnEat(Player player, Level level, BlockPos pos) {
        Block.popResource(level, pos, new ItemStack(Items.BOWL));
    }
}
