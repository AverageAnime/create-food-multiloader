package dev.averageanime.platform;

import dev.averageanime.createfood.lib.platform.ModPlatform;
import dev.averageanime.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.createfood.lib.block.GenericDisplayBlockEntity;
import dev.averageanime.block.type.blockentity.LargeBowlBlockEntity;
import dev.averageanime.createfood.lib.storage.StorageAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiPredicate;
import java.util.function.IntUnaryOperator;

public interface Platform extends ModPlatform {

    Block getPlateBlock();

    Block getSmallPlateBlock();

    Block getBowlBlock();

    Block getLargeBowlBlock();

    Block getBottleBlock();

    StorageAccess createStorageInventory(int size, IntUnaryOperator slotLimit, BiPredicate<Integer, ItemStack> validator);

    BlockEntityType<?> getClothSackBlockEntityType();

    BlockEntityType<?> getRationBoxBlockEntityType();

    void openStorageItemMenu(Player player, net.minecraft.world.inventory.MenuConstructor constructor,
                             net.minecraft.network.chat.Component title, int slotIndex);

    void openBlockInventoryMenu(Player player, MenuProvider provider, BlockPos pos);

    Block getGenericDisplayPlateBlock();

    Block getGenericDisplayBowlBlock();

    ClothSackBlockEntity createClothSackBlockEntity(BlockPos pos, BlockState state);

    RationBoxBlockEntity createRationBoxBlockEntity(BlockPos pos, BlockState state);

    GenericDisplayBlockEntity createGenericDisplayPlateBlockEntity(BlockPos pos, BlockState state);

    LargeBowlBlockEntity createLargeBowlBlockEntity(BlockPos pos, BlockState state);
}