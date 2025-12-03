package dev.averageanime.neoforge;

import com.mojang.logging.LogUtils;
import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.creativetab.ModCreativeModeTabs;
import dev.averageanime.neoforge.fluid.ModFluids;
import dev.averageanime.neoforge.item.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(CommonClass.ID)
public class CreateFood {
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateFood(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModFluids.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        modEventBus.addListener(this::setupClient);

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
            event.enqueueWork(() -> {

            });
        }

    }

    private void setupClient(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GLOW_BERRY_JUICE_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GLOW_BERRY_JUICE_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.CHORUS_FRUIT_JUICE_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.CHORUS_FRUIT_JUICE_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BERRY_JUICE_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BERRY_JUICE_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.APPLE_JUICE_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.APPLE_JUICE_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.VEGETABLE_OIL_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.VEGETABLE_OIL_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.VINEGAR_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.VINEGAR_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SQUID_INK_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SQUID_INK_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.YELLOW_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.YELLOW_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.ORANGE_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.ORANGE_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.MAGENTA_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.MAGENTA_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIGHT_BLUE_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIGHT_BLUE_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIME_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIME_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.PINK_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.PINK_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GRAY_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GRAY_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIGHT_GRAY_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.LIGHT_GRAY_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.CYAN_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.CYAN_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.PURPLE_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.PURPLE_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BLUE_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BLUE_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BROWN_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BROWN_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GREEN_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GREEN_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.RED_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.RED_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BLACK_GELATIN_MIX_FLUID.FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BLACK_GELATIN_MIX_FLUID.SOURCE.get(), RenderType.translucent());
    }
}
