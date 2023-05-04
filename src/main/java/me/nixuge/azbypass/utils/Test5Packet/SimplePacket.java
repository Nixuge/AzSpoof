package me.nixuge.azbypass.utils.Test5Packet;

import io.netty.buffer.ByteBuf;
import me.nixuge.azbypass.utils.Test5Packet.SimplePacket.SimpleMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SimplePacket implements IMessageHandler<SimpleMessage, IMessage> {
    @Override
    public IMessage onMessage(SimpleMessage message, MessageContext ctx) {
        // just to make sure that the side is correct
        if (ctx.side.isClient()) {
            byte byt = message.simpleByte;
        }
        return null;
    }

    public static class SimpleMessage implements IMessage {
        private byte simpleByte;

        // this constructor is required otherwise you'll get errors (used somewhere in
        // fml through reflection)
        public SimpleMessage() {
        }

        public SimpleMessage(byte simpleByte) {
            this.simpleByte = simpleByte;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            // the order is important
            this.simpleByte = buf.readByte();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeByte(simpleByte);
        }
    }
}