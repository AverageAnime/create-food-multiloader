package dev.averageanime.fabric.item;

import dev.averageanime.fabric.CreateFood;
import dev.averageanime.fabric.config.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModTooltips {

    private static final String TOOLTIP_PREFIX = "tooltip.createfood.";
    private static final String TOOLTIP_SUFFIX = "_ingredient";

    public static Supplier<Boolean> SHIFT_DOWN = () -> false;

    private static Map<String, String[]> customTooltipMap = null;

    public static void addTooltip(List<Component> tooltips, String modName, String... ingredientLines) {
        boolean shouldShow = !ModConfig.isShiftRequiredForTooltips() || SHIFT_DOWN.get();

        if (modName != null && ModConfig.isCompatibilityEnabled()) {
            tooltips.add(Component.translatable(modName).withStyle(ChatFormatting.BLUE));
        }

        if (shouldShow) {
            if (ingredientLines != null && ingredientLines.length > 0 && ModConfig.isIngredientsEnabled()) {
                tooltips.add(Component.translatable("tooltip.createfood.ingredients")
                        .withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
                for (String line : ingredientLines) {
                    if (line != null && !line.isEmpty()) {
                        tooltips.add(Component.translatable(line).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        } else {
            if (modName == null && ModConfig.isShiftRequiredForTooltips()) {
                tooltips.add(Component.translatable("tooltip.createfood.hold").withStyle(ChatFormatting.DARK_GRAY)
                        .append(Component.translatable("tooltip.createfood.shift").withStyle(ChatFormatting.GRAY))
                        .append(Component.translatable("tooltip.createfood.info").withStyle(ChatFormatting.DARK_GRAY))
                );
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
        }
    }

    private static void loadCustomTooltips() {
        if (customTooltipMap != null) return;

        customTooltipMap = new HashMap<>();
        List<? extends String> configEntries = ModConfig.CUSTOM_TOOLTIPS.get();

        for (String entry : configEntries) {
            try {
                String[] parts = entry.split("\\|", 2);
                if (parts.length < 2) {
                    CreateFood.LOGGER.warn("Invalid custom tooltip entry (not enough parts): {}", entry);
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
                        if (!shortKey.isEmpty()) {
                            tooltipKeys[i] = TOOLTIP_PREFIX + shortKey + TOOLTIP_SUFFIX;
                        }
                    }
                }

                customTooltipMap.put(itemId, tooltipKeys);
            } catch (Exception e) {
                CreateFood.LOGGER.error("Failed to parse custom tooltip entry: {}", entry, e);
            }
        }
    }
}
