# Create Food — Features Reference

---

## Table of Contents

1. [Creative Tabs](#1-creative-tabs)
2. [Tooltips](#2-tooltips)
3. [Cloth Filter Interactions](#3-cloth-filter-interactions)
4. [Handcrafting](#4-handcrafting)
5. [Crafting Remainders](#5-crafting-remainders)
6. [Display Blocks](#6-display-blocks)
7. [Config Files Reference](#7-config-files-reference)

---

## 1. Creative Tabs

| Tab | Key | Contents |
|-----|-----|----------|
| `Create: Food` | `tab.createfood` | All food items (excluding buckets and display blocks) |
| `Create: Food - Display` | `tab.createfood.display` | Display block items |
| `Create: Food - Fluids` | `tab.createfood.fluid` | Fluid bucket items |

Items disabled via `disable_items` are filtered out of all tabs automatically.

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

**Existing ingredient keys:** apple, apple_cream_frosting, apple_ice_cream, apple_jam, avocado, bacon, beef, beef_meatballs, beetroot, berry, berry_cream_frosting, berry_ice_cream, berry_jam, brown_mushroom, butter, butterscotch, butterscotch_chips, cacao_mass, caramel, caramel_chips, carrot, cheese, chicken, chocolate, chocolate_chips, chocolate_cream_frosting, chocolate_ice_cream, chocolate_graham_cracker_pie_crust, chorus_fruit, chorus_fruit_cream_frosting, chorus_fruit_ice_cream, chorus_fruit_jam, corn, cream_frosting, crimson_fungus, cucumber, dark_chocolate, dark_chocolate_chips, egg, egg_yolk, eggplant, endermite_meatballs, fish, flesh, fried_egg, ginger, glow_berry, glow_berry_cream_frosting, glow_berry_ice_cream, glow_berry_jam, graham_cracker_pie_crust, green_tea, hash_browns, honey, ice_cream, kelp, lettuce, marshmallow, melon_cream_frosting, melon_ice_cream, melon_jam, mushroom, mutton, onion, peanut_butter, pork, pork_meatballs, potato, pressed_cocoa, rabbit, rabbit_meatballs, red_mushroom, rice, salt, sausage, scrambled_egg, slime, slimeballs, soul_berry, spider_eye, squid_ink, strider_meatballs, sugar, taco_sauce, toast, toffee, toffee_chips, tomato, tomato_sauce, ube_cream_frosting, warped_fungus, white_chocolate, white_chocolate_chips

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

The `handcraft_filter` config list restricts outputs:

| Format | Example | Matches |
|--------|---------|---------|
| `mod:mod_id` | `mod:createfood` | Any item from that mod |
| `item:mod_id:item_id` | `item:farmersdelight:hamburger` | One specific item |
| `tag:mod_id:tag_name` | `tag:c:foods` | Any item matching the tag |

To disable: set `enable_handcrafting = false`.

---

## 5. Crafting Remainders

### Egg Impact Remainder

Thrown egg → `eggshell` dropped at impact. Toggle with `enable_egg_impact_remainder`.

### Custom Crafting Remainders

Format: `"input_item|remainder_item"`. Default: `"minecraft:egg|createfood:eggshell"`.

> Only applies to vanilla crafting table recipes.

---

## 6. Display Blocks

Decorative blocks. Not obtainable via recipes — placed and interacted with in world.

### Plates

- **Place:** Bowl in hand, `Shift + RMB` on any surface
- **Toggle plate size:** Empty-handed, `Shift + RMB` on empty plate
- **Add serving:** `RMB` with compatible food; `Shift + RMB` for all at once
- **Remove serving:** `RMB` (one); `Shift + RMB` (all)
- **Break:** `LMB` — drops food block (full) or individual items + bowl (partial)

### Bottles & Bowls

- **Place:** `Shift + RMB` on any surface
- **Retrieve:** `RMB` on placed block
- **Break:** `LMB` — drops block version

### Auto-Registration

> **Toolkit:** Most items are automatically caught by existing pattern-matching in `ModDisplayBlocks`. The toolkit checks the item ID against these patterns and informs you if new code is needed. The Display Block section in the Item tab only shows for regular items (not blocks or fluids, which are always auto-caught).

Pattern-matched automatically: pizza, slice, pie, sandwich, burger, taco, burrito, wrap, cookie, cake, waffle, donut, muffin, pastry, _bowl, _bottle, salad, toast.

### Excluded Items

`apple_slice`, `fish_sticks`, `mozzarella_sticks`, `cookie_crumbs`, `chorus_fruit_slice`, `caramel_apple_slice`, `waffle_cone`, `meat_pie_filling`, `dumpling_wrappers`, `pumpkin_pie_block`, `graham_cracker_chocolate_marshmallow`, `graham_cracker_chocolate`, `chocolate_graham_cracker_chocolate_ice_cream`

> **Toolkit:** If your item doesn't match an auto-pattern, the toolkit generates the EXCLUDED_ITEMS entry or the registerConfig call as appropriate.

---

## 7. Config Files Reference

### `createfood-client.toml`

| Setting | Default | Effect |
|---------|---------|--------|
| `disable_items` | *(compat items)* | Hide items and disable recipes. Requires `/reload`. |
| `show_compatibility` | `true` | Show compat mod name in tooltips |
| `show_ingredients` | `true` | Show ingredient list in tooltips |
| `require_shift` | `false` | Only show tooltip details when Shift is held |
| `custom_tooltips` | *(see §2)* | Add ingredient tooltips to any item. Requires game restart. |

### `createfood-server.toml`

| Setting | Default | Effect |
|---------|---------|--------|
| `enable_handcrafting` | `true` | Toggle two-ingredient RMB hand crafting |
| `handcraft_filter` | *(empty)* | Restrict hand crafting outputs |
| `crafting_remainders` | `minecraft:egg\|createfood:eggshell` | Add remainder items to vanilla crafting recipes |
| `enable_egg_impact_remainder` | `true` | Drop eggshell when thrown egg hits surface |
| `enable_filter_interactions` | `true` | Toggle cloth filter RMB interactions |
| `filter_interactions` | *(4 default entries)* | Define filter item → result interactions |
| `category_overrides` | *(empty)* | Override which effect fires for a named category. Format: `"category_name\|mod_id:effect_id"` |
| `item_overrides` | *(empty)* | Override or suppress effects on specific items. Format: `"item_id\|category_or_effect_id\|duration\|amplifier"` or `"item_id\|category_or_effect_id\|remove"` |
| `nutrition_saturation` | *(empty)* | Override nutrition and/or saturation for any item. Format: `"item_id\|nutrition\|saturation"` — use `-` to keep the built-in value |

---

## 8. Datagen

Item tag files (`data/c/tags/item/<id>.json`) are generated automatically by `ItemTagProvider` and item model files (`assets/createfood/models/item/<id>.json`) by `ItemModelProvider` on each datagen run. Coverage is automatic — every item in `ModItems`, every block in `ModBlocks`, and every fluid bucket in `ModFluids` is included.

**Files you still write manually (or via toolkit):**
- Lang entries (`en_us.json`)
- Recipe JSONs
- Display block registration code (`ModDisplayBlocks.java`)
- Java registration lines (`ModItems`, `ModBlocks`, `ModFluids`)

**Textures you still need to create** (datagen references them but does not generate them):
- `textures/item/<id>.png` for every item
- `textures/block/<id>_top.png`, `_side.png`, `_inner.png` for cake/pie/pizza blocks
- `textures/fluid/<id>_still.png` and `<id>_flow.png` for fluids