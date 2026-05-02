package dev.averageanime.neoforge.item.type;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class EffectDrink extends EffectFood {

    public EffectDrink(Properties properties) {
        super(properties);
    }

    public EffectDrink(Properties properties, Set<String> existingEffectIds) {
        super(properties, existingEffectIds);
    }

    public EffectDrink(Properties properties, Set<String> existingEffectIds, List<DeferredFx> deferredEffects) {
        super(properties, existingEffectIds, deferredEffects);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 32;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        var food = held.getFoodProperties(player);
        if (food != null) {
            if (player.canEat(food.canAlwaysEat())) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(held);
            }
            return InteractionResultHolder.fail(held);
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}