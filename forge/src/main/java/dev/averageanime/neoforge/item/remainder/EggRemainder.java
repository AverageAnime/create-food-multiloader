package dev.averageanime.forge.item.remainder;

import dev.averageanime.CreateFoodCommon;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

@Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID)
public final class EggRemainder {

    private EggRemainder() {}

    @SubscribeEvent
    public static void onEggImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof ThrownEgg egg)) return;
        dev.averageanime.item.remainder.CraftingRemainder.handleEggImpact(egg);
    }
}
