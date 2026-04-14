package dev.averageanime.neoforge.item.type;

import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.item.ModEffectCategories;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EffectFood extends Item {

    /**
     * Category/effect IDs that are already built into this item's FoodProperties definition.
     * Used to distinguish existing effects (handled via the FoodProperties supplier chain)
     * from config additions (applied separately in {@link #applyAdditions}).
     */
    private final Set<String> existingEffectIds;

    public EffectFood(Properties properties) {
        this(properties, Set.of());
    }

    /** @deprecated Prefer {@link #EffectFood(Properties, Set)}. */
    public EffectFood(Properties properties, boolean ignored) {
        this(properties, Set.of());
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds) {
        super(properties);
        this.existingEffectIds = Set.copyOf(existingEffectIds);
    }

    // -------------------------------------------------------------------------
    // Nutrition / saturation override
    // -------------------------------------------------------------------------

    /**
     * Intercepts food property lookups to apply any per-item nutrition and
     * saturation overrides from {@code createfood-server.toml}.
     *
     * <p>Called by {@link ItemStack#getFoodProperties(LivingEntity)} and by our
     * own {@link #finishUsingItem} / {@link #appendHoverText} paths. The base
     * {@link FoodProperties} (baked in at registration) is rebuilt with only the
     * overridden fields replaced; all other properties — effects, eat speed,
     * always-edible flag, crafting-remainder conversion — are preserved exactly.
     */
    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        FoodProperties base = super.getFoodProperties(stack, entity);
        if (base == null) return null;

        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        ModConfig.ItemNutritionOverride override = ModConfig.getItemNutritionOverride(itemId);
        if (override == null) return base;

        int nutrition = override.hasNutritionOverride()     ? override.nutrition()   : base.nutrition();
        float saturation = override.hasSaturationOverride() ? override.saturation()  : base.saturation();

        return rebuildFoodProperties(base, nutrition, saturation);
    }

    /**
     * Rebuilds a {@link FoodProperties} with new nutrition/saturation values,
     * preserving every other field from {@code base}.
     *
     * <p>Effect suppliers are wrapped in forwarding lambdas so that the lazy
     * evaluation and config-override logic baked into each original supplier is
     * fully preserved in the returned copy.
     */
    private static FoodProperties rebuildFoodProperties(FoodProperties base, int nutrition, float saturation) {
        var b = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation);

        if (base.canAlwaysEat()) b.alwaysEdible();
        base.usingConvertsTo().ifPresent(item -> b.usingConvertsTo(item.getItem()));
        // Preserve all effect slots. The forwarding lambda means every supplier's
        // lazy override/remove logic (duration, amplifier, remove) keeps working.
        for (var possible : base.effects()) {
            b.effect(() -> possible.effect(), possible.probability());
        }

        return b.build();
    }

    // -------------------------------------------------------------------------
    // Consumption
    // -------------------------------------------------------------------------

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        ItemStack remainder = stack.getCraftingRemainingItem();

        if (stack.getFoodProperties(consumer) != null) {
            ItemStack result = super.finishUsingItem(stack, level, consumer);
            applyAdditions(level, consumer);
            return result;
        }

        if (!level.isClientSide) {
            if (consumer instanceof ServerPlayer sp) {
                CriteriaTriggers.CONSUME_ITEM.trigger(sp, stack);
            }
            if (consumer instanceof Player player) {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }

        if (stack.isEmpty()) {
            applyAdditions(level, consumer);
            return remainder;
        }

        if (consumer instanceof Player player) {
            if (!player.getAbilities().instabuild && !player.getInventory().add(remainder)) {
                player.drop(remainder, false);
            }
        }

        applyAdditions(level, consumer);
        return stack;
    }

    /**
     * Applies any config-driven effect additions for this item — entries in
     * {@code item_overrides} whose {@code category_or_effect_id} does not resolve
     * to an effect already in the item's built-in FoodProperties definition.
     */
    private void applyAdditions(Level level, LivingEntity consumer) {
        if (level.isClientSide) return;
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        for (ModConfig.ItemEffectOverride addition : ModConfig.getItemOverrideEntries(itemId)) {
            if (isExistingEffect(addition.categoryOrEffectId())) continue;
            if (addition.remove()) continue;
            resolveEffect(addition.categoryOrEffectId()).ifPresent(holder ->
                    consumer.addEffect(new MobEffectInstance(
                            holder, addition.duration(), addition.amplifier()))
            );
        }
    }

    // -------------------------------------------------------------------------
    // Tooltip
    // -------------------------------------------------------------------------

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        FoodProperties food = stack.getFoodProperties(null);
        if (food == null) return;

        // Built-in effects — supplier returns a duration-0 instance for config-removed
        // effects, which addEffectLine silently skips.
        for (FoodProperties.PossibleEffect possible : food.effects()) {
            addEffectLine(tooltip, possible.effect(), context);
        }

        // Config additions — effects injected via item_overrides that aren't already
        // covered by the built-in FoodProperties definition.
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        for (ModConfig.ItemEffectOverride addition : ModConfig.getItemOverrideEntries(itemId)) {
            if (isExistingEffect(addition.categoryOrEffectId())) continue;
            if (addition.remove()) continue;
            resolveEffect(addition.categoryOrEffectId()).ifPresent(holder ->
                    addEffectLine(tooltip,
                            new MobEffectInstance(holder, addition.duration(), addition.amplifier()),
                            context)
            );
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Returns true if {@code categoryOrEffectId} resolves to an effect that is
     * already part of this item's built-in FoodProperties definition.
     *
     * <p>Comparison is done by resolved {@link Holder} so that equivalent spellings
     * (e.g. {@code "strength"} and {@code "minecraft:strength"}) are treated as the
     * same effect, and category names (e.g. {@code "comfort"}) correctly match their
     * resolved holders.
     */
    private boolean isExistingEffect(String categoryOrEffectId) {
        if (existingEffectIds.contains(categoryOrEffectId)) return true;
        Optional<Holder<MobEffect>> incoming = resolveEffect(categoryOrEffectId);
        if (incoming.isEmpty()) return false;
        for (String existingId : existingEffectIds) {
            if (resolveEffect(existingId).map(h -> h.equals(incoming.get())).orElse(false)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a single effect tooltip line. Silently skips null instances and
     * duration-0 instances (used to represent config-removed effects without
     * returning null from a supplier).
     */
    private static void addEffectLine(List<Component> tooltip, MobEffectInstance instance,
                                      TooltipContext context) {
        if (instance == null || instance.getDuration() <= 0) return;
        MutableComponent line = Component.translatable(instance.getDescriptionId());
        if (instance.getAmplifier() > 0) {
            line = Component.translatable("potion.withAmplifier", line,
                    Component.translatable("potion.potency." + instance.getAmplifier()));
        }
        if (instance.getDuration() > 20) {
            line = Component.translatable("potion.withDuration", line,
                    MobEffectUtil.formatDuration(instance, 1.0f, context.tickRate()));
        }
        tooltip.add(line.withStyle(
                instance.getEffect().value().getCategory().getTooltipFormatting()
        ));
    }

    /**
     * Resolves a category name (e.g. {@code "comfort"}), a full effect registry ID
     * (e.g. {@code "minecraft:fire_resistance"}), or an unqualified vanilla effect
     * name (e.g. {@code "strength"} → {@code "minecraft:strength"}) to a {@link Holder}.
     *
     * <p>Resolution order:
     * <ol>
     *   <li>Category name via {@link ModEffectCategories#getByName}</li>
     *   <li>Full registry ID via {@link BuiltInRegistries#MOB_EFFECT}</li>
     *   <li>Unqualified name prefixed with {@code "minecraft:"}</li>
     * </ol>
     */
    static Optional<Holder<MobEffect>> resolveEffect(String categoryOrEffectId) {
        Optional<Holder<MobEffect>> fromCategory = ModEffectCategories
                .getByName(categoryOrEffectId)
                .flatMap(fe -> fe.get());
        if (fromCategory.isPresent()) return fromCategory;

        try {
            Optional<? extends Holder<MobEffect>> fromRegistry =
                    BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(categoryOrEffectId));
            if (fromRegistry.isPresent()) return fromRegistry.map(h -> h);
        } catch (Exception ignored) {}

        if (!categoryOrEffectId.contains(":")) {
            try {
                return BuiltInRegistries.MOB_EFFECT
                        .getHolder(ResourceLocation.fromNamespaceAndPath("minecraft", categoryOrEffectId))
                        .map(h -> h);
            } catch (Exception ignored) {}
        }

        return Optional.empty();
    }
}