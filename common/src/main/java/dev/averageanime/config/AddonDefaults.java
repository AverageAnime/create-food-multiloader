package dev.averageanime.config;

import com.google.gson.JsonElement;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.createfood.lib.config.AddonSpecLoader.ConfigContribution;
import dev.averageanime.createfood.lib.config.ConfigSpecOverlay;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The one place an addon's contribution becomes a config default. Two things feed in and merge under the
 * same precedence: the spec's named sections, and its free-form {@code config} block.
 *
 * <p>A built-in default always wins, and always comes first. That is what keeps this mod's own content
 * stable no matter what is installed alongside it.
 */
public final class AddonDefaults implements ConfigSpecOverlay.Defaults {

    private static final Map<String, String> SECTION_KEYS = Map.of(
            "items.item", "items",
            "blocks.block", "blocks",
            "blocks.fluid", "fluids",
            "blocks.display_block", "display_blocks",
            "items.hide_items", "hide_items",
            "tooltips.custom_tooltips", "tooltips",
            "items.remainders.crafting_remainders", "crafting_remainders",
            "items.effects.item_overrides", "item_effect_overrides");

    /**
     * How many leading pipe-delimited fields identify an entry. Most lists hold one entry per registry
     * name, so the name is the whole identity. The effect overrides do not: they carry a line per
     * (item, category) pair, and keying those on the item alone drops every effect after the first.
     */
    private static final Map<String, Integer> KEY_FIELDS = Map.of(
            "items.effects.item_overrides", 2,
            // A line per (item, display type), not per item: an item can carry both a plate and a
            // small plate, so keying on the id alone silently drops the second one.
            "blocks.display_block", 2);

    public static final String CLIENT = "client";
    public static final String COMMON = "common";
    public static final String SERVER = "server";

    /** Which spec file an option lives in, so a contribution can be addressed unambiguously. */
    private final String file;

    private AddonDefaults(String file) {
        this.file = file;
    }

    public static AddonDefaults forFile(String file) {
        return new AddonDefaults(file);
    }

    @Override
    public List<String> list(String dottedKey, List<String> builtIn, Predicate<Object> validator) {
        List<String> addon = new ArrayList<>(fromSection(dottedKey));
        for (ConfigContribution contribution : contributionsTo(dottedKey)) {
            addon.addAll(asStrings(contribution, validator));
        }
        return merge(builtIn, addon, KEY_FIELDS.getOrDefault(dottedKey, 1));
    }

    @Override
    public boolean bool(String dottedKey, boolean builtIn) {
        ConfigContribution first = firstScalar(dottedKey);
        if (first == null) return builtIn;
        if (!first.value().isJsonPrimitive()) {
            warnBadType(first, "a boolean");
            return builtIn;
        }
        return first.value().getAsBoolean();
    }

    @Override
    public int integer(String dottedKey, int builtIn) {
        ConfigContribution first = firstScalar(dottedKey);
        if (first == null) return builtIn;
        if (!first.value().isJsonPrimitive()) {
            warnBadType(first, "a number");
            return builtIn;
        }
        try {
            return first.value().getAsInt();
        } catch (NumberFormatException e) {
            warnBadType(first, "a number");
            return builtIn;
        }
    }

    /** A contribution to a key the schema never declares reaches nothing, so say so rather than ignore it. */
    @Override
    public void declared(Set<String> dottedKeys) {
        for (ConfigContribution contribution : AddonRegistry.configContributions()) {
            if (dottedKeys.contains(contribution.key())) continue;
            CreateFoodCommon.LOGGER.warn(
                    "Create: Food - addon {} sets {}.{}, which is not a config option this mod defines; ignoring",
                    contribution.addonId(), contribution.file(), contribution.key());
        }
    }

    private static List<String> fromSection(String dottedKey) {
        String section = SECTION_KEYS.get(dottedKey);
        return section == null ? List.of() : AddonRegistry.from(section);
    }

    private List<ConfigContribution> contributionsTo(String dottedKey) {
        List<ConfigContribution> matching = new ArrayList<>();
        for (ConfigContribution contribution : AddonRegistry.configContributions()) {
            if (contribution.file().equals(file) && contribution.key().equals(dottedKey)) {
                matching.add(contribution);
            }
        }
        return matching;
    }

    /** First enabled addon in load order wins a scalar; a later one is reported rather than applied. */
    private ConfigContribution firstScalar(String dottedKey) {
        List<ConfigContribution> matching = contributionsTo(dottedKey);
        if (matching.isEmpty()) return null;
        ConfigContribution winner = matching.get(0);
        for (ConfigContribution loser : matching.subList(1, matching.size())) {
            CreateFoodCommon.LOGGER.warn(
                    "Create: Food - addons {} and {} both set {}; keeping {} from {}",
                    winner.addonId(), loser.addonId(), dottedKey, winner.value(), winner.addonId());
        }
        return winner;
    }

    /** A list contribution is an array of strings; anything else is a mistake worth naming. */
    private static List<String> asStrings(ConfigContribution contribution, Predicate<Object> validator) {
        if (!contribution.value().isJsonArray()) {
            warnBadType(contribution, "a list");
            return List.of();
        }
        List<String> values = new ArrayList<>();
        for (JsonElement element : contribution.value().getAsJsonArray()) {
            if (!element.isJsonPrimitive()) {
                warnBadType(contribution, "a list of strings");
                continue;
            }
            String value = element.getAsString();
            // Rejected here rather than by the loader, whose correction pass can discard the whole list.
            if (validator != null && !validator.test(value)) {
                CreateFoodCommon.LOGGER.warn("Create: Food - addon {} has an invalid {} entry, skipping: {}",
                        contribution.addonId(), contribution.key(), value);
                continue;
            }
            values.add(value);
        }
        return values;
    }

    private static void warnBadType(ConfigContribution contribution, String expected) {
        CreateFoodCommon.LOGGER.warn("Create: Food - addon {} sets {} to something that is not {}; ignoring",
                contribution.addonId(), contribution.key(), expected);
    }

    /**
     * Built-in entries first and intact, then addon entries whose key nothing has claimed yet. Order is
     * stable, so the same install always writes the same config file.
     */
    private static List<String> merge(List<String> builtIn, List<String> addon, int keyFields) {
        if (addon.isEmpty()) return builtIn;
        Set<String> claimed = new LinkedHashSet<>();
        for (String entry : builtIn) claimed.add(key(entry, keyFields));
        List<String> combined = new ArrayList<>(builtIn);
        for (String entry : addon) {
            if (claimed.add(key(entry, keyFields))) combined.add(entry);
        }
        return List.copyOf(combined);
    }

    private static String key(String entry, int keyFields) {
        int bar = -1;
        for (int i = 0; i < keyFields; i++) {
            int next = entry.indexOf('|', bar + 1);
            if (next < 0) return entry;
            bar = next;
        }
        return entry.substring(0, bar);
    }

    /**
     * The effective default for one option, outside the spec build. Registration reads the config file
     * before the loader has loaded it, so it cannot go through the spec; these give it the same answer the
     * spec would have written, and keep the file, key, base list and validator paired in one place.
     */
    public static List<String> effective(String file, String dottedKey, List<String> builtIn,
                                          Predicate<Object> validator) {
        return forFile(file).list(dottedKey, builtIn, validator);
    }

    public static List<String> customItems() {
        return effective(COMMON, "items.item",
                ConfigDefaults.CUSTOM_ITEM_DEFAULT, ConfigDefaults.CUSTOM_ITEM_VALIDATOR);
    }

    public static List<String> customBlocks() {
        return effective(COMMON, "blocks.block",
                ConfigDefaults.CUSTOM_BLOCK_DEFAULT, ConfigDefaults.CUSTOM_BLOCK_VALIDATOR);
    }

    public static List<String> customFluids() {
        return effective(COMMON, "blocks.fluid",
                ConfigDefaults.CUSTOM_FLUID_DEFAULT, ConfigDefaults.CUSTOM_FLUID_VALIDATOR);
    }

    public static List<String> customDisplayBlocks() {
        return effective(COMMON, "blocks.display_block",
                ConfigDefaults.CUSTOM_DISPLAY_BLOCK_DEFAULT, ConfigDefaults.CUSTOM_DISPLAY_BLOCK_VALIDATOR);
    }

    public static List<String> hideItems() {
        return effective(COMMON, "items.hide_items", ConfigDefaults.HIDE_ITEMS_DEFAULT, null);
    }

    public static List<String> customTooltips() {
        return effective(CLIENT, "tooltips.custom_tooltips", ConfigDefaults.CUSTOM_TOOLTIPS_DEFAULT, null);
    }

    /** The base mod ships no foreign food values of its own; the rebalance addon supplies them. */
    public static List<String> itemEffectOverrides() {
        return effective(SERVER, "items.effects.item_overrides",
                List.of(), ConfigDefaults.ITEM_EFFECT_OVERRIDE_VALIDATOR);
    }

    public static List<String> itemNutritionOverrides() {
        return effective(SERVER, "items.nutrition_saturation",
                List.of(), ConfigDefaults.ITEM_NUTRITION_OVERRIDE_VALIDATOR);
    }

    public static List<String> craftingRemainders() {
        return effective(SERVER, "items.remainders.crafting_remainders",
                ConfigDefaults.CRAFTING_REMAINDERS_DEFAULT, null);
    }
}
