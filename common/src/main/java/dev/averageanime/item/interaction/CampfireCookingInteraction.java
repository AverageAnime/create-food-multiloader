package dev.averageanime.item.interaction;

import dev.averageanime.config.ConfigParser;
import dev.averageanime.item.remainder.CraftingRemainder;
import dev.averageanime.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CampfireCookingInteraction {

    private static final TagKey<Block> HEAT_SOURCES =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("farmersdelight", "heat_sources"));

    private record CookProgress(Item item, int ticks) {}

    private record HeatSourceCache(long tick, boolean nearHeatSource) {}

    private static final int HEAT_SOURCE_SCAN_INTERVAL = 10;

    private static final Map<UUID, CookProgress> PROGRESS = new HashMap<>();
    private static final Map<UUID, HeatSourceCache> HEAT_SOURCE_CACHE = new HashMap<>();

    public static void tickPlayer(ServerPlayer player, Level level) {
        if (!Services.PLATFORM.isCampfireCookingEnabled()) {
            clearProgress(player);
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.isEmpty()) {
            clearProgress(player);
            return;
        }

        if (Services.PLATFORM.isCampfireCookingRequireShift() && !player.isShiftKeyDown()) {
            clearProgress(player);
            return;
        }

        if (!isNearHeatSourceCached(player, level)) {
            clearProgress(player);
            return;
        }

        Optional<RecipeHolder<CampfireCookingRecipe>> recipeOpt =
                level.getRecipeManager().getRecipeFor(
                        RecipeType.CAMPFIRE_COOKING, new SingleRecipeInput(mainHand), level);
        if (recipeOpt.isEmpty()) {
            clearProgress(player);
            return;
        }

        CampfireCookingRecipe recipe = recipeOpt.get().value();
        ItemStack result = recipe.getResultItem(level.registryAccess());

        if (ConfigParser.matchesFilterList(result, Services.PLATFORM.getCampfireCookingExclude())) {
            clearProgress(player);
            return;
        }

        if (Services.PLATFORM.isCampfireCookingSticksOnly()) {
            String resultPath = result.getItem().builtInRegistryHolder().key().location().getPath();
            if (!resultPath.endsWith("_stick")) {
                clearProgress(player);
                return;
            }
        }

        List<? extends String> filter = Services.PLATFORM.getCampfireCookingFilter();
        if (!filter.isEmpty() && !ConfigParser.matchesFilterList(result, filter)) {
            clearProgress(player);
            return;
        }

        UUID id = player.getUUID();
        CookProgress prev = PROGRESS.get(id);
        int ticks = (prev != null && prev.item() == mainHand.getItem()) ? prev.ticks() : 0;
        ticks++;

        int cookTime = recipe.getCookingTime();
        if (ticks >= cookTime) {
            ItemStack output = result.copy();
            Item inputItem = mainHand.getItem();
            if (!player.isCreative()) mainHand.shrink(1);
            if (player.getMainHandItem().isEmpty()) {
                player.getInventory().setItem(player.getInventory().selected, output);
            } else if (!player.getInventory().add(output)) {
                player.drop(output, false);
            }
            if (!player.isCreative()) {
                Item remainderItem = CraftingRemainder.getRemainderFor(inputItem);
                if (remainderItem != null) {
                    ItemStack remainder = new ItemStack(remainderItem);
                    if (!player.getInventory().add(remainder)) {
                        player.drop(remainder, false);
                    }
                }
            }
            PROGRESS.remove(id);
            level.playSound(null, player.blockPosition(),
                    SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
                    0.8f, 0.8f + level.random.nextFloat() * 0.4f);
            if (level instanceof ServerLevel sl) {
                double[] hand = handPos(player);
                sl.sendParticles(ParticleTypes.FLAME, hand[0], hand[1], hand[2], 10, 0.1, 0.1, 0.1, 0.02);
            }
        } else {
            PROGRESS.put(id, new CookProgress(mainHand.getItem(), ticks));
            if (ticks % 20 == 0 && level instanceof ServerLevel sl) {
                double[] hand = handPos(player);
                sl.sendParticles(ParticleTypes.SMOKE, hand[0], hand[1], hand[2], 3, 0.05, 0.05, 0.05, 0.01);
            }
        }
    }

    public static void clearProgress(ServerPlayer player) {
        PROGRESS.remove(player.getUUID());
        HEAT_SOURCE_CACHE.remove(player.getUUID());
    }

    private static double[] handPos(ServerPlayer player) {
        float yawRad = (float) Math.toRadians(player.getYRot());
        double lookX = -Math.sin(yawRad);
        double lookZ =  Math.cos(yawRad);
        double rightX = -lookZ;
        double rightZ =  lookX;
        return new double[]{
            player.getX() + lookX * 0.5 + rightX * 0.35,
            player.getY() + player.getEyeHeight() - 0.5,
            player.getZ() + lookZ * 0.5 + rightZ * 0.35
        };
    }

    private static boolean isNearHeatSourceCached(ServerPlayer player, Level level) {
        UUID id = player.getUUID();
        long now = level.getGameTime();
        HeatSourceCache cached = HEAT_SOURCE_CACHE.get(id);
        if (cached != null && now - cached.tick() < HEAT_SOURCE_SCAN_INTERVAL) {
            return cached.nearHeatSource();
        }
        boolean result = isNearHeatSource(player, level);
        HEAT_SOURCE_CACHE.put(id, new HeatSourceCache(now, result));
        return result;
    }

    private static boolean isNearHeatSource(ServerPlayer player, Level level) {
        int hRange = Services.PLATFORM.getCampfireCookingHorizontalRange();
        int vRange = Services.PLATFORM.getCampfireCookingVerticalRange();
        BlockPos center = player.blockPosition();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = -hRange; x <= hRange; x++) {
            for (int y = -vRange; y <= vRange; y++) {
                for (int z = -hRange; z <= hRange; z++) {
                    if (level.getBlockState(cursor.setWithOffset(center, x, y, z)).is(HEAT_SOURCES)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}