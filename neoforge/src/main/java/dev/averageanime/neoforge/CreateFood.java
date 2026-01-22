package dev.averageanime.neoforge;

import com.mojang.logging.LogUtils;
import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.config.condition.ModConditions;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.config.ConfigScreen;
import dev.averageanime.neoforge.fluid.FluidEntry;
import dev.averageanime.neoforge.tab.ModTabs;
import dev.averageanime.neoforge.fluid.ModFluids;
import dev.averageanime.neoforge.item.ModItems;
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
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


@Mod(CommonClass.ID)
public class CreateFood {
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateFood(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modContainer.registerConfig(
                net.neoforged.fml.config.ModConfig.Type.CLIENT,
                ModConfig.BUILDER.build(),
                "createfood-client.toml"
        );

        modEventBus.addListener((FMLClientSetupEvent event) -> {
            IConfigScreenFactory factory = new ConfigScreen();
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, factory);
        });

        ModConditions.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModDisplayBlocks.register(modEventBus);
        ModFluids.register(modEventBus);
        ModTabs.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Create: Food - Startup");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Create: Food - Server Startup");
    }

    @EventBusSubscriber(modid = CommonClass.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            setFluidRenderLayers(ModFluids.SQUID_INK_FLUID);
            setFluidRenderLayers(ModFluids.VEGETABLE_OIL_FLUID);
            setFluidRenderLayers(ModFluids.VINEGAR_FLUID);

            setFluidRenderLayers(ModFluids.APPLE_JUICE_FLUID);
            setFluidRenderLayers(ModFluids.BERRY_JUICE_FLUID);
            setFluidRenderLayers(ModFluids.CHORUS_FRUIT_JUICE_FLUID);
            setFluidRenderLayers(ModFluids.GLOW_BERRY_JUICE_FLUID);

            setFluidRenderLayers(ModFluids.BLACK_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.BLUE_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.BROWN_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.CYAN_GELATIN_MIX_FLUID);
            setFluidRenderLayers(ModFluids.GELATIN_MIX_FLUID);
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
            setFluidRenderLayers(ModFluids.YELLOW_GELATIN_MIX_FLUID);
        }

        private static void setFluidRenderLayers(FluidEntry.FluidType fluid) {
            ItemBlockRenderTypes.setRenderLayer(fluid.FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(fluid.SOURCE.get(), RenderType.translucent());
        }
    }
}