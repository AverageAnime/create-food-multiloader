package dev.averageanime.item.interaction;

import dev.averageanime.platform.Services;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class HandcraftInteraction {

    /**
     * Attempts to handcraft using the player's main-hand + off-hand items.
     *
     * @return {@code true} if a craft was performed (platform caller should consume/cancel the event)
     */
    public static boolean tryHandcraft(ServerPlayer player, Level level) {
        if (!Services.PLATFORM.isHandcraftingEnabled()) return false;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand  = player.getOffhandItem();
        if (mainHand.isEmpty()) return false;

        // Default to single-ingredient input; upgrade to two-slot if both hands are full.
        CraftingInput input = CraftingInput.of(1, 1, List.of(mainHand));
        Optional<RecipeHolder<CraftingRecipe>> match = Optional.empty();

        if (!offHand.isEmpty()) {
            CraftingInput twoSlot = CraftingInput.of(2, 1, List.of(mainHand, offHand));
            match = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, twoSlot, level);
            if (match.isPresent()) input = twoSlot;
        }

        if (match.isEmpty() && Services.PLATFORM.isHandcraftingSingleEnabled()) {
            match = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);
        }

        if (match.isEmpty()) return false;

        CraftingRecipe recipe = match.get().value();
        ItemStack result = recipe.assemble(input, level.registryAccess());
        if (result.isEmpty()) return false;
        if (!isAllowedByFilter(result)) return false;

        NonNullList<ItemStack> remainingItems = recipe.getRemainingItems(input);

        if (!player.isCreative()) {
            mainHand.shrink(1);
            if (input.size() > 1) offHand.shrink(1);
        }

        if (player.getMainHandItem().isEmpty()) {
            player.getInventory().setItem(player.getInventory().selected, result);
        } else if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }

        double handX = player.getX() + player.getLookAngle().x * 0.5;
        double handY = player.getY() + player.getEyeHeight(player.getPose()) - 0.4;
        double handZ = player.getZ() + player.getLookAngle().z * 0.5;

        if (level instanceof ServerLevel serverLevel && Services.PLATFORM.isHandcraftingParticlesEnabled()) {
            serverLevel.sendParticles(
                    ParticleTypes.POOF,
                    handX, handY, handZ,
                    5,
                    0.15, 0.15, 0.15,
                    0.0
            );
        }

        level.playSound(null, player.blockPosition(),
                SoundEvents.CRAFTER_CRAFT, SoundSource.PLAYERS, 1.0F, 1.0F);
        return true;
    }

    public static boolean isAllowedByFilter(ItemStack result) {
        return Services.PLATFORM.isHandcraftingAllowed(result);
    }
}
