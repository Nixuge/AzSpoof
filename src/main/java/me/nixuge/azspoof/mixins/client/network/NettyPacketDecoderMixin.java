package me.nixuge.azspoof.mixins.client.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;
import java.util.List;

@Mixin(NettyPacketDecoder.class)
public class NettyPacketDecoderMixin {
    @Final
    @Shadow
    private EnumPacketDirection direction;
    /**
     * @author Nixuge
     * @reason yes
     */
    @Overwrite
    protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws IOException, InstantiationException, IllegalAccessException, Exception {
        if (p_decode_2_.readableBytes() != 0) {
            PacketBuffer packetbuffer = new PacketBuffer(p_decode_2_);
            int i = packetbuffer.readVarIntFromBuffer();
            Packet<?> packet = ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacket(this.direction, i);

            if (packet == null) {
                // System.out.println("Bad packet id: " + i);
                // IGNORING NOW
            }
            else {
                packet.readPacketData(packetbuffer);

                if (packetbuffer.readableBytes() > 0) {
                    // System.out.println("Packet too long");
                    // IGNORING NOW
                }
                else {
                    p_decode_3_.add(packet);
                }
            }
        }
    }
}
