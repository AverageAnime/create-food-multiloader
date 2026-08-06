package dev.averageanime.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.averageanime.client.renderer.FluidOverlayRenderer;
import dev.averageanime.fabric.block.type.fluid.FluidBlock;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Draws the submersion overlay when the eye is inside one of this mod's
 * fluids — vanilla only covers water. NeoForge gets the same overlay via
 * {@code IClientFluidTypeExtensions.renderOverlay}; Fabric has no equivalent
 * API for fluids reporting {@code FogType.NONE}. Cosmetic, hence
 * {@code require = 0}.
 */
@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @Inject(method = "renderScreenEffect", at = @At("RETURN"), require = 0)
    private static void createfood$renderFluidOverlay(Minecraft minecraft, PoseStack poseStack, CallbackInfo ci) {
        Player player = minecraft.player;
        if (player == null || player.isSpectator()) return;

        BlockPos eyePos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
        FluidState state = player.level().getFluidState(eyePos);
        if (FluidBlock.getFogColor(state.getType()) == null) return;
        if (player.getEyeY() - eyePos.getY() > state.getHeight(player.level(), eyePos)) return;

        FluidRenderHandler handler = FluidRenderHandlerRegistry.INSTANCE.get(state.getType());
        if (handler == null) return;
        TextureAtlasSprite still = handler.getFluidSprites(player.level(), eyePos, state)[0];
        FluidOverlayRenderer.render(minecraft, poseStack, still);
    }
}
