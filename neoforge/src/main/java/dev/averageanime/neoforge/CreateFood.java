package dev.averageanime.neoforge;

import dev.averageanime.config.ConfigBootstrap;
import dev.averageanime.platform.Services;
import dev.averageanime.createfood.lib.compat.BasinFluidCapacity;
import dev.averageanime.createfood.lib.effect.EffectChain;
import dev.averageanime.createfood.lib.pack.NeoForgeBuiltinPacks;
import dev.averageanime.pack.BuiltInPacks;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.client.renderer.ClothSackRenderer;
import dev.averageanime.config.ConfigLifecycle;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.client.renderer.GenericDisplayPlateRenderer;
import dev.averageanime.createfood.lib.client.FluidSurfaceRenderer;
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
import dev.averageanime.neoforge.compat.farmersdelight.ContainerFluids;
import dev.averageanime.neoforge.compat.farmersdelight.FarmersDelightClient;
import dev.averageanime.neoforge.compat.farmersdelight.HollowCookingPot;
import dev.averageanime.createfood.lib.fluid.FluidBlock;
import dev.averageanime.createfood.lib.config.RecipeConditions;
import dev.averageanime.createfood.lib.recipe.RecipeSubjects;
import dev.averageanime.createfood.lib.recipe.adapted.AdaptedRecipes;
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
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(CreateFoodCommon.MOD_ID)
public class CreateFood {
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateFood(IEventBus modEventBus, ModContainer modContainer) {
        // Wire the shared effect chain before anything resolves.
        EffectChain.modLoadedCheck(Services.PLATFORM::isModLoaded);

        // Read lazily: the mixins run long after this.
        BasinFluidCapacity.gate(ConfigValues::isExpandedBasinFluidsEnabled);

        NeoForgeBuiltinPacks.register(modEventBus, CreateFoodCommon.MOD_ID, BuiltInPacks.NAMES);

        modEventBus.addListener(this::commonSetup);

        modContainer.registerConfig(
                net.neoforged.fml.config.ModConfig.Type.CLIENT,
                ConfigRegistration.CLIENT_SPEC,
                ConfigBootstrap.CLIENT_FILE
        );
        modContainer.registerConfig(
                net.neoforged.fml.config.ModConfig.Type.COMMON,
                ConfigRegistration.COMMON_SPEC,
                ConfigBootstrap.COMMON_FILE
        );
        modContainer.registerConfig(
                net.neoforged.fml.config.ModConfig.Type.SERVER,
                ConfigRegistration.SERVER_SPEC,
                ConfigBootstrap.SERVER_FILE
        );
        modEventBus.addListener((FMLClientSetupEvent event) -> {
            IConfigScreenFactory factory = new ConfigRegistration.ConfigScreen();
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, factory);
        });
        modEventBus.addListener((ModConfigEvent.Loading event) -> onServerConfigEvent(event.getConfig(), true));
        modEventBus.addListener((ModConfigEvent.Reloading event) -> onServerConfigEvent(event.getConfig(), true));
        modEventBus.addListener((ModConfigEvent.Unloading event) -> onServerConfigEvent(event.getConfig(), false));

        // Read lazily, like the basin gate above: recipes are not read until datapack load.
        RecipeSubjects.gate(ConfigValues::isRecipeSubjectAvailable);
        RecipeConditions.register(modEventBus, CreateFoodCommon.MOD_ID);
        // Copies Create/FD/Ube's Delight recipes for the plate and bowl interactions when those mods are absent.
        AdaptedRecipes.register(modEventBus, CreateFoodCommon.MOD_ID);

        NeoForge.EVENT_BUS.register(this);
        ItemRegistration.register(modEventBus);
        FluidRegistration.register(modEventBus);
        BlockRegistration.register(modEventBus);
        BlockEntityRegistration.register(modEventBus);
        MenuRegistration.register(modEventBus);
        DisplayBlockRegistration.register(modEventBus);
        TabRegistration.register(modEventBus);
        DisplayTabRegistration.register(modEventBus);
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
                    BlockEntityRegistration.LARGE_BOWL.get(),
                    ctx -> new FluidSurfaceRenderer<>(ctx, CreateFoodCommon.MOD_ID));
            // Behind its own class so Farmer's Delight types are never loaded when it is absent.
            if (ModList.get().isLoaded("farmersdelight")) {
                FarmersDelightClient.registerRenderers(event);
            }
        }

        @SubscribeEvent
        public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener(HollowCookingPot.RELOAD_LISTENER);
        }

        /** Emptying recipes can change on joining a world and on every /reload. */
        @SubscribeEvent
        public static void onRecipesUpdated(RecipesUpdatedEvent event) {
            ContainerFluids.clear();
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