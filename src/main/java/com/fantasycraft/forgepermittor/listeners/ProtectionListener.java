package com.fantasycraft.forgepermittor.listeners;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.info.ItemValidator;
import com.fantasycraft.forgepermittor.protection.ProtectionManager;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by thomas on 8/16/2014.
 */
public class ProtectionListener implements Listener {

    @Getter
    private ProtectionManager protectionManager;
    @Getter
    private ItemValidator validator;

    public ProtectionListener(ProtectionManager protectionManager, ItemValidator validator){
        this.protectionManager = protectionManager;
        this.validator = validator;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        try {
            if (event.hasItem() && event.useItemInHand() != Event.Result.DENY){
                ForgePermittor.log("ItemType: " + ForgePermittor.getInstance().getItemValidator().CheckItem(event.getItem()).toString(), true);
                if (!getProtectionManager().CanUseItem(event.getPlayer(), event.getPlayer().getLocation(), getValidator().CheckItem(event.getItem())))
                {
                    event.setUseItemInHand(Event.Result.DENY);
                }
            }
            if (event.hasBlock() &&  event.useInteractedBlock() != Event.Result.DENY) {
                ForgePermittor.log("BlockType: " + ForgePermittor.getInstance().getItemValidator().CheckBlock(event.getClickedBlock()).toString(), true);
                if (!getProtectionManager().CanUseBlock(event.getPlayer(), event.getClickedBlock(), getValidator().CheckBlock(event.getClickedBlock()))) {
                    event.setUseInteractedBlock(Event.Result.DENY);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
