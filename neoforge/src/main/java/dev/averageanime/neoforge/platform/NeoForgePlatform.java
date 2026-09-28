package dev.averageanime.neoforge.platform;

import dev.averageanime.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.createfood.lib.block.GenericDisplayBlockEntity;
import dev.averageanime.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.createfood.lib.storage.StorageAccess;
import dev.averageanime.neoforge.block.BlockEntityRegistration;
import dev.averageanime.neoforge.block.DisplayBlockRegistration;
import dev.averageanime.createfood.lib.platform.AddonSource;
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
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforge.data.loading.DatagenModLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NeoForgePlatform implements Platform {

    /** {@code LoadingModList}, not {@code ModList}: the latter is empty until every mod is constructed. */
    @Override
    public boolean isModLoaded(String modId) {
        LoadingModList loading = LoadingModList.get();
        if (loading != null) return loading.getModFileById(modId) != null;
        ModList list = ModList.get();
        return list != null && list.isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isRunningDataGen() {
        return DatagenModLoader.isRunningDataGen();
    }

    /** {@code LoadingModList}, so this is safe from mixin plugins onward. */
    @Override
    public List<AddonSource> findModResources(String path) {
        String[] parts = path.split("/");
        List<AddonSource> found = new ArrayList<>();
        for (ModFileInfo info : LoadingModList.get().getModFiles()) {
            if (info == null || info.getMods().isEmpty()) continue;
            try {
                Path resource = info.getFile().findResource(parts);
                if (resource != null && Files.exists(resource)) {
                    found.add(new AddonSource(info.getMods().getFirst().getModId(), resource));
                }
            } catch (Exception ignored) {
                // A mod file that cannot be probed contributes nothing.
            }
        }
        return found;
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
    public Block getLargeBowlBlock() {
        return DisplayBlockRegistration.LARGE_BOWL_BLOCK.get();
    }

    @Override
    public Block getBottleBlock() {
        return DisplayBlockRegistration.BOTTLE_BLOCK.get();
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
    public dev.averageanime.block.type.blockentity.LargeBowlBlockEntity createLargeBowlBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.neoforge.block.type.blockentity.LargeBowlBlockEntity(pos, state);
    }
}
