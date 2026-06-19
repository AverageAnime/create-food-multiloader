package net.averageanime.createfood.item;

import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(modid = net.averageanime.createfood.CreateFood.ID)
public class FoodConsumptionHandler {

    @SubscribeEvent
    public static void onItemUsed(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getEntity().level().isClientSide()) return;
        ItemStack stack = event.getItem();
        if (!stack.isEdible()) return;
        if (stack.getItem() instanceof EffectFood) return;

        ResourceLocation itemLoc = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (itemLoc == null) return;
        String itemId = itemLoc.getPath();

        List<ConfigLogic.ItemEffectOverride> overrides = ConfigLogic.getItemOverrideEntries(
                itemId, CreateFoodConfig.SERVER.itemOverrides.get());
        for (ConfigLogic.ItemEffectOverride override : overrides) {
            if (override.remove()) {
                MobEffect effect = getEffect(override.effectId());
                if (effect != null) player.removeEffect(effect);
            } else if (override.duration() > 0) {
                MobEffect effect = getEffect(override.effectId());
                if (effect != null)
                    player.addEffect(new MobEffectInstance(effect, override.duration(), override.amplifier()));
            }
        }
    }

    private static MobEffect getEffect(String id) {
        MobEffect direct = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(id));
        if (direct != null) return direct;
        String mappedId = ConfigLogic.getCategoryEffectOverride(id, CreateFoodConfig.SERVER.categoryOverrides.get());
        if (mappedId != null) return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(mappedId));
        return null;
    }
}
