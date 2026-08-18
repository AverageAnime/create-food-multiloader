package dev.averageanime.block.type.display;

import dev.averageanime.block.type.bowl.ApplicationRecipes;
import dev.averageanime.block.type.bowl.BowlDippingInteraction;
import dev.averageanime.block.type.bowl.DippingRecipes;
import dev.averageanime.item.remainder.CraftingRemainder;
import dev.averageanime.util.PlayerItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Combining a placed food with a held item, using recipes that already exist: the food's own emptying
 * recipe (taking the contents back out), a filling recipe (topping it up with a held fluid), a
 * two-ingredient crafting recipe, or Create's deploying recipes as a fallback.
 *
 * <p>Shared by {@link FoodBlock}, which tracks servings, and by block-form foods such as a placed cake,
 * which simply become the result's block.
 */
public final class FoodTransforms {

    private FoodTransforms() {}

    /**
     * @param convertsBlock false for taking the contents back out - the result is the placed food itself,
     *                      so there is nothing to convert into and the empty container must be left
     * @param reconcile     true only for Create's deploying recipes, which hand-author a container output
     *                      because Create ignores crafting remainders
     */
    public record Outcome(ItemStack result, List<ItemStack> leftovers, boolean convertsBlock, boolean reconcile) {}

    @Nullable
    public static Outcome resolve(Level level, ItemStack placedStack, ItemStack heldStack) {
        if (placedStack.isEmpty() || heldStack.isEmpty()) return null;

        // Taking the contents back out, paid for with the matching empty container. First, so an empty
        // bottle held at a placed drink cannot instead match some filling recipe.
        DippingRecipes.Emptied placedEmptied = DippingRecipes.findEmptyingRecipe(level, placedStack);
        if (placedEmptied != null && ItemStack.isSameItem(heldStack, placedEmptied.container())) {
            return new Outcome(placedStack.copyWithCount(1), List.of(), false, false);
        }

        // Topping the food up with the fluid out of a held container.
        DippingRecipes.Emptied heldEmptied = DippingRecipes.findEmptyingRecipe(level, heldStack);
        if (heldEmptied != null) {
            DippingRecipes.Dip dip = DippingRecipes.findFillingRecipe(
                    level, placedStack, heldEmptied.fluid(), heldEmptied.amount());
            // Exact amount only: a partial pour would destroy the rest of the container's contents.
            if (dip != null && dip.fluidAmount() == heldEmptied.amount()) {
                return new Outcome(dip.result(), List.of(heldEmptied.container()), true, false);
            }
        }

        Outcome crafted = resolveCrafting(level, placedStack, heldStack);
        if (crafted != null) return crafted;

        // Only reached for the handful of results that exist solely as deploying recipes.
        ApplicationRecipes.Applied applied = ApplicationRecipes.find(level, placedStack, heldStack);
        if (applied != null) return new Outcome(applied.result(), applied.leftovers(), true, true);

        return null;
    }

    @Nullable
    private static Outcome resolveCrafting(Level level, ItemStack placedStack, ItemStack heldStack) {
        Outcome forward = craftPair(level, placedStack, heldStack);
        // Shapeless recipes match either way round, but a shaped 2-wide one is order-sensitive.
        return forward != null ? forward : craftPair(level, heldStack, placedStack);
    }

    @Nullable
    private static Outcome craftPair(Level level, ItemStack first, ItemStack second) {
        CraftingInput input = CraftingInput.of(2, 1, List.of(first, second));
        Optional<RecipeHolder<CraftingRecipe>> match =
                level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);
        if (match.isEmpty()) return null;

        CraftingRecipe recipe = match.get().value();
        ItemStack result = recipe.assemble(input, level.registryAccess());
        if (result.isEmpty()) return null;

        // Vanilla already resolves crafting remainders here, so no reconciliation is needed.
        List<ItemStack> leftovers = new ArrayList<>();
        for (ItemStack leftover : recipe.getRemainingItems(input)) {
            if (!leftover.isEmpty()) leftovers.add(leftover.copy());
        }
        return new Outcome(result, leftovers, true, false);
    }

    /** The containers to hand back for one consumed held item. */
    public static List<ItemStack> containersFor(Outcome outcome, ItemStack heldBefore) {
        return outcome.reconcile() ? reconcileContainers(heldBefore, outcome.leftovers()) : outcome.leftovers();
    }

    /**
     * One consumed held item yields exactly one container. Create's deploying recipes list the container
     * manually because Create ignores crafting remainders, but those items also carry a real remainder for
     * hand use, so the duplicate copy is dropped and the remainder granted once.
     */
    private static List<ItemStack> reconcileContainers(ItemStack heldBefore, List<ItemStack> leftovers) {
        Item remainder = heldBefore.getItem().getCraftingRemainingItem();
        if (remainder == null || remainder == Items.AIR) {
            remainder = CraftingRemainder.getRemainderFor(heldBefore.getItem());
        }
        if (remainder == null || remainder == Items.AIR) return leftovers;

        List<ItemStack> out = new ArrayList<>();
        boolean suppressed = false;
        for (ItemStack leftover : leftovers) {
            if (!suppressed && leftover.getItem() == remainder) {
                suppressed = true;
                continue;
            }
            out.add(leftover);
        }
        out.add(new ItemStack(remainder));
        return out;
    }

    /**
     * Follows the container actually being handled rather than the block, so pouring a sauce bottle onto
     * a plate sounds like a bottle and squeezing a piping bag sounds like fluid, not like a generic item.
     */
    public static SoundEvent soundFor(Level level, ItemStack heldBefore, Outcome outcome, SoundEvent fallback) {
        // Taking contents out fills the held container; every other path empties one into the food.
        boolean fillingTheHeldContainer = !outcome.convertsBlock();
        // For a decant the held item IS the container; otherwise the container comes back as a leftover.
        Item container = outcome.leftovers().isEmpty()
                ? heldBefore.getItem()
                : outcome.leftovers().get(0).getItem();

        if (container == Items.GLASS_BOTTLE) {
            return fillingTheHeldContainer ? SoundEvents.BOTTLE_FILL : SoundEvents.BOTTLE_EMPTY;
        }
        if (container == Items.BUCKET || heldBefore.getItem() instanceof BucketItem) {
            return fillingTheHeldContainer ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_EMPTY;
        }
        // Anything else that holds fluid - a piping bag, a filled bowl, a dipped stick - pours rather than
        // clunks. An emptying recipe is what makes something a fluid vessel, so no per-item list is needed.
        if (container == Items.BOWL || DippingRecipes.findEmptyingRecipe(level, heldBefore) != null) {
            return BowlDippingInteraction.dipSound();
        }
        return fallback;
    }

    /** Hands over {@code times} copies of {@code prototype}, split to respect its max stack size. */
    public static void giveCopies(Player player, InteractionHand hand, ItemStack prototype, int times,
                                  boolean preferHand) {
        if (prototype.isEmpty() || times <= 0) return;

        int remaining = prototype.getCount() * times;
        int perStack = Math.max(1, prototype.getMaxStackSize());
        while (remaining > 0) {
            int count = Math.min(remaining, perStack);
            ItemStack stack = prototype.copyWithCount(count);
            if (preferHand) {
                PlayerItems.giveToHandOrInventory(player, hand, stack);
            } else {
                PlayerItems.give(player, stack);
            }
            remaining -= count;
        }
    }
}
