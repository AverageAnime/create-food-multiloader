package dev.averageanime.item.effect;

import dev.averageanime.createfood.lib.effect.EffectChain;
import dev.averageanime.config.ConfigValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class EffectCategories {

    private EffectCategories() {}

    private static final List<EffectChain> ALL = new ArrayList<>();
    private static final Map<String, EffectChain> BY_NAME = new HashMap<>();

    private record SyntheticOverride(String overrideId, EffectChain effect) {}

    private static final ConcurrentHashMap<String, SyntheticOverride> SYNTHETIC_OVERRIDES =
            new ConcurrentHashMap<>();

    private static EffectChain register(EffectChain effect) {
        ALL.add(effect);
        BY_NAME.put(effect.getCategoryName(), effect);
        return effect;
    }

    public static Optional<EffectChain> getByName(String name) {
        String configOverride = ConfigValues.getCategoryEffectOverride(name);
        if (configOverride != null) {
            SyntheticOverride cached = SYNTHETIC_OVERRIDES.get(name);
            if (cached == null || !cached.overrideId().equals(configOverride)) {
                int colon = configOverride.indexOf(':');
                String modId = colon > 0 ? configOverride.substring(0, colon) : "minecraft";
                cached = new SyntheticOverride(configOverride, EffectChain.category(name)
                        .or(modId, configOverride)
                        .orAll(BY_NAME.get(name))
                        .build());
                SYNTHETIC_OVERRIDES.put(name, cached);
            }
            return Optional.of(cached.effect());
        }
        return Optional.ofNullable(BY_NAME.get(name));
    }

    /** Increases movement speed the lower the user's health is relative to their max. */
    public static final EffectChain ADRENALINE = register(
            EffectChain.category("adrenaline")
                    .or("runiclib", "runiclib:adrenaline")
                    .build()
    );

    /** Applies Weakness and Disgusted to surrounding mobs; Disgusted prevents animals from breeding. */
    public static final EffectChain ALIENATING = register(
            EffectChain.category("alienating")
                    .or("fruitsdelight", "fruitsdelight:alienating")
                    .build()
    );

    /** Nearby animals feel unusually friendly and stick around. */
    public static final EffectChain ANIMAL_CHARM = register(
            EffectChain.category("animal_charm")
                    .or("brewery", "brewery:snowwhite")
                    .or("hearthandharvest", "hearthandharvest:tempting")
                    .or("minecraft", "minecraft:luck")
                    .build()
    );

    /** Allows the user to eat and drink even while their food bar is full. */
    public static final EffectChain APPETIZING = register(
            EffectChain.category("appetizing")
                    .or("fruitsdelight", "fruitsdelight:appetizing")
                    .build()
    );

    /** Grants extra friction, letting the user walk on ice and other slippery blocks normally. */
    public static final EffectChain ASTRINGENT = register(
            EffectChain.category("astringent")
                    .or("fruitsdelight", "fruitsdelight:astringent")
                    .or("minecraft", "minecraft:slow_falling")
                    .build()
    );

    /** Grants nearby players Absorption. */
    public static final EffectChain BALANCED = register(
            EffectChain.category("balanced")
                    .or("herbalbrews", "herbalbrews:balanced")
                    .or("minecraft", "minecraft:absorption")
                    .build()
    );

    /** Increases attack damage the lower the user's health is relative to their max. */
    public static final EffectChain BERSERK = register(
            EffectChain.category("berserk")
                    .or("runiclib", "runiclib:berserk")
                    .build()
    );

    /** Prevents the user from healing via natural regeneration. */
    public static final EffectChain BLOOD_CLOT = register(
            EffectChain.category("blood_clot")
                    .or("runiclib", "runiclib:blood_clot")
                    .build()
    );

    /** Grants nearby players temporary Absorption and Regeneration. */
    public static final EffectChain BONDING = register(
            EffectChain.category("bonding")
                    .or("herbalbrews", "herbalbrews:bonding")
                    .or("minecraft", "minecraft:absorption")
                    .build()
    );

    /** Allows the user to see clearly while submerged in lava. */
    public static final EffectChain BRIMSTONE_VISION = register(
            EffectChain.category("brimstone_vision")
                    .or("runiclib", "runiclib:brimstone_vision")
                    .build()
    );

    /** Slightly increases all major stats. */
    public static final EffectChain CAFFEINATED = register(
            EffectChain.category("caffeinated")
                    .or("runiclib", "runiclib:caffeinated")
                    .build()
    );

    /** Reduces villager trading prices by ~10%. */
    public static final EffectChain CHARISMA = register(
            EffectChain.category("charisma")
                    .or("brewery", "brewery:pintcharisma")
                    .or("minecraft", "minecraft:hero_of_the_village")
                    .build()
    );

    /** Prevents the user from being inflicted with Blindness and Darkness. */
    public static final EffectChain CLARITY = register(
            EffectChain.category("clarity")
                    .or("hearthandharvest", "hearthandharvest:clarity")
                    .or("fruitsdelight", "fruitsdelight:brightening")
                    .build()
    );

    /** Sets nearby enemies on fire for a short time. */
    public static final EffectChain COMBUSTION = register(
            EffectChain.category("combustion")
                    .or("brewery", "brewery:combustion")
                    .or("minecraft", "minecraft:fire_resistance")
                    .build()
    );

    /** @deprecated Folded into Nourishment 1:1; delete after one release. */
    @Deprecated
    public static final EffectChain COMFORT = register(
            EffectChain.category("comfort")
                    .or("farmersdelight", "farmersdelight:nourishment")
                    .build()
    );

    /** Drops a level of experience as orbs each second, repairing Mending gear without an external source. */
    public static final EffectChain CYCLING = register(
            EffectChain.category("cycling")
                    .or("fruitsdelight", "fruitsdelight:cycling")
                    .build()
    );

    /** Converts overflowing nutrition into saturation. */
    public static final EffectChain DIGESTING = register(
            EffectChain.category("digesting")
                    .or("fruitsdelight", "fruitsdelight:digesting")
                    .build()
    );

    /** Attacks have a small chance to launch a fireball. */
    public static final EffectChain EXPLOSION = register(
            EffectChain.category("explosion")
                    .or("brewery", "brewery:explosion")
                    .or("minecraft", "minecraft:strength")
                    .build()
    );

    /** Removes all negative status effects for the duration. */
    public static final EffectChain FARMERS_BLESSING = register(
            EffectChain.category("farmers_blessing")
                    .or("farm_and_charm", "farm_and_charm:farmers_blessing")
                    .or("minecraft", "minecraft:luck")
                    .build()
    );

    /** Combined sustenance + satiation super-buff. */
    public static final EffectChain FEAST = register(
            EffectChain.category("feast")
                    .or("farm_and_charm", "farm_and_charm:feast")
                    .or("minecraft", "minecraft:absorption")
                    .build()
    );

    /** Grants temporary flight for a short duration. */
    public static final EffectChain FLIGHT = register(
            EffectChain.category("flight")
                    .or("brewery", "brewery:haley")
                    .build()
    );

    /** Increases Luck. */
    public static final EffectChain FORTUNE = register(
            EffectChain.category("fortune")
                    .or("herbalbrews", "herbalbrews:fortune")
                    .or("minecraft", "minecraft:luck")
                    .build()
    );

    /** Removes all negative effects and grants Luck +2. */
    public static final EffectChain GRANDMAS_BLESSING = register(
            EffectChain.category("grandmas_blessing")
                    .or("farm_and_charm", "farm_and_charm:grandmas_blessing")
                    .build()
    );

    /** Applies instant health to entities around the user. */
    public static final EffectChain HEAL_AURA = register(
            EffectChain.category("heal_aura")
                    .or("fruitsdelight", "fruitsdelight:heal_aura")
                    .build()
    );

    /** Allows the user to walk on lava. */
    public static final EffectChain LAVA_WALKING = register(
            EffectChain.category("lava_walking")
                    .or("runiclib", "runiclib:lava_walking")
                    .build()
    );

    /** Projectiles shot by the user pass through leaves. */
    public static final EffectChain LEAF_PIERCING = register(
            EffectChain.category("leaf_piercing")
                    .or("fruitsdelight", "fruitsdelight:leaf_piercing")
                    .build()
    );

    /** Drains health from nearby hostile entities and heals the user. */
    public static final EffectChain LIFE_LEECH = register(
            EffectChain.category("life_leech")
                    .or("herbalbrews", "herbalbrews:lifeleech")
                    .build()
    );

    /** Attacks have a small chance to strike the target with lightning. */
    public static final EffectChain LIGHTNING = register(
            EffectChain.category("lightning")
                    .or("brewery", "brewery:lightning_strike")
                    .build()
    );

    /** Increases eating and drinking speed. */
    public static final EffectChain LOZENGE = register(
            EffectChain.category("lozenge")
                    .or("fruitsdelight", "fruitsdelight:lozenge")
                    .or("minecraft", "minecraft:haste")
                    .build()
    );

    /** Increases mining speed based on depth. */
    public static final EffectChain MINING = register(
            EffectChain.category("mining")
                    .or("brewery",     "brewery:mining")
                    .or("herbalbrews", "herbalbrews:deeprush")
                    .or("minecraft", "minecraft:haste")
                    .build()
    );

    /** Creepers nearby will flee from you. */
    public static final EffectChain MUSTARD = register(
            EffectChain.category("mustard")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:mustard")
                    .build()
    );

    /** The base effect on almost every food in the mod. */
    public static final EffectChain NOURISHMENT = register(
            EffectChain.category("nourishment")
                    .or("farmersdelight", "farmersdelight:nourishment")
                    .or("minecraft", "minecraft:saturation")
                    .build()
    );

    /** Reduces enemy aggression; Endermen eye contact becomes safe. */
    public static final EffectChain PACIFY = register(
            EffectChain.category("pacify")
                    .or("brewery", "brewery:pacify")
                    .or("hearthandharvest", "hearthandharvest:pungent")
                    .or("minecraft", "minecraft:invisibility")
                    .build()
    );

    /** Melee hits pop fireworks and deal a little extra damage. */
    public static final EffectChain PARTY_STARTER = register(
            EffectChain.category("party_starter")
                    .or("brewery", "brewery:partystarter")
                    .build()
    );

    /** Nearby entities begin to glow. */
    public static final EffectChain PERCEPTION = register(
            EffectChain.category("perception")
                    .or("runiclib", "runiclib:perception")
                    .build()
    );

    /** Eating rotten flesh, raw chicken, poisonous potatoes, pufferfish, or spider eyes will not cause debuffs. */
    public static final EffectChain PRESERVATION = register(
            EffectChain.category("preservation")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:preservation")
                    .or("minecraft", "minecraft:resistance")
                    .build()
    );

    /** Damages entities that collide with the user. */
    public static final EffectChain PRICKLY = register(
            EffectChain.category("prickly")
                    .or("hearthandharvest", "hearthandharvest:prickly")
                    .build()
    );

    /** Mobs around the user become hostile. */
    public static final EffectChain RAGE_AURA = register(
            EffectChain.category("rage_aura")
                    .or("fruitsdelight", "fruitsdelight:rage_aura")
                    .build()
    );

    /** Gain a stacking 5% attack speed boost (up to 4 stacks) upon dealing melee damage. */
    public static final EffectChain RAGING = register(
            EffectChain.category("raging")
                    .or("brewinandchewin", "brewinandchewin:raging")
                    .or("minecraft", "minecraft:strength")
                    .build()
    );

    /** Prevents the user from being inflicted with Poison and Wither. */
    public static final EffectChain RECOVERING = register(
            EffectChain.category("recovering")
                    .or("fruitsdelight", "fruitsdelight:recovering")
                    .or("minecraft", "minecraft:regeneration")
                    .build()
    );

    /** Next harvests do not consume durability and may yield extra drops. */
    public static final EffectChain REFRESHED = register(
            EffectChain.category("refreshed")
                    .or("candlelight", "candlelight:refreshed")
                    .build()
    );

    /** Prevents the user from being inflicted with Mining Fatigue. */
    public static final EffectChain REFRESHING = register(
            EffectChain.category("refreshing")
                    .or("fruitsdelight", "fruitsdelight:refreshing")
                    .or("minecraft", "minecraft:haste")
                    .build()
    );

    /** Periodically pushes enemies away from the player. */
    public static final EffectChain REPULSION = register(
            EffectChain.category("repulsion")
                    .or("brewery", "brewery:repulsion")
                    .or("minecraft", "minecraft:invisibility")
                    .build()
    );

    /** Phantoms flee or disappear. */
    public static final EffectChain REST = register(
            EffectChain.category("rest")
                    .or("create_confectionery", "create_confectionery:rest")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:sulfur")
                    .or("minecraft", "minecraft:regeneration")
                    .build()
    );

    /** Increases experience points gained while active. */
    public static final EffectChain RESTED = register(
            EffectChain.category("rested")
                    .or("farm_and_charm", "farm_and_charm:rested")
                    .or("minecraft", "minecraft:luck")
                    .build()
    );

    /** Hunger value acts as extra health; damage is absorbed by hunger first. */
    public static final EffectChain SATIATED_SHIELD = register(
            EffectChain.category("satiated_shield")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:satiated_shield")
                    .or("minecraft", "minecraft:absorption")
                    .build()
    );

    /** Prevents hunger from draining too quickly by managing food exhaustion. */
    public static final EffectChain SATIATION = register(
            EffectChain.category("satiation")
                    .or("farm_and_charm", "farm_and_charm:satiation")
                    .build()
    );

    /** Allows the user to crawl into 1-block gaps while sneaking. */
    public static final EffectChain SHRINKING = register(
            EffectChain.category("shrinking")
                    .or("fruitsdelight", "fruitsdelight:shrinking")
                    .build()
    );

    /** Boats the user rides slide on any block as if it were ice. */
    public static final EffectChain SLIDING = register(
            EffectChain.category("sliding")
                    .or("fruitsdelight", "fruitsdelight:sliding")
                    .or("minecraft", "minecraft:jump_boost")
                    .build()
    );

    /** Removes mining fatigue and slowness. */
    public static final EffectChain STIMULATION = register(
            EffectChain.category("stimulation")
                    .or("create_confectionery", "create_confectionery:stimulation")
                    .or("minecraft", "minecraft:haste")
                    .build()
    );

    /** Knockback resistance. */
    public static final EffectChain STOUT_HEART = register(
            EffectChain.category("stout_heart")
                    .or("brewery", "brewery:stoutheart")
                    .or("minecraft", "minecraft:resistance")
                    .build()
    );

    /** Stacking speed buff — Movement Speed up to 5 stacks, then Attack Speed. */
    public static final EffectChain SUGAR_RUSH = register(
            EffectChain.category("sugar_rush")
                    .or("bakery", "bakery:sugar_rush")
                    .or("minecraft", "minecraft:speed")
                    .build()
    );

    /** Makes suspicious sand and gravel easier to find, and reveals suspicious stew effects. */
    public static final EffectChain SUSPICIOUS_SMELL = register(
            EffectChain.category("suspicious_smell")
                    .or("fruitsdelight", "fruitsdelight:suspicious_smell")
                    .build()
    );

    /** Restores hunger (or health when full) periodically. */
    public static final EffectChain SUSTENANCE = register(
            EffectChain.category("sustenance")
                    .or("farm_and_charm", "farm_and_charm:sustenance")
                    .or("minecraft", "minecraft:saturation")
                    .build()
    );

    /** Extra saturation-based healing at any hunger level, on top of existing saturation healing. */
    public static final EffectChain SWEET_HEART = register(
            EffectChain.category("sweet_heart")
                    .or("brewinandchewin", "brewinandchewin:sweet_heart")
                    .or("minecraft", "minecraft:regeneration")
                    .build()
    );

    /** Prevents the user from being inflicted with Slowness. */
    public static final EffectChain SWEETENING = register(
            EffectChain.category("sweetening")
                    .or("fruitsdelight", "fruitsdelight:sweetening")
                    .or("minecraft", "minecraft:speed")
                    .build()
    );

    /** Melee attacks grant Absorption to the attacker. */
    public static final EffectChain TOUCH_ABSORB = register(
            EffectChain.category("touch_absorb")
                    .or("brewery", "brewery:protectivetouch")
                    .build()
    );

    /** Melee attacks directly heal the hit entity. */
    public static final EffectChain TOUCH_HEAL = register(
            EffectChain.category("touch_heal")
                    .or("brewery", "brewery:healingtouch")
                    .build()
    );

    /** Melee attacks apply Poison to the hit entity. */
    public static final EffectChain TOUCH_POISON = register(
            EffectChain.category("touch_poison")
                    .or("brewery", "brewery:toxictouch")
                    .build()
    );

    /** Melee attacks apply Regeneration to the hit entity. */
    public static final EffectChain TOUCH_REGEN = register(
            EffectChain.category("touch_regen")
                    .or("brewery", "brewery:renewingtouch")
                    .build()
    );

    /** Grants Absorption, Regeneration, and Damage Resistance together. */
    public static final EffectChain TOUGH = register(
            EffectChain.category("tough")
                    .or("herbalbrews", "herbalbrews:tough")
                    .or("minecraft", "minecraft:resistance")
                    .build()
    );

    /** Increased movement speed on snow, ice, and powder snow. Prevents sinking into powder snow. */
    public static final EffectChain TUNDRA_STRIDER = register(
            EffectChain.category("tundra_strider")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:tundra_strider")
                    .or("minecraft", "minecraft:speed")
                    .build()
    );

    /** Running does not consume hunger. */
    public static final EffectChain VIGOR = register(
            EffectChain.category("vigor")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:vigor")
                    .or("minecraft", "minecraft:speed")
                    .build()
    );

    /** Reduces exhaustion over time (scales with level). */
    public static final EffectChain VITALITY = register(
            EffectChain.category("vitality")
                    .or("bakery", "bakery:vitality")
                    .or("minecraft", "minecraft:saturation")
                    .build()
    );

    /** Slowly regenerate health when near heat sources such as stoves, campfires, and lava. */
    public static final EffectChain WARMTH = register(
            EffectChain.category("warmth")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:warmth")
                    .or("runiclib", "runiclib:pyromaniac")
                    .or("minecraft", "minecraft:fire_resistance")
                    .build()
    );

    /** Allows the user to walk on water. */
    public static final EffectChain WATER_WALKING = register(
            EffectChain.category("water_walking")
                    .or("runiclib", "runiclib:water_walking")
                    .build()
    );

    /** You will not become truly hungry while this effect is active. */
    public static final EffectChain WELL_SERVED = register(
            EffectChain.category("well_served")
                    .or("candlelight", "candlelight:well_served")
                    .or("minecraft", "minecraft:saturation")
                    .build()
    );
}