package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.type.display.*;
import dev.averageanime.neoforge.block.type.display.plate.PlateBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class BlockStateProvider extends net.neoforged.neoforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CommonClass.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
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

                ModelFile model;
                if (stack == 1) {
                    model = new ModelFile.UncheckedModelFile(
                            ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name));
                } else {
                    model = models().withExistingParent("empty_" + stack, "minecraft:block/air");
                }

                builder.partialState()
                        .with(BottleFoodBlock.FACING, facing)
                        .with(BottleFoodBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder()
                                .modelFile(model)
                                .rotationY(yRot)
                                .build());
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

                ModelFile model;
                if (stack == 1) {
                    model = new ModelFile.UncheckedModelFile(
                            ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name));
                } else {
                    model = models().withExistingParent("empty_bowl_" + stack, "minecraft:block/air");
                }

                builder.partialState()
                        .with(BowlFoodBlock.FACING, facing)
                        .with(BowlFoodBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder()
                                .modelFile(model)
                                .rotationY(yRot)
                                .build());
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

                ModelFile model;
                if (stack == 1) {
                    model = new ModelFile.UncheckedModelFile(
                            ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name));
                } else {
                    model = models().withExistingParent("empty_small_plate_" + stack, "minecraft:block/air");
                }

                builder.partialState()
                        .with(SmallPlateFoodBlock.FACING, facing)
                        .with(SmallPlateFoodBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder()
                                .modelFile(model)
                                .rotationY(yRot)
                                .build());
            }
        }
    }

    private void plateBlock(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        String name = block.getId().getPath();
        PlateBlock plateBlock = (PlateBlock) theBlock;
        int maxStack = plateBlock.maxStackSize;

        VariantBlockStateBuilder builder = getVariantBuilder(theBlock);

        for (int stack = 1; stack <= 9; stack++) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                int yRot = getYRotation(facing);

                ModelFile model;
                if (stack <= maxStack) {
                    model = new ModelFile.UncheckedModelFile(
                            ResourceLocation.fromNamespaceAndPath(CommonClass.ID, "block/" + name + "_" + stack));
                } else {
                    model = models().withExistingParent("empty_plate_" + stack, "minecraft:block/air");
                }

                builder.partialState()
                        .with(PlateBlock.FACING, facing)
                        .with(PlateBlock.STACK_SIZE, stack)
                        .setModels(ConfiguredModel.builder()
                                .modelFile(model)
                                .rotationY(yRot)
                                .build());
            }
        }
    }

    private int getYRotation(Direction facing) {
        return switch (facing) {
            case SOUTH -> 0;
            case WEST -> 90;
            case NORTH -> 180;
            case EAST -> 270;
            default -> 0;
        };
    }
}