package dev.averageanime.neoforge.block;

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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ItemRegistration.ITEMS;

public class DisplayBlockRegistration {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateFoodCommon.MOD_ID);

    public static final DeferredBlock<Block> SMALL_PLATE_BLOCK = BLOCKS.register("small_plate_block",
            () -> new EmptySmallPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.BOWL_BLOCK));

    public static final DeferredBlock<Block> PLATE_BLOCK = BLOCKS.register("plate_block",
            () -> new EmptyPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.SMALL_PLATE_BLOCK));

    public static final DeferredBlock<Block> BOWL_BLOCK = BLOCKS.register("bowl_block",
            () -> new EmptyBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.LARGE_BOWL_BLOCK));

    public static final DeferredBlock<Block> LARGE_BOWL_BLOCK = BLOCKS.register("large_bowl_block",
            () -> new EmptyLargeBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.PLATE_BLOCK));

    public static final DeferredBlock<Block> BOTTLE_BLOCK = BLOCKS.register("bottle_block",
            () -> new EmptyBottleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

    public static final DeferredBlock<Block> GENERIC_DISPLAY_PLATE_BLOCK = BLOCKS.register("generic_display_plate_block",
            () -> new GenericDisplayPlateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.PLATE_BLOCK));

    public static final DeferredBlock<Block> GENERIC_DISPLAY_BOWL_BLOCK = BLOCKS.register("generic_display_bowl_block",
            () -> new GenericDisplayBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), DisplayBlockRegistration.BOWL_BLOCK));

    private static void registerDisplayBlock(DisplayBlockSpecs.Spec spec) {
        String suffixKey = DisplayBlocks.suffixKey(spec.config().type());
        DeferredBlock<Block> block = BLOCKS.register(spec.blockName(),
                () -> DisplayBlocks.createBlock(spec.food(), spec.config()));
        ITEMS.register(spec.blockName(), () -> new DisplayBlockItem(block.get(), spec.food(), suffixKey));
    }

    public static void registerBlockItems() {
        BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof FoodBlock displayableBlock) {
                Item displayItem = displayableBlock.displayItem.get();
                FoodBlock.Registry.register(() -> displayItem, (Supplier<Block>) blockEntry);
            }
        });
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Display Blocks");
        DisplayBlockSpecs.auto().forEach(DisplayBlockRegistration::registerDisplayBlock);
        DisplayBlockSpecs.custom().forEach(DisplayBlockRegistration::registerDisplayBlock);
        ITEMS.register("generic_display_plate_block", () ->
                new BlockItem(GENERIC_DISPLAY_PLATE_BLOCK.get(), new Item.Properties()));
        ITEMS.register("generic_display_bowl_block", () ->
                new BlockItem(GENERIC_DISPLAY_BOWL_BLOCK.get(), new Item.Properties()));

        BLOCKS.register(eventBus);

        eventBus.addListener((net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) -> event.enqueueWork(() -> {
            registerBlockItems();
            CompatPlateBlocks.registerCompatiblePlates();
        }));
    }
}
