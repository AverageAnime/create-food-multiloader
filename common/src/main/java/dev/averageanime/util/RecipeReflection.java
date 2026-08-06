package dev.averageanime.util;

import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class RecipeReflection {

    private RecipeReflection() {}

    public static List<ItemStack> getRollableResults(Recipe<?> recipe, RandomSource random) {
        try {
            Method getRollable = recipe.getClass().getMethod("getRollableResults");
            List<?> rollable = (List<?>) getRollable.invoke(recipe);
            List<ItemStack> out = new ArrayList<>();
            for (Object entry : rollable) {
                float chance = invokeFloat(entry, "chance", "getChance");
                if (chance >= 1.0f || random.nextFloat() < chance) {
                    ItemStack stack = invokeStack(entry, "stack", "getStack");
                    if (!stack.isEmpty()) out.add(stack);
                }
            }
            if (!out.isEmpty()) return out;
        } catch (Exception ignored) {
        }

        ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
        return result.isEmpty() ? List.of() : List.of(result.copy());
    }

    public static float invokeFloat(Object target, String... methodNames) {
        for (String name : methodNames) {
            try { return (float) target.getClass().getMethod(name).invoke(target); }
            catch (NoSuchMethodException ignored) {}
            catch (Exception e) { return 1.0f; }
        }
        return 1.0f;
    }

    public static ItemStack invokeStack(Object target, String... methodNames) {
        for (String name : methodNames) {
            try { return (ItemStack) target.getClass().getMethod(name).invoke(target); }
            catch (NoSuchMethodException ignored) {}
            catch (Exception e) { return ItemStack.EMPTY; }
        }
        return ItemStack.EMPTY;
    }

    public static int invokeInt(Object target, int fallback, String... methodNames) {
        for (String name : methodNames) {
            try {
                Object value = target.getClass().getMethod(name).invoke(target);
                if (value instanceof Number number) return number.intValue();
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) { return fallback; }
        }
        return fallback;
    }

    public static Object invoke(Object target, String... methodNames) {
        for (String name : methodNames) {
            try { return target.getClass().getMethod(name).invoke(target); }
            catch (NoSuchMethodException ignored) {}
            catch (Exception e) { return null; }
        }
        return null;
    }
}
