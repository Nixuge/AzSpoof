package me.nixuge.azspoof.command.commands;


import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

import me.nixuge.azspoof.MessageBuilder;
import me.nixuge.azspoof.command.AbstractCommand;
import me.nixuge.azspoof.config.Cache;

public class AzSpoofCommand extends AbstractCommand {
    private final Cache cache;

    public AzSpoofCommand(final Cache cache) {
        super("azspoof");

        this.cache = cache;
    }

    @Override
    public List<String> getCommandAliases() {
        ArrayList<String> al = new ArrayList<>();
        al.add("spoofaz");
        return al;
    }

    @Override
    public void onCommand(final ICommandSender sender, final String[] args) {
        if (this.cache.isAzSpoof()) {
            this.cache.setAzSpoof(false);
            this.tell(new MessageBuilder("AzSpoof now disabled !", TextFormatting.GRAY));
        } else {
            this.cache.setAzSpoof(true);
            this.tell(new MessageBuilder("AzSpoof now enabled !", TextFormatting.GRAY));
        }
        this.tell(new MessageBuilder("Please relog for the changes to take effect.", TextFormatting.GRAY));
    }
}
