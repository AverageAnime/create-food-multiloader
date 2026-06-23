# Spec JSON

A spec file lets you define any number of items and all their recipes in one JSON file, then upload it to the toolkit's **Import** button to generate all files at once — no form filling required.

---

## File structure

```json
{
  "items": [
    { ... },
    { ... }
  ]
}
```

The file must be a `.json` file with a top-level `"items"` array. Each element is one item entry. The toolkit processes them in order and merges everything into the saved files before you download.

---

## Item entry — full reference

```json
{
  "id": "my_item",
  "displayName": "My Item",
  "type": "food",
  "nutrition": 6,
  "saturation": 0.6,
  "fast": false,
  "tooltips": ["beef", "cheese"],
  "effects": [
    { "effect": "Comfort", "duration": 1200 }
  ],
  "isCompat": false,
  "compatKey": "",
  "recipes": [ ... ]
}
```

### `id` · required
The item's registry ID, no namespace. Must match the texture filename.

```json
"id": "kelp_soup_bowl"
```

### `displayName` · required
The English display name shown in-game.

```json
"displayName": "Kelp Soup"
```

### `type` · required
Controls registration type and item class. All valid values:

| Value | Description                                                                             |
|---|-----------------------------------------------------------------------------------------|
| `food` | Standard food, stack 64. Shows effects in tooltip.                                      |
| `fastFood` | food with `.fast()` — skips eat animation. Equivalent to `"type": "food", "fast": true` |
| `bowlFood` | Food that returns a bowl on use, stack 16. Shows effects in tooltip. Set `crBowl: true` to also return the bowl as a crafting remainder.    |
| `bottle` | DrinkableItem that returns a glass bottle, stack 16                                     |
| `stickFood` | Held on a stick                                                                         |
| `stickFoodCr` | Held on a stick; also returns the stick as a crafting remainder                        |
| `plain` | Non-food ingredient, no special properties                                              |
| `plainCr` | Non-food with a crafting remainder (not yet spec-settable)                              |
| `ingredientBowl` | Non-food, stack 16, returns bowl                                                        |
| `ingredientBottle` | Non-drinkable, stack 16, returns glass bottle                                           |
| `pipingBag` | Stack 2, returns a piping bag                                                           |
| `fluid` | Registers as a fluid — see Fluid items below                                            |
| `block_cake` | Placed cake block (7 bites). Generates slice item automatically                         |
| `block_pie` | 4-slice pie/cheesecake block pair (raw + cooked)                                        |
| `block_pizza` | 4-slice pizza/waffle block pair (raw + cooked)                                          |
| `block_raw_pie` | Standalone raw pie block only, no cooked form                                           |
| `block_raw_pizza` | Standalone raw pizza block only, no cooked form                                         |
| `block_waffle` | 4-slice waffle block. Set `miniId` to override the mini item ID (defaults to `mini_<id>`) |
| `block_gelatin` | Gelatin dessert block (no slice, no raw variant)                                        |
| `block_cheese` | Cheese block with a slice item                                                          |
| `block_gyro_meat` | Gyro meat block with a slice item                                                      |
| `block_cake_base` | Unfrosted cake base block (no slice)                                                   |

### `nutrition` and `saturation`
Integers/floats. Only applies to food types. Ignored for `plain`, `fluid`, and block types (block types use `sliceNutrition`/`sliceSaturation` instead).

```json
"nutrition": 9,
"saturation": 0.8
```

### `fast`
Boolean. Sets `.fast()` on the item — skips the eating animation. Equivalent to using `fastFood` instead of `food`. Use only for snack-sized items (nutrition ≤ 6 typically). Defaults to `false`.

```json
"fast": true
```

### `crBowl`
Boolean. Only applies to `bowlFood` type. When `true`, the item also returns a bowl when used as a **crafting ingredient** (in addition to already returning one on consumption). This sets `.craftRemainder(Items.BOWL)` on the item's properties. Defaults to `false` — most bowl foods only need the eat-return, not the crafting remainder.

```json
"crBowl": true
```

### `tooltips`
Array of tooltip ingredient key strings. These map to `tooltip.createfood.<key>` lang entries.

```json
"tooltips": ["fish", "kelp", "salt"]
```

Available keys are everything in the toolkit's ingredient list — if you've used a key in the toolkit UI before, it works here.

### `effects`
Array of effect objects. Each has:

| Field | Values |
|---|---|
| `effect` | See effect names below |
| `duration` | `300`, `600`, `1200`, `3600`, or `6000` (ticks) |
| `amplifier` | `0`, `1`, `2` — omit for level I (default 0) |
| `chance` | `0.0`–`1.0` — probability the effect applies on eat. Default `1.0` (always applies). |

**Valid effect names:**

| Name in spec | In-game effect |
|---|---|
| `Night Vision` | Night Vision |
| `Glowing` | Glowing |
| `Water Breathing` | Water Breathing |
| `Fire Resistance` | Fire Resistance |
| `Slow Falling` | Slow Falling |
| `Regeneration` | Regeneration |
| `Haste` | Haste |
| `Speed` | Speed |
| `Strength` | Strength |
| `Luck` | Luck |

**Food Effects:**

| Effect              | Description |
|---------------------|---|
| `adrenaline`        | Movement speed increases the lower your health |
| `animal_charm`      | Attracts and calms nearby animals |
| `balanced`          | Grants nearby players Absorption |
| `berserk`           | Attack damage increases the lower your health |
| `blood_clot`        | Prevents natural regeneration |
| `bonding`           | Grants nearby players Absorption and Regeneration |
| `brimstone_vision`  | See clearly while submerged in lava |
| `caffeinated`       | Slightly increases all major stats |
| `charisma`          | Reduces villager trading prices |
| `combustion`        | Ignites nearby enemies |
| `comfort`           | General comfort/wellbeing |
| `explosion`         | Chance to launch a fireball on attack |
| `farmers_blessing`  | Cleanses all negative effects |
| `feast`             | Combined sustenance + satiation super-buff |
| `flight`            | Temporary creative-like flight |
| `fortune`           | Increases Luck |
| `grandmas_blessing` | Cleanses negatives + Luck +2 |
| `lava_walking`      | Walk on lava |
| `life_leech`        | Drains health from nearby hostiles and heals the user |
| `lightning`         | Chance to strike target with lightning |
| `mining`            | Mining speed bonus based on depth |
| `mustard`           | Creepers nearby will flee from you |
| `nourishment`       | Nourishment/saturation |
| `pacify`            | Reduces enemy aggression; Endermen safe |
| `party_starter`     | Fireworks on hit + bonus damage |
| `perception`        | Nearby entities glow |
| `preservation`      | Eating rotten flesh, raw chicken, poisonous potatoes, pufferfish, or spider eyes will not cause debuffs |
| `raging`            | Gain 5% attack speed per melee hit, stacks up to 4× |
| `refreshed`         | Next harvests don't consume durability; may yield extra drops |
| `repulsion`         | Periodically pushes enemies away |
| `rest`              | Phantoms will flee from you |
| `rested`            | Bonus experience gain |
| `satiated_shield`   | Hunger value acts as extra health; damage is absorbed by hunger first |
| `satiation`         | Hunger management |
| `stimulation`       | Removes mining fatigue and slowness |
| `stout_heart`       | Knockback resistance |
| `sugar_rush`        | Stacking speed buff |
| `sustenance`        | Periodic hunger or health restoration |
| `sweet_heart`       | Extra saturation-based healing at any hunger level |
| `touch_absorb`      | Melee attacks grant Absorption |
| `touch_heal`        | Melee attacks heal the target |
| `touch_poison`      | Melee attacks apply Poison |
| `touch_regen`       | Melee attacks apply Regeneration |
| `tough`             | Grants Absorption, Regeneration, and Resistance |
| `tundra_strider`    | Increased movement speed on snow, ice, and powder snow; prevents sinking into powder snow |
| `vigor`             | Running does not consume hunger |
| `vitality`          | Exhaustion reduction |
| `warmth`            | Slowly regenerates health when near heat sources (stoves, campfires, lava) |
| `water_walking`     | Walk on water |
| `well_served`       | Cannot become truly hungry while active |

```json
"effects": [
{ "effect": "Comfort", "duration": 1200 },
{ "effect": "Night Vision", "duration": 600 }
]
```

### `isCompat` and `compatKey`
Set `"isCompat": true` when the item requires a compat mod to be present (e.g. popcorn items). `compatKey` is the config key used. This generates a `ConfigDefaults.java` entry to add the item to `HIDE_ITEMS_DEFAULT`.

```json
"isCompat": true,
"compatKey": "popcorn"
```

---

## Block item fields

For `block_cake`, `block_pie`, `block_pizza`, `block_raw_pie`, `block_raw_pizza`, `block_waffle`, `block_cheese`, and `block_gyro_meat` types, slice items are auto-generated. Configure the slice with:

| Field | Description | Default |
|---|---|---|
| `sliceId` | Override slice ID if it doesn't follow `<id>_slice` | auto |
| `miniId` | Override mini item ID for `block_waffle` if it doesn't follow `mini_<id>` | auto |
| `sliceNutrition` | Slice nutrition | 2 |
| `sliceSaturation` | Slice saturation | 0.3 |
| `sliceFast` | Slice `.fast()` | true |
| `sliceEffects` | Array of effect objects, same format as `effects` | [] |
| `tooltips` | Applied to the slice | [] |

```json
{
  "id": "melon_cheesecake",
  "displayName": "Melon Cheesecake",
  "type": "block_cake",
  "tooltips": [],
  "sliceNutrition": 4,
  "sliceSaturation": 0.5,
  "sliceEffects": [{ "effect": "Comfort", "duration": 1200 }],
  "recipes": [ ... ]
}
```

> **Note on block pairs:** `block_pie` and `block_pizza` auto-generate both the cooked block and a raw block (`raw_<id>`). Your `recipes` array only needs to cover the cooked block — the toolkit generates the cutting recipe and slice-combine recipe automatically.

---

## Fluid item fields

Set `"type": "fluid"` or `"registrationType": "fluid"`. Additional fields:

| Field | Description |
|---|---|
| `fluidFlowSlope` | Flow slope for thin fluids (e.g. `3`) |
| `fluidFlowDecrease` | Flow decrease (e.g. `2`) |
| `createBottle` | `true` to auto-generate bottle item + filling/emptying/bucket recipes |
| `bottleNutrition` | Bottle nutrition (default `4`) |
| `bottleSaturation` | Bottle saturation (default `1.0`) |
| `bottleEffects` | Array of effect objects for the bottle |
| `bottleTooltips` | Tooltip keys for the bottle |
| `createBowl` | `true` to auto-generate bowl item + filling/emptying/bucket recipes |
| `bowlNutrition` | Bowl nutrition (default `4`) |
| `bowlSaturation` | Bowl saturation (default `0.6`) |
| `bowlEffects` | Array of effect objects for the bowl |
| `bowlTooltips` | Tooltip keys for the bowl |

```json
{
  "id": "apple_custard",
  "displayName": "Apple Custard",
  "type": "fluid",
  "fluidFlowSlope": 3,
  "fluidFlowDecrease": 2,
  "createBottle": true,
  "bottleNutrition": 5,
  "bottleSaturation": 0.8,
  "bottleTooltips": [],
  "bottleEffects": [{ "effect": "Comfort", "duration": 600 }]
}
```

---

## Variants

Variants are additional items that share the same base registration (same type, same tooltip inheritance) but differ in nutrition, saturation, effects, and tooltips. They map to the toolkit's Variations section.

```json
{
  "id": "yogurt_bowl",
  "displayName": "Yogurt",
  "type": "bowlFood",
  "nutrition": 4,
  "saturation": 0.6,
  "tooltips": [],
  "effects": [],
  "variants": [
    {
      "id": "yogurt_bowl_berry",
      "displayName": "Yogurt",
      "nutrition": 5,
      "saturation": 0.8,
      "tooltips": ["berry"],
      "effects": []
    },
    {
      "id": "yogurt_bowl_honey",
      "displayName": "Yogurt",
      "nutrition": 4,
      "saturation": 0.8,
      "tooltips": ["honey"],
      "effects": [{ "effect": "Regeneration", "duration": 300 }]
    }
  ],
  "recipes": [ ... ]
}
```

Variants only affect Java registration and lang files. Their recipes are still written in the top-level `recipes` array of the base item, with `"output"` pointing to the variant ID.

If a variant's `tooltips` is `null` or omitted, it inherits the base item's tooltips. Set it to `[]` to explicitly clear them.

---

## Recipes

Every item has a `"recipes"` array. Each recipe object:

```json
{
  "type": "create:deploying",
  "inputs": [
    { "type": "tag", "value": "c:bread_slice" },
    { "type": "tag", "value": "c:cooked_beef" }
  ],
  "output": "beef_bread_slice",
  "count": 1
}
```

### `type` · required

All valid recipe types:

| Value | Description |
|---|---|
| `create:compacting_heated` | Mechanical press with heat |
| `create:compacting` | Mechanical press, no heat |
| `create:deploying` | Deployer applies item to item |
| `create:item_application` | Deployer applies item onto stationary target, returns result + optional applicator |
| `create:emptying` | Extracts fluid from container |
| `create:filling` | Fills container with fluid |
| `create:milling` | Millstone grinds to powder |
| `create:mixing_heated` | Mixer with heat |
| `create:mixing` | Mixer, no heat |
| `create:pressing` | Press on single item |
| `farmersdelight:cooking` | Cooking Pot |
| `farmersdelight:cutting` | Cutting board + knife |
| `minecraft:campfire_cooking` | Campfire |
| `minecraft:crafting_shaped` | Shaped crafting table |
| `minecraft:crafting_shapeless` | Shapeless crafting table |
| `minecraft:smelting` | Furnace |
| `minecraft:smoking` | Smoker |

### `inputs`

Array of ingredient objects. Each has a `type` and `value`:

| Input type | Value format | Example |
|---|---|---|
| `tag` | Tag path — `c:` prefix is added automatically if missing | `"cooked_beef"` or `"c:cooked_beef"` |
| `item` | Full item ID with namespace | `"minecraft:glass_bottle"` |
| `fluid_tag` | Tag path + mB amount separated by `\|` | `"honey\|125"` or `"c:honey\|125"` |

```json
"inputs": [
{ "type": "tag",      "value": "c:pasta_plate" },
{ "type": "item",     "value": "farmersdelight:tomato_sauce" },
{ "type": "fluid_tag","value": "tomato_sauce|250" }
]
```

### `output`
The output item ID (no namespace). Defaults to the item's own `id` if omitted.

```json
"output": "pasta_plate_mushroom_tomato_sauce"
```

### `count`
Output stack size. Defaults to `1`.

```json
"count": 4
```

### `byproductId`
Optional. Only used by `create:item_application`. The item ID of a second result returned alongside the primary output (e.g. the applicator being given back to the player). No namespace defaults to `createfood:`.

```json
"byproductId": "piping_bag"
```

### `suffix`
The recipe type suffix (`_from_deploying`, `_from_crafting`, etc.) is **always added automatically** from the recipe type. The `suffix` field is only for disambiguating two recipes of the **same type** targeting the **same output** that would otherwise produce identical filenames.

```json
{ "type": "create:deploying", "output": "my_item" }
```
→ `my_item_from_deploying.json`

```json
{ "type": "create:deploying", "output": "my_item", "suffix": "sauce" }
```
→ `my_item_from_deploying_sauce.json`

Don't repeat the recipe type name in the suffix — `"suffix": "from_deploying"` would produce `my_item_from_deploying_from_deploying.json`.

If you have two `create:filling` recipes pointing at the same output (one with honey fluid, one with caramel fluid), the toolkit already auto-disambiguates by fluid tag name — no suffix needed.

### `experience` and `cookingtime`
Used by `farmersdelight:cooking`, `minecraft:smelting`, `minecraft:smoking`, and `minecraft:campfire_cooking`.

```json
"experience": 0.35,
"cookingtime": 200
```

### `fluidOutput` and `fluidAmount`
For `create:mixing` and `create:mixing_heated` when the output is a fluid rather than an item.

```json
{
  "type": "create:mixing_heated",
  "inputs": [ ... ],
  "output": "kelp_soup",
  "fluidOutput": true,
  "fluidAmount": 333
}
```

### Shaped crafting (`minecraft:crafting_shaped`)
Requires two extra fields:

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": ["XY ", "   ", "   "],
  "keys": {
    "X": { "type": "tag",  "value": "c:mushrooms" },
    "Y": { "type": "tag",  "value": "c:pasta_plate" }
  },
  "output": "pasta_plate_mushroom"
}
```

`pattern` is an array of exactly 3 strings of exactly 3 characters each. Use space for empty cells. Letters must match keys defined in `keys`.

For the common vertical 2-item pattern (topping over base):

```json
"pattern": ["X  ", "Y  ", "   "]
```

---

## Filename collision rules

The toolkit builds filenames automatically:

```
data/createfood/recipe/<dir>/<output><type_suffix><fluid_suffix><suffix>.json
```

Where:
- `<dir>` comes from the recipe type (e.g. `create/deploying`)
- `<type_suffix>` is the type's default suffix (e.g. `_from_deploying`)
- `<fluid_suffix>` is auto-appended for `create:filling` and `create:mixing` based on the fluid tag name
- `<suffix>` is the manual `suffix` field

Collisions only happen when two recipes of the same type target the same output with no distinguishing fluid input. In that case add a short descriptive string to the second recipe's `suffix` — `"alt"`, `"sauce"`, `"bucket"` etc. Never use the recipe type name itself as the suffix.

---

## What gets generated automatically

You do not need to write recipes for these — the toolkit generates them when it sees the right fields:

| Condition | Auto-generated files |
|---|---|
| `type: "fluid"` + `createBottle: true` | `_bottle_from_filling.json`, `_fluid_from_emptying_bottle.json`, `_bottle_from_bucket.json`, `_bucket_from_bottles.json` |
| `type: "fluid"` + `createBowl: true` | Same four files for bowl |
| `type: "block_pie"` or `"block_pizza"` | `<slice>_from_cutting.json`, `<id>_from_crafting.json` (slice recombine) |
| `type: "block_cake"` | Same two files (7-slice cut + recombine) |
| All `item` registrations | `ModItems.java` line, `ModDisplayBlocks.java` exclusion if needed |
| All `block` registrations | `ModBlocks.java` line, `ModItems.java` slice line |
| All `fluid` registrations | `ModFluids.java` line |
| `isCompat: true` | `ConfigDefaults.java` entry (adds to `HIDE_ITEMS_DEFAULT`) |

---

## Complete examples

### Simple food with one effect

```json
{
  "id": "honeycomb_candy",
  "displayName": "Honeycomb Candy",
  "type": "fastFood",
  "nutrition": 2,
  "saturation": 0.3,
  "fast": true,
  "tooltips": ["honey", "sugar"],
  "effects": [
    { "effect": "Comfort",      "duration": 300 },
    { "effect": "Regeneration", "duration": 300 }
  ],
  "recipes": [
    {
      "type": "create:compacting",
      "inputs": [
        { "type": "item", "value": "minecraft:honeycomb" },
        { "type": "tag",  "value": "c:sugar" }
      ],
      "output": "honeycomb_candy",
      "count": 2
    }
  ]
}
```

### Bowl food with variants

```json
{
  "id": "yogurt_bowl",
  "displayName": "Yogurt",
  "type": "bowlFood",
  "nutrition": 4,
  "saturation": 0.6,
  "tooltips": [],
  "effects": [],
  "variants": [
    {
      "id": "yogurt_bowl_berry",
      "displayName": "Yogurt",
      "nutrition": 5,
      "saturation": 0.8,
      "tooltips": ["berry"],
      "effects": []
    },
    {
      "id": "yogurt_bowl_honey",
      "displayName": "Yogurt",
      "nutrition": 4,
      "saturation": 0.8,
      "tooltips": ["honey"],
      "effects": [{ "effect": "Regeneration", "duration": 300 }]
    }
  ],
  "recipes": [
    {
      "type": "create:filling",
      "inputs": [
        { "type": "item",      "value": "minecraft:bowl" },
        { "type": "fluid_tag", "value": "yogurt|333" }
      ],
      "output": "yogurt_bowl"
    },
    {
      "type": "create:deploying",
      "inputs": [
        { "type": "tag",  "value": "c:yogurt_bowl" },
        { "type": "item", "value": "minecraft:sweet_berries" }
      ],
      "output": "yogurt_bowl_berry"
    },
    {
      "type": "minecraft:crafting_shapeless",
      "inputs": [
        { "type": "tag",  "value": "c:yogurt_bowl" },
        { "type": "item", "value": "minecraft:sweet_berries" }
      ],
      "output": "yogurt_bowl_berry"
    }
  ]
}
```

### Block food (pie with slice nutrition)

```json
{
  "id": "dark_chocolate_berry_pie",
  "displayName": "Dark Chocolate Berry Pie",
  "type": "block_pie",
  "tooltips": ["dark_chocolate", "berry"],
  "sliceNutrition": 5,
  "sliceSaturation": 0.8,
  "sliceEffects": [{ "effect": "Comfort", "duration": 1200 }],
  "recipes": [
    {
      "type": "minecraft:smelting",
      "inputs": [{ "type": "item", "value": "createfood:raw_dark_chocolate_berry_pie" }],
      "output": "dark_chocolate_berry_pie",
      "experience": 0.35,
      "cookingtime": 200
    },
    {
      "type": "minecraft:smoking",
      "inputs": [{ "type": "item", "value": "createfood:raw_dark_chocolate_berry_pie" }],
      "output": "dark_chocolate_berry_pie",
      "experience": 0.35,
      "cookingtime": 100
    }
  ]
}
```

### Fluid with auto-bottle

```json
{
  "id": "apple_custard",
  "displayName": "Apple Custard",
  "type": "fluid",
  "fluidFlowSlope": 3,
  "fluidFlowDecrease": 2,
  "createBottle": true,
  "bottleNutrition": 5,
  "bottleSaturation": 0.8,
  "bottleTooltips": [],
  "bottleEffects": [{ "effect": "Comfort", "duration": 600 }]
}
```

---

## Common mistakes

**Wrong effect name** — effect names are case-sensitive and must match exactly. `"night vision"` won't work; it must be `"Night Vision"`.

**Missing fluid `|mB`** — fluid inputs must include the amount: `"honey|125"`, not `"honey"`.

**Shaped pattern wrong length** — `pattern` must be exactly 3 strings of exactly 3 characters. Pad with spaces: `"X  "` not `"X"`.

**Block type without slice fields** — `block_pie` and `block_cake` will generate slices with the BLANK_ITEM defaults (nutrition 2, saturation 0.3) if you don't set `sliceNutrition` and `sliceSaturation`. Always set them explicitly.

**Using `type: "block"` instead of the specific block type** — `"block"` alone does not map to any registered block helper and generates no Java output. Use the specific type string (`block_cake`, `block_pie`, `block_pizza`, etc.) explicitly.

**Double suffix** — don't put the recipe type name in `suffix`. `"suffix": "from_crafting"` on a `minecraft:crafting_shapeless` recipe produces `item_from_crafting_from_crafting.json`. Use a short descriptive string like `"alt"` or `"sauce"` only when you actually need to disambiguate two identical recipes.

**Variant tooltips inherited unintentionally** — if you want a variant to have no tooltips, set `"tooltips": []`. Omitting the field entirely inherits the base item's tooltips.

**Wrong effect name `sulfur`** — the effect is called `rest`. `"sulfur"` was an old compatibility ID and will silently fail to resolve. Use `"rest"` instead.

**Wrong block type for waffle** — use `block_waffle`, not `block_pizza`. Waffles use a different block class and do not have a raw/cooked pair in the same sense as pies.

**`createBottle`/`createBowl` on a fluid** — bottle and bowl items are auto-generated into `ItemRegistry.java`. You do not need to add them as separate spec items. The bowl variant is automatically registered with `crBowl: true` so the bowl returns as a crafting remainder from filling recipes.

**Incorrectly specifying `_ingredient`** — Toolkit automatically adds `_ingredient`, so it should not be included as part of the spec file.

**Creating unnecessary shaped recipes** — Shaped recipes are datagen created if only two ingredients, so only recipes with 2+ actually need created.