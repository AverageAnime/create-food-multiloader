# Create Food — Mixin Strategy & Compatibility Reference

How and when this mod uses mixins to enhance or extend features, and how each candidate enhancement stays minimally invasive and conflict-free across a wide range of mods.

> **Scope:** Minecraft 1.21.1, Mojmap/Parchment mappings, Mixin 0.8.5 + MixinExtras 0.4.1 (compile-only in common; both loader runtimes bundle 0.5.x, so 0.4.x features like `@WrapMethod` are runtime-safe). Core constraint: prefer loader APIs over bytecode injection, and when injection is unavoidable, use only additive, chain-safe patterns. Note that 1.21.1 predates the 1.21.2 `Consumable` component rework — nothing here references `Consumable`/`ConsumableListener`; food behavior on this version lives in `DataComponents.FOOD` (`FoodProperties`) and the `Item.craftingRemainingItem` field.

---

## Table of Contents

1. [Mixin Policy](#1-mixin-policy)
2. [Current-State Audit](#2-current-state-audit)
3. [Area A — Remainder Coverage](#3-area-a--remainder-coverage)
4. [Area B — Consumption Hooks & Foreign-Food Overrides](#4-area-b--consumption-hooks--foreign-food-overrides)
5. [Area C — Display Block Interactions](#5-area-c--display-block-interactions)
6. [Area D — Client Rendering](#6-area-d--client-rendering)
7. [Prioritized Recommendations](#7-prioritized-recommendations)
8. [Appendix — 1.21.1 Target Signature Reference](#8-appendix--1211-target-signature-reference)

---

## 1. Mixin Policy

Rules for every current and future mixin in this repository:

1. **Events first.** Never mixin where a loader event or extension interface covers the need. NeoForge currently ships **zero** mixins (`PlayerEvent.ItemCraftedEvent`, `ItemSmeltedEvent`, `ProjectileImpactEvent`, `IClientFluidTypeExtensions` cover everything) — keep it that way. Mixins are a Fabric fallback and, rarely, a shared-in-common necessity when *neither* loader has an API.
2. **Additive injectors only.** `@Inject` at `HEAD`/`RETURN` by default. When a value must change, use MixinExtras (`@ModifyVariable`, `@ModifyExpressionValue`, `@ModifyReturnValue`, `@WrapOperation`, `@WrapMethod`) — these chain with other mods' handlers instead of fighting them. **Never `@Redirect` or `@Overwrite`**; both are non-chainable and are the leading cause of inter-mod crashes.
3. **Cancellation is exceptional.** `cancellable = true` only when the feature is impossible otherwise, and always behind a config/condition check so vanilla flow is untouched by default.
4. **Failure posture matches criticality.** Cosmetic mixins set `require = 0` (log-and-continue if another mod changed the target); gameplay mixins keep the config default `defaultRequire = 1` (fail loudly).
5. **Vanilla targets only.** No mixins into other mods' classes, into interfaces, or into anonymous/synthetic inner classes (fragile names, remap hazards).
6. **Conventions.** Handler methods carry the `createfood$` prefix (existing convention); client-only mixins go in the `client` array of the mixin config; every mixin added to the project gets an entry in this document.

Where mixins live:

| Config | Package | Use for |
|---|---|---|
| `common/src/main/resources/createfood.mixins.json` | `dev.averageanime.mixin` | Hooks needed identically on both loaders (no event on either) |
| `fabric/src/main/resources/createfood.fabric.mixins.json` | `dev.averageanime.fabric.mixin` | Fabric-only gaps where NeoForge has an event |
| `neoforge/src/main/resources/createfood.neoforge.mixins.json` | `dev.averageanime.neoforge.mixin` | Should stay empty (policy rule 1) |

All three configs are already registered in `fabric.mod.json` / `neoforge.mods.toml` — new mixins need **no build changes**, only a class and a config entry.

---

## 2. Current-State Audit

> **Status:** All seven prioritized recommendations are implemented (see [§7](#7-prioritized-recommendations) for per-item notes). The table below is the post-implementation state.

Mixins in the repository, by config:

| Mixin | Config | Target (Mojmap) | Injection | Risk | Notes |
|---|---|---|---|---|---|
| `ItemAccessor` | common | `Item.craftingRemainingItem` / `Item.components` | `@Accessor` (`@Mutable`) | Very low | Accessor-only — **no method-body injection**. Shared by the A0 remainder applier and the B0 foreign-food applier. |
| `CampfireBlockEntityMixin` | common | `CampfireBlockEntity.cookTick` → `Containers.dropItemStack` | `@WrapOperation` + `@Local` | Medium | Drops the configured remainder alongside a placed campfire's cooked result. Chains with other campfire wrappers; `defaultRequire 1` (fail-loud, gameplay). |
| `AbstractFurnaceBlockEntityMixin` | common | `AbstractFurnaceBlockEntity.serverTick` / `saveAdditional` / `loadAdditional` | `@Inject` HEAD + TAIL | Medium | Smelting/smoking/blasting remainder, delivered through the **output slot** so a hopper or the player collects it with the result. Detection is signature-independent (captures the input at HEAD, recognizes a completed cook by the slot-0 count drop at TAIL) because **NeoForge patches the private `burn` method's parameter list** — a `@WrapOperation`/`@Local` on `burn` fails to apply on NeoForge. Replaces the retired Fabric `FurnaceResultSlotMixin` and NeoForge `ItemSmeltedEvent` handler. |
| `PotionContentsTooltipMixin` | common | `PotionContents.addPotionTooltip(Iterable, Consumer, float, float)` (static overload) | `@WrapMethod` | Low | Filters `item_overrides` removed/replaced effects out of foreign mods' effect tooltips at generation time — locale-independent, no per-mod code (mods that cache their effect list at construction still render it through this vanilla helper). Scoped by `TooltipOverrideContext` (pushed by `ItemStackTooltipMixin`, Jade compat, and the display-block tooltip delegations), so it is a pass-through for stacks without overrides. Skips the wrapped call when filtering empties a non-empty list, suppressing vanilla's gray "No Effects" fallback line. |
| `ThrownEggMixin` | Fabric | `ThrownEgg.onHit` | `@Inject` HEAD | Low | Egg-impact eggshell parity with NeoForge's `ProjectileImpactEvent`. Config-gated in the common handler. |
| `FogRendererMixin` | Fabric (client) | `FogRenderer.setupColor` / `setupFog` | `@Inject` RETURN, `require = 0` | Low | Custom-fluidBlock fog. `require = 0` (cosmetic). Now reads per-fluidBlock fog distances (D3). |
| `ScreenEffectRendererMixin` | Fabric (client) | `ScreenEffectRenderer.renderScreenEffect` | `@Inject` RETURN, `require = 0` | Low | Submersion overlay for custom fluids (D2). NeoForge gets this via `IClientFluidTypeExtensions.renderOverlay`. |
| `FanProcessingRemainderMixin` | NeoForge (`createfood.create.mixins.json`) | **Create** `AllFanProcessingTypes$SmokingType` / `$BlastingType` `.process(ItemStack, Level)` | `@ModifyReturnValue`, `require = 0` | Medium | **Documented exception to rule 5** (mixes into a mod, not vanilla). Appends the configured remainder to Create's encased-fan Bulk Smoking/Blasting output. String-targeted (`remap = false`) with a vanilla-only handler, so nothing compiles against Create; gated by `CreateCompatMixinPlugin.shouldApplyMixin` (probes for `AllFanProcessingTypes`) so it is skipped entirely when Create is absent. See the rule-5 exception note below. |

**Retired:** `ResultSlotMixin` and `FurnaceResultSlotMixin` (Fabric) and the Fabric `item/remainder/CraftingRemainder` delegation wrapper were deleted — vanilla-native crafting remainders make the crafting-grid injection redundant, and smelting remainders moved to the common `AbstractFurnaceBlockEntityMixin` (which covers hoppers, unlike the old player-take mixin). NeoForge's `ItemCraftedEvent` and `ItemSmeltedEvent` handlers were removed. NeoForge now ships exactly **one** mixin — `FanProcessingRemainderMixin`, the deliberate cross-mod exception below (`onEggImpact` remains its only remainder *event* handler).

Notes:

- The common mixin config carries the two shared-in-common mixins allowed by policy rule 1's "shared-in-common necessity" clause. It gained a `"refmap"` key so the common mixins remap correctly in a production Fabric jar (verified: `createfood.refmap.json` contains intermediary mappings for `ItemAccessor` and `CampfireBlockEntityMixin`).
- The zero-mixin access-widener / access-transformer route ([A0](#a0--vanilla-native-remainder-integration-primary) option b) was not taken; the accessor mixin (option a) needs neither file.

**Rule-5 exception — `FanProcessingRemainderMixin` (mixing into Create).** Rule 5 forbids mixins into other mods' classes, but Create's encased-fan Bulk Smoking/Blasting produces its output purely in code (`AllFanProcessingTypes$SmokingType/$BlastingType.process` → `RecipeApplier.applyRecipeOn(..., false)`) with **no event or API** to append to it, so the `crafting_remainders` feature cannot reach it any other way. The exception is kept as narrow and safe as the policy allows:

- **Additive & chain-safe:** `@ModifyReturnValue` on `process` (never `@Redirect`/`@Overwrite`), returning a fresh list — it composes with any other mod wrapping the same method.
- **No compile coupling:** the target is referenced only as a string (`@Mixin(targets = …, remap = false)`) and the handler touches only vanilla types (`List<ItemStack>`, `ItemStack` via `@Local`), so the project never compiles against Create.
- **Absent-Create safe:** `CreateCompatMixinPlugin` (an `IMixinConfigPlugin` on `createfood.create.mixins.json`) returns `false` from `shouldApplyMixin` when Create isn't loaded, so the mixin is dropped before its target is ever resolved — no crash, no `require` failure.
- **NeoForge-only, isolated config:** Create for Fabric 1.21.1 does not exist and Fabric's mixin compiler can't validate an unresolved target, so the mixin lives in the neoforge module under its own `createfood.create.mixins.json`. This is the only mixin NeoForge ships; it does not touch the "vanilla-only" common/Fabric configs.

---

## 3. Area A — Remainder Coverage

**Current state.** Config `crafting_remainders` (`input_item|remainder_item`) is applied by mod code: on Fabric via the two slot mixins, on NeoForge via `PlayerEvent.ItemCraftedEvent` / `ItemSmeltedEvent`, plus `ProjectileImpactEvent` for eggshells (`neoforge/.../item/remainder/CraftingRemainder.java`). Remainders are inserted into the **crafting player's inventory**, so player-less paths (crafter block, hoppers, Create's mechanical crafter) are not covered.

### A0 — Vanilla-native remainder integration (Primary)

Make config remainders **be** vanilla remainders: at config load/reload, write the `Item.craftingRemainingItem` field on each configured input item (tracking originals so a reload restores then reapplies). Everything that honors `Recipe.getRemainingItems()` then works with zero further code:

- Crafting table — including vanilla's put-back-into-grid behavior
- **Crafter block** — ejects remainders out the front natively
- Furnace **fuel** slot (lava-bucket-style)
- Modded benches that call the vanilla recipe methods (Create's mechanical crafter, most crafting-grid mods)

Two implementation options:

| Option | Mechanism | Trade-off |
|---|---|---|
| **(a) Common accessor mixin** | `@Mixin(Item.class)` interface with `@Mutable @Accessor("craftingRemainingItem")` setter, in `createfood.mixins.json` | Single shared implementation. Accessor mixins inject **nothing into method bodies** — near-zero conflict surface. Recommended. |
| (b) Zero-mixin | Fabric access widener (`accessible` + `mutable` on the field) + NeoForge access transformer (`public-f`) — both file paths already wired in the build | No mixin at all, but the setter call must live in loader modules (common may not compile against the widened field), duplicating a small amount of code. |

Consequences and caveats to accept:

- **Retire `ResultSlotMixin` (Fabric) and the crafting branch of NeoForge's `ItemCraftedEvent` handler** — keeping either would double-grant remainders. Net result: *fewer* injections than today.
- Behavioral change: crafting remainders return to the grid slot when possible (vanilla behavior) rather than directly to the player inventory. This is the intended outcome — config remainders become indistinguishable from vanilla ones.
- Last-write-wins on items that already have a remainder (e.g., buckets); originals are recorded and restored on reload.
- Remainders now also apply when a configured item is used as furnace fuel — document as a feature.
- NeoForge's stack-sensitive `IItemExtension.getCraftingRemainingItem(ItemStack)` defaults to the vanilla field, so the field write is honored there too.
- Hot config reload works: restore all recorded originals, then apply the new list.

**Risk: very low.** No method-body injection (option a) or none at all (option b); the mutation is data-level, the same mechanism `Item.Properties.craftRemainder()` uses at construction.

### A1 — Egg-impact parity on Fabric (Do it)

`enable_egg_impact_remainder` (`ConfigSchema.java:124`) is **silently dead on Fabric** — NeoForge handles it via `ProjectileImpactEvent`, but no Fabric handler exists (verified). Fabric has no projectile-impact callback, and pulling a Porting Lib events module for one hook is heavier than ten lines of mixin.

- Target: `ThrownEgg.onHit(HitResult)`, `@Inject` HEAD → `CraftingRemainder.handleEggImpact`, Fabric mixin config.
- **Risk: low.** Few mods touch `ThrownEgg`; the inject is additive. Fixes a real parity bug behind an existing config option.

### A3 — Campfire remainders (Do it, two-part)

Vanilla has no remainder concept for cooked inputs, so this stays a separate path from A0. Both parts reuse the existing `crafting_remainders` list and are config-gated.

1. **Hand-held cooking (zero mixins).** The mod's own `CampfireCookingInteraction` (cook while holding items near a heat source) is pure mod code — grant the remainder in the interaction handler when cooking completes. Zero compat risk.
2. **Placed campfires (common mixin).** Neither loader fires an event when a campfire finishes cooking. Target: the `Containers.dropItemStack` call inside `CampfireBlockEntity.cookTick`, wrapped with MixinExtras `@WrapOperation` — drop the remainder alongside the cooked result, then call the original. The input stack is known inside `cookTick`, so the remainder resolves directly from config with no recipe-type check. **Risk: medium** — cooking-overhaul mods commonly patch campfires; `@WrapOperation` chains cleanly with other wrappers, and the config gate means default behavior is untouched.

---

## 4. Area B — Consumption Hooks & Foreign-Food Overrides

**Goal.** Let the existing config overrides (`items.nutrition_saturation`, `items.effects.item_overrides`, `items.effects.category_overrides`, including per-effect chance) apply to **any mod's food items**, not just this mod's `EffectFood`/`EffectDrink`.

### B0 — Implemented as a component swap at config load (deviation from the design below)

> **Implemented approach.** The default-component-events design below was **not** used. Those events (`DefaultItemComponentEvents.MODIFY` / `ModifyDefaultComponentsEvent`) fire during item registration at game startup, but the override lists live in the **SERVER** config, which does not load until world/server start (Porting Lib loads it at `SERVER_STARTING` from the per-world `<world>/serverconfig/`). The values simply aren't available when the events fire. Instead, foreign-item overrides are applied by swapping `Item.components` through the shared [`ItemAccessor`](#2-current-state-audit) at the server-config `Loading`/`Reloading` hook (with snapshot/restore on `Unloading`), the same mechanism and lifecycle A0 uses for remainders. Consequences:
>
> - **Hot reload now works for foreign items too** — the stated limitation of the events design (startup-only) no longer applies. (Caveat: item stacks capture their component prototype at construction, so a mid-session reload affects stacks created/deserialized afterwards; relogging refreshes an inventory.)
> - Own items are untouched and keep their runtime `EffectFood` path; the foreign path is keyed on **namespaced** ids (`minecraft:apple`), own items on bare paths — no collision.
> - Client tooltip mods (AppleSkin, etc.) still see the overridden values, because the config syncs to the client and fires `Reloading` there, which re-applies the swap.
> - Prereq refactor was done: `EffectFood.applyNutritionOverride`/effect-rebuild logic was extracted into static, item-id-parameterized helpers (`applyNutritionOverride(base, itemId)`, `findOverrideForEffect`, `resolveEffect`, `withEffects`) reused by `FoodPropertiesPatcher`. This refactor also fixed a latent saturation-inflation / eat-speed-reset bug in the own-item rebuild path (it went through `FoodProperties.Builder`, which re-multiplies saturation and resets `eatSeconds`; the helpers now construct the record directly).
>
> The appliers live in `common/.../config/apply/` (`ConfigApplyHandler`, `RemainderApplier`, `ForeignFoodApplier`, `FoodPropertiesPatcher`); loader listeners are `fabric/.../config/ConfigEvents.java` and the `ModConfigEvent` listeners in the NeoForge `CreateFood` constructor.

The original zero-mixin design (kept for reference):

Both loaders provide a sanctioned API to rewrite the default `DataComponents.FOOD` of *any* registered item at load time:

- **Fabric:** `DefaultItemComponentEvents.MODIFY` (fabric-item-api-v1; present in Fabric API 0.116.11+1.21.1)
- **NeoForge:** `ModifyDefaultComponentsEvent` (mod event bus)

Chance-based effects map directly onto `FoodProperties.Builder.effect(MobEffectInstance, float probability)` — `FoodProperties.PossibleEffect` carries the probability natively, so deferred/chance semantics survive the translation.

**Hybrid design — createfood's own items are untouched:**

- **Own items** keep the existing consumption-time path in `EffectFood.finishUsingItem` (reads config at runtime) → **hot config reload keeps working exactly as today**, zero behavior change.
- **Foreign items** (namespaced ids like `minecraft:apple`, `farmersdelight:apple_pie_slice` in config) are handled by the default-component events. Limitation to document: these apply at startup, so foreign-item overrides need a restart — own-item overrides do not.

Why this beats any mixin: other mods (AppleSkin, tooltip renderers, diet/hunger mods) read the item's food component and automatically see the *overridden* values — no injection ordering, no conflict surface. It also fixes an existing loader asymmetry: NeoForge's `EffectFood` subclass overrides `getFoodProperties(ItemStack, LivingEntity)` (`IItemExtension`) so external systems see overrides, while Fabric has no equivalent today.

Prerequisites (small refactor, documented here for when this is implemented):

1. Extract static variants of `EffectFood.applyNutritionOverride` / `applyEffectOverrides` (currently `protected` instance methods keyed on `this`) so the foreign-item path can reuse the same logic.
2. Make config lookups namespace-aware. `EffectFood` derives its key via `BuiltInRegistries.ITEM.getKey(this).getPath()` — bare path, no namespace — in five places (`EffectFood.java` lines 110/145/168/184/220), and `ConfigLogic` compares with raw string equality. Foreign ids already *parse* (the validators don't restrict the id field) but can never *match*. Convention: bare-path keys remain the format for createfood items (backward compatible); namespaced ids route to the foreign-item path.

### B1 — `Player.eat` runtime hook (Fallback only)

Only if hot-reload of *foreign*-item overrides ever becomes a requirement: MixinExtras `@ModifyVariable` at HEAD with `argsOnly = true` on the `FoodProperties` parameter of `Player.eat(Level, ItemStack, FoodProperties)`. The patched value flows into both `FoodData.eat` and the effect application in `LivingEntity.eat`, and `@ModifyVariable` chains with other mods' modifications.

**Risk: medium.** `Player.eat`/`FoodData` are among the hottest food-mod targets (AppleSkin reads them; Spice of Life-style and diet mods modify them); ordering between multiple modifiers is unspecified. Do not implement while B0 satisfies requirements.

---

## 5. Area C — Display Block Interactions

**Conclusion: no new mixins needed — verified.**

- Food **placement** interception and shift-left-click **eat-from-block** are fully event-based on both loaders: Fabric `UseBlockCallback` + `AttackBlockCallback` (`fabric/.../block/handler/FoodPlacementHandler.java`), NeoForge `UseItemOnBlockEvent` + `PlayerInteractEvent.LeftClickBlock` (`neoforge/.../block/handler/FoodPlacementHandler.java`).
- `FoodBlock.tryEat` consumes via `finishUsingItem` on a copied stack — block-eating already routes through the normal item pipeline, so every Area B improvement (nutrition/effect/chance overrides, foreign-food support) **propagates to display blocks automatically** with no additional hooks.

Any future display-block feature should extend these existing handlers rather than introduce injection.

---

## 6. Area D — Client Rendering

NeoForge needs nothing here: custom-fluidBlock fog is handled by `IClientFluidTypeExtensions.modifyFogColor` / `modifyFogRender` (`neoforge/.../block/type/fluidBlock/FluidEntry.java:169-201` — same constants as the Fabric mixin). Fabric lacks an equivalent API for fluids that report `FogType.NONE` (all custom fluids in 1.21.1), hence the mixin.

| # | Candidate | Target | Pattern | Risk | Verdict |
|---|---|---|---|---|---|
| D1 | Harden existing fog mixin | `FogRenderer.setupColor` / `setupFog` | Keep `@Inject` RETURN; **add `require = 0`**; keep the `FogType.NONE` guard | Low — Sodium keeps `FogRenderer`; Iris bypasses vanilla fog (graceful no-op) | **Do it** — two-annotation change |
| D2 | Submersion overlay for custom fluids | `ScreenEffectRenderer.renderScreenEffect` | `@Inject` RETURN, draw the fluidBlock's still texture when the camera is inside a mod fluidBlock; Fabric only (NeoForge gets it free via `IClientFluidTypeExtensions.renderOverlay`) | Low — rarely-mixined class | Good next feature |
| D3 | Per-fluidBlock fog density | same injection as D1 | Read per-`FluidEntry` fog start/end instead of the hardcoded `0.5f`/`1.5f`; mirror on NeoForge inside `modifyFogRender` | Low — no new injection point | Nice-to-have |

---

## 7. Prioritized Recommendations

All seven are **implemented** (status noted per item):

1. **A0 — vanilla-native remainder integration.** ✅ Done via the `ItemAccessor` accessor mixin + `RemainderApplier` at the server-config load/reload hook. Config remainders behave like vanilla ones everywhere (grid put-back, crafter block, furnace fuel, modded benches); retired `ResultSlotMixin`, the Fabric wrapper, and NeoForge's crafting-event branch — net fewer injections.
2. **B0 — foreign-food overrides.** ✅ Done, but as a **component swap at config load** rather than default-component events (see the deviation note in [§4](#b0--implemented-as-a-component-swap-at-config-load-deviation-from-the-design-below)) — the events fire before the SERVER config loads. Own items keep their hot-reloadable runtime path; foreign overrides are now hot-reloadable too.
3. **A1 — Fabric `ThrownEgg` mixin.** ✅ Done. `ThrownEggMixin` @Inject HEAD → common `handleEggImpact`; fixes the parity bug behind `enable_egg_impact_remainder`.
4. **A3 part 1 — hand-held campfire cooking remainders.** ✅ Done in `CampfireCookingInteraction` (pure mod code), gated by the `crafting_remainders` list.
5. **Hardening.** ✅ Done. `require = 0` on `FogRendererMixin`; Fabric `CraftingRemainder` delegation wrapper deleted; common mixin config gained its `refmap` key; the Fabric `"client"` entrypoint (missing, so client rendering never ran) was added.
6. **A3 part 2 — placed-campfire remainder mixin.** ✅ Done. `CampfireBlockEntityMixin` common `@WrapOperation` + `@Local`, gated by the `crafting_remainders` list.
7. **D2 submersion overlay; D3 per-fluidBlock fog parameters.** ✅ Done. `ScreenEffectRendererMixin` (Fabric) + `renderOverlay` (NeoForge) share `FluidOverlayRenderer`; `fogStart`/`fogEnd` threaded through the common `Fluid` definition into both loaders' fog rendering (defaults unchanged).

---

## 8. Appendix — 1.21.1 Target Signature Reference

Mojmap signatures for every target referenced above (verify against Parchment before implementing):

| Class | Member | Signature |
|---|---|---|
| `net.minecraft.world.inventory.ResultSlot` | `onTake` | `public void onTake(Player player, ItemStack stack)` |
| `net.minecraft.world.inventory.FurnaceResultSlot` | `checkTakeAchievements` | `protected void checkTakeAchievements(ItemStack stack)` |
| `net.minecraft.world.item.Item` | `craftingRemainingItem` | `@Nullable private final Item craftingRemainingItem` (field; set via `Item.Properties.craftRemainder`) |
| `net.minecraft.world.item.crafting.Recipe` | `getRemainingItems` | `default NonNullList<ItemStack> getRemainingItems(T input)` (`T extends RecipeInput`) |
| `net.minecraft.world.entity.projectile.ThrownEgg` | `onHit` | `protected void onHit(HitResult result)` |
| `net.minecraft.world.level.block.CrafterBlock` | `dispenseFrom` | `protected void dispenseFrom(BlockState state, ServerLevel level, BlockPos pos)` |
| `net.minecraft.world.level.block.entity.CampfireBlockEntity` | `cookTick` | `public static void cookTick(Level level, BlockPos pos, BlockState state, CampfireBlockEntity campfire)` — result dropped via `Containers.dropItemStack` |
| `net.minecraft.world.entity.player.Player` | `eat` | `public ItemStack eat(Level level, ItemStack food, FoodProperties foodProperties)` |
| `net.minecraft.world.food.FoodProperties` | — | record with `nutrition`, `saturation`, `canAlwaysEat`, `eatSeconds`, `usingConvertsTo`, `effects` (`PossibleEffect(MobEffectInstance, float probability)`) |
| `net.minecraft.client.renderer.FogRenderer` | `setupColor` | `public static void setupColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount)` |
| `net.minecraft.client.renderer.FogRenderer` | `setupFog` | `public static void setupFog(Camera camera, FogRenderer.FogMode fogMode, float renderDistance, boolean nearFog, float partialTick)` |
| `net.minecraft.client.renderer.ScreenEffectRenderer` | `renderScreenEffect` | `public static void renderScreenEffect(Minecraft minecraft, PoseStack poseStack)` |

Loader APIs referenced:

| Loader | API | Purpose |
|---|---|---|
| Fabric | `net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents.MODIFY` | Rewrite default `DataComponents.FOOD` of any item at load |
| NeoForge | `net.neoforged.neoforge.event.ModifyDefaultComponentsEvent` | Same, mod event bus |
| NeoForge | `IClientFluidTypeExtensions.modifyFogColor` / `modifyFogRender` / `renderOverlay` | Custom fluidBlock fog + submersion overlay without mixins |
| NeoForge | `IItemExtension.getCraftingRemainingItem(ItemStack)` | Stack-sensitive remainder; defaults to the vanilla field, so A0's field write is honored |
