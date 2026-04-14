# Create Food — New Item Guide

> **Toolkit note:** The toolkit is designed to be used alongside this guide. Steps 1–6 inform the choices you make in the Item tab. Steps 7–8 inform recipe creation in the Recipes tab. The toolkit generates Java registration lines and lang entries. All JSON asset files (item tags, item models, block models, blockstates, loot tables, fluid tags) are now fully covered by datagen — run datagen after adding your registration lines and all JSONs are produced automatically. You only need to provide texture PNGs.

---

## Adding a New Item — Step-by-Step

### Step 1 — Determine the Tier

Use the nutrition tier tables in Design §1. Ask:

- How many ingredients does this item have?
- What is its physical form (snack, meal, drink)?
- Is it a standalone dish or a component/topping?

Start at the tier that matches its form, then adjust for ingredient count using the complexity rules in Design §3.

> **Toolkit:** The preview bar shows the tier and label automatically as you set nutrition. The Advisor section (in the Export tab) estimates nutrition and saturation from form + ingredients.

### Step 2 — Determine the Saturation Profile

| Purpose | Saturation range |
|---------|-----------------|
| Quick energy (candy, chocolate) | 0.2 – 0.4 |
| Balanced (most meals) | 0.5 – 0.9 |
| Filling (soups, butter-heavy, hot drinks) | 1.0+ |

At Tier 4–5, saturation converges toward 0.6–0.9 regardless of ingredient richness.

### Step 3 — Select the Item Class

Refer to Design §2.

- Bottle form? → `bottle()` helper, stack 16, return glass bottle
- Bowl or plate? → `bowlConsumable()`, stack 16, return bowl
- Assembled food (sandwich, bun, wrap)? → `food()` or `consumable()`
- Piping bag? → `pipingBag()`, stack 2, return piping bag

> **Toolkit:** Item Type, Stack Size, Converts To, and Remainder are in the Registration section. For fluids with bottle items, enable "Has bottle item" — bottle properties have their own Properties section. The toolkit outputs the correct helper method call (e.g. `bottle("id", 5, 0.8f, tips(...))`) for `ModItems.java`.

### Step 4 — Assign Effects

Consult Design §4.

- Warm/sweet/cozy/indulgent? → Comfort (pick duration tier)
- Complete protein-rich meal? → Nourishment (pick duration tier)
- Contains kelp, glow berry, crimson/warped fungus, or honey? → Add ingredient-specific effect
- Secondary vanilla effect only if 5+ ingredients or strong thematic reason

> **Toolkit:** Effects section in the Item tab. Add any number of effects (confirmation warning appears for 3+). For blocks, effects are on the slice item (Properties/Effects sections). The toolkit generates `fx(Effect, duration)` calls in the helper output.

### Step 5 — Decide on `.fast()`

Consult Design §5. Add `.fast()` for: snack-sized items, single bites, candy/chocolate/bread-slice form. Do not add for meals, bowls, plates, or hot drinks.

> **Toolkit:** "Has fast property" toggle in the Properties section. Maps to `fastFood()` or `consumableFast()` helper.

### Step 6 — Verify Against Comparable Items

Find 2–3 items already in the mod with the same complexity, form, and tier. Adjust if deviation is >±1 nutrition or >±0.2 saturation without a documented reason.

**Quick reference — comparable items by category:**

| New item type | Compare against |
|---------------|----------------|
| Bottle drink | Custard bottles, milkshakes |
| Bowl meal | Scrambled eggs plate, mashed potatoes bowl |
| Assembled sandwich/bun | Existing burger/taco family at same meat tier |
| Sweet roll / pastry | Butterscotch/toffee/caramel variants |
| Cake slice | Cream cake slices, cheesecake slices |

> **Toolkit:** Use the Advisor in the Export tab for a rough estimate.

### Step 7 — Map the Production Chain

Consult Design §7. Work backwards from the finished item to raw ingredients and identify every stage.

**Recipe type decision tree:**

| Question | Recipe Type |
|----------|-------------|
| Solid pressed into chips, crumbs, diced, or ground meat? | Pressing |
| Solid ground into powder or crumbs? | Milling |
| Multiple ingredients blended (cold) into batter, dough, or fluid? | Mixing |
| Multiple ingredients blended with heat? | Mixing (heated) |
| Single raw item cooked with heat alone? | Smelting / Smoking / Campfire |
| Multiple ingredients cooked together? | Cooking Pot |
| Pressed or compacted with heat (cookies, meatballs, taco shell, pita)? | Compacting (heated) |
| Fluid or loose ingredient pressed into solid candy, bar, or raw shape? | Compacting (unheated) |
| Item-to-item addition, building up toppings or closing a sandwich? | Deploying |
| Fluid loaded into a container? | Filling |
| Block-form food portioned into slices? | Cutting |
| Simple dry combination with no processing? | Crafting |

> **Toolkit:** All recipe types are available in the Recipes tab. Select the recipe type and fill in the inputs. For Create recipes, the fluid input/output toggle and fluid|mB input type are available. Block slice cutting recipes and bottle filling/emptying recipes are auto-generated — they appear in the Recipes tab Files panel automatically.

### Step 8 — Assign the ID, Display Name, and Tags

Consult Design §8.

**ID:** `snake_case` · base → primary flavour → toppings → container suffix. Raw prefix for uncooked forms.

**Display name:** Keep generic. Check the lang file for established patterns in the item's category.

**`berry` vs `sweet_berry`:** Use `berry` in ID, "Sweet Berry" in display name.

**Tags:** All tag and asset JSON files are auto-generated by datagen — no manual file creation needed for individual items. Run datagen after adding your registration lines. If the item belongs to a grouping or uses a new ingredient concept, add it to the relevant collection or `_ingredient` tag manually.

> **Toolkit:** Once you fill in the Item tab, files are generated and listed in the Files panel. The Files panel shows Java registration lines and lang entries — all JSON assets are omitted since datagen covers everything. Download the zip from the Export tab. The Checklist section lists the texture PNGs you need to create and the Java file edits needed.

### Registration

```
/**
* Item registration for Create: Food.
*
* <p>All 936 items are declared as single-line static fields using the private
* helper methods defined below. To add a new item, pick the appropriate helper
* and add one line — no anonymous class boilerplate required.
*
* <h3>Helper reference</h3>
* <pre>
*  ── No-food ──────────────────────────────────────────────────────────────
*  plain(id)
*  plain(id, compatKey, fullTipKeys...)      ← full "tooltip.*" keys
*  plainCr(id, remainder)
*  plainCr(id, remainder, compatKey, fullTipKeys...)
*  ingredientBottle(id)     DrinkableItem, stack 16, glass bottle remainder
*  ingredientBottleItem(id) Item, glass bottle remainder
*  ingredientBowlItem(id)   Item, stack 16, bowl remainder
*  pipingBag(id)            Item, stack 2, PIPING_BAG remainder
*  pipingBag(id, compatKey, fullTipKeys...)
*
*  ── Food (trailing args = any mix of Tip and Fx in any order) ────────────
*  food(id, nut, sat, args...)          Item, stack 64
*  fastFood(id, nut, sat, args...)      Item, stack 64, fast eat
*  consumable(id, nut, sat, args...)    ConsumableItem, stack 64
*  consumableFast(id, nut, sat, args...)
*  bottle(id, nut, sat, args...)        DrinkableItem, stack 16, glass bottle
*  bowlFood(id, nut, sat, args...)      Item, stack 16, bowl return
*  bowlConsumable(id, nut, sat, args...)
*  stickFood(id, nut, sat, crStick, args...)        crStick: also set craftRemainder
*  stickConsumable(id, nut, sat, crStick, args...)
* </pre>
*
* <p>Trailing {@code args} accept {@link Tip} and {@link Fx} in any order:
* <ul>
*   <li>{@code tips("tooltip.compat.mymod", "bacon_ingredient", "cheese_ingredient")}
*       — compat key is the full translation key; ingredient keys are SHORT
*         (the {@code tooltip.createfood.} prefix is added automatically).
*   <li>{@code fx(ModEffects.COMFORT, 1200)} — effect with amplifier 0.
*   <li>{@code fx(MobEffects.WATER_BREATHING, 600, 1)} — effect with amplifier.
* </ul>
*
* <h3>Examples</h3>
* <pre>{@code
* // Plain non-food item
* public static final DeferredItem<Item> MY_INGREDIENT = plain("my_ingredient");
*
* // Food item with two ingredient tooltips
* public static final DeferredItem<Item> MY_SANDWICH =
*     food("my_sandwich", 8, 0.7f, tips(null, "beef_ingredient", "lettuce_ingredient"));
*
* // ConsumableItem bowl with compat, tips, and effect
* public static final DeferredItem<Item> MY_STEW =
*     bowlConsumable("my_stew", 12, 0.9f,
*         tips("tooltip.compat.mymod", "beef_ingredient"),
*         fx(ModEffects.NOURISHMENT, 3600));
* }</pre>
*/
```

```
/**
* Single loot table provider for all createfood blocks.
*
* <p>Covers two categories:
* <ol>
*   <li><b>Display blocks</b> — bottle, bowl, salad bowl, plate, small plate,
*       and stacked plate blocks from {@link ModDisplayBlocks}.
*   <li><b>Food blocks</b> — cakes, pies, pizzas, raw blocks, and all other
*       simple self-drop blocks from {@link ModBlocks}.
* </ol>
*
* <h3>Display block loot rules</h3>
* <ul>
*   <li><b>Bottle / bowl / salad bowl / plate food blocks</b> — drop themselves, survives_explosion.
*   <li><b>SmallPlateFoodBlock</b> — drops itself, survives_explosion.
*   <li><b>PlateBlock</b> — drops itself at max stack; drops food items × stack count
*       at lower stacks; always drops one bowl when not at max stack.
* </ul>
*
* <h3>Food block loot rules</h3>
* <ul>
*   <li><b>ModCakeBlock (7 bites)</b> — whole block with no knife at bites=0;
*       knife yields 7..2 slices for bites=0..5; last bite always drops 1 slice.
*   <li><b>ModPieBlock / PizzaBlock (4 bites)</b> — same structure scaled to 4 slices.
*   <li><b>Everything else</b> — simple survives_explosion self-drop.
* </ul>
*/
```

```
/**
 * Generates {@code data/c/tags/item/<id>.json} for every item in the mod.
 *
 * <p>Each item receives its own single-entry tag under the {@code c} namespace:
 * <pre>
 * data/c/tags/item/beef_taco.json
 * { "replace": false, "values": [{ "id": "createfood:beef_taco", "required": false }] }
 * </pre>
 *
 * <p>These tags are used by recipes (via {@code "tag": "c:beef_taco"}) so that
 * other mods and datapacks can substitute equivalent items.
 *
 * <p>Coverage:
 * <ul>
 *   <li>All items in {@link ModItems} (food, ingredients, tools, bottles, etc.)
 *   <li>All block items registered in {@link ModBlocks}
 *   <li>Bucket items for every fluid registered in {@link ModFluids}
 * </ul>
 *
 * <p>Adding a new item to {@link ModItems} automatically generates its tag on
 * the next datagen run. No changes to this file are required.
 *
 * <p><b>Note:</b> Collection/grouping tags (e.g. {@code c:cheeses},
 * {@code c:mushrooms}) and {@code _ingredient} aggregate slot tags are not
 * generated here — those are intentional hand-maintained files because they
 * group multiple items under a single tag for recipe purposes.
 */
```

```
/**
 * Single item model provider for all createfood items.
 *
 * <p>Covers three categories in one pass:
 * <ol>
 *   <li><b>Display block items</b> — block-parent models for display blocks
 *       (plates, bottles, bowls) registered in {@link ModDisplayBlocks}.
 *   <li><b>Regular items</b> — {@code item/generated} flat-sprite models for
 *       every item in {@link ModItems}.
 *   <li><b>Block items</b> — {@code item/generated} flat-sprite models for
 *       every block registered in {@link ModBlocks}, except the 16 gelatin
 *       dessert blocks whose item model parents the block model instead.
 * </ol>
 *
 * <p>Adding a new item or block to the registries automatically generates its
 * model on the next datagen run. No changes to this file are required unless
 * a new block needs a block-parent item model (add its ID to
 * {@link #BLOCK_MODEL_ITEMS}).
 */
```

```
/**
 * Generates {@code data/c/tags/fluid/<id>.json} for every fluid registered
 * in {@link ModFluids}.
 *
 * <p>Each fluid gets one tag containing both the source and flowing variants:
 * <pre>
 * data/c/tags/fluid/apple_custard.json
 * {
 *   "replace": false,
 *   "values": [
 *     { "id": "createfood:flowing_apple_custard", "required": false },
 *     { "id": "createfood:apple_custard",         "required": false }
 *   ]
 * }
 * </pre>
 *
 * <p>Adding a new fluid to {@link ModFluids} automatically generates its tag
 * on the next datagen run. No changes to this file are required.
 */
```

```
/**
 * Single blockstate provider for all createfood blocks.
 *
 * <p>Covers two categories:
 * <ol>
 *   <li><b>Display blocks</b> — bottle, bowl, plate, etc. registered in
 *       {@link ModDisplayBlocks}. Logic unchanged from the original.
 *   <li><b>Food blocks</b> — cakes, pies, pizzas, waffles, raw blocks,
 *       and fluid blocks from {@link ModBlocks} / {@link ModFluids}.
 * </ol>
 *
 * <h3>Food block formats</h3>
 * <ul>
 *   <li><b>ModCakeBlock</b> — {@code bites=0..6, facing} → {@code block/{id}} + {@code block/{id}_slice1..6}
 *   <li><b>ModPieBlock</b> — {@code bites=0..3, facing} → {@code block/{id}} + {@code block/{id}_slice1..3}
 *   <li><b>PizzaBlock (pizza)</b> — same as pie
 *   <li><b>PizzaBlock (waffle)</b> — {@code bites=0..3, facing} → {@code block/{id}} + {@code block/{prefix}mini_waffle1..3}
 *   <li><b>RawPieBlock</b> — {@code facing} only → {@code block/{id}}
 *   <li><b>All others</b> — single {@code ""} variant → {@code block/{id}}
 *   <li><b>Fluid blocks</b> — single {@code ""} variant → {@code block/{fluidId}_block}
 * </ul>
 */
```

```
/**
 * Generates block model JSON files for all food blocks in {@link ModBlocks}
 * and all fluid blocks in {@link ModFluids}.
 *
 * <h3>Model conventions per block type</h3>
 *
 * <b>Cake (7 bites)</b> — full block + 6 slices.
 * Textures: {@code block/{id}_top}, {@code block/{id}_side}, {@code block/{id}_inner},
 * shared {@code block/cake_bottom}.
 * Slice geometry: from-x steps of 3, 5, 7, 9, 11, 13 (2px per bite consumed).
 *
 * <b>Pie/Cheesecake (4 bites)</b> — full block + 3 slices.
 * Textures: {@code block/{id}_top}, {@code block/{id}_inner},
 * shared {@code block/pie_bottom}, {@code block/pie_side}.
 * Slice geometry matches hand-authored originals exactly.
 *
 * <b>Pizza (4 bites)</b> — full block + 3 slices, identical geometry to pie
 * but 2px height instead of 4px.
 * Textures: {@code block/{id}_top}, {@code block/{id}_inner},
 * shared {@code block/pizza_bottom}, {@code block/pizza_side}.
 *
 * <b>Waffle (4 bites)</b> — the full block model uses the waffle's own top
 * texture; the 3 slice models are shared {@code block/mini_waffle1..3} parents
 * with per-waffle texture overrides.
 *
 * <b>Raw pie</b> — single model, top texture per block,
 * shared {@code block/raw_pie_crust_bottom}, {@code block/raw_pie_crust_side}.
 *
 * <b>Raw pizza</b> — single model, top texture per block,
 * shared {@code block/raw_pizza_bottom}, {@code block/raw_pizza_side}.
 *
 * <b>Fluid block</b> — parent {@code block/block}, all faces use
 * {@code createfood:fluid/{id}_flow} texture.
 *
 * <b>Other blocks</b> (gelatin, gyro, cheese, cake_base) — no model generated
 * here; those are hand-authored Blockbench models with custom geometry.
 */
```