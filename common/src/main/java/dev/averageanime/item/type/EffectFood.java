package dev.averageanime.item.type;

import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.config.ItemNutritionOverride;
import dev.averageanime.item.effect.EffectCategories;
import dev.averageanime.item.effect.FoodEffect;
import dev.averageanime.platform.Services;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class EffectFood extends Item {

    public record DeferredFx(String categoryOrEffectId, Supplier<Optional<Holder<MobEffect>>> effect, int duration, int amplifier) {}

    private final Set<String> existingEffectIds;
    private final List<DeferredFx> deferredEffects;

    public EffectFood(Properties properties) {
        this(properties, Set.of(), List.of());
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds) {
        this(properties, existingEffectIds, List.of());
    }

    public EffectFood(Properties properties, Set<String> existingEffectIds, List<DeferredFx> deferredEffects) {
        super(properties);
        this.existingEffectIds = Set.copyOf(existingEffectIds);
        this.deferredEffects = List.copyOf(deferredEffects);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        Item remItem = stack.getItem().getCraftingRemainingItem();
        ItemStack remainder = remItem != null ? new ItemStack(remItem) : ItemStack.EMPTY;

        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food != null) {
            FoodProperties patched = applyNutritionOverride(food);
            patched = applyEffectOverrides(patched);
            if (patched != food) {
                stack.set(DataComponents.FOOD, patched);
            }

            ItemStack result = super.finishUsingItem(stack, level, consumer);
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

    protected FoodProperties applyEffectOverrides(FoodProperties base) {
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        List<ItemEffectOverride> overrides = Services.PLATFORM.getItemOverrideEntries(itemId).stream()
                .filter(o -> isExistingEffect(o.categoryOrEffectId()))
                .toList();
        if (overrides.isEmpty()) return base;

        var b = new FoodProperties.Builder()
                .nutrition(base.nutrition())
                .saturationModifier(base.saturation());
        if (base.canAlwaysEat()) b.alwaysEdible();
        base.usingConvertsTo().ifPresent(item -> b.usingConvertsTo(item.getItem()));
        for (var possible : base.effects()) {
            ItemEffectOverride override = findOverrideForEffect(overrides, possible.effect());
            if (override == null) {
                b.effect(possible.effect(), possible.probability());
            } else if (!override.remove()) {
                b.effect(new MobEffectInstance(possible.effect().getEffect(),
                        override.duration(), override.amplifier()), possible.probability());
            }
        }
        return b.build();
    }

    private static ItemEffectOverride findOverrideForEffect(
            List<ItemEffectOverride> overrides, MobEffectInstance inst) {
        for (var override : overrides) {
            Optional<Holder<MobEffect>> resolved = resolveEffect(override.categoryOrEffectId());
            if (resolved.isPresent() && resolved.get().equals(inst.getEffect())) {
                return override;
            }
        }
        return null;
    }

    protected FoodProperties applyNutritionOverride(FoodProperties base) {
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        ItemNutritionOverride override = Services.PLATFORM.getItemNutritionOverride(itemId);
        if (override == null) return base;

        int   nutrition  = override.hasNutritionOverride()  ? override.nutrition()  : base.nutrition();
        float saturation = override.hasSaturationOverride() ? override.saturation() : base.saturation();
        return rebuildFoodProperties(base, nutrition, saturation);
    }

    protected static FoodProperties rebuildFoodProperties(FoodProperties base, int nutrition, float saturation) {
        var b = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation);
        if (base.canAlwaysEat()) b.alwaysEdible();
        base.usingConvertsTo().ifPresent(item -> b.usingConvertsTo(item.getItem()));
        for (var possible : base.effects()) {
            b.effect(possible.effect(), possible.probability());
        }
        return b.build();
    }

    protected void applyDeferredEffects(Level level, LivingEntity consumer) {
        if (level.isClientSide) return;
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        for (DeferredFx deferred : deferredEffects) {
            ItemEffectOverride override = Services.PLATFORM.getItemEffectOverride(itemId, deferred.categoryOrEffectId());
            if (override != null && override.remove()) continue;
            deferred.effect().get().ifPresent(holder -> {
                int dur = override != null ? override.duration() : deferred.duration();
                int amp = override != null ? override.amplifier() : deferred.amplifier();
                consumer.addEffect(new MobEffectInstance(holder, dur, amp));
            });
        }
    }

    protected void applyAdditions(Level level, LivingEntity consumer) {
        if (level.isClientSide) return;
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        for (ItemEffectOverride addition : Services.PLATFORM.getItemOverrideEntries(itemId)) {
            if (addition.remove()) continue;
            if (isExistingEffect(addition.categoryOrEffectId())) continue;
            resolveEffect(addition.categoryOrEffectId()).ifPresent(holder ->
                    consumer.addEffect(new MobEffectInstance(
                            holder, addition.duration(), addition.amplifier()))
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
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food == null) return;

        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        List<ItemEffectOverride> existingOverrides = Services.PLATFORM.getItemOverrideEntries(itemId).stream()
                .filter(o -> isExistingEffect(o.categoryOrEffectId()))
                .toList();
        for (FoodProperties.PossibleEffect possible : food.effects()) {
            ItemEffectOverride override = findOverrideForEffect(existingOverrides, possible.effect());
            if (override != null && override.remove()) continue;
            if (override != null) {
                addEffectLine(tooltip, new MobEffectInstance(possible.effect().getEffect(),
                        override.duration(), override.amplifier()), context);
            } else {
                addEffectLine(tooltip, possible.effect(), context);
            }
        }

        for (ItemEffectOverride addition : Services.PLATFORM.getItemOverrideEntries(itemId)) {
            if (addition.remove()) continue;
            if (isExistingEffect(addition.categoryOrEffectId())) continue;
            resolveEffect(addition.categoryOrEffectId()).ifPresent(holder ->
                    addEffectLine(tooltip,
                            new MobEffectInstance(holder, addition.duration(), addition.amplifier()),
                            context)
            );
        }

        for (DeferredFx deferred : deferredEffects) {
            ItemEffectOverride override = Services.PLATFORM.getItemEffectOverride(itemId, deferred.categoryOrEffectId());
            if (override != null && override.remove()) continue;
            deferred.effect().get().ifPresent(holder -> {
                int dur = override != null ? override.duration() : deferred.duration();
                int amp = override != null ? override.amplifier() : deferred.amplifier();
                addEffectLine(tooltip, new MobEffectInstance(holder, dur, amp), context);
            });
        }
    }

    static void addEffectLine(List<Component> tooltip, MobEffectInstance instance,
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

    static Optional<Holder<MobEffect>> resolveEffect(String categoryOrEffectId) {
        Optional<Holder<MobEffect>> fromCategory = EffectCategories
                .getByName(categoryOrEffectId)
                .flatMap(FoodEffect::get);
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
