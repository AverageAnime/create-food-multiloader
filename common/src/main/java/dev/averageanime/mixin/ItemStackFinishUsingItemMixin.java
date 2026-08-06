package dev.averageanime.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.averageanime.item.effect.EffectContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackFinishUsingItemMixin {

    @WrapOperation(method = "finishUsingItem",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item;finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack createfood$trackEffectOverrides(Item item, ItemStack stack, Level level, LivingEntity entity,
                                                      Operation<ItemStack> original) {
        EffectContext.begin(entity, stack);
        try {
            return original.call(item, stack, level, entity);
        } finally {
            EffectContext.end(entity);
        }
    }
}
