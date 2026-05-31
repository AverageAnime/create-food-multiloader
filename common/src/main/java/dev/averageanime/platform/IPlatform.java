package dev.averageanime.platform;

import dev.averageanime.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.config.ItemNutritionOverride;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IPlatform {

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    boolean isClient();

    boolean isServer();

    boolean isFabric();

    boolean isNeoforge();

    boolean isItemEnabled(String itemId);

    List<? extends String> getCraftingRemainders();

    boolean isEggImpactRemainderEnabled();

    String getCategoryEffectOverride(String categoryName);

    List<ItemEffectOverride> getItemOverrideEntries(String itemId);

    @Nullable
    ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId);

    @Nullable
    ItemNutritionOverride getItemNutritionOverride(String itemId);

    // ── Generic display plate config ──────────────────────────────────────────

    boolean isGenericPlatesEnabled();

    boolean isAlwaysDisplayUpright();

    boolean isCuttingBoardEnabled();

    boolean isGenericDisplayAllowed(ItemStack stack);

    boolean isPumpkinPiePlacementEnabled();

    // ── Handcrafting config ───────────────────────────────────────────────────

    boolean isHandcraftingEnabled();

    boolean isHandcraftingAllowed(ItemStack result);

    boolean isHandcraftingSingleEnabled();

    boolean isHandcraftingParticlesEnabled();

    // ── Cloth-filter interaction config ───────────────────────────────────────

    boolean isFilterInteractionsEnabled();

    List<String> getFilterInteractions();

    // ── Display block references (for BowlPlacementHandler) ──────────────────

    Block getPlateBlock();

    Block getSmallPlateBlock();

    // ── Storage block config ──────────────────────────────────────────────────

    boolean isClothSackInventoryEnabled();

    boolean isRationBoxInventoryEnabled();

    boolean isSackBlockIconsEnabled();

    boolean isStorageTooltipIconsEnabled();

    // ── Tooltip config ────────────────────────────────────────────────────────

    boolean isShiftRequiredForTooltips();

    boolean isCompatibilityEnabled();

    boolean isIngredientsEnabled();

    List<? extends String> getCustomTooltips();

    // ── Cloth-sack item config ────────────────────────────────────────────────

    boolean isClothSackEatFromItem();

    boolean isClothSackStacking();

    boolean isClothSackItemAllowed(ItemStack stack);

    BlockEntityType<?> getClothSackBlockEntityType();

    // ── Ration-box item config ────────────────────────────────────────────────

    boolean isRationBoxEatFromItemEnabled();

    boolean isRationBoxStacking();

    boolean isRationBoxItemAllowed(ItemStack stack);

    BlockEntityType<?> getRationBoxBlockEntityType();

    /** Opens the storage-item menu; Fabric uses ExtendedScreenHandlerFactory, NeoForge writes slotIndex to the extra data buffer. */
    void openStorageItemMenu(Player player, net.minecraft.world.inventory.MenuConstructor constructor,
                             net.minecraft.network.chat.Component title, int slotIndex);

    /** Opens the block-inventory menu at {@code pos}; Fabric casts to ServerPlayer, NeoForge writes BlockPos to the extra data buffer. */
    void openBlockInventoryMenu(Player player, MenuProvider provider, BlockPos pos);

    Block getGenericDisplayPlateBlock();

    // ── Block entity factories (needed by the common blocks) ──────────────────

    ClothSackBlockEntity createClothSackBlockEntity(BlockPos pos, BlockState state);

    RationBoxBlockEntity createRationBoxBlockEntity(BlockPos pos, BlockState state);

    GenericDisplayPlateBlockEntity createGenericDisplayPlateBlockEntity(BlockPos pos, BlockState state);
}