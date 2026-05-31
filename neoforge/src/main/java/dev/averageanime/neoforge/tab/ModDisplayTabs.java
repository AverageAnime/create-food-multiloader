package dev.averageanime.neoforge.tab;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.config.ModConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Comparator;
import java.util.function.Supplier;
import static dev.averageanime.neoforge.CreateFood.LOGGER;

public class ModDisplayTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CommonClass.MOD_ID);

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB_BLOCK = CREATIVE_MODE_TAB.register("createfood_display",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, "breakfast_plate_block"))))
                    .title(Component.translatable("tab.createfood.display"))
                    .displayItems((params, output) -> ModDisplayBlocks.BLOCKS.getEntries().stream()
                            .filter(holder -> !holder.getId().getPath().equals("generic_display_plate_block"))
                            .filter(holder -> !holder.getId().getPath().equals("small_plate_block"))
                            .filter(holder -> !holder.getId().getPath().equals("plate_block"))
                            .sorted(Comparator.comparing(a -> a.getId().getPath()))
                            .forEach(holder -> {
                                String blockId = holder.getId().getPath();
                                if (ModConfig.isDisplayBlockEnabled(blockId)) {
                                    output.accept(holder.get().asItem());
                                }
                            }))
                    .build());

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Creative Tabs (Display Blocks)");
        CREATIVE_MODE_TAB.register(eventBus);
    }
}