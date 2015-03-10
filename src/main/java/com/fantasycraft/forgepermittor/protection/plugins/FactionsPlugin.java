package com.fantasycraft.forgepermittor.protection.plugins;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.protection.IprotectionPlugin;
import com.fantasycraft.forgepermittor.protection.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import techcable.minecraft.factionsapi.Faction;
import techcable.minecraft.factionsapi.Factions;
import techcable.minecraft.factionsapi.FactionsAPI;

/**
 * Created by thomas on 8/16/2014.
 */
public class FactionsPlugin implements IprotectionPlugin {

    private final Factions factions;

    public FactionsPlugin(){
        this.factions = FactionsAPI.getInstance();
    }

    @Override
    public boolean CanUseBlock(Player player, Block block, BlockType type) {
        if (type == BlockType.Container){
            return canAccesLocation(player, block.getLocation());
        }
        return true;
    }

    @Override
    public boolean CanUseItem(Player player, Location location, ItemType type) {
        if (type == ItemType.Food || type ==  ItemType.Block || type == ItemType.Container || type == ItemType.Weapon)
            return true;
        return canAccesLocation(player, location);
    }

    @Override
    public boolean CanBreakBlock(Player player, Block block) {
        return canAccesLocation(player, block.getLocation());
    }

    private boolean canAccesLocation(Player player, Location location){
        Faction faction = factions.getOwningFaction(location);
        return faction.isNone() || faction.isAlly((factions.getFPlayer(player))) || faction.isMember((factions.getFPlayer(player)));
    }

    @Override
    public void SendErrorMessage(Player player, MessageType type) {
        if (type == MessageType.InteractNotAllowed)
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission to open that in this area.");
        else if (type == MessageType.UsageNotAllowed)
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission for this area.");
        else if (type == MessageType.ToCloseToContainer)
            player.sendMessage(ChatColor.DARK_RED + "Your Container is to close, to someone else his Container.");
    }

    @Override
    public boolean CanDamage(Player player) {
        Entity damager = player.getLastDamageCause() instanceof EntityDamageByEntityEvent ? ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager() : null;
        if (damager instanceof Player)
            return factions.getFPlayer(player).getFaction().isEnemy(factions.getFPlayer((OfflinePlayer) damager));
        return factions.getOwningFaction(player.getLocation()).isSafezone();
    }

    @Override
    public boolean BlockInProtectedLand(Block block) {
        return !factions.getOwningFaction(block.getLocation()).isNone();
    }

    @Override
    public String getname() {
        return "Factions";
    }
}
