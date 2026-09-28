package dev.averageanime.neoforge.compat.create;

import dev.averageanime.config.ConfigValues;
import dev.averageanime.block.type.bowl.BowlDippingInteraction;
import dev.averageanime.createfood.lib.recipe.DippingRecipes;
import dev.averageanime.util.PlayerItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

/** Pours filled containers into Create's basin and fills empty ones from it. */
public final class BasinFluidInteraction {

    private static final ResourceLocation BASIN_BLOCK_ENTITY =
            ResourceLocation.fromNamespaceAndPath("create", "basin");

    private BasinFluidInteraction() {}

    public static boolean tryInteract(Player player, Level level, InteractionHand hand, BlockPos pos) {
        if (!ConfigValues.isBasinFluidItemsEnabled()) return false;
        if (player.isShiftKeyDown()) return false;

        ItemStack held = player.getItemInHand(hand);
        if (held.isEmpty()) return false;

        // Buckets are left to vanilla's own basin bucket handling.
        if (held.getItem() instanceof BucketItem) return false;
        // Anything with its own fluid-handler capability goes through NeoForge's normal
        // capability-based fill/drain flow instead, to avoid handling it twice.
        if (held.getCapability(Capabilities.FluidHandler.ITEM) != null) return false;

        if (!isBasin(level, pos)) return false;

        IFluidHandler tank = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
        if (tank == null) return false;

        return tryEmptyInto(player, level, hand, pos, held, tank)
                || tryFillFrom(player, level, hand, pos, held, tank);
    }

    private static boolean isBasin(Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return false;
        return BASIN_BLOCK_ENTITY.equals(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(be.getType()));
    }

    private static boolean tryEmptyInto(Player player, Level level, InteractionHand hand, BlockPos pos,
                                        ItemStack held, IFluidHandler tank) {
        DippingRecipes.Emptied emptied = DippingRecipes.findEmptyingRecipe(level, held);
        if (emptied == null) return false;

        FluidStack poured = new FluidStack(emptied.fluid(), emptied.amount());
        if (tank.fill(poured, IFluidHandler.FluidAction.SIMULATE) != emptied.amount()) return false;
        if (level.isClientSide()) return true;

        tank.fill(poured, IFluidHandler.FluidAction.EXECUTE);
        if (!player.isCreative()) {
            consume(player, hand, emptied.container().copy());
        }
        level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
        return true;
    }

    private static boolean tryFillFrom(Player player, Level level, InteractionHand hand, BlockPos pos,
                                       ItemStack held, IFluidHandler tank) {
        for (int i = 0; i < tank.getTanks(); i++) {
            FluidStack contents = tank.getFluidInTank(i);
            if (contents.isEmpty()) continue;

            DippingRecipes.Dip dip =
                    DippingRecipes.findFillingRecipe(level, held, contents.getFluid(), contents.getAmount());
            if (dip == null) continue;

            FluidStack drained = new FluidStack(contents.getFluid(), dip.fluidAmount());
            if (tank.drain(drained, IFluidHandler.FluidAction.SIMULATE).getAmount() != dip.fluidAmount()) continue;
            if (level.isClientSide()) return true;

            tank.drain(drained, IFluidHandler.FluidAction.EXECUTE);
            if (!player.isCreative()) held.shrink(1);
            PlayerItems.give(player, dip.result().copy());
            level.playSound(null, pos, BowlDippingInteraction.dipSound(), SoundSource.BLOCKS, 0.9f,
                    0.9f + level.random.nextFloat() * 0.2f);
            return true;
        }
        return false;
    }

    private static void consume(Player player, InteractionHand hand, @Nullable ItemStack leftover) {
        player.getItemInHand(hand).shrink(1);
        if (leftover != null) PlayerItems.giveToHandOrInventory(player, hand, leftover);
    }
}
