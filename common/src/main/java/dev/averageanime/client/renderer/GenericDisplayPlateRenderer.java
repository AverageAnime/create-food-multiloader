package dev.averageanime.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity;
import dev.averageanime.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GenericDisplayPlateRenderer implements BlockEntityRenderer<GenericDisplayPlateBlockEntity> {

    private static final TagKey<Item> UPRIGHT_TAG = TagKey.create(
            Registries.ITEM, ResourceLocation.fromNamespaceAndPath("create", "upright_on_belt"));

    private static final float ITEM_SCALE = 0.6f;

    public GenericDisplayPlateRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public void render(@NotNull GenericDisplayPlateBlockEntity be, float partialTick,
                       @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {

        BlockState state = be.getBlockState();
        if (!state.hasProperty(GenericDisplayPlateBlock.FACING)) return;

        ItemStack displayedItem = be.getDisplayedItem();
        if (displayedItem.isEmpty()) return;

        var level = be.getLevel();
        if (level == null) return;

        var itemRenderer = Minecraft.getInstance().getItemRenderer();

        int blockLight = level.getBrightness(LightLayer.BLOCK, be.getBlockPos());
        int skyLight   = level.getBrightness(LightLayer.SKY,   be.getBlockPos());
        int light = LightTexture.pack(Math.max(blockLight, 10), skyLight);

        Direction facing = state.getValue(GenericDisplayPlateBlock.FACING);
        float yRot = switch (facing) {
            case NORTH -> 180f;
            case SOUTH ->   0f;
            case WEST  -> 270f;
            case EAST  ->  90f;
            default    ->   0f;
        };

        boolean isBlock = displayedItem.getItem() instanceof BlockItem;
        boolean upright = Services.PLATFORM.isAlwaysDisplayUpright()
                || displayedItem.is(UPRIGHT_TAG)
                || isBlock;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        if (upright) {
            float yOffset = 0.38f;
            if (isBlock) {
                yOffset = 0.30f;
                try {
                    VoxelShape shape = ((BlockItem) displayedItem.getItem()).getBlock()
                            .defaultBlockState().getShape(level, be.getBlockPos());
                    if (!shape.isEmpty() && shape.max(Direction.Axis.Y) < 1.0) yOffset = 0.30f;
                } catch (Exception ignored) {}
            }
            poseStack.translate(0.0, yOffset, 0.0);
            poseStack.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
            itemRenderer.renderStatic(displayedItem, ItemDisplayContext.FIXED,
                    light, packedOverlay, poseStack, bufferSource, level, 0);
        } else {
            poseStack.translate(0.0, 0.15, 0.0);
            poseStack.mulPose(Axis.XP.rotationDegrees(90f));
            poseStack.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
            itemRenderer.renderStatic(displayedItem, ItemDisplayContext.FIXED,
                    light, packedOverlay, poseStack, bufferSource, level, 0);
        }

        poseStack.popPose();
    }
}