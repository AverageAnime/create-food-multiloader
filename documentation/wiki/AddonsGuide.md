### ***3.0.0***

---

### Overview

Addons are code-free resources that contributes config defaults, allowing any easy way to register content under the Create: Food namespace and to set any option.

Create: Food ships two bundled addons, enabled by default: 
* `Create: Food+` which adds mod compat content.
* `Create: Food - Rebalance` which rebalances vanilla, Create, and Farmer's Delight nutrition, saturation, and effect tables.

Bundled addons are located at `data/createfood/addons/<addon_id>.json` and third-party addons are located at `data/createfood/addon.json`.
Ship recipes, models, blockstates, textures, loot tables and translations under `assets/createfood/…` and `data/createfood/…`.

| Field             | Notes                                                                                                                                                 |
|-------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| `spec_version`    | Current version: 1.0.                                                                                                                                 |
| `addon_id`        | Identifies the addon, and is what the player writes in `addons_enabled`. Falls back to the mod id. Two addons claiming one id: the second is ignored. |
| `name`            | Displayed name, shown in logs.                                                                                                                        |
| `default_enabled` | Whether the addon contributes is enabled by default.                                                                                                  |
| `config`          | Defaults for any option the mod defines, keyed by config file then by the option's dotted path.                                                       |

|                       | Fields                                                                                                 | Notes                                                                                                                                                                                                                                                |
|-----------------------|--------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `items`               | `id`, `type`, `nutrition`, `saturation`, `remainder`, `tooltips[]`, `compat`, `effects[]`, `condition` | `type` is one of `food`, `fast_food`, `bowl`, `bowl_cr`, `bottle`, `stick`, `stick_cr`, `plain`, `plain_cr`, `ingredient_bottle`, `ingredient_bowl`, `piping_bag`. `nutrition`/`saturation` apply to the food types; `remainder` only to `plain_cr`. |
| `blocks`              | `id`, `type`, `slice`, `condition`                                                                     | `type` is one of `cake`, `pie`, `pizza`, `waffle`, `cheese`, `gyro_meat` (all need `slice`), or `cake_base`, `raw_pie`, `raw_pizza`, `gelatin` (no slice).                                                                                           |
| `fluids`              | `id`, `slope`, `level`, `condition`                                                                    | Each declaration also produces `flowing_<id>`, `<id>_block` and `<id>_bucket`.                                                                                                                                                                       |
| `display_blocks`      | `item`, `display`, `max_stack`, `height`, `particles`, `condition`                                     | `display` is one of `plate`, `small_plate`, `bottle`, `bowl`, `display_bowl`, `salad_bowl`, `large_bowl`, `small_bowl`, `plate_food`. `particles` applies only to `bottle` and `bowl`.                                                               |
| `tooltips`            | `item`, `tooltips[]`, `compat`                                                                         |                                                                                                                                                                                                                                                      |
| `crafting_remainders` | `item`, `remainder`                                                                                    |                                                                                                                                                                                                                                                      |
| `hide_items`          | ids                                                                                                    | Registered but kept out of creative tabs with their recipes disabled.                                                                                                                                                                                |
