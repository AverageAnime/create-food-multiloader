package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.ModFluids;
import dev.averageanime.neoforge.block.type.cake.ModCakeBlock;
import dev.averageanime.neoforge.block.type.display.*;
import dev.averageanime.neoforge.block.type.display.plate.EmptyPlateBlock;
import dev.averageanime.neoforge.block.type.display.plate.PlateBlock;
import dev.averageanime.neoforge.block.type.display.plate.SmallPlateBlock;
import dev.averageanime.neoforge.block.type.fluid.FluidEntry;
import dev.averageanime.neoforge.block.type.pie.ModPieBlock;
import dev.averageanime.neoforge.block.type.pie.PizzaBlock;
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class LootTableProvider extends net.minecraft.data.loot.BlockLootSubProvider {

    private static final ResourceLocation KNIFE_TAG =
            ResourceLocation.fromNamespaceAndPath("c", "tools/knife");

    public LootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    /**
     * Returns all fluid source blocks registered in ModFluids.
     * Fluid blocks have no block item, so attempting to build a loot table
     * for them produces a minecraft:empty key and fails validation.
     */
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

            if (block instanceof ModCakeBlock cakeBlock) {
                add(block, slicedLootTable(block, cakeBlock.pieSlice.get(), 7));
            } else if (block instanceof ModPieBlock || block instanceof PizzaBlock) {
                Item sliceItem = getSliceItemFromFD(block);
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

    /**
     * Builds a loot table for a sliceable block (cake, pie, pizza).
     *
     * <ul>
     *   <li>bites=0, no knife → drop the whole block item
     *   <li>bites=0..maxSlices-2, knife → drop (maxSlices - bite) slices
     *   <li>bites=maxSlices-1 → always drop 1 slice (survives_explosion)
     * </ul>
     */
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

    /**
     * Retrieves the slice item from Farmer's Delight's PieBlock via reflection.
     */
    @SuppressWarnings("unchecked")
    private static Item getSliceItemFromFD(Block block) {
        try {
            Class<?> fdPieBlock = Class.forName("vectorwing.farmersdelight.common.block.PieBlock");
            java.lang.reflect.Field f = fdPieBlock.getDeclaredField("pieSlice");
            f.setAccessible(true);
            java.util.function.Supplier<Item> supplier =
                    (java.util.function.Supplier<Item>) f.get(block);
            return supplier != null ? supplier.get() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Must match exactly the set of blocks for which we called add() above.
     * Fluid blocks are excluded here for the same reason they are excluded in
     * generateFoodBlockLoot() — they have no block item and fail validation.
     */
    @Override
    protected Iterable<Block> getKnownBlocks() {
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