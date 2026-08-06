package dev.averageanime.neoforge.tab;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.neoforge.block.DisplayBlockRegistration;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.neoforge.item.ItemRegistration;
import dev.averageanime.registry.ItemRegistry;
import dev.averageanime.tab.TabFilters;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static dev.averageanime.neoforge.CreateFood.LOGGER;

public class TabRegistration {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateFoodCommon.MOD_ID);

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB = CREATIVE_MODE_TAB.register("createfood",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemRegistry.BREAKFAST_PLATE.get()))
                    .title(Component.translatable("tab.createfood"))
                    .displayItems((params, output) -> {
                        Set<String> displayBlockPaths = DisplayBlockRegistration.BLOCKS.getEntries().stream()
                                .map(entry -> entry.getId().getPath())
                                .collect(Collectors.toSet());
                        ItemRegistration.ITEMS.getEntries().stream()
                                .filter(holder -> TabFilters.isMainTabItem(holder.getId().getPath(), displayBlockPaths))
                                .sorted(Comparator.comparing(a -> a.getId().getPath()))
                                .forEach(holder -> output.accept(holder.get()));
                    })
                    .build());

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB_FLUID = CREATIVE_MODE_TAB.register("createfood_fluid",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(FluidRegistration.BY_ID.get("cream_pie_filling").BUCKET.get()))
                    .title(Component.translatable("tab.createfood.fluid"))
                    .displayItems((params, output) -> ItemRegistration.ITEMS.getEntries().stream()
                            .filter(holder -> TabFilters.isFluidTabItem(holder.getId().getPath()))
                            .sorted(Comparator.comparing(a -> a.getId().getPath()))
                            .forEach(holder -> output.accept(holder.get())))
                    .build());

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Creative Tabs (Items + Fluids)");
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
