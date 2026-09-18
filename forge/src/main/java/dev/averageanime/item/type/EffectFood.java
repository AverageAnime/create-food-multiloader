package dev.averageanime.item.type;

import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.config.ItemNutritionOverride;
import dev.averageanime.client.tooltip.ItemTooltips;
import dev.averageanime.item.effect.EffectCategories;
import dev.averageanime.item.effect.FoodEffect;
import dev.averageanime.platform.Services;
import com.mojang.datafixers.util.Pair;
import dev.averageanime.util.Tooltips;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
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
import dev.averageanime.util.FoodAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class EffectFood extends Item {

    public record DeferredFx(String categoryOrEffectId, Supplier<Optional<Holder<MobEffect>>> effect, int duration, int amplifier, float chance) {}

    private final Set<String> existingEffectIds;
    private final List<DeferredFx> deferredEffects;
    private final @Nullable Tooltips.TooltipSpec tip;

    public EffectFood(Properties properties) {
        this(properties, Set.of(), List.of());
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds) {
        this(properties, existingEffectIds, List.of());
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds, List<DeferredFx> deferredEffects) {
        this(properties, existingEffectIds, deferredEffects, null);
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds, List<DeferredFx> deferredEffects,
                      @Nullable Tooltips.TooltipSpec tip) {
        super(properties);
        this.existingEffectIds = Set.copyOf(existingEffectIds);
        this.deferredEffects = List.copyOf(deferredEffects);
        this.tip = tip;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        Item remItem = stack.getItem().getCraftingRemainingItem();
        ItemStack remainder = remItem != null ? new ItemStack(remItem) : ItemStack.EMPTY;

        FoodProperties food = FoodAccess.get(stack, consumer);
        if (food != null) {
            FoodProperties patched = applyNutritionOverride(food);
            patched = applyEffectOverrides(patched);
            ItemStack result = consumeFood(stack, level, consumer, patched);
            applyAdditions(level, consumer);
            applyDeferredEffects(level, consumer);
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

        applyAdditions(level, consumer);
        applyDeferredEffects(level, consumer);

        if (stack.isEmpty()) return remainder;

        if (consumer instanceof Player player) {
            if (!player.getAbilities().instabuild && !player.getInventory().add(remainder)) {
                player.drop(remainder, false);
            }
        }
        return stack;
    }

    private ItemStack consumeFood(ItemStack stack, Level level, LivingEntity consumer, FoodProperties food) {
        if (!level.isClientSide && consumer instanceof Player player) {
            player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
            if (consumer instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    protected FoodProperties applyEffectOverrides(FoodProperties base) {
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        List<ItemEffectOverride> overrides = Services.PLATFORM.getItemOverrideEntries(itemId).stream()
                .filter(o -> isExistingEffect(o.categoryOrEffectId()))
                .toList();
        if (overrides.isEmpty()) return base;

        var fx = new FoodProperties.Builder();
        for (Pair<MobEffectInstance, Float> possible : base.getEffects()) {
            MobEffectInstance instance = possible.getFirst();
            ItemEffectOverride override = findOverrideForEffect(overrides, instance);
            if (override == null) {
                fx.effect(instance, possible.getSecond());
            } else if (!override.remove()) {
                fx.effect(new MobEffectInstance(instance.getEffect(),
                        override.duration(), override.amplifier()), possible.getSecond());
            }
        }
        return withEffects(base, fx);
    }

    public static ItemEffectOverride findOverrideForEffect(
            List<ItemEffectOverride> overrides, MobEffectInstance inst) {
        for (var override : overrides) {
            Optional<Holder<MobEffect>> resolved = resolveEffect(override.categoryOrEffectId());
            if (resolved.isPresent() && resolved.get().value().equals(inst.getEffect())) {
                return override;
            }
        }
        return null;
    }

    protected FoodProperties applyNutritionOverride(FoodProperties base) {
        return applyNutritionOverride(base, BuiltInRegistries.ITEM.getKey(this).getPath());
    }

    public static FoodProperties applyNutritionOverride(FoodProperties base, String itemId) {
        ItemNutritionOverride override = Services.PLATFORM.getItemNutritionOverride(itemId);
        if (override == null) return base;

        int nutrition = override.hasNutritionOverride() ? override.nutrition() : base.getNutrition();
        float saturation = override.hasSaturationOverride()
                ? override.saturation()
                : base.getSaturationModifier();
        return rebuildFoodProperties(base, nutrition, saturation);
    }

    protected static FoodProperties rebuildFoodProperties(FoodProperties base, int nutrition, float saturation) {
        FoodProperties.Builder builder = copyFoodFlags(base, nutrition, saturation);
        for (Pair<MobEffectInstance, Float> effect : base.getEffects()) {
            builder.effect(effect.getFirst(), effect.getSecond());
        }
        return builder.build();
    }

    public static FoodProperties withEffects(FoodProperties base, FoodProperties.Builder effects) {
        FoodProperties.Builder builder = copyFoodFlags(base, base.getNutrition(), base.getSaturationModifier());
        for (Pair<MobEffectInstance, Float> effect : effects.build().getEffects()) {
            builder.effect(effect.getFirst(), effect.getSecond());
        }
        return builder.build();
    }

    public static void eatAndApplyFoodProperties(FoodProperties food, Level level, LivingEntity consumer) {
        if (consumer instanceof Player player) {
            player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
        }
        if (!level.isClientSide) {
            for (Pair<MobEffectInstance, Float> effect : food.getEffects()) {
                if (consumer.getRandom().nextFloat() < effect.getSecond()) {
                    consumer.addEffect(new MobEffectInstance(effect.getFirst()));
                }
            }
        }
    }

    private static FoodProperties.Builder copyFoodFlags(FoodProperties base, int nutrition, float saturation) {
        FoodProperties.Builder builder = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationMod(saturation);
        if (base.isMeat()) builder.meat();
        if (base.canAlwaysEat()) builder.alwaysEat();
        if (base.isFastFood()) builder.fast();
        return builder;
    }

    /**
     * Apply the effects that are not baked into the FOOD component.
     *
     * <p>Block forms (cakes, pies, pizzas, waffles) eat by reading their slice
     * item's FOOD component directly and never run {@link #finishUsingItem},
     * so the compat-category effects -- which are deferred precisely because
     * they cannot be baked -- silently never fired when the block was eaten.
     * The block eat paths call this so a slice and its block behave alike.
     */
    public void applyNonBakedEffects(Level level, LivingEntity consumer) {
        applyAdditions(level, consumer);
        applyDeferredEffects(level, consumer);
    }

    protected void applyDeferredEffects(Level level, LivingEntity consumer) {
        if (level.isClientSide) return;
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        for (DeferredFx deferred : deferredEffects) {
            ItemEffectOverride override = Services.PLATFORM.getItemEffectOverride(itemId, deferred.categoryOrEffectId());
            if (override != null && override.remove()) continue;
            float chance = override != null ? override.chance() : deferred.chance();
            if (consumer.getRandom().nextFloat() >= chance) continue;
            deferred.effect().get().ifPresent(holder -> {
                int dur = override != null ? override.duration() : deferred.duration();
                int amp = override != null ? override.amplifier() : deferred.amplifier();
                consumer.addEffect(new MobEffectInstance(holder.value(), dur, amp));
            });
        }
    }

    protected void applyAdditions(Level level, LivingEntity consumer) {
        if (level.isClientSide) return;
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        for (ItemEffectOverride addition : Services.PLATFORM.getItemOverrideEntries(itemId)) {
            if (addition.remove()) continue;
            if (isExistingEffect(addition.categoryOrEffectId())) continue;
            if (consumer.getRandom().nextFloat() >= addition.chance()) continue;
            resolveEffect(addition.categoryOrEffectId()).ifPresent(holder ->
                    consumer.addEffect(new MobEffectInstance(
                            holder.value(), addition.duration(), addition.amplifier()))
            );
        }
    }

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

    @Override
    public void appendHoverText(ItemStack stack, Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        appendEffectLines(stack, level, tooltip);
        if (tip != null) ItemTooltips.addTooltip(tooltip, tip.compat(), tip.keys());
    }

    private void appendEffectLines(ItemStack stack, Level level, List<Component> tooltip) {
        FoodProperties food = FoodAccess.get(stack, null);
        if (food == null) return;

        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        List<ItemEffectOverride> allOverrides = Services.PLATFORM.getItemOverrideEntries(itemId);
        List<ItemEffectOverride> existingOverrides = allOverrides.isEmpty() ? allOverrides
                : allOverrides.stream()
                        .filter(o -> isExistingEffect(o.categoryOrEffectId()))
                        .toList();
        for (Pair<MobEffectInstance, Float> possible : food.getEffects()) {
            MobEffectInstance instance = possible.getFirst();
            ItemEffectOverride override = findOverrideForEffect(existingOverrides, instance);
            if (override != null && override.remove()) continue;
            if (override != null) {
                addEffectLine(tooltip, new MobEffectInstance(instance.getEffect(),
                        override.duration(), override.amplifier()), level);
            } else {
                addEffectLine(tooltip, instance, level);
            }
        }

        for (ItemEffectOverride addition : allOverrides) {
            if (addition.remove()) continue;
            if (isExistingEffect(addition.categoryOrEffectId())) continue;
            resolveEffect(addition.categoryOrEffectId()).ifPresent(holder ->
                    addEffectLine(tooltip,
                            new MobEffectInstance(holder.value(), addition.duration(), addition.amplifier()),
                            level, addition.chance())
            );
        }

        for (DeferredFx deferred : deferredEffects) {
            ItemEffectOverride override = Services.PLATFORM.getItemEffectOverride(itemId, deferred.categoryOrEffectId());
            if (override != null && override.remove()) continue;
            deferred.effect().get().ifPresent(holder -> {
                int dur = override != null ? override.duration() : deferred.duration();
                int amp = override != null ? override.amplifier() : deferred.amplifier();
                float chance = override != null ? override.chance() : deferred.chance();
                addEffectLine(tooltip, new MobEffectInstance(holder.value(), dur, amp), level, chance);
            });
        }
    }

    public static void appendForeignEffectLines(ItemStack stack, Level level, List<Component> tooltip) {
        FoodProperties food = FoodAccess.get(stack, null);
        if (food == null) return;

        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        String displayName = stack.getHoverName().getString();
        for (ItemEffectOverride override : Services.PLATFORM.getItemOverrideEntries(itemId)) {
            if (!override.remove()) continue;
            resolveEffect(override.categoryOrEffectId()).ifPresent(holder -> {
                String descriptionText = Component.translatable(holder.value().getDescriptionId()).getString();
                tooltip.removeIf(line -> !line.getString().equals(displayName) && line.getString().contains(descriptionText));
            });
        }

        for (Pair<MobEffectInstance, Float> possible : food.getEffects()) {
            Component line = buildEffectLine(possible.getFirst(), level, possible.getSecond());
            if (line == null) continue;
            String text = line.getString();
            if (tooltip.stream().noneMatch(existing -> existing.getString().equals(text))) {
                tooltip.add(line);
            }
        }
    }

    static void addEffectLine(List<Component> tooltip, MobEffectInstance instance,
                              Level level) {
        addEffectLine(tooltip, instance, level, 1.0f);
    }

    static void addEffectLine(List<Component> tooltip, MobEffectInstance instance,
                              Level level, float chance) {
        Component line = buildEffectLine(instance, level, chance);
        if (line != null) tooltip.add(line);
    }

    static Component buildEffectLine(MobEffectInstance instance, Level level, float chance) {
        if (instance == null || instance.getDuration() <= 0) return null;
        MutableComponent line = Component.translatable(instance.getDescriptionId());
        if (instance.getAmplifier() > 0) {
            line = Component.translatable("potion.withAmplifier", line,
                    Component.translatable("potion.potency." + instance.getAmplifier()));
        }
        if (instance.getDuration() > 20) {
            line = Component.translatable("potion.withDuration", line,
                    MobEffectUtil.formatDuration(instance, 1.0f));
        }
        if (chance < 1.0f) {
            int pct = Math.round(chance * 100);
            line = line.append(Component.translatable("createfood.effect.chance", pct));
        }
        return line.withStyle(
                instance.getEffect().getCategory().getTooltipFormatting()
        );
    }

    public static Optional<Holder<MobEffect>> resolveEffect(String categoryOrEffectId) {
        Optional<Holder<MobEffect>> fromCategory = EffectCategories
                .getByName(categoryOrEffectId)
                .flatMap(FoodEffect::get);
        if (fromCategory.isPresent()) return fromCategory;

        try {
            Optional<? extends Holder<MobEffect>> fromRegistry =
                    BuiltInRegistries.MOB_EFFECT.getHolder(effectKey(new ResourceLocation(categoryOrEffectId)));
            if (fromRegistry.isPresent()) return fromRegistry.map(h -> h);
        } catch (Exception ignored) {}

        if (!categoryOrEffectId.contains(":")) {
            try {
                return BuiltInRegistries.MOB_EFFECT
                        .getHolder(effectKey(new ResourceLocation("minecraft", categoryOrEffectId)))
                        .map(h -> h);
            } catch (Exception ignored) {}
        }

        return Optional.empty();
    }

    private static ResourceKey<MobEffect> effectKey(ResourceLocation id) {
        return ResourceKey.create(Registries.MOB_EFFECT, id);
    }
}