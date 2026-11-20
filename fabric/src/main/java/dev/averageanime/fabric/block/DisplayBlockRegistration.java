package dev.averageanime.fabric.block;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.bowl.EmptyBowlBlock;
import dev.averageanime.block.type.bowl.EmptyLargeBowlBlock;
import dev.averageanime.block.type.bowl.GenericDisplayBowlBlock;
import dev.averageanime.block.type.display.DisplayBlockItem;
import dev.averageanime.block.type.display.DisplayBlockSpecs;
import dev.averageanime.block.type.display.DisplayBlocks;
import dev.averageanime.block.type.display.EmptyBottleBlock;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.plate.CompatPlateBlocks;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.EmptySmallPlateBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.block.type.misc.FoodPlacementHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;


public class DisplayBlockRegistration {

    private static final List<Block> REGISTERED_DISPLAY_BLOCKS = new ArrayList<>();

    public static List<Block> getRegisteredDisplayBlocks() {
        return REGISTERED_DISPLAY_BLOCKS;
    }

    public static Block SMALL_PLATE_BLOCK;
    public static Block PLATE_BLOCK;
    public static Block BOWL_BLOCK;
    public static Block LARGE_BOWL_BLOCK;
    public static Block BOTTLE_BLOCK;
    public static Block GENERIC_DISPLAY_PLATE_BLOCK;
    public static Block GENERIC_DISPLAY_BOWL_BLOCK;

    public static void init() {
        CreateFood.LOGGER.info("Create: Food - Registering Display Blocks");

        SMALL_PLATE_BLOCK = regBlock("small_plate_block",
                new EmptySmallPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> BOWL_BLOCK));
        PLATE_BLOCK = regBlock("plate_block",
                new EmptyPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),
                        () -> SMALL_PLATE_BLOCK));
        BOWL_BLOCK = regBlock("bowl_block",
                new EmptyBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),
                        () -> LARGE_BOWL_BLOCK));
        LARGE_BOWL_BLOCK = regBlock("large_bowl_block",
                new EmptyLargeBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),
                        () -> PLATE_BLOCK));

        BOTTLE_BLOCK = regBlock("bottle_block",
                new EmptyBottleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

        GENERIC_DISPLAY_PLATE_BLOCK = regBlock("generic_display_plate_block",
                new GenericDisplayPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> PLATE_BLOCK));
        GENERIC_DISPLAY_BOWL_BLOCK = regBlock("generic_display_bowl_block",
                new GenericDisplayBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> BOWL_BLOCK));

        DisplayBlockSpecs.auto().forEach(DisplayBlockRegistration::registerDisplayBlock);
        DisplayBlockSpecs.custom().forEach(DisplayBlockRegistration::registerDisplayBlock);
        registerBlockItems();
        CompatPlateBlocks.registerCompatiblePlates();
        FoodPlacementHandler.register();
    }

    private static void registerDisplayBlock(DisplayBlockSpecs.Spec spec) {
        Block block = DisplayBlocks.createBlock(spec.food(), spec.config());
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, spec.blockName());
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        REGISTERED_DISPLAY_BLOCKS.add(block);

        Registry.register(BuiltInRegistries.ITEM, id,
                new DisplayBlockItem(block, spec.food(), DisplayBlocks.suffixKey(spec.config().type())));
    }

    private static void registerBlockItems() {
        for (Block block : REGISTERED_DISPLAY_BLOCKS) {
            if (block instanceof FoodBlock displayableBlock) {
                Item displayItem = displayableBlock.displayItem.get();
                FoodBlock.Registry.register(() -> displayItem, () -> block);
            }
        }
    }

    private static Block regBlock(String id, Block block) {
        Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, id), block);
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, id),
                new BlockItem(block, new Item.Properties()));
        return block;
    }

}
