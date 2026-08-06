package dev.averageanime.fabric.platform;

import dev.averageanime.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.fabric.block.BlockEntityRegistration;
import dev.averageanime.fabric.block.DisplayBlockRegistration;
import dev.averageanime.item.storage.StorageAccess;
import dev.averageanime.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class FabricPlatform implements Platform {

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    @Override
    public boolean isFabric() {
        return true;
    }

    @Override
    public boolean isNeoforge() {
        return false;
    }

    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public Block getPlateBlock() {
        return DisplayBlockRegistration.PLATE_BLOCK;
    }

    @Override
    public Block getSmallPlateBlock() {
        return DisplayBlockRegistration.SMALL_PLATE_BLOCK;
    }

    @Override
    public Block getBowlBlock() {
        return DisplayBlockRegistration.BOWL_BLOCK;
    }

    @Override
    public Block getSmallBowlBlock() {
        return DisplayBlockRegistration.SMALL_BOWL_BLOCK;
    }

    @Override
    public StorageAccess createStorageInventory(int size,
                                                     java.util.function.IntUnaryOperator slotLimit, java.util.function.BiPredicate<Integer, net.minecraft.world.item.ItemStack> validator) {
        return new dev.averageanime.fabric.item.storage.StorageInventory(size, () -> slotLimit.applyAsInt(0), validator);
    }

    @Override
    public BlockEntityType<?> getClothSackBlockEntityType() {
        return BlockEntityRegistration.CLOTH_SACK;
    }

    @Override
    public BlockEntityType<?> getRationBoxBlockEntityType() {
        return BlockEntityRegistration.RATION_BOX;
    }

    @Override
    public void openStorageItemMenu(Player player, MenuConstructor constructor,
                                    Component title, int slotIndex) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new ExtendedScreenHandlerFactory<Integer>() {
                @Override public @NotNull Component getDisplayName() { return title; }
                @Override public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player p) {
                    return constructor.createMenu(id, inv, p);
                }
                @Override public Integer getScreenOpeningData(@NotNull ServerPlayer p) { return slotIndex; }
            });
        }
    }

    @Override
    public void openBlockInventoryMenu(Player player, MenuProvider provider, BlockPos pos) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(provider);
        }
    }

    @Override
    public Block getGenericDisplayBowlBlock() {
        return DisplayBlockRegistration.GENERIC_DISPLAY_BOWL_BLOCK;
    }

    @Override
    public Block getGenericDisplayPlateBlock() {
        return DisplayBlockRegistration.GENERIC_DISPLAY_PLATE_BLOCK;
    }

    @Override
    public ClothSackBlockEntity createClothSackBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.fabric.block.type.blockentity.ClothSackBlockEntity(pos, state);
    }

    @Override
    public RationBoxBlockEntity createRationBoxBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.fabric.block.type.blockentity.RationBoxBlockEntity(pos, state);
    }

    @Override
    public GenericDisplayBlockEntity createGenericDisplayPlateBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.fabric.block.type.blockentity.GenericDisplayBlockEntity(pos, state);
    }

    @Override
    public dev.averageanime.block.type.blockentity.SmallBowlBlockEntity createSmallBowlBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.fabric.block.type.blockentity.SmallBowlBlockEntity(pos, state);
    }
}
