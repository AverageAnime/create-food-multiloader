package dev.averageanime.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.averageanime.block.type.bowl.GenericDisplayBowlBlock;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.platform.Services;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class GenericDisplayPlateRenderer implements BlockEntityRenderer<GenericDisplayBlockEntity> {

    private static final TagKey<Item> UPRIGHT_TAG = TagKey.create(
            Registries.ITEM, ResourceLocation.fromNamespaceAndPath("create", "upright_on_belt"));

    private static final float ITEM_SCALE = 0.6f;

    private final ItemRenderer itemRenderer;

    public GenericDisplayPlateRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(@NotNull GenericDisplayBlockEntity be, float partialTick,
                       @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {

        BlockState state = be.getBlockState();
        if (!state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) return;
        boolean isBowl = state.getBlock() instanceof GenericDisplayBowlBlock;

        ItemStack displayedItem = be.getDisplayedItemForRender();
        if (displayedItem.isEmpty()) return;

        var level = be.getLevel();
        if (level == null) return;

        BlockPos pos = be.getBlockPos();
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight   = level.getBrightness(LightLayer.SKY,   pos);
        int light = LightTexture.pack(Math.max(blockLight, 10), skyLight);

        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        float yRot = switch (facing) {
            case NORTH -> 180f;
            case SOUTH ->   0f;
            case WEST  -> 270f;
            case EAST  ->  90f;
            default    ->   0f;
        };

        boolean isBlock = displayedItem.getItem() instanceof BlockItem;
        boolean upright = isBowl
                || Services.PLATFORM.isAlwaysDisplayUpright()
                || displayedItem.is(UPRIGHT_TAG)
                || isBlock;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        float bowlDrop = isBowl ? 0.16f : 0.0f;

        if (upright) {
            float yOffset = isBlock ? 0.30f : 0.38f;
            poseStack.translate(0.0, yOffset - bowlDrop, 0.0);
            poseStack.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
            itemRenderer.renderStatic(displayedItem, ItemDisplayContext.FIXED,
                    light, packedOverlay, poseStack, bufferSource, level, 0);
        } else {
            poseStack.translate(0.0, 0.15 - bowlDrop, 0.0);
            poseStack.mulPose(Axis.XP.rotationDegrees(90f));
            poseStack.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
            itemRenderer.renderStatic(displayedItem, ItemDisplayContext.FIXED,
                    light, packedOverlay, poseStack, bufferSource, level, 0);
        }

        poseStack.popPose();
    }
}