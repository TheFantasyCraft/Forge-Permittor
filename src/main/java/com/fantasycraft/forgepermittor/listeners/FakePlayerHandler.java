package com.fantasycraft.forgepermittor.listeners;

import com.fantasycraft.forgepermittor.protection.ProtectionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by thomas on 8/30/2014.
 */

public class FakePlayerHandler implements Listener {

    ProtectionManager protectionManager;

    public FakePlayerHandler(ProtectionManager protectionManager){
        this.protectionManager = protectionManager;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        String name = event.getPlayer().getName();
        if (name.startsWith("[") && name.endsWith("]")){
            protectionManager.CanBreakBlock()

        }
    }

}
