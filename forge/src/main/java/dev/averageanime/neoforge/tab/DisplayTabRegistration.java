package dev.averageanime.forge.tab;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.forge.block.DisplayBlockRegistration;
import dev.averageanime.tab.TabFilters;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Comparator;
import java.util.function.Supplier;
import static dev.averageanime.forge.CreateFood.LOGGER;

public class DisplayTabRegistration {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateFoodCommon.MOD_ID);

    public static final Supplier<CreativeModeTab> CREATEFOOD_TAB_BLOCK = CREATIVE_MODE_TAB.register("createfood_display",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(
                    new net.minecraft.resources.ResourceLocation(CreateFoodCommon.MOD_ID, "breakfast_plate_block"))))
                    .title(Component.translatable("tab.createfood.display"))
                    .displayItems((params, output) -> DisplayBlockRegistration.BLOCKS.getEntries().stream()
                            .filter(holder -> TabFilters.isDisplayTabItem(holder.getId().getPath()))
                            .sorted(Comparator.comparing(a -> a.getId().getPath()))
                            .forEach(holder -> output.accept(holder.get().asItem())))
                    .build());

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Creative Tabs (Display Blocks)");
        CREATIVE_MODE_TAB.register(eventBus);
    }
}