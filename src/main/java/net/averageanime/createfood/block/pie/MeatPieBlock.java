package net.averageanime.createfood.block.pie;

import net.averageanime.createfood.block.ModPieBlock;
import net.averageanime.createfood.effect.ModEffects;
import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class MeatPieBlock extends ModPieBlock {

    public MeatPieBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getPieSliceItem() {
        return new ItemStack(ModItems.MEAT_PIE_SLICE.get());
    }

    @Override
    protected InteractionResult consumeBite(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.canEat(false)) {
            return InteractionResult.PASS;
        }
        if (ModEffects.NOURISHMENT.get() != null) {
            pPlayer.addEffect(new MobEffectInstance(ModEffects.NOURISHMENT.get(), 1200, 0));
        }
        pPlayer.getFoodData().eat(5, 0.8F);
        int bites = pState.getValue(BITES);
        pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
        if (bites < MAX_PIE_BITES - 1) {
            pLevel.setBlock(pPos, pState.setValue(BITES, bites + 1), 3);
        } else {
            pLevel.removeBlock(pPos, false);
            pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
        }
        return InteractionResult.SUCCESS;
    }
}
