package dev.averageanime.fabric;

import dev.averageanime.fabric.block.ModBlocks;
import dev.averageanime.fabric.block.ModDisplayBlocks;
import dev.averageanime.fabric.block.type.pie.PumpkinPieBlock;
import dev.averageanime.fabric.block.type.blockentity.ModBlockEntities;
import dev.averageanime.fabric.config.ModConditions;
import dev.averageanime.fabric.config.ModConfig;
import dev.averageanime.fabric.block.ModFluids;
import dev.averageanime.fabric.item.ModItems;
import dev.averageanime.fabric.item.interaction.ClothFilterInteraction;
import dev.averageanime.fabric.block.handler.BowlPlacementHandler;
import dev.averageanime.fabric.item.interaction.HandcraftInteraction;
import dev.averageanime.fabric.menu.ModMenus;
import dev.averageanime.fabric.tab.ModTabs;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateFood implements ModInitializer {
	public static final String MOD_ID = "createfood";
	public static final Logger LOGGER = LoggerFactory.getLogger("createfood");

	@Override
	public void onInitialize() {
		ConfigRegistry.registerConfig(MOD_ID,
				io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.CLIENT,
				ModConfig.CLIENT_SPEC);
		ConfigRegistry.registerConfig(MOD_ID,
				io.github.fabricators_of_create.porting_lib.config.ModConfig.Type.SERVER,
				ModConfig.SERVER_SPEC);

		ModConditions.register();
		ModBlocks.init();
		PumpkinPieBlock.register();
		ModFluids.init();
		ModItems.registerModItems();
		ModBlockEntities.init();
		ModMenus.init();
		ModDisplayBlocks.init();

		HandcraftInteraction.register();
		ClothFilterInteraction.register();
		BowlPlacementHandler.register();

		ModTabs.init();
	}
}