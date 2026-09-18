package dev.averageanime.forge.item;

import dev.averageanime.config.ConfigBootstrap;
import dev.averageanime.config.ConfigDefaults;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.item.ItemFactory;
import dev.averageanime.item.type.EffectFood.DeferredFx;
import dev.averageanime.forge.item.type.EffectFood;
import dev.averageanime.platform.Services;
import dev.averageanime.registry.type.EffectEntry;
import dev.averageanime.util.Tooltips;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static dev.averageanime.forge.CreateFood.LOGGER;

public class ItemRegistration {

    public static final DeferredRegister<net.minecraft.world.item.Item> ITEMS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.ITEM, CreateFoodCommon.MOD_ID);

    private static final ItemFactory.Hooks HOOKS = new ItemFactory.Hooks() {
        @Override
        public Supplier<net.minecraft.world.item.Item> register(String id, Supplier<net.minecraft.world.item.Item> factory) {
            return ITEMS.register(id, factory);
        }

        @Override
        public dev.averageanime.item.type.EffectFood createEffectFood(Properties props, Set<String> ids,
                List<DeferredFx> deferred, Tooltips.TooltipSpec tip) {
            return new EffectFood(props, ids, deferred, tip);
        }

        @Override
        public void addFoodEffect(FoodProperties.Builder builder, String itemId, EffectEntry spec) {
            MobEffect effect = spec.rawEffect();
            builder.effect(() -> {
                ItemEffectOverride override = ConfigValues.getItemEffectOverride(itemId, spec.categoryOrEffectId());
                if (override != null) {
                    if (override.remove()) return new MobEffectInstance(effect, 0, override.amplifier());
                    return new MobEffectInstance(effect, override.duration(), override.amplifier());
                }
                return new MobEffectInstance(effect, spec.duration, spec.amplifier);
            }, spec.chance);
        }
    };

    static {
        ItemFactory.registerAll(HOOKS);
    }

    private static void registerConfigItems() {
        ItemFactory.registerConfigItems(HOOKS,
                ConfigBootstrap.read(ConfigBootstrap.ITEMS, ConfigDefaults.CUSTOM_ITEM_DEFAULT));
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Items");
        registerConfigItems();
        ITEMS.register(eventBus);
    }
}
