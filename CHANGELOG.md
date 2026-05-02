### *** 2.4.0 ***
![newprev240](https://github.com/user-attachments/assets/730ab5d3-46b5-4521-8ccf-81a8e88b4d41)

#### Changes:
```
Fabric version released.
```
* Restructure config + added new settings: `custom_item`, `custom_block`, `custom_fluid`, `enable_pumpkin_pie_placement`. Read more [here](https://github.com/AverageAnime/create-food-multiloader/wiki/Config-Options).
* Updated recipe chains: `cakes`, `cupcakes`, `butter`, `heavy_cream`, `sour_cream`, `yogurt`, `graham_cracker_pie_crust`, `pumpkin_pie_filling`.
* Removed items: `raw_cake_base`, `raw_ube_cake_base`, `raw_cupcake_base`, `raw_chocolate_cupcake_base`, `raw_muffin_base`, `raw_chocolate_chip_muffin`, `raw_caramel_chip_muffin`, `raw_dark_chocolate_chip_muffin`, `raw_toffee_chip_muffin`, `raw_white_chocolate_chip_muffin`,`raw_butterscotch_chip_muffin`.
* Updated translations: `zh_cn.json` [(#30)](  https://github.com/AverageAnime/create-food-multiloader/pull/30), `ru_ru.json`.
  * Added: `de_de.json`, `fr_fr.json`.
* Updated custard recipes to use new milk tag. [(#33)](https://github.com/AverageAnime/create-food-multiloader/issues/33)
* Update NeoForge version to `21.1.219`.
* Change Farmer's Delight `kelp_roll` recipe to use `c:carrot`.

#### Fixes:
* Fix crash related to brewery effect ids. [(#29)](  https://github.com/AverageAnime/create-food-multiloader/pull/29), [(#32)](https://github.com/AverageAnime/create-food-multiloader/issues/32), [(#34)](https://github.com/AverageAnime/create-food-multiloader/issues/34)
* Fix model/texture mip level issues related to plate textures. [(#31)](https://github.com/AverageAnime/create-food-multiloader/issues/31)
* Fix crafting remainders: `ice_cream_bowls`, `meatball_sticks`.
* Fix some ingredient tooltips.
* Fix some incorrect recipes.

### *** 2.3.0 ***
![newprev230](https://github.com/user-attachments/assets/92ce9b96-dbfe-41eb-9b98-da327b60b537)

#### Changes:
* Farmer's Delight is no longer a required dependency.
* New settings in `createfood-server.toml`.  Read more [here](https://github.com/AverageAnime/create-food-multiloader/wiki/Config-Options).
  * New `handcraft_exclude` list in config. [(#26)](https://github.com/AverageAnime/create-food-multiloader/issues/26)
  * Add your own display blocks with `custom_display_block`.
  * New `nutrition_saturation` setting to modify values on food items.
  * Customize food effects. Read about supported effects [here](https://github.com/AverageAnime/create-food-multiloader/wiki/Compatibility).
* `hand_craft` / `Hand Craft` renamed to `handcraft` / `Handcraft`.
* New storage items: `cloth_sack` and `ration_box`. Read more [here](https://github.com/AverageAnime/create-food-multiloader/wiki/Storage-Blocks-Guide).
* Nachos are no longer a compat item and are no longer hidden by default.
* Add ingredient tooltips for Farmer's Delight `kelp_roll`.
* Add more Display Delight translations.

#### Fixes:
* Fix `creme_brulee` not being a drink.
* Fix missing heated requirement for some recipes.
* Fix cheese not being in calzone tooltip.
* Fix various translations, recipes, and textures.
* Fix `marshmallow_dark_chocolate` being hidden by default.

### *** 2.2.0a ***

#### Fixes:
* Fix projectile crash. [(#24)](https://github.com/AverageAnime/create-food-multiloader/issues/24)
* Fix crafting crash. [(#25)](https://github.com/AverageAnime/create-food-multiloader/issues/25)

### *** 2.2.0 ***
![newprev220](https://github.com/user-attachments/assets/944db275-845a-4832-a174-8ba1d8f1c699)

#### Changes:
* New settings in`createfood-server.toml`.  Read more [here](https://github.com/AverageAnime/create-food-multiloader/wiki/Config-Options).
* Crafting recipes with only two ingredients can now be made by holding the ingredients in each hand and using `RMB`. Read more [here](https://github.com/AverageAnime/create-food-multiloader/wiki/Hand-Crafting-Guide).
* Filled cloth filters now have right click interactions to strain the contents. Read more [here](https://github.com/AverageAnime/create-food-multiloader/wiki/Cloth-Filters-Guide).
* Display Delight supported items can now be placed onto Create: Food plates.
* Create: Food plates now support bulk serving placement using `Shift + RMB`.
    * Bulk placement does not work for Create: Food supported items placed on Display Delight plates. Only single placement will occur.
* Eggshells can now be obtained by using eggs in crafting recipes and by throwing them.
* Increased amount of eggshells used to make rich soil from 3 to 8.
* Chicken patty recipes have been updated to match new items.
* The following are no longer compat items and are no longer hidden by default:
    * Paprika can now be made from nether wart if chili peppers aren't available
        * Cinder flour can also be used as a substitute for paprika.
    * Spicy chicken nuggets
    * Spicy sausages
* Decreased nutrition values for sausage biscuit items by 2.

#### Fixes:
* Fix milling recipes. [(#11)](https://github.com/AverageAnime/create-food-multiloader/issues/11) / [(#18)](https://github.com/AverageAnime/create-food-multiloader/issues/18)
* Fix ube tags / recipes. [(#13)](https://github.com/AverageAnime/create-food-multiloader/issues/13)
* Fix bowl duplication. [(#14)](https://github.com/AverageAnime/create-food-multiloader/issues/14)
* Fix chocolate cooking pot and mixing recipes. [(#16)](https://github.com/AverageAnime/create-food-multiloader/issues/16)
* Fix tomato sauce and chocolate filling recipes + tags. [(#17)](https://github.com/AverageAnime/create-food-multiloader/issues/17)
* Fix recipe conflict from `c:powderable_eggs` tag. [(#21)](https://github.com/AverageAnime/create-food-multiloader/issues/21)
* Fix stew recipes.
* Restore some blockstate files to remove log errors.
* Fix missing cloth filter remainder.
* Fix missing some bottle crafting remainders.
* Fix pasta with meatballs filling recipe.

### *** 2.1.0 ***

#### Changes:
* Create is no longer a required dependency.
* Most Create: Food items can now be displayed on plates. Read more [here](https://github.com/AverageAnime/create-food-multiloader/wiki/Display-Blocks-Guide).
    * Create's bar of chocolate, builder's tea, and sweet roll can also be displayed.
* Compatibility items are now hidden by default.
* New sliced crimson fungus and sliced warped fungus. 
    * No current uses, will add in next update.

#### Fixes:
* Fix missing recipes for gyro meat slices and Farmer's Delight wrap. [(#3)](https://github.com/AverageAnime/create-food-multiloader/issues/3)
* Fix grain tag used for slime bucket recipe. [(#4)](https://github.com/AverageAnime/create-food-multiloader/issues/4)
* Fix missing loot table. [(#5)](https://github.com/AverageAnime/create-food-multiloader/issues/5)
* Fix missing translation key. [(#10)](https://github.com/AverageAnime/create-food-multiloader/issues/10)
* Fix amount of cheese slices received when using cutting board recipe. 
* Fix amount of stews received in cooking and mixing recipes.
* Fix folder for `create:upright_on_belt` tag.
* Fix missing compat tooltip for corn flour.

### *** 2.0.0a ***

#### Changes:
* Add missing loot tables for block items.

#### Fixes:
* Fix crash on servers. [(#2)](https://github.com/AverageAnime/create-food-multiloader/issues/2)
* Pumpkin pie fixes.
* Fix filled piping bags crafting recipes.

### *** 2.0.0 ***

#### Note: Repository has [moved](https://github.com/AverageAnime/create-food-multiloader). Includes `1.21.1-neoforge`, `1.20.1-fabric`, and `1.20.1-forge`.
![newprev200](https://github.com/user-attachments/assets/7a11cedb-519d-45ac-b0fb-5ec4c350a2f3)

#### Changes:
* 50+ new items.
* Display names are now simplified to be closer to vanilla naming. 
* Ingredient and mod compatibility information is now displayed in tooltips. Recommend enable `searchAdvancedTooltips` if using JEI.
* New config `createfood-client.toml`. Disable items & adjust tooltips to your liking.
* Add upright on belt tags. [(#86)](https://github.com/AverageAnime/create-food/issues/86)
* Basic support for Display Delight. [(#90)](https://github.com/AverageAnime/create-food/issues/90)
* Balancing changes for cake recipes. [(#91)](https://github.com/AverageAnime/create-food/issues/91)
* Pumpkin pie, cheese block, and all frosted cakes are now placeable & sliceable.
* Standardize some fluid amounts.
* Adjust saturation for some biscuit sandwiches.
* Add composting for various items.
* Various minor texture updates.
* Cakes are no longer stackable to match vanilla behavior.
* Changed IDs:
    * `raw_chorus_cookie` -> `raw_chorus_fruit_cookie`
    * `raw_sweet_berry_cookie` -> `raw_berry_cookie`
* Removed items:
    * `mutton_wrap_onion_lettuce_tomato`
    * `mutton_wrap_onion_tomato`

#### Fixes:
* Fix chicken patty recipe. [(#79)](https://github.com/AverageAnime/create-food/issues/79)
* Fix missing melon jam filling recipe. [(#85)](https://github.com/AverageAnime/create-food/issues/85)
* Fix cotton candy stick mixing recipe. [(#92)](https://github.com/AverageAnime/create-food/issues/92)
* Fix cloth filter recipe. [(#95)](https://github.com/AverageAnime/create-food/issues/95)
* Fix crashes/errors related to tomato sauce filling recipes. [(#96)](https://github.com/AverageAnime/create-food/issues/96)
* Fix gelatin dessert block cutting recipe.
* Fix some emptying recipes using old fabric amounts.
* Fix cream cake textures having incorrect sides.
* Fix missing cooking recipes for pumpkin pie.
* Fix some missing saturation values.
* Fix some item application & deploying recipes not having remainder items.

### *** 1.21.1 Beta 3 ***
* 100+ item ID changes for naming consistency. **Existing versions will be removed when updating**. 
* Add translucent fluids.
* Fix blockstates for cakes to fix outlines.
* Remove hot biscuit sandwiches.
* Remove pastry bars. Replaced filled pastry bars with filled pastries.
* Fix Rustic Delight textures & add syrup coffee animated texture.
* Update chocolate cream cake textures to match other chocolate cream textures.
* Update cake item & block textures.
* Fix crash from chocolate filling recipes using incorrect tag. [(#69)](https://github.com/AverageAnime/create-food/issues/69)
* Fix boiled egg cooking recipe. [(#71)](https://github.com/AverageAnime/create-food/issues/71)
* Add cloth filter to salt mixing recipe to prevent overlap when making potions. [(#72)](https://github.com/AverageAnime/create-food/issues/72)
* Fix recipes not requiring heat. [(#74)](https://github.com/AverageAnime/create-food/issues/74)
* Remove wheat dough splashing recipe. [(#76)](https://github.com/AverageAnime/create-food/issues/76)

### *** 1.21.1 Beta 2 ***

* Fix recipes. [(#66)](https://github.com/AverageAnime/create-food/issues/66) & [(#67)](https://github.com/AverageAnime/create-food/issues/67)
* Fix blockstate jsons to prevent log spam.

### *** 1.21.1 Beta ***

* Should include all items/fluids/blocks from 1.1.11. Open an issue on GitHub if you find any missing items or broken recipes/tags.
* Fluid buckets are now in a separate creative tab.
* IDs changed for beef meatball items. More ID changes for other items mentioned in previous changelogs will follow.
* Asset changes for other mods are no longer found in built-in resource packs.
* Mod compatibility is largely intact, but hasn't been fully tested so recipe or tag issues are likely. 