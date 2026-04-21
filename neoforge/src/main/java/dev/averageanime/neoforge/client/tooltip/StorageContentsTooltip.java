package dev.averageanime.neoforge.client.tooltip;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import java.util.List;

public record StorageContentsTooltip(List<ItemStack> items) implements TooltipComponent {}
