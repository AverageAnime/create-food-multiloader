package dev.averageanime.neoforge;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.client.renderer.ClothSackRenderer;
import dev.averageanime.config.ConfigLifecycle;
import dev.averageanime.client.renderer.GenericDisplayPlateRenderer;
import dev.averageanime.client.renderer.SmallBowlFluidRenderer;
import dev.averageanime.client.tooltip.StorageContentsTooltipRenderer;
import dev.averageanime.client.screen.type.item.ClothSackItemScreen;
import dev.averageanime.client.screen.type.block.ClothSackBlockScreen;
import dev.averageanime.client.screen.type.item.RationBoxItemScreen;
import dev.averageanime.client.screen.type.block.RationBoxBlockScreen;
import dev.averageanime.client.tooltip.StorageContentsTooltip;
import dev.averageanime.neoforge.block.BlockEntityRegistration;
import dev.averageanime.neoforge.block.BlockRegistration;
import dev.averageanime.neoforge.block.DisplayBlockRegistration;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.neoforge.block.type.fluid.FluidBlock;
import dev.averageanime.neoforge.config.ConditionRegistration;
import dev.averageanime.neoforge.config.ConfigRegistration;
import dev.averageanime.neoforge.item.ItemRegistration;
import dev.averageanime.neoforge.menu.MenuRegistration;
import dev.averageanime.neoforge.tab.DisplayTabRegistration;
import dev.averageanime.neoforge.tab.TabRegistration;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(CreateFoodCommon.MOD_ID)
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
                ConfigRegistration.CLIENT_SPEC,
                "createfood-client.toml"
        );
        modContainer.registerConfig(
                net.neoforged.fml.config.ModConfig.Type.COMMON,
                ConfigRegistration.COMMON_SPEC,
                "createfood-common.toml"
        );
        modContainer.registerConfig(
                net.neoforged.fml.config.ModConfig.Type.SERVER,
                ConfigRegistration.SERVER_SPEC,
                "createfood-server.toml"
        );
        modEventBus.addListener((FMLClientSetupEvent event) -> {
            IConfigScreenFactory factory = new ConfigRegistration.ConfigScreen();
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, factory);
        });
        modEventBus.addListener((ModConfigEvent.Loading event) -> onServerConfigEvent(event.getConfig(), true));
        modEventBus.addListener((ModConfigEvent.Reloading event) -> onServerConfigEvent(event.getConfig(), true));
        modEventBus.addListener((ModConfigEvent.Unloading event) -> onServerConfigEvent(event.getConfig(), false));

        ConditionRegistration.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        ItemRegistration.register(modEventBus);
        FluidRegistration.register(modEventBus);
        BlockRegistration.register(modEventBus);
        BlockEntityRegistration.register(modEventBus);
        MenuRegistration.register(modEventBus);
        if (isClassPresent("dev.averageanime.neoforge.block.DisplayBlockRegistration")) {
            DisplayBlockRegistration.register(modEventBus);
        }
        TabRegistration.register(modEventBus);
        if (isClassPresent("dev.averageanime.neoforge.tab.DisplayTabRegistration")) {
            DisplayTabRegistration.register(modEventBus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Create: Food - Startup");
        event.enqueueWork(BlockRegistration::wireUpCandleMap);
    }

    private static void onServerConfigEvent(net.neoforged.fml.config.ModConfig config, boolean loaded) {
        if (config.getType() != net.neoforged.fml.config.ModConfig.Type.SERVER) return;
        if (loaded) ConfigLifecycle.onServerConfigLoaded();
        else ConfigLifecycle.onServerConfigUnloaded();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Create: Food - Server Startup");
    }

    @EventBusSubscriber(modid = CreateFoodCommon.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            for (String id : CreateFoodCommon.TRANSLUCENT_FLUIDS) {
                FluidBlock.FluidType fluid = FluidRegistration.BY_ID.get(id);
                if (fluid != null) setFluidRenderLayers(fluid);
            }
        }

        @SubscribeEvent
        public static void onRegisterBERenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(
                    BlockEntityRegistration.CLOTH_SACK.get(),
                    ClothSackRenderer::new);
            event.registerBlockEntityRenderer(
                    BlockEntityRegistration.GENERIC_DISPLAY_PLATE.get(),
                    GenericDisplayPlateRenderer::new);
            event.registerBlockEntityRenderer(
                    BlockEntityRegistration.SMALL_BOWL.get(),
                    SmallBowlFluidRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterScreens(RegisterMenuScreensEvent event) {
            event.register(MenuRegistration.CLOTH_SACK.get(),      ClothSackBlockScreen::new);
            event.register(MenuRegistration.CLOTH_SACK_ITEM.get(), ClothSackItemScreen::new);
            event.register(MenuRegistration.RATION_BOX.get(),       RationBoxBlockScreen::new);
            event.register(MenuRegistration.RATION_BOX_ITEM.get(), RationBoxItemScreen::new);
        }

        @SubscribeEvent
        public static void onRegisterTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(StorageContentsTooltip.class, StorageContentsTooltipRenderer::new);
        }

        private static void setFluidRenderLayers(FluidBlock.FluidType fluid) {
            ItemBlockRenderTypes.setRenderLayer(fluid.FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(fluid.SOURCE.get(), RenderType.translucent());
        }
    }
}