package dev.averageanime.fabric;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.client.renderer.ClothSackRenderer;
import dev.averageanime.client.renderer.GenericDisplayPlateRenderer;
import dev.averageanime.client.renderer.LargeBowlFluidRenderer;
import dev.averageanime.client.tooltip.StorageContentsTooltipRenderer;
import dev.averageanime.client.screen.type.item.ClothSackItemScreen;
import dev.averageanime.client.screen.type.block.ClothSackBlockScreen;
import dev.averageanime.client.screen.type.item.RationBoxItemScreen;
import dev.averageanime.client.screen.type.block.RationBoxBlockScreen;
import dev.averageanime.client.tooltip.StorageContentsTooltip;
import dev.averageanime.fabric.block.BlockRegistration;
import dev.averageanime.fabric.block.DisplayBlockRegistration;
import dev.averageanime.fabric.block.type.fluid.FluidBlock;
import dev.averageanime.fabric.block.type.pie.PumpkinPieEvents;
import dev.averageanime.fabric.block.BlockEntityRegistration;
import dev.averageanime.config.ConfigLifecycle;
import dev.averageanime.fabric.config.ConfigEvents;
import dev.averageanime.fabric.config.ConditionRegistration;
import dev.averageanime.fabric.config.ConfigRegistration;
import dev.averageanime.fabric.block.FluidRegistration;
import dev.averageanime.fabric.item.ItemRegistration;
import dev.averageanime.client.tooltip.ItemTooltips;
import dev.averageanime.fabric.item.interaction.CampfireCookingInteraction;
import dev.averageanime.fabric.item.interaction.ClothFilterInteraction;
import dev.averageanime.fabric.block.type.bowl.BowlPlacementEvents;
import dev.averageanime.fabric.block.type.display.FoodTransformEvents;
import dev.averageanime.fabric.block.type.plate.PlateSliceEvents;
import dev.averageanime.fabric.item.interaction.HandcraftInteraction;
import dev.averageanime.fabric.menu.MenuRegistration;
import dev.averageanime.fabric.tab.TabRegistration;
import dev.averageanime.registry.type.BlockEntry;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateFood implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(CreateFoodCommon.MOD_ID);

    @Override
    public void onInitialize() {
        ConfigRegistry.registerConfig(CreateFoodCommon.MOD_ID,
                io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.CLIENT,
                ConfigRegistration.CLIENT_SPEC);
        ConfigRegistry.registerConfig(CreateFoodCommon.MOD_ID,
                io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.COMMON,
                ConfigRegistration.COMMON_SPEC);
        ConfigRegistry.registerConfig(CreateFoodCommon.MOD_ID,
                io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.SERVER,
                ConfigRegistration.SERVER_SPEC);
        ConfigEvents.register();

        ConditionRegistration.register();
        ItemRegistration.registerItemRegistration();
        BlockRegistration.init();
        PumpkinPieEvents.register();
        FluidRegistration.init();
        MenuRegistration.init();
        // Must precede BlockEntityRegistration: the block entity types are built against these
        // blocks, and Builder.of stores them eagerly.
        DisplayBlockRegistration.init();
        BlockEntityRegistration.init();

        HandcraftInteraction.register();
        CampfireCookingInteraction.register();
        ClothFilterInteraction.register();
        BowlPlacementEvents.register();
        FoodTransformEvents.register();
        PlateSliceEvents.register();

        TabRegistration.init();
    }

    public static class CreateFoodClient implements ClientModInitializer {

        @Override
        public void onInitializeClient() {
            // Porting Lib fires no Unloading for a remote server's synced config;
            // restore on disconnect instead (no-op when nothing was applied).
            ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                    ConfigLifecycle.onServerConfigUnloaded());

            ItemTooltipCallback.EVENT.register((stack, context, tooltipType, lines) ->
                    ItemTooltips.onItemTooltip(stack, lines));

            MenuScreens.register(MenuRegistration.CLOTH_SACK, ClothSackBlockScreen::new);
            MenuScreens.register(MenuRegistration.RATION_BOX, RationBoxBlockScreen::new);
            MenuScreens.register(MenuRegistration.CLOTH_SACK_ITEM, ClothSackItemScreen::new);
            MenuScreens.register(MenuRegistration.RATION_BOX_ITEM, RationBoxItemScreen::new);

            BlockEntityRendererRegistry.register(BlockEntityRegistration.CLOTH_SACK, ClothSackRenderer::new);
            BlockEntityRendererRegistry.register(BlockEntityRegistration.GENERIC_DISPLAY_PLATE, GenericDisplayPlateRenderer::new);
            BlockEntityRendererRegistry.register(BlockEntityRegistration.LARGE_BOWL, LargeBowlFluidRenderer::new);

            TooltipComponentCallback.EVENT.register(data ->
                    data instanceof StorageContentsTooltip s ? new StorageContentsTooltipRenderer(s) : null);

            FluidRegistration.registerClientRendering();
            registerGelatinBlocks();

            for (String id : CreateFoodCommon.TRANSLUCENT_FLUIDS) {
                FluidBlock fluid = FluidRegistration.BY_ID.get(id);
                if (fluid != null) setFluidRenderLayer(fluid);
            }
        }

        private static void registerGelatinBlocks() {
            for (BlockEntry def : BlockEntry.ALL) {
                if (def.category == BlockEntry.BlockCategory.GELATIN) {
                    BlockRenderLayerMap.INSTANCE.putBlock(def.get(), RenderType.translucent());
                }
            }
        }

        private static void setFluidRenderLayer(FluidBlock entry) {
            BlockRenderLayerMap.INSTANCE.putFluid(entry.SOURCE, RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putFluid(entry.FLOWING, RenderType.translucent());
        }
    }
}
