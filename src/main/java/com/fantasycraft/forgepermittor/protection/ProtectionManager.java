package com.fantasycraft.forgepermittor.protection;

import com.fantasycraft.forgepermittor.info.types.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;

/**
 * Created by thomas15v on 1/05/15.
 */
public class ProtectionManager {

    private final ItemStack ITEM = new ItemStack(Material.LAVA_BUCKET);

    public boolean canUseItem(PlayerInteractEvent event, ItemType type){
        if (type == ItemType.Food || type ==  ItemType.Block || type == ItemType.Container || type == ItemType.Weapon)
            return true;
        else
            return canUseItem(event);

    }

    private boolean canUseItem(PlayerInteractEvent event){
        PlayerInteractEvent checkEvent = new PlayerInteractEvent(event.getPlayer(), event.getAction(), ITEM, event.getClickedBlock(), event.getBlockFace());
        for (RegisteredListener listener : checkEvent.getHandlers().getRegisteredListeners())
        {
            System.out.println(listener.getPlugin().getName());
            if (listener.getPlugin().getName().equalsIgnoreCase("towny")) {
                System.out.print("Calling towny " + checkEvent.isCancelled());
                try {
                    listener.callEvent(checkEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print("towny called " + checkEvent.isCancelled());
            }
        }
        return !checkEvent.isCancelled();
    }

}
