package dev.averageanime.fabric.tab;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.block.DisplayBlockRegistration;
import dev.averageanime.tab.TabFilters;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;

public class DisplayTabRegistration {

    public static final ResourceLocation DISPLAY_GROUP_ID =
            ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "createfood_display");

    public static void init() {
        CreateFood.LOGGER.info("Create: Food - Registering Creative Tabs (Display Blocks)");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DISPLAY_GROUP_ID,
                FabricItemGroup.builder()
                        .title(Component.translatable("tab.createfood.display"))
                        .icon(() -> new ItemStack(BuiltInRegistries.BLOCK.get(
                                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "breakfast_plate_block"))))
                        .displayItems((params, output) ->
                                DisplayBlockRegistration.getRegisteredDisplayBlocks().stream()
                                        .filter(b -> TabFilters.isDisplayTabItem(BuiltInRegistries.BLOCK.getKey(b).getPath()))
                                        .sorted(Comparator.comparing(b -> BuiltInRegistries.BLOCK.getKey(b).getPath()))
                                        .forEach(b -> output.accept(b.asItem())))
                        .build());
    }
}
