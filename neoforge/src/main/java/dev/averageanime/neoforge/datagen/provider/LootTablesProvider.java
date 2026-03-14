package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.block.type.display.*;
import dev.averageanime.neoforge.block.type.display.plate.PlateBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Set;

public class LootTablesProvider extends net.minecraft.data.loot.BlockLootSubProvider {

    public LootTablesProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        ModDisplayBlocks.BLOCKS.getEntries().forEach(blockEntry -> {
            Block block = blockEntry.get();
            if (block instanceof BottleFoodBlock) {
                bottleBlockLoot((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof BowlFoodBlock) {
                bowlBlockLoot((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof SaladBowlFoodBlock) {
                bowlBlockLoot((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof PlateFoodBlock) {
                bowlBlockLoot((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof SmallPlateFoodBlock) {
                smallPlateBlockLoot((DeferredBlock<Block>) blockEntry);
            } else if (block instanceof PlateBlock) {
                plateBlockLoot((DeferredBlock<Block>) blockEntry);
            }
        });
    }

    private void bottleBlockLoot(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        if (theBlock instanceof BottleFoodBlock bottleBlock) {
            Item bottleItem = bottleBlock.displayItem.get();

            add(theBlock, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(theBlock))
                            .when(ExplosionCondition.survivesExplosion())));
        }
    }

    private void bowlBlockLoot(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        if (theBlock instanceof BowlFoodBlock bowlBlock) {
            Item bowlItem = bowlBlock.displayItem.get();

            add(theBlock, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(theBlock))
                            .when(ExplosionCondition.survivesExplosion())));
        } else if (theBlock instanceof SaladBowlFoodBlock bowlBlock) {
            Item bowlItem = bowlBlock.displayItem.get();

            add(theBlock, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(theBlock))
                            .when(ExplosionCondition.survivesExplosion())));
        } else if (theBlock instanceof PlateFoodBlock bowlBlock) {
            Item bowlItem = bowlBlock.displayItem.get();

            add(theBlock, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(theBlock))
                            .when(ExplosionCondition.survivesExplosion())));
        }
    }

    private void smallPlateBlockLoot(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        if (theBlock instanceof SmallPlateFoodBlock) {
            LootTable.Builder builder = LootTable.lootTable();

            builder.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(theBlock))
                    .when(ExplosionCondition.survivesExplosion()));

            add(theBlock, builder);
        }
    }

    private void plateBlockLoot(DeferredBlock<Block> block) {
        Block theBlock = block.get();
        if (theBlock instanceof PlateBlock plateBlock) {
            Item foodItem = plateBlock.displayItem.get();
            int maxStack = plateBlock.maxStackSize;

            LootTable.Builder builder = LootTable.lootTable();

            builder.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(theBlock))
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(theBlock)
                            .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(PlateBlock.STACK_SIZE, maxStack))));

            for (int stack = 1; stack < maxStack; stack++) {
                builder.withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(stack))
                        .add(LootItem.lootTableItem(foodItem))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(theBlock)
                                .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(PlateBlock.STACK_SIZE, stack))));
            }

            builder.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(Items.BOWL))
                    .when(InvertedLootItemCondition.invert(
                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(theBlock)
                                    .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(PlateBlock.STACK_SIZE, maxStack)))));

            add(theBlock, builder);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return (Iterable<Block>) ModDisplayBlocks.BLOCKS.getEntries()
                .stream()
                .map(holder -> holder.get())
                .filter(block -> block != ModDisplayBlocks.SMALL_PLATE_BLOCK.get())
                .filter(block -> block != ModDisplayBlocks.PLATE_BLOCK.get())
                .toList();
    }
}