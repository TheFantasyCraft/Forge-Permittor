package com.fantasycraft.forgepermittor.protection;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by thomas on 8/16/2014.
 */
public interface IprotectionPlugin {

    public boolean HasAcces(Player player, Location location);

}
