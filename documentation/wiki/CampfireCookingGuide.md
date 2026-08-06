### ***2.7.0***

_Note: This is disabled by default. Enable `enable_campfire_cooking` in `createfood-server.toml` to use it._

* Hold a raw ingredient in your main hand, and use `Shift` while standing near a `farmersdelight:heat_sources` block to cook it.
  * Detection range is 3 blocks horizontally and 1 block vertically from the player's feet by default, configurable via `horizontal_range` and `vertical_range`.
  * Holding `Shift` can be turned off with `require_shift`.
  * Progress persists as long as you stay within range and keep the item in hand. Switching items or stepping away will reset progress.
  * Smoke particles rise while cooking; flames appear and a crackle sound plays on completion.