package com.fantasycraft.forgepermittor.listeners;

import com.fantasycraft.forgepermittor.ForgePermittor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by thomas on 8/16/2014.
 */
public class ProtectionListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        List<Material> Ores = Arrays.asList(Material.COAL_ORE, Material.DIAMOND_ORE, Material.REDSTONE_ORE, Material.GOLD_ORE, Material.EMERALD_ORE, Material.IRON_ORE, Material.LAPIS_ORE);

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
