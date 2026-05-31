"""Phase 4: Add 288 new items from 1.21.1 to 1.20.1-forge ModItems.java.

Reads each item definition from ItemRegistry.java and converts to DeferredRegister format.
Custom effects not in 1.20.1-forge (SATIATION, SUSTENANCE, TUNDRA_STRIDER, etc.) are skipped.
"""

import re

ITEMS_TO_ADD_FILE = r"C:\temp\items_to_add.txt"
ITEM_REGISTRY_FILE = r"C:\temp\ItemRegistry_1211.java"
MODITEMS_SRC = r"src\main\java\net\averageanime\createfood\item\ModItems.java"

# Effects that exist in 1.20.1-forge (either via ModEffects or MobEffects)
SUPPORTED_FD_EFFECTS = {"COMFORT", "NOURISHMENT"}
UNSUPPORTED_EFFECTS = {"SATIATION", "SUSTENANCE", "TUNDRA_STRIDER", "SUGAR_RUSH",
                       "LIGHTNING", "ANIMAL_CHARM", "FEAST", "FARMERS_BLESSING",
                       "SATIATED_SHIELD", "REST", "CHARISMA", "VIGOR",
                       "PRESERVATION", "SULFUR", "MUSTARD", "RESTED", "WARMTH",
                       "REPULSION", "VITALITY"}

# Map vanilla MobEffects referenced as fx(MobEffects.X, dur) in 1.21.1
# These are Holder<MobEffect> in 1.21.1 but MobEffects.X in 1.20.1
VANILLA_EFFECT_MAP = {
    "LUCK": "MobEffects.LUCK",
    "FIRE_RESISTANCE": "MobEffects.FIRE_RESISTANCE",
    "POISON": "MobEffects.POISON",
    "NIGHT_VISION": "MobEffects.NIGHT_VISION",
    "MOVEMENT_SPEED": "MobEffects.MOVEMENT_SPEED",
    "DIG_SPEED": "MobEffects.DIG_SPEED",
    "REGENERATION": "MobEffects.REGENERATION",
    "ABSORPTION": "MobEffects.ABSORPTION",
    "SATURATION": "MobEffects.SATURATION",
    "HEAL": "MobEffects.HEAL",
    "BLINDNESS": "MobEffects.BLINDNESS",
    "CONFUSION": "MobEffects.CONFUSION",
    "WEAKNESS": "MobEffects.WEAKNESS",
    "DAMAGE_BOOST": "MobEffects.DAMAGE_BOOST",
}


def parse_effects(fx_args: list[str]) -> list[tuple]:
    """Parse fx(...) calls into (effect_expr, duration, amplifier) tuples.
    Returns list of (effect_code, duration, amp) or empty list if no effects.
    """
    effects = []
    for arg in fx_args:
        arg = arg.strip()
        # Match fx(EFFECT, dur) or fx(EFFECT, dur, amp)
        # Also fx(MobEffects.EFFECT, dur) or fx(MobEffects.EFFECT, dur, amp)
        m = re.match(r'fx\((\w+(?:\.\w+)?),\s*(\d+)(?:,\s*(\d+))?\)', arg)
        if not m:
            continue
        effect_name = m.group(1)
        duration = int(m.group(2))
        amp = int(m.group(3)) if m.group(3) else 0

        # Check if it's a MobEffects.X reference
        if effect_name.startswith("MobEffects."):
            vanilla_key = effect_name.split(".")[1]
            if vanilla_key in VANILLA_EFFECT_MAP:
                effects.append((VANILLA_EFFECT_MAP[vanilla_key], duration, amp))
            # else skip unknown vanilla
        elif effect_name in SUPPORTED_FD_EFFECTS:
            effects.append((f"ModEffects.{effect_name}.get()", duration, amp))
        elif effect_name in UNSUPPORTED_EFFECTS:
            pass  # skip custom effects not yet in 1.20.1
        # else: unknown effect, skip

    return effects


def build_food_props(nutrition: int, saturation: float, fast: bool, effects: list[tuple]) -> str:
    parts = [f"new FoodProperties.Builder().nutrition({nutrition}).saturationMod({saturation}f)"]
    for (effect, dur, amp) in effects:
        parts.append(f".effect(new MobEffectInstance({effect}, {dur}, {amp}), 1.0f)")
    if fast:
        parts.append(".fast()")
    parts.append(".build()")
    return "".join(parts)


def convert_item(item_id: str, line: str) -> str | None:
    """Convert a 1.21.1 ItemRegistry line to a 1.20.1-forge ITEMS.register(...) declaration."""
    field = item_id.upper()

    # Determine factory method
    m = re.search(r'=\s*(\w+)\(', line)
    if not m:
        return None
    method = m.group(1)

    if method == "plain":
        return (
            f"    public static final RegistryObject<Item> {field} = "
            f'ITEMS.register("{item_id}", () -> new Item(new Item.Properties()));'
        )

    if method == "pipingBag":
        return (
            f"    public static final RegistryObject<Item> {field} = "
            f'ITEMS.register("{item_id}", () -> new Item(new Item.Properties().stacksTo(1)));'
        )

    if method == "plainCr":
        # plainCr("id", "craft_remainder", tips(...))
        m2 = re.search(r'plainCr\("([^"]+)",\s*"([^"]+)"', line)
        if not m2:
            return None
        cr_id = m2.group(2)
        # craft remainder references a field in ModItems
        cr_field = cr_id.upper()
        return (
            f"    public static final RegistryObject<Item> {field} = "
            f'ITEMS.register("{item_id}", () -> new Item(new Item.Properties().craftRemainder({cr_field}.get())));'
        )

    if method == "ingredientBottle":
        # non-food bottle (e.g. sauce bottles)
        return (
            f"    public static final RegistryObject<BottleFoodItem> {field} = "
            f'ITEMS.register("{item_id}", () -> new BottleFoodItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));'
        )

    # Food methods: food, fastFood, bottle, bowlFood, stickFood, stickFoodCr
    # Find nutrition and saturation (first two numbers after the id)
    m_ns = re.search(r'(?:food|fastFood|bottle|bowlFood|stickFood|stickFoodCr)\("[^"]+",\s*(\d+),\s*([\d.]+)f?', line)
    if not m_ns:
        return None
    nutrition = int(m_ns.group(1))
    saturation = m_ns.group(2)

    # Extract all fx(...) calls
    fx_calls = re.findall(r'fx\([^)]+\)', line)
    effects = parse_effects(fx_calls)

    fast = method in ("fastFood",)

    if method in ("food", "fastFood"):
        food_props = build_food_props(nutrition, saturation, fast, effects)
        return (
            f"    public static final RegistryObject<Item> {field} = "
            f'ITEMS.register("{item_id}", () -> new Item(new Item.Properties()\n'
            f"                .food({food_props})));"
        )

    if method == "bottle":
        food_props = build_food_props(nutrition, saturation, False, effects)
        return (
            f"    public static final RegistryObject<BottleFoodItem> {field} = "
            f'ITEMS.register("{item_id}", () -> new BottleFoodItem(new Item.Properties()\n'
            f"                .craftRemainder(Items.GLASS_BOTTLE)\n"
            f"                .stacksTo(16)\n"
            f"                .food({food_props})));"
        )

    if method == "bowlFood":
        food_props = build_food_props(nutrition, saturation, False, effects)
        return (
            f"    public static final RegistryObject<BowlFoodItem> {field} = "
            f'ITEMS.register("{item_id}", () -> new BowlFoodItem(new Item.Properties()\n'
            f"                .craftRemainder(Items.BOWL)\n"
            f"                .stacksTo(16)\n"
            f"                .food({food_props})));"
        )

    if method == "stickFood":
        food_props = build_food_props(nutrition, saturation, True, effects)
        return (
            f"    public static final RegistryObject<StickFoodItem> {field} = "
            f'ITEMS.register("{item_id}", () -> new StickFoodItem(new Item.Properties()\n'
            f"                .food({food_props})));"
        )

    if method == "stickFoodCr":
        food_props = build_food_props(nutrition, saturation, True, effects)
        return (
            f"    public static final RegistryObject<StickFoodItem> {field} = "
            f'ITEMS.register("{item_id}", () -> new StickFoodItem(new Item.Properties()\n'
            f"                .craftRemainder(Items.STICK)\n"
            f"                .food({food_props})));"
        )

    return None


# Load ItemRegistry.java into a dict: item_id -> line
print("Loading ItemRegistry.java...")
registry_lines = {}
with open(ITEM_REGISTRY_FILE, encoding="utf-8") as f:
    for line in f:
        if "public static final Item" not in line:
            continue
        # Extract ID: first string in the factory call
        m = re.search(r'=\s*\w+\("([^"]+)"', line)
        if m:
            registry_lines[m.group(1)] = line.strip()

print(f"  Loaded {len(registry_lines)} item definitions")

# Load items_to_add.txt
with open(ITEMS_TO_ADD_FILE, encoding="utf-8") as f:
    items_to_add = [line.strip() for line in f if line.strip()]

print(f"  Items to add: {len(items_to_add)}")

# Generate new declarations
new_lines = ["\n    // ===== Phase 4: New items from 1.21.1 =====\n"]
skipped = []
converted = 0

for item_id in items_to_add:
    src_line = registry_lines.get(item_id)
    if not src_line:
        skipped.append(f"  NOT FOUND IN REGISTRY: {item_id}")
        continue

    result = convert_item(item_id, src_line)
    if result is None:
        skipped.append(f"  PARSE FAILED: {item_id} | {src_line[:80]}")
        continue

    new_lines.append(result + "\n")
    converted += 1

print(f"\nConverted: {converted}, Skipped: {len(skipped)}")
if skipped:
    print("Skipped items:")
    for s in skipped:
        print(s)

# Insert before the closing } of ModItems.java
with open(MODITEMS_SRC, encoding="utf-8") as f:
    content = f.read()

# Find last closing brace
last_brace = content.rfind("}")
insert_text = "\n" + "".join(new_lines) + "\n"
content = content[:last_brace] + insert_text + "}\n"

with open(MODITEMS_SRC, "w", encoding="utf-8") as f:
    f.write(content)

print(f"\nDone. {converted} items appended to ModItems.java")
