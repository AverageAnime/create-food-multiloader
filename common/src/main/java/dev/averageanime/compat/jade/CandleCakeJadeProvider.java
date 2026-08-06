package dev.averageanime.compat.jade;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.cake.CakeFoodBlock;
import dev.averageanime.block.type.cake.CakeCandleBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

enum CandleCakeJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "candle_cake_block");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlock() instanceof CakeCandleBlock candleCake)) return;
        Block parent = candleCake.getParentCake();
        if (!(parent instanceof CakeFoodBlock cake)) return;
        ItemStack stack = cake.getPieSliceItem().copyWithCount(cake.getMaxBites());
        JadeCompat.appendStackTooltip(tooltip, stack, accessor, false);
    }
}
