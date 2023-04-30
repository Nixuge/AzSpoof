package me.nixuge.azbypass.mixins.client.network;

import java.lang.reflect.Field;
import java.util.concurrent.Future;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import me.nixuge.azbypass.McMod;
import me.nixuge.azbypass.config.Cache;
import me.nixuge.azbypass.utils.ReflectionUtils;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
    //     if (packet instanceof SPacketChunkData
    //     || packet instanceof SPacketEntityStatus
    //     || packet instanceof SPacketChat 
    //     || packet instanceof SPacketPlayerPosLook
    //     || packet instanceof SPacketWorldBorder
    //     || packet instanceof SPacketTimeUpdate
    //     || packet instanceof SPacketBlockChange
    //     || packet instanceof SPacketKeepAlive
    //     || packet instanceof SPacketPlayerListItem
    //     || packet instanceof SPacketMultiBlockChange
    //     || packet instanceof SPacketSetSlot
    //     || packet instanceof SPacketEntityMetadata
    //     || packet instanceof SPacketEntityProperties
    //     || packet instanceof SPacketUpdateHealth
    //     || packet instanceof SPacketSetExperience
    //     || packet instanceof SPacketEntityMetadata
    //     || packet instanceof SPacketPlayerAbilities
    //     || packet instanceof SPacketParticles
    //     || packet instanceof SPacketEntity
    //     || packet instanceof S15PacketEntityRelMove
    //     || packet instanceof S17PacketEntityLookMove
    //     || packet instanceof SPacketUpdateTileEntity
    //     || packet instanceof SPacketSpawnObject
    //     || packet instanceof SPacketEntityHeadLook
    //     || packet instanceof SPacketEntityEquipment
    //     || packet instanceof SPacketEntityVelocity
    //     || packet instanceof SPacketSpawnObject
    //     || packet instanceof SPacketTeams
    //     || packet instanceof SPacketBlockAction
    //     || packet instanceof SPacketAnimation
    //     || packet instanceof SPacketUpdateBossInfo
    //     || packet instanceof SPacketEntityTeleport
    //     || packet instanceof SPacketSpawnPlayer
    //     || packet instanceof SPacketChangeGameState
    //     || packet instanceof SPacketUpdateScore
    //     || packet instanceof SPacketDisplayObjective
    //     || packet instanceof SPacketDestroyEntities
    //     || packet instanceof SPacketSpawnPosition
    //     || packet instanceof SPacketPlayerListHeaderFooter
    //     || packet instanceof SPacketScoreboardObjective
        
    //     ) {
    //         return;
    //     }

        // System.out.println("fdp stp crash pas" + packet.getClass());

        // System.out.println("Packet Recieved: " + packet.toString());

        // if (packet instanceof SPacketCustomPayload) {
        //     SPacketCustomPayload pack = (SPacketCustomPayload) packet;
        //     String str = "";
        //     // pack.b()
        //     for (byte bit  : pack.getBufferData().array()) {
        //         str += (char)bit;
        //     }
        //     System.out.println("got custom packet!");
        //     System.out.println(pack.getChannelName());
        //     System.out.println(str);
        // }
        // ((Packet<INetHandler>)packet).processPacket(this.packetListener);
        // if (event.isCanceled() && callbackInfo.isCancellable())
        // {
        //     callbackInfo.cancel();
        // }
        // packet = event.packet;
    }

    private final McMod mcmod = McMod.getInstance();
    private final Cache cache = mcmod.getCache();

    @Inject(method = "dispatchPacket", at = @At("HEAD"), cancellable = true)
    private void onDispatchPacket(final Packet<?> inPacket, final GenericFutureListener <? extends Future <? super Void >> [] futureListeners, CallbackInfo callbackInfo) {
        if (!cache.isAzBypass()) {
            return;
        }

        if (inPacket instanceof C00Handshake) {
            C00Handshake pack = (C00Handshake)inPacket;
            try {
                Field f = ReflectionUtils.findField(C00Handshake.class, String.class);
                f.setAccessible(true);
                String ip = (String)f.get(pack);
                ip = ip.replace("\0FML\0", "");
                
                switch (cache.getBypassType()) {
                    case PACALONE:
                        ip += "\0PAC\0";
                        break;
                    case PAC5DIGIT:
                        ip += "\0PAC00006\0";
                    default:
                        break;
                }

                f.set(pack, ip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    private void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_, CallbackInfo callbackInfo) throws Exception {
        if (cache.isAzBypass()) 
            callbackInfo.cancel();
    }
}
