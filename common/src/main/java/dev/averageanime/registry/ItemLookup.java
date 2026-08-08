package dev.averageanime.registry;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.registry.type.ItemEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

/**
 * Lazy item references for slices and craft remainders.
 * <p>
 * These have to resolve late and tolerate either source: {@link ItemEntry#getById} only knows ids still
 * declared in {@link ItemRegistry}, while anything defined through {@code items.item} exists only in the
 * game registry. Resolving through here means a hardcoded block or item can point at a target that has
 * been moved out to the config — and vice versa — without breaking.
 */
public final class ItemLookup {

    private ItemLookup() {}

    /** Resolves a full item id ({@code mod:path}), falling back to {@link Items#BARRIER}. */
    public static Supplier<Item> byFullId(String itemId) {
        return () -> {
            ResourceLocation rl = ResourceLocation.tryParse(itemId);
            if (rl == null) return Items.BARRIER;
            Item item = BuiltInRegistries.ITEM.get(rl);
            return item != Items.AIR ? item : Items.BARRIER;
        };
    }

    /** Resolves a bare {@code createfood:} id, preferring the hardcoded entry then the game registry. */
    public static Supplier<Item> byModId(String itemId) {
        return () -> {
            try {
                return ItemEntry.getById(itemId).get();
            } catch (IllegalArgumentException | IllegalStateException ignored) {
                return byFullId(CreateFoodCommon.MOD_ID + ":" + itemId).get();
            }
        };
    }
}
