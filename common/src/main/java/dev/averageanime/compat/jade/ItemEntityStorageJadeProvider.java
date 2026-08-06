package dev.averageanime.compat.jade;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.item.storage.StorageAccess;
import dev.averageanime.item.storage.StorageItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import java.util.ArrayList;
import java.util.List;

enum ItemEntityStorageJadeProvider implements IEntityComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "storage_item_entity");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof ItemEntity itemEntity)) return;
        ItemStack stack = itemEntity.getItem();
        if (!(stack.getItem() instanceof StorageItem storageItem)) return;

        StorageAccess inventory = storageItem.loadInventory(stack, accessor.getLevel().registryAccess());
        IElementHelper helper = IElementHelper.get();
        List<IElement> icons = new ArrayList<>();
        for (int i = 0; i < inventory.getInventorySize(); i++) {
            ItemStack contained = inventory.getInventoryItem(i);
            if (!contained.isEmpty()) icons.add(helper.item(contained));
        }
        if (!icons.isEmpty()) tooltip.add(icons);
    }
}
