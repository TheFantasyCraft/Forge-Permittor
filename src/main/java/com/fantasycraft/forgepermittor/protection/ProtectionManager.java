package com.fantasycraft.forgepermittor.protection;

import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 8/16/2014.
 */
public class ProtectionManager {

    @Getter
    List<IprotectionPlugin> Plugins;

    public ProtectionManager(){
        this.Plugins = new ArrayList<IprotectionPlugin>();
    }

    public void RegisterPlugin(IprotectionPlugin plugin){
        for (IprotectionPlugin p : getPlugins()) {
            System.out.println("load: " + p.getname());
            if (p.getname().equalsIgnoreCase(plugin.getname()))
                return;
        }
        this.Plugins.add(plugin);
    }

    public void UnloadPlugin(IprotectionPlugin plugin){
        this.Plugins.remove(plugin);
    }

    public void UnloadPlugin(String plugin){
        for (IprotectionPlugin p : getPlugins()) {
            System.out.println("unload: " + p.getname());
            if (p.getname().equalsIgnoreCase(plugin))
                UnloadPlugin(p);
        }
    }

    public boolean CanUseBlock(Player player, Block block, BlockType type) {
        for (IprotectionPlugin p : getPlugins()){
            if (!p.CanUseBlock(player, block, type)){
                p.SendErrorMessage(player, MessageType.InteractNotAllowed);
                return false;
            }

        }
        return true;
    }

    public boolean CanUseItem(Player player, Location location, ItemType type) {
        for (IprotectionPlugin p : getPlugins()){
            if (!p.CanUseItem(player, location, type)) {
                p.SendErrorMessage(player, MessageType.UsageNotAllowed);
                return false;
            }
        }
        return true;
    }

    public boolean CanBreakBlock(Player player, Block block) {
        for (IprotectionPlugin p : getPlugins()){
            if (!p.CanBreakBlock(player, block)) {
                p.SendErrorMessage(player, MessageType.ToCloseToContainer);
                return false;
            }
        }
        return true;
    }

    public boolean CanDamage(Player player) {
        for (IprotectionPlugin p : getPlugins()){
            if (!p.CanDamage(player)) {
                return false;
            }
        }
        return true;
    }

    public String BlockInProtectedLand(Block block, Player player) {
        for (IprotectionPlugin p : getPlugins()){
            String blockedplugin = p.BlockInProtectedLand(block, player );
            if (blockedplugin != null) {
                return blockedplugin;
            }
        }
        return null;
    }

    public boolean HasPlugin(String name){
        for (IprotectionPlugin p : getPlugins()){
            if (!p.getname().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public String getname() {
        StringBuilder stringBuilder = new StringBuilder();
        for (IprotectionPlugin p : getPlugins())
            stringBuilder.append(p.getname());
        return stringBuilder.toString();
    }

	public List<IprotectionPlugin> getPlugins() {
		return Plugins;
	}
}
