package com.fantasycraft.forgepermittor.protection.plugins;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import com.fantasycraft.forgepermittor.protection.IprotectionPlugin;
import com.fantasycraft.forgepermittor.protection.MessageType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by thomas on 8/16/2014.
 */
public class LWCPlugin implements IprotectionPlugin {

    @Override
    public boolean CanUseBlock(Player player, Block block, BlockType type) {
        return false;
    }

    @Override
    public boolean CanUseItem(Player player, Location location, ItemType type, boolean hasblock) {
        return false;
    }

    @Override
    public boolean CanBreakBlock(Player player, Block block) {
        return false;
    }

    @Override
    public void SendErrorMessage(Player player, MessageType type) {

    }
}
