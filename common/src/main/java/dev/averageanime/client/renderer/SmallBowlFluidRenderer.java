package dev.averageanime.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.averageanime.block.type.blockentity.SmallBowlBlockEntity;
import dev.averageanime.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class SmallBowlFluidRenderer implements BlockEntityRenderer<SmallBowlBlockEntity> {

    private static final float MIN_XZ = 4.5f / 16f;
    private static final float MAX_XZ = 11.5f / 16f;
    private static final float BASIN_FLOOR_Y = 1.0f / 16f;
    private static final float MAX_SURFACE_Y = 1.5f / 16f;
    private static final float MIN_SURFACE_Y = BASIN_FLOOR_Y + 0.05f / 16f;

    private static final ResourceLocation WATER_FALLBACK =
            ResourceLocation.withDefaultNamespace("block/water_still");
    private static final int WATER_TINT = 0x3F76E4;

    public SmallBowlFluidRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SmallBowlBlockEntity bowl, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (bowl.isEmpty()) return;

        Fluid fluid = bowl.getFluid();
        if (fluid == Fluids.EMPTY) return;

        TextureAtlasSprite sprite = resolveSprite(fluid);
        if (sprite == null) return;

        boolean tinted = sprite.contents().name().equals(WATER_FALLBACK);
        float red   = tinted ? ((WATER_TINT >> 16) & 0xFF) / 255f : 1.0f;
        float green = tinted ? ((WATER_TINT >> 8) & 0xFF) / 255f : 1.0f;
        float blue  = tinted ? (WATER_TINT & 0xFF) / 255f : 1.0f;

        float fill = Math.min(1.0f, (float) bowl.getAmount() / Services.PLATFORM.getSmallBowlCapacityMb());
        float y = MIN_SURFACE_Y + (MAX_SURFACE_Y - MIN_SURFACE_Y) * fill;

        float u0 = sprite.getU(MIN_XZ);
        float u1 = sprite.getU(MAX_XZ);
        float v0 = sprite.getV(MIN_XZ);
        float v1 = sprite.getV(MAX_XZ);

        VertexConsumer consumer = bufferSource.getBuffer(
                RenderType.entityTranslucentCull(InventoryMenu.BLOCK_ATLAS));
        PoseStack.Pose pose = poseStack.last();

        vertex(consumer, pose, MIN_XZ, y, MIN_XZ, u0, v0, red, green, blue, packedLight);
        vertex(consumer, pose, MIN_XZ, y, MAX_XZ, u0, v1, red, green, blue, packedLight);
        vertex(consumer, pose, MAX_XZ, y, MAX_XZ, u1, v1, red, green, blue, packedLight);
        vertex(consumer, pose, MAX_XZ, y, MIN_XZ, u1, v0, red, green, blue, packedLight);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose,
                               float x, float y, float z, float u, float v,
                               float red, float green, float blue, int packedLight) {
        consumer.addVertex(pose, x, y, z)
                .setColor(red, green, blue, 1.0f)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0f, 1.0f, 0.0f);
    }

    private static TextureAtlasSprite resolveSprite(Fluid fluid) {
        ResourceLocation id = BuiltInRegistries.FLUID.getKey(fluid);
        if (id == null) return null;

        var atlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
        TextureAtlasSprite sprite = atlas.apply(
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "fluid/" + id.getPath() + "_still"));

        if (sprite.contents().name().equals(MissingTextureAtlasSprite.getLocation())) {
            sprite = atlas.apply(WATER_FALLBACK);
        }
        return sprite;
    }
}
