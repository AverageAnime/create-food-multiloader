package dev.averageanime.block.type.bowl;

import dev.averageanime.config.ConfigValues;
import dev.averageanime.util.RecipeReflection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public final class DippingRecipes {

    private static final ResourceLocation FILLING_TYPE =
            ResourceLocation.fromNamespaceAndPath("create", "filling");
    private static final ResourceLocation EMPTYING_TYPE =
            ResourceLocation.fromNamespaceAndPath("create", "emptying");

    private DippingRecipes() {}

    public record Dip(int fluidAmount, ItemStack result) {}
    public record Emptied(Fluid fluid, int amount, ItemStack container) {}

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Nullable
    public static Dip findFillingRecipe(Level level, ItemStack held, Fluid stored, int storedMb) {
        if (held.isEmpty() || stored == Fluids.EMPTY || storedMb <= 0) return null;
        if (ConfigValues.isDippingExcluded(held)) return null;

        try {
            RecipeType<?> fillingType = BuiltInRegistries.RECIPE_TYPE.get(FILLING_TYPE);
            if (fillingType == null) return null;

            Collection<RecipeHolder<?>> recipes =
                    (Collection) level.getRecipeManager().getAllRecipesFor((RecipeType) fillingType);

            for (RecipeHolder<?> holder : recipes) {
                Recipe<?> recipe = holder.value();

                var ingredients = recipe.getIngredients();
                if (ingredients.isEmpty() || !ingredients.get(0).test(held)) continue;

                Object fluidIngredient = firstElement(RecipeReflection.invoke(recipe, "getFluidIngredients"));
                if (fluidIngredient == null || !fluidIngredientMatches(fluidIngredient, stored)) continue;

                int required = fluidAmount(fluidIngredient);
                if (required <= 0 || required > storedMb) continue;

                List<ItemStack> results = RecipeReflection.getRollableResults(recipe, level.random);
                if (results.isEmpty()) continue;

                ItemStack result = results.get(0);
                if (result.isEmpty()) continue;
                return new Dip(required, result.copy());
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Nullable
    public static Emptied findEmptyingRecipe(Level level, ItemStack container) {
        if (container.isEmpty()) return null;

        if (container.getItem() instanceof BucketItem bucket) {
            Fluid fluid = bucketFluid(bucket);
            if (fluid == null || fluid == Fluids.EMPTY) return null;
            return new Emptied(fluid, 1000, new ItemStack(Items.BUCKET));
        }

        try {
            RecipeType<?> emptyingType = BuiltInRegistries.RECIPE_TYPE.get(EMPTYING_TYPE);
            if (emptyingType == null) return null;

            Collection<RecipeHolder<?>> recipes =
                    (Collection) level.getRecipeManager().getAllRecipesFor((RecipeType) emptyingType);

            for (RecipeHolder<?> holder : recipes) {
                Recipe<?> recipe = holder.value();

                var ingredients = recipe.getIngredients();
                if (ingredients.isEmpty() || !ingredients.get(0).test(container)) continue;

                Object fluidResult = firstElement(RecipeReflection.invoke(recipe, "getFluidResults"));
                if (fluidResult == null) continue;

                Fluid fluid = readFluid(fluidResult);
                if (fluid == null || fluid == Fluids.EMPTY) continue;

                int amount = RecipeReflection.invokeInt(fluidResult, -1, "getAmount", "amount");
                if (amount <= 0) continue;

                List<ItemStack> results = RecipeReflection.getRollableResults(recipe, level.random);
                ItemStack leftover = results.isEmpty() ? ItemStack.EMPTY : results.get(0).copy();
                return new Emptied(fluid, amount, leftover);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static int fluidAmount(Object fluidIngredient) {
        return RecipeReflection.invokeInt(fluidIngredient, -1, "amount", "getAmount", "getRequiredAmount");
    }

    private static boolean fluidIngredientMatches(Object fluidIngredient, Fluid stored) {
        if (matchesAny(RecipeReflection.invoke(fluidIngredient,
                "getFluids", "getStacks", "getMatchingFluidStacks"), stored)) {
            return true;
        }

        Object inner = RecipeReflection.invoke(fluidIngredient, "ingredient", "getIngredient");
        if (inner != null) {
            if (matchesAny(RecipeReflection.invoke(inner,
                    "getStacks", "getFluids", "getMatchingFluidStacks"), stored)) {
                return true;
            }
            if (matchesTagField(inner, stored)) return true;
        }

        return matchesTagField(fluidIngredient, stored);
    }

    private static boolean matchesAny(@Nullable Object fluidStacks, Fluid stored) {
        if (fluidStacks == null) return false;

        if (fluidStacks.getClass().isArray()) {
            int length = Array.getLength(fluidStacks);
            for (int i = 0; i < length; i++) {
                if (readFluid(Array.get(fluidStacks, i)) == stored) return true;
            }
            return false;
        }

        if (fluidStacks instanceof Iterable<?> stacks) {
            for (Object stack : stacks) {
                if (readFluid(stack) == stored) return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static boolean matchesTagField(Object fluidIngredient, Fluid stored) {
        for (Class<?> type = fluidIngredient.getClass(); type != null && type != Object.class; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (!TagKey.class.isAssignableFrom(field.getType())) continue;
                try {
                    field.setAccessible(true);
                    if (field.get(fluidIngredient) instanceof TagKey<?> tag) {
                        return stored.builtInRegistryHolder().is((TagKey<Fluid>) tag);
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return false;
    }

    @Nullable
    private static Fluid readFluid(Object fluidStack) {
        return RecipeReflection.invoke(fluidStack, "getFluid") instanceof Fluid fluid ? fluid : null;
    }

    @Nullable
    private static Fluid bucketFluid(BucketItem bucket) {
        Object fluid = RecipeReflection.invoke(bucket, "getFluid");
        if (fluid instanceof Fluid resolved) return resolved;

        for (Class<?> type = bucket.getClass(); type != null && type != Object.class; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (!Fluid.class.isAssignableFrom(field.getType())) continue;
                try {
                    field.setAccessible(true);
                    if (field.get(bucket) instanceof Fluid resolved) return resolved;
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }

    @Nullable
    private static Object firstElement(@Nullable Object maybeList) {
        if (maybeList instanceof List<?> list) return list.isEmpty() ? null : list.get(0);
        if (maybeList instanceof Collection<?> collection) return collection.stream().findFirst().orElse(null);
        return null;
    }
}
