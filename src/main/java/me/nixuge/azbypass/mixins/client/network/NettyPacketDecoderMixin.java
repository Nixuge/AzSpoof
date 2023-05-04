package me.nixuge.azbypass.mixins.client.network;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumMap;
// import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelFutureListener;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.nixuge.azbypass.utils.Custom127Packet;
import me.nixuge.azbypass.utils.ReflectionUtils;
import me.nixuge.azbypass.utils.Test5Packet.PacketHandler;
import me.nixuge.azbypass.utils.Test5Packet.SimplePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NettyPacketDecoder;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleIndexedCodec;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings("unused")
@Mixin(NettyPacketDecoder.class)
public class NettyPacketDecoderMixin {
    @Shadow
    private EnumPacketDirection direction;

    private static int[] whitelistedPackets = {
        26, //Disconnect
        12, //SPacketUpdateBossInfo
        15, //Chat 
        52, //SPacketEntityHeadLook
        38, //S17PacketEntityLookMove
        57, //SPacketEntityMetadata
        68, //SPacketTimeUpdate
        73, //SPacketEntityTeleport
        74, //SPacketEntityProperties
        5,  //SPacketSpawnPlayer
        45, //SPacketPlayerListItem
        37, //SPacketEntity$S15PacketEntityRelMove
        10, //SPacketBlockAction
        65, //SPacketTeams
        48, //SPacketDestroyEntities
        34, //SPacketParticles
        6,  //SPacketAnimation
        39, //SPacketEntity$S16PacketEntityLook
        0,  //SPacketSpawnObject
        59, //SPacketEntityVelocity
        56, //SPacketDisplayObjective
        66, //SPacketUpdateScore
        46, //SPacketPlayerPosLook
        43, //SPacketPlayerAbilities
        30, //SPacketChangeGameState
        22, //SPacketSetSlot
        32, //SPacketChunkData
        60, //SPacketEntityEquipment
        29, //SPacketUnloadChunk
        31, //SPacketKeepAlive!!!
        11, //SPacketBlockChange
        70, //SPacketSoundEffect
    };

    // For debug purposes
    private static String getProperString(byte b) {
        StringBuilder builder = new StringBuilder();
        builder.append("byte:" + b);
        builder.append("|int:" + (int)b);
        builder.append("|char:" + (char)b);
        return builder.toString();
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuffer, List<Object> p_decode_3_) throws IOException, InstantiationException, IllegalAccessException, Exception
    {
        
        if (byteBuffer.readableBytes() != 0)
        {
            PacketBuffer packetbuffer = new PacketBuffer(byteBuffer);
            int i = packetbuffer.readVarIntFromBuffer();
            Packet<?> packet = ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacket(this.direction, i);

            if (packet == null) {
                if (i == 127) { 
                    
                    System.out.println("127 RECEIVED!");
                    Field f = ReflectionUtils.findField(Minecraft.class, NetworkManager.class);
                    NetworkManager mcNM = (NetworkManager)f.get(Minecraft.getMinecraft());
                    
                    System.out.println("GOT MCNM!");

                    IMessage msg = new SimplePacket.SimpleMessage((byte)i);
                    PacketHandler.net.sendToServer(msg);

                    System.out.println("SENT!");
                    
                    // SimpleIndexedCodec packetCodec = new SimpleIndexedCodec();
                    
                    // FMLEmbeddedChannel channel = NetworkRegistry.INSTANCE.getChannel("PLAY", Side.CLIENT);
                    // channels.get(p_decode_3_)
                    // System.out.println("GOT CHANNEL");
                    // FMLProxyPacket packNew = Test2Packet.sendCustomPacket(127, byteBuffer);
                    // channel.writeAndFlush(packNew).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                    // System.out.println("wrote and flushed !");

                    // FMLProxyPacket pack = Test4Packet.createClientToServerTestPacket(1);
                    // System.out.println("GOT PACKET");
                    // Test4Packet.sendToServer(pack);

                    // System.out.println("GOT McNM !");
                    // mcNM.sendPacket();
                    // System.out.println("Sent packet !");
                }
                // System.out.println("bad packet! (id " + i + ")");

                StringBuilder builder = new StringBuilder();
                // builder.append("new byte[] = {");
                List<Byte> byteList = new ArrayList<>();
                for (int loopI = 0; loopI < byteBuffer.capacity(); loopI ++) {
                    byte b = byteBuffer.getByte(loopI);
                    // builder.append(b + ",");
                    builder.append((char)b);
                    byteList.add(b);
                    // System.out.println(getProperString(b));
                }
                // builder.append("}");
                String finalStr = builder.toString();
                if (!finalStr.contains("disablePlayerPush")) {
                    System.out.println("=====Bad Packet ID ( " + i + ")=====");
                    System.out.println(builder.toString());
                    for (byte b : byteList) {
                        System.out.println(getProperString(b));
                    }
                    // int readableBytes = byteBuffer.readableBytes();
                    // System.out.println(readableBytes);
                    // byteBuffer.ca
                    // System.out.println(byteBuffer.readBytes(readableBytes).read);
                    // throw new IOException("Bad packet id " + i);
                    System.out.println("===================================");
                } else {
                //     //TODO: reimplement this on bukkit
                //     System.out.println("Packet contains the az json shit (id:" + i + ")");
                }

                return;
            }

            // Log if packet incoming is not in the whitelistedPackets list
            //
            // boolean found = false;
            // for (int whitelistedPacketId : whitelistedPackets) {
            //     if (i == whitelistedPacketId) {
            //         found = true;
            //         break;
            //     }
            // }
            // if (!found) {
            //     System.out.println("Packet received. (" + i + ", " + packet.getClass() + ")");
            // }


            // if (packet instanceof SPacketCustomPayload)  {
            //     SPacketCustomPayload pack = (SPacketCustomPayload)packet;
            //     System.out.println("Got custom packet payload");
            //     System.out.println(pack.getChannelName());
            //     PacketBuffer buf = pack.getBufferData();
            //     StringBuilder builder = new StringBuilder();
            //     for (int loopI = 0; loopI < buf.capacity(); loopI ++) {
            //         byte b = buf.getByte(loopI);
            //         // builder.append(b + ",");
            //         builder.append((char)b);
            //         // byteList.add(b);
            //         // System.out.println(getProperString(b));
            //     }
            //     System.out.println(builder.toString());
            // }


            try {
                packet.readPacketData(packetbuffer);
            } catch (Exception e) {
                System.out.println("Exception happened reading packet data (" + packet.getClass() + ").");
            }

            if (packetbuffer.readableBytes() > 0) {
                System.out.println("Packet longer than excepted, ignoring (" + packet.getClass() + ").");
            } else {
                p_decode_3_.add(packet);
            }
        }
    }
}
