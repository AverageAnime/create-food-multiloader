package net.averageanime.createfood.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.stats.Stats;

public class BowlFoodItem extends Item {

    public BowlFoodItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        super.finishUsingItem(pStack, pLevel, pEntityLiving);
        if (pEntityLiving instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, pStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (pStack.isEmpty()) {
            return new ItemStack(Items.BOWL);
        } else {
            if (pEntityLiving instanceof Player player && !player.getAbilities().instabuild) {
                ItemStack bowl = new ItemStack(Items.BOWL);
                if (!player.getInventory().add(bowl)) {
                    player.drop(bowl, false);
                }
            }
            return pStack;
        }
    }
}
