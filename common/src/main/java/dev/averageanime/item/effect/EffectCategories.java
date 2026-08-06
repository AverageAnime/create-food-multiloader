package dev.averageanime.item.effect;

import dev.averageanime.platform.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class EffectCategories {

    private EffectCategories() {}

    private static final List<FoodEffect> ALL = new ArrayList<>();
    private static final Map<String, FoodEffect> BY_NAME = new HashMap<>();

    private record SyntheticOverride(String overrideId, FoodEffect effect) {}

    private static final ConcurrentHashMap<String, SyntheticOverride> SYNTHETIC_OVERRIDES =
            new ConcurrentHashMap<>();

    private static FoodEffect register(FoodEffect effect) {
        ALL.add(effect);
        BY_NAME.put(effect.getCategoryName(), effect);
        return effect;
    }

    public static Optional<FoodEffect> getByName(String name) {
        String configOverride = Services.PLATFORM.getCategoryEffectOverride(name);
        if (configOverride != null) {
            SyntheticOverride cached = SYNTHETIC_OVERRIDES.get(name);
            if (cached == null || !cached.overrideId().equals(configOverride)) {
                cached = new SyntheticOverride(configOverride, FoodEffect.category(name)
                        .or("minecraft", configOverride)
                        .build());
                SYNTHETIC_OVERRIDES.put(name, cached);
            }
            return Optional.of(cached.effect());
        }
        return Optional.ofNullable(BY_NAME.get(name));
    }

    /** Increases movement speed the lower the user's health is relative to their max. */
    public static final FoodEffect ADRENALINE = register(
            FoodEffect.category("adrenaline")
                    .or("runiclib", "runiclib:adrenaline")
                    .build()
    );

    /** Applies Weakness and Disgusted to surrounding mobs; Disgusted prevents animals from breeding. */
    public static final FoodEffect ALIENATING = register(
            FoodEffect.category("alienating")
                    .or("fruitsdelight", "fruitsdelight:alienating")
                    .build()
    );

    /** Nearby animals feel unusually friendly and stick around. */
    public static final FoodEffect ANIMAL_CHARM = register(
            FoodEffect.category("animal_charm")
                    .or("brewery", "brewery:snowwhite")
                    .or("hearthandharvest", "hearthandharvest:tempting")
                    .build()
    );

    /** Allows the user to eat and drink even while their food bar is full. */
    public static final FoodEffect APPETIZING = register(
            FoodEffect.category("appetizing")
                    .or("fruitsdelight", "fruitsdelight:appetizing")
                    .build()
    );

    /** Grants extra friction, letting the user walk on ice and other slippery blocks normally. */
    public static final FoodEffect ASTRINGENT = register(
            FoodEffect.category("astringent")
                    .or("fruitsdelight", "fruitsdelight:astringent")
                    .build()
    );

    /** Grants nearby players Absorption. */
    public static final FoodEffect BALANCED = register(
            FoodEffect.category("balanced")
                    .or("herbalbrews", "herbalbrews:balanced")
                    .build()
    );

    /** Increases attack damage the lower the user's health is relative to their max. */
    public static final FoodEffect BERSERK = register(
            FoodEffect.category("berserk")
                    .or("runiclib", "runiclib:berserk")
                    .build()
    );

    /** Prevents the user from healing via natural regeneration. */
    public static final FoodEffect BLOOD_CLOT = register(
            FoodEffect.category("blood_clot")
                    .or("runiclib", "runiclib:blood_clot")
                    .build()
    );

    /** Grants nearby players temporary Absorption and Regeneration. */
    public static final FoodEffect BONDING = register(
            FoodEffect.category("bonding")
                    .or("herbalbrews", "herbalbrews:bonding")
                    .build()
    );

    /** Allows the user to see clearly while submerged in lava. */
    public static final FoodEffect BRIMSTONE_VISION = register(
            FoodEffect.category("brimstone_vision")
                    .or("runiclib", "runiclib:brimstone_vision")
                    .build()
    );

    /** Slightly increases all major stats. */
    public static final FoodEffect CAFFEINATED = register(
            FoodEffect.category("caffeinated")
                    .or("runiclib", "runiclib:caffeinated")
                    .build()
    );

    /** Reduces villager trading prices by ~10%. */
    public static final FoodEffect CHARISMA = register(
            FoodEffect.category("charisma")
                    .or("brewery", "brewery:pintcharisma")
                    .build()
    );

    /** Prevents the user from being inflicted with Blindness and Darkness. */
    public static final FoodEffect CLARITY = register(
            FoodEffect.category("clarity")
                    .or("hearthandharvest", "hearthandharvest:clarity")
                    .or("fruitsdelight", "fruitsdelight:brightening")
                    .build()
    );

    /** Sets nearby enemies on fire for a short time. */
    public static final FoodEffect COMBUSTION = register(
            FoodEffect.category("combustion")
                    .or("brewery", "brewery:combustion")
                    .build()
    );

    public static final FoodEffect COMFORT = register(
            FoodEffect.category("comfort")
                    .or("farmersdelight", "farmersdelight:comfort")
                    .build()
    );

    /** Drops a level of experience as orbs each second, repairing Mending gear without an external source. */
    public static final FoodEffect CYCLING = register(
            FoodEffect.category("cycling")
                    .or("fruitsdelight", "fruitsdelight:cycling")
                    .build()
    );

    /** Converts overflowing nutrition into saturation. */
    public static final FoodEffect DIGESTING = register(
            FoodEffect.category("digesting")
                    .or("fruitsdelight", "fruitsdelight:digesting")
                    .build()
    );

    /** Attacks have a small chance to launch a fireball. */
    public static final FoodEffect EXPLOSION = register(
            FoodEffect.category("explosion")
                    .or("brewery", "brewery:explosion")
                    .build()
    );

    /** Removes all negative status effects for the duration. */
    public static final FoodEffect FARMERS_BLESSING = register(
            FoodEffect.category("farmers_blessing")
                    .or("farm_and_charm", "farm_and_charm:farmers_blessing")
                    .build()
    );

    /** Combined sustenance + satiation super-buff. */
    public static final FoodEffect FEAST = register(
            FoodEffect.category("feast")
                    .or("farm_and_charm", "farm_and_charm:feast")
                    .build()
    );

    /** Grants temporary flight for a short duration. */
    public static final FoodEffect FLIGHT = register(
            FoodEffect.category("flight")
                    .or("brewery", "brewery:haley")
                    .build()
    );

    /** Increases Luck. */
    public static final FoodEffect FORTUNE = register(
            FoodEffect.category("fortune")
                    .or("herbalbrews", "herbalbrews:fortune")
                    .build()
    );

    /** Removes all negative effects and grants Luck +2. */
    public static final FoodEffect GRANDMAS_BLESSING = register(
            FoodEffect.category("grandmas_blessing")
                    .or("farm_and_charm", "farm_and_charm:grandmas_blessing")
                    .build()
    );

    /** Applies instant health to entities around the user. */
    public static final FoodEffect HEAL_AURA = register(
            FoodEffect.category("heal_aura")
                    .or("fruitsdelight", "fruitsdelight:heal_aura")
                    .build()
    );

    /** Allows the user to walk on lava. */
    public static final FoodEffect LAVA_WALKING = register(
            FoodEffect.category("lava_walking")
                    .or("runiclib", "runiclib:lava_walking")
                    .build()
    );

    /** Projectiles shot by the user pass through leaves. */
    public static final FoodEffect LEAF_PIERCING = register(
            FoodEffect.category("leaf_piercing")
                    .or("fruitsdelight", "fruitsdelight:leaf_piercing")
                    .build()
    );

    /** Drains health from nearby hostile entities and heals the user. */
    public static final FoodEffect LIFE_LEECH = register(
            FoodEffect.category("life_leech")
                    .or("herbalbrews", "herbalbrews:lifeleech")
                    .build()
    );

    /** Attacks have a small chance to strike the target with lightning. */
    public static final FoodEffect LIGHTNING = register(
            FoodEffect.category("lightning")
                    .or("brewery", "brewery:lightning_strike")
                    .build()
    );

    /** Increases eating and drinking speed. */
    public static final FoodEffect LOZENGE = register(
            FoodEffect.category("lozenge")
                    .or("fruitsdelight", "fruitsdelight:lozenge")
                    .build()
    );

    /** Increases mining speed based on depth. */
    public static final FoodEffect MINING = register(
            FoodEffect.category("mining")
                    .or("brewery",     "brewery:mining")
                    .or("herbalbrews", "herbalbrews:deeprush")
                    .build()
    );

    /** Creepers nearby will flee from you. */
    public static final FoodEffect MUSTARD = register(
            FoodEffect.category("mustard")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:mustard")
                    .build()
    );

    public static final FoodEffect NOURISHMENT = register(
            FoodEffect.category("nourishment")
                    .or("farmersdelight", "farmersdelight:nourishment")
                    .build()
    );

    /** Reduces enemy aggression; Endermen eye contact becomes safe. */
    public static final FoodEffect PACIFY = register(
            FoodEffect.category("pacify")
                    .or("brewery", "brewery:pacify")
                    .or("hearthandharvest", "hearthandharvest:pungent")
                    .build()
    );

    /** Melee hits pop fireworks and deal a little extra damage. */
    public static final FoodEffect PARTY_STARTER = register(
            FoodEffect.category("party_starter")
                    .or("brewery", "brewery:partystarter")
                    .build()
    );

    /** Nearby entities begin to glow. */
    public static final FoodEffect PERCEPTION = register(
            FoodEffect.category("perception")
                    .or("runiclib", "runiclib:perception")
                    .build()
    );

    /** Eating rotten flesh, raw chicken, poisonous potatoes, pufferfish, or spider eyes will not cause debuffs. */
    public static final FoodEffect PRESERVATION = register(
            FoodEffect.category("preservation")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:preservation")
                    .build()
    );

    /** Damages entities that collide with the user. */
    public static final FoodEffect PRICKLY = register(
            FoodEffect.category("prickly")
                    .or("hearthandharvest", "hearthandharvest:prickly")
                    .build()
    );

    /** Mobs around the user become hostile. */
    public static final FoodEffect RAGE_AURA = register(
            FoodEffect.category("rage_aura")
                    .or("fruitsdelight", "fruitsdelight:rage_aura")
                    .build()
    );

    /** Gain a stacking 5% attack speed boost (up to 4 stacks) upon dealing melee damage. */
    public static final FoodEffect RAGING = register(
            FoodEffect.category("raging")
                    .or("brewinandchewin", "brewinandchewin:raging")
                    .build()
    );

    /** Prevents the user from being inflicted with Poison and Wither. */
    public static final FoodEffect RECOVERING = register(
            FoodEffect.category("recovering")
                    .or("fruitsdelight", "fruitsdelight:recovering")
                    .build()
    );

    /** Next harvests do not consume durability and may yield extra drops. */
    public static final FoodEffect REFRESHED = register(
            FoodEffect.category("refreshed")
                    .or("candlelight", "candlelight:refreshed")
                    .build()
    );

    /** Prevents the user from being inflicted with Mining Fatigue. */
    public static final FoodEffect REFRESHING = register(
            FoodEffect.category("refreshing")
                    .or("fruitsdelight", "fruitsdelight:refreshing")
                    .build()
    );

    /** Periodically pushes enemies away from the player. */
    public static final FoodEffect REPULSION = register(
            FoodEffect.category("repulsion")
                    .or("brewery", "brewery:repulsion")
                    .build()
    );

    /** Phantoms flee or disappear. */
    public static final FoodEffect REST = register(
            FoodEffect.category("rest")
                    .or("create_confectionery", "create_confectionery:rest")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:sulfur")
                    .build()
    );

    /** Increases experience points gained while active. */
    public static final FoodEffect RESTED = register(
            FoodEffect.category("rested")
                    .or("farm_and_charm", "farm_and_charm:rested")
                    .build()
    );

    /** Hunger value acts as extra health; damage is absorbed by hunger first. */
    public static final FoodEffect SATIATED_SHIELD = register(
            FoodEffect.category("satiated_shield")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:satiated_shield")
                    .build()
    );

    /** Prevents hunger from draining too quickly by managing food exhaustion. */
    public static final FoodEffect SATIATION = register(
            FoodEffect.category("satiation")
                    .or("farm_and_charm", "farm_and_charm:satiation")
                    .build()
    );

    /** Allows the user to crawl into 1-block gaps while sneaking. */
    public static final FoodEffect SHRINKING = register(
            FoodEffect.category("shrinking")
                    .or("fruitsdelight", "fruitsdelight:shrinking")
                    .build()
    );

    /** Boats the user rides slide on any block as if it were ice. */
    public static final FoodEffect SLIDING = register(
            FoodEffect.category("sliding")
                    .or("fruitsdelight", "fruitsdelight:sliding")
                    .build()
    );

    /** Removes mining fatigue and slowness. */
    public static final FoodEffect STIMULATION = register(
            FoodEffect.category("stimulation")
                    .or("create_confectionery", "create_confectionery:stimulation")
                    .build()
    );

    /** Knockback resistance. */
    public static final FoodEffect STOUT_HEART = register(
            FoodEffect.category("stout_heart")
                    .or("brewery", "brewery:stoutheart")
                    .build()
    );

    /** Stacking speed buff — Movement Speed up to 5 stacks, then Attack Speed. */
    public static final FoodEffect SUGAR_RUSH = register(
            FoodEffect.category("sugar_rush")
                    .or("bakery", "bakery:sugar_rush")
                    .build()
    );

    /** Makes suspicious sand and gravel easier to find, and reveals suspicious stew effects. */
    public static final FoodEffect SUSPICIOUS_SMELL = register(
            FoodEffect.category("suspicious_smell")
                    .or("fruitsdelight", "fruitsdelight:suspicious_smell")
                    .build()
    );

    /** Restores hunger (or health when full) periodically. */
    public static final FoodEffect SUSTENANCE = register(
            FoodEffect.category("sustenance")
                    .or("farm_and_charm", "farm_and_charm:sustenance")
                    .build()
    );

    /** Extra saturation-based healing at any hunger level, on top of existing saturation healing. */
    public static final FoodEffect SWEET_HEART = register(
            FoodEffect.category("sweet_heart")
                    .or("brewinandchewin", "brewinandchewin:sweet_heart")
                    .build()
    );

    /** Prevents the user from being inflicted with Slowness. */
    public static final FoodEffect SWEETENING = register(
            FoodEffect.category("sweetening")
                    .or("fruitsdelight", "fruitsdelight:sweetening")
                    .build()
    );

    /** Melee attacks grant Absorption to the attacker. */
    public static final FoodEffect TOUCH_ABSORB = register(
            FoodEffect.category("touch_absorb")
                    .or("brewery", "brewery:protectivetouch")
                    .build()
    );

    /** Melee attacks directly heal the hit entity. */
    public static final FoodEffect TOUCH_HEAL = register(
            FoodEffect.category("touch_heal")
                    .or("brewery", "brewery:healingtouch")
                    .build()
    );

    /** Melee attacks apply Poison to the hit entity. */
    public static final FoodEffect TOUCH_POISON = register(
            FoodEffect.category("touch_poison")
                    .or("brewery", "brewery:toxictouch")
                    .build()
    );

    /** Melee attacks apply Regeneration to the hit entity. */
    public static final FoodEffect TOUCH_REGEN = register(
            FoodEffect.category("touch_regen")
                    .or("brewery", "brewery:renewingtouch")
                    .build()
    );

    /** Grants Absorption, Regeneration, and Damage Resistance together. */
    public static final FoodEffect TOUGH = register(
            FoodEffect.category("tough")
                    .or("herbalbrews", "herbalbrews:tough")
                    .build()
    );

    /** Increased movement speed on snow, ice, and powder snow. Prevents sinking into powder snow. */
    public static final FoodEffect TUNDRA_STRIDER = register(
            FoodEffect.category("tundra_strider")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:tundra_strider")
                    .build()
    );

    /** Running does not consume hunger. */
    public static final FoodEffect VIGOR = register(
            FoodEffect.category("vigor")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:vigor")
                    .build()
    );

    /** Reduces exhaustion over time (scales with level). */
    public static final FoodEffect VITALITY = register(
            FoodEffect.category("vitality")
                    .or("bakery", "bakery:vitality")
                    .build()
    );

    /** Slowly regenerate health when near heat sources such as stoves, campfires, and lava. */
    public static final FoodEffect WARMTH = register(
            FoodEffect.category("warmth")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:warmth")
                    .or("runiclib", "runiclib:pyromaniac")
                    .build()
    );

    /** Allows the user to walk on water. */
    public static final FoodEffect WATER_WALKING = register(
            FoodEffect.category("water_walking")
                    .or("runiclib", "runiclib:water_walking")
                    .build()
    );

    /** You will not become truly hungry while this effect is active. */
    public static final FoodEffect WELL_SERVED = register(
            FoodEffect.category("well_served")
                    .or("candlelight", "candlelight:well_served")
                    .build()
    );
}