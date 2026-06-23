# Create Food — Item Suggestions

> Production chains are verified against existing recipes. Every recipe that makes sense to exist is listed. Items already registered are referenced by ID only.
>
> **Toolkit notes:** Each item entry lists **Tooltip keys** for the Tooltips section. New fluids list a **Flow preset** for the Registration section. Proposals with multiple variants show the **Variations pattern** (ID pattern + variant table) for direct entry into the Variations section. Item types use the toolkit helper names (`food`, `fastFood`, `consumable`, `bowlFood`, `bowlConsumable`, `bottle`, `plain`, etc.).

---

## 🍬 Tier 1 — Snacks & Components

---

### Honeycomb Candy · `honeycomb_candy`

**Type** fastFood · **Nutrition** 2 · **Saturation** 0.3 · **Effects** Comfort 300t, Regeneration 300t · **Tooltip keys** `honey`, `sugar`

A pressed chunk of aerated toffee. Fits alongside the candy-on-a-stick family. Honey already drives Regeneration on honeyed_toast, honeyed_donut, etc. Distinct from marshmallow-on-a-stick — it's a compacted confection, not a roasted snack.

**New items**

| ID | Display Name |
|----|-------------|
| `honeycomb_candy` | Honeycomb Candy |

**Recipes**

```
minecraft:honeycomb + sugar
  └─ Compacting (unheated) → honeycomb_candy ×2
```

---

### Pumpernickel Bread Slice Toppings

`pumpernickel_bread_slice` exists but has no toppings of any kind — not even the simple single-ingredient toppings that regular `bread_slice` has (bacon, cheese, honey, lettuce, tomato, scrambled_egg, beetroot). Since pumpernickel is a distinct bread with a strong flavor, the savoury toppings are more fitting than the sweet ones; the sweet chain is covered separately by the buttered pumpernickel toast proposal below. These follow the identical Deploy + Crafting (shapeless) + Crafting (shaped) pattern as their regular bread_slice equivalents.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `pumpernickel_bread_slice_bacon` | Bread Slice | food | 6 | 0.6 | `bacon` |
| `pumpernickel_bread_slice_cheese` | Bread Slice | food | 6 | 0.3 | `cheese` |
| `pumpernickel_bread_slice_lettuce` | Bread Slice | food | 8 | 0.3 | `lettuce` |
| `pumpernickel_bread_slice_tomato` | Bread Slice | food | 8 | 0.3 | `tomato` |
| `pumpernickel_bread_slice_scrambled_egg` | Bread Slice | food | 8 | 0.8 | `scrambled_egg` |
| `pumpernickel_bread_slice_honey` | Bread Slice | food | 8 | 0.4 | `honey` |

**Recipes (shown for bacon; all others follow identically with their respective topping)**

```
pumpernickel_bread_slice (existing) + c:cooked_pork
  └─ Deploying → pumpernickel_bread_slice_bacon
     (pattern: bread_slice_bacon_from_deploying)

pumpernickel_bread_slice + c:cooked_pork
  └─ Crafting (shapeless) → pumpernickel_bread_slice_bacon

pumpernickel_bread_slice + c:cooked_pork
  └─ Crafting (shaped) → pumpernickel_bread_slice_bacon
     (pattern: bread_slice_bacon_from_shaped — vertical 2-item)

Repeat for: cheese (c:cheeses), lettuce (c:foods/leafy_green),
tomato (c:tomato), scrambled_egg (c:cooked_eggs),
honey (honey fluid 125mB via Filling + honey_bottle via Deploy)
```

---

## 🍔 Tier 4 — Full Meals

### Fish Sandwich Family

`bread_slice_fish` (existing in Batch 1 proposals) → `fish_sandwich` → `fish_sandwich_lettuce` → `fish_sandwich_lettuce_tomato`. Mirrors the mutton sandwich chain.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `fish_sandwich` | Sandwich | food | 7 | 0.6 | `fish` |
| `fish_sandwich_lettuce` | Sandwich | food | 8 | 0.65 | `fish`, `lettuce` |
| `fish_sandwich_lettuce_tomato` | Sandwich | food | 9 | 0.7 | `fish`, `lettuce`, `tomato` |

**Recipes**

```
c:bread_slice_fish + c:bread_slice
  └─ Deploying → fish_sandwich

c:bread_slice_fish + c:bread_slice_lettuce
  └─ Crafting (shapeless) → fish_sandwich_lettuce

fish_sandwich_lettuce + c:tomato
  └─ Deploying → fish_sandwich_lettuce_tomato
```

### Mutton Meatball Family

Full chain: `raw_mutton_meatball` → `mutton_meatball` → `small_mutton_meatballs` → stick variants (1/2/3) → `mutton_meatball_sandwich` → pasta plates. Mirrors beef/pork/rabbit meatball chains exactly.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `raw_mutton_meatball` | Raw Mutton Meatball | plain | — | — | — |
| `mutton_meatball` | Mutton Meatball | food | 4 | 0.6 | `mutton` |
| `small_mutton_meatballs` | Small Mutton Meatballs | consumable | 3 | 0.7 | `mutton` |
| `mutton_meatball_stick_1` | Mutton Meatball Stick | stickConsumable | 5 | 0.7 | `mutton` |
| `mutton_meatball_stick_2` | Mutton Meatball Stick | stickConsumable | 6 | 0.7 | `mutton` |
| `mutton_meatball_stick_3` | Mutton Meatball Stick | stickConsumable | 7 | 0.8 | `mutton` |
| `mutton_meatball_sandwich` | Meatball Sandwich | consumable | 10 | 0.6 | `mutton` |
| `pasta_plate_mutton_meatballs` | Pasta | bowlConsumable | 8 | 0.7 | `mutton` |
| `pasta_plate_mutton_meatballs_tomato_sauce` | Pasta | bowlConsumable | 11 | 0.9 | `mutton`, `tomato_sauce` |

**Recipes (follow beef_meatball chain exactly, substituting c:ground_mutton)**

```
c:ground_mutton
  └─ Compacting (unheated) → raw_mutton_meatball ×2
     (pattern: raw_beef_meatball_from_compacting)

raw_mutton_meatball
  └─ Smelting / Smoking / Campfire → mutton_meatball

mutton_meatball
  └─ Cutting (knife) → small_mutton_meatballs ×2

minecraft:stick + mutton_meatball ×1/2/3
  └─ Crafting (shaped) → mutton_meatball_stick_1/2/3

c:bread_slice_mutton_meatball + c:bread_slice
  └─ Deploying → mutton_meatball_sandwich

c:pasta_plate + c:mutton_meatball
  └─ Deploying → pasta_plate_mutton_meatballs

c:pasta_plate_tomato_sauce + c:mutton_meatball
  └─ Deploying → pasta_plate_mutton_meatballs_tomato_sauce

Full crafting matrix follows pasta_plate_beef_meatballs_tomato_sauce pattern.
```

---

### Pork & Rabbit Sandwich Chains

Mutton has `bread_slice_mutton` → `mutton_sandwich` → `mutton_sandwich_beetroot`. Pork and rabbit have tacos, burritos, wraps, and stews but no sandwich form at all.

---

#### Pork Sandwich · `bread_slice_pork` / `pork_sandwich` / `pork_sandwich_onion`

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `bread_slice_pork` | Bread Slice | food | 4 | 0.5 | `pork` |
| `pork_sandwich` | Sandwich | food | 7 | 0.6 | `pork` |
| `pork_sandwich_onion` | Sandwich | food | 8 | 0.7 | `pork`, `onion` |

**Recipes**

```
c:bread_slice + c:cooked_pork
  └─ Deploying → bread_slice_pork
     (pattern: bread_slice_mutton_from_deploying)

c:bread_slice + c:cooked_pork
  └─ Crafting (shapeless) → bread_slice_pork

c:bread_slice_pork + c:bread_slice
  └─ Deploying → pork_sandwich

c:bread_slice_pork + c:bread_slice
  └─ Crafting (shapeless) → pork_sandwich

c:bread_slice + c:cooked_pork + c:bread_slice
  └─ Crafting (shapeless) → pork_sandwich (all-at-once alt)

c:bread_slice_pork + c:bread_slice_onion
  └─ Crafting (shapeless) → pork_sandwich_onion

c:bread_slice + c:cooked_pork + c:onion + c:bread_slice
  └─ Crafting (shapeless) → pork_sandwich_onion (all-at-once alt)
```

---

#### Rabbit Sandwich · `bread_slice_rabbit` / `rabbit_sandwich` / `rabbit_sandwich_carrot`

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `bread_slice_rabbit` | Bread Slice | food | 5 | 0.5 | `rabbit` |
| `rabbit_sandwich` | Sandwich | food | 7 | 0.6 | `rabbit` |
| `rabbit_sandwich_carrot` | Sandwich | food | 7 | 0.7 | `rabbit`, `carrot` |

**Recipes (mirror pork chain, substituting `c:cooked_rabbit` and `c:carrot`)**

```
c:bread_slice + c:cooked_rabbit
  └─ Deploying → bread_slice_rabbit

c:bread_slice_rabbit + c:bread_slice
  └─ Deploying → rabbit_sandwich

c:bread_slice + c:cooked_rabbit + c:bread_slice
  └─ Crafting (shapeless) → rabbit_sandwich (all-at-once alt)

c:bread_slice_rabbit + c:bread_slice_carrot
  └─ Crafting (shapeless) → rabbit_sandwich_carrot

c:bread_slice + c:cooked_rabbit + c:carrot + c:bread_slice
  └─ Crafting (shapeless) → rabbit_sandwich_carrot (all-at-once alt)
```

---

## 🎂 Tier 4–5 — Block Foods

---

### Chocolate Berry Pie Trio

**Block type** `block_pie` (4 slices) · **Slice nutrition** 5 · **Slice saturation** 0.8 · **Slice effects** Comfort 1200t · **Slice tooltip keys** `chocolate`, `berry` / `dark_chocolate`, `berry` / `white_chocolate`, `berry`

A chocolate + sweet berry combination pie family — three variants matching the chocolate/dark_chocolate/white_chocolate trio applied consistently across cream items and dipped fruit.

**New fluids** (each `.build()`)

| Fluid ID |
|----------|
| `chocolate_berry_pie_filling` |
| `dark_chocolate_berry_pie_filling` |
| `white_chocolate_berry_pie_filling` |

**New items**

| ID | Display Name |
|----|-------------|
| `chocolate_berry_pie_filling_bucket` | Chocolate Berry Pie Filling Bucket |
| `raw_chocolate_berry_pie` | Raw Chocolate Berry Pie |
| `chocolate_berry_pie` | Chocolate Berry Pie |
| `chocolate_berry_pie_slice` | Slice of Chocolate Berry Pie |
| `dark_chocolate_berry_pie_filling_bucket` | Dark Chocolate Berry Pie Filling Bucket |
| `raw_dark_chocolate_berry_pie` | Raw Dark Chocolate Berry Pie |
| `dark_chocolate_berry_pie` | Dark Chocolate Berry Pie |
| `dark_chocolate_berry_pie_slice` | Slice of Dark Chocolate Berry Pie |
| `white_chocolate_berry_pie_filling_bucket` | White Chocolate Berry Pie Filling Bucket |
| `raw_white_chocolate_berry_pie` | Raw White Chocolate Berry Pie |
| `white_chocolate_berry_pie` | White Chocolate Berry Pie |
| `white_chocolate_berry_pie_slice` | Slice of White Chocolate Berry Pie |

**Recipes (shown for dark_chocolate; chocolate and white_chocolate follow identically)**

```
dark_chocolate fluid + berry_jam fluid + sugar + butter
  └─ Mixing (heated) → dark_chocolate_berry_pie_filling fluid (1000mB)

butter + sugar ×2 + minecraft:sweet_berries ×3 + dark_chocolate (item)
  └─ Cooking Pot → dark_chocolate_berry_pie_filling_bucket

raw_pie_crust + dark_chocolate_berry_pie_filling fluid (1000mB)
  └─ Filling → raw_dark_chocolate_berry_pie

raw_pie_crust + dark_chocolate_berry_pie_filling_bucket
  └─ Item Application (any) → raw_dark_chocolate_berry_pie

raw_dark_chocolate_berry_pie
  └─ Smelting / Smoking / Campfire → dark_chocolate_berry_pie

dark_chocolate_berry_pie
  └─ Cutting (knife) → dark_chocolate_berry_pie_slice ×4

dark_chocolate_berry_pie_slice ×4
  └─ Crafting (shaped, 2×2) → dark_chocolate_berry_pie
```

---

### Melon Cheesecake

**Block type** `block_cake` (registerCake) · **Slice type** Item · **Slice nutrition** 4 · **Slice saturation** 0.5 · **Slice effects** Comfort 1200t · **Slice tooltip keys** `melon_cream_frosting`

Melon cream frosting exists but has no cheesecake application, unlike apple/berry/glow_berry/chorus_fruit. Follows the identical raw → Deploy melon_slice → heat chain.

**New items**

| ID | Display Name |
|----|-------------|
| `raw_melon_cheesecake` | Raw Melon Cheesecake |
| `melon_cheesecake` | Melon Cheesecake |
| `melon_cheesecake_slice` | Slice of Melon Cheesecake |

**Recipes**

```
raw_cheesecake (existing) + minecraft:melon_slice
  └─ Crafting (shapeless) → raw_melon_cheesecake
     (pattern: raw_apple_cheesecake_from_crafting)

raw_cheesecake + minecraft:melon_slice
  └─ Item Application (any) → raw_melon_cheesecake

raw_melon_cheesecake
  └─ Smelting / Smoking / Campfire → melon_cheesecake

melon_cheesecake
  └─ Cutting (knife) → melon_cheesecake_slice ×8
     (pattern: apple_cheesecake_from_cutting)
```

---

### Apple & Melon Cream Cake Fruit Variants

Glow berry and chorus fruit have top-garnish variants for both apple cream cake and melon cream cake. Apple and melon do not have their own fruit garnish — the natural self-garnish completing the set.

**New items**

| ID | Display Name | Slice nutrition | Slice saturation | Effects | Slice tooltip keys |
|----|-------------|-----------------|------------------|---------|-------------------|
| `apple_cream_cake_apple` | Apple Cream Cake | 4 | 0.7 | Comfort 1200t | `apple_cream_frosting`, `apple` |
| `apple_cream_cake_slice_apple` | Slice of Apple Cream Cake | 4 | 0.7 | Comfort 1200t | `apple_cream_frosting`, `apple` |
| `melon_cream_cake_melon` | Melon Cream Cake | 4 | 0.7 | Comfort 1200t | `melon_cream_frosting` |
| `melon_cream_cake_slice_melon` | Slice of Melon Cream Cake | 4 | 0.7 | Comfort 1200t | `melon_cream_frosting` |

**Recipes**

```
apple_cream_cake (existing) + minecraft:apple
  └─ Item Application (any) → apple_cream_cake_apple
     (pattern: apple_cream_cake_glow_berry_from_item_application)

apple_cream_cake + minecraft:apple
  └─ Crafting (shapeless) → apple_cream_cake_apple

melon_cream_cake (existing) + minecraft:melon_slice
  └─ Item Application (any) → melon_cream_cake_melon

melon_cream_cake + minecraft:melon_slice
  └─ Crafting (shapeless) → melon_cream_cake_melon
```

---

## 🍞 Tier 2–3 — Pumpernickel Toast Chain

`buttered_pumpernickel_toast` as a base, then seven sweet topping variants extending the buttered_toast family to pumpernickel. All use fastFood and follow the Deploy + Filling + Crafting pattern of the regular toast family.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Effects | Tooltip keys |
|----|-------------|------|-----------|------------|---------|--------------|
| `buttered_pumpernickel_toast` | Toast | fastFood | 4 | 0.6 | — | `butter` |
| `butterscotch_pumpernickel_toast` | Toast | consumable | 4 | 0.9 | Comfort 300t | `butterscotch` |
| `caramel_pumpernickel_toast` | Toast | consumable | 4 | 0.8 | Comfort 300t | `caramel` |
| `toffee_pumpernickel_toast` | Toast | consumable | 4 | 0.9 | Comfort 300t | `toffee` |
| `honeyed_pumpernickel_toast` | Toast | consumable | 5 | 0.8 | Regeneration 300t | `honey` |
| `chocolate_pumpernickel_toast` | Toast | consumable | 5 | 0.6 | Comfort 300t | `chocolate` |
| `dark_chocolate_pumpernickel_toast` | Toast | consumable | 6 | 0.5 | Comfort 300t | `dark_chocolate` |
| `white_chocolate_pumpernickel_toast` | Toast | consumable | 4 | 0.7 | Comfort 300t | `white_chocolate` |

**Recipes (shown for butterscotch; all other sweet toppings follow the same pattern)**

```
── buttered_pumpernickel_toast ────────────────────────────────────────

pumpernickel_bread_slice (existing) + c:butter
  └─ Deploying → buttered_pumpernickel_toast
     (pattern: buttered_toast_from_deploying)

pumpernickel_bread_slice + c:butter
  └─ Crafting (shapeless) → buttered_pumpernickel_toast

── Sweet topping variants ─────────────────────────────────────────────

buttered_pumpernickel_toast + butterscotch fluid (125mB)
  └─ Filling → butterscotch_pumpernickel_toast
     (pattern: butterscotch_toast_from_filling_butterscotch)

buttered_pumpernickel_toast + c:butterscotch (item)
  └─ Deploying → butterscotch_pumpernickel_toast

buttered_pumpernickel_toast + c:butterscotch (item)
  └─ Crafting (shapeless) → butterscotch_pumpernickel_toast
```

---

## 🍞 Tier 2–3 — French Toast Family

### French Toast Slice · `french_toast_slice` chain

**Base type** fastFood · **Base nutrition** 5 · **Base saturation** 0.9 · **Tooltip keys** `egg`, `toast`

Bread soaked in egg and milk then cooked. The slice form earns `.fast()` consistent with toast_slice and buttered_toast.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Effects | Tooltip keys |
|----|-------------|------|-----------|------------|---------|--------------|
| `french_toast_slice` | French Toast | fastFood | 5 | 0.9 | — | `egg`, `toast` |
| `french_toast_slice_sugar` | French Toast | fastFood | 5 | 1.0 | Comfort 300t | `egg`, `sugar` |
| `french_toast_slice_apple_jam` | French Toast | fastFood | 6 | 1.0 | Comfort 300t | `egg`, `apple_jam` |
| `french_toast_slice_berry_jam` | French Toast | fastFood | 6 | 1.0 | Comfort 300t | `egg`, `berry_jam` |
| `french_toast_slice_glow_berry_jam` | French Toast | fastFood | 6 | 1.1 | Comfort 300t, Night Vision 100t | `egg`, `glow_berry_jam` |
| `french_toast_slice_chorus_fruit_jam` | French Toast | fastFood | 7 | 1.0 | Comfort 300t | `egg`, `chorus_fruit_jam` |
| `french_toast_slice_melon_jam` | French Toast | fastFood | 6 | 0.9 | Comfort 300t | `egg`, `melon_jam` |
| `french_toast_slice_syrup` | French Toast | fastFood | 6 | 1.2 | Comfort 300t | `egg`, `sugar` |

**Recipes**

```
── french_toast_slice ─────────────────────────────────────────────────

c:bread_slice + minecraft:egg + c:milk_bottle
  └─ Cooking Pot → french_toast_slice
     (pattern: boiled_egg_from_cooking)

── french_toast_slice_sugar ───────────────────────────────────────────

french_toast_slice + c:powdered_sugar
  └─ Deploying → french_toast_slice_sugar

french_toast_slice + c:powdered_sugar
  └─ Crafting (shapeless) → french_toast_slice_sugar

french_toast_slice + c:powdered_sugar
  └─ Crafting (shaped) → french_toast_slice_sugar

── Jam variants (shown for berry_jam; apple/glow_berry/chorus_fruit/melon follow identically) ─

french_toast_slice + berry_jam fluid (125mB)
  └─ Filling → french_toast_slice_berry_jam
     (pattern: bread_slice_berry_jam_from_filling_berry_jam)

french_toast_slice + c:berry_jam_bottle
  └─ Crafting (shapeless) → french_toast_slice_berry_jam

french_toast_slice + c:berry_jam_bottle
  └─ Crafting (shaped) → french_toast_slice_berry_jam

── french_toast_slice_syrup ───────────────────────────────────────────

french_toast_slice + cane_syrup fluid (125mB)
  └─ Filling → french_toast_slice_syrup
     (pattern: butterscotch_toast_from_filling_butterscotch)

french_toast_slice + c:cane_syrup_bucket
  └─ Crafting (shapeless) → french_toast_slice_syrup

french_toast_slice + c:cane_syrup_bucket
  └─ Crafting (shaped) → french_toast_slice_syrup
```

---

## 🧇 Tier 1 — Garnish Gap Fills

---

### Apple & Melon Cream Sweet Roll Garnish Variants

Berry, glow berry, and chorus fruit cream sweet rolls all have a fruit garnish variant applied via Deploy. Apple and melon cream sweet rolls have none.

**Variations pattern for `apple_cream_sweet_roll`:** `apple_cream_sweet_roll_[v]`
**Variations pattern for `apple_cream_chocolate_sweet_roll`:** `apple_cream_chocolate_sweet_roll_[v]`
**Variations pattern for `melon_cream_sweet_roll`:** `melon_cream_sweet_roll_[v]`
**Variations pattern for `melon_cream_chocolate_sweet_roll`:** `melon_cream_chocolate_sweet_roll_[v]`

| Variant key | Display name | Nutrition | Saturation | Effects | Tooltip keys |
|-------------|-------------|-----------|------------|---------|--------------|
| `apple` | Sweet Roll / Chocolate Sweet Roll | 5 | 0.8 | Comfort 1200t | `apple_cream_frosting`, `apple` |
| `melon` | Sweet Roll / Chocolate Sweet Roll | 5 | 0.8 | Comfort 1200t | `melon_cream_frosting` |

**Recipes (shown for apple; melon follows with `minecraft:melon_slice`)**

```
apple_cream_sweet_roll (existing) + minecraft:apple
  └─ Deploying → apple_cream_sweet_roll_apple

apple_cream_sweet_roll + minecraft:apple
  └─ Crafting (shapeless) → apple_cream_sweet_roll_apple

apple_cream_chocolate_sweet_roll (existing) + minecraft:apple
  └─ Deploying → apple_cream_chocolate_sweet_roll_apple

apple_cream_chocolate_sweet_roll + minecraft:apple
  └─ Crafting (shapeless) → apple_cream_chocolate_sweet_roll_apple
```

---

### Apple & Melon Cream Mini Waffle Garnish Variants

Berry, glow berry, and chorus fruit cream mini waffles all have a fruit garnish variant. Apple and melon cream mini waffles have none — the identical gap.

**Variations pattern for `apple_cream_mini_waffle`:** `apple_cream_mini_waffle_[v]`
**Variations pattern for `melon_cream_mini_waffle`:** `melon_cream_mini_waffle_[v]`

| Variant key | Display name | Nutrition | Saturation | Effects | Tooltip keys |
|-------------|-------------|-----------|------------|---------|--------------|
| `apple` | Mini Waffle | 4 | 0.7 | Comfort 1200t | `apple_cream_frosting`, `apple` |
| `melon` | Mini Waffle | 4 | 0.7 | Comfort 1200t | `melon_cream_frosting` |

**Recipes (shown for apple; melon follows with `minecraft:melon_slice`)**

```
apple_cream_mini_waffle (existing) + minecraft:apple
  └─ Deploying → apple_cream_mini_waffle_apple

apple_cream_mini_waffle + minecraft:apple
  └─ Crafting (shapeless) → apple_cream_mini_waffle_apple

apple_cream_mini_waffle + minecraft:apple
  └─ Crafting (shaped) → apple_cream_mini_waffle_apple
     (pattern: berry_cream_mini_waffle_sweet_berry_from_shaped — vertical 2-item)

melon_cream_mini_waffle (existing) + minecraft:melon_slice
  └─ Deploying → melon_cream_mini_waffle_melon

melon_cream_mini_waffle + minecraft:melon_slice
  └─ Crafting (shapeless/shaped) → melon_cream_mini_waffle_melon
```

---

## 🍎 Tier 1 — Sweet Dipped Snacks

---

### Butterscotch & Toffee Popcorn

`caramel_popcorn` exists as a compat item guarded by `neoforge:not tag_empty c:popcorn`. Butterscotch and toffee follow the identical three-recipe pattern including the same compat condition.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Effects | Tooltip keys |
|----|-------------|------|-----------|------------|---------|--------------|
| `butterscotch_popcorn` | Popcorn | consumable | 3 | 0.8 | Comfort 300t | `butterscotch` |
| `toffee_popcorn` | Popcorn | consumable | 3 | 0.7 | Comfort 300t | `toffee` |

**Recipes (shown for butterscotch; toffee follows with `c:toffee`)**

```
c:popcorn + butterscotch fluid (125mB)
  └─ Filling → butterscotch_popcorn
     [neoforge condition: not tag_empty c:popcorn]

c:popcorn + c:butterscotch (item)
  └─ Crafting (shapeless) → butterscotch_popcorn
     [neoforge condition: not tag_empty c:popcorn]

c:butterscotch / c:popcorn
  └─ Crafting (shaped) → butterscotch_popcorn
     [neoforge condition: not tag_empty c:popcorn]
     (pattern: caramel_popcorn_from_shaped — vertical 2-item, flavour on top)
```

---