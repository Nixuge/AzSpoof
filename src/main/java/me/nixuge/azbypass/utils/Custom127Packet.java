package me.nixuge.azbypass.utils;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.server.SPacketDisconnect;

public class Custom127Packet implements Packet {

    public byte byteSent = 0;

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        // throw new UnsupportedOperationException("Unimplemented method 'readPacketData'");
        // SPacketDisconnect
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        // throw new UnsupportedOperationException("Unimplemented method 'writePacketData'");
        buf.writeByte(127);
        buf.writeByte(1);
    }

    @Override
    public void processPacket(INetHandler handler) {
        throw new UnsupportedOperationException("Unimplemented method 'processPacket'");
    }
}
