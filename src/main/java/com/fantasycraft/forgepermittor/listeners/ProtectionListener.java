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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
                ForgePermittor.log("ItemType: " + getValidator().CheckItem(event.getItem()).toString(), true);
                if (!getProtectionManager().CanUseItem(event.getPlayer(), event.getPlayer().getLocation(), getValidator().CheckItem(event.getItem())))
                {
                    event.setUseItemInHand(Event.Result.DENY);
                    event.setUseInteractedBlock(Event.Result.DENY);
                    return;
                }
            }
            if (event.hasBlock() &&  event.useInteractedBlock() != Event.Result.DENY) {
                ForgePermittor.log("BlockType: " + getValidator().CheckBlock(event.getClickedBlock()).toString(), true);
                if (!getProtectionManager().CanUseBlock(event.getPlayer(), event.getClickedBlock(), getValidator().CheckBlock(event.getClickedBlock()))) {
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
        if ((event.getEntity() instanceof Player) && event.getDamager().getType().getName() == null && !getProtectionManager().CanDamage((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event){
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
               if (!getProtectionManager().CanBreakBlock(player, block))
                   return true;

        } catch (Exception e) {
            ForgePermittor.log(Util.stackTraceToString(e), true);
        }
        return false;
    }

}
