package dev.averageanime.neoforge.item.interaction;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.config.ModConfig;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = CommonClass.ID)
public class HandcraftInteraction {

    private static boolean isAllowedByFilter(ItemStack result) {
        List<? extends String> filter = ModConfig.HANDCRAFTING_FILTER.get();
        if (filter.isEmpty()) return true;

        String resultModId  = result.getItem().builtInRegistryHolder().key().location().getNamespace();
        String resultItemId = result.getItem().builtInRegistryHolder().key().location().toString();

        for (String entry : filter) {
            if (entry.startsWith("mod:") && entry.substring(4).equals(resultModId)) return true;
            if (entry.startsWith("item:") && entry.substring(5).equals(resultItemId)) return true;
            if (entry.startsWith("tag:")) {
                ResourceLocation tagLoc = ResourceLocation.tryParse(entry.substring(4));
                if (tagLoc != null && result.is(TagKey.create(Registries.ITEM, tagLoc))) return true;
            }
        }

        return false;
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (event.getLevel().isClientSide()) return;
        if (!ModConfig.ENABLE_HANDCRAFTING.get()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (mainHand.isEmpty() || offHand.isEmpty()) return;

        var recipeManager = event.getLevel().getRecipeManager();

        CraftingInput input = CraftingInput.of(2, 1, java.util.List.of(
                mainHand,
                offHand
        ));

        Optional<RecipeHolder<CraftingRecipe>> match = recipeManager
                .getRecipeFor(RecipeType.CRAFTING, input, event.getLevel());

        if (match.isEmpty()) return;

        CraftingRecipe recipe = match.get().value();
        ItemStack result = recipe.assemble(input, event.getLevel().registryAccess());

        if (result.isEmpty()) return;
        if (!isAllowedByFilter(result)) return;
        event.setCanceled(true);

        NonNullList<ItemStack> remainingItems = recipe.getRemainingItems(input);

        if (!player.isCreative()) {
            mainHand.shrink(1);
            offHand.shrink(1);
        }

        for (ItemStack remaining : remainingItems) {
            if (!remaining.isEmpty()) {
                if (!player.getInventory().add(remaining)) {
                    player.drop(remaining, false);
                }
            }
        }

        if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }

        player.level().playSound(null, player.blockPosition(),
                SoundEvents.CRAFTER_CRAFT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}