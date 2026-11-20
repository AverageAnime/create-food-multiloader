package dev.averageanime.block.type.display;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/** The item of a display block: named after, and carrying the tooltip of, the food it shows. */
public class DisplayBlockItem extends BlockItem {
    private final Supplier<Item> food;
    private final String suffixKey;

    public DisplayBlockItem(Block block, Supplier<Item> food, String suffixKey) {
        super(block, new Item.Properties());
        this.food = food;
        this.suffixKey = suffixKey;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Item original = food.get();
        if (original != null && original != Items.BARRIER) {
            return Component.translatable("display.createfood.format",
                    original.getDescription(), Component.translatable(suffixKey));
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context,
                                @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        Item original = food.get();
        if (original != null && original != Items.BARRIER) {
            ItemStack originalStack = new ItemStack(original);
            // The shared tooltip mixin pushed the display block's id, not the food's.
            dev.averageanime.item.effect.TooltipContext.runWith(originalStack, () ->
                    original.appendHoverText(originalStack, context, components, flag));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}
