package dev.averageanime.fabric.block;


import dev.averageanime.fabric.CreateFood;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static dev.averageanime.fabric.CreateFood.MOD_ID;

public class ModBlocks {

    public static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                block);
    }

    private static void registerBlockItem (String name, Block block){
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new BlockItem(block, new Item.Properties()));
    }

    public static void registerModBlocks () {
        CreateFood.LOGGER.info("Registering Blocks for " + CreateFood.MOD_ID);
    }


}
