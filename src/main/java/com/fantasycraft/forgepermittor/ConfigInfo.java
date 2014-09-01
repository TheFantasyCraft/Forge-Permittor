package com.fantasycraft.forgepermittor;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by thomas on 9/1/2014.
 */

@Getter
@ToString(exclude = "fileConfiguration")
public class ConfigInfo {

    FileConfiguration fileConfiguration;

    private boolean protection;
    private boolean towny;
    private boolean worldguard;
    private boolean griefpreventions;
    private boolean handlefakeplayers;
    private boolean DeathMessages;

    public ConfigInfo(FileConfiguration fileConfiguration){
        this.fileConfiguration = fileConfiguration;
        reload();
    }

    public void reload(){
        this.protection = getFileConfiguration().getBoolean("protection.enabled");
        this.towny = getFileConfiguration().getBoolean("protection.plugins.towny");
        this.worldguard = getFileConfiguration().getBoolean("protection.plugins.worldguard");
        this.griefpreventions = getFileConfiguration().getBoolean("protection.plugins.griefprevention");
        this.handlefakeplayers = getFileConfiguration().getBoolean("other.HandleFakePlayers");
        this.DeathMessages = getFileConfiguration().getBoolean("other.FixBrokenDeathMessages");
    }

}
