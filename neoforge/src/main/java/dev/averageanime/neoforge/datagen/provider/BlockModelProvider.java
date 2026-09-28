package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.createfood.lib.datagen.DatagenHelpers;
import dev.averageanime.createfood.lib.fluid.FluidBlock;
import dev.averageanime.createfood.lib.fluid.FluidEntry;
import java.util.Map;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockModelProvider extends net.neoforged.neoforge.client.model.generators.BlockModelProvider {

    public BlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateFoodCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerFluidBlockModels();
        registerSharedDisplayModels();
    }

    private void registerFluidBlockModels() {
        for (FluidBlock.FluidType fluidType : FluidRegistration.BY_ID.values()) {
            String fluidId = fluidType.SOURCE.getId().getPath();
            DatagenHelpers.insetBlockModel(this, CreateFoodCommon.MOD_ID, fluidId);
        }
    }

    private static final Map<String, String> SHARED_DISPLAY_MODELS = Map.ofEntries(
            Map.entry("bacon_calzone_plate_block_1",    "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("bacon_calzone_plate_block_2",    "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("beef_calzone_plate_block_1",     "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("beef_calzone_plate_block_2",     "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("chicken_calzone_plate_block_1",  "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("chicken_calzone_plate_block_2",  "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("fish_calzone_plate_block_1",     "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("fish_calzone_plate_block_2",     "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("mushroom_calzone_plate_block_1", "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("mushroom_calzone_plate_block_2", "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("onion_calzone_plate_block_1",    "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("onion_calzone_plate_block_2",    "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("sausage_calzone_plate_block_1",  "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("sausage_calzone_plate_block_2",  "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("rabbit_calzone_plate_block_1",  "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("rabbit_calzone_plate_block_2",  "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("mutton_calzone_plate_block_1",  "createfood:block/cheese_calzone_plate_block_1"),
            Map.entry("mutton_calzone_plate_block_2",  "createfood:block/cheese_calzone_plate_block_2"),
            Map.entry("bacon_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("beef_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("chicken_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("fish_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("mushroom_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("onion_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("sausage_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("rabbit_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("mutton_calzone_small_plate_block", "createfood:block/cheese_calzone_small_plate_block"),
            Map.entry("berry_jam_donut_small_plate_block", "createfood:block/apple_jam_donut_small_plate_block"),
            Map.entry("chorus_fruit_jam_donut_small_plate_block", "createfood:block/apple_jam_donut_small_plate_block"),
            Map.entry("glow_berry_jam_donut_small_plate_block", "createfood:block/apple_jam_donut_small_plate_block"),
            Map.entry("melon_jam_donut_small_plate_block", "createfood:block/apple_jam_donut_small_plate_block"),
            Map.entry("berry_jam_chocolate_donut_small_plate_block", "createfood:block/apple_jam_chocolate_donut_small_plate_block"),
            Map.entry("chorus_fruit_jam_chocolate_donut_small_plate_block", "createfood:block/apple_jam_chocolate_donut_small_plate_block"),
            Map.entry("glow_berry_jam_chocolate_donut_small_plate_block", "createfood:block/apple_jam_chocolate_donut_small_plate_block"),
            Map.entry("melon_jam_chocolate_donut_small_plate_block", "createfood:block/apple_jam_chocolate_donut_small_plate_block")
    );

    private void registerSharedDisplayModels() {
        SHARED_DISPLAY_MODELS.forEach((blockModelId, parentId) ->
                withExistingParent("block/" + blockModelId,
                        ResourceLocation.parse(parentId)));
    }

    private ResourceLocation cf(String path) {
        return ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, path);
    }

}