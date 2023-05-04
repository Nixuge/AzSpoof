package me.nixuge.azbypass.utils.Test5Packet;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper net;

    public static void initPackets() {
        // SimpleIndexedCodec packetCodec = new SimpleIndexedCodec();
        // channels = NetworkRegistry.INSTANCE.newChannel(channelName, packetCodec);
        
        // FMLEmbeddedChannel channel = NetworkRegistry.INSTANCE.getChannel("PLAY", Side.CLIENT);
        net = NetworkRegistry.INSTANCE.newSimpleChannel("PLAY");
        registerMessage(SimplePacket.class, SimplePacket.SimpleMessage.class);
    }

    private static int nextPacketId = 127;

    private static void registerMessage(Class packet, Class message) {
        net.registerMessage(packet, message, nextPacketId, Side.CLIENT);
        nextPacketId++;
    }
}