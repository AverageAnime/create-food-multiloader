# Naming & Package Conventions

This project underwent a naming/organization pass across `common`, `fabric`, and `neoforge`. These
conventions are the settled result — follow them for new code so the drift doesn't restart.

## Suffix vocabulary

- **`*Events`** — a loader-side class that only wires a platform event (Fabric callback / NeoForge
  `@SubscribeEvent`) to logic that already lives in `common`. It contains no logic of its own.
  Example: `PlateSliceEvents` (fabric/neoforge) delegates to `common`'s `PlateSliceInteraction`.
- **`*Interaction`** — common-side static logic for a block/item interaction (right-click, placement,
  slicing, etc.), shared by both loaders. Example: `PlateSliceInteraction`, `BowlPlacementInteraction`,
  `PumpkinPieInteraction`, and the existing `item/interaction/*Interaction` classes.
- **`*Registry`** (common only) — defines registration *data* (what to register), consumed by both loaders.
  Example: `ItemRegistry`, `BlockRegistry`, `FluidRegistry`, `DisplayRegistry`.
- **`*Registration`** (loader only) — *performs* registration against the loader's platform API, consuming
  the common `*Registry` data. Example: `BlockRegistration`, `ItemRegistration`, `ConfigRegistration`.
  This is the loader-side counterpart to common's `*Registry` — the split is: common defines, loaders
  register.
- **`*Patcher`** — mutates existing (often foreign-mod) data based on config. Example: `FoodPatcher`,
  `ForeignFoodPatcher`, `RemainderPatcher`.
- **`Empty*`** prefix — an unoccupied placeholder block a player interacts with to place something onto it.
  Example: `EmptyPlateBlock`, `EmptyBowlBlock`, `EmptySmallPlateBlock`. A block without the `Empty` prefix
  in the same family holds something.
- **`ContainerFoodBlock`** vs **`DisplayFoodBlock`** — the two abstract bases under `block/type/display/`,
  both extending `FoodBlock`. `ContainerFoodBlock` reverts to an injected base block when its last serving
  is removed by hand (`PlateBlock`, `BowlBlock`, `SmallPlateBlock`). `DisplayFoodBlock` vanishes instead
  (`PlateFoodBlock`, `BowlFoodBlock`, `BottleFoodBlock`). When adding a new food-display block, pick
  whichever of the two matches the removal behavior — don't reimplement `handleLastItemRemoved`.
- **`Generic*`** prefix — reserved for the item-agnostic display variants (`GenericDisplayPlateBlock`,
  `GenericDisplayBowlBlock`, etc.) that hold an arbitrary player-chosen item, to keep them unambiguous
  against the food-specific display blocks in the same package.

## Package layout

- **Records that represent a data spec belong in a `type/` sub-package**, not flat in their parent package.
  Established in `registry/type/` (`BlockEntry`, `ItemEntry`, `FluidEntry`, `EffectEntry`, `DisplayEntry`,
  `MultiDisplayEntry`) and `config/type/` (`ItemEffectOverride`, `ItemNutritionOverride`). Keep this rule
  when adding new registry or config records.
- **Mixins are always named `<Target>Mixin`** (e.g. `CampfireBlockEntityMixin`), never left bare and never
  shadowing the vanilla class name. Use `<Target><Method>Mixin` only when two mixins target the same class
  and need disambiguating. Accessors use `<Target>Accessor` (e.g. `ItemAccessor`).
- **fabric and neoforge mirror common's package layout** for anything that exists in both — same relative
  path under `block/type/...`, `menu/type/...`, etc. A loader diverging from common's layout is a bug, not
  a style choice.
- **Loader classes that only delegate to common logic must not share the common class's simple name.**
  If they do, every call site is forced into a fully-qualified reference because the import collides with
  the class being defined. Name the loader class for what it actually is (`*Events`, `*Registration`)
  instead.
- **Blind find-and-replace renames are not safe near vendor library types.** `ModConfig` collided with both
  `porting_lib.config.ModConfigSpec` (fabric) and `net.neoforged.neoforge.common.ModConfigSpec` (neoforge)
  during this pass — a plain string rename of `ModConfig` → `ConfigRegistration` silently mangled the vendor
  type into `ConfigRegistrationSpec` in both files, and only surfaced as a compile error. Before a blind
  rename of a common word (`Config`, `Registry`, `Block`, `Item`, ...), grep the target files first for
  fully-qualified vendor/vanilla usages that contain the token as a substring.
