package com.fantasycraft.forgepermittor.listeners;

import com.fantasycraft.forgepermittor.ForgePermittor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by thomas on 8/16/2014.
 */
public class ProtectionListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        try {
            if (event.hasItem()){
                ForgePermittor.log("ItemType: " + ForgePermittor.getInstance().getItemValidator().CheckItem(event.getItem()).toString(), true);
            }
            if (event.hasBlock())
                ForgePermittor.log("BlockType: " +ForgePermittor.getInstance().getItemValidator().CheckBlock(event.getClickedBlock()).toString(), true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
