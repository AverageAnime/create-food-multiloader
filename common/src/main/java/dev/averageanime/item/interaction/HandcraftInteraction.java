package dev.averageanime.item.interaction;

import dev.averageanime.platform.Services;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.WeakHashMap;

public class HandcraftInteraction {

    private static final Map<ServerPlayer, Long> recentTwoHandCraftTick = new WeakHashMap<>();

    public static boolean consumeRecentTwoHandCraft(ServerPlayer player, Level level) {
        Long tick = recentTwoHandCraftTick.get(player);
        if (tick != null && tick == level.getGameTime()) {
            recentTwoHandCraftTick.remove(player);
            return true;
        }
        return false;
    }

    public static boolean tryHandcraft(ServerPlayer player, Level level) {
        if (!Services.PLATFORM.isHandcraftingEnabled()) return false;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand  = player.getOffhandItem();
        if (mainHand.isEmpty()) return false;

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
        ItemStack mainRemainder = remainingItems.get(0);
        ItemStack offRemainder = input.size() > 1 ? remainingItems.get(1) : ItemStack.EMPTY;

        if (!player.isCreative()) {
            mainHand.shrink(1);
            if (input.size() > 1) offHand.shrink(1);
        }

        if (!player.isCreative() && !mainRemainder.isEmpty() && player.getMainHandItem().isEmpty()) {
            player.setItemInHand(InteractionHand.MAIN_HAND, mainRemainder);
            mainRemainder = ItemStack.EMPTY;
        }
        if (!player.isCreative() && !offRemainder.isEmpty() && player.getOffhandItem().isEmpty()) {
            player.setItemInHand(InteractionHand.OFF_HAND, offRemainder);
            offRemainder = ItemStack.EMPTY;
        }

        if (player.getMainHandItem().isEmpty()) {
            player.getInventory().setItem(player.getInventory().selected, result);
        } else if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }

        if (!player.isCreative()) {
            giveRemainder(player, mainRemainder, false);
            if (input.size() > 1) {
                giveRemainder(player, offRemainder, true);
            }
        }

        if (input.size() > 1) {
            recentTwoHandCraftTick.put(player, level.getGameTime());
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

    private static void giveRemainder(ServerPlayer player, ItemStack remainder, boolean offHand) {
        if (remainder.isEmpty()) return;
        if (offHand && player.getOffhandItem().isEmpty()) {
            player.setItemInHand(InteractionHand.OFF_HAND, remainder);
            return;
        }
        if (!player.getInventory().add(remainder)) {
            player.drop(remainder, false);
        }
    }

    public static boolean isAllowedByFilter(ItemStack result) {
        return Services.PLATFORM.isHandcraftingAllowed(result);
    }
}