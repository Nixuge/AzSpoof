package me.nixuge.azspoof.command.commands;


import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

import me.nixuge.azspoof.MessageBuilder;
import me.nixuge.azspoof.command.AbstractCommand;
import me.nixuge.azspoof.config.Cache;

public class AzSpoofTypeCommand extends AbstractCommand {
    private final Cache cache;

    public AzSpoofTypeCommand(final Cache cache) {
        super("azspooftype");

        this.cache = cache;
    }

    @Override
    public List<String> getCommandAliases() {
        ArrayList<String> al = new ArrayList<>();
        al.add("spooftype");
        return al;
    }

    @Override
    public void onCommand(final ICommandSender sender, final String[] args) {
        String mode = (args.length == 0) ? "NODIGIT" : args[0];
        this.cache.setSpoofMode(mode);
        this.tell(new MessageBuilder("Set AzSpoof mode to " + this.cache.getSpoofType(), TextFormatting.GRAY));
    }
}
