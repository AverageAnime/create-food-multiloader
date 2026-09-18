package dev.averageanime.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class FoodAccess {
    private FoodAccess() {
    }

    @Nullable
    public static FoodProperties get(ItemStack stack, @Nullable LivingEntity entity) {
        return stack.getItem().getFoodProperties(stack, entity);
    }

    public static boolean isFood(ItemStack stack) {
        return get(stack, null) != null;
    }
}
