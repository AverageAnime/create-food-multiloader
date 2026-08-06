package dev.averageanime.neoforge.block;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.BlockFactory;
import dev.averageanime.block.type.cake.CakeFoodBlock;
import dev.averageanime.block.type.storage.ClothSackBlock;
import dev.averageanime.block.type.storage.RationBoxBlock;
import dev.averageanime.client.tooltip.ItemTooltips;
import dev.averageanime.neoforge.item.storage.ClothSackItem;
import dev.averageanime.neoforge.item.storage.RationBoxItem;
import dev.averageanime.platform.Services;
import dev.averageanime.util.Tooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ItemRegistration.ITEMS;

public class BlockRegistration {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreateFoodCommon.MOD_ID);

    private record CandleWiring(Supplier<Block> cake, net.minecraft.world.item.Item candleItem, Supplier<Block> candleCake) {}

    private static final List<CandleWiring> PENDING_CANDLE_WIRING = new ArrayList<>();

    private static final BlockFactory.Hooks HOOKS = new BlockFactory.Hooks() {
        @Override
        public Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier, int stackLimit, @Nullable Tooltips.TooltipSpec tip) {
            DeferredBlock<Block> block = BLOCKS.register(name, blockSupplier);
            ITEMS.register(name, () -> blockItem(block.get(), stackLimit, tip));
            return block;
        }

        @Override
        public void registerCandleCake(String name, Supplier<Block> candleCakeSupplier, Supplier<Block> baseCake, net.minecraft.world.item.Item candleItem) {
            DeferredBlock<Block> candleCake = BLOCKS.register(name, candleCakeSupplier);
            PENDING_CANDLE_WIRING.add(new CandleWiring(baseCake, candleItem, candleCake));
        }
    };

    private static BlockItem blockItem(Block block, int stackLimit, @Nullable Tooltips.TooltipSpec tip) {
        var props = new net.minecraft.world.item.Item.Properties().stacksTo(stackLimit);
        if (tip == null) return new BlockItem(block, props);
        return new BlockItem(block, props) {
            @Override
            public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                        @NotNull List<Component> components, @NotNull TooltipFlag flag) {
                ItemTooltips.addTooltip(components, tip.compat(), tip.keys());
                super.appendHoverText(stack, context, components, flag);
            }
        };
    }

    static {
        BlockFactory.registerAll(HOOKS);
    }

    public static final DeferredBlock<Block> RATION_BOX = registerBlockWithCustomItem("ration_box",
            () -> new RationBoxBlock(BlockBehaviour.Properties.of()
                    .strength(1.5f, 6.0f).sound(SoundType.WOOD).noOcclusion()),
            block -> new RationBoxItem(block, new net.minecraft.world.item.Item.Properties()));

    public static final DeferredBlock<Block> CLOTH_SACK = registerBlockWithCustomItem("cloth_sack",
            () -> new ClothSackBlock(BlockBehaviour.Properties.of()
                    .strength(1.0f, 2.0f).sound(SoundType.WOOL).noOcclusion()),
            block -> new ClothSackItem(block, new net.minecraft.world.item.Item.Properties()));

    private static DeferredBlock<Block> registerBlockWithCustomItem(String name, Supplier<Block> blockSupplier,
                                                                    Function<Block, net.minecraft.world.item.Item> itemFactory) {
        DeferredBlock<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> itemFactory.apply(block.get()));
        return block;
    }

    public static void wireUpCandleMap() {
        for (CandleWiring w : PENDING_CANDLE_WIRING) {
            CakeFoodBlock.registerCandleVariant(w.cake().get(), w.candleItem(), w.candleCake().get());
        }
    }

    private static void registerConfigBlocks() {
        var configFile = Services.PLATFORM.getConfigDir().resolve("createfood-common.toml");
        if (!java.nio.file.Files.exists(configFile)) {
            LOGGER.warn("Create: Food - createfood-common.toml not found yet; skipping custom_block registration for this launch");
            return;
        }
        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("blocks.block", List.of());
            BlockFactory.registerConfigBlocks(HOOKS, entries);
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_block from config", e);
        }
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Blocks");
        registerConfigBlocks();
        BlockFactory.registerCandleCakes(HOOKS);
        BLOCKS.register(eventBus);
    }
}
