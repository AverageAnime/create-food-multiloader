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
import dev.averageanime.createfood.lib.recipe.adapted.AdaptedRecipes;
import dev.averageanime.fabric.config.ConfigRegistration;
import dev.averageanime.fabric.block.FluidRegistration;
import dev.averageanime.fabric.item.ItemRegistration;
import dev.averageanime.fabric.item.interaction.CampfireCookingEvents;
import dev.averageanime.fabric.item.interaction.ClothFilterEvents;
import dev.averageanime.fabric.block.type.bowl.BowlPlacementEvents;
import dev.averageanime.fabric.block.type.display.FoodConversionEvents;
import dev.averageanime.fabric.block.type.plate.PlateSliceEvents;
import dev.averageanime.fabric.item.interaction.HandcraftEvents;
import dev.averageanime.fabric.menu.MenuRegistration;
import dev.averageanime.fabric.tab.TabRegistration;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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
        // Copies Create/FD/Ube's Delight recipes for the plate and bowl interactions when those mods are absent.
        AdaptedRecipes.register(CreateFoodCommon.MOD_ID);
        ItemRegistration.init();
        // NeoForge reads the same fuel from data/neoforge/data_maps/item/furnace_fuels.json; keep the two in step.
        FuelRegistry.INSTANCE.add(BuiltInRegistries.ITEM.get(
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "tallow")), 800);
        BlockRegistration.init();
        PumpkinPieEvents.register();
        FluidRegistration.init();
        MenuRegistration.init();
        // Must precede BlockEntityRegistration.
        DisplayBlockRegistration.init();
        BlockEntityRegistration.init();

        HandcraftEvents.register();
        CampfireCookingEvents.register();
        ClothFilterEvents.register();
        BowlPlacementEvents.register();
        FoodConversionEvents.register();
        PlateSliceEvents.register();

        TabRegistration.init();
    }
}
