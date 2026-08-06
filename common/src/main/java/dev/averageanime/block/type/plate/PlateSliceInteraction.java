package dev.averageanime.block.type.plate;

import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.platform.Services;
import dev.averageanime.util.RecipeReflection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PlateSliceInteraction {

    public static boolean couldSlice(Player player, Level level, InteractionHand hand,
                                     BlockPos pos, BlockState state) {
        if (!Services.PLATFORM.isCuttingBoardEnabled()) return false;
        Block block = state.getBlock();
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.isEmpty()) return false;

        if (block instanceof GenericDisplayPlateBlock) {
            if (!(level.getBlockEntity(pos) instanceof GenericDisplayBlockEntity be) || be.isEmpty()) return false;
            return !findCuttingResults(level, be.getDisplayedItem(), heldItem).isEmpty();
        }
        if (block instanceof FoodBlock foodBlock) {
            Item foodItem = foodBlock.displayItem.get();
            if (foodItem == null) return false;
            return !findCuttingResults(level, new ItemStack(foodItem), heldItem).isEmpty();
        }
        return false;
    }

    public static boolean trySlice(Player player, Level level, InteractionHand hand,
                                   BlockPos pos, BlockState state) {
        if (!Services.PLATFORM.isCuttingBoardEnabled()) return false;
        if (level.isClientSide()) return false;

        Block block = state.getBlock();

        if (block instanceof GenericDisplayPlateBlock) {
            GenericDisplayBlockEntity be = (GenericDisplayBlockEntity) level.getBlockEntity(pos);
            if (be == null || be.isEmpty()) return false;
            ItemStack displayedItem = be.getDisplayedItem();

            ItemStack heldItem = player.getItemInHand(hand);
            List<ItemStack> results = heldItem.isEmpty() ? List.of() : findCuttingResults(level, displayedItem, heldItem);
            if (results.isEmpty()) return false;

            be.takeDisplayedItem();
            double cx = pos.getX() + 0.5, cy = pos.getY() + 0.75, cz = pos.getZ() + 0.5;
            for (ItemStack result : results) {
                if (!result.isEmpty()) {
                    ItemEntity entity = new ItemEntity(level, cx, cy, cz, result.copy());
                    entity.setDefaultPickUpDelay();
                    level.addFreshEntity(entity);
                }
            }
            level.setBlock(pos, preserveFacing(Services.PLATFORM.getPlateBlock().defaultBlockState(), state), 3);
            if (!player.isCreative()) {
                heldItem.hurtAndBreak(1, (ServerLevel) level, (ServerPlayer) player, item -> {});
            }
            playSliceSound(level, pos);
            return true;
        }

        if (!(block instanceof FoodBlock foodBlock)) return false;

        Item foodItem = foodBlock.displayItem.get();
        if (foodItem == null) return false;

        ItemStack heldItem = player.getItemInHand(hand);
        ItemStack foodStack = new ItemStack(foodItem);
        List<ItemStack> results = heldItem.isEmpty() ? List.of() : findCuttingResults(level, foodStack, heldItem);
        if (results.isEmpty()) return false;

        double cx = pos.getX() + 0.5;
        double cy = pos.getY() + 0.75;
        double cz = pos.getZ() + 0.5;
        for (ItemStack result : results) {
            if (!result.isEmpty()) {
                ItemEntity entity = new ItemEntity(level, cx, cy, cz, result.copy());
                entity.setDefaultPickUpDelay();
                level.addFreshEntity(entity);
            }
        }

        Block emptyPlate = (block instanceof SmallPlateBlock)
                ? Services.PLATFORM.getSmallPlateBlock()
                : Services.PLATFORM.getPlateBlock();
        level.setBlock(pos, preserveFacing(emptyPlate.defaultBlockState(), state), 3);

        if (!player.isCreative()) {
            heldItem.hurtAndBreak(1, (ServerLevel) level, (ServerPlayer) player, item -> {});
        }

        playSliceSound(level, pos);
        return true;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static List<ItemStack> findCuttingResults(Level level, ItemStack foodStack, ItemStack toolStack) {
        try {
            RecipeType<?> cuttingType = BuiltInRegistries.RECIPE_TYPE.get(
                    ResourceLocation.fromNamespaceAndPath("farmersdelight", "cutting"));
            if (cuttingType == null) return List.of();

            Collection<RecipeHolder<?>> recipes =
                    (Collection) level.getRecipeManager().getAllRecipesFor((RecipeType) cuttingType);

            for (RecipeHolder<?> holder : recipes) {
                Recipe<?> recipe = holder.value();
                var ingredients = recipe.getIngredients();
                if (ingredients.isEmpty() || !ingredients.get(0).test(foodStack)) continue;
                if (!toolMatchesRecipe(toolStack, recipe)) continue;
                List<ItemStack> results = RecipeReflection.getRollableResults(recipe, level.random);
                if (!results.isEmpty()) return results;
            }
        } catch (Exception ignored) {
        }
        return List.of();
    }

    private static boolean toolMatchesRecipe(ItemStack tool, Object recipe) {
        try {
            Method getToolMethod = recipe.getClass().getMethod("getTool");
            Object toolResult = getToolMethod.invoke(recipe);
            if (toolResult == null) return true;

            if (toolResult instanceof Optional<?> opt) {
                if (opt.isEmpty()) return true;
                return testIngredient(opt.get(), tool);
            }
            if (toolResult instanceof List<?> list) {
                if (list.isEmpty()) return true;
                for (Object elem : list) {
                    if (testIngredient(elem, tool)) return true;
                }
                return false;
            }
            return testIngredient(toolResult, tool);
        } catch (NoSuchMethodException e) {
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    private static boolean testIngredient(Object ingredient, ItemStack tool) {
        try {
            if (ingredient instanceof Ingredient ing) return ing.test(tool);
            Method test = ingredient.getClass().getMethod("test", ItemStack.class);
            return (boolean) test.invoke(ingredient, tool);
        } catch (Exception e) {
            return true;
        }
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