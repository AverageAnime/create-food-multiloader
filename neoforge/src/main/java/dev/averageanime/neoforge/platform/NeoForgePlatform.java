package dev.averageanime.neoforge.platform;

import dev.averageanime.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.item.storage.StorageAccess;
import dev.averageanime.neoforge.block.BlockEntityRegistration;
import dev.averageanime.neoforge.block.DisplayBlockRegistration;
import dev.averageanime.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.data.loading.DatagenModLoader;

import java.nio.file.Path;

public class NeoForgePlatform implements Platform {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isRunningDataGen() {
        return DatagenModLoader.isRunningDataGen();
    }

    @Override
    public boolean isClient() {
        return FMLLoader.getDist().isClient();
    }

    @Override
    public boolean isServer() {
        return FMLLoader.getDist().isDedicatedServer();
    }

    @Override
    public boolean isFabric() {
        return false;
    }

    @Override
    public boolean isNeoforge() {
        return true;
    }

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public Block getPlateBlock() {
        return DisplayBlockRegistration.PLATE_BLOCK.get();
    }

    @Override
    public Block getSmallPlateBlock() {
        return DisplayBlockRegistration.SMALL_PLATE_BLOCK.get();
    }

    @Override
    public Block getBowlBlock() {
        return DisplayBlockRegistration.BOWL_BLOCK.get();
    }

    @Override
    public Block getSmallBowlBlock() {
        return DisplayBlockRegistration.SMALL_BOWL_BLOCK.get();
    }

    @Override
    public StorageAccess createStorageInventory(int size,
                                                     java.util.function.IntUnaryOperator slotLimit, java.util.function.BiPredicate<Integer, net.minecraft.world.item.ItemStack> validator) {
        return new dev.averageanime.neoforge.item.storage.StorageInventory(size, slotLimit, validator);
    }

    @Override
    public BlockEntityType<?> getClothSackBlockEntityType() {
        return BlockEntityRegistration.CLOTH_SACK.get();
    }

    @Override
    public BlockEntityType<?> getRationBoxBlockEntityType() {
        return BlockEntityRegistration.RATION_BOX.get();
    }

    @Override
    public void openStorageItemMenu(Player player, MenuConstructor constructor,
                                    Component title, int slotIndex) {
        player.openMenu(new SimpleMenuProvider(constructor, title), buf -> buf.writeInt(slotIndex));
    }

    @Override
    public void openBlockInventoryMenu(Player player, MenuProvider provider, BlockPos pos) {
        player.openMenu(provider, buf -> buf.writeBlockPos(pos));
    }

    @Override
    public Block getGenericDisplayPlateBlock() {
        return DisplayBlockRegistration.GENERIC_DISPLAY_PLATE_BLOCK.get();
    }

    @Override
    public Block getGenericDisplayBowlBlock() {
        return DisplayBlockRegistration.GENERIC_DISPLAY_BOWL_BLOCK.get();
    }

    @Override
    public ClothSackBlockEntity createClothSackBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.neoforge.block.type.blockentity.ClothSackBlockEntity(pos, state);
    }

    @Override
    public RationBoxBlockEntity createRationBoxBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.neoforge.block.type.blockentity.RationBoxBlockEntity(pos, state);
    }

    @Override
    public GenericDisplayBlockEntity createGenericDisplayPlateBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.neoforge.block.type.blockentity.GenericDisplayBlockEntity(pos, state);
    }

    @Override
    public dev.averageanime.block.type.blockentity.SmallBowlBlockEntity createSmallBowlBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.neoforge.block.type.blockentity.SmallBowlBlockEntity(pos, state);
    }
}
