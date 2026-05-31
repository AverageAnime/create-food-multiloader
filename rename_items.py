"""
Phase 1: Apply item ID renames to ModItems.java.
Renames string IDs and corresponding field names.
"""

import re

SRC = r"src\main\java\net\averageanime\createfood\item\ModItems.java"

# Map: old_item_id -> new_item_id
RENAMES = {
    # Explicit changelog renames (v2.0.0)
    "raw_chorus_cookie": "raw_chorus_fruit_cookie",
    "raw_sweet_berry_cookie": "raw_berry_cookie",

    # Blackstrap molasses bottle rename
    "molasses_bottle": "blackstrap_molasses_bottle",

    # raw_chocolate_cookie -> raw_chocolate_chip_cookie
    "raw_chocolate_cookie": "raw_chocolate_chip_cookie",

    # frosted -> cream (plain cream frosting items)
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

    # X_cream_frosted -> X_cream (flavored cream frosting items)
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

    # glazed -> removed from donuts
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

    # glazed -> removed from sweet rolls
    "caramel_glazed_sweet_roll": "caramel_sweet_roll",
    "caramel_glazed_chocolate_sweet_roll": "caramel_chocolate_sweet_roll",

    # pastry_bar -> pastry (filled pastry bars renamed to filled pastries)
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

    # chips -> chip (singular) and dark/white expanded names for cookies
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

    # beef meatball renames
    "meatball": "beef_meatball",
    "meatball_sandwich": "beef_meatball_sandwich",
    "meatball_stick_1": "beef_meatball_stick_1",
    "meatball_stick_2": "beef_meatball_stick_2",
    "meatball_stick_3": "beef_meatball_stick_3",
    "raw_meatball": "raw_beef_meatball",
    "small_meatballs": "small_beef_meatballs",
    "pasta_plate_meatballs": "pasta_plate_beef_meatballs",

    # marshmallow sticks: remove "covered"
    "chocolate_covered_marshmallow_stick": "chocolate_marshmallow_stick",
    "dark_chocolate_covered_marshmallow_stick": "dark_chocolate_marshmallow_stick",
    "white_chocolate_covered_marshmallow_stick": "white_chocolate_marshmallow_stick",

    # glazed fruits
    "caramel_glazed_apple": "caramel_apple",
    "caramel_glazed_berries": "caramel_berries",
    "chocolate_glazed_apple": "chocolate_apple",
    "dark_chocolate_glazed_apple": "dark_chocolate_apple",
    "dark_chocolate_glazed_berries": "dark_chocolate_berries",
    "white_chocolate_glazed_apple": "white_chocolate_apple",
    "white_chocolate_glazed_berries": "white_chocolate_berries",

    # cheese_and_X_bun -> X_bun_cheese (reordering)
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
}


def id_to_field(item_id: str) -> str:
    return item_id.upper()


with open(SRC, encoding="utf-8") as f:
    content = f.read()

renamed = 0
for old_id, new_id in RENAMES.items():
    old_field = id_to_field(old_id)
    new_field = id_to_field(new_id)

    # Replace string ID in ITEMS.register("old_id", ...)
    old_register = f'ITEMS.register("{old_id}"'
    new_register = f'ITEMS.register("{new_id}"'
    if old_register in content:
        content = content.replace(old_register, new_register)
        # Replace field name declaration
        old_decl = f'RegistryObject<'  # field could be any type
        # Use regex to replace field name specifically
        content = re.sub(
            r'(public static final RegistryObject<\w+>) ' + re.escape(old_field) + r' =',
            r'\1 ' + new_field + ' =',
            content
        )
        renamed += 1
    else:
        print(f"WARNING: '{old_id}' not found in ModItems.java")

with open(SRC, "w", encoding="utf-8") as f:
    f.write(content)

print(f"Done. Applied {renamed} renames.")
