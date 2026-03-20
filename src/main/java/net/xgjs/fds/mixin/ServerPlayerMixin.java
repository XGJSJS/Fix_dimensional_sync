package net.xgjs.fds.mixin;

import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
	@Inject(
			method = "restoreFrom",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/server/level/ServerPlayer;onUpdateAbilities()V",
			shift = At.Shift.AFTER)
	)
	private void afterPortalEntrancePos(ServerPlayer oldPlayer, boolean bl, CallbackInfo ci) {
		ServerPlayer thisPlayer = (ServerPlayer) (Object) this;
		if (bl) {
			thisPlayer.getActiveEffectsMap().putAll(oldPlayer.getActiveEffectsMap());
		}
	}

	@Inject(method = "changeDimension", at = @At("RETURN"))
	private void afterChangeDimension(ServerLevel serverLevel, CallbackInfoReturnable<Entity> cir) {
		syncInfo((ServerPlayer) cir.getReturnValue());
	}

	@Inject(method = "teleportTo(Lnet/minecraft/server/level/ServerLevel;DDDLjava/util/Set;FF)Z", at = @At("TAIL"))
	private void afterTeleportTo(ServerLevel serverLevel, double d, double e, double f, Set<RelativeMovement> set, float g, float h, CallbackInfoReturnable<Boolean> cir) {
		syncInfo((ServerPlayer) (Object) this);
	}

	@Unique
	private static void syncInfo(ServerPlayer serverPlayer) {
		serverPlayer.connection.send(new ClientboundSetExperiencePacket(serverPlayer.experienceProgress, serverPlayer.totalExperience, serverPlayer.experienceLevel));
		serverPlayer.getActiveEffectsMap().forEach((key, effect) -> serverPlayer.connection.send(new ClientboundUpdateMobEffectPacket(serverPlayer.getId(), effect)));
	}
}