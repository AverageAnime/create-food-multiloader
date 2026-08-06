package dev.averageanime.neoforge.datagen.provider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.bowl.BowlFoodBlock;
import dev.averageanime.block.type.bowl.SmallBowlFoodBlock;
import dev.averageanime.block.type.plate.PlateFoodBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.neoforge.block.BlockRegistration;
import dev.averageanime.neoforge.block.DisplayBlockRegistration;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.block.type.cake.CakeFoodBlock;
import dev.averageanime.block.type.cake.CakeCandleBlock;
import dev.averageanime.block.type.display.*;
import dev.averageanime.block.type.bowl.BowlBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.neoforge.block.type.fluid.FluidBlock;
import dev.averageanime.block.type.pie.PieBlock;
import dev.averageanime.block.type.pie.PizzaBlock;
import dev.averageanime.block.type.pie.RawPieBlock;
import dev.averageanime.block.type.storage.ClothSackBlock;
import dev.averageanime.block.type.storage.RationBoxBlock;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class BlockStateProvider extends net.neoforged.neoforge.client.model.generators.BlockStateProvider {

    private final Path resourceRoot;

    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CreateFoodCommon.MOD_ID, exFileHelper);
        this.resourceRoot = output.getOutputFolder().resolve("../../../../common/src/main/resources").normalize();
    }

    @Override
    protected void registerStatesAndModels() {
        registerDisplayBlockStates();
        registerFoodBlockStates();
        registerFluidBlockStates();
    }

    private void registerDisplayBlockStates() {
        DisplayBlockRegistration.BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            switch (block) {
                case BottleFoodBlock bottleFoodBlock -> bottleBlock((DeferredBlock<Block>) blockEntry);
                case BowlFoodBlock bowlFoodBlock -> bowlBlock((DeferredBlock<Block>) blockEntry);
                case SmallBowlFoodBlock smallBowlFoodBlock -> bowlBlock((DeferredBlock<Block>) blockEntry);
                case PlateFoodBlock plateFoodBlock -> bowlBlock((DeferredBlock<Block>) blockEntry);
                case SmallPlateBlock smallPlateBlock -> smallPlateBlock((DeferredBlock<Block>) blockEntry);
                case BowlBlock bowlBlock -> displayBowlBlock((DeferredBlock<Block>) blockEntry);
                case PlateBlock plateBlock -> plateBlock((DeferredBlock<Block>) blockEntry);
                default -> {
                }
            }
        });
    }

    private void bottleBlock(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        String name = block.getId().getPath();
        VariantBlockStateBuilder builder = getVariantBuilder(theBlock);
        for (int stack = 1; stack <= 12; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack == 1
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "block/" + name))
                        : models().withExistingParent("empty_" + stack, "minecraft:block/air");
                builder.partialState()
                        .with(BottleFoodBlock.FACING, facing)
                        .with(BottleFoodBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRot).build());
            }
        }
    }

    private void bowlBlock(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        String name = block.getId().getPath();
        VariantBlockStateBuilder builder = getVariantBuilder(theBlock);
        for (int stack = 1; stack <= 12; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack == 1
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "block/" + name))
                        : models().withExistingParent("empty_bowl_" + stack, "minecraft:block/air");
                builder.partialState()
                        .with(BowlFoodBlock.FACING, facing)
                        .with(BowlFoodBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRot).build());
            }
        }
    }

    private void smallPlateBlock(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        String name = block.getId().getPath();
        VariantBlockStateBuilder builder = getVariantBuilder(theBlock);
        for (int stack = 1; stack <= 12; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack == 1
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "block/" + name))
                        : models().withExistingParent("empty_small_plate_" + stack, "minecraft:block/air");
                builder.partialState()
                        .with(SmallPlateBlock.FACING, facing)
                        .with(SmallPlateBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRot).build());
            }
        }
    }

    private void plateBlock(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        String name = block.getId().getPath();
        int maxStack = ((PlateBlock) theBlock).maxStackSize;
        VariantBlockStateBuilder builder = getVariantBuilder(theBlock);
        for (int stack = 1; stack <= 12; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack <= maxStack
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "block/" + name + "_" + stack))
                        : models().withExistingParent("empty_plate_" + stack, "minecraft:block/air");
                builder.partialState()
                        .with(PlateBlock.FACING, facing)
                        .with(PlateBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRot).build());
            }
        }
    }

    private void displayBowlBlock(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        String name = block.getId().getPath();
        int maxStack = ((BowlBlock) theBlock).maxStackSize;
        VariantBlockStateBuilder builder = getVariantBuilder(theBlock);
        for (int stack = 1; stack <= 12; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack <= maxStack
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "block/" + name + "_" + stack))
                        : models().withExistingParent("empty_bowl_" + stack, "minecraft:block/air");
                builder.partialState()
                        .with(BowlBlock.FACING, facing)
                        .with(BowlBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRot).build());
            }
        }
    }

    private int getYRotation(Direction facing) {
        return switch (facing) {
            case WEST  -> 90;
            case NORTH -> 180;
            case EAST  -> 270;
            default    -> 0;
        };
    }

    private void registerFoodBlockStates() {
        Set<Block> fluidBlocks = new java.util.HashSet<>();
        for (Field field : FluidRegistration.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidBlock.FluidType fluidType) {
                    fluidBlocks.add(fluidType.BLOCK.get());
                }
            } catch (IllegalAccessException ignored) {}
        }

        BlockRegistration.BLOCKS.getEntries().forEach(entry -> {
            Block block = entry.get();

            if (fluidBlocks.contains(block)) return;

            String id = entry.getId().getPath();

            switch (block) {
                case CakeCandleBlock ccb -> candleCakeBlockState(block, ccb);
                case CakeFoodBlock cakeFoodBlock -> cakeBlockState(block, id);
                case PieBlock modPieBlock -> pieBlockState(block, id);
                case PizzaBlock pizzaBlock -> waffleOrPizzaBlockState(block, id);
                case RawPieBlock rawPieBlock -> rawPieBlockState(block, id);
                case RationBoxBlock rationBoxBlock -> rationBoxBlockState(block);
                case ClothSackBlock clothSackBlock -> clothSackBlockState(block);
                default -> simpleBlock(block, unchecked("block/" + id));
            }
        });
    }

    private void registerFluidBlockStates() {
        for (Field field : FluidRegistration.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidBlock.FluidType fluidType) {
                    String fluidId = fluidType.SOURCE.getId().getPath();
                    simpleBlock(fluidType.BLOCK.get(), unchecked("block/" + fluidId + "_block"));
                }
            } catch (IllegalAccessException ignored) {}
        }
    }

    private void candleCakeBlockState(Block block, CakeCandleBlock ccb) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        String cakeName = ccb.getParentCakeName();
        String candleSuffix = ccb.getCandleSuffix();

        JsonObject cakeTextures = readCakeTextures(cakeName);
        String topTex    = cakeTextures.get("top").getAsString();
        String sideTex   = cakeTextures.get("side").getAsString();
        String bottomTex = cakeTextures.has("bottom") ? cakeTextures.get("bottom").getAsString()
                                                       : CreateFoodCommon.MOD_ID + ":block/cake_bottom";

        int[] yRots   = {180, 270,   0,  90};
        String[] dirs = {"south", "west", "north", "east"};

        for (boolean lit : new boolean[]{false, true}) {
            String litSuffix = lit ? "_lit" : "";
            String modelName = "block/" + cakeName + "_" + candleSuffix + litSuffix;
            String candleTex = lit ? "minecraft:block/" + candleSuffix + "_lit"
                                   : "minecraft:block/" + candleSuffix;
            ModelFile model = models().getBuilder(modelName)
                    .parent(new ModelFile.UncheckedModelFile(
                            ResourceLocation.fromNamespaceAndPath("minecraft", "block/template_cake_with_candle")))
                    .texture("bottom",     bottomTex)
                    .texture("top",        topTex)
                    .texture("side",       sideTex)
                    .texture("particle",   sideTex)
                    .texture("candle",     candleTex)
                    .texture("lit_candle", lit ? candleTex : "minecraft:block/" + candleSuffix + "_lit");

            for (int f = 0; f < 4; f++) {
                builder.partialState()
                        .with(CakeCandleBlock.FACING, net.minecraft.core.Direction.byName(dirs[f]))
                        .with(CakeCandleBlock.LIT, lit)
                        .setModels(ConfiguredModel.builder()
                                .modelFile(model)
                                .rotationY(yRots[f])
                                .build());
            }
        }
    }

    private JsonObject readCakeTextures(String cakeName) {
        Path modelFile = resourceRoot.resolve(
                "assets/" + CreateFoodCommon.MOD_ID + "/models/block/" + cakeName + ".json");
        try {
            String json = Files.readString(modelFile);
            return JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("textures");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read cake model for " + cakeName + ": " + modelFile, e);
        }
    }

    private void cakeBlockState(Block block, String id) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (int bite = 0; bite <= 6; bite++) {
            ModelFile model = unchecked(bite == 0 ? "block/" + id : "block/" + id + "_slice" + bite);
            cakeForEachFacing(builder, block, bite, model);
        }
    }

    private void cakeForEachFacing(VariantBlockStateBuilder builder, Block block, int bite, ModelFile model) {
        net.minecraft.world.level.block.state.properties.IntegerProperty bitesProperty =
                (net.minecraft.world.level.block.state.properties.IntegerProperty)
                        block.getStateDefinition().getProperty("bites");

        int[] yRots   = {  0,  90, 180, 270};
        String[] dirs = {"south", "west", "north", "east"};
        for (int f = 0; f < 4; f++) {
            var state = builder.partialState()
                    .with(BlockStateProperties.HORIZONTAL_FACING, Direction.byName(dirs[f]));
            if (bitesProperty != null) state = state.with(bitesProperty, bite);
            state.setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRots[f]).build());
        }
    }

    private void pieBlockState(Block block, String id) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (int bite = 0; bite <= 3; bite++) {
            ModelFile model = unchecked(bite == 0 ? "block/" + id : "block/" + id + "_slice" + bite);
            forEachFacing(builder, block, bite, model);
        }
    }

    private void waffleOrPizzaBlockState(Block block, String id) {
        if (id.endsWith("_waffle") || id.equals("waffle")) {
            String prefix = id.equals("waffle") ? "" : id.replace("_waffle", "_");
            VariantBlockStateBuilder builder = getVariantBuilder(block);
            for (int bite = 0; bite <= 3; bite++) {
                ModelFile model = unchecked(bite == 0 ? "block/" + id : "block/" + prefix + "mini_waffle" + bite);
                forEachFacing(builder, block, bite, model);
            }
        } else {
            pieBlockState(block, id);
        }
    }

    private void rawPieBlockState(Block block, String id) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        ModelFile model = unchecked("block/" + id);
        int[] yRots    = {180, 270,    0,   90};
        String[] dirs  = {"south", "west", "north", "east"};
        for (int f = 0; f < 4; f++) {
            builder.partialState()
                    .with(BlockStateProperties.HORIZONTAL_FACING, Direction.byName(dirs[f]))
                    .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRots[f]).build());
        }
    }

    private void forEachFacing(VariantBlockStateBuilder builder, Block block, int bite, ModelFile model) {
        net.minecraft.world.level.block.state.properties.IntegerProperty bitesProperty =
                (net.minecraft.world.level.block.state.properties.IntegerProperty)
                        block.getStateDefinition().getProperty("bites");

        int[] yRots   = {180, 270,   0,  90};
        String[] dirs = {"south", "west", "north", "east"};
        for (int f = 0; f < 4; f++) {
            var state = builder.partialState()
                    .with(BlockStateProperties.HORIZONTAL_FACING, Direction.byName(dirs[f]));
            if (bitesProperty != null) state = state.with(bitesProperty, bite);
            state.setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRots[f]).build());
        }
    }

    private void clothSackBlockState(Block block) {
        ModelFile closed = unchecked("block/cloth_sack_closed");
        ModelFile open   = unchecked("block/cloth_sack_open");

        VariantBlockStateBuilder builder = getVariantBuilder(block);

        int[] yRots   = {180, 270,   0,  90};
        String[] dirs = {"south", "west", "north", "east"};

        for (int f = 0; f < 4; f++) {
            Direction facing = Direction.byName(dirs[f]);
            int yRot = yRots[f];

            builder.partialState()
                    .with(ClothSackBlock.FACING, facing)
                    .with(ClothSackBlock.OPEN, false)
                    .setModels(ConfiguredModel.builder().modelFile(closed).rotationY(yRot).build());

            builder.partialState()
                    .with(ClothSackBlock.FACING, facing)
                    .with(ClothSackBlock.OPEN, true)
                    .setModels(ConfiguredModel.builder().modelFile(open).rotationY(yRot).build());
        }
    }

    private void rationBoxBlockState(Block block) {
        ModelFile model = unchecked("block/ration_box");
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        int[] yRots   = {180, 270,   0,  90};
        String[] dirs = {"south", "west", "north", "east"};
        for (int f = 0; f < 4; f++) {
            Direction facing = Direction.byName(dirs[f]);
            builder.partialState()
                    .with(RationBoxBlock.FACING, facing)
                    .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRots[f]).build());
        }
    }

    private ModelFile unchecked(String path) {
        return new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, path));
    }
}