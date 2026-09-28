package dev.averageanime.registry;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.createfood.lib.registry.RegistryLookup;
import dev.averageanime.registry.type.ItemEntry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/** Lazy item references, so a target can move between {@link ItemRegistry} and the game registry without breaking its references. */
public final class ItemLookup {

    private ItemLookup() {}

    public static Supplier<Item> byFullId(String itemId) {
        return RegistryLookup.byFullId(itemId);
    }

    public static Supplier<Item> byModId(String itemId) {
        return RegistryLookup.byModId(CreateFoodCommon.MOD_ID, itemId, id -> {
            try {
                return ItemEntry.getById(id).get();
            } catch (IllegalArgumentException | IllegalStateException notDeclaredHere) {
                return null;
            }
        });
    }
}
