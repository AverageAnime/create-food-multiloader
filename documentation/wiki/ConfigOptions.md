### ***2.4.0***

# `createfood-client.toml`

## Display Blocks
Format: `mod:item_id|display_type|max_stack`
> **Note: Custom display blocks will require new model/blockstate/loot_table files.**
* Register items from other mods to be placeable as display blocks using the `display_block` list.
* `display_type` must be one of: `plate`, `small_plate`, `bottle`, `bowl`, `salad_bowl`
* `max_stack` controls the maximum number of items that can be placed on the display block.
> **Note: Changes require a game restart.**

| Type          | Max stack |
|---------------|-----------|
| `plate`       | 9         |
| `small_plate` | 1         |
| `bottle`      | 1         |
| `bowl`        | 1         |
| `salad_bowl`  | 1         |
```
[display]
display_block = [
  "kaleidoscope_cookery:samsa|plate|2",
  "farmersrespite:tea_bottle|bottle|1"
]
```

---

## Items

### Hiding Items
Format: `item_id`
* Create: Food items can be hidden in game by adding entries to the `hide_items` list. Recipes are also disabled. In 2.1.0+ display blocks are also hidden.
```
[items]
hide_items = ["strider_meatball"]
```

### Custom Items
Format: `name|type|nutrition|saturation` for food types, `name|type` for ingredient types
> **Note: Custom items will require new model/texture files.**
* Register brand-new items under the `createfood:` namespace using the `item` list.
* `name` becomes the item's registry ID: `createfood:<name>`.
* To add effects to custom items, use the existing effect server config.
* You must provide model and texture files via a resource pack.
> **Note: Changes require a game restart.**

**Food types**:

| Type        | Eat speed | Stack                              |
|-------------|-----------|------------------------------------|
| `food`      | normal    | 64                                 |
| `fast_food` | fast      | 64                                 |
| `bowl`      | normal    | 16                                 |
| `bowl_cr`   | normal    | 16 (craft remainder: bowl)         |
| `bottle`    | normal    | 16 (craft remainder: glass bottle) |
| `stick`     | fast      | 64                                 |
| `stick_cr`  | fast      | 64 (craft remainder: stick)        |

**Ingredient types**:

| Type                | Stack | Craft remainder |
|---------------------|-------|-----------------|
| `plain`             | 64    | —               |
| `ingredient_bottle` | 16    | glass bottle    |
| `ingredient_bowl`   | 16    | bowl            |
| `piping_bag`        | 2     | piping bag      |

```toml
[items]
item = [
  "my_pie_slice|food|4|0.5",
  "my_stew|bowl|6|0.7",
  "my_juice|bottle|4|0.3",
  "my_flour|plain"
]
```

### Tooltips
- **show_compatibility**: View mod compatibility info in tooltips (default: `true`)
- **show_ingredients**: View ingredients in tooltips (default: `true`)
- **require_shift**: Require holding Shift to view tooltips (default: `false`)
#### Adding Custom Tooltips
Format: `item_key|ingredients`
* Add ingredient or mod compatibility info tooltips to any item using the `custom_tooltips` list.
> **Note: Changes require a game restart.**
```
[items.tooltips]
show_compatibility = true
show_ingredients = true
require_shift = false
custom_tooltips = [
  "farmersdelight:hamburger|onion,lettuce,tomato"
]
```

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
| `raw_pie`   | Raw pie (unsliceable)        | —               |
| `raw_pizza` | Raw pizza (unsliceable)      | —               |
| `gelatin`   | Gelatin dessert block        | —               |

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

### Storage Display
- **show_tooltip_icons**: Show item icons in the tooltip for cloth sack and ration box items (default: `true`)
- **show_sack_block_icons**: Render floating item icons on the front face of placed cloth sack blocks (default: `true`)
```toml
[blocks.storage]
show_tooltip_icons = true
show_sack_block_icons = true
```

---

## Fluids

### Custom Fluids
Format: `name|slopeFindDistance|levelDecreasePerBlock`
> **Note: Custom fluids will require new texture files.**
* Register brand-new fluids under the `createfood:` namespace using the `fluid` list.
* `name` becomes the fluid's registry ID: `createfood:<name>`. A bucket items are registered automatically.
* Control how the fluid flows with `slopeFindDistance` and `levelDecreasePerBlock`.
> **Note: Changes require a game restart.**
```toml
[fluids]
fluid = [
  "honey_syrup|4|3",
  "cream_sauce|2|4"
]
```

---

# `createfood-server.toml`

---

## Items

### Nutrition & Saturation Overrides
Format: `item_id|nutrition|saturation`
* Overwrite nutrition and saturation using the `nutrition_saturation` list.
* Use `-` to keep the default value.
> **Note: Only works with Create: Food items.**
```
[items]
nutrition_saturation = [
  "sugar_cane_juice_bottle|1|1",
  "caramel_pretzel_stick|-|5"
]
```

### Effect Category Overrides
Format: `category_name|mod_id:effect_id`
* Pin a named effect category to a specific effect ID, bypassing the default mod-priority order.
* If the effect ID isn't found in the registry the default priority order is used as a fallback.
```
[items.effects]
category_overrides = ["comfort|farmersdelight:comfort"]
```

### Item Effect Overrides
Format: `item_id|category_or_effect_id|duration|amplifier`, `item_id|category_or_effect_id|remove`
* Add, adjust, or remove effects on using the `item_overrides` list.
* `category_or_effect_id` accepts either a category name (e.g. `comfort`) or a full effect ID (e.g. `minecraft:fire_resistance`).
* Duration is in ticks (20 ticks = 1 second). Amplifier `0` = level I, `1` = level II, etc.
> **Note: Only works with Create: Food items.**
```
[items.effects]
item_overrides = [
  "smore|comfort|9000|0",
  "spicy_chicken_nuggets|minecraft:fire_resistance|remove"
]
```

### Remainders
- **enable_egg_impact_remainder**: Drop an eggshell when a thrown egg hits a surface (default: `true`)
#### Custom Crafting Remainders
Format: `input_item|remainder_item`
* Add custom crafting remainders to any item using the `crafting_remainders` list.
> **Note: Custom remainders only apply to vanilla crafting recipes.**
```
[items.remainders]
enable_egg_impact_remainder = true
crafting_remainders = [
  "minecraft:egg|createfood:eggshell"
]
```

---

## Interactions
- **enable_filter_interactions**: Toggle cloth filter interactions (default: `true`)
- **enable_pumpkin_pie_placement**: Allow vanilla pumpkin pie to be placed as a block (default: `false`)
### Filter Interactions
Format: `filter_item|offhand_item|filter_result|container_result`
* Add cloth filter interactions using the `filter_interactions` list. Use `none` for an empty offhand requirement or to consume the filter with no remainder.
```
[interactions]
filter_interactions = [
  "createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle"
  "createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk"
]
```

### Handcrafting
- **enable_handcrafting**: Toggle crafting two-ingredient recipes via `RMB` (default: `true`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Restrict handcrafting to only allow outputs matching the `filter` list. If empty, all recipes are allowed.
* Block specific outputs from being handcrafted using `exclude`. Takes priority over the filter.
```
[interactions.handcraft]
filter = ["mod:createfood", "item:farmersdelight:hamburger", "tag:c:foods"]
exclude = ["item:createfood:raw_strider_meatball"]
```

---

## Storage

### Cloth Sack
- **cloth_sack_inventory**: Enable the cloth sack's inventory. When disabled it is purely cosmetic (default: `true`)
- **cloth_sack_eat_from_item**: `RMB` with the cloth sack in hand eats the first food stored inside instead of opening the inventory (default: `false`)
- **cloth_sack_allow_food**: Food items are always allowed regardless of the filter. With no filter set this effectively restricts the sack to food only (default: `false`)
- **cloth_sack_stack**: Allow each slot to hold a full stack instead of a single item (default: `true`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Restrict what items can be inserted using `cloth_sack_filter`. If empty, all items are allowed (or only food when `allow_food` is on).
* Block specific items from being inserted using `cloth_sack_exclude`. Takes priority over everything.
```
[storage.cloth_sack]
cloth_sack_filter = ["tag:c:foods"]
cloth_sack_exclude = ["item:createfood:raw_strider_meatball"]
```

### Ration Box
- **ration_box_inventory**: Enable the ration box's inventory. When disabled it is purely cosmetic and cannot be eaten from (default: `true`)
- **ration_box_eat_from_item**: `RMB` with the ration box in hand eats the first food stored inside. When disabled, it opens the inventory instead (default: `true`)
- **ration_box_allow_food**: Food items are always allowed regardless of the filter. With no filter set this effectively restricts the box to food only (default: `true`)
- **ration_box_stack**: Allow each slot to hold a full stack instead of a single item (default: `false`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Restrict what items can be inserted using `ration_box_filter`. If empty, all items are allowed (or only food when `allow_food` is on). Entries are allowed in addition to food when `allow_food` is on.
* Block specific items from being inserted using `ration_box_exclude`. Takes priority over everything.
```
[storage.ration_box]
ration_box_filter = ["mod:createfood", "tag:c:foods/meat"]
ration_box_exclude = ["item:minecraft:rotten_flesh"]
```
