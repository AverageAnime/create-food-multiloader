package dev.averageanime.config;

import dev.averageanime.registry.FluidAmounts;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ConfigValues {

    // ── Client ────────────────────────────────────────────────────────────

    static Supplier<Boolean> ALWAYS_DISPLAY_UPRIGHT       = unbound();
    static Supplier<Boolean> REQUIRE_SHIFT_FOR_TOOLTIPS   = unbound();
    static Supplier<Boolean> SHOW_COMPATIBILITY           = unbound();
    static Supplier<Boolean> SHOW_INGREDIENTS             = unbound();
    static Supplier<Boolean> SHOW_SACK_BLOCK_ICONS        = unbound();
    static Supplier<Boolean> SHOW_STORAGE_TOOLTIP_ICONS   = unbound();

    static Supplier<List<? extends String>> CUSTOM_TOOLTIPS       = unbound();
    static Supplier<List<? extends String>> HIDE_ITEMS            = unbound();

    // ── Server ────────────────────────────────────────────────────────────

    static Supplier<Boolean> CAMPFIRE_COOKING_ENABLED       = unbound();
    static Supplier<Boolean> CAMPFIRE_COOKING_REQUIRE_SHIFT = unbound();
    static Supplier<Boolean> CAMPFIRE_COOKING_STICKS_ONLY   = unbound();
    static Supplier<Integer> CAMPFIRE_COOKING_HORIZONTAL_RANGE = unbound();
    static Supplier<Integer> CAMPFIRE_COOKING_VERTICAL_RANGE   = unbound();
    static Supplier<Boolean> CLOTH_SACK_ALLOW_FOOD          = unbound();
    static Supplier<Boolean> CLOTH_SACK_EAT_FROM_ITEM       = unbound();
    static Supplier<Boolean> CLOTH_SACK_INVENTORY_ENABLED   = unbound();
    static Supplier<Boolean> CLOTH_SACK_STACK               = unbound();
    static Supplier<Boolean> CREATE_BASIN_FLUID_ITEMS       = unbound();
    static Supplier<Boolean> CREATE_EXPANDED_BASIN_FLUIDS   = unbound();
    static Supplier<Boolean> ENABLE_CUTTING_BOARD           = unbound();
    static Supplier<Boolean> ENABLE_DISPLAY_INTERACTIONS    = unbound();
    static Supplier<Integer> LARGE_BOWL_CAPACITY_MB         = unbound();
    static Supplier<Boolean> ENABLE_EGG_IMPACT_REMAINDER    = unbound();
    static Supplier<Boolean> ENABLE_FILTER_INTERACTIONS     = unbound();
    static Supplier<Boolean> ENABLE_GENERIC_DISPLAY         = unbound();
    static Supplier<Boolean> ENABLE_HANDCRAFTING            = unbound();
    static Supplier<Boolean> ENABLE_PUMPKIN_PIE_PLACEMENT   = unbound();
    static Supplier<Boolean> HANDCRAFTING_ALLOW_SINGLE      = unbound();
    static Supplier<Boolean> HANDCRAFTING_PARTICLES         = unbound();
    static Supplier<Boolean> RATION_BOX_ALLOW_FOOD          = unbound();
    static Supplier<Boolean> RATION_BOX_EAT_FROM_ITEM       = unbound();
    static Supplier<Boolean> RATION_BOX_INVENTORY_ENABLED   = unbound();
    static Supplier<Boolean> RATION_BOX_STACK               = unbound();
    static Supplier<Boolean> STACK_EFFECT_DURATION          = unbound();
    static Supplier<Integer> MAX_STACKED_DURATION           = unbound();

    static Supplier<List<? extends String>> CAMPFIRE_COOKING_EXCLUDE   = unbound();
    static Supplier<List<? extends String>> CAMPFIRE_COOKING_FILTER    = unbound();
    static Supplier<List<? extends String>> CATEGORY_EFFECT_OVERRIDES  = unbound();
    static Supplier<List<? extends String>> CLOTH_SACK_EXCLUDE         = unbound();
    static Supplier<List<? extends String>> CLOTH_SACK_FILTER          = unbound();
    static Supplier<List<? extends String>> CRAFTING_REMAINDERS        = unbound();
    static Supplier<List<? extends String>> DISPLAY_INTERACTIONS_EXCLUDE = unbound();
    static Supplier<List<? extends String>> FILTER_INTERACTIONS        = unbound();
    static Supplier<List<? extends String>> GENERIC_DISPLAY_EXCLUDE    = unbound();
    static Supplier<List<? extends String>> HANDCRAFTING_EXCLUDE       = unbound();
    static Supplier<List<? extends String>> HANDCRAFTING_FILTER        = unbound();
    static Supplier<List<? extends String>> ITEM_EFFECT_OVERRIDES      = unbound();
    static Supplier<List<? extends String>> ITEM_NUTRITION_OVERRIDES   = unbound();
    static Supplier<List<? extends String>> RATION_BOX_EXCLUDE         = unbound();
    static Supplier<List<? extends String>> RATION_BOX_FILTER          = unbound();

    private ConfigValues() {}

    private static <T> Supplier<T> unbound() {
        return () -> { throw new IllegalStateException("Config not built yet"); };
    }

    @Nullable
    public static ItemEffectOverride getItemEffectOverride(String itemId, String categoryOrEffectId) {
        try { return ConfigParser.getItemEffectOverride(itemId, categoryOrEffectId, ITEM_EFFECT_OVERRIDES.get()); }
        catch (IllegalStateException ignored) { return null; }
    }

    public static List<ItemEffectOverride> getItemOverrideEntries(String itemId) {
        try { return ConfigParser.getItemOverrideEntries(itemId, ITEM_EFFECT_OVERRIDES.get()); }
        catch (IllegalStateException ignored) { return List.of(); }
    }

    @Nullable
    public static ItemNutritionOverride getItemNutritionOverride(String itemId) {
        try { return ConfigParser.getItemNutritionOverride(itemId, ITEM_NUTRITION_OVERRIDES.get()); }
        catch (IllegalStateException ignored) { return null; }
    }

    public static boolean isEffectDurationStacking() {
        try { return STACK_EFFECT_DURATION.get(); } catch (IllegalStateException e) { return true; }
    }

    public static int getMaxStackedEffectDuration() {
        try { return MAX_STACKED_DURATION.get(); } catch (IllegalStateException e) { return 36000; }
    }

    @Nullable
    public static String getCategoryEffectOverride(String categoryName) {
        try { return ConfigParser.getCategoryEffectOverride(categoryName, CATEGORY_EFFECT_OVERRIDES.get()); }
        catch (IllegalStateException ignored) { return null; }
    }

    public static List<? extends String> getItemNutritionOverrideEntries() {
        try { return ITEM_NUTRITION_OVERRIDES.get(); }
        catch (IllegalStateException ignored) { return List.of(); }
    }

    public static List<? extends String> getItemEffectOverrideEntries() {
        try { return ITEM_EFFECT_OVERRIDES.get(); }
        catch (IllegalStateException ignored) { return List.of(); }
    }

    public static boolean isItemEnabled(String itemId) {
        try { return ConfigParser.isItemEnabled(itemId, HIDE_ITEMS.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isDisplayBlockEnabled(String blockId) {
        try { return ConfigParser.isDisplayBlockEnabled(blockId, HIDE_ITEMS.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isFluidBucketEnabled(String bucketId) {
        try { return ConfigParser.isFluidBucketEnabled(bucketId, HIDE_ITEMS.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isAlwaysDisplayUpright()  { try { return ALWAYS_DISPLAY_UPRIGHT.get();  } catch (IllegalStateException e) { return false; } }
    public static boolean isCuttingBoardEnabled()   { try { return ENABLE_CUTTING_BOARD.get();    } catch (IllegalStateException e) { return true;  } }
    public static boolean isGenericDisplayEnabled() { try { return ENABLE_GENERIC_DISPLAY.get();   } catch (IllegalStateException e) { return true;  } }

    public static boolean isGenericDisplayAllowed(ItemStack stack) {
        try { return !ConfigParser.matchesFilterList(stack, GENERIC_DISPLAY_EXCLUDE.get()); }
        catch (IllegalStateException e) { return true; }
    }

    public static boolean isDisplayInteractionsEnabled() { try { return ENABLE_DISPLAY_INTERACTIONS.get(); } catch (IllegalStateException e) { return true; } }
    public static int getLargeBowlCapacityMb() { try { return LARGE_BOWL_CAPACITY_MB.get(); } catch (IllegalStateException e) { return FluidAmounts.BUCKET * 4; } }

    public static boolean isDisplayInteractionExcluded(ItemStack stack) {
        try { return ConfigParser.matchesFilterList(stack, DISPLAY_INTERACTIONS_EXCLUDE.get()); }
        catch (IllegalStateException e) { return ConfigParser.matchesFilterList(stack, ConfigDefaults.DISPLAY_INTERACTIONS_EXCLUDE_DEFAULT); }
    }

    public static boolean isPumpkinPiePlacementEnabled() { try { return ENABLE_PUMPKIN_PIE_PLACEMENT.get(); } catch (IllegalStateException e) { return false; } }
    public static boolean isCampfireCookingEnabled()       { return CAMPFIRE_COOKING_ENABLED.get(); }
    public static boolean isCampfireCookingRequireShift()  { try { return CAMPFIRE_COOKING_REQUIRE_SHIFT.get(); } catch (IllegalStateException e) { return true; } }
    public static boolean isCampfireCookingSticksOnly()    { return CAMPFIRE_COOKING_STICKS_ONLY.get(); }
    public static int getCampfireCookingHorizontalRange() { try { return CAMPFIRE_COOKING_HORIZONTAL_RANGE.get(); } catch (IllegalStateException e) { return 3; } }
    public static int getCampfireCookingVerticalRange()   { try { return CAMPFIRE_COOKING_VERTICAL_RANGE.get();   } catch (IllegalStateException e) { return 1; } }
    public static List<? extends String> getCampfireCookingExclude() { return CAMPFIRE_COOKING_EXCLUDE.get(); }
    public static List<? extends String> getCampfireCookingFilter()  { return CAMPFIRE_COOKING_FILTER.get(); }

    public static boolean isHandcraftingEnabled()          { return ENABLE_HANDCRAFTING.get(); }
    public static boolean isHandcraftingSingleEnabled()    { return HANDCRAFTING_ALLOW_SINGLE.get(); }
    public static boolean isHandcraftingParticlesEnabled() { return HANDCRAFTING_PARTICLES.get(); }

    public static boolean isHandcraftingAllowed(ItemStack result) {
        if (ConfigParser.matchesFilterList(result, HANDCRAFTING_EXCLUDE.get())) return false;
        List<? extends String> filter = HANDCRAFTING_FILTER.get();
        if (filter.isEmpty()) return true;
        return ConfigParser.matchesFilterList(result, filter);
    }

    public static boolean isFilterInteractionsEnabled() { return ENABLE_FILTER_INTERACTIONS.get(); }
    public static List<String> getFilterInteractions()  { return FILTER_INTERACTIONS.get().stream().map(String::valueOf).toList(); }
    public static List<? extends String> getFilterInteractionEntries() { return FILTER_INTERACTIONS.get(); }

    public static boolean isBasinFluidItemsEnabled() { try { return CREATE_BASIN_FLUID_ITEMS.get(); } catch (IllegalStateException e) { return true; } }

    public static boolean isExpandedBasinFluidsEnabled() { try { return CREATE_EXPANDED_BASIN_FLUIDS.get(); } catch (IllegalStateException e) { return true; } }

    public static boolean isEggImpactRemainderEnabled() { try { return ENABLE_EGG_IMPACT_REMAINDER.get(); } catch (IllegalStateException e) { return true; } }
    public static List<? extends String> getCraftingRemainders() { try { return CRAFTING_REMAINDERS.get(); } catch (IllegalStateException e) { return List.of(); } }

    public static boolean isShiftRequiredForTooltips() { try { return REQUIRE_SHIFT_FOR_TOOLTIPS.get(); } catch (IllegalStateException e) { return false; } }
    public static boolean isCompatibilityEnabled()     { try { return SHOW_COMPATIBILITY.get();         } catch (IllegalStateException e) { return true;  } }
    public static boolean isIngredientsEnabled()       { try { return SHOW_INGREDIENTS.get();           } catch (IllegalStateException e) { return true;  } }
    public static List<? extends String> getCustomTooltips() { return CUSTOM_TOOLTIPS.get(); }

    public static boolean isClothSackInventoryEnabled() { try { return CLOTH_SACK_INVENTORY_ENABLED.get(); } catch (IllegalStateException e) { return true;  } }
    public static boolean isClothSackEatFromItem()      { try { return CLOTH_SACK_EAT_FROM_ITEM.get();     } catch (IllegalStateException e) { return false; } }
    public static boolean isClothSackStacking()         { try { return CLOTH_SACK_STACK.get();             } catch (IllegalStateException e) { return true;  } }

    public static boolean isClothSackItemAllowed(ItemStack stack) {
        try { return ConfigParser.isClothSackItemAllowed(stack, CLOTH_SACK_EXCLUDE.get(), CLOTH_SACK_FILTER.get(), CLOTH_SACK_ALLOW_FOOD.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isRationBoxInventoryEnabled()   { try { return RATION_BOX_INVENTORY_ENABLED.get(); } catch (IllegalStateException e) { return true;  } }
    public static boolean isRationBoxEatFromItemEnabled() { try { return RATION_BOX_EAT_FROM_ITEM.get();     } catch (IllegalStateException e) { return true;  } }
    public static boolean isRationBoxStacking()           { try { return RATION_BOX_STACK.get();             } catch (IllegalStateException e) { return false; } }

    public static boolean isRationBoxItemAllowed(ItemStack stack) {
        try { return ConfigParser.isRationBoxItemAllowed(stack, RATION_BOX_EXCLUDE.get(), RATION_BOX_FILTER.get(), RATION_BOX_ALLOW_FOOD.get()); }
        catch (IllegalStateException ignored) { return true; }
    }

    public static boolean isStorageTooltipIconsEnabled() { try { return SHOW_STORAGE_TOOLTIP_ICONS.get(); } catch (IllegalStateException e) { return true; } }
    public static boolean isSackBlockIconsEnabled()      { try { return SHOW_SACK_BLOCK_ICONS.get();      } catch (IllegalStateException e) { return true; } }
}
