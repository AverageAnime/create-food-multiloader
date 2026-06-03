package net.averageanime.createfood.item.interaction;

import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = CreateFood.ID)
public class HandcraftInteraction {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (tryHandcraft(player, event.getLevel())) {
            event.setCanceled(true);
        }
    }

    public static boolean tryHandcraft(ServerPlayer player, Level level) {
        if (!CreateFoodConfig.SERVER.enableHandcrafting.get()) return false;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand  = player.getOffhandItem();
        if (mainHand.isEmpty()) return false;

        Optional<CraftingRecipe> match = Optional.empty();
        boolean usedTwoSlot = false;

        if (!offHand.isEmpty()) {
            TransientCraftingContainer twoSlot = makeCraftingContainer(2, mainHand, offHand);
            match = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, twoSlot, level);
            if (match.isPresent()) usedTwoSlot = true;
        }

        if (match.isEmpty() && CreateFoodConfig.SERVER.handcraftingAllowSingle.get()) {
            TransientCraftingContainer oneSlot = makeCraftingContainer(1, mainHand);
            match = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, oneSlot, level);
        }

        if (match.isEmpty()) return false;

        boolean finalUsedTwoSlot = usedTwoSlot;
        TransientCraftingContainer container = usedTwoSlot
                ? makeCraftingContainer(2, mainHand, offHand)
                : makeCraftingContainer(1, mainHand);

        CraftingRecipe recipe = match.get();
        ItemStack result = recipe.assemble(container, level.registryAccess());
        if (result.isEmpty()) return false;
        if (!ConfigLogic.isHandcraftingAllowed(result)) return false;

        NonNullList<ItemStack> remainingItems = recipe.getRemainingItems(container);

        if (!player.isCreative()) {
            mainHand.shrink(1);
            if (finalUsedTwoSlot) offHand.shrink(1);
        }

        if (player.getMainHandItem().isEmpty()) {
            player.getInventory().setItem(player.getInventory().selected, result);
        } else if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }

        double handX = player.getX() + player.getLookAngle().x * 0.5;
        double handY = player.getY() + player.getEyeHeight(player.getPose()) - 0.4;
        double handZ = player.getZ() + player.getLookAngle().z * 0.5;

        if (level instanceof ServerLevel serverLevel && CreateFoodConfig.SERVER.handcraftingParticles.get()) {
            serverLevel.sendParticles(
                    ParticleTypes.POOF,
                    handX, handY, handZ,
                    5,
                    0.15, 0.15, 0.15,
                    0.0
            );
        }

        level.playSound(null, player.blockPosition(),
                SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 0.5F, 1.5F);
        return true;
    }

    private static TransientCraftingContainer makeCraftingContainer(int size, ItemStack... items) {
        TransientCraftingContainer container = new TransientCraftingContainer(
                new AbstractContainerMenu(null, -1) {
                    @Override public boolean stillValid(Player p) { return false; }
                    @Override public ItemStack quickMoveStack(Player p, int i) { return ItemStack.EMPTY; }
                }, size, 1);
        for (int i = 0; i < items.length && i < size; i++) {
            container.setItem(i, items[i].copy());
        }
        return container;
    }
}
