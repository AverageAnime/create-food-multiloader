### ***2.3.0***

---



* Hold a filled cloth filter in your main hand and `RMB` to use it.
  * Some filters require a container item in your offhand to produce a result.
  * These interactions are also available through recipes and crafting remainders.

**Defaults:**

* **Egg** (`cloth_filter_egg`):
  * `RMB` with a glass bottle in offhand → produces `egg_whites_bottle`; filter becomes `cloth_filter_egg_yolk`.
* **Egg Yolk** (`cloth_filter_egg_yolk`):
  * `RMB` with empty offhand → produces `egg_yolk`; filter becomes a clean `cloth_filter`.
* **Cacao Mass** (`cloth_filter_cacao_mass`):
  * `RMB` with a bucket in offhand → produces `cacao_butter_bucket`; filter becomes `cloth_filter_pressed_cocoa`.
* **Pressed Cocoa** (`cloth_filter_pressed_cocoa`):
  * `RMB` with empty offhand → produces `pressed_cocoa`; filter becomes a clean `cloth_filter`.

---

Custom filter interactions can be defined with `filter_interactions` — see [Config Options](Config-Options).
