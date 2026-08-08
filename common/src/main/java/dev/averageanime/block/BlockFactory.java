package dev.averageanime.block;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.cake.*;
import dev.averageanime.block.type.misc.CheeseBlock;
import dev.averageanime.block.type.misc.GyroMeatBlock;
import dev.averageanime.block.type.pie.PieBlock;
import dev.averageanime.block.type.pie.PizzaBlock;
import dev.averageanime.block.type.pie.RawPieBlock;
import dev.averageanime.block.type.pie.RawPizzaBlock;
import dev.averageanime.registry.BlockRegistry;
import dev.averageanime.registry.type.BlockEntry;
import dev.averageanime.registry.ItemLookup;
import dev.averageanime.registry.type.ItemEntry;
import dev.averageanime.util.Tooltips;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class BlockFactory {

    public interface Hooks {
        Supplier<net.minecraft.world.level.block.Block> registerBlock(
                String name, Supplier<net.minecraft.world.level.block.Block> blockSupplier, int stackLimit, @Nullable Tooltips.TooltipSpec tip);

        void registerCandleCake(String name, Supplier<net.minecraft.world.level.block.Block> candleCakeSupplier,
                                Supplier<net.minecraft.world.level.block.Block> baseCake, net.minecraft.world.item.Item candleItem);
    }

    private record CandleType(String suffix, net.minecraft.world.level.block.Block block) {}

    private static final List<CandleType> CANDLE_TYPES = List.of(
            new CandleType("candle",            Blocks.CANDLE),
            new CandleType("white_candle",      Blocks.WHITE_CANDLE),
            new CandleType("orange_candle",     Blocks.ORANGE_CANDLE),
            new CandleType("magenta_candle",    Blocks.MAGENTA_CANDLE),
            new CandleType("light_blue_candle", Blocks.LIGHT_BLUE_CANDLE),
            new CandleType("yellow_candle",     Blocks.YELLOW_CANDLE),
            new CandleType("lime_candle",       Blocks.LIME_CANDLE),
            new CandleType("pink_candle",       Blocks.PINK_CANDLE),
            new CandleType("gray_candle",       Blocks.GRAY_CANDLE),
            new CandleType("light_gray_candle", Blocks.LIGHT_GRAY_CANDLE),
            new CandleType("cyan_candle",       Blocks.CYAN_CANDLE),
            new CandleType("purple_candle",     Blocks.PURPLE_CANDLE),
            new CandleType("blue_candle",       Blocks.BLUE_CANDLE),
            new CandleType("brown_candle",      Blocks.BROWN_CANDLE),
            new CandleType("green_candle",      Blocks.GREEN_CANDLE),
            new CandleType("red_candle",        Blocks.RED_CANDLE),
            new CandleType("black_candle",      Blocks.BLACK_CANDLE)
    );

    private record CakeRef(String name, Supplier<net.minecraft.world.level.block.Block> block) {}

    private static final List<CakeRef> CAKES = new ArrayList<>();

    private static final int DEFAULT_STACK = 64;

    private BlockFactory() {}

    private static BlockBehaviour.Properties cakeProps() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE);
    }

    public static void registerAll(Hooks hooks) {
        BlockRegistry.init();
        for (BlockEntry def : BlockEntry.ALL) {
            Supplier<net.minecraft.world.item.Item> slice = def.sliceItemId != null
                    ? ItemLookup.byModId(def.sliceItemId)
                    : null;
            Supplier<net.minecraft.world.level.block.Block> holder = switch (def.category) {
                case CAKE -> registerCake(hooks, def.id, slice, def.tip);
                case CAKE_BASE -> hooks.registerBlock(def.id, () -> new CakeBaseBlock(cakeProps()), 1, def.tip);
                case RAW_PIE -> hooks.registerBlock(def.id, () -> new RawPieBlock(cakeProps()), DEFAULT_STACK, def.tip);
                case RAW_PIZZA -> hooks.registerBlock(def.id, () -> new RawPizzaBlock(cakeProps()), DEFAULT_STACK, def.tip);
                case COOKED_PIE -> hooks.registerBlock(def.id, () -> new PieBlock(cakeProps(), slice), DEFAULT_STACK, def.tip);
                case COOKED_PIZZA -> hooks.registerBlock(def.id, () -> new PizzaBlock(cakeProps(), slice), DEFAULT_STACK, def.tip);
                case GELATIN -> hooks.registerBlock(def.id, () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)), DEFAULT_STACK, null);
                case CHEESE -> hooks.registerBlock(def.id, () -> new CheeseBlock(cakeProps(), slice), DEFAULT_STACK, def.tip);
                case GYRO_MEAT -> hooks.registerBlock(def.id, () -> new GyroMeatBlock(cakeProps(), slice), DEFAULT_STACK, def.tip);
            };
            def.bind(holder);
        }
    }

    private static Supplier<net.minecraft.world.level.block.Block> registerCake(
            Hooks hooks, String name, Supplier<net.minecraft.world.item.Item> slice, @Nullable Tooltips.TooltipSpec tip) {
        Supplier<net.minecraft.world.level.block.Block> holder =
                hooks.registerBlock(name, () -> new CakeFoodBlock(cakeProps(), slice), 1, tip);
        CAKES.add(new CakeRef(name, holder));
        return holder;
    }

    public static void registerCandleCakes(Hooks hooks) {
        for (CakeRef cake : CAKES) {
            for (CandleType candle : CANDLE_TYPES) {
                String blockName = cake.name() + "_" + candle.suffix();
                Supplier<net.minecraft.world.level.block.Block> candleCake = () -> new CakeCandleBlock(
                        cakeProps().lightLevel(s -> s.getValue(CakeCandleBlock.LIT) ? 3 : 0),
                        cake.block(), candle.block(), cake.name(), candle.suffix());
                hooks.registerCandleCake(blockName, candleCake, cake.block(), candle.block().asItem());
            }
        }
    }

    public static void registerConfigBlocks(Hooks hooks, List<String> entries) {
        for (String entry : entries) {
            String[] p = entry.split("\\|");
            if (p.length < 2) {
                CreateFoodCommon.LOGGER.warn("Create: Food - Skipping invalid custom_block entry (too few fields): {}", entry);
                continue;
            }
            String name = p[0];
            String type = p[1].toLowerCase();
            switch (type) {
                case "cake_base" -> hooks.registerBlock(name, () -> new CakeBaseBlock(cakeProps()), 1, null);
                case "raw_pie" -> hooks.registerBlock(name, () -> new RawPieBlock(cakeProps()), DEFAULT_STACK, null);
                case "raw_pizza" -> hooks.registerBlock(name, () -> new RawPizzaBlock(cakeProps()), DEFAULT_STACK, null);
                case "gelatin" -> hooks.registerBlock(name, () -> new SlimeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)), DEFAULT_STACK, null);
                case "cake", "pie", "pizza", "waffle", "cheese", "gyro_meat" -> {
                    if (p.length < 3 || p[2].isBlank()) {
                        CreateFoodCommon.LOGGER.warn("Create: Food - Skipping custom_block entry missing slice item: {}", entry);
                        continue;
                    }
                    Supplier<net.minecraft.world.item.Item> slice = ItemLookup.byFullId(p[2]);
                    switch (type) {
                        case "cake" -> registerCake(hooks, name, slice, null);
                        case "cheese" -> hooks.registerBlock(name, () -> new CheeseBlock(cakeProps(), slice), DEFAULT_STACK, null);
                        case "gyro_meat" -> hooks.registerBlock(name, () -> new GyroMeatBlock(cakeProps(), slice), DEFAULT_STACK, null);
                        case "pie" -> hooks.registerBlock(name, () -> new PieBlock(cakeProps(), slice), DEFAULT_STACK, null);
                        default -> hooks.registerBlock(name, () -> new PizzaBlock(cakeProps(), slice), DEFAULT_STACK, null);
                    }
                }
                default -> CreateFoodCommon.LOGGER.warn("Create: Food - Skipping custom_block entry with unknown type '{}': {}", type, entry);
            }
        }
    }

}
