# Create Food — Features Reference

---

## Table of Contents

1. [Creative Tabs](#1-creative-tabs)
2. [Tooltips](#2-tooltips)
3. [Cloth Filter Interactions](#3-cloth-filter-interactions)
4. [Handcrafting](#4-handcrafting)
5. [Campfire Cooking](#5-campfire-cooking)
6. [Crafting Remainders](#6-crafting-remainders)
7. [Storage Items](#7-storage-items)
8. [Display Blocks](#8-display-blocks)
9. [Fluid Dipping](#9-fluid-dipping)
10. [Config Files Reference](#10-config-files-reference)

---

## 1. Creative Tabs

| Tab | Key | Contents |
|-----|-----|----------|
| `Create: Food` | `tab.createfood` | All food items (excluding buckets and display blocks) |
| `Create: Food - Display` | `tab.createfood.display` | Display block items |
| `Create: Food - Fluids` | `tab.createfood.fluidEntry` | Fluid bucket items |

Items listed in `hide_items` are filtered out of all tabs automatically.

---

## 2. Tooltips

### How Tooltips Work

- **Ingredients** — a list prefixed with `+`, shown under an "Ingredients" header
- **Compatibility** — a mod name shown in blue when the item requires a compat mod

Tooltips respect the `require_shift` setting.

### Tooltip Key Naming

```
tooltip.createfood.<ingredient_key>_ingredient
```

In code (new helper syntax):
```java
// Ingredient keys only (no compat)
food("my_sandwich", 8, 0.7f, tips(null, "beef_ingredient", "lettuce_ingredient"));

// With compat key
bottle("my_bottle", 5, 0.8f, tips("tooltip.compat.peanut_butter", "peanut_butter_ingredient"));
```

> **Toolkit:** Ingredient key selection is in the Tooltips section of the Item tab. Existing keys are shown as clickable buttons. Custom keys can be added (they generate a lang entry automatically). The compat key toggle is also in the Tooltips section.

**Existing ingredient keys:** apple, apple_cream_frosting, apple_ice_cream, apple_jam, avocado, bacon, bayberry, beef, beef_meatballs, beetroot, bell_pepper, berry, berry_cream_frosting, berry_ice_cream, berry_jam, blueberry, brown_mushroom, butter, butterscotch, butterscotch_chips, butterscotch_fudge, cabbage, cacao_mass, caramel, caramel_chips, caramel_fudge, carrot, cheese, chicken, chocolate, chocolate_chips, chocolate_cream_frosting, chocolate_fudge, chocolate_graham_cracker_pie_crust, chocolate_ice_cream, chorus_fruit, chorus_fruit_cream_frosting, chorus_fruit_ice_cream, chorus_fruit_jam, corn, cranberry, cream_cheese, cream_frosting, crimson_fungus, cucumber, dark_chocolate, dark_chocolate_chips, egg, egg_yolk, eggplant, endermite_meatballs, fish, flesh, fried_egg, ginger, glow_berry, glow_berry_cream_frosting, glow_berry_ice_cream, glow_berry_jam, goat_cheese, graham_cracker_pie_crust, green_tea, hash_browns, honey, ice_cream, jam, kelp, lemon, lettuce, marshmallow, melon_cream_frosting, melon_ice_cream, melon_jam, mushroom, mutton, onion, peanut_butter, persimmon, pork, pork_meatballs, potato, pressed_cocoa, protein, rabbit, rabbit_meatballs, red_mushroom, rice, salmon, salt, sausage, scrambled_egg, slime, slimeballs, soul_berry, sour_cream, spider_eye, squid_ink, strider_meatballs, sugar, syrup, taco_sauce, toast, toffee, toffee_chips, toffee_fudge, tomato, tomato_sauce, ube_cream_frosting, vegetable, warped_fungus, white_chocolate, white_chocolate_chips, zucchini

### Custom Tooltips (Config)

The `custom_tooltips` config list adds ingredient tooltips to items from other mods. Format:

```
"mod:item_id|ingredient_key1,ingredient_key2"
```

Custom tooltip changes require a game restart.

> Custom tooltips are a player/server config feature. Use `tips(...)` in `ModItems.java` for items defined in the mod itself.

---

## 3. Cloth Filter Interactions

Holding a filled cloth filter in main hand and pressing RMB performs a filter interaction. The system reads from the `filter_interactions` config list.

Format: `"filter_item|offhand_item|filter_result|container_result"`

### Default Interactions

| Filter (main hand) | Offhand | Filter becomes | Produces |
|---|---|---|---|
| `cloth_filter_egg` | `minecraft:glass_bottle` | `cloth_filter_egg_yolk` | `egg_whites_bottle` |
| `cloth_filter_egg_yolk` | *(empty)* | `cloth_filter` | `egg_yolk` |
| `cloth_filter_cacao_mass` | `minecraft:bucket` | `cloth_filter_pressed_cocoa` | `cacao_butter_bucket` |
| `cloth_filter_pressed_cocoa` | *(empty)* | `cloth_filter` | `pressed_cocoa` |

To disable: set `enable_filter_interactions = false`.

---

## 4. Handcrafting

Holding an item in main hand and a compatible item in offhand, then pressing RMB, attempts to craft a result from any vanilla 2-ingredient recipe.

### Filtering

The `filter` config list restricts outputs to a whitelist:

| Format | Example | Matches |
|--------|---------|---------|
| `mod:mod_id` | `mod:createfood` | Any item from that mod |
| `item:mod_id:item_id` | `item:farmersdelight:hamburger` | One specific item |
| `tag:mod_id:tag_name` | `tag:c:foods` | Any item matching the tag |

The `exclude` list uses the same format but blocks specific outputs regardless of the whitelist.

Additional options: `allow_single_item` (default: `false`) enables crafting from one item alone in main hand; `enable_particles` (default: `true`) toggles particle feedback on craft.

To disable: set `enable_handcrafting = false`.

---

## 5. Campfire Cooking

Holding a raw or cookable item and standing near any block tagged `farmersdelight:heat_sources` (5×4×5 radius around the player) will slowly cook it using vanilla campfire cooking recipes. Smoke particles appear during cooking; flame particles and a campfire crackle sound play on completion.

By default (`sticks_only = true`) only items that produce a `*_stick` result are eligible — intended for marshmallow sticks and hot dog sticks. Set `sticks_only = false` to allow any campfire recipe.

| Config | Default | Effect |
|--------|---------|--------|
| `enable_campfire_cooking` | `true` | Toggle manual campfire cooking |
| `sticks_only` | `true` | Restrict to results whose ID ends in `_stick` |
| `campfire_cooking_exclude` | *(empty)* | Blacklist specific result item IDs |
| `campfire_cooking_filter` | *(empty)* | Whitelist of allowed result item IDs (if non-empty, only these cook) |

---

## 6. Crafting Remainders

### Egg Impact Remainder

Thrown egg → `eggshell` dropped at impact. Toggle with `enable_egg_impact_remainder`.

### Custom Crafting Remainders

Format: `"input_item|remainder_item"`. Default: `"minecraft:egg|createfood:eggshell"`.

> Only applies to vanilla crafting table recipes.

---

## 7. Storage Items

Two storage container items with configurable food-handling behavior.

### Cloth Sack

A bag item. Food storage is off by default.

| Config | Default | Effect |
|--------|---------|--------|
| `cloth_sack_allow_food` | `false` | Allow food items to be stored in the sack |
| `cloth_sack_eat_from_item` | `false` | Allow eating food directly from the sack |
| `cloth_sack_inventory` | `true` | Allow the sack to be used in inventory slots |
| `cloth_sack_stack` | `true` | Allow stacking contents |
| `cloth_sack_exclude` | *(empty)* | Item IDs blocked from entering the sack |
| `cloth_sack_filter` | *(empty)* | Item IDs allowed in the sack (whitelist; if non-empty, only these) |

### Ration Box

A box item. Food storage is on by default and direct eating is enabled.

| Config | Default | Effect |
|--------|---------|--------|
| `ration_box_allow_food` | `true` | Allow food items to be stored in the box |
| `ration_box_eat_from_item` | `true` | Allow eating food directly from the box |
| `ration_box_inventory` | `true` | Allow the box to be used in inventory slots |
| `ration_box_stack` | `false` | Allow stacking contents |
| `ration_box_exclude` | *(empty)* | Item IDs blocked from entering the box |
| `ration_box_filter` | *(empty)* | Item IDs allowed in the box (whitelist; if non-empty, only these) |

---

## 8. Display Blocks

Decorative blocks. Not obtainable via recipes — placed and interacted with in world.

### Plates

- **Place:** Bowl in hand, `Shift + RMB` on any surface
- **Toggle plate size:** Empty-handed, `Shift + RMB` on empty plate
- **Add serving:** `RMB` with compatible food; `Shift + RMB` for all at once
- **Remove serving:** `RMB` (one); `Shift + RMB` (all)
- **Eat from plate:** `Shift + LMB` — consumes one serving directly
- **Cutting board (offhand):** Holding a Farmer's Delight cutting board in offhand and pressing `RMB` on a plate processes the top item with cutting board recipes
- **Break:** `LMB` — drops food block (full) or individual items + bowl (partial)

When `enable_generic_display` is `true` (the default), any item not explicitly registered can still be placed on an empty plate or bowl and rendered generically.

Empty display surfaces cycle between three forms with `Shift + RMB`: plate → small plate → bowl. Placing a food configured for a bowl display fills the bowl up as servings are added (instead of arranging them on a plate).

### Bottles & Bowls

- **Place:** `Shift + RMB` on any surface
- **Retrieve:** `RMB` on placed block
- **Break:** `LMB` — drops block version

### Auto-Registration

> **Toolkit:** Most items are automatically caught by existing pattern-matching in `ModDisplayBlocks`. The toolkit checks the item ID against these patterns and informs you if new code is needed. The Display Block section in the Item tab only shows for regular items (not blocks or fluids, which are always auto-caught).

Pattern-matched automatically (by display type):

| Type | Patterns |
|------|----------|
| Plate | `pizza`, `cake`, `pie`, `burger`, `sandwich`, `toast`, `calzone`, `smore`, `waffle`, `cupcake`, `cookie`, `muffin`, `pastry`, `donut`, `bar_of`, `breakfast_bar`, `taco`, `burrito`, `wrap`, `gyro`, `cheese_block` |
| Small Plate | `slice` (suffix); also: `kelp_roll_slice`, `meringue_cookie`, `baked_potato`, `scone` |
| Bottle | `_juice_bottle`, `_jam_bottle`, `cane_syrup_bottle`, `chocolate_bottle`, `dark_chocolate_bottle`, `white_chocolate_bottle`, `chocolate_milk_bottle`, `fruit_smoothie_bottle`, `hot_chocolate_bottle`, `hot_dark_chocolate_bottle`, `hot_white_chocolate_bottle`, `taco_sauce_bottle`, `sugar_cane_juice_bottle`, `egg_whites_bottle`, `_bottle` (catch-all) |
| Bowl | `ice_cream_bowl`, `soup_bowl`, `stew_bowl`, `_bowl` (catch-all) |
| Salad Bowl | `salad` |

**Skip patterns** — items containing any of these substrings are never auto-registered: `raw_`, `stick_1`, `stick_2`, `dough`, `chips`, `chocolate_berries`, `chocolate_apple`, `bread_slice`, `toast_slice`, `apple_slice`, `tropical_fish_slice`, `pretzel_stick`, `taco_shell`, `donut_hole`, `pie_crust`, `sliced`

### Excluded Items

`apple_slice`, `fish_sticks`, `mozzarella_sticks`, `cookie_crumbs`, `chorus_fruit_slice`, `waffle_cone`, `meat_pie_filling`, `dumpling_wrappers`, `pumpkin_pie_block`, `pumpkin_pie_slice`, `graham_cracker_chocolate_marshmallow`, `graham_cracker_chocolate`, `chocolate_graham_cracker_chocolate_ice_cream`

> **Toolkit:** If your item doesn't match an auto-pattern, the toolkit generates the EXCLUDED_ITEMS entry or the registerConfig call as appropriate.

---

## 9. Fluid Dipping

A single small bowl (`createfood:small_bowl_block`) doubles as a fluid container. Fill it, then right-click it with a food item to transform that item using Create's `create:filling` recipes.

This is the mod's first *functional* Create integration — the ~450 `create:filling` and `create:emptying` recipe JSONs under `data/createfood/recipe/create/` previously only existed for Create's own machines. Create is not a build dependency; the recipes are read at runtime via registry lookup plus reflection, the same approach `PlateSliceInteraction` uses for Farmer's Delight cutting recipes. **Without Create the bowl still fills from buckets, but dipping is inert.**

### Holding fluid

Only a lone bowl holds fluid; a stack of 2–8 behaves exactly as it always has. Capacity is 1000mb (one bucket).

Fluid is stored as raw millibuckets and recipes are charged their exact declared amount, so no rounding error accumulates across fill/dip cycles. "Servings" (`amount / 125`) is a display convention only.

| Source | Amount | Returns |
|--------|--------|---------|
| Any bucket | 1000mb | Empty bucket |
| Bottle (`create:emptying`) | 500mb | Glass bottle |
| Filled bowl (`create:emptying`) | 333mb | Bowl |
| Ice cream stick (`create:emptying`) | 111mb | Stick |

A pour is refused outright if it does not fit in full or if the bowl holds a different fluid — nothing is ever partially consumed.

### Taking fluid back out

Dipping an empty container fills it, which is the normal way to empty a bowl: a glass bottle takes 500mb, a bowl 333mb, a stick or waffle cone 111mb. These come from the 93 container-input filling recipes and are allowed by default; add them to `dipping_exclude` to restrict dipping to food only.

Buckets are the exception and are handled natively in both directions, because Create's data never covers them — no filling recipe takes `minecraft:bucket` and no emptying recipe produces one. A bucket is all-or-nothing, so only a completely full bowl can fill one.

### Dipping

One item converts per click; the result goes to the player's inventory, or drops at their feet if it does not fit.

Exclusion is by exact item id (`dipping_exclude`), never by name — `createfood:ice_cream_bowl` and `createfood:pretzel_stick` are genuine food dips despite reading like containers, while bare `minecraft:bowl` and `minecraft:stick` are containers.

### Losing the fluid

A filled bowl refuses the plate toggle, refuses pickup, and refuses to be stacked on, so fluid cannot be discarded by accident. Broken at exactly 1000mb it leaves a fluid source block behind; at any lesser amount the fluid is lost.

---

## 10. Config Files Reference

### `createfood-client.toml`

| Setting | Default | Effect |
|---------|---------|--------|
| `hide_items` | *(compat items)* | Hide items and disable recipes. Requires `/reload`. |
| `show_compatibility` | `true` | Show compat mod name in tooltips |
| `show_ingredients` | `true` | Show ingredient list in tooltips |
| `require_shift` | `false` | Only show tooltip details when Shift is held |
| `custom_tooltips` | *(see §2)* | Add ingredient tooltips to any item. Requires game restart. |
| `always_display_upright` | `false` | Force all display blocks to render upright regardless of placement |
| `show_sack_block_icons` | `true` | Show content icons on placed cloth sack blocks |
| `show_tooltip_icons` | `true` | Show icons in storage item tooltips |

### `createfood-server.toml`

**Effects**

| Setting | Default | Effect |
|---------|---------|--------|
| `category_overrides` | *(empty)* | Override which effect fires for a named category. Format: `"category_name\|mod_id:effect_id"` |
| `item_overrides` | *(empty)* | Override or suppress effects on specific items. Format: `"item_id\|category_or_effect_id\|duration\|amplifier[\|chance]"` or `"item_id\|category_or_effect_id\|remove"` — chance is a float 0.0–1.0 |
| `nutrition_saturation` | *(empty)* | Override nutrition and/or saturation for any item. Format: `"item_id\|nutrition\|saturation"` — use `-` to keep the built-in value |

**Interactions**

| Setting | Default | Effect |
|---------|---------|--------|
| `enable_handcrafting` | `true` | Toggle two-ingredient RMB hand crafting |
| `allow_single_item` | `false` | Allow handcrafting with only a main-hand item |
| `enable_particles` | `true` | Show particle effects on handcraft |
| `filter` | *(empty)* | Whitelist hand crafting outputs (see §4 for format) |
| `exclude` | *(empty)* | Blacklist specific hand crafting outputs |
| `enable_filter_interactions` | `true` | Toggle cloth filter RMB interactions |
| `filter_interactions` | *(4 default entries)* | Define filter item → result interactions |
| `enable_campfire_cooking` | `true` | Toggle manual campfire cooking (see §5) |
| `sticks_only` | `true` | Restrict campfire cooking to `*_stick` results |
| `campfire_cooking_exclude` | *(empty)* | Blacklist specific campfire cooking results |
| `campfire_cooking_filter` | *(empty)* | Whitelist campfire cooking results |
| `enable_pumpkin_pie_placement` | `false` | Allow placing vanilla pumpkin pie as a block |

**Crafting Remainders**

| Setting | Default | Effect |
|---------|---------|--------|
| `crafting_remainders` | `minecraft:egg\|createfood:eggshell`, cake batter buckets\|`minecraft:bucket` | Add remainder items to vanilla crafting recipes and furnace/smoker smelting |
| `enable_egg_impact_remainder` | `true` | Drop eggshell when thrown egg hits surface |

**Display**

| Setting | Default | Effect |
|---------|---------|--------|
| `enable_cutting_board` | `true` | Allow cutting board offhand interaction on plates |
| `enable_generic_display` | `true` | Allow any item to display generically on plates and bowls |
| `exclude` | *(empty)* | Item IDs blocked from generic plate/bowl placement |
| `enable_dipping` | `true` | Allow a single small bowl to hold fluid and transform dipped food (see §9) |
| `dipping_exclude` | *(empty)* | Item IDs blocked as dipping inputs |

---

## 11. Datagen

Item tag files (`data/c/tags/item/<id>.json`) are generated automatically by `ItemTagProvider` and item model files (`assets/createfood/models/item/<id>.json`) by `ItemModelProvider` on each datagen run. Coverage is automatic — every item in `ModItems`, every block in `ModBlocks`, and every fluidEntry bucket in `ModFluids` is included.

**Files you still write manually (or via toolkit):**
- Lang entries (`en_us.json`)
- Recipe JSONs
- Display block registration code (`ModDisplayBlocks.java`)
- Java registration lines (`ModItems`, `ModBlocks`, `ModFluids`)

**Textures you still need to create** (datagen references them but does not generate them):
- `textures/item/<id>.png` for every item
- `textures/block/<id>_top.png`, `_side.png`, `_inner.png` for cake/pie/pizza blocks
- `textures/fluidEntry/<id>_still.png` and `<id>_flow.png` for fluids