package uk.co.mysterymayhem.gravitymod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import uk.co.mysterymayhem.gravitymod.asm.Hooks;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
	@Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEntityBoundingBox()Lnet/minecraft/util/math/AxisAlignedBB;"))
	private AxisAlignedBB getEntityBoundingBox(Entity entity) {
		return Hooks.getVanillaEntityBoundingBox(entity);
	}

	@Inject(method = "drawNameplate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableLighting()V", shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void correctNamePlace(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift,
			float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking, CallbackInfo info) {
		Hooks.runNameplateCorrection(isThirdPersonFrontal);
	}
}
