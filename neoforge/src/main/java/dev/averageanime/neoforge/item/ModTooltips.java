package dev.averageanime.neoforge.item;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.config.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = CommonClass.ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModTooltips {

    private static final String TOOLTIP_PREFIX = "tooltip.createfood.";
    private static final String TOOLTIP_SUFFIX = "_ingredient";

    private static Map<String, String[]> customTooltipMap = null;

    public static void addTooltip(List<Component> tooltips, String modName, String... ingredientLines) {
        boolean shouldShow = !ModConfig.REQUIRE_SHIFT_FOR_TOOLTIPS.get() || Screen.hasShiftDown();

        if (modName != null && ModConfig.SHOW_COMPATIBILITY.get()) {
            tooltips.add(Component.translatable(modName).withStyle(ChatFormatting.BLUE));
        }

        if (shouldShow) {
            if (ingredientLines != null && ModConfig.SHOW_INGREDIENTS.get()) {
                tooltips.add(Component.translatable("tooltip.createfood.ingredients")
                        .withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
                for (String line : ingredientLines) {
                    if (line != null && !line.isEmpty()) {
                        tooltips.add(Component.translatable(line).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        } else {
            if (modName == null && ModConfig.REQUIRE_SHIFT_FOR_TOOLTIPS.get()) {
                tooltips.add(Component.translatable("tooltip.createfood.hold").withStyle(ChatFormatting.DARK_GRAY)
                        .append(Component.translatable("tooltip.createfood.shift").withStyle(ChatFormatting.GRAY))
                        .append(Component.translatable("tooltip.createfood.info").withStyle(ChatFormatting.DARK_GRAY))
                );
            }
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltips = event.getToolTip();
        String itemId = stack.getItem().builtInRegistryHolder().key().location().toString();

        loadCustomTooltips();
        if (customTooltipMap.containsKey(itemId)) {
            String[] tooltipKeys = customTooltipMap.get(itemId);
            if (tooltipKeys != null && tooltipKeys.length > 0) {
                addTooltip(tooltips, null, tooltipKeys);
            }
        }
    }

    private static void loadCustomTooltips() {
        if (customTooltipMap != null) {
            return;
        }

        customTooltipMap = new HashMap<>();
        List<? extends String> configEntries = ModConfig.CUSTOM_TOOLTIPS.get();

        for (String entry : configEntries) {
            try {
                String[] parts = entry.split("\\|", 2);
                if (parts.length < 2) {
                    CommonClass.LOGGER.warn("Invalid custom tooltip entry (not enough parts): {}", entry);
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
                CommonClass.LOGGER.error("Failed to parse custom tooltip entry: {}", entry, e);
            }
        }
    }
}