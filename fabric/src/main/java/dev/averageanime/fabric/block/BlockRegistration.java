package dev.averageanime.fabric.block;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.BlockFactory;
import dev.averageanime.block.type.cake.CakeFoodBlock;
import dev.averageanime.block.type.storage.ClothSackBlock;
import dev.averageanime.block.type.storage.RationBoxBlock;
import dev.averageanime.fabric.item.storage.ClothSackItem;
import dev.averageanime.fabric.item.storage.RationBoxItem;
import dev.averageanime.client.tooltip.ItemTooltips;
import dev.averageanime.platform.Services;
import dev.averageanime.util.Tooltips;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistration {

    public static Block CLOTH_SACK;
    public static Block RATION_BOX;

    private static final BlockFactory.Hooks HOOKS = new BlockFactory.Hooks() {
        @Override
        public Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier, int stackLimit, @Nullable Tooltips.TooltipSpec tip) {
            Block block = blockSupplier.get();
            Registry.register(BuiltInRegistries.ITEM, id(name), blockItem(block, stackLimit, tip));
            Registry.register(BuiltInRegistries.BLOCK, id(name), block);
            return () -> block;
        }

        @Override
        public void registerCandleCake(String name, Supplier<Block> candleCakeSupplier, Supplier<Block> baseCake, net.minecraft.world.item.Item candleItem) {
            Block candleCake = Registry.register(BuiltInRegistries.BLOCK, id(name), candleCakeSupplier.get());
            CakeFoodBlock.registerCandleVariant(baseCake.get(), candleItem, candleCake);
        }
    };

    public static void init() {
        BlockFactory.registerAll(HOOKS);

        RATION_BOX = registerBlockWithCustomItem("ration_box",
                () -> new RationBoxBlock(BlockBehaviour.Properties.of()
                        .strength(1.5f, 6.0f).sound(SoundType.WOOD).noOcclusion()),
                block -> new RationBoxItem(block, new net.minecraft.world.item.Item.Properties()));

        CLOTH_SACK = registerBlockWithCustomItem("cloth_sack",
                () -> new ClothSackBlock(BlockBehaviour.Properties.of()
                        .strength(1.0f, 2.0f).sound(SoundType.WOOL).noOcclusion()),
                block -> new ClothSackItem(block, new net.minecraft.world.item.Item.Properties()));

        BlockFactory.registerCandleCakes(HOOKS);
        registerConfigBlocks();
    }

    private static void registerConfigBlocks() {
        var configFile = Services.PLATFORM.getConfigDir().resolve("createfood-common.toml");
        if (!java.nio.file.Files.exists(configFile)) {
            CreateFoodCommon.LOGGER.warn("Create: Food - createfood-common.toml not found yet; skipping custom_block registration for this launch");
            return;
        }
        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("blocks.block", List.of());
            BlockFactory.registerConfigBlocks(HOOKS, entries);
        } catch (Exception e) {
            CreateFoodCommon.LOGGER.warn("Create: Food - Failed to read custom_block from config", e);
        }
    }

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

    private static Block registerBlockWithCustomItem(String name, Supplier<Block> blockSupplier,
                                                     Function<Block, net.minecraft.world.item.Item> itemFactory) {
        Block block = Registry.register(BuiltInRegistries.BLOCK, id(name), blockSupplier.get());
        Registry.register(BuiltInRegistries.ITEM, id(name), itemFactory.apply(block));
        return block;
    }

    private static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, name);
    }
}
