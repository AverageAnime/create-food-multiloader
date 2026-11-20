### ***3.1.0***

---

## Adapted Recipes

Plates, bowls and placed food read recipes that belong to other mods: Create's filling, emptying,
deploying, pressing and compacting, Farmer's Delight's cutting, and Ube's Delight's baking mat. When one
of those mods is not installed, Create: Food copies its recipes onto its own `createfood:adapted_*`
types as the game loads, so the interactions keep working. This also allows them to work on Fabric.

* The original recipe files are not changed, and still load only when their mod is present.
* A copy drops only the absent mod's own `mod_loaded` condition. Every other condition still applies:
  hidden items (`hide_items`), other mods' `mod_loaded`, tag checks, and retired recipes.
* A recipe that names the absent mod's own items is not copied, since those items do not exist.
* With the mod installed, nothing is copied and its own recipes are used.

---

### Types

| Adapted type | Copied from | Used by |
|---|---|---|
| `createfood:adapted_filling` | `create:filling` | Large bowl dipping; pouring a fluid onto placed food |
| `createfood:adapted_emptying` | `create:emptying` | Pouring a container into the large bowl; taking a drink back out |
| `createfood:adapted_deploying` | `create:deploying` | Applying an item to placed food |
| `createfood:adapted_cutting` | `farmersdelight:cutting` | A tool on a plate |
| `createfood:adapted_pressing` | `create:pressing` | A rolling pin on a plate |
| `createfood:adapted_compacting` | `create:compacting` | A rolling pin on a plate |
| `createfood:adapted_baking_mat` | `ubesdelight:baking_mat` | A rolling pin on a plate |

Each takes the same JSON as the type it is copied from. A plate holds one item and cannot heat or hold
fluid, so compacting and baking mat recipes are only copied, and only accepted when written by hand, if
they take exactly one item, and for compacting, have no heat and no fluid in or out.

The adapted types are always read, with or without the source mod, but only by Create: Food's blocks —
not by Create's machines or Farmer's Delight's cutting board.
