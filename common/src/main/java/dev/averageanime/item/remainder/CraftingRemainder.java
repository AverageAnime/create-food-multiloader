package dev.averageanime.item.remainder;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class CraftingRemainder {

    private CraftingRemainder() {}

    private record RemainderCache(List<? extends String> source, Map<Item, Optional<Item>> map) {}

    private static volatile RemainderCache cache;

    @Nullable
    public static Item getRemainderFor(Item input) {
        List<? extends String> entries = Services.PLATFORM.getCraftingRemainders();
        RemainderCache c = cache;
        if (c == null || c.source() != entries) {
            c = new RemainderCache(entries, buildRemainderMap(entries));
            cache = c;
        }
        Optional<Item> remainder = c.map().get(input);
        return remainder == null ? null : remainder.orElse(null);
    }

    private static Map<Item, Optional<Item>> buildRemainderMap(List<? extends String> entries) {
        Map<Item, Optional<Item>> map = new HashMap<>();
        for (String entry : entries) {
            String[] parts = entry.split("\\|");
            if (parts.length != 2) continue;

            Item inputItem = resolveItem(parts[0].trim());
            if (inputItem == Items.AIR) continue;

            Item remainderItem = resolveItem(parts[1].trim());
            map.putIfAbsent(inputItem,
                    remainderItem == Items.AIR ? Optional.empty() : Optional.of(remainderItem));
        }
        return map;
    }

    public static void handleEggImpact(ThrownEgg egg) {
        if (egg.level().isClientSide()) return;
        if (!Services.PLATFORM.isEggImpactRemainderEnabled()) return;

        Item eggshellItem = resolveItem("createfood:eggshell");
        if (eggshellItem == Items.AIR) return;
        if (!Services.PLATFORM.isItemEnabled(BuiltInRegistries.ITEM.getKey(eggshellItem).getPath())) return;

        ItemStack eggshell = new ItemStack(eggshellItem, 1);
        egg.level().addFreshEntity(
                new net.minecraft.world.entity.item.ItemEntity(
                        egg.level(), egg.getX(), egg.getY(), egg.getZ(), eggshell
                )
        );
    }

    public static Item resolveItem(String id) {
        try {
            return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
        } catch (Exception e) {
            CreateFoodCommon.LOGGER.error("Could not resolve item '{}'", id, e);
            return Items.AIR;
        }
    }
}