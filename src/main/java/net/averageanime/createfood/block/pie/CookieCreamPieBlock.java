package net.averageanime.createfood.block.pie;

import net.averageanime.createfood.block.ModPieBlock;
import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class CookieCreamPieBlock extends ModPieBlock {

    public CookieCreamPieBlock(Properties pProperties) {
        super(pProperties);
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack((ItemLike) ModItems.COOKIE_CREAM_PIE_SLICE.get());
    }

    protected static InteractionResult consumeBite(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pPlayer.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            pPlayer.addEffect(new MobEffectInstance(ModEffects.COMFORT.get(), 3600, 0));
            pPlayer.getFoodData().eat(5, 0.7F);
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

}
