### ***3.0.0***

---

**Stacking:**

Durations add up. Eating a second food that grants an effect you already have at the same level extends what is left rather than replacing it. Levels do not add up.

A single effect stacks to a maximum of 30 minutes by default. Any food stacks. Other ways of obtaining mob effects keep vanilla behavior. Can be modified with `stack_duration` and `max_stacked_duration`. See [Config Options](Config-Options).

---

**Supported Effects:**

| Effect              | Description                                                                                                 |
|---------------------|-------------------------------------------------------------------------------------------------------------|
| `adrenaline`        | Increases the user's movement speed the lower health out of max health they have                            |
| `alienating`        | Applies Weakness and Disgusted to surrounding mobs; Disgusted prevents animals from breeding                |
| `animal_charm`      | Attracts and calms nearby animals                                                                           |
| `appetizing`        | Eat and drink even while your food bar is full                                                              |
| `astringent`        | Walk on ice and other slippery blocks as if they were normal                                                |
| `balanced`          | Grants nearby players Absorption                                                                            |
| `berserk`           | Increases the user's attack damage the lower health out of max health they have                             |
| `blood_clot`        | Prevents the user to heal from natural regeneration                                                         |
| `bonding`           | Grants nearby players Absorption and Regeneration                                                           |
| `brimstone_vision`  | Allows the user to see clearly under lava                                                                   |
| `caffeinated`       | Increases all major stats slightly                                                                          |
| `charisma`          | Reduces villager trading prices                                                                             |
| `clarity`           | Prevents Blindness and Darkness                                                                             |
| `combustion`        | Ignites nearby enemies                                                                                      |
| `comfort` ¹         | General comfort/wellbeing                                                                                   |
| `cycling`           | Drops a level of experience as orbs each second, repairing Mending gear without an external source          |
| `digesting`         | Overflowing nutrition becomes saturation                                                                    |
| `explosion`         | Chance to launch a fireball on attack                                                                       |
| `farmers_blessing`  | Cleanses all negative effects                                                                               |
| `feast`             | Combined sustenance + satiation super-buff                                                                  |
| `flight`            | Temporary creative-like flight                                                                              |
| `fortune`           | Increases Luck                                                                                              |
| `grandmas_blessing` | Cleanses negatives + Luck +2                                                                                |
| `heal_aura`         | Applies instant health to entities around you                                                               |
| `lava_walking`      | Allows the user to walk on lava                                                                             |
| `leaf_piercing`     | Projectiles you shoot pass through leaves                                                                   |
| `life_leech`        | Drains health from nearby hostiles and heals the user                                                       |
| `lightning`         | Chance to strike target with lightning                                                                      |
| `lozenge`           | Eat and drink faster                                                                                        |
| `mining`            | Mining speed bonus based on depth                                                                           |
| `nourishment`       | Nourishment/saturation                                                                                      |
| `pacify`            | Reduces enemy aggression; Endermen safe                                                                     |
| `party_starter`     | Fireworks on hit + bonus damage                                                                             |
| `perception`        | Entities nearby the user will begin to glow                                                                 |
| `prickly`           | Damages entities that collide with you                                                                      |
| `pyromaniac`        | Heals the user slowly while standing in fire                                                                |
| `rage_aura`         | Mobs around you become hostile                                                                              |
| `raging`            | Allows you to gain a 5% attack speed boost upon dealing melee damage to entities. This stacks up to 4 times |
| `recovering`        | Prevents Poison and Wither                                                                                  |
| `refreshed`         | Your next harvests do not consume durability and may yield extra drops                                      |
| `refreshing`        | Prevents Mining Fatigue                                                                                     |
| `repulsion`         | Periodically pushes enemies away                                                                            |
| `rest`              | Makes phantoms disappear                                                                                    |
| `rested`            | Bonus experience gain                                                                                       |
| `satiation`         | Hunger management                                                                                           |
| `shrinking`         | Crawl into 1-block gaps while sneaking                                                                      |
| `sliding`           | Boats you ride slide on any block as if on ice                                                              |
| `stimulation`       | Removes mining fatigue and slowness                                                                         |
| `stout_heart`       | Knockback resistance                                                                                        |
| `sugar_rush`        | Stacking speed buff                                                                                         |
| `suspicious_smell`  | Makes suspicious sand and gravel easier to find, and reveals suspicious stew effects                        |
| `sustenance`        | Periodic hunger or health restoration                                                                       |
| `sweet_heart`       | Allows for extra saturation based healing at any hunger level on top of the existing saturation healing     |
| `sweetening`        | Prevents Slowness                                                                                           |
| `touch_absorb`      | Melee attacks grant Absorption                                                                              |
| `touch_heal`        | Melee attacks heal the target                                                                               |
| `touch_poison`      | Melee attacks apply Poison                                                                                  |
| `touch_regen`       | Melee attacks apply Regeneration                                                                            |
| `tough`             | Grants Absorption, Regeneration, and Resistance                                                             |
| `vitality`          | Exhaustion reduction                                                                                        |
| `water_walking`     | Allows the user to walk on water                                                                            |
| `well_served`       | You will not become truly hungry while this effect is active                                                |

¹ Deprecated. Farmer's Delight 1.3 merged it with `nourishment`; it is kept so existing config
overrides keep working, and will be removed in a later release.

---

**Ingredient Effects:**

Which effects are applied comes from its ingredients, theme, and tier. Tier follows nutrition, and sets how many effects are guaranteed:

| Tier | Nutrition | Guaranteed effects |
|------|-----------|--------------------|
| T1   | 1-3       | 0                  |
| T2   | 4-6       | 1                  |
| T3   | 7-9       | 2                  |
| T4   | 10-12     | 3                  |
| T5   | 13+       | 4                  |

Past that count the remaining effects are applied by chance instead, at 50%, 35%, 25%, and 20%. An ingredient below its minimum tier falls through to the same chance roll.

|                              | T1   | T2   | T3    | T4    | T5    |
|------------------------------|------|------|-------|-------|-------|
| Base, savoury                | -    | 600t | 1200t | 2400t | 3600t |
| Base, sweet / drink / frozen | -    | 600t | 900t  | 1800t | 3000t |
| Ingredient and themed        | 600t | 600t | 900t  | 1200t | 1800t |

Each ingredient contributes the effect below. Some ingredients include a bonus effect from the listed tier up. They also receive a fallback effect for use when the primary effect's mod is not installed. Effects marked **ᴬ** grow stronger at tier 4 and 5.

| Ingredient           | Effect             | Secondary               | Fallback              | Min tier |
|----------------------|--------------------|-------------------------|-----------------------|----------|
| **Meat**             |                    |                         |                       |          |
| `beef`               | `damage_boost` ᴬ   | -                       | -                     | 3        |
| `pork`               | `health_boost` ᴬ   | -                       | -                     | 3        |
| `mutton`             | `tough`            | -                       | `resistance`          | 3        |
| `rabbit`             | `movement_speed` ᴬ | -                       | -                     | 2        |
| `chicken`            | `vigor`            | -                       | `speed`               | 3        |
| `bacon`              | `raging`           | -                       | `strength`            | 3        |
| `sausage`            | `combustion`       | -                       | `fire_resistance`     | 3        |
| `beef meatballs`     | `satiated_shield`  | -                       | `absorption`          | 3        |
| **Seafood**          |                    |                         |                       |          |
| `fish`               | `water_breathing`  | -                       | -                     | 2        |
| `salmon`             | `dolphins_grace`   | -                       | -                     | 2        |
| `kelp`               | `water_breathing`  | `dolphins_grace` *(T4)* | -                     | 2        |
| `squid ink`          | `invisibility`     | -                       | -                     | 2        |
| **Egg and dairy**    |                    |                         |                       |          |
| `egg`                | `bonding`          | -                       | `absorption`          | 3        |
| `fried egg`          | `bonding`          | -                       | `absorption`          | 3        |
| `scrambled egg`      | `bonding`          | -                       | `absorption`          | 3        |
| `cheese`             | `satiated_shield`  | -                       | `absorption`          | 3        |
| `cream cheese`       | `sweet_heart`      | -                       | `regeneration`        | 3        |
| `butter`             | `lozenge`          | -                       | `haste`               | 2        |
| `sour cream`         | `astringent`       | -                       | `slow_falling`        | 2        |
| **Vegetables**       |                    |                         |                       |          |
| `lettuce`            | `refreshing`       | -                       | `haste`               | 2        |
| `tomato`             | `recovering`       | -                       | `regeneration`        | 2        |
| `onion`              | `repulsion`        | -                       | `invisibility`        | 2        |
| `mushroom`           | `fortune`          | `night_vision` *(T1)*   | `luck`                | 2        |
| `brown mushroom`     | `fortune`          | `night_vision` *(T1)*   | `luck`                | 2        |
| `red mushroom`       | `fortune`          | `night_vision` *(T1)*   | `luck`                | 2        |
| `beetroot`           | `balanced`         | -                       | `absorption`          | 2        |
| `potato`             | `vigor`            | -                       | `speed`               | 2        |
| `carrot`             | `night_vision`     | -                       | -                     | 1        |
| `rice`               | `sustenance`       | -                       | `saturation`          | 2        |
| **Fruit**            |                    |                         |                       |          |
| `apple`              | `animal_charm`     | -                       | `luck`                | 1        |
| `berry`              | `sweet_heart`      | -                       | `regeneration`        | 2        |
| `glow berry`         | `night_vision`     | `glowing` *(T3)*        | -                     | 1        |
| `chorus fruit`       | `slow_falling`     | `jump` ᴬ *(T3)*         | -                     | 2        |
| `melon`              | `regeneration` ᴬ   | -                       | -                     | 2        |
| `pumpkin`            | `luck` ᴬ           | -                       | -                     | 2        |
| **Fungi**            |                    |                         |                       |          |
| `crimson fungus`     | `fire_resistance`  | -                       | -                     | 2        |
| `warped fungus`      | `slow_falling`     | -                       | -                     | 2        |
| **Sweet**            |                    |                         |                       |          |
| `sugar`              | `sugar_rush`       | -                       | `speed`               | 1        |
| `caramel`            | `damage_boost` ᴬ   | -                       | -                     | 2        |
| `toffee`             | `movement_speed` ᴬ | -                       | -                     | 2        |
| `butterscotch`       | `charisma`         | -                       | `hero_of_the_village` | 2        |
| `marshmallow`        | `warmth`           | -                       | `fire_resistance`     | 2        |
| `honey`              | `regeneration` ᴬ   | `sweet_heart` *(T4)*    | -                     | 2        |
| `chocolate`          | `sugar_rush`       | -                       | `speed`               | 1        |
| `dark chocolate`     | `stimulation`      | -                       | `haste`               | 1        |
| `white chocolate`    | `sweetening`       | -                       | `speed`               | 1        |
| `cream frosting`     | `sugar_rush`       | -                       | `speed`               | 1        |
| `ice cream`          | `tundra_strider`   | -                       | `speed`               | 1        |
| **Other**            |                    |                         |                       |          |
| `salt`               | `preservation`     | `mining` *(T1)*         | `resistance`          | 1        |
| `slime`              | `sliding`          | -                       | `jump_boost`          | 2        |
| `coffee`             | `dig_speed` ᴬ      | -                       | -                     | 2        |
| `peanut butter`      | `lozenge`          | -                       | `haste`               | 2        |
| `taco sauce`         | `warmth`           | -                       | `fire_resistance`     | 2        |
| `tomato sauce`       | `warmth`           | -                       | `fire_resistance`     | 2        |
| `alfredo sauce`      | `satiation`        | -                       | none                  | 3        |
| **Compat mods**      |                    |                         |                       |          |
| `dragon`             | `absorption` ᴬ     | `explosion` *(T4)*      | -                     | 3        |
| `endermite`          | `rest`             | `regeneration` ᴬ *(T3)* | `regeneration`        | 2        |
| `strider`            | `fire_resistance`  | -                       | -                     | 2        |
| `eggplant`           | `recovering`       | -                       | `regeneration`        | 2        |
| `cinnamon`           | `warmth`           | -                       | `fire_resistance`     | 1        |
| **Flavour suffixes** |                    |                         |                       |          |
| `*_jam`              | `sugar_rush`       | -                       | `speed`               | 1        |
| `*_cream_frosting`   | `sugar_rush`       | -                       | `speed`               | 1        |
| `*_ice_cream`        | `tundra_strider`   | -                       | `speed`               | 1        |
| `*_fudge`            | `sugar_rush`       | -                       | `speed`               | 1        |
| `*_chips`            | `sugar_rush`       | -                       | `speed`               | 1        |

---

**Theme Effects:**

Some effects come from what a dish *is* rather than what is in it. Theme effects do not count against the tier limit.

| Theme          | Effect             | Fallback     | Min tier |
|----------------|--------------------|--------------|----------|
| `ice cream`    | `tundra_strider`   | `speed`      | 1        |
| `milkshake`    | `tundra_strider`   | `speed`      | 1        |
| `popsicle`     | `tundra_strider`   | `speed`      | 1        |
| `spicy`        | `fire_resistance`  | -            | 1        |
| `magma cream`  | `fire_resistance`  | -            | 1        |
| `pumpernickel` | `vitality`         | `saturation` | 2        |
| `yogurt`       | `preservation`     | `resistance` | 2        |
| `gyro`         | `tough`            | `resistance` | 2        |
| `jerky`        | `vigor`            | `speed`      | 1        |
| `panna cotta`  | `rested`           | `luck`       | 2        |
| `trifle`       | `rested`           | `luck`       | 2        |
| `mini waffle`  | `luck` ᴬ           | -            | 1        |
| `waffle`       | `luck` ᴬ           | -            | 1        |
| `pretzel`      | `movement_speed` ᴬ | -            | 1        |
| `salad`        | `farmers_blessing` | `luck`       | 2        |
| `vegetable`    | `farmers_blessing` | `luck`       | 2        |
| `coffee`       | `dig_speed` ᴬ      | -            | 1        |
| `cocoa`        | `dig_speed` ᴬ      | -            | 2        |
