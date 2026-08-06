package dev.averageanime.compat.jade;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.blockentity.SmallBowlBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

enum SmallBowlJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "small_bowl");
    private static final String BUCKET_SUFFIX = " Bucket";

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public IElement getIcon(BlockAccessor accessor, IPluginConfig config, IElement currentIcon) {
        Item bucket = bucketItem(accessor);
        return bucket == null ? IElementHelper.get().item(new ItemStack(Items.BOWL)) : IElementHelper.get().item(new ItemStack(bucket));
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity be = accessor.getBlockEntity();
        if (!(be instanceof SmallBowlBlockEntity bowl) || bowl.isEmpty()) return;
        Item bucket = bowl.getFluid().getBucket();
        if (bucket == null || bucket == Items.AIR) return;

        String name = new ItemStack(bucket).getHoverName().getString();
        if (name.endsWith(BUCKET_SUFFIX)) name = name.substring(0, name.length() - BUCKET_SUFFIX.length());
        tooltip.add(Component.literal(name).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(bowl.getAmount() + " mB").withStyle(ChatFormatting.GRAY));
    }

    private static Item bucketItem(BlockAccessor accessor) {
        BlockEntity be = accessor.getBlockEntity();
        if (!(be instanceof SmallBowlBlockEntity bowl) || bowl.isEmpty()) return null;
        Item bucket = bowl.getFluid().getBucket();
        return bucket == null || bucket == Items.AIR ? null : bucket;
    }
}
