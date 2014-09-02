package com.fantasycraft.forgepermittor.update;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by thomas on 9/2/2014.
 */
public class UpdateNotifierListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        if (event.getPlayer().isOp() || event.getPlayer().hasPermission("*")){
            event.getPlayer().sendMessage(ChatColor.DARK_RED + "Their is a update for Forge-Permittor go download it here: http://dev.bukkit.org/bukkit-plugins/forge-permittor/");
        }
    }

}
