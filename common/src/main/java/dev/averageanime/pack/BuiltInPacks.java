package dev.averageanime.pack;

import java.util.List;

/**
 * The built-in packs shipped under {@code resourcepacks/}, one per mod whose assets or data this mod
 * changes.
 *
 * <p>A built-in pack sorts above every mod's root pack on both loaders, so what this mod overrides wins
 * without depending on mod load order — which the jar's own root pack could not do on Fabric at all.
 * Each name must match a directory under {@code common/src/main/resources/resourcepacks/}, which
 * {@code :common:verifyBuiltInPacks} checks: on NeoForge a declared pack with no readable
 * {@code pack.mcmeta} is a startup crash rather than a warning.
 *
 * <p>Assets only. Data stays in the root pack: a resource pack and a data pack are separate
 * repositories with separate persistence -- client selection in {@code options.txt}, world selection in
 * {@code level.dat} -- so one menu entry cannot cover both, and a pack holding both would have to be
 * switched off twice to take effect once. Recipe overrides therefore still rely on the
 * {@code ordering="AFTER"} entries in {@code neoforge.mods.toml}.
 *
 * <p>{@code assets/minecraft/atlases/blocks.json} stays in the root pack too, for a different reason:
 * it merges rather than replaces and names only this mod's own fluid sprite directory, so behind a
 * switch it would break Create: Food rather than restore vanilla.
 */
public final class BuiltInPacks {
    public static final List<String> NAMES = List.of(
            "minecraft_overrides",
            "create_overrides",
            "farmersdelight_overrides",
            "culturaldelights_overrides",
            "farmersrespite_overrides",
            "rusticdelight_overrides",
            "expandeddelight_overrides",
            "ends_delight_overrides",
            "ubesdelight_overrides",
            "endersdelight_overrides",
            "brewinandchewin_overrides",
            "create_bic_bit_overrides",
            "create_confectionery_overrides",
            "displaydelight_overrides",
            "frightsdelight_overrides",
            "fruitsdelight_overrides",
            "hearthandharvest_overrides",
            "kaleidoscope_cookery_overrides",
            "moredelight_overrides",
            "mynethersdelight_overrides",
            "ratatouille_fried_delights_overrides",
            "veggiesdelight_overrides");

    private BuiltInPacks() {}
}
