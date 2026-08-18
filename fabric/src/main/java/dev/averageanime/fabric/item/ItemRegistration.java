package dev.averageanime.fabric.item;

import dev.averageanime.config.ConfigBootstrap;
import dev.averageanime.config.ConfigDefaults;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.fabric.CreateFood;
import dev.averageanime.item.ItemFactory;
import dev.averageanime.item.type.EffectFood;
import dev.averageanime.item.type.EffectFood.DeferredFx;
import dev.averageanime.platform.Services;
import dev.averageanime.registry.type.EffectEntry;
import dev.averageanime.util.Tooltips;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item.Properties;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ItemRegistration {

    private static final ItemFactory.Hooks HOOKS = new ItemFactory.Hooks() {
        @Override
        public Supplier<net.minecraft.world.item.Item> register(String id, Supplier<net.minecraft.world.item.Item> factory) {
            net.minecraft.world.item.Item registered = Registry.register(BuiltInRegistries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, id), factory.get());
            return () -> registered;
        }

        @Override
        public EffectFood createEffectFood(Properties props, Set<String> ids, List<DeferredFx> deferred, Tooltips.TooltipSpec tip) {
            return new EffectFood(props, ids, deferred, tip);
        }

        @Override
        public void addFoodEffect(FoodProperties.Builder builder, String itemId, EffectEntry spec) {
            // Matches NeoForge: honour the per-item config override, and carry
            // the spec's own probability instead of forcing every baked effect
            // to fire. Fabric previously did neither.
            ItemEffectOverride override = ConfigValues.getItemEffectOverride(itemId, spec.categoryOrEffectId());
            int duration = spec.duration;
            int amplifier = spec.amplifier;
            if (override != null) {
                duration = override.remove() ? 0 : override.duration();
                amplifier = override.amplifier();
            }
            builder.effect(new MobEffectInstance(spec.rawEffect(), duration, amplifier), spec.chance);
        }
    };

    public static void registerItemRegistration() {
        ItemFactory.registerAll(HOOKS);
        registerConfigItems();
    }

    private static void registerConfigItems() {
        ItemFactory.registerConfigItems(HOOKS,
                ConfigBootstrap.read(ConfigBootstrap.ITEMS, ConfigDefaults.CUSTOM_ITEM_DEFAULT));
    }
}
