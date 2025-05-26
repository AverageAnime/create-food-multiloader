package net.averageanime.createfood.block.waffle;

import net.averageanime.createfood.block.ModPieBlock;
import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DarkChocolateChipWaffleBlock extends ModPieBlock {

    public DarkChocolateChipWaffleBlock(Properties pProperties) {
        super(pProperties);
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack((ItemLike) ModItems.DARK_CHOCOLATE_CHIP_MINI_WAFFLE.get());
    }

    protected static InteractionResult consumeBite(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            pPlayer.getFoodData().eat(5, 0.6F);
            int $$4 = (Integer)pState.getValue(BITES);
            pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
            if ($$4 < 3) {
                pLevel.setBlock(pPos, (BlockState)pState.setValue(BITES, $$4 + 1), 3);
            } else {
                pLevel.removeBlock(pPos, false);
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    static {
        SHAPE_BY_BITE = new VoxelShape[]{
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 8.0D, 8.0D, 2.0D, 14.0D),
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 8.0D)
                ),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 8.0D),
                Block.box(8.0D, 0.0D, 2.0D, 14.0D, 2.0D, 8.0D)
        };
    }

}
