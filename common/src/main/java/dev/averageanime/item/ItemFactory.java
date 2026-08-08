package dev.averageanime.item;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.client.tooltip.ItemTooltips;
import dev.averageanime.item.effect.EffectSpecs;
import dev.averageanime.item.type.EffectDrink;
import dev.averageanime.item.type.EffectFood;
import dev.averageanime.item.type.EffectFood.DeferredFx;
import dev.averageanime.registry.ItemLookup;
import dev.averageanime.registry.ItemRegistry;
import dev.averageanime.registry.type.EffectEntry;
import dev.averageanime.registry.type.ItemEntry;
import dev.averageanime.util.Tooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class ItemFactory {

    public interface Hooks {
        Supplier<net.minecraft.world.item.Item> register(String id, Supplier<net.minecraft.world.item.Item> factory);

        EffectFood createEffectFood(Properties props, Set<String> ids, List<DeferredFx> deferred, Tooltips.TooltipSpec tip);

        void addFoodEffect(FoodProperties.Builder builder, String itemId, EffectEntry spec);
    }

    private static final BiConsumer<List<Component>, Tooltips.TooltipSpec> ADD_TOOLTIP =
            (l, t) -> ItemTooltips.addTooltip(l, t.compat(), t.keys());

    private static final Set<String> CONFIG_FOOD_TYPES =
            Set.of("food", "fast_food", "bowl", "bowl_cr", "bottle", "stick", "stick_cr");

    private ItemFactory() {}

    public static void registerAll(Hooks hooks) {
        ItemRegistry.init();
        for (ItemEntry def : ItemEntry.ALL) {
            def.bind(hooks.register(def.id, () -> build(def, hooks)));
        }
    }

    private static net.minecraft.world.item.Item build(ItemEntry def, Hooks hooks) {
        return switch (def.category) {
            case FOOD -> effectFood(foodProps(def, false, null, hooks), def, hooks);
            case FAST_FOOD -> effectFood(foodProps(def, true, null, hooks), def, hooks);
            case BOWL_FOOD -> effectFood(foodProps(def, false, Items.BOWL, hooks).stacksTo(16), def, hooks);
            case BOWL_FOOD_CR -> effectFood(foodProps(def, false, Items.BOWL, hooks).stacksTo(16).craftRemainder(Items.BOWL), def, hooks);
            case STICK_FOOD -> effectFood(foodProps(def, true, Items.STICK, hooks), def, hooks);
            case STICK_FOOD_CR -> effectFood(foodProps(def, true, Items.STICK, hooks).craftRemainder(Items.STICK), def, hooks);
            case BOTTLE -> effectDrink(foodProps(def, false, Items.GLASS_BOTTLE, hooks).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), def);
            case PLAIN -> plainItem(def, new Properties());
            case PLAIN_CR -> plainItem(def, new Properties().craftRemainder(ItemLookup.byModId(def.remainderId).get()));
            case INGREDIENT_BOTTLE -> new EffectDrink(new Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE));
            case INGREDIENT_BOWL -> new net.minecraft.world.item.Item(new Properties().stacksTo(16).craftRemainder(Items.BOWL));
            case PIPING_BAG -> plainItem(def, new Properties().stacksTo(2).craftRemainder(ItemLookup.byModId("piping_bag").get()));
        };
    }

    private static net.minecraft.world.item.Item plainItem(ItemEntry def, Properties props) {
        Tooltips.TooltipSpec tip = def.tip;
        if (tip == null) return new net.minecraft.world.item.Item(props);
        return new net.minecraft.world.item.Item(props) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c,
                                                  @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f);
                ADD_TOOLTIP.accept(l, tip);
            }
        };
    }

    private static Properties foodProps(ItemEntry def, boolean fast, net.minecraft.world.item.Item converts, Hooks hooks) {
        var b = new FoodProperties.Builder().nutrition(def.nutrition).saturationModifier(def.saturation);
        if (fast) b.fast();
        if (converts != null) b.usingConvertsTo(converts);
        for (EffectEntry s : def.effects) {
            if (s.isFoodEffect()) continue; // mod-dependent — deferred
            if (s.rawEffect() == null) continue;
            hooks.addFoodEffect(b, def.id, s);
        }
        return new Properties().food(b.build());
    }

    private static net.minecraft.world.item.Item effectFood(Properties fp, ItemEntry def, Hooks hooks) {
        return hooks.createEffectFood(fp, EffectSpecs.existingIds(def.effects), EffectSpecs.deferredFx(def.effects), def.tip);
    }

    private static net.minecraft.world.item.Item effectDrink(Properties fp, ItemEntry def) {
        return new EffectDrink(fp, EffectSpecs.existingIds(def.effects), EffectSpecs.deferredFx(def.effects), def.tip);
    }

    // ── Config-driven items ────────────────────────────────────────────────

    public static void registerConfigItems(Hooks hooks, List<String> entries) {
        for (String entry : entries) {
            String[] p = entry.split("\\|");
            if (p.length < 2) {
                CreateFoodCommon.LOGGER.warn("Create: Food - Skipping invalid custom_item entry: {}", entry);
                continue;
            }
            String name = p[0];
            String type = p[1].toLowerCase();
            switch (type) {
                case "plain", "ingredient_bottle", "ingredient_bowl", "piping_bag" ->
                        hooks.register(name, () -> buildSimpleConfigItem(type));
                case "plain_cr" -> {
                    if (p.length < 3 || p[2].isBlank()) {
                        CreateFoodCommon.LOGGER.warn("Create: Food - Skipping custom_item entry missing remainder item: {}", entry);
                        continue;
                    }
                    Supplier<net.minecraft.world.item.Item> remainder = ItemLookup.byFullId(p[2]);
                    hooks.register(name, () ->
                            new net.minecraft.world.item.Item(new Properties().craftRemainder(remainder.get())));
                }
                default -> {
                    if (p.length < 4) {
                        CreateFoodCommon.LOGGER.warn("Create: Food - Skipping invalid custom_item entry: {}", entry);
                        continue;
                    }
                    int nut;
                    float sat;
                    try {
                        nut = Integer.parseInt(p[2]);
                        sat = Float.parseFloat(p[3]);
                    } catch (NumberFormatException e) {
                        CreateFoodCommon.LOGGER.warn("Create: Food - Invalid nutrition/saturation in custom_item entry: {}", entry);
                        continue;
                    }
                    if (!CONFIG_FOOD_TYPES.contains(type)) {
                        CreateFoodCommon.LOGGER.warn("Create: Food - Unknown type '{}' in custom_item entry: {}", type, entry);
                        continue;
                    }
                    hooks.register(name, () -> buildFoodConfigItem(hooks, type, nut, sat));
                }
            }
        }
    }

    private static net.minecraft.world.item.Item buildSimpleConfigItem(String type) {
        return switch (type) {
            case "plain" -> new net.minecraft.world.item.Item(new Properties());
            case "ingredient_bottle" -> new EffectDrink(new Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE));
            case "ingredient_bowl" -> new net.minecraft.world.item.Item(new Properties().stacksTo(16).craftRemainder(Items.BOWL));
            case "piping_bag" -> new net.minecraft.world.item.Item(new Properties().stacksTo(2).craftRemainder(ItemLookup.byModId("piping_bag").get()));
            default -> throw new IllegalStateException("Unhandled simple config item type: " + type);
        };
    }

    private static net.minecraft.world.item.Item buildFoodConfigItem(Hooks hooks, String type, int nut, float sat) {
        var food = new FoodProperties.Builder().nutrition(nut).saturationModifier(sat);
        return switch (type) {
            case "food" -> hooks.createEffectFood(new Properties().food(food.build()), Set.of(), List.of(), null);
            case "fast_food" -> { food.fast(); yield hooks.createEffectFood(new Properties().food(food.build()), Set.of(), List.of(), null); }
            case "bowl" -> { food.usingConvertsTo(Items.BOWL); yield hooks.createEffectFood(new Properties().food(food.build()).stacksTo(16), Set.of(), List.of(), null); }
            case "bowl_cr" -> { food.usingConvertsTo(Items.BOWL); yield hooks.createEffectFood(new Properties().food(food.build()).stacksTo(16).craftRemainder(Items.BOWL), Set.of(), List.of(), null); }
            case "bottle" -> { food.usingConvertsTo(Items.GLASS_BOTTLE); yield new EffectDrink(new Properties().food(food.build()).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)); }
            case "stick" -> { food.fast(); food.usingConvertsTo(Items.STICK); yield hooks.createEffectFood(new Properties().food(food.build()), Set.of(), List.of(), null); }
            case "stick_cr" -> { food.fast(); food.usingConvertsTo(Items.STICK); yield hooks.createEffectFood(new Properties().food(food.build()).craftRemainder(Items.STICK), Set.of(), List.of(), null); }
            default -> throw new IllegalStateException("Unhandled food config item type: " + type);
        };
    }
}
