package dev.averageanime.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.averageanime.config.ItemEffectOverride;
import dev.averageanime.item.effect.TooltipContext;
import dev.averageanime.item.type.EffectFood;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackTooltipMixin {

    @WrapOperation(method = "getTooltipLines",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"))
    private void createfood$appendForeignEffectLines(Item item, ItemStack stack, Item.TooltipContext context,
                                                      List<Component> tooltip, TooltipFlag flag,
                                                      Operation<Void> original) {
        List<ItemEffectOverride> prev = TooltipContext.push(stack);
        try {
            original.call(item, stack, context, tooltip, flag);
        } finally {
            TooltipContext.restore(prev);
        }
        if (!(item instanceof EffectFood)) {
            EffectFood.appendForeignEffectLines(stack, context, tooltip);
        }
    }
}
