package uk.co.mysterymayhem.gravitymod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

	@Shadow
	private Minecraft gameController;

	// Unknown why
	
	@ModifyArg(method = "handlePlayerPosLook", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/client/CPacketPlayer$PositionRotation;<init>(DDDFFZ)V"), index = 1)
	private double handlePlayerPosLook(double old) {
		return this.gameController.player.posY;
	}
}
