package dev.averageanime.tools;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.config.AddonRegistry;
import dev.averageanime.config.AddonDefaults;
import dev.averageanime.config.ConfigDefaults;
import dev.averageanime.createfood.lib.config.AddonSpecLoader;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Checks the bundled addon specs and any spec under {@code createfood.addonRoots}: that every entry
 * survives its validator, that no addon displaces a built-in default, and that the effective config
 * defaults still match the committed snapshot.
 *
 * <p>Pass {@code -Dcreatefood.writeSnapshot=true} to rewrite the snapshot after an intended change.
 */
public final class VerifyAddonSpec {

    /** Registered so that a bundled spec silently failing to load is a failure, not an empty report. */
    private static final List<String> BUNDLED = List.of("createfoodplus");

    private static final Path SNAPSHOT =
            Path.of("common/src/test/resources/addon-defaults-snapshot.txt");

    private VerifyAddonSpec() {}

    public static void main(String[] args) {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        String roots = System.getProperty(AddonRegistry.ADDON_ROOTS_PROPERTY);
        boolean extraRoots = roots != null && !roots.isBlank();
        System.out.println("addon roots: " + (extraRoots ? roots : "(none)"));

        boolean ok = true;
        ok &= reportAddons();

        int contributed = 0;
        for (Map.Entry<String, List<String>> section : sections().entrySet()) {
            contributed += report(section.getKey(), section.getValue());
        }
        System.out.println("total addon entries accepted: " + contributed);

        ok &= builtInsSurvive();
        ok &= nothingSilentlyDropped();
        ok &= ownedByUs();
        ok &= noDuplicateEffectKeys(AddonRegistry.effectOverrides());
        // An extra root is by definition not in the snapshot, so only assert it on a stock build.
        if (!extraRoots) ok &= snapshotMatches();

        if (!ok) System.exit(1);
    }

    /** Every list an addon can contribute to, in the order the snapshot records them. */
    private static Map<String, List<String>> sections() {
        Map<String, List<String>> all = new LinkedHashMap<>();
        all.put("items", AddonRegistry.items());
        all.put("blocks", AddonRegistry.blocks());
        all.put("fluids", AddonRegistry.fluids());
        all.put("display_blocks", AddonRegistry.displayBlocks());
        all.put("effect_overrides", AddonRegistry.effectOverrides());
        all.put("tooltips", AddonRegistry.tooltips());
        all.put("crafting_remainders", AddonRegistry.craftingRemainders());
        all.put("hide_items", AddonRegistry.hideItems());
        return all;
    }

    private static boolean reportAddons() {
        List<AddonSpecLoader.Addon> addons = AddonRegistry.addons();
        Set<String> ids = new HashSet<>();
        boolean ok = true;
        System.out.println("addons loaded: " + addons.size());
        for (AddonSpecLoader.Addon addon : addons) {
            System.out.println("  " + addon.addonId() + " (" + addon.name() + ") "
                    + (addon.builtIn() ? "bundled" : "from " + addon.origin())
                    + ", " + (addon.defaultEnabled() ? "on" : "off") + " by default");
            if (!ids.add(addon.addonId())) {
                System.out.println("  DUPLICATE addon id: " + addon.addonId());
                ok = false;
            }
        }
        for (String required : BUNDLED) {
            if (ids.contains(required)) continue;
            System.out.println("  MISSING bundled addon: " + required
                    + " -- is it on the classpath under data/createfood/addons/?");
            ok = false;
        }
        return ok;
    }

    /** A built-in default must never be displaced or reordered by an addon. */
    private static boolean builtInsSurvive() {
        boolean ok = true;
        ok &= prefix("items", AddonDefaults.customItems(), ConfigDefaults.CUSTOM_ITEM_DEFAULT);
        ok &= prefix("blocks", AddonDefaults.customBlocks(), ConfigDefaults.CUSTOM_BLOCK_DEFAULT);
        ok &= prefix("fluids", AddonDefaults.customFluids(), ConfigDefaults.CUSTOM_FLUID_DEFAULT);
        ok &= prefix("display_blocks", AddonDefaults.customDisplayBlocks(),
                ConfigDefaults.CUSTOM_DISPLAY_BLOCK_DEFAULT);
        ok &= prefix("hide_items", AddonDefaults.hideItems(), ConfigDefaults.HIDE_ITEMS_DEFAULT);
        ok &= prefix("tooltips", AddonDefaults.customTooltips(), ConfigDefaults.CUSTOM_TOOLTIPS_DEFAULT);
        ok &= prefix("remainders", AddonDefaults.craftingRemainders(),
                ConfigDefaults.CRAFTING_REMAINDERS_DEFAULT);
        System.out.println(ok
                ? "built-in defaults survive intact ahead of the addon entries"
                : "MISMATCH: an addon displaced a built-in default");
        return ok;
    }

    /**
     * The merge drops an addon entry whose key a built-in already claims. That is intended, but only for a
     * real clash: an entry vanishing because the key was computed from too few fields is how 101 of the
     * compat effect overrides once disappeared, the list carrying a line per (item, category) rather than
     * per item. So every drop must be explainable by a built-in entry with the same key.
     */
    private static boolean nothingSilentlyDropped() {
        boolean ok = true;
        ok &= noDrops("items.item", ConfigDefaults.CUSTOM_ITEM_DEFAULT,
                AddonRegistry.items(), AddonDefaults.customItems(), 1);
        ok &= noDrops("blocks.block", ConfigDefaults.CUSTOM_BLOCK_DEFAULT,
                AddonRegistry.blocks(), AddonDefaults.customBlocks(), 1);
        ok &= noDrops("blocks.fluid", ConfigDefaults.CUSTOM_FLUID_DEFAULT,
                AddonRegistry.fluids(), AddonDefaults.customFluids(), 1);
        ok &= noDrops("blocks.display_block", ConfigDefaults.CUSTOM_DISPLAY_BLOCK_DEFAULT,
                AddonRegistry.displayBlocks(), AddonDefaults.customDisplayBlocks(), 2);
        ok &= noDrops("items.hide_items", ConfigDefaults.HIDE_ITEMS_DEFAULT,
                AddonRegistry.hideItems(), AddonDefaults.hideItems(), 1);
        ok &= noDrops("items.effects.item_overrides", List.of(),
                AddonRegistry.effectOverrides(), AddonDefaults.itemEffectOverrides(), 2);
        ok &= noDrops("tooltips.custom_tooltips", ConfigDefaults.CUSTOM_TOOLTIPS_DEFAULT,
                AddonRegistry.tooltips(), AddonDefaults.customTooltips(), 1);
        ok &= noDrops("items.remainders.crafting_remainders", ConfigDefaults.CRAFTING_REMAINDERS_DEFAULT,
                AddonRegistry.craftingRemainders(), AddonDefaults.craftingRemainders(), 1);
        System.out.println(ok
                ? "every addon entry reaches the effective defaults, or clashes with a built-in"
                : "MISMATCH: an addon entry was dropped without a built-in claiming its key");
        return ok;
    }

    private static boolean noDrops(String label, List<String> builtIn, List<String> addon,
                                   List<String> effective, int keyFields) {
        Set<String> present = new HashSet<>(effective);
        Set<String> builtInKeys = new HashSet<>();
        for (String entry : builtIn) builtInKeys.add(key(entry, keyFields));

        List<String> unexplained = new ArrayList<>();
        for (String entry : addon) {
            if (present.contains(entry)) continue;
            if (builtInKeys.contains(key(entry, keyFields))) continue; // a genuine clash; built-in wins
            unexplained.add(entry);
        }
        if (unexplained.isEmpty()) return true;
        System.out.println("  " + label + ": " + unexplained.size() + " of " + addon.size()
                + " addon entries vanished with no built-in claiming their key: " + head(unexplained));
        return false;
    }

    /** Mirrors the merge's notion of identity: the first {@code keyFields} pipe-delimited fields. */
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
     * An addon registers under this mod's namespace, so a registration entry naming a foreign one is a
     * mistake. Display blocks and tooltips are exempt: they decorate another mod's item on purpose.
     */
    private static boolean ownedByUs() {
        List<String> foreign = new ArrayList<>();
        for (String key : List.of("items", "blocks", "fluids")) {
            for (String entry : sections().get(key)) {
                String id = entry.split("\\|")[0];
                int colon = id.indexOf(':');
                if (colon >= 0 && !id.substring(0, colon).equals(CreateFoodCommon.MOD_ID)) foreign.add(entry);
            }
        }
        if (foreign.isEmpty()) return true;
        System.out.println("  addon registrations in a foreign namespace: " + head(foreign));
        return false;
    }

    /** Effects come back grouped by item; order is only insignificant while no (item, category) repeats. */
    private static boolean noDuplicateEffectKeys(List<String> entries) {
        Set<String> seen = new HashSet<>();
        List<String> dupes = new ArrayList<>();
        for (String entry : entries) {
            String[] parts = entry.split("\\|");
            if (parts.length < 2) continue;
            if (!seen.add(parts[0] + "|" + parts[1])) dupes.add(entry);
        }
        if (dupes.isEmpty()) return true;
        System.out.println("  effects: duplicate (item, category) keys make order significant: " + head(dupes));
        return false;
    }

    /**
     * The effective defaults, byte for byte. This is what catches an addon quietly changing what a fresh
     * config is written with -- the one thing no compiler and no other check would notice.
     */
    private static boolean snapshotMatches() {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<String, List<String>> section : effectiveDefaults().entrySet()) {
            lines.add("[" + section.getKey() + "] " + section.getValue().size());
            lines.addAll(section.getValue());
        }
        String rendered = String.join("\n", lines) + "\n";

        if (Boolean.getBoolean("createfood.writeSnapshot")) {
            try {
                Files.createDirectories(SNAPSHOT.getParent());
                Files.writeString(SNAPSHOT, rendered, StandardCharsets.UTF_8);
                System.out.println("snapshot rewritten: " + SNAPSHOT + " (" + lines.size() + " lines)");
                return true;
            } catch (IOException e) {
                System.out.println("  failed to write " + SNAPSHOT + ": " + e);
                return false;
            }
        }

        String committed;
        try {
            committed = Files.readString(SNAPSHOT, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("  no snapshot at " + SNAPSHOT
                    + "; rerun with -Dcreatefood.writeSnapshot=true to create it");
            return false;
        }
        if (committed.equals(rendered)) {
            System.out.println("effective config defaults match the committed snapshot");
            return true;
        }
        System.out.println("SNAPSHOT MISMATCH: the effective config defaults have changed.");
        printFirstDifference(committed, rendered);
        System.out.println("  If the change is intended, rerun with -Dcreatefood.writeSnapshot=true.");
        return false;
    }

    /** The nine config lists an addon can shape, keyed by the dotted config key they are written to. */
    private static Map<String, List<String>> effectiveDefaults() {
        Map<String, List<String>> all = new LinkedHashMap<>();
        all.put("items.item", AddonDefaults.customItems());
        all.put("blocks.block", AddonDefaults.customBlocks());
        all.put("blocks.fluid", AddonDefaults.customFluids());
        all.put("blocks.display_block", AddonDefaults.customDisplayBlocks());
        all.put("items.hide_items", AddonDefaults.hideItems());
        all.put("items.effects.item_overrides", AddonDefaults.itemEffectOverrides());
        all.put("items.nutrition_saturation", AddonDefaults.itemNutritionOverrides());
        all.put("tooltips.custom_tooltips", AddonDefaults.customTooltips());
        all.put("items.remainders.crafting_remainders", AddonDefaults.craftingRemainders());
        return all;
    }

    private static void printFirstDifference(String committed, String rendered) {
        String[] was = committed.split("\n", -1);
        String[] now = rendered.split("\n", -1);
        for (int i = 0; i < Math.max(was.length, now.length); i++) {
            String a = i < was.length ? was[i] : "(end of file)";
            String b = i < now.length ? now[i] : "(end of file)";
            if (a.equals(b)) continue;
            System.out.println("  first difference at line " + (i + 1));
            System.out.println("      committed: " + a);
            System.out.println("      now:       " + b);
            return;
        }
    }

    private static int report(String label, List<String> entries) {
        if (entries.isEmpty()) return 0;
        System.out.println("  " + label + ": " + entries.size());
        for (String entry : entries.subList(0, Math.min(entries.size(), 5))) {
            System.out.println("      " + entry);
        }
        if (entries.size() > 5) System.out.println("      ... " + (entries.size() - 5) + " more");
        return entries.size();
    }

    private static boolean prefix(String label, List<String> effective, List<String> builtIn) {
        if (effective.size() >= builtIn.size() && effective.subList(0, builtIn.size()).equals(builtIn)) return true;
        System.out.println("  " + label + ": built-in entries are not an intact prefix of the effective list");
        return false;
    }

    private static List<String> head(List<String> list) {
        return list.subList(0, Math.min(list.size(), 5));
    }
}
