package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.neoforge.block.type.fluid.FluidBlock;
import dev.averageanime.registry.type.FluidEntry;
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
            fluidBlockModel(fluidId);
        }
    }

    private void fluidBlockModel(String fluidId) {
        ResourceLocation texture = cf("fluid/" + FluidEntry.textureFor(fluidId) + "_flow");
        String blockModelName = fluidId + "_block";
        withExistingParent("block/" + blockModelName, mcLoc("block/block"))
                .texture("particle", texture)
                .texture("down",     texture)
                .texture("up",       texture)
                .texture("side",     texture)
                .element()
                .from(1, 1, 1).to(15, 15, 15)
                .face(net.minecraft.core.Direction.DOWN).uvs(1, 1, 15, 15).texture("#down").end()
                .face(net.minecraft.core.Direction.UP).uvs(1, 1, 15, 15).texture("#up").end()
                .face(net.minecraft.core.Direction.NORTH).uvs(1, 1, 15, 15).texture("#side").end()
                .face(net.minecraft.core.Direction.SOUTH).uvs(1, 1, 15, 15).texture("#side").end()
                .face(net.minecraft.core.Direction.WEST).uvs(1, 1, 15, 15).texture("#side").end()
                .face(net.minecraft.core.Direction.EAST).uvs(1, 1, 15, 15).texture("#side").end()
                .end()
                .element()
                .from(0, 0, 0).to(16, 16, 16)
                .face(net.minecraft.core.Direction.DOWN).texture("#down").cullface(net.minecraft.core.Direction.DOWN).end()
                .face(net.minecraft.core.Direction.UP).texture("#down").cullface(net.minecraft.core.Direction.UP).end()
                .face(net.minecraft.core.Direction.NORTH).texture("#down").cullface(net.minecraft.core.Direction.NORTH).end()
                .face(net.minecraft.core.Direction.SOUTH).texture("#down").cullface(net.minecraft.core.Direction.SOUTH).end()
                .face(net.minecraft.core.Direction.WEST).texture("#down").cullface(net.minecraft.core.Direction.WEST).end()
                .face(net.minecraft.core.Direction.EAST).texture("#down").cullface(net.minecraft.core.Direction.EAST).end()
                .end();
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
            Map.entry("mutton_calzone_plate_block_2",  "createfood:block/cheese_calzone_plate_block_2")
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