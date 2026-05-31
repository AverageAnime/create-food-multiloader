package dev.averageanime.neoforge.item.remainder;

import dev.averageanime.CommonClass;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.inventory.CraftingContainer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = CommonClass.MOD_ID)
public final class CraftingRemainder {

    private CraftingRemainder() {}

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getInventory() instanceof CraftingContainer craftingMatrix)) return;
        dev.averageanime.item.remainder.CraftingRemainder.handleItemCrafted(event.getEntity(), craftingMatrix);
    }

    @SubscribeEvent
    public static void onEggImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof ThrownEgg egg)) return;
        dev.averageanime.item.remainder.CraftingRemainder.handleEggImpact(egg);
    }
}
