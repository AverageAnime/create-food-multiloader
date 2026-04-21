package dev.averageanime.neoforge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.averageanime.neoforge.block.type.storage.ClothSackBlock;
import dev.averageanime.neoforge.config.ModConfig;
import dev.averageanime.neoforge.blockentity.ClothSackBlockEntity;
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
        { -0.12f, -0.1f },   // slot 0 — upper left
        {  0.12f, -0.1f },   // slot 1 — upper right
        { -0.12f, -0.33f },   // slot 2 — lower left
        {  0.12f, -0.33f },   // slot 3 — lower right
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
            case SOUTH -> 0f;
            case WEST  -> 270f;
            case EAST  -> 90f;
            default    -> 0f;
        };

        for (int i = 0; i < 4; i++) {
            ItemStack stack = inv.getStackInSlot(i);
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
