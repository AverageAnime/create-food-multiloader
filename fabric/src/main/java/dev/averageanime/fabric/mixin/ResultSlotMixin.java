package dev.averageanime.fabric.mixin;

import dev.averageanime.fabric.item.remainder.CraftingRemainder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultSlot.class)
public class ResultSlotMixin {

    @Shadow @Final private CraftingContainer craftSlots;

    @Inject(method = "onTake", at = @At("HEAD"))
    private void createfood$onCraft(Player player, ItemStack stack, CallbackInfo ci) {
        CraftingRemainder.onItemCrafted(player, craftSlots);
    }
}
