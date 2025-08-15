package dev.averageanime.fabric;

import dev.averageanime.fabric.block.ModBlocks;
import dev.averageanime.fabric.fluid.ModFluids;
import dev.averageanime.fabric.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CreateFood implements ModInitializer {
	public static final String MOD_ID = "createfood";
	public static final Logger LOGGER = LoggerFactory.getLogger("createfood");

	public static final ResourceLocation ITEM_GROUP_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID, "tab");

	@Override
	public void onInitialize() {

			Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP_ID, FabricItemGroup.builder()
					.title(Component.translatable("itemgroup.createfood"))
					.icon(() -> new ItemStack(ModItems.APPLE_CREAM_CHOCOLATE))
					.displayItems((itemDisplayParameters, output) -> {
						output.accept(new ItemStack(ModItems.APPLE_CHEESECAKE));
						output.accept(new ItemStack(ModItems.APPLE_CHEESECAKE_SLICE));
						output.accept(new ItemStack(ModItems.APPLE_CREAM_CHOCOLATE));
						output.accept(new ItemStack(ModItems.APPLE_CREAM_FROSTING_BUCKET));
						output.accept(new ItemStack(ModItems.CHOCOLATE_CREAM_CAKE_CARAMEL));
						output.accept(new ItemStack(ModItems.CHOCOLATE_CREAM_CAKE_SLICE_CARAMEL));
						output.accept(new ItemStack(ModItems.GYRO_MEAT_BLOCK));
						output.accept(new ItemStack(ModItems.GYRO_MEAT_SLICE));
						output.accept(new ItemStack(ModItems.YOGURT_BOTTLE));
					})
					.build());

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();



	}


}

