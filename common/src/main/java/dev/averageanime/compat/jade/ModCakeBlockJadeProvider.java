package dev.averageanime.compat.jade;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.cake.CakeFoodBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

enum ModCakeBlockJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "cake_block");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlock() instanceof CakeFoodBlock cake)) return;
        BlockState state = accessor.getBlockState();
        int remaining = cake.getMaxBites() - state.getValue(CakeBlock.BITES);
        if (remaining <= 0) return;
        ItemStack stack = cake.getPieSliceItem().copyWithCount(remaining);
        JadeCompat.appendStackTooltip(tooltip, stack, accessor, false);
    }
}
