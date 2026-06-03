package net.averageanime.createfood.item.interaction;

import net.averageanime.createfood.config.CreateFoodConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = net.averageanime.createfood.CreateFood.ID)
public final class CraftingRemainder {

    private CraftingRemainder() {}

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getInventory() instanceof CraftingContainer matrix)) return;
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        Map<Item, Integer> remainderCounts = new HashMap<>();

        for (String entry : CreateFoodConfig.SERVER.craftingRemainders.get()) {
            String[] parts = entry.split("\\|");
            if (parts.length != 2) continue;

            Item inputItem = resolveItem(parts[0].trim());
            Item remainderItem = resolveItem(parts[1].trim());

            if (inputItem == Items.AIR || remainderItem == Items.AIR) continue;

            int count = 0;
            for (int i = 0; i < matrix.getContainerSize(); i++) {
                if (matrix.getItem(i).is(inputItem)) count++;
            }
            if (count > 0) remainderCounts.merge(remainderItem, count, Integer::sum);
        }

        for (Map.Entry<Item, Integer> e : remainderCounts.entrySet()) {
            ItemStack remainder = new ItemStack(e.getKey(), e.getValue());
            if (!player.getInventory().add(remainder)) {
                player.drop(remainder, false);
            }
        }
    }

    @SubscribeEvent
    public static void onEggImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof ThrownEgg egg)) return;
        if (egg.level().isClientSide()) return;
        if (!CreateFoodConfig.SERVER.enableEggImpactRemainder.get()) return;

        Item eggshell = resolveItem("createfood:eggshell");
        if (eggshell == Items.AIR) return;

        egg.level().addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(
                egg.level(), egg.getX(), egg.getY(), egg.getZ(), new ItemStack(eggshell)));
    }

    private static Item resolveItem(String id) {
        try {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
            return item != null ? item : Items.AIR;
        } catch (Exception e) {
            return Items.AIR;
        }
    }
}
