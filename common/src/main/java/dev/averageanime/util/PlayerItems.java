package dev.averageanime.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class PlayerItems {

    private PlayerItems() {}

    public static void give(Player player, ItemStack stack) {
        if (stack.isEmpty()) return;
        if (!player.getInventory().add(stack)) player.drop(stack, false);
    }

    public static void giveToHandOrInventory(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.isEmpty()) return;
        if (player.getItemInHand(hand).isEmpty()) {
            player.setItemInHand(hand, stack);
            return;
        }
        give(player, stack);
    }
}
