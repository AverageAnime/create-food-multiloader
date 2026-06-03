package net.averageanime.createfood.client.renderer;

import net.averageanime.createfood.client.tooltip.StorageContentsTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StorageContentsTooltipRenderer implements ClientTooltipComponent {

    private final List<ItemStack> items;

    public StorageContentsTooltipRenderer(StorageContentsTooltip data) {
        this.items = data.items();
    }

    @Override
    public int getHeight() {
        return items.isEmpty() ? 0 : 20;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return items.size() * 18;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics) {
        for (int i = 0; i < items.size(); i++) {
            guiGraphics.renderItem(items.get(i), x + i * 18, y + 1);
            guiGraphics.renderItemDecorations(font, items.get(i), x + i * 18, y + 1);
        }
    }
}
