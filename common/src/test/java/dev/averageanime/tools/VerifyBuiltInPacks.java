package dev.averageanime.tools;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.pack.BuiltInPacks;

/**
 * Checks the built-in packs are structurally sound.
 *
 * <p>The first check is the one that matters at runtime: on NeoForge a pack declared in
 * {@link BuiltInPacks} whose directory has no readable {@code pack.mcmeta} is a startup NPE rather
 * than a warning, because {@code AddPackFindersEvent.addPackFinders} does not check
 * {@code Pack.readMetaAndCreate} for null. Fabric synthesizes metadata instead and would let the
 * mistake through, so running only the Fabric client would not catch it.
 */
public final class VerifyBuiltInPacks {

    private static final Path RESOURCES = Path.of("common/src/main/resources");
    private static final Path PACKS = RESOURCES.resolve("resourcepacks");
    private static final Path LANG = RESOURCES.resolve("assets/createfood/lang");

    private static final List<String> LOCALES =
            List.of("en_us", "de_de", "fr_fr", "ru_ru", "zh_cn");

    private static final String SUFFIX = "_overrides";

    /** Namespaces this mod is entitled to write into the root pack. */
    private static final Set<String> OWN_NAMESPACES =
            Set.of(CreateFoodCommon.MOD_ID, "c", "neoforge");

    /**
     * The one foreign asset that deliberately stays in the root pack. It merges rather than replaces
     * and names only this mod's own fluid sprite directory, so behind a switch it would break
     * Create: Food rather than restore vanilla.
     */
    private static final Set<String> ROOT_PACK_EXCEPTIONS = Set.of(
            "assets/minecraft/atlases/blocks.json");

    private VerifyBuiltInPacks() {}

    public static void main(String[] args) throws IOException {
        List<String> problems = new ArrayList<>();

        Set<String> declared = new LinkedHashSet<>(BuiltInPacks.NAMES);
        if (declared.size() != BuiltInPacks.NAMES.size()) {
            problems.add("BuiltInPacks.NAMES contains a duplicate");
        }

        checkDeclaredPacksExist(declared, problems);
        checkNoOrphanPacks(declared, problems);
        checkPackNamespaces(declared, problems);
        checkRootPackIsClean(problems);
        checkTitles(declared, problems);

        System.out.println("declared packs: " + declared.size());
        if (problems.isEmpty()) {
            System.out.println("built-in packs are consistent");
            return;
        }
        System.out.println("PROBLEMS (" + problems.size() + "):");
        problems.stream().sorted().forEach(p -> System.out.println("    " + p));
        System.exit(1);
    }

    private static void checkDeclaredPacksExist(Set<String> declared, List<String> problems) {
        for (String name : declared) {
            Path dir = PACKS.resolve(name);
            if (!Files.isDirectory(dir)) {
                problems.add("declared pack has no directory: resourcepacks/" + name);
                continue;
            }
            Path meta = dir.resolve("pack.mcmeta");
            if (!Files.isRegularFile(meta)) {
                problems.add("pack has no pack.mcmeta: resourcepacks/" + name);
                continue;
            }
            try {
                JsonObject root = JsonParser.parseString(
                        Files.readString(meta, StandardCharsets.UTF_8)).getAsJsonObject();
                if (!root.has("pack")) {
                    problems.add("pack.mcmeta has no pack object: resourcepacks/" + name);
                }
            } catch (Exception e) {
                problems.add("pack.mcmeta is unreadable: resourcepacks/" + name + " (" + e + ")");
            }
            if (!hasAssets(dir)) {
                problems.add("pack has no assets: resourcepacks/" + name);
            }
            if (hasData(dir)) {
                problems.add("pack holds data, which belongs in the root pack: resourcepacks/" + name);
            }
        }
    }

    private static void checkNoOrphanPacks(Set<String> declared, List<String> problems)
            throws IOException {
        if (!Files.isDirectory(PACKS)) {
            problems.add("no resourcepacks directory at " + PACKS);
            return;
        }
        try (Stream<Path> dirs = Files.list(PACKS)) {
            for (Path dir : dirs.filter(Files::isDirectory).toList()) {
                String name = dir.getFileName().toString();
                if (!declared.contains(name)) {
                    problems.add("pack directory is not declared in BuiltInPacks: " + name);
                }
            }
        }
    }

    /**
     * A pack named after one mod must contain only that mod's namespace. A mistaken move would
     * otherwise put files behind the wrong switch, silently, since every pack is on by default.
     */
    private static void checkPackNamespaces(Set<String> declared, List<String> problems)
            throws IOException {
        for (String name : declared) {
            Path dir = PACKS.resolve(name);
            if (!Files.isDirectory(dir)) continue;
            String expected = name.endsWith(SUFFIX)
                    ? name.substring(0, name.length() - SUFFIX.length())
                    : name;
            for (String kind : List.of("assets", "data")) {
                Path root = dir.resolve(kind);
                if (!Files.isDirectory(root)) continue;
                try (Stream<Path> namespaces = Files.list(root)) {
                    for (Path ns : namespaces.filter(Files::isDirectory).toList()) {
                        String actual = ns.getFileName().toString();
                        if (!actual.equals(expected)) {
                            problems.add("pack " + name + " contains a foreign namespace: "
                                    + kind + "/" + actual);
                        }
                    }
                }
            }
        }
    }

    /**
     * No foreign assets may remain in the root pack, bar the documented exception. Foreign
     * {@code data/} is deliberately not checked: recipe overrides stay in the root pack, because a
     * data pack is a separate repository from a resource pack and could not be switched off by the
     * same menu entry.
     */
    private static void checkRootPackIsClean(List<String> problems) throws IOException {
        for (String kind : List.of("assets")) {
            Path root = RESOURCES.resolve(kind);
            if (!Files.isDirectory(root)) continue;
            try (Stream<Path> namespaces = Files.list(root)) {
                for (Path ns : namespaces.filter(Files::isDirectory).toList()) {
                    if (OWN_NAMESPACES.contains(ns.getFileName().toString())) continue;
                    try (Stream<Path> walk = Files.walk(ns)) {
                        for (Path file : walk.filter(Files::isRegularFile).toList()) {
                            String rel = RESOURCES.relativize(file).toString().replace('\\', '/');
                            if (!ROOT_PACK_EXCEPTIONS.contains(rel)) {
                                problems.add("foreign file left in the root pack: " + rel);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Every pack needs a title in every locale, and the English suffix says which screen the pack
     * shows up in. Every pack is assets-only, so the suffix is checked rather than derived: it is how
     * a player tells these apart from the recipe changes, which no pack controls.
     */
    private static void checkTitles(Set<String> declared, List<String> problems) throws IOException {
        for (String locale : LOCALES) {
            Path file = LANG.resolve(locale + ".json");
            if (!Files.isRegularFile(file)) {
                problems.add("missing lang file: " + file);
                continue;
            }
            JsonObject lang = JsonParser.parseString(
                    Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            for (String name : declared) {
                String key = "pack." + CreateFoodCommon.MOD_ID + "." + name;
                if (!lang.has(key)) {
                    problems.add("missing title key " + key + " in " + locale);
                    continue;
                }
                if (!locale.equals("en_us")) continue;
                Path dir = PACKS.resolve(name);
                if (!Files.isDirectory(dir)) continue;
                String expected = "Assets";
                String title = lang.get(key).getAsString();
                if (!title.endsWith("(" + expected + ")")) {
                    problems.add("title for " + name + " should end in (" + expected
                            + ") but is: " + title);
                }
            }
        }
    }

    private static boolean hasAssets(Path dir) {
        return Files.isDirectory(dir.resolve("assets"));
    }

    private static boolean hasData(Path dir) {
        return Files.isDirectory(dir.resolve("data"));
    }
}
