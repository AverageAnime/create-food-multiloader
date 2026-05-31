package dev.averageanime.client.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record StorageContentsTooltip(List<ItemStack> items) implements TooltipComponent {}
