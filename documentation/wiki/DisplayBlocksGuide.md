### ***3.0.0***

---

* `Shift + RMB` — places a food item as a display block.
    * `RMB` empty-handed takes the item back.
* `LMB` — breaks the block.
    * A full display drops the block version. A partially filled one drops the individual foods plus the container.
* `RMB` with a tool tagged `c:tools/wrench` picks up a full display block intact.
* Custom display blocks can be added via config — see [Config Options](Config-Options).

---

**Plates:**

* `Shift + RMB` with a bowl places an empty plate. `RMB` on an empty plate takes the bowl back.
* `Shift + RMB` empty-handed on an empty plate toggles between plate sizes.
* `RMB` with a compatible food adds one serving, `Shift + RMB` adds as many as fit.
* `RMB` empty-handed removes one serving, `Shift + RMB` removes all.
    * Some foods can be served on both plate sizes.
* `Shift + LMB` on a filled plate eats directly from it.
* `RMB` with a knife cuts the food, using Farmer's Delight cutting board recipes.
* Plates can also display any item generically, much like an item frame.

---

**Bowls & Bottles:**

Bowls work like plates, with two differences: generically displayed items always render upright, and bowls do not support cutting board recipes.

* Drinking a placed bottle, or decanting it with an empty glass bottle, leaves an empty bottle behind — the same way a bowl leaves an empty bowl.
    * Taking the drink back whole still clears the block, since that item already includes its bottle.

---

**Stacking Empty Plates & Bowls:**

* `RMB` with another empty plate or bowl adds one to the stack, `Shift + RMB` adds as many as fit.
* `RMB` empty-handed takes one back, `Shift + RMB` takes the whole stack.

| Block         | Stack |
|---------------|-------|
| `plate`       | 6     |
| `small_plate` | 12    |
| `bowl`        | 4     |
| `large_bowl`  | 8     |

* An empty large bowl can hold fluid, letting you dip food into it.
* The empty bottle is the exception: it does not stack and is not part of the plate/bowl cycle.
    * `RMB` with a drink bottle turns the empty bottle into that drink, and hands the glass bottle back to you.
    * `RMB` empty-handed picks the glass bottle up.

---

**Combining Items:**

Using an item on placed food combines the two, resolved against recipes that already exist. Tried in order:

* The matching **empty container** takes the contents back out. You get the item, the empty container stays standing.
* A **filled container** pours its fluid in. The amount must match exactly, so nothing is lost — a bucket will not apply to a 250mB recipe.
* An **ingredient** that would craft with the placed food by hand. These are the same recipes handcrafting uses, so this works without Create installed.
* Failing that, Create deployer recipes, which cover a handful of results that exist nowhere else.

As many servings convert as the held stack can pay for. **If the block can show the whole result, it becomes that block** — a placed milkshake bottle plus chocolate chips becomes the chip milkshake bottle, and a plate of four bases plus four ingredients becomes a plate of four results. Only a partial conversion, or a result with no block of that kind, gives you items instead.

This also works on foods placed as blocks in their own right — a cake base, a gelatin dessert, a raw pie or pizza. Those have no servings, so the block simply becomes the result's block.

Individual foods can be blocked from being transformed in the config; a blocked food can still be used as an ingredient on something else.

---

**Large Fluid Bowl:**

* `RMB` on a large bowl with a filled container pours the fluid in. Holds 4000mB by default.
    * Any container the mod can empty works, and that container sets the amount.
    * Fluid cannot be added if it does not fit in full, or if the bowl already holds a different fluid.
* `RMB` with a food item dips it. One item is converted per use, and the result goes to your inventory.
* `RMB` with an empty container takes fluid back out.
* Breaking a bowl holding exactly 1000mB leaves a fluid source block behind. Any other amount is lost.

---

**Display Delight Compatibility:**

* Display Delight plates can be placed as Create: Food plates using `Shift + RMB`.
* Foods from either mod can be placed on the other's plates.
    * Bulk placement does not work for Create: Food foods placed on Display Delight plates — only one at a time.
* Recipes are included to convert plates and bowls between the two.

---

See [Config Options](Config-Options) for custom display blocks, exclusion lists, and fluid capacity, and [Compatibility](Compatibility) for supported mods.
