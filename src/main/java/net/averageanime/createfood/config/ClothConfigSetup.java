package net.averageanime.createfood.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

import java.util.ArrayList;
import java.util.List;

public final class ClothConfigSetup {
    private ClothConfigSetup() {}

    public static void register() {
        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> build(parent))
        );
    }

    static Screen build(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(t("createfood.config.title"))
            .setSavingRunnable(() -> {
                CreateFoodConfig.CLIENT_SPEC.save();
                try {
                    // SERVER config only has a backing file when a world is loaded
                    CreateFoodConfig.SERVER_SPEC.save();
                } catch (Exception ignored) {}
            });

        ConfigEntryBuilder eb = builder.entryBuilder();
        addClient(builder, eb);
        addServerItems(builder, eb);
        addServerInteractions(builder, eb);
        addServerDisplay(builder, eb);
        addServerStorage(builder, eb);

        return builder.build();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static Component t(String key) {
        return Component.translatable(key);
    }

    // ── Server: Items ─────────────────────────────────────────────────────────

    private static void addServerItems(ConfigBuilder b, ConfigEntryBuilder eb) {
        ConfigCategory cat = b.getOrCreateCategory(t("createfood.config.server_items"));
        CreateFoodConfig.Server s = CreateFoodConfig.SERVER;

        cat.addEntry(eb.startStrList(t("createfood.config.general.custom_item"), new ArrayList<>(s.customItem.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.customItem.set(v))
            .build());
        cat.addEntry(eb.startStrList(t("createfood.config.general.custom_block"), new ArrayList<>(s.customBlock.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.customBlock.set(v))
            .build());
        cat.addEntry(eb.startStrList(t("createfood.config.general.custom_fluid"), new ArrayList<>(s.customFluid.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.customFluid.set(v))
            .build());

        var nutritionCat = eb.startSubCategory(t("createfood.config.nutrition"));
        nutritionCat.add(eb.startStrList(t("createfood.config.nutrition.nutrition_saturation"), new ArrayList<>(s.nutritionSaturation.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.nutritionSaturation.set(v))
            .build());
        cat.addEntry(nutritionCat.build());

        var effectsCat = eb.startSubCategory(t("createfood.config.effects"));
        effectsCat.add(eb.startStrList(t("createfood.config.effects.category_overrides"), new ArrayList<>(s.categoryOverrides.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.categoryOverrides.set(v))
            .build());
        effectsCat.add(eb.startStrList(t("createfood.config.effects.item_overrides"), new ArrayList<>(s.itemOverrides.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.itemOverrides.set(v))
            .build());
        cat.addEntry(effectsCat.build());

        var remaindersCat = eb.startSubCategory(t("createfood.config.remainders"));
        remaindersCat.add(eb.startBooleanToggle(t("createfood.config.remainders.enable_egg_impact_remainder"), s.enableEggImpactRemainder.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.enableEggImpactRemainder.set(v))
            .build());
        remaindersCat.add(eb.startStrList(t("createfood.config.remainders.crafting_remainders"), new ArrayList<>(s.craftingRemainders.get()))
            .setDefaultValue(List.of("minecraft:egg|createfood:eggshell"))
            .setSaveConsumer(v -> s.craftingRemainders.set(v))
            .build());
        cat.addEntry(remaindersCat.build());
    }

    // ── Server: Interactions ──────────────────────────────────────────────────

    private static final List<String> DEFAULT_FILTER_INTERACTIONS = List.of(
        "createfood:cloth_filter_egg|minecraft:glass_bottle|createfood:cloth_filter_egg_yolk|createfood:egg_whites_bottle",
        "createfood:cloth_filter_egg_yolk|none|createfood:cloth_filter|createfood:egg_yolk",
        "createfood:cloth_filter_cacao_mass|minecraft:bucket|createfood:cloth_filter_pressed_cocoa|createfood:cacao_butter_bucket",
        "createfood:cloth_filter_pressed_cocoa|none|createfood:cloth_filter|createfood:pressed_cocoa"
    );

    private static void addServerInteractions(ConfigBuilder b, ConfigEntryBuilder eb) {
        ConfigCategory cat = b.getOrCreateCategory(t("createfood.config.interactions"));
        CreateFoodConfig.Server s = CreateFoodConfig.SERVER;

        cat.addEntry(eb.startBooleanToggle(t("createfood.config.general.enable_pumpkin_pie_placement"), s.enablePumpkinPiePlacement.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.enablePumpkinPiePlacement.set(v))
            .build());
        cat.addEntry(eb.startBooleanToggle(t("createfood.config.filter_interactions.enable_filter_interactions"), s.enableFilterInteractions.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.enableFilterInteractions.set(v))
            .build());
        cat.addEntry(eb.startStrList(t("createfood.config.filter_interactions.interactions"), new ArrayList<>(s.filterInteractions.get()))
            .setDefaultValue(DEFAULT_FILTER_INTERACTIONS)
            .setSaveConsumer(v -> s.filterInteractions.set(v))
            .build());

        var handcraftingCat = eb.startSubCategory(t("createfood.config.handcrafting"));
        handcraftingCat.add(eb.startBooleanToggle(t("createfood.config.handcrafting.enable_handcrafting"), s.enableHandcrafting.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.enableHandcrafting.set(v))
            .build());
        handcraftingCat.add(eb.startBooleanToggle(t("createfood.config.handcrafting.allow_single"), s.handcraftingAllowSingle.get())
            .setDefaultValue(false)
            .setSaveConsumer(v -> s.handcraftingAllowSingle.set(v))
            .build());
        handcraftingCat.add(eb.startBooleanToggle(t("createfood.config.handcrafting.particles"), s.handcraftingParticles.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.handcraftingParticles.set(v))
            .build());
        handcraftingCat.add(eb.startStrList(t("createfood.config.handcrafting.exclude"), new ArrayList<>(s.handcraftExclude.get()))
            .setDefaultValue(List.of("item:createfood:egg_yolk", "tag:forge:tools"))
            .setSaveConsumer(v -> s.handcraftExclude.set(v))
            .build());
        handcraftingCat.add(eb.startStrList(t("createfood.config.handcrafting.filter"), new ArrayList<>(s.handcraftingFilter.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.handcraftingFilter.set(v))
            .build());
        cat.addEntry(handcraftingCat.build());
    }

    // ── Server: Display ───────────────────────────────────────────────────────

    private static void addServerDisplay(ConfigBuilder b, ConfigEntryBuilder eb) {
        ConfigCategory cat = b.getOrCreateCategory(t("createfood.config.display"));
        CreateFoodConfig.Server s = CreateFoodConfig.SERVER;
        cat.addEntry(eb.startBooleanToggle(t("createfood.config.display.enable_generic_plates"), s.enableGenericPlates.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.enableGenericPlates.set(v))
            .build());
        cat.addEntry(eb.startBooleanToggle(t("createfood.config.display.enable_cutting_board"), s.enableCuttingBoard.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.enableCuttingBoard.set(v))
            .build());
        cat.addEntry(eb.startStrList(t("createfood.config.display.exclude"), new ArrayList<>(s.genericDisplayExclude.get()))
            .setDefaultValue(List.of("tag:forge:tools"))
            .setSaveConsumer(v -> s.genericDisplayExclude.set(v))
            .build());
    }

    // ── Server: Storage ───────────────────────────────────────────────────────

    private static void addServerStorage(ConfigBuilder b, ConfigEntryBuilder eb) {
        ConfigCategory cat = b.getOrCreateCategory(t("createfood.config.storage"));
        CreateFoodConfig.Server s = CreateFoodConfig.SERVER;

        var clothSackCat = eb.startSubCategory(t("createfood.config.cloth_sack"));
        clothSackCat.add(eb.startBooleanToggle(t("createfood.config.cloth_sack.inventory_enabled"), s.clothSackInventoryEnabled.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.clothSackInventoryEnabled.set(v))
            .build());
        clothSackCat.add(eb.startBooleanToggle(t("createfood.config.cloth_sack.eat_from_item"), s.clothSackEatFromItem.get())
            .setDefaultValue(false)
            .setSaveConsumer(v -> s.clothSackEatFromItem.set(v))
            .build());
        clothSackCat.add(eb.startBooleanToggle(t("createfood.config.cloth_sack.stacking"), s.clothSackStack.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.clothSackStack.set(v))
            .build());
        clothSackCat.add(eb.startBooleanToggle(t("createfood.config.cloth_sack.allow_food"), s.clothSackAllowFood.get())
            .setDefaultValue(false)
            .setSaveConsumer(v -> s.clothSackAllowFood.set(v))
            .build());
        clothSackCat.add(eb.startStrList(t("createfood.config.cloth_sack.exclude"), new ArrayList<>(s.clothSackExclude.get()))
            .setDefaultValue(List.of("item:createfood:cloth_sack"))
            .setSaveConsumer(v -> s.clothSackExclude.set(v))
            .build());
        clothSackCat.add(eb.startStrList(t("createfood.config.cloth_sack.filter"), new ArrayList<>(s.clothSackFilter.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.clothSackFilter.set(v))
            .build());
        cat.addEntry(clothSackCat.build());

        var rationBoxCat = eb.startSubCategory(t("createfood.config.ration_box"));
        rationBoxCat.add(eb.startBooleanToggle(t("createfood.config.ration_box.inventory_enabled"), s.rationBoxInventoryEnabled.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.rationBoxInventoryEnabled.set(v))
            .build());
        rationBoxCat.add(eb.startBooleanToggle(t("createfood.config.ration_box.eat_from_item"), s.rationBoxEatFromItem.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.rationBoxEatFromItem.set(v))
            .build());
        rationBoxCat.add(eb.startBooleanToggle(t("createfood.config.ration_box.stacking"), s.rationBoxStack.get())
            .setDefaultValue(false)
            .setSaveConsumer(v -> s.rationBoxStack.set(v))
            .build());
        rationBoxCat.add(eb.startBooleanToggle(t("createfood.config.ration_box.allow_food"), s.rationBoxAllowFood.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> s.rationBoxAllowFood.set(v))
            .build());
        rationBoxCat.add(eb.startStrList(t("createfood.config.ration_box.exclude"), new ArrayList<>(s.rationBoxExclude.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.rationBoxExclude.set(v))
            .build());
        rationBoxCat.add(eb.startStrList(t("createfood.config.ration_box.filter"), new ArrayList<>(s.rationBoxFilter.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> s.rationBoxFilter.set(v))
            .build());
        cat.addEntry(rationBoxCat.build());
    }

    // ── Client ────────────────────────────────────────────────────────────────

    private static final List<String> DEFAULT_DISPLAY_BLOCKS = List.of(
        "create:bar_of_chocolate|plate|6",
        "create:builders_tea|bottle|1|10|true",
        "create:sweet_roll|plate|4",
        "farmersdelight:pumpkin_pie_slice|small_plate|1",
        "minecraft:cake|plate|1",
        "minecraft:pumpkin_pie|plate|1"
    );

    private static void addClient(ConfigBuilder b, ConfigEntryBuilder eb) {
        ConfigCategory cat = b.getOrCreateCategory(t("createfood.config.client"));
        CreateFoodConfig.Client c = CreateFoodConfig.CLIENT;

        var displayCat = eb.startSubCategory(t("createfood.config.client_display"));
        displayCat.add(eb.startBooleanToggle(t("createfood.config.client_display.always_display_upright"), c.alwaysDisplayUpright.get())
            .setDefaultValue(false)
            .setSaveConsumer(v -> c.alwaysDisplayUpright.set(v))
            .build());
        displayCat.add(eb.startStrList(t("createfood.config.client_display.display_block"), new ArrayList<>(c.customDisplayBlock.get()))
            .setDefaultValue(DEFAULT_DISPLAY_BLOCKS)
            .setSaveConsumer(v -> c.customDisplayBlock.set(v))
            .build());
        cat.addEntry(displayCat.build());

        var itemsCat = eb.startSubCategory(t("createfood.config.client_items"));
        itemsCat.add(eb.startStrList(t("createfood.config.client_items.hide_items"), new ArrayList<>(c.hideItems.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> c.hideItems.set(v))
            .build());
        cat.addEntry(itemsCat.build());

        var tooltipsCat = eb.startSubCategory(t("createfood.config.client_tooltips"));
        tooltipsCat.add(eb.startBooleanToggle(t("createfood.config.client_items.require_shift"), c.requireShiftForTooltips.get())
            .setDefaultValue(false)
            .setSaveConsumer(v -> c.requireShiftForTooltips.set(v))
            .build());
        tooltipsCat.add(eb.startBooleanToggle(t("createfood.config.client_items.show_compatibility"), c.showCompatibility.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> c.showCompatibility.set(v))
            .build());
        tooltipsCat.add(eb.startBooleanToggle(t("createfood.config.client_items.show_ingredients"), c.showIngredients.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> c.showIngredients.set(v))
            .build());
        tooltipsCat.add(eb.startStrList(t("createfood.config.client_items.custom_tooltips"), new ArrayList<>(c.customTooltips.get()))
            .setDefaultValue(List.of())
            .setSaveConsumer(v -> c.customTooltips.set(v))
            .build());
        cat.addEntry(tooltipsCat.build());

        var storageCat = eb.startSubCategory(t("createfood.config.client_storage"));
        storageCat.add(eb.startBooleanToggle(t("createfood.config.client_storage.show_sack_block_icons"), c.showSackBlockIcons.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> c.showSackBlockIcons.set(v))
            .build());
        storageCat.add(eb.startBooleanToggle(t("createfood.config.client_storage.show_tooltip_icons"), c.showStorageTooltipIcons.get())
            .setDefaultValue(true)
            .setSaveConsumer(v -> c.showStorageTooltipIcons.set(v))
            .build());
        cat.addEntry(storageCat.build());
    }
}
