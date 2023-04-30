package me.nixuge.azbypass.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

import me.nixuge.azbypass.McMod;
import me.nixuge.azbypass.MessageBuilder;

public abstract class AbstractCommand extends CommandBase {
    private final String name;
    private String prefix;

    public AbstractCommand(final String name) {
        this(name, McMod.NAME);
    }

    public AbstractCommand(final String name, final String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return this.name;
    }

    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "/" + this.name;
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    @Override
    public void execute(MinecraftServer var1, ICommandSender sender, String[] args) throws CommandException {
        this.onCommand(sender, args);
    }

    public abstract void onCommand(final ICommandSender sender, final String[] args);

    protected void tell(final MessageBuilder message) {
        new MessageBuilder("")
                .addMessage(this.prefix != null ? this.prefix + "> " : "", TextFormatting.BLUE)
                .addMessage(message)
                .sendToPlayer();
    }


    protected void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
}
