package com.fantasycraft.forgepermittor.protection.Plugins;

import com.fantasycraft.forgepermittor.protection.IprotectionPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by thomas on 8/16/2014.
 */
public class TownyPlugin implements IprotectionPlugin {
    @Override
    public boolean HasAcces(Player player, Location location) {
        return false;
    }
}
