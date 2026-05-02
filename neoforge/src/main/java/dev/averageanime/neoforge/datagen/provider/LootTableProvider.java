package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.ModFluids;
import dev.averageanime.block.ModCakeBlock;
import dev.averageanime.block.type.display.*;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.neoforge.block.type.fluid.FluidEntry;
import dev.averageanime.neoforge.block.type.storage.ClothSackBlock;
import dev.averageanime.neoforge.block.type.storage.RationBoxBlock;
import dev.averageanime.block.type.ConsumableBlock;
import dev.averageanime.block.type.pie.PieBlock;
import dev.averageanime.block.type.pie.PizzaBlock;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

public class LootTableProvider extends net.minecraft.data.loot.BlockLootSubProvider {

    private static final ResourceLocation KNIFE_TAG =
            ResourceLocation.fromNamespaceAndPath("c", "tools/knife");

    public LootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    private Set<Block> getFluidBlocks() {
        Set<Block> fluidBlocks = new HashSet<>();
        for (Field field : ModFluids.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidEntry.FluidType fluidType) {
                    fluidBlocks.add(fluidType.BLOCK.get());
                }
            } catch (IllegalAccessException ignored) {}
        }
        return fluidBlocks;
    }

    @Override
    protected void generate() {
        generateDisplayBlockLoot();
        generateFoodBlockLoot();
    }

    private void generateDisplayBlockLoot() {
        ModDisplayBlocks.BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof BottleFoodBlock) {
                simpleSelfDrop(block);
            } else if (block instanceof BowlFoodBlock
                    || block instanceof SaladBowlFoodBlock
                    || block instanceof PlateFoodBlock) {
                simpleSelfDrop(block);
            } else if (block instanceof SmallPlateFoodBlock) {
                simpleSelfDrop(block);
            } else if (block instanceof PlateBlock) {
                plateBlockLoot((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof SmallPlateBlock
                    || block instanceof EmptyPlateBlock) {
                add(block, LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.BOWL))
                                .when(ExplosionCondition.survivesExplosion())));
            }
        });
    }

    private void simpleSelfDrop(Block block) {
        add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block))
                        .when(ExplosionCondition.survivesExplosion())));
    }

    private void plateBlockLoot(DeferredBlock<Block> blockEntry) {
        Block block = blockEntry.get();
        PlateBlock plateBlock = (PlateBlock) block;
        Item foodItem = plateBlock.displayItem.get();
        int maxStack = plateBlock.maxStackSize;

        LootTable.Builder builder = LootTable.lootTable();

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block))
                .when(stackSizeIs(block, maxStack)));

        for (int stack = 1; stack < maxStack; stack++) {
            builder.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(stack))
                    .add(LootItem.lootTableItem(foodItem))
                    .when(stackSizeIs(block, stack)));
        }

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.BOWL))
                .when(InvertedLootItemCondition.invert(stackSizeIs(block, maxStack))));

        add(block, builder);
    }

    private void generateFoodBlockLoot() {
        Set<Block> fluidBlocks = getFluidBlocks();

        ModBlocks.BLOCKS.getEntries().forEach(entry -> {
            Block block = entry.get();

            if (fluidBlocks.contains(block)) return;

            if (block instanceof RationBoxBlock || block instanceof ClothSackBlock) {
                add(block, LootTable.lootTable());
            } else if (block instanceof ModCakeBlock cakeBlock) {
                add(block, slicedLootTable(block, cakeBlock.pieSlice.get(), 7));
            } else if (block instanceof PieBlock || block instanceof PizzaBlock) {
                Item sliceItem = getSliceItem(block);
                if (sliceItem != null) {
                    add(block, slicedLootTable(block, sliceItem, 4));
                } else {
                    add(block, createSingleItemTable(block));
                }
            } else {
                add(block, createSingleItemTable(block));
            }
        });
    }

    private LootTable.Builder slicedLootTable(Block block, Item sliceItem, int maxSlices) {
        LootTable.Builder builder = LootTable.lootTable();

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block.asItem()))
                .when(InvertedLootItemCondition.invert(knifeCondition()))
                .when(biteIs(block, 0)));

        for (int bite = 0; bite <= maxSlices - 2; bite++) {
            builder.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(maxSlices - bite))
                    .add(LootItem.lootTableItem(sliceItem))
                    .when(knifeCondition())
                    .when(biteIs(block, bite)));
        }

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(sliceItem))
                .when(ExplosionCondition.survivesExplosion())
                .when(biteIs(block, maxSlices - 1)));

        return builder;
    }

    private LootItemCondition.Builder knifeCondition() {
        return MatchTool.toolMatches(
                ItemPredicate.Builder.item()
                        .of(TagKey.create(net.minecraft.core.registries.Registries.ITEM, KNIFE_TAG)));
    }

    private LootItemCondition.Builder biteIs(Block block, int bites) {
        return LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BlockStateProperties.BITES, bites));
    }

    private LootItemCondition.Builder stackSizeIs(Block block, int size) {
        return LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(PlateBlock.STACK_SIZE, size));
    }

    private static Item getSliceItem(Block block) {
        try {
            if (block instanceof ConsumableBlock biteable) {
                return biteable.getSliceItem().getItem();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        Set<Block> fluidBlocks = getFluidBlocks();

        return Stream.concat(
                ModDisplayBlocks.BLOCKS.getEntries().stream()
                        .map(e -> (Block) e.get()),
                ModBlocks.BLOCKS.getEntries().stream()
                        .map(e -> (Block) e.get())
                        .filter(b -> !fluidBlocks.contains(b))
        ).toList();
    }
}