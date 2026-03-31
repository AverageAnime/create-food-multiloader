package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.ModFluids;
import dev.averageanime.neoforge.block.type.cake.ModCakeBlock;
import dev.averageanime.neoforge.block.type.display.*;
import dev.averageanime.neoforge.block.type.display.plate.PlateBlock;
import dev.averageanime.neoforge.block.type.fluid.FluidEntry;
import dev.averageanime.neoforge.block.type.pie.ModPieBlock;
import dev.averageanime.neoforge.block.type.pie.PizzaBlock;
import dev.averageanime.neoforge.block.type.pie.RawPieBlock;
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

public class BlockStateProvider extends net.neoforged.neoforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CommonClass.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerDisplayBlockStates();
        registerFoodBlockStates();
        registerFluidBlockStates();
    }

    private void registerDisplayBlockStates() {
        ModDisplayBlocks.BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof BottleFoodBlock) {
                bottleBlock((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof BowlFoodBlock) {
                bowlBlock((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof SaladBowlFoodBlock) {
                bowlBlock((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof PlateFoodBlock) {
                bowlBlock((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof SmallPlateFoodBlock) {
                smallPlateBlock((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof PlateBlock) {
                plateBlock((DeferredBlock<Block>) blockEntry);
            }
        });
    }

    private void bottleBlock(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        String name = block.getId().getPath();
        VariantBlockStateBuilder builder = getVariantBuilder(theBlock);
        for (int stack = 1; stack <= 9; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack == 1
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name))
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
        for (int stack = 1; stack <= 9; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack == 1
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name))
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
        for (int stack = 1; stack <= 9; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack == 1
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name))
                        : models().withExistingParent("empty_small_plate_" + stack, "minecraft:block/air");
                builder.partialState()
                        .with(SmallPlateFoodBlock.FACING, facing)
                        .with(SmallPlateFoodBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRot).build());
            }
        }
    }

    private void plateBlock(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        String name = block.getId().getPath();
        int maxStack = ((PlateBlock) theBlock).maxStackSize;
        VariantBlockStateBuilder builder = getVariantBuilder(theBlock);
        for (int stack = 1; stack <= 9; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);
                ModelFile model = stack <= maxStack
                        ? new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name + "_" + stack))
                        : models().withExistingParent("empty_plate_" + stack, "minecraft:block/air");
                builder.partialState()
                        .with(PlateBlock.FACING, facing)
                        .with(PlateBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRot).build());
            }
        }
    }

    private int getYRotation(Direction facing) {
        return switch (facing) {
            case SOUTH -> 0;
            case WEST  -> 90;
            case NORTH -> 180;
            case EAST  -> 270;
            default    -> 0;
        };
    }

    private void registerFoodBlockStates() {
        Set<Block> fluidBlocks = new java.util.HashSet<>();
        for (Field field : ModFluids.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidEntry.FluidType fluidType) {
                    fluidBlocks.add(fluidType.BLOCK.get());
                }
            } catch (IllegalAccessException ignored) {}
        }

        ModBlocks.BLOCKS.getEntries().forEach(entry -> {
            Block block = entry.get();

            if (fluidBlocks.contains(block)) return;

            String id = entry.getId().getPath();

            if (block instanceof ModCakeBlock) {
                cakeBlockState(block, id, 6);
            } else if (block instanceof ModPieBlock) {
                pieBlockState(block, id, 3);
            } else if (block instanceof PizzaBlock) {
                waffleOrPizzaBlockState(block, id, 3);
            } else if (block instanceof RawPieBlock) {
                rawPieBlockState(block, id);
            } else {
                simpleBlock(block, unchecked("block/" + id));
            }
        });
    }

    private void registerFluidBlockStates() {
        for (Field field : ModFluids.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidEntry.FluidType fluidType) {
                    String fluidId = fluidType.SOURCE.getId().getPath();
                    simpleBlock(fluidType.BLOCK.get(), unchecked("block/" + fluidId + "_block"));
                }
            } catch (IllegalAccessException ignored) {}
        }
    }

    private void cakeBlockState(Block block, String id, int maxBites) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (int bite = 0; bite <= maxBites; bite++) {
            ModelFile model = unchecked(bite == 0 ? "block/" + id : "block/" + id + "_slice" + bite);
            forEachFacing(builder, block, bite, model);
        }
    }

    private void pieBlockState(Block block, String id, int maxBites) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (int bite = 0; bite <= maxBites; bite++) {
            ModelFile model = unchecked(bite == 0 ? "block/" + id : "block/" + id + "_slice" + bite);
            forEachFacing(builder, block, bite, model);
        }
    }

    private void waffleOrPizzaBlockState(Block block, String id, int maxBites) {
        if (id.endsWith("_waffle") || id.equals("waffle")) {
            String prefix = id.equals("waffle") ? "" : id.replace("_waffle", "_");
            VariantBlockStateBuilder builder = getVariantBuilder(block);
            for (int bite = 0; bite <= maxBites; bite++) {
                ModelFile model = unchecked(bite == 0 ? "block/" + id : "block/" + prefix + "mini_waffle" + bite);
                forEachFacing(builder, block, bite, model);
            }
        } else {
            pieBlockState(block, id, maxBites);
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

    /** Adds all four facing variants for a given bite value. */
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

    private ModelFile unchecked(String path) {
        return new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath(CommonClass.ID, path));
    }
}