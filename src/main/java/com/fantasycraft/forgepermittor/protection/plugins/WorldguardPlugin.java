package com.fantasycraft.forgepermittor.protection.plugins;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.protection.IprotectionPlugin;
import com.fantasycraft.forgepermittor.protection.MessageType;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by thomas on 8/16/2014.
 */
public class WorldguardPlugin implements IprotectionPlugin {

    @Getter
    WorldGuardPlugin worldGuard;

    public WorldguardPlugin(WorldGuardPlugin worldGuard) {
        this.worldGuard = worldGuard;
    }

    @Override
    public boolean CanUseBlock(Player player, Block block, BlockType type) {
        if (type == BlockType.Container)
            return getWorldGuard().canBuild(player, block);
        else
            return true;
    }

    @Override
    public boolean CanUseItem(Player player, Location location, ItemType type) {
        if (type == ItemType.Food || type ==  ItemType.Block || type == ItemType.Container || type == ItemType.Weapon)
            return true;
        return getWorldGuard().canBuild(player, location);
    }

    @Override
    public boolean CanBreakBlock(Player player, Block block) {
        return getWorldGuard().canBuild(player, block);
    }

    @Override
    public void SendErrorMessage(Player player, MessageType type) {
        if (type == MessageType.InteractNotAllowed)
            player.sendMessage(ChatColor.DARK_RED  + "You don't have permission to open that in this area.");
        else if (type == MessageType.UsageNotAllowed)
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission for this area.");
        else if (type == MessageType.ToCloseToContainer)
            player.sendMessage(ChatColor.DARK_RED + "Your Container is to close, to someone else his Container.");
    }

    @Override
    public boolean CanDamage(Player player) {
        return this.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).allows(DefaultFlag.PVP);
    }

    @Override
    public String BlockInProtectedLand(Block block, Player player) {
        ApplicableRegionSet regions = getWorldGuard().getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation());
        if (regions.size() > 0)
           for (ProtectedRegion region : regions ){
                if (!region.getId().equalsIgnoreCase("__global__"))
                    return getname();
           }
           return null;
    }

    @Override
    public String getname() {
        return "WorldGuard";
    }

	public WorldGuardPlugin getWorldGuard() {
		return worldGuard;
	}
}
