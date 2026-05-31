package dev.averageanime.fabric.tab;

import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.block.ModDisplayBlocks;
import dev.averageanime.fabric.config.ModConfig;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;

public class ModDisplayTabs {

    public static final ResourceLocation DISPLAY_GROUP_ID =
            ResourceLocation.fromNamespaceAndPath(CreateFood.MOD_ID, "createfood_display");

    public static void init() {
        CreateFood.LOGGER.info("Create: Food - Registering Creative Tabs (Display Blocks)");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DISPLAY_GROUP_ID,
                FabricItemGroup.builder()
                        .title(Component.translatable("tab.createfood.display"))
                        .icon(() -> new ItemStack(BuiltInRegistries.BLOCK.get(
                                ResourceLocation.fromNamespaceAndPath(CreateFood.MOD_ID, "breakfast_plate_block"))))
                        .displayItems((params, output) ->
                                ModDisplayBlocks.getRegisteredDisplayBlocks().stream()
                                        .filter(b -> {
                                            ResourceLocation key = BuiltInRegistries.BLOCK.getKey(b);
                                            return !key.getPath().equals("plate_block") && !key.getPath().equals("small_plate_block") && !key.getPath().equals("generic_display_plate_block");
                                        })
                                        .sorted(Comparator.comparing(b -> BuiltInRegistries.BLOCK.getKey(b).getPath()))
                                        .filter(b -> ModConfig.isDisplayBlockEnabled(BuiltInRegistries.BLOCK.getKey(b).getPath()))
                                        .forEach(b -> output.accept(b.asItem())))
                        .build());
    }
}
