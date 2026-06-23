package dev.averageanime.item.interaction;

import dev.averageanime.CommonClass;
import dev.averageanime.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.List;

public class ClothFilterInteraction {

    public static boolean tryFilterInteraction(Player player, Level level) {
        if (!Services.PLATFORM.isFilterInteractionsEnabled()) return false;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand  = player.getOffhandItem();

        String mainHandId = getItemId(mainHand);
        String offHandId  = offHand.isEmpty() ? "none" : getItemId(offHand);

        for (Interaction interaction : loadInteractions()) {
            if (!mainHandId.equals(interaction.filterItem)) continue;

            boolean offHandMatches = interaction.offHandItem.equals("none")
                    ? offHand.isEmpty()
                    : offHandId.equals(interaction.offHandItem);
            if (!offHandMatches) continue;

            if (!player.isCreative()) {
                mainHand.shrink(1);
            }

            if (!interaction.filterResult.equals("none")) {
                ItemStack filterResult = new ItemStack(resolveItem(interaction.filterResult));
                if (!filterResult.isEmpty()) {
                    if (mainHand.isEmpty()) {
                        player.setItemInHand(InteractionHand.MAIN_HAND, filterResult);
                    } else {
                        giveOrDrop(player, filterResult);
                    }
                }
            }

            if (!interaction.offHandItem.equals("none") && !player.isCreative()) {
                offHand.shrink(1);
            }
            ItemStack containerResult = new ItemStack(resolveItem(interaction.containerResult));
            if (!containerResult.isEmpty()) {
                if (offHand.isEmpty()) {
                    player.setItemInHand(InteractionHand.OFF_HAND, containerResult);
                } else {
                    giveOrDrop(player, containerResult);
                }
            }

            level.playSound(null, player.blockPosition(),
                    SoundEvents.HONEY_BLOCK_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    private static List<Interaction> loadInteractions() {
        return Services.PLATFORM.getFilterInteractions().stream()
                .map(entry -> {
                    String[] parts = entry.split("\\|");
                    return new Interaction(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
                })
                .toList();
    }

    private record Interaction(
            String filterItem,
            String offHandItem,
            String filterResult,
            String containerResult
    ) {}

    private static String getItemId(ItemStack stack) {
        return stack.getItem().builtInRegistryHolder().key().location().toString();
    }

    private static Item resolveItem(String id) {
        try {
            return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
        } catch (Exception e) {
            CommonClass.LOGGER.error("Could not resolve item '{}'", id, e);
            return Items.AIR;
        }
    }

    private static void giveOrDrop(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}