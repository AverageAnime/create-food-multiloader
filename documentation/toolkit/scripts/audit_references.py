#!/usr/bin/env python3
"""Post-generation validation for Create: Food.

Checks that every tag and item id referenced by recipes and by the wip spec files
actually resolves. Catches the class of bug where a tag is consumed by recipes but
never populated (c:corn_kernels, c:bread, c:raw_chorus_cookie all shipped that way),
and where a spec names an item or tooltip key that does not exist.

Run from the repository root:
    python documentation/toolkit/scripts/audit_references.py

Exit code is 1 if anything failed, so it can gate a build.
"""
import json
import os
import re
import sys
from collections import defaultdict

ROOT = os.getcwd()
GEN = os.path.join(ROOT, "common", "src", "generated", "resources", "data")
MAIN = os.path.join(ROOT, "common", "src", "main", "resources")
LANG = os.path.join(MAIN, "assets", "createfood", "lang", "en_us.json")

# Tags NeoForge or another mod is expected to populate at runtime; createfood
# deliberately consumes these without declaring them.
CONVENTION = (
    "c:dyes/", "c:buckets/", "c:crops/", "c:seeds", "c:strings", "c:leathers",
    "c:rods/", "c:nuggets/", "c:ingots/", "c:foods/", "c:stones", "c:water",
    "c:tools/", "c:storage_blocks/", "c:dusts/", "c:gems/", "c:drinks/",
    "c:fruits/", "c:vegetables/", "c:flours/",
)

# Fluid tags supplied by Create rather than by this mod.
EXTERNAL_FLUIDS = {"c:honey", "c:milk", "c:chocolate", "c:water", "c:tea"}


def load_json(path):
    with open(path, encoding="utf-8") as fh:
        return json.load(fh)


def declared_tags():
    """Every tag file createfood generates or ships, as 'namespace:path'."""
    out = set()
    for base in (GEN, os.path.join(MAIN, "data")):
        for dirpath, _dirs, files in os.walk(base):
            norm = dirpath.replace("\\", "/")
            if "/tags/" not in norm:
                continue
            ns = norm.split("/data/", 1)[1].split("/")[0]
            kind = norm.split("/tags/", 1)[1].split("/")[0]  # item | fluid | block
            for name in files:
                if not name.endswith(".json"):
                    continue
                sub = norm.split("/tags/", 1)[1]
                sub = sub[len(kind):].lstrip("/")
                path = (sub + "/" + name[:-5]).lstrip("/")
                out.add("%s:%s|%s" % (ns, path, kind))
    return out


def registered_items():
    ids = set()
    for key in load_json(LANG):
        m = re.match(r"^(?:item|block)\.createfood\.([a-z0-9_]+)$", key)
        if m:
            ids.add(m.group(1))
    return ids


def tooltip_keys():
    keys = set()
    for key in load_json(LANG):
        m = re.match(r"^tooltip\.createfood\.([a-z0-9_]+)_ingredient$", key)
        if m:
            keys.add(m.group(1))
    compat = {k.split(".")[-1] for k in load_json(LANG) if k.startswith("tooltip.compat.")}
    return keys, compat


def check_recipe_tags(tags, failures):
    """Every "tag": "ns:path" in a recipe must resolve to a declared or convention tag."""
    seen = defaultdict(list)
    for dirpath, _dirs, files in os.walk(MAIN):
        if os.sep + "recipe" not in dirpath:
            continue
        for name in files:
            if not name.endswith(".json"):
                continue
            full = os.path.join(dirpath, name)
            with open(full, encoding="utf-8") as fh:
                text = fh.read()
            for m in re.finditer(r'"(tag|fluid_tag)"\s*:\s*"([a-z0-9_.-]+:[a-z0-9_/.-]+)"', text):
                kind = "fluid" if m.group(1) == "fluid_tag" else "item"
                seen[(m.group(2), kind)].append(os.path.relpath(full, ROOT))
    for (tag, kind), users in sorted(seen.items()):
        if not tag.startswith("c:"):
            continue  # another mod's tag; that mod declares it
        if tag.startswith(CONVENTION):
            continue
        if kind == "fluid" and tag in EXTERNAL_FLUIDS:
            continue
        if "%s|%s" % (tag, kind) in tags:
            continue
        failures.append(
            "undeclared %s tag %s used by %d recipe(s), e.g. %s"
            % (kind, tag, len(users), users[0])
        )
    return len(seen)


def check_specs(items, tips, compat, failures):
    """Spec files must reference real items, and declare tooltip keys that don't exist yet."""
    wip = os.path.join(ROOT, "documentation", "wip")
    checked = 0
    for name in sorted(os.listdir(wip)):
        if not name.endswith(".json"):
            continue
        path = os.path.join(wip, name)
        try:
            spec = load_json(path)
        except json.JSONDecodeError as exc:
            failures.append("%s is not valid JSON: %s" % (name, exc))
            continue
        entries = spec.get("items", [])
        # Lang output is merged across the whole file, so a custom key declared on any
        # entry covers every entry in that file.
        file_tips, file_compat = set(), set()
        for e in entries:
            for c in e.get("customTooltips", []) or e.get("customTooltipKeys", []):
                file_tips.add(c if isinstance(c, str) else c.get("key"))
            for c in e.get("customCompatKeys", []):
                file_compat.add(c if isinstance(c, str) else c.get("key"))
        declared = set()
        for e in entries:
            eid = e.get("id")
            if not eid:
                continue
            declared.add(eid)
            etype = e.get("type") or e.get("registrationType") or ""
            # Mirror the sub-items the toolkit generates so pending specs don't false-positive.
            if etype in ("block_pie", "block_pizza", "block_cake", "block_cheese", "block_gyro_meat"):
                declared.add(e.get("sliceId") or eid + "_slice")
            if etype in ("block_pie", "block_pizza"):
                declared.add("raw_" + eid)
            if etype == "block_waffle":
                declared.add(e.get("miniId") or "mini_" + eid)
            if etype in ("fluidBlock", "fluidEntry", "fluid"):
                declared.update({eid + "_bucket", eid + "_block"})
                if e.get("createBottle"):
                    declared.add(eid + "_bottle")
                if e.get("createBowl"):
                    declared.add(eid + "_bowl")
            for v in e.get("variants", []) or []:
                if v.get("id"):
                    declared.add(v["id"])
        for entry in entries:
            checked += 1
            eid = entry.get("id", "?")
            custom = file_tips
            for key in entry.get("tooltips", []):
                if key not in tips and key not in custom:
                    failures.append(
                        "%s: %s uses tooltip key '%s' that has no lang entry and is not in customTooltips"
                        % (name, eid, key)
                    )
            ckey = entry.get("compatKey", "")
            ckey = ckey.replace("tooltip.compat.", "")
            declared_compat = file_compat
            if entry.get("isCompat") and ckey and ckey not in compat and ckey not in declared_compat:
                failures.append(
                    "%s: %s has compatKey '%s' with no lang entry and no customCompatKeys entry"
                    % (name, eid, ckey)
                )
            for rec in entry.get("recipes", []):
                out = rec.get("output", eid)
                if ":" in out:
                    continue  # another mod's item
                if out not in items and out not in declared:
                    failures.append(
                        "%s: %s has a recipe producing '%s', which is not a registered item"
                        % (name, eid, out)
                    )
    return checked


def main():
    failures = []
    tags = declared_tags()
    items = registered_items()
    tips, compat = tooltip_keys()

    n_tags = check_recipe_tags(tags, failures)
    n_specs = check_specs(items, tips, compat, failures)

    print("declared tags: %d | registered items: %d | ingredient keys: %d | compat keys: %d"
          % (len(tags), len(items), len(tips), len(compat)))
    print("distinct tags referenced by recipes: %d | spec entries checked: %d" % (n_tags, n_specs))

    if failures:
        print("\n%d problem(s):" % len(failures))
        for f in failures:
            print("  - " + f)
        return 1
    print("\nno problems found")
    return 0


if __name__ == "__main__":
    sys.exit(main())
