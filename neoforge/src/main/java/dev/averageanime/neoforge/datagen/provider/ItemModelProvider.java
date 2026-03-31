package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.type.display.*;
import dev.averageanime.neoforge.block.type.display.plate.PlateBlock;
import dev.averageanime.neoforge.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;
import java.util.Set;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {

    /**
     * Block item IDs whose inventory model should parent the block model
     * rather than using {@code item/generated}.
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
     * Items whose layer0 texture is a shared sprite rather than {@code item/{id}}.
     *
     * <p>Key = item registry ID, value = full resource location string for layer0.
     * Add entries here when a new item reuses an existing texture rather than
     * having its own dedicated PNG.
     */
    /**
     * Items whose layer0 texture is a shared sprite rather than {@code item/{id}}.
     * Key = item registry ID, value = full resource location string for layer0.
     */
    private static final Map<String, String> ALL_TEXTURE_OVERRIDES = Map.ofEntries(
            Map.entry("bacon_calzone",    "createfood:item/calzone"),
            Map.entry("cheese_calzone",   "createfood:item/calzone"),
            Map.entry("fish_calzone",     "createfood:item/calzone"),
            Map.entry("mushroom_calzone", "createfood:item/calzone"),
            Map.entry("onion_calzone",    "createfood:item/calzone"),
            Map.entry("sausage_calzone",  "createfood:item/calzone"),
            Map.entry("raw_bacon_calzone",    "createfood:item/raw_calzone"),
            Map.entry("raw_cheese_calzone",   "createfood:item/raw_calzone"),
            Map.entry("raw_fish_calzone",     "createfood:item/raw_calzone"),
            Map.entry("raw_mushroom_calzone", "createfood:item/raw_calzone"),
            Map.entry("raw_onion_calzone",    "createfood:item/raw_calzone"),
            Map.entry("raw_sausage_calzone",  "createfood:item/raw_calzone"),
            Map.entry("chocolate_sweet_dough", "createfood:item/chocolate_sugar_dough"),
            Map.entry("corn_stick",            "createfood:item/smoked_corn_stick"),
            Map.entry("pumpkin_pie_block", "minecraft:item/pumpkin_pie"),
            Map.entry("plate_block",       "minecraft:item/bowl"),
            Map.entry("small_plate_block", "minecraft:item/bowl")
    );

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CommonClass.ID, existingFileHelper);
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

    /** Display block: item model parents the block's placed model directly. */
    private void simpleBlockItem(DeferredBlock<Block> block) {
        String name = block.getId().getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name)));
    }

    /** Display plate block: item model parents the full-stack plate model. */
    private void plateBlockItem(DeferredBlock<Block> block, int maxStack) {
        String name = block.getId().getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name + "_" + maxStack)));
    }

    /**
     * Standard flat-sprite item model.
     *
     * <p>If {@code id} has an entry in {@link #ALL_TEXTURE_OVERRIDES}, that texture
     * is used for layer0 instead of the default {@code createfood:item/{id}}.
     */
    private void generatedItem(String id) {
        String layer0 = ALL_TEXTURE_OVERRIDES.getOrDefault(id,
                ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "item/" + id).toString());
        withExistingParent(id, "item/generated")
                .texture("layer0", ResourceLocation.parse(layer0));
    }

    /** Block item whose inventory model parents the block model geometry. */
    private void blockParentItem(String id) {
        getBuilder(id).parent(new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + id)));
    }
}