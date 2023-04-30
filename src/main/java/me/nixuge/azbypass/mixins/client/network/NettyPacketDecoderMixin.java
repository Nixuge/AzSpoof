package me.nixuge.azbypass.mixins.client.network;

import java.io.IOException;
// import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NettyPacketDecoder;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketDisconnect;

@Mixin(NettyPacketDecoder.class)
public class NettyPacketDecoderMixin {
    @Shadow
    private EnumPacketDirection direction;

    // private String getProperString(byte b) {
    //     StringBuilder builder = new StringBuilder();
    //     builder.append("byte:" + b);
    //     builder.append("|int:" + (int)b);
    //     builder.append("|char:" + (char)b);
    //     return builder.toString();
    // }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuffer, List<Object> p_decode_3_) throws IOException, InstantiationException, IllegalAccessException, Exception
    {
        
        if (byteBuffer.readableBytes() != 0)
        {
            PacketBuffer packetbuffer = new PacketBuffer(byteBuffer);
            int i = packetbuffer.readVarIntFromBuffer();
            Packet<?> packet = ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacket(this.direction, i);
            
            if (packet == null) {
                StringBuilder builder = new StringBuilder();
                // builder.append("new byte[] = {");
                // List<Byte> byteList = new ArrayList<>();
                for (int loopI = 0; loopI < byteBuffer.capacity(); loopI ++) {
                    byte b = byteBuffer.getByte(loopI);
                    // builder.append(b + ",");
                    builder.append((char)b);
                    // byteList.add(b);
                    // System.out.println(getProperString(b));
                }
                // builder.append("}");
                // String finalStr = builder.toString();
                // if (!finalStr.contains("disablePlayerPush")) {
                    System.out.println("=====Bad Packet ID ( " + i + ")=====");
                    System.out.println(builder.toString());
                    // for (byte b : byteList) {
                    //     System.out.println(getProperString(b));
                    // }
                    // int readableBytes = byteBuffer.readableBytes();
                    // System.out.println(readableBytes);
                    // byteBuffer.ca
                    // System.out.println(byteBuffer.readBytes(readableBytes).read);
                    // throw new IOException("Bad packet id " + i);
                    System.out.println("===================================");
                // } else {
                //     //TODO: reimplement this on bukkit
                //     System.out.println("Packet contains the az json shit (id:" + i + ")");
                // }

                return;
            }

            int[] whitelistedPackets = {
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
            boolean found = false;
            for (int whitelistedPacketId : whitelistedPackets) {
                if (i == whitelistedPacketId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Packet received. (" + i + ", " + packet.getClass() + ")");
            }
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

            if (i == 26) {
                System.out.println("PACKET " + i + " RECEIVED!!!");
                System.out.println(packet.getClass());
                SPacketDisconnect pack = (SPacketDisconnect)packet;
                System.out.println(pack.getReason());
            }

            try {
                packet.readPacketData(packetbuffer);
            } catch (Exception e) {
                System.out.println("Exception happened reading packet data");
            }
            

            if (packetbuffer.readableBytes() > 0) {
                // throw new IOException("Packet " + ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getId() + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetbuffer.readableBytes() + " bytes extra whilst reading packet " + i);
                System.out.println("Packet longer than excepted, ignoring.");
            } else {
                p_decode_3_.add(packet);
            }
        }
    }
}
