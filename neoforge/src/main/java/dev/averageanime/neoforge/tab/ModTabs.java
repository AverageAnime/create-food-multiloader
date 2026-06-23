package dev.averageanime.neoforge.tab;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.ModFluids;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.item.ModItems;
import dev.averageanime.registry.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Comparator;
import java.util.function.Supplier;

import static dev.averageanime.neoforge.CreateFood.LOGGER;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CommonClass.MOD_ID);

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB = CREATIVE_MODE_TAB.register("createfood",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemRegistry.BREAKFAST_PLATE.get()))
                    .title(Component.translatable("tab.createfood"))
                    .displayItems((params, output) -> ModItems.ITEMS.getEntries().stream()
                            .filter(holder -> !holder.getId().getPath().endsWith("_bucket"))
                            .filter(holder -> !holder.getId().getPath().endsWith("pumpkin_pie_block"))
                            .filter(holder -> !holder.getId().getPath().equals("small_plate_block"))
                            .filter(holder -> !holder.getId().getPath().equals("plate_block"))
                            .filter(holder -> !isDisplayBlock(holder.getId().getPath()))
                            .filter(holder -> ModConfig.isItemEnabled(holder.getId().getPath()))
                            .sorted(Comparator.comparing(a -> a.getId().getPath()))
                            .forEach(holder -> output.accept(holder.get())))
                    .build());

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB_FLUID = CREATIVE_MODE_TAB.register("createfood_fluid",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModFluids.BY_ID.get("cream_pie_filling").BUCKET.get()))
                    .title(Component.translatable("tab.createfood.fluid"))
                    .displayItems((params, output) -> ModItems.ITEMS.getEntries().stream()
                            .filter(holder -> holder.getId().getPath().endsWith("_bucket"))
                            .filter(holder -> ModConfig.isFluidBucketEnabled(holder.getId().getPath()))
                            .sorted(Comparator.comparing(a -> a.getId().getPath()))
                            .forEach(holder -> output.accept(holder.get())))
                    .build());

    private static boolean isDisplayBlock(String itemPath) {
        return ModDisplayBlocks.BLOCKS.getEntries().stream()
                .anyMatch(entry -> entry.getId().getPath().equals(itemPath));
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Creative Tabs (Items + Fluids)");
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
