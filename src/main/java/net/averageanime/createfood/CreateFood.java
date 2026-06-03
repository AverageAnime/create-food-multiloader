package net.averageanime.createfood;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.averageanime.createfood.block.ModBlocks;
import net.averageanime.createfood.client.renderer.ClothSackRenderer;
import net.averageanime.createfood.client.renderer.StorageContentsTooltipRenderer;
import net.averageanime.createfood.client.tooltip.StorageContentsTooltip;
import net.averageanime.createfood.menu.ModMenus;
import net.averageanime.createfood.screen.ClothSackItemScreen;
import net.averageanime.createfood.screen.ClothSackScreen;
import net.averageanime.createfood.screen.RationBoxItemScreen;
import net.averageanime.createfood.screen.RationBoxScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.slf4j.Logger;
import net.averageanime.createfood.block.ModBlockEntities;
import net.averageanime.createfood.block.ModDisplayBlocks;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.averageanime.createfood.config.EnabledCondition;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.averageanime.createfood.creativetab.ModCreativeTab;
import net.averageanime.createfood.fluid.ModFluids;
import net.averageanime.createfood.item.ModItems;
import net.averageanime.createfood.client.renderer.GenericDisplayPlateRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;


@Mod(CreateFood.ID)
public class CreateFood {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "createfood";
    public static final String NAME = "Create: Food";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

    public CreateFood() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CreateFoodConfig.register();
        ModBlocks.register();
        ModBlocks.registerStorageBlocks(modEventBus);
        ModDisplayBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModFluids.register();
        ModItems.ITEMS.register(modEventBus);
        ModMenus.register(modEventBus);

        ModCreativeTab.register(modEventBus);
        REGISTRATE.registerEventListeners(modEventBus);

        modEventBus.addListener((FMLCommonSetupEvent event) -> event.enqueueWork(() -> {
            ModBlocks.wireUpCandleMap();
            CraftingHelper.register(EnabledCondition.Serializer.INSTANCE);
            // Composting chances (ported from NeoForge data_maps)
            ComposterBlock.COMPOSTABLES.put(ModItems.EGGSHELL.get(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModItems.BOILED_EGG_PEELED.get(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.APPLE_CREAM_CAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.BERRY_CREAM_CAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.CHORUS_FRUIT_CREAM_CAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.CREAM_CAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.CHOCOLATE_CREAM_CAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.GLOW_BERRY_CREAM_CAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.UBE_CREAM_UBE_CAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.APPLE_CHEESECAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.CHEESECAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.CHORUS_FRUIT_CHEESECAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.GLOW_BERRY_CHEESECAKE.get().asItem(), 1.0f);
            ComposterBlock.COMPOSTABLES.put(ModItems.APPLE_CREAM_CAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.BERRY_CREAM_CAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHORUS_FRUIT_CREAM_CAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CREAM_CAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHOCOLATE_CREAM_CAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.GLOW_BERRY_CREAM_CAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.UBE_CREAM_UBE_CAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.APPLE_CHEESECAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHEESECAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHORUS_FRUIT_CHEESECAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.GLOW_BERRY_CHEESECAKE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.BERRY_PIE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHORUS_FRUIT_PIE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.GLOW_BERRY_PIE_SLICE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.WHITE_CHOCOLATE_CHIP_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.WHITE_CHOCOLATE_CHIP_CHOCOLATE_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.TOFFEE_CHIP_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.TOFFEE_CHIP_CHOCOLATE_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.DARK_CHOCOLATE_CHIP_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.DARK_CHOCOLATE_CHIP_CHOCOLATE_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHOCOLATE_CHIP_CHOCOLATE_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CARAMEL_CHIP_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CARAMEL_CHIP_CHOCOLATE_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.BUTTERSCOTCH_CHIP_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.BUTTERSCOTCH_CHIP_CHOCOLATE_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHORUS_FRUIT_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.GLOW_BERRY_COOKIE.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModItems.GRAHAM_CRACKER_PIE_CRUST.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModItems.SLICED_BEETROOT.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.SLICED_BROWN_MUSHROOM.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.SLICED_CARROT.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.SLICED_ONION.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.SLICED_POTATO.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.SLICED_RED_MUSHROOM.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.SLICED_TOMATO.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHORUS_FRUIT_SLICE.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.APPLE_SLICE.get(), 0.50f);
            ComposterBlock.COMPOSTABLES.put(ModItems.MINI_CHOCOLATE_GRAHAM_CRACKER_PIE_CRUST.get(), 0.30f);
            ComposterBlock.COMPOSTABLES.put(ModItems.MINI_GRAHAM_CRACKER_PIE_CRUST.get(), 0.30f);
        }));
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }

    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.GENERIC_DISPLAY_PLATE.get(), GenericDisplayPlateRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.CLOTH_SACK.get(), ClothSackRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                MenuScreens.register(ModMenus.CLOTH_SACK.get(), ClothSackScreen::new);
                MenuScreens.register(ModMenus.CLOTH_SACK_ITEM.get(), ClothSackItemScreen::new);
                MenuScreens.register(ModMenus.RATION_BOX.get(), RationBoxScreen::new);
                MenuScreens.register(ModMenus.RATION_BOX_ITEM.get(), RationBoxItemScreen::new);

                // Translucent fluid render layers
                setTranslucent(ModFluids.APPLE_JUICE_FROSTING);
                setTranslucent(ModFluids.BERRY_JUICE_FROSTING);
                setTranslucent(ModFluids.BLACK_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.BLUE_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.BROWN_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.CHORUS_FRUIT_JUICE_FROSTING);
                setTranslucent(ModFluids.CYAN_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.GLOW_BERRY_JUICE_FROSTING);
                setTranslucent(ModFluids.GRAY_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.GREEN_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.LIGHT_BLUE_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.LIGHT_GRAY_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.LIME_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.MAGENTA_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.ORANGE_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.PINK_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.PURPLE_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.RED_GELATIN_MIX_FROSTING);
                setTranslucent(ModFluids.SQUID_INK_FROSTING);
                setTranslucent(ModFluids.VEGETABLE_OIL_FROSTING);
                setTranslucent(ModFluids.VINEGAR_FROSTING);
                setTranslucent(ModFluids.YELLOW_GELATIN_MIX_FROSTING);
            });
        }

        @SubscribeEvent
        public static void onRegisterTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(StorageContentsTooltip.class, StorageContentsTooltipRenderer::new);
        }

        private static void setTranslucent(FluidEntry<? extends ForgeFlowingFluid> entry) {
            ItemBlockRenderTypes.setRenderLayer(entry.getSource(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(entry.get(), RenderType.translucent());
        }
    }
}
