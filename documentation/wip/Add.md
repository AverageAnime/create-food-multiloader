# Create Food — Item Suggestions

> Production chains are verified against existing recipes. Every recipe that makes sense to exist is listed. Items already registered are referenced by ID only.
>
> **Toolkit notes:** Each item entry lists **Tooltip keys** for the Tooltips section. Item types use the toolkit helper names (`food`, `fastFood`, `consumable`, `bowlFood`, `bowlConsumable`, `bottle`, `plain`, `fluid`, etc.).

---

## 🍚 Tier 2 — Rice Pudding Topping

### Caramel Rice Pudding

`rice_pudding_bowl` has no toppings. `yogurt_bowl` establishes the canonical pattern for topping a bowl food with a bottle item via Deploying. Caramel rice pudding is a classic European dessert.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `rice_pudding_bowl_caramel` | Rice Pudding | bowlFood | 7 | 0.8 | `caramel` |

**Recipes**

```
c:rice_pudding_bowl + c:caramel_bottle
  └─ Deploying → rice_pudding_bowl_caramel
     (pattern: yogurt_bowl_honey_from_deploying — bottle onto tagged bowl)

c:rice_pudding_bowl + c:caramel_bottle
  └─ Crafting (shapeless) → rice_pudding_bowl_caramel

caramel fluid (250mB) + c:rice_pudding_bowl
  └─ Filling → rice_pudding_bowl_caramel
```

---

## 🍞 Tier 2 — Bread Pudding Topping

### Butterscotch Bread Pudding

`bread_pudding_bowl` has no toppings. Butterscotch is the natural warm-candy complement to the custard-and-bread base — butterscotch bread pudding is a well-established dessert. Uses the same bottle Deploying + Filling pattern as rice pudding caramel above.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `bread_pudding_bowl_butterscotch` | Bread Pudding | bowlFood | 8 | 0.85 | `butterscotch` |

**Recipes**

```
c:bread_pudding_bowl + c:butterscotch_bottle
  └─ Deploying → bread_pudding_bowl_butterscotch

c:bread_pudding_bowl + c:butterscotch_bottle
  └─ Crafting (shapeless) → bread_pudding_bowl_butterscotch

butterscotch fluid (250mB) + c:bread_pudding_bowl
  └─ Filling → bread_pudding_bowl_butterscotch
```

---

## 🥣 Tier 2 — Cream Soup Cheese Variants

### Mushroom Cream Soup with Cheese · Tomato Cream Soup with Cheese

`potato_cream_soup_bowl_cheese` exists via Deploying + Filling + FD Cooking + Crafting from `c:potato_cream_soup_bowl` + `c:cheeses` / cheese fluid (125mB). `mushroom_cream_soup_bowl` and `tomato_cream_soup_bowl` are both tagged but have no cheese variant. Cream of mushroom with cheese is a classic gratin-style soup; tomato cream soup with cheese is canonical comfort food. Both use the identical four-route potato precedent.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `mushroom_cream_soup_bowl_cheese` | Mushroom Cream Soup | bowlFood | 8 | 0.7 | `cheese` |
| `tomato_cream_soup_bowl_cheese` | Tomato Cream Soup | bowlFood | 8 | 0.65 | `cheese` |

**Recipes (shown for mushroom; tomato follows with `c:tomato_cream_soup_bowl`)**

```
── mushroom_cream_soup_bowl_cheese ───────────────────────────────────

c:mushroom_cream_soup_bowl + c:cheeses
  └─ Deploying → mushroom_cream_soup_bowl_cheese
     (pattern: potato_cream_soup_bowl_cheese_from_deploying)

c:mushroom_cream_soup_bowl + c:cheeses
  └─ Crafting (shapeless) → mushroom_cream_soup_bowl_cheese
     (pattern: potato_cream_soup_bowl_cheese_from_crafting)

c:mushroom_cream_soup_bowl + cheese fluid (125mB)
  └─ Filling → mushroom_cream_soup_bowl_cheese
     (pattern: potato_cream_soup_bowl_cheese_from_filling_cheese)

c:mushrooms × 2 + c:heavy_cream_bottle + c:butter + c:salt + c:cheeses
  └─ FD Cooking → mushroom_cream_soup_bowl_cheese
     (pattern: potato_cream_soup_bowl_cheese_from_cooking)

Note: tomato variant's FD Cooking differs — use c:tomato × 3 + c:heavy_cream_bottle + c:salt + c:cheeses (no butter)
```

---

## 🍈 Tier 3–4 — Melon Pie

### `melon_pie_filling` (fluid) · `melon_pie`

Every core Create Food fruit flavor has a pie: berry, chorus_fruit, glow_berry, apple. Melon is the only one absent. Each existing fruit pie is backed by its own filling fluid (butter + sugar × 2 + fruit × 3 → Mixing heated → filling fluid → Filling into raw pie crust). Melon pie is a genuine dish (honeydew/cantaloupe custard pie is a regional staple).

**New items**

| ID | Display Name | Type |
|----|-------------|------|
| `melon_pie_filling` | Melon Pie Filling | fluid |
| `melon_pie_filling_bucket` | Melon Pie Filling Bucket | — |
| `melon_pie` | Melon Pie | block_pie |
| `melon_pie_slice` | Slice of Melon Pie | — |

Fluid fields: `fluidFlowSlope: 3`, `fluidFlowDecrease: 2`. Slice nutrition/saturation consistent with existing fruit pie slices.

**Recipes**

```
── melon_pie_filling fluid ───────────────────────────────────────────

c:butter + c:sugar × 2 + c:melon × 3
  └─ Mixing (heated) → melon_pie_filling fluid (1000mB)
     (pattern: berry_pie_filling_fluid_from_mixing_heated)

c:butter + c:sugar × 2 + c:melon × 3
  └─ FD Cooking → melon_pie_filling_bucket
     (pattern: chorus_fruit_pie_filling_bucket_from_cooking — experience 0.25)

── raw_melon_pie ─────────────────────────────────────────────────────

c:raw_pie_crust + c:melon_pie_filling_bucket
  └─ Crafting (shapeless) → raw_melon_pie
     (pattern: raw_berry_pie_from_crafting)

c:raw_pie_crust + melon_pie_filling fluid (1000mB)
  └─ Filling → raw_melon_pie
     (pattern: raw_chorus_fruit_pie_from_filling_chorus_fruit_pie_filling)

c:raw_pie_crust + c:melon_pie_filling_bucket
  └─ Item Application → raw_melon_pie
     (pattern: raw_chorus_fruit_pie_from_item_application)

── melon_pie ─────────────────────────────────────────────────────────

createfood:raw_melon_pie
  └─ Smelting → melon_pie (experience 0.95, cookingtime 550)

createfood:raw_melon_pie
  └─ Smoking → melon_pie (experience 0.95, cookingtime 550)

createfood:raw_melon_pie
  └─ Campfire Cooking → melon_pie (experience 0.95, cookingtime 550)
```

---

## 🍮 Tier 2 — Chocolate Crème Brûlée

### `chocolate_custard_sugar_bottle` · `chocolate_creme_brulee_bottle`

`creme_brulee_bottle` is made from `custard_sugar_bottle` (plain custard + sugar) baked via Smelting/Smoking/Campfire. `chocolate_custard_bottle` already exists but has no sugar intermediate or baked form. Chocolate crème brûlée is a well-established restaurant dessert. The chain is a direct two-step substitution of the plain pattern.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `chocolate_custard_sugar_bottle` | Chocolate Custard | bottle | 4 | 0.7 | `sugar` |
| `chocolate_creme_brulee_bottle` | Chocolate Crème Brûlée | bottle | 5 | 0.9 | — |

**Recipes**

```
── chocolate_custard_sugar_bottle ────────────────────────────────────

c:chocolate_custard_bottle + c:sugar
  └─ Crafting (shapeless) → chocolate_custard_sugar_bottle
     (pattern: custard_sugar_bottle_from_crafting)

c:chocolate_custard_bottle + c:sugar
  └─ Deploying → chocolate_custard_sugar_bottle
     (pattern: custard_sugar_bottle_from_deploying)

── chocolate_creme_brulee_bottle ─────────────────────────────────────

createfood:chocolate_custard_sugar_bottle
  └─ Smelting → chocolate_creme_brulee_bottle
     (pattern: creme_brulee_bottle_from_smelting — cookingtime 100, experience 0.1)

createfood:chocolate_custard_sugar_bottle
  └─ Smoking → chocolate_creme_brulee_bottle
     (cookingtime 100, experience 0.1)

createfood:chocolate_custard_sugar_bottle
  └─ Campfire Cooking → chocolate_creme_brulee_bottle
     (cookingtime 100, experience 0.1)
```

---

## 🍯 Tier 1 — Honeyed Pastry & Chocolate Pastry

### `honeyed_pastry` · `honeyed_chocolate_pastry`

Every baked-good family with a chocolate-base variant has a honeyed form: `honeyed_donut` / `honeyed_chocolate_donut`, `honeyed_sweet_roll` / `honeyed_chocolate_sweet_roll`, `honeyed_cupcake` / `honeyed_chocolate_cupcake`. The pastry family has neither. Both use the identical Crafting (shapeless) + Filling (honey fluid 250mB) two-recipe pattern.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `honeyed_pastry` | Pastry | fastFood | 4 | 0.9 | `honey` |
| `honeyed_chocolate_pastry` | Chocolate Pastry | fastFood | 4 | 0.9 | `honey` |

**Recipes**

```
── honeyed_pastry ────────────────────────────────────────────────────

c:pastry_base + minecraft:honey_bottle
  └─ Crafting (shapeless) → honeyed_pastry
     (pattern: honeyed_sweet_roll_from_crafting)

honey fluid (250mB) + c:pastry_base
  └─ Filling → honeyed_pastry
     (pattern: honeyed_sweet_roll_from_filling_honey)

── honeyed_chocolate_pastry ─────────────────────────────────────────

c:chocolate_pastry_base + minecraft:honey_bottle
  └─ Crafting (shapeless) → honeyed_chocolate_pastry
     (pattern: honeyed_chocolate_cupcake_from_crafting)

honey fluid (250mB) + c:chocolate_pastry_base
  └─ Filling → honeyed_chocolate_pastry
     (pattern: honeyed_chocolate_cupcake_from_filling_honey)
```

---

## 🍳 Tier 2 — Shakshuka

### `shakshuka` (fluid) · `shakshuka_bowl`

`tomato_sauce` (FD item) is used in pasta plates, pizza dough, and nachos — always as a secondary topping. Shakshuka puts it in the starring role: eggs poached directly in seasoned tomato sauce. This is distinct from `scrambled_eggs_plate_tomato`, which uses raw egg fluid + fresh tomatoes on a plate. Shakshuka uses the pre-made `tomato_sauce` together with eggs, onion, and paprika in the FD Cooking Pot.

**New items**

| ID | Display Name | Type |
|----|-------------|------|
| `shakshuka` | Shakshuka | fluid |
| `shakshuka_bucket` | Shakshuka Bucket | — |
| `shakshuka_bowl` | Shakshuka | bowlFood |

Fluid fields: `fluidFlowSlope: 3`, `fluidFlowDecrease: 2`, `createBowl: true`.
Bowl nutrition 9, saturation 0.75. No tooltip keys needed — "Shakshuka" is a unique display name with no sibling items to disambiguate from.

**Recipes**

```
── shakshuka fluid ───────────────────────────────────────────────────

c:tomato_sauce fluid (250mB) + c:egg fluid (500mB) + c:onion + c:paprika
  └─ Create Mixing (heated) → shakshuka fluid (333mB)
     (pattern: mutton_stew_fluid_from_mixing_heated)

c:tomato × 2 + c:egg fluid (500mB) + c:onion + c:paprika
  └─ Create Mixing (heated) → shakshuka fluid (333mB)
     (alt: raw tomatoes instead of tomato_sauce — suffix: alt)

── shakshuka_bowl (FD cooking) ───────────────────────────────────────

farmersdelight:tomato_sauce + c:eggs × 2 + c:onion + c:paprika
  └─ FD Cooking (container: bowl) → shakshuka_bowl
     (pattern: pork_stew_bowl_from_cooking — experience 0.3)

c:tomato × 2 + c:eggs × 2 + c:onion + c:paprika
  └─ FD Cooking (container: bowl) → shakshuka_bowl
     (alt: raw tomatoes — suffix: alt — experience 0.3)

── shakshuka_bowl (mixing from fluid) ────────────────────────────────

shakshuka fluid (1000mB) + minecraft:bowl × 3
  └─ Create Mixing → shakshuka_bowl × 3
     (pattern: mutton_stew_bowl_from_mixing_mutton_stew)
```

---

## 🍝 Tier 2 — Pasta with Sausage

### `pasta_plate_sausage` · `pasta_plate_sausage_tomato_sauce`

`pasta_plate` accepts proteins via Deploy + Crafting; every other cooked meat protein has a pasta form. Sausage pasta is a staple Italian-American dish and the natural fit for `c:sausages` here. The tomato sauce variant follows the chain established by `pasta_plate_chicken_cut` → `pasta_plate_chicken_cut_tomato_sauce`.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `pasta_plate_sausage` | Pasta | food | 8 | 0.7 | `sausage` |
| `pasta_plate_sausage_tomato_sauce` | Pasta | food | 11 | 0.9 | `sausage`, `tomato_sauce` |

**Recipes**

```
── pasta_plate_sausage ───────────────────────────────────────────────

c:pasta_plate + c:sausages
  └─ Deploying → pasta_plate_sausage
     (pattern: pasta_plate_chicken_cut_from_deploying)

c:pasta_plate + c:sausages
  └─ Crafting (shapeless) → pasta_plate_sausage

── pasta_plate_sausage_tomato_sauce ──────────────────────────────────

c:pasta_plate_tomato_sauce + c:sausages
  └─ Deploying → pasta_plate_sausage_tomato_sauce

c:pasta_plate_sausage + farmersdelight:tomato_sauce
  └─ Deploying → pasta_plate_sausage_tomato_sauce
     (alt — suffix: alt)

c:pasta_plate_sausage + farmersdelight:tomato_sauce
  └─ Crafting (shapeless) → pasta_plate_sausage_tomato_sauce

c:pasta_plate_tomato_sauce + c:sausages
  └─ Crafting (shapeless) → pasta_plate_sausage_tomato_sauce
     (suffix: alt — pattern: pasta_plate_chicken_cut_tomato_sauce_from_crafting_alt)

c:tomato_sauce fluid (250mB) + c:pasta_plate_sausage
  └─ Filling → pasta_plate_sausage_tomato_sauce
     (pattern: pasta_plate_chicken_cut_tomato_sauce_from_filling_tomato_sauce)
```

---

## 🥚 Tier 2 — Scotch Egg

### `raw_scotch_egg` · `scotch_egg`

`boiled_egg_peeled` is currently used in only one recipe: `boiled_egg_peeled_salt`. `ground_sausage` similarly has no food use. A scotch egg wraps a peeled boiled egg in sausage meat, coats it in breadcrumbs, and deep-fries it. The raw form mirrors `raw_fishcake` (breadcrumbs + protein compacted); the cooked form follows the same Smelting/Smoking/Campfire bake chain.

**New items**

| ID | Display Name | Type | Nutrition | Saturation | Tooltip keys |
|----|-------------|------|-----------|------------|--------------|
| `raw_scotch_egg` | Raw Scotch Egg | plain | — | — | — |
| `scotch_egg` | Scotch Egg | food | 9 | 0.8 | — |

**Recipes**

```
── raw_scotch_egg ────────────────────────────────────────────────────

c:boiled_egg_peeled + c:ground_sausage + c:bread_crumbs + c:paprika
  └─ Crafting (shapeless) → raw_scotch_egg
     (pattern: raw_fishcake_from_crafting)

c:boiled_egg_peeled + c:ground_sausage + c:bread_crumbs + c:paprika
  └─ Compacting → raw_scotch_egg

── scotch_egg ────────────────────────────────────────────────────────

createfood:raw_scotch_egg
  └─ Smelting → scotch_egg (experience 0.35, cookingtime 200)

createfood:raw_scotch_egg
  └─ Smoking → scotch_egg (experience 0.35, cookingtime 100)

createfood:raw_scotch_egg
  └─ Campfire Cooking → scotch_egg (experience 0.35, cookingtime 600)

c:boiled_egg_peeled + c:ground_sausage + c:bread_crumbs + c:paprika
  └─ Compacting (heated) → scotch_egg
     (skips raw step; pattern: tater_tots_from_compacting_heated)
```

---
