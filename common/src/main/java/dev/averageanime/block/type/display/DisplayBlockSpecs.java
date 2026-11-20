package dev.averageanime.block.type.display;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.config.AddonDefaults;
import dev.averageanime.config.ConfigBootstrap;
import dev.averageanime.createfood.lib.registry.DisplayEntry;
import dev.averageanime.registry.DisplayRegistry;
import dev.averageanime.registry.DisplayRegistry.DisplayType;
import dev.averageanime.registry.type.BlockEntry;
import dev.averageanime.registry.type.ItemEntry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Which display blocks to register, resolved identically for both loaders so that only the act of
 * registering stays loader-specific. The item is a supplier because the food it shows is not necessarily
 * registered yet when the block is.
 */
public final class DisplayBlockSpecs {

    public record Spec(String blockName, Supplier<Item> food, DisplayEntry<DisplayType> config) {}

    private DisplayBlockSpecs() {}

    /** One display block per matching shape for every item this mod registers. */
    public static List<Spec> auto() {
        List<Spec> specs = new ArrayList<>();
        try {
            for (String itemName : DisplayRegistry.getItemNames()) {
                if (DisplayRegistry.isSkipped(itemName)) continue;

                for (DisplayEntry<DisplayType> config : DisplayRegistry.findMatchingConfigs(itemName)) {
                    specs.add(new Spec(DisplayRegistry.getBlockName(itemName, config.type()),
                            ownFood(itemName), config));
                }
            }
        } catch (Exception e) {
            CreateFoodCommon.LOGGER.error("Create: Food - Failed to auto-register display blocks", e);
        }
        return specs;
    }

    /**
     * The {@code custom_display_block} entries, {@code item|type|max_stack[|height[|particles]]}. The
     * fifth field is {@code true}/{@code false} or a particle id, and {@code true} means white smoke.
     * An invalid entry is logged and skipped rather than failing the others.
     */
    public static List<Spec> custom() {
        List<Spec> specs = new ArrayList<>();
        for (String entry : ConfigBootstrap.read(ConfigBootstrap.DISPLAY_BLOCK, AddonDefaults.customDisplayBlocks())) {
            Spec spec = parse(entry);
            if (spec != null) specs.add(spec);
        }
        return specs;
    }

    private static Spec parse(String entry) {
        String[] p = entry.split("\\|");
        if (p.length < 3 || p.length > 5) {
            CreateFoodCommon.LOGGER.warn("Create: Food - Skipping invalid custom_display_block entry: {}", entry);
            return null;
        }
        String modItemId = p[0];
        int maxStack;
        try {
            maxStack = Integer.parseInt(p[2]);
        } catch (NumberFormatException e) {
            CreateFoodCommon.LOGGER.warn("Create: Food - Invalid max_stack in custom_display_block entry: {}", entry);
            return null;
        }
        double height = 12;
        if (p.length >= 4) {
            try {
                height = Double.parseDouble(p[3]);
            } catch (NumberFormatException e) {
                CreateFoodCommon.LOGGER.warn("Create: Food - Invalid height in custom_display_block entry: {}", entry);
                return null;
            }
        }
        boolean hasParticles = false;
        ParticleOptions particle = ParticleTypes.WHITE_SMOKE;
        if (p.length == 5 && !p[4].isBlank() && !p[4].equalsIgnoreCase("false")) {
            hasParticles = true;
            if (!p[4].equalsIgnoreCase("true")) {
                ResourceLocation particleId = ResourceLocation.tryParse(p[4]);
                Object resolved = particleId == null ? null : BuiltInRegistries.PARTICLE_TYPE.get(particleId);
                if (resolved instanceof ParticleOptions options) {
                    particle = options;
                } else {
                    CreateFoodCommon.LOGGER.warn("Create: Food - Unknown or non-simple particle '{}' in custom_display_block entry: {} (using white smoke)", p[4], entry);
                }
            }
        }
        DisplayType type = DisplayRegistry.parseType(p[1]);
        if (type == null) {
            CreateFoodCommon.LOGGER.warn("Create: Food - Unknown display_type '{}' in custom_display_block entry: {}", p[1].toLowerCase(), entry);
            return null;
        }
        ResourceLocation itemId = ResourceLocation.tryParse(modItemId);
        if (itemId == null) {
            CreateFoodCommon.LOGGER.warn("Create: Food - Invalid item ID '{}' in custom_display_block entry: {}", modItemId, entry);
            return null;
        }
        final ParticleOptions particleType = particle;
        return new Spec(DisplayRegistry.getBlockName(itemId.getPath(), type), foreignFood(modItemId),
                new DisplayEntry<>(type, maxStack, height, hasParticles, hasParticles ? () -> particleType : null));
    }

    private static Supplier<Item> ownFood(String itemName) {
        return () -> {
            for (ItemEntry def : ItemEntry.ALL) {
                if (def.id.equals(itemName)) return def.get();
            }
            BlockEntry block = BlockEntry.getById(itemName);
            return block != null ? block.get().asItem() : Items.BARRIER;
        };
    }

    private static Supplier<Item> foreignFood(String itemId) {
        return () -> {
            try {
                Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemId));
                return item != Items.AIR ? item : Items.BARRIER;
            } catch (Exception e) {
                return Items.BARRIER;
            }
        };
    }
}
