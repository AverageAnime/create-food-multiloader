package net.averageanime.createfood.item;

import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.config.CreateFoodConfig;
import net.averageanime.createfood.item.effect.EffectCategories;
import net.averageanime.createfood.item.effect.FoodEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class EffectFood extends Item {

    public record DeferredFx(String categoryOrEffectId, Supplier<Optional<MobEffect>> effect,
                              int duration, int amplifier, float chance) {}

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
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        // In 1.20.1, FoodProperties are baked at item creation time and can't be swapped
        // via DataComponents. Config nutrition/effect overrides apply only to players.
        if (consumer instanceof net.minecraft.world.entity.player.Player player) {
            FoodProperties food = stack.getItem().getFoodProperties();
            if (food != null) {
                FoodProperties patched = applyNutritionOverride(food);
                patched = applyEffectOverrides(patched);
                if (patched != food) {
                    // Apply patched nutrition and effects manually, then suppress the
                    // vanilla eat() by not going through super's food-eating path.
                    player.getFoodData().eat(patched.getNutrition(), patched.getSaturationModifier());
                    if (!level.isClientSide) {
                        for (var pair : patched.getEffects()) {
                            if (level.random.nextFloat() < pair.getSecond()) {
                                player.addEffect(new MobEffectInstance(pair.getFirst()));
                            }
                        }
                    }
                    // Shrink stack and return container (mirrors Item.finishUsingItem)
                    if (!level.isClientSide) {
                        if (consumer instanceof net.minecraft.server.level.ServerPlayer sp) {
                            net.minecraft.advancements.CriteriaTriggers.CONSUME_ITEM.trigger(sp, stack);
                            sp.awardStat(net.minecraft.stats.Stats.ITEM_USED.get(this));
                        }
                        if (!player.getAbilities().instabuild) stack.shrink(1);
                    }
                    applyAdditions(level, consumer);
                    applyDeferredEffects(level, consumer);
                    return stack;
                }
            }
        }

        ItemStack result = super.finishUsingItem(stack, level, consumer);
        applyAdditions(level, consumer);
        applyDeferredEffects(level, consumer);
        return result;
    }

    protected FoodProperties applyEffectOverrides(FoodProperties base) {
        String itemId = ForgeRegistries.ITEMS.getKey(this) != null
                ? ForgeRegistries.ITEMS.getKey(this).getPath() : "";
        List<ConfigLogic.ItemEffectOverride> overrides;
        try {
            overrides = ConfigLogic.getItemOverrideEntries(itemId, CreateFoodConfig.SERVER.itemOverrides.get())
                    .stream().filter(o -> isExistingEffect(o.effectId())).toList();
        } catch (Exception e) {
            return base;
        }
        if (overrides.isEmpty()) return base;

        var b = new FoodProperties.Builder()
                .nutrition(base.getNutrition())
                .saturationMod(base.getSaturationModifier());
        if (base.canAlwaysEat()) b.alwaysEat();
        for (var pair : base.getEffects()) {
            ConfigLogic.ItemEffectOverride override = findOverrideForEffect(overrides, pair.getFirst());
            if (override == null) {
                b.effect(pair.getFirst(), pair.getSecond());
            } else if (!override.remove()) {
                b.effect(new MobEffectInstance(pair.getFirst().getEffect(),
                        override.duration(), override.amplifier()), pair.getSecond());
            }
        }
        return b.build();
    }

    private ConfigLogic.ItemEffectOverride findOverrideForEffect(
            List<ConfigLogic.ItemEffectOverride> overrides, MobEffectInstance inst) {
        for (var override : overrides) {
            Optional<MobEffect> resolved = resolveEffect(override.effectId());
            if (resolved.isPresent() && resolved.get() == inst.getEffect()) return override;
        }
        return null;
    }

    @Override
    @Nullable
    public FoodProperties getFoodProperties() {
        FoodProperties base = super.getFoodProperties();
        if (base == null) return null;
        return applyNutritionOverride(base);
    }

    @Override
    @Nullable
    public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        FoodProperties base = super.getFoodProperties(stack, entity);
        if (base == null) return null;
        return applyNutritionOverride(base);
    }

    protected FoodProperties applyNutritionOverride(FoodProperties base) {
        String itemId = ForgeRegistries.ITEMS.getKey(this) != null
                ? ForgeRegistries.ITEMS.getKey(this).getPath() : "";
        ConfigLogic.ItemNutritionOverride override;
        try {
            override = ConfigLogic.getItemNutritionOverride(itemId, CreateFoodConfig.SERVER.nutritionSaturation.get());
        } catch (Exception e) {
            return base;
        }
        if (override == null) return base;

        int nutrition  = override.nutrition()  == ConfigLogic.ItemNutritionOverride.KEEP_INT   ? base.getNutrition()          : override.nutrition();
        float saturation = override.saturation() == ConfigLogic.ItemNutritionOverride.KEEP_FLOAT ? base.getSaturationModifier() : override.saturation();
        return rebuildFoodProperties(base, nutrition, saturation);
    }

    protected static FoodProperties rebuildFoodProperties(FoodProperties base, int nutrition, float saturation) {
        var b = new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation);
        if (base.canAlwaysEat()) b.alwaysEat();
        for (var pair : base.getEffects()) b.effect(pair.getFirst(), pair.getSecond());
        return b.build();
    }

    protected void applyDeferredEffects(Level level, LivingEntity consumer) {
        if (level.isClientSide) return;
        String itemId = ForgeRegistries.ITEMS.getKey(this) != null
                ? ForgeRegistries.ITEMS.getKey(this).getPath() : "";
        for (DeferredFx deferred : deferredEffects) {
            ConfigLogic.ItemEffectOverride override = null;
            try {
                override = ConfigLogic.getItemEffectOverride(itemId, deferred.categoryOrEffectId(),
                        CreateFoodConfig.SERVER.itemOverrides.get());
            } catch (Exception ignored) {}
            if (override != null && override.remove()) continue;
            float chance = override != null ? override.chance() : deferred.chance();
            if (consumer.getRandom().nextFloat() >= chance) continue;
            final ConfigLogic.ItemEffectOverride finalOverride = override;
            deferred.effect().get().ifPresent(effect -> {
                int dur = finalOverride != null ? finalOverride.duration() : deferred.duration();
                int amp = finalOverride != null ? finalOverride.amplifier() : deferred.amplifier();
                consumer.addEffect(new MobEffectInstance(effect, dur, amp));
            });
        }
    }

    protected void applyAdditions(Level level, LivingEntity consumer) {
        if (level.isClientSide) return;
        String itemId = ForgeRegistries.ITEMS.getKey(this) != null
                ? ForgeRegistries.ITEMS.getKey(this).getPath() : "";
        List<ConfigLogic.ItemEffectOverride> additions;
        try {
            additions = ConfigLogic.getItemOverrideEntries(itemId, CreateFoodConfig.SERVER.itemOverrides.get());
        } catch (Exception e) {
            return;
        }
        for (ConfigLogic.ItemEffectOverride addition : additions) {
            if (addition.remove()) continue;
            if (isExistingEffect(addition.effectId())) continue;
            if (consumer.getRandom().nextFloat() >= addition.chance()) continue;
            resolveEffect(addition.effectId()).ifPresent(effect ->
                    consumer.addEffect(new MobEffectInstance(effect, addition.duration(), addition.amplifier()))
            );
        }
    }

    private boolean isExistingEffect(String categoryOrEffectId) {
        if (existingEffectIds.contains(categoryOrEffectId)) return true;
        Optional<MobEffect> incoming = resolveEffect(categoryOrEffectId);
        if (incoming.isEmpty()) return false;
        for (String existingId : existingEffectIds) {
            if (resolveEffect(existingId).map(e -> e == incoming.get()).orElse(false)) return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        FoodProperties food = stack.getItem().getFoodProperties(stack, null);
        if (food == null) return;

        String itemId = ForgeRegistries.ITEMS.getKey(this) != null
                ? ForgeRegistries.ITEMS.getKey(this).getPath() : "";

        List<ConfigLogic.ItemEffectOverride> allOverrides;
        try {
            allOverrides = ConfigLogic.getItemOverrideEntries(itemId, CreateFoodConfig.SERVER.itemOverrides.get());
        } catch (Exception e) {
            allOverrides = List.of();
        }
        List<ConfigLogic.ItemEffectOverride> existingOverrides = allOverrides.stream()
                .filter(o -> isExistingEffect(o.effectId())).toList();

        for (var pair : food.getEffects()) {
            ConfigLogic.ItemEffectOverride override = findOverrideForEffect(existingOverrides, pair.getFirst());
            if (override != null && override.remove()) continue;
            if (override != null) {
                addEffectLine(tooltip, new MobEffectInstance(pair.getFirst().getEffect(),
                        override.duration(), override.amplifier()));
            } else {
                addEffectLine(tooltip, pair.getFirst());
            }
        }

        for (ConfigLogic.ItemEffectOverride addition : allOverrides) {
            if (addition.remove()) continue;
            if (isExistingEffect(addition.effectId())) continue;
            resolveEffect(addition.effectId()).ifPresent(effect ->
                    addEffectLine(tooltip, new MobEffectInstance(effect, addition.duration(), addition.amplifier()),
                            addition.chance())
            );
        }

        for (DeferredFx deferred : deferredEffects) {
            ConfigLogic.ItemEffectOverride override = null;
            try {
                override = ConfigLogic.getItemEffectOverride(itemId, deferred.categoryOrEffectId(),
                        CreateFoodConfig.SERVER.itemOverrides.get());
            } catch (Exception ignored) {}
            if (override != null && override.remove()) continue;
            final ConfigLogic.ItemEffectOverride finalOverride = override;
            deferred.effect().get().ifPresent(effect -> {
                int dur = finalOverride != null ? finalOverride.duration() : deferred.duration();
                int amp = finalOverride != null ? finalOverride.amplifier() : deferred.amplifier();
                float chance = finalOverride != null ? finalOverride.chance() : deferred.chance();
                addEffectLine(tooltip, new MobEffectInstance(effect, dur, amp), chance);
            });
        }
    }

    static void addEffectLine(List<Component> tooltip, MobEffectInstance instance) {
        addEffectLine(tooltip, instance, 1.0f);
    }

    static void addEffectLine(List<Component> tooltip, MobEffectInstance instance, float chance) {
        if (instance == null || instance.getDuration() <= 0) return;
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
        tooltip.add(line.withStyle(instance.getEffect().getCategory().getTooltipFormatting()));
    }

    public static Optional<MobEffect> resolveEffect(String categoryOrEffectId) {
        Optional<FoodEffect> fromCategory = EffectCategories.getByName(categoryOrEffectId);
        if (fromCategory.isPresent()) {
            Optional<MobEffect> resolved = fromCategory.get().get();
            if (resolved.isPresent()) return resolved;
        }

        try {
            ResourceLocation loc = ResourceLocation.tryParse(categoryOrEffectId);
            if (loc != null) {
                MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(loc);
                if (effect != null) return Optional.of(effect);
            }
        } catch (Exception ignored) {}

        if (!categoryOrEffectId.contains(":")) {
            try {
                MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                        new ResourceLocation("minecraft", categoryOrEffectId));
                if (effect != null) return Optional.of(effect);
            } catch (Exception ignored) {}
        }

        return Optional.empty();
    }
}
