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

## Tooltips
- **show_compat**: View mod compatibility info in tooltips (default: `true`)
- **show_ingredients**: View ingredients in tooltips (default: `true`)
- **require_shift**: Require holding Shift to view tooltips (default: `false`)
### Adding Custom Tooltips
Format: `item_key|ingredients`
* Add ingredient or mod compatibility info tooltips to any item using the `custom_tooltips` list.
```
"farmersdelight:hamburger|onion,lettuce,tomato"
```
> **Note: Changes to custom tooltips require a game restart.**

---

# `createfood-server.toml`

---

## Remainders
- **enable_egg_impact_remainder**: Drop an eggshell when a thrown egg hits a surface (default: `true`)
### Custom Crafting Remainders
Format: `input_item|remainder_item`
* Add custom crafting remainders to any item using the `crafting_remainders` list.
```
"minecraft:egg|createfood:eggshell"
```
> **Note: Custom remainders only apply to vanilla crafting recipes.**

---

## Filter Interactions
- **enable_filter_interactions**: Toggle cloth filter interactions (default: `true`)
### Custom Filter Interactions
Format: `filter_item|offhand_item|filter_result|container_result`
* Add custom cloth filter interactions using the `filter_interactions` list. Use `none` for an empty offhand requirement or to consume the filter with no remainder.
```
"createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle"
"createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk"
```

---

## Handcrafting
- **enable_hand_crafting**: Toggle crafting two-ingredient recipes via `RMB` (default: `true`)
### Filtering
Format: `mod:mod_id`,  `item:mod_id:item_id`,  `tag:mod_id:tag_name`
* Restrict hand crafting to only allow outputs matching the `hand_craft_filter` list. If empty, all recipes are allowed.
```
hand_craft_filter = ["mod:createfood", "item:farmersdelight:hamburger", "tag:c:foods"]
```

---

## Category Overrides
Format: `category_name|mod_id:effect_id`
* Pin a named effect category to a specific effect ID, bypassing the default mod-priority order. 
* If the effect ID isn't found in the registry the default priority order is used as a fallback.
```
category_overrides = ["comfort|farmersdelight:comfort"]
```

---

## Item Effect Overrides
Format: `item_id|category_or_effect_id|duration|amplifier`

Format: `item_id|category_or_effect_id|remove`
* Add, adjust, or remove effects on Create: Food items using the `item_overrides` list.
* `item_id` uses the same unqualified format as `disable_items`.
* `category_or_effect_id` accepts either a category name (e.g. `comfort`) or a full effect ID (e.g. `minecraft:fire_resistance`).
* Duration is in ticks (20 ticks = 1 second). Amplifier `0` = level I, `1` = level II, etc.
```
item_overrides = [
  "smore|comfort|9000|0",
  "spicy_chicken_nuggets|minecraft:fire_resistance|remove"
]
```
---

## Nutrition & Saturation Overrides
Format: `item_id|nutrition|saturation`

* Overwrite nutrition and saturation on Create: Food items using the `nutrition_saturation_overrides` list.
* Use `-` to use the default value.
```
nutrition_saturation_overrides = [
  "sugar_cane_juice_bottle|1|1",
  "caramel_pretzel_stick|-|5"
]
```
---