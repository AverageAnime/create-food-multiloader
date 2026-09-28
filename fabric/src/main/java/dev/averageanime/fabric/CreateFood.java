package dev.averageanime.fabric;

import dev.averageanime.platform.Services;
import dev.averageanime.createfood.lib.effect.EffectChain;
import dev.averageanime.createfood.lib.pack.FabricBuiltinPacks;
import dev.averageanime.pack.BuiltInPacks;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.fabric.block.BlockRegistration;
import dev.averageanime.fabric.block.DisplayBlockRegistration;
import dev.averageanime.fabric.block.type.pie.PumpkinPieEvents;
import dev.averageanime.fabric.block.BlockEntityRegistration;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.fabric.config.ConfigEvents;
import dev.averageanime.createfood.lib.config.RecipeConditions;
import dev.averageanime.createfood.lib.recipe.RecipeSubjects;
import dev.averageanime.fabric.config.ConfigRegistration;
import dev.averageanime.fabric.block.FluidRegistration;
import dev.averageanime.fabric.item.ItemRegistration;
import dev.averageanime.fabric.item.interaction.CampfireCookingInteraction;
import dev.averageanime.fabric.item.interaction.ClothFilterInteraction;
import dev.averageanime.fabric.block.type.bowl.BowlPlacementEvents;
import dev.averageanime.fabric.block.type.display.FoodConversionEvents;
import dev.averageanime.fabric.block.type.plate.PlateSliceEvents;
import dev.averageanime.fabric.item.interaction.HandcraftInteraction;
import dev.averageanime.fabric.menu.MenuRegistration;
import dev.averageanime.fabric.tab.TabRegistration;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateFood implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(CreateFoodCommon.MOD_ID);

    @Override
    public void onInitialize() {
        // Wire the shared effect chain before anything resolves.
        EffectChain.modLoadedCheck(Services.PLATFORM::isModLoaded);

        // Before the pack repository is built, which is after mod init.
        FabricBuiltinPacks.register(CreateFoodCommon.MOD_ID, BuiltInPacks.NAMES);

        ConfigRegistry.registerConfig(CreateFoodCommon.MOD_ID,
                io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.CLIENT,
                ConfigRegistration.CLIENT_SPEC);
        ConfigRegistry.registerConfig(CreateFoodCommon.MOD_ID,
                io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.COMMON,
                ConfigRegistration.COMMON_SPEC);
        ConfigRegistry.registerConfig(CreateFoodCommon.MOD_ID,
                io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.SERVER,
                ConfigRegistration.SERVER_SPEC);
        ConfigEvents.register();

        // Read lazily: recipes are not read until datapack load.
        RecipeSubjects.gate(ConfigValues::isRecipeSubjectAvailable);
        RecipeConditions.register(CreateFoodCommon.MOD_ID);
        ItemRegistration.registerItemRegistration();
        BlockRegistration.init();
        PumpkinPieEvents.register();
        FluidRegistration.init();
        MenuRegistration.init();
        // Must precede BlockEntityRegistration.
        DisplayBlockRegistration.init();
        BlockEntityRegistration.init();

        HandcraftInteraction.register();
        CampfireCookingInteraction.register();
        ClothFilterInteraction.register();
        BowlPlacementEvents.register();
        FoodConversionEvents.register();
        PlateSliceEvents.register();

        TabRegistration.init();
    }
}
