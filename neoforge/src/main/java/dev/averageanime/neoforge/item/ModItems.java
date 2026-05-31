package dev.averageanime.neoforge.item;

import dev.averageanime.CommonClass;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.item.ItemBuilder;
import dev.averageanime.item.type.EffectDrink;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.item.type.EffectFood;
import dev.averageanime.registry.ItemRegistry;
import dev.averageanime.registry.type.Effect;
import dev.averageanime.registry.type.Item;
import dev.averageanime.util.Tooltips;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.averageanime.neoforge.CreateFood.LOGGER;
import static dev.averageanime.neoforge.item.ModTooltips.addTooltip;

@SuppressWarnings({"unused"})
public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CommonClass.MOD_ID);

    // ── Registration loop ─────────────────────────────────────────────────────

    static {
        // Force ModItemDefs class to initialize so ItemDef.ALL is populated (JLS §12.4.1)
        ItemRegistry.init();

        for (Item def : Item.ALL) {
            DeferredItem<net.minecraft.world.item.Item> deferred = ITEMS.register(def.id, () -> buildItem(def));
            def.bind(deferred); // DeferredItem<Item> implements Supplier<Item>
        }
    }

    // ── Item builder ──────────────────────────────────────────────────────────

    private static net.minecraft.world.item.Item buildItem(Item def) {
        return switch (def.category) {
            case FOOD -> effectFood(
                    foodProps(def.id, def.nutrition, def.saturation, false, null, def.effects),
                    def);
            case FAST_FOOD -> effectFood(
                    foodProps(def.id, def.nutrition, def.saturation, true, null, def.effects),
                    def);
            case BOWL_FOOD -> effectFood(
                    foodProps(def.id, def.nutrition, def.saturation, false, Items.BOWL, def.effects).stacksTo(16),
                    def);
            case BOWL_FOOD_CR -> effectFood(
                    foodProps(def.id, def.nutrition, def.saturation, false, Items.BOWL, def.effects).stacksTo(16).craftRemainder(Items.BOWL),
                    def);
            case STICK_FOOD -> effectFood(
                    foodProps(def.id, def.nutrition, def.saturation, true, Items.STICK, def.effects),
                    def);
            case STICK_FOOD_CR -> effectFood(
                    foodProps(def.id, def.nutrition, def.saturation, true, Items.STICK, def.effects).craftRemainder(Items.STICK),
                    def);
            case BOTTLE -> effectDrink(
                    foodProps(def.id, def.nutrition, def.saturation, false, Items.GLASS_BOTTLE, def.effects)
                            .stacksTo(16).craftRemainder(Items.GLASS_BOTTLE),
                    def);
            case PLAIN -> plainItem(def);
            case PLAIN_CR -> plainCrItem(def, Item.getById(def.remainderId).get());
            case INGREDIENT_BOTTLE -> new EffectDrink(
                    new net.minecraft.world.item.Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE));
            case INGREDIENT_BOWL -> new net.minecraft.world.item.Item(
                    new net.minecraft.world.item.Item.Properties().stacksTo(16).craftRemainder(Items.BOWL));
            case PIPING_BAG -> pipingBagItem(def);
        };
    }

    // ── FoodProperties builder (NeoForge: includes config-override logic) ─────

    private static net.minecraft.world.item.Item.Properties foodProps(String itemId, int nut, float sat, boolean fast,
                                                                      net.minecraft.world.item.Item converts, List<Effect> specs) {
        var b = new FoodProperties.Builder().nutrition(nut).saturationModifier(sat);
        if (fast) b.fast();
        if (converts != null) b.usingConvertsTo(converts);
        for (Effect s : specs) {
            if (s.isFoodEffect()) continue; // mod-dependent — deferred
            Holder<MobEffect> holder = s.rawEffect();
            if (holder == null) continue;
            b.effect(() -> {
                ItemEffectOverride override =
                        ModConfig.getItemEffectOverride(itemId, s.categoryOrEffectId());
                if (override != null) {
                    if (override.remove()) return new MobEffectInstance(holder, 0, override.amplifier());
                    return new MobEffectInstance(holder, override.duration(), override.amplifier());
                }
                return new MobEffectInstance(holder, s.duration, s.amplifier);
            }, 1.0f);
        }
        return new net.minecraft.world.item.Item.Properties().food(b.build());
    }

    // ── EffectFood / EffectDrink builders ────────────────────────────────────

    private static net.minecraft.world.item.Item effectFood(net.minecraft.world.item.Item.Properties fp, Item def) {
        var ids      = ItemBuilder.existingIds(def.effects);
        var deferred = ItemBuilder.deferredFx(def.effects);
        Tooltips.Tip tip      = def.tip;
        if (tip == null) return new EffectFood(fp, ids, deferred);
        return new EffectFood(fp, ids, deferred) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c,
                                                  @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip);
            }
        };
    }

    private static net.minecraft.world.item.Item effectDrink(net.minecraft.world.item.Item.Properties fp, Item def) {
        var ids      = ItemBuilder.existingIds(def.effects);
        var deferred = ItemBuilder.deferredFx(def.effects);
        Tooltips.Tip tip      = def.tip;
        if (tip == null) return new EffectDrink(fp, ids, deferred);
        return new EffectDrink(fp, ids, deferred) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c,
                                                  @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip);
            }
        };
    }

    // ── Plain item helpers ────────────────────────────────────────────────────

    private static net.minecraft.world.item.Item plainItem(Item def) {
        return ItemBuilder.plainItem(def, (l, t) -> addTooltip(l, t.compat(), t.keys()));
    }

    private static net.minecraft.world.item.Item plainCrItem(Item def, net.minecraft.world.item.Item remainder) {
        return ItemBuilder.plainCrItem(def, remainder, (l, t) -> addTooltip(l, t.compat(), t.keys()));
    }

    private static net.minecraft.world.item.Item pipingBagItem(Item def) {
        return ItemBuilder.pipingBagItem(def, ItemRegistry.PIPING_BAG.get(), (l, t) -> addTooltip(l, t.compat(), t.keys()));
    }

    // ── Tooltip helper ────────────────────────────────────────────────────────

    private static void doTip(List<Component> l, Tooltips.Tip tip) {
        ItemBuilder.doTip(l, tip, (list, t) -> addTooltip(list, t.compat(), t.keys()));
    }

    // ── Config-driven items (platform-specific) ───────────────────────────────

    private static void registerConfigItems() {
        var configFile = net.neoforged.fml.loading.FMLPaths.CONFIGDIR.get().resolve("createfood-client.toml");
        if (!java.nio.file.Files.exists(configFile)) return;
        try (var raw = com.electronwill.nightconfig.core.file.FileConfig.of(configFile.toFile())) {
            raw.load();
            List<String> entries = raw.getOrElse("items.item", List.of());
            for (String entry : entries) {
                String[] p = entry.split("\\|");
                if (p.length < 2) {
                    LOGGER.warn("Create: Food - Skipping invalid custom_item entry: {}", entry);
                    continue;
                }
                String name = p[0];
                String type = p[1].toLowerCase();
                // piping_bag requires a lazy reference to PIPING_BAG which isn't bound yet
                if (type.equals("piping_bag")) {
                    ITEMS.register(name, () -> new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties().stacksTo(2).craftRemainder(ItemRegistry.PIPING_BAG.get())));
                    continue;
                }
                net.minecraft.world.item.Item item = switch (type) {
                    case "plain"             -> new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties());
                    case "ingredient_bottle" -> new EffectDrink(new net.minecraft.world.item.Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE));
                    case "ingredient_bowl"   -> new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties().stacksTo(16).craftRemainder(Items.BOWL));
                    default -> {
                        if (p.length < 4) {
                            LOGGER.warn("Create: Food - Skipping invalid custom_item entry: {}", entry);
                            yield null;
                        }
                        int nut; float sat;
                        try {
                            nut = Integer.parseInt(p[2]);
                            sat = Float.parseFloat(p[3]);
                        } catch (NumberFormatException e) {
                            LOGGER.warn("Create: Food - Invalid nutrition/saturation in custom_item entry: {}", entry);
                            yield null;
                        }
                        var food = new FoodProperties.Builder().nutrition(nut).saturationModifier(sat);
                        yield switch (type) {
                            case "food"     -> new EffectFood(new net.minecraft.world.item.Item.Properties().food(food.build()));
                            case "fast_food"-> { food.fast(); yield new EffectFood(new net.minecraft.world.item.Item.Properties().food(food.build())); }
                            case "bowl"     -> { food.usingConvertsTo(Items.BOWL); yield new EffectFood(new net.minecraft.world.item.Item.Properties().food(food.build()).stacksTo(16)); }
                            case "bowl_cr"  -> { food.usingConvertsTo(Items.BOWL); yield new EffectFood(new net.minecraft.world.item.Item.Properties().food(food.build()).stacksTo(16).craftRemainder(Items.BOWL)); }
                            case "bottle"   -> { food.usingConvertsTo(Items.GLASS_BOTTLE); yield new EffectDrink(new net.minecraft.world.item.Item.Properties().food(food.build()).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)); }
                            case "stick"    -> { food.fast(); food.usingConvertsTo(Items.STICK); yield new EffectFood(new net.minecraft.world.item.Item.Properties().food(food.build())); }
                            case "stick_cr" -> { food.fast(); food.usingConvertsTo(Items.STICK); yield new EffectFood(new net.minecraft.world.item.Item.Properties().food(food.build()).craftRemainder(Items.STICK)); }
                            default -> { LOGGER.warn("Create: Food - Unknown type '{}' in custom_item entry: {}", type, entry); yield null; }
                        };
                    }
                };
                if (item == null) continue;
                ITEMS.register(name, () -> item);
            }
        } catch (Exception e) {
            LOGGER.warn("Create: Food - Failed to read custom_item from config", e);
        }
    }

    // ── Entry point ───────────────────────────────────────────────────────────

    public static void register(IEventBus eventBus) {
        LOGGER.info("Create: Food - Registering Items");
        registerConfigItems();
        ITEMS.register(eventBus);
    }
}
