package dev.averageanime.item;

import dev.averageanime.item.type.EffectFood;
import dev.averageanime.registry.type.Effect;
import dev.averageanime.registry.type.Item;
import dev.averageanime.util.Tooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public final class ItemBuilder {
    private ItemBuilder() {}

    public static Set<String> existingIds(List<Effect> specs) {
        return specs.stream()
                .filter(s -> !s.isFoodEffect() || s.foodEffect().hasAnyLoadedCandidate())
                .map(Effect::categoryOrEffectId)
                .collect(Collectors.toUnmodifiableSet());
    }

    public static List<EffectFood.DeferredFx> deferredFx(List<Effect> specs) {
        return specs.stream()
                .filter(s -> s.isFoodEffect() && s.foodEffect().hasAnyLoadedCandidate())
                .map(s -> new EffectFood.DeferredFx(s.categoryOrEffectId(), s.foodEffect()::get, s.duration, s.amplifier, s.chance))
                .toList();
    }

    public static void doTip(List<Component> l, @Nullable Tooltips.Tip tip,
                              BiConsumer<List<Component>, Tooltips.Tip> addTooltipFn) {
        if (tip == null) return;
        addTooltipFn.accept(l, tip);
    }

    public static net.minecraft.world.item.Item plainItem(Item def,
                                                           BiConsumer<List<Component>, Tooltips.Tip> addTooltipFn) {
        Tooltips.Tip tip = def.tip;
        if (tip == null) return new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties());
        return new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties()) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c,
                                                  @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip, addTooltipFn);
            }
        };
    }

    public static net.minecraft.world.item.Item plainCrItem(Item def,
                                                             net.minecraft.world.item.Item remainder,
                                                             BiConsumer<List<Component>, Tooltips.Tip> addTooltipFn) {
        Tooltips.Tip tip = def.tip;
        if (tip == null) return new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties().craftRemainder(remainder));
        return new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties().craftRemainder(remainder)) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c,
                                                  @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip, addTooltipFn);
            }
        };
    }

    public static net.minecraft.world.item.Item pipingBagItem(Item def,
                                                               net.minecraft.world.item.Item pipingBag,
                                                               BiConsumer<List<Component>, Tooltips.Tip> addTooltipFn) {
        Tooltips.Tip tip = def.tip;
        if (tip == null) return new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties().stacksTo(2).craftRemainder(pipingBag));
        return new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties().stacksTo(2).craftRemainder(pipingBag)) {
            @Override public void appendHoverText(@NotNull ItemStack s, @NotNull TooltipContext c,
                                                  @NotNull List<Component> l, @NotNull TooltipFlag f) {
                super.appendHoverText(s, c, l, f); doTip(l, tip, addTooltipFn);
            }
        };
    }
}