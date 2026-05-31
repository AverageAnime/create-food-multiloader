"""Phase 2: Remove deprecated item registrations from ModItems.java."""

IDS_TO_REMOVE = [
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
]

MARKERS = {f'register("{item_id}"' for item_id in IDS_TO_REMOVE}

SRC = r"src\main\java\net\averageanime\createfood\item\ModItems.java"

with open(SRC, encoding="utf-8") as f:
    lines = f.readlines()

removed = 0
kept = []
for line in lines:
    if any(m in line for m in MARKERS):
        removed += 1
    else:
        kept.append(line)

with open(SRC, "w", encoding="utf-8") as f:
    f.writelines(kept)

print(f"Removed {removed} deprecated item registrations.")
