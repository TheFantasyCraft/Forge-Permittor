package com.fantasycraft.forgepermittor.protection.Plugins;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.protection.IprotectionPlugin;
import com.fantasycraft.forgepermittor.protection.MessageType;
import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.FPerm;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by thomas on 8/16/2014.
 */
public class FactionsPlugin implements IprotectionPlugin {

    @Override
    public boolean CanUseBlock(Player player, Block block, BlockType type) {
        if (type == BlockType.Container)
            return FPerm.BUILD.has(player, block.getLocation());
        return true;
    }

    @Override
    public boolean CanUseItem(Player player, Location location, ItemType type) {
        if (type == ItemType.Food || type ==  ItemType.Block || type == ItemType.Container || type == ItemType.Weapon)
            return true;
        return FPerm.BUILD.has(player, location);
    }

    @Override
    public boolean CanBreakBlock(Player player, Block block) {
        return FPerm.BUILD.has(player, block.getLocation());
    }

    @Override
    public void SendErrorMessage(Player player, MessageType type) {
        if (type == MessageType.InteractNotAllowed)
            FPlayers.i.get(player).sendMessage(ChatColor.DARK_RED + "You don't have permission to open that in this area.");
        else if (type == MessageType.UsageNotAllowed)
            FPlayers.i.get(player).sendMessage(ChatColor.DARK_RED + "You don't have permission for this area.");
        else if (type == MessageType.ToCloseToContainer)
            FPlayers.i.get(player).sendMessage(ChatColor.DARK_RED + "Your Container is to close, to someone else his Container.");
    }

    @Override
    public boolean CanDamage(Player player) {
        return true;
    }

    @Override
    public String BlockInProtectedLand(Block block, Player player) {
        return Board.getFactionAt(block).getTag().equalsIgnoreCase("warzone") || Board.getFactionAt(block).getTag().equalsIgnoreCase("safezone") ? getname() : null;
    }

    @Override
    public String getname() {
        return "Factions";
    }
}
