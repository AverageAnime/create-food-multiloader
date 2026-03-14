package dev.averageanime.neoforge.item.remainder;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.config.ModConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = CommonClass.ID, bus = EventBusSubscriber.Bus.GAME)
public class CraftingRemainder {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getInventory() instanceof CraftingContainer craftingMatrix)) return;

        Map<Item, Integer> remainderCounts = new HashMap<>();

        for (String entry : ModConfig.CRAFTING_REMAINDERS.get()) {
            String[] parts = entry.split("\\|");
            if (parts.length != 2) continue;

            Item inputItem = resolveItem(parts[0].trim());
            Item remainderItem = resolveItem(parts[1].trim());

            if (inputItem == net.minecraft.world.item.Items.AIR) continue;
            if (remainderItem == net.minecraft.world.item.Items.AIR) continue;

            int count = 0;
            for (int i = 0; i < craftingMatrix.getContainerSize(); i++) {
                if (craftingMatrix.getItem(i).is(inputItem)) count++;
            }

            if (count > 0) remainderCounts.merge(remainderItem, count, Integer::sum);
        }

        for (Map.Entry<Item, Integer> entry : remainderCounts.entrySet()) {
            ItemStack remainder = new ItemStack(entry.getKey(), entry.getValue());
            if (!event.getEntity().getInventory().add(remainder)) {
                event.getEntity().drop(remainder, false);
            }
        }
    }

    @SubscribeEvent
    public static void onEggImpact(ProjectileImpactEvent event) {
        if (!ModConfig.ENABLE_EGG_IMPACT_REMAINDER.get()) return;
        if (!(event.getProjectile() instanceof ThrownEgg egg)) return;

        if (egg.level().isClientSide()) return;

        Item eggshellItem = resolveItem("createfood:eggshell");
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
            CommonClass.LOGGER.error("Could not resolve item '{}'", id, e);
            return net.minecraft.world.item.Items.AIR;
        }
    }
}