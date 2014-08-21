package com.fantasycraft.forgepermittor.protection.Plugins;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.protection.IprotectionPlugin;
import com.fantasycraft.forgepermittor.protection.MessageType;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by thomas on 8/16/2014.
 */
public class TownyPlugin implements IprotectionPlugin {

    @Getter
    private Towny towny;

    public TownyPlugin(Towny towny){
        this.towny = towny;
    }

    public boolean CanUseBlock(Player player, Block block, BlockType type){

        if (type == BlockType.Container)
            return PlayerCacheUtil.getCachePermission(player, block.getLocation(), Material.CHEST.getId(), (byte) 0, TownyPermission.ActionType.SWITCH);
        else
            return true; //Just to be sure

    }

    public boolean CanUseItem(Player player, Location location, ItemType type){

        if (type == ItemType.Item || type == ItemType.AdvItem)
            return PlayerCacheUtil.getCachePermission(player, location, Material.ENDER_PEARL.getId(), (byte) 0, TownyPermission.ActionType.ITEM_USE);
        else
            return true;
    }

    @Override
    public void SendErrorMessage(Player player, MessageType type) {
        if (this.getTowny().getCache(player).hasBlockErrMsg())
            TownyMessaging.sendErrorMsg(player, this.getTowny().getCache(player).getBlockErrMsg());
    }

}
