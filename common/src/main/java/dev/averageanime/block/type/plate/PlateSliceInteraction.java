package dev.averageanime.block.type.plate;

import dev.averageanime.config.ConfigValues;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.createfood.lib.block.GenericDisplayBlockEntity;
import dev.averageanime.createfood.lib.recipe.BakingMatRecipes;
import dev.averageanime.createfood.lib.recipe.CuttingRecipes;
import dev.averageanime.createfood.lib.recipe.PressRecipes;
import dev.averageanime.createfood.lib.recipe.adapted.PlateRecipeView;
import dev.averageanime.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * A plate is a cutting board for any held tool, and with a rolling pin also a hand press and baking mat.
 * Recipes are read from the source mods when they are installed and from the adapted copies when they
 * are not (see the library's {@code RecipeAdapter}).
 *
 * <p>Order, first match wins: cutting with the held tool; then, for a {@code c:tools/rolling_pin} only and
 * while {@code enable_rolling_pin} is on, single-ingredient baking mat, pressing, and unheated one-item
 * compacting. Cutting recipes that name the pin as their tool (crumbs) are ordinary cutting, so they
 * follow {@code enable_cutting_board} alone. A pin may also do what a
 * knife does (raw beef to ground beef); that overlap is intended.
 *
 * <p>When the last serving yields one item that has a plate block of the same kind, the plate converts in
 * place instead of dropping it, as held-item conversions do; this is how salt dough becomes pizza dough.
 *
 * <p>An item named in {@code display_interactions_exclude} is left alone, as it is by display interactions.
 */
public class PlateSliceInteraction {

    private static final TagKey<Item> ROLLING_PIN =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/rolling_pin"));

    public static boolean couldSlice(Player player, Level level, InteractionHand hand,
                                     BlockPos pos, BlockState state) {
        if (!ConfigValues.isCuttingBoardEnabled()) return false;
        Block block = state.getBlock();
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.isEmpty()) return false;

        if (block instanceof GenericDisplayPlateBlock) {
            if (!(level.getBlockEntity(pos) instanceof GenericDisplayBlockEntity be) || be.isEmpty()) return false;
            return !candidates(level, be.getDisplayedItem(), heldItem).isEmpty();
        }
        if (block instanceof FoodBlock foodBlock) {
            Item foodItem = foodBlock.displayItem.get();
            if (foodItem == null) return false;
            return !candidates(level, new ItemStack(foodItem), heldItem).isEmpty();
        }
        return false;
    }

    public static boolean trySlice(Player player, Level level, InteractionHand hand,
                                   BlockPos pos, BlockState state) {
        if (!ConfigValues.isCuttingBoardEnabled()) return false;
        if (level.isClientSide()) return false;

        Block block = state.getBlock();
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.isEmpty()) return false;

        if (block instanceof GenericDisplayPlateBlock) {
            GenericDisplayBlockEntity be = (GenericDisplayBlockEntity) level.getBlockEntity(pos);
            if (be == null || be.isEmpty()) return false;
            List<ItemStack> results = rollFirst(level, be.getDisplayedItem(), heldItem);
            if (results.isEmpty()) return false;

            be.takeDisplayedItem();
            dropResults(level, pos, results);
            level.setBlock(pos, preserveFacing(Services.PLATFORM.getPlateBlock().defaultBlockState(), state), 3);
            finish(player, level, pos, heldItem);
            return true;
        }

        if (!(block instanceof FoodBlock foodBlock)) return false;
        Item foodItem = foodBlock.displayItem.get();
        if (foodItem == null) return false;

        List<ItemStack> results = rollFirst(level, new ItemStack(foodItem), heldItem);
        if (results.isEmpty()) return false;

        Block plated = state.getValue(FoodBlock.STACK_SIZE) == 1 && results.size() == 1 && results.getFirst().getCount() == 1
                ? foodBlock.displayBlockFor(results.getFirst().getItem()) : null;
        if (plated != null) {
            level.setBlock(pos, preserveFacing(plated.defaultBlockState().setValue(FoodBlock.STACK_SIZE, 1), state), 3);
        } else {
            dropResults(level, pos, results);
            // One serving per use; the last one reverts to whatever container the block sits in.
            foodBlock.removeServing(state, level, pos);
        }
        finish(player, level, pos, heldItem);
        return true;
    }

    /** Every recipe the held tool could run on this item, in the order they are tried. */
    private static List<PlateRecipeView> candidates(Level level, ItemStack food, ItemStack held) {
        List<PlateRecipeView> found = new ArrayList<>();
        if (ConfigValues.isDisplayInteractionExcluded(food)) return found;
        for (PlateRecipeView view : CuttingRecipes.all(level)) {
            if (view.accepts(food) && view.acceptsTool(held)) found.add(view);
        }
        if (held.is(ROLLING_PIN) && ConfigValues.isRollingPinEnabled()) {
            for (PlateRecipeView view : BakingMatRecipes.all(level)) {
                if (view.accepts(food) && view.acceptsTool(held)) found.add(view);
            }
            for (PlateRecipeView view : PressRecipes.pressing(level)) {
                if (view.accepts(food)) found.add(view);
            }
            for (PlateRecipeView view : PressRecipes.compacting(level)) {
                if (view.accepts(food)) found.add(view);
            }
        }
        return found;
    }

    /** Rolls candidates in order; one whose chances all miss falls through to the next. */
    private static List<ItemStack> rollFirst(Level level, ItemStack food, ItemStack held) {
        for (PlateRecipeView view : candidates(level, food, held)) {
            List<ItemStack> results = view.roll(level.random);
            if (!results.isEmpty()) return results;
        }
        return List.of();
    }

    private static void dropResults(Level level, BlockPos pos, List<ItemStack> results) {
        double cx = pos.getX() + 0.5, cy = pos.getY() + 0.75, cz = pos.getZ() + 0.5;
        for (ItemStack result : results) {
            if (result.isEmpty()) continue;
            ItemEntity entity = new ItemEntity(level, cx, cy, cz, result.copy());
            entity.setDefaultPickUpDelay();
            level.addFreshEntity(entity);
        }
    }

    private static void finish(Player player, Level level, BlockPos pos, ItemStack heldItem) {
        if (!player.isCreative()) {
            heldItem.hurtAndBreak(1, (ServerLevel) level, (ServerPlayer) player, item -> {});
        }
        playSliceSound(level, pos);
    }

    private static BlockState preserveFacing(BlockState target, BlockState source) {
        if (target.hasProperty(BlockStateProperties.HORIZONTAL_FACING)
                && source.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            return target.setValue(BlockStateProperties.HORIZONTAL_FACING,
                    source.getValue(BlockStateProperties.HORIZONTAL_FACING));
        }
        return target;
    }

    private static void playSliceSound(Level level, BlockPos pos) {
        SoundEvent sound = BuiltInRegistries.SOUND_EVENT
                .getOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "block.cutting_board.knife_cut"))
                .orElse(SoundEvents.WOOL_HIT);
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0f,
                0.9f + level.random.nextFloat() * 0.2f);
    }
}
