package com.fantasycraft.forgepermittor.protection;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by thomas15v on 1/05/15.
 */
public class ProtectionManager {

    private final ItemStack ITEM = new ItemStack(Material.LAVA_BUCKET);
    private List<String> enabledPlugins;

    public ProtectionManager(){
        enabledPlugins = Arrays.asList("Towny", "Factions", "PlotSquared", "GriefPrevention", "GriefPreventionPlus");
    }

    public boolean canUseItem(PlayerInteractEvent event, ItemType type){
        if (type == ItemType.Food || type ==  ItemType.Block || type == ItemType.Container || type == ItemType.Weapon)
            return true;
        else
            return checkPlayerInteractEventEvent(event, false);

    }

    public boolean canUseBlock(PlayerInteractEvent event, BlockType type){
        if (type == BlockType.Container)
            return checkPlayerInteractEventEvent(event, true);
        return true;
    }

    private boolean checkPlayerInteractEventEvent(PlayerInteractEvent event, boolean block){
        PlayerInteractEvent checkEvent = new PlayerInteractEvent(event.getPlayer(), event.getAction(),
                block ? null : ITEM,
                block ? new FakeBlock(event.getClickedBlock()) : event.getClickedBlock(),
                event.getBlockFace());

        for (RegisteredListener listener : checkEvent.getHandlers().getRegisteredListeners())
        {
            System.out.println(listener.getPlugin().getName());
            if (enabledPlugins.contains(listener.getPlugin().getName())) {
                try {
                    listener.callEvent(checkEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return !checkEvent.isCancelled();
}
