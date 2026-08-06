package dev.averageanime.fabric.item;

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
            builder.effect(new MobEffectInstance(spec.rawEffect(), spec.duration, spec.amplifier), 1.0f);
        }
    };

    public static void registerItemRegistration() {
        ItemFactory.registerAll(HOOKS);
        registerConfigItems();
    }

    private static void registerConfigItems() {
        var configFile = Services.PLATFORM.getConfigDir().resolve("createfood-common.toml");
        if (!java.nio.file.Files.exists(configFile)) {
            CreateFood.LOGGER.warn("Create: Food - createfood-common.toml not found yet; skipping custom_item registration for this launch");
            return;
        }
        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("items.item", List.of());
            ItemFactory.registerConfigItems(HOOKS, entries);
        } catch (Exception e) {
            CreateFood.LOGGER.warn("Create: Food - Failed to read custom_item from config", e);
        }
    }
}
