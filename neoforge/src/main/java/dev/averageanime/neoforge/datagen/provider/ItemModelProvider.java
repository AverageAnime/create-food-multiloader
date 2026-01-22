package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.type.display.*;
import dev.averageanime.neoforge.block.type.display.plate.PlateBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CommonClass.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModDisplayBlocks.BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();

            if (block instanceof PlateBlock plateBlock) {
                plateBlockItem((DeferredBlock<Block>) blockEntry, plateBlock.maxStackSize);
            } else if (block instanceof BottleFoodBlock ||
                    block instanceof BowlFoodBlock ||
                    block instanceof SaladBowlFoodBlock ||
                    block instanceof SmallPlateFoodBlock ||
                    block instanceof PlateFoodBlock) {
                simpleBlockItem((DeferredBlock<Block>) blockEntry);
            }
        });
    }

    /**
     * Creates a simple block item that references its block model
     */
    private void simpleBlockItem(DeferredBlock<Block> block) {
        String name = block.getId().getPath();
        getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile(
                        ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name)));
    }

    /**
     * Creates a plate block item with stack size variant
     */
    private void plateBlockItem(DeferredBlock<Block> block, int maxStack) {
        String name = block.getId().getPath();
        getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile(
                        ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name + "_" + maxStack)));
    }
}