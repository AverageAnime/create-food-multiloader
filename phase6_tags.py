"""Phase 6: Update forge tag files for renames, deletions, and new items/blocks."""

import os
import json

TAGS_ITEMS_DIR = r"src\main\resources\data\forge\tags\items"
TAGS_FLUIDS_DIR = r"src\main\resources\data\forge\tags\fluids"

def make_tag(item_id: str) -> str:
    return ('{\n  "replace": false,\n  "values": [\n    {\n'
            f'      "id": "createfood:{item_id}",\n'
            '      "required": false\n    }\n  ]\n}\n')

# ── Phase 1: item renames (old_id → new_id) ─────────────────────────────────
ITEM_RENAMES = {
    "raw_chorus_cookie": "raw_chorus_fruit_cookie",
    "raw_sweet_berry_cookie": "raw_berry_cookie",
    "molasses_bottle": "blackstrap_molasses_bottle",
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
    # Block renames (Phase 5)
    "berry_cream_frosted_cake": "berry_cream_cake",
    "chocolate_cream_frosted_cake": "chocolate_cream_cake",
    "chorus_fruit_cream_frosted_cake": "chorus_fruit_cream_cake",
    "frosted_cake": "cream_cake",
    "glow_berry_cream_frosted_cake": "glow_berry_cream_cake",
    "ube_cream_frosted_ube_cake": "ube_cream_ube_cake",
    "cake_chorus_fruit": "cream_cake_chorus_fruit",
    "cake_glow_berry": "cream_cake_glow_berry",
}

# ── Phase 2: deprecated items to delete ──────────────────────────────────────
DEPRECATED_IDS = {
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
    # Also old cupcake/muffin base names
    "raw_cupcake_base", "raw_muffin_base",
}

# ── Phase 4: new items to add ────────────────────────────────────────────────
with open(r"C:\temp\items_to_add.txt", encoding="utf-8") as f:
    NEW_ITEMS = [line.strip() for line in f if line.strip()]

# ── Phase 5: new block items to add ─────────────────────────────────────────
NEW_BLOCK_ITEMS = [
    "apple_cream_cake", "cheese_block", "chocolate_cake_base",
    "chocolate_cream_chocolate_cake", "cream_chocolate_cake",
    "meat_pie", "melon_cream_cake", "pumpkin_pie_block", "raw_meat_pie",
]

# ────────────────────────────────────────────────────────────────────────────

pass  # make_tag defined above

items_dir = TAGS_ITEMS_DIR
renamed = deleted = created = 0

# Step 1: Rename tag files for Phase 1 & 5 renames
for old_id, new_id in ITEM_RENAMES.items():
    old_path = os.path.join(items_dir, f"{old_id}.json")
    new_path = os.path.join(items_dir, f"{new_id}.json")
    if os.path.exists(old_path):
        if os.path.exists(new_path):
            # new already exists, just delete old
            os.remove(old_path)
        else:
            os.rename(old_path, new_path)
            # Update content to use new item ID
            with open(new_path, "w", encoding="utf-8") as f:
                f.write(make_tag(new_id))
        renamed += 1

# Step 2: Delete tag files for deprecated items
for item_id in DEPRECATED_IDS:
    path = os.path.join(items_dir, f"{item_id}.json")
    if os.path.exists(path):
        os.remove(path)
        deleted += 1

# Step 3: Create new tag files for Phase 4 new items
for item_id in NEW_ITEMS:
    path = os.path.join(items_dir, f"{item_id}.json")
    if not os.path.exists(path):
        with open(path, "w", encoding="utf-8") as f:
            f.write(make_tag(item_id))
        created += 1

# Step 4: Create new tag files for Phase 5 new block items
for item_id in NEW_BLOCK_ITEMS:
    path = os.path.join(items_dir, f"{item_id}.json")
    if not os.path.exists(path):
        with open(path, "w", encoding="utf-8") as f:
            f.write(make_tag(item_id))
        created += 1

print(f"Renamed: {renamed}, Deleted: {deleted}, Created: {created}")
print(f"Total item tags now: {len(os.listdir(items_dir))}")
