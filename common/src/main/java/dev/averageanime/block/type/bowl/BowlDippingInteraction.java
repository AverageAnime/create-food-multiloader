package dev.averageanime.block.type.bowl;

import dev.averageanime.block.type.blockentity.LargeBowlBlockEntity;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.platform.Services;
import dev.averageanime.util.PlayerItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public final class BowlDippingInteraction {

    private BowlDippingInteraction() {}

    public static ItemInteractionResult tryInteract(Player player, Level level, InteractionHand hand,
                                                    BlockPos pos, LargeBowlBlockEntity bowl) {
        if (!Services.PLATFORM.isDisplayInteractionsEnabled()) return null;

        ItemStack held = player.getItemInHand(hand);
        if (held.isEmpty()) return null;

        DippingRecipes.Emptied emptied = DippingRecipes.findEmptyingRecipe(level, held);
        if (emptied != null && bowl.canAccept(emptied.fluid(), emptied.amount())) {
            if (level.isClientSide()) return ItemInteractionResult.SUCCESS;
            return fill(player, level, hand, pos, bowl, emptied);
        }

        if (bowl.isEmpty()) return null;

        if (canFillBucket(held, bowl)) {
            if (level.isClientSide()) return ItemInteractionResult.SUCCESS;
            return fillBucket(player, level, hand, pos, bowl);
        }

        if (ConfigValues.isDisplayInteractionExcluded(held)) return null;

        DippingRecipes.Dip dip = DippingRecipes.findFillingRecipe(level, held, bowl.getFluid(), bowl.getAmount());
        if (dip == null) return null;
        if (level.isClientSide()) return ItemInteractionResult.SUCCESS;
        return dip(player, level, pos, held, bowl, dip);
    }

    private static boolean canFillBucket(ItemStack held, LargeBowlBlockEntity bowl) {
        return held.getItem() == Items.BUCKET
                && bowl.getAmount() >= Services.PLATFORM.getLargeBowlCapacityMb()
                && filledBucketFor(bowl) != null;
    }

    @Nullable
    private static ItemStack filledBucketFor(LargeBowlBlockEntity bowl) {
        Item bucket = bowl.getFluid().getBucket();
        return bucket == null || bucket == Items.AIR ? null : new ItemStack(bucket);
    }

    private static ItemInteractionResult fillBucket(Player player, Level level, InteractionHand hand,
                                                    BlockPos pos, LargeBowlBlockEntity bowl) {
        ItemStack filled = filledBucketFor(bowl);
        if (filled == null || !bowl.drain(Services.PLATFORM.getLargeBowlCapacityMb())) return null;

        if (!player.isCreative()) {
            player.getItemInHand(hand).shrink(1);
            PlayerItems.giveToHandOrInventory(player, hand, filled);
        }

        level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
        return ItemInteractionResult.SUCCESS;
    }

    private static ItemInteractionResult fill(Player player, Level level, InteractionHand hand, BlockPos pos,
                                              LargeBowlBlockEntity bowl, DippingRecipes.Emptied emptied) {
        if (!bowl.fill(emptied.fluid(), emptied.amount())) return null;

        if (!player.isCreative()) {
            player.getItemInHand(hand).shrink(1);
            PlayerItems.giveToHandOrInventory(player, hand, emptied.container().copy());
        }

        level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
        return ItemInteractionResult.SUCCESS;
    }

    private static ItemInteractionResult dip(Player player, Level level, BlockPos pos, ItemStack held,
                                             LargeBowlBlockEntity bowl, DippingRecipes.Dip dip) {
        if (!bowl.drain(dip.fluidAmount())) return null;

        if (!player.isCreative()) held.shrink(1);
        PlayerItems.give(player, dip.result().copy());

        level.playSound(null, pos, dipSound(), SoundSource.BLOCKS, 0.9f,
                0.9f + level.random.nextFloat() * 0.2f);
        return ItemInteractionResult.SUCCESS;
    }

    public static SoundEvent dipSound() {
        return BuiltInRegistries.SOUND_EVENT
                .getOptional(ResourceLocation.fromNamespaceAndPath("create", "spout"))
                .orElse(SoundEvents.BREWING_STAND_BREW);
    }
}
