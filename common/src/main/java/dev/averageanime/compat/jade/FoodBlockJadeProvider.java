package dev.averageanime.compat.jade;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.display.FoodBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

enum FoodBlockJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "food_block");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlock() instanceof FoodBlock foodBlock)) return;
        Item item = foodBlock.displayItem.get();
        if (item == null) return;
        BlockState state = accessor.getBlockState();
        int count = state.hasProperty(FoodBlock.STACK_SIZE) ? state.getValue(FoodBlock.STACK_SIZE) : 1;
        JadeCompat.appendStackTooltip(tooltip, new ItemStack(item, count), accessor, false);
    }
}
