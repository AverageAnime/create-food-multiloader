package dev.averageanime.platform;

import dev.averageanime.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.block.type.blockentity.LargeBowlBlockEntity;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.config.ItemNutritionOverride;
import dev.averageanime.item.storage.StorageAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntUnaryOperator;

public interface Platform {

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    /**
     * True while a datagen run is in progress. Config-driven registration falls back to
     * {@link dev.averageanime.config.ConfigDefaults} in that case, so generated resources depend only on
     * committed source rather than on whatever the dev run directory's config happens to contain.
     */
    default boolean isRunningDataGen() { return false; }

    boolean isClient();

    boolean isServer();

    boolean isFabric();

    boolean isNeoforge();

    Path getConfigDir();

    default boolean isItemEnabled(String itemId) { return ConfigValues.isItemEnabled(itemId); }

    default List<? extends String> getCraftingRemainders() { return ConfigValues.getCraftingRemainders(); }

    default boolean isEggImpactRemainderEnabled() { return ConfigValues.isEggImpactRemainderEnabled(); }

    default String getCategoryEffectOverride(String categoryName) { return ConfigValues.getCategoryEffectOverride(categoryName); }

    default boolean isEffectDurationStacking() { return ConfigValues.isEffectDurationStacking(); }

    default int getMaxStackedEffectDuration() { return ConfigValues.getMaxStackedEffectDuration(); }

    default List<ItemEffectOverride> getItemOverrideEntries(String itemId) { return ConfigValues.getItemOverrideEntries(itemId); }

    @Nullable
    default ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId) {
        return ConfigValues.getItemEffectOverride(itemId, categoryOrEffectId);
    }

    @Nullable
    default ItemNutritionOverride getItemNutritionOverride(String itemId) { return ConfigValues.getItemNutritionOverride(itemId); }

    default boolean isGenericDisplayEnabled() { return ConfigValues.isGenericDisplayEnabled(); }

    default boolean isAlwaysDisplayUpright() { return ConfigValues.isAlwaysDisplayUpright(); }

    default boolean isCuttingBoardEnabled() { return ConfigValues.isCuttingBoardEnabled(); }

    default boolean isDisplayInteractionsEnabled() { return ConfigValues.isDisplayInteractionsEnabled(); }

    default boolean isBasinFluidItemsEnabled() { return ConfigValues.isBasinFluidItemsEnabled(); }

    default int getLargeBowlCapacityMb() { return ConfigValues.getLargeBowlCapacityMb(); }

    default boolean isGenericDisplayAllowed(ItemStack stack) { return ConfigValues.isGenericDisplayAllowed(stack); }

    default boolean isPumpkinPiePlacementEnabled() { return ConfigValues.isPumpkinPiePlacementEnabled(); }

    default boolean isCampfireCookingEnabled() { return ConfigValues.isCampfireCookingEnabled(); }

    default boolean isCampfireCookingRequireShift() { return ConfigValues.isCampfireCookingRequireShift(); }

    default boolean isCampfireCookingSticksOnly() { return ConfigValues.isCampfireCookingSticksOnly(); }

    default int getCampfireCookingHorizontalRange() { return ConfigValues.getCampfireCookingHorizontalRange(); }

    default int getCampfireCookingVerticalRange() { return ConfigValues.getCampfireCookingVerticalRange(); }

    default List<? extends String> getCampfireCookingExclude() { return ConfigValues.getCampfireCookingExclude(); }

    default List<? extends String> getCampfireCookingFilter() { return ConfigValues.getCampfireCookingFilter(); }

    default boolean isHandcraftingEnabled() { return ConfigValues.isHandcraftingEnabled(); }

    default boolean isHandcraftingAllowed(ItemStack result) { return ConfigValues.isHandcraftingAllowed(result); }

    default boolean isHandcraftingSingleEnabled() { return ConfigValues.isHandcraftingSingleEnabled(); }

    default boolean isHandcraftingParticlesEnabled() { return ConfigValues.isHandcraftingParticlesEnabled(); }

    default boolean isFilterInteractionsEnabled() { return ConfigValues.isFilterInteractionsEnabled(); }

    default List<String> getFilterInteractions() { return ConfigValues.getFilterInteractions(); }

    default List<? extends String> getFilterInteractionEntries() { return ConfigValues.getFilterInteractionEntries(); }

    default boolean isClothSackInventoryEnabled() { return ConfigValues.isClothSackInventoryEnabled(); }

    default boolean isRationBoxInventoryEnabled() { return ConfigValues.isRationBoxInventoryEnabled(); }

    default boolean isSackBlockIconsEnabled() { return ConfigValues.isSackBlockIconsEnabled(); }

    default boolean isStorageTooltipIconsEnabled() { return ConfigValues.isStorageTooltipIconsEnabled(); }

    default boolean isShiftRequiredForTooltips() { return ConfigValues.isShiftRequiredForTooltips(); }

    default boolean isCompatibilityEnabled() { return ConfigValues.isCompatibilityEnabled(); }

    default boolean isIngredientsEnabled() { return ConfigValues.isIngredientsEnabled(); }

    default List<? extends String> getCustomTooltips() { return ConfigValues.getCustomTooltips(); }

    default boolean isClothSackEatFromItem() { return ConfigValues.isClothSackEatFromItem(); }

    default boolean isClothSackStacking() { return ConfigValues.isClothSackStacking(); }

    default boolean isClothSackItemAllowed(ItemStack stack) { return ConfigValues.isClothSackItemAllowed(stack); }

    default boolean isRationBoxEatFromItemEnabled() { return ConfigValues.isRationBoxEatFromItemEnabled(); }

    default boolean isRationBoxStacking() { return ConfigValues.isRationBoxStacking(); }

    default boolean isRationBoxItemAllowed(ItemStack stack) { return ConfigValues.isRationBoxItemAllowed(stack); }

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