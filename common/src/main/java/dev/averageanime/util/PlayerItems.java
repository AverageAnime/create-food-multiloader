package dev.averageanime.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Handing items back to a player after an interaction consumed something.
 *
 * <p>Both methods take ownership of the stack passed in; callers copy first if they still need it.
 */
public final class PlayerItems {

    private PlayerItems() {}

    /** Inventory, else dropped at the player's feet. */
    public static void give(Player player, ItemStack stack) {
        if (stack.isEmpty()) return;
        if (!player.getInventory().add(stack)) player.drop(stack, false);
    }

    /**
     * The hand if it is now empty, else inventory, else dropped. Used for handing back the container
     * of whatever was just consumed out of that hand.
     */
    public static void giveToHandOrInventory(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.isEmpty()) return;
        if (player.getItemInHand(hand).isEmpty()) {
            player.setItemInHand(hand, stack);
            return;
        }
        give(player, stack);
    }
}
