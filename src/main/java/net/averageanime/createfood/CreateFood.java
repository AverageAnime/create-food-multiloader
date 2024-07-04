package net.averageanime.createfood;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
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

        ModFluids.register();
        ModItems.register();

        FMLModContainer container = (FMLModContainer) ModLoadingContext.get().getActiveContainer();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeTab.register(modBus);
        REGISTRATE.registerEventListeners(modBus);

    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }

}