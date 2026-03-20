package net.xgjs.fds.mixin;

import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "respawn", at = @At("RETURN"))
    private void afterRespawn(ServerPlayer serverPlayer, boolean bl, CallbackInfoReturnable<ServerPlayer> cir) {
        ServerPlayer serverPlayer2 = cir.getReturnValue();

        serverPlayer2.getActiveEffectsMap().forEach((key, effect) -> {
            serverPlayer2.connection.send(new ClientboundUpdateMobEffectPacket(serverPlayer2.getId(), effect));
        });
    }
}