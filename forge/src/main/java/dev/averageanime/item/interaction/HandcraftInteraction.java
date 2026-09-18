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
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
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

        TransientCraftingContainer input = craftingInput(mainHand);
        int inputSize = 1;
        Optional<CraftingRecipe> match = Optional.empty();

        if (!offHand.isEmpty()) {
            TransientCraftingContainer twoSlot = craftingInput(mainHand, offHand);
            match = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, twoSlot, level);
            if (match.isPresent()) {
                input = twoSlot;
                inputSize = 2;
            }
        }

        if (match.isEmpty() && Services.PLATFORM.isHandcraftingSingleEnabled()) {
            match = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);
        }

        if (match.isEmpty()) return false;

        CraftingRecipe recipe = match.get();
        ItemStack result = recipe.assemble(input, level.registryAccess());
        if (result.isEmpty()) return false;
        if (!isAllowedByFilter(result)) return false;

        NonNullList<ItemStack> remainingItems = recipe.getRemainingItems(input);
        ItemStack mainRemainder = remainingItems.get(0);
        ItemStack offRemainder = inputSize > 1 ? remainingItems.get(1) : ItemStack.EMPTY;

        if (!player.isCreative()) {
            mainHand.shrink(1);
            if (inputSize > 1) offHand.shrink(1);
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
            if (inputSize > 1) {
                giveRemainder(player, offRemainder, true);
            }
        }

        if (inputSize > 1) {
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
                SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
        return true;
    }

    private static TransientCraftingContainer craftingInput(ItemStack... stacks) {
        TransientCraftingContainer input = new TransientCraftingContainer(new AbstractContainerMenu(null, -1) {
            @Override
            public ItemStack quickMoveStack(net.minecraft.world.entity.player.Player player, int slot) {
                return ItemStack.EMPTY;
            }

            @Override
            public boolean stillValid(net.minecraft.world.entity.player.Player player) {
                return false;
            }
        }, stacks.length, 1);
        for (int i = 0; i < stacks.length; i++) {
            input.setItem(i, stacks[i]);
        }
        return input;
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