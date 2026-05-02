package dev.averageanime.fabric;

import dev.averageanime.fabric.block.ModBlocks;
import dev.averageanime.fabric.block.ModFluids;
import dev.averageanime.fabric.block.type.fluid.FluidEntry;
import dev.averageanime.fabric.block.type.blockentity.ModBlockEntities;
import dev.averageanime.fabric.client.renderer.ClothSackRenderer;
import dev.averageanime.fabric.client.screen.ClothSackItemScreen;
import dev.averageanime.fabric.client.screen.ClothSackScreen;
import dev.averageanime.fabric.client.screen.RationBoxItemScreen;
import dev.averageanime.fabric.client.screen.RationBoxScreen;
import dev.averageanime.fabric.client.tooltip.ClientStorageContentsTooltip;
import dev.averageanime.fabric.client.tooltip.StorageContentsTooltip;
import dev.averageanime.fabric.item.ModTooltips;
import dev.averageanime.fabric.menu.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;

public class CreateFoodClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModTooltips.SHIFT_DOWN = Screen::hasShiftDown;

        ItemTooltipCallback.EVENT.register((stack, context, tooltipType, lines) ->
                ModTooltips.onItemTooltip(stack, lines));

        MenuScreens.register(ModMenus.CLOTH_SACK, ClothSackScreen::new);
        MenuScreens.register(ModMenus.RATION_BOX, RationBoxScreen::new);
        MenuScreens.register(ModMenus.CLOTH_SACK_ITEM, ClothSackItemScreen::new);
        MenuScreens.register(ModMenus.RATION_BOX_ITEM, RationBoxItemScreen::new);

        BlockEntityRendererRegistry.register(ModBlockEntities.CLOTH_SACK, ClothSackRenderer::new);

        TooltipComponentCallback.EVENT.register(data ->
                data instanceof StorageContentsTooltip s ? new ClientStorageContentsTooltip(s) : null);

        ModFluids.registerClientRendering();
        registerGelatinBlocks();

        setFluidRenderLayer(ModFluids.APPLE_JUICE_FLUID);
        setFluidRenderLayer(ModFluids.BERRY_JUICE_FLUID);
        setFluidRenderLayer(ModFluids.BLACK_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.BLUE_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.BROWN_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.CHORUS_FRUIT_JUICE_FLUID);
        setFluidRenderLayer(ModFluids.CYAN_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.GLOW_BERRY_JUICE_FLUID);
        setFluidRenderLayer(ModFluids.GRAY_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.GREEN_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.LIGHT_BLUE_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.LIGHT_GRAY_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.LIME_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.MAGENTA_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.ORANGE_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.PINK_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.PURPLE_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.RED_GELATIN_MIX_FLUID);
        setFluidRenderLayer(ModFluids.SQUID_INK_FLUID);
        setFluidRenderLayer(ModFluids.VEGETABLE_OIL_FLUID);
        setFluidRenderLayer(ModFluids.VINEGAR_FLUID);
        setFluidRenderLayer(ModFluids.YELLOW_GELATIN_MIX_FLUID);
    }

    private static void registerGelatinBlocks() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROWN_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CYAN_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRAY_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GREEN_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_BLUE_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_GRAY_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIME_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAGENTA_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGE_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_GELATIN_DESSERT_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.YELLOW_GELATIN_DESSERT_BLOCK, RenderType.translucent());
    }

    private static void setFluidRenderLayer(FluidEntry entry) {
        BlockRenderLayerMap.INSTANCE.putFluid(entry.SOURCE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(entry.FLOWING, RenderType.translucent());
    }
}
