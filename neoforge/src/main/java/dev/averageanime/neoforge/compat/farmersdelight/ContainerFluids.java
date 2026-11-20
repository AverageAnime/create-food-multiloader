package dev.averageanime.neoforge.compat.farmersdelight;

import dev.averageanime.createfood.lib.recipe.DippingRecipes;
import dev.averageanime.registry.FluidAmounts;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The fluid a single filled container holds, for display: buckets and other fluid-handler items first,
 * then emptying recipes, which are what give bottles and bowls a fluid at all. Those are Create's own
 * when it is installed and the adapted copies of them when it is not.
 *
 * <p>Results are cached per item, because a renderer asks every frame and the recipe route walks every
 * emptying recipe by reflection. Call {@link #clear()} whenever recipes change. Only potions are
 * special-cased: any other container whose fluid varies with its components shows the first fluid seen.
 */
public final class ContainerFluids {

    private static final Map<Item, Optional<FluidStack>> CACHE = new HashMap<>();

    private ContainerFluids() {}

    /** @return one item's worth of fluid, or null if the stack is not a filled container */
    @Nullable
    public static FluidStack of(Level level, ItemStack stack) {
        if (stack.isEmpty()) return null;

        // A potion's fluid depends on its contents component, so it cannot share a per-item cache entry.
        if (stack.is(Items.POTION)) {
            PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
            return contents != null && contents.is(Potions.WATER)
                    ? new FluidStack(Fluids.WATER, FluidAmounts.SERVING)
                    : null;
        }

        return CACHE.computeIfAbsent(stack.getItem(), item -> resolve(level, stack)).orElse(null);
    }

    public static void clear() {
        CACHE.clear();
    }

    private static Optional<FluidStack> resolve(Level level, ItemStack stack) {
        Optional<FluidStack> contained = FluidUtil.getFluidContained(stack.copyWithCount(1));
        if (contained.isPresent() && !contained.get().isEmpty()) return contained;

        DippingRecipes.Emptied emptied = DippingRecipes.findEmptyingRecipe(level, stack.copyWithCount(1));
        if (emptied == null) return Optional.empty();
        return Optional.of(new FluidStack(emptied.fluid(), emptied.amount()));
    }
}
