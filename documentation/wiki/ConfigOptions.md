### ***2.3.0***

# `createfood-client.toml`

## Disabling Items
Format: `item_id`
* Create: Food items can be hidden in game by adding entries to the `disable_items` list. Recipes are also disabled.
* In 2.1.0+ display blocks are also hidden.
```
disable_items = ["strider_meatball"]
```

---

## Custom Display Block
Format: `mod:item_id|display_type|max_stack`
> **Note: Custom display blocks will require new models/blockstate/loot_table files.**
* Register items from other mods to be placeable as display blocks using the `custom_display_block` list.
* `display_type` must be one of: `plate`, `small_plate`, `bottle`, `bowl`, `salad_bowl`
* `max_stack` controls the maximum number of items that can be placed on the display block.
> **Note: Changes require a game restart.**
```
custom_display_block = [
  "kaleidoscope_cookery:samsa|plate|2",
  "farmersrespite:tea_bottle|bottle|1"
]
```

---

## Storage
- **show_tooltip_icons**: Show item icons in the tooltip for cloth sack and ration box items (default: `true`)
- **show_sack_block_icons**: Render floating item icons on the front face of placed cloth sack blocks (default: `true`)

---

## Tooltips
- **show_compatibility**: View mod compatibility info in tooltips (default: `true`)
- **show_ingredients**: View ingredients in tooltips (default: `true`)
- **require_shift**: Require holding Shift to view tooltips (default: `false`)
### Adding Custom Tooltips
Format: `item_key|ingredients`
* Add ingredient or mod compatibility info tooltips to any item using the `custom_tooltips` list.
> **Note: Changes require a game restart.**
```
"farmersdelight:hamburger|onion,lettuce,tomato"
```

---

# `createfood-server.toml`

---

## Effect Category Overrides
Format: `category_name|mod_id:effect_id`
* Pin a named effect category to a specific effect ID, bypassing the default mod-priority order.
* If the effect ID isn't found in the registry the default priority order is used as a fallback.
```
category_overrides = ["comfort|farmersdelight:comfort"]
```

---

## Item Effect Overrides
Format: `item_id|category_or_effect_id|duration|amplifier`, `item_id|category_or_effect_id|remove`
* Add, adjust, or remove effects on Create: Food items using the `item_overrides` list.
* `category_or_effect_id` accepts either a category name (e.g. `comfort`) or a full effect ID (e.g. `minecraft:fire_resistance`).
* Duration is in ticks (20 ticks = 1 second). Amplifier `0` = level I, `1` = level II, etc.
> **Note: Only works with Create: Food items.**
```
item_overrides = [
  "smore|comfort|9000|0",
  "spicy_chicken_nuggets|minecraft:fire_resistance|remove"
]
```
---

## Nutrition & Saturation Overrides
Format: `item_id|nutrition|saturation`
* Overwrite nutrition and saturation on Create: Food items using the `nutrition_saturation` list.
* Use `-` to keep the default value.
> **Note: Only works with Create: Food items.**
```
nutrition_saturation = [
  "sugar_cane_juice_bottle|1|1",
  "caramel_pretzel_stick|-|5"
]
```
---

## Handcrafting
- **enable_handcrafting**: Toggle crafting two-ingredient recipes via `RMB` (default: `true`)
### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Restrict handcrafting to only allow outputs matching the `handcraft_filter` list. If empty, all recipes are allowed.
* Block specific outputs from being handcrafted using `handcraft_exclude`. Takes priority over the filter.
```
handcraft_filter = ["mod:createfood", "item:farmersdelight:hamburger", "tag:c:foods"]
handcraft_exclude = ["item:createfood:raw_strider_meatball"]
```

---

## Interactions
- **enable_filter_interactions**: Toggle cloth filter interactions (default: `true`)
### Filter Interactions
Format: `filter_item|offhand_item|filter_result|container_result`
* Add cloth filter interactions using the `filter_interactions` list. Use `none` for an empty offhand requirement or to consume the filter with no remainder.
```
"createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle"
"createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk"
```

---

## Remainders
- **enable_egg_impact_remainder**: Drop an eggshell when a thrown egg hits a surface (default: `true`)
### Custom Crafting Remainders
Format: `input_item|remainder_item`
* Add custom crafting remainders to any item using the `crafting_remainders` list.
> **Note: Custom remainders only apply to vanilla crafting recipes.**
```
"minecraft:egg|createfood:eggshell"
```

---

## Storage

### Cloth Sack
- **cloth_sack_inventory**: Enable the cloth sack's inventory. When disabled it is purely cosmetic (default: `true`)
- **cloth_sack_eat_from_item**: Right-clicking with the cloth sack in hand eats the first food stored inside instead of opening the inventory (default: `false`)
- **cloth_sack_allow_food**: Food items are always allowed regardless of the filter. With no filter set this effectively restricts the sack to food only (default: `false`)
- **cloth_sack_stack**: Allow each slot to hold a full stack instead of a single item (default: `true`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Restrict what items can be inserted using `cloth_sack_filter`. If empty, all items are allowed (or only food when `allow_food` is on).
* Block specific items from being inserted using `cloth_sack_exclude`. Takes priority over everything.
```
cloth_sack_filter = ["tag:c:foods"]
cloth_sack_exclude = ["item:createfood:raw_strider_meatball"]
```

### Ration Box
- **ration_box_inventory**: Enable the ration box's inventory. When disabled it is purely cosmetic and cannot be eaten from (default: `true`)
- **ration_box_eat_from_item**: Right-clicking with the ration box in hand eats the first food stored inside. When disabled, it opens the inventory instead (default: `true`)
- **ration_box_allow_food**: Food items are always allowed regardless of the filter. With no filter set this effectively restricts the box to food only (default: `true`)
- **ration_box_stack**: Allow each slot to hold a full stack instead of a single item (default: `false`)
#### Filter & Exclude
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Restrict what items can be inserted using `ration_box_filter`. If empty, all items are allowed (or only food when `allow_food` is on). Entries are allowed in addition to food when `allow_food` is on.
* Block specific items from being inserted using `ration_box_exclude`. Takes priority over everything.
```
ration_box_filter = ["mod:createfood", "tag:c:foods/meat"]
ration_box_exclude = ["item:minecraft:rotten_flesh"]
```
