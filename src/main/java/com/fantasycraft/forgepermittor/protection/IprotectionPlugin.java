package com.fantasycraft.forgepermittor.protection;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by thomas on 8/16/2014.
 */
public interface IprotectionPlugin {

    public boolean CanUseBlock(Player player, Block block, BlockType type);

    public boolean CanUseItem(Player player, Location location, ItemType type);

    public boolean CanBreakBlock(Player player, Block block);

    public void SendErrorMessage(Player player, MessageType type);

    public boolean CanDamage(Player player);

}
