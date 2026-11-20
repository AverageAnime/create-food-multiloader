package dev.averageanime.neoforge.compat.farmersdelight;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * What a Farmer's Delight cooking pot is shown to hold. The renderer and the bubble particles both read
 * this, so a pot bubbles exactly when it shows liquid.
 *
 * <p>A finished meal hides the ingredients entirely: while the meal display or output slot holds
 * anything, only that item counts, shown as its fluid if it is a fluid container and as the item
 * otherwise. Without a meal, each ingredient is shown as its fluid if it carries one -- merged by fluid
 * and kept in slot order, which is the order they are laid out across the surface -- and as an item if
 * not. The container slot is never shown: bowls waiting there are not in the pot.
 */
public final class CookingPotContents {

    private CookingPotContents() {}

    public static List<FluidStack> fluids(CookingPotBlockEntity pot) {
        List<FluidStack> fluids = new ArrayList<>();
        collect(pot, fluids, null);
        return fluids;
    }

    public static List<ItemStack> items(CookingPotBlockEntity pot) {
        List<ItemStack> items = new ArrayList<>();
        collect(pot, null, items);
        return items;
    }

    public static boolean hasFluid(CookingPotBlockEntity pot) {
        return !fluids(pot).isEmpty();
    }

    /** Either list may be null when the caller only wants the other. */
    private static void collect(CookingPotBlockEntity pot, List<FluidStack> fluids, List<ItemStack> items) {
        Level level = pot.getLevel();
        if (level == null) return;
        ItemStackHandler inventory = pot.getInventory();

        ItemStack meal = inventory.getStackInSlot(CookingPotBlockEntity.MEAL_DISPLAY_SLOT);
        if (meal.isEmpty()) meal = inventory.getStackInSlot(CookingPotBlockEntity.OUTPUT_SLOT);
        if (!meal.isEmpty()) {
            add(level, meal, fluids, items);
            return;
        }
        for (int slot = 0; slot < CookingPotBlockEntity.MEAL_DISPLAY_SLOT; slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (!stack.isEmpty()) add(level, stack, fluids, items);
        }
    }

    private static void add(Level level, ItemStack stack, List<FluidStack> fluids, List<ItemStack> items) {
        FluidStack fluid = total(level, stack);
        if (fluid == null) {
            if (items != null) items.add(stack);
        } else if (fluids != null) {
            merge(fluids, fluid);
        }
    }

    private static FluidStack total(Level level, ItemStack stack) {
        FluidStack one = ContainerFluids.of(level, stack);
        return one == null ? null : one.copyWithAmount(one.getAmount() * stack.getCount());
    }

    private static void merge(List<FluidStack> fluids, FluidStack fluid) {
        for (int i = 0; i < fluids.size(); i++) {
            FluidStack existing = fluids.get(i);
            if (FluidStack.isSameFluidSameComponents(existing, fluid)) {
                fluids.set(i, existing.copyWithAmount(existing.getAmount() + fluid.getAmount()));
                return;
            }
        }
        fluids.add(fluid);
    }
}
