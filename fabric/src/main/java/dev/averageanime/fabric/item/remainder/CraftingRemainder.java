package dev.averageanime.fabric.item.remainder;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;

public final class CraftingRemainder {

    private CraftingRemainder() {}

    public static void onItemCrafted(Player player, CraftingContainer craftingMatrix) {
        dev.averageanime.item.remainder.CraftingRemainder.handleItemCrafted(player, craftingMatrix);
    }
}
