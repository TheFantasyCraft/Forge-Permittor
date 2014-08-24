package com.fantasycraft.forgepermittor.listeners;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.info.ItemValidator;
import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.nms.util.Util;
import com.fantasycraft.forgepermittor.protection.ProtectionManager;
import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;
import com.griefcraft.scripting.event.LWCProtectionRegisterEvent;
import com.griefcraft.scripting.event.LWCProtectionRegistrationPostEvent;
import com.griefcraft.util.matchers.DoubleChestMatcher;
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

                //Ik its messy but resolving the block type takes clockcycles, we don't want to do that twice.
                if (getProtectionManager().HasPlugin("LWC")){
                    LWCAutoProtect(player, block);
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

    private void LWCAutoProtect(Player player, Block block){

        if (!LWC.ENABLED) {
            return;
        }
        LWC lwc = LWC.getInstance();
        Protection current = lwc.findProtection(block);
        if (current != null) {
            if (current.getProtectionFinder() != null) {
                current.getProtectionFinder().fullMatchBlocks();
                lwc.getProtectionCache().add(current);
            }
            return;
        }
        String autoRegisterType = "private";

        if (!lwc.hasPermission(player, "lwc.create." + autoRegisterType, new String[]{"lwc.create", "lwc.protect"})) {
            return;
        }
        Protection.Type ptype;
        try {
            ptype = Protection.Type.valueOf(autoRegisterType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return;
        }
        if (ptype == null) {
            player.sendMessage("§4LWC_INVALID_CONFIG_autoRegister");
            return;
        }
        if (DoubleChestMatcher.PROTECTABLES_CHESTS.contains(block.getType())) {
            BlockFace[] faces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
            for (BlockFace blockFace : faces) {
                Block face = block.getRelative(blockFace);
                if ((face.getType() == block.getType()) &&
                        (lwc.findProtection(face) != null)) {
                    return;
                }
            }
        }
        try {
            LWCProtectionRegisterEvent evt = new LWCProtectionRegisterEvent(player, block);
            lwc.getModuleLoader().dispatchEvent(evt);
            if (evt.isCancelled()) {
                return;
            }
            Protection protection = lwc.getPhysicalDatabase().registerProtection(block.getTypeId(), ptype, block.getWorld().getName(), player.getName(), "", block.getX(), block.getY(), block.getZ());
            lwc.sendLocale(player, "protection.onplace.create.finalize", new Object[]{"type", lwc.getPlugin().getMessageParser().parseMessage(autoRegisterType.toLowerCase(), new Object[0]), "block", LWC.materialToString(block)});
            if (protection != null) {
                lwc.getModuleLoader().dispatchEvent(new LWCProtectionRegistrationPostEvent(protection));
            }
        } catch (Exception e) {
            lwc.sendLocale(player, "protection.internalerror", new Object[]{"id", "PLAYER_INTERACT"});
            e.printStackTrace();
        }

    }
}


