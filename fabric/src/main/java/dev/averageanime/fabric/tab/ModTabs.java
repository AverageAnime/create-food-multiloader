package dev.averageanime.fabric.tab;

import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.block.ModDisplayBlocks;
import dev.averageanime.fabric.block.ModFluids;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.registry.ItemRegistry;
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

public class ModTabs {

    public static final ResourceLocation ITEM_GROUP_ID =
            ResourceLocation.fromNamespaceAndPath(CreateFood.MOD_ID, "createfood");
    public static final ResourceLocation FLUID_GROUP_ID =
            ResourceLocation.fromNamespaceAndPath(CreateFood.MOD_ID, "createfood_fluid");

    public static void init() {
        CreateFood.LOGGER.info("Create: Food - Registering Creative Tabs (Items + Fluids)");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP_ID,
                FabricItemGroup.builder()
                        .title(Component.translatable("tab.createfood"))
                        .icon(() -> new ItemStack(ItemRegistry.BREAKFAST_PLATE.get()))
                        .displayItems((params, output) -> {
                            Set<String> displayBlockPaths = collectDisplayBlockPaths();
                            BuiltInRegistries.ITEM.entrySet().stream()
                                    .filter(e -> e.getKey().location().getNamespace().equals(CreateFood.MOD_ID))
                                    .filter(e -> !e.getKey().location().getPath().endsWith("_bucket"))
                                    .filter(e -> !e.getKey().location().getPath().endsWith("pumpkin_pie_block"))
                                    .filter(e -> !e.getKey().location().getPath().equals("small_plate_block"))
                                    .filter(e -> !e.getKey().location().getPath().equals("plate_block"))
                                    .filter(e -> !displayBlockPaths.contains(e.getKey().location().getPath()))
                                    .filter(e -> ModConfig.isItemEnabled(e.getKey().location().getPath()))
                                    .sorted(Comparator.comparing(e -> e.getKey().location().getPath()))
                                    .forEach(e -> output.accept(e.getValue()));
                        })
                        .build());

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FLUID_GROUP_ID,
                FabricItemGroup.builder()
                        .title(Component.translatable("tab.createfood.fluid"))
                        .icon(() -> new ItemStack(ModFluids.BY_ID.get("cream_pie_filling").BUCKET))
                        .displayItems((params, output) ->
                                BuiltInRegistries.ITEM.entrySet().stream()
                                        .filter(e -> e.getKey().location().getNamespace().equals(CreateFood.MOD_ID))
                                        .filter(e -> e.getKey().location().getPath().endsWith("_bucket"))
                                        .filter(e -> ModConfig.isFluidBucketEnabled(e.getKey().location().getPath()))
                                        .sorted(Comparator.comparing(e -> e.getKey().location().getPath()))
                                        .forEach(e -> output.accept(e.getValue())))
                        .build());

        ModDisplayTabs.init();
    }

    private static Set<String> collectDisplayBlockPaths() {
        Set<String> paths = new HashSet<>();
        for (Block block : ModDisplayBlocks.getRegisteredDisplayBlocks()) {
            ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
            if (key != null && key.getNamespace().equals(CreateFood.MOD_ID)) {
                paths.add(key.getPath());
            }
        }
        return paths;
    }
}
