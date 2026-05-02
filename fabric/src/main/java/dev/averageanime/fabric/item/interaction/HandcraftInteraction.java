package dev.averageanime.fabric.item.interaction;

import dev.averageanime.fabric.config.ModConfig;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;
import java.util.Optional;

public class HandcraftInteraction {

    public static void register() {
        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (hand != InteractionHand.MAIN_HAND) return InteractionResultHolder.pass(player.getItemInHand(hand));
            if (level.isClientSide()) return InteractionResultHolder.pass(player.getItemInHand(hand));
            if (!ModConfig.ENABLE_HANDCRAFTING.get()) return InteractionResultHolder.pass(player.getItemInHand(hand));
            if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResultHolder.pass(player.getItemInHand(hand));

            ItemStack mainHand = serverPlayer.getMainHandItem();
            ItemStack offHand = serverPlayer.getOffhandItem();
            if (mainHand.isEmpty() || offHand.isEmpty()) return InteractionResultHolder.pass(mainHand);

            var recipeManager = level.getRecipeManager();
            CraftingInput input = CraftingInput.of(2, 1, List.of(mainHand, offHand));

            Optional<RecipeHolder<CraftingRecipe>> match = recipeManager
                    .getRecipeFor(RecipeType.CRAFTING, input, level);
            if (match.isEmpty()) return InteractionResultHolder.pass(mainHand);

            CraftingRecipe recipe = match.get().value();
            ItemStack result = recipe.assemble(input, level.registryAccess());
            if (result.isEmpty()) return InteractionResultHolder.pass(mainHand);
            if (!isAllowedByFilter(result)) return InteractionResultHolder.pass(mainHand);

            NonNullList<ItemStack> remainingItems = recipe.getRemainingItems(input);

            if (!serverPlayer.isCreative()) {
                mainHand.shrink(1);
                offHand.shrink(1);
            }

            for (ItemStack remaining : remainingItems) {
                if (!remaining.isEmpty()) {
                    if (!serverPlayer.getInventory().add(remaining)) {
                        serverPlayer.drop(remaining, false);
                    }
                }
            }

            if (!serverPlayer.getInventory().add(result)) {
                serverPlayer.drop(result, false);
            }

            level.playSound(null, serverPlayer.blockPosition(),
                    SoundEvents.CRAFTER_CRAFT, SoundSource.PLAYERS, 1.0F, 1.0F);

            return InteractionResultHolder.success(serverPlayer.getMainHandItem());
        });
    }

    private static boolean isAllowedByFilter(ItemStack result) {
        if (ModConfig.matchesFilterList(result, ModConfig.HANDCRAFTING_EXCLUDE.get())) return false;

        List<? extends String> filter = ModConfig.HANDCRAFTING_FILTER.get();
        if (filter.isEmpty()) return true;
        return ModConfig.matchesFilterList(result, filter);
    }
}
