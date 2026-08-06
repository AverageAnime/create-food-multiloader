package dev.averageanime.fabric.tab;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.block.DisplayBlockRegistration;
import dev.averageanime.fabric.block.FluidRegistration;
import dev.averageanime.registry.ItemRegistry;
import dev.averageanime.tab.TabFilters;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class TabRegistration {

    public static final ResourceLocation ITEM_GROUP_ID =
            ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "createfood");
    public static final ResourceLocation FLUID_GROUP_ID =
            ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "createfood_fluid");

    public static void init() {
        CreateFood.LOGGER.info("Create: Food - Registering Creative Tabs (Items + Fluids)");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP_ID,
                FabricItemGroup.builder()
                        .title(Component.translatable("tab.createfood"))
                        .icon(() -> new ItemStack(ItemRegistry.BREAKFAST_PLATE.get()))
                        .displayItems((params, output) -> {
                            Set<String> displayBlockPaths = collectDisplayBlockPaths();
                            BuiltInRegistries.ITEM.entrySet().stream()
                                    .filter(e -> e.getKey().location().getNamespace().equals(CreateFoodCommon.MOD_ID))
                                    .filter(e -> TabFilters.isMainTabItem(e.getKey().location().getPath(), displayBlockPaths))
                                    .sorted(Comparator.comparing(e -> e.getKey().location().getPath()))
                                    .forEach(e -> output.accept(e.getValue()));
                        })
                        .build());

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FLUID_GROUP_ID,
                FabricItemGroup.builder()
                        .title(Component.translatable("tab.createfood.fluid"))
                        .icon(() -> new ItemStack(FluidRegistration.BY_ID.get("cream_pie_filling").BUCKET))
                        .displayItems((params, output) ->
                                BuiltInRegistries.ITEM.entrySet().stream()
                                        .filter(e -> e.getKey().location().getNamespace().equals(CreateFoodCommon.MOD_ID))
                                        .filter(e -> TabFilters.isFluidTabItem(e.getKey().location().getPath()))
                                        .sorted(Comparator.comparing(e -> e.getKey().location().getPath()))
                                        .forEach(e -> output.accept(e.getValue())))
                        .build());

        DisplayTabRegistration.init();
    }

    private static Set<String> collectDisplayBlockPaths() {
        Set<String> paths = new HashSet<>();
        for (Block block : DisplayBlockRegistration.getRegisteredDisplayBlocks()) {
            ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
            if (key != null && key.getNamespace().equals(CreateFoodCommon.MOD_ID)) {
                paths.add(key.getPath());
            }
        }
        return paths;
    }
}
