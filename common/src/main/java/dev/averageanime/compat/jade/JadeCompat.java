package dev.averageanime.compat.jade;

import dev.averageanime.item.effect.TooltipContext;
import dev.averageanime.item.type.EffectFood;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class JadeCompat {
    private JadeCompat() {}

    static void appendStackTooltip(ITooltip tooltip, ItemStack stack, BlockAccessor accessor, boolean includeName) {
        if (stack.isEmpty()) return;

        if (includeName) {
            tooltip.add(stack.getHoverName().copy().withStyle(ChatFormatting.GRAY));
        }

        List<Component> extra = new ArrayList<>();
        Item.TooltipContext context = Item.TooltipContext.of(accessor.getLevel());
        TooltipContext.runWith(stack, () ->
                stack.getItem().appendHoverText(stack, context, extra, TooltipFlag.NORMAL));
        if (!(stack.getItem() instanceof EffectFood)) {
            EffectFood.appendForeignEffectLines(stack, context, extra);
        }

        Set<String> seen = new HashSet<>();
        for (Component line : extra) {
            if (seen.add(line.getString())) tooltip.add(line);
        }
    }
}
