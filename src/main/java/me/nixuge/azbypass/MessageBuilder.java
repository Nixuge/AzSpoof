package me.nixuge.azbypass;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextComponentString;

public class MessageBuilder {
    private final ITextComponent messageObject;

    public MessageBuilder(final String message) {
        this.messageObject = new TextComponentString(message);
    }

    public MessageBuilder(final String message, final TextFormatting colour) {
        this.messageObject = new TextComponentString(message);
        this.messageObject.getStyle().setColor(colour);
    }

    public MessageBuilder addMessage(final MessageBuilder messageBuilder) {
        this.messageObject.appendSibling(messageBuilder.build());
        return this;
    }

    public MessageBuilder addMessage(final String message, final TextFormatting colour) {
        final ITextComponent messageNew = new TextComponentString(message);
        messageNew.getStyle().setColor(colour);
        this.messageObject.appendSibling(messageNew);
        return this;
    }

    public ITextComponent build() {
        return this.messageObject;
    }

    public void sendToPlayer() {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player != null) {
            player.addChatMessage(this.messageObject);
        }
    }
}
