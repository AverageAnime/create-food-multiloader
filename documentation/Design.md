# Create Food — Design Reference

Items should integrate with the existing systems with the goal of coherence within established patterns, while maintaining the flexibility that makes items feel distinct and purposeful.

> **Toolkit note:** The Create: Food Toolkit (`.jsx`) generates registration code (`ModItems`, `ModBlocks`, `ModFluids`), lang entries, and recipe JSONs. All JSON asset files (item tags, item models, fluidBlock block models, blockstates, loot tables, fluidBlock tags) are now generated automatically by datagen on the next run — you do not need to create or manage these files manually. You only need to provide the texture PNGs.

---

## Table of Contents

1. [Nutrition Tiers](#1-nutrition-tiers)
2. [Item Types & Stack Rules](#2-item-types--stack-rules)
3. [Ingredient Additions](#3-ingredient-additions) — *computed*
4. [Effects](#4-effects) — *computed*
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

**Saturation:** any ladder rung. Concentrates may exceed it (see below).

> Filled pastries register at nutrition 2–3 but carry high saturation. Their low nutrition reflects a small serving; the saturation reflects richness.

---

### Tier 2 — Light (4–6 nutrition) · Simple Foods

First level of "real food." Single-ingredient additions or simple preparations.

| Nutrition | Examples |
|-----------|---------|
| 4 | Donuts, muffins, basic chocolates, chicken nuggets |
| 5 | Single-topping bread, basic pizza slices, milkshakes, custard bottle (plain) |
| 6 | Bread with bacon/cheese, ice cream sandwiches, custard with sugar, chocolate milk |

**Saturation:** any ladder rung.

---

### Tier 3 — Moderate (7–9 nutrition) · Basic Meals

Standard satisfying meals. Mid-level food.

| Nutrition | Examples |
|-----------|---------|
| 7 | Single-topping buns, simple sandwiches, jam sandwiches, mashed potatoes bowl |
| 8 | Dual-topping buns, complex sandwiches, scrambled eggs with bacon, berry custard |
| 9 | Multi-topping combinations, non-rice burritos, chorus fruit custard, melon custard |

**Saturation:** any ladder rung.

---

### Tier 4 — Substantial (10–12 nutrition) · Full Meals

Hearty meals requiring multiple quality ingredients.

| Nutrition | Examples |
|-----------|---------|
| 10 | Cheeseburgers, fully-loaded buns, pork/rabbit tacos with lettuce & sauce, wraps, pumpkin custard |
| 11 | Multi-ingredient burritos, premium sandwiches, chicken burrito with rice |
| 12 | Complex burgers with 3+ toppings, stews, beef taco with lettuce, pork taco with lettuce & sauce |

**Saturation:** any ladder rung, subject to the effective cap.

---

### Tier 5 — Premium (13+ nutrition) · Complex Meals

Endgame food. Requires significant ingredient investment.

| Nutrition | Examples |
|-----------|---------|
| 13 | Beef taco with lettuce & sauce, chicken taco with lettuce & sauce |
| 14+ | Beef burrito with rice |

**Saturation:** any ladder rung, subject to the effective cap.

> A beef version of a dish is typically 1–2 nutrition higher than the rabbit equivalent at the same tier. Fish carries lighter saturation than other proteins of the same nutrition value.

---

### Saturation ladder

Saturation is quantised to a ladder, at 0.1 intervals:

```
0.1  0.2  0.3  0.4  0.5  0.6  0.7  0.8  0.9  1.0  1.1  1.2
```

Vanilla names six of these (`FoodConstants`: POOR 0.1, LOW 0.3, NORMAL 0.6,
GOOD 0.8, MAX 1.0, SUPERNATURAL 1.2) and the ladder was originally just those.
It is filled in between them because **nutrition is an integer stat**: a
quarter of the distinctions between ingredients are worth well under one hunger
point and vanish when rounded. Saturation is a float and carries that
remainder — an item sitting above its rounded value gains a rung, below it
loses one.

**1.2 is the ceiling.** That is the golden apple; nothing should beat it per
point of hunger.

**Effective saturation is capped at 20.** `FoodData.eat` clamps saturation to
the post-eat food level, so `nutrition x modifier x 2` above 20 is unreachable
in any game state — it is simply wasted. 30 entries used to exceed it.

### Concentrates

Families whose base nutrition is ≤ 2 may exceed the ladder, up to **2.5**,
capped at 10 effective saturation. This is the "tiny portion, dense energy"
archetype — cotton candy, breakfast bars, blackstrap molasses — kept as a rule
rather than three accidents. The `nutrition ≤ 2` gate makes it un-abusable.

### Form bounds

What an item physically is bounds what it may be worth, whatever the formula
computes:

| Form | Nutrition |
|------|-----------|
| `stickFood`, `fastFood` | ≤ 6 |
| `bottle` | ≤ 8 |
| `bowlFood`, `bowlFoodCr` | ≥ 4 |

A family that falls below its floor is **shifted** as a whole rather than
clamped per item — clamping turned all eleven ice cream bowls into exactly 4,
which is not a balance decision, it is a loss of information.

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
ingredientBowl("my_bowl")       // Item, stack 16, bowl remainder
pipingBag("my_bag")

// Food — trailing args accept tips(...) and fx(...) in any order
food("my_item", nut, sat)
fastFood("my_item", nut, sat)
bottle("my_bottle", nut, sat)           // DrinkableItem, stack 16, glass bottle
bowlFood("my_bowl", nut, sat)           // stack 16, no remainder
bowlFoodCr("my_bowl", nut, sat)         // stack 16, bowl craftRemainder
stickFood("my_item", nut, sat, crStick) // always fast, stick return
stickFoodCr("my_item", nut, sat, crStick) // always fast, stick craftRemainder

// Trailing arg examples
tips(null, "beef_ingredient", "lettuce_ingredient")
tips("tooltip.compat.mymod", "beef_ingredient")
fx(ModEffects.NOURISHMENT, 1200)
fx(MobEffects.WATER_BREATHING, 600, 1)       // amplifier 1
fx(ModEffects.NOURISHMENT, 1200, 0, 0.5f)    // 50% chance
```

### Bowl foods

- `bowlFood()` — stacks to **16**, no remainder
- `bowlFoodCr()` — stacks to **16**, returns `Items.BOWL` via `craftRemainder`

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
| Bowl (no return) | `bowlFood()` | 16 | — |
| Bowl (with return) | `bowlFoodCr()` | 16 | Bowl |
| Sandwich / bun / wrap | `food()` | 64 | — |
| Piping bag | `pipingBag()` | 2 | Piping Bag |

### Block-Form Foods

> **Toolkit:** Select "block" registration type and choose the Block Type. The toolkit generates the `ModBlocks.java` and `ModItems.java` registration lines, lang entries, and cutting/combine-back recipes. Blockstates, fluidBlock block models, and loot tables are covered by datagen.

| Method | Block type | Slices | Used for |
|--------|-----------|--------|---------|
| `registerCake` | `ModCakeBlock` | 7 | Cream cakes, cheesecakes |
| `registerCookedPie` | `ModPieBlock` | 4 | Fruit pies, cheesecakes |
| `registerRawPie` | `RawPieBlock` | — | Uncooked pies |
| `registerCookedPizza` | `PizzaBlock` | 4 | Cooked pizzas, waffles |
| `registerRawPizza` | `RawPizzaBlock` | — | Raw pizzas |

### Fluid Amounts

Every amount divides 1000 evenly, so any container converts to any other without rounding. The
canonical list lives in `FluidAmounts.java`; `Toolkit.jsx` mirrors it as `AMOUNTS`. Keep the three
in step — recipe JSON is data and is not validated against the constants at load time.

| Name            | Volume | Per bucket | Used for                                                   |
|-----------------|--------|------------|------------------------------------------------------------|
| `BUCKET`        | 1000mB | 1          | Bucket, fluid block, full-batch mixing output              |
| `BATCH`         | 750mB  | —          | Multi-serving mixing output                                |
| `LARGE_SERVING` | 500mB  | 2          | Concentrated bottles, cake, piping bag, fudge/toffee block |
| `SERVING`       | 250mB  | 4          | Bowl, standard bottle, cupcake, donut, sauce portion       |
| `SMALL_SERVING` | 125mB  | 8          | Bread slice, toast, hollow chocolate, spread, frying oil, ice cream stick, waffle cone |

**Bottles come in two sizes.** Most hold 250mB; concentrates hold 500mB. The size is declared per
fluid in the toolkit (`bottleSize`), which derives the bucket crafting ratio from it — 4 bottles
per bucket at 250mB, 2 at 500mB. The 500mB fluids are the jams (`apple`, `berry`, `chorus_fruit`,
`glow_berry`, `melon`), the milkshakes (plain, `apple`, `berry`, `chocolate`, `chorus_fruit`,
`glow_berry`, `melon`), `molasses` and `blackstrap_molasses`.

Bowls are a single 250mB serving — the same volume as a standard bottle. A bowl is distinguished
by being a *meal* (higher nutrition), not by holding more.

**The compat overrides enforce this too.** Delightful Creators ships its Create integration at 333mB — a
third of a bucket, which does not divide 1000 and so breaks the property the ladder rests on. The
override files under `data/delightfulcreators/` and `data/culturaldelights/` normalise every one of them
to 250mB, and where it also ships an emptying recipe that is overridden to match, so no fluid can be
filled at one rate and drained at another.

### Registering a New Fluid

> **Toolkit:** Select "fluidBlock" registration type. Flow slope/decrease, bottle item, and bottle food properties are all configurable. The toolkit generates the `ModFluids.java` registration line, lang entries, and optionally the bottle item registration in `ModItems.java`. Blockstates, block models, and fluidBlock tags are all covered by datagen.

**Flow presets:**

| Pattern | Call | Used for |
|---------|------|---------|
| Default (thick, barely flows) | `.build()` | Frostings, ice creams, stews, pie fillings |
| Thick (slow flow) | `.flow(1, 4)` | Custards, meringue |
| Medium | `.flow(2, 4)` | Jams, milkshakes, taco sauce |
| Thin (runny) | `.flow(3, 2)` | Juices |

---

## 3. Ingredient Additions

> **These numbers are computed, not chosen.** `cfood.py balance` fits a delta per
> ingredient against the whole registry and rewrites the values in place. Edit
> the rules, not the entries: a hand-edited value is overwritten on the next
> `--apply` unless it is pinned in `overrides.json` with a reason.

```
nut = base(family) + sum(delta(ingredient) x taper x scale) + complexity
```

### Per-ingredient deltas

Each ingredient carries its own value, fitted by ridge regression against the
existing registry, with the classes below used only as priors so a rare
ingredient falls back to its class rather than absorbing whatever its two items
happened to be worth. Representative fitted values:

| Ingredient | Delta | Ingredient | Delta |
|------------|-------|------------|-------|
| chicken | +2.7 | tomato | +1.3 |
| sausage | +2.4 | chorus fruit | +1.2 |
| beef | +2.4 | mushroom | +1.0 |
| bacon | +2.2 | honey | +0.9 |
| cheese | +1.8 | melon | +0.7 |
| fish | +1.7 | chocolate | +0.6 |
| onion | +1.6 | berry | +0.5 |
| lettuce | +1.5 | apple | +0.2 |

Class priors: sauce +3, protein +2, dairy/fat +2, spread +1, produce +1,
garnish 0.

### Taper and scale

The largest addition counts in full; later ones taper by **1.00 / 0.60 / 0.40 /
0.25**. Each is then scaled by what it is being added to, following the running
total rather than the family base — the first rasher of bacon on a bread slice
is most of the meal, the third topping on the burger it has become is a
garnish:

| Running total | Scale |
|---------------|-------|
| ≤ 3 (minimal: plain bread, bare bun) | 1.4 |
| 4–9 (medium: buns, sandwiches) | 1.0 |
| 10+ (large: already a full meal) | 0.6 |

### Recipe complexity

Every food is produced by at least one recipe, and the crafting graph over both
recipe trees supplies a small cost term — `0.20 x depth + 0.06 x raw`, where
depth is processing steps along the cheapest route and raw is the count of
transitive raw inputs. It is deliberately small; its job is to separate the
~250 items that declare no ingredients at all, not to drive values.

> Fitting found that recipe **depth** predicts almost nothing on its own. It is
> recipe **breadth** — how many distinct raw inputs a dish consumes — that
> tracks how the mod was actually valued.

### Ordering rules

- **Monotonicity.** Within a family, an item whose ingredients are a subset of
  another's may never out-value it.
- **Upgrades.** A refinement never ranks below its base — `apple_ice_cream`
  against `ice_cream`. Refinements are derived from the names: the qualifier
  must itself be an ingredient, so `glow_berry` is *not* a refined `berry` and
  `chorus_fruit` is *not* a refined `fruit`; they are separate crops.
- **Variants.** Declared groups rank strictly. Currently plain < white < dark
  chocolate, enforced on *effective* saturation.

### Saturation

Ordering is enforced on **effective saturation** (`nutrition x modifier x 2`),
which is what the player actually receives. Note that trading one nutrition for
one saturation rung makes an item worse on both axes unless
`nutrition > 10 x modifier + 1`, which for ordinary food never holds — so a
better variant keeps its nutrition and gains saturation.


## 4. Effects

> **These are computed too.** `cfood.py balance apply effects` rewrites every
> `fx(...)` list from the rules below. The full ingredient table is published
> in `documentation/wiki/FoodEffectsGuide.md` for players.

Effect duration scales with **tier**, which follows nutrition: T1 is 1–3, T2 is
4–6, T3 is 7–9, T4 is 10–12, T5 is 13+.

### Duration ladder

```
600 · 900 · 1200 · 1800 · 2400 · 3000 · 3600 · 4800
```

900 exists because raising the floor flattened everything onto it — 70% of all
effects sat at exactly 600, which is a step rather than a ladder. Tier 3 now
separates from tiers 1–2. Above a floor you can only move up, so the tier 5
signature was trimmed from 6000 to pay for most of it.

**The floor is 600 ticks — 30 seconds.** The ladder used to start at 300, and
15 seconds is over almost before the eating animation finishes: long enough to
appear in the HUD, never long enough to matter. Only the floor moved when this
was corrected — raising the upper tiers to match doubled total effect time
across the mod.

| | T1 | T2 | T3 | T4 | T5 |
|---|---|---|---|---|---|
| Base, savoury | — | 600 | 1200 | 2400 | 3600 |
| Base, sweet / drink / frozen | — | 600 | 900 | 1800 | 3000 |
| Ingredient and themed | 600 | 600 | 900 | 1200 | 1800 |
| Tier 5 signature | | | | | `feast`, 4800 savoury / 3600 sweet |

### Base effect

**Nourishment**, on every family that declares one. Comfort no longer exists —
Farmer's Delight folded it into Nourishment 1:1 — so sweet and savoury are told
apart by duration and by their ingredient effects, not by a second base effect.
Components and garnishes (bacon bits, sliced veg, chips, crusts) get none.

Bowls and plates additionally carry **Satiation** from T3.

### Per-tier caps

An item reliably delivers **tier − 1** effects.

| Tier | Guaranteed |
|------|------------|
| 1 | 0 |
| 2 | 1 |
| 3 | 2 |
| 4 | 3 |
| 5 | 4 |

The base effect and Satiation count against it; themed effects do not, since a
theme is the dish's identity rather than something it merely contains.

Nothing past the cap is discarded — it is chance-gated instead (see below), so
the cap governs *reliability*, not presence. Effects are kept in priority
order: base first, then ingredients by delta, so what a dish guarantees is what
matters most about it.

### Amplifier

Tier 4 and 5 dishes make an effect **stronger**, not merely longer — but only
where the amplifier demonstrably does something. Attribute effects scale as
`amount × (amplifier + 1)`, Regeneration ticks at `50 >> amplifier`, and
Saturation restores `amplifier + 1`. Those are amplifiable:

`movement_speed` · `dig_speed` · `damage_boost` · `health_boost` ·
`damage_resistance` · `luck` · `jump` · `absorption` · `regeneration` ·
`saturation`

Everything else is binary. Fire Resistance, Water Breathing, Night Vision,
Glowing, Slow Falling, Invisibility and Dolphin's Grace ignore the amplifier
completely, so raising it only produces a tooltip that lies.

> **Compat categories are never amplified.** Farmer's Delight's
> `NourishmentEffect.applyEffectTick(entity, amplifier)` does not read the
> amplifier parameter at all, so "Nourishment II" behaved exactly like
> Nourishment I — while its vanilla fallback (`minecraft:saturation`) *does*
> scale, which made an amplified tier 5 meal strictly better for players
> *without* Farmer's Delight than with it. The other categories cannot be
> verified from here, so none of them are amplified.

### Chance

Chance turns a truncated effect into a rarer one instead of no effect at all:

- **Anything past the tier cap** appears at 50%, then 35%, then 25%, and 20%
  thereafter. A loaded dish still tastes of everything in it, just not every
  time.
- **Tier 1 flavour effects** are a 50% chance of a real duration, rather than a
  guaranteed token one. Since tier 1 guarantees nothing, a snack's entire
  character is a gamble — which is the point of a snack.

The tooltip renders the percentage, so the odds are always visible.

### Ingredient effects

Assigned from ingredients, with a minimum tier per ingredient so a snack does
not carry a meal's buff. Where a vanilla effect is the exact analogue it is
preferred, so the ingredient works in every install:

| Ingredient | Effect |
|------------|--------|
| Kelp, fish | Water Breathing |
| Salmon | Dolphin's Grace |
| Squid ink | Invisibility |
| Glow berry | Night Vision (+ Glowing at T4) |
| Carrot | Night Vision |
| Chorus fruit, warped fungus | Slow Falling |
| Crimson fungus | Fire Resistance |
| Honey, melon | Regeneration |
| Beef | Strength |
| Pork | Health Boost |
| Rabbit | Speed |
| Pumpkin | Luck |
| Coffee | Haste |

The rest map to compat categories — `raging` for bacon, `pacify` for onion,
`refreshing` for lettuce, `fortune` for mushroom, `preservation` for salt,
`rest` for marshmallow, and so on. See the wiki table for all 56.

### Vanilla fallbacks

26 categories carry a final `.or("minecraft", ...)` candidate, so a category
resolves to a sane vanilla effect when the mod providing it is absent. This is
deliberately narrow — the ingredient categories plus `nourishment` — and not a
blanket remap of all 67. Without it only 7% of effect time reached a player
with no compat mods; with it, 97%.

`nourishment` falls back to `minecraft:saturation`, which is *instantaneous*
(it calls `FoodData.eat` once), so the long durations at its call sites cannot
make it strong. `satiation` deliberately has none: every item carrying it also
carries `nourishment`, and both resolving to the same instant effect would
apply it twice.

> **Registry ids are not constant names.** `MobEffects.DIG_SPEED` registers as
> `minecraft:haste`, `DAMAGE_BOOST` as `strength`, `JUMP` as `jump_boost`. A
> wrong id fails silently — the effect simply never fires — so
> `gradlew :common:verifyFallbacks` asserts every one resolves.


## 5. Fast Property

> **Toolkit:** "Has fast property" toggle in the Properties section. Maps to `fastFood()` or `consumableFast()` helper.

### Use `.fast()` on

Bread slices, toast slices, cookies, small baked snacks, boiled eggs, candy, chocolate bars, snacks with nutrition ≤ 4, glow berry slices, apple slices, cotton candy, marshmallow on a stick.

### Do NOT use `.fast()` on

Burgers, sandwiches, wraps, complex assembled meals, plated items, bowl items, hot beverages, anything Tier 3+.

> Physical form > nutrition tier when deciding `.fast()`.

---

## 6. Flavor Variants

> **Toolkit:** Use the Variations section. Each variant gets its own ID (via ID pattern), display name, nutrition, saturation, and effects. The toolkit generates all registration, lang, fluidBlock block model, loot table, and recipe files for all variants at once.

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

> **Toolkit:** All recipe types are available in the Recipes tab. Inputs support tag c:, item, and fluidBlock|mB (Create only). The toolkit generates correct recipe JSON for each type.

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

> **Do not mill a bare vanilla item.** The millstone is the most contested machine in the Create
> ecosystem, and the first matching recipe wins — milling `minecraft:beetroot` used to shadow Create's own
> `beetroot → red dye + seeds`. Every milling recipe here has a `create:pressing` twin, so press the
> vanilla item instead and leave the millstone to tags we own. Where both mods genuinely want the same
> input, override theirs to carry both outputs, as `data/create/recipe/milling/beetroot.json` does.

### Create — Filling

Loading fluidBlock into container. Custard/juice bottles, piping bags, buckets.

> Bottle filling/emptying recipes are auto-generated by the toolkit when creating a fluidBlock with a bottle item.

### Create — Emptying

Extracting fluidBlock from container.

---

## 8. Tags & Naming Conventions

> **Toolkit:** All JSON asset files are now auto-generated by datagen — item tags (`ItemTagProvider`), item models (`ItemModelProvider`), fluidBlock block models + blockstates (`BlockModelProvider`, `BlockStateProvider`), loot tables (`LootTableProvider`), and fluidBlock tags (`FluidTagProvider`). No hand-written JSON files are needed. The toolkit only generates Java registration lines and lang entries. Texture PNGs must still be provided manually.

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

### Recipe Shadowing Across Mods

The crafting grid, Farmer's Delight's cutting board and cooking pot, the vanilla furnace/smoker/campfire,
Create's basin, press, millstone and spout, and every addon workstation resolve an input by
`getRecipeFor(...)` — **first match wins, and the rest are dead**, in an order that is arbitrary but
stable per install. So a recipe of ours whose ingredient tag is a *superset* of what a compat mod's own
recipe names will match everything theirs does, and can delete their product silently.

Before changing anything, ask what is actually lost. Usually nothing: the foreign product has another
route, or a Create: Food item does its job. Only when a chain dies is there a bug — and note that
**adding the foreign item to a `c:` tag is bookkeeping, not a fix**; it records an equivalence but cannot
make an item obtainable while its recipe is still shadowed.

When a chain does die, the goal decides the tool: **every mod we claim compat with should follow Create:
Food's design**, so override their recipe onto our chain rather than yielding to it, narrowing our own
tags, or deleting our own recipes. Worked examples:

- `data/ratatouille/recipe/mixing/nibs_to_liquor.json` — widened to `c:cacao_nibs` so either mod's nibs
  feed Ratatouille's cocoa chain, which our cacao nib recipes had severed at its first step.
- `data/ends_delight/recipe/food/chorus_fruit_grain.json` — moved downstream onto our chorus fruit slice.

Narrowing one of our tags is the fallback, and only where a compat mod's **raw** ingredient sits in a tag
we consume on a machine that mod also uses for it — `c:bell_pepper` drops the whole-pepper crop tags for
exactly this reason. Never gate one of our recipes off because another mod is installed without first
checking our own product has a second route.

The cutting board also matches on **tool**, so two recipes sharing an input coexist when they need
different tools; that is why `wheat_dough_small_from_cutting` takes an axe.

### Overriding a compat mod's recipe

Substituting a *smaller* portion for a larger one is the point, not a fault. `c:onion` holds the
whole onion beside `sliced_onion` and `diced_onion` so that a consuming recipe takes any of them:
spending a processing step to get more usable portions out of one crop is the reward the design is
built around, and a beef patty standing in for a steak is the same trade. What must never happen is
the reverse — a portion becoming a whole unit — and that is a property of the recipe, not of the tag.

So the tag a slot gets depends on which direction the recipe runs. A recipe that *consumes* an
ingredient takes the wide substitution tag. A recipe that *produces* the smaller form, or converts
one input into one canonical unit, takes the narrow crop tag: `c:crops/onion`, never `c:onion`. Our
own tree follows this exactly — `diced_onion_from_milling`, `_from_pressing`, `_from_cutting`,
`_from_crafting` and `_from_millstone` all take `c:crops/onion`, and the lone exception,
`diced_onion_from_cutting`, takes `c:sliced_onion` because it goes one step down rather than looping.

Five failure modes account for nearly every override that has to be rejected, and all five are
invisible in a diff:

- **The recipe's own output is in the tag.** `cutting/tomato_slices_cut` cuts a tomato into slices,
  and `c:tomato` holds `createfood:sliced_tomato` — widening it feeds the recipe its own product.
  Every `cutting/*`, `cutting_board/*` and `*_crate` recipe fails this, as does milling sugar into
  powdered sugar when `c:sugar` holds `powdered_sugar`. This one is mechanical: refuse any
  substitution whose tag intersects the recipe's results.
- **Widening lets their recipe eat our item.** Ratatouille's `smelting/sausage` takes
  `ratatouille:raw_sausage`; widening it to `c:raw_sausages` makes it match ours too, and — first
  match wins — our raw sausage smelts into *their* cooked sausage. Re-route their **inputs** onto
  our chain; never widen their recipe onto our **outputs**.
- **The widened recipe becomes a duplicate of one of ours.** A single-input machine keeps one recipe
  per input, so if the override ends up with the same tag we already consume on that machine,
  exactly one of the two survives and which one is arbitrary per install. Hearth and Harvest cutting
  a cheese wheel on `c:cheese_block`, and Rustic Delight smelting `c:sliced_potato`, both hit this.
- **A portion becoming a whole unit.** Only bites when the recipe turns one input into one output of
  the same kind — smelting, haunting, crate packing. `c:beetroot` holds sliced and shredded beetroot
  beside the whole root, so widening `beetroot_crate` there packs nine slices into a crate that
  unpacks to nine whole beetroots, and widening `haunting/poisonous_potato` turns a potato slice into
  a whole poisonous potato. In a dish this is harmless, because the output is not a portion of the
  input; a soup does not care whether the onion arrived whole or diced. Test it as: one consumable
  input slot, and a tag holding both a whole item and a portion of one.
- **The tag is real but colour- or species-blind.** Rustic Delight ships nine bell pepper rolls, one
  per pepper colour, and every tag holding `bell_pepper_slice_black` — `c:bell_pepper`,
  `c:foods/bell_pepper`, `c:pepper`, `c:foods/vegetable` — holds all nine colours, so the widened
  recipes collapse into one and eight of the nine rolls become unobtainable. Hearth and Harvest's
  `c:milk_bottle` does the same across species: it holds goat milk beside cow milk, so a goat cheese
  wheel widened onto it is made from cow milk and collides with the cheddar wheel. When the result
  carries a distinction no available tag preserves, the literal item stays, and `cfood overrides
  parity` reports the slot instead of it being converted. The check is `cfood overrides collisions`:
  a pair it does *not* mark `both sit in <tag>` is a real collapse.

Two further constraints on the *form* an override may take, both of which silently cost a Fabric
player the whole recipe:

- **A hosted override must use ingredient forms both loaders read.** The jars in `neoforge/run/mods`
  are NeoForge builds, so copying a recipe out of one carries `neoforge:compound`,
  `neoforge:difference`, `neoforge:components`, `neoforge:single`, `neoforge:tag` and
  `neoforge:block_tag` with it. None of those parse on Fabric, and because our file shadows the mod's
  by id, the dish becomes unobtainable there rather than falling back. The portable spellings are:
  a bare JSON array for "any of these" (Farmer's Delight, Hearth and Harvest and Veggies Delight all
  ship arrays, which is what makes it safe), `{"item": …, "components": …}` for a potion,
  Create's `{"type": "fluid_stack", "amount": …, "fluid": …}` and
  `{"type": "fluid_tag", "amount": …, "fluid_tag": …}` for fluids. A block tag has no item-tag
  counterpart and must be spelled out as its members.
- **Widen a narrow tag only to the flat tag with the same leaf.** `c:crops/tomato` → `c:tomato` is
  right; `c:foods/raw_beef` → `c:dumpling_ingredients` and `c:foods/leafy_green` →
  `c:salad_ingredients` are the same aggregate trap as the fifth mode above, and a "smallest
  superset" search finds them first. Require the leaf to match, the flat tag to be a strict
  superset, and the four earlier tests to pass.

Two further rules about what a recipe may reference:

- **Do not host a foreign recipe that names a tag nothing declares.** Veggies Delight's Corn Delight
  compat wants `c:foods/vegetables` and `c:foods/tortilla`; nothing populates either, so those
  recipes cannot fire and the override buys nothing while permanently blunting `cfood audit`.

  Establish that a tag really is dead before dropping anything, because two things fake it. `cfood
  audit` is repo-scoped and reads a Create fluid slot (`{"type": "neoforge:tag", "amount": …}`) as an
  item tag, so it reports `c:honey` as dead when Create and Brewin' & Chewin' both fill it as a
  *fluid* tag. And **NeoForge ships the conventional tags itself**, in the loader artifact rather
  than in `run/mods`: `c:crops/beetroot`, `c:bones` and `c:concretes` are all populated from there,
  so a check that reads only the mod jars condemns them wrongly. Ratatouille's `honey_cake`,
  Ratatouille Fried Delights' `tumbling/pasta_42` and Ube's Delight's `bulalo` are all fine.
- **Where the tag is foreign but real, join it instead.** Hearth and Harvest owns `c:flours` and
  Expanded Delight owns `c:jams/sweet_berry`. Adding our `corn_flour` and `berry_jam_bottle` to
  those tags is what makes their recipes accept our items, and it is also what keeps `cfood audit`
  able to tell a genuinely unpopulated tag from one a dependency fills out of our sight.

### Retagging is not the whole job — the bill is

Three passes over this surface each stopped at substituting tags, and each looked finished while
most recipes were still wrong. Swapping `c:foods/bread` for `c:bread` does not make Farmer's
Delight's bacon sandwich a Create: Food sandwich; **ours is two bread slices around a filling, and
theirs was one loaf**. The tag is the smaller half. What follows is the larger half, and every rule
in it was learned by getting it wrong first.

**The standard is whatever `data/createfood/recipe/**` already does.** Not a rule invented for the
override, and not the other mod's own recipe. Before changing a slot, find the recipe of ours that
makes the same kind of thing and read the answer off it. Learning the standard from our own tree is
also what makes it checkable: for every ingredient, the tag our recipes use for it; for every
family, the bill our recipes give it.

- **Composition.** A sandwich takes `c:bread_slice` twice, a burger `c:bun` twice, a wrap
  `c:taco_shell_ingredient`, a toast one `c:toast`. A single bread slot in a foreign sandwich is a
  defect, not a cheaper variant.
- **Quantity per serving.** A juice is **one fruit and one sugar per 250mB**, so a bottle takes one
  and a 500mB compacting output takes two. Farmer's Delight's melon juice asked for four melons for
  one bottle. Scale to the yield; do not copy the count the other mod chose.
- **No ingredient standing beside its own processed form.** A pie crust already is the flour, so a
  recipe naming both a crust and raw wheat charges twice for the same thing. Seven pies did.
- **Ranking a tag by how often we use it picks the wrong one.** Frequency says melon slices are
  `c:fruits` and bell pepper slices are `c:pepper`, because our fruit recipes reference the
  aggregates constantly. Rank by *specificity* — leaf name matching the item first, then fewest
  members — and exclude the aggregates outright.
- **A foreign tag our recipes never use is almost always wrong.** The test is not "do we reference
  this tag" but **"do we ship this ingredient"**. Hearth and Harvest's `c:batter_bowl` looked
  legitimate because no recipe of ours names it — but we ship muffin batter and cake batter, so
  their generic batter must become ours. `c:avocado` is the genuine exception: we ship no avocado,
  so there is nothing to convert it to. Reaching for "we don't use that tag" as grounds to leave a
  slot alone is the single easiest way to do none of the work while appearing to.

**Vanilla literals count.** A check that only looks at foreign item ids passes `minecraft:wheat`,
`minecraft:egg` and `minecraft:bowl` straight through, and those are as wrong as
`farmersdelight:beef_patty` when our own recipes say `c:eggs`. `cfood overrides parity` reads only
foreign literals, so it cannot see this class at all — it is not a sufficient check on its own.

**A one-step recipe that reaches an item our chain builds in stages is a bypass, whatever it is
made of.** The test is mechanical: if every one of our routes to an item passes through an
intermediate (`c:raw_*`, `*_base`, `*_batter`, `*_filling`, `*_dough`) and theirs does not, theirs
skips a step. That found apple pie, pumpkin pie four times over, chocolate pie, cheesecake, pie
crust twice and both sweet rolls. Confirm the intermediate is obtainable before retiring anything —
a retirement that strands an item is worse than the bypass it removed.

**Retired recipes are brought up to standard too.** `neoforge:never` makes the body inert, but the
file is what the next reader sees, so it carries the same bill as an active override would. A
retired recipe being byte-identical to one of ours is fine and expected —
`data/farmersdelight/recipe/hamburger.json` now holds exactly our flat hamburger bill.

**Rewrite first, then retire.** These are one action, not two options. A foreign recipe is brought
onto Create: Food's bill *and then* made inert with `neoforge:never`, because our own routes already
make the item — the rewrite is what the file records, the retirement is what stops the mod's cheaper
original standing beside our chain. Retiring a recipe while leaving its original body is half the
job, and it is the failure that has recurred most in this work: it looks finished in `git status`
and reads as untouched to the next person who opens the file.

The rewritten body is simply **our recipe for that item** — machine included, where we have one.
`data/farmersdelight/recipe/apple_pie.json` holds `c:raw_apple_pie` baked, not its original crust +
apples + sugar; `data/hearthandharvest/recipe/blueberry_muffin.json` holds muffin batter compacted,
not a generic batter bowl. Being byte-identical to a `data/createfood/recipe/**` file is the
expected outcome, not a duplicate to avoid. Two mechanical consequences:

- **Match the result key to the type you copied.** Create recipe types take `results` as a list of
  `{"item": {"id": …}}`; vanilla and Farmer's Delight types take a single `result`. Copying a
  compacting bill onto a crafting recipe without swapping the key leaves a file that would not
  parse if it ever loaded.
- **Confirm our route is genuinely complete before retiring.** A retirement that strands an item is
  worse than the bypass it removed, and the raw intermediate it now depends on must itself be
  obtainable.

### The two halves of compat

Everything above is about overrides, and an override is only half the work. Three separate attempts
at this pass went wrong by reading the override tree as the whole compat surface, so the split is
written down here.

| half | lives at | job |
|---|---|---|
| **Our-side routes** | `data/createfood/recipe/<machine mod>/<machine>/compat/<their mod>/<OUR dish name>_from_<machine>.json`, result = **their** item id | make the foreign dish reachable through our chain |
| **Overrides** | `data/<their ns>/recipe/<their exact path>.json` | change or retire *their* recipe |

The first is the larger half — 222 recipes over 74 foreign items and 11 namespaces, including mods
with no jar in `neoforge/run/mods` at all. The `compat/<their mod>/` segment is not optional; a
recipe of ours producing a foreign item belongs under it, and `RecipeGenerator` carries the segment
through to the generated shaped copies on its own.

**A foreign dish is identified by decomposing its recipe's ingredients, never by its name.** Most
mods do not spell out toppings: Farmer's Delight's `hamburger` is bread + patty + lettuce + tomato +
onion, which is our `hamburger_onion_lettuce_tomato`. `balance/foreign.py` holds the vocabulary —
`TAG_INGREDIENTS` and `ITEM_INGREDIENTS` for ingredients, `FORM_TAGS` and `FORM_ITEMS` for the base
a dish is built on. The two are deliberately separate: `IGNORED` drops bases from *pricing* because
the family already accounts for them, and merging forms into the pricing maps would feed a bun into
the value model, where an unclassified ingredient falls through to `PRODUCE`.

**Route counts follow the family, and are copied from our own dish for that family** — measured,
not chosen: wraps and burritos get one flat shapeless recipe, tacos and burgers four, sandwiches
six. A foreign dish with one route is not necessarily under-served.

**`c:<our dish name>` is the equivalence table.** Where the composition names a slot we do not
register, the foreign item fills it and the tag says so — `c:hamburger_onion_lettuce_tomato` holds
only `farmersdelight:hamburger`, and we ship four routes producing that item because we have no item
of our own. Where the composition matches a slot we *do* register, the foreign item joins that tag
instead and unification handles it; our existing recipes already cover the dish and no route
producing the foreign item is wanted.

Not every foreign dish has a counterpart, and forcing one is worse than leaving it. Of 118 composed
foreign dishes with no equivalence declared, only a handful scored a genuine composition match — we
ship no pancakes, quiches, tarts, cobblers or noodles, and Kaleidoscope Cookery's line-up has almost
nothing in common with ours. Those stay unmapped.

`cfood overrides parity` reports input slots in an override that still name a literal foreign item a
`c:` tag already holds. Read it: a slot held by several non-aggregate tags used to be dropped
silently, so an override could look finished with half its inputs untouched.

### Tag Naming

- **Single-item tags:** Auto-generated by datagen — no hand-written files needed
- **Fluid tags:** Auto-generated by datagen for still + flowing variants
- **Collection tags:** `c:cheeses`, `c:mushrooms`, `c:fruits`, `c:vegetables` — hand-maintained
- **`_ingredient` suffix:** aggregate slot tags used in recipes — hand-maintained
- **`_compat` suffix:** the narrow, same-item view of a tag whose wide form exists for recipes.
  `c:X` may hold things that merely stand in for each other — `c:vegetable_oil` holds oil bottles
  *and* oil buckets, because recipes want either. Almost Unified cannot be pointed at a tag like
  that; it would merge a 250mB bottle with a 1000mB bucket. `c:X_compat` lists only the items that
  are the same physical thing, and that is what the published unification config names instead. Use
  it only where no narrower tag already isolates the subset: `c:tomato` is unusable for the same
  reason, but `c:crops/tomato` already holds exactly the whole tomatoes, so no `_compat` tag is
  minted for it. Most of the ten historical `_compat` tags turned out unnecessary, because their
  main tag is already all-one-item (`c:butter`, `c:milk_powder`, the jams and juices).

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

### AU


---

### Why the list is shaped this way

The `tags` list is in four groups, and they are not interchangeable.

**Group 1 — tags Create: Food shares with another mod.** Both mods ship the same thing under
different names: `create_confectionery:bar_of_black_chocolate` is our dark chocolate bar,
`ratatouille_fried_delights:breadcrumb` is our bread crumbs, `ubesdelight:milk_powder` is our milk
powder. Merging costs nothing and removes the duplicate from the recipe viewer. Ten entries were
added here: End's Delight's chorus fruit pie, pie slice and popsicle are ours, Kaleidoscope Cookery's
meat pie and My Nether's Delight's boiled egg are ours, and Expanded Delight's lemon is Fruits
Delight's.

**Group 2 — tags Create: Food is not in at all.** Two *other* mods duplicate each other: Hearth and
Harvest and Rustic Delight both add a batter bowl, Expanded Delight and Veggies Delight both grow
sweet potatoes, Farm & Charm and Farmer's Delight both mince beef. Create: Food is neutral here, but
a pack with both mods still ends up with two of everything.

This group grew by thirty-three entries. Some are ingredients — Create: BiC Bit and Ratatouille Fried
Delights each add ketchup and mayonnaise in both a bottle and a bucket; Hearth and Harvest and Rustic
Delight each grow cotton, and each add a wild variety of it. Some are whole workstations: Farmer's
Delight and Kaleidoscope Cookery both add a stove, Hearth and Harvest and Kaleidoscope Cookery both
add a scarecrow, Expanded Delight and Hearth and Harvest both add a cask, and Create and Farmer's
Delight both add rope. None of these tags existed before, which is why none of it was unifiable: the
mods had no reason to agree on a tag, and Create: Food had no item to put in one. The tags are
declared by our datagen purely so this config has something to point at.

**Group 3 — convention tags, which is how an aggregate still gets unified.** `c:tomato` can never be
unified: it deliberately holds whole tomatoes *and* Create: Food's sliced and diced ones, so that any
recipe calling for tomato accepts all three. Merging it would turn a diced tomato into a whole one.
But the whole tomatoes inside it — `farmersdelight:tomato` and `kaleidoscope_cookery:tomato` — are
the same item, and they sit together in `c:crops/tomato`. So the aggregate is ignored and the narrow
crop tag is unified instead. Same story for coffee beans, cotton, peanuts, sweet potatoes, salt dust,
wheat flour and ginger. Where a crop has a crate or sack its `c:storage_blocks/...` tag is unified
too — unifying an item without its crate leaves two crates that pack and unpack different items.

**Group 4 — the `_compat` tags.** `c:vegetable_oil` holds oil bottles and oil buckets together,
because recipes want either. Pointing Almost Unified at it would merge a 250mB bottle with a 1000mB
bucket. `c:vegetable_oil_bottle_compat` and `c:vegetable_oil_bucket_compat` are the same-item halves,
and they are what gets unified instead.

---

### Why `ignored_tags` is long

Every entry is a tag that looks unifiable and is not. Four kinds:

**Substitution tags.** `c:apple` holds a whole apple and Create: Food's apple slice; `c:carrot`,
`c:onion`, `c:potato`, `c:beetroot` and the mushrooms and fungi are the same shape. They exist so
that a recipe accepts either form. Unified, one portion silently becomes the other.

**Transitional items.** `c:hamburger` holds our hamburger *and*
`delightfulcreators:incomplete_hamburger`, the half-built item from its sequenced assembly.
`c:dumplings`, `c:pumpkin_pie`, `c:eggplant_burger`, `c:bacon_sandwich`, `c:mutton_sandwich`,
`c:bread_fried_egg` and the five `c:pasta_plate*` tags each carry one. Unifying any of these replaces
a finished dish with a half-built one, or makes an assembly step produce its own input. **These are
the entries that will actually break a world**, which is why they are listed explicitly rather than
merely left out of `tags`.

**Things that are not the same item.** `c:paprika` holds `create:cinder_flour`, which is not paprika.
`c:sugar` holds granulated sugar, Create: Food's powdered sugar, Hearth and Harvest's sugar cubes
and — via Farmer's Respite — `minecraft:honey_bottle`. `c:bun` holds Ratatouille Fried Delights'
*top* and *bottom* bun halves alongside whole buns. `c:sausages` holds our sausage bits next to our
sausages. `c:milk_bottle` and `c:drinks/milk` put goat milk and cow milk together. `c:crops/garlic`
mixes a whole bulb with a clove and a chop; `c:crops/rice` puts ordinary rice next to My Nether's
Delight's ghasmati.

**Slot tags.** Sixteen `*_ingredients` tags name a recipe slot rather than an item —
`c:dumpling_ingredients` holds every filling a dumpling accepts, `c:frosting_ingredients` both butter
and cream cheese, `c:stew_potato_ingredients` either a potato or a sweet potato. Unifying one would
collapse a whole slot to a single ingredient.

**Portions and near-misses.** `c:cooked_rabbit` holds the whole rabbit beside our cuts and jerky;
`c:eggs` holds a turtle egg; `c:flours` holds corn flour beside wheat flour; `c:bread` holds a
tortilla. `c:salts` and the two `c:jams/*` tags are listed because the item they would merge is
already unified through `c:salt` and the `*_jam_bottle` tags, so naming them again can only
introduce a second, competing decision.

**Aggregates.** `c:fruits` (32 members), `c:vegetables`, `c:cheeses` and `c:cheese_slice` (cheddar,
goat, aged and flaxen are genuinely different cheeses), `c:bell_pepper` (29 members — nine colours in
three preparations), `c:calamari` (raw and cooked together). Nothing in these is interchangeable with
anything else in them. Four more join them for the same reason, from tags a dependency owns that
Create: Food now declares so `cfood audit` can see them: `c:ice` (three vanilla ices), `c:raw_meat`
and `c:raw_meat_delight` (a whole meat group each), and `c:cheese` (Create: BiC Bit's young and aged
wedges, which are different cheeses at different ages).

---

Put this in `config/almostunified/duplicates.json` so shaped recipes are compared on their `pattern`
and `key` again:

```json
{
  "override_duplicate_rules": {
    "minecraft:crafting_shaped": {
      "ignored_fields": ["neoforge:conditions", "show_notification", "category", "group"],
      "rules": {},
      "handle_implicit_counts": false
    }
  }
}
```
