package com.fantasycraft.forgepermittor.protection;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by thomas15v on 1/05/15.
 */
public class ProtectionManager {

    private final ItemStack ITEM = new ItemStack(Material.LAVA_BUCKET);
    private List<String> enabledPlugins;
    private final int scanRange = Bukkit.getViewDistance() * 16 + 8;

    public ProtectionManager(List<String> enabledPlugins){
        this.enabledPlugins = enabledPlugins;
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

    private boolean checkPlayerInteractEventEvent(PlayerInteractEvent event, boolean block) {
        PlayerInteractEvent checkEvent = new PlayerInteractEvent(event.getPlayer(), event.getAction(),
                block ? null : ITEM,
                block ? new FakeBlock(event.getClickedBlock()) : event.getClickedBlock(),
                event.getBlockFace());
                callEvent(checkEvent);
                ForgePermittor.log("ReCheckEvent; " + event.isCancelled() + " RealEvent: " + event.isCancelled() , true);


        return !checkEvent.isCancelled();
    }

    private void callEvent(Event event){
        for (RegisteredListener listener : event.getHandlers().getRegisteredListeners()) {
            if (enabledPlugins.contains(listener.getPlugin().getName())) {
                try {
                    listener.callEvent(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean canAccesLocation(Player player, Location location){
        return canBreakBlock(player, location.getBlock());
    }

    public boolean canBreakBlock(Player player, Block block) {
        BlockBreakEvent event = new BlockBreakEvent(block, player);
        callEvent(event);
        return !event.isCancelled();
    }

    public boolean canBreakBlock(Block block){
        for (Entity entity : getNearbyEntities(block.getLocation()))
            if (entity instanceof Player)
                if (!canBreakBlock((Player) entity, block))
                    return false;
        return true;
    }

    private List<Entity> getNearbyEntities(Location location){
        List<Entity> near = new ArrayList<Entity>();
        for(Entity e : location.getWorld().getEntities()) {
            if(e.getLocation().distance(location) <= scanRange)
                near.add(e);
        }
        return near;
    }

    public boolean canDamage(EntityDamageByEntityEvent event) {
        for (Entity entity : getNearbyEntities(event.getEntity().getLocation()) ){
            if (entity != event.getEntity() && entity instanceof Player)
            {
                EntityDamageByEntityEvent checkEvent = new EntityDamageByEntityEvent(entity, event.getEntity(), EntityDamageEvent.DamageCause.CUSTOM, event.getDamage());
                callEvent(checkEvent);
                System.out.println(((Player) entity).getName() + " " + checkEvent.isCancelled());
                if (checkEvent.isCancelled())
                    return false;
            }
        }
        return true;
    }
}
