package dev.averageanime.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.createfood.lib.config.AddonSpecLoader;
import dev.averageanime.platform.Services;

import java.util.ArrayList;
import java.util.List;

/** Maps each section of {@code data/createfood/addon.json} to the pipe-delimited form the config uses. */
public final class AddonRegistry {

    /** Probed in every mod file. */
    public static final String ADDON_PATH = "data/" + CreateFoodCommon.MOD_ID + "/addon.json";

    /** Extra roots to scan, separated by the platform path separator. */
    public static final String ADDON_ROOTS_PROPERTY = CreateFoodCommon.MOD_ID + ".addonRoots";

    private static final int SUPPORTED_SPEC_VERSION = 1;

    /**
     * Specs this mod ships itself, read from the classpath. The probed {@link #ADDON_PATH} is left to
     * third parties; a mod file holds one spec, and this mod carries more than one.
     */
    private static final String BUNDLED_COMPAT = "/data/createfood/addons/createfoodplus.json";
    private static final String BUNDLED_REBALANCE = "/data/createfood/addons/createfoodrebalance.json";

    private static final String ITEMS = "items";
    private static final String BLOCKS = "blocks";
    private static final String FLUIDS = "fluids";
    private static final String DISPLAY_BLOCKS = "display_blocks";
    private static final String TOOLTIPS = "tooltips";
    private static final String REMAINDERS = "crafting_remainders";
    private static final String HIDE = "hide_items";

    /**
     * Where an item's inline {@code effects[]} land. There is no array of this name in a spec -- it is
     * registered so that entries the items section routes here are validated as effect overrides.
     */
    private static final String EFFECTS = "item_effect_overrides";

    private static final AddonSpecLoader LOADER =
            new AddonSpecLoader(ADDON_PATH, ADDON_ROOTS_PROPERTY, SUPPORTED_SPEC_VERSION,
                    CreateFoodCommon.LOGGER, path -> Services.PLATFORM.findModResources(path))
                    .builtin(BUNDLED_COMPAT)
                    .builtin(BUNDLED_REBALANCE)
                    .gate(AddonGate::isEnabled)
                    .section(ITEMS, ConfigDefaults.CUSTOM_ITEM_VALIDATOR, AddonRegistry::item)
                    .section(BLOCKS, ConfigDefaults.CUSTOM_BLOCK_VALIDATOR, AddonRegistry::block)
                    .section(FLUIDS, ConfigDefaults.CUSTOM_FLUID_VALIDATOR, AddonRegistry::fluid)
                    .section(DISPLAY_BLOCKS, ConfigDefaults.CUSTOM_DISPLAY_BLOCK_VALIDATOR, AddonRegistry::displayBlock)
                    .section(TOOLTIPS, null, AddonRegistry::tooltip)
                    .section(REMAINDERS, null, AddonRegistry::remainder)
                    .section(HIDE, null, (entry, sink) -> add(sink, HIDE, str(entry, "value", null)))
                    .section(EFFECTS, ConfigDefaults.ITEM_EFFECT_OVERRIDE_VALIDATOR, (entry, sink) -> {});

    private AddonRegistry() {}

    public static List<String> items()              { return LOADER.get(ITEMS); }
    public static List<String> blocks()             { return LOADER.get(BLOCKS); }
    public static List<String> fluids()             { return LOADER.get(FLUIDS); }
    public static List<String> displayBlocks()      { return LOADER.get(DISPLAY_BLOCKS); }
    public static List<String> craftingRemainders() { return LOADER.get(REMAINDERS); }
    public static List<String> hideItems()          { return LOADER.get(HIDE); }
    public static List<String> effectOverrides()    { return LOADER.get(EFFECTS); }
    public static List<String> tooltips()           { return LOADER.get(TOOLTIPS); }

    /** Every loaded addon, whatever the gate says. */
    public static List<AddonSpecLoader.Addon> addons() { return LOADER.addons(); }

    /** A section's entries from every enabled addon, in load order. */
    public static List<String> from(String section) { return LOADER.get(section); }

    /** One addon's entries for a section, whatever the gate says. */
    public static List<String> from(String section, String addonId) { return LOADER.get(section, addonId); }

    public static List<AddonSpecLoader.ConfigContribution> configContributions() {
        return LOADER.configContributions();
    }

    private static String withCondition(String entry, JsonObject o) {
        String condition = str(o, "condition", null);
        if (condition == null || condition.isBlank()) return entry;
        return entry + (condition.startsWith("@") ? condition : "@" + condition);
    }

    private static void item(JsonObject o, AddonSpecLoader.Sink sink) {
        String id = str(o, "id", null);
        String type = str(o, "type", null);
        if (id == null || type == null) return;

        String entry;
        if (type.equalsIgnoreCase("plain_cr")) {
            entry = id + "|" + type + "|" + str(o, "remainder", "");
        } else if (o.has("nutrition") || o.has("saturation")) {
            entry = id + "|" + type + "|" + num(o, "nutrition", "0") + "|" + num(o, "saturation", "0");
        } else {
            entry = id + "|" + type;
        }
        sink.add(ITEMS, withCondition(entry, o));
        readInlineEffects(id, o, sink);
        add(sink, TOOLTIPS, tooltipEntry(id, o));
    }

    private static void block(JsonObject o, AddonSpecLoader.Sink sink) {
        String id = str(o, "id", null);
        String type = str(o, "type", null);
        if (id == null || type == null) return;
        String slice = str(o, "slice", null);
        sink.add(BLOCKS, withCondition(slice == null ? id + "|" + type : id + "|" + type + "|" + slice, o));
    }

    private static void fluid(JsonObject o, AddonSpecLoader.Sink sink) {
        String id = str(o, "id", null);
        if (id == null) return;
        sink.add(FLUIDS, withCondition(o.has("slope") || o.has("level")
                ? id + "|" + num(o, "slope", "1") + "|" + num(o, "level", "1")
                : id, o));
    }

    private static void displayBlock(JsonObject o, AddonSpecLoader.Sink sink) {
        String item = str(o, "item", null);
        String display = str(o, "display", null);
        if (item == null || display == null) return;
        StringBuilder entry = new StringBuilder(item)
                .append("|").append(display)
                .append("|").append(num(o, "max_stack", "1"));
        if (o.has("height") || o.has("particles")) {
            entry.append("|").append(num(o, "height", "0"));
            if (o.has("particles")) entry.append("|").append(str(o, "particles", "false"));
        }
        sink.add(DISPLAY_BLOCKS, withCondition(entry.toString(), o));
    }

    private static void tooltip(JsonObject o, AddonSpecLoader.Sink sink) {
        String item = str(o, "item", null);
        if (item == null) return;
        add(sink, TOOLTIPS, tooltipEntry(item, o));
    }

    private static void remainder(JsonObject o, AddonSpecLoader.Sink sink) {
        String item = str(o, "item", null);
        String remainder = str(o, "remainder", null);
        if (item == null || remainder == null) return;
        sink.add(REMAINDERS, item + "|" + remainder);
    }

    private static void readInlineEffects(String itemId, JsonObject item, AddonSpecLoader.Sink sink) {
        for (JsonElement raw : array(item, "effects")) {
            if (!raw.isJsonObject()) continue;
            JsonObject o = raw.getAsJsonObject();
            String category = str(o, "category", null);
            if (category == null) continue;
            String entry;
            if (o.has("remove") && o.get("remove").getAsBoolean()) {
                entry = itemId + "|" + category + "|remove";
            } else {
                entry = itemId + "|" + category + "|" + num(o, "duration", "0") + "|" + num(o, "amplifier", "0");
                if (o.has("chance")) entry = entry + "|" + num(o, "chance", "1");
            }
            // Validated as an effect override by the section it lands in, not the one that declared it.
            sink.add(EFFECTS, entry);
        }
    }

    private static String tooltipEntry(String itemId, JsonObject o) {
        List<String> keys = new ArrayList<>();
        for (JsonElement raw : array(o, "tooltips")) {
            if (raw.isJsonPrimitive()) keys.add(raw.getAsString());
        }
        String compat = str(o, "compat", null);
        if (keys.isEmpty() && compat == null) return null;
        String qualified = itemId.contains(":") ? itemId : CreateFoodCommon.MOD_ID + ":" + itemId;
        String entry = qualified + "|" + String.join(",", keys);
        return compat != null ? entry + "|" + compat : entry;
    }

    private static void add(AddonSpecLoader.Sink sink, String section, String value) {
        if (value != null) sink.add(section, value);
    }

    private static JsonArray array(JsonObject o, String key) {
        JsonElement e = o.get(key);
        return e != null && e.isJsonArray() ? e.getAsJsonArray() : new JsonArray();
    }

    private static String str(JsonObject o, String key, String fallback) {
        JsonElement e = o.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsString() : fallback;
    }

    private static String num(JsonObject o, String key, String fallback) {
        JsonElement e = o.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsString() : fallback;
    }
}
