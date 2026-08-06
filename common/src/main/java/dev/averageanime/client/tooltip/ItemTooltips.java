package dev.averageanime.client.tooltip;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTooltips {

    private static final String TOOLTIP_PREFIX = "tooltip.createfood.";
    private static final String TOOLTIP_SUFFIX = "_ingredient";

    private static Map<String, String[]> customTooltipMap = null;

    public static void addTooltip(List<Component> tooltips, String modName, String... ingredientLines) {
        boolean shouldShow = !Services.PLATFORM.isShiftRequiredForTooltips() || Screen.hasShiftDown();

        if (modName != null && Services.PLATFORM.isCompatibilityEnabled()) {
            tooltips.add(Component.translatable(modName).withStyle(ChatFormatting.BLUE));
        }

        if (shouldShow) {
            if (ingredientLines != null && ingredientLines.length > 0 && Services.PLATFORM.isIngredientsEnabled()) {
                tooltips.add(Component.translatable("tooltip.createfood.ingredients")
                        .withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
                for (String line : ingredientLines) {
                    if (line != null && !line.isEmpty()) {
                        tooltips.add(Component.translatable(line).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        } else {
            boolean hasIngredients = ingredientLines != null && ingredientLines.length > 0;
            if (hasIngredients && Services.PLATFORM.isShiftRequiredForTooltips()) {
                tooltips.add(Component.translatable("tooltip.createfood.hold").withStyle(ChatFormatting.DARK_GRAY)
                        .append(Component.translatable("tooltip.createfood.shift").withStyle(ChatFormatting.GRAY))
                        .append(Component.translatable("tooltip.createfood.info").withStyle(ChatFormatting.DARK_GRAY)));
            }
        }
    }

    public static void onItemTooltip(ItemStack stack, List<Component> lines) {
        String itemId = stack.getItem().builtInRegistryHolder().key().location().toString();
        loadCustomTooltips();
        if (customTooltipMap.containsKey(itemId)) {
            String[] tooltipKeys = customTooltipMap.get(itemId);
            if (tooltipKeys != null && tooltipKeys.length > 0) {
                addTooltip(lines, null, tooltipKeys);
            }
        } else if (stack.getItem() instanceof BlockItem bi && bi.getBlock() instanceof FoodBlock fb) {
            net.minecraft.world.item.Item original = fb.displayItem.get();
            if (original != null && original != Items.AIR) {
                String originalId = BuiltInRegistries.ITEM.getKey(original).toString();
                String[] tooltipKeys = customTooltipMap.get(originalId);
                if (tooltipKeys != null && tooltipKeys.length > 0) {
                    addTooltip(lines, null, tooltipKeys);
                }
            }
        }
    }

    private static void loadCustomTooltips() {
        if (customTooltipMap != null) return;
        customTooltipMap = new HashMap<>();
        for (String entry : Services.PLATFORM.getCustomTooltips()) {
            try {
                String[] parts = entry.split("\\|", 2);
                if (parts.length < 2) {
                    CreateFoodCommon.LOGGER.warn("Create: Food - Invalid custom tooltip entry (not enough parts): {}", entry);
                    continue;
                }
                String itemId = parts[0].trim();
                String[] tooltipKeys = null;
                String tooltipsPart = parts[1].trim();
                if (!tooltipsPart.isEmpty()) {
                    String[] shortKeys = tooltipsPart.split(",");
                    tooltipKeys = new String[shortKeys.length];
                    for (int i = 0; i < shortKeys.length; i++) {
                        String shortKey = shortKeys[i].trim();
                        if (!shortKey.isEmpty()) tooltipKeys[i] = TOOLTIP_PREFIX + shortKey + TOOLTIP_SUFFIX;
                    }
                }
                customTooltipMap.put(itemId, tooltipKeys);
            } catch (Exception e) {
                CreateFoodCommon.LOGGER.error("Create: Food - Failed to parse custom tooltip entry: {}", entry, e);
            }
        }
    }
}