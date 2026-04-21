### ***2.3.0***

_Note: Both storage items work identically. The only differences are their slot count and default settings._

**As an item:**

* `RMB` — opens the inventory, or eats the first stored food if `eat_from_item` is enabled.
    * Plays the correct eating or drinking animation and applies all food effects.
    * Food that returns a container on use (e.g. mushroom stew → bowl) places the container back into storage if there is a free slot, otherwise it goes to your inventory.
* Hovering over the item shows its stored contents.

**As a block:**

* `RMB` — opens the inventory.
    * The cloth sack block visually opens and closes as players interact with it.
    * Floating item icons are rendered on the front face of placed cloth sack blocks.
* Breaking the block drops it with its inventory intact.
* Middle-click in creative mode preserves inventory.