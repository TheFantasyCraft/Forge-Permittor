package com.fantasycraft.forgepermittor.listeners;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.info.ItemValidator;
import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.nms.util.Util;
import com.fantasycraft.forgepermittor.protection.ProtectionManager;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by thomas on 8/16/2014.
 */
public class ProtectionListener extends DisableableListener implements Listener {

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
        if (!isEnabled())
            return;
        try {
            if (event.hasItem() && event.useItemInHand() != Event.Result.DENY){
                ForgePermittor.log("ItemType: " + getValidator().CheckItem(event.getItem()).toString(), true);
                if (!getProtectionManager().canUseItem(event, getValidator().CheckItem(event.getItem())))
                {
                    event.setUseItemInHand(Event.Result.DENY);
                    event.setUseInteractedBlock(Event.Result.DENY);

                    return;
                }
            }
            if (event.hasBlock() && event.useInteractedBlock() != Event.Result.DENY) {
                ForgePermittor.log("BlockType: " + getValidator().CheckBlock(event.getClickedBlock()).toString(), true);
                if (!getProtectionManager().canUseBlock(event, getValidator().CheckBlock(event.getClickedBlock()))) {
                    event.setUseInteractedBlock(Event.Result.DENY);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
    {
        if (!isEnabled())
            return;

        if ((event.getEntity() instanceof Player) && event.getDamager().getType().getTypeId() > 200 &&
                !getProtectionManager().canDamage(event)) {
            event.setCancelled(true);
            event.getEntity().setFireTicks(0);
            ((Player) event.getEntity()).setHealth(20);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event){
        if (!isEnabled())
            return;

        if (event.isCancelled())
            return;
        try {

            BlockType type = BlockType.Unknown;
            Player player = event.getPlayer();
            Block block = event.getBlock();

            if (event.getBlock().getTypeId() == 0) {
                ForgePermittor.log("tile: " + getValidator().CheckItem(event.getItemInHand()), true);
                if (getValidator().CheckItem(event.getItemInHand()) == ItemType.Container)
                    type = BlockType.Container;
            } else {
                ForgePermittor.log("tile: " + getValidator().CheckBlock(event.getBlock()), true);
                type = getValidator().CheckBlock(event.getBlock());
            }

            if (type == BlockType.Container) {
                if (CheckBlockPlaceforContainer(player, block.getRelative(BlockFace.NORTH))
                        || CheckBlockPlaceforContainer(player, block.getRelative(BlockFace.SOUTH))
                        || CheckBlockPlaceforContainer(player, block.getRelative(BlockFace.WEST))
                        || CheckBlockPlaceforContainer(player, block.getRelative(BlockFace.EAST))) {
                    event.setCancelled(true);
                }
            }
        }
        catch (Exception e){
            ForgePermittor.log(e.toString(), true);
        }
    }

    private boolean CheckBlockPlaceforContainer(Player player, Block block){
        try {
           if ( block.getTypeId() != 0 && getValidator().CheckBlock(block) == BlockType.Container )
               if (!getProtectionManager().canBreakBlock(player, block))
                   return true;

        } catch (Exception e) {
            ForgePermittor.log(Util.stackTraceToString(e), true);
        }
        return false;
    }

    private void blockItemUse(Player player){
        Inventory inventory = player.getInventory();
        ItemStack item = player.getItemInHand();
        boolean hasreplaced = false;
        inventory.remove(item);
        for (int i = 0; i < inventory.getSize(); i++){
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, item);
                hasreplaced = true;
            }
        }

        if (!hasreplaced)
            player.getWorld().dropItem(player.getLocation(), item);
    }

    @EventHandler
    public void entityInteractEvent(PlayerInteractEntityEvent event){
        if (!event.isCancelled() && event.getRightClicked() != null) {
            boolean allowed = protectionManager.canAccesLocation(event.getPlayer(), event.getRightClicked().getLocation());
            if (!allowed)
                event.setCancelled(true);
        }
    }
}


