package dev.averageanime.compat.jade;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.item.storage.StorageAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import java.util.ArrayList;
import java.util.List;

enum ClothSackJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "cloth_sack");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity be = accessor.getBlockEntity();
        if (!(be instanceof ClothSackBlockEntity sack)) return;
        StorageAccess inventory = sack.inventory;
        IElementHelper helper = IElementHelper.get();
        List<IElement> icons = new ArrayList<>();
        for (int i = 0; i < inventory.getInventorySize(); i++) {
            ItemStack stack = inventory.getInventoryItem(i);
            if (!stack.isEmpty()) icons.add(helper.item(stack));
        }
        if (!icons.isEmpty()) tooltip.add(icons);
    }
}
