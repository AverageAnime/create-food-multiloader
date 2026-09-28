# Addon specs

The spec that used to live here now ships inside the mod jar:

- `common/src/main/resources/data/createfood/addons/createfoodplus.json` — Create: Food+, the compat content, on by default.
- `common/src/main/resources/data/createfood/addons/createfoodrebalance.json` — Create: Food Rebalance, the vanilla, Create and Farmer's Delight nutrition and effect tables, off by default.

Those two are first-party and are read from the classpath. A third-party addon ships one spec at
`data/createfood/addon.json` in its own jar, and is found by scanning mod files; `-Dcreatefood.addonRoots`
points the loader at extra directories for testing one before it is packaged.

The format is documented in [`../wiki/AddonSpec.md`](../wiki/AddonsGuide.md).
