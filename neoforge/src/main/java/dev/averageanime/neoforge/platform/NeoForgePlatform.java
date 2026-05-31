package dev.averageanime.neoforge.platform;

import dev.averageanime.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity;
import dev.averageanime.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.config.ItemNutritionOverride;
import dev.averageanime.neoforge.block.ModDisplayBlocks;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.platform.IPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NeoForgePlatform implements IPlatform {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
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

    @Override public boolean isItemEnabled(String itemId)               { return ModConfig.isItemEnabled(itemId); }
    @Override public List<? extends String> getCraftingRemainders()     { try { return ModConfig.CRAFTING_REMAINDERS.get(); } catch (IllegalStateException e) { return List.of(); } }
    @Override public boolean isEggImpactRemainderEnabled()              { try { return ModConfig.ENABLE_EGG_IMPACT_REMAINDER.get(); } catch (IllegalStateException e) { return true; } }

    @Override
    public String getCategoryEffectOverride(String categoryName) {
        return ModConfig.getCategoryEffectOverride(categoryName);
    }

    @Override
    public List<ItemEffectOverride> getItemOverrideEntries(String itemId) {
        return ModConfig.getItemOverrideEntries(itemId);
    }

    @Nullable
    @Override
    public ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId) {
        return ModConfig.getItemEffectOverride(itemId, categoryOrEffectId);
    }

    @Nullable
    @Override
    public ItemNutritionOverride getItemNutritionOverride(String itemId) {
        return ModConfig.getItemNutritionOverride(itemId);
    }

    @Override public boolean isPumpkinPiePlacementEnabled()                  { try { return ModConfig.ENABLE_PUMPKIN_PIE_PLACEMENT.get(); } catch (IllegalStateException e) { return false; } }
    @Override public boolean isGenericPlatesEnabled()                        { return ModConfig.isEnableGenericPlates(); }
    @Override public boolean isAlwaysDisplayUpright()                        { return ModConfig.isAlwaysDisplayUpright(); }
    @Override public boolean isCuttingBoardEnabled()                         { return ModConfig.isEnableCuttingBoard(); }
    @Override public boolean isGenericDisplayAllowed(ItemStack stack)        { return ModConfig.isGenericDisplayAllowed(stack); }

    @Override
    public boolean isHandcraftingEnabled() {
        return ModConfig.ENABLE_HANDCRAFTING.get();
    }

    @Override
    public boolean isHandcraftingSingleEnabled() {
        return ModConfig.HANDCRAFTING_ALLOW_SINGLE.get();
    }

    @Override
    public boolean isHandcraftingParticlesEnabled() {
        return ModConfig.HANDCRAFTING_PARTICLES.get();
    }

    @Override
    public boolean isHandcraftingAllowed(ItemStack result) {
        if (ModConfig.matchesFilterList(result, ModConfig.HANDCRAFTING_EXCLUDE.get())) return false;
        List<? extends String> filter = ModConfig.HANDCRAFTING_FILTER.get();
        if (filter.isEmpty()) return true;
        return ModConfig.matchesFilterList(result, filter);
    }

    @Override
    public boolean isFilterInteractionsEnabled() {
        return ModConfig.ENABLE_FILTER_INTERACTIONS.get();
    }

    @Override
    public List<String> getFilterInteractions() {
        return ModConfig.FILTER_INTERACTIONS.get().stream().map(String::valueOf).toList();
    }

    @Override
    public Block getPlateBlock() {
        return ModDisplayBlocks.PLATE_BLOCK.get();
    }

    @Override
    public Block getSmallPlateBlock() {
        return ModDisplayBlocks.SMALL_PLATE_BLOCK.get();
    }

    @Override
    public boolean isClothSackInventoryEnabled() {
        return ModConfig.isClothSackInventoryEnabled();
    }

    @Override
    public boolean isRationBoxInventoryEnabled() {
        return ModConfig.isRationBoxInventoryEnabled();
    }

    @Override
    public boolean isSackBlockIconsEnabled() {
        return ModConfig.isSackBlockIconsEnabled();
    }

    @Override
    public boolean isStorageTooltipIconsEnabled() {
        return ModConfig.isStorageTooltipIconsEnabled();
    }

    // ── Tooltip config ────────────────────────────────────────────────────────

    @Override public boolean isShiftRequiredForTooltips()              { return ModConfig.REQUIRE_SHIFT_FOR_TOOLTIPS.get(); }
    @Override public boolean isCompatibilityEnabled()                  { return ModConfig.SHOW_COMPATIBILITY.get(); }
    @Override public boolean isIngredientsEnabled()                    { return ModConfig.SHOW_INGREDIENTS.get(); }
    @Override public java.util.List<? extends String> getCustomTooltips() { return ModConfig.CUSTOM_TOOLTIPS.get(); }

    // ── Cloth-sack item config ────────────────────────────────────────────────

    @Override public boolean isClothSackEatFromItem()                  { return ModConfig.isClothSackEatFromItem(); }
    @Override public boolean isClothSackStacking()                     { return ModConfig.isClothSackStacking(); }
    @Override public boolean isClothSackItemAllowed(net.minecraft.world.item.ItemStack stack) { return ModConfig.isClothSackItemAllowed(stack); }
    @Override public net.minecraft.world.level.block.entity.BlockEntityType<?> getClothSackBlockEntityType() { return dev.averageanime.neoforge.block.ModBlockEntities.CLOTH_SACK.get(); }

    // ── Ration-box item config ────────────────────────────────────────────────

    @Override public boolean isRationBoxEatFromItemEnabled()           { return ModConfig.isRationBoxEatFromItemEnabled(); }
    @Override public boolean isRationBoxStacking()                     { return ModConfig.isRationBoxStacking(); }
    @Override public boolean isRationBoxItemAllowed(net.minecraft.world.item.ItemStack stack) { return ModConfig.isRationBoxItemAllowed(stack); }
    @Override public net.minecraft.world.level.block.entity.BlockEntityType<?> getRationBoxBlockEntityType() { return dev.averageanime.neoforge.block.ModBlockEntities.RATION_BOX.get(); }

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
    public ClothSackBlockEntity createClothSackBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.neoforge.block.type.blockentity.ClothSackBlockEntity(pos, state);
    }

    @Override
    public RationBoxBlockEntity createRationBoxBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.neoforge.block.type.blockentity.RationBoxBlockEntity(pos, state);
    }

    @Override
    public GenericDisplayPlateBlockEntity createGenericDisplayPlateBlockEntity(BlockPos pos, BlockState state) {
        return new dev.averageanime.neoforge.block.type.blockentity.GenericDisplayPlateBlockEntity(pos, state);
    }

    @Override
    public net.minecraft.world.level.block.Block getGenericDisplayPlateBlock() {
        return ModDisplayBlocks.GENERIC_DISPLAY_PLATE_BLOCK.get();
    }
}