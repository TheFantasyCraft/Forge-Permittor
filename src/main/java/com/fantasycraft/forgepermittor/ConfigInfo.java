package com.fantasycraft.forgepermittor;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Created by thomas on 9/1/2014.
 */

@Getter
@ToString(exclude = "fileConfiguration")
public class ConfigInfo {

    FileConfiguration fileConfiguration;

    private boolean protection;
    private List<String> plugins;
    private boolean handlefakeplayers;
    private boolean factions;
    private boolean DeathMessages;
    private boolean debug;

    public ConfigInfo(FileConfiguration fileConfiguration){
        this.fileConfiguration = fileConfiguration;
        reload();
    }

    private void reload(){
        //don't reload make a new instance
        this.protection = getFileConfiguration().getBoolean("protection.enabled");
        this.plugins = getFileConfiguration().getStringList("protection.plugins");
        this.factions = getFileConfiguration().getBoolean("protection.plugins.factions");
        this.handlefakeplayers = getFileConfiguration().getBoolean("other.HandleFakePlayers");
        this.DeathMessages = getFileConfiguration().getBoolean("other.FixBrokenDeathMessages");
        this.debug = getFileConfiguration().getBoolean("other.debug");
    }

}
