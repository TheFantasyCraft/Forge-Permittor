package com.fantasycraft.forgepermittor.protection;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thomas on 8/16/2014.
 */
public class ProtectionManager {

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

}
