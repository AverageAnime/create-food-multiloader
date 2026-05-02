package dev.averageanime.neoforge.item.type;

import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.item.ModEffectCategories;
import dev.averageanime.item.effect.FoodEffect;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class EffectFood extends Item {

    /** Carries the data needed to resolve and apply a mod-dependent effect at consumption time. */
    public record DeferredFx(String categoryOrEffectId, Supplier<Optional<Holder<MobEffect>>> effect, int duration, int amplifier) {}

    private final Set<String> existingEffectIds;
    private final List<DeferredFx> deferredEffects;

    public EffectFood(Properties properties) {
        this(properties, Set.of(), List.of());
    }

    public EffectFood(Properties properties, boolean ignored) {
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
    public @Nullable FoodProperties getFoodProperties(@NotNull ItemStack stack, @Nullable LivingEntity entity) {
        FoodProperties base = super.getFoodProperties(stack, entity);
        if (base == null) return null;

        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        ModConfig.ItemNutritionOverride override = ModConfig.getItemNutritionOverride(itemId);
        if (override == null) return base;

        int nutrition = override.hasNutritionOverride()     ? override.nutrition()   : base.nutrition();
        float saturation = override.hasSaturationOverride() ? override.saturation()  : base.saturation();

        return rebuildFoodProperties(base, nutrition, saturation);
    }

    private static FoodProperties rebuildFoodProperties(FoodProperties base, int nutrition, float saturation) {
        var b = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation);

        if (base.canAlwaysEat()) b.alwaysEdible();
        base.usingConvertsTo().ifPresent(item -> b.usingConvertsTo(item.getItem()));
        for (var possible : base.effects()) {
            b.effect(possible::effect, possible.probability());
        }

        return b.build();
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        ItemStack remainder = stack.getCraftingRemainingItem();

        if (stack.getFoodProperties(consumer) != null) {
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

        if (stack.isEmpty()) {
            applyAdditions(level, consumer);
            applyDeferredEffects(level, consumer);
            return remainder;
        }

        if (consumer instanceof Player player) {
            if (!player.getAbilities().instabuild && !player.getInventory().add(remainder)) {
                player.drop(remainder, false);
            }
        }

        applyAdditions(level, consumer);
        applyDeferredEffects(level, consumer);
        return stack;
    }

    private void applyDeferredEffects(Level level, LivingEntity consumer) {
        if (level.isClientSide) return;
        String itemId = BuiltInRegistries.ITEM.getKey(this).getPath();
        for (DeferredFx deferred : deferredEffects) {
            ModConfig.ItemEffectOverride override = ModConfig.getItemEffectOverride(itemId, deferred.categoryOrEffectId());
            if (override != null && override.remove()) continue;
            deferred.effect().get().ifPresent(holder -> {
                int dur = override != null ? override.duration() : deferred.duration();
                int amp = override != null ? override.amplifier() : deferred.amplifier();
                consumer.addEffect(new MobEffectInstance(holder, dur, amp));
            });
        }
    }

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

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        FoodProperties food = stack.getFoodProperties(null);
        if (food == null) return;

        for (FoodProperties.PossibleEffect possible : food.effects()) {
            addEffectLine(tooltip, possible.effect(), context);
        }

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

        for (DeferredFx deferred : deferredEffects) {
            ModConfig.ItemEffectOverride override = ModConfig.getItemEffectOverride(itemId, deferred.categoryOrEffectId());
            if (override != null && override.remove()) continue;
            deferred.effect().get().ifPresent(holder -> {
                int dur = override != null ? override.duration() : deferred.duration();
                int amp = override != null ? override.amplifier() : deferred.amplifier();
                addEffectLine(tooltip, new MobEffectInstance(holder, dur, amp), context);
            });
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

    static Optional<Holder<MobEffect>> resolveEffect(String categoryOrEffectId) {
        Optional<Holder<MobEffect>> fromCategory = ModEffectCategories
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
