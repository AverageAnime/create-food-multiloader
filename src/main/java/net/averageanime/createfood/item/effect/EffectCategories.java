package net.averageanime.createfood.item.effect;

import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class EffectCategories {

    private EffectCategories() {}

    private static final List<FoodEffect> ALL = new ArrayList<>();
    private static final Map<String, FoodEffect> BY_NAME = new HashMap<>();

    private static FoodEffect register(FoodEffect effect) {
        ALL.add(effect);
        BY_NAME.put(effect.getCategoryName(), effect);
        return effect;
    }

    public static Optional<FoodEffect> getByName(String name) {
        try {
            String configOverride = ConfigLogic.getCategoryEffectOverride(
                    name, CreateFoodConfig.SERVER.categoryOverrides.get());
            if (configOverride != null) {
                FoodEffect synthetic = FoodEffect.category(name)
                        .or("minecraft", configOverride)
                        .build();
                return Optional.of(synthetic);
            }
        } catch (Exception ignored) {
            // config not yet loaded or server-side not available
        }
        return Optional.ofNullable(BY_NAME.get(name));
    }

    public static final FoodEffect COMFORT = register(
            FoodEffect.category("comfort")
                    .or("farmersdelight", "farmersdelight:comfort")
                    .build()
    );

    public static final FoodEffect NOURISHMENT = register(
            FoodEffect.category("nourishment")
                    .or("farmersdelight", "farmersdelight:nourishment")
                    .build()
    );

    public static final FoodEffect VITALITY = register(
            FoodEffect.category("vitality")
                    .or("bakery", "bakery:vitality")
                    .build()
    );

    public static final FoodEffect SATIATION = register(
            FoodEffect.category("satiation")
                    .or("farm_and_charm", "farm_and_charm:satiation")
                    .build()
    );

    public static final FoodEffect SUSTENANCE = register(
            FoodEffect.category("sustenance")
                    .or("farm_and_charm", "farm_and_charm:sustenance")
                    .build()
    );

    public static final FoodEffect FEAST = register(
            FoodEffect.category("feast")
                    .or("farm_and_charm", "farm_and_charm:feast")
                    .build()
    );

    public static final FoodEffect SUGAR_RUSH = register(
            FoodEffect.category("sugar_rush")
                    .or("bakery", "bakery:sugar_rush")
                    .build()
    );

    public static final FoodEffect RESTED = register(
            FoodEffect.category("rested")
                    .or("farm_and_charm", "farm_and_charm:rested")
                    .build()
    );

    public static final FoodEffect FARMERS_BLESSING = register(
            FoodEffect.category("farmers_blessing")
                    .or("farm_and_charm", "farm_and_charm:farmers_blessing")
                    .build()
    );

    public static final FoodEffect GRANDMAS_BLESSING = register(
            FoodEffect.category("grandmas_blessing")
                    .or("farm_and_charm", "farm_and_charm:grandmas_blessing")
                    .build()
    );

    public static final FoodEffect STOUT_HEART = register(
            FoodEffect.category("stout_heart")
                    .or("brewery", "brewery:stoutheart")
                    .build()
    );

    public static final FoodEffect TOUCH_POISON = register(
            FoodEffect.category("touch_poison")
                    .or("brewery", "brewery:toxictouch")
                    .build()
    );

    public static final FoodEffect TOUCH_REGEN = register(
            FoodEffect.category("touch_regen")
                    .or("brewery", "brewery:renewingtouch")
                    .build()
    );

    public static final FoodEffect TOUCH_ABSORB = register(
            FoodEffect.category("touch_absorb")
                    .or("brewery", "brewery:protectivetouch")
                    .build()
    );

    public static final FoodEffect TOUCH_HEAL = register(
            FoodEffect.category("touch_heal")
                    .or("brewery", "brewery:healingtouch")
                    .build()
    );

    public static final FoodEffect COMBUSTION = register(
            FoodEffect.category("combustion")
                    .or("brewery", "brewery:combustion")
                    .build()
    );

    public static final FoodEffect EXPLOSION = register(
            FoodEffect.category("explosion")
                    .or("brewery", "brewery:explosion")
                    .build()
    );

    public static final FoodEffect REPULSION = register(
            FoodEffect.category("repulsion")
                    .or("brewery", "brewery:repulsion")
                    .build()
    );

    public static final FoodEffect LIGHTNING = register(
            FoodEffect.category("lightning")
                    .or("brewery", "brewery:lightning_strike")
                    .build()
    );

    public static final FoodEffect PARTY_STARTER = register(
            FoodEffect.category("party_starter")
                    .or("brewery", "brewery:partystarter")
                    .build()
    );

    public static final FoodEffect FLIGHT = register(
            FoodEffect.category("flight")
                    .or("brewery", "brewery:haley")
                    .build()
    );

    public static final FoodEffect MINING = register(
            FoodEffect.category("mining")
                    .or("brewery",     "brewery:mining")
                    .or("herbalbrews", "herbalbrews:deeprush")
                    .build()
    );

    public static final FoodEffect PACIFY = register(
            FoodEffect.category("pacify")
                    .or("brewery", "brewery:pacify")
                    .build()
    );

    public static final FoodEffect CHARISMA = register(
            FoodEffect.category("charisma")
                    .or("brewery", "brewery:pintcharisma")
                    .build()
    );

    public static final FoodEffect ANIMAL_CHARM = register(
            FoodEffect.category("animal_charm")
                    .or("brewery", "brewery:snowwhite")
                    .build()
    );

    public static final FoodEffect BALANCED = register(
            FoodEffect.category("balanced")
                    .or("herbalbrews", "herbalbrews:balanced")
                    .build()
    );

    public static final FoodEffect BONDING = register(
            FoodEffect.category("bonding")
                    .or("herbalbrews", "herbalbrews:bonding")
                    .build()
    );

    public static final FoodEffect FORTUNE = register(
            FoodEffect.category("fortune")
                    .or("herbalbrews", "herbalbrews:fortune")
                    .build()
    );

    public static final FoodEffect TOUGH = register(
            FoodEffect.category("tough")
                    .or("herbalbrews", "herbalbrews:tough")
                    .build()
    );

    public static final FoodEffect LIFE_LEECH = register(
            FoodEffect.category("life_leech")
                    .or("herbalbrews", "herbalbrews:lifeleech")
                    .build()
    );

    public static final FoodEffect TUNDRA_STRIDER = register(
            FoodEffect.category("tundra_strider")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:tundra_strider")
                    .build()
    );

    public static final FoodEffect WARMTH = register(
            FoodEffect.category("warmth")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:warmth")
                    .or("runiclib", "runiclib:pyromaniac")
                    .build()
    );

    public static final FoodEffect SATIATED_SHIELD = register(
            FoodEffect.category("satiated_shield")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:satiated_shield")
                    .build()
    );

    public static final FoodEffect VIGOR = register(
            FoodEffect.category("vigor")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:vigor")
                    .build()
    );

    public static final FoodEffect REST = register(
            FoodEffect.category("rest")
                    .or("create_confectionary", "create_confectionary:rest")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:sulfur")
                    .build()
    );

    public static final FoodEffect MUSTARD = register(
            FoodEffect.category("mustard")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:mustard")
                    .build()
    );

    public static final FoodEffect PRESERVATION = register(
            FoodEffect.category("preservation")
                    .or("kaleidoscope_cookery", "kaleidoscope_cookery:preservation")
                    .build()
    );

    public static final FoodEffect RAGING = register(
            FoodEffect.category("raging")
                    .or("brewinandchewin", "brewinandchewin:raging")
                    .build()
    );

    public static final FoodEffect SWEET_HEART = register(
            FoodEffect.category("sweet_heart")
                    .or("brewinandchewin", "brewinandchewin:sweet_heart")
                    .build()
    );

    public static final FoodEffect REFRESHED = register(
            FoodEffect.category("refreshed")
                    .or("candlelight", "candlelight:refreshed")
                    .build()
    );

    public static final FoodEffect WELL_SERVED = register(
            FoodEffect.category("well_served")
                    .or("candlelight", "candlelight:well_served")
                    .build()
    );

    public static final FoodEffect STIMULATION = register(
            FoodEffect.category("stimulation")
                    .or("create_confectionary", "create_confectionary:stimulation")
                    .build()
    );

    public static final FoodEffect ADRENALINE = register(
            FoodEffect.category("adrenaline")
                    .or("runiclib", "runiclib:adrenaline")
                    .build()
    );

    public static final FoodEffect BERSERK = register(
            FoodEffect.category("berserk")
                    .or("runiclib", "runiclib:berserk")
                    .build()
    );

    public static final FoodEffect BLOOD_CLOT = register(
            FoodEffect.category("blood_clot")
                    .or("runiclib", "runiclib:blood_clot")
                    .build()
    );

    public static final FoodEffect BRIMSTONE_VISION = register(
            FoodEffect.category("brimstone_vision")
                    .or("runiclib", "runiclib:brimstone_vision")
                    .build()
    );

    public static final FoodEffect CAFFEINATED = register(
            FoodEffect.category("caffeinated")
                    .or("runiclib", "runiclib:caffeinated")
                    .build()
    );

    public static final FoodEffect LAVA_WALKING = register(
            FoodEffect.category("lava_walking")
                    .or("runiclib", "runiclib:lava_walking")
                    .build()
    );

    public static final FoodEffect PERCEPTION = register(
            FoodEffect.category("perception")
                    .or("runiclib", "runiclib:perception")
                    .build()
    );

    public static final FoodEffect WATER_WALKING = register(
            FoodEffect.category("water_walking")
                    .or("runiclib", "runiclib:water_walking")
                    .build()
    );
}
