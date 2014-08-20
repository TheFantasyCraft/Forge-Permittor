package com.fantasycraft.forgepermittor.protection.Plugins;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.protection.IprotectionPlugin;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import lombok.Getter;
import org.bukkit.Location;
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
        return PlayerCacheUtil.getCachePermission(player, player.getLocation(), Integer.valueOf(1979), (byte) 0, TownyPermission.ActionType.SWITCH);

    }

    public boolean CanUseItem(Player player, Location location, ItemType type){
        return false;
    }
}
