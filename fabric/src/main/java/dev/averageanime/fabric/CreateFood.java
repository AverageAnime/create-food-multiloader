package dev.averageanime.fabric;

import dev.averageanime.CommonClass;
import dev.averageanime.client.renderer.ClothSackRenderer;
import dev.averageanime.client.renderer.GenericDisplayPlateRenderer;
import dev.averageanime.client.renderer.StorageContentsTooltipRenderer;
import dev.averageanime.client.screen.type.ClothSackItemScreen;
import dev.averageanime.client.screen.type.ClothSackScreen;
import dev.averageanime.client.screen.type.RationBoxItemScreen;
import dev.averageanime.client.screen.type.RationBoxScreen;
import dev.averageanime.client.tooltip.StorageContentsTooltip;
import dev.averageanime.fabric.block.ModBlocks;
import dev.averageanime.fabric.block.ModDisplayBlocks;
import dev.averageanime.fabric.block.type.fluid.FluidEntry;
import dev.averageanime.fabric.block.type.pie.PumpkinPieBlock;
import dev.averageanime.fabric.block.ModBlockEntities;
import dev.averageanime.fabric.config.ModConditions;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.block.ModFluids;
import dev.averageanime.fabric.item.ModItems;
import dev.averageanime.fabric.item.ModTooltips;
import dev.averageanime.fabric.item.interaction.CampfireCookingInteraction;
import dev.averageanime.fabric.item.interaction.ClothFilterInteraction;
import dev.averageanime.fabric.block.handler.BowlPlacementHandler;
import dev.averageanime.fabric.block.handler.PlateSliceHandler;
import dev.averageanime.fabric.item.interaction.HandcraftInteraction;
import dev.averageanime.fabric.menu.ModMenus;
import dev.averageanime.fabric.tab.ModTabs;
import dev.averageanime.registry.BlockRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateFood implements ModInitializer {
	public static final String MOD_ID = CommonClass.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(CommonClass.MOD_ID);

	@Override
	public void onInitialize() {
		ConfigRegistry.registerConfig(CommonClass.MOD_ID,
				io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.CLIENT,
				ModConfig.CLIENT_SPEC);
		ConfigRegistry.registerConfig(CommonClass.MOD_ID,
				io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.SERVER,
				ModConfig.SERVER_SPEC);

		ModConditions.register();
		ModItems.registerModItems();
		ModBlocks.init();
		PumpkinPieBlock.register();
		ModFluids.init();
		ModBlockEntities.init();
		ModMenus.init();
		ModDisplayBlocks.init();

		HandcraftInteraction.register();
		CampfireCookingInteraction.register();
		ClothFilterInteraction.register();
		BowlPlacementHandler.register();
		PlateSliceHandler.register();

		ModTabs.init();
	}

	public static class CreateFoodClient implements ClientModInitializer {

		@Override
		public void onInitializeClient() {
			ItemTooltipCallback.EVENT.register((stack, context, tooltipType, lines) ->
					ModTooltips.onItemTooltip(stack, lines));

			MenuScreens.register(ModMenus.CLOTH_SACK, ClothSackScreen::new);
			MenuScreens.register(ModMenus.RATION_BOX, RationBoxScreen::new);
			MenuScreens.register(ModMenus.CLOTH_SACK_ITEM, ClothSackItemScreen::new);
			MenuScreens.register(ModMenus.RATION_BOX_ITEM, RationBoxItemScreen::new);

			BlockEntityRendererRegistry.register(ModBlockEntities.CLOTH_SACK, ClothSackRenderer::new);
			BlockEntityRendererRegistry.register(ModBlockEntities.GENERIC_DISPLAY_PLATE, GenericDisplayPlateRenderer::new);

			TooltipComponentCallback.EVENT.register(data ->
					data instanceof StorageContentsTooltip s ? new StorageContentsTooltipRenderer(s) : null);

			ModFluids.registerClientRendering();
			registerGelatinBlocks();

			for (String id : dev.averageanime.CommonClass.TRANSLUCENT_FLUIDS) {
				FluidEntry fluid = ModFluids.BY_ID.get(id);
				if (fluid != null) setFluidRenderLayer(fluid);
			}
		}

		private static void registerGelatinBlocks() {
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BLACK_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BLUE_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BROWN_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CYAN_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GRAY_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GREEN_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LIGHT_BLUE_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LIGHT_GRAY_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LIME_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MAGENTA_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ORANGE_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.PINK_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.PURPLE_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.RED_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.YELLOW_GELATIN_DESSERT_BLOCK.get(), RenderType.translucent());
		}

		private static void setFluidRenderLayer(FluidEntry entry) {
			BlockRenderLayerMap.INSTANCE.putFluid(entry.SOURCE, RenderType.translucent());
			BlockRenderLayerMap.INSTANCE.putFluid(entry.FLOWING, RenderType.translucent());
		}
	}
}