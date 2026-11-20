### ***3.1.0***

---

### Overview

* `Shift + RMB` — places a food item as a display block.
  * `RMB` empty-handed takes the item back.
* `LMB` — breaks the block.
  * A full display drops the block version. A partially filled one drops the individual foods plus the container.
  * `RMB` with a tool tagged `c:tools/wrench` picks up a full display block intact.
* Custom display blocks can be added via config — see [Config Options](https://github.com/AverageAnime/create-food-multiloader/wiki/Config-Options).

---

### Plates

* `Shift + RMB` with a bowl places an empty plate. `RMB` on an empty plate takes the bowl back.
* `Shift + RMB` empty-handed on an empty plate toggles between plate sizes.
* `RMB` with a compatible food adds one serving, `Shift + RMB` adds as many as fit.
* `RMB` empty-handed removes one serving, `Shift + RMB` removes all.
  * Some foods can be served on both plate sizes.
* `Shift + LMB` on a filled plate eats directly from it.
* `RMB` with a knife cuts one serving of the food, using Farmer's Delight cutting board recipes.
* `RMB` with a rolling pin also presses, compacts and rolls it, using Create pressing, unheated single-item compacting and single-ingredient Ube's Delight baking mat recipes. Items listed in `display_interactions_exclude` are skipped, and `enable_rolling_pin` turns the pin's extra recipes off.
* Both work without Farmer's Delight, Create or Ube's Delight installed. See [Adapted Recipes](https://github.com/AverageAnime/create-food-multiloader/wiki/Adapted-Recipes).
* Plates can also display any item generically, much like an item frame.

---

### Bowls & Bottles

Bowls work similar to plates, with two differences: generically displayed items always render upright, and an item displayed generically in a bowl cannot be cut. Food placed in a bowl or bottle can be cut or rolled like food on a plate. Drinking a placed bottle, or decanting it with an empty glass bottle, leaves an empty bottle behind — the same way a bowl leaves an empty bowl.

* The empty bottle keeps the glass shape of the drink it held.
* `Shift + RMB` empty-handed on an empty bottle cycles its shape: standard, mug, jar, tall, goblet, juice.
* `RMB` with a filled bottle stands that drink back up. `RMB` empty-handed takes the glass bottle back.

---

### Empty Plates & Bowls

* `RMB` with another empty plate or bowl adds one to the stack, `Shift + RMB` adds as many as fit.
* `RMB` empty-handed takes one back, `Shift + RMB` takes the whole stack.
* An empty large bowl can hold fluid, letting you dip food into it.

| Block         | Stack |
|---------------|-------|
| `plate`       | 6     |
| `small_plate` | 12    |
| `bowl`        | 4     |
| `large_bowl`  | 8     |


---

### Combining Items

Using an item on placed food combines the two, checked against existing recipes:

* The matching empty container takes the contents back out.
* A filled container pours its fluid in. The amount must match exactly.
* An ingredient that would craft with the placed food by hand.
* Failing the above, Create deploying recipes.
* Failing those too, on a plate holding several servings: a container holding enough for several fills tops that many (a ganache bottle tops two caramel shortbreads), or one ingredient that crafts with several of the placed food (a caramel piece turns four shortbreads into four caramel shortbreads).

Filling and deploying recipes work without Create installed too.

As many servings convert as the held stack can pay for. If the block can show the whole result, it becomes that block — a placed milkshake bottle plus chocolate chips becomes the chip milkshake bottle, and a plate of four bases plus four ingredients becomes a plate of four results. A partial conversion gives you items instead.

This also works on placeable foods — cake base, gelatin dessert, raw pie, pizza, etc.

---

### Large Fluid Bowl

* `RMB` on a large bowl with a filled container pours the fluid in. Holds 4000mB by default.
    * Any container the mod can empty works, and that container sets the amount.
    * Fluid cannot be added if it does not fit in full, or if the bowl already holds a different fluid.
* `RMB` with a food item dips it. One item is converted per use, and the result goes to your inventory.
* `RMB` with an empty container takes fluid back out.
* Breaking a full bowl (4000mB by default) leaves a fluid source block behind. Any lesser amount is lost.

---

### Display Delight Compatibility

* Display Delight plates can be placed as Create: Food plates using `Shift + RMB`.
* Foods from either mod can be placed on the other's plates.
    * Bulk placement does not work for Create: Food foods placed on Display Delight plates — only one at a time.
* Recipes are included to convert plates and bowls between the two.

---

See [Config Options](https://github.com/AverageAnime/create-food-multiloader/wiki/Config-Options) for custom display blocks, exclusion lists, and fluid capacity, and [Compatibility](https://github.com/AverageAnime/create-food-multiloader/wiki/Compatibility) for supported mods.
