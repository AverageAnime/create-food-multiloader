package dev.averageanime.neoforge.item;

import dev.averageanime.neoforge.item.effect.FoodEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ModEffectCategories {

    private ModEffectCategories() {}

    private static final List<FoodEffect> ALL = new ArrayList<>();
    private static final Map<String, FoodEffect> BY_NAME = new HashMap<>();

    private static FoodEffect register(FoodEffect effect) {
        ALL.add(effect);
        BY_NAME.put(effect.getCategoryName(), effect);
        return effect;
    }

    /**
     * Looks up a {@link FoodEffect} by its category name (e.g. {@code "comfort"}).
     * Returns empty if the name is not a known category — callers should fall back
     * to treating the string as a direct registry ID.
     */
    public static Optional<FoodEffect> getByName(String name) {
        return Optional.ofNullable(BY_NAME.get(name));
    }

    public static final FoodEffect COMFORT = register(
            FoodEffect.category("comfort")
                    .or("farmersdelight",  "farmersdelight:comfort")
                    .build()
    );

    public static final FoodEffect NOURISHMENT = register(
            FoodEffect.category("nourishment")
                    .or("farmersdelight",  "farmersdelight:nourishment")
                    .build()
    );

    /** Reduces exhaustion over time (scales with level). */
    public static final FoodEffect VITALITY = register(
            FoodEffect.category("vitality")
                    .or("bakery",  "bakery:vitality")
                    .build()
    );

    /** Prevents hunger from draining too quickly by managing food exhaustion. */
    public static final FoodEffect SATIATION = register(
            FoodEffect.category("satiation")
                    .or("farm_and_charm",  "farm_and_charm:satiation")
                    .build()
    );

    /** Restores hunger (or health when full) periodically. */
    public static final FoodEffect SUSTENANCE = register(
            FoodEffect.category("sustenance")
                    .or("farm_and_charm",  "farm_and_charm:sustenance")
                    .build()
    );

    /** Combined sustenance + satiation super-buff. */
    public static final FoodEffect FEAST = register(
            FoodEffect.category("feast")
                    .or("farm_and_charm",  "farm_and_charm:feast")
                    .build()
    );

    /** Stacking speed buff — Movement Speed up to 5 stacks, then Attack Speed. */
    public static final FoodEffect SUGAR_RUSH = register(
            FoodEffect.category("sugar_rush")
                    .or("bakery",  "bakery:sugar_rush")
                    .build()
    );

    /** Increases experience points gained while active. */
    public static final FoodEffect RESTED = register(
            FoodEffect.category("rested")
                    .or("farm_and_charm",  "farm_and_charm:rested")
                    .build()
    );

    /** Removes all negative status effects for the duration. */
    public static final FoodEffect FARMERS_BLESSING = register(
            FoodEffect.category("farmers_blessing")
                    .or("farm_and_charm",  "farm_and_charm:farmers_blessing")
                    .build()
    );

    /** Removes all negative effects and grants Luck +2. */
    public static final FoodEffect GRANDMAS_BLESSING = register(
            FoodEffect.category("grandmas_blessing")
                    .or("farm_and_charm",  "farm_and_charm:grandmas_blessing")
                    .build()
    );

    /** Melee attacks apply Poison to the hit entity. */
    public static final FoodEffect TOUCH_POISON = register(
            FoodEffect.category("touch_poison")
                    .or("brewery",  "brewery:toxic_touch")
                    .build()
    );

    /** Melee attacks apply Regeneration to the hit entity. */
    public static final FoodEffect TOUCH_REGEN = register(
            FoodEffect.category("touch_regen")
                    .or("brewery",  "brewery:renewing_touch")
                    .build()
    );

    /** Melee attacks grant Absorption to the attacker. */
    public static final FoodEffect TOUCH_ABSORB = register(
            FoodEffect.category("touch_absorb")
                    .or("brewery",  "brewery:protective_touch")
                    .build()
    );

    /** Melee attacks directly heal the hit entity. */
    public static final FoodEffect TOUCH_HEAL = register(
            FoodEffect.category("touch_heal")
                    .or("brewery",  "brewery:healing_touch")
                    .build()
    );

    /** Sets nearby enemies on fire for a short time. */
    public static final FoodEffect COMBUSTION = register(
            FoodEffect.category("combustion")
                    .or("brewery",  "brewery:combustion")
                    .build()
    );

    /** Attacks have a small chance to launch a fireball. */
    public static final FoodEffect EXPLOSION = register(
            FoodEffect.category("explosion")
                    .or("brewery",  "brewery:explosion")
                    .build()
    );

    /** Periodically pushes enemies away from the player. */
    public static final FoodEffect REPULSION = register(
            FoodEffect.category("repulsion")
                    .or("brewery",  "brewery:repulsion")
                    .build()
    );

    /** Attacks have a small chance to strike the target with lightning. */
    public static final FoodEffect LIGHTNING = register(
            FoodEffect.category("lightning")
                    .or("brewery",  "brewery:lightning")
                    .build()
    );

    /** Melee hits pop fireworks and deal a little extra damage. */
    public static final FoodEffect PARTY_STARTER = register(
            FoodEffect.category("party_starter")
                    .or("brewery",  "brewery:partystarter")
                    .build()
    );

    /** Grants temporary flight for a short duration. */
    public static final FoodEffect FLIGHT = register(
            FoodEffect.category("flight")
                    .or("brewery",  "brewery:haley")
                    .build()
    );

    /**
     * Increases mining speed based on depth.
     * brewery:mining and herbalbrews:deeprush are functionally equivalent,
     * so only the first loaded mod's effect fires.
     */
    public static final FoodEffect MINING = register(
            FoodEffect.category("mining")
                    .or("brewery",      "brewery:mining")
                    .or("herbalbrews",  "herbalbrews:deeprush")
                    .build()
    );

    /** Reduces enemy aggression; Endermen eye contact becomes safe. */
    public static final FoodEffect PACIFY = register(
            FoodEffect.category("pacify")
                    .or("brewery",  "brewery:pacify")
                    .build()
    );

    /** Reduces villager trading prices by ~10%. */
    public static final FoodEffect CHARISMA = register(
            FoodEffect.category("charisma")
                    .or("brewery",  "brewery:pint_charisma")
                    .build()
    );

    /** Nearby animals feel unusually friendly and stick around. */
    public static final FoodEffect ANIMAL_CHARM = register(
            FoodEffect.category("animal_charm")
                    .or("brewery",  "brewery:snow_white")
                    .build()
    );

    /** Grants nearby players Absorption. */
    public static final FoodEffect BALANCED = register(
            FoodEffect.category("balanced")
                    .or("herbalbrews",  "herbalbrews:balanced")
                    .build()
    );

    /** Grants nearby players temporary Absorption and Regeneration. */
    public static final FoodEffect BONDING = register(
            FoodEffect.category("bonding")
                    .or("herbalbrews",  "herbalbrews:bonding")
                    .build()
    );

    /** Increases Luck. */
    public static final FoodEffect FORTUNE = register(
            FoodEffect.category("fortune")
                    .or("herbalbrews",  "herbalbrews:fortune")
                    .build()
    );

    /** Grants Absorption, Regeneration, and Damage Resistance together. */
    public static final FoodEffect TOUGH = register(
            FoodEffect.category("tough")
                    .or("herbalbrews",  "herbalbrews:tough")
                    .build()
    );

    /** Drains health from nearby hostile entities and heals the user. */
    public static final FoodEffect LIFE_LEECH = register(
            FoodEffect.category("life_leech")
                    .or("herbalbrews",  "herbalbrews:lifeleech")
                    .build()
    );

    /** Increased movement speed on snow, ice, and powder snow. Prevents sinking into powder snow. */
    public static final FoodEffect TUNDRA_STRIDER = register(
            FoodEffect.category("tundra_strider")
                    .or("kaleidoscope_cookery",  "kaleidoscope_cookery:tundra_strider")
                    .build()
    );

    /** Slowly regenerate health when near heat sources such as stoves, campfires, and lava. */
    public static final FoodEffect WARMTH = register(
            FoodEffect.category("warmth")
                    .or("kaleidoscope_cookery",  "kaleidoscope_cookery:warmth")
                    .build()
    );

    /** Hunger value acts as extra health; damage is absorbed by hunger first. */
    public static final FoodEffect SATIATED_SHIELD = register(
            FoodEffect.category("satiated_shield")
                    .or("kaleidoscope_cookery",  "kaleidoscope_cookery:satiated_shield")
                    .build()
    );

    /** Running does not consume hunger. */
    public static final FoodEffect VIGOR = register(
            FoodEffect.category("vigor")
                    .or("kaleidoscope_cookery",  "kaleidoscope_cookery:vigor")
                    .build()
    );

    /** Phantoms will flee from you. */
    public static final FoodEffect SULFUR = register(
            FoodEffect.category("sulfur")
                    .or("kaleidoscope_cookery",  "kaleidoscope_cookery:sulfur")
                    .build()
    );

    /** Creepers nearby will flee from you. */
    public static final FoodEffect MUSTARD = register(
            FoodEffect.category("mustard")
                    .or("kaleidoscope_cookery",  "kaleidoscope_cookery:mustard")
                    .build()
    );

    /**
     * Eating rotten flesh, raw chicken, poisonous potatoes, pufferfish, or
     * spider eyes will not cause debuffs.
     */
    public static final FoodEffect PRESERVATION = register(
            FoodEffect.category("preservation")
                    .or("kaleidoscope_cookery",  "kaleidoscope_cookery:preservation")
                    .build()
    );
}