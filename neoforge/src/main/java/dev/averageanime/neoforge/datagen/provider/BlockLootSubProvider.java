package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.block.type.bowl.*;
import dev.averageanime.block.type.plate.*;
import dev.averageanime.neoforge.block.BlockRegistration;
import dev.averageanime.neoforge.block.DisplayBlockRegistration;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.block.type.cake.CakeFoodBlock;
import dev.averageanime.block.type.cake.CakeCandleBlock;
import dev.averageanime.block.type.display.*;
import dev.averageanime.neoforge.block.type.fluid.FluidBlock;
import dev.averageanime.block.type.storage.ClothSackBlock;
import dev.averageanime.block.type.storage.RationBoxBlock;
import dev.averageanime.block.type.misc.ConsumableBlock;
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

public class BlockLootSubProvider extends net.minecraft.data.loot.BlockLootSubProvider {

    private static final ResourceLocation KNIFE_TAG =
            ResourceLocation.fromNamespaceAndPath("c", "tools/knife");

    public BlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    private Set<Block> getFluidBlocks() {
        Set<Block> fluidBlocks = new HashSet<>();
        for (Field field : FluidRegistration.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidBlock.FluidType fluidType) {
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
        DisplayBlockRegistration.BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof BottleFoodBlock) {
                simpleSelfDrop(block);
            } else if (block instanceof BowlFoodBlock
                    || block instanceof LargeBowlFoodBlock
                    || block instanceof PlateFoodBlock) {
                simpleSelfDrop(block);
            } else if (block instanceof SmallPlateBlock) {
                simpleSelfDrop(block);
            } else if (block instanceof PlateBlock || block instanceof BowlBlock) {
                plateBlockLoot((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof EmptyBottleBlock) {
                add(block, LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE))));
            } else if (block instanceof EmptySmallPlateBlock
                    || block instanceof EmptyPlateBlock
                    || block instanceof EmptyBowlBlock
                    || block instanceof EmptyLargeBowlBlock) {
                int maxStack = block instanceof EmptySmallPlateBlock ? EmptySmallPlateBlock.MAX_STACK
                        : block instanceof EmptyPlateBlock ? EmptyPlateBlock.MAX_STACK
                        : block instanceof EmptyLargeBowlBlock ? EmptyLargeBowlBlock.MAX_STACK
                        : EmptyBowlBlock.MAX_STACK;
                LootTable.Builder builder = LootTable.lootTable();
                for (int stack = 1; stack <= maxStack; stack++) {
                    builder.withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(stack))
                            .add(LootItem.lootTableItem(Items.BOWL))
                            .when(stackSizeIs(block, stack)));
                }
                add(block, builder);
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
        FoodBlock foodBlock = (FoodBlock) block;
        Item foodItem = foodBlock.displayItem.get();
        int maxStack = foodBlock.maxStackSize;

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

        BlockRegistration.BLOCKS.getEntries().forEach(entry -> {
            Block block = entry.get();

            if (fluidBlocks.contains(block)) return;
            if (block instanceof CakeCandleBlock) return;
            if (block instanceof RationBoxBlock || block instanceof ClothSackBlock) return; // skip entirely
            if (block.getLootTable().equals(net.minecraft.world.level.storage.loot.BuiltInLootTables.EMPTY)) return; // <-- add this

            if (block instanceof CakeFoodBlock cakeBlock) {
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
                DisplayBlockRegistration.BLOCKS.getEntries().stream()
                        .map(e -> (Block) e.get())
                        .filter(b -> !(b instanceof GenericDisplayPlateBlock))
                        .filter(b -> !(b instanceof GenericDisplayBowlBlock)),
                BlockRegistration.BLOCKS.getEntries().stream()
                        .map(e -> (Block) e.get())
                        .filter(b -> !fluidBlocks.contains(b))
                        .filter(b -> !(b instanceof CakeCandleBlock))
                        .filter(b -> !(b instanceof GenericDisplayPlateBlock))
                        .filter(b -> !(b instanceof RationBoxBlock))      // <-- add
                        .filter(b -> !(b instanceof ClothSackBlock))
        ).toList();
    }
}