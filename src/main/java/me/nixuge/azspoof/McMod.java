package me.nixuge.azspoof;

import lombok.Getter;
import lombok.Setter;
import me.nixuge.azspoof.command.commands.AzSpoofCommand;
import me.nixuge.azspoof.command.commands.AzSpoofTypeCommand;
import me.nixuge.azspoof.config.Cache;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(
        modid = McMod.MOD_ID,
        name = McMod.NAME,
        version = McMod.VERSION,
        clientSideOnly = true
)
@Getter
@Setter
public class McMod {
    public static final String MOD_ID = "azspoof";
    public static final String NAME = "AzSpoof";
    public static final String VERSION = "1.0.0";

    
    @Getter
    @Mod.Instance(value = McMod.MOD_ID)
    private static McMod instance;

    private final Cache cache = new Cache();
    private Configuration configuration;
    private String configDirectory;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        this.configDirectory = event.getModConfigurationDirectory().toString();
        final File path = new File(this.configDirectory + File.separator + McMod.MOD_ID + ".cfg");
        this.configuration = new Configuration(path);
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new AzSpoofCommand(this.cache));
        ClientCommandHandler.instance.registerCommand(new AzSpoofTypeCommand(this.cache));
    }
}
