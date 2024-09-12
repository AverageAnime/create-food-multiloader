package net.averageanime.createfood;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.averageanime.createfood.block.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.averageanime.createfood.creativetab.ModCreativeTab;
import net.averageanime.createfood.fluid.ModFluids;
import net.averageanime.createfood.item.ModItems;

@Mod(CreateFood.ID)
public class CreateFood {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "createfood";
    public static final String NAME = "Create: Food";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);
    
    public CreateFood() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register();
        ModFluids.register();
        ModItems.register();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeTab.register(modEventBus);
        REGISTRATE.registerEventListeners(modBus);

    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }

}