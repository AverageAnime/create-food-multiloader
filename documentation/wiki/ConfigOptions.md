### ***2.7.1***
> **Note: If you are updating to a version with new config defaults, you will need to regenerate the list, or add the new entries manually.**

# `createfood-client.toml`

---

## Blocks

### Display Settings
- **always_display_upright**: Items on display plates always render upright regardless of `create:upright_on_belt` tag (default: `false`)

### Storage Display
- **show_sack_block_icons**: Render floating item icons on the front face of placed cloth sack blocks (default: `true`)
- **show_tooltip_icons**: Show item icons in the tooltip for cloth sack and ration box items (default: `true`)
```toml
[blocks]
always_display_upright = false
show_sack_block_icons = true
show_tooltip_icons = true
```

---

## Tooltips
- **require_shift**: Require holding Shift to view tooltips (default: `false`)
- **show_compatibility**: View mod compatibility info in tooltips (default: `true`)
- **show_ingredients**: View ingredients in tooltips (default: `true`)
#### Adding Custom Tooltips
Format: `item_key|ingredients`, or `item_key|ingredients|compat_key`
* Add ingredient or mod compatibility info tooltips to any item using the `custom_tooltips` list.
* `ingredients` is a comma-separated list of short keys; each resolves to `tooltip.createfood.<key>_ingredient`.
* `compat_key` is optional and adds the blue compatibility line above the ingredients, resolving to
  `tooltip.compat.<compat_key>`. Leave `ingredients` empty to show only the compatibility line.
> **Note: Changes require a game restart.**
```toml
[tooltips]
require_shift = false
show_compatibility = true
show_ingredients = true
custom_tooltips = [
  "farmersdelight:hamburger|onion,lettuce,tomato",
  "createfood:eggplant_burger||eggplant"
]
```

---

# `createfood-common.toml`

---

## Blocks

### Custom Blocks
Format: `name|type|slice_item_id` for sliced types, `name|type` for unsliced types
> **Note: Custom blocks will require new model/blockstate/texture files.**
* Register brand-new blocks under the `createfood:` namespace using the `block` list.
* `name` becomes the block's registry ID: `createfood:<name>`.
> **Note: Changes require a game restart.**

| Type        | Block behaviour              | `slice_item_id` |
|-------------|------------------------------|-----------------|
| `cake`      | Cake (sliceable, stacksTo 1) | Required        |
| `pie`       | Pie (sliceable)              | Required        |
| `pizza`     | Pizza (sliceable)            | Required        |
| `waffle`    | Waffle (sliceable)           | Required        |
| `cheese`    | Cheese wheel (sliceable)     | Required        |
| `gyro_meat` | Gyro meat spit (sliceable)   | Required        |
| `cake_base` | Undecorated cake (stacksTo 1)| —               |
| `raw_pie`   | Raw pie (unsliceable)        | —               |
| `raw_pizza` | Raw pizza (unsliceable)      | —               |
| `gelatin`   | Bouncy block, same as slime  | —               |

`slice_item_id` accepts any registered item ID (`createfood:`, vanilla, or another mod).
```toml
[blocks]
block = [
  "my_cheesecake|cake|createfood:my_cheesecake_slice",
  "my_berry_pie|pie|createfood:my_berry_pie_slice",
  "my_margherita|pizza|createfood:my_margherita_slice",
  "my_waffles|waffle|createfood:my_waffle_slice",
  "my_raw_pie|raw_pie",
  "my_panna_cotta|gelatin"
]
```

### Custom Display Blocks
Format: `mod:item_id|display_type|max_stack`, `mod:item_id|display_type|max_stack|height`, `mod:item_id|display_type|max_stack|height|particles`
> **Note: Custom display blocks will require new model/blockstate/loot_table files.**
* Register items from other mods to be placeable as display blocks using the `display_block` list.
* `display_type` must be one of: `plate`, `plate_food`, `small_plate`, `bottle`, `bowl`, `display_bowl`, `salad_bowl`, `small_bowl`
* `plate_food` renders the food directly on a plate (used by the pasta and breakfast plates) and ignores `max_stack`.
* `max_stack` controls the maximum number of items that can be placed on the display block.
* `height` is optional and sets the model height in pixels (default: `12`). Fractional values such as `4.5` are allowed.
* `particles` is optional and applies to `bottle` and `bowl` (default: `false`). Use `true` for the default
  rising steam, or name a particle directly (e.g. `minecraft:snowflake`) to use that instead.
> **Note: Changes require a game restart.**
```toml
[blocks]
display_block = [
  "kaleidoscope_cookery:samsa|plate|2",
  "farmersrespite:tea_bottle|bottle|1|10|true",
  "mymod:ice_cream_bowl|bowl|1|4.5|minecraft:snowflake"
]
```

### Custom Fluids
Format: `name|slopeFindDistance|levelDecreasePerBlock`, or just `name` to use the default flow behaviour
> **Note: Custom fluids will require new texture files.**
* Register brand-new fluids under the `createfood:` namespace using the `fluid` list.
* `name` becomes the fluid's registry ID: `createfood:<name>`. Bucket items are registered automatically.
* Control how the fluid flows with `slopeFindDistance` and `levelDecreasePerBlock`.
> **Note: Changes require a game restart.**
```toml
[blocks]
fluid = [
  "honey_syrup|4|3",
  "cream_sauce|2|4",
  "plain_syrup"
]
```

---

## Items

### Hiding Items
Format: `item_id`
* Create: Food items can be hidden in game by adding entries to the `hide_items` list. Recipes are also disabled. In 2.1.0+ display blocks are also hidden.
* Adding a fluid ID also hides that fluid's bucket, so `_bucket` is not needed.
```toml
[items]
hide_items = ["strider_meatball"]
```

### Custom Items
Format: `name|type|nutrition|saturation` for food types, `name|type` for ingredient types, `name|plain_cr|remainder_item_id`
> **Note: Custom items will require new model/texture files.**
* Register brand-new items under the `createfood:` namespace using the `item` list.
* `name` becomes the item's registry ID: `createfood:<name>`.
* To add effects to custom items, use the existing effect server config.
* You must provide model and texture files via a resource pack.
> **Note: Changes require a game restart.**

**Food types**:

| Type        | Eat speed | Stack                              |
|-------------|-----------|-------------------------------------|
| `food`      | normal    | 64                                  |
| `fast_food` | fast      | 64                                  |
| `bowl`      | normal    | 16                                  |
| `bowl_cr`   | normal    | 16 (craft remainder: bowl)          |
| `bottle`    | normal    | 16 (craft remainder: glass bottle)  |
| `stick`     | fast      | 64                                  |
| `stick_cr`  | fast      | 64 (craft remainder: stick)         |

**Ingredient types**:

| Type                | Stack | Craft remainder        |
|---------------------|-------|------------------------|
| `plain`             | 64    | —                      |
| `plain_cr`          | 64    | `remainder_item_id`    |
| `ingredient_bottle` | 16    | glass bottle           |
| `ingredient_bowl`   | 16    | bowl                   |
| `piping_bag`        | 2     | piping bag             |

`plain_cr` takes a third field naming the item left behind when it is used in a crafting recipe.
It accepts any registered item ID (`createfood:`, vanilla, or another mod).

```toml
[items]
item = [
  "my_pie_slice|food|4|0.5",
  "my_stew|bowl|6|0.7",
  "my_juice|bottle|4|0.3",
  "my_flour|plain",
  "my_filled_mould|plain_cr|createfood:cloth_filter"
]
```

---

# `createfood-server.toml`

---

## Display

### Generic Display Plates & Bowls
- **enable_generic_display**: Enable generic display plate and bowl blocks that can hold any single item (default: `true`)
- **enable_cutting_board**: Enable Farmer's Delight cutting board recipes to work on display plates (default: `true`)
#### Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Block specific items from being placed on generic display plates and bowls using `generic_display_exclude`.
```toml
[blocks.display]
enable_cutting_board = true
enable_generic_display = true
generic_display_exclude = ["tag:c:tools"]
```

### Fluid Dipping
- **enable_dipping**: Allow a single small bowl to hold fluid, and food dipped into it to be transformed using Create's filling recipes (default: `true`)
- **small_bowl_capacity_mb**: Maximum fluid, in mB, a small bowl can hold (default: `4000`)
> **Note: Lowering this value after bowls have been filled beyond the new capacity silently discards the excess fluid the next time the bowl is loaded.**
#### Dipping Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Block specific items from being dipped using `dipping_exclude`. Empty by default.
```toml
[blocks.display]
enable_dipping = true
small_bowl_capacity_mb = 4000
dipping_exclude = []
```

---

## Interactions
- **enable_filter_interactions**: Toggle cloth filter interactions (default: `true`)
- **enable_pumpkin_pie_placement**: Allow vanilla pumpkin pie to be placed as a block (default: `false`)

### Campfire Cooking
- **enable_campfire_cooking**: Toggle campfire cooking (default: `false`)
- **require_shift**: Require holding Shift for campfire cooking to progress (default: `true`)
- **sticks_only**: Only cook recipes whose result ID ends with `_stick`, e.g. popsicles and skewers (default: `false`)
- **horizontal_range**: Horizontal search radius, in blocks, for detecting heat sources (default: `3`)
- **vertical_range**: Vertical search radius, in blocks, for detecting heat sources (default: `1`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Block specific items from being cooked using `campfire_cooking_exclude`. Takes priority over the filter and `sticks_only`.
* Restrict campfire cooking to only produce outputs matching `campfire_cooking_filter`. Works in addition to `sticks_only` when both are set.
```toml
[interactions.campfire_cooking]
enable_campfire_cooking = false
require_shift = true
sticks_only = false
horizontal_range = 3
vertical_range = 1
campfire_cooking_exclude = []
campfire_cooking_filter = []
```

### Filter Interactions
Format: `filter_item|offhand_item|filter_result|container_result`
* Add cloth filter interactions using the `filter_interactions` list. Use `none` for an empty offhand requirement or to consume the filter with no remainder.
```toml
[interactions]
filter_interactions = [
  "createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle",
  "createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk"
]
```

### Handcrafting
- **allow_single_item**: Allow single-ingredient recipes to be handcrafted (default: `false`)
- **enable_handcrafting**: Toggle crafting two-ingredient recipes via `RMB` (default: `true`)
- **enable_particles**: Show particle effects when handcrafting (default: `true`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Block specific outputs from being handcrafted using `exclude`. Takes priority over the filter.
* Restrict handcrafting to only allow outputs matching the `filter` list. If empty, all recipes are allowed.
```toml
[interactions.handcraft]
allow_single_item = false
enable_handcrafting = true
enable_particles = true
exclude = ["item:createfood:egg_yolk", "tag:minecraft:enchantable/durability", "tag:minecraft:enchantable/vanishing", "tag:c:tools"]
filter = ["mod:createfood", "item:farmersdelight:hamburger", "tag:c:foods"]
```

---

## Items

### Nutrition & Saturation Overrides
Format: `item_id|nutrition|saturation`
* Overwrite nutrition and saturation using the `nutrition_saturation` list.
* Use `-` to keep the default value.
* `item_id` may be a **bare id** for a Create: Food item (e.g. `sugar_cane_juice_bottle`) or a **namespaced id** for any other mod's food (e.g. `minecraft:apple`, `farmersdelight:apple_pie_slice`). 
    * Foreign items must already have a food component.
> **Note: Foreign-item overrides only affects item stacks created or loaded after reload — relog to refresh an existing inventory.**
```toml
[items]
nutrition_saturation = [
  "sugar_cane_juice_bottle|1|1",
  "caramel_pretzel_stick|-|5",
  "minecraft:apple|1|0.1"
]
```

### Effect Category Overrides
Format: `category_name|mod_id:effect_id`
* Pin a named effect category to a specific effect ID, bypassing the default mod-priority order.
* If the effect ID isn't found in the registry the default priority order is used as a fallback.
```toml
[items.effects]
category_overrides = ["comfort|farmersdelight:comfort"]
```

### Item Effect Overrides
Format: `item_id|category_or_effect_id|duration|amplifier`, `item_id|category_or_effect_id|duration|amplifier|chance`, `item_id|category_or_effect_id|remove`
* Add, adjust, or remove effects on using the `item_overrides` list.
* `category_or_effect_id` accepts either a category name (e.g. `comfort`) or a full effect ID (e.g. `minecraft:fire_resistance`).
* Duration is in ticks (20 ticks = 1 second). Amplifier `0` = level I, `1` = level II, etc.
* `chance` is optional (0.0–1.0, default `1.0`). When below 1.0, the effect has a percentage chance to apply and the tooltip always shows the chance (e.g. `Strength (07:30), 50% Chance`).
* `item_id` may be a bare Create: Food id or a namespaced id for any other mod's food (same rules as `nutrition_saturation` above).
```toml
[items.effects]
item_overrides = [
  "smore|comfort|9000|0",
  "spicy_chicken_nuggets|minecraft:fire_resistance|remove",
  "apple_cream_donut|strength|9000|0|0.5",
  "minecraft:golden_apple|minecraft:regeneration|remove"
]
```

### Remainders
- **enable_egg_impact_remainder**: Drop an eggshell when a thrown egg hits a surface (default: `true`)
#### Custom Crafting Remainders
Format: `input_item|remainder_item`
* Add custom crafting remainders to any item using the `crafting_remainders` list.
  * **Crafting table** — the remainder is returned to the grid slot (vanilla put-back), not dropped into your inventory.
  * **Crafter block** and **modded crafting benches** (Create's mechanical crafter, etc.) — the remainder is produced automatically.
  * **Smelting/smoking/blasting** — the remainder is delivered through the furnace's output slot, so it comes out with the result whether a player or a hopper takes it. (While a remainder is waiting in the output slot, the next result waits until it's collected, like any full output.)
  * **Create's bulk smoking/blasting** — the remainder drops alongside the result. NeoForge only.
  * **Campfire cooking** — both hand-held cooking and placed campfires drop the remainder alongside the cooked result.
  * **Eating** — eating an item configured as a remainder input returns its remainder, matching vanilla food containers (e.g. bowls).
```toml
[items.remainders]
enable_egg_impact_remainder = true
crafting_remainders = [
  "minecraft:egg|createfood:eggshell",
  "createfood:cake_batter_bucket|minecraft:bucket",
  "createfood:chocolate_cake_batter_bucket|minecraft:bucket",
  "createfood:ube_cake_batter_bucket|minecraft:bucket"
]
```

---

## Storage

### Cloth Sack
- **cloth_sack_allow_food**: Food items are always allowed regardless of the filter. With no filter set this effectively restricts the sack to food only (default: `false`)
- **cloth_sack_eat_from_item**: `RMB` with the cloth sack in hand eats the first food stored inside instead of opening the inventory (default: `false`)
- **cloth_sack_inventory**: Enable the cloth sack's inventory. When disabled it is purely cosmetic (default: `true`)
- **cloth_sack_stack**: Allow each slot to hold a full stack instead of a single item (default: `true`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Block specific items from being inserted using `cloth_sack_exclude`. Takes priority over everything.
* Restrict what items can be inserted using `cloth_sack_filter`. If empty, all items are allowed (or only food when `allow_food` is on).
```toml
[blocks.storage.cloth_sack]
cloth_sack_allow_food = false
cloth_sack_eat_from_item = false
cloth_sack_inventory = true
cloth_sack_stack = true
cloth_sack_exclude = ["item:createfood:cloth_sack"]
cloth_sack_filter = ["tag:c:foods"]
```

### Ration Box
- **ration_box_allow_food**: Food items are always allowed regardless of the filter. With no filter set this effectively restricts the box to food only (default: `true`)
- **ration_box_eat_from_item**: `RMB` with the ration box in hand eats the first food stored inside. When disabled, it opens the inventory instead (default: `true`)
- **ration_box_inventory**: Enable the ration box's inventory. When disabled it is purely cosmetic and cannot be eaten from (default: `true`)
- **ration_box_stack**: Allow each slot to hold a full stack instead of a single item (default: `false`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Block specific items from being inserted using `ration_box_exclude`. Takes priority over everything.
* Restrict what items can be inserted using `ration_box_filter`. If empty, all items are allowed (or only food when `allow_food` is on). Entries are allowed in addition to food when `allow_food` is on.
```toml
[blocks.storage.ration_box]
ration_box_allow_food = true
ration_box_eat_from_item = true
ration_box_inventory = true
ration_box_stack = false
ration_box_exclude = ["item:minecraft:rotten_flesh"]
ration_box_filter = ["mod:createfood", "tag:c:foods/meat"]
```
