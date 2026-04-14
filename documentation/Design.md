# Create Food — Design Reference

Items should integrate with the existing systems with the goal of coherence within established patterns, while maintaining the flexibility that makes items feel distinct and purposeful.

> **Toolkit note:** The Create: Food Toolkit (`.jsx`) generates registration code (`ModItems`, `ModBlocks`, `ModFluids`), lang entries, and recipe JSONs. All JSON asset files (item tags, item models, fluid block models, blockstates, loot tables, fluid tags) are now generated automatically by datagen on the next run — you do not need to create or manage these files manually. You only need to provide the texture PNGs.

---

## Table of Contents

1. [Nutrition Tiers](#1-nutrition-tiers)
2. [Item Types & Stack Rules](#2-item-types--stack-rules)
3. [Ingredient Additions](#3-ingredient-additions)
4. [Effects](#4-effects)
5. [Fast Property](#5-fast-property)
6. [Flavor Variants](#6-flavor-variants)
7. [Recipe Type Usage](#7-recipe-type-usage)
8. [Tags & Naming Conventions](#8-tags--naming-conventions)

---

## 1. Nutrition Tiers

### Tier 1 — Minimal (1–3 nutrition) · Components & Snacks

Building blocks and quick snacks. Wide saturation variance is acceptable.

| Nutrition | Examples |
|-----------|---------|
| 1 | Mushroom slices, donut base, ice cream sticks, glow berry slices (fast, Night Vision) |
| 2 | Bread slice, toast slice, ice cream cones, basic cake slices, boiled egg |
| 3 | Toast plate, cookies, pastries, donut variants, butterscotch/toffee/caramel toast |

**Saturation range:** 0.1 – 1.9 (high variance accepted at this tier)

> Filled pastries (butterscotch, toffee, caramel) register at nutrition 2–3 but carry saturation 1.1+ and Comfort effects. Their low nutrition reflects small serving size; high saturation reflects richness.

---

### Tier 2 — Light (4–6 nutrition) · Simple Foods

First level of "real food." Single-ingredient additions or simple preparations.

| Nutrition | Examples |
|-----------|---------|
| 4 | Donuts, muffins, basic chocolates, chicken nuggets |
| 5 | Single-topping bread, basic pizza slices, milkshakes, custard bottle (plain) |
| 6 | Bread with bacon/cheese, ice cream sandwiches, custard with sugar, chocolate milk |

**Saturation range:** 0.2 – 1.6

---

### Tier 3 — Moderate (7–9 nutrition) · Basic Meals

Standard satisfying meals. Mid-level food.

| Nutrition | Examples |
|-----------|---------|
| 7 | Single-topping buns, simple sandwiches, jam sandwiches, mashed potatoes bowl |
| 8 | Dual-topping buns, complex sandwiches, scrambled eggs with bacon, berry custard |
| 9 | Multi-topping combinations, non-rice burritos, chorus fruit custard, melon custard |

**Saturation range:** 0.2 – 1.6

---

### Tier 4 — Substantial (10–12 nutrition) · Full Meals

Hearty meals requiring multiple quality ingredients.

| Nutrition | Examples |
|-----------|---------|
| 10 | Cheeseburgers, fully-loaded buns, pork/rabbit tacos with lettuce & sauce, wraps, pumpkin custard |
| 11 | Multi-ingredient burritos, premium sandwiches, chicken burrito with rice |
| 12 | Complex burgers with 3+ toppings, stews, beef taco with lettuce, pork taco with lettuce & sauce |

**Saturation range:** 0.4 – 1.1

---

### Tier 5 — Premium (13+ nutrition) · Complex Meals

Endgame food. Requires significant ingredient investment.

| Nutrition | Examples |
|-----------|---------|
| 13 | Beef taco with lettuce & sauce, chicken taco with lettuce & sauce |
| 14+ | Beef burrito with rice |

**Saturation range:** 0.4 – 1.1

> A beef version of a dish is typically 1–2 nutrition higher than the rabbit equivalent at the same tier. Fish carries lighter saturation than other proteins of the same nutrition value.

---

## 2. Item Types & Stack Rules

> **Toolkit:** Set in the Registration section. Item Type, Stack Size, Converts To, and Remainder are all configurable fields. The toolkit outputs the correct helper call for `ModItems.java` automatically based on these settings.

### Helper Methods (ModItems.java)

The new registration system uses concise helper methods. The toolkit generates these directly — you add one line per item:

```java
// Non-food
plain("my_ingredient")
plain("my_ingredient", "tooltip.compat.mymod", "beef_ingredient")
plainCr("my_item", () -> Items.BOWL)
ingredientBottle("my_bottle")   // DrinkableItem, stack 16, glass bottle remainder
ingredientBowlItem("my_bowl")   // Item, stack 16, bowl remainder
pipingBag("my_bag")

// Food — trailing args accept tips(...) and fx(...) in any order
food("my_item", nut, sat)
fastFood("my_item", nut, sat)
consumable("my_item", nut, sat)
consumableFast("my_item", nut, sat)
bottle("my_bottle", nut, sat)           // DrinkableItem, stack 16, glass bottle
bowlFood("my_bowl", nut, sat)           // stack 16, bowl return
bowlConsumable("my_bowl", nut, sat)     // ConsumableItem, stack 16, bowl return
stickFood("my_item", nut, sat, crStick) // always fast, stick return
stickConsumable("my_item", nut, sat, crStick)

// Trailing arg examples
tips(null, "beef_ingredient", "lettuce_ingredient")
tips("tooltip.compat.mymod", "beef_ingredient")
fx(ModEffects.COMFORT, 1200)
fx(MobEffects.WATER_BREATHING, 600, 1)  // amplifier 1
```

### ConsumableItem (bowl/plate/complex foods)

- Stacks to **16** when serving-sized (bowls, plates)
- Returns `Items.BOWL` via `usingConvertsTo` for bowl-based items

### DrinkableItem (bottles)

- Stacks to **16**
- Returns `Items.GLASS_BOTTLE` via both `usingConvertsTo` and `craftRemainder`

### Plain Item

- Stacks to **64**
- Piping bags: stack to **2**, return `PIPING_BAG` via `craftRemainder`

### Summary

| Form | Helper | Stack | Returns |
|------|--------|-------|---------|
| Bottle | `bottle()` | 16 | Glass Bottle |
| Bowl / plate | `bowlConsumable()` | 16 | Bowl |
| Sandwich / bun / wrap | `food()` or `consumable()` | 64 | — |
| Piping bag | `pipingBag()` | 2 | Piping Bag |

### Block-Form Foods

> **Toolkit:** Select "block" registration type and choose the Block Type. The toolkit generates the `ModBlocks.java` and `ModItems.java` registration lines, lang entries, and cutting/combine-back recipes. Blockstates, fluid block models, and loot tables are covered by datagen.

| Method | Block type | Slices | Used for |
|--------|-----------|--------|---------|
| `registerCake` | `ModCakeBlock` | 7 | Cream cakes, cheesecakes |
| `registerCookedPie` | `ModPieBlock` | 4 | Fruit pies, cheesecakes |
| `registerRawPie` | `RawPieBlock` | — | Uncooked pies |
| `registerCookedPizza` | `PizzaBlock` | 4 | Cooked pizzas, waffles |
| `registerRawPizza` | `RawPizzaBlock` | — | Raw pizzas |

### Fluid Container Sizes

| Container | Volume |
|-----------|--------|
| Bottle | 250mB |
| Bowl | 333mB |
| Bucket | 1000mB |

### Registering a New Fluid

> **Toolkit:** Select "fluid" registration type. Flow slope/decrease, bottle item, and bottle food properties are all configurable. The toolkit generates the `ModFluids.java` registration line, lang entries, and optionally the bottle item registration in `ModItems.java`. Blockstates, block models, and fluid tags are all covered by datagen.

**Flow presets:**

| Pattern | Call | Used for |
|---------|------|---------|
| Default (thick, barely flows) | `.build()` | Frostings, ice creams, stews, pie fillings |
| Thick (slow flow) | `.flow(1, 4)` | Custards, meringue |
| Medium | `.flow(2, 4)` | Jams, milkshakes, taco sauce |
| Thin (runny) | `.flow(3, 2)` | Juices |

---

## 3. Ingredient Additions

### On Minimal Bases (plain bread, toast — 2–3 nutrition)

Any substantial addition: **+3 to +5 nutrition**

### On Medium Bases (buns, sandwiches — 6–9 nutrition)

| Ingredient type | Nutrition added |
|-----------------|----------------|
| Protein (beef, pork, chicken, fish) | +2 |
| Dairy (cheese, cream cheese) | +2 |
| Vegetable (lettuce, tomato, onion, beetroot) | +1 |

### On Large/Complex Bases (10+ nutrition)

Any addition: **+0 to +2 nutrition** (heavily diminished returns)

### Saturation

- **First addition:** +0.1 to +0.4 saturation
- **Each subsequent addition:** +0.0 to +0.2

Saturation at Tier 4–5 converges toward **0.6–0.9** regardless of ingredient count.

---

## 4. Effects

> **Toolkit:** Add effects in the Effects section. Effect, Duration, and Amplifier are labelled dropdowns/inputs. The toolkit generates the correct `fx(...)` call in the helper output.

Effect duration does **not** scale with nutrition values — it reflects the thematic weight of the food.

### Comfort

| Duration | Ticks | Foods |
|----------|-------|-------|
| Small | 300–600 | Small pastries, toffee sweet roll, small sweets |
| Standard | 1200 | Sweet rolls, cream cakes, chocolate-dipped items |
| Substantial | 3600 | Chocolate bars, filled pastries |
| Premium | 6000 | Hot chocolate, hot dark chocolate, hot white chocolate |

### Nourishment

| Duration | Ticks | Foods |
|----------|-------|-------|
| Standard | 1200 | Tacos with sauce, complex wraps |
| Substantial | 3600 | Crimson/warped fungus burgers, gyro items, stew bowls |

### Ingredient-Specific Effects

| Ingredient | Effect | Notes |
|------------|--------|-------|
| Kelp | Water Breathing (600–1200t) | Duration scales with dish complexity |
| Glow Berry | Night Vision (600t) + Glowing (600t) | On glow berry as primary topping |
| Glow Berry (raw) | Night Vision (100t) | Brief flash on bite-size items |
| Crimson Fungus | Fire Resistance (1200t) | Fungus-topped burgers |
| Warped Fungus | Slow Falling (1200t) | Fungus-topped burgers |
| Honey | Regeneration (300–600t) | On honeyed items |
| Meringue bowl | Regeneration (300t) | Egg-white confection |

> Secondary effects only on items with 5+ ingredients or clear thematic purpose. Do not stack 3+ effects without precedent.

---

## 5. Fast Property

> **Toolkit:** "Has fast property" toggle in the Properties section. Maps to `fastFood()` or `consumableFast()` helper.

### Use `.fast()` on

Bread slices, toast slices, cookies, small baked snacks, boiled eggs, candy, chocolate bars, snacks with nutrition ≤ 4, glow berry slices, apple slices, cotton candy, marshmallow on a stick.

### Do NOT use `.fast()` on

Burgers, sandwiches, wraps, complex assembled meals, plated items, bowl items, hot beverages, anything Tier 3+.

> Physical form > nutrition tier when deciding `.fast()`.

---

## 6. Flavor Variants

> **Toolkit:** Use the Variations section. Each variant gets its own ID (via ID pattern), display name, nutrition, saturation, and effects. The toolkit generates all registration, lang, fluid block model, loot table, and recipe files for all variants at once.

### Cosmetic Variants

Flavor provides cosmetic choice only — same nutrition and effects across all variants. Used when the flavor ingredient is a garnish or minor topping.

### Functional Variants

Distinct nutritional profiles or thematic effects. Used when:
1. The ingredient is **whole or primary** (jam, ice cream flavor, frosting base)
2. Different **nutritional profiles** apply
3. **Thematic effects** are ingredient-driven (glow berry, kelp)

> Chocolate variants: +0.1–0.2 sat vs equivalent fruit flavor. Chorus fruit: +1–2 nut vs berry/apple equivalents.

---

## 7. Recipe Type Usage

> **Toolkit:** All recipe types are available in the Recipes tab. Inputs support tag c:, item, and fluid|mB (Create only). The toolkit generates correct recipe JSON for each type.

### Vanilla — Crafting, Shaped

Dry combinations. Gelatin, cloth filter assembly, non-processed item construction.

### Vanilla — Smelting, Smoking, Campfire

Single raw ingredient + heat. Raw meat, raw baked bases, cookies, muffins.

### Farmer's Delight — Cutting

Purely divisional. Block foods → slices. Vegetables → shredded/diced. Meats → portions.

> Block slice cutting recipes are auto-generated by the toolkit when registering a block-type item.

### Farmer's Delight — Cooking Pot

Multi-ingredient cooked result. Soups, stews, custards, jams, confectionery, hot drinks, dairy.

### Create — Mixing (Unheated)

Cold blending. Doughs, batters, frostings, ice creams, juices, milkshakes.

### Create — Mixing (Heated)

Heated blending. Custards, pie fillings, chocolate fluids, confectionery, hot chocolate, dairy.

### Create — Compacting (Heated)

Cookies, waffles, meatballs, pita, taco shells, graham crackers, sausage.

### Create — Compacting (Unheated)

Setting candy/fudge/bars, raw shaped intermediates, separating eggs.

### Create — Deploying

Item onto item. Each topping = one recipe. Each intermediate is a registered item.

### Create — Pressing

Solid-to-solid, no basin. Chips, crumbs, shredded veg, ground meat, chips from bars.

### Create — Milling

Grinding to powder. Cacao, dried ingredients, crackers, cookies.

### Create — Filling

Loading fluid into container. Custard/juice bottles, piping bags, buckets.

> Bottle filling/emptying recipes are auto-generated by the toolkit when creating a fluid with a bottle item.

### Create — Emptying

Extracting fluid from container.

---

## 8. Tags & Naming Conventions

> **Toolkit:** All JSON asset files are now auto-generated by datagen — item tags (`ItemTagProvider`), item models (`ItemModelProvider`), fluid block models + blockstates (`BlockModelProvider`, `BlockStateProvider`), loot tables (`LootTableProvider`), and fluid tags (`FluidTagProvider`). No hand-written JSON files are needed. The toolkit only generates Java registration lines and lang entries. Texture PNGs must still be provided manually.

### ID Naming

ID in `snake_case`: base → primary flavour → additional toppings → container suffix. Raw prefix for uncooked forms. Use `berry` in IDs, "Sweet Berry" in display names.

### Compat Mods

| Compat key | Mod |
|-----------|-----|
| `tooltip.compat.peanut_butter` | Expanded Delight |
| `tooltip.compat.cinnamon` | Expanded Delight |
| `tooltip.compat.coffee` | Farmer's Respite |
| `tooltip.compat.corn` | Cultural Delights |
| `tooltip.compat.eggplant` | Cultural Delights |
| `tooltip.compat.dragon_meat` | End's Delight |
| `tooltip.compat.endermite_meat` | End's Delight |
| `tooltip.compat.strider_meat` | My Nether's Delight |
| `tooltip.compat.raw_flesh_cookie` | Fright's Delight |
| `tooltip.compat.ube` | Ube's Delight |
| `tooltip.compat.raw_ginger_cookie` | Ube's Delight |

### Tag Naming

- **Single-item tags:** Auto-generated by datagen — no hand-written files needed
- **Fluid tags:** Auto-generated by datagen for still + flowing variants
- **Collection tags:** `c:cheeses`, `c:mushrooms`, `c:fruits`, `c:vegetables` — hand-maintained
- **`_ingredient` suffix:** aggregate slot tags used in recipes — hand-maintained
- **`_compat` suffix:** thin mod-substitution wrapper tags

### Tooltip Keys

`tooltip.createfood.<key>_ingredient` in en_us.json as `" + Display Name"`. Use `tips(compat, "key1", "key2")` in `ModItems.java`. Compat key (first argument) renders blue above ingredient list.

### Display Block Registration

> **Toolkit:** The Display Block section in the Item tab auto-detects whether the ID matches an existing pattern. If it matches (pizza, slice, pie, sandwich, burger, etc.), no new code is needed. If it doesn't match, configure the display type(s) and the toolkit generates the `registerConfig`/`registerMultiConfig` call.

Items excluded from display blocks: `apple_slice`, `fish_sticks`, `mozzarella_sticks`, `cookie_crumbs`, `chorus_fruit_slice`, `caramel_apple_slice`, `waffle_cone`, `meat_pie_filling`, `dumpling_wrappers`, `pumpkin_pie_block`, `graham_cracker_chocolate_marshmallow`, `graham_cracker_chocolate`, `chocolate_graham_cracker_chocolate_ice_cream`.

### Recipe Conditions

> **Toolkit:** This condition is included automatically in all generated recipe files.

```json
"neoforge:conditions": [{"type": "createfood:enabled", "id": "item_id"}]
```