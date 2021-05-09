package uk.co.mysterymayhem.gravitymod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import uk.co.mysterymayhem.gravitymod.asm.Hooks;

@Mixin(World.class)
public class MixinWorld {
	@ModifyVariable(method = "getCollisionBoxes", argsOnly = true, at = @At("HEAD"))
	private AxisAlignedBB getCollisionBoxes(AxisAlignedBB box) {
		return Hooks.normaliseAABB(box);
	}
}
