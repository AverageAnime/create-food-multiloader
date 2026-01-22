package dev.averageanime.neoforge.tab;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.fluid.ModFluids;
import dev.averageanime.neoforge.item.ModItems;
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
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CommonClass.ID);

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB = CREATIVE_MODE_TAB.register("createfood",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BREAKFAST_PLATE.get()))
                    .title(Component.translatable("tab.createfood"))
                    .displayItems((params, output) -> ModItems.ITEMS.getEntries().stream()
                            .filter(holder -> !holder.getId().getPath().endsWith("_bucket"))
                            .filter(holder -> !holder.getId().getPath().endsWith("pumpkin_pie_block"))
                            .filter(holder -> {
                                String itemPath = holder.getId().getPath();
                                return ModDisplayBlocks.BLOCKS.getEntries().stream()
                                        .noneMatch(blockHolder -> blockHolder.getId().getPath().equals(itemPath));
                            })
                            .sorted(Comparator.comparing(a -> a.getId().getPath()))
                            .forEach(holder -> {
                                String itemId = holder.getId().getPath();
                                if (ModConfig.isItemEnabled(itemId)) {
                                    output.accept(holder.get());
                                }
                            }))
                    .build());

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB_FLUID = CREATIVE_MODE_TAB.register("createfood_fluid",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModFluids.CREAM_PIE_FILLING_FLUID.BUCKET.get()))
                    .title(Component.translatable("tab.createfood.fluid"))
                    .displayItems((params, output) -> ModItems.ITEMS.getEntries().stream()
                            .filter(holder -> holder.getId().getPath().endsWith("_bucket"))
                            .sorted(Comparator.comparing(a -> a.getId().getPath()))
                            .forEach(holder -> {
                                String itemId = holder.getId().getPath();
                                if (ModConfig.isItemEnabled(itemId)) {
                                    output.accept(holder.get());
                                }
                            }))
                    .build());

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB_BLOCK = CREATIVE_MODE_TAB.register("createfood_display",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModDisplayBlocks.CREAM_SWEET_ROLL_PLATE.get()))
                    .title(Component.translatable("tab.createfood.display"))
                    .displayItems((params, output) -> ModDisplayBlocks.BLOCKS.getEntries().stream()
                            .sorted(Comparator.comparing(a -> a.getId().getPath()))
                            .forEach(holder -> {
                                String blockId = holder.getId().getPath();
                                if (ModConfig.isDisplayBlockEnabled(blockId)) {
                                    output.accept(holder.get().asItem());
                                }
                            }))
                    .build());

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Creative Tabs");
        CREATIVE_MODE_TAB.register(eventBus);
    }
}