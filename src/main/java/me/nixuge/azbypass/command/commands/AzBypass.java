package me.nixuge.azbypass.command.commands;


import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

import me.nixuge.azbypass.MessageBuilder;
import me.nixuge.azbypass.command.AbstractCommand;
import me.nixuge.azbypass.config.Cache;

public class AzBypass extends AbstractCommand {
    private final Cache cache;

    public AzBypass(final Cache cache) {
        super("azbypass");

        this.cache = cache;
    }

    @Override
    public List<String> getCommandAliases() {
        ArrayList<String> al = new ArrayList<>();
        al.add("bypassaz");
        return al;
    }

    @Override
    public void onCommand(final ICommandSender sender, final String[] args) {
        if (this.cache.isAzBypass()) {
            this.cache.setAzBypass(false);
            this.tell(new MessageBuilder("AzBypass now disabled !", TextFormatting.GRAY));
        } else {
            this.cache.setAzBypass(true);
            this.tell(new MessageBuilder("AzBypass now enabled !", TextFormatting.GRAY));
        }
        this.tell(new MessageBuilder("Please relog for the changes to take effect.", TextFormatting.GRAY));
    }
}
