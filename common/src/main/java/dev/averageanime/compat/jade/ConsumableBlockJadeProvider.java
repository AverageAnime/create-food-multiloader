package dev.averageanime.compat.jade;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.misc.ConsumableBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

enum ConsumableBlockJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "consumable_block");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlock() instanceof ConsumableBlock consumable)) return;
        BlockState state = accessor.getBlockState();
        int remaining = consumable.getMaxBites() - state.getValue(ConsumableBlock.BITES);
        if (remaining <= 0) return;
        ItemStack stack = consumable.getSliceItem().copyWithCount(remaining);
        JadeCompat.appendStackTooltip(tooltip, stack, accessor, false);
    }
}
