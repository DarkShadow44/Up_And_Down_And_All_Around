package uk.co.mysterymayhem.gravitymod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import uk.co.mysterymayhem.gravitymod.asm.Hooks;

@Mixin(RenderLivingBase.class)
public class MixinRenderLivingBase {

	// Fix head rotations when on ceiling
	
	
	@Redirect(method = "doRender", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;prevRotationYawHead"))
	private float prevRotationYawHead(EntityLivingBase entity) {
		return Hooks.getPrevRelativeYawHead(entity);
	}

	@Redirect(method = "doRender", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;rotationYawHead"))
	private float rotationYawHead(EntityLivingBase entity) {
		return Hooks.getRelativeYawHead(entity);
	}

	@Redirect(method = "doRender", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;prevRotationPitch"))
	private float prevRotationPitch(EntityLivingBase entity) {
		return Hooks.getRelativePrevPitch(entity);
	}

	@Redirect(method = "doRender", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;rotationPitch"))
	private float rotationPitch(EntityLivingBase entity) {
		return Hooks.getRelativePitch(entity);
	}
}
