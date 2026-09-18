package dev.averageanime.forge;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.client.renderer.ClothSackRenderer;
import dev.averageanime.config.ConfigLifecycle;
import dev.averageanime.client.renderer.GenericDisplayPlateRenderer;
import dev.averageanime.client.renderer.LargeBowlFluidRenderer;
import dev.averageanime.client.tooltip.StorageContentsTooltipRenderer;
import dev.averageanime.client.screen.type.item.ClothSackItemScreen;
import dev.averageanime.client.screen.type.block.ClothSackBlockScreen;
import dev.averageanime.client.screen.type.item.RationBoxItemScreen;
import dev.averageanime.client.screen.type.block.RationBoxBlockScreen;
import dev.averageanime.client.tooltip.StorageContentsTooltip;
import dev.averageanime.forge.block.BlockEntityRegistration;
import dev.averageanime.forge.block.BlockRegistration;
import dev.averageanime.forge.block.DisplayBlockRegistration;
import dev.averageanime.forge.block.FluidRegistration;
import dev.averageanime.forge.block.type.fluid.FluidBlock;
import dev.averageanime.forge.config.ConditionRegistration;
import dev.averageanime.forge.config.ConfigRegistration;
import dev.averageanime.forge.item.ItemRegistration;
import dev.averageanime.forge.menu.MenuRegistration;
import dev.averageanime.forge.tab.DisplayTabRegistration;
import dev.averageanime.forge.tab.TabRegistration;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.ModLoadingContext;
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

    public CreateFood() {
        IEventBus modEventBus = net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(
                net.minecraftforge.fml.config.ModConfig.Type.CLIENT,
                ConfigRegistration.CLIENT_SPEC,
                "createfood-client.toml"
        );
        ModLoadingContext.get().registerConfig(
                net.minecraftforge.fml.config.ModConfig.Type.COMMON,
                ConfigRegistration.COMMON_SPEC,
                "createfood-common.toml"
        );
        ModLoadingContext.get().registerConfig(
                net.minecraftforge.fml.config.ModConfig.Type.SERVER,
                ConfigRegistration.SERVER_SPEC,
                "createfood-server.toml"
        );

        ConditionRegistration.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ItemRegistration.register(modEventBus);
        FluidRegistration.register(modEventBus);
        BlockRegistration.register(modEventBus);
        BlockEntityRegistration.register(modEventBus);
        MenuRegistration.register(modEventBus);
        if (isClassPresent("dev.averageanime.forge.block.DisplayBlockRegistration")) {
            DisplayBlockRegistration.register(modEventBus);
        }
        TabRegistration.register(modEventBus);
        if (isClassPresent("dev.averageanime.forge.tab.DisplayTabRegistration")) {
            DisplayTabRegistration.register(modEventBus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Create: Food - Startup");
        event.enqueueWork(BlockRegistration::wireUpCandleMap);
    }

    private static void onServerConfigEvent(net.minecraftforge.fml.config.ModConfig config, boolean loaded) {
        if (config.getType() != net.minecraftforge.fml.config.ModConfig.Type.SERVER) return;
        if (loaded) ConfigLifecycle.onServerConfigLoaded();
        else ConfigLifecycle.onServerConfigUnloaded();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Create: Food - Server Startup");
    }

    @Mod.EventBusSubscriber(modid = CreateFoodCommon.MOD_ID, value = Dist.CLIENT)
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
                    BlockEntityRegistration.LARGE_BOWL.get(),
                    LargeBowlFluidRenderer::new);
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