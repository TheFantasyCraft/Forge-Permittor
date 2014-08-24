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
public class LwcPlugin implements IprotectionPlugin {

    @Override
    public boolean CanUseBlock(Player player, Block block, BlockType type) {
        return true;
    }

    @Override
    public boolean CanUseItem(Player player, Location location, ItemType type) {
       /* Block block = location.getBlock();
        if (block.getTypeId() == 0)
            return true;
        Protection protection = LWC.getInstance().findProtection(block);
        protection.getAccess(player.getName(), )*/
        return true;
    }

    @Override
    public boolean CanBreakBlock(Player player, Block block) {
        return true;
    }

    @Override
    public void SendErrorMessage(Player player, MessageType type) {

    }

    @Override
    public boolean CanDamage(Player player) {
        return true;
    }

    @Override
    public String getname() {
        return "LWC";
    }
}
