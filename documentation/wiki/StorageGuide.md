### ***3.0.0***

---

The cloth sack and the ration box work the same way. They differ only in slot count and in their defaults — the ration box eats from the item in hand, while the cloth sack opens its inventory.

**As an item:**

* `RMB` — opens the inventory, or eats the first stored food if `eat_from_item` is enabled.
    * Plays the correct eating or drinking animation, at the same speed and with the same effects as eating that food from hand.
    * Always-edible food, such as a golden apple or honey bottle, can be eaten on a full hunger bar.
    * Food that returns a container on use (e.g. mushroom stew → bowl) places the container back into storage if there is a free slot, otherwise it goes to your inventory.
* Hovering over the item shows its stored contents.

**As a block:**

* `RMB` — opens the inventory.
    * The cloth sack block visually opens and closes as players interact with it.
    * Floating item icons are rendered on the front face of placed cloth sack blocks.
* Breaking the block drops it with its inventory intact.
* Middle-click in creative mode preserves inventory.

---

Stack size, what may be stored, and the eat-from-item toggle are all configurable — see [Config Options](Config-Options).
