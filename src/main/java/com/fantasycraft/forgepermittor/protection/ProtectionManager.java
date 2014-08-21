package com.fantasycraft.forgepermittor.protection;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thomas on 8/16/2014.
 */
public class ProtectionManager implements IprotectionPlugin {

    @Getter
    List<IprotectionPlugin> Plugins;

    public ProtectionManager(){
        this.Plugins = new LinkedList<IprotectionPlugin>();
    }

    public void RegisterPlugin(IprotectionPlugin plugin){
        this.Plugins.add(plugin);
    }

    public void UnloadPlugin(IprotectionPlugin plugin){
        this.Plugins.remove(plugin);
    }

    @Override
    public boolean CanUseBlock(Player player, Block block, BlockType type) {
        for (IprotectionPlugin p : getPlugins()){
            if (!p.CanUseBlock(player, block, type)){
                p.SendErrorMessage(player, MessageType.InteractNotAllowed);
                return false;
            }

        }
        return true;
    }

    @Override
    public boolean CanUseItem(Player player, Location location, ItemType type) {
        for (IprotectionPlugin p : getPlugins()){
            if (!p.CanUseItem(player, location, type)) {
                p.SendErrorMessage(player, MessageType.UsageNotAllowed);
                return false;
            }
        }
        return true;
    }

    @Override
    @Deprecated //simply don't use
    public void SendErrorMessage(Player player, MessageType type) {}
}
