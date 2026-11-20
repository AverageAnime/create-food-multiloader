package dev.averageanime.neoforge.compat.farmersdelight;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.averageanime.config.ConfigValues;
import dev.averageanime.createfood.lib.client.BasinItems;
import dev.averageanime.createfood.lib.client.FluidSegments;
import dev.averageanime.registry.FluidAmounts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

import java.util.List;

/**
 * Draws a cooking pot's contents inside the hollow pot model from the {@code farmersdelight_overrides}
 * pack the way Create's basin does: fluids side by side, items on a ring, floating on the fluid when there
 * is any. With the display switched off, a pot that holds something is closed with FD's own top face
 * instead, so it looks exactly like FD's solid model; an empty pot is left open either way. See
 * {@link HollowCookingPot} for why the model itself cannot switch.
 */
public class CookingPotRenderer implements BlockEntityRenderer<CookingPotBlockEntity> {

    /** A bucket fills the pot to the brim, the same as one of Create's basin tanks. */
    private static final int CAPACITY_MB = FluidAmounts.BUCKET;

    // Nudged in from the walls and floor so the fluid's faces do not z-fight with the model's.
    private static final float MIN_XZ = 4.01f / 16f;
    private static final float MAX_XZ = 11.99f / 16f;
    private static final float FLOOR_Y = 1.01f / 16f;
    private static final float MAX_Y = 9.5f / 16f;

    // Item centres rest just off the floor when dry and ride the surface when afloat, at the same depth
    // whatever the fill. A film too shallow to lift them off the floor leaves them resting on it.
    private static final float ITEM_DRY_Y = 2.5f / 16f;
    // The opening is 8px across, half a basin's, so the ring and the items are both scaled down to fit.
    private static final float ITEM_RING = 1.5f / 16f;
    private static final float ITEM_SCALE = 0.5f;

    // The lid is the part of FD's top face the hollow model leaves open: the 2px rim is the walls' own
    // top faces, so the lid spans the 8x8 opening (px 4-12) and sits exactly where FD's face did.
    private static final float LID_MIN = 4f / 16f;
    private static final float LID_MAX = 12f / 16f;
    private static final float LID_Y = 10f / 16f;
    private static final ResourceLocation LID_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("farmersdelight", "block/cooking_pot_top");

    public CookingPotRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CookingPotBlockEntity pot, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffers, int packedLight, int packedOverlay) {
        // FD's solid pot hides anything drawn inside it, and the floating items would poke through its top.
        if (!HollowCookingPot.isActive()) return;
        if (!ConfigValues.isCookingPotDisplayEnabled()) {
            if (!isEmpty(pot)) renderLid(pot, poseStack, buffers, packedLight);
            return;
        }
        List<FluidStack> fluids = CookingPotContents.fluids(pot);
        float surfaceY = FluidSegments.render(poseStack, buffers, packedLight, fluids, CAPACITY_MB,
                MIN_XZ, MIN_XZ, MAX_XZ, MAX_XZ, FLOOR_Y, MAX_Y);
        boolean afloat = surfaceY > ITEM_DRY_Y;
        float itemY = afloat ? surfaceY : ITEM_DRY_Y;
        BasinItems.render(poseStack, buffers, packedLight, packedOverlay, pot.getLevel(), pot.getBlockPos(),
                partialTick, CookingPotContents.items(pot), 0.5f, 0.5f, ITEM_RING, itemY, ITEM_SCALE, afloat);
    }

    /** The container slot is not contents: bowls waiting there do not fill the pot. */
    private static boolean isEmpty(CookingPotBlockEntity pot) {
        ItemStackHandler inventory = pot.getInventory();
        for (int slot = 0; slot <= CookingPotBlockEntity.MEAL_DISPLAY_SLOT; slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty()) return false;
        }
        return inventory.getStackInSlot(CookingPotBlockEntity.OUTPUT_SLOT).isEmpty();
    }

    private static void renderLid(CookingPotBlockEntity pot, PoseStack poseStack,
                                  MultiBufferSource buffers, int light) {
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(LID_TEXTURE);

        poseStack.pushPose();
        // FD's blockstate turns the model, top texture included, by these "y" values; a block model is
        // rotated by -y about the block centre.
        Direction facing = pot.getBlockState()
                .getOptionalValue(BlockStateProperties.HORIZONTAL_FACING).orElse(Direction.NORTH);
        float y = switch (facing) {
            case EAST -> 90f;
            case SOUTH -> 180f;
            case WEST -> 270f;
            default -> 0f;
        };
        poseStack.translate(0.5f, 0f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(-y));
        poseStack.translate(-0.5f, 0f, -0.5f);

        VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutout(InventoryMenu.BLOCK_ATLAS));
        PoseStack.Pose pose = poseStack.last();
        float u0 = sprite.getU(LID_MIN), u1 = sprite.getU(LID_MAX);
        float v0 = sprite.getV(LID_MIN), v1 = sprite.getV(LID_MAX);
        lidVertex(consumer, pose, LID_MIN, LID_MIN, u0, v0, light);
        lidVertex(consumer, pose, LID_MIN, LID_MAX, u0, v1, light);
        lidVertex(consumer, pose, LID_MAX, LID_MAX, u1, v1, light);
        lidVertex(consumer, pose, LID_MAX, LID_MIN, u1, v0, light);
        poseStack.popPose();
    }

    private static void lidVertex(VertexConsumer consumer, PoseStack.Pose pose,
                                  float x, float z, float u, float v, int light) {
        consumer.addVertex(pose, x, LID_Y, z)
                .setColor(1f, 1f, 1f, 1f)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, 0f, 1f, 0f);
    }
}
