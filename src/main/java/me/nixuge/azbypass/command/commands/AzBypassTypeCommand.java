package me.nixuge.azbypass.command.commands;


import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

import me.nixuge.azbypass.MessageBuilder;
import me.nixuge.azbypass.command.AbstractCommand;
import me.nixuge.azbypass.config.Cache;

public class AzBypassTypeCommand extends AbstractCommand {
    private final Cache cache;

    public AzBypassTypeCommand(final Cache cache) {
        super("azbypasstype");

        this.cache = cache;
    }

    @Override
    public List<String> getCommandAliases() {
        ArrayList<String> al = new ArrayList<>();
        al.add("bypasstype");
        return al;
    }

    @Override
    public void onCommand(final ICommandSender sender, final String[] args) {
        String mode = (args.length == 0) ? "NODIGIT" : args[0];
        this.cache.setBypassMode(mode);
        this.tell(new MessageBuilder("Set AzBypass mode to " + this.cache.getBypassType(), TextFormatting.GRAY));
    }
}
