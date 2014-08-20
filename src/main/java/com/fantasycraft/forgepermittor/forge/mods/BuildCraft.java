package com.fantasycraft.forgepermittor.forge.mods;

import com.fantasycraft.forgepermittor.forge.IMod;

/**
 * Created by thomas on 8/20/2014.
 */
public class BuildCraft extends IMod {
    public BuildCraft(){
        addContainerInterface("buildcraft.core.ItemBlockBuildCraft");

        addConnectablesInterface("buildcraft.api.transport.IPipeConnection");

        addItemBlockContainerInterface("buildcraft.core.IItemPipe");

    }

    @Override
    public String getName() {
        return "BuildCraft";
    }
}
