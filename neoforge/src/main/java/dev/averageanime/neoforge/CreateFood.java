package dev.averageanime.neoforge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.ModFluids;
import dev.averageanime.neoforge.block.type.fluid.FluidEntry;
import dev.averageanime.neoforge.block.type.blockentity.ModBlockEntities;
import dev.averageanime.neoforge.client.renderer.ClothSackRenderer;
import dev.averageanime.neoforge.client.screen.ClothSackItemScreen;
import dev.averageanime.neoforge.client.screen.ClothSackScreen;
import dev.averageanime.neoforge.client.screen.RationBoxItemScreen;
import dev.averageanime.neoforge.client.screen.RationBoxScreen;
import dev.averageanime.neoforge.client.tooltip.ClientStorageContentsTooltip;
import dev.averageanime.neoforge.client.tooltip.StorageContentsTooltip;
import dev.averageanime.neoforge.config.ModConditions;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.item.ModItems;
import dev.averageanime.neoforge.menu.ModMenus;
import dev.averageanime.neoforge.tab.ModDisplayTabs;
import dev.averageanime.neoforge.tab.ModTabs;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(CommonClass.MOD_ID)
public class CreateFood {
    public static final Logger LOGGER = LogUtils.getLogger();

    private static boolean isClassPresent(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public CreateFood(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modContainer.registerConfig(
                net.neoforged.fml.config.ModConfig.Type.CLIENT,
                ModConfig.BUILDER.build(),
                "createfood-client.toml"
        );
        modContainer.registerConfig(
                net.neoforged.fml.config.ModConfig.Type.SERVER,
                ModConfig.SERVER_BUILDER.build(),
                "createfood-server.toml"
        );
        modEventBus.addListener((FMLClientSetupEvent event) -> {
            IConfigScreenFactory factory = new ModConfig.ConfigScreen();
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, factory);
        });

        ModConditions.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        ModItems.register(modEventBus);
        ModFluids.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        if (isClassPresent("dev.averageanime.neoforge.block.ModDisplayBlocks")) {
            ModDisplayBlocks.register(modEventBus);
        }
        ModTabs.register(modEventBus);
        if (isClassPresent("dev.averageanime.neoforge.tab.ModDisplayTabs")) {
            ModDisplayTabs.register(modEventBus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Create: Food - Startup");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Create: Food - Server Startup");
    }

    @EventBusSubscriber(modid = CommonClass.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            setFluidRenderLayers(ModFluids.APPLE_JUICE_FLUID);
            setFluidRenderLayers(ModFluids.BERRY_JUICE_FLUID);
            setFluidRenderLayers(ModFluids.BLACK_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.BLUE_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.BROWN_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.CHORUS_FRUIT_JUICE_FLUID);
            setFluidRenderLayers(ModFluids.CYAN_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.GLOW_BERRY_JUICE_FLUID);
            setFluidRenderLayers(ModFluids.GRAY_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.GREEN_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.LIGHT_BLUE_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.LIGHT_GRAY_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.LIME_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.MAGENTA_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.ORANGE_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.PINK_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.PURPLE_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.RED_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.SQUID_INK_FLUID);
            setFluidRenderLayers(ModFluids.VEGETABLE_OIL_FLUID);
            setFluidRenderLayers(ModFluids.VINEGAR_FLUID);
            setFluidRenderLayers(ModFluids.YELLOW_GELATIN_MIX_FLUID);
        }

        @SubscribeEvent
        public static void onRegisterBERenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(
                    ModBlockEntities.CLOTH_SACK.get(),
                    ClothSackRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenus.CLOTH_SACK.get(),      ClothSackScreen::new);
            event.register(ModMenus.CLOTH_SACK_ITEM.get(), ClothSackItemScreen::new);
            event.register(ModMenus.RATION_BOX.get(),       RationBoxScreen::new);
            event.register(ModMenus.RATION_BOX_ITEM.get(), RationBoxItemScreen::new);
        }

        @SubscribeEvent
        public static void onRegisterTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(StorageContentsTooltip.class, ClientStorageContentsTooltip::new);
        }

        private static void setFluidRenderLayers(FluidEntry.FluidType fluid) {
            ItemBlockRenderTypes.setRenderLayer(fluid.FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(fluid.SOURCE.get(), RenderType.translucent());
        }
    }
}