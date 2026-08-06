package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.bowl.BowlFoodBlock;
import dev.averageanime.block.type.bowl.SmallBowlFoodBlock;
import dev.averageanime.block.type.plate.PlateFoodBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.neoforge.block.DisplayBlockRegistration;
import dev.averageanime.block.type.display.*;
import dev.averageanime.block.type.bowl.BowlBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.neoforge.item.ItemRegistration;
import java.util.Map;
import java.util.Set;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {

    private static final Set<String> BLOCK_MODEL_ITEMS = Set.of(
            "black_gelatin_dessert_block",
            "blue_gelatin_dessert_block",
            "brown_gelatin_dessert_block",
            "cyan_gelatin_dessert_block",
            "gelatin_dessert_block",
            "gray_gelatin_dessert_block",
            "green_gelatin_dessert_block",
            "light_blue_gelatin_dessert_block",
            "light_gray_gelatin_dessert_block",
            "lime_gelatin_dessert_block",
            "magenta_gelatin_dessert_block",
            "orange_gelatin_dessert_block",
            "pink_gelatin_dessert_block",
            "purple_gelatin_dessert_block",
            "red_gelatin_dessert_block",
            "yellow_gelatin_dessert_block"
    );

    private static final Map<String, String> ALL_TEXTURE_OVERRIDES = Map.ofEntries(
            Map.entry("bacon_calzone",    "createfood:item/calzone"),
            Map.entry("beef_calzone",     "createfood:item/calzone"),
            Map.entry("cheese_calzone",   "createfood:item/calzone"),
            Map.entry("cheese_potato_dumplings",  "farmersdelight:item/dumplings"),
            Map.entry("chicken_calzone",  "createfood:item/calzone"),
            Map.entry("chocolate_sweet_dough", "createfood:item/chocolate_sugar_dough"),
            Map.entry("egg_dumplings",  "farmersdelight:item/dumplings"),
            Map.entry("fish_calzone",     "createfood:item/calzone"),
            Map.entry("mushroom_calzone", "createfood:item/calzone"),
            Map.entry("mutton_calzone",  "createfood:item/calzone"),
            Map.entry("onion_calzone",    "createfood:item/calzone"),
            Map.entry("plate_block",       "minecraft:item/bowl"),
            Map.entry("pumpkin_pie_block", "minecraft:item/pumpkin_pie"),
            Map.entry("rabbit_calzone",  "createfood:item/calzone"),
            Map.entry("raw_bacon_calzone",    "createfood:item/raw_calzone"),
            Map.entry("raw_beef_calzone",     "createfood:item/raw_calzone"),
            Map.entry("raw_cheese_calzone",   "createfood:item/raw_calzone"),
            Map.entry("raw_chicken_calzone",  "createfood:item/raw_calzone"),
            Map.entry("raw_fish_calzone",     "createfood:item/raw_calzone"),
            Map.entry("raw_mushroom_calzone", "createfood:item/raw_calzone"),
            Map.entry("raw_mutton_calzone",  "createfood:item/raw_calzone"),
            Map.entry("raw_onion_calzone",    "createfood:item/raw_calzone"),
            Map.entry("raw_rabbit_calzone",  "createfood:item/raw_calzone"),
            Map.entry("raw_sausage_calzone",  "createfood:item/raw_calzone"),
            Map.entry("sausage_calzone",  "createfood:item/calzone"),
            Map.entry("small_plate_block", "minecraft:item/bowl")
    );

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateFoodCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        DisplayBlockRegistration.BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof PlateBlock plateBlock) {
                plateBlockItem((DeferredBlock<Block>) blockEntry, plateBlock.maxStackSize);
            } else if (block instanceof BowlBlock bowlBlock) {
                plateBlockItem((DeferredBlock<Block>) blockEntry, bowlBlock.maxStackSize);
            } else if (block instanceof BottleFoodBlock
                    || block instanceof BowlFoodBlock
                    || block instanceof SmallBowlFoodBlock
                    || block instanceof SmallPlateBlock
                    || block instanceof PlateFoodBlock) {
                simpleBlockItem((DeferredBlock<Block>) blockEntry);
            }
        });

        var displayBlockIds = DisplayBlockRegistration.BLOCKS.getEntries().stream()
                .map(e -> e.getId().getPath())
                .collect(java.util.stream.Collectors.toSet());

        ItemRegistration.ITEMS.getEntries().forEach(holder -> {
            String id = holder.getId().getPath();
            if (displayBlockIds.contains(id)) return; // handled above
            if (BLOCK_MODEL_ITEMS.contains(id)) {
                blockParentItem(id);
            } else {
                generatedItem(id);
            }
        });
    }

    private void simpleBlockItem(DeferredBlock<Block> block) {
        String name = block.getId().getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "block/" + name)));
    }

    private void plateBlockItem(DeferredBlock<Block> block, int maxStack) {
        String name = block.getId().getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "block/" + name + "_" + maxStack)));
    }

    private void generatedItem(String id) {
        String layer0 = ALL_TEXTURE_OVERRIDES.getOrDefault(id,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "item/" + id).toString());
        withExistingParent(id, "item/generated")
                .texture("layer0", ResourceLocation.parse(layer0));
    }

    private void blockParentItem(String id) {
        getBuilder(id).parent(new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "block/" + id)));
    }
}