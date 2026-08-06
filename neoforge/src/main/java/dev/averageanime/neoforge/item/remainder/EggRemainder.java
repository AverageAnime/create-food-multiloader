package dev.averageanime.neoforge.item.remainder;

import dev.averageanime.CreateFoodCommon;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

@EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public final class EggRemainder {

    private EggRemainder() {}

    @SubscribeEvent
    public static void onEggImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof ThrownEgg egg)) return;
        dev.averageanime.item.remainder.CraftingRemainder.handleEggImpact(egg);
    }
}
