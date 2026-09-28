package dev.averageanime.mixin;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface ItemAccessorMixin {

    // @Mutable is required: craftingRemainingItem and components are both declared final on Item,
    // and Mixin won't generate a setter for a final field without it.
    @Mutable
    @Accessor("craftingRemainingItem")
    void createfood$setCraftingRemainingItem(@Nullable Item item);

    @Mutable
    @Accessor("components")
    void createfood$setComponents(DataComponentMap components);
}
