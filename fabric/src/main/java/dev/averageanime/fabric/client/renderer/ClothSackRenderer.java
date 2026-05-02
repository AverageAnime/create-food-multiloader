package dev.averageanime.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.averageanime.fabric.block.type.storage.ClothSackBlock;
import dev.averageanime.fabric.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.fabric.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ClothSackRenderer implements BlockEntityRenderer<ClothSackBlockEntity> {

    private static final float[][] OFFSETS = {
            { -0.12f, -0.1f },
            {  0.12f, -0.1f },
            { -0.12f, -0.33f },
            {  0.12f, -0.33f },
    };

    private static final float OUTSET = 0.315f;
    private static final float ICON_SCALE = 0.25f;

    public ClothSackRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public void render(@NotNull ClothSackBlockEntity be, float partialTick, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        if (!ModConfig.isSackBlockIconsEnabled()) return;

        BlockState state = be.getBlockState();
        if (!state.hasProperty(ClothSackBlock.FACING)) return;

        Direction facing = state.getValue(ClothSackBlock.FACING);
        var inv = be.inventory;
        var level = be.getLevel();
        if (level == null) return;

        var itemRenderer = Minecraft.getInstance().getItemRenderer();

        int blockLight = level.getBrightness(LightLayer.BLOCK, be.getBlockPos());
        int skyLight   = level.getBrightness(LightLayer.SKY,   be.getBlockPos());
        int light = LightTexture.pack(blockLight, skyLight);

        float yRot = switch (facing) {
            case NORTH -> 180f;
            case WEST  -> 270f;
            case EAST  -> 90f;
            default    -> 0f;
        };

        for (int i = 0; i < 4; i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.isEmpty()) continue;

            float right = OFFSETS[i][0];
            float up    = OFFSETS[i][1];

            poseStack.pushPose();

            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

            poseStack.translate(right, up, OUTSET);

            poseStack.scale(-ICON_SCALE, ICON_SCALE, 0.001f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED,
                    light, packedOverlay, poseStack, bufferSource, level, 0);

            poseStack.popPose();
        }
    }
}
