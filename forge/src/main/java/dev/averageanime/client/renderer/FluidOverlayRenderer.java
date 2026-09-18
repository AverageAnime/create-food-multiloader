package dev.averageanime.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;

public final class FluidOverlayRenderer {

    private static final float ALPHA = 0.35f;

    private FluidOverlayRenderer() {}

    public static void render(Minecraft minecraft, PoseStack poseStack, TextureAtlasSprite sprite) {
        Player player = minecraft.player;
        if (player == null) return;
        BlockPos eyePos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
        float brightness = LightTexture.getBrightness(player.level().dimensionType(),
                player.level().getMaxLocalRawBrightness(eyePos));

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, sprite.atlasLocation());
        RenderSystem.enableBlend();
        Matrix4f pose = poseStack.last().pose();
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        buffer.vertex(pose, -1.0F, -1.0F, -0.5F).uv(sprite.getU1(), sprite.getV1()).color(brightness, brightness, brightness, ALPHA).endVertex();
        buffer.vertex(pose, 1.0F, -1.0F, -0.5F).uv(sprite.getU0(), sprite.getV1()).color(brightness, brightness, brightness, ALPHA).endVertex();
        buffer.vertex(pose, 1.0F, 1.0F, -0.5F).uv(sprite.getU0(), sprite.getV0()).color(brightness, brightness, brightness, ALPHA).endVertex();
        buffer.vertex(pose, -1.0F, 1.0F, -0.5F).uv(sprite.getU1(), sprite.getV0()).color(brightness, brightness, brightness, ALPHA).endVertex();
        BufferUploader.drawWithShader(buffer.end());
        RenderSystem.disableBlend();
    }
}
