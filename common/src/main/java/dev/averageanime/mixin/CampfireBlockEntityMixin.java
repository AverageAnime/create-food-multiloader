package dev.averageanime.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.averageanime.item.remainder.CraftingRemainder;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(net.minecraft.world.level.block.entity.CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin {

    @WrapOperation(method = "cookTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/Containers;dropItemStack(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"))
    private static void createfood$dropRemainder(Level level, double x, double y, double z, ItemStack result,
                                                 Operation<Void> original,
                                                 @Local(ordinal = 0) ItemStack input) {
        original.call(level, x, y, z, result);
        Item remainder = CraftingRemainder.getRemainderFor(input.getItem());
        if (remainder != null) {
            Containers.dropItemStack(level, x, y, z, new ItemStack(remainder));
        }
    }
}
