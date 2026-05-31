"""Phase 7: Recipe updates.

Steps:
1. Fix 324+ existing recipe files: replace old item IDs with renamed IDs
2. Copy 1,274 new recipe files from 1.21.1 with format transformations:
   - c: → forge: (tag namespaces)
   - neoforge:conditions / fabric:load_conditions → remove
   - category → remove
   - result id → result item (crafting) or result string (cooking)
   - fluid result id → fluid
   - Create fluid ingredient tag → fluidTag
   - campfire_cooking/ dir → campfire/
3. Delete recipe files for deprecated items
"""

import os
import re
import json
import subprocess

RECIPES_DIR = r"src\main\resources\data\createfood\recipes"

# ── Item ID renames (from Phase 1 + Phase 5 block renames) ──────────────────
ITEM_RENAMES = {
    "raw_chorus_cookie": "raw_chorus_fruit_cookie",
    "raw_sweet_berry_cookie": "raw_berry_cookie",
    "raw_chocolate_cookie": "raw_chocolate_chip_cookie",
    "frosted_cupcake": "cream_cupcake",
    "frosted_chocolate_cupcake": "cream_chocolate_cupcake",
    "frosted_mini_waffle": "cream_mini_waffle",
    "frosted_mini_waffle_chorus_fruit": "cream_mini_waffle_chorus_fruit",
    "frosted_mini_waffle_glow_berry": "cream_mini_waffle_glow_berry",
    "frosted_mini_waffle_sweet_berry": "cream_mini_waffle_sweet_berry",
    "frosted_sweet_roll_chorus_fruit": "cream_sweet_roll_chorus_fruit",
    "frosted_sweet_roll_glow_berry": "cream_sweet_roll_glow_berry",
    "frosted_sweet_roll_sweet_berry": "cream_sweet_roll_sweet_berry",
    "frosted_chocolate_sweet_roll_chorus_fruit": "cream_chocolate_sweet_roll_chorus_fruit",
    "frosted_chocolate_sweet_roll_glow_berry": "cream_chocolate_sweet_roll_glow_berry",
    "frosted_chocolate_sweet_roll_sweet_berry": "cream_chocolate_sweet_roll_sweet_berry",
    "apple_cream_frosted_cupcake": "apple_cream_cupcake",
    "apple_cream_frosted_sweet_roll": "apple_cream_sweet_roll",
    "apple_cream_frosted_mini_waffle": "apple_cream_mini_waffle",
    "apple_cream_frosted_chocolate_cupcake": "apple_cream_chocolate_cupcake",
    "apple_cream_frosted_chocolate_sweet_roll": "apple_cream_chocolate_sweet_roll",
    "berry_cream_frosted_cupcake": "berry_cream_cupcake",
    "berry_cream_frosted_sweet_roll": "berry_cream_sweet_roll",
    "berry_cream_frosted_mini_waffle": "berry_cream_mini_waffle",
    "berry_cream_frosted_chocolate_cupcake": "berry_cream_chocolate_cupcake",
    "berry_cream_frosted_chocolate_sweet_roll": "berry_cream_chocolate_sweet_roll",
    "berry_cream_frosted_sweet_roll_sweet_berry": "berry_cream_sweet_roll_sweet_berry",
    "berry_cream_frosted_mini_waffle_sweet_berry": "berry_cream_mini_waffle_sweet_berry",
    "berry_cream_frosted_chocolate_sweet_roll_sweet_berry": "berry_cream_chocolate_sweet_roll_sweet_berry",
    "chocolate_cream_frosted_cupcake": "chocolate_cream_cupcake",
    "chocolate_cream_frosted_chocolate_cupcake": "chocolate_cream_chocolate_cupcake",
    "chocolate_cream_frosted_mini_waffle": "chocolate_cream_mini_waffle",
    "chorus_fruit_cream_frosted_cupcake": "chorus_fruit_cream_cupcake",
    "chorus_fruit_cream_frosted_chocolate_cupcake": "chorus_fruit_cream_chocolate_cupcake",
    "chorus_fruit_cream_frosted_mini_waffle": "chorus_fruit_cream_mini_waffle",
    "chorus_fruit_cream_frosted_sweet_roll": "chorus_fruit_cream_sweet_roll",
    "chorus_fruit_cream_frosted_chocolate_sweet_roll": "chorus_fruit_cream_chocolate_sweet_roll",
    "chorus_fruit_cream_frosted_mini_waffle_chorus_fruit": "chorus_fruit_cream_mini_waffle_chorus_fruit",
    "chorus_fruit_cream_frosted_sweet_roll_chorus_fruit": "chorus_fruit_cream_sweet_roll_chorus_fruit",
    "chorus_fruit_cream_frosted_chocolate_sweet_roll_chorus_fruit": "chorus_fruit_cream_chocolate_sweet_roll_chorus_fruit",
    "glow_berry_cream_frosted_cupcake": "glow_berry_cream_cupcake",
    "glow_berry_cream_frosted_chocolate_cupcake": "glow_berry_cream_chocolate_cupcake",
    "glow_berry_cream_frosted_mini_waffle": "glow_berry_cream_mini_waffle",
    "glow_berry_cream_frosted_sweet_roll": "glow_berry_cream_sweet_roll",
    "glow_berry_cream_frosted_chocolate_sweet_roll": "glow_berry_cream_chocolate_sweet_roll",
    "glow_berry_cream_frosted_mini_waffle_glow_berry": "glow_berry_cream_mini_waffle_glow_berry",
    "glow_berry_cream_frosted_sweet_roll_glow_berry": "glow_berry_cream_sweet_roll_glow_berry",
    "glow_berry_cream_frosted_chocolate_sweet_roll_glow_berry": "glow_berry_cream_chocolate_sweet_roll_glow_berry",
    "melon_cream_frosted_cupcake": "melon_cream_cupcake",
    "melon_cream_frosted_chocolate_cupcake": "melon_cream_chocolate_cupcake",
    "melon_cream_frosted_mini_waffle": "melon_cream_mini_waffle",
    "melon_cream_frosted_sweet_roll": "melon_cream_sweet_roll",
    "melon_cream_frosted_chocolate_sweet_roll": "melon_cream_chocolate_sweet_roll",
    "cream_glazed_donut": "cream_donut",
    "cream_glazed_chocolate_donut": "cream_chocolate_donut",
    "apple_cream_glazed_donut": "apple_cream_donut",
    "apple_cream_glazed_chocolate_donut": "apple_cream_chocolate_donut",
    "berry_cream_glazed_donut": "berry_cream_donut",
    "berry_cream_glazed_chocolate_donut": "berry_cream_chocolate_donut",
    "chorus_fruit_cream_glazed_donut": "chorus_fruit_cream_donut",
    "chorus_fruit_cream_glazed_chocolate_donut": "chorus_fruit_cream_chocolate_donut",
    "glow_berry_cream_glazed_donut": "glow_berry_cream_donut",
    "glow_berry_cream_glazed_chocolate_donut": "glow_berry_cream_chocolate_donut",
    "melon_cream_glazed_donut": "melon_cream_donut",
    "melon_cream_glazed_chocolate_donut": "melon_cream_chocolate_donut",
    "caramel_glazed_sweet_roll": "caramel_sweet_roll",
    "caramel_glazed_chocolate_sweet_roll": "caramel_chocolate_sweet_roll",
    "apple_cream_chocolate_pastry_bar": "apple_cream_chocolate_pastry",
    "apple_cream_pastry_bar": "apple_cream_pastry",
    "berry_cream_chocolate_pastry_bar": "berry_cream_chocolate_pastry",
    "berry_cream_pastry_bar": "berry_cream_pastry",
    "caramel_chocolate_pastry_bar": "caramel_chocolate_pastry",
    "caramel_pastry_bar": "caramel_pastry",
    "chorus_fruit_cream_chocolate_pastry_bar": "chorus_fruit_cream_chocolate_pastry",
    "chorus_fruit_cream_pastry_bar": "chorus_fruit_cream_pastry",
    "glow_berry_cream_chocolate_pastry_bar": "glow_berry_cream_chocolate_pastry",
    "glow_berry_cream_pastry_bar": "glow_berry_cream_pastry",
    "melon_cream_chocolate_pastry_bar": "melon_cream_chocolate_pastry",
    "melon_cream_pastry_bar": "melon_cream_pastry",
    "butterscotch_chips_cookie": "butterscotch_chip_cookie",
    "butterscotch_chips_chocolate_cookie": "butterscotch_chip_chocolate_cookie",
    "raw_butterscotch_chips_cookie": "raw_butterscotch_chip_cookie",
    "raw_butterscotch_chips_chocolate_cookie": "raw_butterscotch_chip_chocolate_cookie",
    "caramel_chips_cookie": "caramel_chip_cookie",
    "caramel_chips_chocolate_cookie": "caramel_chip_chocolate_cookie",
    "raw_caramel_chips_cookie": "raw_caramel_chip_cookie",
    "raw_caramel_chips_chocolate_cookie": "raw_caramel_chip_chocolate_cookie",
    "dark_chips_cookie": "dark_chocolate_chip_cookie",
    "dark_chips_chocolate_cookie": "dark_chocolate_chip_chocolate_cookie",
    "raw_dark_chips_cookie": "raw_dark_chocolate_chip_cookie",
    "raw_dark_chips_chocolate_cookie": "raw_dark_chocolate_chip_chocolate_cookie",
    "toffee_chips_cookie": "toffee_chip_cookie",
    "toffee_chips_chocolate_cookie": "toffee_chip_chocolate_cookie",
    "raw_toffee_chips_cookie": "raw_toffee_chip_cookie",
    "raw_toffee_chips_chocolate_cookie": "raw_toffee_chip_chocolate_cookie",
    "white_chips_cookie": "white_chocolate_chip_cookie",
    "white_chips_chocolate_cookie": "white_chocolate_chip_chocolate_cookie",
    "raw_white_chips_cookie": "raw_white_chocolate_chip_cookie",
    "raw_white_chips_chocolate_cookie": "raw_white_chocolate_chip_chocolate_cookie",
    "meatball": "beef_meatball",
    "meatball_sandwich": "beef_meatball_sandwich",
    "meatball_stick_1": "beef_meatball_stick_1",
    "meatball_stick_2": "beef_meatball_stick_2",
    "meatball_stick_3": "beef_meatball_stick_3",
    "raw_meatball": "raw_beef_meatball",
    "small_meatballs": "small_beef_meatballs",
    "pasta_plate_meatballs": "pasta_plate_beef_meatballs",
    "chocolate_covered_marshmallow_stick": "chocolate_marshmallow_stick",
    "dark_chocolate_covered_marshmallow_stick": "dark_chocolate_marshmallow_stick",
    "white_chocolate_covered_marshmallow_stick": "white_chocolate_marshmallow_stick",
    "caramel_glazed_apple": "caramel_apple",
    "caramel_glazed_berries": "caramel_berries",
    "chocolate_glazed_apple": "chocolate_apple",
    "dark_chocolate_glazed_apple": "dark_chocolate_apple",
    "dark_chocolate_glazed_berries": "dark_chocolate_berries",
    "white_chocolate_glazed_apple": "white_chocolate_apple",
    "white_chocolate_glazed_berries": "white_chocolate_berries",
    "cheese_and_beef_bun": "beef_bun_cheese",
    "cheese_and_beef_bun_bacon": "beef_bun_cheese_bacon",
    "cheese_and_beef_bun_lettuce": "beef_bun_cheese_lettuce",
    "cheese_and_beef_bun_lettuce_tomato": "beef_bun_cheese_lettuce_tomato",
    "cheese_and_beef_bun_bacon_lettuce": "beef_bun_cheese_bacon_lettuce",
    "cheese_and_beef_bun_bacon_lettuce_tomato": "beef_bun_cheese_bacon_lettuce_tomato",
    "cheese_and_beef_bun_onion": "beef_bun_cheese_onion",
    "cheese_and_beef_bun_onion_bacon": "beef_bun_cheese_onion_bacon",
    "cheese_and_beef_bun_onion_lettuce": "beef_bun_cheese_onion_lettuce",
    "cheese_and_beef_bun_onion_lettuce_tomato": "beef_bun_cheese_onion_lettuce_tomato",
    "cheese_and_beef_bun_onion_bacon_lettuce": "beef_bun_cheese_onion_bacon_lettuce",
    "cheese_and_chicken_bun": "chicken_bun_cheese",
    "cheese_and_chicken_bun_lettuce": "chicken_bun_cheese_lettuce",
    "cheese_and_chicken_bun_lettuce_tomato": "chicken_bun_cheese_lettuce_tomato",
    "cheese_and_chicken_bun_bacon_lettuce": "chicken_bun_cheese_bacon_lettuce",
    "cheese_and_eggplant_bun": "eggplant_bun_cheese",
    "cheese_and_eggplant_bun_lettuce": "eggplant_bun_cheese_lettuce",
    "cheese_and_eggplant_bun_lettuce_tomato": "eggplant_bun_cheese_lettuce_tomato",
    "cheese_and_sausage_biscuit": "sausage_biscuit_cheese",
    "cheese_and_sausage_biscuit_sandwich": "sausage_biscuit_sandwich_cheese",
    # block renames
    "berry_cream_frosted_cake": "berry_cream_cake",
    "chocolate_cream_frosted_cake": "chocolate_cream_cake",
    "chorus_fruit_cream_frosted_cake": "chorus_fruit_cream_cake",
    "frosted_cake": "cream_cake",
    "glow_berry_cream_frosted_cake": "glow_berry_cream_cake",
    "ube_cream_frosted_ube_cake": "ube_cream_ube_cake",
    "cake_chorus_fruit": "cream_cake_chorus_fruit",
    "cake_glow_berry": "cream_cake_glow_berry",
}

# Deprecated item IDs
DEPRECATED = {
    "raw_cake_base", "raw_ube_cake_base", "raw_cupcake", "raw_muffin",
    "raw_chocolate_chip_muffin", "raw_caramel_chip_muffin",
    "raw_dark_chocolate_chip_muffin", "raw_toffee_chip_muffin",
    "raw_white_chocolate_chip_muffin", "raw_butterscotch_chip_muffin",
    "mutton_wrap_onion_lettuce_tomato", "mutton_wrap_onion_tomato",
    "hot_cheese_and_sausage_biscuit_sandwich", "hot_cheese_biscuit_sandwich",
    "hot_sausage_biscuit_sandwich",
    "pastry_bar_base", "raw_pastry_bar_base",
    "chocolate_pastry_bar_base", "raw_chocolate_pastry_bar_base",
    "raw_chocolate_cupcake_base",
}

# Cooking recipe types that use simple string result
COOKING_TYPES = {"minecraft:smelting", "minecraft:campfire_cooking",
                 "minecraft:smoking", "minecraft:blasting"}


def transform_1211_recipe(content: str, recipe_path: str) -> str:
    """Transform a 1.21.1 recipe JSON to 1.20.1-forge format."""
    try:
        data = json.loads(content)
    except json.JSONDecodeError:
        return content  # return unchanged if can't parse

    recipe_type = data.get("type", "")

    # Remove 1.21.1-only fields
    data.pop("neoforge:conditions", None)
    data.pop("fabric:load_conditions", None)
    data.pop("category", None)

    # Transform tag namespaces: c: → forge:
    content_str = json.dumps(data)
    content_str = re.sub(r'"c:([^"]+)"', lambda m: f'"forge:{m.group(1)}"', content_str)
    data = json.loads(content_str)

    # Transform neoforge: → forge: in condition types
    # (handled by removing conditions above)

    # Transform result format
    if "result" in data:
        result = data["result"]
        if isinstance(result, dict):
            if recipe_type in COOKING_TYPES:
                # Cooking: {"count": 1, "id": "X"} → "X"
                item_id = result.get("id", "")
                if item_id:
                    data["result"] = item_id
            else:
                # Crafting: {"id": "X"} → {"item": "X"}
                # or {"count": N, "id": "X"} → {"count": N, "item": "X"}
                if "id" in result:
                    result["item"] = result.pop("id")
                data["result"] = result

    # Transform results array (Create recipes)
    if "results" in data:
        new_results = []
        for r in data["results"]:
            if isinstance(r, dict):
                # Fluid result: {"amount": N, "id": "X"} → {"amount": N, "fluid": "X"}
                if "id" in r and "amount" in r and "count" not in r:
                    r["fluid"] = r.pop("id")
                # Item result: {"count": N, "item": {"id": "X"}} → {"count": N, "item": "X"}
                elif "item" in r and isinstance(r["item"], dict) and "id" in r["item"]:
                    r["item"] = r["item"]["id"]
                # Item result: {"count": N, "id": "X"} → {"count": N, "item": "X"}
                elif "id" in r and "count" in r:
                    r["item"] = r.pop("id")
            new_results.append(r)
        data["results"] = new_results

    # Transform Create fluid ingredients
    # {"type": "create:fluid_ingredient", "amount": N, "fluid_tag": "forge:X"}
    # Already handled by c: → forge: above
    # But also handle fluidTag vs fluid_tag
    if "ingredients" in data:
        new_ingredients = []
        for ing in data["ingredients"]:
            if isinstance(ing, dict):
                # Fluid ingredient: may use fluidTag or fluid_tag
                if "fluidTag" not in ing and "fluid_tag" in ing:
                    ing["fluidTag"] = ing.pop("fluid_tag")
                    ing.pop("type", None)
            new_ingredients.append(ing)
        data["ingredients"] = new_ingredients

    return json.dumps(data, indent=4)


# ── Step 1: Fix existing recipes (apply item ID renames) ─────────────────────
print("Step 1: Fixing existing recipe files...")
fixed = 0
for root, dirs, files in os.walk(RECIPES_DIR):
    for fname in files:
        if not fname.endswith(".json"):
            continue
        fpath = os.path.join(root, fname)
        with open(fpath, encoding="utf-8") as f:
            content = f.read()

        new_content = content
        for old_id, new_id in ITEM_RENAMES.items():
            # Replace in createfood: references
            new_content = new_content.replace(f'"createfood:{old_id}"', f'"createfood:{new_id}"')
            # Replace in forge: tag references
            new_content = new_content.replace(f'"forge:{old_id}"', f'"forge:{new_id}"')

        if new_content != content:
            with open(fpath, "w", encoding="utf-8") as f:
                f.write(new_content)
            fixed += 1

print(f"  Fixed {fixed} existing recipe files")

# ── Step 2: Delete deprecated item recipes ───────────────────────────────────
print("Step 2: Checking for deprecated item recipes...")
deleted = 0
for root, dirs, files in os.walk(RECIPES_DIR):
    for fname in files:
        if not fname.endswith(".json"):
            continue
        fpath = os.path.join(root, fname)
        with open(fpath, encoding="utf-8") as f:
            content = f.read()
        # Delete if the RESULT references a deprecated item
        for dep_id in DEPRECATED:
            if f'"createfood:{dep_id}"' in content and '"result"' in content:
                # Check it's actually a result, not just an ingredient
                try:
                    data = json.loads(content)
                    result = data.get("result", "")
                    if isinstance(result, str) and f"createfood:{dep_id}" in result:
                        os.remove(fpath)
                        deleted += 1
                        break
                    elif isinstance(result, dict) and f"createfood:{dep_id}" in str(result):
                        os.remove(fpath)
                        deleted += 1
                        break
                except:
                    pass

print(f"  Deleted {deleted} deprecated item recipe files")

# ── Step 3: Copy new recipe files from 1.21.1 with transformations ───────────
print("Step 3: Copying new recipes from 1.21.1...")

# Get list of 1.21.1 main recipe files
result = subprocess.run(
    ["git", "ls-tree", "-r", "1.21.1", "--name-only"],
    capture_output=True, text=True, encoding="utf-8"
)
all_1211_files = [
    line for line in result.stdout.splitlines()
    if "common/src/main/resources/data/createfood/recipe/" in line
]

copied = skipped_compat = 0
COMPAT_ONLY_1201 = {"beachparty", "createbitterballen", "farm_and_charm"}

for src_path in all_1211_files:
    # Extract relative path from recipe root
    rel = src_path.replace("common/src/main/resources/data/createfood/recipe/", "")

    # Skip compat-only subdirs
    top_dir = rel.split("/")[0]
    if top_dir in COMPAT_ONLY_1201:
        skipped_compat += 1
        continue

    # Map campfire_cooking/ → campfire/
    rel_dest = rel.replace("campfire_cooking/", "campfire/")

    dest_path = os.path.join(RECIPES_DIR, rel_dest)

    # Skip if already exists (don't overwrite 1.20.1 compat recipes)
    if os.path.exists(dest_path):
        continue

    # Fetch content from git
    fetch = subprocess.run(
        ["git", "show", f"1.21.1:{src_path}"],
        capture_output=True, text=True, encoding="utf-8"
    )
    if fetch.returncode != 0:
        continue

    # Transform
    transformed = transform_1211_recipe(fetch.stdout, rel)

    # Write
    os.makedirs(os.path.dirname(dest_path), exist_ok=True)
    with open(dest_path, "w", encoding="utf-8", newline="\n") as f:
        f.write(transformed)
    copied += 1

print(f"  Copied {copied} new recipe files (skipped {skipped_compat} compat-only)")

# Final count
total = sum(len(files) for _, _, files in os.walk(RECIPES_DIR) if files)
print(f"\nTotal recipes now: {total}")
print("Done.")
