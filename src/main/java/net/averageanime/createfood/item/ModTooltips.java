package net.averageanime.createfood.item;

import net.averageanime.createfood.block.display.FoodBlock;
import net.averageanime.createfood.config.CreateFoodConfig;
import java.util.Arrays;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = net.averageanime.createfood.CreateFood.ID, value = Dist.CLIENT)
public class ModTooltips {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModTooltips.class);
    private static final String TOOLTIP_PREFIX = "tooltip.createfood.";
    private static final String TOOLTIP_SUFFIX = "_ingredient";

    private static Map<String, String[]> customTooltipMap = null;

    public static void addTooltip(List<Component> tooltips, String modName, String... ingredientLines) {
        boolean shouldShow = !CreateFoodConfig.CLIENT.requireShiftForTooltips.get() || Screen.hasShiftDown();

        if (modName != null && CreateFoodConfig.CLIENT.showCompatibility.get()) {
            tooltips.add(Component.translatable(modName).withStyle(ChatFormatting.BLUE));
        }

        if (shouldShow) {
            if (ingredientLines != null && ingredientLines.length > 0 && CreateFoodConfig.CLIENT.showIngredients.get()) {
                tooltips.add(Component.translatable("tooltip.createfood.ingredients")
                        .withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
                for (String line : ingredientLines) {
                    if (line != null && !line.isEmpty()) {
                        tooltips.add(Component.translatable(line).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        } else {
            if (modName == null && CreateFoodConfig.CLIENT.requireShiftForTooltips.get()) {
                tooltips.add(Component.translatable("tooltip.createfood.hold").withStyle(ChatFormatting.DARK_GRAY)
                        .append(Component.translatable("tooltip.createfood.shift").withStyle(ChatFormatting.GRAY))
                        .append(Component.translatable("tooltip.createfood.info").withStyle(ChatFormatting.DARK_GRAY)));
            }
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;
        if (CreateFoodConfig.CLIENT == null) return;
        onItemTooltip(stack, event.getToolTip());
    }

    public static void onItemTooltip(ItemStack stack, List<Component> lines) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (key == null) return;
        String itemId = key.toString();
        String path = key.getPath();
        loadCustomTooltips();
        if (customTooltipMap.containsKey(itemId)) {
            // Config-driven custom tooltips (for external mod items)
            String[] tooltipKeys = customTooltipMap.get(itemId);
            if (tooltipKeys != null && tooltipKeys.length > 0) {
                addTooltip(lines, null, tooltipKeys);
            }
        } else if (stack.getItem() instanceof BlockItem bi && bi.getBlock() instanceof FoodBlock fb) {
            // Display plate blocks inherit tooltips from the item they display.
            // Must come before the createfood namespace check — display blocks are also
            // createfood namespace, so they would short-circuit there and never reach here.
            net.minecraft.world.item.Item original = fb.displayItem.get();
            if (original != null && original != Items.AIR) {
                ResourceLocation originalKey = ForgeRegistries.ITEMS.getKey(original);
                if (originalKey != null) {
                    String[] tooltipKeys = customTooltipMap.get(originalKey.toString());
                    if (tooltipKeys != null && tooltipKeys.length > 0) {
                        addTooltip(lines, null, tooltipKeys);
                    } else if ("createfood".equals(originalKey.getNamespace())) {
                        String[] entry = BuiltinTooltips.TIPS.get(originalKey.getPath());
                        if (entry != null && entry.length >= 1) {
                            String compatKey = entry[0];
                            String[] ingredientKeys = entry.length > 1 ? Arrays.copyOfRange(entry, 1, entry.length) : new String[0];
                            addTooltip(lines, compatKey, ingredientKeys);
                        }
                    }
                }
            }
        } else if ("createfood".equals(key.getNamespace())) {
            // Built-in ingredient tooltips for CreateFood items
            String[] entry = BuiltinTooltips.TIPS.get(path);
            if (entry != null && entry.length >= 1) {
                String compatKey = entry[0]; // may be null
                String[] ingredientKeys = entry.length > 1 ? Arrays.copyOfRange(entry, 1, entry.length) : new String[0];
                addTooltip(lines, compatKey, ingredientKeys);
            }
        }
    }

    private static void loadCustomTooltips() {
        if (customTooltipMap != null) return;
        customTooltipMap = new HashMap<>();
        if (CreateFoodConfig.CLIENT == null) return;
        for (String entry : CreateFoodConfig.CLIENT.customTooltips.get()) {
            try {
                String[] parts = entry.split("\\|", 2);
                if (parts.length < 2) {
                    LOGGER.warn("Invalid custom tooltip entry (not enough parts): {}", entry);
                    continue;
                }
                String itemId = parts[0].trim();
                String tooltipsPart = parts[1].trim();
                String[] tooltipKeys = null;
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
                LOGGER.error("Failed to parse custom tooltip entry: {}", entry, e);
            }
        }
    }

    public static void invalidateCache() {
        customTooltipMap = null;
    }
}
