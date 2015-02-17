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
    private boolean griefpreventionplus;
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
        this.towny = getFileConfiguration().getBoolean("protection.plugins.towny");
        this.worldguard = getFileConfiguration().getBoolean("protection.plugins.worldguard");
        this.griefpreventions = getFileConfiguration().getBoolean("protection.plugins.griefprevention");
        this.griefpreventionplus = getFileConfiguration().getBoolean("protection.plugins.griefpreventionplus");
        this.factions = getFileConfiguration().getBoolean("protection.plugins.factions");
        this.handlefakeplayers = getFileConfiguration().getBoolean("other.HandleFakePlayers");
        this.DeathMessages = getFileConfiguration().getBoolean("other.FixBrokenDeathMessages");
        this.debug = getFileConfiguration().getBoolean("other.debug");
    }

	public FileConfiguration getFileConfiguration() {
		return fileConfiguration;
	}

	public boolean isProtection() {
		return protection;
	}

	public boolean isTowny() {
		return towny;
	}

	public boolean isWorldguard() {
		return worldguard;
	}

	public boolean isGriefpreventions() {
		return griefpreventions;
	}

	public boolean isHandlefakeplayers() {
		return handlefakeplayers;
	}

	public boolean isFactions() {
		return factions;
	}

	public boolean isDeathMessages() {
		return DeathMessages;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isGriefpreventionplus() {
		return griefpreventionplus;
	}

}
