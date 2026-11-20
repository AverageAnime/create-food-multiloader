package dev.averageanime.block.type.display;

import dev.averageanime.createfood.lib.recipe.ApplicationRecipes;
import dev.averageanime.block.type.bowl.BowlDippingInteraction;
import dev.averageanime.createfood.lib.recipe.DippingRecipes;
import dev.averageanime.item.remainder.CraftingRemainder;
import dev.averageanime.createfood.lib.util.PlayerItems;
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
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class FoodConversion {

    private FoodConversion() {}

    /**
     * {@code convertsBlock} false when taking contents back out; {@code reconcile} true only for Create's deploying
     * recipes. {@code result} and {@code leftovers} are what one held item gives, and it pays for
     * {@code servingsPerHeld} placed servings: 1, except a container holding several fills (a 250 mB bottle on a
     * 125 mB recipe) or a crafting recipe taking several of the placed food (4 shortbread + a caramel piece).
     */
    public record Outcome(ItemStack result, List<ItemStack> leftovers, boolean convertsBlock, boolean reconcile,
                          int servingsPerHeld) {}

    /** {@code placedServings} is how many the placed block holds; 1 rules out the several-serving routes. */
    @Nullable
    public static Outcome resolve(Level level, ItemStack placedStack, ItemStack heldStack, int placedServings) {
        if (placedStack.isEmpty() || heldStack.isEmpty()) return null;

        // First, so an empty bottle at a placed drink cannot match a filling recipe instead.
        DippingRecipes.Emptied placedEmptied = DippingRecipes.findEmptyingRecipe(level, placedStack);
        if (placedEmptied != null && ItemStack.isSameItem(heldStack, placedEmptied.container())) {
            return new Outcome(placedStack.copyWithCount(1), List.of(), false, false, 1);
        }

        DippingRecipes.Emptied heldEmptied = DippingRecipes.findEmptyingRecipe(level, heldStack);
        DippingRecipes.Dip dip = heldEmptied == null ? null
                : DippingRecipes.findFillingRecipe(level, placedStack, heldEmptied.fluid(), heldEmptied.amount());
        if (dip != null && dip.fluidAmount() == heldEmptied.amount()) {
            return new Outcome(dip.result(), List.of(heldEmptied.container()), true, false, 1);
        }

        Outcome crafted = resolveCrafting(level, placedStack, heldStack);
        if (crafted != null) return crafted;

        ApplicationRecipes.Applied applied = ApplicationRecipes.find(level, placedStack, heldStack);
        if (applied != null) return new Outcome(applied.result(), applied.leftovers(), true, true, 1);

        // The several-serving routes come last, so nothing that converted one serving before resolves differently.
        // Both spend the held item only on whole multiples, so no fluid or ingredient is wasted.
        if (placedServings < 2) return null;

        if (dip != null && dip.fluidAmount() > 0 && heldEmptied.amount() % dip.fluidAmount() == 0) {
            int servings = heldEmptied.amount() / dip.fluidAmount();
            if (servings <= placedServings) {
                ItemStack result = dip.result().copyWithCount(dip.result().getCount() * servings);
                return new Outcome(result, List.of(heldEmptied.container()), true, false, servings);
            }
        }

        return craftSeveral(level, placedStack, heldStack, placedServings);
    }

    /** The smallest shapeless recipe of N placed servings plus the held item, N from 2 up to what the block holds. */
    @Nullable
    private static Outcome craftSeveral(Level level, ItemStack placedStack, ItemStack heldStack, int placedServings) {
        // A crafting grid holds eight of the placed food beside the held item.
        int most = Math.min(placedServings, 8);
        for (int servings = 2; servings <= most; servings++) {
            List<ItemStack> items = new ArrayList<>();
            for (int i = 0; i < servings; i++) items.add(placedStack.copyWithCount(1));
            items.add(heldStack.copyWithCount(1));
            CraftingInput input = CraftingInput.of(servings + 1, 1, items);

            // Shapeless only: a row of copies could otherwise match a one-row shaped pattern by position.
            for (RecipeHolder<CraftingRecipe> holder :
                    level.getRecipeManager().getRecipesFor(RecipeType.CRAFTING, input, level)) {
                if (!(holder.value() instanceof ShapelessRecipe recipe)) continue;
                ItemStack result = recipe.assemble(input, level.registryAccess());
                if (result.isEmpty()) continue;

                List<ItemStack> leftovers = new ArrayList<>();
                for (ItemStack leftover : recipe.getRemainingItems(input)) {
                    if (!leftover.isEmpty()) leftovers.add(leftover.copy());
                }
                return new Outcome(result, leftovers, true, false, servings);
            }
        }
        return null;
    }

    @Nullable
    private static Outcome resolveCrafting(Level level, ItemStack placedStack, ItemStack heldStack) {
        Outcome forward = craftPair(level, placedStack, heldStack);
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

        List<ItemStack> leftovers = new ArrayList<>();
        for (ItemStack leftover : recipe.getRemainingItems(input)) {
            if (!leftover.isEmpty()) leftovers.add(leftover.copy());
        }
        return new Outcome(result, leftovers, true, false, 1);
    }

    public static List<ItemStack> containersFor(Outcome outcome, ItemStack heldBefore) {
        return outcome.reconcile() ? reconcileContainers(heldBefore, outcome.leftovers()) : outcome.leftovers();
    }

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

    public static SoundEvent soundFor(Level level, ItemStack heldBefore, Outcome outcome, SoundEvent fallback) {
        boolean fillingTheHeldContainer = !outcome.convertsBlock();
        Item container = outcome.leftovers().isEmpty()
                ? heldBefore.getItem()
                : outcome.leftovers().get(0).getItem();

        if (container == Items.GLASS_BOTTLE) {
            return fillingTheHeldContainer ? SoundEvents.BOTTLE_FILL : SoundEvents.BOTTLE_EMPTY;
        }
        if (container == Items.BUCKET || heldBefore.getItem() instanceof BucketItem) {
            return fillingTheHeldContainer ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_EMPTY;
        }
        if (container == Items.BOWL || DippingRecipes.findEmptyingRecipe(level, heldBefore) != null) {
            return BowlDippingInteraction.dipSound();
        }
        return fallback;
    }

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
