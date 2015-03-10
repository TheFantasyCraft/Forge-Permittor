package com.fantasycraft.forgepermittor.protection.plugins;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.protection.IprotectionPlugin;
import com.fantasycraft.forgepermittor.protection.MessageType;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/16/2014.
 */
public class GriefProtectionPlugin implements IprotectionPlugin {

    private boolean isNewerVersion;
    private Method method;

    public GriefProtectionPlugin(){
        try {
            method = Claim.class.getMethod("allowBuild", Player.class, Material.class);
        } catch (Exception e) {}
        isNewerVersion = method != null;
    }

    @Override
    public boolean CanUseBlock(Player player, Block block, BlockType type) {
        if (type == BlockType.Container)
            return checkContainers(player, block.getLocation());
        return true;
    }

    @Override
    public boolean CanUseItem(Player player, Location location, ItemType type) {
        if (type == ItemType.Food || type ==  ItemType.Block || type == ItemType.Container || type == ItemType.Weapon)
            return true;
        return allowbuild(player, location);
    }

    @Override
    public boolean CanBreakBlock(Player player, Block block) {
        return allowbuild(player, block.getLocation());
    }

    @Override
    public void SendErrorMessage(Player player, MessageType type) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), true, null);
        if (type == MessageType.ToCloseToContainer)
            sendMessage(player, ChatColor.RED, "Your Container is to close, to someone else his Container.");
        else
            sendMessage(player, ChatColor.RED, Messages.NoAccessPermission, new String[]{claim != null ? claim.getOwnerName() : "Herobrine"});

    }

    @Override
    public boolean CanDamage(Player player) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), true, null);
        if (claim != null && claim.allowAccess(player) == null)
            return false;
        return true;
    }

    @Override
    public boolean BlockInProtectedLand(Block block) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(block.getLocation(), true, null);
        return block != null && claim != null;
    }

    @Override
    public String getname() {
        return "GriefPrevention";
    }

    private boolean checkContainers(Player player, Location location)
    {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
        if (claim != null && claim.allowContainers(player) != null)
            return false;
        return true;
    }

    private boolean allowbuild(Player player, Location location)
    {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
        ForgePermittor.log(isNewerVersion + " " + method , true);
        if (claim != null)
            try {
                return isNewerVersion ? (method.invoke(claim, player, player.getItemInHand().getType())) == null : claim.allowBuild(player) == null;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        return true;
    }


    static void sendMessage(Player player, ChatColor color, Messages messageID, String... args)
    {
        sendMessage(player, color, messageID, 0L, args);
    }

    static void sendMessage(Player player, ChatColor color, Messages messageID, long delayInTicks, String... args)
    {
        String message = GriefPrevention.instance.dataStore.getMessage(messageID, args);
        sendMessage(player, color, message);
    }

    static void sendMessage(Player player, ChatColor color, String message)
    {
        if (player != null)
            player.sendMessage(color + message);
    }
}
