package dev.averageanime.compat.jade;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

enum PlateJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "generic_display");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public IElement getIcon(BlockAccessor accessor, IPluginConfig config, IElement currentIcon) {
        BlockEntity be = accessor.getBlockEntity();
        if (!(be instanceof GenericDisplayBlockEntity plate)) return currentIcon;
        ItemStack displayed = plate.getDisplayedItemForRender();
        if (displayed.isEmpty()) return currentIcon;
        return IElementHelper.get().item(displayed);
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity be = accessor.getBlockEntity();
        if (!(be instanceof GenericDisplayBlockEntity plate)) return;
        JadeCompat.appendStackTooltip(tooltip, plate.getDisplayedItemForRender(), accessor, true);
    }
}
