package dev.averageanime.fabric.mixin;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.averageanime.fabric.block.type.fluid.FluidBlock;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.level.material.FluidState;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Shadow private static float fogRed;
    @Shadow private static float fogGreen;
    @Shadow private static float fogBlue;

    @Inject(method = "setupColor", at = @At("RETURN"), require = 0)
    private static void onSetupColor(Camera camera, float partialTick, ClientLevel level,
                                     int renderDistance, float darkenWorldAmount, CallbackInfo ci) {
        if (camera.getFluidInCamera() != FogType.NONE) return;
        FluidState fluidState = level.getFluidState(BlockPos.containing(camera.getPosition()));
        Vector3f color = FluidBlock.getFogColor(fluidState.getType());
        if (color == null) return;
        fogRed = color.x();
        fogGreen = color.y();
        fogBlue = color.z();
    }

    @Inject(method = "setupFog", at = @At("RETURN"), require = 0)
    private static void onSetupFog(Camera camera, FogRenderer.FogMode fogMode, float renderDistance,
                                   boolean nearFog, float partialTick, CallbackInfo ci) {
        if (camera.getFluidInCamera() != FogType.NONE) return;
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        FluidState fluidState = level.getFluidState(BlockPos.containing(camera.getPosition()));
        FluidBlock.FogParams params = FluidBlock.getFogParams(fluidState.getType());
        if (params == null) return;
        RenderSystem.setShaderFogStart(params.start());
        RenderSystem.setShaderFogEnd(params.end());
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
    }
}
