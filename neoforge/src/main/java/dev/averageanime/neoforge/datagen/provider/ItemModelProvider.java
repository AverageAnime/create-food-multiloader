package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.type.display.*;
import dev.averageanime.neoforge.block.type.plate.PlateBlock;
import dev.averageanime.neoforge.item.ModItems;
import java.util.Map;
import java.util.Set;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {

    /**
     * Block item IDs whose inventory model should parent the block model.
     */
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

    /**
     * Items whose layer0 texture is a shared sprite.
     */
    private static final Map<String, String> ALL_TEXTURE_OVERRIDES = Map.ofEntries(
            Map.entry("bacon_calzone",    "createfood:item/calzone"),
            Map.entry("beef_calzone",     "createfood:item/calzone"),
            Map.entry("cheese_calzone",   "createfood:item/calzone"),
            Map.entry("cheese_potato_dumplings",  "farmersdelight:item/dumplings"),
            Map.entry("chicken_calzone",  "createfood:item/calzone"),
            Map.entry("chocolate_sweet_dough", "createfood:item/chocolate_sugar_dough"),
            Map.entry("corn_stick",            "createfood:item/smoked_corn_stick"),
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
        super(output, CommonClass.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModDisplayBlocks.BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof PlateBlock plateBlock) {
                plateBlockItem((DeferredBlock<Block>) blockEntry, plateBlock.maxStackSize);
            } else if (block instanceof BottleFoodBlock
                    || block instanceof BowlFoodBlock
                    || block instanceof SaladBowlFoodBlock
                    || block instanceof SmallPlateFoodBlock
                    || block instanceof PlateFoodBlock) {
                simpleBlockItem((DeferredBlock<Block>) blockEntry);
            }
        });

        var displayBlockIds = ModDisplayBlocks.BLOCKS.getEntries().stream()
                .map(e -> e.getId().getPath())
                .collect(java.util.stream.Collectors.toSet());

        ModItems.ITEMS.getEntries().forEach(holder -> {
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
                ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, "block/" + name)));
    }

    private void plateBlockItem(DeferredBlock<Block> block, int maxStack) {
        String name = block.getId().getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, "block/" + name + "_" + maxStack)));
    }

    private void generatedItem(String id) {
        String layer0 = ALL_TEXTURE_OVERRIDES.getOrDefault(id,
                ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, "item/" + id).toString());
        withExistingParent(id, "item/generated")
                .texture("layer0", ResourceLocation.parse(layer0));
    }

    private void blockParentItem(String id) {
        getBuilder(id).parent(new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, "block/" + id)));
    }
}