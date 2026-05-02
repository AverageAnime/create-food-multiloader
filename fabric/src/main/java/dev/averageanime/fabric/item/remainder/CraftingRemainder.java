package dev.averageanime.fabric.item.remainder;

import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.config.ModConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public final class CraftingRemainder {

    private CraftingRemainder() {}

    public static void onItemCrafted(Player player, CraftingContainer craftingMatrix) {
        if (player.level().isClientSide()) return;

        Map<Item, Integer> remainderCounts = new HashMap<>();

        for (String entry : ModConfig.CRAFTING_REMAINDERS.get()) {
            String[] parts = entry.split("\\|");
            if (parts.length != 2) continue;

            Item inputItem = resolveItem(parts[0].trim());
            Item remainderItem = resolveItem(parts[1].trim());

            if (inputItem == Items.AIR) continue;
            if (remainderItem == Items.AIR) continue;

            int count = 0;
            for (int i = 0; i < craftingMatrix.getContainerSize(); i++) {
                if (craftingMatrix.getItem(i).is(inputItem)) count++;
            }

            if (count > 0) remainderCounts.merge(remainderItem, count, Integer::sum);
        }

        for (Map.Entry<Item, Integer> entry : remainderCounts.entrySet()) {
            ItemStack remainder = new ItemStack(entry.getKey(), entry.getValue());
            if (!player.getInventory().add(remainder)) {
                player.drop(remainder, false);
            }
        }
    }

    public static void onEggImpact(ThrownEgg egg) {
        if (egg.level().isClientSide()) return;
        if (!ModConfig.ENABLE_EGG_IMPACT_REMAINDER.get()) return;

        Item eggshellItem = resolveItem("createfood:eggshell");
        if (eggshellItem == Items.AIR) return;
        if (!ModConfig.isItemEnabled(BuiltInRegistries.ITEM.getKey(eggshellItem).getPath())) return;

        ItemStack eggshell = new ItemStack(eggshellItem, 1);
        egg.level().addFreshEntity(
                new net.minecraft.world.entity.item.ItemEntity(
                        egg.level(), egg.getX(), egg.getY(), egg.getZ(), eggshell
                )
        );
    }

    private static Item resolveItem(String id) {
        try {
            return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
        } catch (Exception e) {
            CreateFood.LOGGER.error("Could not resolve item '{}'", id, e);
            return Items.AIR;
        }
    }
}
