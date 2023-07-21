package me.nixuge.azspoof.mixins.client.packets;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;

// Prolly unneeded
@Mixin(C00Handshake.class)
public class C00HandshakeMixin {
    @Shadow private int protocolVersion;

    @Shadow private String ip;

    @Shadow private int port;

    @Shadow private EnumConnectionState requestedState;

    /**
     * @author
     * Writes the raw packet data to the data stream.
     */
    @Overwrite
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.protocolVersion);
        buf.writeString(this.ip);
        buf.writeShort(this.port);
        buf.writeVarIntToBuffer(this.requestedState.getId());
    }
}
